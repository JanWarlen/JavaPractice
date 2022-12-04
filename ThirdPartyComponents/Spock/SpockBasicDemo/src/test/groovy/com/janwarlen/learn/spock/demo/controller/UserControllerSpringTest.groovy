package com.janwarlen.learn.spock.demo.controller

import com.alibaba.fastjson2.JSONObject
import com.janwarlen.learn.spock.demo.SpockBasicDemoApplication
import com.janwarlen.learn.spock.demo.entity.VO.UserVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Specification

import javax.servlet.http.HttpServletResponse

/**
 * 这种启动spring容器的方式单元测试会比较慢，并且会连接数据库操作，不太建议
 * 如果是为了验证一些filter，这种方式是最适合的
 */
@AutoConfigureMockMvc
@SpringBootTest(classes = SpockBasicDemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerSpringTest extends Specification {

    @Autowired
    private MockMvc mockMvc;

    def "GetUserById"() {

        given:
        def id = 1
        def user = new UserVO()
        user.setName("test")
        user.setGender(true)
        user.setWeight(110)
        when:
        def response = mockMvc.perform(MockMvcRequestBuilders.get("/user/getUserById?id=" + id)).andReturn()

        then:
        verifyAll(response) {
            response.getResponse().status == HttpServletResponse.SC_OK
            response.getResponse().getContentType() == "application/json"
            def res = JSONObject.parseObject(response.getResponse().getContentAsString(), UserVO.class)
            res.getName() == user.getName()
            res.getGender() == user.getGender()
            res.getWeight() == user.getWeight()
        }

    }
}
