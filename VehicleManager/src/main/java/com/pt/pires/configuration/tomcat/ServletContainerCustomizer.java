package com.pt.pires.configuration.tomcat;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServletContainerCustomizer implements EmbeddedServletContainerCustomizer {
	
	private final static String KEYSTORE_TYPE = "PKCS12";
	
	private final static String KEYSTORE_ALIAS = "keyAlias";
	
	private final static String KEYSTORE_PASSWORD = "keyPwd";

	private TomcatCustomizerConnector customizer;

	// By default is error
	@Value("${application.keystore.fullPath: error}")
	private String keyStoreFullpath;
	
	// Don't use TLS/HTTPS by default
	@Value("${application.https: false}")
	private boolean https;
	
	public ServletContainerCustomizer() throws IOException {
		if(https) {
			customizer = new TomcatCustomizerConnector(keyStoreFullpath, KEYSTORE_PASSWORD,
					KEYSTORE_TYPE, KEYSTORE_ALIAS);
		}
	}
	
	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		if(container instanceof TomcatEmbeddedServletContainerFactory && https) {
			TomcatEmbeddedServletContainerFactory containerFactory = (TomcatEmbeddedServletContainerFactory) container;
			containerFactory.addConnectorCustomizers(customizer);
		}
	}

}
