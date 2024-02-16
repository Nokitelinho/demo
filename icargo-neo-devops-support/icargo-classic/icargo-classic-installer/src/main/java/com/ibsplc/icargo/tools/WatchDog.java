package com.ibsplc.icargo.tools;

import org.springframework.stereotype.Component;

import com.ibsplc.icargo.tools.exec.ProcessExecutor;
import com.ibsplc.icargo.tools.exec.ProcessResult;

@Component
public class WatchDog {

	private boolean isDeploymentInProgress = false;

	public Process currentProcess;

	public String currentProcessKey;

	public void setDeploymentInProgress(boolean isDeploymentInProgress) {
		this.isDeploymentInProgress = isDeploymentInProgress;
	}

	public boolean isDeploymentInProgress() {
		return isDeploymentInProgress;
	}

	public void setCurrentProcess(Process currentProcess) {
		this.currentProcess = currentProcess;
	}

	public Process getCurrentProcess() {
		return currentProcess;
	}

	public String getCurrentProcessKey() {
		return currentProcessKey;
	}

	public void setCurrentProcessKey(String currentProcessKey) {
		this.currentProcessKey = currentProcessKey;
	}
}
