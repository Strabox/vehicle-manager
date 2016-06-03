package com.pt.pires.configuration.tomcat;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
public class ServletContainerCustomizer implements EmbeddedServletContainerCustomizer {

	private final static String KEYSTORE_RESOURCE_FILE_PATH = "/other/myKeystore.p12";
	
	private final static String KEYSTORE_TYPE = "PKCS12";
	
	private final static String KEYSTORE_ALIAS = "keyAlias";
	
	private final static String KEYSTORE_PASSWORD = "keyPwd";
	
	private String absoluteKeystoreFile;

	private TomcatCustomizerConnector customizer;

	@Value("${application.https}")
	private boolean https;
	
	public ServletContainerCustomizer() throws IOException {
		Resource resource = new ClassPathResource(KEYSTORE_RESOURCE_FILE_PATH);
		absoluteKeystoreFile = resource.getFile().getAbsolutePath();
		customizer = new TomcatCustomizerConnector(absoluteKeystoreFile, KEYSTORE_PASSWORD,
				KEYSTORE_TYPE, KEYSTORE_ALIAS);
	}
	
	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		if(container instanceof TomcatEmbeddedServletContainerFactory && https) {
			TomcatEmbeddedServletContainerFactory containerFactory = (TomcatEmbeddedServletContainerFactory) container;
			containerFactory.addConnectorCustomizers(customizer);
		}
	}

}
