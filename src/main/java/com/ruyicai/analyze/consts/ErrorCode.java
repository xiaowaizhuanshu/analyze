package com.ruyicai.analyze.consts;

public enum ErrorCode {
	
	OK("0", "正确"), ERROR("500", "错误");
	
	
	public String value;
	
	public String memo;
	
	ErrorCode(String value, String memo) {
		
		this.value = value;
		this.memo = memo;
	}
}
