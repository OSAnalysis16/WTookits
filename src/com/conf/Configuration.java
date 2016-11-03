package com.conf;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Glad on 2016/10/24.
 */
public class Configuration {
    private static Configuration configuration;
    private Properties properties;
    private String configNowPath;

    private String dirPath;
    private String categoryPath;

    public String getDestPath() {
        return destPath;
    }

    private String destPath;

    public String getTermWeightStr() {
        return termWeightStr;
    }

    public String getDirPath() {
        return dirPath;
    }

    public String getCategoryPath() {
        return categoryPath;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }

    public void setCategoryPath(String categoryPath) {
        this.categoryPath = categoryPath;
    }

    public void setDestPath(String destPath) {
        this.destPath = destPath;
    }

    public void setTermWeightStr(String termWeightStr) {
        this.termWeightStr = termWeightStr;
    }

    private String termWeightStr = "";

    public Configuration(String configPath){
        InputStream is =  Thread.currentThread().getContextClassLoader().getResourceAsStream(configPath);
        properties = new Properties();
        try {
            properties.load(is);
            termWeightStr = properties.getProperty("TermWeighting");
            dirPath = properties.getProperty("DirPath");
            destPath = properties.getProperty("DestPath");
            categoryPath = properties.getProperty("CategoryPath");

            configNowPath = configPath;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Configuration(String termWeightStr, String dirPath, String categoryPath, String destPath){
        this.termWeightStr = termWeightStr;
        this.dirPath = dirPath;
        this.categoryPath = categoryPath;
        this.destPath = destPath;
    }

    public Configuration(){}

    public static Configuration loadConf(String configPath){
        if(configuration == null || (!configuration.configNowPath.equals(configPath))){
            configuration = new Configuration(configPath);
        }
        return configuration;
    }


}
