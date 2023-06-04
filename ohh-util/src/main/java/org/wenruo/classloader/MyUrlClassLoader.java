package org.wenruo.classloader;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author wendb
 * @date 2023/6/3
 */
public class MyUrlClassLoader extends URLClassLoader {
    private final String jarName;

    public MyUrlClassLoader(URL[] urls, String jarName) {
        super(urls, null);
        this.jarName = jarName;
    }

    @Override
    protected Class<?> findClass(final String name) throws ClassNotFoundException {
        System.out.println("[" + jarName + "]-loadClass-" + name);
        return super.findClass(name);

    }

}
