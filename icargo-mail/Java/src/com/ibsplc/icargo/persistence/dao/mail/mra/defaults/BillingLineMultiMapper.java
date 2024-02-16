/*
 * BillingLineMultiMapper.java Created on Dec 15, 2005
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
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineChargeVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineDetailVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineParameterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-1556
 *
 */
public class BillingLineMultiMapper implements MultiMapper<BillingLineVO> {

	  private Log log = LogFactory.getLogger("MAILTRACKING MRA BILLINGLINE");
	  /**
		 * If Billing Line Valid End date is 
		 * earlier than current date, 
		 * its Status is - Expired.
		 */
	  private static final String STA_EXPIRED = "E";
    
    /**
     * @author A-2398
     * @param rs
     * @return List<BillingLineVO>
     * @throws SQLException
     */
	  public List<BillingLineVO> map(ResultSet rs) throws SQLException {
    	 log.entering("BillingLineMultiMapper", "map");
    	 
    	 List<BillingLineVO> blgLineDetails =
             new ArrayList<BillingLineVO>();
    	 LinkedHashMap<String, BillingLineVO> billingLineMap = new LinkedHashMap<String, BillingLineVO>();
    	 LinkedHashMap<String, BillingLineParameterVO> billingLineParametersMap = new LinkedHashMap<String, BillingLineParameterVO>();
    	// LinkedHashMap<String, BillingLineDetailVO> billingLineDtlMap = new LinkedHashMap<String, BillingLineDetailVO>();
    	// LinkedHashMap<String, BillingLineChargeVO> billingLineChargeDtlMap = new LinkedHashMap<String, BillingLineChargeVO>();
    	 TreeMap<String,BillingLineChargeVO> billingCharge = new TreeMap<String,BillingLineChargeVO>();
    	 TreeMap<String,BillingLineDetailVO> billingDetails = new TreeMap<String, BillingLineDetailVO>();
    	 Collection<BillingLineChargeVO> billingChargeVO = null;
    	 Collection<BillingLineDetailVO> billingDetailVO = null;
    	 
    	 StringBuilder billingLineParametersStringBuffer = null;
    	 StringBuilder billingLineDtlStringBuffer = null;
    	 StringBuilder billingLineChgDtlStringBuffer = null;
    	 BillingLineDetailVO billingLineDetailVO=null;
    		 BillingLineVO billingLineVO = null;
    		 StringBuilder blgLineKey = null;
    		// String blgLinePrev = null;
    		 
    		 while(rs.next()) {
    			 blgLineKey = new StringBuilder().append(rs.getString("BLGMTXCOD"))
    			 								.append(rs.getString("CMPCOD"))
    			 								.append(rs.getInt("BLGLINSEQNUM"));
    			 if (!billingLineMap.containsKey(blgLineKey
 						.toString())) {
    				 
    				 	/* 
    		    		 * Added By A-3434 for BUG 26273
    		    		 * for eliminating duplicates (Parent)
    		    		 */
    				 
    		    		if(billingLineVO!=null){
    		    			
    		    				//increment();						
    		    				
    		    			}
    		    		
    				// blgLinePrev = blgLineKey;
    		    	String blglinsta = rs.getString("BLGLINSTA");
    				 billingLineVO = new BillingLineVO();
    				 billingLineMap.put(blgLineKey.toString(),
    						 billingLineVO);
    				 blgLineDetails.add(billingLineVO);
    				 billingLineVO.setBillingLineParameters(
    	                        new ArrayList<BillingLineParameterVO>());
    				 billingLineVO.setBillingLineDetails(
 	                        new ArrayList<BillingLineDetailVO>());
    				 
    	                billingLineVO.setBillingLineStatus(blglinsta);
    	               if(rs.getDate("VLDSTRDAT") != null) {
    	                    billingLineVO.setValidityStartDate(
    	                    		new LocalDate(LocalDate.NO_STATION, Location.NONE,
    	                            rs.getDate("VLDSTRDAT")));
    	                }
    	               if(rs.getDate("VLDENDDAT") != null) {
   	                    billingLineVO.setValidityEndDate(
   	                    		new LocalDate(LocalDate.NO_STATION, Location.NONE,
   	                            rs.getDate("VLDENDDAT")));
   	                }
    	               billingLineVO.setBillingMatrixId(rs.getString("BLGMTXCOD"));
    	               billingLineVO.setCompanyCode(rs.getString("CMPCOD"));
    	                billingLineVO.setAirlineCode(rs.getString("BILPTYARLCOD"));
    	                billingLineVO.setCurrencyCode(rs.getString("CURCOD"));
    	                billingLineVO.setPoaCode(rs.getString("BILPTYPOACOD"));
    	                billingLineVO.setApplicableRate(rs.getDouble("APLRAT"));
    	                billingLineVO.setRevenueExpenditureFlag(rs.getString("REVEXPFLG"));
    	                billingLineVO.setBillingSector(rs.getString("BILSEC"));
    	                billingLineVO.setBillingBasis(rs.getString("BLGBAS"));
    	                billingLineVO.setBillingLineSequenceNumber(rs.getInt("BLGLINSEQNUM"));
    	                LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
    	                
						// the condition check used to check the  expired rate line status.
    	                if(billingLineVO.getValidityEndDate() != null &&
    	                	currentDate.after(billingLineVO.getValidityEndDate())&&"A".equalsIgnoreCase(blglinsta)) {
							billingLineVO.setBillingLineStatus(STA_EXPIRED);
						} else {
							billingLineVO.setBillingLineStatus(blglinsta);
						}
						
    	                
    	                
    	                /*
    	                 * Added by Sandeep as part of Optimistic Locking Mechanism
    	                 * 
    	                 */
    	                if(rs.getTimestamp("LSTUPDTIM") != null) {
    	                    billingLineVO.setLastUpdatedTime(
    	                    		new LocalDate(LocalDate.NO_STATION, Location.NONE,
    	                            rs.getTimestamp("LSTUPDTIM")));
    	                }
    	                billingLineVO.setLastUpdatedUser(rs.getString("LSTUPDUSR"));
        	            //Added as part of CRQ-12578
        	            billingLineVO.setUnitCode(rs.getString("UNTCOD"));
        	            if("Y".equals(rs.getString("RATINCSRVTAXFLG"))){
        	            	billingLineVO.setTaxIncludedInRateFlag(true);
        	            }
        	            else{
        	            	billingLineVO.setTaxIncludedInRateFlag(false);
        	            }
    	                
    	                //increment();
    	            }
    			 billingLineParametersStringBuffer=new StringBuilder().append(rs.getString("BLGMTXCOD"))
							.append(rs.getString("CMPCOD"))
							.append(rs.getInt("BLGLINSEQNUM"))
							.append(rs.getString("PARCOD"))
							.append(rs.getString("PARVAL"));
    			 
    	            if(rs.getInt("BLGLINSEQNUM") > 0) {
    			 if (!billingLineParametersMap
    						.containsKey(billingLineParametersStringBuffer.toString()) ) {
    				 
    	               BillingLineParameterVO blgLineParameterVO =
    	                        new BillingLineParameterVO();
    	               billingLineParametersMap.put(billingLineParametersStringBuffer.toString(),
    	            		   blgLineParameterVO);
    	                blgLineParameterVO
    	                        .setParameterCode(rs.getString("PARCOD"));
    	                blgLineParameterVO.setParameterValue(rs.getString("PARVAL"));
    	                blgLineParameterVO.setExcludeFlag(rs.getString("EXCFLG"));
    	                billingLineVO = billingLineMap
    							.get(blgLineKey.toString());
    	                billingLineVO.getBillingLineParameters()
                        .add(blgLineParameterVO);
    	                
    	            }
    			 
    			 // Added by A-4809 for ICRD-153988 Starts..
    			 if(rs.getString("CHGTYP")!=null){
    			 billingLineDtlStringBuffer=new StringBuilder().append(rs.getString("BLGMTXCOD"))
							.append(rs.getString("CMPCOD"))
							.append(rs.getInt("BLGLINSEQNUM"))
							.append(rs.getString("CHGTYP"));
        		 if (!billingDetails
 						.containsKey(billingLineDtlStringBuffer.toString())) {
    				 billingLineDetailVO=new BillingLineDetailVO();
        			 billingLineDetailVO.setForKeyCheck(billingLineDtlStringBuffer.toString());
        			 billingLineDetailVO.setCompanycode(rs.getString("CMPCOD"));
        			 billingLineDetailVO.setBillingMatrixID(rs.getString("BLGMTXCOD"));
        			 billingLineDetailVO.setBillingLineSequenceNumber(rs.getInt("BLGLINSEQNUM"));
    				 billingLineDetailVO.setChargeType(rs.getString("CHGTYP"));
    				 billingLineDetailVO.setRatingBasis(rs.getString("RATBSS"));
    	                if (billingLineDetailVO.getChargeType() != null
								&& !"".equals(billingLineDetailVO.getChargeType())
								&& !"M".equals(billingLineDetailVO
										.getChargeType()) && rs.getString("RATBSS")!=null) {
								billingLineVO.setSurchargeIndicator("Y");
							}
    	             billingDetails.put(billingLineDtlStringBuffer.toString(),billingLineDetailVO);
        		 }
    			 
        		 
        		 
    			 billingLineChgDtlStringBuffer=new StringBuilder().append(rs.getString("BLGMTXCOD"))
							.append(rs.getString("CMPCOD"))
							.append(rs.getInt("BLGLINSEQNUM"))
							.append(rs.getString("CHGTYP"))
							.append(rs.getDouble("FRMWGT"));
    			 if(!billingCharge.containsKey(billingLineChgDtlStringBuffer.toString())){
    				
    				 BillingLineChargeVO billingLineChargeVO=new BillingLineChargeVO();
 				 billingLineChargeVO.setForKeyCheck(billingLineDtlStringBuffer.toString());
    				 billingLineChargeVO.setApplicableRateCharge(rs.getDouble("APPRAT"));
    				 Money aplRatChg=null;
    				 if(rs.getString("CURCOD")!=null){
    					try {
    						aplRatChg = CurrencyHelper
    						.getMoney(rs.getString("CURCOD"));
    					} catch (CurrencyException e) {
    						log.log(Log.FINE,  "CurrencyException");
    					}
    					aplRatChg.setAmount(billingLineChargeVO.getApplicableRateCharge());
    					billingLineChargeVO.setAplRatChg(aplRatChg);
    				 }	
    				 billingLineChargeVO.setRateType(rs.getString("RATTYP"));
    				 billingLineChargeVO.setFrmWgt(rs.getDouble("FRMWGT"));
					billingCharge.put(billingLineChgDtlStringBuffer.toString(),billingLineChargeVO);
    			 }

    			
 			 
    			
    			 }
    			 // Added by A-4809 for ICRD-153988 Ends..
    	         }

    			 
    	           
    	        
    	        }
    		//This part of the code is required to add the last parent
 	 		//The last parent wont be added by the main loop
    		 // Added by A-4809 for ICRD-153988 Starts..
    		 billingDetailVO = new ArrayList<BillingLineDetailVO>(billingDetails.values());
    		 log.log(Log.INFO, "collection",billingDetailVO);
    		 billingChargeVO = new ArrayList<BillingLineChargeVO>(billingCharge.values());
    		 log.log(Log.INFO, "collection",billingChargeVO);
    		 //Collection<BillingLineDetailVO> billingLineDetailVOCol = new ArrayList<BillingLineDetailVO>();
    		 Collection<BillingLineChargeVO> billingLineChargeVOCol = null;
    		 if(!billingDetailVO.isEmpty()){
    			 for(BillingLineDetailVO dtlVO:billingDetailVO){
    				 StringBuilder key = new  StringBuilder().append(dtlVO.getBillingMatrixID()).
    						 append(dtlVO.getCompanycode()).append(dtlVO.getBillingLineSequenceNumber());
    				 BillingLineDetailVO billingLineDetailsVO = dtlVO;
    				 billingLineDetailsVO.setBillingLineCharges(new ArrayList<BillingLineChargeVO>());
    				 if(!billingChargeVO.isEmpty()){
    					 billingLineChargeVOCol = new ArrayList<BillingLineChargeVO>();
    					 for(BillingLineChargeVO chrgVO:billingChargeVO){
    						 if(dtlVO.getForKeyCheck().equals(chrgVO.getForKeyCheck())){
    							 billingLineChargeVOCol.add(chrgVO);
    						 }
    					 } 
    					 billingLineDetailsVO.getBillingLineCharges().addAll(billingLineChargeVOCol);
    					 //billingLineDetailVOCol.add(billingLineDetailsVO);
    				 }
    				// BillingLineVO bb =  billingLineMap.get(key.toString());  
 				 //billingLineMap.get(key.toString()).setBillingLineDetails(new ArrayList<BillingLineDetailVO>()); 
    				 billingLineMap.get(key.toString()).getBillingLineDetails().add(billingLineDetailsVO);
    			 } 
    		 }
    		 //Added by A-4809 for ICRD-153988 Ends..
    	        log.exiting("BillingLineMultiMapper", "map");
    	        return new ArrayList<BillingLineVO>(billingLineMap.values());
    	    }
    	}