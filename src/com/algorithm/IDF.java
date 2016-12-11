package com.algorithm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Glad on 2016/10/24.
 */

/**
 * IDF是逆向文件频率（inverse document frequency, IDF）是一个词的普遍重要性的度量。
 * 某一个特定词语的IDF，可以由总文件数目除以包含该词语的文件数目，再将得到的商取对数得到。
 * 其公式如下所示：
 *               总文件数目                                                        |N|
 * idf_i = ----------------- = log ------------
 *          包含第i个词的文件数目                                       | df_i |
 * 
 * @param splitedWords List<Map.Entry<String, List<STring>>> => List<Document> 采用List<words>表示文件
 * @param fileToCate Map<String, String> 表示文件所属类别，当算法属于无监督类型时候该参数为null
 * 
 * @return Map<String, Map<String, Double>> 返回每一个文件中的每一个词的权重
 * 
 * @author chen 2016/12/11
 *
 */

public class IDF extends TermWeighting{

    @Override
    public Map<String, Map<String, Double>> calculate(List<Map.Entry<String, List<String>>> splitedWords,
                                               Map<String, String> fileToCate) {
    	// 首先获取总文件数目。
    	int docNum = splitedWords.size();
    	
    	// 获取每一个文件中的每一个词出现的文件数。
    	Map<String, Map<String, Integer>> wordDocNum = new HashMap<>();
    	CalcWordDocNum( splitedWords, wordDocNum );
    	
    	// 计算每一个文件的每一个词的idf。
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
