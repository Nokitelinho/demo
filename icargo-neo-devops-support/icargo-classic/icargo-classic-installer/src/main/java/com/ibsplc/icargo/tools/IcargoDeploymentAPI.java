package com.ibsplc.icargo.tools;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.ibsplc.icargo.tools.deployer.data.PropertyMap;
import com.ibsplc.icargo.tools.deployer.data.PropertyMapList;
import com.ibsplc.icargo.tools.deployer.exceptions.DeploymentFailedException;
import com.ibsplc.icargo.tools.exec.InvalidExitValueException;
import com.ibsplc.icargo.tools.exec.ProcessExecutor;

@RestController
public class IcargoDeploymentAPI {

	private static final long MEGABYTE = 1024L * 1024L;
  
	@Autowired
	private IcargoDeploymentHelper helper;

	@Autowired
	private WatchDog watchdog;

	@RequestMapping("/autodeploy")
	public StreamingResponseBody autoDeploy(@RequestParam("url") String url, @RequestParam("domain") String domain,
			@RequestParam("invKey") String invKey) {
		System.out.println("===================> " + watchdog.isDeploymentInProgress());
		if (watchdog.isDeploymentInProgress()) {
			return helper.notifyDeploymentProgress();
		} else {
			watchdog.setDeploymentInProgress(true);
			watchdog.setCurrentProcessKey(invKey);
			return helper.doAutomateDeployment(url, domain);
		}
	}

	
	
	@RequestMapping("/taillog")
	public StreamingResponseBody getTailLog(@RequestParam("domain") String domain) {
		return helper.getTailLog(domain);
	}

	@RequestMapping("/execute")
	public StreamingResponseBody execute(@RequestParam("action") String action, @RequestParam("domain") String domain, @RequestParam("url") String url) {
		return helper.execute(action, domain, url);
	}
	
	@RequestMapping("/kill")
	public String kill(@RequestParam("invKey") String invKey) {
		if (StringUtils.equals(invKey, watchdog.getCurrentProcessKey())) {
			Process p = watchdog.getCurrentProcess();
			// long id = p.pid();
			p.destroyForcibly();
			System.out.println("Killed Process " + p);
			return String.format("Killed process with invoker-key %s [ProcessID:%s]", invKey, p.pid());
		}
		return "No Process exists with invoker-key " + invKey;
	}

	@RequestMapping("/getinvkey")
	public String getinvkey() {
		if (watchdog.getCurrentProcess() != null && watchdog.getCurrentProcess().isAlive()) {
			return watchdog.getCurrentProcessKey();
		}
		return "";
	}

	@RequestMapping("/getmemorydetails")
	public String getmemorydetails() {
		Runtime runtime = Runtime.getRuntime();
        // Run the garbage collector
        runtime.gc();
        // Calculate the used memory
        long memory = runtime.totalMemory() - runtime.freeMemory();
		return memory / MEGABYTE + " MB" ;
	}
	
	@RequestMapping("/getwatch")
	public boolean getwatch() {
		System.out.println("Deployment Progress ===================> " + watchdog.isDeploymentInProgress());
		return watchdog.isDeploymentInProgress();
	}

	@RequestMapping("/getproperties")
	public Properties getProperties() {
		return helper.loadProperties();
	}

	@RequestMapping(value = { "/savepropertiesfile" }, method = { RequestMethod.POST }, headers = {
			"Accept=application/json" })
	public PropertyMap saveProperties(@RequestBody PropertyMapList map) {
		Properties prop = new Properties();
		for (PropertyMap m : map.getProperties()) {
			prop.put(m.getKey(), m.getValue());
		}
		PropertyMap ret = new PropertyMap();
		ret.setValue(helper.saveProperties(prop));
		return ret;
	}

	@ExceptionHandler({ Exception.class })
	public void exceptionHandler(final Exception ex) throws Exception {
		throw ex;

	}
}
