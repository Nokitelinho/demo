/*
* @(#) Procedure.java 1.0 Apr 12, 2005 
* Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
*
* This Software is the proprietary information of IBS Software Services (P) Ltd.
* Use is subject to License terms.
*
*/
package com.ibsplc.xibase.server.framework.persistence.query;

import com.ibsplc.xibase.server.framework.persistence.query.sql.SqlType;

/**
 * The interface for all native procedures.
 *
 */
/*
* Revision History
* Revision      Date                Author          Description
* 1.0           Apr 12, 2005        Binu K          First draft
*/
public interface Procedure {
	/**
	 * Set a parameter to the query at the specified position
	 * 
	 * @param pos - the postion at which to set
	 * @param value - the value to set
	 * @return the instance of Procedure with parameter set
	 */
	Procedure setParameter(int pos, Object value);
	
	/**
	 * Set an OUT parameter to the procedure at the specified position
	 * 
	 * @param pos- the postion at which to set
	 * @param sqlType - The type of the OUT paramater
	 * @return the instance of Procedure with parameter set
	 */
	Procedure setOutParameter(int pos, SqlType sqlType);
	
	/**
	 * Execute the procedure
	 *
	 */
	void execute();
	
	/**
	 * Get the OUT parameter at the specified position
	 * 
	 * @param pos - the postion at which the OUT parameter was set
	 * @return - the rerurn of execution of the prcoedure
	 */
	Object getParameter(int pos);
    
    /**
     * Make the prciedure sensitive to previous updates made within 
     * the transaction. 
     * 
     * @param isSensitiveToUpdates
     * @return- the instance of Procedure with the value set
     */
    Procedure setSensitivity(boolean isSensitiveToUpdates) ;
    
    /**
     * Indicates if  the query is senstitive
     * @return
     */
    boolean isSensitive();    

    Procedure setNullParameter(int pos, int type);

}