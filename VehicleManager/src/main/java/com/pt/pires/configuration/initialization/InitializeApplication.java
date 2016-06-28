package com.pt.pires.configuration.initialization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.pt.pires.services.integrator.IImportIntegratorService;
import com.pt.pires.util.FileUtil;

@Component
public class InitializeApplication {
	
	@Value("${application.init:false}")
	private boolean initi;
	
	@Autowired
	@Qualifier("importIntegratorService")
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
