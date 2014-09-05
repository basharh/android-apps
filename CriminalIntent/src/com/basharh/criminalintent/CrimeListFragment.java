package com.basharh.criminalintent;

import java.util.ArrayList;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.CheckBox;
import android.content.Intent;

public class CrimeListFragment extends ListFragment {

    private static final String TAG = "CrimeListFragment";
    private static final int REQUEST_CRIME = 1;

    private ArrayList<Crime> mCrimes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getActivity() is a convenience method which returns the
        // activity that's hosting the fragment.
        getActivity().setTitle(R.string.crimes_title);
        mCrimes = CrimeLab.get(getActivity()).getCrimes();

        CrimeAdapter adapter = new CrimeAdapter(mCrimes);

        // setListAdapter() is a convenience method in ListFragment
        // which is used to set the ListView's adapter to the provided
        // adapter.
        setListAdapter(adapter);
    }

    /* We will use onResume to refresh the data of the list. The data
     * is refreshed by calling notifyDataSetChanged on the adapter
     * which causes it to regenerate the data in the ListFragment's
     * ListView. */
    @Override
    public void onResume() {
        super.onResume();
        ((CrimeAdapter)getListAdapter()).notifyDataSetChanged();
    }

    /* This method handles receiving the result from activities
     * launched from this fragment via calls to
     * startActivityForResult() */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CRIME) {
            // Handle result
        }
    }

    /* Handle the click on the list items. The getListAdapter() adapter
     * method returns the ListAdapter that provides the data to the
     * ListView of the ListFragment. */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id){

        //Crime c = (Crime)(getListAdapter()).getItem(position);
        Crime c = ((CrimeAdapter)getListAdapter()).getItem(position);

        // Start CrimePagerActivity
        Intent i = new Intent(getActivity(), CrimePagerActivity.class);

        i.putExtra(CrimeFragment.EXTRA_CRIME_ID, c.getId());
        //startActivity(i);
        startActivityForResult(i, REQUEST_CRIME);
    }

    // An inner class which will serve as a ListAdapter for the
    // ListFragment
    private class CrimeAdapter extends ArrayAdapter<Crime> {
        public CrimeAdapter(ArrayList<Crime> crimes) {
            super(getActivity(), 0, crimes);
        }

        /* The default implementation of getView returns a TextView
         * with the list object string rep as contents. To customize
         * the view that's return for the position, we override this
         * method and return a different view. */
        @Override
        public View getView(int position, View convertView,
            ViewGroup parent){

            // If we weren't given a view, inflate one
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                    .inflate(R.layout.list_item_crime, null);
            }

            int titleViewRId = R.id.crime_list_item_titleTextView;
            int dateViewRId = R.id.crime_list_item_dateTextView;
            int solvedCheckBoxRId = R.id.crime_list_item_solvedCheckBox;

            // Configure the view for this Crime
            Crime c = getItem(position);

            TextView titleTextView =
                (TextView)convertView.findViewById( titleViewRId );
            titleTextView.setText(c.getTitle());

            TextView dateTextView =
                (TextView)convertView.findViewById(dateViewRId);
            dateTextView.setText(c.getDate().toString());

            CheckBox solvedCheckBox =
                (CheckBox)convertView.findViewById( solvedCheckBoxRId );
            solvedCheckBox.setChecked(c.isSolved());

            return convertView;
        }
    }
}
