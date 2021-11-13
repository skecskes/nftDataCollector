package io.monax.nft.datasource;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.io.Serializable;

public class RestClient implements Serializable {

    public String getContent(String requestURL, String auth) {
        OkHttpClient client = new OkHttpClient();

        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(requestURL)
                .get();
        if (auth != null) {
            requestBuilder.addHeader("Authorization", "Bearer " + auth);
        }
        Request request = requestBuilder.build();

        try (Response response = client.newCall(request).execute()) {
            assert response.body() != null;
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
