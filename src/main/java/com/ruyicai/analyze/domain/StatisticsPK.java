package com.ruyicai.analyze.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class StatisticsPK implements Serializable{

	@Column
	private String name;
	
	@Column
	private String lotno;
	
	@Column
	private String batchcode;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLotno() {
		return lotno;
	}

	public void setLotno(String lotno) {
		this.lotno = lotno;
	}

	public String getBatchcode() {
		return batchcode;
	}

	public void setBatchcode(String batchcode) {
		this.batchcode = batchcode;
	}

	public StatisticsPK(String name, String lotno, String batchcode) {
		super();
		this.name = name;
		this.lotno = lotno;
		this.batchcode = batchcode;
	}

	public StatisticsPK() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((batchcode == null) ? 0 : batchcode.hashCode());
		result = prime * result + ((lotno == null) ? 0 : lotno.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StatisticsPK other = (StatisticsPK) obj;
		if (batchcode == null) {
			if (other.batchcode != null)
				return false;
		} else if (!batchcode.equals(other.batchcode))
			return false;
		if (lotno == null) {
			if (other.lotno != null)
				return false;
		} else if (!lotno.equals(other.lotno))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
	
	
}
