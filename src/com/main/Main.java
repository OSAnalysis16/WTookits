package com.main;

import com.algorithm.TermWeighting;
import com.conf.Configuration;

public class Main {

    public static void main(String[] args) {
        Configuration conf = Configuration.loadConf(args[0]);
        MainHolder.setUp(conf);
        TermWeighting tw = MainHolder.getTermWeighting();
        //TODO
        System.out.println(tw.getClass().getName());
    }
}
