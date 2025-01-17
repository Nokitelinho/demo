/*
 *
 */
package com.ibsplc.icargo.tools.exec.stream.slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibsplc.icargo.tools.exec.stream.CallerLoggerUtil;

/**
 * Creates output streams that write to {@link Logger}s.
 *
 */
public class Slf4jStream {

  private final Logger log;

  private Slf4jStream(Logger log) {
    this.log = log;
  }

  /**
   * @param log logger which an output stream redirects to.
   * @return Slf4jStream with the given logger.
   */
  public static Slf4jStream of(Logger log) {
    return new Slf4jStream(log);
  }

  /**
   * @param klass class which is used to get the logger's name.
   * @return Slf4jStream with a logger named after the given class.
   */
  public static Slf4jStream of(Class<?> klass) {
    return of(LoggerFactory.getLogger(klass));
  }

  /**
   * Constructs a logger from a class name and an additional name,
   * appended to the end. So the final logger name will be:
   * <blockquote><code>klass.getName() + "." + name</code></blockquote>
   *
   * @param klass class which is used to get the logger's name.
   * @param name logger's name, appended to the class name.
   * @return Slf4jStream with a logger named after the given class.
   *
   * @since 1.8
   */
  public static Slf4jStream of(Class<?> klass, String name) {
    if (name == null) {
      return of(klass);
    } else {
      return of(LoggerFactory.getLogger(klass.getName() + "." + name));
    }
  }

  /**
   * @param name logger's name (full or short).
   *    In case of short name (no dots) the given name is prefixed by caller's class name and a dot.
   * @return Slf4jStream with the given logger.
   */
  public static Slf4jStream of(String name) {
    return of(LoggerFactory.getLogger(CallerLoggerUtil.getName(name, 1)));
  }

  /**
   * @return Slf4jStream with the logger of caller of this method.
   */
  public static Slf4jStream ofCaller() {
    return of(LoggerFactory.getLogger(CallerLoggerUtil.getName(null, 1)));
  }

  /**
   * @param level the desired logging level
   * @return output stream that writes with a given level.
   */
  public Slf4jOutputStream as(Level level) {
    switch (level) {
    case TRACE: return asTrace();
    case DEBUG: return asDebug();
    case INFO: return asInfo();
    case WARN: return asWarn();
    case ERROR: return asError();
    }
    throw new IllegalArgumentException("Invalid level " + level);
  }

  /**
   * @return output stream that writes <code>trace</code> level.
   */
  public Slf4jOutputStream asTrace() {
    return new Slf4jTraceOutputStream(log);
  }

  /**
   * @return output stream that writes <code>debug</code> level.
   */
  public Slf4jOutputStream asDebug() {
    return new Slf4jDebugOutputStream(log);
  }

  /**
   * @return output stream that writes <code>info</code> level.
   */
  public Slf4jOutputStream asInfo() {
    return new Slf4jInfoOutputStream(log);
  }

  /**
   * @return output stream that writes <code>warn</code> level.
   */
  public Slf4jOutputStream asWarn() {
    return new Slf4jWarnOutputStream(log);
  }

  /**
   * @return output stream that writes <code>error</code> level.
   */
  public Slf4jOutputStream asError() {
    return new Slf4jErrorOutputStream(log);
  }

}
