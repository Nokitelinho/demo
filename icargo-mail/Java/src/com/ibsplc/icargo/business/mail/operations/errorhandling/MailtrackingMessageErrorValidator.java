/*
 * MailTrackingDefaultsServiceImpl.java Created on 20-03-2014
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.errorhandling;
import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.MailUploadController;
import com.ibsplc.icargo.business.mail.operations.vo.MailFlightSummaryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailUploadVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailWebserviceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.tx.txmonitor.validator.AbstractErrorValidator;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
public class MailtrackingMessageErrorValidator extends AbstractErrorValidator{
	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ibsplc.xibase.server.framework.event.action.ActionBuilder#doAction
	 * (java.lang.String[], java.lang.String[])
	 */
	//public Collection<String> saveAndProcessMailBags(ScannedMailDetailsVO scannedMailDetailsVO)	throws SystemException {
	//public Collection<String> saveMailUploadDetails(Collection<MailUploadVO> mailBagVOs,String scanningPort)throws SystemException {
	public Collection<String> saveMailUploadDetails(Collection<MailUploadVO> mailBagVOs,String scanningPort)throws SystemException {
		Collection<String> errors = getErrorCodes();
		log.log(Log.FINE, "Inside validator calll...................................");
		log.log(Log.FINE, "Business modules need to validate the error codes and return only the error codes which needs to be logged.............");
		
		log.log(Log.FINE, "Errors are...................................",errors);
		
		setErrorCodes(errors);
		log.log(Log.FINE, "Inside validator calll...................................");
		
		return errors;
	}

	public Collection<String> saveMailDetailsFromJob(Collection<MailUploadVO> mailBagVOs,String scanningPort)throws SystemException {
		
		log.log(Log.FINE, "Inside validator calll saveMailDetailsFromJob...................................");
		Collection<String> errors =saveMailUploadDetails(mailBagVOs,scanningPort);
		log.log(Log.FINE, "Errors are...................................",errors);
		
		
		setErrorCodes(errors);
		log.log(Log.FINE, "Inside validator calll saveMailDetailsFromJob..................................");
		
		return errors;
	}
	public Collection<String> performMailOperationForGHA(
			Collection<MailWebserviceVO> webServicesVos, String scanningPort) throws SystemException
	  {
	
	    this.log.log(3, "Inside validator calll performMailOperationForGHA...................................");
	    Collection<String> errors = getErrorCodes();

	    setErrorCodes(errors);
	    this.log.log(3, "Inside validator calll performMailOperationForGHA..................................");

	    return errors;
	  }

	public Collection<String> saveMailUploadDetailsForAndroid(MailUploadVO mailBagVOs,String scanningPort)throws SystemException {
		Collection<String> errors = getErrorCodes();
		log.log(Log.FINE, "Inside validator calll...................................");
		log.log(Log.FINE, "Business modules need to validate the error codes and return only the error codes which needs to be logged.............");
		
		log.log(Log.FINE, "Errors are...................................",errors);
		
		setErrorCodes(errors);
		log.log(Log.FINE, "Inside validator calll...................................");
		
		return errors;
	}
	/**
	 * @author A-7371
	 * @param mailBagVOs
	 * @return
	 * @throws SystemException
	 */
	public Collection<String> validateMailBagDetails(MailUploadVO mailBagVOs)throws SystemException {
		Collection<String> errors = getErrorCodes();
		log.log(Log.FINE, "Inside validator calll...................................");
		log.log(Log.FINE, "Business modules need to validate the error codes and return only the error codes which needs to be logged.............");
		
		log.log(Log.FINE, "Errors are...................................",errors);
		
		setErrorCodes(errors);
		log.log(Log.FINE, "Inside validator calll...................................");
		
		return errors;
	}
	/**
	 * @author A-8061
	 * @param mailFlightSummaryVO
	 * @param eventCode
	 * @return
	 * @throws SystemException
	 */
	public  Collection<String> performMailAWBTransactions(MailFlightSummaryVO mailFlightSummaryVO,String eventCode)throws SystemException {
		Collection<String> errors = getErrorCodes();
		log.log(Log.FINE, "Inside validator calll...................................");
		log.log(Log.FINE, "Business modules need to validate the error codes and return only the error codes which needs to be logged.............");
		
		log.log(Log.FINE, "Errors are...................................",errors);
		
		setErrorCodes(errors);
		log.log(Log.FINE, "Inside validator calll...................................");
		
		return errors;
	}

	 //*Added as part of ICRD-229584 starts
		public Collection<String> getManifestInfo(MailbagVO MailBagVO) throws SystemException
		{
			log.log(Log.FINE, "Inside validator calll...................................");
			Collection<String> errors = getErrorCodes();
			log.log(Log.FINE, "Errors are...................................",errors);
			setErrorCodes(errors);
			return errors;
		}
		public Collection<String> performErrorStampingForFoundMailWebServices(MailUploadVO mailBagVOs,String scanningPort)throws SystemException 
		  {
			
		    this.log.log(3, "Inside validator calll performErrorStampingForFoundMailWebServices...................................");
		    Collection<String> errors = saveMailUploadDetailsForAndroid(mailBagVOs, scanningPort);
		    setErrorCodes(errors);
		    this.log.log(3, "Inside validator calll performMailOperationForGHA..................................");

		    return errors;
		  }
		//Added as part of ICRD-229584 ends*/
}
