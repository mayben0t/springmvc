package util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @program: springmvc
 * @description: Bean 助手类
 * @author: WangX
 * @create: 2019-09-29 16:45
 **/
public final class BeanHelper {

    /**
     * 定义Bean映射（用于存放Bean类与Bean实例的映射关系）
     */
    private static final Map<Class<?>,Object> BEAN_MAP = new HashMap();

    static {
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClass();
        for(Class<?> item:beanClassSet){
            Object o = ReflectionUtil.newInstance(item);
            BEAN_MAP.put(item,o);
        }
    }

    /**
     * 获取Bean映射
     */
    public static Map<Class<?>,Object> getBeanMap(){
        return BEAN_MAP;
    }

    /**
     * 获取bean实例
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> cls){
        if(BEAN_MAP == null||BEAN_MAP.size()<1){
            throw new RuntimeException("Bean map is null,check plz");
        }
        if(!BEAN_MAP.containsKey(cls)){
            throw new RuntimeException("can not get bean by class: "+cls);
        }
        return (T)BEAN_MAP.get(cls);
    }
}
