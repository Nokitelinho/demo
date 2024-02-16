/*
 * GenerateInvoiceMapper.java Created on July 7, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import static com.ibsplc.icargo.framework.util.time.LocalDate.NO_STATION;
import static com.ibsplc.icargo.framework.util.time.Location.NONE;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-3434
 * 
 */

public class GenerateInvoiceMapper implements MultiMapper<String> {

	private static final String CLASS_NAME = "GenerateInvoiceMapper";

	private Log log = LogFactory.getLogger("MRA:DEFAULTS");

	/**
	 * @param resultSet
	 * @throws SQLException
	 * @return List<CN51SummaryVO>
	 */
	public List<String> map(ResultSet resultSet) throws SQLException {

		List<String> datecol = new ArrayList<String>();
		if (resultSet != null) {
			String flag = "false";
			while (resultSet.next()) {

				LocalDate fromDate = new LocalDate(
						NO_STATION, NONE, resultSet.getDate("FRMDATE"));
				LocalDate toDate = new LocalDate(
						NO_STATION, NONE, resultSet.getDate("TODATE"));				
				String blgprd = resultSet.getString("BILPRDCOD");
				LocalDate curdate = new LocalDate(LocalDate.NO_STATION,
						Location.NONE, false);
				log.log(Log.INFO, "\n\n", fromDate);
				log.log(Log.INFO, "\n\ntoDate", toDate);
				log.log(Log.INFO, "\n\ntoDate", blgprd);
				if (("true").equalsIgnoreCase(flag)) {

					String fromDatnext = fromDate.toDisplayDateOnlyFormat();
					String toDatnext = toDate.toDisplayDateOnlyFormat();
					String datesnext = fromDatnext.concat("$")
							.concat(toDatnext).concat("$").concat(blgprd);
					datecol.add(datesnext);
					flag = "true";
					break;
				}
				if (curdate.before(toDate.addDays(1)) && curdate.after(fromDate.addDays(-1))
						&& ("false").equalsIgnoreCase(flag)) {

					String fromDat = fromDate.addDays(+1).toDisplayDateOnlyFormat();
					String toDat = toDate.addDays(-1).toDisplayDateOnlyFormat();
					String dates = fromDat.concat("$").concat(toDat)
							.concat("$").concat(blgprd);
					log.log(Log.INFO, "\n\ndates", dates);
					datecol.add(dates);
					flag = "true";
				}

			}
		}
		/*
		 * while (resultSet.next()) {
		 * 
		 * Date fromDate=resultSet.getDate("FRMDATE"); Date
		 * toDate=resultSet.getDate("TODATE");
		 * 
		 * if(flag!="true"){
		 * 
		 * String fromDatnext=fromDate.toString(); String
		 * toDatnext=toDate.toString(); String
		 * datesnext=fromDatnext.concat("-").concat(toDatnext);
		 * datecol.add(datesnext); flag="true"; }
		 *  }
		 */

		return datecol;

	}

}
