package com.fu.ismb.util;

import com.fu.ismb.model.Point;

/**
 * Created by manlm on 9/28/2016.
 */

public class GeometryHelper {

    /**
     * Calc user's current coordinates
     *
     * @param pointA     triangle vertices
     * @param pointB     triangle vertices
     * @param pointC     triangle vertices
     * @param distanceSB
     * @param distanceSC
     * @return
     */
    public Point calcPoint(Point pointA, Point pointB, Point pointC, double distanceSA
            , double distanceSB, double distanceSC) {
//        double xA = pointA.getX();
//        double yA = pointA.getY();
//
//        double xB = pointB.getX();
//        double yB = pointB.getY();
//
//        double xC = pointC.getX();
//        double yC = pointC.getY();
//
//        double yByX = (Math.pow(xB, 2) + Math.pow(yB, 2) - Math.pow(distanceSB, 2)
//                - Math.pow(xC, 2) - Math.pow(yC, 2) + Math.pow(distanceSC, 2) + 2 * (xB - xC))
//                / (2 * yC - yB);
//
//        double a = Math.pow(yByX, 2) + 1;
//        double b = -2 * (xB + yB * yByX);
//        double c = Math.pow(xB, 2) + Math.pow(yB, 2) - Math.pow(distanceSB, 2);
//
//        double delta = Math.pow(b, 2) - 4 * a * c;
//
//        double x1 = (-b + Math.sqrt(delta)) / (2 * a);
//        double y1 = x1 * yByX;
//
//        double x2 = (-b - Math.sqrt(delta)) / (2 * a);
//        double y2 = x2 * yByX;
//
//        double difference1 = Math.abs(distanceSA - calcDistance(x1, y1, xA, yA));
//        double difference2 = Math.abs(distanceSA - calcDistance(x2, y2, xA, yA));
//
//        if (difference1 < difference2) {
//            return new Point(x1, y1);
//        }
//
//        return new Point(x2, y2);
        return null;
    }

    /**
     * Calculate distance between 2 points
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    private static double calcDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    /**
     * Check if a point is in triangle
     *
     * @param pointA triangle vertices
     * @param pointB triangle vertices
     * @param pointC triangle vertices
     * @param pointS user's current coordinates
     * @return
     */
    private static boolean isInTriangle(Point pointA, Point pointB, Point pointC, Point pointS) {
        double determinantASAC = calcDeterminant(pointA.getX() - pointS.getX(), pointA.getY() - pointS.getY()
                , pointA.getX() - pointC.getX(), pointA.getY() - pointC.getY());

        double determinantBSBA = calcDeterminant(pointB.getX() - pointS.getX(), pointB.getY() - pointS.getY()
                , pointB.getX() - pointA.getX(), pointB.getY() - pointA.getY());

        double determinantCSCB = calcDeterminant(pointC.getX() - pointS.getX(), pointC.getY() - pointS.getY()
                , pointC.getX() - pointB.getX(), pointC.getY() - pointB.getY());

        return determinantASAC * determinantBSBA > 0 && determinantBSBA * determinantCSCB > 0;
    }

    /**
     * Calculate determinant of 2 vectors
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    private static double calcDeterminant(double x1, double y1, double x2, double y2) {
        return x1 * y2 - x2 * y1;
    }

//    public static void main(String[] args) {
//        System.out.println(calcDistance(-0.30,1006.37,300,500));
//    }
}
