package withus.service;

import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class
AndroidPushPeriodicNotifications {

    public static String PeriodicNotificationJson(String title, String data,List<String> tokenData) throws JSONException, NullPointerException {
        List<String> tokenlist = new ArrayList<String>();
        JSONObject body = new JSONObject();
        JSONArray token = new JSONArray();
        tokenlist = tokenData;

        for(int i=0; i<tokenlist.size(); i++) {
            token.put(tokenlist.get(i));
        }


        body.put("registration_ids", token);
        //알림 내용 지정
        JSONObject notification = new JSONObject();
        notification.put("title",title);
        try {
            notification.put("body", URLEncoder.encode(data,"EUC-KR"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        body.put("data", notification);
        return body.toString();
    }
}
