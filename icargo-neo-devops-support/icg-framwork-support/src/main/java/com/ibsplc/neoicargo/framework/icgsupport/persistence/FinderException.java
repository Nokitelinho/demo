/**
 * 
 */
package com.ibsplc.neoicargo.framework.icgsupport.persistence;

import java.util.ArrayList;
import java.util.List;

import com.ibsplc.neoicargo.framework.core.lang.error.ErrorVO;

import lombok.Getter;

/**
 * @author A-1759
 *
 */
@Getter
public class FinderException extends Exception {

	private String errorCode="OP_FAILED";
	
	private List<ErrorVO> errors = new ArrayList<>();
	/**
	 * 
	 */
	private static final long serialVersionUID = 6811971279437391928L;

	public FinderException() {
		super("OP_FAILED");
		ErrorVO errorVO = new ErrorVO();
		errorVO.setErrorCode(errorCode);
		errors.add(errorVO);
	}

	public FinderException(String msg) {
		super(msg);
		errorCode=msg;
		ErrorVO errorVO = new ErrorVO();
		errorVO.setErrorCode(msg);
		errors.add(errorVO);
	}

	public FinderException(Throwable nestedException) {
		super(nestedException);
		ErrorVO errorVO = new ErrorVO();
		errorVO.setErrorCode(errorCode);
		errors.add(errorVO);
	}
}
