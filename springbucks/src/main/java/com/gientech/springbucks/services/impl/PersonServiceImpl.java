package com.gientech.springbucks.services.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gientech.springbucks.mapper.PersonMapper;
import com.gientech.springbucks.pojo.Person;
import com.gientech.springbucks.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Author: Yuwei
 * @Date: 2022-08-16-17:08
 * @Description:
 */
@Service
public class PersonServiceImpl extends ServiceImpl<PersonMapper,Person> implements PersonService {

    @Autowired
    PersonMapper personMapper;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Person> getPersonAll() {
        return personMapper.getPersonAll();
    }

}
