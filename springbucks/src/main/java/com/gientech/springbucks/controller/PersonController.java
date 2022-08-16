package com.gientech.springbucks.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gientech.springbucks.pojo.Person;
import com.gientech.springbucks.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Yuwei
 * @Date: 2022-08-16-17:07
 * @Description:
 */

@RestController
public class PersonController {
    @Autowired
    PersonService personService;

    @GetMapping("/getperson")
    public List<Person> getPerson() {
        List<Person> personAll = personService.getPersonAll();
        return personAll;
    }

    @GetMapping("/getPage")
    public Page<Person> page(@RequestParam(value = "pn", defaultValue = "1") Integer pn) {
        //构造分页参数
        Page<Person> page = new Page<>(pn, 2);
        //调用page进行分页 获得分页信息
        Page<Person> personPage = personService.page(page, null);
        //获得所有对象信息
        List<Person> records = personPage.getRecords();
        page.setRecords(records);
        return page;
    }

    @PostMapping("/add")
    public Boolean add(Person person) {
        boolean save = personService.save(person);
        return save;
    }

    @GetMapping("/deleteUser/{id}")
    public Boolean deleteUser(@PathVariable("id") int id) {
        boolean b = personService.removeById(id);
        return b;
    }

}