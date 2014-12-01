package com.example.admin.vkreader.javaClasses;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListClass {
    public String[] title;
    private ArrayList arr = new ArrayList();

    public ListClass(JSONObject jsonObject) {
        try {
            jsonObject = jsonObject.getJSONObject("response");
            JSONArray jArray = jsonObject.getJSONArray("wall");
            title = new String[jArray.length()];
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json_message = jArray.getJSONObject(i);
                if (json_message != null) {
                    String text = json_message.getString("text");
                    Pattern pat = Pattern.compile("<.+?>");
                    Matcher mat = pat.matcher(text);
                    mat.find();
                    int k = mat.start();
                    String match = mat.replaceAll("\n");
                    String substring = match.substring(0, k);
                    title[i] = substring;
                    JSONObject im = json_message.getJSONObject("attachment");
                    im = im.getJSONObject("photo");
                    String urls = im.getString("src_big");
                    String data = json_message.optString("date").toString();
                    JsonClass gs = new JsonClass(match, data, urls);
                    gs.getMap().put("textContent", gs.getTextContent());
                    gs.getMap().put("textDate", gs.getTextDate());
                    gs.getMap().put("imageContent", gs.getImageContent());
                    arr.add(gs.getMap());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public ArrayList getArr() {
        return arr;
    }
}
