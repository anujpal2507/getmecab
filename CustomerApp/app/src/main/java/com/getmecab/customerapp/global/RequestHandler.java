package com.getmecab.customerapp.global;

import android.content.Context;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.GZIPInputStream;

/**
 * Created by anuj "email : anujpal2507@gmail.com" on 16/9/16.
 */
public class RequestHandler {
    public static DefaultHttpClient httpClient;

    public static HttpResponse makeRequest(Context context, String url, List<NameValuePair> params) {
        try {
            HttpResponse httpResponse = null;
            if (httpClient == null) {
                httpClient = new DefaultHttpClient();
                httpClient.setCookieStore(new BasicCookieStore());
            }
            RequestHandler.httpClient.setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(0, false));
            HttpParams httpParams = httpClient.getParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, 120 * 1000);
            HttpConnectionParams.setSoTimeout(httpParams, 180 * 1000);
            if (params != null) {
                String urlParams = URLEncodedUtils.format(params, HTTP.UTF_8);
                Log.d("TESTING","urlParams >>>"+urlParams);
                HttpGet httpGet = new HttpGet(url + "?" + urlParams);
                httpGet.addHeader("Content-Type","application/x-www-form-urlencoded");
                httpGet.setHeader("Accept", "application/json");
                httpGet.setHeader("Accept-Encoding", "gzip");
                httpGet.setHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_KEEP_ALIVE);
                Log.d("TESTING","urlParams httpGet >>>"+httpGet.getURI());
                httpResponse = httpClient.execute(httpGet);
            }
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity.getContentEncoding() != null && (httpEntity.getContentEncoding().toString().equalsIgnoreCase("gzip") || httpEntity.getContentEncoding().toString().contains("gzip"))) {
                httpResponse.setEntity(new GzipDecompressingEntity(httpResponse.getEntity()));
            }
            return httpResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    static class GzipDecompressingEntity extends HttpEntityWrapper {
        public GzipDecompressingEntity(final HttpEntity httpEntity) {
            super(httpEntity);
        }

        @Override
        public InputStream getContent() throws IOException, IllegalStateException {
            InputStream inputStream = wrappedEntity.getContent();
            return new GZIPInputStream(inputStream);
        }

        @Override
        public long getContentLength() {
            return -1;
        }
    }
}
