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

    protected List<List<String>> splitTermMap = null;
    protected List<Map<String, Double>> termWeightingMap = null;

    public TermWeighting(){
        splitTermMap = new ArrayList<>();
        termWeightingMap = new ArrayList<>();
    }
    /**
     * split word in the document
     * @param data
     * @return List<Document> and use List<word> represents a document
     */
    public List<List<String>> split(List<Map.Entry<String, String>> data){
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

}
