package com.fu.ismb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.estimote.sdk.SystemRequirementsChecker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.fu.ismb.R;
import com.fu.ismb.dao.AccountDao;
import com.fu.ismb.dao.CartDao;
import com.fu.ismb.dao.ProductDao;
import com.fu.ismb.database.DBHelper;
import com.fu.ismb.entity.Account;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DBHelper dbHelper;

    private AccountDao accountDao;

    private ProductDao productDao;

    private CartDao cartDao;

    private LoginButton loginButton;

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_main);

        if (Profile.getCurrentProfile() != null) {
            changeManageActivity();
        }

        loginButton = (LoginButton) findViewById(R.id.login_button);
        if (this.dbHelper == null) {
            this.dbHelper = new DBHelper(getApplicationContext());
        }
        if (this.accountDao == null) {
            this.accountDao = new AccountDao(dbHelper);
        }
        if (this.productDao == null) {
            this.productDao = new ProductDao(dbHelper);
        }
        if (this.cartDao == null) {
            this.cartDao = new CartDao(dbHelper);
        }

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_LONG).show();

                Profile profile = Profile.getCurrentProfile();

                List<Account> accountList = new ArrayList<>();
                while (accountList.isEmpty()) {
                    accountList = accountDao.getAll();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Account account = accountList.get(0);

                account.setAppFbId(profile.getId());
                account.setFirstName(profile.getFirstName());
                account.setLastName(profile.getLastName());

                accountDao.update(account);

                if (account.getPhone() != null) {
                    changeGetDataActivity();
                } else {
                    changePhoneActivity();
                }
            }

            @Override
            public void onCancel() {
                Toast.makeText(MainActivity.this, "Login attempt canceled.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(MainActivity.this, "Login attempt failed.", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SystemRequirementsChecker.checkWithDefaultDialogs(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void changePhoneActivity() {
        Intent myIntent = new Intent(this, InsertPhoneActivity.class);
        startActivity(myIntent);
    }

    private void changeManageActivity() {
        Intent myIntent = new Intent(this, ManageActivity.class);
        startActivity(myIntent);
    }

    private void changeGetDataActivity() {
        Intent myIntent = new Intent(this, GetDataActivity.class);
        startActivity(myIntent);
    }
}
