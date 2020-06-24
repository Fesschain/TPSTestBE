package main.java.netty;

import javax.net.ssl.SSLParameters;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class RequestHelper {

    private static HttpClient httpClient;

    public static HttpClient getHttpClient() {
        if(httpClient == null) {
            SSLParameters parameters = new SSLParameters();
            parameters.setProtocols(new String[]{"TLSv1.2"});
            httpClient = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofMinutes(1))
                    .sslParameters(parameters)
                    .build();
        }
        return httpClient;
    }

    public static String GET_Request(String url){
        try{
            HttpRequest request = HttpRequest.newBuilder().version(HttpClient.Version.HTTP_1_1).timeout(Duration.ofMinutes(1)).GET().uri(new URI(url)).build();
            HttpResponse<String> send = getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            String code = String.valueOf(send.statusCode());


            return send.body();
        }catch (URISyntaxException ignored){
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
