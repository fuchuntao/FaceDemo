package com.offcn.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class AccessTokenUtil {

	public static String getAccessToken(){
		try {
			CloseableHttpClient client = HttpClients.createDefault();
			String url="https://aip.baidubce.com/oauth/2.0/token";
			String grant_type="client_credentials";
//			String client_id="dDVgms4ppRGZFqPAoDmV9eoZ";
//			String client_secret="eZhzAgLXhWoyjkPffer4fyzFooqSEpv6";

			String client_id="ORDeD7AoDf3ZFGrBe1QQqX3n";
			String client_secret="nmNiph2yFu7WrpuYGTmW9uQaIjLiDQEM";
			HttpPost post = new HttpPost(url+"?grant_type="+grant_type+"&client_id="+client_id+"&client_secret="+client_secret);
			
			CloseableHttpResponse response = client.execute(post);
			if(response.getStatusLine().getStatusCode()==200){
				HttpEntity entity = response.getEntity();
				String s=EntityUtils.toString(entity, "utf-8");
				JsonObject jsonObject = new JsonParser().parse(s).getAsJsonObject();
				
				String token=jsonObject.get("access_token").getAsString();
				return token;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
