package com.algorithm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Glad on 2016/10/24.
 */

/**
 * IDF�������ļ�Ƶ�ʣ�inverse document frequency, IDF����һ���ʵ��ձ���Ҫ�ԵĶ�����
 * ĳһ���ض������IDF�����������ļ���Ŀ���԰����ô�����ļ���Ŀ���ٽ��õ�����ȡ�����õ���
 * �乫ʽ������ʾ��
 *               ���ļ���Ŀ                                                        |N|
 * idf_i = ----------------- = log ------------
 *          ������i���ʵ��ļ���Ŀ                                       | df_i |
 * 
 * @param splitedWords List<Map.Entry<String, List<STring>>> => List<Document> ����List<words>��ʾ�ļ�
 * @param fileToCate Map<String, String> ��ʾ�ļ�������𣬵��㷨�����޼ල����ʱ��ò���Ϊnull
 * 
 * @return Map<String, Map<String, Double>> ����ÿһ���ļ��е�ÿһ���ʵ�Ȩ��
 * 
 * @author chen 2016/12/11
 *
 */

public class IDF extends TermWeighting{

    @Override
    public Map<String, Map<String, Double>> calculate(List<Map.Entry<String, List<String>>> splitedWords,
                                               Map<String, String> fileToCate) {
    	// ���Ȼ�ȡ���ļ���Ŀ��
    	int docNum = splitedWords.size();
    	
    	// ��ȡÿһ���ļ��е�ÿһ���ʳ��ֵ��ļ�����
    	Map<String, Map<String, Integer>> wordDocNum = new HashMap<>();
    	CalcWordDocNum( splitedWords, wordDocNum );
    	
    	// ����ÿһ���ļ���ÿһ���ʵ�idf��
    	CalcIDF(docNum, wordDocNum, termWeightingMap);
    	
        return termWeightingMap;
    }
    
    private void CalcWordDocNum(List<Map.Entry<String, List<String>>> splited_words,
    														Map<String, Map<String, Integer>> word_doc_num){
    	Map<String, Integer> wordDocCount = new HashMap<>();
    	for (Map.Entry<String, List<String>> doc : splited_words){
    		String filename = doc.getKey();
    		List<String> docSplitedWords = doc.getValue();
    		Map<String, Integer> wordOfDoc = new HashMap<>();
    		
    		for (String word : docSplitedWords){
    			if (wordOfDoc.containsKey(word))
    				continue;
    			
    			wordOfDoc.put(word,  1);
    			
    			if (wordDocCount.containsKey(word)){
    				int count = wordDocCount.get(word);
    				wordDocCount.put(word, count+1);
    			}
    			else{
    				wordDocCount.put(word, 1);
    			}
    		}
    		
    		word_doc_num.put(filename, wordOfDoc);
    	}
    	
    	for (String key : wordDocCount.keySet()){
    		for (String filename : word_doc_num.keySet()){
    			if (word_doc_num.get(filename).containsKey(key)){
    				word_doc_num.get(filename).put(key, wordDocCount.get(key));
    			}
    		}
    	}
    }
    
    private void CalcIDF(int doc_num, 
    					 Map<String, Map<String, Integer>> word_doc_num, 
    					 Map<String, Map<String, Double>> term_weighting_map){
    	for (String filename : word_doc_num.keySet()){
    		Map<String, Integer> wordCount = word_doc_num.get(filename);
    		Map<String, Double> idfMapDoc = new HashMap<>();
    		
    		for (String key : wordCount.keySet()){
    			Double idf = Math.log(doc_num/wordCount.get(key));
    			idfMapDoc.put(key, idf);
    		}
    		
    		term_weighting_map.put(filename, idfMapDoc);
    	}
    }

}
