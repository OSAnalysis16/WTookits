package com.algorithm;


import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Glad on 2016/10/24.
 */
public abstract class TermWeighting implements Weighting{

    protected List<Map.Entry<String, List<String>>> splitTermMap = null;
    protected Map<String, Map<String, Double>> termWeightingMap = null;

    public TermWeighting(){
        splitTermMap = new ArrayList<>();
        termWeightingMap = new HashMap<>();
    }
    /**
     * split word in the document
     * @param data
     * @return List<Document> and use List<word> represents a document
     */
    public List<Map.Entry<String, List<String>>> split(List<Map.Entry<String, String>> data){
        for (Map.Entry<String, String> document : data) {
            String fileName = document.getKey();
            List<Term> list = ToAnalysis.parse(document.getValue()).getTerms();
            List<String> termName = new ArrayList<>();
            for (Term term : list) {
                String wordToken = term.getName();
                if (!wordToken.trim().isEmpty() && wordToken.length() > 1) {
                    termName.add(wordToken);
                }
            }
            Map.Entry<String, List<String>> e = new HashMap.SimpleEntry<>(fileName, termName);
            splitTermMap.add(e);
        }
        return splitTermMap;
    }

}
