package com.ckx.runner.core.service;


import com.ckx.runner.core.domain.Example;

public interface ExampleService {

    //新增示例
    public Example save(Example example);

    //通过id查找示例信息
    public Example findById(Long id);

}
