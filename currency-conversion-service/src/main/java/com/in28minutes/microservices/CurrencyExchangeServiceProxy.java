package com.in28minutes.microservices;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



//@FeignClient(name="currency-exchange-service",url="localhost:8081") // disable url when using ribbon
//@FeignClient(name="currency-exchange-service")
// feign make a call to netflix and talk to the naming and get the uri for it
@FeignClient(name="netflix-zuul-api-gateway-server")
@RibbonClient(name="currency-exchange-service")
public interface CurrencyExchangeServiceProxy {

	// define a method to talk to currency exchange service
	// we have take the definition of the method from the currencyexchangeservice
	// make a few modifications interms of the return values
	//@GetMapping("/currency-exchange/from/{from}/to/{to}")
	@GetMapping("/currency-exchange-service/currency-exchange/from/{from}/to/{to}")
	public CurrencyConversionBean retrieveExchangeValue(@PathVariable("from") String from,
			                                   @PathVariable("to") String to); 
		
	
}
