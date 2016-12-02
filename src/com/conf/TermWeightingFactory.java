package com.conf;

import com.algorithm.Okapi;
import com.algorithm.TermWeighting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Glad on 2016/10/24.
 */
public interface TermWeightingFactory {
    String getName();
    TermWeighting getTermWeightingInstance();
    Boolean isSupervised();

    List<TermWeightingFactory> termWeightFactories = new ArrayList<TermWeightingFactory>(
            Arrays.asList(IDFFactory.getIdfFactory(), TFFactory.getTFFactory(),
                    TF_ICFFactory.getTf_IcfFactory(), OkapiFactory.getOkapiFactory()));
}
