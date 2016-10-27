package com.main;

import com.algorithm.TermWeighting;
import com.conf.Configuration;
import com.conf.TermWeightingFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Glad on 2016/10/24.
 */
public class MainHolder {
    
    private static TermWeighting termWeighting = null;

    public static void setUp(Configuration configuration){
        //  termWeighting
        Map<String, TermWeightingFactory> termWeightingFactoryMap = new HashMap<>();
        for(TermWeightingFactory i: TermWeightingFactory.termWeightFactories){
            termWeightingFactoryMap.put(i.getName(), i);
        }
        String termWeightStr = configuration.getTermWeightStr();
        termWeighting = termWeightingFactoryMap.get(termWeightStr).getTermWeightingInstance();
    }

    public static TermWeighting getTermWeighting(){
        return termWeighting;
//        System.out.print("MainHolder is not initialized");
//        System.exit(1);
//        return null;
//        throw new MainHolderNotInitializedException();
    }
}
