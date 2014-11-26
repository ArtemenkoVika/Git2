package com.example.vv.vkreader.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.example.vv.vkreader.JavaClasses.GsonClass;
import com.example.vv.vkreader.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaseFragment extends Fragment {
    protected GsonClass gs;

    protected class ParseTask extends AsyncTask<Void, Void, String[]> {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";
        String[] title2;
        List a = new ArrayList();

        @Override
        protected String[] doInBackground(Void... params) {
            try {
                URL url = new URL(getResources().getString(R.string.url));
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                resultJson = buffer.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(resultJson);
                jsonObject = jsonObject.getJSONObject("response");
                JSONArray jArray = jsonObject.getJSONArray("wall");
                title2 = new String[jArray.length()];
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
                        String toUp = substring.toUpperCase();
                        title2[i] = toUp;
                        JSONObject im = json_message.getJSONObject("attachment");
                        im = im.getJSONObject("photo");
                        String urls = im.getString("src_big");
                        String data = json_message.optString("date").toString();
                        gs = new GsonClass(match, data, urls);
                        gs.getMap().put("textContent", gs.getTextContent());
                        gs.getMap().put("textDate", gs.getTextDate());
                        gs.getMap().put("imageContent", gs.getImageContent());
                        a.add(gs.getMap());
                    }
                }
                gs.setArr(a);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return title2;
        }

        @Override
        protected void onPostExecute(String[] strJson) {
            super.onPostExecute(strJson);
        }
    }

    public static class LoadImageFromNetwork extends AsyncTask<String, Void, Bitmap> {
        ImageView imageBitmap;

        public LoadImageFromNetwork(ImageView imageBitmap) {
            this.imageBitmap = imageBitmap;
        }

        protected Bitmap doInBackground(String... url) {
            Bitmap imageT = null;
            try {
                InputStream init = new java.net.URL(url[0]).openStream();
                imageT = BitmapFactory.decodeStream(init);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return imageT;
        }

        protected void onPostExecute(Bitmap param) {
            imageBitmap.setImageBitmap(param);
            imageBitmap.setVisibility(View.VISIBLE);
        }
    }
}
