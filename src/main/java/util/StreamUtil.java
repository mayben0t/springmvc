package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @program: springmvc
 * @description: 流操作工具类
 * @author: WangX
 * @create: 2019-09-29 23:01
 **/
public final class StreamUtil {
    private static final Logger logger = LoggerFactory.getLogger(StreamUtil.class);

    /**
     * 从输入流中获取
     */
    public static String getString(InputStream is){
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line=reader.readLine())!=null){
                sb.append(line);
            }
        }catch (Exception e){
            logger.error("get string failure",e);
            throw new RuntimeException(e);
        }
        return sb.toString();
    }
}
