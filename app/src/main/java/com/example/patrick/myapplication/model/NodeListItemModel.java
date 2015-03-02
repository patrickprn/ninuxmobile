package com.example.patrick.myapplication.model;

/**
 * Created by Patrizio Perna on 09/11/14.
 */
public class NodeListItemModel {
    private String status; // active,potential,planned

    private String name;

    private String layer;

    public NodeListItemModel(String status, String name, String layer) {
        this.status = status;
        this.name = name;
        this.layer = layer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLayer() {
        return layer;
    }
}
