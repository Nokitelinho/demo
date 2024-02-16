package com.ibsplc.icargo.tools.deployer.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ibsplc.icargo.tools.WatchDog;
import com.ibsplc.icargo.tools.exec.ProcessExecutor;
import com.ibsplc.icargo.tools.exec.ProcessResult;
import com.ibsplc.icargo.tools.exec.listener.ProcessListener;

@Component
public class DeployProcessListener extends ProcessListener {

	@Autowired
	private WatchDog watchdog;
	
	
	@Override
	public void beforeStart(ProcessExecutor executor) {
		watchdog.setDeploymentInProgress(true);
		super.beforeStart(executor);
	}
	
	@Override
	public void afterStart(Process process, ProcessExecutor executor) {
		watchdog.setDeploymentInProgress(true);
		watchdog.setCurrentProcess(process);
		super.afterStart(process, executor);
	}
	
	@Override
	public void afterStop(Process process) {
		watchdog.setDeploymentInProgress(false);
		super.afterStop(process);
	}
	
	@Override
	public void afterFinish(Process process, ProcessResult result) {
		watchdog.setDeploymentInProgress(false);
		super.afterFinish(process, result);
	}
}
