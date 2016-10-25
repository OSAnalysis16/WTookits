package com.algorithm;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Glad on 2016/10/24.
 */
public interface TermWeighting {

    List<List<String>> split(List<Map.Entry<String, String>> data) ;
    List<Map<String, Double>> calculate(List<List<String>> splitedWords);
}
