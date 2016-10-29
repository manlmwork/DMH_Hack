package com.fu.ismb.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.EstimoteSDK;
import com.estimote.sdk.MacAddress;
import com.estimote.sdk.Region;
import com.estimote.sdk.Utils;
import com.fu.ismb.R;
import com.fu.ismb.activity.MainActivity;
import com.fu.ismb.dao.AccountDao;
import com.fu.ismb.dao.BeaconDao;
import com.fu.ismb.dao.CartDao;
import com.fu.ismb.dao.ProductDao;
import com.fu.ismb.database.DBHelper;
import com.fu.ismb.entity.Cart;
import com.fu.ismb.entity.Product;
import com.fu.ismb.fragment.AreaName;
import com.fu.ismb.model.Point;
import com.fu.ismb.model.Rectangle;
import com.fu.ismb.util.RunningAverageFilter;
import com.fu.ismb.util.Trilateration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import de.greenrobot.event.EventBus;

/**
 * Created by PhucNT on 9/15/2016.
 */
public class DetectBeaconService extends Service {

    private DBHelper dbHelper;

    private AccountDao accountDao;

    private ProductDao productDao;

    private BeaconDao beaconDao;

    private CartDao cartDao;

    private static final String TAG = "DetectBeaconService";

    private static final String DEFAULT_UUID = "B9407F30-F5F8-466E-AFF9-25556B57FE6E";

    private BeaconManager beaconManager;

    private String curArea = null;

    private String curAreaF = "";

    private int flag = 0;

    com.fu.ismb.entity.Beacon B1Entity;
    com.fu.ismb.entity.Beacon B2Entity;
    com.fu.ismb.entity.Beacon B3Entity;

    private RunningAverageFilter runningAverageFilter;

    /**
     * indicates how to behave if the service is killed
     */
    int mStartMode;

    /**
     * interface for clients that bind
     */
    IBinder mBinder;

    /**
     * indicates whether onRebind should be used
     */
    boolean mAllowRebind;

