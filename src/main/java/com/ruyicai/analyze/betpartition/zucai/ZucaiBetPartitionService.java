package com.ruyicai.analyze.betpartition.zucai;

import java.util.List;

import javax.annotation.Resource;

import net.rubyeye.xmemcached.MemcachedClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruyicai.analyze.betpartition.LotTypeBetPartition;
import com.ruyicai.analyze.domain.StatisticsData;
import com.ruyicai.analyze.domain.StatisticsPK;
import com.ruyicai.analyze.domain.Tlot;
import com.ruyicai.analyze.service.StatisticsDataService;
import com.ruyicai.analyze.service.TlotService;

@Service("zucaiBetPartitionService")
public class ZucaiBetPartitionService implements LotTypeBetPartition{

	private static Logger logger = LoggerFactory.getLogger(ZucaiBetPartitionService.class);
	
	@Resource
	private StatisticsDataService statisticsDataService;
	
	@Resource
	private TlotService tlotService;
	
	@Autowired
	MemcachedClient memcachedClient;
	
	
	@Override
	public void onSuccess(Tlot tlot) {
		logger.info("BetPartition start :"+tlot.getLotno()+" "+tlot.getBatchcode());
		StatisticsData data_betpartition = statisticsDataService.findByLotnoAndBatchcodeAndKeyThroughCache(tlot.getLotno(), tlot.getBatchcode(), BetPartitionData.KEY);
		BetPartitionData betpartition;
		if(data_betpartition==null) {
			betpartition = new BetPartitionData();
			betpartition = betpartition.getEmptyBetPartionData(tlot.getLotno());
		}else {
			betpartition = BetPartitionData.fromJsonToBetPartitionData(data_betpartition.getValue());
		}
		betpartition.onSuccess(tlot);
		
		StatisticsData data_betpartition_1 = new StatisticsData(new StatisticsPK(BetPartitionData.KEY, tlot.getLotno(), tlot.getBatchcode()));
		data_betpartition_1.setValue(betpartition.toJson());
		data_betpartition_1.setValueString(betpartition.toString(tlot.getLotno()));
		statisticsDataService.merge(data_betpartition_1);
		
		logger.info("BetPartition end :"+tlot.getLotno()+" "+tlot.getBatchcode());
		
		try {
			logger.info("BetPartition try to put to cache:"+tlot.getLotno()+" "+tlot.getBatchcode());
			
			memcachedClient.set(tlot.getLotno() + BetPartitionData.KEY +"_"+tlot.getBatchcode(), 864000, data_betpartition_1);
			
			logger.info("onPrize put to cache success:"+tlot.getLotno()+" "+tlot.getBatchcode());
		}catch (Exception e) {
			logger.debug("onPrize put cache exception:"+tlot.getLotno()+" "+tlot.getBatchcode(), e);
		}

	}

	@Override
	public void refresh(String batchcode, String lotno) {
		logger.info("refresh betpartiton start :lotno="+lotno+" batchcode="+batchcode);
		List<Tlot> lots = tlotService.find(lotno, batchcode, 0, 200);
		BetPartitionData betPartitionData = new BetPartitionData();
		betPartitionData = betPartitionData.getEmptyBetPartionData(lotno);
		
		for(Tlot tlot:lots) {
			betPartitionData.onSuccess(tlot);
		}
		
		StatisticsData statisticsData = new StatisticsData(new StatisticsPK(BetPartitionData.KEY, lotno, batchcode));
		statisticsData.setValue(betPartitionData.toJson());
		statisticsData.setValueString(betPartitionData.toString(lotno));
		
		statisticsDataService.merge(statisticsData);
		
		logger.info("refresh betpartiton end :lotno="+lotno+" batchcode="+batchcode);
		
		
	}

}
