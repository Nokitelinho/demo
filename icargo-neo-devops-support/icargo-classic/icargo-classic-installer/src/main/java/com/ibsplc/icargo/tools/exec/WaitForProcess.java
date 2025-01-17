/*
 * 
 */
package com.ibsplc.icargo.tools.exec;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibsplc.icargo.tools.exec.close.ProcessCloser;
import com.ibsplc.icargo.tools.exec.listener.ProcessListener;
import com.ibsplc.icargo.tools.exec.stop.ProcessStopper;


/**
 * Handles the executed process.
 *
 */
public class WaitForProcess implements Callable<ProcessResult> {

  private static final Logger log = LoggerFactory.getLogger(WaitForProcess.class);

  private final Process process;

  /**
   * Set of main attributes used to start the process.
   */
  private final ProcessAttributes attributes;

  /**
   * Helper for stopping the process in case of interruption.
   */
  private final ProcessStopper stopper;

  /**
   * Helper for closing the process' standard streams.
   */
  private final ProcessCloser closer;

  /**
   * Buffer where the process output is redirected to or <code>null</code> if it's not used.
   */
  private final ByteArrayOutputStream out;

  /**
   * Process event listener (not <code>null</code>).
   */
  private final ProcessListener listener;

  /**
   * Helper for logging messages about starting and waiting for the processes.
   */
  private final MessageLogger messageLogger;

  /**
   * Thread which executes this operation.
   */
  private volatile Thread workerThread;

  public WaitForProcess(Process process, ProcessAttributes attributes, ProcessStopper stopper, ProcessCloser closer, ByteArrayOutputStream out, ProcessListener listener, MessageLogger messageLogger) {
    this.process = process;
    this.attributes = attributes;
    this.stopper = stopper;
    this.closer = closer;
    this.out = out;
    this.listener = listener;
    this.messageLogger = messageLogger;
  }

  /**
   * @return the sub process.
   */
  public Process getProcess() {
    return process;
  }

  public ProcessResult call() throws IOException, InterruptedException {
    try {
      workerThread = Thread.currentThread();
      int exit;
      boolean finished = false;
      try {
        exit = process.waitFor();
        finished = true;
        messageLogger.message(log, "{} stopped with exit code {}", this, exit);
      }
      finally {
        if (!finished) {
          messageLogger.message(log, "Stopping {}...", this);
          stopper.stop(process);
        }

        closer.close(process);
      }
      ProcessOutput output = getCurrentOutput();
      ProcessResult result = new ProcessResult(exit, output, process);
      InvalidExitUtil.checkExit(attributes, result);
      listener.afterFinish(process, result);
      return result;
    }
    finally {
      // Invoke listeners - regardless process finished or got cancelled
      listener.afterStop(process);
      workerThread = null;
    }
  }

  private ProcessOutput getCurrentOutput() {
    return out == null ? null : new ProcessOutput(out.toByteArray());
  }

  /**
   * Adds a suffix for an error message including:
   * <ul>
   *   <li>executed command</li>
   *   <li>working directory (unless it's inherited from parent)</li>
   *   <li>environment (unless it's the same with the parent)</li>
   *   <li>output read so far (unless it's not read)</li>
   * </ul>
   * @param sb where the suffix is appended to.
   */
  public void addExceptionMessageSuffix(StringBuilder sb) {
    InvalidExitUtil.addExceptionMessageSuffix(attributes, sb, getCurrentOutput());
  }

  /**
   * @return current stacktrace of the worker thread, <code>null</code> if this operation is currently not running.
   */
  public StackTraceElement[] getStackTrace() {
    Thread t = workerThread;
    return t == null ? null : t.getStackTrace();
  }

  @Override
  public String toString() {
    return process.toString();
  }

}
