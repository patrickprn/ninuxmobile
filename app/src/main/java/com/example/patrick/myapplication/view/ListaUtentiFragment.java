package com.example.patrick.myapplication.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.patrick.myapplication.R;

/**
 * Created by Patrizio Perna on 09/11/14.
 */
public class ListaUtentiFragment extends Fragment {


    public ListaUtentiFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_lista_utenti, container, false);
        return rootView;
    }
}

