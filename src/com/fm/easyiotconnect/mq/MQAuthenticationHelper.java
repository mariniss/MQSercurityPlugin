package com.fm.easyiotconnect.mq;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fabiomarini on 18/09/14.
 */
public class MQAuthenticationHelper {

    /**
     *
     * @param username
     * @param password
     * @return
     */
    public static Boolean authenticateConnection(String username, String password, String serverUrlConnectionCheck) throws IOException {

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(serverUrlConnectionCheck);

        List nameValuePairs = new ArrayList(1);
        nameValuePairs.add(new BasicNameValuePair("username", username));
        nameValuePairs.add(new BasicNameValuePair("password", password));
        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

        HttpResponse response = client.execute(post);
        String json_string = EntityUtils.toString(response.getEntity());

        CharSequence json_content = json_string.subSequence(json_string.indexOf('{') + 1, json_string.indexOf('}'));
        String[] response_value = json_content.toString().split(":");

        if("\"OK\"".equals(response_value[1]))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     *
     * @param username
     * @param serverUrlSessionCheck
     * @return
     */
    public static Boolean authenticateSession(String username, String queueName, String serverUrlSessionCheck) throws IOException {

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(serverUrlSessionCheck);

        List nameValuePairs = new ArrayList(1);
        nameValuePairs.add(new BasicNameValuePair("username", username));
        nameValuePairs.add(new BasicNameValuePair("queueName", queueName));
        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

        HttpResponse response = client.execute(post);
        String json_string = EntityUtils.toString(response.getEntity());

        CharSequence json_content = json_string.subSequence(json_string.indexOf('{') + 1, json_string.indexOf('}'));
        String[] response_value = json_content.toString().split(":");

        if("\"OK\"".equals(response_value[1]))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
