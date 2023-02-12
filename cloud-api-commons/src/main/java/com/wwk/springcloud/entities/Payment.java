package com.wwk.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wwkbear
 * @create 2023-01-27-17:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    private Long Id;
    private String serial;

}
