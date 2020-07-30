# Zuul-API-Gateway

API Gateway - Zuul - would take care of providing common features that we need to implement for all these microservices

1. Authentication, Authorization, Security
2. Rate Limits
3. Fault Toleration
4. Service Aggregation

instead of allowing microservice to call each other directly, we would make all the calls go through a API gateway 

coz of all the calls are routed through API gateway, API gateway serves as a great place for debugging as well as doing analytics

we want to intercept all the calls to go through API gateway between all the microservices
netfix provides a implementation called Zuul

3 steps to configure Zuul

1. create a component for Zuul server
2. decide what should it do when it intercept a request 
3. make sure that all the important request are configured to pass through to pass through the Zuul API gateway

1. add a dependency 

<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-zuul</artifactId>
</dependency>
    
2. add @EnableZuulProxy

3 create a class with ZuulLoggingFilter extends ZuulFilter

@Component
public class ZuulLoggingFilter extends ZuulFilter{
	
	private Logger logger=LoggerFactory.getLogger(this.getClass());

	// execute filter for every request
	@Override
	public boolean shouldFilter() {
		return true;
	}

	// real logic of intercept
	@Override
	public Object run() throws ZuulException {
		//how can i get the current request
		HttpServletRequest request=RequestContext.getCurrentContext().getRequest();
		logger.info("request -> {} request uri -> {}",
				request,request.getRequestURI());
		return null;
	}

	// all the request intercept before execution
	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

}

syntax : http://localhost:8765/{spring-application-name}/{uri}

http://localhost:8765/currency-exchange-service/currency-exchange/from/USD/to/INR

http://localhost:8765/currency-conversion-service/currency-converter-feign/from/USD/to/INR/quantity/1000





