/*
 * UserCompositeData.java Created on 14/10/22
 *
 * Copyright 2022 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.business.admin.user.vo;

import java.io.Serializable;

/**
 * @author jens
 */
public class UserCompositeData implements Serializable {

    private String name;
    private int age;

    public UserCompositeData() {
    }

    public UserCompositeData(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    @Override
    public String toString() {
        return "UserCompositeData{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
