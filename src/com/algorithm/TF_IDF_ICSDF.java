package com.algorithm;
import java.util.*;
import java.lang.Math.*;
/**
 * Created by huang on 2016/12/9.
 */
public class TF_IDF_ICSDF extends TermWeighting {

    /**
     * @param splitedWords List<Document> and use List<word> represents a document
     * @param fileToCate Map<FileName, category>
     *          file's category, if the term weighting algorithm is unsupervised, this field is null
     * @return
     */
    @Override
    public Map<String, Map<String, Double>> calculate(List<Map.Entry<String, List<String>>> splitedWords,
                                                      Map<String, String> fileToCate) {
        Map<String, Map<String, Double>> tfAllfiles =new HashMap<>();
        for(Map.Entry<String,List<String>> document : splitedWords){
            String fileName = document.getKey();
            List<String> fileWords = document.getValue();
            //TF
            Map<String, Double> termFrequency = tf(fileWords);
            //TF in all files
            tfAllfiles.put(fileName, termFrequency);
        }
        //IDF : inverse document frequency
        Map<String,Double> inverseDocumentFrequency = idf(splitedWords);
        //ICSDF : inverse class space density frequency
        Map<String, Double> inverseClassSpaceDensityFrequency = icsdf(splitedWords,fileToCate);
        //TF_IDF_ICSDF
        tf_idf_icsdf(tfAllfiles,inverseDocumentFrequency,inverseClassSpaceDensityFrequency,termWeightingMap);
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

    private Map<String, Double> idf(List<Map.Entry<String, List<String>>> splitedWords){
        int docNum = splitedWords.size();//文档数目
        Map<String, Double> inverseDocumentFrequency = new HashMap<>();
        Map<String, Integer> documentFrequency = new HashMap<>();

        for (Map.Entry<String, List<String>> document : splitedWords) {
            List<String> documentSplitedWord = document.getValue();
            HashSet<String> documentWordSet = new HashSet<String>(documentSplitedWord);
            for(String word : documentWordSet){
                if(documentFrequency.containsKey(word)){
                    documentFrequency.put(word, documentFrequency.get(word)+1);
                }else{
                    documentFrequency.put(word, 1);
                }
            }
        }

        for (Map.Entry<String, Integer> term : documentFrequency.entrySet()) {
            Double value = Math.log(docNum / term.getValue().doubleValue()) + 1;
            inverseDocumentFrequency.put(term.getKey(), value);
        }
        return inverseDocumentFrequency;
    }

    private Map<String, Double> icsdf(List<Map.Entry<String, List<String>>> splitedWords,Map<String, String> fileToCate){
        Map<String, Double> icsdf = new HashMap<>();

        Map<String,Double> DocNumOfClasses = new HashMap<>();//每个类的文档数目
        //计算每个类的文档数目
        for(Map.Entry<String,String> document : fileToCate.entrySet()){
            String cateName = document.getValue();
            if(DocNumOfClasses.containsKey(cateName)){
                Double NumClass = DocNumOfClasses.get(cateName);
                DocNumOfClasses.put(cateName, NumClass + 1.0);
            }else{
                DocNumOfClasses.put(cateName, 1.0);
            }
        }
        Map<String, Map<String, Integer>> allDFofClasses = new HashMap<>();//每个词的类文档频率
        //计算每一个字在每一个类的文档频率
        for(Map.Entry<String, List<String>> document : splitedWords){
            //Map<String, Integer> documentFrequencyOfClasses = new HashMap<>();//一个字在多个类的文档频率
            List<String> documentSplitedWord = document.getValue();//该篇文档的字s
            HashSet<String> documentWordSet = new HashSet<String>(documentSplitedWord);//文档字去重

            String documentName = document.getKey();//该篇文档的文档名
            String documentCate = fileToCate.get(documentName);//该篇文档的类名

            for(String word : documentWordSet){
                if(allDFofClasses.containsKey(word)){
                    Map<String, Integer> documentFrequencyOfClasses = new HashMap<>();//一个字在多个类的文档频率
                    documentFrequencyOfClasses = allDFofClasses.get(word);
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
        for(Map.Entry<String,Map<String,Integer>> term : allDFofClasses.entrySet()){
            String word = term.getKey();
            Double value;
            Double mClasses = Double.valueOf(DocNumOfClasses.size());//一共多少个类
            Double deno = .0;
            Map<String, Integer> documentFrequencyOfClasses = new HashMap<>();//一个字在多个类的文档频率
            documentFrequencyOfClasses = term.getValue();
            for(Map.Entry<String,Double> DNC : DocNumOfClasses.entrySet()){
                //检测该字在该类别是不是没出现过，没出现过会是null值
                if(documentFrequencyOfClasses.get(DNC.getKey())==null){
                    deno += 0.0 / DNC.getValue();
                }else{
                    deno += Double.valueOf(documentFrequencyOfClasses.get(DNC.getKey())) / DNC.getValue();
                }
            }
            value = 1 + Math.log(mClasses / deno);
            icsdf.put(word,value);
        }
        return icsdf;
    }
    private  void tf_idf_icsdf(Map<String, Map<String, Double>> tfAllfiles,
                               Map<String, Double> inverseDocuFrequency,
                               Map<String, Double> ICSDF,
                               Map<String, Map<String, Double>> TF_IDF_ICSDF){
        for(Map.Entry<String, Map<String, Double>> file : tfAllfiles.entrySet()){
            Map<String, Double> tf = file.getValue();
            Map<String, Double> tfidficsdf = new HashMap<>();
            for(Map.Entry<String ,Double> term : tf.entrySet()){
                String word = term.getKey();
                Double value = term.getValue() * inverseDocuFrequency.get(word) * ICSDF.get(word);
                tfidficsdf.put(word, value);
            }
            TF_IDF_ICSDF.put(file.getKey(), tfidficsdf);
        }
    }



}
