/*
* @(#) Types.java 1.0 Apr 27, 2005 
* Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
*
* This Software is the proprietary information of IBS Software Services (P) Ltd.
* Use is subject to License terms.
*
*/
package com.ibsplc.xibase.server.framework.persistence.query.parser;

/**
 *An enum represeting the types of Queries supported
 *
 */
/*
* Revision History
* Revision      Date                Author          Description
* 1.0           Apr 27, 2005                A-1456          First draft
*/
public enum Types {

	NATIVE("NATIVE"),OBJECT("OBJECT"),PROCEDURE("PROCEDURE");

	Types(String name) {
	}

	
}
