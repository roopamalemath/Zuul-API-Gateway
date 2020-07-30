package com.in28minutes.microservices;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {
	
	@Autowired
	private CurrencyExchangeServiceProxy currencyExchangeServiceProxy;
	
	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrency(@PathVariable String from,
													@PathVariable String to,
													@PathVariable BigDecimal quantity) {
		
	Map<String, String> uriVariables=new HashMap<>();
	uriVariables.put("from", from);
	uriVariables.put("to", to);
		// invoking extrenal microservice
	ResponseEntity<CurrencyConversionBean> responseEntity=new RestTemplate().getForEntity("http://localhost:8081/currency-exchange/from/{from}/to/{to}", 
														  CurrencyConversionBean.class, 
														  uriVariables);
	// get the actual repsonse object
	CurrencyConversionBean response=responseEntity.getBody();
	//System.out.println(response.);
	
	//return new CurrencyConversionBean(1L, from, to, BigDecimal.ONE, quantity ,quantity, 0);
	// once we are getting the cnversion bean we want to use the values from the response
	return new CurrencyConversionBean(response.getId(), from, to, response.getConversionMultiple(), quantity, quantity.multiply(response.getConversionMultiple()), response.getPort());
		
	}
	
	
	@GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrencyFeign(@PathVariable String from,
													@PathVariable String to,
													@PathVariable BigDecimal quantity) {
	CurrencyConversionBean response=currencyExchangeServiceProxy.retrieveExchangeValue(from, to);
	return new CurrencyConversionBean(response.getId(), from, to, response.getConversionMultiple(), quantity, quantity.multiply(response.getConversionMultiple()), response.getPort());
		
	}

}
