package com.algorithm;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;

/**
 * class TF_IDF.
 * TF-IDF（term ferquency - inverse document frequency）是一种用于检索的加权技术，可以评估一个词对于一个文件集或者语料库的重要性。‘
 * TF-IDF包含有两项，一项是TF是词频，指的是某一个给定的词在文本中出现的次数（可以归一化也可以不归一化）。
 * 另一项是IDF，逆向文本频率，是一个词语的普遍重要性度量。
 * TF-IDF = TF * IDF。
 * 其计算公式如下所示：
 *                      词在文本中出现的频率                             总文件数目                                             n_i            | N |
 * TF-IDF = TF * IDF = ---------------- * --------------- = ----------- * log-----------
 *                       所有词出现的总次数                       包含某个词的文件数目                sum( n_i )        | df_i |
 *                       
 * @param splitedWords List<Map.Entry<String, List<STring>>> => List<Document> 采用List<words>表示文件
 * @param fileToCate Map<String, String> 表示文件所属类别，当算法属于无监督类型时候该参数为null
 * 
 * @return Map<String, Map<String, Double>> 返回每一个文件中的每一个词的权重
 * 
 * @author chen 2016/12/11
 *
 */

public class TF_IDF extends TermWeighting {

	@Override
	public Map<String, Map<String, Double>> calculate(List<Entry<String, List<String>>> splitedWords,
			Map<String, String> fileToCate) {
		// 首先计算tf。
		Map<String, Map<String, Double>> tfDoc = new HashMap<>();
		CalcTF(splitedWords, tfDoc);
		
		// 然后计算idf。
		Map<String, Map<String, Double>> idfDoc = new HashMap<>();
		CalcIDF(splitedWords, idfDoc);
		
		// 再计算tf-idf。
		CalcTF_IDF(tfDoc, idfDoc, termWeightingMap);
		
		return termWeightingMap;
	}
	
	private void CalcTF(List<Map.Entry<String, List<String>>> splited_words, Map<String, Map<String, Double>> tf_doc){
		Map<String, Integer> docWordCount = new HashMap<>();
		
		for (Map.Entry<String, List<String>> doc : splited_words){
			List<String> docSplitedWord = doc.getValue();
			Map<String, Double> termFrequency = new HashMap<>();
			int count = 0;
			
			for (String term : docSplitedWord){
				if (termFrequency.containsKey(term)){
					Double frequency = termFrequency.get(term);
					termFrequency.put(term, frequency+1);
				}else{
					termFrequency.put(term, 1.0);
				}
				count++;
			}
			
			tf_doc.put(doc.getKey(), termFrequency);
			docWordCount.put(doc.getKey(), count);
		}
		
		// 归一化。
		for (String filename : tf_doc.keySet()){
			Map<String, Double> freq = tf_doc.get(filename);
			
			for (String key : freq.keySet()){
				Double tf = freq.get(key) / docWordCount.get(filename);
				freq.put(key, tf);
			}
			
			tf_doc.put(filename, freq);
		}
	}
	
	private void CalcIDF(List<Map.Entry<String, List<String>>> splited_words, Map<String, Map<String, Double>> idf_doc){
		// 首先获取总文件数目。
    	int docNum = splited_words.size();
    	
    	// 获取每一个文件中的每一个词出现的文件数。
    	Map<String, Map<String, Integer>> wordDocNum = new HashMap<>();
    	CalcWordDocNum( splited_words, wordDocNum );
    	
    	// 计算每一个文件的每一个词的idf。
    	CalcIDF(docNum, wordDocNum, idf_doc);
	}
	
	private void CalcTF_IDF(Map<String, Map<String, Double>> tf_doc,
							Map<String, Map<String, Double>> idf_doc,
							Map<String, Map<String, Double>> tf_idf){
		for (String filename : tf_doc.keySet()){
			if (!idf_doc.containsKey(filename)){
				System.out.println("ERROR: finename doesn't matched in tf and idf.");
				return;
			}
			
			Map<String, Double> tfidf = tf_doc.get(filename);
			
			for (String key : tfidf.keySet()){
				if (!idf_doc.get(filename).containsKey(key)){
					System.out.println("ERROR: key doesn't matched in tf and idf.");
					return;
				}
				
				Double tiVal = tfidf.get(key) * idf_doc.get(filename).get(key);
				
				tfidf.put(key, tiVal);
			}
			
			tf_idf.put(filename, tfidf);
		}
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
