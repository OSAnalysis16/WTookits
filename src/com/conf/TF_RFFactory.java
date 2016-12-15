package com.conf;

import com.algorithm.TF_RF;
import com.algorithm.TermWeighting;

/**
 * Created by cassyang on 2016/11/30.
 */
public class TF_RFFactory implements TermWeightingFactory{
    private static TF_RFFactory tf_rfFactory = new TF_RFFactory();
    private TF_RFFactory (){}

    public static TF_RFFactory  getIF_RFFactory()
    {
        return tf_rfFactory;
    }

    @Override
    public String getName() {
        return "IF_RF";
    }

    @Override
    public TermWeighting getTermWeightingInstance() {
        return new TF_RF();
    }
}
