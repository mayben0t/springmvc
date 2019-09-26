package util;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * 属性文件操作工具类
 */
public class PropsUtil {
    private static final Logger logger = LoggerFactory.getLogger(PropsUtil.class);

    /**
     * 加载属性文件
     */
    public static Properties loadProps(String fileName){
        Properties props = new Properties();
        try (InputStream  is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)){
            if(is == null){
                throw new FileNotFoundException(fileName + " file is not found");
            }
            props = new Properties();
            props.load(is);
        }catch (IOException e){
            e.printStackTrace();
        }
        return props;
    }

    public static String getString(Properties props,String key){ return getString(props,key,"");}

    public static String getString(Properties props,String key,String defaultValue){
        return props.containsKey(key)?props.getProperty(key):defaultValue;
    }

    public static int getInt(Properties props,String key,int defaultValue){
        return props.containsKey(key)?Integer.valueOf(props.getProperty(key)):defaultValue;
    }

    public static int getInt(Properties props,String key){
        return getInt(props,key,0);
    }

    public static boolean getBoolean(Properties props,String key,boolean defaultValue){
        return props.containsKey(key)? Boolean.getBoolean(props.getProperty(key)):defaultValue;
    }

    public static boolean getBoolean(Properties props,String key){
        return getBoolean(props,key,false);
    }


}
