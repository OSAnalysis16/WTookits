package com.conf;

import com.algorithm.TF_RF;
import com.algorithm.TermWeighting;

/**
 * Created by cassyang on 2016/11/30.
 */
public class TF_RFFactory  implements TermWeightingFactory{
    private static TF_RFFactory tf_rfFactory = new TF_RFFactory();
    private TF_RFFactory(){}

    public static TF_RFFactory getTf_RfFactory(){
        return tf_rfFactory;
    }

    @Override
    public String getName() {
        return "TF-RF";
    }

    @Override
    public TermWeighting getTermWeightingInstance() {
        return new TF_RF();
    }

    @Override
    public Boolean isSupervised() {
        return true;
    }
}
