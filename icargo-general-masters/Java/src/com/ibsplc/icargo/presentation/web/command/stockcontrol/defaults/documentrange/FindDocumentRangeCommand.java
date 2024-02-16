/**
 * FindDocumentRangeCommand.java Created on June 13, 2014
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.documentrange;

import java.util.Collection;

import com.ibsplc.icargo.business.shared.document.vo.DocumentFilterVO;
import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.document.DocumentTypeDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.DocumentRangeForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * The Class FindDocumentRangeCommand.
 *
 * @author A-4777
 */ 
public class FindDocumentRangeCommand  extends BaseCommand{
	
	/** The log. */
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	public void execute(InvocationContext invocationContext)throws CommandInvocationException {
		log.entering("FindDocumentRangeCommand", "execute--->");
		String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
		DocumentRangeForm documentRangeForm = (DocumentRangeForm) invocationContext.screenModel;
		/*
		 * Call for finding Document Details - Format String value
		 */
			DocumentTypeDelegate documentTypeDelegate = new DocumentTypeDelegate();
			Collection<DocumentVO> documentVOs = null;
			DocumentFilterVO documentFilterVO = new DocumentFilterVO();
			documentFilterVO.setCompanyCode(companyCode);	
			 try {				 			
				 documentVOs = documentTypeDelegate.findDocumentDetails(documentFilterVO);
			} catch(BusinessDelegateException e) {
				handleDelegateException(e);
			}			
			
			if(documentVOs != null && documentVOs.size() > 0){
				for(DocumentVO documentVO : documentVOs) {
					if(documentRangeForm.getDocType().equals(documentVO.getDocumentType())) {
						String documentFormatString = documentVO.getFormatString();	
						String documentFormat = documentVO.getDocumentFormat();
						int range = 0;
						if ((DocumentVO.DOCUMENT_NUMVALIDATOR).equals(documentFormat)){
							log.log(Log.INFO, "Numeric Format Found------> ");	
							String ns = "";																			
							String splitNum[] = null;
							if(documentFormatString.length() > 0) {
								if(documentFormatString.contains("...")) {
									//N1...100
									splitNum = documentFormatString.split("\\.\\.\\.");								
									range = Integer.parseInt(splitNum[1]);
									documentRangeForm.setDocumentRange(String.valueOf(range));	
									
								} else {
									//N7									
										ns = documentFormatString.substring(1);									
										documentRangeForm.setDocumentRange(ns);									
								}
							}
							
						} else if((DocumentVO.DOCUMENT_ALPHANUMVALIDATOR).equals(documentFormat)){
							range = findDocumentMaxLength(documentFormatString);
							documentRangeForm.setDocumentRange(String.valueOf(range));		
							
						}
						log.log(Log.INFO, "Range Got as------> " +documentRangeForm.getDocumentRange());
									
						
					}					
					
				}
				
				
			}			
		 
		log.exiting("FindDocumentRangeCommand", "execute");
		invocationContext.target = "find_success";
	}
	/**
	 * @author A-4777
	 * @param documentFormatString
	 * @return
	 */
	private int findDocumentMaxLength(String documentFormatString) {	
		log.entering("FindDocumentRangeCommand", "findDocumentMaxLength");
		/**
		 * Method for finding document format
		 */
		String documentString = findDocumentFormat(documentFormatString);
		log.log(Log.INFO, "Document String Obtained------> " +documentString);	
		String splitString[] = null;		
		StringBuilder splitAlphaString = new StringBuilder("");
		StringBuilder splitNumString = new StringBuilder("");		
		int maxAlphaValue = 0;		
		int maxNumValue = 0;
		int maxRange = 0;
		if(documentString.length() > 0) {
			//A1...2N5...100
			if(documentString.contains("}{")) {
				splitString = documentString.split("\\}\\{");	
				splitAlphaString = new StringBuilder(splitString[0]);
				splitNumString = new StringBuilder(splitString[1]);
				/**Eg:
				 * Checking Alpha part : A10...100N5...200
				 * {10,100}
				 */
				if(splitAlphaString.toString().contains(",")) {	
					 String splitAlpha[] = null;
					 splitAlpha = splitAlphaString.toString().split(",");					
					 maxAlphaValue = Integer.parseInt(splitAlpha[1]);								
					
				}
				/**Eg:
				 * Checking Alpha part : A100N2...50
				 * {100}
				 */
				
				else {
					 maxAlphaValue = Integer.parseInt(splitAlphaString.substring(1));
					
				}
				/**Eg:
				 * Checking Numeric part : A5...10N20...200
				 * {20,200}
				 */
				if(splitNumString.toString().contains(",")) {	
					 String splitNum[] = null;
					 splitNum = splitNumString.toString().split(",");					
					 maxNumValue = Integer.parseInt(splitNum[1].substring(0,splitNum[1].length()-1));								
					
				}/**Eg:
				 * Checking Numeric part : A5...10N500
				 * {500}
				 */ 
				else {
					maxNumValue = Integer.parseInt(splitNumString.substring(0,splitNumString.length()-1));	
				}
				maxRange = maxAlphaValue + maxNumValue;
				
			}
			/**
			 * Checking : A100
			 * {100}
			 */
			 
			else {
				maxRange = Integer.parseInt(documentString.substring(1,documentString.length()-1));
				
			}
	    }
		
		return maxRange;
		
	}
	/**
	 * @author A-4777
	 * @param documentFormatString
	 * @return
	 */
	private String findDocumentFormat(String documentFormatString) {
		log.entering("FindDocumentRangeCommand", "findDocumentFormat");
		StringBuilder reFormat=new StringBuilder("");
		int ii=0;
		String partialNo = "";
		while(ii<documentFormatString.length()){
			int jj=documentFormatString.charAt(ii);	
			if(jj<60){
				if(jj != 46){
					String no = String.valueOf(documentFormatString.charAt(ii)); 	
					int kk=ii+1;													
					if(kk<documentFormatString.length()){																
						jj=documentFormatString.charAt(ii+1);
						if(jj<60){					
							if(jj == 46){
								if(partialNo.trim().length() == 0){
									partialNo=no+",";
								}
								ii++;
								continue;
							}else{
								String remDocumentFormat = documentFormatString.substring(kk,documentFormatString.length());
								int j = 0;
								while(j < remDocumentFormat.length()){
									ii++;
									int nextVal = remDocumentFormat.charAt(j);
									if(nextVal < 60 && nextVal != 46){
										no = no+remDocumentFormat.charAt(j);
									}else{
										if(nextVal == 46){
											partialNo = no+partialNo + ",";
										}else{											
											no = partialNo+no;
											partialNo = "";
											ii--;
										}										
										break;
									}
									j++;
								}
								if(j == remDocumentFormat.length()){
									no = partialNo+no;
									partialNo = "";
								}
							}
						}else{
							no=partialNo+no;	
							partialNo = "";
						}

					}else{
						no=partialNo+no;	
						partialNo = "";
					}
					if(partialNo.trim().length() == 0){
						StringBuilder strd = new StringBuilder();
						String str = strd.append("{").append(no).append("}").toString();
						reFormat.append(str);
					}else{
						ii++;
						continue;
					}
				}else{
					ii++;
					continue;
				}
			}
			ii++;
		}
		log.log(Log.INFO, "Format String obtained----->> " +reFormat.toString());
		return reFormat.toString();
	}
}
