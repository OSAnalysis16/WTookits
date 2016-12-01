package com.algorithm;

import java.util.*;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;

/**
 * Created by Glad on 2016/10/24.
 * Edited by qibai on 2016/10/25.
 */
public class TF extends TermWeighting {

    /**
     * calculate term weighting for each term in document
     * @param splitedWords
     * @return
     */
    @Override
    public List<Map<String, Double>> calculate(List<List<String>> splitedWords,
                                               Map<String, String> fileToCate) {

        for (List<String> documentSplitedWord : splitedWords) {
            Map<String,Double> termCount = new HashMap<>();
            for (String term : documentSplitedWord) {

                if (termCount.containsKey(term)){
                    Double count = termCount.get(term);
                    termCount.put(term,count+1);
                }else {
                    termCount.put(term, 1.0);
                }
            }
            termWeightingMap.add(termCount);
        }
        return termWeightingMap;
    }

//    test
//    public static void main(String[] args) {
//        new TF();
//        String str = "��ӭʹ��ansj_seg,(ansj���ķִ�)���������������ʲô���ⶼ������ϵ��.��һ����������.�������.ansj_seg����,��׼,������!";
//        List<Map.Entry<String, String>> test = new  ArrayList<Map.Entry<String, String>>();
//        test.add(new AbstractMap.SimpleEntry("test", str));
//        List<List<String>> split = split2(test);
//        List<Map<String, Double>> maps = calculate2(split);
//
//    }

}
