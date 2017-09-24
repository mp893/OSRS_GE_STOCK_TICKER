package org.mwp.osrs.client;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.rxjava.ext.web.handler.BodyHandler;
import sun.security.provider.certpath.Vertex;

import java.util.Arrays;

public class clientBase extends AbstractVerticle {

    BodyHandler handler;

    @Override
    public void start(Future<Void> future){
        try{
        HttpClientOptions options = new HttpClientOptions().setKeepAlive(true)
                                                           .setDefaultHost("services.runescape.com")
                                                           .setTrustAll(true)
                                                           .setLogActivity(true);
        HttpClient httpClient = vertx.createHttpClient(options);
        String requestUri = "/m=itemdb_oldschool/api/catalogue/detail.json?item=";

        httpClient.getNow(requestUri + "2", httpClientResponse -> {
            httpClientResponse.bodyHandler(BodyHandler-> {
                    System.out.println(BodyHandler.getString(0, 100));
                });
            });
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
