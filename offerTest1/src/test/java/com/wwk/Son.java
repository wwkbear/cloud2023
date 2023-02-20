package com.wwk;

/**
 * @author wwkbear
 * @create 2023-02-19-21:48
 */
public class Son extends Father {
    private int i = test(1);
    private static int j = method();

    static {
        System.out.println("6");
    }
    Son(){
        System.out.println("7");
    }

    {
        System.out.println("8");
    }

    public int test(int i) {
        System.out.println("9");
        return 1;
    }
    public static int method(){
        System.out.println("10");
        return 1;
    }

    public static void main(String[] args) {
        Son s1 = new Son();
        System.out.println("====");
        Son s2 = new Son();
    }
    //个人答案：1，5，6，10，4，3，2，9，8，7
    //4，3，2，10，9，8，7
    //运行结果：5，1，10，6，9，3，2，9，8，7
    //9，3，2，9，8，7

}
