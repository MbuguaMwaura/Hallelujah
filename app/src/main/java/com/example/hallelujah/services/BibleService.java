package com.example.hallelujah.services;

import com.example.hallelujah.Constants;
import com.example.hallelujah.models.BibleVerse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BibleService {

    public static void findVerse(String book, String chapter,String verseFrom, String verseTo, Callback callback){
        OkHttpClient client = new OkHttpClient.Builder().build();
        HttpUrl.Builder builder = HttpUrl.parse(Constants.API_BASE_URL).newBuilder();
        builder.addQueryParameter("Book",book);
        builder.addQueryParameter("chapter",chapter);
        builder.addQueryParameter("VerseFrom",verseFrom);
        builder.addQueryParameter("VerseTo",verseTo);

        String url = builder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .header(Constants.RAPID_API_HEADER_KEY,Constants.RAPID_KEY)
                .header(Constants.RAPID_HOST_KEY,Constants.RAPID_BIBLE_HOST)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<BibleVerse> processResults(Response response){
        ArrayList<BibleVerse> bibleVerses = new ArrayList<>();
        try{
            String jsonData = response.body().string();
            JSONObject jsonObject = new JSONObject(jsonData);
            String book = jsonObject.getString("Book");
            String chapter = jsonObject.getString("Chapter");
            String verseFrom = jsonObject.getString("VerseFrom");
            String verseTo = jsonObject.getString("VerseTo");
            String output = jsonObject.getString("Output");

            BibleVerse bibleVerse = new BibleVerse(book,chapter,verseFrom,verseTo,output);
            bibleVerses.add(bibleVerse);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bibleVerses;
    }
}