    /**
     * The service is starting, due to a call to startService()
     */
    @Override
    public int onStartCommand(Intent intent, final int flags, int startId) {
        // Let it continue running until it is stopped.

        EstimoteSDK.initialize(getApplicationContext()
                , getResources().getString(R.string.estimote_app_id)
                , getResources().getString(R.string.estimote_app_token));

        beaconManager = new BeaconManager(getApplicationContext());
        beaconManager.setBackgroundScanPeriod(200, 0);

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
        if (this.beaconDao == null) {
            this.beaconDao = new BeaconDao(dbHelper);
        }

        runningAverageFilter = new RunningAverageFilter();

        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list) {
//                if (list.size()>=1){
//                    Double D1 = beaconDao.calculateAccuracy(list.get(0).getMeasuredPower(), list.get(0).getRssi());
//                    Double D2 = Utils.computeAccuracy(list.get(0));
//                    Log.e("DDDBeacon", "D1 " + D1);
//                    Log.e("DDDBeacon", "D2 " + D2);
//                }

                if (list.size() >= 3) {
                    List<Beacon> newList = new ArrayList<>(list);
                    // sort by minor
                    Collections.sort(newList, new com.fu.ismb.entity.Beacon.BeaconSDKComparator());
                    // parse to entity
                    List<com.fu.ismb.entity.Beacon> beaconEntity = beaconDao.getListBeacon(newList);
                    // sort by minor
                    Collections.sort(beaconEntity, new com.fu.ismb.entity.Beacon.BeaconComparatorbyMinor());
                    // set distance to list entity
                    for (int k = 0; k < beaconEntity.size(); k++) {
                        beaconEntity.get(k).setDistance(Utils.computeAccuracy(newList.get(k)));
                    }
                    // sort by distance and z
                    Collections.sort(beaconEntity, new com.fu.ismb.entity.Beacon.BeaconComparatorbyDistanceAndZ());

                    for (int i = 1; i < beaconEntity.size() - 1; i++) {
                        if (beaconEntity.get(i - 1).getZ() == beaconEntity.get(i).getZ() && beaconEntity.get(i).getZ() == beaconEntity.get(i + 1).getZ()) {
                            B1Entity = beaconEntity.get(i - 1);
                            B2Entity = beaconEntity.get(i);
                            B3Entity = beaconEntity.get(i + 1);
                            break;
                        }
                    }
                    Beacon B1 = findBeacon(newList, B1Entity.getMinor());
                    Beacon B2 = findBeacon(newList, B2Entity.getMinor());
                    Beacon B3 = findBeacon(newList, B3Entity.getMinor());

                    Double D1 = beaconDao.calculateAccuracy(B1.getMeasuredPower(), B1.getRssi());
                    Double D2 = beaconDao.calculateAccuracy(B2.getMeasuredPower(), B2.getRssi());
                    Double D3 = beaconDao.calculateAccuracy(B3.getMeasuredPower(), B3.getRssi());

                    Point A = new Point();
                    A.setX(B1Entity.getX());
                    A.setY(B1Entity.getY());
                    A.setZ(B1Entity.getZ());

                    Point B = new Point();
                    B.setX(B2Entity.getX());
                    B.setY(B2Entity.getY());
                    B.setZ(B2Entity.getY());

                    Point C = new Point();
                    C.setX(B3Entity.getX());
                    C.setY(B3Entity.getY());
                    C.setZ(B3Entity.getZ());

                    Point sPoint = Trilateration.calcTrilateration(A, B, C, D1, D2, D3);
                    Log.e("rectangle", "areaN Point:" + sPoint.getX() + " _ " + sPoint.getY());
                    if (sPoint.getX() >= 0 && sPoint.getY() >= 0) {

                        runningAverageFilter.addMeasurement(sPoint);

                        sPoint = runningAverageFilter.calculatePoint();

                        Rectangle rectangle = beaconDao.findBeacons(sPoint);
                        String areaName = beaconDao.getAreaName(rectangle);
                        if (flag == 0 && !"".equals(areaName)) {
                            curAreaF = areaName;
                        }
                        if (!"".equals(curAreaF) && areaName.equals(curAreaF)) {
                            flag++;
                        } else {
                            curAreaF = areaName;
                            flag = 0;
                        }
                        Log.e("rectangle", "areaN S x:" + sPoint.getX());
                        Log.e("rectangle", "areaN S y:" + sPoint.getY());
                        Log.e("rectangle", "curF: " + curAreaF);
                        Log.e("rectangle", "Flag: " + flag);

                        if (flag >= 5 && !areaName.equals(curArea)) {
                            flag = 0;
                            curArea = areaName;
                            AreaName data = new AreaName(areaName, sPoint.getX(), sPoint.getY());
                            EventBus.getDefault().postSticky(data);
                            Log.e("OTTO ", "OTTO area :" + areaName);
                            List<Product> productList = cartDao.getProductInCartByAreaName(areaName);
                            if (!productList.isEmpty()) {
                                showNotification("ISMB thông báo", productList.get(0).getAreaName(), productList);
                                Cart cart;
                                for (Product product : productList) {
                                    cart = cartDao.getByPrimary(product.getId());
                                    cart.setIsFound(1);
                                    cart.setLastUpdate(System.currentTimeMillis());
                                    cartDao.update(cart);
                                }
                            }
                        }
                    }
                }
            }
        }
    );

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startRanging(new Region(
                        "monitored region",
                        UUID.fromString(DEFAULT_UUID), null, null));
            }
        }
    );

    Toast.makeText(this,"Service Started",Toast.LENGTH_LONG).show();

    return START_STICKY;
}

    public void showNotification(String title, String areaName, List<Product> list) {
        Intent notifyIntent = new Intent(this, MainActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
                new Intent[]{notifyIntent}, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.InboxStyle ibStyle = new Notification.InboxStyle();
        ibStyle.addLine("Khu vực: " + areaName);
        for (int j = 0; j < list.size(); j++) {
            ibStyle.addLine("Sản phẩm: " + list.get(j).getName());

        }
//        ibStyle.setSummaryText("+3 more");

        Notification notification = new Notification.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
//                .setStyle(new Notification.InboxStyle()
//                        .addLine("Khu vực: " + AreaName)
//                        .addLine("Sản phẩm 1")
//                        .addLine(list.get(0).getName())
//                        .setSummaryText("+3 more"))
                .setStyle(ibStyle)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setPriority(Notification.PRIORITY_MAX)
                .build();


        notification.flags |= Notification.FLAG_SHOW_LIGHTS;
        notification.ledARGB = 0xff00ff00;
        notification.ledOnMS = 300;
        notification.ledOffMS = 1000;

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }


    /**
     * A client is binding to the service with bindService()
     */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Called when all clients have unbound with unbindService()
     */
    @Override
    public boolean onUnbind(Intent intent) {
        return mAllowRebind;
    }

    /**
     * Called when a client is binding to the service with bindService()
     */
    @Override
    public void onRebind(Intent intent) {
        // nothing to do here
    }

    /**
     * Called when The service is no longer used and is being destroyed
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }

    public Beacon findBeacon(List<Beacon> list, int minor) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getMinor() == minor) {
                return list.get(i);
            }
        }
        return null;
    }
}
