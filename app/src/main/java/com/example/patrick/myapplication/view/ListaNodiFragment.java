package com.example.patrick.myapplication.view;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;

import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.example.patrick.myapplication.ListaNodiAdapter;

import com.example.patrick.myapplication.R;
import com.example.patrick.myapplication.bean.NodeBean;
import com.example.patrick.myapplication.network.ServerProxy;


import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Patrizio Perna on 09/11/14.
 */
public class ListaNodiFragment extends Fragment implements SearchView.OnQueryTextListener {

    /**
     * Interfaccia di Callback per comunicare con l'activity che contiene il Fragment.
     */
    public static interface OnMyListaNodiItemClick {
        public void onClick(NodeBean item);
    }

    /**
     * Riferimento all'activity di Callback.
     */
    private OnMyListaNodiItemClick mActivityAttached;

    private SearchView  mSearchView;
    private ListView    mListView;
    private ListaNodiAdapter mNodiAdapter;
    private ArrayList<NodeBean> listaNodi =  new ArrayList<NodeBean>();


    public ListaNodiFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(final Menu menu,final MenuInflater inflater) {

        //inflater.inflate(R.menu.menu_nodi_fragment, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        mListView.setTextFilterEnabled(true);
        setupSearchView();
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if(activity instanceof OnMyListaNodiItemClick) {
            // L'activity che contiene il fragment è compatibile con l'interfacci di Callback, mi memorizzo il riferimento.
            mActivityAttached = (OnMyListaNodiItemClick)activity;
        }
        else {
            // L'activity non è compatibile, creo un riferimento fittizzio.
            mActivityAttached = new OnMyListaNodiItemClick() {
                public void onClick(NodeBean item) {}
            };
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);


        View rootView = inflater.inflate(R.layout.fragment_nodi, container, false);

        mListView = (ListView) rootView.findViewById(R.id.listViewNodi);

        // aggiungo un listener

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),"Cliccato elemento:"+String.valueOf(position),Toast.LENGTH_SHORT).show();
                NodeBean item = (NodeBean)parent.getItemAtPosition(position);

                Log.i("onItemClick:  ",item.getName());

                // Richiamo il metodo di callback
                mActivityAttached.onClick(item);
            }
        });


        // Create the adapter to convert the array to views
          mNodiAdapter = new ListaNodiAdapter(getActivity(),listaNodi);

        // Aggiungo l'header sulla listview
        View header = (View)getActivity().getLayoutInflater().inflate(R.layout.listview_nodes_header, null);
        mListView.addHeaderView(header);

        mListView.setAdapter(mNodiAdapter);

        return rootView;
    }




    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint(getString(R.string.option_menu_search_hint));
    }


    @Override
    public boolean onQueryTextChange(String s) {
        if (TextUtils.isEmpty(s)) {
            listaNodi.clear();
            mNodiAdapter.resetData();
            mNodiAdapter.notifyDataSetChanged();
    }

        // TODO Auto-generated method stub

        s = s.isEmpty() ? "" : "Query: " + s;
        Log.i("textchange-FRAGMENT", s);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {

        // clean listview
        mNodiAdapter.resetData();
        mNodiAdapter.notifyDataSetChanged();

        // request
        GetNodeListTask getNodeListTask = new GetNodeListTask();
        getNodeListTask.execute(s);
        return true;
    }


    // creo una classe innestata per reperire la lista di nodi
    private class GetNodeListTask extends
            AsyncTask<String, Void, ArrayList<NodeBean>> {
        private PowerManager.WakeLock mWakeLock;

        private String msg;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            PowerManager powerManager = (PowerManager) getActivity().getSystemService(getActivity().POWER_SERVICE);
            mWakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK
                    | PowerManager.ON_AFTER_RELEASE, "MainActivity");
            mWakeLock.acquire();

            Toast.makeText(getActivity(),"Comunicazione con il server in corso...",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected ArrayList<NodeBean> doInBackground(String... params) {

            ServerProxy proxy = new ServerProxy();
            try {
                Log.i("doInBackground",
                        "Comunicazione con il server in corso...");
                return proxy.getNodesList(params[0],"roma","100");
            } catch (IOException e) {
                Log.i("doInBackground",
                        "Errore nella comunicazione con il server in doBackground",
                        e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<NodeBean> result) {
            super.onPostExecute(result);
            mWakeLock.release();
            if (result == null) {
                Log.i("onPostExecute", "Si è verificato un errore");
                Toast.makeText(getActivity(),getString(R.string.error_connection_server), Toast.LENGTH_SHORT).show();


            } else {
                listaNodi.clear();
                listaNodi.addAll(result);
                mNodiAdapter.notifyDataSetChanged();

            }
        }
    }
}