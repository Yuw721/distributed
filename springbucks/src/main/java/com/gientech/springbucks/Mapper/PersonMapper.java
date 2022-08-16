package com.gientech.springbucks.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gientech.springbucks.pojo.Person;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: Yuwei
 * @Date: 2022-08-16-17:05
 * @Description:
 */
@Mapper
public interface PersonMapper extends BaseMapper<Person> {

    @Select("select * from t_person")
    List<Person> getPersonAll();

}
