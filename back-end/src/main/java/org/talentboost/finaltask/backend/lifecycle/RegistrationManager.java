package org.talentboost.finaltask.backend.lifecycle;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.talentboost.finaltask.backend.util.Env;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class RegistrationManager {

    private final String APP_NAME;
    private final String APP_ADDRESS;
    private final String DOMAIN_API_URL;
    private String APP_ID;

    @Autowired
    RegistrationManager(Env env) {
        APP_NAME = Objects.requireNonNull(env.get("APP_NAME"));
        APP_ADDRESS = String.format("http://%s:8080",
                Objects.requireNonNull(env.get("HOST_IP")));

        DOMAIN_API_URL = Objects.requireNonNull(env.get("DOMAIN_API_URL"));
    }

    @PostConstruct
    public void register() throws IOException {
        CloseableHttpClient client = HttpClients.createMinimal();
        HttpPost post = new HttpPost(
                DOMAIN_API_URL + "/register"
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

        try (
                InputStream inStream = response.getEntity().getContent();
                InputStreamReader inReader = new InputStreamReader(inStream);
                BufferedReader bufReader = new BufferedReader(inReader)
        ) {
            APP_ID = bufReader.readLine();
            client.close();
        }
    }

    @PreDestroy
    public void deregister() throws IOException {
        CloseableHttpClient client = HttpClients.createMinimal();

        HttpDelete delete = new HttpDelete(
                String.format("%s/deregister/%s", DOMAIN_API_URL, APP_ID)
        );

        client.execute(delete);
        client.close();
    }

}
