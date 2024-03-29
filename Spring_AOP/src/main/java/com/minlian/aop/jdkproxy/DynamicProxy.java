package com.minlian.aop.jdkproxy;

import com.minlian.aop.monitor.MonitorSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


public class DynamicProxy implements InvocationHandler {

    private Object target;

    public DynamicProxy(Object object) {
        this.target = object;
    }

    @Override
    public Object invoke(Object arg0, Method arg1, Object[] arg2)
            throws Throwable {
        MonitorSession.begin(arg1.getName());
        Object obj = arg1.invoke(target, arg2);
        MonitorSession.end();
        return obj;
    }

    //获取被代理接口实例对象
    public <T> T getProxy() {
        return (T) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                this
        );
    }

}
