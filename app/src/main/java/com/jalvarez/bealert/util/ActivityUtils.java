package com.jalvarez.bealert.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by jalvarez on 2/17/17.
 * This is a file created for the project BeAlert
 * <p>
 * Javier Alvarez Gonzalez
 * Android Developer
 * javierag0292@gmail.com
 * San Jose, Costa Rica
 */

public class ActivityUtils {

    /**
     * The fragment is added to the container view with id frameId. The operation is
     * performed by the fragmentManager.
     *
     */
    public static void addFragmentToActivity (FragmentManager fragmentManager, Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

    /**
     * The fragment is replaced in the container view with id. The operation is
     * performed by the fragmentManager.
     *
     */
    public static void replaceFragmentInActivity (FragmentManager fragmentManager, Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment);
        transaction.commit();
    }

}
