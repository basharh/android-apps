package com.basharh.criminalintent;

import android.support.v4.app.Fragment;
import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment(){
        // return new CrimeFragment();

        // Get the crimeId that should be passed to the CrimeFragment
        // constructor.
        UUID crimeId = (UUID)getIntent()
            .getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);

        return CrimeFragment.newInstance(crimeId);
    }
}
