package com.ruyicai.analyze.jms;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.ruyicai.analyze.domain.WinData;

@Service("windataProducer")
public class WindataQueueProducer {

	@Resource
	private JmsTemplate jmsTemplate;
	
	@Resource(name="orderEncash")
	private Destination destination;

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
	
	public void send(final WinData win){
		this.jmsTemplate.send(destination,new MessageCreator() {   
            public Message createMessage(Session session) throws JMSException {   

            	TextMessage message = session.createTextMessage();
            	message.setText(win.toJson());
                
                return (Message) message;   
            }   
        });
	}
	
}
