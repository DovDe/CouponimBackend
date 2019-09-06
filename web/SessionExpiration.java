package com.dovdekeyser.groupon_springboot_web.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SessionExpiration implements Runnable {
	
	private boolean quit = false;

	
	@Autowired
	private Map<String,Session> tokensMap;

	private LoginController login;
	
	@Override
	public void run() {
		long currentTime = System.currentTimeMillis();
		long halfHour = 1000 * 60 * 30 ;
		System.out.println("session: thread running");
		
		while(!quit) {
			System.out.println("Session: new Round");
			tokensMap.forEach((k,v)->{
				if(v.getLastAccessed()+ halfHour  <  currentTime) login.logout(k);
				System.out.println("logged out: " + k);
			});
			

			
			try {
				Thread.sleep(halfHour);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());

			}
		}
	}
	
	public void stop() {
		quit = true;
	}

}
