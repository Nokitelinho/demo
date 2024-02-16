/*
 * MailAcceptance.java Created on Jun 19, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO.DESTN_FLT;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_UPDATE;
import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;

import com.ibsplc.icargo.business.mail.operations.FlightClosedException;
//import com.ibsplc.icargo.business.mail.operations.MLDController;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
//import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.AssignedFlight;
import com.ibsplc.icargo.business.mail.operations.AssignedFlightPK;
import com.ibsplc.icargo.business.mail.operations.proxy.FlightOperationsProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.MailOperationsMRAProxy;
 

import com.ibsplc.icargo.business.mail.operations.AssignedFlightSegment;
import com.ibsplc.icargo.business.mail.operations.AssignedFlightSegmentPK;
import com.ibsplc.icargo.business.mail.operations.vo.DSNAtAirportVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.business.mail.operations.Container;
import com.ibsplc.icargo.business.mail.operations.ContainerPK;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerAuditVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.ULDAtAirport;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ULDAtAirportVO;

import com.ibsplc.icargo.business.mail.operations.vo.converter.MailtrackingDefaultsVOConverter;
import com.ibsplc.icargo.business.mail.operations.Mailbag;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
//import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingIndexVO;
import com.ibsplc.icargo.business.mail.operations.CapacityBookingProxyException;
import com.ibsplc.icargo.business.mail.operations.ContainerAssignmentException;
import com.ibsplc.icargo.business.mail.operations.InvalidFlightSegmentException;
import com.ibsplc.icargo.business.mail.operations.MailAcceptance;
import com.ibsplc.icargo.business.mail.operations.MailBookingException;
import com.ibsplc.icargo.business.mail.operations.MailController;
import com.ibsplc.icargo.business.mail.operations.MailDefaultStorageUnitException;
import com.ibsplc.icargo.business.mail.operations.ULDDefaultsProxyException;
import com.ibsplc.icargo.business.mail.operations.proxy.WarehouseDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.vo.OffloadFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.OffloadVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailSummaryVO;
import com.ibsplc.icargo.business.warehouse.defaults.location.vo.LocationValidationVO;
import com.ibsplc.icargo.business.warehouse.defaults.vo.WarehouseVO;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;

import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.audit.util.AuditUtils;

import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.keygen.provider.Criterion;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryVO;

/**
 *
 * @author A-1303
 * This class is not persisted and it represents the Mail
 *         acceptance Information
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 	Date 		Author 		Description
 * -------------------------------------------------------------------------
 * 0.1     		Jun 19, 2006		a-1303		TODO
 * 			    Sep 7, 2006      a-1739		Audit Implemented
 */
public class MailAcceptance {

	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");

	private static final String MODULE = "mail.operations";
	private static final String HYPHEN = "-";
	private static final String USPS_INTERNATIONAL_PA="mailtracking.defaults.uspsinternationalpa";
	private static final String USPS_DOMESTIC_PA = "mailtracking.domesticmra.usps";
	 private Map<String,String> exchangeOfficeMap;

	 /**
		 * @author a-1936
		 * @param containers
		 * @return
		 * @throws SystemException
		 */
		 public  static    Collection<ContainerDetailsVO> findMailbagsInContainer(Collection<ContainerDetailsVO> containers)
	     throws SystemException{
			 try {
					return constructDAO().findMailbagsInContainer(containers);
				} catch (PersistenceException ex) {
					throw new SystemException(ex.getMessage(), ex);
				}
			}
		 /**
			 * @author a-1936 methods the DAO instance ..
			 * @return
			 * @throws SystemException
			 */

			private static MailTrackingDefaultsDAO constructDAO() throws SystemException {
				try {
					EntityManager em = PersistenceController.getEntityManager();
					return MailTrackingDefaultsDAO.class.cast(em.getQueryDAO(MODULE));
				} catch (PersistenceException ex) {
					ex.getErrorCode();
					throw new SystemException(ex.getMessage());
				}
			}
			/**
			 * This method is used to save the Acceptance Details in the System
			 * @param mailAcceptanceVO
			 * @param mailBagsForMonitorSLA
			 * @throws SystemException
			 * @throws FlightClosedException
			 * @throws DuplicateMailBagsException 
			 */

