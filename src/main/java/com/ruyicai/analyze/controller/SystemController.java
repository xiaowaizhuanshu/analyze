package com.ruyicai.analyze.controller;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruyicai.analyze.betpartition.BetPartitionService;
import com.ruyicai.analyze.consts.ErrorCode;
import com.ruyicai.analyze.domain.Tlot;
import com.ruyicai.analyze.domain.WinData;
import com.ruyicai.analyze.jms.TlotQueueProducer;
import com.ruyicai.analyze.jms.WindataQueueProducer;
import com.ruyicai.analyze.statisticswin.StatisticswinService;

@Controller
@RequestMapping("/system")
public class SystemController {

	private static Logger logger = LoggerFactory.getLogger(SystemController.class);

	
	@Resource
	private TlotQueueProducer prizeInfoQueueProducer;
	
	@Resource
	private WindataQueueProducer windataQueueProducer;
	
	@Resource
	private BetPartitionService betPartitionService;
	
	@Resource
	private StatisticswinService statisticswinService;
	
	@RequestMapping(value="/goMissvalue")
	public String goMissvalue() {
		return "missvalue/main";
	}
	
	
	@RequestMapping(value="/goBetPartition")
	public String goBetPartition() {
		return "betpartition/main";
	}
	
	
	
	
	
	
	@RequestMapping(value="/refreshBetpartition",method=RequestMethod.POST)
	public @ResponseBody
	ResponseData refreshBetpartition(@RequestParam("lotno") String lotno,@RequestParam("batchcode") String batchcode) {
		ResponseData rd = new ResponseData();
		try{
			rd.setErrorCode(ErrorCode.OK.value);
			betPartitionService.refresh(lotno, batchcode);
		}catch(Exception e) {
			rd.setErrorCode(ErrorCode.ERROR.value);
			logger.info("SystemController err", e);
		}
		return rd;
	}
	
	
	@RequestMapping(value="/refreshStatisticsWinning",method=RequestMethod.POST)
	public @ResponseBody
	ResponseData refreshBetpartition(@RequestParam("lotno") String lotno) {
		ResponseData rd = new ResponseData();
		try{
			rd.setErrorCode(ErrorCode.OK.value);
			statisticswinService.refresh(lotno);
		}catch(Exception e) {
			rd.setErrorCode(ErrorCode.ERROR.value);
			logger.info("SystemController err", e);
		}
		return rd;
	}
	
	
	
	
	
	
	@RequestMapping(value="/sendtlot",method=RequestMethod.POST)
	public @ResponseBody
	ResponseData sendtlot(@RequestParam("flowno") int flowno,@RequestParam("lotno") String lotno,
			@RequestParam("batchcode") String batchcode,@RequestParam("betcode") String betcode,
			@RequestParam("lotmulti") String lotmulti) {
		ResponseData rd = new ResponseData();
		try{
			rd.setErrorCode(ErrorCode.OK.value);
			Tlot tlot = new Tlot();
			tlot.setBatchcode(batchcode);
			tlot.setBetcode(betcode);
			tlot.setFlowno(flowno);
			tlot.setLotmulti(new BigDecimal(lotmulti));
			tlot.setLotno(lotno);
			System.out.println(tlot.toJson());
			prizeInfoQueueProducer.send(tlot);
			
		}catch(Exception e) {
			rd.setErrorCode(ErrorCode.ERROR.value);
			logger.info("SystemController err", e);
		}
		return rd;
	}
		
		
		
		@RequestMapping(value="/sendwindata",method=RequestMethod.POST)
		public @ResponseBody
		ResponseData sendwindata(@RequestParam("userno") String userno,@RequestParam("lotno") String lotno,
				@RequestParam("code") String code,@RequestParam("amount") String amount) {
			ResponseData rd = new ResponseData();
			try{
				rd.setErrorCode(ErrorCode.OK.value);
				WinData win = new WinData();
				win.setUserno(userno);
				win.setLotno(lotno);
				win.setCode(code);
				win.setAmount(new BigDecimal(amount));
				System.out.println(win.toJson());
				windataQueueProducer.send(win);
				
			}catch(Exception e) {
				rd.setErrorCode(ErrorCode.ERROR.value);
				logger.info("SystemController err", e);
			}
			return rd;
	}
	

}
