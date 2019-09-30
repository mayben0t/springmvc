package model;

import java.util.Map;

/**
 * @program: springmvc
 * @description: 请求参数对象
 * @author: WangX
 * @create: 2019-09-29 21:17
 **/
public class Param {
    private Map<String,Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    /**
     *  根据参数名获取long型参数值
     */
    public long getLong(String name){
        return (Long)(paramMap.get(name));
    }

    /**
     * 获取所有字段信息
     */
    public Map<String,Object> getMap(){
        return paramMap;
    }
}
