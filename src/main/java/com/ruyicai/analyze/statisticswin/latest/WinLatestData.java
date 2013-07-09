package com.ruyicai.analyze.statisticswin.latest;

import java.util.ArrayList;
import java.util.List;

import com.ruyicai.analyze.domain.WinData;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

public class WinLatestData {

	public static final String KEY = "LatestWin";
	
	List<WinData> list = new ArrayList<WinData>();

	public List<WinData> getList() {
		return list;
	}

	public void setList(List<WinData> list) {
		this.list = list;
	}
	
	
//	public static String toJsonArray(Collection<WinLatestData> collection) {
//        return new JSONSerializer().exclude("*.class").serialize(collection);
//    }
//    
//    public static Collection<WinLatestData> fromJsonArrayToWinLatestData(String json) {
//        return new JSONDeserializer<List<WinLatestData>>().use(null, ArrayList.class).use("values", WinLatestData.class).deserialize(json);
//    }
	
	public String toJson() {
		return new JSONSerializer().exclude("*.class").deepSerialize(this);
	}
	
	public static WinLatestData fromJsonToWinLatestData(String json) {
		return (WinLatestData) (new JSONDeserializer())
				.use("list.values", WinData.class).deserialize(json,
						WinLatestData.class);
	}
	
	
	public void enCash(WinData windata) {
		if(list.isEmpty()||list.size()<10) {
			list.add(0,windata);
		}else {
			list.remove(list.size()-1);
			list.add(0,windata);
		}

	}
	

	
	
	
	
	
}
