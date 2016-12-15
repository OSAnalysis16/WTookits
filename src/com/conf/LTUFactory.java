package com.conf;

import com.algorithm.LTU;
import com.algorithm.TermWeighting;

/**
 * Created by Zach on 2016/12/2.
 */

public class LTUFactory implements TermWeightingFactory{
    private static LTUFactory ltuFactory = new LTUFactory();
    private LTUFactory(){
    }

    public static LTUFactory getLTUFactory(){
        return ltuFactory;
    }

    @Override
    public String getName() {
        return "LTU";
    }

    @Override
    public TermWeighting getTermWeightingInstance() {
        return new LTU();
    }

    @Override
    public Boolean isSupervised() {
        return false;
    }
}