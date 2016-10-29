package com.fu.ismb.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import com.fu.ismb.R;
import com.fu.ismb.dao.CartDao;
import com.fu.ismb.database.DBHelper;
import com.fu.ismb.entity.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by PhucNT on 9/30/2016.
 */

public class CartFragment extends ListFragment {
    private CartDao cartDao;
    private DBHelper dbHelper;

    ArrayList<HashMap<String, String>> data = new ArrayList<>();
    SimpleAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.fragment_cart, container, false);
        if (this.dbHelper == null) {
            this.dbHelper = new DBHelper(getContext());
        }
        if (this.cartDao == null) {
            this.cartDao = new CartDao(dbHelper);
        }
        List<Product> listpro = cartDao.sortedListCart();

        HashMap<String, String> hashMap;
        for (int i = 0; i < listpro.size(); i++) {
            hashMap = new HashMap<>();
            hashMap.put("Name", listpro.get(i).getName());
            hashMap.put("AreaName", listpro.get(i).getAreaName());
            data.add(hashMap);
        }

        String[] from = {"Name", "AreaName"};

        int[] to = {R.id.txtItem, R.id.txtSubItem};

        adapter = new SimpleAdapter(getActivity(), data, R.layout.row_layout, from, to);
        setListAdapter(adapter);
        return v;

    }


}
