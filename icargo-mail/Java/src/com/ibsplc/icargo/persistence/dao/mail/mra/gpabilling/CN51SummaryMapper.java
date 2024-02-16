/*
 * CN51SummaryMapper.java Created on Jan 9, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
//import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import com.ibsplc.icargo.framework.util.BeanHelper;


/**
 * @author A-1556
 *
 */
public class CN51SummaryMapper implements MultiMapper<CN51SummaryVO> {

	private static final String CLASS_NAME = "CN51SummaryMapper";

	private Log log = LogFactory.getLogger("MRA:GPABILLING");
	
	private static final String POATYP="POATYP";

    /**
     * @param resultSet
     * @return cn51SummaryVO
     * @throws SQLException
     * @throws SystemException 
     */
    /* (non-Javadoc)
     * @see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
     */
    public List<CN51SummaryVO> map(ResultSet resultSet) throws SQLException{
    	log.entering(CLASS_NAME,"map");
    	//Added By A-8527 for ICRD-234294 Starts
    	String invoiceDetailsbaseKey = "";
    	String invoiceDetailsKey = "";
    	 List<CN51SummaryVO> cn51SummaryVOs = new ArrayList<CN51SummaryVO>();
    	 Collection <CN51SummaryVO> rebillDetailsVOs =null;
        CN51SummaryVO cn51SummaryVO = null;
        CN51SummaryVO cn51SummaryVO1 = null;
        while (resultSet.next())
        {
        	String companyCode= resultSet.getString("CMPCOD");
        	String gpaCode=resultSet.getString("GPACOD");
        	String invoiceNumber=resultSet.getString("INVNUM");
        	String serialNumber=String.valueOf(resultSet.getInt("INVSERNUM"));
        	invoiceDetailsKey=companyCode+gpaCode+invoiceNumber;
        	//invoiceDetailsKey = invoiceDetailsKey.substring(0, invoiceDetailsKey.length() - 1);
        	 if(!invoiceDetailsbaseKey.equals(invoiceDetailsKey)){
        		//Added By A-8527 for ICRD-234294 Ends	 
        	cn51SummaryVO = new CN51SummaryVO();
	    	cn51SummaryVO.setBillingCurrencyCode(resultSet.getString("BLGCURCOD"));
	        cn51SummaryVO.setCompanyCode(resultSet.getString("CMPCOD"));
	        cn51SummaryVO.setGpaCode(resultSet.getString("GPACOD"));
	        cn51SummaryVO.setInvoiceNumber(resultSet.getString("INVNUM"));
	        cn51SummaryVO.setInvSerialNumber(resultSet.getInt("INVSERNUM"));
	        cn51SummaryVO.setInvoiceStatus(resultSet.getString("INVSTA"));
	        cn51SummaryVO.setGpaName(resultSet.getString("POANAM"));
	        
	        	/**
	        	* @author A-3447
	        	 */
	      
	        
	        Timestamp upTime = resultSet.getTimestamp("LSTUPDTIM");
		    
	    	if(upTime!=null){
			LocalDate date=new LocalDate(
					LocalDate.NO_STATION,Location.NONE,upTime);
			cn51SummaryVO.setLastUpdatedTime(date);
			
		}
	    	
	    	  Timestamp updatedTime = resultSet.getTimestamp("UPTDTIM");
			    
		    	if(updatedTime!=null){
				LocalDate date=new LocalDate(
						LocalDate.NO_STATION,Location.NONE,updatedTime);
				cn51SummaryVO.setLastUpdatedTime(date);
				
			}
		    	//cn51SummaryVO.setBillingPeriodFrom(resultSet.getString("BLGPRDFRM"));
		    	//cn51SummaryVO.setBillingPeriodTo(resultSet.getString("BLGPRDTOO"));
		    	cn51SummaryVO.setBillingPeriodFrom((new LocalDate("***", Location.NONE, 
		    			resultSet.getDate("BLGPRDFRM"))).toDisplayDateOnlyFormat());
		    	cn51SummaryVO.setBillingPeriodTo((new LocalDate("***", Location.NONE, 
		    			resultSet.getDate("BLGPRDTOO"))).toDisplayDateOnlyFormat());
                
		    		cn51SummaryVO.setLstupdatedUser(resultSet.getString("UPTDUSR"));
	        		cn51SummaryVO.setLastUpdateduser(resultSet.getString("LSTUPDUSR"));
	        		cn51SummaryVO.setBillingBasis(resultSet.getString("BLGBAS"));
	        		cn51SummaryVO.setSequenceNo(resultSet.getInt("SEQNUM"));
	        		cn51SummaryVO.setBillingStatus(resultSet.getString("BLGSTA"));
	        		/**
	        		 * 
	        		 * @author A-3447
	        		 */
	        //Added for money starts
	        try{
	        	if(resultSet.getString("BLGCURCOD")!=null && resultSet.getString("BLGCURCOD").trim().length()>0){
				        Money amtInBillCurr = CurrencyHelper.getMoney(resultSet.getString("BLGCURCOD"));
				        amtInBillCurr.setAmount(resultSet.getDouble("TOTAMTBLGCUR"));
				        cn51SummaryVO.setTotalAmountInBillingCurrency(amtInBillCurr);
				        log.log(Log.INFO, "billing amount", cn51SummaryVO.getTotalAmountInBillingCurrency());
	        	}
	        	if(resultSet.getString("BLGCURCOD")!=null && resultSet.getString("BLGCURCOD").trim().length()>0){
				        Money amtInConCurr= CurrencyHelper.getMoney(resultSet.getString("BLGCURCOD"));
				        amtInConCurr.setAmount(resultSet.getDouble("TOTAMTCTRCUR"));
				        cn51SummaryVO.setTotalAmountInContractCurrency(amtInConCurr);
	        	}
	        }
	        catch(CurrencyException currencyException){
	        	currencyException.getErrorCode();
	        }
	        cn51SummaryVO.setContractCurrencyCode(resultSet.getString("CRTCURCOD"));

	        if(resultSet.getDate("BLDDAT") != null ){
				  cn51SummaryVO.setBilledDate(new LocalDate(LocalDate.NO_STATION,
											        		Location.NONE,
											        	 resultSet.getDate("BLDDAT")));
	        }

	        String period = resultSet.getString("BLDPRD");
	        cn51SummaryVO.setBillingPeriod(period);
	        //Added by A-3434 for finding billingprdfrom and billingprdto
	       /*String from ="";

	        for(int i=0;i<=period.trim().length()-1;i++){
        	Character b= period.charAt(i);
        	String d =b.toString();
           	if("-".matches(d)){
        		String  a = period.substring(0,i);
        		from=a;
        		int index = i;
        		String to = period.substring(index+1,period.trim().length());
        		cn51SummaryVO.setBillingPeriodTo(to);
        		cn51SummaryVO.setBillingPeriodFrom(from);

        	}

        }*/
	        	//Added By A-8527 for ICRD-234294 Starts
				 cn51SummaryVO.setPassFileName(resultSet.getString("INTFCDFILNAM"));
				 cn51SummaryVO.setPeriodNumber(resultSet.getString("PRDNUM"));
				 if (Objects.nonNull(resultSet.getString(POATYP))
						 && !resultSet.getString(POATYP).isEmpty()
						 && CN51SummaryVO.POSTAL_AUTHORITY_TYPE_PASS.equals(resultSet.getString(POATYP))) {
					 cn51SummaryVO.setPASS(true);
				 }
	        	cn51SummaryVOs.add(cn51SummaryVO);
	        	invoiceDetailsbaseKey=companyCode+gpaCode+invoiceNumber;
        	}else{
        		
        		cn51SummaryVO1 = new CN51SummaryVO();
        		try {
					BeanHelper.copyProperties(cn51SummaryVO1,cn51SummaryVO);
				} catch (SystemException e) {
					e.getError();
				}
        		cn51SummaryVO1.setRebillInvoiceDetails(null);
				cn51SummaryVO1.setCompanyCode(resultSet.getString("CMPCOD"));
				cn51SummaryVO1.setRebillRound(resultSet.getInt("RBLRND"));
				StringBuilder rebillInvoiceNo=new StringBuilder();
				rebillInvoiceNo.append(resultSet.getString("INVNUM"));
				rebillInvoiceNo.append("-");
				rebillInvoiceNo.append(String.valueOf(resultSet.getInt("RBLRND")));	
				cn51SummaryVO1.setRebillInvoiceNumber(rebillInvoiceNo.toString());
        		cn51SummaryVO1.setInvoiceStatus(resultSet.getString("INVSTA"));
        		if(resultSet.getDate("BLDDAT") != null ){
					cn51SummaryVO1.setBilledDate(new LocalDate(
							LocalDate.NO_STATION, Location.NONE, resultSet
									.getDate("BLDDAT")));
        		}
        		 try{
					if (resultSet.getString("BLGCURCOD") != null
							&& resultSet.getString("BLGCURCOD").trim().length() > 0) {
						Money amtInBillCurr = CurrencyHelper.getMoney(resultSet
								.getString("BLGCURCOD"));
						amtInBillCurr.setAmount(resultSet
								.getDouble("TOTAMTBLGCUR"));
						cn51SummaryVO1
								.setTotalAmountInBillingCurrency(amtInBillCurr);
						log.log(Log.INFO, "billing amount", cn51SummaryVO1
								.getTotalAmountInBillingCurrency());
					}
					if (resultSet.getString("BLGCURCOD") != null
							&& resultSet.getString("BLGCURCOD").trim().length() > 0) {
						Money amtInConCurr = CurrencyHelper.getMoney(resultSet
								.getString("BLGCURCOD"));
						amtInConCurr.setAmount(resultSet
								.getDouble("TOTAMTCTRCUR"));
						cn51SummaryVO1
								.setTotalAmountInContractCurrency(amtInConCurr);
					}
				} catch (CurrencyException currencyException) {
     	        	currencyException.getErrorCode();
     	        }
				if(cn51SummaryVO.getRebillInvoiceDetails() != null && !cn51SummaryVO.getRebillInvoiceDetails().isEmpty())
					cn51SummaryVO.getRebillInvoiceDetails().add(cn51SummaryVO1);
				else{
					cn51SummaryVO.setRebillInvoiceDetails(new ArrayList<CN51SummaryVO>());
					cn51SummaryVO.getRebillInvoiceDetails().add(cn51SummaryVO1);
				}
				 cn51SummaryVO.setPassFileName(resultSet.getString("INTFCDFILNAM"));
				 cn51SummaryVO.setPeriodNumber(resultSet.getString("PRDNUM"));
				 if (Objects.nonNull(resultSet.getString(POATYP))
						 && !resultSet.getString(POATYP).isEmpty()
						 && CN51SummaryVO.POSTAL_AUTHORITY_TYPE_PASS.equals(resultSet.getString(POATYP))) {
					 cn51SummaryVO.setPASS(true);
				 }
        	}
        }
      //Added By A-8527 for ICRD-234294 Starts
	    log.entering(CLASS_NAME,"map");
	    
	    
    	return cn51SummaryVOs;
    }

}
