package util;

import annotation.Controller;
import annotation.Service;

import java.util.*;

/**
 * @program: springmvc
 * @description:
 * @author: WangX
 * @create: 2019-09-29 10:22
 **/
public final class ClassHelper {

    /**
     * 定义集合类
     */
    private static final Set<Class<?>> CLASS_SET;

    static {
        String basePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }
    /**
     * 获取应用包名下的所有类
     */
    public static Set<Class<?>> getClassSet(){
        return CLASS_SET;
    }

    /**
     * 获取应用包名下所有Service类
     */
    public static Set<Class<?>> getServiceClass(){
        return getRequiredClass(Service.class);
    }

    /**
     * 获取应用包名下所有Controller类
     */
    public static Set<Class<?>> getControllerClass(){
        return getRequiredClass(Controller.class);
    }

    /**
     * 获取应用包名下所有需要的类
     */
    public static Set<Class<?>> getRequiredClass(Class annotationClass){
        Set<Class<?>> classSet = getClassSet();
        Set<Class<?>> requiredClassSet = new HashSet<>();
        if(Objects.nonNull(classSet) && classSet.size()>0){
            for(Class<?> item:classSet){
                // == if(item.isAnnotationPresent(annotation))
                if(Objects.nonNull(item.getAnnotation(annotationClass))){
                    requiredClassSet.add(item);
                }
            }
        }
        return requiredClassSet;
    }

    public static Set<Class<?>> getBeanClass(){
        Set<Class<?>> controllerClass = getControllerClass();
        controllerClass.addAll(getServiceClass());
        return controllerClass;
    }
}
