package org.talentboost.finaltask.backend.lifecycle;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class RegistrationManager {

    private static final String APP_NAME = "VLADO";
    private static final String APP_ADDRESS = String.format("http://%s:8080",
            System.getenv("HOST_IP"));
    private static String APP_ID;

    @PostConstruct
    public void register() throws IOException {
        CloseableHttpClient client = HttpClients.createMinimal();
        HttpPost post = new HttpPost(
                "http://meme-it-platform-service-api.herokuapp.com/domain/register"
        );

        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("name", APP_NAME));
        urlParameters.add(new BasicNameValuePair("address", APP_ADDRESS));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 200) {
            throw new RuntimeException(
                    "Something went wrong: Status code"
                            + statusCode
            );
        }

        BufferedReader br = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent())
        );

        APP_ID = br.readLine();

        client.close();
    }

    @PreDestroy
    public void deregister() throws IOException {
        CloseableHttpClient client = HttpClients.createMinimal();

        HttpDelete delete = new HttpDelete("http://meme-it-platform-service-api.herokuapp.com/domain/deregister/" + APP_ID);

        client.execute(delete);
        client.close();
    }

}
