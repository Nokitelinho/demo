/*
 * InvoiceLovFilterQuery.java Created on Jan 9, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceLovVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1556
 *
 */
//Changed NativeQuery to PageableNativeQuery by A-5220 for ICRD-32647
public class InvoiceLovFilterQuery extends PageableNativeQuery<InvoiceLovVO> {
	private Log log = LogFactory.getLogger("MRA_GPABILLING");

	private String baseQuery;
	private InvoiceLovVO invoiceLovVO;
	/**
	 * @param totalRecordCount
	 * @param mapper
	 * @param baseQuery
	 * @param invoiceLovVO
	 * @return 
	 * @throws SystemException
	 */
	//Modified by A-5220 for ICRD-32647 starts
    public InvoiceLovFilterQuery(int pagesize,int totalRecordCount,
    		Mapper<InvoiceLovVO> mapper,
    		String baseQuery,
    		InvoiceLovVO invoiceLovVO) throws SystemException {
        super(pagesize,totalRecordCount, mapper);
      //Modified by A-5220 for ICRD-32647 ends
        log.entering("InvoiceLovFilterQuery","constructor");

        this.baseQuery=baseQuery;
        this.invoiceLovVO=invoiceLovVO;
        log.exiting("InvoiceLovFilterQuery","constructor");
    }

    /**
     * @return String
     */
    public String getNativeQuery() {
    	String companyCode=invoiceLovVO.getCompanyCode();
    	String invoiceNumber=invoiceLovVO.getInvoiceNumber();
    	String gpaCode=invoiceLovVO.getGpaCode();

    	  int index=0;
          this.setParameter(++index,companyCode);
          StringBuilder sbul = new StringBuilder(baseQuery);
          if(invoiceNumber!=null && invoiceNumber.trim().length()>0){
        	  sbul.append(" AND SMY.INVNUM LIKE '");
        	  sbul.append(invoiceNumber.replace('*','%'));
        	  sbul.append('%');
        	  sbul.append("'");
        	 // this.setParameter(++index,invoiceNumber);

          }
          if(gpaCode!=null && gpaCode.trim().length()>0){
        	  sbul.append(" AND SMY.GPACOD LIKE '");
        	  sbul.append(gpaCode.replace('*','%'));
        	  sbul.append('%');
        	  sbul.append("'");
        	  //this.setParameter(++index,gpaCode);
          }
        return sbul.toString();
    }

}
