package com.gientech.springbucks.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author: Yuwei
 * @Date: 2022-08-16-17:06
 * @Description:
 */
@Data
@TableName("t_person")
public class Person {

    private Long id;
    private String name;
    private String sex;
    private Integer age;
}
