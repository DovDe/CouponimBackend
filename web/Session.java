package com.dovdekeyser.groupon_springboot_web.web;

import com.dovdekeyser.groupon_springboot_web.facade.Facade;

public class Session {
	private Facade facade;
	private long lastAccessed;
	
	public Facade getFacade() {
		return facade;
	}
	public void setFacade(Facade facade) {
		this.facade = facade;
	}
	public long getLastAccessed() {
		return lastAccessed;
	}
	public void setLastAccessed(long lastAccessed) {
		this.lastAccessed = lastAccessed;
	}
	
	
	
}
