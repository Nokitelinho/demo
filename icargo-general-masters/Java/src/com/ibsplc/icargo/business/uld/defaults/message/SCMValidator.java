/*
 * SCMValidator.java Created on Aug 02, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.uld.defaults.message;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.ULD;
import com.ibsplc.icargo.business.uld.defaults.ULDController;
import com.ibsplc.icargo.business.uld.defaults.ULDDoesNotExistsException;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDSCMReconcileDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDSCMReconcileVO;
import com.ibsplc.icargo.business.uld.defaults.misc.ULDDiscrepancy;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDiscrepancyVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-2048
 *
 */
public class SCMValidator {
	private Log log = LogFactory.getLogger("ULD");
	
	
	private static final String  ERR_TWO= "ERR2";
	private static final String  ERR_ONE= "ERR1";
	
	/**
	 * 
	 * @param reconcileVO
	 * @throws SystemException
	 */
	public void checkForUldInSystem(ULDSCMReconcileVO reconcileVO)
	throws SystemException{
		log.entering("SCMValidator","checkForUldInSystem");
		ULD uld = null;
		Collection<ULDSCMReconcileDetailsVO> reconcileDetailsVOs = 
			reconcileVO.getReconcileDetailsVOs();
		if(reconcileDetailsVOs != null && reconcileDetailsVOs.size() >0 ) {
			for(ULDSCMReconcileDetailsVO detailsVO : reconcileDetailsVOs) {
				if(ERR_TWO.equalsIgnoreCase(detailsVO.getErrorCode())) {
					try {
						uld =ULD.find(detailsVO.getCompanyCode(),
								detailsVO.getUldNumber());
					}catch(FinderException finderexception){
						log.log(Log.FINE,"FINDER CAUGHT>>");
						log.log(Log.FINE,
								"SETTING ERROR CODE ERR3 TO THE ULDNUM ",
								detailsVO.getUldNumber());
						detailsVO.setErrorCode("ERR3");
					}
					
				}
			}
		}	
	}
	/**
	 * 
	 * @param reconcileVO
	 * @throws SystemException
	 */
	public void checkForLoanOrBorrow(ULDSCMReconcileVO reconcileVO)
	throws SystemException{
		log.entering("SCMValidator","checkForLoanOrBorrow");
		ULD uld = null;
		Collection<ULDSCMReconcileDetailsVO> reconcileDetailsVOs = 
			reconcileVO.getReconcileDetailsVOs();
		if(reconcileDetailsVOs != null && reconcileDetailsVOs.size() >0 ) {
			for(ULDSCMReconcileDetailsVO detailsVO : reconcileDetailsVOs) {
				if(ERR_TWO.equalsIgnoreCase(detailsVO.getErrorCode())) {
					try {
						uld =ULD.find(detailsVO.getCompanyCode(),
								detailsVO.getUldNumber());
					}catch(FinderException finderexception){
						log.log(Log.FINE,"FINDER CAUGHT>>>");
					}
					
					if(detailsVO.getAirlineIdentifier() == uld.getOwnerAirlineIdentifier()) {
						if( detailsVO.getAirlineIdentifier() != uld.getOperationalAirlineIdentifier()) {
							log.log(Log.FINE,"RETURN TRANSACTION>>>SETTING ERROR CODE ERR4");
							detailsVO.setErrorCode("ERR4");
						}
					}else {
						log.log(Log.FINE,"LOAN TRANSACTION>>>SETTING ERROR CODE ERR5");
						detailsVO.setErrorCode("ERR5");
					}
					
				}
			}
		}
	}
	
