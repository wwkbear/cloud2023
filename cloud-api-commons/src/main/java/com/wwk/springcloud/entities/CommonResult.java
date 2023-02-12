package com.wwk.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一返回类
 * @author wwkbear
 * @create 2023-01-27-17:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {
    private Integer code;
    private String  message;
    private T data;

    public CommonResult(Integer code,String message){
        //如果T为null，一旦调用全参就会异常
        this(code,message,null);
    }

}
