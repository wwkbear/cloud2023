package com.wwk;

import java.io.IOException;
import java.util.Properties;

/**
 * @author wwkbear
 * @create 2023-02-19-18:09
 */
public class Singleton3 {
    private static final Singleton3 INSTANCE;
    private String info;

    //使用静态代码块的饿汉式
    private Singleton3(){};
    private Singleton3(String info){
        this.info = info;
    };

//    static {
//        INSTANCE = new Singleton3();
//    }
    //如果要进行读取配置文件中的数据，那么使用静态代码块还可以完成读取。
    static {
        Properties properties = new Properties();
        try {
            //注：这里使用类路径加载器是因为配置文件就放在源文件src下。就能通过类加载器读取文件。（还有其他的方式，但这里只使用这种）
            properties.load(Singleton3.class.getClassLoader().getResourceAsStream("test.properties"));
            String pro = properties.getProperty("info");
            INSTANCE = new Singleton3(pro);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Singleton3 getInstance(){
        return INSTANCE;
    }

    public String getInfo() {
        return info;
    }

    @Override
    public String toString() {
        return getInfo();
    }
}
