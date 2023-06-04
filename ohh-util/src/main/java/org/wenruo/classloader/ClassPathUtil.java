package org.wenruo.classloader;

import java.io.File;

/**
 * @author wendb
 * @date 2023/6/3
 */
public class ClassPathUtil {

    public static String getJarPath(String jarName)  {
        String[] pathArr = System.getProperty("java.class.path").split(":");
        for (String p : pathArr) {
            String[] arr = p.split(File.separator);
            String thisJarName = arr[arr.length - 1];
            if (thisJarName.equals(jarName)) {
                return p;
            }
        }
        throw new Error("jar not found:"+jarName);
    }
}
