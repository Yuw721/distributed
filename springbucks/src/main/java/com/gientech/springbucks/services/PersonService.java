package com.gientech.springbucks.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gientech.springbucks.pojo.Person;

import java.util.List;

/**
 * @Author: Yuwei
 * @Date: 2022-08-16-17:06
 * @Description:
 */
public interface PersonService extends IService<Person> {

    List<Person> getPersonAll();
}
