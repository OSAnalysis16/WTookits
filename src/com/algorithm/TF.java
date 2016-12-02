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
     * @param splitedWords List<Entry<fileName, Document>> and use List<word> represents a document
     * @return
     */
    @Override
    public Map<String, Map<String, Double>> calculate(List<Map.Entry<String, List<String>>> splitedWords,
                                               Map<String, String> fileToCate) {

        for (Map.Entry<String, List<String>> document : splitedWords) {
            List<String> documentSplitedWord = document.getValue();
            Map<String, Double> termFrequency = new HashMap<>();
            for (String term : documentSplitedWord) {

                if (termFrequency.containsKey(term)){
                    Double frequency = termFrequency.get(term);
                    termFrequency.put(term, frequency + 1);
                }else {
                    termFrequency.put(term, 1.0);
                }
            }
            termWeightingMap.put(document.getKey(), termFrequency);
        }
        return termWeightingMap;
    }

    @Override
    public Map<String, Map<String, Double>> calculate(List<Map.Entry<String, List<String>>> splitedWords, Map<String, String> fileToCate, Map<String, Long> fileInfo) {
        return null;
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
