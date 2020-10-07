package withus.service;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class AndriodSingleNotification {
    public static String SingleNotificationJson(String title, String data, String tokenData) throws JSONException, NullPointerException {
        JSONObject body = new JSONObject();

        body.put("to", tokenData);
        //알림 내용 지정
        JSONObject notification = new JSONObject();
        notification.put("title", title);
        try {
            notification.put("body", URLEncoder.encode(data, "EUC-KR"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        body.put("data", notification);
        return body.toString();
    }
}

