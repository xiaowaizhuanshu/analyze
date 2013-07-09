package com.ruyicai.analyze.betpartition;

import com.ruyicai.analyze.domain.Tlot;

public interface LotTypeBetPartition {

	public void onSuccess(Tlot tlot);
	
	public void refresh(String batchcode,String lotno);
}
