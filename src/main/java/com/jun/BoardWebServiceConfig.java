package com.jun;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;
import org.springframework.xml.xsd.commons.CommonsXsdSchemaCollection;


@EnableWs
@Configuration
public class BoardWebServiceConfig {
    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/ws/*");
    }

    @Bean(name = "users")
    public DefaultWsdl11Definition defaultWsdl11Definition() {
    	DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
    	wsdl11Definition.setPortTypeName("userPort");
    	wsdl11Definition.setLocationUri("/ws");
    	wsdl11Definition.setTargetNamespace("http://jun.com/schemas/users");
    	wsdl11Definition.setSchemaCollection(xsds());
        return wsdl11Definition;
    }
    
//    @Bean
//    public XsdSchema userSchema(){
//    	return new SimpleXsdSchema(new ClassPathResource("users.xsd"));
//    }
//
	@Bean 
	public CommonsXsdSchemaCollection xsds() { 
	 CommonsXsdSchemaCollection collection = 
	   new CommonsXsdSchemaCollection(new Resource[] { new ClassPathResource("users.xsd") });
//	 	new CommonsXsdSchemaCollection(new Resource[] { new ClassPathResource("users.xsd"),new ClassPathResource("types.xsd") });
	 collection.setInline(false); 
	 return collection; 
	} 
    
    @Bean
    public XsdSchema types(){
    	return new SimpleXsdSchema(new ClassPathResource("types.xsd"));
    }
}
