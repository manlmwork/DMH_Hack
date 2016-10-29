package com.fu.ismb.activity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;

import com.fu.ismb.R;
import com.fu.ismb.dao.AccountDao;
import com.fu.ismb.dao.AreaDao;
import com.fu.ismb.dao.BeaconDao;
import com.fu.ismb.dao.CartDao;
import com.fu.ismb.dao.ProductDao;
import com.fu.ismb.dao.SyncDao;
import com.fu.ismb.database.DBHelper;
import com.fu.ismb.entity.Account;
import com.fu.ismb.entity.Area;
import com.fu.ismb.entity.Beacon;
import com.fu.ismb.entity.Cart;
import com.fu.ismb.entity.Product;
import com.fu.ismb.entity.Sync;
import com.fu.ismb.model.AreaModel;
import com.fu.ismb.model.BeaconModel;
import com.fu.ismb.model.Data;
import com.fu.ismb.model.ProductModel;
import com.fu.ismb.model.SaveData;
import com.fu.ismb.task.AsyncResponse;
import com.fu.ismb.task.GetCartTask;
import com.fu.ismb.task.GetDataTask;
import com.fu.ismb.util.Common;

import java.util.List;

public class GetDataActivity extends AppCompatActivity implements AsyncResponse {

    private GetDataTask getDataTask = new GetDataTask();

    private DBHelper dbHelper;

    private AccountDao accountDao;

    private ProductDao productDao;

    private BeaconDao beaconDao;

    private AreaDao areaDao;

    private CartDao cartDao;

    private SyncDao syncDao;

    ProgressBar mprogressBar;

    ObjectAnimator anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_data);

        mprogressBar = (ProgressBar) findViewById(R.id.circular_progress_bar);
        anim = ObjectAnimator.ofInt(mprogressBar, "progress", 0, 100);
        anim.setDuration(100000);
        anim.setInterpolator(new DecelerateInterpolator());
        anim.start();

        if (isOnline()) {
            if (this.dbHelper == null) {
                this.dbHelper = new DBHelper(getApplicationContext());
            }
            if (this.accountDao == null) {
                this.accountDao = new AccountDao(dbHelper);
            }
            if (this.cartDao == null) {
                this.cartDao = new CartDao(dbHelper);
            }

            getDataTask.delegate = this;

            getDataTask.execute(accountDao.getAll().get(0).getToken());

            GetCartTask getCartTask = new GetCartTask();
            getCartTask.delegate = new AsyncResponse() {

                @Override
                public void processFinish(Object output) {
                    List<SaveData> productIdList = (List<SaveData>) output;
                    if (productIdList != null) {
                        if (!productIdList.isEmpty()) {
                            cartDao.deleteAll();
                            for (int i = 0; i < productIdList.size(); i++) {
                                Cart cart = new Cart();
                                cart.setProductId(Common.safeLongToInt(productIdList.get(i).getProductId()));
                                cart.setIsFound(0);
                                cartDao.insert(cart);
                            }
                        }
                    }

                }
            };

            Account account = accountDao.getAll().get(0);
            getCartTask.execute(account.getToken(), account.getBotFbId());
        }
    }

    @Override
    public void processFinish(Object output) {

        if (this.dbHelper == null) {
            this.dbHelper = new DBHelper(getApplicationContext());
        }
        if (this.productDao == null) {
            this.productDao = new ProductDao(dbHelper);
        }
        if (this.beaconDao == null) {
            this.beaconDao = new BeaconDao(dbHelper);
        }
        if (this.areaDao == null) {
            this.areaDao = new AreaDao(dbHelper);
        }
        if (this.syncDao == null) {
            this.syncDao = new SyncDao(dbHelper);
        }

        Data data = (Data) output;
        List<Product> productList = data.getProductList().getProductList();
        List<Beacon> beaconList = data.getBeaconList().getBeaconList();
        List<Area> areaList = data.getAreaList().getAreaList();
        List<Sync> syncList = syncDao.getAll();

        if (productList != null && !productList.isEmpty()) {
            for (Product product : productList) {
                if (productDao.getByPrimary(product.getId()) == null) {
                    productDao.insert(product);
                } else {
                    productDao.update(product);
                }
            }
            saveSync(syncList, data.getProductList().getLastSync());
        }

        if (beaconList != null && !beaconList.isEmpty()) {
            for (Beacon beacon : beaconList) {
                if (beaconDao.getByPrimary(beacon.getId()) == null) {
                    beaconDao.insert(beacon);
                } else {
                    beaconDao.update(beacon);
                }
            }
            saveSync(syncList, data.getBeaconList().getLastSync());
        }

        if (areaList != null && !areaList.isEmpty()) {
            for (Area area : areaList) {
                if (areaDao.getByPrimary(area.getId()) == null) {
                    areaDao.insert(area);
                } else {
                    areaDao.update(area);
                }
            }
            saveSync(syncList, data.getAreaList().getLastSync());
        }


        anim.setDuration(0);

        Intent myIntent = new Intent(this, ManageActivity.class);
        startActivity(myIntent);
    }

    private boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private void saveSync(List<Sync> syncList, long lastsync) {
        if (syncList != null && !syncList.isEmpty()) {
            syncList.get(0).setLastSync(lastsync);
        } else {
            Sync sync = new Sync();
            sync.setId("1");
            sync.setLastSync(lastsync);
        }

    }
}
