package model;

/**
 * @program: springmvc
 * @description: 返回数据对象
 * @author: WangX
 * @create: 2019-09-29 21:50
 **/
public class Data {

    /**
     * 模型数据
     */
    private Object model;

    public Data(Object model) {
        this.model = model;
    }

    public Object getModel() {
        return model;
    }
}
