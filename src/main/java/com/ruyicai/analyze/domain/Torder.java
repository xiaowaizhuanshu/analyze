package com.ruyicai.analyze.domain;

import java.math.BigDecimal;

public class Torder {

	private String lotno;

	private String batchcode;

	private String orderInfo;

	private BigDecimal lotmulti;
	
	private BigDecimal prizeamt;
	
	private String playtype;

	private String userno;
	
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

	public String getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	}

	public BigDecimal getLotmulti() {
		return lotmulti;
	}

	public void setLotmulti(BigDecimal lotmulti) {
		this.lotmulti = lotmulti;
	}

	public BigDecimal getPrizeamt() {
		return prizeamt;
	}

	public void setPrizeamt(BigDecimal prizeamt) {
		this.prizeamt = prizeamt;
	}

	public String getPlaytype() {
		return playtype;
	}

	public void setPlaytype(String playtype) {
		this.playtype = playtype;
	}
	

	public String getUserno() {
		return userno;
	}

	public void setUserno(String userno) {
		this.userno = userno;
	}

	@Override
	public String toString() {
		return "Torder [lotno=" + lotno + ", batchcode=" + batchcode
				+ ", orderInfo=" + orderInfo + ", lotmulti=" + lotmulti
				+ ", prizeamt=" + prizeamt + ", playtype=" + playtype
				+ ", userno=" + userno + "]";
	}

}
