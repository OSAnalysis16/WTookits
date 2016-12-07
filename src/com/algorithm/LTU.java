package com.algorithm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Zach on 2016/12/2.
 */

public class LTU extends TermWeighting{
	@Override
    public Map<String, Map<String, Double>> calculate(List<Map.Entry<String, List<String>>> splitedWords, Map<String, String> fileToCate) {
        return null;
    }


    private Map<String, Double> tf(List<String> splitWords){
        Map<String, Double> termFrequency = new HashMap<>();
        for (String term : splitWords) {

            if (termFrequency.containsKey(term)){
                Double frequency = termFrequency.get(term);
                termFrequency.put(term, frequency + 1);
            }else {
                termFrequency.put(term, 1.0);
            }
        }
        return termFrequency;
    }

    private Map<String, Integer> df(Map<String, Map<String, Double>> tfAllfiles){
        Map<String, Integer> corpusFrequency = new HashMap<>();
        for (Map.Entry<String, Map<String, Double>> file : tfAllfiles.entrySet()) {
            Map<String, Double> words = file.getValue();
            for (Map.Entry<String, Double> word : words.entrySet()) {
                String term = word.getKey();
                if(corpusFrequency.containsKey(term)){
                    corpusFrequency.put(term, corpusFrequency.get(term) + 1);
                }else{
                    corpusFrequency.put(term, 1);
                }
            }
        }
        return corpusFrequency;
    }

}