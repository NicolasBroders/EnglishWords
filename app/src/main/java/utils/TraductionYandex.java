package utils;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class TraductionYandex extends AsyncTask<String, Void, String> {
        String resultString;

    @Override
    protected String doInBackground(String... params) {
            //String variables
            String textToBeTranslated = null;
            try {
                    textToBeTranslated = URLEncoder.encode(params[0], "utf-8");
            } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
            }
            String languagePair = params[1];

            String jsonString;

            try {
            //Set up the translation call URL
            String yandexKey = "trnsl.1.1.20170823T130435Z.79a583874abfc8ff.61e23593359fdc92452e69a3d5ec05347fc4180b";
            String yandexUrl = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=" + yandexKey
            + "&text=" + textToBeTranslated+"&lang=" + languagePair;
            URL yandexTranslateURL = new URL(yandexUrl);

            //Set Http Conncection, Input Stream, and Buffered Reader
            HttpURLConnection httpJsonConnection = (HttpURLConnection) yandexTranslateURL.openConnection();
            InputStream inputStream = httpJsonConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            //Set string builder and insert retrieved JSON result into it
            StringBuilder jsonStringBuilder = new StringBuilder();
            while ((jsonString = bufferedReader.readLine()) != null) {
            jsonStringBuilder.append(jsonString + "\n");
            }

            //Close and disconnect
            try {
            bufferedReader.close();
            } catch (IOException e) {
            e.printStackTrace();
            }
            inputStream.close();
            httpJsonConnection.disconnect();

            //Making result human readable
            resultString = jsonStringBuilder.toString().trim();

            //Getting the characters between [ and ]

            resultString = resultString.substring(resultString.indexOf('[')+1);
            resultString = resultString.substring(0,resultString.indexOf("]"));

            //Getting the characters between " and "
            resultString = resultString.substring(resultString.indexOf("text\"")+1);
            resultString = resultString.replaceAll("\"","");
            Log.d("Translation Result:", resultString);
            return resultString;

            } catch (MalformedURLException e) {
            e.printStackTrace();
            } catch (IOException e) {
            e.printStackTrace();
            }
            return null;
    }

    @Override
    protected void onPreExecute() {
            super.onPreExecute();
    }



    @Override
    protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
    }
}