package com.ruyicai.analyze.statisticswin.latest;

import java.util.List;

import javax.annotation.Resource;

import net.rubyeye.xmemcached.MemcachedClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruyicai.analyze.domain.StatisticsData;
import com.ruyicai.analyze.domain.StatisticsPK;
import com.ruyicai.analyze.domain.WinData;
import com.ruyicai.analyze.service.StatisticsDataService;
import com.ruyicai.analyze.service.WindataService;
import com.ruyicai.analyze.statisticswin.StaTypeStatisticswin;

@Service("winLatestService")
public class WinLatestService implements StaTypeStatisticswin {

	private static Logger logger = LoggerFactory.getLogger(WinLatestService.class);
	
	@Resource
	private StatisticsDataService statisticsDataService;
	
	@Resource
	private WindataService winDataService;
	
	@Autowired
	MemcachedClient memcachedClient;
	
	@Override
	public void enCash(WinData windata) {
		StatisticsData sdate = statisticsDataService.findByLotnoAndBatchcodeAndKeyThroughCache(windata.getLotno(), "all", WinLatestData.KEY);
		WinLatestData latestdata;
		if(sdate==null) {
			sdate = new StatisticsData(new StatisticsPK(WinLatestData.KEY, windata.getLotno(), "all"));
			latestdata = new WinLatestData();
		}else {
			latestdata = WinLatestData.fromJsonToWinLatestData(sdate.getValue());
		}
		
		latestdata.enCash(windata);
		sdate.setValue(latestdata.toJson());
		statisticsDataService.merge(sdate);
		
		try {
			logger.info("encash try to put to cache:"+windata.getLotno());
			
			memcachedClient.set(windata.getLotno() + WinLatestData.KEY +"_all", 864000, sdate);
			
			logger.info("encash put to cache success:"+windata.getLotno());
		}catch (Exception e) {
			logger.debug("encash put cache exception:"+windata.getLotno(), e);
		}

	}

	@Override
	public void refresh(String lotno) {
		
		logger.info("refresh encash start:"+lotno);
		StatisticsData sdate = new StatisticsData(new StatisticsPK(WinLatestData.KEY, lotno, "all"));
		List<WinData> list = winDataService.find(lotno,0, 10);
		WinLatestData latestData = new WinLatestData();
		latestData.setList(list);
		sdate.setValue(latestData.toJson());
		statisticsDataService.merge(sdate);
		logger.info("refresh encash end:"+lotno);

	}

}
