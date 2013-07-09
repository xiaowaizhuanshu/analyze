package com.ruyicai.analyze.jms.listener;

import java.util.Map;

import javax.annotation.Resource;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ruyicai.analyze.betpartition.BetPartitionService;
import com.ruyicai.analyze.domain.Tlot;
import com.ruyicai.analyze.domain.Torder;
import com.ruyicai.analyze.util.HttpTookit;

@Service("onZcBetSuccessListener")
public class OnZcBetSuccessListener implements MessageListener{
	private static Logger logger = LoggerFactory.getLogger(OnZcBetSuccessListener.class);
	@Resource
	private BetPartitionService betPartitionService;

	@Override
	public void onMessage(Message message) {
		
		Map<String, Object> map = null;
		
		try {
			
			
			map = ((ActiveMQMessage)message).getProperties();
			
			String lotno = (String) map.get("lotno");
			String orderid = (String) map.get("orderid");
			logger.info("reciveQueue-betpartition,lotno="+lotno);
			
			if("T01003".equals(lotno)||"T01004".equals(lotno)||"T01005".equals(lotno)||"T01006".equals(lotno)) {
				Torder torder = HttpTookit.findtorder(orderid);
				for(String oneCode:torder.getOrderInfo().split("!")) {
					Tlot tlot = new Tlot();
					tlot.setBatchcode(torder.getBatchcode());
					tlot.setLotmulti(torder.getLotmulti());
					tlot.setLotno(torder.getLotno());
					tlot.setBetcode(oneCode.split("_")[0]);
					betPartitionService.onSuccess(tlot);
				}
				
				
			}
//			text = (String)((ActiveMQTextMessage)message).getText();
//			tlot = Tlot.fromJsonToPrizeInfo(text);
//			if("T01003".equals(tlot.getLotno())||"T01004".equals(tlot.getLotno())||"T01005".equals(tlot.getLotno())||"T01006".equals(tlot.getLotno())) {
//				logger.info(tlot.getLotno()+" "+tlot.getBatchcode()+" "+tlot.getLotmulti()+" "+tlot.getBetcode());
//				betPartitionService.onSuccess(tlot);
//			}else {
//				logger.info("betpartition is not zucai");
//			}
			
		} catch (Exception e) {
			logger.info("OnSuccess err",e);
		} finally {
			map.clear();
		}
		
	}
	
}
