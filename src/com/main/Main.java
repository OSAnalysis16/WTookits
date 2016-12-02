package com.main;

import com.algorithm.TermWeighting;
import com.conf.Configuration;
import com.input.DataHolder;

import java.util.List;
import java.util.Map;

public class Main {
    /**
     * There are 3 methods to initial configuration
     *      1. get the termWeighting from configuration file
     *          Configuration conf = Configuration.loadConf(args[0]);
     *      2. get the termWeighting from user
     *          String termWeightStr = "TF";
     *          String dirPath = "C:\\Users\\Glad\\IdeaProjects\\WTookits\\data";
     *          String categoryPath = "";
     *          String destPath = "";
     *          Configuration conf = new Configuration(termWeightStr, dirPath, categoryPath, destPath);
     *      3. another get it from user
     *          Configuration conf = new Configuration();
     *          conf.setTermWeightStr(termWeightStr);
     *          conf.setDirPath(dirPath);
     *          conf.setCategoryPath(categoryPath);
     *          conf.setDestPath(destPath);
     *
     */
    public static void main(String[] args) {

        String termWeightStr = "TF-ICF";
        String dirPath = "C:\\Users\\WooJetTrue\\desktop\\WTookits\\data";
        String categoryPath = "";
        String destPath = "C:\\Users\\WooJetTrue\\desktop\\WTookits\\data\\result.txt";
        Configuration conf = new Configuration(termWeightStr, dirPath, categoryPath, destPath);

        //  initial mainHolder. One conf match one mainHolder, and then match one termWeighting.
        /*MainHolder mainHolder = new MainHolder(conf);
        TermWeighting tw = mainHolder.getTermWeighting();
        System.out.println(tw.getClass().getName());

        DataHolder dh = mainHolder.getDataHolder();
        System.out.println(dh.getClass().getName());

        List<Map.Entry<String, String>> data = dh.getData();
        Map<String, String> fileToCate = dh.getFileToCate();
        List<Map.Entry<String, List<String>>> splitData = tw.split(data);
        Map<String, Map<String, Double>> calculate = tw.calculate(splitData, fileToCate, dh.getFileInfo());
        dh.writeResult(calculate, conf.getDestPath());
        */

        MainHolder mainHolder = new MainHolder(conf);
        //让mainHolder内部写入到目的result文件中，同时返回数据，以便用户是否要在内存中进一步操作。
        Map<String, Map<String, Double> > calculate = mainHolder.calculateAndwrite();

    }
}
