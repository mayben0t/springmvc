package util;

import annotation.Action;
import model.Handler;
import model.Request;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @program: springmvc
 * @description: 控制器助手类
 * @author: WangX
 * @create: 2019-09-29 17:57
 **/
public final class ControllerHepler {
    /**
     * 用于存放请求与处理器的映射关系（简称 Action Map）
     */
    private static final Map<Request, Handler> ACTION_MAP = new HashMap<>();

    static {
        //获取所有的Controller类
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClass();
        if(CollectionUtils.isNotEmpty(controllerClassSet)){
            for(Class<?> controllerClass:controllerClassSet){
                Method[] declaredMethods = controllerClass.getDeclaredMethods();
                if(ArrayUtil.isNotEmpty(declaredMethods)){
                    for(Method method:declaredMethods){
                        if(method.isAnnotationPresent(Action.class)){
                            Action action = method.getAnnotation(Action.class);
                            String mapping = action.value();
                            if(mapping.matches("\\w+:/\\w*")){
                                String[] array = mapping.split(":");
                                if(ArrayUtil.isNotEmpty(array)&&array.length == 2){
                                    String requestMethod = array[0];
                                    String requestPath = array[1];
                                    Request request = new Request(requestMethod, requestPath);
                                    Handler handler = new Handler(controllerClass, method);
                                    ACTION_MAP.put(request,handler);
                                }
                            }
                        }
                    }
                }
            }
        }

    }


    /**
     * 获取handler
     */
    public static Handler getHandler(String requestMethod,String requestPath){
        Request request = new Request(requestMethod, requestPath);
        return ACTION_MAP.get(request);
    }
}
