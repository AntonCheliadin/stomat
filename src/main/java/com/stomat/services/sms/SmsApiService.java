package com.stomat.services.sms;

import com.stomat.utils.StreamUtil;
import org.apache.http.HttpEntity;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SmsApiService {

    public String sendSms(String recipient, String message) {

        String xmlRequest = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<request>\n" +
                "   <operation>SENDSMS</operation>\t\t\n" +
                "   <message type=\"single\" lifetime=\"24\">\n" +
                "      <recipient>" + recipient + "</recipient>\t\t\n" +
                "      <body>" + message + "</body>\n" +
                "   </message>\n" +
                "</request>";

        try {
            return doHttpRequest(xmlRequest);
        } catch (IOException | AuthenticationException e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    public String getBalance() {
        String xmlRequest = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<request>\n" +
                "   <operation>GETBALANCE</operation>\n" +
                "</request>";

        try {
            return doHttpRequest(xmlRequest);
        } catch (AuthenticationException | IOException e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    private String doHttpRequest(String xmlRequest) throws AuthenticationException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://sms-fly.com/api/api.noai.php");


        httpPost.setEntity(new StringEntity(xmlRequest));
        var credentials = new UsernamePasswordCredentials("380668086634", "kxsBEcFHhz5v7Dh");
        httpPost.addHeader(new BasicScheme().authenticate(credentials, httpPost, null));
        httpPost.addHeader("Accept", "text/xml");
        httpPost.addHeader("Content-Type", "text/xml");

        CloseableHttpResponse response = httpClient.execute(httpPost);

        HttpEntity responseEntity = response.getEntity();

        String responseXml = StreamUtil.inputStreamToString(responseEntity.getContent());
        System.out.println(responseXml);

        httpClient.close();

        return responseXml;
    }
}
