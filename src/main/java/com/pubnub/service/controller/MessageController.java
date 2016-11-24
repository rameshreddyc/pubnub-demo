package com.pubnub.service.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.PNCallback;
import com.pubnub.api.models.consumer.PNPublishResult;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.service.config.PubNubProperties;

@RestController
public class MessageController {
	
	private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
	
	@Resource
	private PubNubProperties pubNubProperties;
	
	@Resource
	private PubNub pubNub;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<String> push(@RequestBody JsonNode message){
		JsonNode channel = message.get("channel");
		String ch = pubNubProperties.getGlobalChannel();
		if(channel!=null && !channel.isNull()){
			ch=channel.asText();
		}
		pubNub.publish()
	    .message(message)
	    .channel(ch)
	    .async(new PNCallback<PNPublishResult>() {
	        @Override
	        public void onResponse(PNPublishResult result, PNStatus status) {
	            // handle publish result, status always present, result if successful
	            // status.isError to see if error happened
	        	logger.error("Is Error :: "+status.isError());
	        }
	    });
		return new ResponseEntity<String>("{\"status\":\"success\"}",HttpStatus.OK);
	}

}
