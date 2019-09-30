package model;

import java.lang.reflect.Method;

/**
 * @program: springmvc
 * @description: 封装Action信息
 * @author: WangX
 * @create: 2019-09-29 17:55
 **/
public class Handler {

    /**
     * controller类
     */
    private Class<?> controllerClass;

    /**
     * Action方法
     */
    private Method actionMethod;

    public Handler(Class<?> controllerClass, Method actionMethod) {
        this.controllerClass = controllerClass;
        this.actionMethod = actionMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getActionMethod() {
        return actionMethod;
    }
}
