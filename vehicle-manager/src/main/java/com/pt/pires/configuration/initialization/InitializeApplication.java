package com.pt.pires.configuration.initialization;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.pt.pires.services.integrator.IImportIntegratorService;
import com.pt.pires.util.FileUtil;

@Component
public class InitializeApplication {
	
	//By default doesn't initialize
	@Value("${application.init:false}")
	private boolean initi;
	
	@Inject
	@Named("importIntegratorService")
	private IImportIntegratorService importIntegartorService;

	
	@EventListener({ContextRefreshedEvent.class})
    void contextRefreshedEvent() throws Exception {
		FileUtil.makeDir(FileUtil.getRootPath());
		if(initi){
			System.out.println("[Starting Config File Initialization....]");
			importIntegartorService.validate();
			importIntegartorService.importFrom();
			System.out.println("[Finishing Config File Initialization]");
		}else{
			System.out.println("[Config File Initialization Skipped]");
		}
    }
	
}
