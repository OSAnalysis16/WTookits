package com.algorithm;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cassyang on 2016/11/30.
 */
public class TF_RF implements TermWeighting {

    ///分词之后获得的字符数组
    static List<List<String>> splitTermMap = null;
    ///计算字符的TF
    static List<Map<String, Double>> termWeightingTFMap = null;

    ///构造函数在新建一个TF_RF算法时需要将
    ///splitTermMap和termWeightingTFMap重新初始化
    public TF_RF() {
        splitTermMap = new ArrayList<>();
        termWeightingTFMap = new ArrayList<>();
    }
    @Override
    public List<List<String>> split(List<Map.Entry<String, String>> data) {
    ///////对多个文档进行分词
        for (Map.Entry<String, String> document : data) {
            ////////对单个文档进行分词
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

    @Override
    ////////计算tf（词频）
    public List<Map<String, Double>> calculate(List<List<String>> splitedWords) {
        ///////循环所有分得的词
        ///////不同来源的词分开记录
        for (List<String> documentSplitedWord : splitedWords) {
            ////////遍历所有词通过Map<String ,Double >来记录词频
            Map<String,Double> termCount = new HashMap<>();
            for (String term : documentSplitedWord) {

                if (termCount.containsKey(term)){
                    Double count = termCount.get(term);
                    termCount.put(term,count+1);
                }else {
                    termCount.put(term, 1.0);
                }
            }
            termWeightingTFMap.add(termCount);
        }
        /////训练集进行训练得到结果
        /////获得参数A和B
        /////得到最终结果
        File fashion = new File("C:/Users/huang/git/WTookits/data/supersived_data/fashion");
        File science = new File("C:/Users/huang/git/WTookits/data/supersived_data/science");
        File sport = new File("C:/Users/huang/git/WTookits/data/supersived_data/sport");

        //File outfile = new File("C:/Users/huang/git/WTookits/data/supersived_data/fileCate.txt");

        String[] fas = fashion.list();
        String[] sci = science.list();
        String[] spo = sport.list();
        Map<String, String> fileCatetory = new HashMap<>();

        for(String str : fas){
            fileCatetory.put(str, "fashion");
        }
        for(String str : sci){
            fileCatetory.put(str, "science");
        }
        for(String str : spo){
            fileCatetory.put(str, "sport");
        }

        try{
            PrintWriter pw = new PrintWriter("C:/Users/huang/git/WTookits/data/supersived_data/fileCate.txt");
            StringBuilder sb = new StringBuilder();

            for(Map.Entry<String, String> term : fileCatetory.entrySet()){
                sb.append(term.getKey()+"\t"+term.getValue()+"\n");
            }
            pw.write(sb.toString());
            pw.flush();
            pw.close();
        }catch(Exception e){
            e.printStackTrace();
        }


        //////计算tf_rf，其中
        //////w(tk,cj) = tfk * log2(2+A/max(B,1));

        return termWeightingTFMap;
    }



}
