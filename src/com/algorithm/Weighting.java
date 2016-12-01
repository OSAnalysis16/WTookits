package com.algorithm;

import java.util.List;
import java.util.Map;

/**
 * Created by Glad on 2016/12/1.
 */
public interface Weighting {
    /**
     * calculate term weighting for each term in document
     * @param splitedWords List<Entry<fileName, Document>> and use List<word> represents a document
     * @param fileToCate Map<FileName, category>
     *          file's category, if the term weighting algorithm is unsupervised, this field is null
     * @return Map<fileName, Map<term, weight>>, use Map<word, weighting> represents the term's weighting in the document
     */
    Map<String, Map<String, Double>> calculate(List<Map.Entry<String, List<String>>> splitedWords, Map<String, String> fileToCate);
}
