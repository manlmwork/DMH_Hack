package com.fu.ismb.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.fu.ismb.dao.AccountDao;
import com.fu.ismb.dao.CartDao;
import com.fu.ismb.database.DBHelper;
import com.fu.ismb.entity.Account;
import com.fu.ismb.entity.Cart;
import com.fu.ismb.model.CartInfo;
import com.fu.ismb.task.AsyncResponse;
import com.fu.ismb.task.SyncCartTask;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by manlm on 10/1/2016.
 */

public class SyncCartService extends Service {

    public static final long SYNC_INTERVAL = 300 * 1000; //  2h

    private DBHelper dbHelper;

    private AccountDao accountDao;

    private CartDao cartDao;

    private SyncCartTask syncCartTask;

    // run on another Thread to avoid crash
    private Handler mHandler = new Handler();
    // timer handling
    private Timer mTimer = null;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        // cancel if already existed
        if (mTimer != null) {
            mTimer.cancel();
        } else {
            // recreate new
            mTimer = new Timer();
        }
        // schedule task
        mTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(), 0, SYNC_INTERVAL);
    }

    class TimeDisplayTimerTask extends TimerTask {

        @Override
        public void run() {
            // run on another thread
            mHandler.post(new Runnable() {

                @Override
                public void run() {
                    Log.d("[RUN]", "[run] Start");
                    if (isOnline()) {
                        if (dbHelper == null) {
                            dbHelper = new DBHelper(getApplicationContext());
                        }
                        if (accountDao == null) {
                            accountDao = new AccountDao(dbHelper);
                        }
                        if (cartDao == null) {
                            cartDao = new CartDao(dbHelper);
                        }

                        Account account = accountDao.getAll().get(0);

                        CartInfo cartInfo = new CartInfo();
                        cartInfo.setBotFbId(account.getBotFbId());
                        cartInfo.setCart(cartDao.getBy(DBHelper.CART_COLUMN_IS_FOUND, String.valueOf(1)));

                        syncCartTask = new SyncCartTask();
                        syncCartTask.delegate = new AsyncResponse() {
                            @Override
                            public void processFinish(Object output) {
                                List<Cart> cart = (List<Cart>) output;
                                if (!cart.isEmpty()) {

                                    for (int i = 0; i < cart.size(); i++) {
                                        cartDao.delete(cart.get(i));
                                    }
                                }
                            }
                        };

                        syncCartTask.execute(cartInfo, account.getToken());

                    }
                }
            });
        }

        private boolean isOnline() {
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }
    }
}
