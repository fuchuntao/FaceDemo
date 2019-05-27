package com.offcn.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.offcn.entity.FaceInformation;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.offcn.util.AccessTokenUtil;


@RestController
public class RegisterController {

	@RequestMapping("/register")
	public String registerFace(@RequestBody FaceInformation faceInformation, HttpServletRequest request, HttpServletResponse resp){
		
		try {
			String imageData = faceInformation.getImageData();
			String url="https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/add";
			String access_token = AccessTokenUtil.getAccessToken();
			CloseableHttpClient client = HttpClients.createDefault();
			HttpPost post = new HttpPost(url+"?access_token="+access_token);
			
			//设置请求头
			post.setHeader("Content-Type","application/json");
			
			//准备请求参数
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			list.add(new BasicNameValuePair("image", imageData));
			list.add(new BasicNameValuePair("image_type", "BASE64"));
			list.add(new BasicNameValuePair("group_id", "java1207"));
			list.add(new BasicNameValuePair("user_id", "fu"));
			
			UrlEncodedFormEntity uploadEntity = new UrlEncodedFormEntity(list);
			
			post.setEntity(uploadEntity);
			
			CloseableHttpResponse res = client.execute(post);
			
			if(res.getStatusLine().getStatusCode()==200){
				HttpEntity entity = res.getEntity();
				String s=EntityUtils.toString(entity, "utf-8");
				System.out.println(s);
				return s;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
}
