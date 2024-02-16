/**
 * 
 */
package com.ibsplc.neoicargo.framework.icgsupport.lang;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.neoicargo.framework.core.lang.DomainException;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorVO;

/**
 * @author A-1759
 *
 */
public class BusDomainException extends DomainException {

	private final List<ErrorVO> errors = new ArrayList<>();

	public BusDomainException(String errorCode, String message) {
		super(errorCode, message);
		ErrorVO errorVO = new ErrorVO();
		errorVO.setErrorCode(errorCode);
		errorVO.setDefaultMessage(message);
		errors.add(errorVO);
	}

	public List<ErrorVO> getErrors() {
		return errors;
	}

	public void addError(ErrorVO errorVO) {
		if (errorVO == null) {
			throw new NullPointerException("ErrorVO is NULL !");
		}
		this.errors.add(errorVO);
	}

	public void addErrors(Collection<ErrorVO> errorVOs) {
		for (ErrorVO errorVO : errorVOs) {
			addError(errorVO);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibsplc.neoicargo.framework.core.lang.DomainException#type()
	 */
	@Override
	public Type type() {
		return Type.BAD_REQUEST;
	}

}
