package com.janwarlen.learn.spock.demo.mapper

import com.janwarlen.learn.spock.demo.SpockBasicDemoApplication
import com.janwarlen.learn.spock.demo.entity.DO.User
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import javax.annotation.Resource

/**
 * 使用启动spring容器的方式，主要是测试表结构是否变动导致已有业务是否能正常运行
 */
@SpringBootTest(classes = SpockBasicDemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserMapperTest extends Specification {

    @Resource
    private UserMapper userMapper;

    def "checkTableInsert"() {
        given:
        def user = new User()
        user.setName("testInsert")
        user.setGender(true)
        user.setWeight(999)
        when:
        def insert = userMapper.insertSelective(user)
        then:
        insert == 1
    }


    def "checkTableSelect"() {
        given:
        def user = new User()
        user.setName("testInsert")
        user.setGender(true)
        user.setWeight(999)
        when:
        def users = userMapper.select(user)
        then:
        verifyAll(users) {
            null != users
            users.size() == 1
            def res = users.get(0)
            res.getName() == user.getName()
            res.getGender() == user.getGender()
            res.getWeight() == user.getWeight()
        }
    }

    def "checkTableDelete"() {
        given:
        def user = new User()
        user.setName("testInsert")
        user.setGender(true)
        user.setWeight(999)
        when:
        userMapper.delete(user)
        def users = userMapper.select(user)
        then:
        null == users || users.size() == 0
    }
}
