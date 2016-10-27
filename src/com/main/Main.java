package com.main;

import com.algorithm.TermWeighting;
import com.conf.Configuration;
import com.input.UnsupervisedDH;

import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Configuration conf = Configuration.loadConf(args[0]);
        MainHolder.setUp(conf);
        TermWeighting tw = MainHolder.getTermWeighting();
        //TODO
        System.out.println(tw.getClass().getName());
        UnsupervisedDH udh = new UnsupervisedDH(conf.getDirPath());
        List<Map.Entry<String, String>> data = udh.getData();
        List<List<String>> splitData = tw.split(data);
        List<Map<String, Double>> calculate = tw.calculate(splitData);
        udh.writeResult(calculate, conf.getDestPath());
    }
}
