package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * @program: springmvc
 * @description: json工具类
 * @author: WangX
 * @create: 2019-09-30 11:54
 **/
public final class JsonUtil {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 将pojo转为json
     */
    public static <T> String toJson(T obj){
        String json;
        try {
            json = OBJECT_MAPPER.writeValueAsString(obj);
        }catch (Exception e){
            logger.error("convert pojo to JSON failure",e);
            throw new RuntimeException(e);
        }
        return json;
    }

    /**
     * 将json转为pojo
     */
    public static <T> T fromJson(String json,Class<T> type){
        T pojo;
        try {
            pojo = OBJECT_MAPPER.readValue(json,type);
        }catch (Exception e){
            logger.error("convert JSON to POJO failure",e);
            throw new RuntimeException(e);
        }
        return pojo;
    }

}
