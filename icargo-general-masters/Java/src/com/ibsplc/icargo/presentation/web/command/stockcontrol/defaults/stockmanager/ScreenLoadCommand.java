/*
 * ScreenLoadCommand.java Created on Jan 17, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.stockmanager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.shared.document.vo.DocumentFilterVO;
import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAllocationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.document.DocumentTypeDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.StockManagerSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.StockManagerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1619
 *
 */
public class ScreenLoadCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("STOCK MANAGER");
	
	private static final String SCREENLOAD_SUCCESS = 
		"screenload_stockmanager_success";

	private static final String MODULE_NAME = "stockcontrol.defaults";

	private static final String SCREEN_ID = "stockcontrol.defaults.stockmanager";

	private static final String SYSTEMPARAMETERVALUE = "stock.defaults.defaultstockholdercodeforcto";
	/**
	 * execute method 
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ScreenloadCommandStockManager", "execute");
    	
    	//Getting Logon Attributes
    	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
    	String companyCode = logonAttributes.getCompanyCode();
    	
    	StockManagerForm form = (StockManagerForm)invocationContext.screenModel;
    	StockManagerSession session = getScreenSession(MODULE_NAME, SCREEN_ID);
    	AreaDelegate areaDelegate = getAreaDelegate();
    	SharedDefaultsDelegate sharedDefaultsDelegate = getSharedDefaultsDelegate();
    	
    	Collection<String> stockHolderCodes = new ArrayList<String>();
		stockHolderCodes.add(SYSTEMPARAMETERVALUE);
		
		Map<String, String> stockHolderCodeMap = null;
		
		Collection<String> homeAirlineCodes = new ArrayList<String>();
		homeAirlineCodes.add("stock.defaults.homeairlinecode");
    	Map<String, String> homeAirlineCodeMap = null;		
		try {
			stockHolderCodeMap = areaDelegate.
			findAirportParametersByCode(
					logonAttributes.getCompanyCode(),
					logonAttributes.getAirportCode(), stockHolderCodes);
			homeAirlineCodeMap = sharedDefaultsDelegate.
					findSystemParameterByCodes(homeAirlineCodes);
			log.log(Log.FINE, " keys in map---> ", stockHolderCodeMap.keySet());
			log.log(Log.FINE, " values in map---> ", stockHolderCodeMap.values());
		} catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
			// To be reviewed Auto-generated catch block
//printStackTrrace()();
		}
		
		if(homeAirlineCodeMap != null) {
			session.setHomeairlinecode((String)
					homeAirlineCodeMap.get("stock.defaults.homeairlinecode"));
		}
		if(stockHolderCodeMap != null) {
			session.setStockHolderCode((String)
					stockHolderCodeMap.get(SYSTEMPARAMETERVALUE));
		}
		
		DocumentTypeDelegate documentTypeDelegate = new DocumentTypeDelegate(); 
		Collection<DocumentVO> documentCol = new ArrayList<DocumentVO>();

    	DocumentFilterVO filterVO =new DocumentFilterVO();
	    filterVO.setCompanyCode(companyCode);

		try {
			//docTypes = (HashMap<String, Collection<String>>)
				//documentTypeDelegate.findAllDocuments(companyCode);
			documentCol = documentTypeDelegate.findDocumentDetails(filterVO);
		}catch(BusinessDelegateException businessDelegateException) {	    	
			handleDelegateException(businessDelegateException);
		}	    
		
		HashMap<String, Collection<String>> docTypes = 
			(HashMap<String, Collection<String>>)getMapForDocument(documentCol);
		Collection<String> key = (Collection<String>)docTypes.keySet();	
	    ArrayList<String> keys = new ArrayList<String>(key);	    
	    Collection<String> documentSubtype=docTypes.get(keys.get(0));
	    form.setDocumentType(keys.get(0).toString());
	    form.setAirline("");
	    form.setDocumentSubType(((List<String>)documentSubtype).get(0).toString());
	    
		session.setDocumentValues(docTypes);
		session.setDocumentVO(documentCol);

		//added to populate Summary Count from when called from Stock List
		StockAllocationVO stockAllocationVO = session.getStockAllocationVO();
		form.setScreenStatusFlag(ComponentAttributeConstants.
				SCREEN_STATUS_SCREENLOAD);
		if(stockAllocationVO != null) {
			Collection<RangeVO> newColl = new ArrayList<RangeVO>();
			long count = 0;
			newColl = stockAllocationVO.getRanges();
			if(newColl!=null) {
				for(RangeVO iter : newColl) {
					count += iter.getNumberOfDocuments();
				}				
			}
			for(DocumentVO docvo:documentCol) {    				
        		if(docvo.getDocumentSubType().trim().equals(stockAllocationVO.getDocumentSubType().trim())) {
        			form.setDocumentSubType(docvo.getDocumentSubTypeDes());        			
        		}        		
        	}   
			form.setDocumentType(stockAllocationVO.getDocumentType());			
			form.setSummaryCount(count+"");
			form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);			
		}
		
		
		invocationContext.target = SCREENLOAD_SUCCESS;		
		log.exiting("ScreenloadCommandStockManager", "execute");    	
    }
    
	// private methods
	/**
	 * @return AreaDelegate
	 */
	private AreaDelegate getAreaDelegate() {		
		return new AreaDelegate();
	}
	/**
	 * @return SharedDefaultsDelegate
	 */
	private SharedDefaultsDelegate getSharedDefaultsDelegate() {		
		return new SharedDefaultsDelegate();
	}
	
	/**
	 * @param documentCol
	 * @return Map<String, Collection<String>>
	 */
	private Map<String, Collection<String>> getMapForDocument(Collection<DocumentVO> documentCol) {
    	
    	Map<String,Collection<String>> documentMap=
			new HashMap<String,Collection<String>>();
		String docType = "";
		Collection<String> arr=null;
		for(DocumentVO vo:documentCol){
			docType=vo.getDocumentType();
			if(documentMap.get(docType)==null){
				arr=new ArrayList<String>();
				for(DocumentVO documentVO:documentCol){
					if(docType.equals(documentVO.getDocumentType())){
						arr.add(documentVO.getDocumentSubTypeDes());
					}		
				}
				documentMap.put(docType,arr);
			}
		}
		return documentMap;    	
    }

}
