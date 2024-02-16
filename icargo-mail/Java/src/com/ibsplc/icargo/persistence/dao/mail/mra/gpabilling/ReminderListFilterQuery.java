/*
 * ReminderListFilterQuery.java Created on Jan 23, 2019
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information 
 * of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling;

import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.ReminderDetailsFilterVO;
import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.ReminderDetailsVO;
import com.ibsplc.icargo.business.cra.defaults.vo.converter.TransactionPrivilegeConverter;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author A-5526
 *
 */
public class ReminderListFilterQuery extends PageableNativeQuery<ReminderDetailsVO>{
	private static final String CLASS_NAME = "ReminderListFilterQuery";

	private Log log = LogFactory.getLogger("MRA GPABiLLING");
	private ReminderDetailsFilterVO reminderDetailsFilterVO;
	private String noncass;
	private String cass;
	
	
	public ReminderListFilterQuery(
			ReminderDetailsFilterVO reminderDetailsFilterVO,
			String noncass, String cass,ReminderDetailsGpaBillingMapper reminderDetailsMapper) throws SystemException {
		super(reminderDetailsFilterVO.getTotalRecordsCount(),reminderDetailsMapper);
		this.reminderDetailsFilterVO = reminderDetailsFilterVO;
		this.noncass = noncass;
		this.cass = cass;
		
	}
	@Override
	public String getNativeQuery() {
		log.entering(CLASS_NAME, "getNativeQuery");
		
		StringBuilder finalQuery = new StringBuilder();
		
		int index = 0;
		/*if (awbquery != null) {
				finalQuery.append(awbquery);
			finalQuery=filterquery(finalQuery,index);
			} */
		 if (noncass != null) {
			finalQuery.append(noncass);
			finalQuery=filterquery(finalQuery,index);
			}
		else if (cass != null){
			finalQuery.append(cass);
			finalQuery=filterquery(finalQuery,index);
			}
		log.log(100, finalQuery.toString());
		return finalQuery.toString();
	}

