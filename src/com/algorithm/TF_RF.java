package com.algorithm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by cassyang on 2016/11/30.
 */
public class TF_RF extends TermWeighting {

    /*
    * Created by cassyang on 2016/11/30.
    * */
    @Override
    public Map<String, Map<String, Double>> calculate(List<Map.Entry<String, List<String>>> splitedWords,
                                                      Map<String, String> fileToCate) {
        ///////储存所有文件的tf的结构///////
        Map<String, Map<String, Double>> tfAllfiles =new HashMap<>();
        ///////循环所有文件进行分词///////
        for(Map.Entry<String,List<String>> document : splitedWords){
            String fileName = document.getKey();
            List<String> fileWords = document.getValue();
            ////////计算tf（词频////////
            Map<String, Double> termFrequency = calculateTf(fileWords);
            ////////所有文章TF////////
            tfAllfiles.put(fileName, termFrequency);
        }
        //////计算每个组别里面的所有词频//////
        Map<String, Map<String, Integer>> allDFofClasses = calculateAllDf(splitedWords, fileToCate);
        //////计算tf_rf//////
        calcutateTF_RF(tfAllfiles, allDFofClasses, termWeightingMap);

        return termWeightingMap;
    }

    ////////计算tf（词频）////////
    private Map<String, Double> calculateTf(List<String> splitedWords) {
        Map<String, Double> termFrequency = new HashMap<>();
        ///////循环所有分得的词///////
        ///////不同来源的词分开记录///////
        for (String term : splitedWords) {

            if (termFrequency.containsKey(term)){
                Double count = termFrequency.get(term);
                termFrequency.put(term,count+1);
            }else {
                termFrequency.put(term, 1.0);
            }
        }
       return termFrequency;

        /////训练集进行训练得到结果

    }


    private Map<String, Map<String, Integer>> calculateAllDf(List<Map.Entry<String, List<String>>> splitedWords,
                                Map<String, String> fileToCate){
        //////计算每个组别里面的所有词频//////
        /////第一个String为字////////
        /////第二个String为类别//////
        /////Integer为出现频数///////
        Map<String, Map<String, Integer>> allDFofClasses = new HashMap<>();//每个词的类文档频率
        //计算每一个字在每一个类的文档频率
        for(Map.Entry<String, List<String>> document : splitedWords){
            List<String> documentSplitedWord = document.getValue();//该篇文档的字s
            HashSet<String> documentWordSet = new HashSet<String>(documentSplitedWord);//文档字去重

            String documentName = document.getKey();//该篇文档的文档名
            String documentCate = fileToCate.get(documentName);//该篇文档的类名

            for(String word : documentWordSet){
                if(allDFofClasses.containsKey(word)){
                    Map<String, Integer> documentFrequencyOfClasses = allDFofClasses.get(word);//一个字在多个类的文档频率
                    if(documentFrequencyOfClasses.containsKey(documentCate)){
                        Integer docuFreOfClass = documentFrequencyOfClasses.get(documentCate);
                        documentFrequencyOfClasses.put(documentCate, docuFreOfClass + 1);
                    }else{
                        documentFrequencyOfClasses.put(documentCate, 1);
                    }
                }else{
                    Map<String, Integer> documentFrequencyOfClasses = new HashMap<>();//一个字在多个类的文档频率
                    documentFrequencyOfClasses.put(documentCate, 1);
                    allDFofClasses.put(word, documentFrequencyOfClasses);
                }
            }
        }
        return allDFofClasses;

    }
    private void calcutateTF_RF(Map<String, Map<String, Double>> tfAllfiles,
                                Map<String, Map<String, Integer>> allDFofClasses,
                                Map<String, Map<String, Double>> TF_RF){
        for(Map.Entry<String, Map<String, Double>> file : tfAllfiles.entrySet()){
            Map<String, Double> tf = file.getValue();
            Map<String, Double> tfrf= new HashMap<>();
            for(Map.Entry<String ,Double> term : tf.entrySet()){
                String word = term.getKey();
                /////获得参数A和B
                /////得到最终结果
                //////计算tf_rf，其中
                //////w(tk,cj) = tfk * log2(2+A/max(B,1));
                int a = 0,b=0;
                if(allDFofClasses.containsKey(word)){
                    Set<Map.Entry<String,Integer>> tfWord = allDFofClasses.get(word).entrySet();
                    for(Iterator<Map.Entry<String,Integer>> iterator = tfWord.iterator();iterator.hasNext();)
                    {
                        if(a == 0)
                            a = iterator.next().getValue();
                        else
                            b+=iterator.next().getValue();
                    }
                }
                Double value = term.getValue()*Math.log(2 + (a / Math.max(b, 1)));
                tfrf.put(word,value);
            }
            TF_RF.put(file.getKey(),tfrf);
        }
    }

}

