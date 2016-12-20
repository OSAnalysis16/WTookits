package com.conf;

import com.algorithm.TF_IDF_ICSDF;
import com.algorithm.TermWeighting;

/**
 * Created by huang on 2016/12/19.
 */
public class TF_IDF_ICSDFFactory implements TermWeightingFactory{
    private static TF_IDF_ICSDFFactory tf_idf_icsdfFactory = new TF_IDF_ICSDFFactory();
    private TF_IDF_ICSDFFactory(){}

    public static TF_IDF_ICSDFFactory getTf_Idf_IcsdfFactory(){
        return tf_idf_icsdfFactory;
    }

    @Override
    public String getName() {
        return "TF-IDF-ICSDF";
    }

    @Override
    public TermWeighting getTermWeightingInstance() {
        return new TF_IDF_ICSDF();
    }

    @Override
    public Boolean isSupervised() {
        return true;
    }

}
