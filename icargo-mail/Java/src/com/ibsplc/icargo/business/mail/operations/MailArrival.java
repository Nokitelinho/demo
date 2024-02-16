/*
 * MailArrival.java Created on Aug 17, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_UPDATE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentSummaryVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditVO;
import com.ibsplc.icargo.business.mail.operations.proxy.FlightOperationsProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.MailOperationsMRAProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.OperationsFltHandlingProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedAreaProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.vo.AssignedFlightSegmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.AssignedFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerAssignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerInInventoryListVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNAtAirportVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInInventoryListVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagAuditVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.business.operations.shipment.vo.UldInFlightVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.audit.util.AuditUtils;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.interceptor.Advice;
import com.ibsplc.xibase.server.framework.interceptor.Phase;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.server.framework.util.log.ExtendedLogManager;
import com.ibsplc.xibase.server.framework.util.log.Logger;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.business.mail.operations.vo.OfficeOfExchangeVO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * Saves the arrival details of an Inbound Flight
 *
 * @author A-1739
 *
 */

/*
 * Revision History
 * --------------------------------------------------------------------------
 * Revision Date Author Description
 * ------------------------------------------------------------------------- 0.1
 * Aug 17, 2006 A-1739 First Draft Sep 7, 2006 A-1739 Audit Impl
 */
public class MailArrival {
	private static final String TRANSACTION_ARRIVAL = "ARRIVAL";
	private static final String MODULE = "mail.operations";
	private static Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");

	private static final String  ENTITY = "MailArrival";
	//Added for hht error log out by A-5526 starts
		private static final Logger errPgExceptionLogger = ExtendedLogManager.getLogger("MAILHHTERR");
		//Added for hht error log out by A-5526 ends




		/**
		 * TODO Purpose Jan 29, 2007, A-1739
		 *
		 * @param dsnVOs
		 * @param mailbagVO
		 * @param containerDetailsVO
		 * @throws SystemException 
		 */
		private void updateMailbagDSNVO(Collection<DSNVO> dsnVOs,
				MailbagVO mailbagVO, ContainerDetailsVO containerDetailsVO) throws SystemException {
			for (DSNVO dsnVO : dsnVOs) {
				if (MailConstantsVO.FLAG_YES.equals(dsnVO.getPltEnableFlag())) {
					if (dsnVO.getDsn().equals(mailbagVO.getDespatchSerialNumber())
							&& dsnVO.getOriginExchangeOffice().equals(
									mailbagVO.getOoe())
							&& dsnVO.getDestinationExchangeOffice().equals(
									mailbagVO.getDoe())
							&& dsnVO.getMailCategoryCode().equals(
									mailbagVO.getMailCategoryCode())
							&& dsnVO.getMailSubclass().equals(
									mailbagVO.getMailSubclass())
							&& dsnVO.getYear() == mailbagVO.getYear()) {
						if (MailConstantsVO.FLAG_YES.equals(mailbagVO
								.getAcceptanceFlag())) {
							// manifested mailbag
							if (!MailConstantsVO.FLAG_YES.equals(mailbagVO
									.getArrivedFlag())) {
								dsnVO.setReceivedBags(dsnVO.getReceivedBags() + 1);
								/*if(mailbagVO.getWeight()!=null){
								dsnVO.setReceivedWeight(dsnVO.getReceivedWeight()
										+ mailbagVO.getWeight().getSystemValue());//added by a-7371
								}*/
								
									try {
										dsnVO.setReceivedWeight(Measure.addMeasureValues(dsnVO.getReceivedWeight(), mailbagVO.getWeight()));
									} catch (UnitException e) {
										// TODO Auto-generated catch block
										throw new SystemException(e.getErrorCode());
									}//added by A-7371
								

								// update containerVO
								containerDetailsVO
										.setReceivedBags(containerDetailsVO
												.getReceivedBags() + 1);
								if(mailbagVO.getWeight()!=null){
							/*	containerDetailsVO
										.setReceivedWeight(containerDetailsVO
												.getReceivedWeight()
												+ mailbagVO.getWeight());*///added by A-7371
									try {
										containerDetailsVO
										.setReceivedWeight(Measure.addMeasureValues(containerDetailsVO
													.getReceivedWeight(), mailbagVO.getWeight()));
									} catch (UnitException e) {
										// TODO Auto-generated catch block
										throw new SystemException(e.getErrorCode());
									}//added by A-7371
								}
							}
							if (!MailConstantsVO.FLAG_YES.equals(mailbagVO
									.getDeliveredFlag())) {
								dsnVO
										.setDeliveredBags(dsnVO.getDeliveredBags() + 1);
								/*if(mailbagVO.getWeight()!=null){
								dsnVO.setDeliveredWeight(dsnVO.getDeliveredWeight()
										+ mailbagVO.getWeight().getSystemValue());//added by A-7371
								}*/
								
									try {
										dsnVO.setDeliveredWeight(Measure.addMeasureValues(dsnVO.getDeliveredWeight(), mailbagVO.getWeight()));
									} catch (UnitException e) {
										// TODO Auto-generated catch block
										throw new SystemException(e.getErrorCode());
									}//added by A-7371
							
								
							}
							// already existing dsnvo
							dsnVO.setOperationFlag(OPERATION_FLAG_UPDATE);
						} else {
							// arrival must already be marked
							if (!MailConstantsVO.FLAG_YES.equals(mailbagVO
									.getDeliveredFlag())) {
								dsnVO
										.setDeliveredBags(dsnVO.getDeliveredBags() + 1);
								/*if(mailbagVO.getWeight()!=null){
								dsnVO.setDeliveredWeight(dsnVO.getDeliveredWeight()
										+ mailbagVO.getWeight().getSystemValue());//added by A-7371
								}*/
								
									try {
										dsnVO.setDeliveredWeight(Measure.addMeasureValues(dsnVO.getDeliveredWeight(), mailbagVO.getWeight()));
									} catch (UnitException e) {
										// TODO Auto-generated catch block
										throw new SystemException(e.getErrorCode());
									}//added by A-7371
								
							}
							if (!OPERATION_FLAG_INSERT.equals(dsnVO
									.getOperationFlag())) {
								dsnVO.setOperationFlag(OPERATION_FLAG_UPDATE);
							}
						}
					}
				}
			}
		}


		/**
		 * updates the dsnVO of a despatchVO Jan 29, 2007, A-1739
		 *
		 * @param dsnVOs
		 * @param despatchDetailsVO
		 * @param containerDetailsVO
		 * @throws SystemException 
		 */
		private void updateDespatchDSNVO(Collection<DSNVO> dsnVOs,
				DespatchDetailsVO despatchDetailsVO,
				ContainerDetailsVO containerDetailsVO) throws SystemException {

			for (DSNVO dsnVO : dsnVOs) {
				if (MailConstantsVO.FLAG_NO.equals(dsnVO.getPltEnableFlag())) {
					if (dsnVO.getDsn().equals(despatchDetailsVO.getDsn())
							&& dsnVO.getOriginExchangeOffice().equals(
									despatchDetailsVO.getOriginOfficeOfExchange())
							&& dsnVO.getDestinationExchangeOffice().equals(
									despatchDetailsVO
											.getDestinationOfficeOfExchange())
							&& dsnVO.getMailCategoryCode().equals(
									despatchDetailsVO.getMailCategoryCode())
							&& dsnVO.getMailSubclass().equals(
									despatchDetailsVO.getMailSubclass())
							&& dsnVO.getYear() == despatchDetailsVO.getYear()) {

						if (despatchDetailsVO.getAcceptedBags() > 0) {
							/*
							 * In this case the received count becomes whatever
							 * mailbags are yet to be received. Those receieved at
							 * the client sid are already present in the dsnvo. Only
							 * the remaining mailbags are added.
							 */
							dsnVO.setReceivedBags(dsnVO.getReceivedBags()
									+ despatchDetailsVO.getAcceptedBags()
									- despatchDetailsVO.getReceivedBags());
							/*dsnVO.setReceivedWeight(dsnVO.getReceivedWeight()
									+ despatchDetailsVO.getAcceptedWeight()
									- despatchDetailsVO.getReceivedWeight());*/
							
								Measure despatchRecWt;
								try {
									despatchRecWt = Measure.subtractMeasureValues(despatchDetailsVO.getAcceptedWeight(), despatchDetailsVO.getReceivedWeight());
									Measure recievedWt;
									try {
										recievedWt = Measure.addMeasureValues(dsnVO.getReceivedWeight(), despatchRecWt);
										dsnVO.setReceivedWeight(recievedWt);//added by A-7371
									} catch (UnitException e1) {
										// TODO Auto-generated catch block
										throw new SystemException(e1.getErrorCode());
									}
								} catch (UnitException e1) {
									// TODO Auto-generated catch block
									throw new SystemException(e1.getErrorCode());
								}
					
							dsnVO.setDeliveredBags(dsnVO.getDeliveredBags()
									+ despatchDetailsVO.getAcceptedBags()
									- despatchDetailsVO.getDeliveredBags());
							/*dsnVO.setDeliveredWeight(dsnVO.getDeliveredWeight()
									+ despatchDetailsVO.getAcceptedWeight()
									- despatchDetailsVO.getDeliveredWeight());*/
							
								Measure despatchAcpWt;
								try {
									despatchAcpWt = Measure.subtractMeasureValues(despatchDetailsVO.getAcceptedWeight(), despatchDetailsVO.getDeliveredWeight());
									Measure deliveredWt;
									try {
										deliveredWt = Measure.addMeasureValues(dsnVO.getDeliveredWeight(), despatchAcpWt);
										dsnVO.setDeliveredWeight(deliveredWt);//added by A-7371
									} catch (UnitException e1) {
										// TODO Auto-generated catch block
										throw new SystemException(e1.getErrorCode());
									}
									
								} catch (UnitException e1) {
									// TODO Auto-generated catch block
									throw new SystemException(e1.getErrorCode());
								}

							// update containerVO
							containerDetailsVO.setReceivedBags(containerDetailsVO
									.getReceivedBags()
									+ (despatchDetailsVO.getAcceptedBags()-despatchDetailsVO.getReceivedBags()));

							/*containerDetailsVO.setReceivedWeight(containerDetailsVO
									.getReceivedWeight()
									+ (despatchDetailsVO.getAcceptedWeight()-despatchDetailsVO.getReceivedWeight()));*/
							Measure despWt;
							try {
								despWt = Measure.subtractMeasureValues(despatchDetailsVO.getAcceptedWeight(), despatchDetailsVO.getReceivedWeight());
								try {
									containerDetailsVO.setReceivedWeight(Measure.addMeasureValues(containerDetailsVO
											.getReceivedWeight(), despWt));
								} catch (UnitException e) {
									// TODO Auto-generated catch block
									throw new SystemException(e.getErrorCode());
								}//added by A-7371
							} catch (UnitException e) {
								// TODO Auto-generated catch block
								throw new SystemException(e.getErrorCode());
							}

							log.log(Log.FINE, "CHECK FOR THE  RECEIVED BAGS ",
									containerDetailsVO);
							// this is a manifested dsnvo coz acpbags > 0
							dsnVO.setOperationFlag(OPERATION_FLAG_UPDATE);
						} else {
							/*
							 * This is the case of found mailbags In this case the
							 * client might have added the newly received mailbags
							 * already to the dsnov. Only the bags to be delivered
							 * need to be updated
							 */
							dsnVO.setDeliveredBags(dsnVO.getDeliveredBags()
									+ despatchDetailsVO.getReceivedBags()
									- despatchDetailsVO.getDeliveredBags());
							/*dsnVO.setDeliveredWeight(dsnVO.getDeliveredWeight()
									+ despatchDetailsVO.getReceivedWeight()
									- despatchDetailsVO.getDeliveredWeight());*/
							Measure despatchDelWt;
							try {
								despatchDelWt = Measure.subtractMeasureValues(despatchDetailsVO.getReceivedWeight(), despatchDetailsVO.getDeliveredWeight());
								Measure deliveredWt;
								try {
									deliveredWt = Measure.addMeasureValues(dsnVO.getDeliveredWeight(), despatchDelWt);
									dsnVO.setDeliveredWeight(deliveredWt);//added by A-7371
								} catch (UnitException e) {
									// TODO Auto-generated catch block
									throw new SystemException(e.getErrorCode());
								}
									
							} catch (UnitException e) {
								// TODO Auto-generated catch block
								throw new SystemException(e.getErrorCode());
							}
								
								
							// may or maynot be new dsnvo
							if (!OPERATION_FLAG_INSERT.equals(dsnVO
									.getOperationFlag())) {
								dsnVO.setOperationFlag(OPERATION_FLAG_UPDATE);
							}
						}

					}
				}
			}
		}



		/**
		 * TODO Purpose Jan 25, 2007, A-1739
		 *
		 * @param mailArrivalVO
		 * @param mailbagsAlreadyArrived TODO
		 * @throws SystemException
		 */
		private void markDeliveryForTerminatingMailVOs(MailArrivalVO mailArrivalVO)
				throws SystemException,MailTrackingBusinessException {
			log.entering(ENTITY, "markDeliveryForTerminatingMailVOs");

			Collection<ContainerDetailsVO> containers = mailArrivalVO
					.getContainerDetails();
			String companyCode = mailArrivalVO.getCompanyCode();
			String airportCode = mailArrivalVO.getAirportCode();

			boolean isPartial = mailArrivalVO.isPartialDelivery();

			Map<String, String> cityCache = new HashMap<String, String>();
			for (ContainerDetailsVO containerDetailsVO : containers) {
				if(!isPartial || (isPartial &&
						OPERATION_FLAG_UPDATE.equals(containerDetailsVO.getOperationFlag()))) {
					Collection<DSNVO> dsnVOs = containerDetailsVO.getDsnVOs();
					// marking arrival & delvery for despatches
					Collection<DespatchDetailsVO> despatches = containerDetailsVO
							.getDesptachDetailsVOs();
					if (despatches != null && despatches.size() > 0) {
						for (DespatchDetailsVO despatchDetailsVO : despatches) {
							if(!isPartial || (isPartial &&
									OPERATION_FLAG_UPDATE.equals(
										despatchDetailsVO.getOperationalFlag()))) {
								boolean isAlreadyDlv = false;
								if (despatchDetailsVO.getAcceptedBags() > 0) {
									if (despatchDetailsVO.getDeliveredBags() == despatchDetailsVO
											.getAcceptedBags()) {
										isAlreadyDlv = true;
									}
								} else {
									if (despatchDetailsVO.getDeliveredBags() == despatchDetailsVO
											.getReceivedBags()) {
										isAlreadyDlv = true;
									}
								}
								OfficeOfExchangeVO originOfficeOfExchangeVO;//Added by A-8164 for ICRD-342541 
								originOfficeOfExchangeVO=new MailController().validateOfficeOfExchange(companyCode, despatchDetailsVO
										.getOriginOfficeOfExchange());
								String poaCode=originOfficeOfExchangeVO.getPoaCode();
								if (!isAlreadyDlv
										&&  airportCode.equals(despatchDetailsVO.getDestination())?true:isValidDeliveryAirport(despatchDetailsVO
												.getDestinationOfficeOfExchange(),
												companyCode, airportCode, cityCache,MailConstantsVO.RESDIT_DELIVERED,poaCode,despatchDetailsVO.getConsignmentDate())) {
									// update DSNVO
									updateDespatchDSNVO(dsnVOs, despatchDetailsVO,
											containerDetailsVO);
									// setting received bags to mark arrival
									if(despatchDetailsVO
											.getAcceptedBags()>0){
									despatchDetailsVO.setReceivedBags(despatchDetailsVO
											.getAcceptedBags());
									despatchDetailsVO.setReceivedWeight(despatchDetailsVO
											.getAcceptedWeight());
									// setting delivered bags to mark delivery
									despatchDetailsVO.setDeliveredBags(despatchDetailsVO
											.getAcceptedBags());
									despatchDetailsVO.setDeliveredWeight(despatchDetailsVO
											.getAcceptedWeight());
									}else{

										log.log(Log.FINE,"THE  PURE ACCEPTED AT ARRIVAL PORT");
										despatchDetailsVO.setReceivedBags(despatchDetailsVO
												.getReceivedBags());
										despatchDetailsVO.setReceivedWeight(despatchDetailsVO
												.getReceivedWeight());
										// setting delivered bags to mark delivery
										despatchDetailsVO.setDeliveredBags(despatchDetailsVO
												.getReceivedBags());
										despatchDetailsVO.setDeliveredWeight(despatchDetailsVO
												.getReceivedWeight());
									}
									despatchDetailsVO.setReceivedDate(new LocalDate(
											airportCode, Location.ARP, true));

									if (!OPERATION_FLAG_INSERT.equals(despatchDetailsVO
											.getOperationalFlag())) {
										despatchDetailsVO
												.setOperationalFlag(OPERATION_FLAG_UPDATE);
										containerDetailsVO
												.setOperationFlag(OPERATION_FLAG_UPDATE);
									}
								}
							}
						}
					}
					// marking arrival and delivery for mailbags
					Collection<MailbagVO> mailbags = containerDetailsVO
							.getMailDetails();
					if (mailbags != null && mailbags.size() > 0) {
						for (MailbagVO mailbagVO : mailbags) {
							if(!isPartial || (isPartial && OPERATION_FLAG_UPDATE.equals(
									mailbagVO.getOperationalFlag()))) {
								Mailbag mailbagToFindPA = null;//Added by A-8164 for ICRD-342541 starts
								String poaCode=null;
								MailbagPK mailbagPk = new MailbagPK();
								mailbagPk.setCompanyCode(companyCode);
								mailbagPk.setMailSequenceNumber(findMailSequenceNumber(mailbagVO.getMailbagId(), companyCode));
								try {
									mailbagToFindPA = Mailbag.find(mailbagPk);
								} catch (FinderException e) {							
									e.getMessage();
								}
								if(mailbagToFindPA!=null && mailbagToFindPA.getPaCode()!=null ){
									poaCode=mailbagToFindPA.getPaCode();
								}
								else{
									OfficeOfExchangeVO originOfficeOfExchangeVO; 
									originOfficeOfExchangeVO=new MailController().validateOfficeOfExchange(mailbagVO.getCompanyCode(), mailbagVO.getOoe());
									poaCode=originOfficeOfExchangeVO.getPoaCode();
								}//Added by A-8164 for ICRD-342541 ends
								if (!MailConstantsVO.FLAG_YES.equals(mailbagVO
										.getDeliveredFlag())
										&& airportCode.equals(mailbagVO.getDestination())?true: isValidDeliveryAirport(mailbagVO.getDoe(),
												companyCode, airportCode, cityCache,MailConstantsVO.RESDIT_DELIVERED,poaCode,mailbagVO.getConsignmentDate())) {
									// udpate dsnvo

									updateMailbagDSNVO(dsnVOs, mailbagVO,
											containerDetailsVO);
									// setting recevie & delivery flags
									mailbagVO.setArrivedFlag(MailConstantsVO.FLAG_YES);
									mailbagVO.setDeliveredFlag(MailConstantsVO.FLAG_YES);
									mailbagVO.setScannedPort(airportCode);
									if(mailArrivalVO.getScanDate() != null){
									mailbagVO.setScannedDate(mailArrivalVO.getScanDate());
									}
									// Condition added by A-6385 for ICRD-91729
									if(mailbagVO.getScannedUser() == null){
									mailbagVO.setScannedUser(mailArrivalVO.getArrivedUser());
									}
									mailbagVO.setFlightDate(mailArrivalVO.getArrivalDate());

									/*
									 * EDITING START 01/02/08
									 * ADDED BY RENO K ABRAHAM FOR SB ULD DELIVERED STATUS
									 */

									if(MailConstantsVO.FLAG_YES.equals(containerDetailsVO.getPaBuiltFlag())) {
										containerDetailsVO.setArrivedStatus(MailConstantsVO.FLAG_YES);
										if(!"N".equals(containerDetailsVO.getDeliverPABuiltContainer())){
											containerDetailsVO.setDeliveredStatus(MailConstantsVO.FLAG_YES);
										}
									}
									//EDITING END

									if (!OPERATION_FLAG_INSERT.equals(mailbagVO
											.getOperationalFlag())) {
										mailbagVO.setOperationalFlag(OPERATION_FLAG_UPDATE);
										containerDetailsVO
												.setOperationFlag(OPERATION_FLAG_UPDATE);
									}
								}
							}
						}
					}
					/*
					 * EDITING START 01/02/08
					 * ADDED BY RENO K ABRAHAM FOR SB ULD DELIVERED/ARRIVED STATUS
					 */
					if (dsnVOs ==null || dsnVOs.size()==0){
							if(!isPartial || (isPartial &&
									OPERATION_FLAG_UPDATE.equals(containerDetailsVO.getOperationFlag()))) {
								if(MailConstantsVO.FLAG_YES.equals(containerDetailsVO.getPaBuiltFlag())) {
									containerDetailsVO.setArrivedStatus(MailConstantsVO.FLAG_YES);
									containerDetailsVO.setDeliveredStatus(MailConstantsVO.FLAG_YES);
									containerDetailsVO.setOperationFlag(OPERATION_FLAG_UPDATE);
								}
							}
					}
					//EDITING END
				}
			}

			log.exiting(ENTITY, "markDeliveryForTerminatingMailVOs");
		}


