package com.fu.ismb.util;

import com.fu.ismb.model.Point;

/**
 * Created by manlm on 10/9/2016.
 */

public class Trilateration {

    private Trilateration() {
        // Default Constructor
    }

    private static Point translateCoordinateSystem(Point origin, Point point) {
        return new Point(point.getX() - origin.getX()
                , point.getY() - origin.getY()
                , point.getZ() - origin.getZ());
    }

    private static Point reverseTranslateCoordinateSystem(Point origin, Point point) {
        return new Point(point.getX() + origin.getX()
                , point.getY() + origin.getY()
                , point.getZ() + origin.getZ());
    }

    private static double calcRotationAngle(Point point) {
        return Math.acos(Math.abs(point.getX()) / Math.sqrt(Math.pow(point.getX(), 2)
                + Math.pow(point.getY(), 2)));
    }

    private static Point rotate(Point point, double angle) {
        double cosAngle = Math.cos(angle);
        double sinAngle = Math.sin(angle);
        return new Point(point.getX() * cosAngle + point.getY() * sinAngle
                , point.getY() * cosAngle - point.getX() * sinAngle
                , point.getZ());
    }

    public static Point calcTrilateration(Point pointA, Point pointB, Point pointC
            , double distanceSA, double distanceSB, double distanceSC) {

        Point pointB1 = translateCoordinateSystem(pointA, pointB);
        Point pointC1 = translateCoordinateSystem(pointA, pointC);

        double rotationAngle = calcRotationAngle(pointB1);
        Point pointB2 = rotate(pointB1, rotationAngle);
        Point pointC2 = rotate(pointC1, rotationAngle);

        double x = (Math.pow(distanceSA, 2) - Math.pow(distanceSB, 2) + Math.pow(pointB2.getX(), 2))
                / (2 * pointB2.getX());
        double y = (Math.pow(distanceSA, 2) - Math.pow(distanceSC, 2) + Math.pow(pointC2.getX(), 2) + Math.pow(pointC2.getY(), 2)) / (2 * pointC2.getY())
                - ((pointC2.getX() / pointC2.getY()) * x);

        double z1 = Math.sqrt(Math.pow(distanceSA, 2) - Math.pow(x, 2) - Math.pow(y, 2));
        double z2 = -z1;

        Point pointZ1 = rotate(new Point(x, y, z1), Math.toRadians(-Math.toDegrees(rotationAngle)));
        Point pointZ2 = rotate(new Point(x, y, z2), Math.toRadians(-Math.toDegrees(rotationAngle)));

        Point pointZ1Final = reverseTranslateCoordinateSystem(pointA, pointZ1);
        Point pointZ2Final = reverseTranslateCoordinateSystem(pointA, pointZ2);

        return pointZ1Final;
    }
}
