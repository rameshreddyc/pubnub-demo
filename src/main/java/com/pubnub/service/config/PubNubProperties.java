package com.pubnub.service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("pub.nub")
public class PubNubProperties {
	private String publishKey;
	private String subscribeKey;
	private String globalChannel;
	public String getPublishKey() {
		return publishKey;
	}
	public void setPublishKey(String publishKey) {
		this.publishKey = publishKey;
	}
	public String getSubscribeKey() {
		return subscribeKey;
	}
	public void setSubscribeKey(String subscribeKey) {
		this.subscribeKey = subscribeKey;
	}
	public String getGlobalChannel() {
		return globalChannel;
	}
	public void setGlobalChannel(String globalChannel) {
		this.globalChannel = globalChannel;
	}
	
	
}
