package com.wwk;

/**
 * @author wwkbear
 * @create 2023-02-19-21:46
 */
public class Father {
    private int i = test(1);
    private static int j = method();

    static {
        System.out.println("1");
    }
    Father(){
        System.out.println("2");
    }

    {
        System.out.println("3");
    }

    protected int test(int i) {
        System.out.println("4");
        return 1;
    }
    public static int method(){
        System.out.println("5");
        return 1;
    }

}
