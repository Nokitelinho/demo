/**
 * 
 */
package com.ibsplc.neoicargo.framework.icgsupport.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @author A-1759
 *
 */
@Getter
@Setter
public class AbstractVO implements Serializable {
	 public static final String OPERATION_FLAG_INSERT = "I";
	  public static final String OPERATION_FLAG_DELETE = "D";
	  public static final String OPERATION_FLAG_UPDATE = "U";
	  public static final String FLAG_YES = "Y";
	  public static final String FLAG_NO = "N";
	  protected String operationFlag;
	  protected String triggerPoint;
	  protected boolean ignoreWarnings;

	  public boolean isInsert() { return "I".equals(this.operationFlag); }
	  
	  public boolean isUpdate() { return "U".equals(this.operationFlag); }
	  
	  public boolean isDelete() { return "D".equals(this.operationFlag); }



}
