package com.conf;

import com.algorithm.TermWeighting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Glad on 2016/10/24.
 * Edited by Zach on 2016/12/02
 */
public interface TermWeightingFactory {
    String getName();
    TermWeighting getTermWeightingInstance();
    Boolean isSupervised();

    List<TermWeightingFactory> termWeightFactories = new ArrayList<TermWeightingFactory>(
            Arrays.asList(IDFFactory.getIdfFactory(), TFFactory.getTFFactory(),
                    TF_ICFFactory.getTf_IcfFactory(), TF_IDFFactory.GetTf_idfFactory(),
                    LTUFactory.getLTUFactory()， ACTFactory.getACTFactory()));
}
