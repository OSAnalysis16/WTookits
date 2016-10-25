package com.conf;

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

    List<TermWeightingFactory> termWeightFactories = new ArrayList<TermWeightingFactory>(
            Arrays.asList(IDFFactory.getIdfFactory(), TFFactory.getTFFactory()));
}
