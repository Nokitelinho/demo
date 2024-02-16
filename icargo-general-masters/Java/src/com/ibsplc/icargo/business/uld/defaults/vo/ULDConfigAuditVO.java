/*
 * ULDConfigAuditVO.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.vo;

import java.io.Serializable;

import com.ibsplc.xibase.server.framework.audit.vo.AuditVO;

/**
 * @author A-1883
 *
 */
public class ULDConfigAuditVO extends AuditVO implements Serializable{
	/**
	 * @param argA
	 * @param argB
	 * @param argC
	 */
	public ULDConfigAuditVO(String argA, String argB, String argC) {
		super(argA, argB, argC);
		// To be reviewed Auto-generated constructor stub
	}
}
