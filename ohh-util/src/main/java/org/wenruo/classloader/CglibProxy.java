package org.wenruo.classloader;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;

import java.lang.reflect.Method;

public class CglibProxy {

    public static <T> T create(Object target, Class<T> clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback((InvocationHandler) (proxy, method, objects) -> {
           // System.out.println("[执行代理方法"+method.getName()+"]...被代理对象的classloader -->" + target.getClass().getClassLoader());
            Method declaredMethod = target.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());
            return declaredMethod.invoke(target, objects);
        });
        return (T) enhancer.create();
    }

}
