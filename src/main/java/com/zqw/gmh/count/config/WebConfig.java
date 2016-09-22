package com.zqw.gmh.count.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class WebConfig {
	@Value("${http.port}")
	private String httpPort;
	@Value("${server.port}")
	private String httpsPort;
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
 
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404");
                ErrorPage error502Page = new ErrorPage(HttpStatus.BAD_GATEWAY, "/502");
                ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500");
                container.addErrorPages(error404Page,error502Page,error500Page);
            }
        };
    }
    @Bean
    public EmbeddedServletContainerFactory servletContainer(){
    	TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory(){
    		@Override
    		protected void postProcessContext(Context context){
    			SecurityConstraint securityConstraint = new SecurityConstraint();
    			securityConstraint.setUserConstraint("CONFIDENTIAL");
    			SecurityCollection collection = new SecurityCollection();
    			collection.addPattern("/*");
    			securityConstraint.addCollection(collection);
    			context.addConstraint(securityConstraint);
    		}
    	};
    	tomcat.addAdditionalTomcatConnectors(httpConnector());
    	return tomcat;
    }
    @Bean
    public Connector httpConnector(){
    	Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
    	connector.setScheme("http");
    	connector.setPort(Integer.parseInt(httpPort));
    	connector.setSecure(false);
    	connector.setRedirectPort(Integer.parseInt(httpsPort));
    	
    	return connector;
    }
}