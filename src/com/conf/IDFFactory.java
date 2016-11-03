package com.conf;

import com.algorithm.IDF;
import com.algorithm.TermWeighting;

/**
 * Created by Glad on 2016/10/24.
 */
public class IDFFactory implements TermWeightingFactory{
    private static IDFFactory idfFactory = new IDFFactory();
    private IDFFactory(){}

    public static IDFFactory getIdfFactory(){
        return idfFactory;
    }

    @Override
    public String getName() {
        return "IDF";
    }

    @Override
    public TermWeighting getTermWeightingInstance() {
        return new IDF();
    }

    @Override
    public Boolean isSupervised() {
        return false;
    }

}
