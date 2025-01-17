/*
 * 
 */
package com.ibsplc.icargo.tools.exec.stream;

import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copies all data from an System.input stream to an output stream of the executed process.
 *
 */
public class InputStreamPumper implements Runnable {

  private static final Logger log = LoggerFactory.getLogger(InputStreamPumper.class);

  public static final int SLEEPING_TIME = 100;

  /** the input stream to pump from */
  private final InputStream is;

  /** the output stream to pmp into */
  private final OutputStream os;

  /** flag to stop the stream pumping */
  private volatile boolean stop;

  /**
   * Create a new stream pumper.
   *
   * @param is input stream to read data from
   * @param os output stream to write data to.
   */
  public InputStreamPumper(final InputStream is, final OutputStream os) {
    this.is = is;
    this.os = os;
    this.stop = false;
  }

  /**
   * Copies data from the input stream to the output stream. Terminates as
   * soon as the input stream is closed or an error occurs.
   */
  public void run() {
    try {
      while (!stop) {
        while (is.available() > 0 && !stop) {
          os.write(is.read());
        }
        os.flush();
        Thread.sleep(SLEEPING_TIME);
      }
    }
    catch (Exception e) {
      log.error("Got exception while reading/writing the stream", e);
    }
  }

  public void stopProcessing() {
    stop = true;
  }

}
