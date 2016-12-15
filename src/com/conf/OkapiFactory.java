package com.conf;

import com.algorithm.Okapi;
import com.algorithm.TermWeighting;

/**
 * Created by WooJetTrue on 2016/12/1.
 */
public class OkapiFactory implements TermWeightingFactory{
    private static OkapiFactory okapiFactory = new OkapiFactory();
    private OkapiFactory(){
    }

    public static OkapiFactory getOkapiFactory(){
        return okapiFactory;
    }

    @Override
    public String getName() {
        return "Okapi";
    }

    @Override
    public TermWeighting getTermWeightingInstance() {
        return new Okapi();
    }

    @Override
    public Boolean isSupervised() {
        return false;
    }
}
