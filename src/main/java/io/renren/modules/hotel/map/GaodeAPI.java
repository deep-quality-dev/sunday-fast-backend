package io.renren.modules.hotel.map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.hutool.http.HttpUtil;

/**
 * 搞得地图API
 * 
 * @author taoz
 *
 */
public class GaodeAPI {
	public static String key = "61da46f8202196bd6a794a0fd70d8a9b";

	// 22.544269,114.051819 114.05926,22.5429819
	public static float getDistance(String startLonLat, String endLonLat) {
		// 返回起始地startAddr与目的地endAddr之间的距离，单位：米
		Long result = 0L;
		String queryUrl = "http://restapi.amap.com/v3/distance?key=" + key + "&origins=" + startLonLat + "&destination=" + endLonLat;
		String queryResult = HttpUtil.get(queryUrl);
		JSONObject jo = JSON.parseObject(queryResult);
		JSONArray ja = jo.getJSONArray("results");
		Object obj = ja.getJSONObject(0).get("distance");
		if (obj == null) {
			return result;
		}
		result = Long.parseLong(obj.toString());
		return meter2Km(result);
	}

	public static float meter2Km(Long meter) {
		float bb4 = meter;// 长度单位是1051米。。
		float cc = bb4 / 100; // 得到10.51==
		int d = Math.round(cc);// 四舍五入是11
		float e = d / (float) 10;// 把10 也强转为float型的，再让10除以它==

		return e;
	}

	public static void main(String[] args) {
		// 22.544269,114.051819 114.05926,22.5429819
		String startLonLat = "113.849945,22.787577";
		String endLonLat = "114.216614,22.572170";
		System.out.println(GaodeAPI.getDistance(startLonLat, endLonLat) + " 公里");// 1.1公里
	}
}
