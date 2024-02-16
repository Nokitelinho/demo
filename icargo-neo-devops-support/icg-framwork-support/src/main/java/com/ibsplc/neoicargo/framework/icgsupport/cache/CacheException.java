/**
 * 
 */
package com.ibsplc.neoicargo.framework.icgsupport.cache;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorVO;

/**
 * @author A-1759
 *
 */
public class CacheException extends BusinessException {

	public static final String DATABASE_UNAVAILABLE = "CON001";
	public static final String SERVICE_NOT_ACCESSIBLE = "CON002";
	public static final String RESOURCE_FACTORY_ERROR = "CON003";
	public static final String UNEXPECTED_DB_ERROR = "CON004";
	public static final String UNEXPECTED_SERVER_ERROR = "CON005";
	public static final String ACCESS_DENIED_ERROR = "CON006";
	public static final String NO_MAPPING_FOUND = "MAPPING001";
	public static final String SQL_ERROR = "CON007";
	public static final String RESOURCE_NOT_FOUND = "CON008";
	public static final String REPORT_ERROR = "CON009";

	public CacheException(Collection<ErrorVO> errorsList) {
		super(new ArrayList<>(errorsList));
	}

	public CacheException(String errorCode, String message) {
		super(errorCode, message);
	}

	public CacheException(String errorCode, Throwable exception) {
		super(errorCode, null);
	}

	public CacheException(String errorCode) {
		super(errorCode, null);
	}
	
	public ErrorVO getError() {
		return getErrors().stream().findFirst().orElse(null);
	}

}
