package util;

/**
 * @program: springmvc
 * @description: 加载相应的helper类
 * @author: WangX
 * @create: 2019-09-29 20:40
 **/
public final class HelperLoader {

    public static void init(){
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                IocHelper.class,
                ControllerHepler.class
        };
        for(Class<?> cls:classList){
            ClassUtil.loadClass(cls.getName(),true);
        }
    }
}
