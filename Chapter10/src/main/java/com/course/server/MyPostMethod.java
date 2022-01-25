package com.course.server;

import com.course.bean.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Api(value = "/",description = "这是我全部的post方法")
@RequestMapping("/v1")
public class MyPostMethod {
    private static Cookie cookie;

    //模拟用户登录成功，获取到cookies，携带cookies访问其他接口获取列表
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ApiOperation(value = "登录接口，成功后获取cookies信息",httpMethod = "POST")
    public String login(HttpServletResponse response,
                        @RequestParam(value = "userName",required = true) String userName,
                        @RequestParam(value = "password",required = true) String password){
        if(userName.equals("guanmengting") && password.equals("123456")){
            cookie = new Cookie("login","true");
            response.addCookie(cookie);
            return "恭喜你登录成功！";
        }
        return "用户名或密码错误！";
    }

    @RequestMapping(value = "/getUserList",method = RequestMethod.POST)
    @ApiOperation(value = "获取用户列表",httpMethod = "POST")
    public String getUserList(HttpServletRequest request,
                            @RequestBody User u){
        User user = new User();
        //获取cookies
        Cookie[] cookies = request.getCookies();
        //验证cookies是否合法
        for(Cookie c : cookies){
            if(c.getName().equals("login") && c.getValue().equals("true")
                && u.getUserName().equals("guanmengting") && u.getPassword().equals("123456")){
                user.setName("Rose");
                user.setAge("28");
                user.setSex("woman");
                return user.toString();
            }
        }
        return "参数不合法";
    }
}
