/*
 * @(#)OperationFailedException 0.1 07/05/2002
 * Copyright 2002 IBS Software Services . All Rights Reserved.
 * 
 * This Software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to License terms.   
 */
package com.ibsplc.xibase.server.framework.exceptions;


import com.ibsplc.neoicargo.framework.core.lang.error.ErrorVO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * The exception thrown when an operation fails in the transaction manager.
 */

/*
 * Revision History Revision Date Author Description - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
 * 0.1 07/05/2002 TCC Created the class
 */
public class OperationFailedException extends Exception {

	private static final long serialVersionUID = 69140786278542817L;

	/** Access violation exception is occured * */
	public static final String ACCESS_VIOLATION = "ACCESS_VIOLATION";

	/** Security Module is un reachable * */
	public static final String SECURITY_UNREACHABLE = "SECURITY_UNREACHABLE";

	/** Session Handling failed * */
	public static final String SESSION_HANDLE = "SESSION_HANDLE";

	/** Authentication failed Exception * */
	public static final String AUTH_FAILED = "AUTH_FAILED";

	/** Authentication failed Exception * */
	public static final String OP_FAILED = "OP_FAILED";

	/** String format of the error* */
	private String errorCode = "OP_FAILED";

	/** One of the Tx Manager failed * */
	public static final String TX_FAILED = "TX_FAILED";	

	/**
	 * All transactions in this product has been blocked. This code is set by
	 * BlockedTransactionsManager when a transaction is configured to be blocked
	 */
	public static final String PRODUCT_BLOCKED = "tx.block.product";
	
	/**
	 * All transactions in this subproduct has been blocked. This code is set by
	 * BlockedTransactionsManager when a transaction is configured to be blocked
	 */
	public static final String SUBPRODUCT_BLOCKED = "tx.block.subproduct";	
	
	/**
	 * An action has been blocked. This code is set by
	 * BlockedTransactionsManager when a transaction is configured to be blocked
	 */
	public static final String ACTION_BLOCKED = "tx.block.action";	
	

	/*
	 * The collection of errors for this exception
	 */
	private Collection<ErrorVO> errors;

	/**
	 * Constructor. create a new IresException object
	 */
	public OperationFailedException() {
	}

	/**
	 * Constructor. create a new OperationFailedException with the specified
	 * message.
	 * 
	 * @param msg
	 *            msg to use for the exception
	 * @depricated
	 */
	public OperationFailedException(String msg) {
		super(msg);
	}

	/**
	 * Constructor. create a new OperationFailedException with a nested
	 * Exception
	 * 
	 * @param nestedException
	 *            a exception to nest within the IresException
	 * @depricated
	 */
	public OperationFailedException(Throwable nestedException) {
		super(nestedException);
	}

	public OperationFailedException(String message, Collection<ErrorVO> errorVOs) {
		super(message);
		addErrors(errorVOs);
	}

	/**
	 * This is the only Allowable Constructor . All others are created just for
	 * backword compitability
	 * 
	 *            message to use
	 */
	public OperationFailedException(String message, String errorCode) {
		super(message);
		this.errorCode = errorCode;
		ErrorVO errorVO = new ErrorVO(errorCode);
		errorVO.setDefaultMessage(message);
		addError(errorVO);
	}

	public OperationFailedException(String errorCode, Object[] errorData) {
		ErrorVO errorVO = new ErrorVO(errorCode);
		errorVO.setErrorData(errorData);
		addError(errorVO);
	}

	/**
	 * Constructor. create a new OperationFailedException with a nested
	 * Exception
	 * 
	 * @param errorCode
	 *            an error code
	 * @param nestedException
	 *            a exception to nest within the OperationFailedException
	 */
	public OperationFailedException(String errorCode, Throwable nestedException) {
		super(nestedException);
		this.errorCode = errorCode;
		ErrorVO errorVO = new ErrorVO(errorCode);
		addError(errorVO);
	}

	/**
	 * Returns the error code
	 * 
	 * @String message
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * Add an error to this message
	 * 
	 * @param errorVO
	 *            The error
	 */
	public void addError(ErrorVO errorVO) {
		if (errors == null) {
			errors = new ArrayList<ErrorVO>();
		}
		if (errorVO == null) {
			throw new NullPointerException("ErrorVO is NULL !");
		}
		errors.add(errorVO);
		if (errorCode == null)
			errorCode = errorVO.getErrorCode();
	}

	/**
	 * Add several errors to this message
	 * 
	 * @param errorVOs
	 *            Errors
	 */
	public void addErrors(Collection<ErrorVO> errorVOs) {
		if (errors == null) {
			errors = new ArrayList<ErrorVO>();
		}
		for (ErrorVO errorVO : errorVOs) {
			addError(errorVO);
		}
	}

	/**
	 * @return Returns the errors.
	 */
	public Collection<ErrorVO> getErrors() {
		Collection<ErrorVO> actualErrors = null;
		if (errors == null) {
			ErrorVO defaultError = new ErrorVO(this.errorCode);
			actualErrors = Collections.singletonList(defaultError);
		} else {
			actualErrors = errors;
		}
		return actualErrors;
	}

}
