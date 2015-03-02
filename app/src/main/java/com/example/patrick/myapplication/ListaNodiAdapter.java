package com.example.patrick.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.patrick.myapplication.bean.NodeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Patrizio Perna on 11/11/14.
 */
public class ListaNodiAdapter extends ArrayAdapter<NodeBean> implements Filterable{

    private List<NodeBean> originalNodeList;
    private List<NodeBean> nodeList;
    private Context context;
    private NodeFilter nodeFilter;

    public ListaNodiAdapter(Context context,  List<NodeBean> nodeList) {
        super(context, 0, nodeList);
        this.context = context;
        this.nodeList = nodeList;
        this.nodeList.addAll(nodeList);
        this.originalNodeList = nodeList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NodeBean nodoItem = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
           convertView = LayoutInflater.from(getContext()).inflate(R.layout.nodo_row, parent, false);
        }
        // Lookup view for data population
        TextView statoNodo = (TextView) convertView.findViewById(R.id.statoNodo);
        TextView nomeNodo = (TextView) convertView.findViewById(R.id.nomeNodo);
        TextView layerNodo = (TextView) convertView.findViewById(R.id.layerNodo);

        // Populate the data into the template view using the data object
        statoNodo.setText(nodoItem.getStatus());
        nomeNodo.setText(nodoItem.getName());
        layerNodo.setText(nodoItem.getLayer());

        // Return the completed view to render on screen
        return convertView;
    }

    public void resetData() {
        nodeList = originalNodeList;
    }

    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Override
    public int getCount() {
        return nodeList.size();
    }

    @Override
    public NodeBean getItem(int position) {
        return nodeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return nodeList.get(position).hashCode();
    }

    @Override
    public Filter getFilter() {
        if (nodeFilter == null)
            nodeFilter = new NodeFilter();
        return nodeFilter;
    }

    public class NodeFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                results.values = originalNodeList;
                results.count = originalNodeList.size();
            } else {
                List<NodeBean> nNodeList = new ArrayList<NodeBean>();

                for (NodeBean c : nodeList) {
                    if (c.getName()
                            .toUpperCase()
                            .startsWith(constraint.toString().toUpperCase()))
                        // startsWith(constraint.toString().toUpperCase()))
                        nNodeList.add(c);

                }

                results.values = nNodeList;
                results.count = nNodeList.size();

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            // inform the adapter about the new list filtered
            if (results.count == 0) {
                // notifyDataSetInvalidated();
                nodeList = (List<NodeBean>) results.values;
                notifyDataSetChanged();
            } else {
                nodeList = (List<NodeBean>) results.values;
                notifyDataSetChanged();
            }
        }

    }

}
