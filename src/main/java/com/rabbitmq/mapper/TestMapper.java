package com.rabbitmq.mapper;

import com.rabbitmq.bean.TestBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * Created by liwk
 * Date:2018/8/6 9:31
 */
@Component
@Mapper
public interface TestMapper {
    TestBean getBeanId(Integer id);
}
