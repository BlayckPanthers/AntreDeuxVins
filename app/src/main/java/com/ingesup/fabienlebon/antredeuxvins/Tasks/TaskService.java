package com.ingesup.fabienlebon.antredeuxvins.Tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ingesup.fabienlebon.antredeuxvins.Entities.User;
import com.ingesup.fabienlebon.antredeuxvins.Tools.EncryptPassword;
import com.loopj.android.http.HttpGet;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

/**
 * Created by fabienlebon on 06/04/2018.
 */

public class TaskService extends AsyncTask<String,Integer,String> {

    OnAsyncRequestComplete caller;
    Context context;
    String method = "GET";
    List<NameValuePair> parameters = null;
    ProgressDialog pDialog = null;

    // Three Constructors
    public TaskService(Activity a, String m, List<NameValuePair> p) {
        caller = (OnAsyncRequestComplete) a;
        context = a;
        method = m;
        parameters = p;
    }

    public TaskService(Activity a, String m) {
        caller = (OnAsyncRequestComplete) a;
        context = a;
        method = m;
    }

    public TaskService(Activity a) {
        caller = (OnAsyncRequestComplete) a;
        context = a;
    }

    public interface OnAsyncRequestComplete {
        public void asyncResponse(String response);
    }

    private String get(String address) {
        try {

            if (parameters != null) {

                String query = "";
                String EQ = "="; String AMP = "&";
                for (NameValuePair param : parameters) {
                    query += param.getName() + EQ + URLEncoder.encode(param.getValue()) + AMP;
                }

                if (query != "") {
                    address += "?" + query;
                }
            }

            HttpClient client = new DefaultHttpClient();
            HttpGet get= new HttpGet(address);

            HttpResponse response = client.execute(get);
            return stringifyResponse(response);

        } catch (ClientProtocolException e) {
        } catch (IOException e) {
        }

        return null;
    }

    private String post(String address) {
        try {

            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(address);

            post.setHeader("Accept",
                    "application/x-www-form-urlencoded");
            post.setHeader("Content-Type",
                    "application/x-www-form-urlencoded");

            if (parameters != null) {
                post.setEntity(new UrlEncodedFormEntity(parameters));
            }

            HttpResponse response = client.execute(post);
            return stringifyResponse(response);

        } catch (ClientProtocolException e) {
        } catch (IOException e) {
        }

        return null;
    }

    private String stringifyResponse(HttpResponse response) {
        BufferedReader in;
        try {
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuffer sb = new StringBuffer("");
            String line = "";
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            in.close();

            return sb.toString();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading data..");
        pDialog.show();
    }

    @Override
    protected void onPostExecute(String s) {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
        caller.asyncResponse(s);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(String s) {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
        caller.asyncResponse(s);
    }

    @Override
    protected String doInBackground(String... urls) {

        // get url pointing to entry point of API
        String address = urls[0].toString();
        if (method == "POST") {
            return post(address);
        }

        if (method == "GET") {
            return get(address);
        }

        return null;
    }
}
