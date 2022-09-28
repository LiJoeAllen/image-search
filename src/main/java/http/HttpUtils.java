package http;

import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

/**
 * @author ht
 */
public class HttpUtils {


    public static void get(String api, @NotNull Map<String, Object> query) {
        HttpClient client = HttpClient.newHttpClient();
        StringBuilder queryStr = new StringBuilder();
        query.forEach((s, o) -> {
            if ("".contentEquals(queryStr)) {
                queryStr.append("?");
            } else {
                queryStr.append("&");
            }
            queryStr.append(s);
            queryStr.append("=");
            queryStr.append(o);
        });
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(api + queryStr))
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println)
                .join();

    }
}
