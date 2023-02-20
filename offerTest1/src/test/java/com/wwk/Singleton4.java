package com.wwk;

/**
 * 懒汉式
 * 1.线程不安全（适用于单线程）
 * @author wwkbear
 * @create 2023-02-19-18:53
 */
public class Singleton4 {
    private static Singleton4 singleton4;
    private Singleton4(){};

//    public static Singleton4 getSingleton4() throws InterruptedException {
//        if (singleton4 == null){
//            //假定前面有一些代码需要时间处理。
//            Thread.sleep(1000);
//
//            singleton4 = new Singleton4();
//        }
//        return singleton4;
//    }

    public static Singleton4 getSingleton4() throws InterruptedException {
        if (singleton4 == null){ //这里是有很多线程都来获取的时候，就不需要每个都进去等待同步锁。性能稍微好一点。
            synchronized (Singleton4.class){
                if (singleton4 == null){
                    //假定前面有一些代码需要时间处理。
                    Thread.sleep(1000);

                    singleton4 = new Singleton4();
                }
            }
        }

        return singleton4;
    }

}
