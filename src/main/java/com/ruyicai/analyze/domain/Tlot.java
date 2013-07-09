package com.ruyicai.analyze.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Entity
@Table(name="tlot")
public class Tlot implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue
	private int flowno;
	
	private String lotno;
	
	private String batchcode;
	
	private String betcode;
	
	private BigDecimal lotmulti;

	public int getFlowno() {
		return flowno;
	}

	public void setFlowno(int flowno) {
		this.flowno = flowno;
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

	public String getBetcode() {
		return betcode;
	}

	public void setBetcode(String betcode) {
		this.betcode = betcode;
	}

	public BigDecimal getLotmulti() {
		return lotmulti;
	}

	public void setLotmulti(BigDecimal lotmulti) {
		this.lotmulti = lotmulti;
	}
	
	public String toJson() {
        return new JSONSerializer().exclude("*.class").deepSerialize(this);
    }
	
	public static Tlot fromJsonToPrizeInfo(String json) {
        return new JSONDeserializer<Tlot>().use(null, Tlot.class)
        			.deserialize(json);
    }
	
}
