
/*
 * MRAAccountingDetailsMapper.java Created on Nov 12, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAAccountingVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-3229
 *
 */
public class MRAAccountingDetailsMapper implements MultiMapper<MRAAccountingVO> {

	/**
	 * @author A-3229
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public List<MRAAccountingVO> map(ResultSet rs) throws SQLException {

		Log log =LogFactory.getLogger("MRA DEFAULTS");
		List<MRAAccountingVO> mraAccountingVOs = new ArrayList<MRAAccountingVO>();
		MRAAccountingVO  mraAccountingVO = null;
		String oldPk=null;
		String newPk=null;

		//Money variable
		Money credit=null;
		Money debit=null;

		try {
			while(rs.next()){

				newPk = new StringBuilder().append(rs.getString("ACCTXNIDR")).append(rs.getString("ACCCOD"))
				.append(rs.getString("ACCNAM")).toString();
				
				if(!(newPk.equals(oldPk)))	{

					/* 
					 * Added By A-3434 for BUG 43144
					 * for eliminating duplicates (Parent)
					 */

					if(mraAccountingVO!=null){

						//increment();						

					}

					oldPk = newPk;
					mraAccountingVO = new MRAAccountingVO();

					mraAccountingVO.setFunctionPoint(rs.getString("FUNPNT"));
					mraAccountingVO.setDetails(rs.getString("AWBDTL"));
					mraAccountingVO.setAccountingMonth(rs.getString("ACCMON"));
					mraAccountingVO.setAccEntryId(rs.getString("ACCTXNIDR"));
					mraAccountingVO.setAccountCode(rs.getString("ACCCOD"));

					credit = CurrencyHelper.getMoney("NZD");
					credit.setAmount(rs.getDouble("CRR"));
					mraAccountingVO.setCreditAmount(credit);

					debit = CurrencyHelper.getMoney("NZD");
					debit.setAmount(rs.getDouble("DRR"));
					mraAccountingVO.setDebitAmount(debit);
					mraAccountingVO.setAccountName(rs.getString("ACCNAM"));
					mraAccountingVO.setAccountingString(rs.getString("ACCSTR"));
					mraAccountingVOs.add(mraAccountingVO);

				}
			}
		}catch(CurrencyException e){
			e.getErrorCode();
		}

		// This part of the code is required to add the last parent
		// The last parent wont be added by the main loop

		if (mraAccountingVO != null) {

			//increment();

		}
		log.exiting("MRAAccountingDetailsMapper","MAP METHOD");
		return mraAccountingVOs;
	}
}