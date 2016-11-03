package com.conf;

import com.input.DataHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Glad on 2016/11/3.
 */
public interface DataHolderFactory {
    String getName();
    DataHolder getDataHolderInstance(Configuration conf);

    List<DataHolderFactory> dataHolderFactories = new ArrayList<DataHolderFactory>(
            Arrays.asList(UnsupervisedDHFactory.getUnsupervisedDHFactory(),
                    SupervisedDHFactory.getSupervisedDHFactory()));
}
