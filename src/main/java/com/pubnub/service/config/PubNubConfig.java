package com.pubnub.service.config;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.enums.PNStatusCategory;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;

@Configuration
@EnableConfigurationProperties(PubNubProperties.class)
public class PubNubConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(PubNubConfig.class);
	
	@Resource
	private PubNubProperties pubNubProperties;
	
	@Bean
	public PNConfiguration pnConfiguration(){
		PNConfiguration pnConfiguration = new PNConfiguration();
	    pnConfiguration.setSubscribeKey(pubNubProperties.getSubscribeKey());
	    pnConfiguration.setPublishKey(pubNubProperties.getPublishKey());
	    return pnConfiguration;
	}
	
	@Bean
	public PubNub pubNub(PNConfiguration pnConfiguration){
		PubNub pubnub = new PubNub(pnConfiguration);
		SubscribeCallback subscribeCallback = new SubscribeCallback() {
		    @Override
		    public void status(PubNub pubnub, PNStatus status) {
		        if (status.getCategory() == PNStatusCategory.PNUnexpectedDisconnectCategory) {
		            // internet got lost, do some magic and call reconnect when ready
		            pubnub.reconnect();
		        } else if (status.getCategory() == PNStatusCategory.PNTimeoutCategory) {
		            // do some magic and call reconnect when ready
		            pubnub.reconnect();
		        } else {
		        	logger.error(status.getErrorData().getInformation());
		        }
		    }
		 
		    @Override
		    public void message(PubNub pubnub, PNMessageResult message) {
		 
		    }
		 
		    @Override
		    public void presence(PubNub pubnub, PNPresenceEventResult presence) {
		 
		    }
		};
		 
		pubnub.addListener(subscribeCallback);
		return pubnub;
	}

}
