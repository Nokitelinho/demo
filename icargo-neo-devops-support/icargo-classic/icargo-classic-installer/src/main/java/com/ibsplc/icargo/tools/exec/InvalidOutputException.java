/*
 * 
 */
package com.ibsplc.icargo.tools.exec;

import com.ibsplc.icargo.tools.exec.listener.ProcessListener;

/**
 * Process finished with an unexpected output.
 *
 * @see ProcessListener#afterFinish(Process, ProcessResult)
 * @since 1.8
 */
public class InvalidOutputException extends InvalidResultException {

  private static final long serialVersionUID = 1L;

  /**
   * @param message the detail message of the exception
   * @param result result of execution (contains also the exit value)
   */
  public InvalidOutputException(String message, ProcessResult result) {
    super(message, result);
  }

}
