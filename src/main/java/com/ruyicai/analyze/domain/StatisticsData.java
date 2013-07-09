package com.ruyicai.analyze.domain;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Entity
@Table(name="tstatisticsdata")
@Cacheable(value=true)
public class StatisticsData implements Serializable{


	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private StatisticsPK id;
	
	private String value;
	
	private String valueString;
	
	
	public StatisticsPK getId() {
		return id;
	}

	public void setId(StatisticsPK id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValueString() {
		return valueString;
	}

	public void setValueString(String valueString) {
		this.valueString = valueString;
	}

	

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
    }
	
	public static StatisticsData fromJsonToStatisticsData(String json) {
		return new JSONDeserializer<StatisticsData>().use(null, StatisticsData.class)
							.deserialize(json);
    }

	

	public StatisticsData(StatisticsPK id) {
		super();
		this.id = id;
	}

	public StatisticsData() {
		super();
	}
	
	
	

	
	
}
