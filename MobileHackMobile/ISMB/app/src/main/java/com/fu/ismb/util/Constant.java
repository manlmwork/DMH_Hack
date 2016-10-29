package com.fu.ismb.util;

/**
 * Created by manlm on 9/21/2016.
 */

public class Constant {

    public static final String SERVER_URL ="https://e1a52e04.ap.ngrok.io/api/";

    public enum STATUS_CODE {
        ADD(201),REMOVE(202),RESET(203), CLONE(204);

        private int value;

        STATUS_CODE(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private Constant() {
        // default constructor
    }
}
