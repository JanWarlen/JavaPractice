package com.janwarlen.learn.spock.demo.controller

import com.janwarlen.learn.spock.demo.entity.DTO.UserDTO
import com.janwarlen.learn.spock.demo.service.UserService
import spock.lang.Specification

class UserControllerTest extends Specification {

    def userService = Mock(UserService)
    def userController = new UserController(userService: userService)

    def "GetUserById"() {

        given:
        def id = 1
        def user = new UserDTO()
        user.setName("testData")
        user.setGender(false)
        user.setWeight(188)
        userService.getUserById(new Integer(1)) >> user
        when:
        def userRes = userController.getUserById(1)
        then:
        with(userRes) {
            userRes.getName() == user.getName()
            userRes.getGender() == user.getGender()
            userRes.getWeight() == user.getWeight()
        }

    }
}
