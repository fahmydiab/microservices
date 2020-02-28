package com.fahmy.microservices.limitsservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fahmy.microservices.limitsservice.bean.LimitsConfig;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class LimitsConfigController {
	
	@Autowired
	private Configuration configuration;

	@GetMapping("/limits")
	public LimitsConfig retrieveLimitsFromConfig() {
		return new LimitsConfig(configuration.getMaximum(),
				configuration.getMinimum());
		
	}
	@GetMapping("/fault-tolerance-example")
	@HystrixCommand(fallbackMethod = "fallbackRetrieveConfiguration")
	public LimitsConfig retrieveConfig() {
		throw new RuntimeException("Not available");
	}
	
	public LimitsConfig fallbackRetrieveConfiguration() {
		return new LimitsConfig(999,9);
	}
}
