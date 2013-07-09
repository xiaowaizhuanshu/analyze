package com.ruyicai.analyze.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Entity
@Table(name = "windata")
public class WinData {

	@Id
	@GeneratedValue
	private int id;
	private String nickName;
	private String mobildid;
	private String email;
	private BigDecimal amount;
	private String lotno;
	private String code;
	private String userno;
	private String playtype;
	private Date data;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getMobildid() {
		return mobildid;
	}

	public void setMobildid(String mobildid) {
		this.mobildid = mobildid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getLotno() {
		return lotno;
	}

	public void setLotno(String lotno) {
		this.lotno = lotno;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUserno() {
		return userno;
	}

	public void setUserno(String userno) {
		this.userno = userno;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
	public String getPlaytype() {
		return playtype;
	}

	public void setPlaytype(String playtype) {
		this.playtype = playtype;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").deepSerialize(this);
	}

	public static WinData fromJsonToPrizeInfo(String json) {
		return new JSONDeserializer<WinData>().use(null, WinData.class)
				.deserialize(json);
	}

}
