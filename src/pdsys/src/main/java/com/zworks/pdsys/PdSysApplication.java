package com.zworks.pdsys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class PdSysApplication {
	protected static final Logger logger = LoggerFactory.getLogger(PdSysApplication.class);

	public static void main(String[] args) {
		logger.info("PDSYS 启动开始...");
		SpringApplication app = new SpringApplication(PdSysApplication.class); 
        app.run(args);
       
        logger.info("             _                               _                    _                _"); 
        logger.info("            | |                             | |                  | |              | |");
        logger.info(" _ __     __| |  ___   _   _   ___     ___  | |_    __ _   _ __  | |_    ___    __| |");
        logger.info("| '_ \\   / _` | / __| | | | | / __|   / __| | __|  / _` | | '__| | __|  / _ \\  / _` |");
        logger.info("| |_) | | (_| | \\__ \\ | |_| | \\__ \\   \\__ \\ | |_  | (_| | | |    | |_  |  __/ | (_| |");
        logger.info("| .__/   \\__,_| |___/  \\__, | |___/   |___/  \\__|  \\__,_| |_|     \\__|  \\___|  \\__,_|");
        logger.info("| |                     __/ |");                                                        
        logger.info("|_|                    |___/");         
	}
}
