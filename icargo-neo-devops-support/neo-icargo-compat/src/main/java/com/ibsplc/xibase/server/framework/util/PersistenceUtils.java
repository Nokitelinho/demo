/*
 * PersistenceUtils.java Created on Jul 29, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.xibase.server.framework.util;


import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import org.hibernate.LobHelper;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import java.io.*;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;

/**
 * Utilites for handling BLOB's and CLOB's
 * 
 * @author Binu K
 */

/*
 * Revision History Revision Date Author Description 0.1 Jul 31, 2005 Binu K
 * First draft
 */

public class PersistenceUtils {

	private static Boolean pgEDBConnection = Boolean.TRUE;

	
	/**
	 * Create a BLOB type given a byte array.
	 * 
	 * @param byteData -
	 *            the byte data
	 * @return - a BLOB
	 */
	public static Blob createBlob(byte[] byteData) throws SystemException {
		return resolveLobHelper().createBlob(byteData);
	}

	/**
	 * Create a BLOB type given a binary stream.
	 * 
	 * @param is -
	 *            the binary stream
	 * @return - a BLOB
	 */
	public static Blob createBlob(InputStream is) {
		byte[] byteData = getByteData(is);
		return createBlob(byteData);
	}

	public static <T extends Serializable> byte[] toBytes(T object) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			oos.flush();
			byte[] byteData = baos.toByteArray();
			oos.close();
			return byteData;
		} catch (IOException e) {
			throw new SystemException("CON005", "Persistence Error", e);
		}
	}

	/**
	 * Create a BLOB given a Serializable object
	 * 
	 * @param object - a Serializable Object
	 * @return Blob
	 */
	public static <T extends Serializable> Blob createBlob(T object) {
		return createBlob(toBytes(object));
	}

	/**
	 * Get the byte data underlying the BLOB type.
	 * 
	 * @param blob -
	 *            the BLOB read in from the DB
	 * @return - the byte data
	 */
	public static byte[] getBytes(Blob blob) throws SystemException {
		try {
			InputStream is = blob.getBinaryStream();
			return getByteData(is);
		} catch (SQLException e) {
			throw new SystemException("CON005","Persistence Error", e);
		}

	}

	/**
	 * Get the Serializable Object represented by a BLOB
	 * 
	 * @param blob
	 * @return
	 */
	public static <T extends Serializable> T getObject(Blob blob) {
		try {
			BufferedInputStream bis = new BufferedInputStream(blob.getBinaryStream());
			ObjectInputStream ois = new ObjectInputStream(bis);
			Object object = ois.readObject();
			ois.close();
			return (T) object;
		} catch (SQLException | IOException | ClassNotFoundException e) {
			throw new SystemException("CON005", "Persistence Error", e);
		}
	}

	public static <T extends Serializable> T getObject(byte[] blob) {
		try {
			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(blob));
			Object object = ois.readObject();
			ois.close();
			return (T) object;
		} catch (IOException | ClassNotFoundException e) {
			throw new SystemException("CON005", "Persistence Error", e);
		}
	}
	/**
	 * Create a CLOB type given a char array
	 * 
	 * @param charData -
	 *            the character data
	 * @return - a CLOB
	public static final Clob createClob(String charData) throws SystemException {
		return getConfiguredLOBHandler().createCLOB(charData);
	}



	public static final Clob createClob(char[] charData) throws SystemException {
		CharArrayReader car = new CharArrayReader(charData);
		return createClob(car);
	}


	public static final Clob createClob(Reader reader) throws SystemException {
		String charData = getCharData(reader);
		return createClob(charData);
	}

	 */
	/**
	 * Gets the character data underlying the CLOB.
	 * 
	 * @param clob -
	 *            the CLOB read in from the DB
	 * @return the character data
	 */
	public static String getString(Clob clob) {
		try {
			
			Reader reader = clob.getCharacterStream();
			String charData = getCharData(reader);
			return charData;
		} catch (SQLException e) {
			throw new SystemException("CON005", "Persistence Error", e);
		}

	}

	/**
	 * Gets the character data underlying the CLOB.
	 * 
	 * @param clob -
	 *            the CLOB read in from the DB
	 * @return the character data
	 */
	public static char[] getChars(Clob clob) {
		if (clob != null) {
			try {
				Reader reader = clob.getCharacterStream();
				String charData = getCharData(reader);
				return charData.toCharArray();
			} catch (SQLException e) {
				throw new SystemException("CON005", "Persistence Error", e);
			}
		}else{
			return null;
		}
	}

	private static void close(InputStream is) throws IOException {
		is.close();
	}

	private static void close(OutputStream os) throws IOException {
		os.close();
	}

	/*
	 * Get the byte[] data from a stream
	 */
	private static byte[] getByteData(InputStream is) {
		byte[] byteData = null;
		BufferedInputStream bis = null;
		ByteArrayOutputStream baos = null;
		try {
			bis = new BufferedInputStream(is);
			baos = new ByteArrayOutputStream();
			int bytes = -1;
			while ((bytes = bis.read()) != -1) {
				baos.write(bytes);
			}
			byteData = baos.toByteArray();
			baos.flush();
			close(baos);
			close(bis);
			return byteData;
		} catch (IOException ioe) {
			throw new SystemException("CON005","Persistence Error", ioe);
		}
	}

	/*
	 * Get the character data from the reader
	 */
	private static String getCharData(Reader reader) {
		try {
			BufferedReader bur = new BufferedReader(reader);
			StringWriter sw = new StringWriter();
			int ch = -1;
			String charData = null;
			while ((ch = bur.read()) != -1) {
				sw.write(ch);
			}
			sw.flush();
			//bur.close();
			charData = sw.toString();
			return charData;
		} catch (IOException e) {
			throw new SystemException("CON005", "Persistence Error", e);
		}

	}

	private static LobHelper resolveLobHelper() {
		EntityManager manager = ContextUtil.getInstance().getBean(javax.persistence.EntityManager.class);
		Session hibernateSession = manager.unwrap(Session.class);
		return hibernateSession.getLobHelper();
	}

	/**
	 * This method appends the dynamic search string
	 * passed from client to the current SQL Query
	 * 
	 * @author A-2881
	 * @param finalQuery
	 * @param dynamicQueryString
	 * @return
	 */
	public static String appendDynamicParams(String finalQuery, String dynamicQueryString) {
		if (dynamicQueryString == null)
			return finalQuery;
		throw new UnsupportedOperationException("appendDynamicParams not supported.");
		/*
		try {
			return replaceDynamicPlaceHolders(finalQuery, mapDynamicSearchParams(dynamicQueryString));
		} catch (SystemException e) {
			return finalQuery;
		}
		 */
	}
	
	/**
	 * This method maps the keys present in the search string 
	 * passed from client with the corresponding columns mapped in 
	 * dynamicsearch.mapping.xml
	 * 
	 * @author A-2881
	 * @param dynamicSearchString
	 * @return
	private static String mapDynamicSearchParams(String dynamicSearchString)
			throws SystemException {
		Map<String, DynamicSearchProperties> map = getColumnMappings();
		if (map.size() == 0)
			return null;
		for (String key : map.keySet()) {
			dynamicSearchString = dynamicSearchString.replaceAll(key, map.get(key).getMapping());
		}
		return "AND ( " + dynamicSearchString + " ) ";
	}
	 */
   /**
    * This method replaces the \/*DYNAPLACEHOLDER*\/
	* content in the current query and replaces it with the 
	* search string from client
    * 
    * @author A-2881
    * @param finalQuery
    * @param dynamicQuery
    * @return
    */
	private static String replaceDynamicPlaceHolders(String finalQuery,
			String dynamicQuery) {
		if(dynamicQuery==null)
			return finalQuery;
		 return finalQuery.replaceAll("\\/\\*DYNAPLACEHOLDER\\*\\/", dynamicQuery);
	}

	/**
	 * <br>
	 * This method returns the keys and corresponding properties
	 * for the current numerical screen Id.
	 * 
	 * @author A-2881
	 * @return
	 * @throws SystemException
	private static Map<String, DynamicSearchProperties> getColumnMappings()
			throws SystemException {
		Map<String, DynamicSearchProperties> map = DynamicSearchUtil.getColumnMapping(
				 (String) ContextUtils.getRequestContext().getParameter(AbstractControl.REQ_TRIGGER_POINT));
		return Collections.emptyMap();
	}
	 */

	/**
	 * Method to check if the current connection is a EDB connection.
	 * @author A-2394
	 * @return
	 * @throws SQLException 
	 */
	public static boolean isPostgreEDB() {
		return pgEDBConnection.booleanValue();				
	}
}
