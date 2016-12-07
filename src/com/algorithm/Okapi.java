package com.algorithm;


import javax.print.attribute.HashDocAttributeSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by WooJetTrue on 2016/12/1.
 */

/***
 *
* */
public class Okapi  extends TermWeighting{
    @Override
    public Map<String, Map<String, Double>> calculate(List<Map.Entry<String, List<String>>> splitedWords, Map<String, String> fileToCate) {
        Map<String, Map<String, Double>> tfAllfiles = new HashMap<>();
        Map<String,Integer> doclen = new HashMap<>();
        int filenum = splitedWords.size();// N
        long dlsum = 0;
        double avg_dl = 0; // avg_dl
        for (Map.Entry<String, List<String>> document : splitedWords) {
            String fileName = document.getKey();
            List<String> documentSplitedWord = document.getValue();
            // TF
            Map<String,Double> termFrequency = tf(documentSplitedWord);
            // TF in all file
            tfAllfiles.put(fileName, termFrequency);

            dlsum+=document.getValue().size();

            doclen.put(fileName,document.getValue().size());
        }

        // DF:document frequency
        Map<String, Integer> dfAllfiles = df(tfAllfiles);
        avg_dl = dlsum/filenum;

        //calculate the term weighting
        for (Map.Entry<String, Map<String, Double>> file : tfAllfiles.entrySet()) {
            Map<String, Double> tficf = new HashMap<>();
            Map<String, Double> termFrequency = file.getValue();
            int dli = doclen.get(file.getKey());
            for (Map.Entry<String, Double> term : termFrequency.entrySet()) {
                String word = term.getKey();
                int dfi = dfAllfiles.get(word);
                Double value = term.getValue()/(0.5+1.5*dli/avg_dl)*Math.log((filenum-dfi+0.5)/(dfi+0.5));
                tficf.put(word, value);
            }
            termWeightingMap.put(file.getKey(), tficf);
        }
        System.out.println(termWeightingMap);
        return termWeightingMap;
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
