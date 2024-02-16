 /*
 * MailbagAuditTransactionCodeMapper.java Created on Nov 5 2015 by A-5945 for ICRD-119569
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;

public class MailbagAuditTransactionCodeMapper  implements MultiMapper<String>{

	   public List<String> map(ResultSet rs) throws SQLException {
       	List<String> txnCodeDetails = new ArrayList<String>();
       	while(rs.next()){
				String codeAndDescription = new StringBuilder().append(rs.getString("TXNCOD")).
								append("~").append(rs.getString("TXNCODDES")).toString();
				txnCodeDetails.add(codeAndDescription);
       	}
       	if(txnCodeDetails.size() > 0) {
				return txnCodeDetails;
			}
   	    return null;
       }
}
