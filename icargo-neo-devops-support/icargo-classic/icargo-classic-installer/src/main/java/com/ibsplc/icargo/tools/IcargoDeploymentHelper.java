package com.ibsplc.icargo.tools;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.ibsplc.icargo.tools.deployer.data.PropertyItems;
import com.ibsplc.icargo.tools.deployer.data.PropertyMap;
import com.ibsplc.icargo.tools.deployer.exceptions.DeploymentFailedException;
import com.ibsplc.icargo.tools.deployer.listener.DeployProcessListener;
import com.ibsplc.icargo.tools.exec.InvalidExitValueException;
import com.ibsplc.icargo.tools.exec.MessageLogger;
import com.ibsplc.icargo.tools.exec.ProcessExecutor;

@Component
public class IcargoDeploymentHelper {

	private static final String PROP_FILE = "deploy.properties";

	private static final String MSG_PROGRESS = "Deployment already in progress. Please try after some time !";

	@Autowired
	private DeployProcessListener listener;

	public StreamingResponseBody doAutomateDeployment(String url, String domain) {
		Properties prop = loadProperties();
		List<String> commandList = new ArrayList<String>();
		commandList.add(String.valueOf(prop.get(PropertyItems.SCRIPT_EXECUTING_COMMAND.name())));
		commandList.add(String.valueOf(prop.get(PropertyItems.SCRIPT_FULL_NAME.name())));
		commandList.add(String.valueOf(prop.get(PropertyItems.SCRIPT_METHOD_UPGRADE.name())));
		commandList.add(domain);
		commandList.add(url);
		System.out.println(commandList);
		return new StreamingResponseBody() {
			@Override
			public void writeTo(OutputStream out) throws IOException {
				try {
					ProcessExecutor proc = new ProcessExecutor();
					long timeout = Long.valueOf(prop.getProperty(PropertyItems.PROCESS_TIMEOUT_MINUTES.name()));
					proc.timeout(timeout, TimeUnit.MINUTES);
					proc.command(commandList);
					proc.addListener(listener);
					proc.setMessageLogger(new MessageLogger() {

						@Override
						public void message(Logger log, String format, Object... arguments) {
							System.out.printf(format.replace("{}", "%s"), arguments);

						}
					});
					proc.redirectOutput(new OutputStream() {

						@Override
						public void write(int b) throws IOException {
							final byte c = (byte) b;
							System.out.print((char) c);
							out.write(c);
							if ((c == '\n') || (c == '\r')) {
								out.flush();
							}
						}
					});
					proc.execute();

				} catch (InvalidExitValueException | InterruptedException | TimeoutException e) {
					e.printStackTrace();
					throw new DeploymentFailedException(e.getMessage());
				}

			}
		};
	}

	public StreamingResponseBody doAutomateDeployment_backup(String url, String domain) {
		Properties prop = loadProperties();
		List<String> commandList = new ArrayList<String>();
		commandList.add(String.valueOf(prop.get(PropertyItems.SCRIPT_EXECUTING_COMMAND.name())));
		commandList.add(String.valueOf(prop.get(PropertyItems.SCRIPT_FULL_NAME.name())));
		commandList.add(url);
		commandList.add(domain);
		System.out.println(commandList);
		return new StreamingResponseBody() {
			@Override
			public void writeTo(OutputStream out) throws IOException {
				try {
					new ProcessExecutor().command(commandList).addListener(listener).redirectOutput(new OutputStream() {

						@Override
						public void write(int b) throws IOException {
							final byte c = (byte) b;
							System.out.print((char) c);
							out.write(c);
							if ((c == '\n') || (c == '\r')) {
								out.flush();
							}
						}
					}).execute();
				} catch (InvalidExitValueException | InterruptedException | TimeoutException e) {
					e.printStackTrace();
					throw new DeploymentFailedException(e.getMessage());
				}

			}
		};
	}

