package com.intive.rest;

import java.util.List;
import java.util.ArrayList;

public class ChartValues {

    private List<String> vals = new ArrayList<String>();
    private List<Object[]> objVals = new ArrayList<Object[]>();

    public List<String> getVals() {
        return vals;
    }

    public void setVals(List<String> vals) {
        this.vals = vals;
    }

    public List<Object[]> getObjVals() {
        return objVals;
    }

    public void setObjVals(List<Object[]> objVals) {
        this.objVals = objVals;
    }
}