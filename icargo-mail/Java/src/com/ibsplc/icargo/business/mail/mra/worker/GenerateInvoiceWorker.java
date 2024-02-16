/*
 * GenerateInvoiceWorker.java Created on May 5, 2019
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.worker;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.GenerateInvoiceJobScheduleVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GenerateInvoiceFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;
import com.ibsplc.icargo.framework.jobscheduler.worker.RequestWorker;
import com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.worker.GenerateInvoiceWorker.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8061	:	05-05-2019	:	Draft
 */
@Module("mail")
@SubModule("mra")


public class GenerateInvoiceWorker extends RequestWorker {
	
	private Log log = LogFactory.getLogger("MAIL MRA");
	private static final String CLASS = "GenerateInvoiceWorker";
	
	
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.jobscheduler.worker.BaseWorker#execute(com.ibsplc.icargo.framework.jobscheduler.worker.WorkerContext)
	 *	Added by 			:   A-8061 on 05-May-2019
	 * 	Used for 	        :   
	 *	Parameters	        :	@param workercontext
	 *	Parameters	        :	@throws SystemException
	 */
	@Override
	public void execute(WorkerContext workercontext) throws SystemException {
		log.entering(CLASS,"execute");
		
		Collection<PostalAdministrationVO> postalAdministrationVOs = new ArrayList<PostalAdministrationVO>();
		GenerateInvoiceJobScheduleVO GenerateInvoiceJobScheduleVO = (GenerateInvoiceJobScheduleVO) workercontext
				.getJobScheduleVO(); 
		
		GenerateInvoiceFilterVO  generateInvoiceFilterVO = new GenerateInvoiceFilterVO();
		 LogonAttributes logon = ContextUtils.getSecurityContext().getLogonAttributesVO();
		generateInvoiceFilterVO.setCompanyCode(logon.getCompanyCode());
		generateInvoiceFilterVO.setGpaCode(GenerateInvoiceJobScheduleVO.getGpaCode());
		//generateInvoiceFilterVO.setGpaName(GenerateInvoiceJobScheduleVO.getGpaName());
		generateInvoiceFilterVO.setCountryCode(GenerateInvoiceJobScheduleVO.getCountryCode());
		generateInvoiceFilterVO.setBillingPeriodFrom(new LocalDate(LocalDate.NO_STATION,
				Location.NONE, true).setDate( GenerateInvoiceJobScheduleVO.getBillingPeriodFrom() ));
		generateInvoiceFilterVO.setBillingPeriodTo(new LocalDate(LocalDate.NO_STATION,
				Location.NONE, true).setDate( GenerateInvoiceJobScheduleVO.getBillingPeriodTo() ));
		generateInvoiceFilterVO.setBillingFrequency(GenerateInvoiceJobScheduleVO.getBillingFrequency());
		generateInvoiceFilterVO.setInvoiceType(GenerateInvoiceJobScheduleVO.getInvoiceType());
		if("Y".equals(GenerateInvoiceJobScheduleVO.getAddNewFlag())){
			generateInvoiceFilterVO.setAddNew(true);
		}else{
			generateInvoiceFilterVO.setAddNew(false);
		}
		generateInvoiceFilterVO.setCurrentDate(new LocalDate(logon.getAirportCode(),Location.ARP,true));

		generateInvoiceFilterVO.setTransactionCode(GenerateInvoiceJobScheduleVO.getTransactionCode());
		generateInvoiceFilterVO.setInvoiceLogSerialNumber(Integer.parseInt(GenerateInvoiceJobScheduleVO.getInvoiceLogSerialNumber()));
		
		despatchRequest("sendEmailforPAs",postalAdministrationVOs,generateInvoiceFilterVO);
		
		
		log.exiting(CLASS, "execute");
	
		
		
	}
   /**
     * 
     *	Overriding Method	:	@see com.ibsplc.icargo.framework.jobscheduler.worker.BaseWorker#instantiateJobScheduleVO()
     *	Added by 			:   A-8061 on 05-May-2019
     * 	Used for 	        :   
     *	Parameters	        :	@return
   */
	@Override
	public JobScheduleVO instantiateJobScheduleVO() {
		
		log.entering(CLASS, "instantiateJobScheduleVO");
		return new GenerateInvoiceJobScheduleVO();	
		
	}

}
