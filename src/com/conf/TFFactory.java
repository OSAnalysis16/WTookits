package com.conf;

import com.algorithm.TF;
import com.algorithm.TermWeighting;

/**
 * Created by Glad on 2016/10/24.
 */
public class TFFactory implements TermWeightingFactory{
    private static TFFactory tfFactory = new TFFactory();
    private TFFactory(){}

    public static TFFactory getTFFactory(){
        return tfFactory;
    }


    @Override
    public String getName() {
        return "TF";
    }

    @Override
    public TermWeighting getTermWeightingInstance() {
        return new TF();
    }

    @Override
    public Boolean isSupervised() {
        return false;
    }

}
