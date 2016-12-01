package com.conf;

import com.algorithm.TF_ICF;
import com.algorithm.TermWeighting;

/**
 * Created by Glad on 2016/12/1.
 */
public class TF_ICFFactory implements TermWeightingFactory {
    private static TF_ICFFactory tf_icfFactory = new TF_ICFFactory();
    private TF_ICFFactory(){}

    public static TF_ICFFactory getTf_IcfFactory(){
        return tf_icfFactory;
    }

    @Override
    public String getName() {
        return "TF-ICF";
    }

    @Override
    public TermWeighting getTermWeightingInstance() {
        return new TF_ICF();
    }

    @Override
    public Boolean isSupervised() {
        return false;
    }
}

