package com.fu.ismb.service;

import android.util.Log;

import com.fu.ismb.dao.AccountDao;
import com.fu.ismb.database.DBHelper;
import com.fu.ismb.entity.Account;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Created by manlm on 9/1/2016.
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    private DBHelper dbHelper;

    private AccountDao accountDao;

    @Override
    public void onTokenRefresh() {

        //Getting registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        if (this.dbHelper == null) {
            this.dbHelper = new DBHelper(getApplicationContext());
        }
        if (this.accountDao == null) {
            this.accountDao = new AccountDao(dbHelper);
        }

        Account account = new Account();
        account.setToken(refreshedToken);
        accountDao.insert(account);

        // subscribe a topic
        FirebaseMessaging.getInstance().subscribeToTopic("news");

        //Displaying token on logcat
        Log.d(TAG, "Refreshed token: " + refreshedToken);

    }

    private void sendRegistrationToServer(String token) {
        //You can implement this method to store the token on your server
        //Not required for current project
    }
}
