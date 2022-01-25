package com.course.testng.paramter;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class DataProviderTest {

    @Test(dataProvider = "data")
    public void testDataProvider(String name,int age){
        System.out.println("name=" + name + ",age=" + age);
    }

    @DataProvider(name = "data")
    public Object[][] providerData(){
        Object[][] o = new Object[][]{
                {"Jack",28},
                {"Rose",26},
                {"Tom",10}
        };
        return o;
    }

    @Test(dataProvider = "methodData")
    public void test1(String name,int age){
        System.out.println("test1 name=" + name + ",age=" + age);
    }

    @Test(dataProvider = "methodData")
    public void test2(String name,int age){
        System.out.println("test2 name=" + name + ",age=" + age);
    }

    @DataProvider(name = "methodData")
    public Object[][] methodData(Method method){
        Object[][] o = null;
        if(method.getName().equals("test1")){
            o = new Object[][]{
                    {"Jack",28},
                    {"Rose",26}
            };
        }else if(method.getName().equals("test2")){
            o = new Object[][]{
                    {"Tom",10},
                    {"Jerry",12}
            };
        }
        return o;
    }
}
