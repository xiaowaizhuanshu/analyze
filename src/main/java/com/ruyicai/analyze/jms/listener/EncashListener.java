package com.ruyicai.analyze.jms.listener;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ruyicai.analyze.domain.WinData;
import com.ruyicai.analyze.service.WindataService;
import com.ruyicai.analyze.statisticswin.StatisticswinService;
import com.ruyicai.analyze.util.HttpTookit;

import flexjson.JSONDeserializer;

@Service("encashListener")
public class EncashListener implements MessageListener{
	private static Logger logger = LoggerFactory.getLogger(EncashListener.class);
	private static final String LOTTERY = ResourceBundle.getBundle("url").getString("url.lottery");
	
	@Resource
	private StatisticswinService statisticswinService;
	
	@Resource
	private WindataService windataService;

	@Override
	public void onMessage(Message message) {
		Map<String, Object> messageMap = null;
		WinData windata;
		try {
			logger.info("reciveQueue-Encash");
			JSONDeserializer<HashMap<String, Object>> mess = new JSONDeserializer<HashMap<String, Object>>();
			messageMap = mess.deserialize(((ActiveMQTextMessage)message).getText());
			
			String lotno = (String) messageMap.get("lotno");
			
//			Torder torder = HttpTookit.findtorder((String)messageMap.get("orderid"));
			
			if("T01007".equals(lotno)||"T01010".equals(lotno)||"T01012".equals(lotno)||"F47107".equals(lotno)
					||"T01014".equals(lotno)
					||"T01015".equals(lotno)) {
				
				String userinfo = HttpTookit.doGet(LOTTERY+"/tuserinfoes/?json&find=ByUserno&userno="+(String)messageMap.get("userno"), "");
				JSONDeserializer<HashMap> json = new JSONDeserializer<HashMap>();
				Map map = json.deserialize(userinfo);
				if(!"0".equals(map.get("errorCode"))) {
					logger.info("get userinfo err");
					return ;
				}
				Map user = (HashMap)map.get("value");
				windata = new WinData();
				windata.setEmail((String)user.get("email"));
				windata.setMobildid((String)user.get("mobileid"));
				windata.setNickName((String)user.get("nickname"));
				windata.setData(new Date());
				
				windata.setLotno(lotno);
				windata.setAmount(new BigDecimal((Integer)messageMap.get("orderprizeamt")));
				windata.setCode("");
				windata.setPlaytype((String) messageMap.get("playtype"));
				windata.setUserno((String) messageMap.get("userno"));
				windataService.merge(windata);
				statisticswinService.enCash(windata);

			}else {
				logger.info("Encash is not support");
			}
			
		} catch (Exception e) {
			logger.info("Encash err",e);
		} finally {
			messageMap.clear();
			windata = null;
		}
		
	}
	
}
