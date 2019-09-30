package core;

import model.Data;
import model.Handler;
import model.Param;
import model.View;
import util.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @program: springmvc
 * @description: 请求转发器
 * @author: WangX
 * @create: 2019-09-29 21:52
 **/
@WebServlet(urlPatterns = "/*",loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        //初始化相关helper类
        HelperLoader.init();
        //获取ServletContext对象(用于注册servlet)
        ServletContext servletContext = servletConfig.getServletContext();
        //注册处理jsp的servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath()+"*");
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取请求方法与请求路径
        String requestMethod = request.getMethod().toLowerCase();
        String requestPath = request.getPathInfo();
        //获取Action处理器
        Handler handler = ControllerHepler.getHandler(requestMethod, requestPath);
        if(handler != null){
            //获取controller类及其bean实例
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);
            //创建请求参数对象
            Map<String,Object> paramMap = new HashMap<>();
            Enumeration<String> paramNames = request.getParameterNames();
            while (paramNames.hasMoreElements()){
                String paramName = paramNames.nextElement();
                String paramValue = request.getParameter(paramName);
                paramMap.put(paramName,paramValue);
            }
            String body = CodecUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
            if(StringUtil.isNotEmpty(body)){
                String[] params = StringUtil.splitString(body, "&");
                if(ArrayUtil.isNotEmpty(params)){
                    for(String param:params){
                        String[] array = StringUtil.splitString(param, "=");
                        if(ArrayUtil.isNotEmpty(array) && array.length==2){
                            String paramName = array[0];
                            String paramValue = array[1];
                            paramMap.put(paramName,paramValue);
                        }
                    }
                }
            }
            Param param = new Param(paramMap);
            Method actionMethod = handler.getActionMethod();
            Object result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
            if(request instanceof View){
                //返回jsp页面
                View view = (View) result;
                String path = view.getPath();
                if(StringUtil.isNotEmpty(path)){
                    if(path.startsWith("/")){
                        response.sendRedirect(request.getContextPath());
                    }else {
                        Map<String, Object> model = view.getModel();
                        for(Map.Entry<String,Object> entry:model.entrySet()){
                            request.setAttribute(entry.getKey(),entry.getValue());
                        }
                        request.getRequestDispatcher(ConfigHelper.getAppJspPath()+path).forward(request,response);
                    }
                }else if(result instanceof Data){
                    Data data = (Data) result;
                    Object model = data.getModel();
                    if(Objects.nonNull(model)){
                        response.setContentType("application/json");
                        response.setCharacterEncoding("utf-8");
                        PrintWriter writer = response.getWriter();
                        String json = JsonUtil.toJson(model);
                        writer.write(json);
                        writer.flush();
                        writer.close();
                    }
                }
            }
        }
    }
}
