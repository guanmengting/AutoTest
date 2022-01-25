package com.course.server;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@Api(value = "/",description = "这是我全部的get方法")
public class MyGetMethod {

    @RequestMapping(value = "/getCookies",method = RequestMethod.GET)
    @ApiOperation(value = "通过这个方法可以获取cookies",httpMethod = "GET")
    public String getCookies(HttpServletResponse response){
        //HttpServletRequest 装请求信息的类
        //HttpServletResponse 装响应信息的类
        Cookie cookie = new Cookie("login", "true");
        response.addCookie(cookie);
        return "获得cookies成功";
    }

    /**
     *  需要携带cookies才能进行访问的get请求
     */
    @RequestMapping(value = "/get/with/cookies",method = RequestMethod.GET)
    @ApiOperation(value = "需要携带cookies才能进行访问的get请求",httpMethod = "GET")
    public String getWithCookies(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(Objects.isNull(cookies)){
            return "必须携带cookies信息来访问";
        }
        for (Cookie cookie : cookies){
            if(cookie.getName().equals("login")
                    && cookie.getValue().equals("true")){
                return "访问成功";
            }
        }
        return "必须携带cookies信息来访问";
    }

    /**
     * 需要携带参数才能访问的get请求：模拟获取商品列表
     *
     * 第一种实现方式 url: ip:port/get/with/param?key=value&key=value
     */
    @RequestMapping(value = "/get/with/param",method = RequestMethod.GET)
    @ApiOperation(value = "需要携带参数才能访问的get请求方法一",httpMethod = "GET")
    public Map<String,Integer> getList(@RequestParam Integer start,
                                       @RequestParam Integer end) {
        Map<String,Integer> myList = new HashMap<>();
        myList.put("鞋子",200);
        myList.put("帽子",20);
        myList.put("裙子",400);
        return myList;
    }

    /**
     * 第二种实现方式 url: ip:port/get/with/param/10/20
     */
    @RequestMapping(value = "/get/with/param/{start}/{end}")
    @ApiOperation(value = "需要携带参数才能访问的get请求方法二",httpMethod = "GET")
    public Map<String,Integer> myGetList(@PathVariable Integer start,
                                         @PathVariable Integer end){
        Map<String,Integer> myList = new HashMap<>();
        myList.put("鞋子",200);
        myList.put("帽子",20);
        myList.put("裙子",400);
        return myList;
    }
}
