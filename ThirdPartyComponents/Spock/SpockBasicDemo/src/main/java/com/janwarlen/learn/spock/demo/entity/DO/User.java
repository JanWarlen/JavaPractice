package com.janwarlen.learn.spock.demo.entity.DO;

import javax.persistence.*;
import java.util.Date;

@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "INTEGER")
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
    @Column(name = "birthDate")
    private Date birthdate;

    /**
     * 创建时间
     */
    @Column(name = "gmt_create")
    private Date gmtCreate;

    /**
     * 更新时间
     */
    @Column(name = "gmt_update")
    private Date gmtUpdate;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取姓名
     *
     * @return name - 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置姓名
     *
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取性别，1 男 0 女
     *
     * @return gender - 性别，1 男 0 女
     */
    public Boolean getGender() {
        return gender;
    }

    /**
     * 设置性别，1 男 0 女
     *
     * @param gender 性别，1 男 0 女
     */
    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    /**
     * 获取体重
     *
     * @return weight - 体重
     */
    public Integer getWeight() {
        return weight;
    }

    /**
     * 设置体重
     *
     * @param weight 体重
     */
    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    /**
     * 获取出生日期
     *
     * @return birthDate - 出生日期
     */
    public Date getBirthdate() {
        return birthdate;
    }

    /**
     * 设置出生日期
     *
     * @param birthdate 出生日期
     */
    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    /**
     * 获取创建时间
     *
     * @return gmt_create - 创建时间
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * 设置创建时间
     *
     * @param gmtCreate 创建时间
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * 获取更新时间
     *
     * @return gmt_update - 更新时间
     */
    public Date getGmtUpdate() {
        return gmtUpdate;
    }

    /**
     * 设置更新时间
     *
     * @param gmtUpdate 更新时间
     */
    public void setGmtUpdate(Date gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }
}