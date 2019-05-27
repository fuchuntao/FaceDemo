package com.offcn.controller;

import com.google.gson.*;
import com.offcn.common.Result;
import com.offcn.entity.FaceInformation;
import com.offcn.util.AccessTokenUtil;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class LoginController {

	/**
	 * 人脸搜索
	 * @Title: loginFace
	 * @Description: TODO 功能描述
	 * @param faceInformation
	 * @return: java.lang.String
	 */
	@RequestMapping(value = "/login")
	public String loginFace(@RequestBody FaceInformation faceInformation){
		String imageData = faceInformation.getImageData();
		try {
			String access_token = AccessTokenUtil.getAccessToken();
			String url="https://aip.baidubce.com/rest/2.0/face/v3/search";
			CloseableHttpClient client = HttpClients.createDefault();
			HttpPost post = new HttpPost(url+"?access_token="+access_token);
			//设置请求头
			post.setHeader("Content-Type","application/json");
			//准备请求参数
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			list.add(new BasicNameValuePair("image", imageData));
			list.add(new BasicNameValuePair("image_type", "BASE64"));
			list.add(new BasicNameValuePair("group_id_list", "java1206"));
			UrlEncodedFormEntity uploadEntity = new UrlEncodedFormEntity(list);
			post.setEntity(uploadEntity);
			CloseableHttpResponse res = client.execute(post);
			
			if(res.getStatusLine().getStatusCode()==200){
				HttpEntity entity = res.getEntity();
				String s=EntityUtils.toString(entity, "utf-8");
				JsonObject JsonObject = new JsonParser().parse(s).getAsJsonObject();
				JsonArray jsonArray = JsonObject.get("result").getAsJsonObject().get("user_list").getAsJsonArray();
				for (JsonElement jsonElement : jsonArray) {
					Gson gson = new Gson();
					Result bean = gson.fromJson(jsonElement, Result.class);
			        double score = bean.getScore();
			        System.out.println("人脸识别相似度:"+score);
				}
				return s;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
}
