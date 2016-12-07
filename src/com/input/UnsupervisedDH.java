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
public class UnsupervisedDH extends DataHolder{

    private Boolean isSupervised = false;

    public UnsupervisedDH(String dirPath){
        data = new ArrayList<>();
        loadFromFile(dirPath);
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

        if(reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Map<String, String> getFileToCate() {
        System.out.println("The actually term weighting is unsupervised. The file to category result is null.");
        return fileToCate;
    }
}
