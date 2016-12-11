package com.algorithm;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;

/**
 * class TF_IDF.
 * TF-IDF��term ferquency - inverse document frequency����һ�����ڼ����ļ�Ȩ��������������һ���ʶ���һ���ļ����������Ͽ����Ҫ�ԡ���
 * TF-IDF���������һ����TF�Ǵ�Ƶ��ָ����ĳһ�������Ĵ����ı��г��ֵĴ��������Թ�һ��Ҳ���Բ���һ������
 * ��һ����IDF�������ı�Ƶ�ʣ���һ��������ձ���Ҫ�Զ�����
 * TF-IDF = TF * IDF��
 * ����㹫ʽ������ʾ��
 *                      �����ı��г��ֵ�Ƶ��                             ���ļ���Ŀ                                             n_i            | N |
 * TF-IDF = TF * IDF = ---------------- * --------------- = ----------- * log-----------
 *                       ���дʳ��ֵ��ܴ���                       ����ĳ���ʵ��ļ���Ŀ                sum( n_i )        | df_i |
 *                       
 * @param splitedWords List<Map.Entry<String, List<STring>>> => List<Document> ����List<words>��ʾ�ļ�
 * @param fileToCate Map<String, String> ��ʾ�ļ�������𣬵��㷨�����޼ල����ʱ��ò���Ϊnull
 * 
 * @return Map<String, Map<String, Double>> ����ÿһ���ļ��е�ÿһ���ʵ�Ȩ��
 * 
 * @author chen 2016/12/11
 *
 */

public class TF_IDF extends TermWeighting {

	@Override
	public Map<String, Map<String, Double>> calculate(List<Entry<String, List<String>>> splitedWords,
			Map<String, String> fileToCate) {
		// ���ȼ���tf��
		Map<String, Map<String, Double>> tfDoc = new HashMap<>();
		CalcTF(splitedWords, tfDoc);
		
		// Ȼ�����idf��
		Map<String, Map<String, Double>> idfDoc = new HashMap<>();
		CalcIDF(splitedWords, idfDoc);
		
		// �ټ���tf-idf��
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
		
		// ��һ����
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
		// ���Ȼ�ȡ���ļ���Ŀ��
    	int docNum = splited_words.size();
    	
    	// ��ȡÿһ���ļ��е�ÿһ���ʳ��ֵ��ļ�����
    	Map<String, Map<String, Integer>> wordDocNum = new HashMap<>();
    	CalcWordDocNum( splited_words, wordDocNum );
    	
    	// ����ÿһ���ļ���ÿһ���ʵ�idf��
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
