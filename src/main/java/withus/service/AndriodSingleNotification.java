package withus.service;

import com.google.gson.JsonObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class AndriodSingleNotification {
    public static String SingleNotificationJson(String title, String data, String tokenData) throws NullPointerException {
        JsonObject body = new JsonObject();

        body.addProperty("to", tokenData);

        //알림 내용 지정
        JsonObject notification = new JsonObject();
        notification.addProperty("title", title);

        try {
            notification.addProperty("body", URLEncoder.encode(data, "EUC-KR"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        body.add("data", notification);

        return body.toString();
    }
}

