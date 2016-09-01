package it.missioneims.ims;

import android.app.ProgressDialog;
import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.ViewStub;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView;
            switch ( getArguments().getInt(ARG_SECTION_NUMBER) )
            {
                case 1:
                    textView = (TextView) rootView.findViewById(R.id.section_label);
                    textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
                    break;
                case 2:
                    CreateEventsView(rootView);
                    break;
                case 3:
                    CreateMembersView(rootView);
                    break;
                case 4:
                    //CreateContactView(rootView);
                    break;
                default:
                    textView = (TextView) rootView.findViewById(R.id.section_label);
                    textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            }
            return rootView;
        }

        private void CreateContactView(View rootView) {

        }

        private void CreateEventsView(View rootView) {

            /*WebRequest request = new WebRequest();
            String response = request.makeWebServiceCall("http://localhost:57460/api/events", WebRequest.GETRequest);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(response);
            */

            // Calling async task to get json
            GetEvents events = new GetEvents();
            events.rootView = rootView;
            events.execute();
        }

        // URL to get contacts JSON
        private static String eventsurl = "http://192.168.1.6:57460/api/events";

        // JSON Node names
        private static final String TAG_EVENTINFO = "eventsinfo";
        private static final String TAG_ID = "Id";
        private static final String TAG_TITLE = "Title";
        private static final String TAG_DESCRIPTION = "Description";
        private static final String TAG_IMAGEURL = "ImageURL";


        private ArrayList<HashMap<String, String>> ParseJSON(String json) throws JSONException {
            if (json != null) {
                try {
                    // Hashmap for ListView
                    ArrayList<HashMap<String, String>> studentList = new ArrayList<HashMap<String, String>>();

                    JSONArray students = new JSONArray(json);

                    // looping through All Students
                    for (int i = 0; i < students.length(); i++) {
                        JSONObject c = students.getJSONObject(i);

                        String id = c.getString(TAG_ID);
                        String name = c.getString(TAG_TITLE);
                        String email = c.getString(TAG_DESCRIPTION);
                        String address = c.getString(TAG_IMAGEURL);

                        // tmp hashmap for single student
                        HashMap<String, String> student = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        student.put(TAG_ID, id);
                        student.put(TAG_TITLE, name);
                        student.put(TAG_DESCRIPTION, email);
                        student.put(TAG_IMAGEURL, address);

                        // adding student to students list
                        studentList.add(student);
                    }
                    return studentList;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }
            } else {
                //Log.e("ServiceHandler", "Couldn't get any data from the url");
                return null;
            }
        }
        /**
         * Async task class to get json by making HTTP call
         */
        public class GetEvents extends AsyncTask<Void, Void, Void> {
            public View rootView;
            // Hashmap for ListView
            ArrayList<HashMap<String, String>> list;
            JSONArray arrayJson;
            @Override
            protected Void doInBackground(Void... arg0) {
                // Creating service handler class instance
                WebRequest webreq = new WebRequest();

                // Making a request to url and getting response
                String jsonStr = webreq.makeWebServiceCall(eventsurl, WebRequest.GETRequest);


                try {
                    list = ParseJSON(jsonStr);
                    arrayJson = new JSONArray(jsonStr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                /*
                // Dismiss the progress dialog
                if (pDialog.isShowing())
                    pDialog.dismiss();

                ListAdapter adapter = new SimpleAdapter(
                        getActivity(), list,
                        R.layout.events_list, new String[]{TAG_TITLE, TAG_DESCRIPTION,TAG_IMAGEURL},
                                            new int[]{R.id.txtTitle,R.id.txtDescription, R.id.txtImageUrl});*/
                ListView eventList = (ListView) rootView.findViewById(R.id.eventsListView);
                JSONObject[] jsonObjects = new JSONObject[arrayJson.length()];
                for (int i = 0; i < arrayJson.length(); i++)
                {
                    try {
                        jsonObjects[i] = arrayJson.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                EventsAdapter adapter= new EventsAdapter(getActivity(), R.layout.events_list,jsonObjects);
                eventList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

        }
        private void CreateMembersView(View rootView) {
            ListView membersList = (ListView) rootView.findViewById(R.id.membersListView);
            if (membersList != null) {
                //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
                String[] memberNames = new String[]{
                        "Brenda",
                        "Dafne",
                        "Fabiana",
                        "Francesca",
                        "Francesca",
                        "Jolanda",
                        "Julieta",
                        "Ludovica",
                        "Marcelo",
                        "Mirko",
                        "Robson",
                        "Sandy",
                        "Thomas",
                        "Valeria"
                };
                String[] memberRoles = new String[]{
                        getString(R.string.singer),
                        getString(R.string.dancerF),
                        getString(R.string.photographerF),
                        getString(R.string.photographerF),
                        getString(R.string.dancerF),
                        getString(R.string.singer),
                        getString(R.string.singer),
                        getString(R.string.dancerF),
                        getString(R.string.musician),
                        getString(R.string.dancerM),
                        getString(R.string.programmerM),
                        getString(R.string.singer),
                        getString(R.string.singer),
                        getString(R.string.dancerF),
                };
                Integer[] imgid = new Integer[]{
                    R.drawable.brenda,
                        R.drawable.dafne,
                        R.drawable.fabiana,
                        R.drawable.francesca,
                        R.drawable.francesca_anastasio,
                        R.drawable.jolanda,
                        R.drawable.julieta,
                        R.drawable.ludo,
                        R.drawable.marcelo,
                        R.drawable.mirko,
                        R.drawable.robson,
                        R.drawable.sandy,
                        R.drawable.thomas,
                        R.drawable.valeria
                };

                CustomListAdapter adapter=new CustomListAdapter(this.getActivity(), R.layout.members_list,memberNames,memberRoles, imgid);
                membersList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.news);
                case 1:
                    return getString(R.string.events);
                case 2:
                    return getString(R.string.members);
                case 3:
                    return getString(R.string.contacts);
            }
            return null;
        }
    }
}
