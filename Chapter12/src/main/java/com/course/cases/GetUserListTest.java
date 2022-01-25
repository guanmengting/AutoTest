package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.GetUserListCase;
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
import java.util.List;

public class GetUserListTest {

    @Test(dependsOnGroups = "loginTrue",description = "获取名称为test的用户信息")
    public void getUserList() throws IOException, InterruptedException {
        SqlSession session = DatabaseUtil.getSqlSession();
        GetUserListCase getUserListCase = session.selectOne("getUserListCase",1);
        System.out.println(getUserListCase.toString());
        System.out.println(TestConfig.getUserListUrl);
        //发送请求，获取结果
        JSONArray resultJson = getJsonResult(getUserListCase);

        Thread.sleep(3000);

        //验证结果
        List<User> userList = session.selectList(getUserListCase.getExpected(),getUserListCase);
        for(User u : userList){
            System.out.println("获取的user：" + u.toString());
        }
        JSONArray userListJson = new JSONArray(userList);
        Assert.assertEquals(userListJson.length(),resultJson.length());
        for(int i=0;i<resultJson.length();i++){
            JSONObject expect = (JSONObject)resultJson.get(i);
            JSONObject actual = (JSONObject)userListJson.get(i);
            Assert.assertEquals(expect.toString(),actual.toString());
        }
    }

    private JSONArray getJsonResult(GetUserListCase getUserListCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.getUserInfoUrl);
        JSONObject param = new JSONObject();
        param.put("userName",getUserListCase.getUserName());
        param.put("age",getUserListCase.getAge());
        param.put("sex",getUserListCase.getSex());
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);

        post.setHeader("content-type","application/json");

        //设置cookies
        TestConfig.httpClient = HttpClientBuilder.create().setDefaultCookieStore(TestConfig.cookieStore).build();
        HttpResponse response = TestConfig.httpClient.execute(post);
        String result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);
        JSONArray jsonArray = new JSONArray(result);
        return jsonArray;
    }

}
