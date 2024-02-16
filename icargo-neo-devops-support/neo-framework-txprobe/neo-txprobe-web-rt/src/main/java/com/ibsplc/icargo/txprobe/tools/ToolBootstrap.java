/*
 * ToolBootstrap.java Created on 22-Apr-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.txprobe.tools;

import java.util.Arrays;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			22-Apr-2016       		Jens J P 			First Draft
 */
/**
 * @author A-2394
 *
 */
@SpringBootApplication
@ImportResource("/config/app/tools.xml")
public class ToolBootstrap {

	static boolean shutdown = Boolean.getBoolean("shutdownAfterExec");
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {	
		SpringApplication app = new SpringApplication(ToolBootstrap.class);
		app.setHeadless(false);
		app.setWebEnvironment(true);
		ConfigurableApplicationContext applicationContext = app.run(args);
		final Map<String, Command> commands = applicationContext.getBeansOfType(Command.class);
		final String command = args != null && args.length > 0 ? args[0].trim() : "noop";
		boolean executed = false;
		for(Map.Entry<String, Command> e : commands.entrySet()){
			if(e.getValue().name().equals(command)){
				executed = true;
				try {
					final String[] cargs = Arrays.copyOfRange(args, 1, args.length);
					e.getValue().execute(cargs);
				} catch (Exception ex) {
					ex.printStackTrace();
					showHelp(commands);
				}
			}
		}
		if(!executed){
			System.out.println("no command operation matched.");
			showHelp(commands);
		}
		if(shutdown)
			System.exit(0);
	}

	private static void showHelp(Map<String, Command> commands){
		commands.forEach((k, v) -> {
			System.out.println("Command : " + v.name());
			System.out.println(v.usage());
			System.out.println("------------------------------------------------------------------------------------------------");
		});
	}
}
