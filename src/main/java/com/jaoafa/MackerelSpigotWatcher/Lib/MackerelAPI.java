package com.jaoafa.MackerelSpigotWatcher.Lib;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MackerelAPI {
	private String baseUrl = "https://mackerel.io/api/v0/";
	private String apikey;
	private String hostId;

	public MackerelAPI(String apikey, String hostId) {
		this.apikey = apikey;
		this.hostId = hostId;
	}

	public JSONObject getHostInfomation() throws IOException {
		String url = baseUrl + "hosts/" + hostId;
		OkHttpClient okclient = new OkHttpClient();
		Request request = new Request.Builder()
				.url(url)
				.header("X-Api-Key", apikey)
				.header("Content-Type", "application/json")
				.build();
		Response response = okclient.newCall(request).execute();
		String result = response.body().string();
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
			final RequestBody requestBody = RequestBody.create(array.toString(), mediaTypeJson);

			OkHttpClient okclient = new OkHttpClient();
			Request request = new Request.Builder()
					.url(url)
					.post(requestBody)
					.header("X-Api-Key", apikey)
					.header("Content-Type", "application/json")
					.build();
			Response response = okclient.newCall(request).execute();
			int code = response.code();
			if (code != 200) {
				System.out.println(response.body().string());
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
		MackerelAPI mackerelAPI;
		String hostId;
		String name;
		double value;
		long time;

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

		public String getHostId() {
			return hostId;
		}

		public String getName() {
			return name;
		}

		public double getValue() {
			return value;
		}

		public long getTime() {
			return time;
		}
	}
}
