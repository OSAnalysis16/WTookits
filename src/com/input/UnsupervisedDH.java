package com.input;

import com.util.MapComparator;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.util.function.Function;

/**
 * Created by Glad on 2016/10/25.
 */
public class UnsupervisedDH {
    protected List<Map.Entry<String, String>> data = null;
    protected Map<Integer, String> fileIndex = null;

    public UnsupervisedDH(String dirPath){
        data = new ArrayList<>();
        fileIndex = new HashMap<>();
        loadFromFile(dirPath);
    }

    public List<Map.Entry<String, String>> getData(){
        return data;
    }

    private void loadFromFile(String dirPath) {
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        BufferedReader reader = null;
        for (File file : files) {
            StringBuilder sb = new StringBuilder();
            try{
                reader = new BufferedReader(new FileReader(file));
                String temp = "";
                while ((temp = reader.readLine()) != null){
                    sb.append(temp);
                }
                Map.Entry<String, String> e = new HashMap.SimpleEntry<>(file.getName(), sb.toString());
                data.add(e);
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        for (int i = 0; i < data.size(); i++) {
            fileIndex.put(i, data.get(i).getKey());
        }

        if(reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeResult(List<Map<String, Double>> result, String outFile){
        try {
            PrintWriter pw = new PrintWriter(outFile);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < result.size(); i++) {
                String fileName = fileIndex.get(i);
                sb.append(fileName + "\t");
                Map<String, Double> sorted = MapComparator.sortByValue(result.get(i));
//                sorted.forEach((k, v) -> sb.append(k + ":" + v + ","));
                for (Map.Entry<String, Double> stringDoubleEntry : sorted.entrySet()) {
                    sb.append(stringDoubleEntry.getKey() + ":" + stringDoubleEntry.getValue() + ",");
                }
                sb.deleteCharAt(sb.length() - 1);
                sb.append("\n\n");
            }
            pw.write(sb.toString());
            pw.flush();
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
