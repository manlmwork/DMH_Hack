package com.fu.ismb.model;

import com.fu.ismb.entity.Beacon;

import java.util.List;

/**
 * Created by PhucNT on 10/20/2016.
 */

public class BeaconModel {

    private List<Beacon> beaconList;

    private long lastSync;

    public List<Beacon> getBeaconList() {
        return beaconList;
    }

    public void setBeaconList(List<Beacon> beaconList) {
        this.beaconList = beaconList;
    }

    public long getLastSync() {
        return lastSync;
    }

    public void setLastSync(long lastSync) {
        this.lastSync = lastSync;
    }
}