	StringBuilder filterquery(StringBuilder finalQuery,int index){
		
		String typeofbilling=reminderDetailsFilterVO.getTypeOfBilling();
		String invoiceRefNo=reminderDetailsFilterVO.getInvoiceNumber();
		String dateFilterMode=reminderDetailsFilterVO.getDateFilterMode();
		LocalDate fromDate=reminderDetailsFilterVO.getFromDate();
		LocalDate toDate=reminderDetailsFilterVO.getToDate();
		String country=reminderDetailsFilterVO.getCountryCode();
		String station = reminderDetailsFilterVO.getStation();
		String paymentStatus = reminderDetailsFilterVO.getPaymentStatus();
		String reminderStatus=reminderDetailsFilterVO.getReminderStatus();
		String companyCode= reminderDetailsFilterVO.getCompanyCode();
		String privilegedStations=reminderDetailsFilterVO.getStationForPrivilege();
		String rebillRound[]=reminderDetailsFilterVO.getGpaRebillRound() != null ? reminderDetailsFilterVO.getGpaRebillRound().split(",") : null;
		String gpaCode=reminderDetailsFilterVO.getGpaCode();
		String mailbagId=reminderDetailsFilterVO.getMailbagID();
		
		StringBuilder privilegedStationsBuilder = new StringBuilder();
		if(privilegedStations !=null && privilegedStations.trim().length() > 0){
           	for(String stn: privilegedStations.split(",")){
           		if(privilegedStationsBuilder.length() == 0){
           			privilegedStationsBuilder.append("'").append(stn).append("'");
           		}else{
           			privilegedStationsBuilder.append(",").append("'").append(stn).append("'");
           		}
           	}
		} 
		
		String privilegeString = null;
		if(typeofbilling!=null && typeofbilling.trim().length()>0){
			 privilegeString = TransactionPrivilegeConverter.getTxnPrvConvertorForReminderList(
	    		reminderDetailsFilterVO).getQueryStringForPrivilege();
		}
		if(privilegeString != null && privilegeString.trim().length()>0){
			finalQuery.append(privilegeString.toString());
		}
		if(rebillRound != null && rebillRound.length > 0 && rebillRound[0].trim().length() > 0) {
			
			this.setParameter(++index, companyCode);
 			if(invoiceRefNo!=null && invoiceRefNo.trim().length()>0){
 				finalQuery.append(" AND RMKDTL.INVNUM = ? ");
 				this.setParameter(++index, invoiceRefNo);
 			}
 			if (gpaCode != null && gpaCode.trim().length() > 0) {    
 				finalQuery.append(" AND RMKDTL.GPACOD = ? ");
 				this.setParameter(++index, gpaCode);
 			}
 			if (mailbagId != null && mailbagId.trim().length() > 0) {
 				finalQuery.append(" AND MST.MALIDR = ? ");
 				this.setParameter(++index, mailbagId);      
 			}
			finalQuery.append(" AND RMKDTL.RBLRND IN ( ");
				int length = rebillRound.length;
				int i=0;
 				for(String round : rebillRound){
 					i++;
 					finalQuery.append(Integer.parseInt(round));
 					if(i<length){
 						finalQuery.append(",");
 					}
 				}
 				finalQuery.append(" ) ");
			if (reminderStatus != null && reminderStatus.trim().length() > 0) {
					finalQuery.append(" AND RMKDTL.RBLSTA = ? ");
					this.setParameter(++index, reminderStatus);
			}	
	
		}else{
		
 			this.setParameter(++index, companyCode);
 			if (gpaCode != null && gpaCode.trim().length() > 0) {    
 				finalQuery.append(" AND C66.GPACOD= ? ");
 				this.setParameter(++index, gpaCode);
 			}
 			if (mailbagId != null && mailbagId.trim().length() > 0) {
 				finalQuery.append(" AND MST.MALIDR= ? ");
 				this.setParameter(++index, mailbagId);      
 			}
 			if(invoiceRefNo!=null && invoiceRefNo.trim().length()>0){
 				finalQuery.append(" AND C66.INVNUM= ? ");
 				this.setParameter(++index, invoiceRefNo);
 			}
 			//if (dateFilterMode != null && dateFilterMode.trim().length() > 0){
			if( fromDate != null && toDate != null){ 
				  finalQuery.append(" AND MST.RCVDAT BETWEEN ? AND ? ");    
				 this.setParameter(++index, fromDate);
				 this.setParameter(++index, toDate);     
			  }
 			//}
 			/*finalQuery.append(" UNION ALL ");
 			finalQuery.append(" SELECT MST.MALIDR, MST.MALSEQNUM,RMKDTL.MCAREFNUM MCANO,RMKDTL.GPACOD,RMKDTL.INVNUM,RMKDTL.INVSERNUM,RMKDTL.RBLINVNUM,'GPA Billing' TYPOFBLG,'Rebill generated' REMSTA,RMKDTL.RCVDAT,RMKDTL.ORGCOD,RMKDTL.DSTCOD,RMKDTL.BLGCURCOD,");
 			finalQuery.append(" RMKDTL.NETAMTBLGCUR , RMKDTL.MCAAMT,RMKDTL.STLAMT ,RMKDTL.DUEAMT,RMKDTL.RBLRND REMNUM ");
 			finalQuery.append(" FROM MALMRAGPARBLDTL RMKDTL , MALMRABLGMST MST ");
 			finalQuery.append(" WHERE RMKDTL.CMPCOD = MST.CMPCOD ");
 			finalQuery.append(" AND RMKDTL.MALSEQNUM = MST.MALSEQNUM ");  
 			finalQuery.append(" AND (RMKDTL.DUEAMT > 1 OR RMKDTL.DUEAMT <-1) ");
 			finalQuery.append(" AND RMKDTL.CMPCOD = ? ");   
 			this.setParameter(++index, companyCode);
 			if(invoiceRefNo!=null && invoiceRefNo.trim().length()>0){
 				finalQuery.append(" AND RMKDTL.INVNUM = ? ");
 				this.setParameter(++index, invoiceRefNo);
 			}
 			if (gpaCode != null && gpaCode.trim().length() > 0) {    
 				finalQuery.append(" AND RMKDTL.GPACOD = ? ");
 				this.setParameter(++index, gpaCode);
 			}
 			if (mailbagId != null && mailbagId.trim().length() > 0) {
 				finalQuery.append(" AND MST.MALIDR = ? ");
 				this.setParameter(++index, mailbagId);      
 			}
 			if(rebillRound!=null && rebillRound.length >0 && rebillRound[0].trim().length() > 0 ){
 	 				finalQuery.append(" AND RMKDTL.RBLRND IN ( ");
 	 				int length = rebillRound.length;
 					int i=0;
 	 				for(String round : rebillRound){
 	 					i++;
 	 					finalQuery.append(Integer.parseInt(round));
 	 					if(i<length){
 	 						finalQuery.append(",");
 	 					}
 	 				}
 	 				finalQuery.append(" ) ");
 	 		}
			if (reminderStatus != null && reminderStatus.trim().length() > 0) {
					finalQuery.append(" AND RMKDTL.RBLSTA = ? ");
					this.setParameter(++index, reminderStatus);
			}	
			//Added as part of ICRD-336415 starts
			if(fromDate==null || ("").equals(fromDate)){
				finalQuery.append("AND RMKDTL.INVSERNUM=(SELECT MAX(INVSERNUM) FROM MALMRAGPAC51SMY WHERE CMPCOD=? AND INVNUM=? AND GPACOD=?)");
				this.setParameter(++index, companyCode);
				this.setParameter(++index, invoiceRefNo);
				this.setParameter(++index, gpaCode);
			}*/
			//Added as part of ICRD-336415 ends
 		 }
		return finalQuery;	
		}
	}
	
