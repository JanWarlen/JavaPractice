package com.janwarlen.learn.spock.demo.entity.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class UserDTO {
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别，1 男 0 女
     */
    private Boolean gender;

    /**
     * 体重
     */
    private Integer weight;

    /**
     * 出生日期
     */
    private Date birthdate;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 更新时间
     */
    private Date gmtUpdate;
}
