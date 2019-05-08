package ru.sberbank.converter.data.network;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import ru.sberbank.converter.environment.Logger;

public class HttpClient {
    private LoadCallback callback;
    private DownloadTask downloadTask;

    void getContentString(String urlStr, LoadCallback callback) {
        this.callback = callback;
        downloadTask = new DownloadTask();
        downloadTask.execute(urlStr);
    }

    void cancelDownload() {
        callback = null;
        if (downloadTask != null) {
            downloadTask.cancel(true);
            downloadTask = null;
        }
    }

    public interface LoadCallback {
        void onLoadSuccess(String result);

        void onLoadError(Exception exception);
    }

    @SuppressLint("StaticFieldLeak")
    private class DownloadTask extends AsyncTask<String, Void, DownloadTask.Result> {
        @Override
        protected void onPostExecute(@NonNull Result result) {
            super.onPostExecute(result);
            if (result.response != null) {
                callback.onLoadSuccess(result.response);
            } else {
                callback.onLoadError(result.exception);
            }
        }

        @Override
        @NonNull
        protected DownloadTask.Result doInBackground(String... urls) {
            if (!isCancelled() && urls != null && urls.length == 1) {
                String urlString = urls[0];
                try {
                    URL url = new URL(urlString);
                    String resultString = downloadUrl(url);
                    if (resultString != null) {
                        return new Result(resultString);
                    } else {
                        return new Result(new IOException("Received empty response"));
                    }
                } catch (Exception e) {
                    Logger.printStackTrace(e);
                    return new Result(e);
                }
            }
            return new Result(new IllegalArgumentException(
                    "Wrong params for Download task: " + Arrays.toString(urls)));
        }

        private String downloadUrl(URL url) throws IOException {
            InputStream stream = null;
            HttpURLConnection connection = null;
            String result = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(3000);
                connection.setConnectTimeout(3000);
                connection.setRequestMethod("GET");
                connection.connect();

                int responseCode = connection.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    throw new IOException("HTTP error code: " + responseCode);
                }

                stream = connection.getInputStream();
                if (stream != null) {
                    result = readStream(stream);
                }
            } finally {
                if (stream != null) {
                    stream.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return result;
        }

        private String readStream(InputStream in) {
            BufferedReader reader = null;
            StringBuilder response = new StringBuilder();
            try {
                reader = new BufferedReader(new InputStreamReader(in, "windows-1251"));
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return response.toString();
        }

        class Result {
            String response;
            Exception exception;

            Result(String response) {
                this.response = response;
            }

            Result(Exception exception) {
                this.exception = exception;
            }
        }
    }
}