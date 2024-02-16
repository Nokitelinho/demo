/*
 * 
 */
package com.ibsplc.icargo.tools.exec.stop;

/**
 * {@link ProcessStopper} implementation that does nothing - it keeps the process running.
 */
public class NopProcessStopper implements ProcessStopper {

  /**
   * Singleton instance of the {@link NopProcessStopper}.
   */
  public static final ProcessStopper INSTANCE = new NopProcessStopper();

  public void stop(Process process) {
    // don't stop the process
  }

}
