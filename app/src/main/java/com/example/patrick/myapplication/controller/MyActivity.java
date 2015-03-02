package com.example.patrick.myapplication.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.example.patrick.myapplication.SettingsActivity;
import com.example.patrick.myapplication.bean.NodeBean;
import com.example.patrick.myapplication.view.ListaUtentiFragment;
import com.example.patrick.myapplication.view.ListaNodiFragment;
import com.example.patrick.myapplication.NsMenuAdapter;
import com.example.patrick.myapplication.R;
import com.example.patrick.myapplication.model.NsMenuItemModel;
import com.example.patrick.myapplication.view.MyDetailFragment;
import com.example.patrick.myapplication.view.MyMapFragment;
import com.example.patrick.myapplication.view.NearMeFragment;


public class MyActivity extends ActionBarActivity implements
        SearchView.OnQueryTextListener, ListaNodiFragment.OnMyListaNodiItemClick {
    private String[] menuItems;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private SearchView mSearchView;
    private CustomActionBarDrawerToggle mDrawerToggle;
    private CursorAdapter adapter;
    private ListaNodiFragment nodiFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Creazione dei fragment

        nodiFragment = new ListaNodiFragment();

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        _initMenu();
        mDrawerToggle = new CustomActionBarDrawerToggle(this, mDrawerLayout);
        mDrawerLayout.setDrawerListener(mDrawerToggle);


        // test shared preferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String username = prefs.getString("edittext_username", "");
        Log.i("MyACTIVITY", username);


        //getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new ListaNodiFragment()).commit();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        setupSearchView();

        // When using the support library, the setOnActionExpandListener() method is
        // static and accepts the MenuItem object as an argument
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do something when collapsed
                return true;  // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded
                return true;  // Return true to expand action view
            }
        });
        return true;
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_search).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_search: {
                Toast.makeText(this, "Search selected", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_container, nodiFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack("ts")
                        .commit();

                return true;
            }

            case R.id.action_refresh: {
                Toast.makeText(this, "Refresh selected", Toast.LENGTH_SHORT).show();
                return true;
            }

            case R.id.action_settings: {
                Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            }

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void _initMenu() {
        NsMenuAdapter mAdapter = new NsMenuAdapter(this);

        // Add Header
        mAdapter.addHeader(R.string.ns_menu_main_header);

        // Add first block
        menuItems = getResources().getStringArray(
                R.array.ns_menu_items);
        String[] menuItemsIcon = getResources().getStringArray(
                R.array.ns_menu_items_icon);


        int res = 0;
        for (String item : menuItems) {

            int id_title = getResources().getIdentifier(item, "string",
                    this.getPackageName());
            int id_icon = getResources().getIdentifier(menuItemsIcon[res],
                    "drawable", this.getPackageName());

            NsMenuItemModel mItem = new NsMenuItemModel(id_title, id_icon);
            if (res == 1) mItem.counter = 12;
            if (res == 3) mItem.counter = 3;
            mAdapter.addItem(mItem);
            res++;
        }

        mAdapter.addHeader(R.string.ns_menu_main_header2);

        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        if (mDrawerList != null)
            mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener(getApplicationContext()));
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        // TODO Auto-generated method stub
        s = s.isEmpty() ? "" : "Query: " + s;
        Log.i("textchange", s);
        return true;

    }


    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Search Here");
    }


    private void fetchData() {

    }

    /**
     * Implementazione dell'interfaccia onClick di ListaNodiFragment
     *
     * @param item NodeBean object selected from listview
     */
    @Override
    public void onClick(NodeBean item) {

        // Preparo l'argomento da passare al Fragment o all'Activity. Questo argomento contiene l'oggetto cliccato.
        Bundle arguments = new Bundle();
        arguments.putParcelable("com.example.patrick.myapplication.NodeBean",item);

        // Recupero la vista detailContainer
        View detailView = findViewById(R.id.detail_container);
        if (detailView == null) {
            // Non esiste spazio per la visualizzazione del dattagli, quindi ho necessità di lanciare una nuova activity.
            // Carico gli arguments nell'intent di chiamata.
            //Intent intent = new Intent(this, DetailActivity.class);
            new LoadDetailActivityTask().execute(arguments);

        } else {
            // Esiste lo spazio, procedo con la creazione del Fragment!
            MyDetailFragment myDetailFragment = new MyDetailFragment();
            // Imposto gli argument del fragment.
            myDetailFragment.setArguments(arguments);

            // Procedo con la sostituzione del fragment visualizzato.
            FragmentManager fragmentManager = this.getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.detail_container, myDetailFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack("ts")
                    .commit();
        }

    }


    /**
     * Classe innestata che implementa il listener per il drawer
     */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {


        Context mContext;

        public DrawerItemClickListener(Context context) {
            mContext = context;
        }

        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {

            // Highlight the selected item, update the title, and close the drawer
            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            //setTitle("......");

            //String text = "menu click... should be implemented";
            //Toast.makeText(MyActivity.this, text, Toast.LENGTH_LONG).show();
            Fragment rFragment = null;
            switch (position) {
                case 1: {
                    rFragment = new MyMapFragment();
                }
                break;
                case 2:
                    rFragment = new ListaUtentiFragment();
                    break;
                case 3:
                    rFragment = new ListaNodiFragment();
                    break;
                case 4:
                    rFragment = new NearMeFragment();
            }
            // Getting reference to the FragmentManager
            FragmentManager fragmentManager = getSupportFragmentManager();

            // Creating a fragment transaction
            FragmentTransaction ft = fragmentManager.beginTransaction();

            // Adding a fragment to the fragment transaction
            ft.replace(R.id.frame_container, rFragment);

            // Committing the transaction
            ft.commit();

            // Closing the drawer
            mDrawerLayout.closeDrawer(mDrawerList);

        }
    }


    // Estendo ActionBarDrawerToggle
    private class CustomActionBarDrawerToggle extends ActionBarDrawerToggle {

        public CustomActionBarDrawerToggle(Activity mActivity, DrawerLayout mDrawerLayout) {
            super(
                    mActivity,               /* host Activity */
                    mDrawerLayout,           /* DrawerLayout object */
                    R.string.ns_menu_open,   /* "open drawer" description */
                    R.string.ns_menu_close); /* "close drawer" description */
        }


        /**
         * Called when a drawer has settled in a completely closed state.
         */
        @Override
        public void onDrawerClosed(View view) {
            getSupportActionBar().setTitle(getString(R.string.ns_menu_close));
            supportInvalidateOptionsMenu();
        }

        /**
         * Called when a drawer has settled in a completely open state.
         */
        @Override
        public void onDrawerOpened(View drawerView) {
            getSupportActionBar().setTitle(getString(R.string.ns_menu_open));
            supportInvalidateOptionsMenu();
        }
    }


    /**
     * Async task per avviare l'activity di detail
     *
     */
    class LoadDetailActivityTask extends AsyncTask<Bundle, Void, Void> {
        @Override
        protected Void doInBackground(Bundle...arguments) {

            Intent intent = new Intent(MyActivity.this, DetailActivity.class);
            intent.putExtras(arguments[0]);
            startActivity(intent);
            Log.i("LoadDetailActivityTask","doInBackground");
            return (null);
        }


        @Override
        protected void onPostExecute(Void unused) {


        }
    }
}
