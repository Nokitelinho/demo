/*
 * StockcontrolDefaultsProxyException.java Created on Jan 3, 2007
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */


package com.ibsplc.icargo.business.mail.operations;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;

/**
 *
 * @author A-1739
 */
public class StockcontrolDefaultsProxyException extends BusinessException {
  
  public static final String NO_AWBSTOCK = 
      "mailtracking.defaults.attachawb.msg.err.noawbstockforstockholder";
  
  public static final String STOCKHOLDER_NO_STOCK =
      "mailtracking.defaults.attachawb.msg.err.nostockforstockholder";
  public static final String NO_STHFOR_AGENT = 
      "mailtracking.defaults.attachawb.msg.err.nostockholderforagent";
  
  public static final String INVALID_STOCKHOLDER = 
      "mailtracking.defaults.attachawb.msg.err.invalidstockholder";
  
  public static final String NOAWB_INSTOCK = 
      "mailtracking.defaults.attachawb.msg.err.noawbinstock";
  
  public static final String DOC_NOTOF_STH = 
      "mailtracking.defaults.attachawb.msg.err.documentnotofstockholder";
  
  public static final String NO_AGENT_CODE = 
	      "mailtracking.defaults.attachawb.msg.err.noAgentCode";
  /**
   * Creates a new instance of StockcontrolDefaultsProxyException
   */
  public StockcontrolDefaultsProxyException() {
    super();
  }
  
  public StockcontrolDefaultsProxyException(String errorCode) {
    super(errorCode);
  }
  
  /**
   *
   */
  public  StockcontrolDefaultsProxyException(
      String errorCode, Object[] errorData) {
  }
  
}
