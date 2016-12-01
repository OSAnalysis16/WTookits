package com.algorithm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Glad on 2016/11/23.
 */
public class TF_ICF extends TermWeighting{

    /**
     * 对于TF-IDF而言，因为corpus是动态的，当来了新的N个document之后，需要O(N^2)的时间复杂度重新计算TF-IDF
     * 在TF-ICF论文中，作者通过实验得到：
     *  "It is reasonable to express the IDF component of TF-IDF using term occurrence rates
     *      from a sufficiently large and diverse static corpus."
     * 因此在静态语料库计算IDF，作者称为ICF(inverse corpus frequency)，定义一种term weighting的算法。
     * 在这里，暂时认为输入的文档就是标准静态语料库
     * 公式： w(i,j) = log(f(i,j) + 1) * log ((N +1) / (n(j) + 1))
     * i表示文档i，j表示term j， f(i,j)是j在i的tf值， N是语料中文档总数， nj是语料中termj出现的文档数。
     * @param splitedWords List<Document> and use List<word> represents a document
     * @param fileToCate Map<FileName, category>
     *          file's category, if the term weighting algorithm is unsupervised, this field is null
     * @return
     */
    @Override
    public Map<String, Map<String, Double>> calculate(List<Map.Entry<String, List<String>>> splitedWords,
                                               Map<String, String> fileToCate) {
        int docNum = splitedWords.size();
        Map<String, Map<String, Double>> tfAllfiles = new HashMap<>();
        for (Map.Entry<String, List<String>> document : splitedWords) {
            String fileName = document.getKey();
            List<String> documentSplitedWord = document.getValue();
            // TF
            Map<String,Double> termFrequency = tf(documentSplitedWord);
            // TF in all file
            tfAllfiles.put(fileName, termFrequency);
        }
        // ICF: inverse corpus frequency
        Map<String, Double> inverseCorpusFrequency = icf(tfAllfiles);
        tf_icf(tfAllfiles, inverseCorpusFrequency, termWeightingMap);
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

    private Map<String, Double> icf(Map<String, Map<String, Double>> tfAllfiles){
        int docNum = tfAllfiles.keySet().size();
        Map<String, Double> inverseCorpusFrequency = new HashMap<>();

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

        for (Map.Entry<String, Integer> term : corpusFrequency.entrySet()) {
            Double value = Math.log((docNum + 1) / (term.getValue().doubleValue() + 1.0));
            inverseCorpusFrequency.put(term.getKey(), value);
        }
        return inverseCorpusFrequency;
    }

    private void tf_icf(Map<String, Map<String, Double>> tfAllfiles,
                        Map<String, Double> inverseCorpusFrequency,
                        Map<String, Map<String, Double>> resTfIcf){

        for (Map.Entry<String, Map<String, Double>> file : tfAllfiles.entrySet()) {
            Map<String, Double> tficf = new HashMap<>();
            Map<String, Double> termFrequency = file.getValue();
            for (Map.Entry<String, Double> term : termFrequency.entrySet()) {
                String word = term.getKey();
                Double value = Math.log(term.getValue()) * inverseCorpusFrequency.get(word);
                tficf.put(word, value);
            }
            resTfIcf.put(file.getKey(), tficf);
        }
    }
}
