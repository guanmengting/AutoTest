package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.GetUserInfoCase;
import com.course.model.User;
import com.course.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetUserInfoTest {

    @Test(dependsOnGroups = "loginTrue",description = "获取userId为1的用户信息")
    public void getUserInfo() throws IOException, InterruptedException {
        SqlSession session = DatabaseUtil.getSqlSession();
        GetUserInfoCase getUserInfoCase = session.selectOne("getUserInfoCase",1);
        System.out.println(getUserInfoCase.toString());
        System.out.println(TestConfig.getUserInfoUrl);

        JSONArray resultJson = getJsonResult(getUserInfoCase);

        Thread.sleep(3000);

        User user = session.selectOne(getUserInfoCase.getExpected(),getUserInfoCase);
        List<User> userList = new ArrayList<>();
        userList.add(user);
        JSONArray jsonArray = new JSONArray(userList);
        //Assert.assertEquals(jsonArray,resultJson);//如果字段顺序不一样，应把每个字段取出来做验证

        JSONArray resultArray = new JSONArray(resultJson.getString(0));
        System.out.println(jsonArray.toString());
        System.out.println(resultArray.toString());
        Assert.assertEquals(jsonArray.toString(),resultArray.toString());
    }
    private JSONArray getJsonResult(GetUserInfoCase getUserInfoCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.getUserInfoUrl);
        JSONObject param = new JSONObject();
        param.put("userId",getUserInfoCase.getUserId());
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);

        post.setHeader("content-type","application/json");

        //设置cookies
        TestConfig.httpClient = HttpClientBuilder.create().setDefaultCookieStore(TestConfig.cookieStore).build();
        HttpResponse response = TestConfig.httpClient.execute(post);
        String result = EntityUtils.toString(response.getEntity(),"utf-8");

        List resultList = Arrays.asList(result);
        JSONArray array = new JSONArray(resultList);
        return array;

    }
}
