package com.example.helloworld.citylist;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ListViewLoader extends AsyncTask<String,Void,List<City>>{

    public final ProgressDialog dialog;
    Context context;
    SimpleAdapter adapter;

    public ListViewLoader(Context context,SimpleAdapter adapter) {
        this.context=context;
        dialog = new ProgressDialog(this.context);
        this.adapter=adapter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setMessage("Parsing Cities");
        dialog.show();
    }

    @Override
    protected void onPostExecute(List<City> cities) {
        super.onPostExecute(cities);
        dialog.dismiss();
        adapter.setItemList(cities);
        adapter.notifyDataSetChanged();

    }

    @Override
    protected List<City> doInBackground(String... params) {
        List<City> result=new ArrayList<>();
        try{
            InputStream is = context.getAssets().open("cities.json");
            byte[] b = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ( is.read(b) != -1)
                baos.write(b);
            String JSONResp = new String(baos.toByteArray());
            JSONArray arr = new JSONArray(JSONResp);
            System.out.println("Array is "+arr.toString());
            for (int i=0; i < arr.length(); i++) {
                result.add(convertDevice(arr.getJSONObject(i)));
            }
            return result;

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private City convertDevice(JSONObject obj) throws JSONException {
        String id = obj.getString("id");
        String city = obj.getString("name");
        String state = obj.getString("state");

        return new City(id,city,state);
    }
}
