/*
 * ConnectionUtil.java Created on 25-Apr-2018
 *
 * Copyright 2017 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.framework.probe.sql;

import com.ibsplc.neoicargo.framework.probe.sql.spy.ConnectionSpy;
import com.ibsplc.neoicargo.framework.probe.sql.spy.RdbmsSpecifics;
import com.ibsplc.neoicargo.framework.probe.sql.spy.Spy;
import com.ibsplc.neoicargo.framework.probe.sql.spy.agents.SqlPrincipalAgent;

import javax.sql.XAConnection;
import javax.transaction.xa.XAResource;
import java.sql.Connection;
import java.sql.SQLException;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			25-Apr-2018       		Jens J P 			First Draft
 */

/**
 * @author Jens J P
 *
 */
public class ConnectionUtil {

	public static Connection wrap(Connection conn, SqlPrincipalAgent agent) {
		return new ConnectionSpy(conn, agent);
	}


	
}
