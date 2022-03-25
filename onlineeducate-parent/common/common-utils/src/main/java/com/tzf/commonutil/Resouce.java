package com.tzf.commonutil;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
@Data
public class Resouce {


    private Integer code; //返回码

    private boolean success;// 是否成功

    private String message;// 返回消息

    private Map<String, Object> data = new HashMap<>();


    private Resouce(){};// 私有化这个类，让其他类无法调用其成员

    // 操作成功返回
    public static Resouce ok(){
        Resouce resouce = new Resouce();
        resouce.setSuccess(true);
        resouce.setCode(20000);
        resouce.setMessage("成功");
        return resouce;
    }

    // 操作失败返回
    public static Resouce error(){
        Resouce resouce = new Resouce();
        resouce.setMessage("失败/(ㄒoㄒ)/~~");
        resouce.setCode(20001);
        resouce.setSuccess(false);
        return resouce;
    }

    //单独引用设置code
    public Resouce code(Integer code){
        this.setCode(code);
        return this;
    }

    public Resouce message(String message) {
        this.setMessage(message);
        return this;
    }

    public Resouce success(boolean success) {
        this.setSuccess(success);
        return this;
    }

    // map集合式
    public Resouce data(Map<String, Object> data) {
        this.setData(data);
        return this;
    }
    // key,value式
    public Resouce data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

}
