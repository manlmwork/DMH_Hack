package com.fu.ismb.util;

import android.os.SystemClock;

import com.fu.ismb.model.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by manlm on 10/24/2016.
 */

public class RunningAverageFilter {

    private static final long DEFAULT_SAMPLE_EXPIRATION_MILLISECONDS = 2000; /* 2 seconds */

    private static long sampleExpirationMilliseconds = DEFAULT_SAMPLE_EXPIRATION_MILLISECONDS;

    private List<Measurement> mMeasurements = new ArrayList<>();

    public void addMeasurement(Point point) {
        Measurement measurement = new Measurement();
        measurement.point = point;
        measurement.timestamp = SystemClock.elapsedRealtime();
        mMeasurements.add(measurement);
    }

    public boolean noMeasurementsAvailable() {
        return mMeasurements.isEmpty();
    }

    public Point calculatePoint() {
        refreshMeasurements();
        int size = mMeasurements.size();
        int startIndex = 0;
        int endIndex = size - 1;
        if (size > 2) {
            startIndex = size / 10 + 1;
            endIndex = size - size / 10 - 2;
        }

        double sumX = 0;
        double sumY = 0;
        double sumZ = 0;
        Point point;
        for (int i = startIndex; i <= endIndex; i++) {
            point = mMeasurements.get(i).point;
            sumX += point.getX();
            sumY += point.getY();
            sumZ += point.getZ();
        }
        int devide = endIndex - startIndex + 1;
        return new Point(sumX / devide, sumY / devide, sumZ / devide);
    }

    private synchronized void refreshMeasurements() {
        List<Measurement> newMeasurements = new ArrayList<>();
        Iterator<Measurement> iterator = mMeasurements.iterator();
        while (iterator.hasNext()) {
            Measurement measurement = iterator.next();
            if (SystemClock.elapsedRealtime() - measurement.timestamp < sampleExpirationMilliseconds) {
                newMeasurements.add(measurement);
            }
        }
        mMeasurements = newMeasurements;
//
    }

    private class Measurement implements Comparable<Measurement> {

        Point point;
        long timestamp;

        @Override
        public int compareTo(Measurement arg0) {
            return point.compareTo(arg0.point);
        }
    }

    public static void setSampleExpirationMilliseconds(long newSampleExpirationMilliseconds) {
        sampleExpirationMilliseconds = newSampleExpirationMilliseconds;
    }
}
