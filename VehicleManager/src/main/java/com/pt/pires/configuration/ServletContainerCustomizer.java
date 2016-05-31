package com.pt.pires.configuration;

import java.io.IOException;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class ServletContainerCustomizer implements EmbeddedServletContainerCustomizer {

	private final static String KEYSTORE_RESOURCE_FILE_PATH = "/other/myKeystore.p12";
	
	private final static String KEYSTORE_TYPE = "PKCS12";
	
	private final static String KEYSTORE_ALIAS = "keyAlias";
	
	private final static String KEYSTORE_PASSWORD = "keyPwd";
	
	private String absoluteKeystoreFile;

	private TomcatCustomizerConnector customizer;
	
	public ServletContainerCustomizer() throws IOException {
		Resource resource = new ClassPathResource(KEYSTORE_RESOURCE_FILE_PATH);
		absoluteKeystoreFile = resource.getFile().getAbsolutePath();
		customizer = new TomcatCustomizerConnector(absoluteKeystoreFile, KEYSTORE_PASSWORD,
				KEYSTORE_TYPE, KEYSTORE_ALIAS);
	}
	
	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		if(container instanceof TomcatEmbeddedServletContainerFactory) {
			TomcatEmbeddedServletContainerFactory containerFactory = (TomcatEmbeddedServletContainerFactory) container;
			containerFactory.addConnectorCustomizers(customizer);
		}
	}

}