	public StreamingResponseBody getTailLog(String domain) {
		Properties prop = loadProperties();
		List<String> commandList = new ArrayList<String>();
		commandList.add(String.valueOf(prop.get(PropertyItems.SCRIPT_EXECUTING_COMMAND.name())));
		commandList.add(String.valueOf(prop.get(PropertyItems.SCRIPT_FULL_NAME.name())));
		commandList.add(String.valueOf(prop.get(PropertyItems.SCRIPT_METHOD_TAIL_LOG.name())));
		commandList.add(domain);
		System.out.println(commandList);
		return new StreamingResponseBody() {
			@Override
			public void writeTo(OutputStream out) throws IOException {
				try {
					ProcessExecutor proc = new ProcessExecutor();
					long timeout = 9;
					proc.timeout(timeout, TimeUnit.MINUTES);
					proc.command(commandList);
					proc.setMessageLogger(new MessageLogger() {

						@Override
						public void message(Logger log, String format, Object... arguments) {
							System.out.printf(format.replace("{}", "%s"), arguments);

						}
					});
					proc.redirectOutput(new OutputStream() {

						@Override
						public void write(int b) throws IOException {
							final byte c = (byte) b;
							System.out.print((char) c);
							out.write(c);
							if ((c == '\n') || (c == '\r')) {
								out.flush();
							}
						}
					});
					proc.execute();

				} catch (InvalidExitValueException | InterruptedException e) {
					e.printStackTrace();
					throw new DeploymentFailedException(e.getMessage());
				} catch(TimeoutException e) {
					System.out.println("\n--- Exiting from taillog ----");
				}

			}
		};
	}


	public StreamingResponseBody execute(String action, String domain, String url) {
		Properties prop = loadProperties();
		List<String> commandList = new ArrayList<String>();
		commandList.add(String.valueOf(prop.get(PropertyItems.SCRIPT_EXECUTING_COMMAND.name())));
		commandList.add(String.valueOf(prop.get(PropertyItems.SCRIPT_FULL_NAME.name())));
		commandList.add(action);
		commandList.add(domain);
		commandList.add(url);
		System.out.println(commandList);
		return new StreamingResponseBody() {
			@Override
			public void writeTo(OutputStream out) throws IOException {
				try {
					ProcessExecutor proc = new ProcessExecutor();
					long timeout = 9;
					proc.timeout(timeout, TimeUnit.MINUTES);
					proc.command(commandList);
					proc.setMessageLogger(new MessageLogger() {
						
						@Override
						public void message(Logger log, String format, Object... arguments) {
							System.out.printf(format.replace("{}", "%s"), arguments);
							
						}
					});
					proc.redirectOutput(new OutputStream() {
						
						@Override
						public void write(int b) throws IOException {
							final byte c = (byte) b;
							System.out.print((char) c);
							out.write(c);
							if ((c == '\n') || (c == '\r')) {
								out.flush();
							}
						}
					});
					proc.execute();
					
				} catch (InvalidExitValueException | InterruptedException e) {
					e.printStackTrace();
					throw new DeploymentFailedException(e.getMessage());
				} catch(TimeoutException e) {
					System.out.println("\n--- Exiting from execute method ----");
				}
				
			}
		};
	}
	
	
	public StreamingResponseBody notifyDeploymentProgress() {
		return new StreamingResponseBody() {

			@Override
			public void writeTo(OutputStream out) throws IOException {
				out.write(MSG_PROGRESS.getBytes());
			}
		};
	}

	private Properties loadCustomProperties() {
		Properties prop = new Properties();
		try {
			prop.load(new FileReader(new File(PROP_FILE)));
		} catch (IOException e) {
			return null;
		}
		return prop;
	}

	public Properties loadProperties() {
		Properties prop = new Properties();
		for (PropertyItems item : PropertyItems.values()) {
			prop.put(item.name(), item.getHardcodedValue());
		}
		Properties custom = loadCustomProperties();
		if (custom != null) {
			for (Object key : custom.keySet()) {
				prop.put(key, custom.get(key));
			}
		}
		return prop;
	}

	public String saveProperties(Properties prop) {
		try {
			File file = new File(PROP_FILE);
			prop.store(new FileWriter(file), null);
			return "properties saved to file " + file.getAbsolutePath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Error in saving to file";
	}

}
