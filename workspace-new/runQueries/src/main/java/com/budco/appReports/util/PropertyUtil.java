package com.budco.appReports.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtil {
    
public static Properties loadProperties(String propsFileName){
         
        final Properties props = new Properties();

        final File propsFile = new File(propsFileName);
        final String fileName = propsFile.getAbsolutePath();

        try {
            FileInputStream inStream = new FileInputStream(propsFile);
            props.load(inStream);
        }
        catch (FileNotFoundException e) {
           System.out.println("could not find properties file " + fileName);
        }
        catch (IOException e) {
           System.out.println("could not use properties file " + fileName);
        }
        
        return props;
    }

}

