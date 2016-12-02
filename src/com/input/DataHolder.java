package com.input;

import com.algorithm.TermWeighting;
import com.conf.Configuration;
import com.util.MapComparator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/**
 * Created by Glad on 2016/11/3.
 */
public abstract class DataHolder {
    protected List<Map.Entry<String, String>> data = null;
    protected Map<String, String> fileToCate = null;
    protected Map<String,Long> fileInfo = null;
    public List<Map.Entry<String, String>> getData(){
        return data;
    }


    /**
     * if isSupervised, get the map <FileName, Category>
     *     else return null and warn the user;
     * @return
     */
    public abstract Map<String, String> getFileToCate();

    public abstract Map<String, Long> getFileInfo();

    public void writeResult(Map<String, Map<String, Double>> result, String outFile){
        try {
            PrintWriter pw = new PrintWriter(outFile);
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, Map<String, Double>> file : result.entrySet()) {
                String fileName = file.getKey();
                sb.append(fileName + "\t");
                Map<String, Double> sorted = MapComparator.sortByValue(file.getValue());
                //                sorted.forEach((k, v) -> sb.append(k + ":" + v + ","));
                for (Map.Entry<String, Double> stringDoubleEntry : sorted.entrySet()) {
                    sb.append(stringDoubleEntry.getKey() + ":" + stringDoubleEntry.getValue() + ",");
                }
                sb.deleteCharAt(sb.length() - 1);
                sb.append("\n\n");
            }
//            for (int i = 0; i < result.size(); i++) {
//                String fileName = fileIndex.get(i);
//                sb.append(fileName + "\t");
//                Map<String, Double> sorted = MapComparator.sortByValue(result.get(i));
////                sorted.forEach((k, v) -> sb.append(k + ":" + v + ","));
//                for (Map.Entry<String, Double> stringDoubleEntry : sorted.entrySet()) {
//                    sb.append(stringDoubleEntry.getKey() + ":" + stringDoubleEntry.getValue() + ",");
//                }
//                sb.deleteCharAt(sb.length() - 1);
//                sb.append("\n\n");
//            }
            pw.write(sb.toString());
            pw.flush();
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
