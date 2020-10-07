package withus.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class
AndroidPushPeriodicNotifications {

    public static String PeriodicNotificationJson(String title, String data, List<String> tokenData) throws NullPointerException {
        List<String> tokenlist = new ArrayList<String>();
        JsonObject body = new JsonObject();
        JsonArray token = new JsonArray();
        tokenlist = tokenData;

        for (int i = 0; i < tokenlist.size(); i++) {
            token.add(tokenlist.get(i));
        }


        body.add("registration_ids", token);
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
