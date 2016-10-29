package com.fu.ismb.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.login.LoginManager;
import com.fu.ismb.R;
import com.fu.ismb.dao.AccountDao;
import com.fu.ismb.dao.CartDao;
import com.fu.ismb.database.DBHelper;
import com.fu.ismb.entity.Account;
import com.fu.ismb.fragment.CartFragment;
import com.fu.ismb.fragment.MapFragment;
import com.fu.ismb.service.DetectBeaconService;
import com.fu.ismb.service.SyncCartService;

public class ManageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;

    private DBHelper dbHelper;

    private AccountDao accountDao;

    private CartDao cartDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_manage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        navigationView.getMenu().getItem(0).setChecked(true);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new CartFragment()).commit();

        if (this.dbHelper == null) {
            this.dbHelper = new DBHelper(getApplicationContext());
        }
        if (this.accountDao == null) {
            this.accountDao = new AccountDao(dbHelper);
        }

        Account account = accountDao.getAll().get(0);
        LinearLayout header = (LinearLayout) navigationView.getHeaderView(0);
        TextView headerText = (TextView) header.getChildAt(1);
        headerText.setText(account.getFirstName() + " " + account.getLastName());
        TextView headerPhone = (TextView) header.getChildAt(2);
        headerPhone.setText(account.getPhone());

        startSyncCartService();
        startDetectBeaconService();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;

        if (id == R.id.nav_settings) {
            showInputDialog();
        } else if (id == R.id.nav_map) {
            fragment = new MapFragment();
            navigationView.getMenu().getItem(2).setChecked(true);
        } else if (id == R.id.nav_cart) {
            fragment = new CartFragment();
            navigationView.getMenu().getItem(0).setChecked(true);
        } else if (id == R.id.nav_logout) {
            LoginManager.getInstance().logOut();
            stopSyncCartService();
            stopDetectBeaconService();
            changeMainActivity();
        }
        if (fragment != null) {
            this.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, fragment)
                    .commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changeMainActivity() {
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
    }

    public void showInputDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.input)
                .content(R.string.err_msg_phone)
                .inputType(InputType.TYPE_CLASS_NUMBER)
                .inputRange(10, 13)
                .positiveText(R.string.Save)
                .input(R.string.input_hint, 0, false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        Toast.makeText(ManageActivity.this, "Save " + input.toString(), Toast.LENGTH_SHORT).show();
                        Account account = accountDao.getAll().get(0);
                        account.setPhone(input.toString());
                        accountDao.update(account);

                        LinearLayout header = (LinearLayout) navigationView.getHeaderView(0);
                        TextView headerPhone = (TextView) header.getChildAt(2);
                        headerPhone.setText(input.toString());

                        navigationView.getMenu().getItem(0).setChecked(true);
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new CartFragment()).commit();
                    }
                }).show();
    }

    private void startSyncCartService() {
        startService(new Intent(getBaseContext(), SyncCartService.class));
    }

    private void stopSyncCartService() {
        stopService(new Intent(getBaseContext(), SyncCartService.class));
    }

    private void startDetectBeaconService() {
        startService(new Intent(getBaseContext(), DetectBeaconService.class));
    }

    private void stopDetectBeaconService() {
        stopService(new Intent(getBaseContext(), DetectBeaconService.class));
    }
}
