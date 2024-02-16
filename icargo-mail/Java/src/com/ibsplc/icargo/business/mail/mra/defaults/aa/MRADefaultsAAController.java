/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.defaults.aa.MRADefaultsAAController.java
 *
 *	Created by	:	U-1393
 *	Created on	:	Jan 14, 2020
 *
 *  Copyright 2014 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults.aa;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.ibsplc.icargo.business.cra.accounting.extinterface.vo.SAPInterfaceFileLogVO;
import com.ibsplc.icargo.business.cra.accounting.extinterface.vo.SAPInterfaceFilterVO;
import com.ibsplc.icargo.business.mail.mra.proxy.CRAAccountingProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.MRADefaultProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.shared.defaults.filegenerate.vo.FileGenerateVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.services.jaxws.proxy.exception.WebServiceException;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.airlinebilling.defaults.aa.MRADefaultsAAController.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	U-1393	:	Jan 14, 2020	:	Draft
 */
@Module("mail")
@SubModule("mra")
public class MRADefaultsAAController {

	private Log log = LogFactory.getLogger("MRA:DEFAULTS");
	private static final String CLASS_NAME = "MRADefaultsAAController";
	private static final String MODULE_NAME = "mail.mra.defaults";
	private static final String SAPFI_FILENAME_PREFIX="icargo_aa_gl_";
	private static final String GENERATED_ACCPERIOD = " Generated Successfully for the Accounting Period ";
	private static final String STATUS_OK="OK";
	private static final String EXCEPTION = "Exception while generating SAP file ";
	
