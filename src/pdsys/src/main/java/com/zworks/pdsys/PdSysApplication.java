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
       
        logger.info("\n"+
        		"○○○○○○○○○╭╭╮╮╮启动╭╭╭╮╮○○○○ \n"+
        		"○○○○○○○○○╰╰ ╮╮成功╭╭ ╯╯○○○○○  \n"+
        		"○○○○○○○○○○○○○╰╮╭╯○○○○○○○○  \n"+
        		"○○◥█◣◢█◤○○○○○○╮╭○○○○○○○○○  \n"+
        		"○○○◥██◤○○○○◢█████◣○○○○○○○  \n"+
        		"○○○○◥█◣○○○◢███████◣○○○○○○  \n"+
        		"○○○○○██◣○◢███████●█◣○○○○○  \n"+
        		"○○○○○███████启动成功████○○○○○  \n"+
        		"○○○○○◥██████████████○○○○○  \n"+
        		"○ ﹏﹏﹏﹏~◥████████████◤﹏﹏﹏﹏○\n");
	}
}
