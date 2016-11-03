package com.conf;

import com.input.DataHolder;
import com.input.UnsupervisedDH;

/**
 * Created by Glad on 2016/11/3.
 */
public class UnsupervisedDHFactory implements DataHolderFactory{
    private static UnsupervisedDHFactory unsupervisedDHFactory = new UnsupervisedDHFactory();
    private UnsupervisedDHFactory(){}

    public static UnsupervisedDHFactory getUnsupervisedDHFactory(){
        return unsupervisedDHFactory;
    }

    @Override
    public String getName() {
        return "UnSupervisedDH";
    }

    @Override
    public DataHolder getDataHolderInstance(Configuration conf) {
        return new UnsupervisedDH(conf.getDirPath());
    }
}
