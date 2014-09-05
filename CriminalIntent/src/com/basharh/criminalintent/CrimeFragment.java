package com.basharh.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.EditText;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.app.Activity;
import android.text.TextWatcher;
import android.text.Editable;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.content.Intent;
import java.util.Date;
import java.util.UUID;
import android.util.Log;

public class CrimeFragment extends Fragment {

    private static final String TAG = "CrimeFragment";
    public static final String EXTRA_CRIME_ID =
        "com.bignerdranch.android.criminalintent.crime_id";

    private static final String DIALOG_DATE = "date";
    private static final int REQUEST_DATE = 0;

    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;


    private void updateDate() {
        mDateButton.setText(mCrime.getDate().toString());
    }

    /* A constructor of CrimeFragment's that accept a UUID for the
     * crimeId for the Crime that should be shown */
    public static CrimeFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CRIME_ID, crimeId);
        CrimeFragment fragment = new CrimeFragment();
        // Fragment now has a Bundle as its arguments.
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == REQUEST_DATE) {
            Date date = (Date)data
                .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            updateDate();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //UUID crimeId = (UUID)getActivity().getIntent()
            //.getSerializableExtra(EXTRA_CRIME_ID);

        UUID crimeId = (UUID)getArguments().getSerializable(EXTRA_CRIME_ID);

        // retrieve a Crime object based on the value of the crimeId
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
    }

    /* The return view of onCreateView becomes the root view of the
     * fragment. This is where we inflate any layout resources and
     * modify the views before returning the root view for the fragment
     * to display. */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
            Bundle savedInstanceState) {

        /* Inflates fragment's views by passing in the ID for the
         * fragment layout. */
        View v = inflater.inflate(R.layout.fragment_crime, parent, false);

        mTitleField = (EditText)v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            public void onTextChanged( CharSequence c, int start,
                    int before, int count) {
                Log.d(TAG, String.format( "title: %s", c.toString() ) );
                mCrime.setTitle( c.toString() );
            }
            public void beforeTextChanged( CharSequence c, int start,
                    int count, int after) {
                // This space intentionally left blank
            }
            public void afterTextChanged(Editable c) {
                // This one too
            }
        });

        /* The data button by default shows the current date */
        mDateButton = (Button)v.findViewById( R.id.crime_date );
        updateDate();
        // When the button is pressed, show the DatePickerFragment on
        // the activity that's hosting this CrimeFragment.
        mDateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment
                    .newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
            }
        });

        mSolvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked( mCrime.isSolved() );
        mSolvedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(TAG, String.format( "isChecked: %s", isChecked ) );
                mCrime.setSolved(isChecked); // Set the crime's solved property
            }
        });

        return v;
    }
}
