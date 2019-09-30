package util;

import annotation.Inject;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Set;

/**
 * @program: springmvc
 * @description: 依赖注入助手类
 * @author: WangX
 * @create: 2019-09-29 17:04
 **/
public final class IocHelper {

    static {
        invocationOfController();
    }


    public static Void invocationOfController(){
        Set<Class<?>> beanClass = ClassHelper.getBeanClass();
        BeanHelper.getBeanMap();
        if(Objects.nonNull(beanClass) && beanClass.size()>0){
            for(Class<?> item:beanClass){
                Field[] declaredFields = item.getDeclaredFields();
                if(declaredFields.length>0){
                    for(Field fieldItem:declaredFields){
                        fieldItem.setAccessible(true);
                        if(fieldItem.isAnnotationPresent(Inject.class)){
                            Class<?> type = fieldItem.getType();
                            Object bean = BeanHelper.getBean(type);
                            ReflectionUtil.setField(item,fieldItem,bean);
                        }
                    }
                }
            }
        }
        return null;
    }
}
