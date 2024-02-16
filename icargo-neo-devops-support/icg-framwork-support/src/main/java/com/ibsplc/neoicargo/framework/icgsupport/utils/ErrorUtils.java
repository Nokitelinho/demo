/**
 * 
 */
package com.ibsplc.neoicargo.framework.icgsupport.utils;

import com.ibsplc.neoicargo.framework.core.lang.error.ErrorVO;

/**
 * @author A-1759
 *
 */
public class ErrorUtils {

	public static ErrorVO getError(String errorCode, Object... errorData) {
		return new ErrorVO(errorCode, errorData.toString());
	}

	public static ErrorVO getError(String errorCode, String errorDescription, Object... errorData) {
		return getError(errorCode, errorData);
	}

	public static ErrorVO getError(String errorCode, String errorDescription, Throwable exception,
			Object... errorData) {
		return getError(errorCode, errorData);
	}

	public static ErrorVO getError(String errorCode, Throwable exception, Object... errorData) {
		return getError(errorCode, errorData);
	}

}
