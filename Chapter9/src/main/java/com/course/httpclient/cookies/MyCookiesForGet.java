package com.course.httpclient.cookies;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyCookiesForGet {
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
    public void testGetWithCookies() throws IOException {
        String uri = bundle.getString("test.get.with.cookies");

        HttpClient client = HttpClientBuilder.create().setDefaultCookieStore(this.store).build();

        HttpGet get = new HttpGet(this.url + uri);
        HttpResponse response = client.execute(get);
        //获取响应状态码
        int statusCode = response.getStatusLine().getStatusCode();
        if(statusCode == 200){
            String result = EntityUtils.toString(response.getEntity(),"utf-8");
            System.out.println(result);
        }
    }
}
