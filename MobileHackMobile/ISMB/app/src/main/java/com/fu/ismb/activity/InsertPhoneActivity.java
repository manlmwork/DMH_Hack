package com.fu.ismb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.fu.ismb.R;
import com.fu.ismb.dao.AccountDao;
import com.fu.ismb.database.DBHelper;
import com.fu.ismb.entity.Account;
import com.fu.ismb.service.DetectBeaconService;
import com.fu.ismb.task.AsyncResponse;
import com.fu.ismb.task.SendPhoneInfoTask;


public class InsertPhoneActivity extends AppCompatActivity {

    private EditText txtPhone;

    private TextInputLayout inputLayoutPhone;

    private DBHelper dbHelper;

    private AccountDao accountDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_phone);

        txtPhone = (EditText) findViewById(R.id.txtPhone);
        inputLayoutPhone = (TextInputLayout) findViewById(R.id.input_layout_phone);
        requestFocus(txtPhone);
    }

    public void savePhone(View view) {

        if (this.dbHelper == null) {
            this.dbHelper = new DBHelper(getApplicationContext());
        }
        if (this.accountDao == null) {
            this.accountDao = new AccountDao(dbHelper);
        }

        EditText phoneNum = (EditText) findViewById(R.id.txtPhone);
        String phone = phoneNum.getText().toString();

        String regexStr = "^[0-9]{10,13}$";

        if (phone.length() < 10 || phone.length() > 13 || !phone.matches(regexStr)) {
            inputLayoutPhone.setError(getString(R.string.err_msg_phone));
            requestFocus(txtPhone);

        } else {
            inputLayoutPhone.setErrorEnabled(false);

            Account account = accountDao.getAll().get(0);
            account.setPhone(phone);
            accountDao.update(account);
            SendPhoneInfoTask sendPhoneInfoTask = new SendPhoneInfoTask();
            sendPhoneInfoTask.delegate = new AsyncResponse() {
                @Override
                public void processFinish(Object output) {

                    Account account = accountDao.getAll().get(0);
                    account.setBotFbId(String.valueOf(output));
                    accountDao.update(account);

//                    startService();
//                finishAffinity();
//                System.exit(0);
                    changeActivity();
                }
            };
            sendPhoneInfoTask.execute(account.getAppFbId(), account.getFirstName()
                    , account.getLastName(), phone, account.getToken());

            Toast.makeText(this, "Saved Phone", Toast.LENGTH_LONG).show();
        }


    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    // Method to start the service
    public void startService() {
        startService(new Intent(getBaseContext(), DetectBeaconService.class));
    }

    // Method to stop the service
    public void stopService() {
        stopService(new Intent(getBaseContext(), DetectBeaconService.class));
    }

    private void changeActivity() {
        Intent myIntent = new Intent(this, GetDataActivity.class);
        startActivity(myIntent);
    }
}
