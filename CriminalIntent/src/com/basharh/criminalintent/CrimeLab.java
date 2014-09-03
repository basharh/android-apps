package com.basharh.criminalintent;

import android.content.Context;
import java.util.ArrayList;
import java.util.UUID;
import android.util.Log;

/* This is a singleton class which stores  a list of Crime objects */
public class CrimeLab {

    private static final String TAG = "CrimeLab";

    private ArrayList<Crime> mCrimes;
    private static CrimeLab sCrimeLab;
    private Context mAppContext; // Still not sure why this is used

    private CrimeLab(Context appContext) {
        Log.d(TAG, "Initializing Crime Lab");
        mAppContext = appContext;
        mCrimes = new ArrayList<Crime>();
        // Initialize the CrimeLab with a list of 100 Crime's
        for (int i = 0; i < 100; i++) {
            Crime c = new Crime();
            c.setTitle("Crime #" + i);
            c.setSolved(i % 2 == 0); // Every other one
            mCrimes.add(c);
        }
    }

    /* This method is used to obtain an application-wide CrimeLab
     * object which we can use to store Crimes.
     * As part of its construction, a CrimeLab requires a Context which
     * it uses to construct a CrimeLab.
     * */
    public static CrimeLab get(Context c) {
        if (sCrimeLab == null)
            sCrimeLab = new CrimeLab(c.getApplicationContext());

        return sCrimeLab;
    }

    public ArrayList<Crime> getCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID id) {
        for (Crime c : mCrimes) {
            if ( c.getId().equals(id) )
                return c;
        }
        return null;
    }
}
