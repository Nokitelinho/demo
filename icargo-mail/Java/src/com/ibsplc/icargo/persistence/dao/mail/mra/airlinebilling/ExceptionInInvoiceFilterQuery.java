/* ExceptionInInvoiceFilterQuery.java Created on Feb 19, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.ExceptionInInvoiceFilterVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *
 * @author A-2518
 *
 */
public class ExceptionInInvoiceFilterQuery extends NativeQuery{
	private Log log = LogFactory.getLogger("MRA_GPABilling");

    private String baseQuery;
    private ExceptionInInvoiceFilterVO exceptionInInvoiceFilterVO;
    /**
	 * @param baseQuery
	 * @param exceptionInInvoiceFilterVO
	 * @throws SystemException
	 */
    public ExceptionInInvoiceFilterQuery(String baseQuery,ExceptionInInvoiceFilterVO exceptionInInvoiceFilterVO) throws SystemException {
        super();
    	log.entering("ExceptionInInvoiceFilterQuery","constructor");
		this.exceptionInInvoiceFilterVO = exceptionInInvoiceFilterVO;
		this.baseQuery = baseQuery;
		log.exiting("ExceptionInInvoiceFilterQuery","constructor");

    }
    /*Added by A-2391*/

    /* (non-Javadoc)
     * @see com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery#getNativeQuery()
     */
    /**
	 * @return String
	 */
    public String getNativeQuery() {
    	log.entering("GPABillingDetailsFilterQuery","getNativeQuery");
    	String companyCode = exceptionInInvoiceFilterVO.getCompanyCode();
    	String airlineCode=exceptionInInvoiceFilterVO.getAirlineCode();
    	String invoiceNumber=exceptionInInvoiceFilterVO.getInvoiceNumber();
    	String clearancePeriod=exceptionInInvoiceFilterVO.getClearancePeriod();
    	String exceptionStatus=exceptionInInvoiceFilterVO.getExceptionStatus();
    	String memoCode=exceptionInInvoiceFilterVO.getMemoCode();
    	String memoStatus=exceptionInInvoiceFilterVO.getMemoStatus();
    	
    	//String contractCurrency=exceptionInInvoiceFilterVO.getContractCurrency();

    	int index = 0;
		this.setParameter( ++index, companyCode );
		this.setParameter( ++index, companyCode );
		
		
		
		
		StringBuilder sbul = new StringBuilder(baseQuery);

		if ( airlineCode != null && airlineCode.trim().length() > 0  ) {
			sbul.append( "AND EXP.ARLCOD = ? ");
			this.setParameter( ++index, airlineCode );
		}
		if ( invoiceNumber != null && invoiceNumber.trim().length() > 0  ) {
			sbul.append( "AND EXP.INVNUM = ? ");
			this.setParameter( ++index, invoiceNumber );
		}
		if ( clearancePeriod != null && clearancePeriod.trim().length() > 0  ) {
			sbul.append( "AND EXP.CLRPRD = ? ");
			this.setParameter( ++index, clearancePeriod );
		}
		if ( exceptionStatus != null && exceptionStatus.trim().length() > 0  ) {
			sbul.append( "AND EXP.EXPSTA = ? ");
			this.setParameter( ++index, exceptionStatus );
		}
		if ( memoCode != null && memoCode.trim().length() > 0  ) {
			sbul.append( "AND EXP.MEMCOD = ? ");
			this.setParameter( ++index, memoCode );
		}
		if ( memoStatus != null && memoStatus.trim().length() > 0  ) {
			sbul.append( "AND EXP.MEMSTA = ? ");
			this.setParameter( ++index, memoStatus );
		}
//		if ( contractCurrency != null && contractCurrency.trim().length() > 0  ) {
//			sbul.append( "AND EXP.CRTCURCOD = ? ");
//			this.setParameter( ++index, contractCurrency );
//		}

		return sbul.toString();
    }


}
