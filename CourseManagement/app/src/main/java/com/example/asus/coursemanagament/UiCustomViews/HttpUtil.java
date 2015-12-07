package com.example.asus.coursemanagament.UiCustomViews;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class HttpUtil{
    public static void doPost(final String address,final Map<String,String> params,final HttpCallbackListener listener)throws IOException{
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection=null;
                try{
                    URL url=new URL(address);
                    connection=(HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);

                    String body = "";
                    if (params != null) {
                        boolean first = true;
                        for (String param : params.keySet()) {
                            if (first) {
                                first = false;
                            }
                            else {
                                body += "&";
                            }
                            String value = params.get(param);
                            body += URLEncoder.encode(param, "UTF-8") + "=";
                            body += URLEncoder.encode(value, "UTF-8");
                        }
                    }
                    DataOutputStream out=new DataOutputStream(connection.getOutputStream());
                    out.writeBytes(body);
                    out.flush();
                    out.close();

                    InputStream in = connection.getInputStream();
                    BufferedReader reader =new BufferedReader(new InputStreamReader(in));
                    final StringBuilder response=new StringBuilder();
                    String line;
                    while((line=reader.readLine())!=null){
                        response.append(line);
                    }
                    in.close();
                    listener.onFinish(response.toString());
                } catch (Exception e) {
                    if(listener!=null){
                        listener.onError(e);
                    }
                }
                finally{
                    if(connection!=null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
}