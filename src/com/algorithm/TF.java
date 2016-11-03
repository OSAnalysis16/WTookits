package com.algorithm;

import java.util.*;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;

/**
 * Created by Glad on 2016/10/24.
 * Edited by qibai on 2016/10/25.
 */
public class TF implements TermWeighting {

    static List<List<String>> splitTermMap = null;
    static List<Map<String, Double>> termWeightingMap = null;

    public TF() {
        splitTermMap = new ArrayList<>();
        termWeightingMap = new ArrayList<>();
    }

    /**
     * split word in the document
     * @param data
     * @return
     */
    @Override
    public List<List<String>> split(List<Map.Entry<String, String>> data) {
        for (Map.Entry<String, String> document : data) {

            List<Term> list = ToAnalysis.parse(document.getValue()).getTerms();
            List<String> termName = new ArrayList<>();
            for (Term term : list) {
                String wordToken = term.getName();
                if (!wordToken.trim().isEmpty() && wordToken.length() > 1) {
                    termName.add(wordToken);
                }
            }
            splitTermMap.add(termName);
        }
        return splitTermMap;
    }

    /**
     * calculate term weighting for each term in document
     * @param splitedWords
     * @return
     */
    @Override
    public List<Map<String, Double>> calculate(List<List<String>> splitedWords) {

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
//        String str = "欢迎使用ansj_seg,(ansj中文分词)在这里如果你遇到什么问题都可以联系我.我一定尽我所能.帮助大家.ansj_seg更快,更准,更自由!";
//        List<Map.Entry<String, String>> test = new  ArrayList<Map.Entry<String, String>>();
//        test.add(new AbstractMap.SimpleEntry("test", str));
//        List<List<String>> split = split2(test);
//        List<Map<String, Double>> maps = calculate2(split);
//
//    }

}