	/**
	 * 
	 * @param reconcileVO
	 * @throws SystemException
	 */
	public void changeDiscrepancyDetails(ULDSCMReconcileVO reconcileVO)
	throws SystemException{
		log.entering("SCMValidator","changeDiscrepancyDetails");
		Collection<ULDDiscrepancy> uLDDiscrepancys = null;
		Collection<String> ulds = null;
		Collection<ULDMovementVO> uldMovementVOs = null;
		ULDMovementVO movementVO = null;
		
		Collection<ULDSCMReconcileDetailsVO> reconcileDetailsVOs = 
			reconcileVO.getReconcileDetailsVOs();
		if(reconcileDetailsVOs != null && reconcileDetailsVOs.size() >0 ) {
			for(ULDSCMReconcileDetailsVO detailsVO : reconcileDetailsVOs) {
				if(ERR_TWO.equalsIgnoreCase(detailsVO.getErrorCode()) || 
						ERR_ONE.equalsIgnoreCase(detailsVO.getErrorCode())) {
						//Change added by Sreekumar S as a part of AirNZ CR434 on 22Mar08 
						uLDDiscrepancys = ULDDiscrepancy.findULDDiscrepanciesObjects(
								detailsVO.getCompanyCode(),
								detailsVO.getUldNumber(),null);
						if(uLDDiscrepancys != null && uLDDiscrepancys.size() > 0){
							for(ULDDiscrepancy uLDDiscrepancy : uLDDiscrepancys){
								if(ERR_TWO.equalsIgnoreCase(detailsVO.getErrorCode())) {
									log.log(Log.INFO,"FOUND IN SCM VO");
									if("F".equalsIgnoreCase(uLDDiscrepancy.getDiscrepancyCode())) {
										log.log(Log.INFO," FOUND IN DISCREPANCY ");
										log.log(Log.INFO," UPDATING THE SCMSEQNUM AND REPORTING STATION");
										uLDDiscrepancy.setScmSequenceNumber(detailsVO.getSequenceNumber());
										uLDDiscrepancy.setReportingStation(detailsVO.getAirportCode());
									}else {
										log.log(Log.INFO," MISSING IN DISCREPANCY --->>> GOING 4 RECONCILATION");
										ulds = new ArrayList<String>();
										movementVO = new ULDMovementVO();
										uldMovementVOs = new ArrayList<ULDMovementVO>();
										
										movementVO.setPointOfLading(detailsVO.getAirportCode());
										movementVO.setPointOfUnLading(uLDDiscrepancy.getReportingStation());
										movementVO.setCompanyCode(detailsVO.getCompanyCode());
										movementVO.setCurrentStation(detailsVO.getAirportCode());
										movementVO.setDummyMovement(true);
										movementVO.setUpdateCurrentStation(true);
										movementVO.setLastUpdatedTime(new LocalDate(uLDDiscrepancy.getReportingStation(),
																						Location.ARP,true));
																		
										 LogonAttributes logonAttributes =
											 ContextUtils.getSecurityContext().getLogonAttributesVO();

											 movementVO.setLastUpdatedUser(logonAttributes.getUserId());
										
										uldMovementVOs.add(movementVO);
										
										ulds.add(detailsVO.getUldNumber());
										
										 try{
							 				 new ULDController().saveULDMovement(ulds , uldMovementVOs);
										 }catch(ULDDoesNotExistsException uldDoesNotExistsException){
											 log.log(Log.INFO,"%%%%%%%%%%%%%  wil never throw this exception");
										 }
										 
										ulds =null;
										uldMovementVOs = null;
									}
								}else if(ERR_ONE.equalsIgnoreCase(detailsVO.getErrorCode())) {
									log.log(Log.INFO,"MISSING IN SCM VO");
									if("M".equalsIgnoreCase(uLDDiscrepancy.getDiscrepancyCode())) {
										log.log(Log.INFO," MISSING IN DISCREPANCY ");
										uLDDiscrepancy.setScmSequenceNumber(detailsVO.getSequenceNumber());	
									}else {
										log.log(Log.INFO," FOUND IN DISCREPANCY --->>> GOING 4 RECONCILATION");
										ulds = new ArrayList<String>();
										movementVO = new ULDMovementVO();
										uldMovementVOs = new ArrayList<ULDMovementVO>();
										
										movementVO.setPointOfLading(uLDDiscrepancy.getReportingStation());
										movementVO.setPointOfUnLading(detailsVO.getAirportCode());
										movementVO.setCompanyCode(detailsVO.getCompanyCode());
										movementVO.setCurrentStation(uLDDiscrepancy.getReportingStation());
										movementVO.setDummyMovement(true);
										movementVO.setUpdateCurrentStation(true);
										movementVO.setLastUpdatedTime(new LocalDate(uLDDiscrepancy.getReportingStation(),
												Location.ARP,true));
										 LogonAttributes logonAttributes =
											 ContextUtils.getSecurityContext().getLogonAttributesVO();

											 movementVO.setLastUpdatedUser(logonAttributes.getUserId());
										
										uldMovementVOs.add(movementVO);
										
										ulds.add(detailsVO.getUldNumber());
										 try{
							 				 new ULDController().saveULDMovement(ulds , uldMovementVOs);
										 }catch(ULDDoesNotExistsException uldDoesNotExistsException){
											 log.log(Log.INFO,"%%%%%%%%%%%%%  wil never throw this exception");
										 }
										 
										ulds =null;
										uldMovementVOs = null;
									}
								}
							}
						}
/*						if(ERR_TWO.equalsIgnoreCase(detailsVO.getErrorCode())) {
							log.log(Log.INFO,"FOUND IN SCM VO");
							if("F".equalsIgnoreCase(uLDDiscrepancy.getDiscrepancyCode())) {
								log.log(Log.INFO," FOUND IN DISCREPANCY ");
								log.log(Log.INFO," UPDATING THE SCMSEQNUM AND REPORTING STATION");
								uLDDiscrepancy.setScmSequenceNumber(detailsVO.getSequenceNumber());
								uLDDiscrepancy.setReportingStation(detailsVO.getAirportCode());
							}else {
								log.log(Log.INFO," MISSING IN DISCREPANCY --->>> GOING 4 RECONCILATION");
								ulds = new ArrayList<String>();
								movementVO = new ULDMovementVO();
								uldMovementVOs = new ArrayList<ULDMovementVO>();
								
								movementVO.setPointOfLading(detailsVO.getAirportCode());
								movementVO.setPointOfUnLading(uLDDiscrepancy.getReportingStation());
								movementVO.setCompanyCode(detailsVO.getCompanyCode());
								movementVO.setCurrentStation(detailsVO.getAirportCode());
								movementVO.setDummyMovement(true);
								movementVO.setUpdateCurrentStation(true);
								movementVO.setLastUpdatedTime(new LocalDate(uLDDiscrepancy.getReportingStation(),
																				Location.ARP,true));
																
								 LogonAttributes logonAttributes =
									 ContextUtils.getSecurityContext().getLogonAttributesVO();

									 movementVO.setLastUpdatedUser(logonAttributes.getUserId());
								
								uldMovementVOs.add(movementVO);
								
								ulds.add(detailsVO.getUldNumber());
								
								 try{
					 				 new ULDController().saveULDMovement(ulds , uldMovementVOs);
								 }catch(ULDDoesNotExistsException uldDoesNotExistsException){
									 log.log(Log.INFO,"%%%%%%%%%%%%%  wil never throw this exception");
								 }
								 
								ulds =null;
								uldMovementVOs = null;
								
							}
						}else if(ERR_ONE.equalsIgnoreCase(detailsVO.getErrorCode())) {
							log.log(Log.INFO,"MISSING IN SCM VO");
							if("M".equalsIgnoreCase(uLDDiscrepancy.getDiscrepancyCode())) {
								log.log(Log.INFO," MISSING IN DISCREPANCY ");
								uLDDiscrepancy.setScmSequenceNumber(detailsVO.getSequenceNumber());	
							}else {
								log.log(Log.INFO," FOUND IN DISCREPANCY --->>> GOING 4 RECONCILATION");
								ulds = new ArrayList<String>();
								movementVO = new ULDMovementVO();
								uldMovementVOs = new ArrayList<ULDMovementVO>();
								
								movementVO.setPointOfLading(uLDDiscrepancy.getReportingStation());
								movementVO.setPointOfUnLading(detailsVO.getAirportCode());
								movementVO.setCompanyCode(detailsVO.getCompanyCode());
								movementVO.setCurrentStation(uLDDiscrepancy.getReportingStation());
								movementVO.setDummyMovement(true);
								movementVO.setUpdateCurrentStation(true);
								movementVO.setLastUpdatedTime(new LocalDate(uLDDiscrepancy.getReportingStation(),
										Location.ARP,true));
								 LogonAttributes logonAttributes =
									 ContextUtils.getSecurityContext().getLogonAttributesVO();

									 movementVO.setLastUpdatedUser(logonAttributes.getUserId());
								
								uldMovementVOs.add(movementVO);
								
								ulds.add(detailsVO.getUldNumber());
								 try{
					 				 new ULDController().saveULDMovement(ulds , uldMovementVOs);
								 }catch(ULDDoesNotExistsException uldDoesNotExistsException){
									 log.log(Log.INFO,"%%%%%%%%%%%%%  wil never throw this exception");
								 }
								 
								ulds =null;
								uldMovementVOs = null;
								
							}
						}*/
				   else{
						log.log(Log.FINE,"FINDER CAUGHT>>NEW ENTRY INTO ULDDiscrepancy");
						ULDDiscrepancyVO uldDiscrepancysVO = new ULDDiscrepancyVO();
						uldDiscrepancysVO.setCompanyCode(detailsVO.getCompanyCode());
						uldDiscrepancysVO.setUldNumber(detailsVO.getUldNumber());
						uldDiscrepancysVO.setReportingStation(detailsVO.getAirportCode());
						uldDiscrepancysVO.setScmSequenceNumber(detailsVO.getSequenceNumber());
						if(ERR_TWO.equalsIgnoreCase(detailsVO.getErrorCode())){
							uldDiscrepancysVO.setDiscrepencyCode("F");
						}else {
							uldDiscrepancysVO.setDiscrepencyCode("M");
						}
						
						uldDiscrepancysVO.setDiscrepencyDate(reconcileVO.getStockCheckDate());
						
						new ULDDiscrepancy(uldDiscrepancysVO);
					}
					detailsVO.setErrorCode(null);	
				}
			}
		}
	}
}
