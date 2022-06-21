package com.example.traderbookv2.service;

import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class MailGunService {
    public void sendMail(String subject,String body,String recipients) {
        final String DOMAIN = "sandbox21f2be22d48040119538412bdb7c6d49.mailgun.org";
        try {
            List<NameValuePair> form = new ArrayList<>();
            form.add(new BasicNameValuePair("from", "Traderbook<admin@mg.example.com>"));
            form.add(new BasicNameValuePair("to", "petko.lyutskanov@trading212.com"));
            form.add(new BasicNameValuePair("subject", subject));
            form.add(new BasicNameValuePair("text", body));
            postMessage(String.format("https://api.mailgun.net/v3/%s/messages", DOMAIN), form);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private  String postMessage(String URL,List<NameValuePair> form) throws Exception {
        if(form==null||URL==null||URL.trim().length()==0) return "";
        StringBuilder stringBuilder = new StringBuilder();

        org.apache.http.client.HttpClient client = buildHttpClient();
        HttpPost httpPost = new HttpPost(URL);
        HttpResponse response = null;
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(form, Consts.UTF_8);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            response = client.execute(httpPost);
            stringBuilder.append(getBody(response.getEntity().getContent()));
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                System.out.println("post mailgun messages ok:"+URL);
            } else {
                System.out.println("Failed to post mailgun,status=" + statusCode);
                throw new RuntimeException("post failed:"+URL);
            }
        } catch (Exception e) {
            throw e;
        }finally {
            if(response!=null&&response.getEntity()!=null) {
                response.getEntity().consumeContent();
            }
        }
        return stringBuilder.toString();
    }

    private  String getBody(InputStream inputStream) {
        String result = "";
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    inputStream));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                result += inputLine;
                result += "\n";
            }
            in.close();
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
        return result;
    }

    private  HttpClient buildHttpClient() {
        return HttpClientBuilder
                .create()
                .setDefaultCredentialsProvider(getAuthProvider())
                .build();
    }

    private  CredentialsProvider getAuthProvider() {
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials
                = new UsernamePasswordCredentials("api", "75bbb809a27b4de65d49423774faee75-4f207195-49baaad7");
        provider.setCredentials(AuthScope.ANY, credentials);
        return provider;
    }

}
