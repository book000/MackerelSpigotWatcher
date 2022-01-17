package com.tomacheese.mackerelspigotwatcher.lib;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

public class MackerelAPI {
    private final String baseUrl = "https://mackerel.io/api/v0/";
    private final String apikey;
    private final String hostId;

    public MackerelAPI(String apikey, String hostId) {
        this.apikey = apikey;
        this.hostId = hostId;
    }

    public JSONObject getHostInformation() throws IOException {
        String url = baseUrl + "hosts/" + hostId;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
            .url(url)
            .header("X-Api-Key", apikey)
            .header("Content-Type", "application/json")
            .build();
        Response response = client.newCall(request).execute();
        String result = Objects.requireNonNull(response.body()).string();
        JSONObject json = new JSONObject(result);
        response.close();
        if (!json.has("host")) {
            return null;
        }
        return json;
    }

    public boolean postMetrics(Iterable<? extends Metric> metrics) {
        try {
            String url = baseUrl + "tsdb";
            JSONArray array = new JSONArray();
            for (Metric metric : metrics) {
                JSONObject obj = new JSONObject();
                obj.put("hostId", metric.hostId);
                obj.put("name", metric.name);
                obj.put("time", metric.time);
                obj.put("value", metric.value);
                array.put(obj);
            }
            final MediaType mediaTypeJson = MediaType.parse("application/json; charset=utf-8");
            RequestBody requestBody = RequestBody.create(array.toString(), mediaTypeJson);

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .header("X-Api-Key", apikey)
                .header("Content-Type", "application/json")
                .build();
            Response response = client.newCall(request).execute();
            int code = response.code();
            if (code != 200) {
                System.out.println(Objects.requireNonNull(response.body()).string());
            }
            response.close();
            return code == 200;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getHostId() {
        return hostId;
    }

    public static class Metric {
        final MackerelAPI mackerelAPI;
        final String hostId;
        final String name;
        final double value;
        final long time;

        public Metric(MackerelAPI mackerelAPI, String name, String value, long time) {
            this.mackerelAPI = mackerelAPI;
            this.hostId = mackerelAPI.getHostId();
            this.name = name;
            this.value = Double.parseDouble(value);
            this.time = time;
        }

        public Metric(MackerelAPI mackerelAPI, String name, long value, long time) {
            this.mackerelAPI = mackerelAPI;
            this.hostId = mackerelAPI.getHostId();
            this.name = name;
            this.value = value;
            this.time = time;
        }

        public Metric(MackerelAPI mackerelAPI, String name, int value, long time) {
            this.mackerelAPI = mackerelAPI;
            this.hostId = mackerelAPI.getHostId();
            this.name = name;
            this.value = value;
            this.time = time;
        }

        public Metric(MackerelAPI mackerelAPI, String name, double value, long time) {
            this.mackerelAPI = mackerelAPI;
            this.hostId = mackerelAPI.getHostId();
            this.name = name;
            this.value = value;
            this.time = time;
        }

        public String getName() {
            return name;
        }
    }
}
