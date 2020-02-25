package com.pt.pires.configuration.tomcat;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;

public class TomcatCustomizerConnector implements TomcatConnectorCustomizer{

	private static final String SCHEME = "https";
	private static final String SSL_PROTOCOL = "TLS";
	private static final String PROTOCOL = "org.apache.coyote.http11.Http11Protocol";
	private int SECURE_SERVER_PORT = 443;
	
	private String absoluteKeystoreFile;
	private String keystorePassword;
	private String keystoreType;
	private String keystoreAlias;
	
	public TomcatCustomizerConnector(String absoluteKeystoreFile,
	String keystorePassword, String keystoreType, String keystoreAlias) {
		this.absoluteKeystoreFile = absoluteKeystoreFile;
		this.keystorePassword = keystorePassword;
		this.keystoreType = keystoreType;
		this.keystoreAlias = keystoreAlias.toLowerCase();
	}
	
	@Override
	public void customize(Connector connector) {
		connector.setPort(SECURE_SERVER_PORT);
	    connector.setSecure(true);
	    connector.setScheme(SCHEME);
	    
	    connector.setAttribute("SSLEnabled", true);
        connector.setAttribute("sslProtocol", SSL_PROTOCOL);
        connector.setAttribute("protocol", PROTOCOL);
        connector.setAttribute("clientAuth", false);
        connector.setAttribute("keystoreFile", absoluteKeystoreFile);
        connector.setAttribute("keystoreType", keystoreType);
        connector.setAttribute("keystorePass", keystorePassword);
        connector.setAttribute("keystoreAlias", keystoreAlias);
        connector.setAttribute("keyPass", keystorePassword);
	}

}
