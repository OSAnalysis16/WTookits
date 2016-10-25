package com.input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Glad on 2016/10/25.
 */
public class SupervisedDH extends UnsupervisedDH{

    protected Map<String, String> fileToCato = null;

    public SupervisedDH(String dirPath, String categoryPath) {
        super(dirPath);
        fileToCato = new HashMap<>();
        loadCatogoryMap(categoryPath);
    }

    private void loadCatogoryMap(String categoryPath) {
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new FileReader(categoryPath));
            String temp = "";
            while ((temp = reader.readLine()) != null){
                String[] fields = temp.split("\t");
                fileToCato.put(fields[0], fields[1]);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
