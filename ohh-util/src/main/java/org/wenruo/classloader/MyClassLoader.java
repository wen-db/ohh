package org.wenruo.classloader;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author wendb
 * @date 2023/6/2
 */
public class MyClassLoader extends ClassLoader {
    private String jarName;

    public MyClassLoader(String jarName) {
        this.jarName = jarName;
    }

  /*  @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            return loadMyClass(name, jarName);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class<?> findLoadedClass = this.findLoadedClass(name);
        if (findLoadedClass != null) {
            return findLoadedClass;
        } else {
            try {
                Class<?> c = this.findClass(name);
                if (resolve) {
                    this.resolveClass(c);
                }
                return c;
            } catch (Throwable e) {
                return super.loadClass(name, resolve);
            }

        }

    }
*/

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        try {
            return loadMyClass(name, jarName);
        } catch (Throwable e) {
            e.printStackTrace();
            return super.loadClass(name);
        }

    }

    public Class<?> loadMyClass(String className, String jarName) throws ClassNotFoundException, IOException {
       File file = new File(getJarPath(jarName));
        JarFile jarFile = new JarFile(file);
        Enumeration<JarEntry> enumeration = jarFile.entries();
        while (enumeration.hasMoreElements()) {
            JarEntry jarEntry = enumeration.nextElement();
            if (jarEntry.getName().equals(className.replaceAll("\\.", File.separator) + ".class")) {
                InputStream inputStream = jarFile.getInputStream(jarEntry);
                byte[] bytes = IOUtils.toByteArray(inputStream);
               // System.out.println(jarName+className+" length="+bytes.length);
                Class<?> c = super.defineClass(className, bytes, 0, bytes.length);
               // System.out.println("c=====" + c.getProtectionDomain().getCodeSource().getLocation().toString());
                return c;
            }

        }
        return super.loadClass(className);
    }


    private String getJarPath(String jarName) throws ClassNotFoundException {
        String[] pathArr = System.getProperty("java.class.path").split(":");
        for (String p : pathArr) {
            String[] arr = p.split(File.separator);
            String thisJarName = arr[arr.length - 1];
            if (thisJarName.equals(jarName)) {
                return p;
            }
        }
        throw new ClassNotFoundException(jarName);
    }
}