			public void saveAcceptanceDetails(MailAcceptanceVO mailAcceptanceVO,Collection<MailbagVO>
				 mailBagsForMonitorSLA)throws SystemException, FlightClosedException, DuplicateMailBagsException {
				log.entering("MailAcceptance", "saveAcceptanceDetails");

				boolean isAcceptanceToFlt = false;
				boolean hasFlightDeparted = false;

				if (mailAcceptanceVO.getFlightSequenceNumber() != DESTN_FLT) {
					isAcceptanceToFlt = true;
					//no need to do closed flight validation for deviation list
					if(!MailConstantsVO.ONLOAD_MESSAGE.equals(mailAcceptanceVO.getMailSource()) && mailAcceptanceVO.isScanned() && !mailAcceptanceVO.isFromDeviationList() && !mailAcceptanceVO.isModifyAfterExportOpr()) {       
						// this method throws exception if flight is closed
						checkForClosedFlight(mailAcceptanceVO);
					}
					if (mailAcceptanceVO.getFlightStatus() == null) {
						hasFlightDeparted = checkForDepartedFlight(mailAcceptanceVO);
					} else if (MailConstantsVO.FLIGHT_STATUS_DEPARTED.equals(mailAcceptanceVO.getFlightStatus())) {
						hasFlightDeparted = true;
					}
				}
				Collection<MailbagVO>mailbagVOs=getMailBags(mailAcceptanceVO,mailAcceptanceVO.getContainerDetails());
				//updateAcceptedMailbags(mailbagVOs);
				boolean hasUpdated = false;
				if (isAcceptanceToFlt) {
					updateAcceptedMailbags(mailbagVOs);
					hasUpdated = saveFlightAcceptanceDetails(mailAcceptanceVO);
				} else {
					hasUpdated = saveDestnAcceptanceDetails(mailAcceptanceVO);
				}
             /* boolean isFromTruck=false;//RFT change
              if("AA".equals(mailAcceptanceVO.getCompanyCode())&&mailAcceptanceVO.getIsFromTruck()!=null&&MailConstantsVO.FLAG_YES.equals(mailAcceptanceVO.getIsFromTruck())){
            	  isFromTruck=true;//RFT change
              }*/
				if (hasUpdated) {
					updateContainers(mailAcceptanceVO);

		            if(!mailAcceptanceVO.isInventory()){
		            //if(!isFromTruck){//RFT change
		            	log.log(Log.FINE, "Flagging Resdits For acceptance !!!");
					    flagResditsForAcceptance(mailAcceptanceVO, mailbagVOs, hasFlightDeparted);
					    //Added for Mailbag History stamping thru advice 
					
					    flagHistoryDetailsOfMailbags(mailAcceptanceVO,mailbagVOs);
		             // }
		            }
		          //Added by A-8527 for IASCB-34446 start
					String enableMLDSend= findSystemParameterValue(MailConstantsVO.MAIL_MLD_ENABLED_SEND);
					if(MailConstantsVO.FLAG_YES.equals(enableMLDSend)){
					//Added by A-8527 for IASCB-34446 Ends	
		            MailController mailController = (MailController)SpringAdapter.getInstance().getBean("mAilcontroller");
		            mailController.flagMLDForMailAcceptance(mailAcceptanceVO, mailbagVOs);
					}
					String importEnabled = findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRAIMPORT);
					boolean isReImported = false;
					if (Objects.nonNull(importEnabled) && !importEnabled.isEmpty() && importEnabled.contains("D")) {
						isReImported = new MailController().reImportPABuiltMailbagsToMRA(mailbagVOs);
					}
		            //importMRAData
					boolean provisionalRateImport =false;
  		            Collection<RateAuditVO> rateAuditVOs = new MailController().createRateAuditVOs(mailAcceptanceVO.getContainerDetails(),MailConstantsVO.MAIL_STATUS_ACCEPTED, provisionalRateImport) ;      
  		              
  		            log.log(Log.FINEST, "RateAuditVO-->", rateAuditVOs);  
  		        	if(rateAuditVOs!=null && !rateAuditVOs.isEmpty() && importEnabled!=null && (mailAcceptanceVO.getFlightSequenceNumber() != DESTN_FLT) && (importEnabled.contains("M") || importEnabled.contains("D"))
  		        			&&!isReImported){
  		          try {
  						new MailOperationsMRAProxy().importMRAData(rateAuditVOs);
  					} catch (ProxyException e) {     
  						throw new SystemException(e.getMessage(), e);     
  		          }
  		          }
  		        	// upront rate Calculation
					String provisionalRateimportEnabled = findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRA_PROVISIONAL_RATE_IMPORT);
					if(MailConstantsVO.FLAG_YES.equals(provisionalRateimportEnabled)){
						provisionalRateImport = true;
  		        	Collection<RateAuditVO> provisionalRateAuditVOs = new MailController().createRateAuditVOs(mailAcceptanceVO.getContainerDetails(),MailConstantsVO.MAIL_STATUS_ACCEPTED
  		        			,provisionalRateImport
  		        			) ;    
  		            log.log(Log.FINEST, "RateAuditVO-->", provisionalRateAuditVOs);  
  		        	if(provisionalRateAuditVOs!=null && !provisionalRateAuditVOs.isEmpty()){
  		          try {
  						Proxy.getInstance().get(MailOperationsMRAProxy.class).importMailProvisionalRateData(provisionalRateAuditVOs);
  					} catch (ProxyException e) {      
  						throw new SystemException(e.getMessage(), e);     
  		          }
  		          }
  		          }
  		          //removed condition (mailAcceptanceVO.isFromDeviationList()||mailAcceptanceVO.isFromCarditList())&&
  		          if(mailAcceptanceVO.getFlightSequenceNumber() != DESTN_FLT){
  		        	mailAcceptanceVO.setAssignedToFlight(true); 
  		        	//if(mailAcceptanceVO.isFromDeviationList()||mailAcceptanceVO.isFromCarditList()) {
  		             flagResditsForAcceptance(mailAcceptanceVO, mailbagVOs, hasFlightDeparted);
  		        	//}
  		          flagHistoryDetailsOfMailbags(mailAcceptanceVO,mailbagVOs);
  		          if(mailAcceptanceVO.isFromDeviationList()){
  		          flagArrivalHistoryForDeviationMailbags(mailAcceptanceVO); 
  		          }
  		          }
				//Added as part of CRQ ICRD-93584 by A-5526 starts
				//new MLDController().flagMLDForMailAcceptance(mailAcceptanceVO, mailbagVOs);
				//Added as part of CRQ ICRD-93584 by A-5526 ends
				}

				log.exiting("MailAcceptance", "saveAcceptanceDetails");
			}
			/**
			 * This method will stamp mailbag arrival for deviation list
			 * @param mailAcceptanceVO
			 * @throws SystemException
			 */
			private void flagArrivalHistoryForDeviationMailbags(MailAcceptanceVO mailAcceptanceVO)
					throws SystemException {
				if(mailAcceptanceVO!=null) {
  		        	MailController mailController = (MailController)SpringAdapter.getInstance().getBean("mAilcontroller");	          
  		        	MailArrivalVO mailArrivalVO = new MailArrivalVO();
  		        	MailbagVO mailbagVOForArrival = null;
  		        	ContainerDetailsVO arrivalContainerDetailsVO = null;
  		        	Collection<ContainerDetailsVO> arrivalContainerDetailsVOs = new ArrayList<>();
  		        	Collection<MailbagVO> mailbagVosForArrival = new ArrayList<>();
  		        	mailArrivalVO.setCompanyCode(mailAcceptanceVO.getCompanyCode());
  		        	if(mailAcceptanceVO.getContainerDetails()!=null) {
  		        	for(ContainerDetailsVO containerDetailsVO: mailAcceptanceVO.getContainerDetails()) {
  		        		arrivalContainerDetailsVO = new ContainerDetailsVO();
  		        		BeanHelper.copyProperties(arrivalContainerDetailsVO , containerDetailsVO);
  		        		if(containerDetailsVO.getMailDetails()!=null) {
  		        			for(MailbagVO mailbagVO: containerDetailsVO.getMailDetails()) {
  		        				mailbagVOForArrival = new MailbagVO();
  		        				BeanHelper.copyProperties(mailbagVOForArrival , mailbagVO);
  		        				mailbagVOForArrival.setArrivedFlag(MailConstantsVO.FLAG_YES);
  		        				mailbagVOForArrival.setAutoArriveMail(MailConstantsVO.FLAG_YES);
  		        				mailbagVOForArrival.setScannedPort(containerDetailsVO.getPou());
  		        				mailbagVosForArrival.add(mailbagVOForArrival);
  		        			}
  		        			arrivalContainerDetailsVO.setMailDetails(mailbagVosForArrival);
  		        		}
  		        		arrivalContainerDetailsVOs.add(arrivalContainerDetailsVO);
  		        	}
  		        	mailArrivalVO.setContainerDetails(arrivalContainerDetailsVOs);
			     	}
					mailController.flagMailbagAuditForArrival(mailArrivalVO );  		        	
					mailController.flagMailbagHistoryForArrival(mailArrivalVO );
  		           }
			}
			/**
			 * @author a-1936 This method is used to check whether the Flight is closed
			 *         for Operations
			 * @param mailAcceptanceVO
			 * @return
			 * @throws SystemException
			 * @throws FlightClosedException
			 */
			public void checkForClosedFlight(MailAcceptanceVO mailAcceptanceVO)
					throws SystemException, FlightClosedException {
				log.entering("MailAcceptance", "isFlightClosedForOperations");
				AssignedFlight assignedFlight = null;
				AssignedFlightPK assignedFlightPk = new AssignedFlightPK();
				assignedFlightPk.setAirportCode(   mailAcceptanceVO.getPol());
				assignedFlightPk.setCompanyCode(   mailAcceptanceVO.getCompanyCode());
				assignedFlightPk.setFlightNumber(   mailAcceptanceVO.getFlightNumber());
				assignedFlightPk.setFlightSequenceNumber(   mailAcceptanceVO
						.getFlightSequenceNumber());
				assignedFlightPk.setLegSerialNumber(   mailAcceptanceVO
						.getLegSerialNumber());
				assignedFlightPk.setCarrierId(   mailAcceptanceVO.getCarrierId());
				try {
					assignedFlight = AssignedFlight.find(assignedFlightPk);
				} catch (FinderException ex) {
					log.log(Log.INFO, "No Assigned Flight Found");
					//no much  problem since already validated but not present in ASGFLT
//					throw new SystemException(ex.getMessage(), ex);
				}
				if (assignedFlight != null) {
					if (MailConstantsVO.FLIGHT_STATUS_CLOSED.equals(assignedFlight
							.getExportClosingFlag())) {
						throw new FlightClosedException(
								FlightClosedException.FLIGHT_STATUS_CLOSED,
								new String[] {
										new StringBuilder()
												.append(
														mailAcceptanceVO
																.getFlightCarrierCode())
												.append(" ").append(
														mailAcceptanceVO
																.getFlightNumber())
												.toString(),
										mailAcceptanceVO.getFlightDate()
												.toDisplayDateOnlyFormat() });
					}
				}
				log.exiting("MailAcceptance", "isFlightClosedForOperations");
			}

			/**
			 * A-1739
			 *
			 * @param mailAcceptanceVO
			 * @return
			 * @throws SystemException
			 */
			public boolean checkForDepartedFlight(MailAcceptanceVO mailAcceptanceVO)
					throws SystemException {
				Collection<FlightValidationVO> flightValidationVOs = null;

				flightValidationVOs = Proxy.getInstance().get(FlightOperationsProxy.class)
						.validateFlightForAirport(createFlightFilterVO(mailAcceptanceVO));

				if (flightValidationVOs != null) {
					for (FlightValidationVO flightValidationVO : flightValidationVOs) {
						if (flightValidationVO.getFlightSequenceNumber() == mailAcceptanceVO
								.getFlightSequenceNumber()
								&& flightValidationVO.getLegSerialNumber() == mailAcceptanceVO
										.getLegSerialNumber()) {
							if (flightValidationVO.getAtd()!=null) {
								return true;

							}
						}
					}
				}
				return false;
			}

			/**
			 * A-1739
			 *
			 * @param mailAcceptanceVO
			 * @return
			 */
			private FlightFilterVO createFlightFilterVO(
					MailAcceptanceVO mailAcceptanceVO) {
				FlightFilterVO flightFilterVO = new FlightFilterVO();
				flightFilterVO.setCompanyCode(mailAcceptanceVO.getCompanyCode());
				flightFilterVO.setFlightCarrierId(mailAcceptanceVO.getCarrierId());
				flightFilterVO.setFlightNumber(mailAcceptanceVO.getFlightNumber());
				flightFilterVO.setFlightSequenceNumber(mailAcceptanceVO
						.getFlightSequenceNumber());
				flightFilterVO.setStation(mailAcceptanceVO.getPol());
				flightFilterVO.setDirection(FlightFilterVO.OUTBOUND);
				return flightFilterVO;
			}
			/**
			 * @author A-1739
			 * @param mailAcceptanceVO
			 * @return
			 * @throws SystemException
			 */
			private boolean saveFlightAcceptanceDetails(
					MailAcceptanceVO mailAcceptanceVO) throws SystemException {

				log.entering("MailAcceptance", "saveFlightAcceptanceDetails");

				Map<AssignedFlightSegmentPK, Collection<ContainerDetailsVO>> uldSegmentMap = groupULDForSegments(mailAcceptanceVO
						.getContainerDetails());

				boolean hasUpdated = true;
				boolean isUpdateSuccessfull = false;
				AssignedFlightSegment assignedFlightSegment = null;

				for (Map.Entry<AssignedFlightSegmentPK, Collection<ContainerDetailsVO>> flightSegULDMap : uldSegmentMap
						.entrySet()) {
					try {
						assignedFlightSegment = AssignedFlightSegment
								.find(flightSegULDMap.getKey());
					} catch (FinderException ex) {
						throw new SystemException(ex.getMessage(), ex);
					}
					isUpdateSuccessfull = assignedFlightSegment.saveFlightAcceptanceDetails(mailAcceptanceVO, flightSegULDMap.getValue());
					if (isUpdateSuccessfull) {
						hasUpdated = true;
					}
				}

				log.exiting("MailAcceptance", "saveFlightAcceptanceDetails");
				return hasUpdated;
			}

			/**
			 * A-1739
			 *
			 * @param mailAcceptanceVO
			 * @throws SystemException
			 * @throws DuplicateMailBagsException 
			 */
			private boolean saveDestnAcceptanceDetails(MailAcceptanceVO mailAcceptanceVO)
					throws SystemException, DuplicateMailBagsException {

				log.entering("MailAcceptance", "saveDestnAcceptanceDetails");
				Collection<ContainerDetailsVO> containers = mailAcceptanceVO
						.getContainerDetails();
				boolean hasUpdated = false;

				for (ContainerDetailsVO containerDetailsVO : containers) {
					if (OPERATION_FLAG_INSERT.equals(containerDetailsVO
							.getOperationFlag())) {
						insertContainerDestAcceptanceDtls(mailAcceptanceVO,
								containerDetailsVO);
						hasUpdated = true;
					} else if (OPERATION_FLAG_UPDATE.equals(containerDetailsVO
							.getOperationFlag())) {
						updateContainerDestAcceptanceDtls(mailAcceptanceVO,
								containerDetailsVO);
						hasUpdated = true;
					}
				}

				log.exiting("MailAcceptance", "saveDestnAcceptanceDetails");
				return hasUpdated;
			}



			/**
			 * @author A-1739
			 *
			 * @param mailAcceptanceVO
			 * @throws SystemException
			 */
			private void updateContainers(MailAcceptanceVO mailAcceptanceVO)
					throws SystemException {
				Collection<ContainerDetailsVO> containerDetails = mailAcceptanceVO
						.getContainerDetails();

				for (ContainerDetailsVO containerDetailsVO : containerDetails) {
					if (OPERATION_FLAG_INSERT.equals(containerDetailsVO
							.getOperationFlag())) {
						ContainerPK containerPK = new ContainerPK();
						containerPK.setCompanyCode(   mailAcceptanceVO.getCompanyCode());
						containerPK.setCarrierId(   mailAcceptanceVO.getCarrierId());
						containerPK.setFlightNumber(   mailAcceptanceVO.getFlightNumber());
						containerPK.setFlightSequenceNumber(   mailAcceptanceVO
								.getFlightSequenceNumber());
						containerPK.setLegSerialNumber(   mailAcceptanceVO
								.getLegSerialNumber());
						containerPK.setAssignmentPort(   containerDetailsVO.getPol());
						containerPK.setContainerNumber(   containerDetailsVO
								.getContainerNumber());

						Container container = null;
						try {
							container = Container.find(containerPK);
						} catch (FinderException ex) {
							throw new SystemException(ex.getMessage(), ex);
						}
						container.setAcceptanceFlag(MailConstantsVO.FLAG_YES);
						container.setLastUpdateTime(containerDetailsVO.getLastUpdateTime());
						/*ContainerAuditVO containerAuditVO = new ContainerAuditVO(
								ContainerVO.MODULE, ContainerVO.SUBMODULE,
								ContainerVO.ENTITY);
//						containerAuditVO.setAdditionalInformation("Container Accepted");
						containerAuditVO.setActionCode(MailConstantsVO.AUDIT_CONACP);
						performContainerAudit(containerAuditVO, container);*/
					}
				}
				MailController mailController = (MailController)SpringAdapter.getInstance().getBean("mAilcontroller");
				mailController.auditContainer(mailAcceptanceVO);
			}

			/**
			 * @author A-1739
			 * @param containers
			 * @return
			 */
			private Map<AssignedFlightSegmentPK, Collection<ContainerDetailsVO>> groupULDForSegments(
					Collection<ContainerDetailsVO> containers) {
				log.entering("MailAcceptance", "groupULDForSegments");
				Map<AssignedFlightSegmentPK, Collection<ContainerDetailsVO>> uldSegmentMap = new HashMap<AssignedFlightSegmentPK, Collection<ContainerDetailsVO>>();
				Collection<ContainerDetailsVO> segmentContainers = null;
				for (ContainerDetailsVO containerDetailsVO : containers) {
					if (containerDetailsVO.getOperationFlag() != null) {
						AssignedFlightSegmentPK assignedFlightSegPK = constructAssignedFlightSegPK(containerDetailsVO);
						segmentContainers = uldSegmentMap.get(assignedFlightSegPK);
						if (segmentContainers == null) {
							segmentContainers = new ArrayList<ContainerDetailsVO>();
							uldSegmentMap.put(assignedFlightSegPK, segmentContainers);
						}
						segmentContainers.add(containerDetailsVO);
						log.log(Log.FINEST, "containerDetailsVO", containerDetailsVO);
					}
				}

				log.exiting("MailAcceptance", "groupULDForSegments");
				return uldSegmentMap;
			}

			/**
			 * A-1739
			 *
			 * @param mailAcceptanceVO
			 * @param containerDetailsVO
			 * @throws SystemException
			 * @throws DuplicateMailBagsException 
			 */
			private void insertContainerDestAcceptanceDtls(
					MailAcceptanceVO mailAcceptanceVO,
					ContainerDetailsVO containerDetailsVO) throws SystemException, DuplicateMailBagsException {
				log.entering("MailAcceptance", "insertContainerDestAcceptanceDtls");
				ULDAtAirportVO uldAtAirportVO = new ULDAtAirportVO();
				uldAtAirportVO.setCompanyCode(mailAcceptanceVO.getCompanyCode());
				uldAtAirportVO.setCarrierId(mailAcceptanceVO.getCarrierId());
				uldAtAirportVO.setAirportCode(mailAcceptanceVO.getPol());
				uldAtAirportVO.setCarrierCode(mailAcceptanceVO.getFlightCarrierCode());
				uldAtAirportVO.setLastUpdateUser(mailAcceptanceVO.getAcceptedUser());
				
				if(!mailAcceptanceVO.isInventory()){
					containerDetailsVO.setMailUpdateFlag(true);
				}
				int bags=0;
				double weight=0;
				if(containerDetailsVO.getMailDetails()!=null && containerDetailsVO.getMailDetails().size()>0){

					 for(MailbagVO mailbagVO:containerDetailsVO.getMailDetails()){
						 bags=bags+1;
						 if(mailbagVO.getWeight()!=null){
						 weight=weight+mailbagVO.getWeight().getSystemValue();//added by A-7371
						 }
					 }
				}

				uldAtAirportVO.setNumberOfBags(bags);
				//uldAtAirportVO.setTotalWeight(weight);
				uldAtAirportVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT,weight));//added by A-7371
				ULDAtAirport uldAtAirport = null;
				boolean isBulkContainer = false;
				if (MailConstantsVO.ULD_TYPE.equals(containerDetailsVO
						.getContainerType())) {
					uldAtAirportVO
							.setUldNumber(containerDetailsVO.getContainerNumber());
					uldAtAirportVO.setRemarks(containerDetailsVO.getRemarks());
					uldAtAirportVO.setFinalDestination(containerDetailsVO
							.getDestination());
					uldAtAirportVO.setWarehouseCode(containerDetailsVO.getWareHouse());
					uldAtAirportVO.setLocationCode(containerDetailsVO.getLocation());
					uldAtAirportVO.setTransferFromCarrier(
							containerDetailsVO.getTransferFromCarrier());
					uldAtAirport = new ULDAtAirport(uldAtAirportVO);
				} else if (MailConstantsVO.BULK_TYPE.equals(containerDetailsVO
						.getContainerType())) {
					isBulkContainer = true;
					uldAtAirportVO.setUldNumber(
							constructBulkULDNumber(
									containerDetailsVO.getDestination(),
									containerDetailsVO.getCarrierCode()));

					log.log(Log.FINEST, "uldatarp --> ", uldAtAirportVO);
					try {
						uldAtAirport = ULDAtAirport
								.find(constructULDArpPKFromULDArpVO(uldAtAirportVO));
					} catch (FinderException finderException) {
						// ignore create if not
						uldAtAirportVO.setFinalDestination(containerDetailsVO
								.getDestination());
						uldAtAirport = new ULDAtAirport(uldAtAirportVO);
					}
				}


				if(uldAtAirport != null){
						uldAtAirport.insertMailBagInULDAtArpAcceptanceDtls(containerDetailsVO,mailAcceptanceVO.isInventoryForArrival());
				}



				log.exiting("MailAcceptance", "insertContainerDestAcceptanceDtls");
			}

			/**
			 * @author A-1739
			 * @param mailAcceptanceVO
			 * @param containerDetailsVO
			 * @throws SystemException
			 * @throws DuplicateMailBagsException 
			 */
			private void updateContainerDestAcceptanceDtls(
					MailAcceptanceVO mailAcceptanceVO,
					ContainerDetailsVO containerDetailsVO) throws SystemException, DuplicateMailBagsException {

				ULDAtAirportVO uldAtAirportVO = new ULDAtAirportVO();
				uldAtAirportVO.setCompanyCode(mailAcceptanceVO.getCompanyCode());
				uldAtAirportVO.setCarrierId(mailAcceptanceVO.getCarrierId());
				uldAtAirportVO.setAirportCode(mailAcceptanceVO.getPol());
				uldAtAirportVO.setCarrierCode(mailAcceptanceVO.getFlightCarrierCode());
				uldAtAirportVO.setLastUpdateUser(mailAcceptanceVO.getAcceptedUser());
				if(!mailAcceptanceVO.isInventory()){
					containerDetailsVO.setMailUpdateFlag(true);
				}

				boolean isScanned = mailAcceptanceVO.isScanned();
				Collection<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();
				Collection<DespatchDetailsVO> despatchDetailsVOs = containerDetailsVO
						.getDesptachDetailsVOs();
				if (containerDetailsVO.getMailDetails() != null) {
					mailbagVOs.addAll(containerDetailsVO.getMailDetails());
				}
				if (despatchDetailsVOs != null) {
					for (DespatchDetailsVO despatchDetailsVO : despatchDetailsVOs) {
						mailbagVOs.add(MailtrackingDefaultsVOConverter
								.convertToMailBagVO(despatchDetailsVO));
					}
				}
				ULDAtAirport uldAtAirport = null;
				boolean isBulkContainer = false;
				if (MailConstantsVO.ULD_TYPE.equals(containerDetailsVO
						.getContainerType())) {
					uldAtAirportVO
							.setUldNumber(containerDetailsVO.getContainerNumber());
					try {
						uldAtAirport = ULDAtAirport
								.find(constructULDArpPKFromULDArpVO(uldAtAirportVO));

						uldAtAirport.setRemarks(containerDetailsVO.getRemarks());
						uldAtAirport
								.setWarehouseCode(containerDetailsVO.getWareHouse());
						uldAtAirport.setLocationCode(containerDetailsVO.getLocation());
						uldAtAirport.setLastUpdateUser(mailAcceptanceVO
								.getAcceptedUser());
						uldAtAirport.setTransferFromCarrier(
								containerDetailsVO.getTransferFromCarrier());
						uldAtAirport.setLastUpdateTime(
							containerDetailsVO.getUldLastUpdateTime());
						/*if (mailbagVOs.size() > 0) {

							for (MailbagVO mailbagVO : mailbagVOs) {
								if (OPERATION_FLAG_INSERT.equals(mailbagVO
										.getOperationalFlag())) {

									uldAtAirport.setNumberOfBags(uldAtAirport
											.getNumberOfBags() + 1);
									uldAtAirport.setTotalWeight(uldAtAirport
											.getTotalWeight() + mailbagVO.getWeight());
								}

							}

						}*/
					} catch (FinderException exception) {
						uldAtAirport = new ULDAtAirport(uldAtAirportVO);    
						//throw new SystemException("NO SUCH ULD", exception);
					}

				} else if (MailConstantsVO.BULK_TYPE.equals(containerDetailsVO
						.getContainerType())) {
					isBulkContainer = true;
					uldAtAirportVO.setUldNumber(
						constructBulkULDNumber(
								containerDetailsVO.getDestination(),
								containerDetailsVO.getCarrierCode()));
					try {
						uldAtAirport = ULDAtAirport
								.find(constructULDArpPKFromULDArpVO(uldAtAirportVO));
						uldAtAirport.setLastUpdateUser(mailAcceptanceVO
								.getAcceptedUser());
						uldAtAirport.setLastUpdateTime(
								 new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
						/*if (mailbagVOs.size() > 0) {

							for (MailbagVO mailbagVO : mailbagVOs) {
								if (OPERATION_FLAG_INSERT.equals(mailbagVO
										.getOperationalFlag())) {

									uldAtAirport.setNumberOfBags(uldAtAirport
											.getNumberOfBags() + 1);
									uldAtAirport.setTotalWeight(uldAtAirport
											.getTotalWeight() + mailbagVO.getWeight());
								}

							}

						}*/
					} catch (FinderException finderException) {
						uldAtAirport = new ULDAtAirport(uldAtAirportVO);
						//throw new SystemException("ULD NOT FOUND", finderException);
					}
				}


				new ULDAtAirport().insertMailBagInULDAtArpAcceptanceDtls(containerDetailsVO,mailAcceptanceVO.isInventoryForArrival());





			}


			/**
			 * @author A-1739
			 * @param oldDSNVO
			 * @return
			 * @throws SystemException
			 */
			private DSNVO cloneDSNVO(DSNVO oldDSNVO) throws SystemException {
				DSNVO dsnVO = new DSNVO();
				BeanHelper.copyProperties(dsnVO, oldDSNVO);
				// for making addition generic
				dsnVO.setBags(0);
				//dsnVO.setWeight(0);
				dsnVO.setWeight(new Measure(UnitConstants.MAIL_WGT,0.0));//added by A-7371
				return dsnVO;
			}

			/**
			 * A-1739
			 *
			 * @param dsnMSTVO
			 * @param dsnVO
			 * @param mailAcceptanceVO
			 * @throws UnitException 
			 */
			private void updateDSNAtAirport(DSNVO dsnMSTVO, DSNVO dsnVO,
					MailAcceptanceVO mailAcceptanceVO) throws UnitException {
				log.entering("MailAcceptance", "updateDSNAtAirport");
				for (DSNAtAirportVO dsnAtArpVO : dsnMSTVO.getDsnAtAirports()) {
					if (dsnAtArpVO.getAirportCode().equals(mailAcceptanceVO.getPol())) {
						dsnAtArpVO.setTotalAcceptedBags(dsnVO.getBags()
								- dsnVO.getPrevBagCount());
						/*dsnAtArpVO.setTotalAcceptedWeight(dsnVO.getWeight()
								- dsnVO.getPrevBagWeight());*/
						if(dsnVO.getWeight()!=null && dsnVO.getPrevBagWeight()!=null){
						dsnAtArpVO.setTotalAcceptedWeight(Measure.subtractMeasureValues(dsnVO.getWeight(), dsnVO.getPrevBagWeight()));//added by A-7371
						}
						if (mailAcceptanceVO.getFlightSequenceNumber() != DESTN_FLT) {
							dsnAtArpVO.setTotalBagsInFlight(dsnVO.getBags()
									- dsnVO.getPrevBagCount());
							/*dsnAtArpVO.setTotalWeightInFlight(dsnVO.getWeight()
									- dsnVO.getPrevBagWeight());*/
							if(dsnVO.getWeight()!=null && dsnVO.getPrevBagWeight()!=null){//added by A-7371
							dsnAtArpVO.setTotalWeightInFlight(Measure.subtractMeasureValues(dsnVO.getWeight(), dsnVO.getPrevBagWeight()));//added by A-7371
							}
						} else {
							dsnAtArpVO.setTotalBagsAtDestination(dsnVO.getBags()
									- dsnVO.getPrevBagCount());
							/*dsnAtArpVO.setTotalWeightAtDestination(dsnVO.getWeight()
									- dsnVO.getPrevBagWeight());*/
							if(dsnVO.getWeight()!=null && dsnVO.getPrevBagWeight()!=null){//added by A-7371
								dsnAtArpVO.setTotalWeightAtDestination(Measure.subtractMeasureValues(dsnVO.getWeight(), dsnVO.getPrevBagWeight()));//added by A-7371
							}
						}
						break;
					}
				}
				log.exiting("MailAcceptance", "updateDSNAtAirport");
			}


			/**
			 * A-1739
			 *
			 * @param mailbagVO
			 * @param mailAcceptanceVO
			 * @param containerDetailsVO
			 * @return
			 */
			private Collection<MailbagHistoryVO> constructMailbagHistories(
					MailbagVO mailbagVO, MailAcceptanceVO mailAcceptanceVO,
					ContainerDetailsVO containerDetailsVO) {
				log.entering("MailAcceptance", "constructMailbagHistories");
				MailbagHistoryVO historyVO = new MailbagHistoryVO();
				historyVO.setMailStatus(MailConstantsVO.MAIL_STATUS_ACCEPTED);
				historyVO.setCarrierId(mailAcceptanceVO.getCarrierId());
				historyVO.setFlightNumber(mailAcceptanceVO.getFlightNumber());
				historyVO.setFlightSequenceNumber(mailAcceptanceVO
						.getFlightSequenceNumber());
				historyVO.setFlightDate(mailAcceptanceVO.getFlightDate());
				historyVO.setCarrierCode(mailAcceptanceVO.getFlightCarrierCode());
				// Workaround for client
				if (historyVO.getCarrierCode() == null) {
					historyVO.setCarrierCode(mailAcceptanceVO.getFlightCarrierCode());
				}
				historyVO.setSegmentSerialNumber(containerDetailsVO
						.getSegmentSerialNumber());
				historyVO.setContainerNumber(containerDetailsVO.getContainerNumber());
				historyVO.setContainerType(containerDetailsVO.getContainerType());
				historyVO.setPou(containerDetailsVO.getPou());
				historyVO.setScannedPort(mailbagVO.getScannedPort());
				historyVO.setScanDate(mailbagVO.getScannedDate());
				historyVO.setScanUser(mailbagVO.getScannedUser());
		        historyVO.setMailClass(mailbagVO.getMailClass());
		        historyVO.setPaBuiltFlag(containerDetailsVO.getPaBuiltFlag());
		        historyVO.setMailSource(mailbagVO.getMailSource());//Added for ICRD-156218
		        historyVO.setMessageVersion(mailbagVO.getMessageVersion());
				Collection<MailbagHistoryVO> histories = new ArrayList<MailbagHistoryVO>();
				histories.add(historyVO);

				log.exiting("MailAcceptance", "constructMailbagHistories");
				return histories;
			}

			/**
			 * TODO Purpose
			 * Oct 9, 2006, a-1739
			 * @param containerAuditVO
			 * @param container
			 * @throws SystemException
			 */
			private void performContainerAudit(
					ContainerAuditVO containerAuditVO, Container container)
			throws SystemException {
				log.entering("MailAcceptance", "performContainerAudit");
				ContainerPK containerPK = container.getContainerPK();
				containerAuditVO.setCompanyCode(containerPK.getCompanyCode());
				containerAuditVO.setContainerNumber(containerPK.getContainerNumber());
				containerAuditVO.setCarrierId(containerPK.getCarrierId());
				containerAuditVO.setFlightNumber(containerPK.getFlightNumber());
				containerAuditVO.setFlightSequenceNumber(
						containerPK.getFlightSequenceNumber());
				containerAuditVO.setAssignedPort(containerPK.getAssignmentPort());
				containerAuditVO.setLegSerialNumber(containerPK.getLegSerialNumber());
				//Added by A-7794 as part of ICRD-208677
				containerAuditVO.setAuditRemarks(container.getRemarks());
				AuditUtils.performAudit(containerAuditVO);
				log.exiting("MailAcceptance", "performContainerAudit");
			}

			/**
			 * A-1739
			 *
			 * @param containerDetailsVO
			 * @return
			 */
			private AssignedFlightSegmentPK constructAssignedFlightSegPK(
					ContainerDetailsVO containerDetailsVO) {
				AssignedFlightSegmentPK assignedFlightSegPK = new AssignedFlightSegmentPK();
				assignedFlightSegPK.setCompanyCode(   containerDetailsVO.getCompanyCode());
				assignedFlightSegPK.setCarrierId(   containerDetailsVO.getCarrierId());
				assignedFlightSegPK.setFlightNumber(   containerDetailsVO.getFlightNumber());
				assignedFlightSegPK.setFlightSequenceNumber(   containerDetailsVO
						.getFlightSequenceNumber());
				assignedFlightSegPK.setSegmentSerialNumber(   containerDetailsVO
						.getSegmentSerialNumber());
				return assignedFlightSegPK;
			}


			private String constructBulkULDNumber(String airport,String carrierCode) {
				/*
				 * This "airport" can be the POU / Destination
				 */
				if(airport != null && airport.trim().length() > 0) {
					return new StringBuilder().append(MailConstantsVO.CONST_BULK)
					.append(MailConstantsVO.SEPARATOR).append(airport).toString();
				} else {
					//This case comes during arrival
					return MailConstantsVO.CONST_BULK_ARR_ARP.concat(MailConstantsVO.SEPARATOR).concat(carrierCode);
				}
			}


			/**
			 * @author A-1739
			 * @param uldAtAirportVO
			 * @return
			 */
			private ULDAtAirportPK constructULDArpPKFromULDArpVO(
					ULDAtAirportVO uldAtAirportVO) {
				ULDAtAirportPK uldArpPK = new ULDAtAirportPK();
				uldArpPK.setCompanyCode(   uldAtAirportVO.getCompanyCode());
				uldArpPK.setCarrierId(   uldAtAirportVO.getCarrierId());
				uldArpPK.setAirportCode(   uldAtAirportVO.getAirportCode());
				uldArpPK.setUldNumber(   uldAtAirportVO.getUldNumber());
				return uldArpPK;
			}




			/**
			 * @author A-1739
			 * @param mailAcceptanceVO
			 * @param dsnMap
			 * @param hasFlightDeparted
			 * @throws SystemException
			 */
			public void flagResditsForAcceptance(MailAcceptanceVO mailAcceptanceVO,
					Collection<MailbagVO> acceptedMailbags, boolean hasFlightDeparted)
					throws SystemException {
				log.exiting("MailAcceptance", "flagResditsForAcceptance");

				// Added By Karthick V Starts
				Collection<ContainerDetailsVO> acceptedUlds = null;
				if (mailAcceptanceVO.getContainerDetails() != null
						&& mailAcceptanceVO.getContainerDetails().size() > 0) {
					acceptedUlds = new ArrayList<ContainerDetailsVO>();

					for (ContainerDetailsVO containerDetailsVO : mailAcceptanceVO
							.getContainerDetails()) {
						if (ContainerDetailsVO.FLAG_YES.equals(containerDetailsVO
								.getPaBuiltFlag())
								&& MailConstantsVO.ULD_TYPE.equals(containerDetailsVO
										.getContainerType())) {
							acceptedUlds.add(containerDetailsVO);
						}
					}

				}
				MailController mailController = (MailController)SpringAdapter.getInstance().getBean("mAilcontroller");
				mailController.flagResditsForAcceptance(mailAcceptanceVO,
						hasFlightDeparted, acceptedMailbags, acceptedUlds);
				LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
				if(mailAcceptanceVO.isFromDeviationList() && mailAcceptanceVO.isAssignedToFlight()){
					for(MailbagVO mailbagVO:acceptedMailbags){
						if(mailbagVO.getLastUpdateUser()==null) {
							mailbagVO.setLastUpdateUser(logonAttributes.getUserId());
						}
				Collection<FlightValidationVO> flightVOs=null;
				FlightFilterVO	flightFilterVO=new FlightFilterVO();      
				flightFilterVO.setCompanyCode(mailAcceptanceVO.getCompanyCode());
				flightFilterVO.setDirection(MailConstantsVO.OPERATION_OUTBOUND);  
				flightFilterVO.setStation(mailAcceptanceVO.getPol());
				flightFilterVO.setPageNumber(1);      
				flightFilterVO.setFlightNumber(mailAcceptanceVO.getFlightNumber());
				flightFilterVO.setFlightSequenceNumber(mailAcceptanceVO.getFlightSequenceNumber());
					
				
					flightVOs=new MailController().validateFlight(flightFilterVO);
					if(flightVOs!=null && !flightVOs.isEmpty() && mailbagVO!=null ){
					mailController.flagHistoryforFlightArrival(mailbagVO,flightVOs);	
					mailController.flagAuditforFlightArrival(mailbagVO,flightVOs);
					}
					
					}
			}
				}




			/**
			 * @author A-1739
			 * @param mailbagVOs
			 * @throws SystemException
			 * @throws DuplicateMailBagsException 
			 */
			public void updateAcceptedMailbags(Collection<MailbagVO> mailbagVOs)
					throws SystemException, DuplicateMailBagsException {
				log.entering("DSN", "addMailbags");

				for (MailbagVO mailbagVO : mailbagVOs) {
						// Added for ICRD-255189 starts
						mailbagVO = new MailController().constructOriginDestinationDetails(mailbagVO);
						//Added for ICRD-255189 ends
						
						Mailbag mailbag = null;
						//Modified for ICRD-126626
						try {
							mailbag = findMailbag(constructMailbagPK(mailbagVO));
						} catch (FinderException e) {
							// TODO Auto-generated catch block
							//e.printStackTrace();
						}
						boolean isDuplicate = false;
						if(mailbag!=null){
							//Added by A-8353 as part of ICRD-230449 starts
							if(!MailConstantsVO.MAIL_STATUS_NEW.equals(mailbag.getLatestStatus()) && !mailbagVO.isFromDeviationList() && !"CARDIT".equals(mailbagVO.getFromPanel())){
							isDuplicate = new MailController().checkForDuplicateMailbag(mailbagVO.getCompanyCode(),mailbagVO.getPaCode(),mailbag);
							//Added by A-8353 as part of ICRD-230449 ends
							/*if(mailbagVO.getFlightNumber().equals(mailbag.getFlightNumber()) && 
									mailbagVO.getFlightSequenceNumber()==(mailbag.getFlightSequenceNumber()) 
									&& !isDuplicate){
								throw new DuplicateMailBagsException(
					        			DuplicateMailBagsException.
					        			DUPLICATEMAILBAGS_EXCEPTION,
					        			new Object[] {mailbagVO.getMailbagId()});
							    }*/
							}
							if(mailbagVO.getFlightNumber()!=null && mailbagVO.getMailbagId()!=null && (mailbag.getMailIdr().equals(mailbagVO.getMailbagId())) &&
									!mailbagVO.getFlightNumber().equals(mailbag.getFlightNumber())){
										mailbagVO.setMailUpdateFlag(true);
							}else if(mailbagVO.getFlightNumber().equals(mailbag.getFlightNumber()) 
									&& (MailConstantsVO.MAIL_STATUS_NEW.equals(mailbag.getLatestStatus()) || MailConstantsVO.MAIL_STATUS_AWB_BOOKED.equals(mailbag.getLatestStatus()))){
								mailbagVO.setMailUpdateFlag(true);       
							}
						if(!isDuplicate)	{
							//Added by A-8176 start
							//This was added to set the malseqnum for mailbags which was updated by mail outbound screen
						 if(mailbag!=null && mailbag.getMailbagPK()!=null){
								mailbagVO.setMailSequenceNumber(mailbag.getMailbagPK().getMailSequenceNumber());
						 }
						 //Added by A-8176 ends
						mailbag.updateAcceptanceFlightDetails(mailbagVO);
						}
						mailbag.updateAcceptanceDamage(mailbagVO);
						mailbag.setLastUpdateTime(
							mailbagVO.getLastUpdateTime());
						//Added by A-7540 as a part of ICRD-197419
						mailbag.setMailRemarks(mailbagVO.getMailRemarks()); 
						mailbag.setMailbagSource(mailbag.getMailbagSource());
						mailbag.setPaCode(mailbagVO.getPaCode());
						mailbag.updatePrimaryAcceptanceDetails(mailbagVO);
					} 
						
					 if(mailbag == null || isDuplicate) {
						 
						/*if(mailbagVO.getFlightSequenceNumber()>0 ){
								mailbagVO.setLatestStatus(MailConstantsVO.MAIL_STATUS_ASSIGNED);
						}*/
						
						String paCode=findSystemParameterValue(USPS_DOMESTIC_PA);
						if(mailbagVO.getMailbagId().length()==12 ||mailbagVO.getMailbagId().length()==10 && paCode.equals(mailbagVO.getPaCode())){
							String routIndex=mailbagVO.getMailbagId().substring(4,8);
							
							String org=null;
							String dest=null;
							Collection<RoutingIndexVO> routingIndexVOs=new ArrayList <RoutingIndexVO>();
							RoutingIndexVO routingIndexFilterVO=new RoutingIndexVO();
							routingIndexFilterVO.setCompanyCode(mailbagVO.getCompanyCode());
							routingIndexFilterVO.setRoutingIndex(routIndex);
							routingIndexFilterVO.setScannedDate(mailbagVO.getScannedDate());
							routingIndexVOs=findRoutingIndex(routingIndexFilterVO);
							exchangeOfficeMap=new HashMap<String,String>();
							for(RoutingIndexVO routingIndexVO:routingIndexVOs){
							if(routingIndexVO!=null&&routingIndexVO.getRoutingIndex()!=null){
								 org=routingIndexVO.getOrigin();
								 dest=routingIndexVO.getDestination();
								exchangeOfficeMap=findOfficeOfExchangeForPA(mailbagVO.getCompanyCode(),findSystemParameterValue(USPS_DOMESTIC_PA));
								if(exchangeOfficeMap!=null && !exchangeOfficeMap.isEmpty()){
						    		if(exchangeOfficeMap.containsKey(org)){
						    				
						    			mailbagVO.setOoe(exchangeOfficeMap.get(org));
						    		}
						    		if(exchangeOfficeMap.containsKey(dest)){
						    			mailbagVO.setDoe(exchangeOfficeMap.get(dest));
						    		}
						    	}
							
							mailbagVO.setMailCategoryCode("B");
							String mailClass=mailbagVO.getMailbagId().substring(3,4);
							mailbagVO.setMailClass(mailClass);
							mailbagVO.setMailSubclass(mailClass+"X");
							mailbagVO.setOrigin(org);
							mailbagVO.setDestination(dest);
							
							int lastTwoDigits = Calendar.getInstance().get(Calendar.YEAR) % 100;
							String lastDigitOfYear = String.valueOf(lastTwoDigits).substring(1,2);
							mailbagVO.setYear(Integer.parseInt(lastDigitOfYear));
							mailbagVO.setHighestNumberedReceptacle("9");
							mailbagVO.setRegisteredOrInsuredIndicator("9");
							/*String despacthNumber=generateDespatchSerialNumber(MailConstantsVO.FLAG_YES,mailbagVO);
							String rsn=generateReceptacleSerialNumber(despacthNumber,mailbagVO);
					    	if(rsn.length()>3){
							
					    		generateDespatchSerialNumber(MailConstantsVO.FLAG_NO,mailbagVO);
					    		despacthNumber=(generateDespatchSerialNumber(MailConstantsVO.FLAG_YES,mailbagVO));
					    		rsn=generateReceptacleSerialNumber(despacthNumber,mailbagVO);				
								
					    	}*/
							
							mailbagVO.setDespatchSerialNumber(MailConstantsVO.DOM_MAILBAG_DEF_DSNVAL);  
							mailbagVO.setReceptacleSerialNumber(MailConstantsVO.DOM_MAILBAG_DEF_RSNVAL);
							
					    	mailbagVO.setWeight(new Measure(UnitConstants.MAIL_WGT, Double.parseDouble(mailbagVO.getMailbagId().substring(10,12))));
					    	mailbagVO.setAcceptedWeight(new Measure(UnitConstants.MAIL_WGT, Double.parseDouble(mailbagVO.getMailbagId().substring(10,12))));	
					    	mailbagVO.setStrWeight(new Measure(UnitConstants.MAIL_WGT, Double.parseDouble(mailbagVO.getMailbagId().substring(10,12))));	
							}
							}
						}
						if (OPERATION_FLAG_INSERT
								.equals(mailbagVO.getOperationalFlag())) {
							 mailbagVO.setConsignmentDate(mailbagVO.getScannedDate());	// Added by A-8353 for ICRD-230449
							 //Added by A-7794 as part of ICRD-232299
							 String scanWaved = constructDAO().checkScanningWavedDest(mailbagVO);
							 if(scanWaved != null){
								 mailbagVO.setScanningWavedFlag(scanWaved);
							 }
							 
							//ICRD-341146 Begin 
								if(new MailController().isUSPSMailbag(mailbagVO)){
									mailbagVO.setOnTimeDelivery(MailConstantsVO.FLAG_NO);
								}else{
									mailbagVO.setOnTimeDelivery(MailConstantsVO.FLAG_NOT_AVAILABLE);
								}
							//ICRD-341146 End
							MailController.calculateAndUpdateLatestAcceptanceTime(mailbagVO);

							MailAcceptance.populatePrimaryAcceptanceDetails(mailbagVO);
							Mailbag mailbagForAdd = Mailbag.getInstance().persistMailbag(mailbagVO);

							if(mailbagForAdd!=null && mailbagForAdd.getMailbagPK()!=null){
								mailbagVO.setMailSequenceNumber(mailbagForAdd.getMailbagPK().getMailSequenceNumber());
							}
//							performMailbagAudit(mailbagAuditVO, mailbag,
//									AuditVO.CREATE_ACTION, null);
						} else {
							throw new SystemException("NO MAIL BAG FOUND");
						}
					}

				}
				log.exiting("DSN", "addMailbags");
			}

			/**
			 * @author A-5991
			 * @param mailbagVO
			 * @return
			 * @throws SystemException
			 */
			private MailbagPK constructMailbagPK(MailbagVO mailbagVO) throws SystemException{
				MailbagPK mailbagPK=new MailbagPK();
				mailbagPK.setCompanyCode(mailbagVO.getCompanyCode());
				//mail sequence number check added as part of IASCB-52624 by A-8672 to handle the insert from deviation and other panels
				if(mailbagVO.getMailSequenceNumber()>0){
					mailbagPK.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
				}else{
				mailbagPK.setMailSequenceNumber(findMailSequenceNumber(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode()));
				}
				return mailbagPK;
			}

		     /**
		        * @author A-5991
		        * @param mailBagId
		        * @param companyCode
		        * @return
		        * @throws SystemException
		        */
		       public long findMailSequenceNumber(String mailBagId,String companyCode)
		    		   throws SystemException{

		    	   return constructDAO().findMailSequenceNumber(mailBagId, companyCode);

		       }

		       private Mailbag findMailbag(MailbagPK mailbagPK) throws FinderException, SystemException{

		    	   return Mailbag.find(mailbagPK);
		       }


		   	/**
				 * Utilty for finding syspar Mar 23, 2007, A-1739
				 *
				 * @param syspar
				 * @return
				 * @throws SystemException
				 */
				private String findSystemParameterValue(String syspar)
				throws SystemException {
					String sysparValue = null;
					ArrayList<String> systemParameters = new ArrayList<String>();
					systemParameters.add(syspar);
					HashMap<String, String> systemParameterMap = Proxy.getInstance().get(SharedDefaultsProxy.class)
							.findSystemParameterByCodes(systemParameters);
					log.log(Log.FINE, " systemParameterMap ", systemParameterMap);
					if (systemParameterMap != null) {
						sysparValue = systemParameterMap.get(syspar);
					}
					return sysparValue;
				}

				/**
				 * @author A-5991
				 * @param containerDetailsVOs
				 * @return
				 * @throws SystemException 
				 */
				public Collection<MailbagVO>getMailBags(MailAcceptanceVO mailAcceptanceVO,Collection<ContainerDetailsVO>containerDetailsVOs) throws SystemException{
					Collection<MailbagVO>mailbagVOs=null;
					Collection<DespatchDetailsVO>despatchDetailsVOs=null;
					if(containerDetailsVOs!=null){
						for(ContainerDetailsVO containerDetailsVO:containerDetailsVOs){
							if(mailbagVOs==null){
								mailbagVOs=new ArrayList<MailbagVO>();
							}
							if(containerDetailsVO.getMailDetails()!=null){
								
								
								for(MailbagVO mailbagVO:containerDetailsVO.getMailDetails()){
									if(!(("I".equals(mailbagVO.getOperationalFlag())) || "U".equals(mailbagVO.getOperationalFlag())) 
											|| (MailbagVO.FLAG_YES.equals(mailbagVO.getTransferFlag()) && mailbagVO.isFromDeviationList()))
										{
										continue;
										}
									mailbagVO.setMailbagHistories(constructMailbagHistories(mailbagVO, mailAcceptanceVO, containerDetailsVO));
									mailbagVO.setCarrierCode(mailAcceptanceVO.getFlightCarrierCode());
									//mailbagVO.setPaBuiltFlag(mailAcceptanceVO.getPaBuiltFlag());   
									if(mailbagVO.getPou()==null||mailbagVO.getPou().isEmpty()){
									mailbagVO.setPou(containerDetailsVO.getDestination());
									}
									if(mailAcceptanceVO.getCompanyCode()!=null){
										mailbagVO.setCompanyCode(mailAcceptanceVO.getCompanyCode());
									}
									//adding this to check the lat warning message 
									if (mailAcceptanceVO.getShowWarning() != null) {
										mailbagVO.setLatValidationNeeded(mailAcceptanceVO.getShowWarning());
									}
									//Added for ICRD-140584 starts
									if(!mailAcceptanceVO.isInventory()){
										mailbagVO.setMailUpdateFlag(true);
									}
									//Added for ICRD-140584 ends
									//Added for ICRD-243469 starts
									String paCode = null;
									String companyCode = mailbagVO.getCompanyCode();
									String originOfFiceExchange = mailbagVO.getOoe();
									if(mailbagVO.getPaCode() == null){
										try{
											paCode = new MailController().findPAForOfficeOfExchange(companyCode,originOfFiceExchange);
										}catch (SystemException e) {
											
										}
										
										mailbagVO.setPaCode(paCode);
									}
									/*String serviceLevel = null;
									serviceLevel = findMailServiceLevel(mailbagVO);
									if(serviceLevel!=null){
										mailbagVO.setMailServiceLevel(serviceLevel);
									}*/
									String scanWavedAirport = constructDAO().checkScanningWavedDest(mailbagVO);
									if(scanWavedAirport!=null){
										mailbagVO.setScanningWavedFlag(scanWavedAirport);
									}
									if(mailAcceptanceVO.isFromDeviationList()){
										mailbagVO.setFromDeviationList(true);
									}
									mailbagVO.setMailSource(mailAcceptanceVO.getMailSource());
									mailbagVO.setMailbagDataSource(mailAcceptanceVO.getMailDataSource());
									//Added by A-8527 for IASCB-58918
									mailbagVO.setMessageVersion(mailAcceptanceVO.getMessageVersion());
									//Added for ICRD-243469 ends
									mailbagVOs.add(mailbagVO);
								}
							}
							if(containerDetailsVO.getDesptachDetailsVOs()!=null){
								for(DespatchDetailsVO despatchDetailsVO:containerDetailsVO.getDesptachDetailsVOs()){
									MailbagVO  mailbagVO=MailtrackingDefaultsVOConverter.convertToMailBagVO(despatchDetailsVO);
									mailbagVO.setMailbagHistories(constructMailbagHistories(mailbagVO, mailAcceptanceVO, containerDetailsVO));
									mailbagVO.setCarrierCode(mailAcceptanceVO.getFlightCarrierCode());
									mailbagVO.setPou(containerDetailsVO.getDestination());
									mailbagVO.setPaBuiltFlag(containerDetailsVO.getPaBuiltFlag());
									if(mailAcceptanceVO.getCompanyCode()!=null){
										mailbagVO.setCompanyCode(mailAcceptanceVO.getCompanyCode());
									}
									mailbagVOs.add(mailbagVO);
								}
							}
						}
					}
					return mailbagVOs;
				}


				/**
				 * @author A-1936 This method is used to find the MailBags Tat has been
				 *         accepted to the Flight
				 * @param operationalFlightVO
				 * @return
				 * @throws SystemException
				 */
				public static Collection<MailbagVO> findMailBagsForUpliftedResdit(
						OperationalFlightVO operationalFlightVO) throws SystemException {
					Collection<MailbagVO> mailbags = null;
					try {
						mailbags = constructDAO().findMailBagsForUpliftedResdit(
								operationalFlightVO);
					} catch (PersistenceException ex) {
						throw new SystemException(ex.getErrorCode(), ex);
					}
					return mailbags;
				}

				/**
				 * @author A-1876 This method is used to find the ULDs That has been
				 *         accepted to the Flight
				 * @param operationalFlightVO
				 * @return
				 * @throws SystemException
				 */
				public static Collection<ContainerDetailsVO> findUldsForUpliftedResdit(
						OperationalFlightVO operationalFlightVO) throws SystemException {
					Collection<ContainerDetailsVO> containerDetailsVOs = null;
					try {
						containerDetailsVOs = constructDAO().findUldsForUpliftedResdit(
								operationalFlightVO);
					} catch (PersistenceException ex) {
						throw new SystemException(ex.getErrorCode(), ex);
					}
					return containerDetailsVOs;
				}
				/**
				 * @author A-1936
				 * This method is used to
				 * @param mailAcceptanceVO
				 * @throws ContainerAssignmentException
				 * @throws FlightClosedException
				 * @throws InvalidFlightSegmentException
				 * @throws SystemException
				 * @throws ULDDefaultsProxyException
				 * @throws CapacityBookingProxyException
				 * @throws MailBookingException
				 * @throws DuplicateMailBagsException 
				 */
			    public void  saveInventoryDetailsForArrival(MailAcceptanceVO mailAcceptanceVO)
			    throws ContainerAssignmentException, FlightClosedException,
			    InvalidFlightSegmentException, SystemException, ULDDefaultsProxyException, CapacityBookingProxyException, MailBookingException,MailDefaultStorageUnitException, DuplicateMailBagsException{
			    log.entering("MailAcceptance","saveInventoryDetailsForArrival");
			    	 new MailController().saveAcceptedContainers(mailAcceptanceVO);
			    	 new MailAcceptance().saveAirportDetailsForArrival(mailAcceptanceVO);
			    log.exiting("MailAcceptance","saveInventoryDetailsForArrival");
			    }
			    /**
				 * @author A-2553
				 * This method is used to  save arrival details to airport
				 * @param mailAcceptanceVO
			     * @throws DuplicateMailBagsException 
				 */
				private void saveAirportDetailsForArrival(MailAcceptanceVO mailAcceptanceVO)
				throws SystemException, DuplicateMailBagsException{
					log.entering("MailAcceptance", "saveAirportDetailsForArrival");
					boolean isAcceptanceToFlt = false;
					Collection<MailbagVO> mailBagsForMonitorSLA =null;
					// Only needed for Flight acceptance . Since we r reusing constructDSNMap(),initialised, mailBagsForMonitorSLA =null
					boolean hasUpdated = false;
					hasUpdated = saveDestnAcceptanceDetails(mailAcceptanceVO);
					if (hasUpdated) {
			           log.log(Log.INFO, "THE HAS UPDATED IN THE MAILACCEPTANCE",
							hasUpdated);
						/*
			             *Added By Karthick V
			             * Pass the Reference of the mailBagsForMonitorSLA and get back all the
			             * mailBags that are collected for Monitoring the SLA to the Controller
			             *
			             */
						updateContainers(mailAcceptanceVO);
					}
					log.exiting("MailAcceptance", "saveAirportDetailsForArrival");
				}



				/**
				 * @author A-2037 The method is used to find the mail acceptance details.
				 * @param operationalFlightVO
				 * @return
				 * @throws SystemException
				 */
				public static MailAcceptanceVO findFlightAcceptanceDetails(
						OperationalFlightVO operationalFlightVO) throws SystemException {
					MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();
					Collection<ContainerDetailsVO> acceptedULDs =
							findAcceptedULDs(operationalFlightVO);
			        if(acceptedULDs != null && acceptedULDs.size() > 0) {
			    	   mailAcceptanceVO.setContainerDetails(acceptedULDs);
					}
					return mailAcceptanceVO;
				}
				/**
				 * @author A-1739
				 * @param operationalFlightVO
				 * @return
				 * @throws SystemException
				 */
				private static Collection<ContainerDetailsVO> findAcceptedULDs(
						OperationalFlightVO operationalFlightVO) throws SystemException {
					return Container.findAcceptedULDs(operationalFlightVO);
				}

				/**
				 * @author A-1936
				 * @param companyCode
				 * @param airportCode
				 * @param warehouseCode
				 * @param transactionCodes
				 * @return
				 * @throws SystemException
				 */
				public static Map<String, Collection<String>> findWarehouseTransactionLocations(String companyCode,
						String airportCode,String warehouseCode,Collection<String> transactionCodes) throws SystemException {

					return new WarehouseDefaultsProxy().findWarehouseTransactionLocations(companyCode,
							airportCode,warehouseCode,transactionCodes);

				}

				/**
				 * @author a-1936 This method is used to find all the WareHouses for the
				 *         Given Airport
				 * @param companyCode
				 * @param airportCode
				 * @return
				 * @throws SystemException
				 */
				public static Collection<WarehouseVO> findAllWarehouses(String companyCode,
						String airportCode) throws SystemException {
					return new WarehouseDefaultsProxy().findAllWarehouses(companyCode,
							airportCode);

				}
				/**
				 * @author a-1936 This method is used to validate the Location
				 * @param companyCode
				 * @param airportCode
				 * @param warehouseCode
				 * @param locationCode
				 * @return
				 * @throws SystemException
				 */
				public static LocationValidationVO validateLocation(String companyCode,
						String airportCode, String warehouseCode, String locationCode)
						throws SystemException {
					return new WarehouseDefaultsProxy().validateLocation(companyCode,
							airportCode, warehouseCode, locationCode);

				}

				/**
				 * @author A-2037 The method is used to find the mail acceptance details for
				 *         destination Assigned.
				 * @param operationalFlightVO
				 * @return MailAcceptanceVO
				 * @throws SystemException
				 */
				public static MailAcceptanceVO findDestinationAcceptanceDetails(
						OperationalFlightVO operationalFlightVO) throws SystemException {

					MailAcceptanceVO mailAcceptanceVO = null;
					Collection<ContainerDetailsVO> acceptedULDs =
						findAcceptedULDs(operationalFlightVO);
					if (acceptedULDs != null && acceptedULDs.size() > 0) {
						mailAcceptanceVO = new MailAcceptanceVO();
						mailAcceptanceVO.setContainerDetails(acceptedULDs);
					}
					return mailAcceptanceVO;
				}

				/**
				 * @author A-1936 This method is used to find the Offload details
				 * @param offloadFilterVO
				 * @return
				 * @throws SystemException
				 */
				public static OffloadVO findOffloadDetails(OffloadFilterVO offloadFilterVO)
						throws SystemException {
					OffloadVO offloadVo = null;

					if (MailConstantsVO.OFFLOAD_CONTAINER.equals(offloadFilterVO
							.getOffloadType())) {
						offloadVo = findAcceptedContainersForOffload(offloadFilterVO);
					} else if (MailConstantsVO.OFFLOAD_DSN.equals(offloadFilterVO
							.getOffloadType())) {
						offloadVo = findAcceptedDespatchesForOffload(offloadFilterVO);
					} else {
						offloadVo = findAcceptedMailBagsForOffload(offloadFilterVO);
					}
					return offloadVo;
				}

				/**
				 * @author A-1936 This method is used to findAcceptedContainersForOffload
				 * @param offloadFilterVO
				 * @return
				 * @throws SystemException
				 */
				private static OffloadVO findAcceptedContainersForOffload(
						OffloadFilterVO offloadFilterVO) throws SystemException {
					Collection<ContainerVO> containerVos = null;
					OffloadVO offloadVo = null;
					try {
						containerVos = constructDAO().findAcceptedContainersForOffload(
								offloadFilterVO);
					} catch (PersistenceException ex) {
						throw new SystemException(ex.getErrorCode(), ex);
					}
					if (containerVos != null && containerVos.size() > 0) {
						offloadVo = constructOffloadVO(offloadFilterVO);
						offloadVo.setOffloadContainers(containerVos);
					}
					return offloadVo;
				}

				/**
				* @author A-1936 This method is used to findAcceptedDespatchesForOffload
				* @param offloadFilterVO
				* @return
				* @throws SystemException
				*/
				private static OffloadVO findAcceptedDespatchesForOffload(
					OffloadFilterVO offloadFilterVO) throws SystemException {
				Page<DespatchDetailsVO> despatchDetailsVOs = null;
				OffloadVO offloadVo = null;
				try {
					despatchDetailsVOs = constructDAO()
							.findAcceptedDespatchesForOffload(offloadFilterVO);
				} catch (PersistenceException ex) {
					throw new SystemException(ex.getErrorCode(), ex);
				}
				if (despatchDetailsVOs != null && despatchDetailsVOs.size() > 0) {
					offloadVo = constructOffloadVO(offloadFilterVO);
					offloadVo.setOffloadDSNs(despatchDetailsVOs);
				}
				return offloadVo;
				}

				/**
				 * @author A-1936 This method is used to findAcceptedMailBagsForOffload
				 * @param offloadFilterVO
				 * @return
				 * @throws SystemException
				 */
				private static OffloadVO findAcceptedMailBagsForOffload(
						OffloadFilterVO offloadFilterVO) throws SystemException {
					Page<MailbagVO> mailbagVOs = null;
					OffloadVO offloadVo = null;
					try {
						mailbagVOs = constructDAO().findAcceptedMailBagsForOffload(
								offloadFilterVO);
					} catch (PersistenceException ex) {
						throw new SystemException(ex.getErrorCode(), ex);
					}
					if (mailbagVOs != null && mailbagVOs.size() > 0) {
						offloadVo = constructOffloadVO(offloadFilterVO);
						offloadVo.setOffloadMailbags(mailbagVOs);
					}
					return offloadVo;
				}


				/**
				 * @author A-1936 This method is used to construct the OffloadVO
				 * @param offloadFilterVO
				 * @return
				 */
				private static OffloadVO constructOffloadVO(OffloadFilterVO offloadFilterVO) {
					OffloadVO offloadVo = new OffloadVO();
					offloadVo.setCompanyCode(offloadFilterVO.getCompanyCode());
					offloadVo.setFlightNumber(offloadFilterVO.getFlightNumber());
					offloadVo.setFlightSequenceNumber(offloadFilterVO
							.getFlightSequenceNumber());
					offloadVo.setPol(offloadFilterVO.getPol());
					offloadVo.setCarrierId(offloadFilterVO.getCarrierId());
					offloadVo.setLegSerialNumber(offloadFilterVO.getLegSerialNumber());
					offloadVo.setCarrierCode(offloadFilterVO.getCarrierCode());
					offloadVo.setFlightDate(offloadFilterVO.getFlightDate());
					return offloadVo;
				}


				/**
				 * TODO Purpose
				 * Jan 19, 2007, A-1739
				 * @param flightVO
				 * @return
				 */
				public static MailManifestVO findMailbagManifest(OperationalFlightVO flightVO)
				throws SystemException {
					try {
						return constructDAO().findMailbagManifestDetails(flightVO);
					} catch (PersistenceException ex) {
						throw new SystemException(ex.getMessage(), ex);
					}
				}

				/**
				 * TODO Purpose
				 * Jan 19, 2007, A-1739
				 * @param flightVO
				 * @return
				 * @throws SystemException
				 */
				public static MailManifestVO findMailAWBManifest(OperationalFlightVO flightVO)
				throws SystemException {
					try {
						return constructDAO().findMailAWBManifestDetails(flightVO);
					} catch (PersistenceException ex) {
						throw new SystemException(ex.getMessage(), ex);
					}
				}
				/**
				 * TODO Purpose
				 * Mar 27, 2008, A-2553
				 * @param flightVO
				 * @return
				 * @throws SystemException
				 */
				public static MailManifestVO findDSNMailbagManifest(OperationalFlightVO flightVO)
				throws SystemException {
					try {
						return constructDAO().findDSNMailbagManifest(flightVO);
					} catch (PersistenceException ex) {
						throw new SystemException(ex.getMessage(), ex);
					}
				}
				/**
				 * TODO Purpose
				 * Jan 19, 2007, A-1739
				 * @param flightVO
				 * @return
				 */
				public static MailManifestVO findDestnCatManifest(OperationalFlightVO flightVO)
				throws SystemException {
					try {
						return constructDAO().findManifestbyDestination(flightVO);
					} catch (PersistenceException ex) {
						throw new SystemException(ex.getMessage(), ex);
					}
				}

				/**
				 * TODO Purpose
				 * Sep 02, 2007, A-1876
				 * @param flightVO
				 * @return
				 */
				public static Collection<MailSummaryVO> findDestnCatManifestSummary(OperationalFlightVO flightVO)
				throws SystemException {
					try {
						return constructDAO().findManifestSummaryByDestination(flightVO);
					} catch (PersistenceException ex) {
						throw new SystemException(ex.getMessage(), ex);
					}
				}

/**
				 * TODO Purpose
				 * Jan 29, 2007, A-1739
				 * @param carditEnquiryFilterVO
				 * @return
				 * @throws SystemException
				 */
				public static CarditEnquiryVO findCarditDetails(
						CarditEnquiryFilterVO carditEnquiryFilterVO) throws SystemException {
					return AssignedFlightSegment.findCarditDetails(carditEnquiryFilterVO);
				}

				/**
				 *
				 * 	Method		:	MailAcceptance.findMailBagsForTransportCompletedResdit
				 *	Added by 	:
				 * 	Used for 	:
				 *	Parameters	:	@param operationalFlightVO
				 *	Parameters	:	@return
				 *	Parameters	:	@throws SystemException
				 *	Return type	: 	Collection<MailbagVO>
				 */
				public static Collection<MailbagVO> findMailBagsForTransportCompletedResdit(
					OperationalFlightVO operationalFlightVO) throws SystemException {
					Collection<MailbagVO> mailbags = null;
					try {
						mailbags = constructDAO().findMailBagsForTransportCompletedResdit(
						operationalFlightVO);
					} catch (PersistenceException ex) {
						throw new SystemException(ex.getErrorCode(), ex);
					}
					return mailbags;
				}
				/**
				 *
				 * 	Method		:	MailAcceptance.findUldsForTransportCompletedResdit
				 *	Added by 	:
				 * 	Used for 	:
				 *	Parameters	:	@param operationalFlightVO
				 *	Parameters	:	@return
				 *	Parameters	:	@throws SystemException
				 *	Return type	: 	Collection<ContainerDetailsVO>
				 */
				public static Collection<ContainerDetailsVO> findUldsForTransportCompletedResdit(
					OperationalFlightVO operationalFlightVO) throws SystemException {
					Collection<ContainerDetailsVO> containerDetailsVOs = null;
					try {
						containerDetailsVOs = constructDAO().findUldsForTransportCompletedResdit(
						operationalFlightVO);
					} catch (PersistenceException ex) {
						throw new SystemException(ex.getErrorCode(), ex);
					}
					return containerDetailsVOs;
				}
				
				public void flagHistoryDetailsOfMailbags(MailAcceptanceVO mailAcceptanceVO,
						Collection<MailbagVO> mailbagVOs) throws SystemException {
					MailController mailController = (MailController)SpringAdapter.getInstance().getBean("mAilcontroller");
					
					mailController.insertOrUpdateHistoryDetailsForAcceptance(mailAcceptanceVO,mailbagVOs);
					mailController.insertOrUpdateAuditDetailsForAcceptance(mailAcceptanceVO,mailbagVOs);
				
					
				}
				
					
					
						
						
							
						
						
						
						
						
						
					
						
					
				/**
				 * @author A-7929
				 * @param offloadFilterVO
				 * @return
				 * @throws SystemException
				 */
				public static OffloadVO findOffLoadDetails(OffloadFilterVO offloadFilterVO) throws SystemException {
					OffloadVO offloadVo = null;
					if (MailConstantsVO.OFFLOAD_CONTAINER.equals(offloadFilterVO.getOffloadType())) {
						offloadVo = findAcceptedContainersForOffLoad(offloadFilterVO);
					}
					else if (MailConstantsVO.OFFLOAD_DSN.equals(offloadFilterVO.getOffloadType())) {
						offloadVo = findAcceptedDespatchesForOffload(offloadFilterVO);
					} else {
						offloadVo = findAcceptedMailBagsForOffload(offloadFilterVO);
					}
					return offloadVo;
				}	 
                /**
                 * @author A-7929
                 * @param offloadFilterVO
                 * @return
                 * @throws SystemException
                 */
				private static OffloadVO findAcceptedContainersForOffLoad(OffloadFilterVO offloadFilterVO) throws SystemException {
					Page<ContainerVO> containerVos = null;
					OffloadVO offloadVo = null;
					try {
						containerVos = constructDAO().findAcceptedContainersForOffLoad(
								offloadFilterVO);
					} catch (PersistenceException ex) {
						throw new SystemException(ex.getErrorCode(), ex);
					}
					if (containerVos != null && containerVos.size() > 0) {
						offloadVo = constructOffloadVO(offloadFilterVO);
						offloadVo.setOffloadContainerDetails(containerVos);
					}
					
					return offloadVo;
				}
					
				
				
				public static Collection<RoutingIndexVO> findRoutingIndex(RoutingIndexVO routingIndexVO) throws SystemException {
					Collection<RoutingIndexVO> routingIndexVOs = null;
						try {
							routingIndexVOs = constructDAO().findRoutingIndex(
									routingIndexVO);
						} catch (PersistenceException ex) {
							throw new SystemException(ex.getErrorCode(), ex);
						}
						return routingIndexVOs;
					}
				
				
				public Map<String,String> findOfficeOfExchangeForPA(String companyCode,
						String paCode) throws SystemException {
					log.entering(MODULE, "findOfficeOfExchangeForAirports");
					return new MailController().findOfficeOfExchangeForPA(companyCode,paCode);
				}
				
				

private static String generateDespatchSerialNumber(String currentKey,MailbagVO maibagVO)
			throws SystemException {
			String key=null;
			StringBuilder keyCondition = new StringBuilder();
			keyCondition.append(maibagVO.getYear());
			Criterion criterion = KeyUtils.getCriterion(maibagVO.getCompanyCode(),
					"DOM_USPS_DSN", keyCondition.toString());
			//Code to be modified once framework issue is fixed
			key=KeyUtils.getKey(criterion);
			if(MailConstantsVO.FLAG_YES.equals(currentKey)&&
					key.length() > 4) {
				key = "9999";
				KeyUtils.resetKey(criterion, "0");
			}else if(MailConstantsVO.FLAG_YES.equals(currentKey)&&
					!"1".equals(key)){
				key=String.valueOf(Long.parseLong(key)-1);
				KeyUtils.resetKey(criterion, key);
			}
		return checkLength(key, 4);
	}
	
private static String generateReceptacleSerialNumber(String dsn,MailbagVO maibagVO)
			throws SystemException {
		String key = null;
			StringBuilder keyCondition = new StringBuilder();
			keyCondition.append(maibagVO.getYear())
            		.append(dsn);
			Criterion criterion = KeyUtils.getCriterion(maibagVO.getCompanyCode(),
					"DOM_USPS_RSN", keyCondition.toString());
		key = KeyUtils.getKey(criterion);
		if (key.length() > 3) {
			key="999";
			KeyUtils.resetKey(criterion, "0");
		}
		String rsn = checkLength(key, 3);
			
		return rsn;
	}

private static String checkLength(String key,int maxLength){
	String modifiedKey = null;
	StringBuilder buildKey = new StringBuilder();
	modifiedKey = new StringBuilder().append(key).toString();
	int keyLength = modifiedKey.length();
	if(modifiedKey.length() < maxLength){
		int diff = maxLength - keyLength;
		String val = null;
		for(int i=0;i< diff;i++){
			val = buildKey.append("0").toString();
		}
		modifiedKey = 	new StringBuilder().append(val).append(key).toString();
	}
	return modifiedKey;
}
/**
 * 	Method		:	MailAcceptance.findDsnAndRsnForMailbag
 *	Added by 	:	A-7531 on 31-Oct-2018
 * 	Used for 	:
 *	Parameters	:	@param maibagVO
 *	Parameters	:	@return 
 *	Return type	: 	MailbagVO
 * @throws SystemException 
 */
public static MailbagVO findDsnAndRsnForMailbag(MailbagVO mailbagVO) throws SystemException {
	
	String despacthNumber=generateDespatchSerialNumber(MailConstantsVO.FLAG_YES,mailbagVO);
	String rsn=generateReceptacleSerialNumber(despacthNumber,mailbagVO);
	if(rsn.length()>3){
	
		generateDespatchSerialNumber(MailConstantsVO.FLAG_NO,mailbagVO);
		despacthNumber=(generateDespatchSerialNumber(MailConstantsVO.FLAG_YES,mailbagVO));
		rsn=generateReceptacleSerialNumber(despacthNumber,mailbagVO);				
		
	}
	
	mailbagVO.setDespatchSerialNumber(despacthNumber);
	mailbagVO.setReceptacleSerialNumber(rsn);
	return mailbagVO;
}


/**
 * @author A-8353
 * @param mailbagVO
 * @param mailbag
 * @return
 * @throws SystemException
 * @throws DuplicateMailBagsException
 */
/*private static boolean checkForDuplicateMailbag(MailbagVO mailbagVO,Mailbag mailbag) throws SystemException, DuplicateMailBagsException {
    PostalAdministrationVO postalAdministrationVO = PostalAdministration.findPACode(mailbagVO.getCompanyCode(), mailbagVO.getPaCode());
    LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,true);
    LocalDate dspDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, mailbag.getDespatchDate(), true);
    
    long seconds=currentDate.findDifference(dspDate);
    float days=seconds/86400000;
    if((days)<= postalAdministrationVO.getDupMailbagPeriod()){
    	return false;
    }
    return true;
    
}	*/ 
	/**
	 *
	 * 	Method		:	MailAcceptance.populatePrimaryAcceptanceDetails
	 *	Added by 	:	A-6245 on 26-Feb-2021
	 * 	Used for 	:	IASCB-96538
	 *	Parameters	:	@param scannedMailDetailsVO
	 *	Parameters	:	@param mailbagVO
	 *	Return type	: 	void
	 */
	public static void populatePrimaryAcceptanceDetails(MailbagVO mailbagVO) {
		if ((MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(mailbagVO.getLatestStatus())
				|| MailConstantsVO.MAIL_STATUS_ASSIGNED.equals(mailbagVO.getLatestStatus()))) {
			mailbagVO.setAcceptanceAirportCode(mailbagVO.getScannedPort());
			mailbagVO.setAcceptanceScanDate(mailbagVO.getScannedDate());
			if (MailConstantsVO.FLAG_YES.equals(mailbagVO.getPaBuiltFlag())) {
				mailbagVO.setPaBuiltFlag(MailConstantsVO.FLAG_YES);
				mailbagVO.setAcceptancePostalContainerNumber(mailbagVO.getContainerNumber());
			}
			else {
				mailbagVO.setPaBuiltFlag(null);
			}
		}
	}	
	public static Collection<ContainerDetailsVO> findMailbagsInContainerWithoutAcceptance(
			Collection<ContainerDetailsVO> containers) throws SystemException {
		 try {
				return constructDAO().findMailbagsInContainerWithoutAcceptance(containers);
			} catch (PersistenceException ex) {
				throw new SystemException(ex.getMessage(), ex);
			}
	}
}
