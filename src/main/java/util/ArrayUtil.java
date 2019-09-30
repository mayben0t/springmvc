package util;

import org.apache.commons.lang3.ArrayUtils;

/**
 * @program: springmvc
 * @description: 数组工具类
 * @author: WangX
 * @create: 2019-09-29 17:38
 **/
public class ArrayUtil {
    /**
     * 判断数组是否非空
     */
    public static boolean isNotEmpty(Object[] array){
        return !ArrayUtils.isEmpty(array);
    }

    /**
     * 判断数组是否为空
     */
    public static boolean isEmpty(Object[] array){
        return !isNotEmpty(array);
    }
}
