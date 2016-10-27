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

    private String termWeightStr = "";

    private Configuration(String configPath){
        InputStream is =  Thread.currentThread().getContextClassLoader().getResourceAsStream(configPath);
        properties = new Properties();
        try {
            properties.load(is);
            termWeightStr = properties.getProperty("TermWeighting");
            dirPath = properties.getProperty("DirPath");
            destPath = properties.getProperty("DestPath");

            configNowPath = configPath;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static Configuration loadConf(String configPath){
        if(configuration == null || (!configuration.configNowPath.equals(configPath))){
            configuration = new Configuration(configPath);
        }
        return configuration;
    }
}
