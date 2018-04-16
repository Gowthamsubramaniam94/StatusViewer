package com.saymehow.whatsappstatus;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

public class WSFragmentManager {

    private FragmentActivity myContext;

    /**
     * Last fragment tag
     */
    public static String myLastTag = "";

    public WSFragmentManager(FragmentActivity aContext) {
        myContext = aContext;
    }

    /**
     * Load Fragment
     *
     * @param aFragment
     * @param aUri
     * @param aTag
     * @param aTitleStr
     * @param aBundle
     */
    public void updateContent(Fragment aFragment, Uri aUri, String aTag,
                              String aTitleStr, Bundle aBundle) {
        try {

            Log.e("TAG Screen name", aTag);

            final FragmentManager fm = myContext.getSupportFragmentManager();
            final FragmentTransaction tr = fm.beginTransaction();

            Fragment currentFragment = fm.findFragmentByTag(myLastTag);
            if (currentFragment != null) {
                tr.hide(currentFragment);
                Log.i(myLastTag, "Hidded");
            }

            if (aFragment.isAdded()) {
                Log.i(aTag, "Already Added");
                tr.show(aFragment);
            } else {
                if (aBundle != null)
                    aFragment.setArguments(aBundle);
                tr.add(R.id.frame_container, aFragment, aTag);
                tr.addToBackStack(aTag);
                Log.i(aTag, "Newly Added");

            }


            if (android.os.Build.VERSION.SDK_INT > 23) {
                // only for gingerbread and newer versions
                tr.commit();
            } else {
                tr.commit();
                fm.executePendingTransactions();
            }
            myLastTag = aTag;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
