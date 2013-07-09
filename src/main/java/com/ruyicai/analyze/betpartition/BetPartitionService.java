package com.ruyicai.analyze.betpartition;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ruyicai.analyze.betpartition.zucai.ZucaiBetPartitionService;
import com.ruyicai.analyze.domain.Tlot;
import com.ruyicai.analyze.service.TlotService;

@Service("betPartitionService")
public class BetPartitionService {

	private static Logger logger = LoggerFactory
			.getLogger(BetPartitionService.class);

	@Resource
	private ZucaiBetPartitionService zucaiBetPartitionService;
	
	@Resource
	private TlotService tlotService;

	
	public void onSuccess(Tlot tlot) {
		if ("T01003".equals(tlot.getLotno())
				|| "T01004".equals(tlot.getLotno())
				|| "T01005".equals(tlot.getLotno())
				|| "T01006".equals(tlot.getLotno())) {
			zucaiBetPartitionService.onSuccess(tlot);
			tlotService.merge(tlot);
		}
	}
	
	
	public void refresh(String lotno,String batchcode) {
		zucaiBetPartitionService.refresh(batchcode, lotno);
	}

}
