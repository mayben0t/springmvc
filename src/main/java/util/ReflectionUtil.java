package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @program: springmvc
 * @description: 反射工具类
 * @author: WangX
 * @create: 2019-09-29 11:19
 **/
public final class ReflectionUtil {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);

    /**
     * 创建实例
     */
    public static Object newInstance(Class<?> cls){
        Object instance;
        try {
            instance = cls.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return instance;
    }

    /**
     * 调用方法
     */
    public static Object invokeMethod(Object obj, Method method,Object... args){
        Object result = null;
        boolean isAccess = method.isAccessible();
        if(isAccess){
            doInvoke(result,obj,method,args);
        }else {
            synchronized (obj){
                try {
                    doInvoke(result,obj,method,args);
                }
                finally {
                    method.setAccessible(false);
                }
            }
        }
        return result;
    }

    public static Void doInvoke(Object result,Object obj, Method method,Object... args){
        try {
            method.setAccessible(true);
            result = method.invoke(obj,args);
        }catch (Exception e){
            logger.error("反射方法报错,e:[{}]",e);
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * 设置成员变量的值
     */
    public static void setField(Object obj, Field field,Object value){
        try {
            field.setAccessible(true);
            field.set(obj,value);
        }catch (Exception e){
            logger.error("set field failure",e);
            throw new RuntimeException(e);
        }
    }
}
