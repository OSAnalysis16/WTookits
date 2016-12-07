package com.main;

import com.algorithm.TermWeighting;
import com.conf.Configuration;
import com.conf.DataHolderFactory;
import com.conf.TermWeightingFactory;
import com.input.DataHolder;

import javax.xml.crypto.Data;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Glad on 2016/10/24.
 */
public class MainHolder {
    
    private TermWeighting termWeighting = null;
    private DataHolder dataHolder = null;
    public MainHolder(Configuration configuration){
        //  termWeighting
        Map<String, TermWeightingFactory> termWeightingFactoryMap = new HashMap<>();
        for(TermWeightingFactory i: TermWeightingFactory.termWeightFactories){
            termWeightingFactoryMap.put(i.getName(), i);
        }
        String termWeightStr = configuration.getTermWeightStr();
        TermWeightingFactory termWeightingFactory = termWeightingFactoryMap.get(termWeightStr);

        Boolean isSupervised = termWeightingFactory.isSupervised();
        termWeighting = termWeightingFactory.getTermWeightingInstance();

        //  dataHolder
        Map<String, DataHolderFactory> dataHolderFactoryMap = new HashMap<>();
        for(DataHolderFactory i: DataHolderFactory.dataHolderFactories){
            dataHolderFactoryMap.put(i.getName(), i);
        }
        String dataHolderStr = getDataHolderStr(isSupervised);
        dataHolder = dataHolderFactoryMap.get(dataHolderStr).getDataHolderInstance(configuration);
    }

    private String getDataHolderStr(Boolean isSupervised) {
        if(isSupervised) return "SupervisedDH";
        return "UnSupervisedDH";
    }

    public TermWeighting getTermWeighting(){
        return termWeighting;
//        System.out.print("MainHolder is not initialized");
//        System.exit(1);
//        return null;
//        throw new MainHolderNotInitializedException();
    }

    public DataHolder getDataHolder(){
        return dataHolder;
    }

}
