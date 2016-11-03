package com.conf;

import com.input.DataHolder;
import com.input.SupervisedDH;

/**
 * Created by Glad on 2016/11/3.
 */
public class SupervisedDHFactory implements DataHolderFactory{
    private static SupervisedDHFactory supervisedDHFactory = new SupervisedDHFactory();
    private SupervisedDHFactory(){}

    public static SupervisedDHFactory getSupervisedDHFactory(){
        return supervisedDHFactory;
    }

    @Override
    public String getName() {
        return "SupervisedDH";
    }

    @Override
    public DataHolder getDataHolderInstance(Configuration conf) {
        return new SupervisedDH(conf.getDirPath(), conf.getCategoryPath());
    }

}
