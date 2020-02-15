package com.song.leaf.service;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class HttpDemo {
    public void get() throws IOException {
        CloseableHttpClient httpClient  = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://www.baidu.com");
        CloseableHttpResponse resp = httpClient.execute(httpGet);
        if (resp.getStatusLine().getStatusCode() == 200) {
            System.out.println("请求成功");
        } else {
            System.out.println("请求失败");
        }
        httpClient.close();
        resp.close();
    }

    public static void main(String[] args) {
        HttpDemo httpDemo = new HttpDemo();
        try {
            httpDemo.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
