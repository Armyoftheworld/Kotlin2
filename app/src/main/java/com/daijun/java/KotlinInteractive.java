package com.daijun.java;


import com.daijun.kotlin.ChildClass;
import com.daijun.kotlin.KotlinUtils;
import com.daijun.kotlin.PropertyJvmField;
import com.daijun.kotlin.PropertyKotlin;

import java.io.IOException;
import java.util.Date;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2020-02-20
 * @description
 */
public class KotlinInteractive {

    public static void main(String[] args) {
        KotlinUtils.getTime();
        KotlinUtils.getDate();

        PropertyJvmField field = new PropertyJvmField();
        System.out.println(field.id);

        System.out.println(PropertyJvmField.sex);
        System.out.println(PropertyJvmField.name);
        System.out.println(PropertyJvmField.MAX_COUNT);
        System.out.println(PropertyKotlin.friends);

        PropertyJvmField.getWeek();
        PropertyKotlin.getMonth();
        PropertyJvmField.getYear();

        KotlinUtils.getkotlinSeconds();

        field.addGirlFriends("Nancy");
        field.showGirlFriends();

        KotlinUtils.getMinutes();
        KotlinUtils.getMinutes(new Date());

        try {
            KotlinUtils.throwExecptionFunction();
        } catch (IOException e) {
            e.printStackTrace();
        }
        KotlinUtils.unboxChildClass(KotlinUtils.boxChildClass(new ChildClass()));
        System.out.println(KotlinUtils.returnEmptyList());
    }
}