		/**
		 * This method does the group delivery for all mailbags that can be
		 * delivered Jan 25, 2007, A-1739
		 *
		 * @param mailArrivalVO
		 * @throws SystemException
		 * @throws FlightClosedException
		 * @throws InvalidFlightSegmentException
		 * @throws MailbagIncorrectlyDeliveredException
		 * @throws DuplicateMailBagsException
		 * @throws ContainerAssignmentException
		 */
		/**
		 * This method does the group delivery for all mailbags that can be
		 * delivered Jan 25, 2007, A-1739
		 *
		 * @param mailArrivalVO
		 * @param mailbagsAlreadyArrived
		 * @throws SystemException
		 * @throws FlightClosedException
		 * @throws InvalidFlightSegmentException
		 * @throws MailbagIncorrectlyDeliveredException
		 * @throws DuplicateMailBagsException
		 * @throws ContainerAssignmentException
		 * @throws ULDDefaultsProxyException
		 * @throws CapacityBookingProxyException
		 * @throws MailBookingException
		 * @throws MailTrackingBusinessException
		 */
		public void deliverMailbags(MailArrivalVO mailArrivalVO,
			Collection<MailbagVO> arrivedMailBagsForMonitorSLA, Collection<MailbagVO> deliveredMailBagsForMonitorSLA,
			Collection<DespatchDetailsVO> despatchDetailsForInventoryRemoval)
			throws SystemException, MailTrackingBusinessException {
			log.entering(ENTITY, "deliverMailbags");
			markDeliveryForTerminatingMailVOs(mailArrivalVO);

			log.log(Log.FINEST, "after marking delivery ", mailArrivalVO);

			MailController mailController = (MailController)SpringAdapter.getInstance().getBean("mAilcontroller");
			
				mailController.saveArrivalDetails(mailArrivalVO);
		
			log.exiting(ENTITY, "deliverMailbags");
		}

		/**
		 * This method is used to save ArrivalDetails
		 *
		 * @param mailArrivalVO
		 * @param despatchDetailsForInventoryRemoval
		 * @return
		 * @throws SystemException
		 * @throws ContainerAssignmentException
		 * @throws DuplicateMailBagsException
		 * @throws MailbagIncorrectlyDeliveredException
		 * @throws InvalidFlightSegmentException
		 * @throws FlightClosedException
		 * @throws ULDDefaultsProxyException
		 * @throws CapacityBookingProxyException
		 * @throws MailBookingException
		 */
		 @Advice(name = "mail.operations.saveArrivalDetails", phase = Phase.POST_INVOKE)
		public ScannedMailDetailsVO saveArrivalDetails(MailArrivalVO mailArrivalVO,
				Collection<MailbagVO> arrivedMailBagsForMonitorSLA,
				Collection<MailbagVO> deliveredMailBagsForMonitorSLA)
				throws SystemException, ContainerAssignmentException,
				DuplicateMailBagsException, MailbagIncorrectlyDeliveredException,
				InvalidFlightSegmentException, FlightClosedException, ULDDefaultsProxyException, CapacityBookingProxyException, MailBookingException,MailTrackingBusinessException {

				log.entering(ENTITY, "saveArrivedMails");
				log.log(Log.FINE, "The Mail Arrival VO", mailArrivalVO);
				boolean fltClosureCheckNeeded= false;
				boolean isRDTRestrictReq = false;
				updateMailbagVOforResdit(mailArrivalVO);
				if(!mailArrivalVO.isMailVOUpdated()){
					Collection<MailbagVO> mailVOs = new ArrayList<MailbagVO>();
					Collection<ContainerDetailsVO> containerDetailsVOs = mailArrivalVO
					.getContainerDetails();
					if(containerDetailsVOs != null && !containerDetailsVOs.isEmpty()){
						for(ContainerDetailsVO contVO : containerDetailsVOs){
							if(contVO.getMailDetails()!=null && !contVO.getMailDetails().isEmpty()){
								Collection<MailbagVO> mailbagVOs=contVO.getMailDetails();
								if(!mailArrivalVO.isFlightChange()){
								for(MailbagVO mailbagVO:mailbagVOs){
									if(mailbagVO.getMailSequenceNumber()>0 && mailbagVO.getOperationalFlag()!=null)
									{
									mailVOs.add(mailbagVO);
									if(mailbagVO.getDocumentNumber() != null && mailbagVO.getDocumentNumber().trim().length() >0){
										fltClosureCheckNeeded = true;
									}
								}
									if(MailConstantsVO.MAIL_SRC_RESDIT.equals(mailbagVO.getMailbagSource())){
										isRDTRestrictReq = true;
									}	
								}
							}
							}
								
						}
						MailController.updateMailbagVOs(mailVOs,mailArrivalVO.isFlightChange());
					}
				}
				Collection<DespatchDetailsVO> despatchDetailsForInventoryRemoval= new ArrayList<DespatchDetailsVO>();
				Map<String, Collection<DespatchDetailsVO>> despatchesMapForInventory =new HashMap<String, Collection<DespatchDetailsVO>>();
				Map<String, Collection<MailbagVO>> mailBagsMapForInventory =new HashMap<String, Collection<MailbagVO>>();
			    Collection < MailbagVO> deliveredArrivedMails =new ArrayList<MailbagVO>();
		        Collection < ContainerDetailsVO> deliveredContainers =new ArrayList<ContainerDetailsVO>();
		        Collection<MailbagVO> newlyArrivedMailbags = new ArrayList<MailbagVO>();
		        Collection<MailbagVO> expMails = new ArrayList<MailbagVO>();
		        Collection<ContainerDetailsVO> paBuiltContainers = new ArrayList<ContainerDetailsVO>();
		        Collection<ContainerDetailsVO> arrivedContainers = mailArrivalVO.getContainerDetails();
		        updatebulkDetails(arrivedContainers);
			    AssignedFlightPK inboundFlightPK = createInboundFlightPK(mailArrivalVO);
			    AssignedFlightVO inboundFlightVO = constructInboundFlightVO(mailArrivalVO);

			    findOrCreateInboundFlight(inboundFlightPK, inboundFlightVO);

			    boolean isScanned = mailArrivalVO.isScanned();
			    if (isScanned && !fltClosureCheckNeeded){
				 checkFlightClosureForArrival(mailArrivalVO);
			    }
			    ScannedMailDetailsVO scanDetailsVO = constructScannedMailDetailsForFlight(mailArrivalVO);

				Map<String, DSNVO> dsnMap  = checkForIncorrectArrivalDetails(mailArrivalVO,despatchDetailsForInventoryRemoval,
											 expMails, newlyArrivedMailbags, deliveredMailBagsForMonitorSLA,deliveredArrivedMails,deliveredContainers);

				if(arrivedMailBagsForMonitorSLA != null) {
					arrivedMailBagsForMonitorSLA.addAll(newlyArrivedMailbags);
				}

				Map<AssignedFlightSegmentPK, Collection<ContainerDetailsVO>> segmentContainerMap = groupContainersForSegmentForArrival(mailArrivalVO, paBuiltContainers);

				boolean isUpdated = false;
				boolean isSaveSuccessfull = false;
				for (Map.Entry<AssignedFlightSegmentPK, Collection<ContainerDetailsVO>> segmentContainer : segmentContainerMap.entrySet()) {
					AssignedFlightSegment flightSegment = null;
					AssignedFlightSegmentPK assignedFlightSegmentPK = segmentContainer.getKey();
					Collection<ContainerDetailsVO> segmentContainers = segmentContainer.getValue();
					try {
						flightSegment = AssignedFlightSegment.find(assignedFlightSegmentPK);
					} catch (FinderException exception) {
						flightSegment = new AssignedFlightSegment(constructAssignedFlightSegVOForContainer(assignedFlightSegmentPK, segmentContainers));
					}
					isSaveSuccessfull =flightSegment.saveArrivalDetails(segmentContainers,mailArrivalVO, expMails, mailBagsMapForInventory,despatchesMapForInventory);
					if (isSaveSuccessfull){
						isUpdated = true;
					}
				}

				if (isUpdated) {
					saveDSNMstDetails(dsnMap, isScanned);
					 Collection<MailbagVO> mailbagsForMLDForDelivery = new ArrayList<MailbagVO>();
					 Collection<MailbagVO> mailbagsForMLDForArrival = new ArrayList<MailbagVO>();
					Collection<MailbagVO> mailbagsForResdit = new ArrayList<MailbagVO>();
					Collection<MailbagVO> mailbagsForHistory = new ArrayList<MailbagVO>();
					mailbagsForResdit.addAll(newlyArrivedMailbags);
					mailbagsForResdit.addAll(deliveredArrivedMails);
					mailbagsForHistory.addAll(newlyArrivedMailbags);
					mailbagsForHistory.addAll(deliveredArrivedMails);
					mailbagsForHistory.addAll(deliveredArrivedMails);
					MailController mailController = (MailController)SpringAdapter.getInstance().getBean("mAilcontroller");
					if(!isRDTRestrictReq){
					mailController.flagResditsForArrival(mailArrivalVO,mailbagsForResdit, paBuiltContainers);
					}
					mailController.flagMailbagAuditForArrival(mailArrivalVO);
					mailController.flagMailbagHistoryForArrival(mailArrivalVO);
					/*String resditEnabled = findSystemParameterValue(MailConstantsVO.IS_RESDITMESSAGING_ENABLED);
					if(MailConstantsVO.FLAG_YES .equals(resditEnabled)){
						log.log(Log.FINE, "Resdit Enabled ", resditEnabled);
					new ResditController().flagResditsForArrival(mailArrivalVO,mailbagsForResdit, paBuiltContainers);
					}*/
				// Added as part of CRQ ICRD-93584 by A-5526 starts
				if (newlyArrivedMailbags != null && newlyArrivedMailbags.size() > 0) {
					for (MailbagVO mailbagVO : newlyArrivedMailbags) {
						if ("Y".equals(mailbagVO.getArrivedFlag())
								&& "Y".equals(mailbagVO.getDeliveredFlag())) {
							mailbagsForMLDForDelivery.add(mailbagVO);
							mailbagsForMLDForArrival.add(mailbagVO);
						} else if ("Y".equals(mailbagVO.getArrivedFlag())) {
							mailbagsForMLDForArrival.add(mailbagVO);
						}
					}
				}
				if (deliveredArrivedMails != null
						&& deliveredArrivedMails.size() > 0) {
					for (MailbagVO mailbagVO : deliveredArrivedMails) {
						if ("Y".equals(mailbagVO.getDeliveredFlag())) {
							mailbagsForMLDForDelivery.add(mailbagVO);

						}
					}
					String importEnabled = findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRAIMPORT);
					boolean provisionalRateImport =false;
					if (importEnabled != null && importEnabled.contains("D")) {
						Collection<RateAuditVO> rateAuditVOs = new MailController().createRateAuditVOs(mailArrivalVO.getContainerDetails(), MailConstantsVO.MAIL_STATUS_DELIVERED, provisionalRateImport);
						if(rateAuditVOs != null && !rateAuditVOs.isEmpty()) {
					        try {
								new MailOperationsMRAProxy().importMRAData(rateAuditVOs);
							} catch (ProxyException e) {
								throw new SystemException(e.getMessage(), e);
							}
						}
					 }
					// import Provisonal rate Data to malmraproint for upront rate Calculation
					String provisionalRateimportEnabled = findSystemParameterValue(MailConstantsVO.SYS_PARA_TRIGGER_FOR_MRA_PROVISIONAL_RATE_IMPORT);
					if(provisionalRateimportEnabled!=null && MailConstantsVO.FLAG_YES.equals(provisionalRateimportEnabled)){
						provisionalRateImport = true;
			      	Collection<RateAuditVO> provisionalRateAuditVOs = new MailController().createRateAuditVOs(mailArrivalVO.getContainerDetails(),MailConstantsVO.MAIL_STATUS_DELIVERED,provisionalRateImport) ;      
			      	if(provisionalRateAuditVOs!=null && !provisionalRateAuditVOs.isEmpty()){
			        try {
			        	Proxy.getInstance().get(MailOperationsMRAProxy.class).importMailProvisionalRateData(provisionalRateAuditVOs);
							} catch (ProxyException e) {
								throw new SystemException(e.getMessage(), e);
							}
						}
					 }
				}

				if (mailbagsForMLDForArrival != null
						&& mailbagsForMLDForArrival.size() > 0) {
					//Added by A-8527 for IASCB-34446 start
					String enableMLDSend= findSystemParameterValue(MailConstantsVO.MAIL_MLD_ENABLED_SEND);
					if(MailConstantsVO.FLAG_YES.equals(enableMLDSend)){
					//Added by A-8527 for IASCB-34446 Ends
					mailController.flagMLDForMailOperations(
							mailbagsForMLDForArrival, "HND");
					}
					/*new MLDController().flagMLDForMailOperations(
							mailbagsForMLDForArrival, "HND");*/
				}
				if (mailbagsForMLDForDelivery != null
						&& mailbagsForMLDForDelivery.size() > 0) {
					//Added by A-8527 for IASCB-34446 start
					String enableMLDSend= findSystemParameterValue(MailConstantsVO.MAIL_MLD_ENABLED_SEND);
					if(MailConstantsVO.FLAG_YES.equals(enableMLDSend)){
					//Added by A-8527 for IASCB-34446 Ends
					mailController.flagMLDForMailOperations(
							mailbagsForMLDForDelivery, "DLV");
					}
					/*new MLDController().flagMLDForMailOperations(
							mailbagsForMLDForDelivery, "DLV");*/
				}
				
				//Added for CRQ ICRD-135130 by A-8061 starts
				//Added by A-8527 for IASCB-34446 start
				String enableMLDSend= findSystemParameterValue(MailConstantsVO.MAIL_MLD_ENABLED_SEND);
				if(MailConstantsVO.FLAG_YES.equals(enableMLDSend)){
				//Added by A-8527 for IASCB-34446 Ends
				mailController.flagMLDForMailOperations(
						mailbagsForMLDForArrival, "RCF"); 
				}
				
				
				//Added for CRQ ICRD-135130 by A-8061 end
				
				
				// Added as part of CRQ ICRD-93584 by A-5526 ends
					if (UldInFlightVO.FLAG_YES.equals(findSystemParameterValue(MailConstantsVO.FLAG_UPD_OPRULD))) {
						updateOperationalULDs(mailArrivalVO);
					}
				}
				scanDetailsVO.setMailDetails(expMails);

				if(deliveredArrivedMails != null && deliveredArrivedMails.size() > 0) {
					removeDeliveredMailbagsFromInventory(deliveredArrivedMails);
				}

				if(deliveredContainers !=null && deliveredContainers.size()>0){
						try {
							removeDeliveredContainersFromInventory(deliveredContainers);
						} catch (FinderException e) {
							// TODO Auto-generated catch block
							e.getErrorCode();
						}
				}

				if(despatchDetailsForInventoryRemoval!=null && despatchDetailsForInventoryRemoval.size()>0){
					removeDeliveredDespatchesFromInventory(despatchDetailsForInventoryRemoval);
				}


				/*
				 * Added By Karthick V as the part of the NCA Mail Tracking CR For all
				 * MailBags that has been Arrived But Not Transfered or Delivered at the
				 * Port... All those MailBags and the Despacthes are to be moved to the
				 * Inventory... For the Corresponding Carrier ... TODO
				 *
				 * First construct the Mail Acceptance VO from the Mail Arrival Vo Then
				 * get all the Containers and its Details ...
				 */


				try {
					saveInventoryDetailsForArrival(constructMailAcceptanceForInventory(mailArrivalVO, despatchesMapForInventory,mailBagsMapForInventory));
				} catch (InventoryForArrivalFailedException e) {
					// TODO Auto-generated catch block
					e.getMessage();
				}

				log.exiting(ENTITY, "saveArrivedMails");

