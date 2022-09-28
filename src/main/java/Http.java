import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ht
 */
public class Http {
    public static String API = "https" + "://" + "pixa" + "bay." + "com" + "/api/";
    public static String key = "30121772-bd40792ce93cd4db71c1c525d";
    public static String per_page = "200";
    public static String q = "Border%20Collie";
    public static String BorderCollie = "BorderCollie";
    public static String src = "src/main/resources/";
    public static Map<String, Object> Query = new HashMap<>();
    public static int l = 3;

    public static void main(String[] args) {
        for (int i = 1; i <= l; i++) {
            File file = new File(src + BorderCollie + i);
            if (!file.exists()) {
                try {
                    page(String.valueOf(i));
                    System.out.println(file + "文件已创建");
                } catch (JSONException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("\n" + file + "文件已存在");
            }
        }
    }

    public static void page(String page) throws JSONException {
        Query.put("key", key);
        Query.put("per_page", per_page);
        Query.put("page", page);
        Query.put("q", q);
        HttpClient client = HttpClient.newHttpClient();
        StringBuilder queryStr = new StringBuilder();
        Query.forEach((s, o) -> {
            if ("".contentEquals(queryStr)) {
                queryStr.append("?");
            } else {
                queryStr.append("&");
            }
            queryStr.append(s);
            queryStr.append("=");
            queryStr.append(o);
        });
        System.out.println(API + queryStr);
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(API + queryStr)).build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body).thenAccept(s -> {
            ConsoleProgressBarDemo cpb = new ConsoleProgressBarDemo(50, '#');
            JsonUtils.createJsonFile(s, src + "BorderCollie" + page + ".json");
            JsonRootBean jsonRootBean = JSONObject.parseObject(s, JsonRootBean.class);
            System.out.println(jsonRootBean.getHits().size());
            List<Hits> rootBeanHits = jsonRootBean.getHits();
            for (int i = 0; i < rootBeanHits.size(); i++) {
                Hits hits = rootBeanHits.get(i);
                ImgUtils.downloadFile(hits.getLargeImageURL(), name(hits, page));
                cpb.show((i + 1) / rootBeanHits.size() * 100);
            }
        }).join();
    }

    public static String name(Hits hits, String page) {
        return src + BorderCollie + page + "/" + BorderCollie + "_" + hits.getId() + "_" + hits.getImageWidth() + "_" + hits.getImageHeight() + "_" + hits.getImageSize();
    }

}
