package org.wenruo.classloader;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author wendb
 * @date 2023/6/3
 */
public class ClassLoaderFactory {
    public static <T> T loadClass(String jarName, Class<T> clazz) {
        try {
            String url = "file:" + ClassPathUtil.getJarPath(jarName);
            MyUrlClassLoader classLoader = new MyUrlClassLoader(new URL[]{new URL(url)}, jarName);
            Class<?> c = classLoader.loadClass(clazz.getName());
            return CglibProxy.create(c.newInstance(), clazz);
        } catch (MalformedURLException e) {
            throw new RuntimeException(jarName + " not fund");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(clazz.getName() + "not fund");
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

}
