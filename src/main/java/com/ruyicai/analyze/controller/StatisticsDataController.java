package com.ruyicai.analyze.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruyicai.analyze.betpartition.zucai.BetPartitionData;
import com.ruyicai.analyze.consts.ErrorCode;
import com.ruyicai.analyze.domain.StatisticsData;
import com.ruyicai.analyze.service.StatisticsDataService;
import com.ruyicai.analyze.statisticswin.latest.WinLatestData;

@Controller
@RequestMapping("/select")
public class StatisticsDataController {

	private static Logger logger = LoggerFactory.getLogger(StatisticsDataController.class);
	
	@Resource
	private StatisticsDataService statisticsDataService;
	
	
	
	@RequestMapping(value = "/betpartition")
	public @ResponseBody
	ResponseData selectBetpartition(@RequestParam("lotno") String lotno,
			@RequestParam("batchcode") String batchcode) {
		logger.info("Get betpartition:lotno="+lotno+" batchcode="+batchcode);
		ResponseData rd = new ResponseData();
		
		try{
			StatisticsData sdata = statisticsDataService.findByLotnoAndBatchcodeAndKeyThroughCache(lotno, batchcode, "BetPartition");
			if(sdata==null) {
				sdata = new StatisticsData();
				BetPartitionData bdata = new BetPartitionData();
				bdata = bdata.getEmptyBetPartionData(lotno);
				sdata.setValueString(bdata.toString(lotno));
			}
			rd.setErrorCode(ErrorCode.OK.value);
			rd.setValue(sdata.getValueString());
		}catch(Exception e) {
			rd.setErrorCode(ErrorCode.ERROR.value);
			logger.info("Get betpartition err",e);
		}
		return rd;
		
	}
	
	
	
	@RequestMapping(value = "/statiswinning")
	public @ResponseBody
	ResponseData selectStatiswinning(@RequestParam("lotno") String lotno) {
		logger.info("Get statiswinning:lotno="+lotno);
		ResponseData rd = new ResponseData();

		Object[] list = new Object[1];
		try{
			StatisticsData sdata = statisticsDataService.findByLotnoAndBatchcodeAndKeyThroughCache(lotno, "all", WinLatestData.KEY);
			WinLatestData win = WinLatestData.fromJsonToWinLatestData(sdata.getValue());
			String[] wins = new String[win.getList().size()];
			for(int i=0;i<wins.length;i++) {
				wins[i] = win.getList().get(i).toJson();
			}
			list[0]=wins;
			rd.setErrorCode(ErrorCode.OK.value);
			rd.setValue(list);
		}catch(Exception e) {
			logger.info("Get statiswinning err",e);
			rd.setErrorCode(ErrorCode.ERROR.value);
		}
		return rd;
	}
	
	
	
}
