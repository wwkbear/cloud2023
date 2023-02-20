package com.wwk;

/**
 * 1.构造器私有化
 * 2.自行创建实例并使用静态变量存储
 * 3.提供获取实例的方法
 * @author wwkbear
 * @create 2023-02-19-18:03
 */
public class Singleton1 {
    //饿汉式直接创建
    private Singleton1(){
    }
    public static final Singleton1 INSTENCE = new Singleton1();

}
