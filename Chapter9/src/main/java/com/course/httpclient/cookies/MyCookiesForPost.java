package com.course.httpclient.cookies;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyCookiesForPost {
    private String url;
    private ResourceBundle bundle;
    private CookieStore store;

    @BeforeTest
    public void beforeTest(){
        bundle = ResourceBundle.getBundle("application", Locale.CHINA);
        url = bundle.getString("test.url");
    }

    @Test
    public void testGetCookies() throws IOException {
        String uri = bundle.getString("getCookies.uri");

        /* init client */
        this.store = new BasicCookieStore();
        HttpClient client = HttpClientBuilder.create().setDefaultCookieStore(this.store).build();

        /* do stuff */
        HttpGet get = new HttpGet(this.url + uri);
        HttpResponse response = client.execute(get);

        /* check cookies */
        List<Cookie> cookieList = this.store.getCookies();
        for (Cookie cookie : cookieList){
            String name = cookie.getName();
            String value = cookie.getValue();
            System.out.println("cookieName=" + name + ",cookieValue=" + value);
        }
    }

    @Test(dependsOnMethods = "testGetCookies")
    public void testPostWithCookies() throws IOException {
        String uri = bundle.getString("test.post.with.cookies");

        //声明一个client对象 并设置cookies信息
        HttpClient client = HttpClientBuilder.create().setDefaultCookieStore(this.store).build();
        //声明一个post方法
        HttpPost post = new HttpPost(this.url + uri);

        //设置请求头信息
        post.setHeader("content-type","application/json");

        //设置参数信息
        JSONObject param = new JSONObject();
        param.put("name","Tom");
        param.put("age","12");
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);

        //执行post方法
        HttpResponse response = client.execute(post);
        //获取响应状态码
        int statusCode = response.getStatusLine().getStatusCode();
        if(statusCode == 200){
            //获取响应结果
            String result = EntityUtils.toString(response.getEntity(),"utf-8");
            System.out.println(result);
            //将响应结果字符串转化为json对象
            JSONObject resultJson = new JSONObject(result);
            //获取结果的值
            String name = (String)resultJson.get("name");
            String success = (String)resultJson.get("success");
            //判断结果的值
            Assert.assertEquals("Tom",name);
            Assert.assertEquals("1",success);
        }
    }

}
