package com.fu.ismb.service;

import com.estimote.sdk.repackaged.gson_v2_3_1.com.google.gson.Gson;
import com.fu.ismb.dao.CartDao;
import com.fu.ismb.database.DBHelper;
import com.fu.ismb.entity.Cart;
import com.fu.ismb.model.ProductResponse;
import com.fu.ismb.model.SaveData;
import com.fu.ismb.util.Common;
import com.fu.ismb.util.Constant;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.List;

/**
 * Created by manlm on 9/1/2016.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private DBHelper dbHelper;
    private CartDao cartDao;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (this.dbHelper == null) {
            this.dbHelper = new DBHelper(getApplicationContext());
        }
        if (this.cartDao == null) {
            this.cartDao = new CartDao(dbHelper);
        }

        Gson gson = new Gson();
        ProductResponse productResponse = gson.fromJson(remoteMessage.getData().get("data"), ProductResponse.class);
        int sttCode = productResponse.getStatusCode();
        if (sttCode == Constant.STATUS_CODE.ADD.getValue()) {
            List<Cart> cart = cartDao.getBy("productId", productResponse.getList().get(0).toString());
            if (cart.isEmpty()) {
                Cart cart1 = new Cart();
                cart1.setProductId(Common.safeLongToInt(productResponse.getList().get(0).getProductId()));
                cart1.setIsFound(0);
                cartDao.insert(cart1);
            }
        } else if (sttCode == Constant.STATUS_CODE.REMOVE.getValue()) {
            List<Cart> cart = cartDao.getBy("productId", productResponse.getList().get(0).toString());
            if (!cart.isEmpty()) {
                cartDao.delete(cart.get(0));
            }
        } else if (sttCode == Constant.STATUS_CODE.RESET.getValue()) {
            cartDao.deleteAll();
        } else if (sttCode == Constant.STATUS_CODE.CLONE.getValue()) {
            List<Cart> cart;
            List<SaveData> productIdList = productResponse.getList();
            int size = productIdList.size();
            for (int i = 0; i < size; i++) {
                cart = cartDao.getBy("productId", productIdList.get(i).toString());
                if (!cart.isEmpty()) {
                    Cart cart1 = new Cart();
                    cart1.setProductId(Common.safeLongToInt(productIdList.get(0).getProductId()));
                    cart1.setIsFound(0);
                    cart1.setLastUpdate(System.currentTimeMillis());
                    cartDao.insert(cart1);
                }
            }
        }
    }
}