				return scanDetailsVO;
		}
	
		private MailbagInULDForSegmentPK constructMailbagInULDForSegmentPK(ContainerDetailsVO containerDtlsVO,MailbagVO mailbagvo){
			
			MailbagInULDForSegmentPK	mailbagInULDForSegmentPK = new MailbagInULDForSegmentPK();
			mailbagInULDForSegmentPK.setCompanyCode(containerDtlsVO.getCompanyCode());
			mailbagInULDForSegmentPK.setCarrierId(containerDtlsVO.getCarrierId());
			mailbagInULDForSegmentPK.setFlightNumber(containerDtlsVO.getFlightNumber());
			mailbagInULDForSegmentPK.setFlightSequenceNumber(containerDtlsVO.getFlightSequenceNumber());
			mailbagInULDForSegmentPK.setSegmentSerialNumber(containerDtlsVO.getSegmentSerialNumber());
			if(MailConstantsVO.BULK_TYPE.equals(containerDtlsVO.getContainerType())){
			mailbagInULDForSegmentPK.setUldNumber(MailConstantsVO.CONST_BULK+MailConstantsVO.SEPARATOR+containerDtlsVO.getPou());
			}else{
				mailbagInULDForSegmentPK.setUldNumber(containerDtlsVO.getContainerNumber());
			}
			mailbagInULDForSegmentPK.setMailSequenceNumber(mailbagvo.getMailSequenceNumber());
			
			return mailbagInULDForSegmentPK;
		}
		private void updatebulkDetails(Collection<ContainerDetailsVO> containerDetailsVOs)  throws SystemException{
			LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
			if(containerDetailsVOs!=null && containerDetailsVOs.size()>0){
				updateMailVOsLegSerialNumber(containerDetailsVOs);
			for(ContainerDetailsVO containerDtlsVO:containerDetailsVOs){
				if(MailConstantsVO.BULK_TYPE.equals(containerDtlsVO.getContainerType()))	{
					log.log(Log.FINE, "Inside UpDATEBULK>>>>");
				Collection<MailbagVO> mailBagVOinConatiners =null;
				mailBagVOinConatiners =containerDtlsVO.getMailDetails();
				if(mailBagVOinConatiners!=null){
					for(MailbagVO mailbagvo:mailBagVOinConatiners){
					updateBarrowDetails(containerDtlsVO, mailbagvo);
					}
					break;
					}
				}
			}}
		}


		private void updateBarrowDetails(ContainerDetailsVO containerDtlsVO, MailbagVO mailbagvo)
				throws SystemException {
			if(mailbagvo.getOperationalFlag()!=null && (mailbagvo.getOperationalFlag().equals(MailConstantsVO.OPERATION_FLAG_UPDATE)
					||mailbagvo.getOperationalFlag().equals(MailConstantsVO.OPERATION_FLAG_INSERT))){
				MailbagInULDForSegment mailbagInULDForSegment = null;
				try {
					mailbagInULDForSegment = MailbagInULDForSegment
							.find(constructMailbagInULDForSegmentPK(containerDtlsVO,mailbagvo));
				}  catch (FinderException e) {
					log.log(Log.SEVERE, "Finder Exception Caught");
				}
			if(mailbagInULDForSegment!=null){
				log.log(Log.FINE, "mailbag not null>>>>");
			Container container = null;
			ContainerPK containerPK = new ContainerPK();
			containerPK.setCompanyCode(containerDtlsVO.getCompanyCode());
			containerPK.setCarrierId(containerDtlsVO.getCarrierId());
			containerPK.setContainerNumber(mailbagInULDForSegment.getContainerNumber());
			containerPK.setAssignmentPort(containerDtlsVO.getPol());
			containerPK.setFlightNumber(containerDtlsVO.getFlightNumber());
			containerPK.setFlightSequenceNumber(containerDtlsVO.getFlightSequenceNumber());
     if(mailbagvo.getLegSerialNumber()==0){
			  FlightValidationVO flightValidationVO = new FlightValidationVO();
			  try{
			       flightValidationVO = validateFlightForBulk(containerDtlsVO);
			  }catch(SystemException s){
				  log.log(Log.SEVERE, "SystemException Caught");
			  }if(flightValidationVO!= null){
			      containerPK.setLegSerialNumber(
			          flightValidationVO.getLegSerialNumber());
			  }
				}
			 else {
				   containerPK.setLegSerialNumber(
						   mailbagvo.getLegSerialNumber());
			}
			try{
				container = Container.find(containerPK);
			}catch(SystemException systemException){
				 log.log(Log.SEVERE, "SystemException Caught");
			} catch (FinderException finderException) {
				 log.log(Log.SEVERE, "FinderException Caught");
			}
			if(container!=null && !MailConstantsVO.FLAG_YES.equals(container.getArrivedStatus())){
				log.log(Log.FINE, "Container not null>>>>");
			container.setArrivedStatus(MailConstantsVO.FLAG_YES);
			}
			}
			}
		}

		/**
		 * A-1739
		 *
		 * @param mailArrivalVO
		 * @return
		 */
		private AssignedFlightPK createInboundFlightPK(MailArrivalVO mailArrivalVO) {
			AssignedFlightPK inboundFlightPK = new AssignedFlightPK();
			inboundFlightPK.setCompanyCode(mailArrivalVO.getCompanyCode());
			inboundFlightPK.setCarrierId(mailArrivalVO.getCarrierId());
			inboundFlightPK.setFlightNumber(mailArrivalVO.getFlightNumber());
			inboundFlightPK.setFlightSequenceNumber(mailArrivalVO
					.getFlightSequenceNumber());
			inboundFlightPK.setLegSerialNumber(mailArrivalVO.getLegSerialNumber());
			inboundFlightPK.setAirportCode(mailArrivalVO.getAirportCode());
			return inboundFlightPK;
		}

		/**
		 * A-1739
		 *
		 * @param mailArrivalVO
		 * @return
		 */
		private AssignedFlightVO constructInboundFlightVO(MailArrivalVO mailArrivalVO) throws SystemException{
			AssignedFlightVO inboundFlightVO = new AssignedFlightVO();
			inboundFlightVO.setCompanyCode(mailArrivalVO.getCompanyCode());
			inboundFlightVO.setCarrierId(mailArrivalVO.getCarrierId());
			inboundFlightVO.setCarrierCode(mailArrivalVO.getFlightCarrierCode());
			inboundFlightVO.setFlightNumber(mailArrivalVO.getFlightNumber());
			inboundFlightVO.setFlightSequenceNumber(mailArrivalVO
					.getFlightSequenceNumber());
			inboundFlightVO.setLegSerialNumber(mailArrivalVO.getLegSerialNumber());
			inboundFlightVO.setAirportCode(mailArrivalVO.getAirportCode());
			inboundFlightVO.setFlightDate(mailArrivalVO.getArrivalDate());
			inboundFlightVO.setImportFlightStatus(MailConstantsVO.FLIGHT_STATUS_OPEN);
			inboundFlightVO.setExportFlightStatus(MailConstantsVO.FLIGHT_STATUS_CLOSED);
			if(mailArrivalVO.getFlightStatus()!=null){
			inboundFlightVO.setFlightStatus(mailArrivalVO.getFlightStatus());
			}
			else{
				inboundFlightVO.setFlightStatus(MailConstantsVO.FLIGHT_STATUS_CLOSED);
			}
	//Added by A-5945  for ICRD-118205 starts
			LogonAttributes logonVO = ContextUtils.getSecurityContext()
			.getLogonAttributesVO();
			inboundFlightVO.setLastUpdateTime(new LocalDate(logonVO.getAirportCode(),ARP,true));
			inboundFlightVO.setLastUpdateUser(logonVO.getUserId());
			//Added by A-5945 for ICRD-118205 ends
			return inboundFlightVO;
		}

		 private FlightValidationVO validateFlightForBulk(
		            ContainerDetailsVO containerDetailsVO) throws SystemException {
		        Collection<FlightValidationVO> flightValidationVOs = null;
		        flightValidationVOs = new FlightOperationsProxy()
		        .validateFlightForAirport(constructFlightFilterVOForContainer(containerDetailsVO));
		        for (FlightValidationVO flightValidationVO : flightValidationVOs) {
		            if (flightValidationVO.getFlightSequenceNumber() == containerDetailsVO
		                    .getFlightSequenceNumber()) {
		                return flightValidationVO;
		            }
		        }
		        return null;
		    }

		 private FlightFilterVO constructFlightFilterVOForContainer(
		            ContainerDetailsVO containerDetailsVO) {
		        FlightFilterVO flightFilterVO = new FlightFilterVO();
		        flightFilterVO.setCompanyCode(containerDetailsVO.getCompanyCode());
		        flightFilterVO.setFlightCarrierId(containerDetailsVO.getCarrierId());
		        flightFilterVO.setFlightNumber(containerDetailsVO.getFlightNumber());
		        flightFilterVO.setFlightDate(containerDetailsVO.getFlightDate());
		                flightFilterVO.setDirection(FlightFilterVO.INBOUND);
		        flightFilterVO.setStation(containerDetailsVO.getPou());
		        flightFilterVO.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
		        return flightFilterVO;
		}

		 /**
			 * @author a-1936 Added By Karthick V as the part of the NCA Mail Tracking
			 *         CR
			 *
			 * This method is used to Construct the Mail Acceptance VO against which
			 * Carrier the MailBags will be Saved For the Inventory...
			 * @param mailArrivalVO
			 * @return
			 * @throws ContainerAssignmentException
			 */
			private MailAcceptanceVO constructMailAcceptanceForInventory(
					MailArrivalVO mailArrivalVO,
					Map<String, Collection<DespatchDetailsVO>> despatchesMap,
					Map<String, Collection<MailbagVO>> mailBagsMap)
					throws SystemException {
				log.entering(ENTITY, "constructMailAcceptanceForInventory");
				MailAcceptanceVO mailAcceptanceVo = new MailAcceptanceVO();
				Collection<ContainerDetailsVO> containerDetailsVOs = null;
				Collection<ContainerDetailsVO> emptyContainerDetailsVOs = null;
				mailAcceptanceVo.setCompanyCode(mailArrivalVO.getCompanyCode());
				mailAcceptanceVo.setCarrierId(mailArrivalVO.getCarrierId());
				mailAcceptanceVo.setPol(mailArrivalVO.getAirportCode());
				mailAcceptanceVo.setFlightCarrierCode(mailArrivalVO
						.getFlightCarrierCode());
				mailAcceptanceVo.setFlightNumber(String
						.valueOf(MailConstantsVO.DESTN_FLT));
				mailAcceptanceVo.setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
				mailAcceptanceVo.setOwnAirlineCode(mailArrivalVO.getOwnAirlineCode());
				mailAcceptanceVo.setOwnAirlineId(mailArrivalVO.getOwnAirlineId());
				mailAcceptanceVo.setAcceptedUser(mailArrivalVO.getArrivedUser());
				mailAcceptanceVo.setLegSerialNumber(MailConstantsVO.DESTN_FLT);
				mailAcceptanceVo.setTransactionCode(MailConstantsVO.MAIL_TXNCOD_ARR);
				/*
				 * Added to ignore the Container From being Saved before .. Any how this
				 * will be Done wen SaveDestinationAcceptanceDetails is invoked...
				 */
				mailAcceptanceVo.setPreassignNeeded(false);
				mailAcceptanceVo.setScanned(true);
				mailAcceptanceVo.setInventory(true);
				mailAcceptanceVo.setInventoryForArrival(true);

				mailAcceptanceVo.setContainerDetails(new ArrayList<ContainerDetailsVO>());
				emptyContainerDetailsVOs = constructEmptyContainerForInventoryForArrival(mailArrivalVO,mailAcceptanceVo);
				log.log(Log.FINE, "emptyContainerDetailsVOs", emptyContainerDetailsVOs);
				if(emptyContainerDetailsVOs!=null && emptyContainerDetailsVOs.size()>0){
					mailAcceptanceVo.getContainerDetails().addAll(emptyContainerDetailsVOs);
				}
				containerDetailsVOs = constructContainerForInventoryForArrival(
						mailArrivalVO,mailAcceptanceVo, mailBagsMap, despatchesMap);
				log.log(Log.FINE, "containerDetailsVOs", containerDetailsVOs);
				log.log(Log.FINE, "THE MAIL ACCEPTANCE VO FOR THE INVENTORY SAVE",
						mailAcceptanceVo);
				log.exiting(ENTITY, "constructMailAcceptanceForInventory");
				return mailAcceptanceVo;

			}

			/**
			 * @author A-2553
			 * @param mailArrivalVO
			 * @param mailAcceptanceVo
			 * @return
			 * @throws SystemException
			 */
			private Collection<ContainerDetailsVO> constructEmptyContainerForInventoryForArrival(
					MailArrivalVO mailArrivalVO,MailAcceptanceVO mailAcceptanceVo)throws SystemException {
				Collection<ContainerDetailsVO> containersForArrival = new ArrayList<ContainerDetailsVO>();
				if(mailArrivalVO.getContainerDetails()!=null && mailArrivalVO.getContainerDetails().size()>0){
					for (ContainerDetailsVO cntdtlsVO : mailArrivalVO.getContainerDetails() ){
						if(((cntdtlsVO.getDsnVOs()==null ||cntdtlsVO.getDsnVOs().size()==0))&& cntdtlsVO.getOperationFlag()!=null &&
								!MailConstantsVO.FLAG_YES.equals(cntdtlsVO.getDeliveredStatus())){

							//Below Code change for BUG MTK753
							//START
//							updateContainerDetailsForInventory(cntdtlsVO,mailAcceptanceVo);
//							containersForArrival.add(cntdtlsVO);
							ContainerDetailsVO conntDtlsVO = new ContainerDetailsVO();
							conntDtlsVO.setContainerType(MailConstantsVO.BULK_TYPE);
							conntDtlsVO.setDestination(null);
							conntDtlsVO.setContainerNumber(
									MailConstantsVO.CONST_BULK_ARR_ARP
									.concat(MailConstantsVO.SEPARATOR)
									.concat(cntdtlsVO.getCarrierCode()));
							updateContainerDetailsForInventory(conntDtlsVO,mailAcceptanceVo);
							containersForArrival.add(conntDtlsVO);
							break;

							//END


						}
					}
				}
				return containersForArrival;

			}

			/**
			 * @author a-1936 Added By Karthick V as the Part of the NCA Mail Tracking
			 *         CR This method is used to reset the Pks of the Arrived Containers
			 *         as Required For the Inventory Save..
			 * @param containerDetailsVo
			 * @throws SystemException
			 */
			private void updateContainerDetailsForInventory(
					ContainerDetailsVO containerDetails,
					MailAcceptanceVO mailAcceptanceVo) throws SystemException {
				log.entering(ENTITY, "updateContainerDetailsForInventory");
				containerDetails.setCompanyCode(mailAcceptanceVo.getCompanyCode());
				containerDetails
						.setCarrierCode(mailAcceptanceVo.getFlightCarrierCode());
				containerDetails.setCarrierId(mailAcceptanceVo.getCarrierId());
				containerDetails.setFlightNumber(String
						.valueOf(MailConstantsVO.DESTN_FLT));
				containerDetails.setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
				containerDetails.setLegSerialNumber(MailConstantsVO.DESTN_FLT);
				containerDetails.setSegmentSerialNumber(MailConstantsVO.DESTN_FLT);
				containerDetails.setPol(mailAcceptanceVo.getPol());
				containerDetails.setPou(null);
//				containerDetails.setDestination(null);
				containerDetails.setAcceptedFlag(MailConstantsVO.FLAG_YES);
				containerDetails.setTransactionCode(MailConstantsVO.MAIL_TXNCOD_ARR);
				containerDetails.setArrivedStatus(MailConstantsVO.FLAG_NO);
				containerDetails.setAssignmentDate(new LocalDate(mailAcceptanceVo
						.getPol(), Location.ARP, true));
				containerDetails.setFlightDate(null);
				containerDetails.setOffloadFlag(MailConstantsVO.FLAG_NO);
				containerDetails.setAssignedUser(mailAcceptanceVo.getAcceptedUser());
				containerDetails.setAssignmentDate(new LocalDate(containerDetails
						.getPol(), Location.ARP, true));
				updateOperationalFlagForContainer(containerDetails);
				log.exiting(ENTITY, "updateContainerDetailsForInventory");
			}

			/**
			 * @author a-1936
			 * @param containerDetails
			 * @throws SystemException
			 * @throws ContainerAssignmentException
			 */
			private void updateOperationalFlagForContainer(
					ContainerDetailsVO containerDetails) throws SystemException {
				log.entering(ENTITY, "validateContainersForTransfer");
				ContainerAssignmentVO containerAssignmentVO = Container
						.findContainerAssignmentForArrival(containerDetails.getCompanyCode(),
								containerDetails.getContainerNumber(), containerDetails
										.getPol());
				if (containerAssignmentVO != null) {
					if (containerAssignmentVO.getFlightSequenceNumber() > 0) {
						containerDetails
								.setOperationFlag(ContainerDetailsVO.OPERATION_FLAG_INSERT);
						containerDetails
								.setContainerOperationFlag(ContainerDetailsVO.OPERATION_FLAG_INSERT);
					} else {
						if (containerAssignmentVO.getCarrierId() == containerDetails
								.getCarrierId()) {
							containerDetails
									.setContainerOperationFlag(ContainerDetailsVO.OPERATION_FLAG_UPDATE);
							if (MailConstantsVO.FLAG_YES.equals(containerAssignmentVO
									.getAcceptanceFlag())) {
								containerDetails
										.setOperationFlag(ContainerDetailsVO.OPERATION_FLAG_UPDATE);
							} else {
								containerDetails
										.setOperationFlag(ContainerDetailsVO.OPERATION_FLAG_INSERT);
							}
						} else {
							containerDetails
									.setOperationFlag(ContainerDetailsVO.OPERATION_FLAG_INSERT);
							containerDetails
									.setContainerOperationFlag(ContainerDetailsVO.OPERATION_FLAG_INSERT);
						}
					}
				} else {
					containerDetails
							.setOperationFlag(ContainerDetailsVO.OPERATION_FLAG_INSERT);
					containerDetails
							.setContainerOperationFlag(ContainerDetailsVO.OPERATION_FLAG_INSERT);
				}
				log.exiting(ENTITY, "validateContainersForTransfer");
			}

			/**
			 * @author A-1936 Added By Karthick V as the part of the NCA Mail Tracking
			 *         CR
			 * @param mailAcceptance
			 * @param mailBagsForInventory
			 * @param despacthesForInventory
			 * @throws SystemException
			 * @throws ContainerAssignmentException
			 */
			private Collection<ContainerDetailsVO> constructContainerForInventoryForArrival(
					MailArrivalVO mailArrivalVO,
					MailAcceptanceVO mailAcceptanceVo,
					Map<String, Collection<MailbagVO>> mailBagsForInventoryMap,
					Map<String, Collection<DespatchDetailsVO>> despacthesForInventoryMap)
					throws SystemException {
				Collection<String> containerKeys = new ArrayList<String>();
				Collection<MailbagVO> mailBags = null;
				Collection<DespatchDetailsVO> despacthes = null;
				ContainerDetailsVO containerDetailsVo = null;
				Collection<ContainerDetailsVO> actualArrivedContainers = mailArrivalVO.getContainerDetails();
				Collection<ContainerDetailsVO> containersForArrival = mailAcceptanceVo.getContainerDetails();
				if(containersForArrival == null){
					containersForArrival = new ArrayList<ContainerDetailsVO>();
				}

				/*
				 * Iterate the MailBags Map and the Despatch Map and identify the Total
				 * No of Containers that are being involved in the process.....
				 *
				 *
				 *
				 */
				if (mailBagsForInventoryMap != null
						&& mailBagsForInventoryMap.size() > 0) {
					for (String key : mailBagsForInventoryMap.keySet()) {
						if (!containerKeys.contains(key)) {
							containerKeys.add(key);
						}
					}
				}
				if (despacthesForInventoryMap != null
						&& despacthesForInventoryMap.size() > 0) {
					for (String key : despacthesForInventoryMap.keySet()) {
						if (!containerKeys.contains(key)) {
							containerKeys.add(key);
						}
					}
				}

				if (containerKeys.size() > 0) {
					for (String containerKey : containerKeys) {
						containerDetailsVo = new ContainerDetailsVO();
						String[] splitStr = containerKey
								.split(MailConstantsVO.ARPULD_KEYSEP);
						containerDetailsVo.setContainerNumber(splitStr[0]);
						containerDetailsVo.setContainerType(splitStr[1]);
						if(splitStr.length>2 && "Y".equals(splitStr[2])){
							containerDetailsVo.setPaBuiltFlag(splitStr[2]);
						}
						/*
						 * FOR M39 (CARDIT_1.2/RESDIT_1.1)
						 * Since new insert is going for MTKCONMST during arrival- acceptance,
						 * the journeyid and pacode should be carry forwarded to the new insert also.
						 */
						if(actualArrivedContainers != null && actualArrivedContainers.size() > 0) {
							for(ContainerDetailsVO arrivedContDtlVO : actualArrivedContainers) {
								if((arrivedContDtlVO.getContainerNumber() != null &&
										arrivedContDtlVO.getContainerNumber().equals(containerDetailsVo.getContainerNumber())) &&
										(arrivedContDtlVO.getContainerType() != null &&
												arrivedContDtlVO.getContainerType().equals(containerDetailsVo.getContainerType()))) {
									containerDetailsVo.setDestination(arrivedContDtlVO.getDestination());
									if(arrivedContDtlVO.getPaBuiltFlag() != null &&
											arrivedContDtlVO.getPaBuiltFlag().equals(containerDetailsVo.getPaBuiltFlag())) {
										containerDetailsVo.setPaCode(arrivedContDtlVO.getPaCode());
										containerDetailsVo.setContainerJnyId(arrivedContDtlVO.getContainerJnyId());
									}
								}
							}
						}
						updateContainerDetailsForInventory(containerDetailsVo,
								mailAcceptanceVo);

						containerDetailsVo.setMailDetails(new ArrayList<MailbagVO>());
						containerDetailsVo
								.setDesptachDetailsVOs(new ArrayList<DespatchDetailsVO>());
						containerDetailsVo.setDsnVOs(new ArrayList<DSNVO>());

						mailBags = mailBagsForInventoryMap.get(containerKey);
						if (mailBags != null && mailBags.size() > 0) {
							updateDSNsForMailBags(mailBags, containerDetailsVo);
						}

						despacthes = despacthesForInventoryMap.get(containerKey);


						/*
						 * The below code is written for the purpose of ULD ACQUITTAL
						 * The bags present in the ULD will be moved to a BULK named "BULK-ARR-QF(carrierCode)"
						 */
						if(MailConstantsVO.ULD_TYPE.equals(containerDetailsVo.getContainerType())){
							containerDetailsVo.setContainerType(MailConstantsVO.BULK_TYPE);
							containerDetailsVo.setDestination(null);
							containerDetailsVo.setContainerNumber(
									MailConstantsVO.CONST_BULK_ARR_ARP
									.concat(MailConstantsVO.SEPARATOR)
									.concat(containerDetailsVo.getCarrierCode()));
							if(containersForArrival != null && containersForArrival.size() > 0){
								boolean isExisting = false;
								for(ContainerDetailsVO cntDtlVO : containersForArrival){
									if(cntDtlVO.getContainerNumber().equals(containerDetailsVo.getContainerNumber())){
										if(containerDetailsVo.getMailDetails() != null && containerDetailsVo.getMailDetails().size() > 0){
											if(cntDtlVO.getMailDetails() == null){
												cntDtlVO.setMailDetails(new ArrayList<MailbagVO>());
											}

											cntDtlVO.getMailDetails().addAll(containerDetailsVo.getMailDetails());
										}
										if(containerDetailsVo.getDesptachDetailsVOs() != null &&
												containerDetailsVo.getDesptachDetailsVOs().size() > 0){
											if(cntDtlVO.getDesptachDetailsVOs() == null){
												cntDtlVO.setDesptachDetailsVOs(new ArrayList<DespatchDetailsVO>());
											}

											cntDtlVO.getDesptachDetailsVOs().addAll(containerDetailsVo.getDesptachDetailsVOs());
										}
										if(containerDetailsVo.getDsnVOs() != null && containerDetailsVo.getDsnVOs().size() > 0){
											if(cntDtlVO.getDsnVOs() == null){
												cntDtlVO.setDsnVOs(new ArrayList<DSNVO>());
											}

											cntDtlVO.getDsnVOs().addAll(containerDetailsVo.getDsnVOs());
										}
										isExisting = true;
										break;
									}
								}
								if(!isExisting){
									updateOperationalFlagForContainer(containerDetailsVo);
									containersForArrival.add(containerDetailsVo);
								}
							}else{
								updateOperationalFlagForContainer(containerDetailsVo);
								containersForArrival.add(containerDetailsVo);
							}
						}else{
							if(containersForArrival != null && containersForArrival.size() > 0){
								boolean isExisting = false;
								for(ContainerDetailsVO cntDtlVO : containersForArrival){
									if(cntDtlVO.getContainerNumber().equals(containerDetailsVo.getContainerNumber())){
										if(containerDetailsVo.getMailDetails() != null && containerDetailsVo.getMailDetails().size() > 0){
											if(cntDtlVO.getMailDetails() == null){
												cntDtlVO.setMailDetails(new ArrayList<MailbagVO>());
											}

											cntDtlVO.getMailDetails().addAll(containerDetailsVo.getMailDetails());
										}
										if(containerDetailsVo.getDesptachDetailsVOs() != null &&
												containerDetailsVo.getDesptachDetailsVOs().size() > 0){
											if(cntDtlVO.getDesptachDetailsVOs() == null){
												cntDtlVO.setDesptachDetailsVOs(new ArrayList<DespatchDetailsVO>());
											}

											cntDtlVO.getDesptachDetailsVOs().addAll(containerDetailsVo.getDesptachDetailsVOs());
										}
										if(containerDetailsVo.getDsnVOs() != null && containerDetailsVo.getDsnVOs().size() > 0){
											if(cntDtlVO.getDsnVOs() == null){
												cntDtlVO.setDsnVOs(new ArrayList<DSNVO>());
											}

											cntDtlVO.getDsnVOs().addAll(containerDetailsVo.getDsnVOs());
										}
										isExisting = true;
										break;
									}
								}
								if(!isExisting){
									containersForArrival.add(containerDetailsVo);
								}
							}else{
								containersForArrival.add(containerDetailsVo);
							}
						}
					}
				}

				return containersForArrival;
			}

			/**
			 * a-1936 Added By Karthick V as the part of the NCA Mail Tracking Cr
			 *
			 * @param damagedDespatches
			 * @return
			 * @throws SystemException 
			 */
			private void updateDSNsForMailBags(Collection<MailbagVO> mailBags,
					ContainerDetailsVO containerDetails) throws SystemException {
				Map<String, Collection<MailbagVO>> dsnMailMap = new HashMap<String, Collection<MailbagVO>>();
				Map<String, DSNVO> dsnMap = new HashMap<String, DSNVO>();

				Collection<DSNVO> dsnVos = new ArrayList<DSNVO>();
				for (MailbagVO mailbagVO : mailBags) {
					String dsnPK = constructDSNPKFromMail(mailbagVO);
					/*
					 * Update the Despatch Carrier Details For the Inventory
					 */
					mailbagVO.setCarrierCode(containerDetails.getCarrierCode());
					mailbagVO.setCarrierId(containerDetails.getCarrierId());
					mailbagVO.setFinalDestination(null);
					mailbagVO.setFlightNumber(containerDetails.getFlightNumber());
					mailbagVO.setFlightSequenceNumber(containerDetails
							.getFlightSequenceNumber());
					mailbagVO.setSegmentSerialNumber(containerDetails
							.getSegmentSerialNumber());
					mailbagVO.setPou(null);
					mailbagVO
							.setOperationalFlag(ContainerDetailsVO.OPERATION_FLAG_INSERT);
					mailbagVO.setScannedDate(new LocalDate(containerDetails.getPol(),
							Location.ARP, true));
					mailbagVO.setScannedPort(containerDetails.getPol());

					/*
					 * Moved Mails from Flight to Airport will be placed in
					 * a Bulk named "BULK-ARR-QF" @ the respective port.
					 * After Arrival, the bulk will be moved out of uld and
					 * will be placed inside the above mentioned BULK and
					 * the ULD will be set free.
					 * This is done as per the QF bussiness and this change is
					 * well known to QF bussinedd team
					 */
//					mailbagVO.setContainerNumber(containerDetails.getContainerNumber());
//					mailbagVO.setContainerType(containerDetails.getContainerType());MailConstantsVO.CONST_BULK_ARR_ARP
					String containerNumber =
						MailConstantsVO.CONST_BULK_ARR_ARP
						.concat(MailConstantsVO.SEPARATOR)
						.concat(mailbagVO.getCarrierCode());
					mailbagVO.setContainerNumber(containerNumber);
//					mailbagVO.setContainerType(MailConstantsVO.BULK_TYPE);
					mailbagVO.setUldNumber(containerDetails.getContainerNumber());

					Collection<MailbagVO> dsnMails = dsnMailMap.get(dsnPK);
					DSNVO dsnVo = dsnMap.get(dsnPK);
					if (dsnMails == null) {
						dsnMails = new ArrayList<MailbagVO>();
						dsnMailMap.put(dsnPK, dsnMails);
						dsnVo = new DSNVO();
						dsnMap.put(dsnPK, dsnVo);
						dsnVos.add(dsnVo);
						updateDSNVOFromMailbag(dsnVo, mailbagVO, false);

					}
					if (ContainerDetailsVO.OPERATION_FLAG_INSERT
							.equals(containerDetails.getOperationFlag())) {
						dsnVo
								.setOperationFlag(ContainerDetailsVO.OPERATION_FLAG_INSERT);
					} else {
						dsnVo
								.setOperationFlag(ContainerDetailsVO.OPERATION_FLAG_UPDATE);
					}
					dsnMails.add(mailbagVO);
					updateDSNVOFromMailbag(dsnVo, mailbagVO, true);
				}

				containerDetails.getMailDetails().addAll(mailBags);
				containerDetails.getDsnVOs().addAll(dsnVos);

			}

			/**
			 * @author a-1936
			 * @param dsnVO
			 * @param mailBag
			 * @param isUpdate
			 * @return
			 * @throws SystemException 
			 */
			private DSNVO updateDSNVOFromMailbag(DSNVO dsnVO, MailbagVO mailBag,
					boolean isUpdate) throws SystemException {
				if (!isUpdate) {
					dsnVO.setCompanyCode(mailBag.getCompanyCode());
					dsnVO.setDsn(mailBag.getDespatchSerialNumber());
					dsnVO.setOriginExchangeOffice(mailBag.getOoe());
					dsnVO.setDestinationExchangeOffice(mailBag.getDoe());
					dsnVO.setMailClass(mailBag.getMailClass());
					// Added to include the DSN PK
					dsnVO.setMailSubclass(mailBag.getMailSubclass());
					dsnVO.setMailCategoryCode(mailBag.getMailCategoryCode());
					dsnVO.setYear(mailBag.getYear());
					dsnVO.setPltEnableFlag(MailConstantsVO.FLAG_YES);
				} else {
					dsnVO.setBags(dsnVO.getBags() + 1);
					//dsnVO.setWeight(dsnVO.getWeight() + mailBag.getWeight());
					
						try {
							dsnVO.setWeight(Measure.addMeasureValues(dsnVO.getWeight(),  mailBag.getWeight()));
						} catch (UnitException e) {
							// TODO Auto-generated catch block
							throw new SystemException(e.getErrorCode());
						}
					
				}
				return dsnVO;
			}



			/**
			 * @author a-1936
			 * @param dsnVO
			 * @param despatch
			 * @param isUpdate
			 * @return
			 * @throws SystemException 
			 */

			private DSNVO updateDSNVOFromDespatch(DSNVO dsnVO,
					DespatchDetailsVO despatch, boolean isUpdate) throws SystemException {
				if (!isUpdate) {
					dsnVO.setCompanyCode(despatch.getCompanyCode());
					dsnVO.setDsn(despatch.getDsn());
					dsnVO.setOriginExchangeOffice(despatch.getOriginOfficeOfExchange());
					dsnVO.setDestinationExchangeOffice(despatch
							.getDestinationOfficeOfExchange());
					dsnVO.setMailClass(despatch.getMailClass());
					// Added to include the DSN PK
					dsnVO.setMailSubclass(despatch.getMailSubclass());
					dsnVO.setMailCategoryCode(despatch.getMailCategoryCode());
					dsnVO.setYear(despatch.getYear());
					dsnVO.setPltEnableFlag(MailConstantsVO.FLAG_NO);
				} else {
					dsnVO.setBags(dsnVO.getBags()
							+ (despatch.getReceivedBags() - despatch
									.getPrevReceivedBags()));
					/*dsnVO.setWeight(dsnVO.getWeight()
							+ (despatch.getReceivedWeight() - despatch
									.getPrevReceivedWeight()));*/
					
						Measure despatchWt;
						try {
							despatchWt = Measure.subtractMeasureValues(despatch.getReceivedWeight(), despatch.getPrevReceivedWeight());
							Measure weight;
							try {
								weight = Measure.addMeasureValues(dsnVO.getWeight(), despatchWt);
								dsnVO.setWeight(weight);//added by A-7371
							} catch (UnitException e1) {
								// TODO Auto-generated catch block
								throw new SystemException(e1.getErrorCode());
							}
						} catch (UnitException e1) {
							// TODO Auto-generated catch block
							throw new SystemException(e1.getErrorCode());
						}
						
							
						
					despatch.setAcceptedBags((despatch.getReceivedBags() - despatch
									.getPrevReceivedBags()));
					/*despatch.setAcceptedWeight(despatch.getReceivedWeight() - despatch
							.getPrevReceivedWeight());*/
					
						try {
							despatch.setAcceptedWeight(Measure.subtractMeasureValues(despatch.getReceivedWeight(), despatch
									.getPrevReceivedWeight()));
						} catch (UnitException e) {
							// TODO Auto-generated catch block
							throw new SystemException(e.getErrorCode());
						}

				}
				return dsnVO;
			}

			/**
			 * @author a-1936 This method is used to save the Inventory Information for
			 *         the Arrival...
			 *
			 */
			/**
			 * Added By Karthick V as the part of the NCA Mail Tracking CR For all
			 * MailBags that has been Arrived But Not Transfered or Delivered at the
			 * Port... All those MailBags and the Despacthes are to be moved to the
			 * Inventory... For the Corresponding Carrier ... TODO First construct the
			 * Mail Acceptance VO from the Mail Arrival Vo Then get all the Containers
			 * and its Details ...
			 * @throws CapacityBookingProxyException
			 * @throws MailBookingException
			 * @throws DuplicateMailBagsException 
			 */
			private void saveInventoryDetailsForArrival(
					MailAcceptanceVO mailAcceptanceVO)
					throws InventoryForArrivalFailedException, SystemException ,
					ULDDefaultsProxyException, CapacityBookingProxyException, MailBookingException, DuplicateMailBagsException {
				log.entering(ENTITY, "saveInventoryDetailsForArrival");
				log.log(Log.FINEST, "mailaxp", mailAcceptanceVO);
				try {
					new MailAcceptance()
							.saveInventoryDetailsForArrival(mailAcceptanceVO);
				} catch (FlightClosedException ex) {
					throw new InventoryForArrivalFailedException(ex);
				} catch (ContainerAssignmentException ex) {
					// TODO Auto-generated catch block
					throw new InventoryForArrivalFailedException(ex);

				} catch (InvalidFlightSegmentException ex) {
					throw new InventoryForArrivalFailedException(ex);
					// TODO Auto-generated catch block
				}
				catch (MailDefaultStorageUnitException ex) {
					throw new InventoryForArrivalFailedException(ex);
					// TODO Auto-generated catch block
				}
				log.exiting(ENTITY, "saveInventoryDetailsForArrival");

			}



			private String constructDSNPKFromMail(MailbagVO mailbagVO) {
				log.entering("MailAcceptance", "constructDSNPK");
				StringBuilder dsnPK = new StringBuilder();
				dsnPK.append(mailbagVO.getCompanyCode());
				dsnPK.append(mailbagVO.getDespatchSerialNumber());
				// Added to include the DSN PK
				dsnPK.append(mailbagVO.getMailSubclass());
				dsnPK.append(mailbagVO.getMailCategoryCode());
				dsnPK.append(mailbagVO.getOoe());
				dsnPK.append(mailbagVO.getDoe());
				dsnPK.append(mailbagVO.getYear());
				log.exiting("MailAcceptance", "constructDSNPK");
				return dsnPK.toString();
			}

			/**
			 * @author a-1936
			 * Added By Karthick V to remove all  the despacthes that are being delivered to be removed from the Inventory ..
			 * @param despatchDetailsVos
			 * @throws SystemException
			 */
			private void removeDeliveredDespatchesFromInventory(Collection<DespatchDetailsVO> despatchDetailsVos)
			  throws SystemException{
				Collection<DespatchDetailsVO> despatchDetailsVosForReassign = updateDespacthesForInventoryRemoval(despatchDetailsVos);
				 new ReassignController().reassignDSNsFromDestination(despatchDetailsVosForReassign);
			}

			/**
			 * @author a-2553
			 * Added By Paulson to remove all  the empty containers that are being delivered to be removed from the Inventory ..
			 * @param deliveredContainers
			 * @throws SystemException
			 */
			private void removeDeliveredContainersFromInventory(Collection<ContainerDetailsVO> deliveredContainers)
			  throws SystemException, FinderException{
				Collection<ContainerVO> containersForReassign = updateContainersForInventoryRemoval(deliveredContainers);

				new ReassignController().reassignContainerFromDestToDest(containersForReassign, null);

			}

			/*
			    * Added By Karthick V as the part of the NCA Mail Tracking CR
			    * In case the Arrival and the Delivery happens at the same time   then No
			    * Mails and the Despacthes will be  saved to the Inventory..
			    *
			    * Let us Assume if the  AutoArrival Happens First followed By Auto Delivery then all the
			    * MailBags that has been arrived as the part of the Auto Arrival  has to be removed as soon as the
			    * Delivery Takes Place..
			    */
				private void removeDeliveredMailbagsFromInventory(Collection<MailbagVO> mailbagsAlreadyArrived)
				throws SystemException {
					log.entering(ENTITY, "removeDeliveredMailbagsFromInventory");
					 Collection<MailbagVO> mailBagsForRemoval=updateMailBagForInventoryRemoval(mailbagsAlreadyArrived);
					new ReassignController().reassignMailFromDestination(mailBagsForRemoval);
					log.exiting(ENTITY, "removeDeliveredMailbagsFromInventory");

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

				private void updateOperationalULDs(MailArrivalVO mailArrivalVO)
						throws SystemException {
					log.entering(ENTITY, "updateOperationalULDs");

					Collection<ContainerDetailsVO> containerDetails = mailArrivalVO
							.getContainerDetails();

					Collection<UldInFlightVO> uldInFlightVOs = new ArrayList<UldInFlightVO>();
					if (containerDetails != null && containerDetails.size() > 0) {
						for (ContainerDetailsVO containerDetailsVO : containerDetails) {
							if (MailConstantsVO.ULD_TYPE.equals(containerDetailsVO
									.getContainerType())
									&& OPERATION_FLAG_UPDATE.equals(containerDetailsVO
											.getOperationFlag())) {
								uldInFlightVOs
										.add(constructUldInFlightVO(containerDetailsVO));
							}
						}
					}
					if (uldInFlightVOs != null && uldInFlightVOs.size() > 0) {
						new OperationsFltHandlingProxy()
								.saveOperationalULDsInFlight(uldInFlightVOs);
					}
					log.exiting(ENTITY, "updateOperationalULDs");

				}

				private UldInFlightVO constructUldInFlightVO(
						ContainerDetailsVO containerDetailsVO) {
					UldInFlightVO uldInFlightVO = new UldInFlightVO();
					uldInFlightVO.setCompanyCode(containerDetailsVO.getCompanyCode());
					uldInFlightVO.setUldNumber(containerDetailsVO.getContainerNumber());
					uldInFlightVO.setCarrierId(containerDetailsVO.getCarrierId());
					if (containerDetailsVO.getFlightSequenceNumber() > 0) {
						uldInFlightVO.setFlightNumber(containerDetailsVO.getFlightNumber());
						uldInFlightVO.setFlightSequenceNumber(containerDetailsVO
								.getFlightSequenceNumber());
						uldInFlightVO.setLegSerialNumber(containerDetailsVO
								.getLegSerialNumber());
					}
					uldInFlightVO.setPou(containerDetailsVO.getPou());
					uldInFlightVO.setAirportCode(containerDetailsVO.getPol());
					uldInFlightVO.setFlightDirection(MailConstantsVO.OPERATION_INBOUND);

					return uldInFlightVO;
				}


				/**
				 *
				 * @param assignedFlightSegmentPK
				 * @param segmentContainers
				 * @return
				 */
				private AssignedFlightSegmentVO constructAssignedFlightSegVOForContainer(
						AssignedFlightSegmentPK assignedFlightSegmentPK,
						Collection<ContainerDetailsVO> segmentContainers) {

					AssignedFlightSegmentVO asgFltSegVO = new AssignedFlightSegmentVO();
					asgFltSegVO.setCarrierId(assignedFlightSegmentPK.getCarrierId());
					asgFltSegVO.setCompanyCode(assignedFlightSegmentPK.getCompanyCode());
					asgFltSegVO.setFlightNumber(assignedFlightSegmentPK.getFlightNumber());
					asgFltSegVO.setFlightSequenceNumber(assignedFlightSegmentPK
							.getFlightSequenceNumber());
					asgFltSegVO.setSegmentSerialNumber(assignedFlightSegmentPK
							.getSegmentSerialNumber());

					String pou = null;
					String pol = null;
					for (ContainerDetailsVO containerDetailsVO : segmentContainers) {
						pou = containerDetailsVO.getPou();
						pol = containerDetailsVO.getPol();
						break;
					}
					asgFltSegVO.setPol(pol);
					asgFltSegVO.setPou(pou);

					return asgFltSegVO;
				}

				/**
				 * A-1739
				 *
				 * @param mailArrivalVO
				 * @param paBuiltContainers
				 * @return
				 * @throws SystemException
				 * @throws InvalidFlightSegmentException
				 */
				private Map<AssignedFlightSegmentPK, Collection<ContainerDetailsVO>> groupContainersForSegmentForArrival(
						MailArrivalVO mailArrivalVO,
						Collection<ContainerDetailsVO> paBuiltContainers)
						throws SystemException, InvalidFlightSegmentException {

					Collection<ContainerDetailsVO> containerDetails = mailArrivalVO
							.getContainerDetails();

					Map<AssignedFlightSegmentPK, Collection<ContainerDetailsVO>> segConMap = new HashMap<AssignedFlightSegmentPK, Collection<ContainerDetailsVO>>();
			if(containerDetails!=null && containerDetails.size()>0){
					for (ContainerDetailsVO containerDetailsVO : containerDetails) {
						AssignedFlightSegmentPK assignedFlightSegPK = null;

						boolean isUpdated = false;

						assignedFlightSegPK = constructAssignedFlightSegPK(containerDetailsVO);

						if (assignedFlightSegPK.getSegmentSerialNumber() == 0) {
							// newly added container. 'll have to find the segment
							assignedFlightSegPK = constructAssignedFlightSegmentPKForArrival(
									containerDetailsVO, mailArrivalVO);

							containerDetailsVO.setCompanyCode(assignedFlightSegPK
									.getCompanyCode());
							containerDetailsVO.setCarrierId(assignedFlightSegPK
									.getCarrierId());
							containerDetailsVO.setFlightNumber(assignedFlightSegPK
									.getFlightNumber());
							containerDetailsVO.setFlightSequenceNumber(assignedFlightSegPK
									.getFlightSequenceNumber());
							containerDetailsVO.setSegmentSerialNumber(assignedFlightSegPK
									.getSegmentSerialNumber());
							containerDetailsVO.setCarrierCode(mailArrivalVO
									.getFlightCarrierCode());
							containerDetailsVO.setPou(mailArrivalVO.getAirportCode());
						}

						if (containerDetailsVO.getOperationFlag() != null) {
							isUpdated = true;
						}
						if (isUpdated) {
							Collection<ContainerDetailsVO> containersForSegment = segConMap
									.get(assignedFlightSegPK);
							if (containersForSegment == null) {
								containersForSegment = new ArrayList<ContainerDetailsVO>();
								segConMap.put(assignedFlightSegPK, containersForSegment);
							}
							containersForSegment.add(containerDetailsVO);

							if (MailConstantsVO.FLAG_YES.equals(containerDetailsVO
									.getPaBuiltFlag())) {
								paBuiltContainers.add(containerDetailsVO);
							}
						}
					}}

					return segConMap;
				}

				/**
				 * @author A-1739
				 * @param mailArrivalVO
				 * @return
				 * @throws SystemException
				 * @throws MailbagIncorrectlyDeliveredException
				 * @throws DuplicateMailBagsException
				 */

				private Map<String, DSNVO> checkForIncorrectArrivalDetails(
						MailArrivalVO mailArrivalVO,
						Collection<DespatchDetailsVO> despatchDetailsForInventoryRemoval,
						Collection<MailbagVO> expMailbags,  Collection<MailbagVO> newArrivedMailbags,
						Collection<MailbagVO> deliveredMailBagsForMonitorSLA,
						Collection<MailbagVO> deliveredArrivedMails,
						Collection<ContainerDetailsVO> deliveredContainers)
						throws SystemException, MailbagIncorrectlyDeliveredException, DuplicateMailBagsException,MailTrackingBusinessException {
					log.entering(ENTITY, "checkForIncorrectlyDeliveredMailbags");

					Map<String, DSNVO> dsnMap = new HashMap<String, DSNVO>();
					Collection<ContainerDetailsVO> containersToRem = new ArrayList<ContainerDetailsVO>();

					String companyCode = mailArrivalVO.getCompanyCode();
					String deliveredPort = mailArrivalVO.getAirportCode();
					boolean isScanned = mailArrivalVO.isScanned();
					boolean deliveryChkNeeded = mailArrivalVO.isDeliveryCheckNeeded();
					Collection<ContainerDetailsVO> containers = mailArrivalVO.getContainerDetails();
					String POST_DATA_CAPTURE="PDC";
					/*
					 * Check for incorrect container assignment for newly added containers
					 */
					if(containers!=null && containers.size()>0){
					for (ContainerDetailsVO containerDetailsVO : containers) {
						if (containerDetailsVO.getOperationFlag() != null) {
							if (mailArrivalVO.isScanned()) {
								containerDetailsVO.setCompanyCode(mailArrivalVO.getCompanyCode());
								if (OPERATION_FLAG_INSERT.equals(containerDetailsVO.getOperationFlag())) {
									try {
										checkContainerAssignmentAtPol(containerDetailsVO);
									} catch (ContainerAssignmentException ex) {
										if (containerDetailsVO.getMailDetails() != null) {
											updateMailbagVOsWithErr(containerDetailsVO.getMailDetails(),MailConstantsVO.ERR_MSG_NEW_ULD_ASG);
											expMailbags.addAll(containerDetailsVO.getMailDetails());
										}
										containersToRem.add(containerDetailsVO);
										continue;
									}
								}
							}

							Collection<DSNVO> dsnVOs = containerDetailsVO.getDsnVOs();
							if(dsnVOs==null ||dsnVOs.size()==0){
								if("U".equals(containerDetailsVO.getOperationFlag())&& "Y".equals(containerDetailsVO.getDeliveredStatus())){
									deliveredContainers.add(containerDetailsVO);
								}
							}

							if (dsnVOs != null && dsnVOs.size() > 0) {
								for (DSNVO dsnVO : dsnVOs) {
									log.log(Log.FINE, "The DSN VO", dsnVO);
									if (dsnVO.getOperationFlag() != null) {
										String dsnPK = constructDSNPKFrmDSNVO(dsnVO);
										DSNVO dsnMstVO = dsnMap.get(dsnPK);
										if (dsnMstVO == null) {
											dsnMstVO = copyDSNVO(dsnVO);
											dsnMap.put(dsnPK, dsnMstVO);
											Collection<DSNAtAirportVO> dsnAtAirports = new ArrayList<DSNAtAirportVO>();
											dsnMstVO.setDsnAtAirports(dsnAtAirports);
												DSNAtAirportVO dsnAtAirportVO = new DSNAtAirportVO();
												dsnAtAirportVO.setAirportCode(mailArrivalVO.getAirportCode());
												dsnAtAirportVO.setMailClass(dsnVO.getMailClass());
												dsnAtAirports.add(dsnAtAirportVO);
											Collection<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();
											dsnMstVO.setMailbags(mailbagVOs);
										}
										updateDSNAtAirportVOForArrival(dsnMstVO, dsnVO);

										// set newly arrived bags of DSNVO
										// bags are supposed to be newly arrived if the received
										// count is greater than manifest bags
										if (dsnVO.getReceivedBags() > dsnVO.getBags()) {
											dsnMstVO.setBags(dsnVO.getReceivedBags()
													- dsnVO.getBags());
											/*dsnMstVO.setWeight(dsnVO.getReceivedWeight()
													- dsnVO.getWeight());*/
											
												try {
													dsnMstVO.setWeight(Measure.subtractMeasureValues(dsnVO.getReceivedWeight(), dsnVO.getWeight()));
												} catch (UnitException e) {
													// TODO Auto-generated catch block
													throw new SystemException(e.getErrorCode());
												}
											
										}
									}
								}
								}


							Collection<MailbagVO> duplicateMailbags = new ArrayList<MailbagVO>();
							Collection<MailbagVO> incorrectlyDeliveredMails = new ArrayList<MailbagVO>();
							Collection<MailbagVO> mailbags = containerDetailsVO.getMailDetails();

							if (mailbags != null && mailbags.size() > 0) {


								for (MailbagVO mailbagVO : mailbags) {
									
									if (mailbagVO.getOperationalFlag() != null || "PDC".equals(mailbagVO.getActionMode())) {           	
										
									//Added by A-5945 for ICRD-129779 starts
									//As per our design multiple arrival at the same port will not be permitted.
									//Hence restricting arrival of a manifested mailbag ,if the same is already arrived as found in the same port.
									//Need to handle return cases (mailbag returning back to same port).Will handle this scenario later
										Collection<MailbagHistoryVO>  mailhistories = new  ArrayList<MailbagHistoryVO>();
										 mailhistories = (mailbagVO.getMailbagHistories()!=null && !mailbagVO.getMailbagHistories().isEmpty()) ?
												 mailbagVO.getMailbagHistories():(ArrayList<MailbagHistoryVO>) Mailbag.findMailbagHistoriesWithoutCarditDetails(mailbagVO.getCompanyCode(), mailbagVO.getMailbagId());
										 if(mailhistories!=null&& mailhistories.size()>0){
											 for(MailbagHistoryVO mailbaghistoryvo :mailhistories ){
												 if(mailbagVO.getFlightNumber()!=null && mailbagVO.getFlightSequenceNumber()>0){
												 if(MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mailbaghistoryvo.getMailStatus()) &&
														 mailbaghistoryvo.getScannedPort().equals(mailbagVO.getScannedPort())	 ){
													 if(!(mailbaghistoryvo.getFlightNumber().equals(mailbagVO.getFlightNumber())) ||
															mailbaghistoryvo.getFlightSequenceNumber()!= mailbagVO.getFlightSequenceNumber()){
														 //The mailbag already arived at same port
														//Added as part of bug ICRD-130734 by A-5526
														 if(MailConstantsVO.OPERATION_FLAG_UPDATE.equals(mailbagVO.getOperationalFlag()) ||
																 POST_DATA_CAPTURE.equals(mailbagVO.getActionMode()) || (MailConstantsVO.OPERATION_FLAG_INSERT.equals(mailbagVO.getOperationalFlag()) 
																		 && !mailArrivalVO.isFlightChange())){


														/*throw new DuplicateMailBagsException(
																	DuplicateMailBagsException.MAILBAG_ALREADY_ARRIVAL_EXCEPTION,    //commented by A-8353 for ICRD-230449
																	new Object[] { mailbagVO.getMailbagId() });*/
														 }
													 }
													 else if((mailbaghistoryvo.getFlightNumber()!=null && mailbaghistoryvo.getFlightNumber().equals(mailbagVO.getFlightNumber())) &&
															mailbaghistoryvo.getFlightSequenceNumber()== mailbagVO.getFlightSequenceNumber() &&
															(mailbaghistoryvo.getContainerNumber()!=null && !mailbaghistoryvo.getContainerNumber().equals(mailbagVO.getContainerNumber())) 
															){    
														 if(MailConstantsVO.OPERATION_FLAG_UPDATE.equals(mailbagVO.getOperationalFlag()) ||
																 POST_DATA_CAPTURE.equals(mailbagVO.getActionMode()) || (MailConstantsVO.OPERATION_FLAG_INSERT.equals(mailbagVO.getOperationalFlag()) 
																		 && !mailArrivalVO.isFlightChange())){      
															 
															 throw new DuplicateMailBagsException(
																		DuplicateMailBagsException.MAILBAG_ALREADY_ARRIVAL_INSAMEFLIGHT_DIFF_CNTAINER_EXCEPTION,
																		new Object[] { mailbagVO.getMailbagId(), mailbaghistoryvo.getContainerNumber()});
														 }       
														 
													 }


												 }
												 //Added as part of bug ICRD-141447 starts
												 //Commented as part of bug ICRD-145435 by A-5526 starts
												/* if(MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mailbaghistoryvo.getMailStatus())){
													 if((MailConstantsVO.OPERATION_FLAG_UPDATE.equals(mailbagVO.getOperationalFlag()) || MailConstantsVO.OPERATION_FLAG_INSERT.equals(mailbagVO.getOperationalFlag()))
															 && POST_DATA_CAPTURE.equals(mailbagVO.getActionMode()) ){
													 throw new MailTrackingBusinessException(
															 "mail.operations.mailarrival.mailbagalreadydelivered",
																new Object[] { mailbagVO.getMailbagId() });
													 }
												 } */
												//Commented as part of bug ICRD-145435 by A-5526 ends
												 //Added as part of bug ICRD-141447 ends
											 }
											 }
										 }
									 }
										
									//added by A-5945 for ICRD-129779 ends
									 if (mailbagVO.getOperationalFlag() != null	 ){          
										boolean isNew = false;
										Mailbag mailbag = null;
										try {
											mailbag = Mailbag.find(constructMailbagPK(mailbagVO));
										} catch (FinderException exception) {
											isNew = true;
										}


										if(mailbag!=null){
											if(mailbagVO.getMailCompanyCode()!=null ){
												if( (!mailbagVO.getMailCompanyCode().equals(mailbag.getMailCompanyCode())) ){
													//Mailcompanycode already present, Updating the same
												MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(MailbagAuditVO.MOD_NAM,MailbagAuditVO.SUB_MOD_OPERATIONS,MailbagAuditVO.ENTITY_MAILBAG);
												mailbagAuditVO = (MailbagAuditVO)AuditUtils.populateAuditDetails(mailbagAuditVO,mailbag,false);
												mailbag.setMailCompanyCode(mailbagVO.getMailCompanyCode());
												mailbagAuditVO = (MailbagAuditVO)AuditUtils.populateAuditDetails(mailbagAuditVO,mailbag,false);
												mailbagAuditVO.setActionCode("UPDATEMAL");
												mailbagAuditVO.setCompanyCode(mailbagVO.getCompanyCode());
												mailbagAuditVO.setMailbagId(mailbagVO.getMailbagId());
												mailbagAuditVO.setDsn(mailbagVO.getDespatchSerialNumber());
												mailbagAuditVO.setOriginExchangeOffice(mailbagVO.getOoe());
												mailbagAuditVO.setDestinationExchangeOffice(mailbagVO.getDoe());
												mailbagAuditVO.setMailSubclass(mailbagVO.getMailSubclass());
												mailbagAuditVO.setMailCategoryCode(mailbagVO.getMailCategoryCode());
												mailbagAuditVO.setYear(mailbagVO.getYear());
												LogonAttributes logonAttributes=ContextUtils.getSecurityContext().getLogonAttributesVO();
												mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId());
												StringBuffer additionalInfo = new StringBuffer();
												additionalInfo.append("Company code ").append("updated for mailbag	").append(mailbagVO.getMailbagId());
												mailbagAuditVO.setAdditionalInformation(additionalInfo.toString());
												AuditUtils.performAudit(mailbagAuditVO);
												}
												else {
													mailbag.setMailCompanyCode(mailbagVO.getMailCompanyCode());
												}
											}
										}
										// incase client ignores
										// imp for received resdit
										mailbagVO.setCarrierCode(mailArrivalVO.getFlightCarrierCode());
										mailbagVO.setMailSource(mailArrivalVO.getMailSource());
										mailbagVO.setMailbagDataSource(mailArrivalVO.getMailDataSource());
										/*if(mailbagVO.getMailRemarks()==null || mailbagVO.getMailRemarks()==""){
											mailbagVO.setMailRemarks(mailbag.getMailRemarks());
											}*/
										//this method throws Exception if mailbags are duplicate
										MailbagVO currentMailbagVO = checkForDuplicateArrival(mailbagVO, isScanned);


										String dsnPK = constructDSNPKForMailbag(mailbagVO);
										DSNVO dsnVO = dsnMap.get(dsnPK);

										if (dsnVO != null) {
											if (isScanned && mailbagVO.getErrorType() != null) {
												// add to error collection
												duplicateMailbags.add(mailbagVO);
												/*
												 * If scanmode then only add if not duplicate
												 * else this block throws exception Remove any
												 * bags already present in dsnVO Otherwise they
												 * will go for save
												 */
												removeDuplicatebagsWeight(containerDetailsVO,
														dsnVO, mailbagVO);
												continue;
											}
											// log.log(Log.FINEST, "Mailbag " + mailbagVO);
											dsnVO.getMailbags().add(mailbagVO);
										}
										
										String poaCode=null; //Added by A-8164 for ICRD-342541
										if(mailbag!=null && mailbag.getPaCode()!=null){
											poaCode=mailbag.getPaCode();
										}
										else{
											OfficeOfExchangeVO originOfficeOfExchangeVO;
											originOfficeOfExchangeVO=new MailController().validateOfficeOfExchange(companyCode, mailbagVO.getOoe());
											poaCode=originOfficeOfExchangeVO.getPoaCode();
										}

										//arrival marked but not transferred
										if (MailConstantsVO.FLAG_YES.equals(mailbagVO.getArrivedFlag()) &&
												!MailConstantsVO.FLAG_YES.equals(mailbagVO.getTransferFlag())) {

											String latestStatus = null;
											if(currentMailbagVO != null) {
												latestStatus = currentMailbagVO.getLatestStatus();
											}

											/*
											 * This block for checking whether delivery needs to be done or
											 * if the mailbags are newly arrived ones.
											 * If newly arrived ones, then delivery also would not have happened and latest
											 * port in db would not be the current port
											 */

											//new arrival
											if(currentMailbagVO == null ||
                                                    !currentMailbagVO.getScannedPort().equals(mailbagVO.getScannedPort())||!MailConstantsVO.FLAG_YES.equals(mailbagVO.getDeliveredFlag())) {//changed the check by A-8893 for IASCB-53375
												newArrivedMailbags.add(mailbagVO);
											}

											//not already delivered
											//but to be delivered
											if (!MailConstantsVO.MAIL_STATUS_DELIVERED.equals(latestStatus) &&
													MailConstantsVO.FLAG_YES.equals(mailbagVO.getDeliveredFlag())) {

												if (deliveredPort.equals(mailbagVO.getDestination())?true:isValidDeliveryAirport(mailbagVO.getDoe(),
														companyCode, deliveredPort, null,MailConstantsVO.RESDIT_DELIVERED,poaCode,mailbagVO.getConsignmentDate())) {
													//new delivery
													if(deliveredMailBagsForMonitorSLA != null) {
														deliveredMailBagsForMonitorSLA.add(mailbagVO);
													}
													//already arrived
													if(currentMailbagVO != null &&
															currentMailbagVO.getScannedPort().equals(mailbagVO.getScannedPort())) {
														deliveredArrivedMails.add(mailbagVO);
													}

												} else {
													if(deliveryChkNeeded) {
														/*
														 * For scan mode, if delivery possible set
														 * the err in vo only
														 */
														mailbagVO.setErrorType(MailConstantsVO.EXCEPT_FATAL);
														mailbagVO.setErrorDescription(MailConstantsVO.INCORRECT_DLV);
														incorrectlyDeliveredMails.add(mailbagVO);
													}
												}
											}
										}

										if(("U").equals(mailbagVO.getOperationalFlag()) && mailArrivalVO.isDeliveryNeeded())
										{
												if(!MailConstantsVO.FLAG_YES.equals(mailbagVO.getDeliveredFlag())) {
											if (deliveredPort.equals(mailbagVO.getDestination())?true:isValidDeliveryAirport(mailbagVO.getDoe(),
													companyCode, deliveredPort, null,MailConstantsVO.RESDIT_DELIVERED,poaCode,mailbagVO.getConsignmentDate())) {
												//new delivery
												if(deliveredMailBagsForMonitorSLA != null) {
													deliveredMailBagsForMonitorSLA.add(mailbagVO);
												}
												//already arrived
												if(currentMailbagVO != null &&
														currentMailbagVO.getScannedPort().equals(mailbagVO.getScannedPort())) {
													deliveredArrivedMails.add(mailbagVO);
												}

											} else {
												incorrectlyDeliveredMails.add(mailbagVO);

											}

										}

										}
									}
								}


								if(duplicateMailbags.size() > 0) {
									// remove all duplicate mailbags from the collection in
									// containerdetails
									// so that they will not get saved in the SEG and MST tables
									mailbags.removeAll(duplicateMailbags);
									/*
									 * correct the piece&weight of uld and dsn
									 */
									removeArrivalExceptionPieces(containerDetailsVO,
											duplicateMailbags);
									// add all duplicate mailbags to give to client
									expMailbags.addAll(duplicateMailbags);
								}


								if (incorrectlyDeliveredMails.size() > 0) {
									if (isScanned) {
										expMailbags.addAll(incorrectlyDeliveredMails);
										// remove those with probs so that the others alone
										// will get saved
										mailbags.removeAll(incorrectlyDeliveredMails);

										removeArrivalExceptionPieces(containerDetailsVO,
												incorrectlyDeliveredMails);
									} else {
										throw new MailbagIncorrectlyDeliveredException(
												MailbagIncorrectlyDeliveredException.MAILBAG_INCORRECTLY_DELIVERED,
												new String[] { deliveredPort });
									}
								}

							}

							Collection<DespatchDetailsVO> incorrectlyDeliveredDespatches = new ArrayList<DespatchDetailsVO>();
							Collection<DespatchDetailsVO> despatches = containerDetailsVO
									.getDesptachDetailsVOs();
							if (despatches != null && despatches.size() > 0) {
								for (DespatchDetailsVO despatchDetailsVO : despatches) {
									if (despatchDetailsVO.getOperationalFlag() != null) {

										// incase client ignores
										despatchDetailsVO.setCarrierCode(mailArrivalVO.getFlightCarrierCode());

										if (despatchDetailsVO.getDeliveredBags() != despatchDetailsVO
												.getPrevDeliveredBags()
												|| (despatchDetailsVO.getDeliveredWeight() != despatchDetailsVO
														.getPrevDeliveredWeight())) {
											OfficeOfExchangeVO originOfficeOfExchangeVO; //Added by A-8164 for ICRD-342541 
											originOfficeOfExchangeVO=new MailController().validateOfficeOfExchange(companyCode, despatchDetailsVO
													.getOriginOfficeOfExchange());
											String poaCode=originOfficeOfExchangeVO.getPoaCode();
											if (deliveredPort.equals(despatchDetailsVO.getDestination())?false:!isValidDeliveryAirport(despatchDetailsVO
													.getDestinationOfficeOfExchange(),
													companyCode, deliveredPort, null,MailConstantsVO.RESDIT_DELIVERED,poaCode,despatchDetailsVO.getConsignmentDate())) {
												incorrectlyDeliveredDespatches
														.add(despatchDetailsVO);
											}
											/*
											 * Added By Karthick V as the part of the NCA
											 * Mail Tracking CR Add all the Despatches For
											 * the Delivery.. Again these has to be Removed
											 * From the Inventory .
											 *
											 *
											 */
											else {
												log.log(Log.FINE, "Valid Delivery Port");
												if (despatchDetailsForInventoryRemoval != null) {

													/*
													 * Added to avoid entering during deliver all mail
													 * Only if Prev Received Bags are greater than zero
													 *
													 */
													if(despatchDetailsVO.getPrevReceivedBags()>0 && (despatchDetailsVO.getPrevReceivedBags()>
													   despatchDetailsVO.getPrevDeliveredBags())){
														log.log(Log.FINE, "Valid Delivery Port and despatches collected ");
															despatchDetailsForInventoryRemoval
																	.add(despatchDetailsVO);
												     }else{
												    	 log.log(Log.FINE, "No ----------------despatches collected ");
												     }
												}

											}

										}
									}
								}
								if (incorrectlyDeliveredDespatches.size() > 0) {
									throw new MailbagIncorrectlyDeliveredException(
											MailbagIncorrectlyDeliveredException.DESPATCH_INCORRECTLY_DELIVERED,
											new String[] { deliveredPort });
								}
							}
						}
					}

					containers.removeAll(containersToRem);
					}

					log.log(Log.FINE, "container  ", containers);
					/*
					 * For scanmode, remove all the dsnmap entries which are PLT enabled and
					 * zero size. In normal this'll throw exception
					 */
					Collection<String> dsnsToRem = new ArrayList<String>();
					if (dsnMap.size() > 0) {
						for (Map.Entry<String, DSNVO> dsnEntry : dsnMap.entrySet()) {
							DSNVO dsnVO = dsnMap.get(dsnEntry.getKey());
							if (MailConstantsVO.FLAG_YES.equals(dsnVO.getPltEnableFlag())) {
								if (dsnVO.getMailbags().size() == 0) {
									dsnsToRem.add(dsnEntry.getKey());
								}
							}
						}
						for (String dsnPK : dsnsToRem) {
							dsnMap.remove(dsnPK);
						}
					}


					log.exiting(ENTITY, "checkForIncorrectlyDeliveredMailbags");
					return dsnMap;
				}

				/**
				 * TODO Purpose Oct 9, 2006, a-1739
				 *
				 * @param mailArrivalVO
				 * @return
				 */
				private ScannedMailDetailsVO constructScannedMailDetailsForFlight(
						MailArrivalVO mailArrivalVO) {
					ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO();
					scannedMailDetailsVO.setCompanyCode(mailArrivalVO.getCompanyCode());
					scannedMailDetailsVO.setCarrierId(mailArrivalVO.getCarrierId());
					scannedMailDetailsVO.setFlightNumber(mailArrivalVO.getFlightNumber());
					scannedMailDetailsVO.setFlightSequenceNumber(mailArrivalVO
							.getFlightSequenceNumber());
					scannedMailDetailsVO.setLegSerialNumber(mailArrivalVO
							.getLegSerialNumber());
					scannedMailDetailsVO.setFlightDate(mailArrivalVO.getArrivalDate());
					scannedMailDetailsVO.setCarrierCode(mailArrivalVO
							.getFlightCarrierCode());
					return scannedMailDetailsVO;
				}

				/**
				 * TODO Purpose Dec 7, 2006, a-1739
				 *
				 * @param mailArrivalVO
				 * @throws SystemException
				 * @throws FlightClosedException
				 */

				private void checkFlightClosureForArrival(MailArrivalVO mailArrivalVO)
						throws SystemException, FlightClosedException {
					AssignedFlightPK inbFlightPK = constructInbFlightPK(mailArrivalVO);

					if (checkInboundFlightClosed(inbFlightPK)) {


						//Added for HHT error log by A-5526
						StringBuilder errorString = new StringBuilder();
						errorString
								.append("checkFlightClosureForArrival scanned info -");

						if (mailArrivalVO.getFlightNumber() != null) {
							errorString.append(mailArrivalVO
									.getFlightNumber());
							errorString.append(" ");
							errorString
							.append(" scanned info ends.Closed flight info begins ");
						}




						if (inbFlightPK != null) {
							if (inbFlightPK.getFlightNumber() != null) {
								errorString.append(
										inbFlightPK.getFlightNumber())
										.append("-");
							}

								errorString.append(
										inbFlightPK.getFlightSequenceNumber())
										.append("-");

								if (inbFlightPK.getAirportCode()!= null) {
									errorString.append(
											inbFlightPK.getAirportCode())
											.append("-");
								}

						}

						errorString
								.append("- MailHHTBusniessException.FLIGHT_CLOSED_EXCEPTION");
						String finalerrorString = errorString.toString();

						errPgExceptionLogger.info(finalerrorString);

						//Added for HHT error log by A-5526
						throw new FlightClosedException(
								FlightClosedException.FLIGHT_STATUS_CLOSED, new String[] {
										new StringBuilder().append(
												mailArrivalVO.getFlightCarrierCode())
												.append(" ").append(
														mailArrivalVO.getFlightNumber())
												.toString(),mailArrivalVO.getArrivalDate()!=null?
										mailArrivalVO.getArrivalDate()
												.toDisplayDateOnlyFormat():"" });
					}
				}

				/**
				 * A-1739
				 *
				 * @param inboundFlightPK2
				 * @param inboundFlightVO
				 * @return
				 * @throws SystemException
				 */
				private AssignedFlight findOrCreateInboundFlight(
						AssignedFlightPK inboundFlightPK, AssignedFlightVO inboundFlightVO)
						throws SystemException {
					AssignedFlight inboundFlight = null;
					try {
						inboundFlight = AssignedFlight.find(inboundFlightPK);
					} catch (FinderException exception) {
						inboundFlight = new AssignedFlight(inboundFlightVO);
					}
					return inboundFlight;
				}

				/**
				 * @author a-1936
				 * Added By Karthick  V
				 * This method is used to Update the Flight  with the Destiantion Details that could be used for
				 * the Reassign Dsns From  Destination..
				 * @param despatchDetailsVos
				 * @return
				 * @throws SystemException
				 */
				private Collection<DespatchDetailsVO> updateDespacthesForInventoryRemoval(Collection<DespatchDetailsVO> despatchDetailsVos)
				  throws SystemException{
				 DespatchDetailsVO despatch = null;
				 Collection<DespatchDetailsVO>  despatchesForRemoval = new ArrayList<DespatchDetailsVO>();
					for(DespatchDetailsVO desp:despatchDetailsVos){
						despatch = new DespatchDetailsVO();
						BeanHelper.copyProperties(despatch, desp);
						despatch.setFlightNumber(String.valueOf(MailConstantsVO.DESTN_FLT));
						despatch.setContainerNumber(despatch.getContainerForInventory());
						String containerType = despatch.getContainerType();
						despatch.setContainerType(despatch.getContainerTypeAtAirport());
						if(despatch.getContainerType()==null || despatch.getContainerType().trim().length()== 0 ){
							despatch.setContainerType(containerType);
						}
						despatch.setFlightDate(null);
						despatch.setFlightNumber(String.valueOf(MailConstantsVO.DESTN_FLT));
						despatch.setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
						despatch.setLegSerialNumber(MailConstantsVO.DESTN_FLT);
						despatch.setSegmentSerialNumber(MailConstantsVO.DESTN_FLT);
						despatch.setPou(null);
						despatch.setDestination(null);
						despatch.setAcceptedBags(despatch.getDeliveredBags()-despatch.getPrevDeliveredBags());
						//despatch.setAcceptedWeight(despatch.getDeliveredWeight()-despatch.getPrevDeliveredWeight());
						
							try {
								despatch.setAcceptedWeight(Measure.subtractMeasureValues(despatch.getDeliveredWeight(), despatch.getPrevDeliveredWeight()));
							} catch (UnitException e) {
								// TODO Auto-generated catch block
								throw new SystemException(e.getErrorCode());
							}//added by A-7371
						
						despatchesForRemoval.add(despatch);
						}
					log.log(Log.FINE, "THE DESPATCHES FOR REMOVAL ", despatchesForRemoval);
					return despatchesForRemoval;
				}
				/**
			     * @author a-1936
			     * Added  By  Karthick V as the part of the NCA Mail Tracking CR ..
			     * Reset the Flight Pks to Carrier Needed for the Reassign ..
			     * @param mailBags
			     * @param carrierID
			     * @param airportCode
			     */
			    private Collection<MailbagVO> updateMailBagForInventoryRemoval(Collection<MailbagVO> mailBags
			        ) {
			    	log.entering("Mail Arrival", "updateMailBagForInventoryRemoval");
			    	Collection<MailbagVO> mailBagsForRemoval = new ArrayList<MailbagVO>();
			    	MailbagVO mailBagVO=null;
			        for(MailbagVO mailBag:mailBags) {
			        	 mailBagVO = new MailbagVO();
			        	 mailBagVO.setCarrierId(mailBag.getCarrierId());
			        	 mailBagVO.setCarrierCode(mailBag.getCarrierCode());
			        	 if(MailConstantsVO.ULD_TYPE.equals(mailBag.getContainerType())){
			        	 mailBagVO.setContainerNumber(mailBag.getUldNumber());
			        	 }
			        	 else{
			        	 mailBagVO.setContainerNumber(mailBag.getInventoryContainer());
			        	 }
						 mailBagVO.setInventoryContainerType(mailBag.getInventoryContainerType());
			        	 mailBagVO.setFinalDestination(null);
			        	 mailBagVO.setFlightNumber(String.valueOf(MailConstantsVO.DESTN_FLT));
			        	 mailBagVO.setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
			        	 mailBagVO.setSegmentSerialNumber(MailConstantsVO.DESTN_FLT);
			        	 mailBagVO.setPou(null);
			        	 mailBagVO.setCompanyCode(mailBag.getCompanyCode());
			     		 mailBagVO.setMailbagId(mailBag.getMailbagId());
			     		 mailBagVO.setOoe(mailBag.getOoe());
			        	 mailBagVO.setDoe(mailBag.getDoe());
			        	 mailBagVO.setMailCategoryCode(mailBag.getMailCategoryCode());
			        	 mailBagVO.setMailClass(mailBag.getMailClass());
			     		 mailBagVO.setMailSubclass(mailBag.getMailSubclass());
			     		 mailBagVO.setYear(mailBag.getYear());
			     		 mailBagVO.setDespatchSerialNumber(mailBag.getDespatchSerialNumber());
			     		 mailBagVO.setReceptacleSerialNumber(mailBag.getReceptacleSerialNumber());
			     		 mailBagVO.setHighestNumberedReceptacle(mailBag.getHighestNumberedReceptacle());
			     		 mailBagVO.setRegisteredOrInsuredIndicator(mailBag.getRegisteredOrInsuredIndicator());
			     		 mailBagVO.setWeight(mailBag.getWeight());
			     		 mailBagVO.setDamageFlag(mailBag.getDamageFlag());
			     		 mailBagVO.setArrivedFlag(mailBag.getArrivedFlag());
			     		 mailBagVO.setDeliveredFlag(mailBag.getDeliveredFlag());
			     		 mailBagVO.setTransferFlag(mailBag.getTransferFlag());
			     		 mailBagVO.setScannedPort(mailBag.getPou());
			     		 mailBagsForRemoval.add(mailBagVO);
			        }
			        log.exiting("Mail Arrival", "updateMailBagForInventoryRemoval");
			        return mailBagsForRemoval;

			    }

			    /**
				 * @author a-2553
				 * Added By Paulson
				 * This method is used to Update the Flight  with the Destiantion Details that could be used for
				 * the Reassign containers From  Destination..
				 * @param deliveredContainers
				 * @return
				 * @throws SystemException
				 */
				private Collection<ContainerVO> updateContainersForInventoryRemoval(Collection<ContainerDetailsVO> deliveredContainers)
				  throws SystemException{
				 ContainerVO containerVO = null;
				 Collection<ContainerVO>  containerVOs = new ArrayList<ContainerVO>();
					for(ContainerDetailsVO cntDetVO : deliveredContainers){
						containerVO = new ContainerVO();
						containerVO.setFlightNumber(String.valueOf(MailConstantsVO.DESTN_FLT));
						containerVO.setFlightDate(null);
						containerVO.setFlightSequenceNumber(MailConstantsVO.DESTN_FLT);
						containerVO.setLegSerialNumber(MailConstantsVO.DESTN_FLT);
						containerVO.setSegmentSerialNumber(MailConstantsVO.DESTN_FLT);
						containerVO.setPou(null);
						containerVO.setContainerNumber(cntDetVO.getContainerNumber());
						containerVO.setCompanyCode(cntDetVO.getCompanyCode());
						containerVO.setCarrierCode(cntDetVO.getCarrierCode());
						containerVO.setAssignedPort(cntDetVO.getPol());
						containerVO.setType(cntDetVO.getContainerType());
						containerVO.setFinalDestination(cntDetVO.getDestination());
						containerVO.setRemarks(cntDetVO.getRemarks());
						containerVO.setReassignFlag(cntDetVO.isReassignFlag());
						containerVO.setAssignedUser(cntDetVO.getAssignedUser());
						containerVO.setAssignedDate(cntDetVO.getAssignmentDate());
						containerVO.setPaBuiltFlag(cntDetVO.getPaBuiltFlag());
						containerVO.setArrivedStatus(cntDetVO.getArrivedStatus());
						containerVO.setPaBuiltOpenedFlag(cntDetVO.getArrivedStatus());
						containerVO.setAcceptanceFlag(cntDetVO.getAcceptedFlag());
						containerVO.setOwnAirlineCode(cntDetVO.getOwnAirlineCode());
						containerVOs.add(containerVO);
						}
					log.log(Log.FINE, "THE containerVOs FOR REMOVAL ", containerVOs);
					return containerVOs;
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
					assignedFlightSegPK.setCompanyCode(containerDetailsVO.getCompanyCode());
					assignedFlightSegPK.setCarrierId(containerDetailsVO.getCarrierId());
					assignedFlightSegPK.setFlightNumber(containerDetailsVO
							.getFlightNumber());
					assignedFlightSegPK.setFlightSequenceNumber(containerDetailsVO
							.getFlightSequenceNumber());
					assignedFlightSegPK.setSegmentSerialNumber(containerDetailsVO
							.getSegmentSerialNumber());
					return assignedFlightSegPK;
				}

				/**
				 * @author A-1739
				 * @param containerDetailsVO
				 * @param mailArrivalVO
				 * @return
				 * @throws SystemException
				 * @throws InvalidFlightSegmentException
				 */
				private AssignedFlightSegmentPK constructAssignedFlightSegmentPKForArrival(
						ContainerDetailsVO containerDetailsVO, MailArrivalVO mailArrivalVO)
						throws SystemException, InvalidFlightSegmentException {

					int segmentSerialNumber = findFlightSegment(mailArrivalVO
							.getCompanyCode(), mailArrivalVO.getCarrierId(), mailArrivalVO
							.getFlightNumber(), mailArrivalVO.getFlightSequenceNumber(),
							containerDetailsVO.getPol(), mailArrivalVO.getAirportCode());
					AssignedFlightSegmentPK assignedFlightSegmentPK = new AssignedFlightSegmentPK();
					assignedFlightSegmentPK.setCompanyCode(mailArrivalVO.getCompanyCode());
					assignedFlightSegmentPK.setCarrierId(mailArrivalVO.getCarrierId());
					assignedFlightSegmentPK
							.setFlightNumber(mailArrivalVO.getFlightNumber());
					assignedFlightSegmentPK.setFlightSequenceNumber(mailArrivalVO
							.getFlightSequenceNumber());
					assignedFlightSegmentPK.setSegmentSerialNumber(segmentSerialNumber);

					try {
						AssignedFlightSegment.find(assignedFlightSegmentPK);
					} catch (FinderException ex) {
						AssignedFlightSegmentVO segmentVO = constructAssignedFlightSegmentVOForArrival(
								containerDetailsVO, mailArrivalVO, segmentSerialNumber);
						log.log(Log.FINE, "asgFLightSeg not found  creating--", segmentVO);
						new AssignedFlightSegment(segmentVO);
					}
					return assignedFlightSegmentPK;
				}

				/**
				 * @author A-1739 This method is used to find the FlightSegment
				 * @param companyCode
				 * @param carrierId
				 * @param flightNumber
				 * @param flightSequenceNumber
				 * @param pol
				 * @param pou
				 * @return
				 * @throws SystemException
				 * @throws InvalidFlightSegmentException
				 */
				private int findFlightSegment(String companyCode, int carrierId,
						String flightNumber, long flightSequenceNumber, String pol,
						String pou) throws SystemException, InvalidFlightSegmentException {
					int segmentSerialNum = 0;
					log.entering(ENTITY, "findFlightSegment");
					Collection<FlightSegmentSummaryVO> flightSegments = null;

					flightSegments = Proxy.getInstance().get(FlightOperationsProxy.class).findFlightSegments(
							companyCode, carrierId, flightNumber, flightSequenceNumber);

					String containerSegment = new StringBuilder().append(pol).append(pou)
							.toString();
					String flightSegment = null;

					for (FlightSegmentSummaryVO segmentSummaryVO : flightSegments) {
						flightSegment = new StringBuilder().append(
								segmentSummaryVO.getSegmentOrigin()).append(
								segmentSummaryVO.getSegmentDestination()).toString();
						log.log(Log.FINEST, "from proxy -- >", flightSegment);
						log.log(Log.FINEST, "from container  -- >", containerSegment);
						if (flightSegment.equals(containerSegment)) {
							segmentSerialNum = segmentSummaryVO.getSegmentSerialNumber();
						}
					}
					if (segmentSerialNum == 0) {
						throw new InvalidFlightSegmentException(
								new String[] { containerSegment });
					}
					log.exiting(ENTITY, "findFlightSegment");
					return segmentSerialNum;
				}

				/**
				 * A-1739 This method checks if there is already a container assigned at the
				 * other port If it accepted If it is in a closed flight
				 *
				 * @param containerDetailsVO
				 * @throws SystemException
				 * @throws ContainerAssignmentException
				 */
				private void checkContainerAssignmentAtPol(
						ContainerDetailsVO containerDetailsVO) throws SystemException,
						ContainerAssignmentException {
					log.entering("AssignedFlightSegment", "checkContainerAssignmentAtPol");
					ContainerAssignmentVO containerAsgVO = Container
							.findContainerAssignment(containerDetailsVO.getCompanyCode(),
									containerDetailsVO.getContainerNumber(),
									containerDetailsVO.getPol());
					if (containerAsgVO != null) {
						if (MailConstantsVO.FLAG_YES.equals(containerAsgVO.getAcceptanceFlag())) {
							if (containerAsgVO.getFlightSequenceNumber() == MailConstantsVO.DESTN_FLT) {
								throw new ContainerAssignmentException(
										ContainerAssignmentException.DESTN_ASSIGNED,
										new Object[] { containerAsgVO.getContainerNumber(),
												containerDetailsVO.getPol() });
							}else if(!checkFlightClosedForOperations(containerAsgVO)) {
								throw new ContainerAssignmentException(
										ContainerAssignmentException.FLIGHT_STATUS_POL_OPEN,
										new Object[] {
												containerAsgVO.getContainerNumber(),
												new StringBuilder(containerAsgVO
														.getCarrierCode())
														.append(" ")
														.append(
																containerAsgVO
																		.getFlightNumber())
														.append(" ")
														.append(
																containerAsgVO
																		.getFlightDate()
																		.toDisplayDateOnlyFormat()),
												containerDetailsVO.getPol() });
							}
						}
					}
					log.exiting("AssignedFlightSegment", "checkContainerAssignmentAtPol");
				}

				/**
				 * A-1739
				 *
				 * @param containerAsgVO
				 * @return
				 * @throws SystemException
				 */
				private boolean checkFlightClosedForOperations(
						ContainerAssignmentVO containerAsgVO) throws SystemException {

					AssignedFlightPK asgFlightPK = constructAssignedFlightPKForULD(containerAsgVO);
					return checkFlightClosed(asgFlightPK);
				}

				/**
				 * A-1739
				 *
				 * @param containerAsgVO
				 * @return
				 */
				private AssignedFlightPK constructAssignedFlightPKForULD(
						ContainerAssignmentVO containerAsgVO) {

					AssignedFlightPK assignedFlightPK = new AssignedFlightPK();
					assignedFlightPK.setCompanyCode(containerAsgVO.getCompanyCode());
					assignedFlightPK.setCarrierId(containerAsgVO.getCarrierId());
					assignedFlightPK.setFlightNumber(containerAsgVO.getFlightNumber());
					assignedFlightPK.setFlightSequenceNumber(containerAsgVO
							.getFlightSequenceNumber());
					assignedFlightPK.setAirportCode(containerAsgVO.getAirportCode());
					assignedFlightPK
							.setLegSerialNumber(containerAsgVO.getLegSerialNumber());
					return assignedFlightPK;
				}

				private boolean checkFlightClosed(AssignedFlightPK assignedFlightPK)
						throws SystemException {
					log.entering(ENTITY, "isFlightClosedForOperations");

					AssignedFlight assignedFlight = null;
					try {
						assignedFlight = AssignedFlight.find(assignedFlightPK);
					} catch (FinderException ex) {
						throw new SystemException(ex.getMessage(), ex);
					}

					boolean isFlightClosed = false;
					if (MailConstantsVO.FLIGHT_STATUS_CLOSED.equals(assignedFlight
							.getExportClosingFlag())) {
						isFlightClosed = true;
					}
					log.log(Log.FINE, "isFlightClosed ", isFlightClosed);
					log.exiting(ENTITY, "isFlightClosedForOperations");
					return isFlightClosed;
				}


				private void updateMailbagVOsWithErr(Collection<MailbagVO> mailDetails,
						String errMsg) {
					log.entering(ENTITY, "updateMailbagVOsWithErr");
					if (mailDetails != null && mailDetails.size() > 0) {
						for (MailbagVO mailbagVO : mailDetails) {
							mailbagVO.setErrorType(MailConstantsVO.EXCEPT_FATAL);
							mailbagVO.setErrorDescription(errMsg);
						}
					}
					log.exiting(ENTITY, "updateMailbagVOsWithErr");

				}

				/**
				 * A-1739
				 *
				 * @param dsnVO
				 * @return
				 */
				private String constructDSNPKFrmDSNVO(DSNVO dsnVO) {
					StringBuilder dsnPK = new StringBuilder();
					dsnPK.append(dsnVO.getCompanyCode());
					dsnPK.append(dsnVO.getDsn());
					dsnPK.append(dsnVO.getOriginExchangeOffice());
					dsnPK
							.append(dsnVO
									.getDestinationExchangeOffice());
					dsnPK.append(dsnVO.getMailSubclass());
					dsnPK.append(dsnVO.getMailCategoryCode());
					dsnPK.append(dsnVO.getYear());
					return dsnPK.toString();
				}

				/**
				 * @author A-1739
				 * @param oldDSNVO
				 * @return
				 * @throws SystemException
				 */
				private DSNVO copyDSNVO(DSNVO oldDSNVO) throws SystemException {
					DSNVO dsnVO = new DSNVO();
					BeanHelper.copyProperties(dsnVO, oldDSNVO);
					dsnVO.setBags(0);
					//dsnVO.setWeight(0);
					dsnVO.setWeight(new Measure(UnitConstants.MAIL_WGT,0.0));//added by A-7371
					return dsnVO;
				}


				/**
				 * A-1739
				 *
				 * @param dsnMstVO
				 * @param dsnVO
				 * @throws SystemException 
				 */
				private void updateDSNAtAirportVOForArrival(DSNVO dsnMstVO, DSNVO dsnVO) throws SystemException {
					Collection<DSNAtAirportVO> dsnAtAirports = dsnMstVO.getDsnAtAirports();
					for (DSNAtAirportVO dsnAtAirportVO : dsnAtAirports) {
						dsnAtAirportVO.setTotalBagsArrived(dsnAtAirportVO
								.getTotalBagsArrived()
								+ dsnVO.getReceivedBags() - dsnVO.getPrevReceivedBags());
						/*dsnAtAirportVO
								.setTotalWeightArrived(dsnAtAirportVO
										.getTotalWeightArrived()
										+ dsnVO.getReceivedWeight()
										- dsnVO.getPrevReceivedWeight());*/
						Measure dsnRecWt;
						try {
							//Added by A-8164 for ICRD-338891
							if(dsnVO.getPrevReceivedWeight()!=null&&dsnVO.getReceivedWeight()!=null
									&&!dsnVO.getPrevReceivedWeight().getDisplayUnit().equals(dsnVO.getReceivedWeight().getDisplayUnit())){
							Measure convertedWeight=
							//		new Measure(UnitConstants.MAIL_WGT,dsnVO.getPrevReceivedWeight().getSystemValue(),0,dsnVO.getPrevReceivedWeight().getDisplayUnit());//To make display units of both measure same
							

							new Measure(UnitConstants.MAIL_WGT,dsnVO.getPrevReceivedWeight().getSystemValue(),0,dsnVO.getReceivedWeight().getDisplayUnit());//To make display units of both measure same



							dsnRecWt = Measure.subtractMeasureValues(dsnVO.getReceivedWeight(), convertedWeight); 
							}
							else{
								dsnRecWt = Measure.subtractMeasureValues(dsnVO.getReceivedWeight(), dsnVO.getPrevReceivedWeight());
							}
							try {  
								dsnAtAirportVO
								.setTotalWeightArrived(Measure.addMeasureValues(dsnAtAirportVO
												.getTotalWeightArrived(), dsnRecWt));
							} catch (UnitException e) {
								// TODO Auto-generated catch block
								throw new SystemException(e.getErrorCode());
							}//added by A-7371
						} catch (UnitException e) {
							// TODO Auto-generated catch block
							throw new SystemException(e.getErrorCode());
						}//added by A-7371
						

						dsnAtAirportVO.setTotalBagsDelivered(dsnAtAirportVO
								.getTotalBagsDelivered()
								+ dsnVO.getDeliveredBags() - dsnVO.getPrevDeliveredBags());
						/*dsnAtAirportVO.setTotalWeightDelivered(dsnAtAirportVO
								.getTotalWeightDelivered()
								+ dsnVO.getDeliveredWeight()
								- dsnVO.getPrevDeliveredWeight());*/
						Measure dsnDelWt;
						try {
							dsnDelWt = Measure.subtractMeasureValues(dsnVO.getDeliveredWeight(), dsnVO.getPrevDeliveredWeight());
							try {
								dsnAtAirportVO.setTotalWeightDelivered(Measure.addMeasureValues(dsnAtAirportVO
										.getTotalWeightDelivered(), dsnDelWt));
							} catch (UnitException e) {
								// TODO Auto-generated catch block
								throw new SystemException(e.getErrorCode());
							}//added by A-7371
						} catch (UnitException e) {
							// TODO Auto-generated catch block
							throw new SystemException(e.getErrorCode());
						}
						
						
					}
				}


				private MailbagPK constructMailbagPK(MailbagVO mailbagVO) {
					MailbagPK mailbagPK = new MailbagPK();
					mailbagPK.setCompanyCode(mailbagVO.getCompanyCode());
					mailbagPK.setMailSequenceNumber(mailbagVO.getMailSequenceNumber()>0?mailbagVO.getMailSequenceNumber():findMailSequenceNumber(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode()));
					return mailbagPK;
				}


				/**
				 * This method throws exception in non scan mode else it silently returns an
				 * errocode alon ein the vo Oct 9, 2006, a-1739
				 *
				 * @param mailbagVO
				 * @param isScanned
				 * @return
				 * @throws DuplicateMailBagsException
				 * @throws SystemException
				 */
				private MailbagVO checkForDuplicateArrival(MailbagVO mailbagVO, boolean isScanned)
						throws DuplicateMailBagsException, SystemException {
					boolean isNew = false;
					Mailbag mailbag = null;
					try {
						mailbag = Mailbag.find(constructMailbagPK(mailbagVO));
					} catch (FinderException exception) {
						isNew = true;
					}

					String latestStatus = null;
					if(mailbag != null) {
						latestStatus = mailbag.getLatestStatus();
					}
					if (OPERATION_FLAG_INSERT.equals(mailbagVO.getOperationalFlag())) {
						if (!isNew && !(MailConstantsVO.MAIL_STATUS_NEW.equals(mailbag.getLatestStatus())) 
								&& (MailConstantsVO.OPERATION_INBOUND.equals(mailbag
										.getOperationalStatus()) && mailbag
										.getScannedPort()
										.equals(mailbagVO.getScannedPort()))) {
							if (isScanned) {
								mailbagVO.setErrorType(MailConstantsVO.EXCEPT_FATAL);
								mailbagVO
										.setErrorDescription(MailConstantsVO.DUPLICATE_MAIL_ERR);
							} else {
								/*throw new DuplicateMailBagsException(
										DuplicateMailBagsException.DUPLICATEMAILBAGS_EXCEPTION, //commented by A-8353 for ICRD-230449
										new Object[] { mailbagVO.getMailbagId() });*/
							}
						}
					} else if (OPERATION_FLAG_UPDATE.equals(mailbagVO.getOperationalFlag())) {
						if (mailbag != null) {
							if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(latestStatus) &&
									MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mailbagVO.getLatestStatus())) {
								if(isScanned) {
									mailbagVO.setErrorType(MailConstantsVO.EXCEPT_FATAL);
									mailbagVO.setErrorDescription(MailConstantsVO.DUPLICATE_ARRIVAL);
								}
							}
						}
					}
					if(mailbag != null) {
						return mailbag.retrieveVO();
					}
					return null;
				}

				/**
				 * A-1739
				 *
				 * @param mailbagVO
				 * @return
				 */
				private String constructDSNPKForMailbag(MailbagVO mailbagVO) {
					StringBuilder dsnPK = new StringBuilder();
					dsnPK.append(mailbagVO.getCompanyCode());
					dsnPK.append(mailbagVO.getDespatchSerialNumber());
					dsnPK.append(mailbagVO.getMailSubclass());
					dsnPK.append(mailbagVO.getMailCategoryCode());
					dsnPK.append(mailbagVO.getOoe());
					dsnPK.append(mailbagVO.getDoe());
					dsnPK.append(mailbagVO.getYear());
					return dsnPK.toString();
				}

				/**
				 * TODO Purpose Oct 9, 2006, a-1739
				 *
				 * @param containerDetailsVO
				 * @param dsnVO
				 * @param mailbagVO
				 * @throws SystemException 
				 */
				private void removeDuplicatebagsWeight(
						ContainerDetailsVO containerDetailsVO, DSNVO dsnVO,
						MailbagVO mailbagVO) throws SystemException {
					dsnVO.setReceivedBags(dsnVO.getReceivedBags() - 1);
					/*dsnVO.setReceivedWeight(dsnVO.getReceivedWeight()
							- mailbagVO.getWeight());*///added by A-7371
				
						try {
							dsnVO.setReceivedWeight(Measure.subtractMeasureValues(dsnVO.getReceivedWeight(), mailbagVO.getWeight()));
						} catch (UnitException e1) {
							// TODO Auto-generated catch block
							throw new SystemException(e1.getErrorCode());
						}

					// done in excpeitonpice removal
					// containerDetailsVO.setReceivedBags(
					// containerDetailsVO.getReceivedBags() - 1);
					// containerDetailsVO.setReceivedWeight(
					// containerDetailsVO.getReceivedWeight() - mailbagVO.getWeight());

					Collection<DSNAtAirportVO> dsnAtArps = dsnVO.getDsnAtAirports();
					if (dsnAtArps != null && dsnAtArps.size() > 0) {
						for (DSNAtAirportVO dsnArpVO : dsnAtArps) {
							dsnArpVO
									.setTotalBagsArrived(dsnArpVO.getTotalBagsArrived() - 1);
							/*dsnArpVO.setTotalWeightArrived(dsnArpVO.getTotalWeightArrived()
									- mailbagVO.getWeight());*/
							try {
								dsnArpVO.setTotalWeightArrived(Measure.subtractMeasureValues(dsnArpVO.getTotalWeightArrived(), mailbagVO.getWeight()));
							} catch (UnitException e) {
								// TODO Auto-generated catch block
								throw new SystemException(e.getErrorCode());
							}
									
						}
					}
				}



				/**
				 * @author A-1739
				 * @author A-3227
				 * @param doe
				 * @param companyCode
				 * @param deliveredPort
				 * @param cityCache
				 * @param eventCode
				 * @param poaCode 
				 * @return
				 * @throws SystemException
				 * @throws MailTrackingBusinessException
				 */
				public boolean isValidDeliveryAirport(String doe, String companyCode,String deliveredPort,
						 Map<String, String> cityCache,String eventCode, String poaCode,LocalDate dspDate)throws SystemException,
						 MailTrackingBusinessException{
					log.entering(ENTITY, "isValidDeliveryAirport");
					Collection<String> officeOfExchanges = new ArrayList<String>();
					if(doe !=null && doe.length() > 0) {
						officeOfExchanges.add(doe);
					}
					/*
				     * findCityAndAirportForOE method returns Collection<ArrayList<String>> in which,
				     * the inner collection contains the values in the order :
				     * 0.OFFICE OF EXCHANGE
				     * 1.CITY NEAR TO OE
				     * 2.NEAREST AIRPORT TO CITY
				     */
					String deliveryCityCode = null;
					String nearestAirport =  null;
					String nearestAirportToCity = null;
					log.log(Log.FINE, "----officeOfExchanges---", officeOfExchanges);
					Collection<ArrayList<String>> groupedOECityArpCodes = 
							new MailController().findCityAndAirportForOE(companyCode, officeOfExchanges);
					log
							.log(Log.FINE, "----groupedOECityArpCodes---",
									groupedOECityArpCodes);
					if(groupedOECityArpCodes != null && groupedOECityArpCodes.size() > 0) {
						for(ArrayList<String> cityAndArpForOE : groupedOECityArpCodes) {
							if(cityAndArpForOE.size() == 3) {
								if(doe != null && doe.length() > 0 && doe.equals(cityAndArpForOE.get(0))) {
									deliveryCityCode = cityAndArpForOE.get(1);
									nearestAirportToCity = cityAndArpForOE.get(2);
								}
							}
						}
					}
					/*
					 * take from the map first if not present, does proxy call and adds to
					 * cache
					 */
					if (cityCache != null) {
						nearestAirport = cityCache.get(deliveryCityCode);
					}

//					if (nearestAirport == null) {// nearestarp not found in cache
//						Collection<String> cityCodes = new ArrayList<String>();
//						cityCodes.add(deliveryCityCode);
			//
//						Map<String, CityVO> cityVOMap = null;
//						try {
//							cityVOMap = new SharedAreaProxy().validateCityCodes(
//									companyCode, cityCodes);
//						} catch (SharedProxyException ex) {
//							log.log(Log.SEVERE, "sharedproxyException " + ex);
//						}
			//
//						CityVO cityVO = cityVOMap.get(deliveryCityCode);
//						nearestAirport = cityVO.getNearestAirport();
			//
//						if (cityCache != null && nearestAirport != null) {
//							cityCache.put(deliveryCityCode, nearestAirport);
//						}
//					}
					if (nearestAirport == null && nearestAirportToCity != null ) {// nearest arp not found in cache
						nearestAirport = nearestAirportToCity;
						if (cityCache !=null &&
								nearestAirport != null &&
								nearestAirport.length() > 0  &&
								deliveryCityCode != null &&
								deliveryCityCode.length() > 0) {
							cityCache.put(deliveryCityCode, nearestAirport);
						}
					}
					LogonAttributes logonAttributes=ContextUtils.getSecurityContext().getLogonAttributesVO();
					AirportValidationVO airportValidationVO = new SharedAreaProxy().validateAirportCode(
							logonAttributes.getCompanyCode(),deliveredPort);
					if(airportValidationVO!=null ){
					log.log(Log.FINE, "airportValidationVO.getCityCode",airportValidationVO.getCityCode());
					log.log(Log.FINE, "deliveryCityCode",deliveryCityCode);
					if(airportValidationVO.getCityCode().equals(deliveryCityCode)){
						log.log(Log.FINE, "inside city validation returning true");

						return true;
					}
					}
					//Coterminus
					//String deliveryCode=MailConstantsVO.RESDIT_DELIVERED;//Modified for ICRD-335156
					log.log(Log.FINE, "Mail Arrival Coterminus check begins: deliveryCode",eventCode);
					log.log(Log.FINE, "Mail Arrival Coterminus check begins: deliveredPort",deliveredPort);
					log.log(Log.FINE, "Mail Arrival Coterminus check begins: doe",doe);
					Page<OfficeOfExchangeVO> destinationAirport=new MailController().findOfficeOfExchange(
							companyCode,doe,1);
					OfficeOfExchangeVO officeOfExchangeVO=new OfficeOfExchangeVO();
					if(destinationAirport!=null && !destinationAirport.isEmpty()){
						officeOfExchangeVO = destinationAirport.iterator().next();
					}
					//Added as part of bug ICRD-269711 by A-5526
					/*If airport code ofDOE is present,consider it.Else take the nearest airport 
					code of the city of the DOE.*/
					if(officeOfExchangeVO!=null &&  
							officeOfExchangeVO.getAirportCode()!=null &&
							 !officeOfExchangeVO.getAirportCode().isEmpty()){
						nearestAirport=officeOfExchangeVO.getAirportCode();
					}
					//Modified with nearestAirport as part of bug ICRD-269711 by A-5526
					boolean coTerminusCheck= new MailController().validateCoterminusairports(
							nearestAirport, deliveredPort,eventCode,poaCode,dspDate);//Changed by A-8164 for ICRD-342541
					
					log.log(Log.FINE, "Mail Arrival coTerminusCheck : ",coTerminusCheck);
					if(coTerminusCheck)
						return true;
					else
						throw new MailTrackingBusinessException(MailTrackingBusinessException.INVALID_DELIVERY_AIRPORT);


					//if (nearestAirport != null && nearestAirport.equals(deliveredPort)) {
					//	return true;
					//}
					//return false;
				}
				/**
				 * TODO Purpose Oct 18, 2006, a-1739
				 *
				 * @param containerDetailsVO
				 * @param duplicateBags
				 * @throws SystemException 
				 */
				private void removeArrivalExceptionPieces(
						ContainerDetailsVO containerDetailsVO,
						Collection<MailbagVO> duplicateBags) throws SystemException {
					log.entering(ENTITY, "updateRemovedPiecesCount");
					int bagCount = duplicateBags.size();
					double bagsWeight = calculateWeightofBags(duplicateBags);
					containerDetailsVO.setReceivedBags(containerDetailsVO.getReceivedBags()
							- bagCount);
					/*containerDetailsVO.setReceivedWeight(containerDetailsVO
							.getReceivedWeight()
							- bagsWeight);*/
					try {
						containerDetailsVO.setReceivedWeight(Measure.subtractMeasureValues(containerDetailsVO
								.getReceivedWeight(), new Measure(UnitConstants.MAIL_WGT,bagsWeight)));
					} catch (UnitException e1) {
						// TODO Auto-generated catch block
						throw new SystemException(e1.getErrorCode());
					}
					
					Collection<DSNVO> dsnVOs = containerDetailsVO.getDsnVOs();
					if (dsnVOs != null && dsnVOs.size() > 0) {
						Collection<DSNVO> dsnVOsToRem = new ArrayList<DSNVO>();
						for (DSNVO dsnVO : dsnVOs) {
							for (MailbagVO mailbagVO : duplicateBags) {
								if (dsnVO.getDsn().equals(
										mailbagVO.getDespatchSerialNumber())
										&& dsnVO.getOriginExchangeOffice().equals(
												mailbagVO.getOoe())
										&& dsnVO.getDestinationExchangeOffice().equals(
												mailbagVO.getDoe())
										&& dsnVO.getMailCategoryCode().equals(
												mailbagVO.getMailCategoryCode())
										&& dsnVO.getMailSubclass().equals(
												mailbagVO.getMailSubclass())
										&& dsnVO.getYear() == mailbagVO.getYear()) {
									dsnVO.setReceivedBags(dsnVO.getReceivedBags() - 1);
									/*dsnVO.setReceivedWeight(dsnVO.getReceivedWeight()
											- mailbagVO.getWeight());*///added by A-7371
									
										try {
											dsnVO.setReceivedWeight(Measure.subtractMeasureValues(dsnVO.getReceivedWeight(), mailbagVO.getWeight()));
										} catch (UnitException e) {
											// TODO Auto-generated catch block
											throw new SystemException(e.getErrorCode());
										}
									
								}
							}
							if (dsnVO.getReceivedBags() == 0) {
								dsnVOsToRem.add(dsnVO);
							}
						}
						dsnVOs.removeAll(dsnVOsToRem);
					}
					log.exiting(ENTITY, "updateRemovedPiecesCount");

				}


				/**
				 * A-1739
				 *
				 * @param mailbags
				 * @return
				 */
				private double calculateWeightofBags(Collection<MailbagVO> mailbags) {
					double totalWeight = 0;
					for (MailbagVO mailbagVO : mailbags) {
						totalWeight += mailbagVO.getWeight().getSystemValue();//added by A-7371
					}
					return totalWeight;
				}

				/**
				 * TODO Purpose Dec 7, 2006, a-1739
				 *
				 * @param mailArrivalVO
				 * @return
				 */
				private AssignedFlightPK constructInbFlightPK(MailArrivalVO mailArrivalVO) {
					AssignedFlightPK inbFlightPK = new AssignedFlightPK();
					inbFlightPK.setCompanyCode(mailArrivalVO.getCompanyCode());
					inbFlightPK.setAirportCode(mailArrivalVO.getAirportCode());
					inbFlightPK.setCarrierId(mailArrivalVO.getCarrierId());
					inbFlightPK.setFlightNumber(mailArrivalVO.getFlightNumber());
					inbFlightPK.setFlightSequenceNumber(mailArrivalVO
							.getFlightSequenceNumber());
					inbFlightPK.setLegSerialNumber(mailArrivalVO.getLegSerialNumber());
					return inbFlightPK;
				}


				/**
				 * TODO Purpose Dec 7, 2006, a-1739
				 *
				 * @param inbFlightPK
				 * @return
				 * @throws SystemException
				 */
				private boolean checkInboundFlightClosed(AssignedFlightPK inbFlightPK)
						throws SystemException {
					log.entering(ENTITY, "checkInboundFlightClosed");
					AssignedFlight inbFlight = null;
					try {
						inbFlight = AssignedFlight.find(inbFlightPK);
					} catch (FinderException exception) {
						throw new SystemException(exception.getMessage(), exception);
					}

					return MailConstantsVO.FLIGHT_STATUS_CLOSED.equals(inbFlight.getImportClosingFlag()
							);
				}


				/**
				 * @author A-1739
				 * @param containerDetailsVO
				 * @param mailArrivalVO
				 * @param segmentSerialNumber
				 * @return
				 */
				private AssignedFlightSegmentVO constructAssignedFlightSegmentVOForArrival(
						ContainerDetailsVO containerDetailsVO, MailArrivalVO mailArrivalVO,
						int segmentSerialNumber) {

					AssignedFlightSegmentVO asgFlightSegVO = new AssignedFlightSegmentVO();
					asgFlightSegVO.setCompanyCode(mailArrivalVO.getCompanyCode());
					asgFlightSegVO.setCarrierId(mailArrivalVO.getCarrierId());
					asgFlightSegVO.setFlightNumber(mailArrivalVO.getFlightNumber());
					asgFlightSegVO.setFlightSequenceNumber(mailArrivalVO
							.getFlightSequenceNumber());
					asgFlightSegVO.setSegmentSerialNumber(segmentSerialNumber);
					asgFlightSegVO.setPol(containerDetailsVO.getPol());
					asgFlightSegVO.setPou(mailArrivalVO.getAirportCode());
					return asgFlightSegVO;
				}

				private Collection<MailbagVO> saveDSNMstDetails(Map<String, DSNVO> dsnMap,
						boolean isScanned) throws SystemException,
			DuplicateMailBagsException {

		Collection<MailbagVO> errMailbags = new ArrayList<MailbagVO>();

		for (Map.Entry<String, DSNVO> dsnEntry : dsnMap.entrySet()) {

			DSNVO dsnVO = dsnEntry.getValue();

			boolean isUpdate = true;

			try {
				new Mailbag().saveArrivalDetails(dsnVO);
			} catch (Exception exception) {
				log.log(Log.FINE,
						"Exception in MailController at initiateArrivalForFlights for Offline *Flight* with DSN"
								+ dsnVO);
				continue;
			}
		}

		return errMailbags;
	}



				/**
				 * @author A-5991
				 * @param mailIdr
				 * @param companyCode
				 * @return
				 */
				private long findMailSequenceNumber(String mailIdr,String companyCode){
					try {
						return Mailbag.findMailBagSequenceNumberFromMailIdr(mailIdr, companyCode);
					} catch (SystemException e) {
						// TODO Auto-generated catch block
						log.log(Log.FINE, "SystemException-findMailBagSequenceNumberFromMailIdr");
					}
					return 0;
				}



				/**
				 * @author A-1936 This method is used to deliver all the Mailbags to the PA ..
				 *         These Mail Bags are actually Arrived and they lie in the
				 *         Inventory ..(At that Airport )..
				 * @param mailbagsInInventory
				 * @param poaCode
				 * @throws SystemException
				 */
				/*
				 * Fetch all those mailBags which are already Arrived and not Delivered or
				 * Transferred correponding to the particular Destination,Category etc. All
				 * those Mail Bags which are actually Delivered or the Transferred will not
				 * be there in the Inventory List .. Note: For all these Mail bags the
				 * Status has to be Updated in the Mail Bag Master and at the same time
				 * Updates has to take place against the History also .
				 */
				public void deliverMailBagsFromInventory(
						 Collection<MailInInventoryListVO> mailbagsInInventory) throws SystemException {
						 Collection<MailbagVO> mailBagsToRemoveFromInventory = new ArrayList<MailbagVO>();
						 Collection<MailbagVO> mailBagsFromInventory = null;
						 Collection<MailbagVO> arrivedMailBags = new ArrayList<MailbagVO>();
						 Map<AssignedFlightSegmentPK, Collection<MailbagVO>> assignedFlightSegmentMap = new HashMap<AssignedFlightSegmentPK, Collection<MailbagVO>>();
						 String airportCode = null;
					 // First Remove all the Mail Bags that are found in the Inventory Starts
						 boolean isRDTRestrictReq = false;
					if (mailbagsInInventory != null && mailbagsInInventory.size() > 0) {
						// Iterate to find out the MailBags correponding to the particular
						// Destination,Category etc ..
						for (MailInInventoryListVO mailInInventoryList : mailbagsInInventory) {
							mailBagsFromInventory = Mailbag
									.findMailBagsForInventory(mailInInventoryList);
							if (mailBagsFromInventory != null
									&& mailBagsFromInventory.size() > 0) {
								if(MailConstantsVO.FLAG_YES.equals(mailInInventoryList.getPaBuiltFlag())){
									for(MailbagVO mail:mailBagsFromInventory){
										mail.setPaBuiltFlag(mailInInventoryList.getPaBuiltFlag());
										if(MailConstantsVO.MAIL_SRC_RESDIT.equals(mail.getMailbagSource())){
											isRDTRestrictReq = true;
										}
									}
								}
								mailBagsToRemoveFromInventory.addAll(mailBagsFromInventory);
							}
						}
						if (mailBagsToRemoveFromInventory != null
								&& mailBagsToRemoveFromInventory.size() > 0) {
							new ReassignController()
									.reassignMailFromDestination(mailBagsToRemoveFromInventory);
						}
						// First Remove all the Mail Bags that are found in the Inventory
						// Ends...
						// Iterate to find out the MailBags correponding to the particular
						// Destination,Category etc ..
						for (MailInInventoryListVO mailInInventoryList : mailbagsInInventory) {
			                 if(airportCode==null){
			                	 airportCode=mailInInventoryList.getCurrentAirport();
			                 }
							mailBagsFromInventory = Mailbag
									.findMailsForDeliveryFromInventory(mailInInventoryList);
							if (mailBagsFromInventory != null
									&& mailBagsFromInventory.size() > 0) {
								 for(MailbagVO mail:mailBagsFromInventory){
									mail.setScannedDate(mailInInventoryList.getOperationTime());
									mail.setScannedUser(mailInInventoryList.getScannedUser());
									if(MailConstantsVO.FLAG_YES.equals(mailInInventoryList.getPaBuiltFlag())){
										mail.setPaBuiltFlag(mailInInventoryList.getPaBuiltFlag());
										if(MailConstantsVO.MAIL_SRC_RESDIT.equals(mail.getMailbagSource())){
											isRDTRestrictReq = true;
										}
									}
								 }
							    arrivedMailBags.addAll(mailBagsFromInventory);
							}
						}
						if (arrivedMailBags != null && arrivedMailBags.size() > 0) {
							Collection<MailbagVO> newMailbagVos = null;
							for (MailbagVO mailbagVo : arrivedMailBags) {
								log.entering("reassignMailbagsFromFlightToDestination",
										"Group the mailBags for DifferentSegments");
								AssignedFlightSegmentPK assignedFlightSegmentPK = constructAsgFlightPKForMailbag(mailbagVo);
								newMailbagVos = assignedFlightSegmentMap
										.get(assignedFlightSegmentPK);
								if (newMailbagVos == null) {
									newMailbagVos = new ArrayList<MailbagVO>();
									assignedFlightSegmentMap.put(assignedFlightSegmentPK,
											newMailbagVos);
								}
								newMailbagVos.add(mailbagVo);
							}
							if (assignedFlightSegmentMap != null
									&& assignedFlightSegmentMap.size() > 0) {
								log.log(Log.FINE,
										"THE No of the AssignedFlights Found are ",
										assignedFlightSegmentMap.size());
								AssignedFlightSegment assignedFlightSegment = null;
								try {
									for (AssignedFlightSegmentPK flightSegmentPK : assignedFlightSegmentMap
											.keySet()) {
										assignedFlightSegment = AssignedFlightSegment
												.find(flightSegmentPK);
										assignedFlightSegment
												.deliverMailBagsFromInventory(assignedFlightSegmentMap
														.get(flightSegmentPK));
									}
								} catch (FinderException ex) {
									log.log(Log.INFO, "DATA INCONSISTENT ");
									throw new SystemException(ex.getMessage(), ex);
								}
							}
						}
					}
					updateDSNsForDeliveryFromInventory(arrivedMailBags);
					Collection<ContainerInInventoryListVO> containersInInventory = new ArrayList<ContainerInInventoryListVO>();
					MailController mailController = (MailController)SpringAdapter.getInstance().getBean("mAilcontroller");
					if(!isRDTRestrictReq){
					mailController.flagResditsForInventoryDelivery(containersInInventory,arrivedMailBags);
					//Added for Mailbag history stamping through advice -A-5526
					}
					mailController.flagMailbagHistoryForDelivery(arrivedMailBags);
					mailController.flagMailbagAuditForDelivery(arrivedMailBags);
					/*String resditEnabled = findSystemParameterValue(MailConstantsVO.IS_RESDITMESSAGING_ENABLED);
					if(MailConstantsVO.FLAG_YES .equals(resditEnabled)){
						log.log(Log.FINE, "Resdit Enabled ", resditEnabled);
					new ResditController().flagResditsForInventoryDelivery(containersInInventory,arrivedMailBags);
				}*/
				}




				/**
				 * This method is used to construct the AssignedFlightSegmentPK from the
				 * MailBagVos A-1936
				 *
				 * @param flightAssignedMailbagVO
				 * @return
				 */
				private AssignedFlightSegmentPK constructAsgFlightPKForMailbag(
						MailbagVO flightAssignedMailbagVO) {
					AssignedFlightSegmentPK assignedFlightPK = new AssignedFlightSegmentPK();
					assignedFlightPK.setCompanyCode(flightAssignedMailbagVO
							.getCompanyCode());
					assignedFlightPK.setFlightNumber(flightAssignedMailbagVO
							.getFlightNumber());
					assignedFlightPK.setCarrierId(flightAssignedMailbagVO.getCarrierId());
					assignedFlightPK.setFlightSequenceNumber(flightAssignedMailbagVO
							.getFlightSequenceNumber());
					assignedFlightPK.setSegmentSerialNumber(flightAssignedMailbagVO
							.getSegmentSerialNumber());
					log.log(Log.FINE, "The Assigned Flight PK  companyCode is ",
							assignedFlightPK.getCompanyCode());
					log.log(Log.FINE, "The Assigned Flight PK  flightNumber is ",
							assignedFlightPK.getFlightNumber());
					log.log(Log.FINE, "The Assigned Flight PK carrierId  is ",
							assignedFlightPK.getCarrierId());
					log.log(Log.FINE, "The Assigned Flight PK flightSequenceNumber  is ",
							assignedFlightPK.getFlightSequenceNumber());
					log.log(Log.FINE, "The Assigned Flight PK  segmentSerialNumber is ",
							assignedFlightPK.getSegmentSerialNumber());
					return assignedFlightPK;
				}



				 /**
				  * @author A-1936
				  * This method is used to Update the Mail Bag Details and the History Details
				  * @param mailBags
				  * @throws SystemException
				  */
				 public void updateDSNsForDeliveryFromInventory(Collection<MailbagVO>  mailBags )
				  throws SystemException{
				 Mailbag mailbag= null;
					 for(MailbagVO mail:mailBags){

						 MailbagPK mailbagPK=new MailbagPK();
						 mailbagPK.setCompanyCode(mail.getCompanyCode());
						 long mailSequenceNumber=Mailbag.findMailBagSequenceNumberFromMailIdr(mail.getMailbagId(), mail.getCompanyCode());
						 mailbagPK.setMailSequenceNumber(mailSequenceNumber);
						  try {
							mailbag=Mailbag.find(mailbagPK);
						} catch (FinderException e) {
							throw new SystemException(e.getErrors());
						}
						 if(mailbag!=null){
						 mailbag.updateMailbagForDeliveryFromInventory(mail);
						 }
					 }
				 }
				 /**
				  *
				  * @param mailArrivalVO
				  */
				 private void updateMailbagVOforResdit(MailArrivalVO mailArrivalVO){
					 boolean resditReq = false;
						Collection<String> paramNames = new ArrayList<String>();
						paramNames.add(MailConstantsVO.IS_RESDITMESSAGING_ENABLED);
						try{
							Map<String, String> paramValMap =
								new SharedDefaultsProxy().findSystemParameterByCodes(
									paramNames);
							resditReq= MailConstantsVO.FLAG_YES.equals(
									paramValMap.get(MailConstantsVO.IS_RESDITMESSAGING_ENABLED));
						}catch(Exception exception){
							log.log(Log.FINE, exception.getMessage());
						}
						log.log(Log.FINE, "Resdit Required Flag "+resditReq);
						if(resditReq){
							Collection<ContainerDetailsVO> containerDetails = mailArrivalVO.getContainerDetails();
							if(containerDetails!=null && !containerDetails.isEmpty()){
								for (ContainerDetailsVO containerDetailsVO : containerDetails) {
									Collection<MailbagVO> mailbags = containerDetailsVO
									.getMailDetails();
									if (mailbags != null && !mailbags.isEmpty()) {
										for (MailbagVO mailbagVO : mailbags) {
											if (mailbagVO.getOperationalFlag() != null && !"N".equals(mailbagVO.getOperationalFlag())) {
												mailbagVO.setResditRequired(resditReq);
											}
										}
									}
								 }
						 }
					 }
				 }
				 /**
				  *
				  * @param containerDetails
				  */
				 private void updateMailVOsLegSerialNumber(Collection<ContainerDetailsVO> containerDetails)throws SystemException{
					 for(ContainerDetailsVO containerDtlsVO:containerDetails){
							if(MailConstantsVO.BULK_TYPE.equals(containerDtlsVO.getContainerType())){
								FlightValidationVO flightValidationVO = null;
								try{
						              flightValidationVO = validateFlightForBulk(containerDtlsVO);
						    	  }catch(Exception e){
						    		  log.log(Log.FINE, "updateMailVOsLegSerialNumber "+e.getMessage());
						    	  }
						    	  if(flightValidationVO!=null){
						    		  Collection<MailbagVO> mailVOs = containerDtlsVO.getMailDetails();
						    		  if(mailVOs!=null && !mailVOs.isEmpty()){
							    		  for(MailbagVO mailVO : mailVOs){
							    			  mailVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
										}
									}
								 }
						 }
					 }
				 }
	/**
	 * @author a-9529 methods the DAO instance ..
	 * @return
	 * @throws SystemException
	 */
	private static MailTrackingDefaultsDAO constructDAO() throws SystemException {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MailTrackingDefaultsDAO.class.cast(em.getQueryDAO(MODULE));
		} catch (PersistenceException ex) {
			ex.getErrorCode();
			log.log(Log.SEVERE, ex.getMessage(), ex);
			throw new SystemException(ex.getMessage());
		}
	}
	/**
	 * @author a-9529
	 * @param containers
	 * @return
	 * @throws SystemException
	 */
	public  static    Collection<ContainerDetailsVO> findMailbagsInContainerFromInboundForReact(Collection<ContainerDetailsVO> containers)
			throws SystemException{
		try {
			return constructDAO().findMailbagsInContainerFromInboundForReact(containers);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex);
					 }
				 }

}