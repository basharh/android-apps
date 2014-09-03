package com.basharh.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.EditText;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.text.TextWatcher;
import android.text.Editable;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import java.util.UUID;
import android.util.Log;

public class CrimeFragment extends Fragment {

    private static final String TAG = "CrimeFragment";
    public static final String EXTRA_CRIME_ID =
        "com.bignerdranch.android.criminalintent.crime_id";

    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;

    /* A constructor of CrimeFragment's that accept a UUID for the
     * crimeId for the Crime that should be shown */
    public static CrimeFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CRIME_ID, crimeId);
        CrimeFragment fragment = new CrimeFragment();
        // fragment now has a Bundle as its arguments.
        fragment.setArguments(args);
        return fragment;
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

        /* Add the listener for Title EditText widget in the fragment's
         * view */
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
        mDateButton.setText( mCrime.getDate().toString() );
        mDateButton.setEnabled( false ); // disable the button

        mSolvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked( mCrime.isSolved() );
        mSolvedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(TAG, String.format( "isChecked: %s", isChecked ) );
                mCrime.setSolved(isChecked); // Set the crime's solved property
            }
        });

        // Update the title edit text view with the title of the crime
        mTitleField = (EditText)v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            public void onTextChanged( CharSequence c, int start,
                    int before, int count) {
            }

            public void beforeTextChanged( CharSequence c, int start,
                    int count, int after) {
                // This space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // This one too
            }

        });

        return v;
    }
}
