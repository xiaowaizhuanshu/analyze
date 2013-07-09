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

import com.ruyicai.analyze.domain.Tlot;

@Service("myQueueProducer")
public class TlotQueueProducer {

	@Resource
	private JmsTemplate jmsTemplate;
	
	@Resource(name="zcBetSuccessQueue")
	private Destination destination;

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
	
	public void send(final Tlot tlot){
		this.jmsTemplate.send(destination,new MessageCreator() {   
            public Message createMessage(Session session) throws JMSException {   

            	TextMessage message = session.createTextMessage();
            	message.setText(tlot.toJson());
                
                return (Message) message;   
            }   
        });
	}
	
}
