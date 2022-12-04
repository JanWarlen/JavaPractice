package com.janwarlen.learn.spock.demo.service.impl

import com.janwarlen.learn.spock.demo.entity.DO.User
import com.janwarlen.learn.spock.demo.mapper.UserMapper
import spock.lang.Specification

class UserServiceImplTest extends Specification {

    def userMapper = Mock(UserMapper)

    def userService = new UserServiceImpl(userMapper: userMapper)

    def "GetUserById"() {
        given:
        def user = new User()
        user.setName("testData")
        user.setGender(false)
        user.setWeight(188)
        userMapper.selectByPrimaryKey(new Integer(1)) >> user
        when:
        def userDTO = userService.getUserById(1)
        then:
        with(userDTO) {
            userDTO.getName() == user.getName()
            userDTO.getGender() == user.getGender()
            userDTO.getWeight() == user.getWeight()
        }
    }
}