	/**
	 * Method		:	MRADefaultsAAController.generateInterfaceFile
	 * Added by 	:	U-1393 on Jan 14, 2020
	 * @param interfaceFilterVO
	 * @throws SystemException
	 */
	public MRADefaultsDAO constructDAO() throws SystemException{
		log.entering(CLASS_NAME, "constructDAO");
		MRADefaultsDAO mraDefaultsDao = null;
		try {
			EntityManager em = PersistenceController.getEntityManager();
			mraDefaultsDao = MRADefaultsDAO.class.cast(em.getQueryDAO(MODULE_NAME));
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
		log.exiting(CLASS_NAME, "constructDAO");
		return mraDefaultsDao;		
	}

	public void generateSAPInterfaceFile(SAPInterfaceFilterVO interfaceFilterVO)
			throws SystemException { 
		log.entering(CLASS_NAME, "generateSAPInterfaceFile");
		if(interfaceFilterVO.isJob()){
					
			LocalDate todate = 	interfaceFilterVO.getToDate();
			interfaceFilterVO.setFromDate(null);
			interfaceFilterVO.setToDate(null);
			
			if(todate!=null){
				//passing previous month
				Calendar calendar=todate.toCalendar();
				if(calendar!=null){
					calendar.add(Calendar.MONTH, -1);
					int year     = calendar.get(Calendar.YEAR);
					String accMon	 = new SimpleDateFormat("MMM").format(calendar.getTimeInMillis() ).toUpperCase();
					interfaceFilterVO.setAccountMonth(accMon+year);
					generateSAPFIInterfaceFile(interfaceFilterVO);	
					
					//passing current month
					SAPInterfaceFilterVO interfaceFilterVOCurrent = new SAPInterfaceFilterVO();
					calendar.add(Calendar.MONTH, 1);
					int accYear     = calendar.get(Calendar.YEAR);
					String accMonNext = new SimpleDateFormat("MMM").format(calendar.getTimeInMillis() ).toUpperCase();
					interfaceFilterVOCurrent.setCompanyCode(interfaceFilterVO.getCompanyCode());
					interfaceFilterVOCurrent.setSubsystem(interfaceFilterVO.getSubsystem());
					interfaceFilterVOCurrent.setFileType(interfaceFilterVO.getFileType());
					interfaceFilterVOCurrent.setRegenerateFlag(interfaceFilterVO.getRegenerateFlag());
					interfaceFilterVOCurrent.setJob(interfaceFilterVO.isJob());
					interfaceFilterVOCurrent.setToDate(interfaceFilterVO.getToDate());
					interfaceFilterVOCurrent.setFromDate(interfaceFilterVO.getFromDate());
					interfaceFilterVOCurrent.setAccountMonth(accMonNext+accYear);
					generateSAPFIInterfaceFile(interfaceFilterVOCurrent);
				}
				
			}
			
							
		}else{
			generateSAPFIInterfaceFile(interfaceFilterVO);
		}
		log.exiting(CLASS_NAME, "generateSAPInterfaceFile");	
	}
	
	public void generateSAPFIInterfaceFile(SAPInterfaceFilterVO interfaceFilterVO)
			throws SystemException  { 
		log.entering(CLASS_NAME, "generateSAPFIInterfaceFile");
		interfaceFilterVO.setInterfaceFilename(generateFileNameForSAPFI());
		SAPInterfaceFileLogVO sapInterfaceFileLogVO = populateSAPInterfaceFileLogVO(interfaceFilterVO);
		try {
		sapInterfaceFileLogVO=	new CRAAccountingProxy().saveSAPInterfaceFileLog(sapInterfaceFileLogVO);
		}
		catch(ProxyException e) {
			throw new SystemException(e.getMessage());
		}
		String status = constructDAO().generateSAPFIFile(interfaceFilterVO);
		log.log(Log.INFO, "status"+status);
		StringBuffer addInfo = new StringBuffer();
		if(interfaceFilterVO.getAccountMonth()!=null){
		addInfo.append(interfaceFilterVO.getInterfaceFilename()).append(GENERATED_ACCPERIOD).
			append(" ").append(interfaceFilterVO.getAccountMonth());
		}else if(interfaceFilterVO.getFromDate()!=null && interfaceFilterVO.getToDate()!=null){
			addInfo.append(interfaceFilterVO.getInterfaceFilename()).append(GENERATED_ACCPERIOD).
		append(" ").append(interfaceFilterVO.getFromDate().toDisplayFormat("dd-MM-YYYY")).append(" To ")
		.append(interfaceFilterVO.getToDate().toDisplayFormat("dd-MM-YYYY"));
		}
		if (!STATUS_OK.equals(status)) {
			sapInterfaceFileLogVO.setRemarks(status);
			sapInterfaceFileLogVO.setInterfaceFilename("");
			try {
				new CRAAccountingProxy().updateInterfaceLogDetails(sapInterfaceFileLogVO,
				SAPInterfaceFileLogVO.FILE_GENERATION_STATUS_FAILED);
			}
			catch(ProxyException e) {
				throw new SystemException(e.getMessage());
			}
		}
		else {
			try {           
				sapInterfaceFileLogVO.setRemarks(addInfo.toString());
				new MRADefaultProxy().sendSAPInterfaceFile(interfaceFilterVO,sapInterfaceFileLogVO);
				status = SAPInterfaceFileLogVO.FILE_GENERATION_STATUS_COMPLETED ;
				sapInterfaceFileLogVO.setRemarks(addInfo.toString());
				log.log(Log.INFO, "file generation status"+status);
			} catch (Exception e) {
				log.log(Log.INFO, "Exception occured "+e.getMessage());
				status = SAPInterfaceFileLogVO.FILE_GENERATION_STATUS_FAILED ;
				sapInterfaceFileLogVO.setRemarks(EXCEPTION);
			} finally {
				try {
					new CRAAccountingProxy().updateInterfaceLogDetails(sapInterfaceFileLogVO,status);
				}
				catch(ProxyException e) {
					throw new SystemException(e.getMessage());
				}
			}
		}
		log.exiting(CLASS_NAME, "generateSAPFIInterfaceFile");	
	}
	
	public String generateFileNameForSAPFI() throws SystemException {
		log.entering(CLASS_NAME, "generateFileNameForSAPFI");
		StringBuilder fileName = new StringBuilder(SAPFI_FILENAME_PREFIX);
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		LocalDate currentDate = new LocalDate(logonAttributes.getStationCode(), Location.STN, true);
		fileName.append(currentDate.toDisplayFormat("yyyyMMdd")).append("_").append(currentDate.toDisplayFormat("hhmmss")).append(".txt");
		log.log(Log.INFO, "SAP File Name generated ---> "+fileName);
		log.exiting(CLASS_NAME, "generateFileNameForSAPFI");
		return fileName.toString();
	}
	
	public SAPInterfaceFileLogVO populateSAPInterfaceFileLogVO(SAPInterfaceFilterVO interfaceFilerVO) throws SystemException {
		log.entering(CLASS_NAME, "populateSAPInterfaceFileLogVO");
		SAPInterfaceFileLogVO sapInterfaceFileLogVO = new SAPInterfaceFileLogVO();
		sapInterfaceFileLogVO.setCompanyCode(interfaceFilerVO.getCompanyCode());
		sapInterfaceFileLogVO.setAccountMonth(interfaceFilerVO.getAccountMonth());
		sapInterfaceFileLogVO.setFromDate(interfaceFilerVO.getFromDate());
		sapInterfaceFileLogVO.setToDate(interfaceFilerVO.getToDate());
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		sapInterfaceFileLogVO.setUserCode(logonAttributes.getUserId());
		LocalDate currentTime = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
		sapInterfaceFileLogVO.setGenerationTime(currentTime);
		sapInterfaceFileLogVO.setGenerationStatus(SAPInterfaceFileLogVO.FILE_GENERATION_STATUS_FAILED);
		sapInterfaceFileLogVO.setFileType(interfaceFilerVO.getFileType());
		sapInterfaceFileLogVO.setSubSystem(interfaceFilerVO.getSubsystem());
		sapInterfaceFileLogVO.setInterfaceFilename(interfaceFilerVO.getInterfaceFilename());
		log.exiting(CLASS_NAME, "populateSAPInterfaceFileLogVO");
		return sapInterfaceFileLogVO;
	}
	
	public void sendSAPInterfaceFile(SAPInterfaceFilterVO interfaceFilterVO,SAPInterfaceFileLogVO sapInterfaceFileLogVO) throws  SystemException, ProxyException{
	  	FileGenerateVO generateVo = new FileGenerateVO();
		generateVo.setFileName(interfaceFilterVO.getInterfaceFilename());
		generateVo.setFileType("SAPFIM");
		generateVo.setProcessId(interfaceFilterVO.getInterfaceFilename());
		generateVo.setCompanyCode(interfaceFilterVO.getCompanyCode());
		generateVo.setSerialNumber(1);
		String status = null;
        try {
        	status = new SharedDefaultsProxy().generateFile(generateVo);
		} catch (WebServiceException e) {
			throw new SystemException(e.getMessage(), e);
		}  
        if("S".equals(status)) {
        	status = SAPInterfaceFileLogVO.FILE_GENERATION_STATUS_COMPLETED ;
        }
        else {
        	status = SAPInterfaceFileLogVO.FILE_GENERATION_STATUS_FAILED ;
        	sapInterfaceFileLogVO.setRemarks("Generated file "+interfaceFilterVO.getInterfaceFilename()+" sending to SAP is failed");
        }
        new CRAAccountingProxy().updateInterfaceLogDetails(sapInterfaceFileLogVO,status);
	}
}
