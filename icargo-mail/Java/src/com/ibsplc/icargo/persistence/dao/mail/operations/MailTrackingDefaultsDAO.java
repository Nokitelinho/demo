
/*
 * MailTrackingDefaultsDAO.java Created on June 29, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GenerateInvoiceFilterVO;
import com.ibsplc.icargo.business.mail.operations.MailEventPK;
import com.ibsplc.icargo.business.mail.operations.vo.*;
import com.ibsplc.icargo.business.msgbroker.message.vo.cardit.CarditTempMsgVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ConsignmentInformationVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ContainerInformationVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ReceptacleInformationVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.TransportInformationVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.FlightListingFilterVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.ImportFlightOperationsVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.ImportOperationsFilterVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.ManifestFilterVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.ManifestVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.OffloadULDDetailsVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentSummaryVO;
import com.ibsplc.icargo.business.shared.audit.vo.AuditDetailsVO;
import com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadFilterVO;
import com.ibsplc.icargo.business.warehouse.defaults.ramp.vo.RunnerFlightFilterVO;
import com.ibsplc.icargo.business.warehouse.defaults.ramp.vo.RunnerFlightULDVO;
import com.ibsplc.icargo.business.warehouse.defaults.ramp.vo.RunnerFlightVO;
import com.ibsplc.icargo.framework.report.exception.ReportGenerationException;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.persistence.query.QueryDAO;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.*;
/**
 * @author a-5991
 *
 */
public interface MailTrackingDefaultsDAO extends QueryDAO {



				/**
				 *
				 * @param operationalFlightVO
				 * @return
				 * @throws SystemException
				 * @throws PersistenceException
				 */
				public Page<OperationalFlightVO> findMailFlightDetails(OperationalFlightVO operationalFlightVO)
					throws SystemException,PersistenceException;


				/**
				 * @author a-2037 This method is used to find all the mail subclass codes
				 * @param companyCode
				 * @param subclassCode
				 * @return Collection<MailSubClassVO>
				 * @throws SystemException
				 * @throws PersistenceException
				 */
				Collection<MailSubClassVO> findMailSubClassCodes(String companyCode,
						String subclassCode) throws SystemException, PersistenceException;



				/**
				 * @author A-1936 This method is used to find the Uplift
				 * @param operationalFlightVO
				 * @return
				 * @throws SystemException
				 * @throws PersistenceException
				 */
				Collection<MailbagVO> findMailBagsForUpliftedResdit(
						OperationalFlightVO operationalFlightVO) throws SystemException,
						PersistenceException;


				/**
				 * This method finds Cardit Details of Maiibag
				 * @param companyCode
				 * @param mailbagId
				 * @return
				 * @throws SystemException
				 * @throws PersistenceException
				 */
				MailbagHistoryVO findCarditDetailsOfMailbag(String companyCode,String mailBagId,
						long mailSequenceNumber) throws SystemException, PersistenceException; /*modified by A-8149 for ICRD-248207*/

				/**
				 * @author A-2037 This method is used to find the History of a Mailbag
				 * @param companyCode
				 * @param mailbagId
				 * @return
				 * @throws SystemException
				 * @throws PersistenceException
				 */
				Collection<MailbagHistoryVO> findMailbagHistories(String companyCode,String mailBagId,
						long mailSequenceNumbe,String mldMsgGenerateFlagr) throws SystemException, PersistenceException;  /*modified by A-8149 for ICRD-248207*/
						
				Collection<MailHistoryRemarksVO> findMailbagNotes(String mailBagId) throws SystemException, PersistenceException;
                 /**
                  *  
                  * @param mailbagEnquiryFilterVO
                  * @return
                  * @throws SystemException
                  * @throws PersistenceException
                  */
				Collection<MailbagHistoryVO> findMailStatusDetails(MailbagEnquiryFilterVO mailbagEnquiryFilterVO) throws SystemException, PersistenceException;
				/**
				 * TODO Purpose Oct 6, 2006, a-1739
				 *
				 * @param mailbagVO
				 * @return
				 */
				MailbagVO findMailbagDetailsForUpload(MailbagVO mailbagVO)
						throws SystemException, PersistenceException;


				/**
				 * @author A-1885
				 * @param mailUploadVo
				 * @return
				 * @throws SystemException
				 * @throws PersistenceException
				 */
				public Collection<MailUploadVO> findMailbagAndContainer(MailUploadVO mailUploadVo)
				throws SystemException,PersistenceException;


				/**
				 *
				 * 	Method		:	MailTrackingDefaultsDAO.findAirportCityForMLD
				 *	Added by 	:	A-4803 on 14-Nov-2014
				 * 	Used for 	:	finding airport city for mld
				 *	Parameters	:	@param companyCode
				 *	Parameters	:	@param destination
				 *	Parameters	:	@return
				 *	Parameters	:	@throws SystemException
				 *	Parameters	:	@throws PersistenceException
				 *	Return type	: 	String
				public String findAirportCityForMLD(String companyCode, String destination) throws SystemException,
				PersistenceException;

				/**
				 * @author A-5991
				 * @param flightAssignedContainerVO
				 * @return
				 * @throws SystemException
				 * @throws PersistenceException
				 */
				public int findNumberOfBarrowsPresentinFlightorCarrier(ContainerVO flightAssignedContainerVO) throws SystemException ,PersistenceException;

				/**
				 *  Find the flight details on which the container had arrived at a port
				 * Sep 12, 2007, a-1739
				 * @param containerVO
				 * @return
				 * @throws SystemException
				 * @throws PersistenceException
				 */
				ContainerAssignmentVO findArrivalDetailsForULD(ContainerVO containerVO)
						throws SystemException, PersistenceException;


				/**
				 *
				 * 	Method		:	MailTrackingDefaultsDAO.findAlreadyAssignedTrolleyNumberForMLD
				 *	Added by 	:	A-4803 on 28-Oct-2014
				 * 	Used for 	:	To find whether a container is already presnet for the mail bag
				 *	Parameters	:	@param mldMasterVO
				 *	Parameters	:	@return
				 *	Parameters	:	@throws SystemException
				 *	Parameters	:	@throws PersistenceException
				 *	Return type	: 	String
				 */
				public String findAlreadyAssignedTrolleyNumberForMLD(MLDMasterVO mldMasterVO) throws
				SystemException, PersistenceException;


				/**
				 * @author A-1876 This method is used to find the Uplift
				 * @param operationalFlightVO
				 * @return
				 * @throws SystemException
				 * @throws PersistenceException
				 */
				Collection<ContainerDetailsVO> findUldsForUpliftedResdit(
						OperationalFlightVO operationalFlightVO) throws SystemException,
						PersistenceException;


				/**
				 * Finds the arrival details for a mailbag
				 * Sep 12, 2007, a-1739
				 * @param mailbagVO
				 * @return
				 * @throws SystemException
				 * @throws PersistenceException
				 */
				MailbagVO findArrivalDetailsForMailbag(MailbagVO mailbagVO) throws SystemException,
				PersistenceException;

				/**
				 * @author a-1936 This method is used validate the MailSubClass
				 * @param companyCode
				 * @param mailSubClass
				 * @return
				 * @throws SystemException
				 * @throws PersistenceException
				 */
				boolean validateMailSubClass(String companyCode, String mailSubClass)
						throws SystemException, PersistenceException;


				/**
				 * @author A-1936 This method is used to validate teh OfficeofExchange
				 * @param companyCode
				 * @param officeOfExchange
				 * @return
				 * @throws SystemException
				 * @throws PersistenceException
				 */
				OfficeOfExchangeVO validateOfficeOfExchange(String companyCode, String officeOfExchange)
						throws SystemException, PersistenceException;

				/**
				 * @author A-2553
				 * @param dsnVO
				 * @return
				 * @throws SystemException
				 * @throws PersistenceException
				 */
				public String findMailType(MailbagVO mailbagVO)
				throws SystemException,PersistenceException;

				/**
				 *
				 * @param mailbagVO
				 * @return
				 * @throws SystemException
				 * @throws PersistenceException
				 */
				MailbagVO findLatestFlightDetailsOfMailbag(MailbagVO mailbagVO)
						throws SystemException, PersistenceException;


			   	/**
			   	 *
			   	 * @param mailbagVO
			   	 * @return
			   	 * @throws SystemException
			   	 * @throws PersistenceException
			   	 */
			   	public boolean isMailbagAlreadyArrived(MailbagVO mailbagVO) throws SystemException,
			   	PersistenceException;


			   	/**
				 * @author A-5526
				 * @param companyCode
				 * @return
				 * @throws SystemException
				 * @throws PersistenceException
				 */
				public Collection<MLDConfigurationVO> findMLDCongfigurations(
						MLDConfigurationFilterVO mLDConfigurationFilterVO) throws SystemException,
						PersistenceException ;

				/**
				 * @author A-5991
				 * @param mailId
				 * @param companyCode
				 * @return
				 * @throws SystemException
				 */
				public long findMailSequenceNumber(String mailId,String companyCode) throws SystemException;


				/**
				 * @author a-1936
				 * This method  is used to find out the Mail Bags in the Containers
				 * @param containers
				 * @return
				 * @throws SystemException
				 * @throws PersistenceException
				 */
				 Collection<ContainerDetailsVO> findMailbagsInContainer(Collection<ContainerDetailsVO> containers)
			     throws SystemException,PersistenceException;


				 Collection<ContainerDetailsVO> findMailbagsInContainerWithoutAcceptance(Collection<ContainerDetailsVO> containers)
					     throws SystemException,PersistenceException;
					/**
					 * @author A-2521
					 * For fetching already Stamped flight details for Collection of Mail Events
					 */
					public HashMap<String,Collection<MailResditVO>> findResditFlightDetailsForMailbagEvents(
							Collection<MailResditVO> mailResditVOs)
					throws SystemException, PersistenceException;



					/**
					 *
					 * @author A-1739 This method is used to findFlaggedResditSeqNum
					 * @param mailResditVO
					 * @param isSentCheckNeeded
					 * @return
					 * @throws SystemException
					 * @throws PersistenceException
					 */
					boolean checkResditExists(MailResditVO mailResditVO,
							boolean isSentCheckNeeded) throws SystemException,
							PersistenceException;



					/**
					 *
					 * 	Method		:	MailTrackingDefaultsDAO.checkResditExists
					 *	Added by 	:	A-5201 on 21-Nov-2014
					 * 	Used for 	:
					 *	Parameters	:	@param mailResditVO
					 *	Parameters	:	@param isSentCheckNeeded
					 *	Parameters	:	@return
					 *	Parameters	:	@throws SystemException
					 *	Parameters	:	@throws PersistenceException
					 *	Return type	: 	boolean
					 */
					boolean checkResditExistsFromReassign(MailResditVO mailResditVO,
							boolean isSentCheckNeeded) throws SystemException,
							PersistenceException;


				 	/**
						 * @author A-4072
						 * For Mail 4
						 * Finding PA for Mailbags . If cardit exists then sender of cardit else PA of OOE
						 * //Added for ICRD-63167 moving Cardit Resdit from QF to Base
						 */
						public HashMap<String, String>  findPAForMailbags(
								Collection<MailbagVO> mailbagVOs)
						throws SystemException, PersistenceException;


						 /**
						 * @author a-1739
						 * Method for finding the flight details of a mailbags' resdit
						 */
				       Collection<MailResditVO> findResditFlightDetailsForMailbag (
						MailResditVO mailResditVO) throws SystemException,PersistenceException;


				       /**
				   	 * @author A-1739 This method is used to check whether the Cardit is Present
				   	 *         for MailBag
				   	 * @param companyCode
				   	 * @param mailbagId
				   	 * @return
				   	 * @throws SystemException
				   	 */
				   	String findCarditForMailbag(String companyCode, String mailbagId)
				   			throws SystemException, PersistenceException;


				   	/**
					 *
					 * @param mailbagVO
					 * @return
					 * @throws SystemException
					 * @throws PersistenceException
					 */
					boolean isFlightSameForReceptacle(MailbagVO mailbagVO)
							throws SystemException, PersistenceException;


					/**
					 * @author A-2037 This method is used to find the ULDs in CARDIT
					 * @param operationalFlightVO
					 * @return
					 * @throws SystemException
					 * @throws PersistenceException
					 */
					Collection<PreAdviceDetailsVO> findULDInCARDIT(
							OperationalFlightVO operationalFlightVO) throws SystemException,
							PersistenceException;

					/**
					 * @author A-2037 This method is used to find the Mailbags in CARDIT
					 * @param operationalFlightVO
					 * @return
					 * @throws SystemException
					 * @throws PersistenceException
					 */
					Collection<PreAdviceDetailsVO> findMailbagsInCARDIT(
							OperationalFlightVO operationalFlightVO) throws SystemException,
							PersistenceException;

					/**
					 * @author A-1936 This method is used to check whether the Cardit is Present
					 *         For uld
					 * @param companyCode
					 * @param uldNumber
					 * @return
					 * @throws SystemException
					 * @throws PersistenceException
					 */
					boolean checkCarditPresentForUld(String companyCode, String uldNumber)
							throws SystemException, PersistenceException;



					/**
					 * @author A-1936
					 * @param containerDetailsVO
					 * @return
					 * @throws SystemException
					 * @throws PersistenceException
					 */
					boolean isFlightSameForUld(ContainerDetailsVO containerDetailsVO)
							throws SystemException, PersistenceException;
				/**
				 * @author A-5991
				 * @param companyCode
				 * @param consignmentId
				 * @return
				 * @throws SystemException
				 * @throws PersistenceException
				 */
					CarditVO findCarditDetailsForResdit(String companyCode, String consignmentId)
							throws SystemException, PersistenceException;

					/**
					 * ContainerCoundFor resdit Sep 15, 2006, a-1739
					 *
					 * @param companyCode
					 * @param consignmentNumber
					 * @return int
					 * @throws SystemException
					 * @throws PersistenceException
					 */
					int findCarditContainerCount(String companyCode, String consignmentNumber)
							throws SystemException, PersistenceException;


					/**
					 * find no of receptacles in cardit Sep 15, 2006, a-1739
					 *
					 * @param companyCode
					 * @param consignmentNumber
					 * @return int
					 * @throws SystemException
					 * @throws PersistenceException
					 */
					int findCarditReceptacleCount(String companyCode, String consignmentNumber)
							throws SystemException, PersistenceException;


					/**
					 * TODO Purpose Jan 23, 2007, A-1739
					 *
					 * @param carditEnquiryFilterVO
					 * @return
					 */
					CarditEnquiryVO findCarditMailDetails(
							CarditEnquiryFilterVO carditEnquiryFilterVO)
							throws SystemException, PersistenceException;


					/**
					 * TODO Purpose Jan 25, 2007, A-1739
					 *
					 * @param carditEnquiryFilterVO
					 * @return
					 */
					CarditEnquiryVO findCarditDocumentDetails(
							CarditEnquiryFilterVO carditEnquiryFilterVO)
							throws SystemException, PersistenceException;


					/**
					 * TODO Purpose Jan 25, 2007, A-1739
					 *
					 * @param carditEnquiryFilterVO
					 * @return
					 */
					CarditEnquiryVO findCarditDespatchDetails(
							CarditEnquiryFilterVO carditEnquiryFilterVO)
							throws SystemException, PersistenceException;

					/**
					 * TODO Purpose Feb 5, 2007, A-1739
					 *
					 * @param companyCode
					 * @param mailbagId
					 * @return
					 */
					Collection<CarditTransportationVO> findCarditTransportationDetails(
							String companyCode, String carditKey) throws SystemException,
							PersistenceException;


					/**
					 * @author A-1936 ADDED AS THE PART OF NCA-CR This method is used to find
					 *         the CarditDetails for the MailBags which will be used while
					 *         cardits are being flagged for all the Transferred Mails..
					 * @param companyCode
					 * @param mailID
					 * @return
					 * @throws SystemException
					 * @throws PersistenceException
					 */
					MailbagVO findCarditDetailsForAllMailBags(String companyCode, long mailID)
							throws SystemException, PersistenceException;

					   public MailbagVO findTransferFromInfoFromCarditForMailbags(MailbagVO mailbagVO) throws SystemException,
						PersistenceException;


					   /**
						 *
						 * @param reportVO
						 * @return
						 * @throws SystemException
						 */
						public Collection<CarditPreAdviseReportVO> generateCarditPreAdviceReport (CarditPreAdviseReportVO reportVO)
						throws SystemException;
					 	/**
					   	 *
					   	 * @param companyCode
					   	 * @param mailbagId
					   	 * @return
					   	 * @throws SystemException
					   	 * @throws PersistenceException
					   	 * @author A-2572
					   	 */
					   	public String findCarditOriginForResditGeneration(String companyCode,long mailbagId)
						throws SystemException,PersistenceException;


					    /**
					        * Jun 3, 2008, a-1739
					        * @param uldResditVO
					        * @return
					        * @throws SystemException
					        * @throws PersistenceException
					        */
					       Collection<UldResditVO> findULDResditStatus (UldResditVO uldResditVO)
							throws SystemException, PersistenceException;

					       /**
					  	 *
					  	 * @param uldResditVOs
					  	 * @return
					  	 * @throws SystemException
					  	 */
					  	public HashMap<String, String> findPAForShipperbuiltULDs(
					  			Collection<UldResditVO> uldResditVOs,boolean isFromCardit)	throws SystemException,PersistenceException;

						/**
						 * @author A-2037 This method is used to findOfficeOfExchange
						 * @param companyCode
						 * @param officeOfExchange
						 * @param pageNumber
						 * @return
						 * @throws SystemException
						 * @throws PersistenceException
						 */
						Page<OfficeOfExchangeVO> findOfficeOfExchange(String companyCode,
								String officeOfExchange, int pageNumber) throws SystemException,
								PersistenceException;


						/**
						 * Find the PA corresponding to a Office of Exchange Sep 13, 2006, a-1739
						 *
						 * @param companyCode
						 * @param officeOfExchange
						 * @return the PA Code
						 * @throws SystemException
						 * @throws PersistenceException
						 */
						String findPAForOfficeOfExchange(String companyCode, String officeOfExchange)
								throws SystemException, PersistenceException;



						/**
						 * @author A-5526
						 * @param companyCode
						 * @param paCode
						 * @return
						 * @throws SystemException
						 * @throws PersistenceException
						 * Added for CRQ ICRD-111886 by A-5526
						 */
						String findPartyIdentifierForPA(String companyCode, String paCode)
								throws SystemException, PersistenceException;

						/**
						 * TODO Purpose Feb 1, 2007, A-1739
						 *
						 * @param companyCode
						 * @param carrierId
						 * @return
						 */
						ResditConfigurationVO findResditConfurationForAirline(String companyCode,
								int carrierId) throws SystemException, PersistenceException;


						/**
						 * Find the ResditConfiguration for a txn Feb 5, 2007, A-1739
						 *
						 * @param companyCode
						 * @param carrierId
						 * @param txnId
						 * @return
						 */
						ResditTransactionDetailVO findResditConfurationForTxn(String companyCode,
								int carrierId, String txnId) throws SystemException,
								PersistenceException;


						/**
						 * @author A-2553
						 * @param companyCode
						 * @param mailbagId
						 * @param opFltVO
						 * @return
						 * @throws SystemException
						 */
						public MailbagVO findExistingMailbags(String companyCode,long mailbagId)
						throws SystemException,PersistenceException;


						/**
						 * @author a-2518
						 * @param companyCode
						 * @param officeOfExchange
						 * @return
						 * @throws SystemException
						 * @throws PersistenceException
						 */
						String findPostalAuthorityCode(String companyCode, String officeOfExchange)
								throws SystemException, PersistenceException;

						/**
						 * @author a-2518
						 * @param companyCode
						 * @param gpaCode
						 * @param origin
						 * @param destination
						 * @param mailCategory
						 * @param activity
						 * @param scanDate
						 * @return MailActivityDetailVO
						 * @throws SystemException
						 * @throws PersistenceException
						 */
						MailActivityDetailVO findServiceTimeAndSLAId(String companyCode,
								String gpaCode, String origin, String destination,
								String mailCategory, String activity, LocalDate scanDate)
								throws SystemException, PersistenceException;



						/**
						 * @author A-3227
						 * This method fetches the latest Container Assignment
						 * irrespective of the PORT to which it is assigned.
						 * This to know the current assignment of the Container.
						 * @param containerNumber
						 * @param companyCode
						 * @return
						 * @throws SystemException
						 */
						ContainerAssignmentVO findLatestContainerAssignment(String companyCode,String containerNumber)
						throws SystemException,PersistenceException;

						/**
						 * @author a-1936 This method Checks whether the container is already
						 *         assigned to a flight/destn from the current airport
						 * @param companyCode
						 * @param containerNumber
						 * @param pol
						 * @throws SystemException
						 * @throws PersistenceException
						 */
						ContainerAssignmentVO findContainerAssignment(String companyCode,
								String containerNumber, String pol) throws SystemException,
								PersistenceException;

						  /**
						   * @author A-3227
						   * findCityForOfficeOfExchange
						   * @param companyCode
						   * @param officeOfExchanges
						   * @return
						   * @throws SystemException
						   * @throws PersistenceException
						   */
						   public HashMap<String,String> findCityForOfficeOfExchange(
								   String companyCode, Collection<String> officeOfExchanges) throws SystemException,
									PersistenceException;


							/**
							 * @author a-1883 NCA CR
							 * @param operationalFlightVO
							 * @return
							 * @throws SystemException
							 * @throws PersistenceException
							 */
							Collection<MailDiscrepancyVO> findMailDiscrepancies(
									OperationalFlightVO operationalFlightVO) throws SystemException,
									PersistenceException;

							/**
							 * @param operationalFlightVO
							 * @return
							 * @throws SystemException
							 * @throws PersistenceException
							 */
							Collection<ContainerVO> findULDsInInboundFlight(
									OperationalFlightVO operationalFlightVO) throws SystemException,
									PersistenceException;


							/**
							 * TODO Purpose Oct 6, 2006, a-1739
							 *
							 * @param mailbagVO
							 * @return
							 */


							/**
							 * @author A-1936 This method is used to find the LastAssignedResditForUld
							 * @param containerDetailVo
							 * @param eventCode
							 * @return
							 * @throws SystemException
							 * @throws PersistenceException
							 */
							UldResditVO findLastAssignedResditForUld(
									ContainerDetailsVO containerDetailVo, String eventCode)
									throws SystemException, PersistenceException;
							/**
							 * @author a-1876 This method is used to list the PartnerCarriers.
							 * @param companyCode
							 * @param ownCarrierCode
							 * @param airportCode
							 * @return Collection<PartnerCarrierVO>
							 * @throws SystemException
							 * @throws PersistenceException
							 */
							Collection<PartnerCarrierVO> findAllPartnerCarriers(String companyCode,
									String ownCarrierCode, String airportCode) throws SystemException,
									PersistenceException;

							Collection<CoTerminusVO> findAllCoTerminusAirports(CoTerminusFilterVO filterVO) throws SystemException,
							PersistenceException;
                            Collection<MailRdtMasterVO> findRdtMasterDetails(RdtMasterFilterVO filterVO) throws SystemException,
							PersistenceException;
					
							/*added by A-8149 for ICRD-243386*/
							Page<MailServiceStandardVO> listServiceStandardDetails(MailServiceStandardFilterVO mailServiceStandardFilterVO,int pageNumeber) throws SystemException,
							PersistenceException;
					
							boolean validateCoterminusairports(String actualAirport,String eventAirport,String eventCode,String paCode,LocalDate dspDate) throws SystemException,PersistenceException;

							/**
							 * This method finds mail sequence number
							 *
							 * @param mailInConsignmentVO
							 * @return int
							 * @throws SystemException
							 * @throws PersistenceException
							 */
							int findMailSequenceNumber(MailInConsignmentVO mailInConsignmentVO)
									throws SystemException, PersistenceException;

							/**
							 * @author a-1883
							 * @param consignmentDocumentVO
							 * @return ConsignmentDocumentVO
							 * @throws SystemException
							 * @throws PersistenceException
							 */
							ConsignmentDocumentVO checkConsignmentDocumentExists(
									ConsignmentDocumentVO consignmentDocumentVO)
									throws SystemException, PersistenceException;

							/**
							 * @author A-2037 This method is used to get the
							 *         ConsignmentDetailsForMailBag
							 * @param companyCode
							 * @param mailId
							 * @param airportCode
							 * @return
							 * @throws SystemException
							 * @throws PersistenceException
							 */
							MailInConsignmentVO findConsignmentDetailsForMailbag(String companyCode,
									String mailId, String airportCode) throws SystemException,
									PersistenceException;

							/**
							 * @author a-1883 This method returns Consignment Details
							 * @param consignmentFilterVO
							 * @return ConsignmentDocumentVO
							 * @throws SystemException
							 * @throws PersistenceException
							 */
							ConsignmentDocumentVO findConsignmentDocumentDetails(
									ConsignmentFilterVO consignmentFilterVO) throws SystemException,
									PersistenceException;

							/**
							 * This method checks whether mail(dsn) accepted
							 *
							 * @author a-1883
							 * @param mailInConsignmentVO
							 * @return String
							 * @throws SystemException
							 * @throws PersistenceException
							 */
							String checkMailAccepted(MailInConsignmentVO mailInConsignmentVO)
									throws SystemException, PersistenceException;

							/**
							 * This method collects Mail details for Report
							 *
							 * @author A-1883
							 * @param operationalFlightVO
							 * @param consignmentKey TODO
							 * @return
							 * @throws SystemException
							 * @throws PersistenceException
							 */
							Collection<MailDetailVO> findMailbagDetailsForReport(
									OperationalFlightVO operationalFlightVO, String consignmentKey) throws SystemException,
									PersistenceException;

							 /**
							  * @author A-3227
							  * @param companyCode
							  * @param despatchDetailsVOs
							  * @return
							  * @throws SystemException
							  * @throws PersistenceException
							  */
							   public DespatchDetailsVO findConsignmentDetailsForDespatch(
									   String companyCode,DespatchDetailsVO despatchDetailsVO) throws SystemException,
										PersistenceException;

							   /**
								 * @author a-3429 This method returns Consignment Details for print
								 * @param consignmentFilterVO
								 * @return ConsignmentDocumentVO
								 * @throws SystemException
								 * @throws PersistenceException
								 */
								ConsignmentDocumentVO generateConsignmentReport(
										ConsignmentFilterVO consignmentFilterVO) throws SystemException,
										PersistenceException;

								/**
								 *
								 * @param mLDConfigurationFilterVO
								 * @return
								 * @throws SystemException
								 * @throws PersistenceException
								 */
								public Collection<MLDMasterVO> findMLDDetails(
										String companyCode,int recordCount) throws SystemException,
										PersistenceException ;

								/**
								 *
								 * 	Method		:	MailTrackingDefaultsDAO.findAirportCityForMLD
								 *	Added by 	:	A-4803 on 14-Nov-2014
								 * 	Used for 	:	finding airport city for mld
								 *	Parameters	:	@param companyCode
								 *	Parameters	:	@param destination
								 *	Parameters	:	@return
								 *	Parameters	:	@throws SystemException
								 *	Parameters	:	@throws PersistenceException
								 *	Return type	: 	String
								 */
								public String findAirportCityForMLD(String companyCode, String destination) throws SystemException,
								PersistenceException;

								 /**
								 * @author a-2553
								 * @param carditEnquiryFilterVO
								 * @param pageNumber
								 * @return
								 * @throws SystemException
								 */
								public Page<MailbagVO> findCarditMails(
										CarditEnquiryFilterVO carditEnquiryFilterVO, int pageNumber)
										throws SystemException,PersistenceException;


								/**
								* @author A-2107
							    * @param consignmentFilterVO
							    * @throws SystemException
							    * @throws PersistenceException
							    */
							   public Collection<MailbagVO> findCartIdsMailbags(ConsignmentFilterVO consignmentFilterVO) throws SystemException;

							   /**
							    * @param companyCode
							    * @param mailbagID
							    * @return
							    * @throws SystemException
							    * @throws PersistenceException
							    */
							public CarditReceptacleVO findDuplicateMailbagsInCardit(String companyCode,
									String mailbagID)throws SystemException, PersistenceException ;

							/**
							 * @author a-1936
							 * This method is used to check wether the  Transportation Details already exist for the Particular  Cardit
							 * @param companyCode
							 * @param carditKey
							 * @param carditTransportationVO
							 * @return
							 * @throws SystemException
							 * @throws PersistenceException
							 */
							boolean  checkCarditTransportExists(String companyCode,String carditKey,CarditTransportationVO carditTransportationVO) throws SystemException,
							 PersistenceException;

							/**
							 * @author a-2391 This method returns Consignment Details
							 * @param consignmentFilterVO
							 * @return ConsignmentDocumentVO
							 * @throws SystemException
							 * @throws PersistenceException
							 */
							ConsignmentDocumentVO findConsignmentDocumentDetailsForHHT(
									ConsignmentFilterVO consignmentFilterVO) throws SystemException,
									PersistenceException;
							/**
							 * @author A-2037 This method is used to find Local PAs
							 * @param companyCode
							 * @param countryCode
							 * @return Collection<PostalAdministrationVO>
							 * @throws SystemException
							 * @throws PersistenceException
							 */
							Collection<PostalAdministrationVO> findLocalPAs(String companyCode,
									String countryCode) throws SystemException, PersistenceException;

							/**
							 *
							 * @param opFltVo
							 * @return
							 * @throws SystemException
							 * @throws PersistenceException
							 */
							public ContainerAssignmentVO findContainerDetailsForMRD(
									OperationalFlightVO opFltVo, String mailBag)  throws SystemException,
									PersistenceException ;

							/**
							 *
							 * 	Method		:	MailTrackingDefaultsDAO.validateMailBagsForUPL
							 *	Added by 	:	A-4803 on 24-Nov-2014
							 * 	Used for 	:	validating mail bags for MLD UPL
							 *	Parameters	:	@param flightValidationVO
							 *	Parameters	:	@return
							 *	Parameters	:	@throws SystemException
							 *	Parameters	:	@throws PersistenceException
							 *	Return type	: 	Collection<String>
							 */
							Collection<String> validateMailBagsForUPL(FlightValidationVO flightValidationVO) throws
							SystemException, PersistenceException;


							/**
							 *
							 * @author a-1936 This method is used to find the MailBags for a DST-CTG
							 *         +CONNUM+CAR+ARP Added as the Part of NCA CR
							 *
							 * @param mailInInventoryListVo
							 * @return
							 * @throws SystemException
							 * @throws PersistenceException
							 */
							Collection<MailbagVO> findMailBagsForInventory(
									MailInInventoryListVO mailInInventoryListVo)
									throws SystemException, PersistenceException;


							/**
							 *
							 * @author a-1936 This method is used to find the MailBags containing the Flight Details For the Delivery..
							 * @param mailInInventoryListVo
							 * @return
							 * @throws SystemException
							 * @throws PersistenceException
							 */
							Collection<MailbagVO> findMailsForDeliveryFromInventory(
									MailInInventoryListVO mailInInventoryListVo)
									throws SystemException, PersistenceException;


							/**
							 * @author A-1739 This method is used to find the FlightLegSerialNumber
							 * @param containerVO
							 * @return
							 * @throws SystemException
							 * @throws PersistenceException
							 */
							int findFlightLegSerialNumber(ContainerVO containerVO)
									throws SystemException, PersistenceException;


							public long findResditSequenceNumber(MailResditVO mailResditVO)  throws SystemException,
							PersistenceException;

							public Collection<MailInConsignmentVO> findConsignmentDetailsForDsn(
									String companyCode,DSNVO dsnVO) throws SystemException,
									PersistenceException ;

							/**
							 *
							 * 	Method		:	MailTrackingDefaultsDAO.validateMailFlight
							 *	Added by 	:	A-5160 on 26-Nov-2014
							 * 	Used for 	:	validating mail flight
							 *	Parameters	:	@param flightFilterVO
							 *	Parameters	:	@return
							 *	Parameters	:	@throws SystemException
							 *	Parameters	:	@throws PersistenceException
							 *	Return type	: 	Collection<FlightValidationVO>
							 */
							public Collection<FlightValidationVO> validateMailFlight(FlightFilterVO flightFilterVO) throws SystemException,
							PersistenceException;
							/**
							 * @author A-2037 This method is used to find the accepted ULDs
							 * @param operationalFlightVO
							 * @return
							 * @throws SystemException
							 * @throws PersistenceException
							 */
							Collection<ContainerDetailsVO> findAcceptedULDs(
									OperationalFlightVO operationalFlightVO) throws SystemException,
									PersistenceException;

							/**
							 * @author A-3227  - AUG 12, 2008
							 * @param operationalFlightVO
							 * @return
							 * @throws SystemException
							 * @throws PersistenceException
							 */
							public boolean checkRoutingsForMails(OperationalFlightVO operationalFlightVO,DSNVO dSNVO,String type)
							throws SystemException,PersistenceException;

							/**
							 * @author A-2037 Method for OfficeOfExchangeLOV containing code and
							 *         description
							 * @param companyCode
							 * @param code
							 * @param description
							 * @param pageNumber
							 * @return
							 * @throws SystemException
							 * @throws PersistenceException
							 */
							Page<OfficeOfExchangeVO> findOfficeOfExchangeLov(OfficeOfExchangeVO officeofExchangeVO, int pageNumber,int defaultSize)
									throws SystemException, PersistenceException;


							/**
							 * @author A-2037 This method is used to find Postal Administration Code
							 *         Details
							 * @param companyCode
							 * @param paCode
							 * @return PostalAdministrationVO
							 * @throws SystemException
							 * @throws PersistenceException
							 */
							PostalAdministrationVO findPACode(String companyCode, String paCode)
									throws SystemException, PersistenceException;


							/**
							 * @author A-2037 Method for MailSubClassLOV containing code and description
							 * @param companyCode
							 * @param code
							 * @param description
							 * @param pageNumber
							 * @return
							 * @throws SystemException
							 * @throws PersistenceException
							 */
							Page<MailSubClassVO> findMailSubClassCodeLov(String companyCode,
									String code, String description, int pageNumber,int defaultSize)
									throws SystemException, PersistenceException;

							/**
							 * @param operationalFlightVO
							 * @return
							 * @throws SystemException
							 * @throws PersistenceException
							 */
							Collection<ContainerVO> findULDsInAssignedFlight(
									OperationalFlightVO operationalFlightVO) throws SystemException,
									PersistenceException;


							/**
							 * @author a-1936
							 * This  method is used to find the Transfer Manifest Details
							 * @param tranferManifestFilterVo
							 * @return
							 * @throws SystemException
							 * @throws PersistenceException
							 */
							 Page<TransferManifestVO> findTransferManifest(TransferManifestFilterVO tranferManifestFilterVo)
							   throws SystemException, PersistenceException;

								/**
								 * @author a-2553
								 * Added By Paulson as the  part of  the Air NewZealand CR...
								 * @param companyCode,transferManifestId
								 * @return
								 */
								TransferManifestVO generateTransferManifestReport(String companyCode,String transferManifestId)
								 throws SystemException,PersistenceException;




								/**
								 * @author A-2553
								 * @param mailbagVOs
								 * @throws SystemException
								 * @throws PersistenceException
								 */
								ArrayList<MailbagVO> findMailTag(ArrayList<MailbagVO> mailbagVOs)throws SystemException,
								PersistenceException;


							  /**
							   * @author a-1936
							   * @param operationalFlightVo
							   * @return
							   * @throws SystemException
							   * @throws PersistenceException
							   */
							  MailManifestVO findContainersInFlightForManifest(OperationalFlightVO operationalFlightVo)
							   throws SystemException,PersistenceException;

							  /**
							   * @author a-1936
							   * This  method is used to find the mailbags in the Container for the Manifest
							   * @param containers
							   * @return
							   * @throws SystemException
							   * @throws PersistenceException
							   */
							  Collection<ContainerDetailsVO> findMailbagsInContainerForManifest(Collection<ContainerDetailsVO> containers)
							  throws SystemException,PersistenceException;

								/**
								 * @param operationalFlightVO
								 * @return
								 * @throws SystemException
								 * @throws PersistenceException
								 */
								Collection<ContainerVO> findContainersInAssignedFlight(
										OperationalFlightVO operationalFlightVO) throws SystemException,
										PersistenceException;


								/**
								 * @author a-1883
								 * @param aWBFilterVO
								 * @return AWBDetailVO
								 * @throws SystemException
								 * @throws PersistenceException
								 */
								AWBDetailVO findAWBDetails(AWBFilterVO aWBFilterVO) throws SystemException,
										PersistenceException;


								/**
								 * @author a-1936
								 * @param operationalFlightVo
								 * @return
								 * @throws SystemException
								 * @throws PersistenceException
								 */
								Collection<String> findMailBagsInClosedFlight(
										OperationalFlightVO operationalFlightVo) throws SystemException,
										PersistenceException;

								/**
								 * This method is used to find all the Containers that can be Offloaded for
								 * a ParticularFlight
								 *
								 * @param offloadFilterVO
								 * @return
								 * @throws SystemException
								 * @throws PersistenceException
								 */
								Collection<ContainerVO> findAcceptedContainersForOffload(
										OffloadFilterVO offloadFilterVO) throws SystemException,
										PersistenceException;

								Page<ContainerVO> findAcceptedContainersForOffLoad(
										OffloadFilterVO offloadFilterVO) throws SystemException,
										PersistenceException;

											/**
								 * This method is used to find all the DSNS,that can be Offloaded for a
								 * ParticularFlight
								 *
								 * @param offloadFilterVO
								 * @return
								 * @throws SystemException
								 * @throws PersistenceException
								 */
								Page<DespatchDetailsVO> findAcceptedDespatchesForOffload(
										OffloadFilterVO offloadFilterVO) throws SystemException,
										PersistenceException;

											/**
								 * This method is used to find all the MailBags that can be Offloaded for a
								 * ParticularFlight
								 *
								 * @param offloadFilterVO
								 * @return
								 * @throws SystemException
								 * @throws PersistenceException
								 */
								Page<MailbagVO> findAcceptedMailBagsForOffload(
										OffloadFilterVO offloadFilterVO) throws SystemException,
										PersistenceException;

								/**
								 * @author a-1936 This method is used to find the MailBags ...
								 * @param mailbagEnquiryFilterVO
								 * @param pageNumber
								 * @return
								 * @throws SystemException
								 * @throws PersistenceException
								 */
								Page<MailbagVO> findMailbags(MailbagEnquiryFilterVO mailbagEnquiryFilterVO,
										int pageNumber) throws SystemException, PersistenceException;


								/**
								 * @author A-2667
								 * This method is used to search Mailbags
								 * @param carditEnquiryFilterVO
								 * @return
								 * @throws SystemException
								 */
								Page<MailbagVO> findConsignmentDetails(CarditEnquiryFilterVO carditEnquiryFilterVO,int pageNumber)
									throws SystemException,PersistenceException;

								/**
								 * @author A-5931 Method for MBI LOV containing mailboxCode and mailboxDescription
								 * @param companyCode
								 * @param mailboxCode
								 * @param mailboxDescription
								 * @param pageNumber
								 * @return
								 * @throws SystemException
								 * @throws PersistenceException
								 */
								Page<MailBoxIdLovVO> findMailBoxIdLov(String companyCode, String mailboxCode,
										String mailboxDesc, int pageNumber, int defaultSize) throws SystemException,
										PersistenceException;

								 /**
							        * Jun 6, 2008, 2556
							        * @param dsnVO
							        * @param mode
							        * @return
							        * @throws SystemException
							        * @throws PersistenceException
							        */
							       Collection<DespatchDetailsVO> findDespatchesOnDSN (DSNVO dsnVO,String mode)
									throws SystemException, PersistenceException;

							   	/**
							   	 * @author a-1936 This method is used to return all the containers which are
							   	 *         already assigned to a particular Flight
							   	 * @param operationalFlightVO
							   	 * @return
							   	 * @throws SystemException
							   	 * @throws PersistenceException
							   	 */
							   	Collection<ContainerVO> findFlightAssignedContainers(
							   			OperationalFlightVO operationalFlightVO) throws SystemException,
							   			PersistenceException;

			/**


							/**
								 * @author a-1936 This method is used to find the containers
								 * @param searchContainerFilterVO
								 * @param pageNumber
								 * @return
								 * @throws SystemException
								 * @throws PersistenceException
								 */
								Page<ContainerVO> findContainers(
										SearchContainerFilterVO searchContainerFilterVO, int pageNumber)
										throws SystemException, PersistenceException;


								/**
								 *
								 * @author A-2553
								 * @param mailStatusFilterVO
								 * @return
								 * @throws SystemException
								 * @throws PersistenceException
								 */
								Collection<MailStatusVO> generateMailStatusReport(MailStatusFilterVO
										mailStatusFilterVO) throws SystemException, PersistenceException;



							   	/**
							   	 * @author A-1936 This method is used to findDestinationAssignedContainers
							   	 * @param destinationFilterVO
							   	 * @return
							   	 * @throws SystemException
							   	 * @throws PersistenceException
							   	 */
							   	Collection<ContainerVO> findDestinationAssignedContainers(
							   			DestinationFilterVO destinationFilterVO) throws SystemException,
							   			PersistenceException;

								/**
								 * For finds mailbags for manifest Jan 18, 2007, A-1739
								 *
								 * @param opFlightVO
								 * @return
								 * @throws SystemException
								 * @throws PersistenceException
								 */

								MailManifestVO findMailbagManifestDetails(OperationalFlightVO opFlightVO)
										throws SystemException, PersistenceException;

								/**
								 * Find finding AWBs for manfist Jan 18, 2007, A-1739
								 *
								 * @param opFlightVO
								 * @return
								 * @throws SystemException
								 * @throws PersistenceException
								 */
								MailManifestVO findMailAWBManifestDetails(OperationalFlightVO opFlightVO)
										throws SystemException, PersistenceException;

								/**
								 * For finding DSNs and Mailbags for manfist MAr 27,2008, A-2553
								 *
								 * @param opFlightVO
								 * @return
								 * @throws SystemException
								 * @throws PersistenceException
								 */
								MailManifestVO findDSNMailbagManifest(OperationalFlightVO opFlightVO)
										throws SystemException, PersistenceException;

								/**
								 * For finding manifest by destn and cat Jan 18, 2007, A-1739
								 *
								 * @param opFlightVO
								 * @return
								 * @throws SystemException
								 * @throws PersistenceException
								 */
								public MailManifestVO findManifestbyDestination(
										OperationalFlightVO opFlightVO) throws SystemException,
										PersistenceException;

								  /**
								 * This method is used to find the Damaged Mailbag Details Based On
								 * User defined criteria
								 *
								 * @author A-3227 RENO K ABRAHAM
								 * @param companyCode
								 * @param mailbagId
								 * @return
								 * @throws SystemException
								 * @throws PersistenceException
								 */
								Collection<DamagedMailbagVO> findDamageMailReport(DamageMailFilterVO
										damageMailFilterVO) throws SystemException, PersistenceException;

								/**
								 * For finding manifest summary by destn and cat Sep 02, 2007, A-1876
								 *
								 * @param opFlightVO
								 * @return
								 * @throws SystemException
								 * @throws PersistenceException
								 */
								public Collection<MailSummaryVO> findManifestSummaryByDestination(
										OperationalFlightVO opFlightVO) throws SystemException,
										PersistenceException;


								Collection<AWBDetailVO> findAWBDetailsForFlight(
										OperationalFlightVO operationalFlightVO) throws SystemException,
										PersistenceException;


								public Collection<MailbagVO> findMailBagForDespatch(MailbagVO mailbagVO)
										throws SystemException,PersistenceException;
								/**
								 * @author A-1739 Thi smethod is used to find the ArrivedContainers
								 * @param opFlightVO
								 * @return
								 * @throws SystemException
								 * @throws PersistenceException
								 */
								List<ContainerDetailsVO> findArrivedContainers(
										MailArrivalFilterVO mailArrivalFilterVO) throws SystemException,
										PersistenceException;
								/**
								 * @author a-1936 ADDED AS THE PART OF NCA-CR findMailDetail This method is
								 *         used to find out the MailDetais For all MailBags for which
								 *         Resdits are not sent and having the Search Mode as Despatch..
								 * @param despatchDetailVos
								 * @param unsentResditEvent
								 * @return
								 * @throws SystemException
								 * @throws PersistenceException
								 */
								Collection<MailbagVO> findMailDetailsForDespatches(
										Collection<DespatchDetailsVO> despatchDetailVos,
										String unsentResditEvent) throws SystemException,
										PersistenceException;

								/**
								 *
								 * @author a-1936 ADDED AS THE PART OF NCA-CR This method is used to find
								 *         out the MailDetais For all MailBags for which Resdits are not
								 *         sent and having the Search Mode as Document..
								 * @param consignmentDocumentVos
								 * @param unsentResditEvent
								 * @return
								 * @throws SystemException
								 * @throws PersistenceException
								 */
								Collection<MailbagVO> findMailDetailsForDocument(
										Collection<ConsignmentDocumentVO> consignmentDocumentVos,
										String unsentResditEvent) throws SystemException,
										PersistenceException;

								/**
								    * @param partyCode
								    * @return
								    * @throws SystemException
								    * @throws PersistenceException
								    */
									public String findPartyName(String companyCode,
											String partyCode)throws SystemException, PersistenceException ;

									/**
									 * TODO Purpose Sep 14, 2006, a-1739
									 *
									 * @param companyCode
									 * @param receptacleID
									 * @return offload reason
									 * @throws SystemException
									 * @throws PersistenceException
									 */
									String findOffloadReasonForMailbag(String companyCode, String receptacleID)
											throws SystemException, PersistenceException;


									/**
									 * TODO Purpose Sep 14, 2006, a-1739
									 *
									 * @param companyCode
									 * @param containerNumber
									 * @return offload reason
									 * @throws SystemException
									 * @throws PersistenceException
									 */
									String findOffloadReasonForULD(String companyCode, String containerNumber)
											throws SystemException, PersistenceException;


									/**
									 * Find the reason for return of a mailbag Sep 14, 2006, a-1739
									 *
									 * @param companyCode
									 * @param receptacleID
									 * @param airportCode
									 *            TODO
									 * @return the damagereason
									 * @throws SystemException
									 * @throws PersistenceException
									 */
									String findDamageReason(String companyCode, String receptacleID,
											String airportCode) throws SystemException, PersistenceException;


									/**
									 * @author A-2037 This method is used to find the Damaged Mailbag Details
									 * @param companyCode
									 * @param mailbagId
									 * @return
									 * @throws SystemException
									 * @throws PersistenceException
									 */
									Collection<DamagedMailbagVO> findMailbagDamages(String companyCode,
											String mailbagId) throws SystemException, PersistenceException;

									/**
									 *
									 * @param ConsignmentInformationVO
									 * @return
									 * @throws SystemException
									 * @throws PersistenceException
									 */
									public Collection<CarditReferenceInformationVO>  findCCForSendResdit (
											ConsignmentInformationVO consgmntInfo) throws SystemException, PersistenceException;

									/**
									 *
									 * @param consignmentInformationVOsForXX
									 * @return
									 * @throws SystemException
									 * @throws PersistenceException
									 */
									public HashMap<String, String>  findRecepientForXXResdits (
											Collection<ConsignmentInformationVO> consignmentInformationVOsForXX )
											 throws SystemException, PersistenceException;

									 /**
								   	   * @author A-3251
								   	   * This  method is used to find the details of uld and bulk to generate a report for Daily Mail Station
								   	   * @param containers
								   	   * @return
								   	   * @throws SystemException
								   	   * @throws PersistenceException
								   	   */

								  Collection<DailyMailStationReportVO> generateDailyMailStationReport(DailyMailStationFilterVO filterVO)
								  throws SystemException,ReportGenerationException;


									/**
									 * @author A-2037 Method for PALov containing PACode and PADescription
									 * @param companyCode
									 * @param paCode
									 * @param paName
									 * @param pageNumber
									 * @return
									 * @throws SystemException
									 * @throws PersistenceException
									 */
									Page<PostalAdministrationVO> findPALov(String companyCode, String paCode,
											String paName, int pageNumber,int defaultSize) throws SystemException,
											PersistenceException;
								   	/**
								   	*  Added for icrd-110909
							        * @param companyCode
							        * @param airportCode
							        * @return
							        */
									public  Collection<String> findOfficeOfExchangesForAirport(
											String companyCode, String airportCode)
									throws SystemException,PersistenceException;



								/**
								 * @author A-5991
								 * @param dsnVO
								 * @return
								 * @throws SystemException
								 * @throws PersistenceException
								 */
								   	public Collection<MailbagVO> findDSNMailbags(DSNVO dsnVO)
								   	   throws SystemException,PersistenceException;

									/**
									 *
									 * @param companyCode
									 * @param officeOfExchange
									 * @return
									 * @throws SystemException
									 * @throws PersistenceException
									 */
									PostalAdministrationVO findPADetails(String companyCode,
											String officeOfExchange) throws SystemException,
											PersistenceException;

									/**
									 * @author A-3251
									 * @param postalAdministrationDetailsVO
									 * @throws SystemException
									 */
								   public PostalAdministrationDetailsVO validatePoaDetails(PostalAdministrationDetailsVO postalAdministrationDetailsVO)
								   throws SystemException,PersistenceException;
									/**
								   	 * 	Method		:	MailTrackingDefaultsDAO.findAllPACodes
								   	 *	Added by 	:	A-4809 on 08-Jan-2014
								   	 * 	Used for 	:	ICRD-42160
								   	 *	Parameters	:	@param generateInvoiceFilterVO
								   	 *	Parameters	:	@return
								   	 *	Parameters	:	@throws SystemException
								   	 *	Parameters	:	@throws PersistenceException
								   	 *	Return type	: 	Collection<PostalAdministrationVO>
								   	 */
								   	public Collection<PostalAdministrationVO> findAllPACodes(GenerateInvoiceFilterVO generateInvoiceFilterVO)
								   		throws SystemException,PersistenceException;

											/**
									 *
									 * @param companyCode
									 * @return
									 * @throws SystemException
									 * @throws PersistenceException
									 */
									public Collection<MailScanDetailVO> findScannedMailDetails(
											String companyCode,int uploadCount) throws SystemException,
											PersistenceException ;

									/**
									 * 	Method		:	MailTrackingDefaultsDAO.performUploadCorrection
									 *	Added by 	:	A-4809 on Dec 4, 2015
									 * 	Used for 	:
									 *	Parameters	:	@param companyCode
									 *	Parameters	:	@return
									 *	Return type	: 	Object
									 */
									public void  performUploadCorrection(String companyCode)throws SystemException,PersistenceException;
									/**
									 *
									 * @author A-3227 RENO K ABRAHAM
									 * @param mailHandedOverFilterVO
									 * @return
									 * @throws SystemException
									 * @throws PersistenceException
									 */
									Collection<MailHandedOverVO> generateMailHandedOverReport(MailHandedOverFilterVO
											mailHandedOverFilterVO) throws SystemException, PersistenceException;

									/**
									 @author a-1936
								      * Added By Karthick V as the  part of  the Air NewZealand CR...
								      * This method is used to find all  the DSNs and the mail bags Required For the Impport Manifest Report..
									  * @param operationalFlightVo
									  * @return
									  * @throws SystemException
									  * @throws PersistenceException
									  */
									MailManifestVO findImportManifestDetails(OperationalFlightVO operationalFlightVo) throws SystemException,PersistenceException;
									/**
									 * 	Method		:	MailTrackingDefaultsDAO.findOnlineFlightsAndConatiners
									 *	Added by 	:	A-4809 on Sep 30, 2015
									 * 	Used for 	:
									 *	Parameters	:	@param companyCode
									 *	Parameters	:	@return
									 *	Parameters	:	@throws SystemException
									 *	Parameters	:	@throws PersistenceException
									 *	Return type	: 	Collection<ContainerDetailsVO>
									 */
									public List<MailArrivalVO> findOnlineFlightsAndConatiners(String companyCode)
											throws SystemException,PersistenceException;
									/**
									 * 	Method		:	MailTrackingDefaultsDAO.findFlightsForArrival
									 *	Added by 	:	A-4809 on Sep 30, 2015
									 * 	Used for 	:
									 *	Parameters	:	@param companyCode
									 *	Parameters	:	@return
									 *	Parameters	:	@throws SystemException
									 *	Parameters	:	@throws PersistenceException
									 *	Return type	: 	Collection<OperationalFlightVO>
									 */
									public Collection<OperationalFlightVO> findFlightsForArrival(String companyCode)
									throws SystemException,PersistenceException;
									/**
									 * 	Method		:	MailTrackingDefaultsDAO.findArrivalDetailsForReleasingMails
									 *	Added by 	:	A-4809 on Sep 30, 2015
									 * 	Used for 	:
									 *	Parameters	:	@param flightVO
									 *	Parameters	:	@return
									 *	Parameters	:	@throws SystemException
									 *	Parameters	:	@throws PersistenceException
									 *	Return type	: 	MailArrivalVO
									 */
									public List<ContainerDetailsVO> findArrivalDetailsForReleasingMails(OperationalFlightVO flightVO)
									throws SystemException,PersistenceException;
									/**
									 * @author A-1885
									 * @param companyCode
									 * @param time
									 * @return
									 * @throws SystemException
									 * @throws PersistenceException
									 */
									public Collection<OperationalFlightVO> findFlightForMailOperationClosure
								(String companyCode, int time,String airportCode)throws SystemException,PersistenceException;
								       /**
								   	 * @param operationalFlightVO
								   	 * @return
								   	 * @throws SystemException
								   	 * @throws PersistenceException
								   	 */
								   	public String findAnyContainerInAssignedFlight(
								   			OperationalFlightVO operationalFlightVO) throws SystemException,
								   			PersistenceException ;
								   	/**
								   	 * @author A-5166
								   	 * Added for ICRD-36146 on 07-Mar-2013
								   	 * @param companyCode
								   	 * @return
								   	 * @throws SystemException
								   	 * @throws PersistenceException
								   	 */
								   	public Collection<OperationalFlightVO> findImportFlghtsForArrival(String companyCode)
								   		throws SystemException,PersistenceException;

								   	/**
									 *
									 * @param dSNEnquiryFilterVO
									 * @param pageNumber
									 * @return
									 * @throws PersistenceException
									 * @throws SystemException
									 */
									public Page<DespatchDetailsVO> findDSNs(DSNEnquiryFilterVO dSNEnquiryFilterVO, int pageNumber)
											throws PersistenceException,SystemException;

									/**
									 * @author A-5526 Added for CRQ-ICRD-103713
									 * @param companyCode
									 * @return
									 * @throws SystemException
									 * @throws PersistenceException
									 */
									String findUpuCodeNameForPA(String companyCode, String paCode)
											throws SystemException, PersistenceException;

									/**
									 * Find the PA corresponding to a Mailbox ID MAY 23, 2016, a-5526
									 *
									 * @param companyCode
									 * @param mailboxId
									 * @return the PA Code
									 * @throws SystemException
									 * @throws PersistenceException
									 */
									String findPAForMailboxID(String companyCode, String mailboxId,String originOE)
											throws SystemException, PersistenceException;
									/**
									 * A-1739
									 *
									 * @param companyCode
									 * @throws SystemException
									 * @throws PersistenceException
									 */
									void invokeResditReceiver(String companyCode) throws SystemException,
											PersistenceException;

									/**
									 * Checks for any flagged resdit events and returns them Sep 8, 2006, a-1739
									 *
									 * @param companyCode
									 * @return
									 * @throws SystemException
									 * @throws PersistenceException
									 */
									Collection<ResditEventVO> findResditEvents(String companyCode)
											throws SystemException, PersistenceException;

									/**
									 * @author A-5249
									 * method: findMailbagsforFlightSegments
									 * to change the assigned flight status to TBA if mailbag present
									 * CR Id: ICRD-84046
									 * @param operationalFlightVO
									 * @param segments
									 * @return boolean
									 * @throws SystemException
									 * @throws PersistenceException
									 */
									public boolean findMailbagsforFlightSegments(OperationalFlightVO operationalFlightVO,
											Collection<FlightSegmentVO> segments,String cancellation) throws SystemException,PersistenceException;
									/**
									 *
									 * 	Method		:	MailTrackingDefaultsDAO.findMailBagsForTransportCompletedResdit
									 *	Added by 	:
									 * 	Used for 	:
									 *	Parameters	:	@param operationalFlightVO
									 *	Parameters	:	@return
									 *	Parameters	:	@throws SystemException
									 *	Parameters	:	@throws PersistenceException
									 *	Return type	: 	Collection<MailbagVO>
									 */
									public Collection<MailbagVO> findMailBagsForTransportCompletedResdit(
											OperationalFlightVO operationalFlightVO) throws SystemException,
											PersistenceException;
									/**
									 *
									 * 	Method		:	MailTrackingDefaultsDAO.findUldsForTransportCompletedResdit
									 *	Added by 	:
									 * 	Used for 	:
									 *	Parameters	:	@param operationalFlightVO
									 *	Parameters	:	@return
									 *	Parameters	:	@throws SystemException
									 *	Parameters	:	@throws PersistenceException
									 *	Return type	: 	Collection<ContainerDetailsVO>
									 */
									public Collection<ContainerDetailsVO> findUldsForTransportCompletedResdit(
											OperationalFlightVO operationalFlightVO) throws SystemException,
											PersistenceException;
									/**
									 *
									 * @param handoverVO
									 * @return
									 * @throws SystemException
									 * @throws PersistenceException
									 */
									public Collection<OperationalFlightVO> findOperationalFlightForMRD(
											HandoverVO handoverVO) throws SystemException,
											PersistenceException ;
									/**
									 *
									 * @param resditEvents
									 * @return
									 * @throws SystemException
									 * @throws PersistenceException
									 */
									public Collection<CarditVO>  findCarditDetailsForResdit (
											Collection<ResditEventVO> resditEvents) throws SystemException, PersistenceException;

									/**
									 *
									 * @param companyCode
									 * @param paCode
									 * @return
									 * @throws SystemException
									 * @throws PersistenceException
									 */
									PostalAdministrationVO findPartialResditFlagForPA(String companyCode,
											String paCode) throws SystemException, PersistenceException;

									/**
									 *
									 * @param carditEnqFilterVO
									 * @return
									 * @throws SystemException
									 * @throws PersistenceException
									 */
									public Collection<ConsignmentRoutingVO> findConsignmentRoutingDetails(CarditEnquiryFilterVO carditEnqFilterVO)
										throws SystemException,PersistenceException;

									/**
									 *
									 * @param companyCode
									 * @param ExchangeOfficeCode
									 * @return
									 * @throws SystemException
									 * @throws PersistenceException
									 */
									public String findCityForOfficeOfExchange(String companyCode,String ExchangeOfficeCode )throws SystemException, PersistenceException ;

									/**
									 *
									 * @param carditEnqFilterVO
									 * @param airport
									 * @return
									 * @throws SystemException
									 * @throws PersistenceException
									 */
									public Collection<TransportInformationVO> findRoutingDetailsFromCardit(CarditEnquiryFilterVO carditEnqFilterVO,String airport)
									throws SystemException,PersistenceException;

									/**
									 *
									 * @param resditEventVO
									 * @return
									 * @throws SystemException
									 * @throws PersistenceException
									 */
									Collection<TransportInformationVO> findTransportDetailsForMailbag(
											ResditEventVO resditEventVO) throws SystemException,
											PersistenceException;
									/**
									 *
									 * @param resditEventVO
									 * @return
									 * @throws SystemException
									 * @throws PersistenceException
									 */
									Collection<ReceptacleInformationVO> findMailbagDetailsForResdit(
											ResditEventVO resditEventVO) throws SystemException,
											PersistenceException;

									/**
									 *
									 * @param resditEventVO
									 * @return
									 * @throws SystemException
									 * @throws PersistenceException
									 */
									Collection<ReceptacleInformationVO> findMailbagDetailsForXXResdit(
											ResditEventVO resditEventVO) throws SystemException,
											PersistenceException;

									/**
									 *
									 * @param resditEventVO
									 * @return
									 * @throws SystemException
									 * @throws PersistenceException
									 */
									Collection<TransportInformationVO> findTransportDetailsForULD(
											ResditEventVO resditEventVO) throws SystemException,
											PersistenceException;

									/**
									 *
									 * @param resditEventVO
									 * @return
									 * @throws SystemException
									 * @throws PersistenceException
									 */
									 public Collection<ReceptacleInformationVO> findMailbagDetailsForSBUldsFromCardit(
												ResditEventVO resditEventVO) throws SystemException,
												PersistenceException;

									 /**
									  *
									  * @param resditEventVO
									  * @return
									  * @throws SystemException
									  * @throws PersistenceException
									  */
									 Collection<ContainerInformationVO> findULDDetailsForResdit(
												ResditEventVO resditEventVO) throws SystemException,
												PersistenceException;

									 /**
									  *
									  * @param resditEventVO
									  * @return
									  * @throws SystemException
									  * @throws PersistenceException
									  */
									 Collection<ContainerInformationVO> findULDDetailsForResditWithoutCardit(
												ResditEventVO resditEventVO) throws SystemException,
												PersistenceException;
										/**
										 * @author a-1936
										 * This method is used to find out the other DSns for the Same AWb excluding the One
										 * passed as the Filter
										 * @param dsnVo
										 * @param containerDetailsVO
										 * @return
										 * @throws SystemException
										 */
										Collection<DespatchDetailsVO> findOtherDSNsForSameAWB(DSNVO dsnVo
												,ContainerDetailsVO containerDetailsVO)
										 throws SystemException,PersistenceException;
										/*Map<Long, Collection<MailbagHistoryVO>> findCarditDetailsOfMailbagMap(String companyCode,
												long[] malseqnum) throws SystemException, PersistenceException;*/
										Map<Long, Collection<MailbagHistoryVO>> findMailbagHistoriesMap(String companyCode,
												long[] malseqnum) throws SystemException, PersistenceException;

										Map<Long, MailInConsignmentVO> findAllConsignmentDetailsForMailbag(String companyCode,
												long[] malseqnum) throws SystemException, PersistenceException;

										  /**
										   * @author a-1936
										   * This  method is used to find the mailbags in the Container for the Manifest
										   * @param containers
										   * @return
										   * @throws SystemException
										   * @throws PersistenceException
										   */
										  Collection<ContainerDetailsVO> findMailbagsInContainerForImportManifest(Collection<ContainerDetailsVO> containers)
										  throws SystemException,PersistenceException;

										/**
										 * @author A-6371
										 * For fetching mailonhandlist
										 */
										public Page<MailOnHandDetailsVO> findMailOnHandDetails(SearchContainerFilterVO searchContainerFilterVO,int pageNumber)
										throws SystemException, PersistenceException;
										
										/**
										 *
										 * @param mailAuditHistoryFilterVO
										 * @return
										 * @throws SystemException
										 */
										Collection<MailBagAuditHistoryVO> findMailAuditHistoryDetails(MailAuditHistoryFilterVO mailAuditHistoryFilterVO)
										throws SystemException;
										/**
										 *
										 * @param entities
										 * @param isForHistory
										 * @param companyCode
										 * @return
										 * @throws SystemException
										 */
										HashMap<String,String> findAuditTransactionCodes(Collection<String> entities, boolean isForHistory,
												String companyCode) throws SystemException;
										
										/**
										 * @author a-5526 This method Checks whether the container is already
										 *         assigned to a flight/destn from the current airport in arrival flow
										 * @param companyCode
										 * @param containerNumber
										 * @param pol
										 * @throws SystemException
										 * @throws PersistenceException
										 */
										ContainerAssignmentVO findContainerAssignmentForArrival(String companyCode,
												String containerNumber, String pol) throws SystemException,
												PersistenceException;
										
										
										/**
										 * @author A-7871 
										 * for ICRD-257316
										 * @param containerDetailsVO
										 * @throws BusinessDelegateException
										 */
										public int findMailbagcountInContainer(ContainerVO containerVO)
												throws SystemException, RemoteException;

/**
 * @author A-5526
 * @param fileUploadFilterVO
 * @return
 */
										public String processMailOperationFromFile(
			FileUploadFilterVO fileUploadFilterVO) throws SystemException,
			PersistenceException;

	/**
	 * fetchDataForOfflineUpload
	 * 
	 * @param companyCode
	 * @param fileType
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<MailUploadVO> fetchDataForOfflineUpload(
			String companyCode, String fileType) throws SystemException,
			PersistenceException;

	/**
	 * removeDataFromTempTable
	 * 
	 * @param fileUploadFilterVO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public void removeDataFromTempTable(FileUploadFilterVO fileUploadFilterVO)
			throws SystemException, PersistenceException;
	
	/**
	 * @author a-7794 This method is used to list the Audit details
	 * @param mailAuditFilterVO
	 * @return Collection<AuditDetailsVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 * ICRD-229934
	 */
	Collection<AuditDetailsVO> findCONAuditDetails(
			MailAuditFilterVO mailAuditFilterVO) throws SystemException,
			PersistenceException;

	/**
	 * 
	 * 	Method		:	MailTrackingDefaultsDAO.findMailbagIdForMailTag
	 *	Added by 	:	a-6245 on 22-Jun-2017
	 * 	Used for 	:
	 *	Parameters	:	@param mailbagVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException 
	 *	Return type	: 	MailbagVO
	 */
	public MailbagVO findMailbagIdForMailTag(
			MailbagVO mailbagVO) throws SystemException,
			PersistenceException;

	/**
	 * 	Method		:	MailTrackingDefaultsDAO.findOfficeOfExchangeForPA
	 *	Added by 	:	a-6245 on 10-Jul-2017
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param paCode
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException 
	 *	Return type	: 	MailbagVO
	 */
	public HashMap<String,String> findOfficeOfExchangeForPA(String companyCode,
			String paCode)
			throws SystemException, PersistenceException;
	/**
	 * 
	 * 	Method		:	MailTrackingDefaultsDAO.findMailBookingAWBs
	 *	Added by 	:	a-7779 on 24-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@param mailBookingFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException 
	 *	Return type	: 	Collection<MailBookingFlightDetailVO>
	 */
	/*public Collection<MailBookingDetailVO> findMailBookingAWBs(
			MailBookingFilterVO mailBookingFilterVO) throws SystemException, PersistenceException;*/
	/**
	 * 
	 * 	Method		:	MailTrackingDefaultsDAO.fetchBookedFlightDetails
	 *	Added by 	:	a-7779 on 29-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param shipmentPrefix
	 *	Parameters	:	@param masterDocumentNumber
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<MailBookingFlightDetailVO>
	 */
	/*public Collection<MailBookingDetailVO> fetchBookedFlightDetails(
			String companyCode, String shipmentPrefix,
			String masterDocumentNumber)throws SystemException, PersistenceException;*/
/**
 * 
 * 	Method		:	MailTrackingDefaultsDAO.findAwbAtachedMailbagDetails
 *	Added by 	:	a-7779 on 31-Aug-2017
 * 	Used for 	:
 *	Parameters	:	@param companyCode
 *	Parameters	:	@param ownerId
 *	Parameters	:	@param masterDocumentNumber
 *	Parameters	:	@return 
 *	Return type	: 	ScannedMailDetailsVO
 */
	public ScannedMailDetailsVO findAwbAtachedMailbagDetails(ShipmentSummaryVO shipmentSummaryVO,MailFlightSummaryVO mailFlightSummaryVO)throws SystemException, PersistenceException;

/**
 * @author A-7371
 * @param shipmentSummaryVO
 * @param mailFlightSummaryVO
 * @return
 * @throws SystemException 
 */
public List<MailbagVO> findMailBagsforReassign(ShipmentSummaryVO shipmentSummaryVO,MailFlightSummaryVO mailFlightSummaryVO) throws SystemException;
/**
 *
 * @param resditVO
 * @return
 * @throws SystemException
 * @throws PersistenceException
 */
public String findMailboxId(MailResditVO resditVO) throws
SystemException, PersistenceException;
			
	/**
	 * 
	 * 	Method		:	MailTrackingDefaultsDAO.findAwbAtachedMailbagDetailsForOffload
	 *	Added by 	:	a-8061 on 30-Apr-2018
	 * 	Used for 	:
	 *	Parameters	:	@param shipmentSummaryVO
	 *	Parameters	:	@param mailFlightSummaryVO
	 *	Parameters	:	@return 
	 *	Return type	: 	ScannedMailDetailsVO
	 */
		public int findAwbPartialOflPcs(ShipmentSummaryVO shipmentSummaryVO,MailFlightSummaryVO mailFlightSummaryVO)throws SystemException, PersistenceException;
			
		
	/**
	 * 
	 * 	Method		:	MailTrackingDefaultsDAO.findAgentCodeForPA
	 *	Added by 	:	U-1267 on Nov 1, 2017
	 * 	Used for 	:	findAgentCodeForPA
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param officeOfExchange
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException 
	 *	Return type	: 	String
	 */
	public String findAgentCodeForPA(String companyCode, String officeOfExchange)
			throws SystemException, PersistenceException;

	/**
	 * 
	 * 	Method		:	MailTrackingDefaultsDAO.findMailbagVOsForDsnVOs
	 *	Added by 	:	U-1267 on 08-Nov-2017
	 * 	Used for 	:	ICRD-211205
	 *	Parameters	:	@param containerDetailsVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException 
	 *	Return type	: 	Collection <MailbagVO>
	 */
	public Collection <MailbagVO> findMailbagVOsForDsnVOs(ContainerDetailsVO containerDetailsVO)
			throws SystemException, PersistenceException;
	/**
	 * @author A-7871
	 * @param mailbagVOs
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public ArrayList<MailbagVO> findMailTagDetails(ArrayList<MailbagVO> mailbagVOs)throws SystemException,
	PersistenceException;

/**
 * @author A-8061
 * @param companyCode
 * @param mailbagId
 * @return
 * @throws SystemException
 * @throws PersistenceException
 */
	public Collection<MailbagHistoryVO> findMailbagResditEvents(String companyCode, String mailbagId) throws SystemException, PersistenceException;

/**
 * @author A-8061
 * @param carditEnquiryFilterVO
 * @return
 */
public String[] findGrandTotals(CarditEnquiryFilterVO carditEnquiryFilterVO)throws SystemException, PersistenceException;

/**
 * @author A-6986
 * @param mailServiceLevelVO
 * @return
 */	
public String findMailServiceLevelForIntPA(MailServiceLevelVO mailServiceLevelVO) throws SystemException, PersistenceException;
/**
 * @author A-6986
 * @param mailServiceLevelVO
 * @return
 */	
public String findMailServiceLevelForDomPA(MailServiceLevelVO mailServiceLevelVO) throws SystemException, PersistenceException;
/**
 * 
 * 	Method		:	MailTrackingDefaultsDAO.listPostalCalendarDetails
 *	Added by 	:	A-8164 on 04-Jul-2018
 * 	Used for 	:	ICRD-236925
 *	Parameters	:	@param uSPSPostalCalendarFilterVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws PersistenceException 
 *	Return type	: 	Collection<USPSPostalCalendarVO>
 */
public Collection<USPSPostalCalendarVO> listPostalCalendarDetails(
		USPSPostalCalendarFilterVO uSPSPostalCalendarFilterVO) throws SystemException, PersistenceException;
/**
 * @author A-6986
 * @param contractFilterVO
 * @param pageNumber
 * @return
 */	
public Collection<GPAContractVO> listContractdetails(GPAContractFilterVO contractFilterVO)
		throws SystemException, PersistenceException;
			
/**
 * @author A-6986
 * @param contractFilterVO
 * @param pageNumber
 * @return
 */	
public Collection<GPAContractVO> listODForContract(GPAContractFilterVO contractFilterVO)
		throws SystemException, PersistenceException;
 /**
 * @author A-7371
 * @param mailbagVO
 * @return
 * @throws SystemException
 * @throws PersistenceException
 */
public Timestamp fetchSegmentSTA(MailbagVO mailbagVO)throws SystemException, PersistenceException; 
/**
 * @author A-7371
 * @param mailbagVO
 * @return
 * @throws SystemException
 * @throws PersistenceException
 */
public String fetchHandlingConfiguration(MailbagVO mailbagVO)throws SystemException, PersistenceException;

/**
 * @author A-7371 
 * @param mailbagVO
 * @return
 * @throws SystemException
 * @throws PersistenceException
 */
public String fetchRDTOffset(MailbagVO mailbagVO,String paCodDom)throws SystemException, PersistenceException;
			
			
/**
 * @author A-6986
 * @param mailHandoverFilterVO
 * @return
 */	
public Page<MailHandoverVO> findMailHandoverDetails(MailHandoverFilterVO mailHandoverFilterVO,int pageNumber)
		throws SystemException, PersistenceException;

/**
 * @author A-7871
 * Used for ICRD-240184
 * @param currentAirport
 * @param paCode
 * @return receiveFromTruckEnabled
 * @throws SystemException 
 */	
public String checkReceivedFromTruckEnabled(String currentAirport,String orginAirport,String paCode,LocalDate dspDate) throws SystemException;

/**
 * 	Method		:	MailTrackingDefaultsDAO.findRotingIndex
 *	Added by 	:	A-7531 on 30-Oct-2018
 * 	Used for 	:
 *	Parameters	:	@param routeIndex
 *	Parameters	:	@param companycode
 *	Parameters	:	@return
 *	Return type	: 	RoutingIndexVO
 */
public Collection<RoutingIndexVO> findRoutingIndex(RoutingIndexVO routingIndexVO)throws SystemException, PersistenceException;


/***
 * @author A-7794
 * @param destAirport
 * @return
 * @throws SystemException
 */
public String checkScanningWavedDest(MailbagVO mailbagVO)throws SystemException;

/**
 * @author A-8236
 * ICRD-255189
 * @param companyCode
 * @param officeOfExchanges
 * @return
 * @throws SystemException
 */
public HashMap<String,String> findAirportForOfficeOfExchange(String companyCode, Collection<String> officeOfExchanges) throws SystemException, PersistenceException;

/**
 * @author A-8061
 * @param shipmentSummaryVO
 * @param mailFlightSummaryVO
 * @return
 * @throws SystemException
 */
public ScannedMailDetailsVO findMailbagsForAWB(ShipmentSummaryVO shipmentSummaryVO,MailFlightSummaryVO mailFlightSummaryVO) throws SystemException;


/**
 * @author A-8061
 * @param mailbagVO
 * @return
 * @throws SystemException
 */
public Boolean isMailAsAwb(MailbagVO mailbagVO)throws SystemException;


public ScannedMailDetailsVO findAWBAttachedMailbags(ShipmentSummaryVO shipmentSummaryVO,
		MailFlightSummaryVO mailFlightSummaryVO)throws SystemException;


/**
 * @author A-5526
 * @param operationalFlightVO
 * @return
 * @throws SystemException
 */
public Collection<ContainerVO> findEmptyULDsInAssignedFlight(OperationalFlightVO operationalFlightVO) throws SystemException;

public Page<MailAcceptanceVO> findOutboundFlightsDetails(OperationalFlightVO operationalFlightVO, int pageNumber)
	   throws SystemException, PersistenceException;
public Page<ContainerDetailsVO> findContainerDetails(OperationalFlightVO operationalFlightVO,int pageNumber)
	   throws SystemException, PersistenceException;
public Page<MailbagVO> findMailbagsinContainer(ContainerDetailsVO containerDetailsVO,int pageNumber)
	   throws SystemException, PersistenceException;
public Page<DSNVO> findMailbagsinContainerdsnview(ContainerDetailsVO containerDetailsVO,int pageNumber)
	   throws SystemException, PersistenceException;
public  MailbagVO findCarditSummaryView(CarditEnquiryFilterVO carditEnquiryFilterVO)
	   throws SystemException, PersistenceException;
public  Page<MailbagVO> findGroupedCarditMails(CarditEnquiryFilterVO carditEnquiryFilterVO, int pageNumber)
throws SystemException,PersistenceException;
public   MailbagVO findLyinglistSummaryView(MailbagEnquiryFilterVO mailbagEnquiryFilterVO)
	   throws SystemException, PersistenceException;
public Page<MailbagVO>  findGroupedLyingList(MailbagEnquiryFilterVO mailbagEnquiryFilterVO,int pageNumber)
	   throws SystemException, PersistenceException;
public Page<MailAcceptanceVO> findOutboundCarrierDetails(OperationalFlightVO operationalFlightVO, int pageNumber)
	   throws SystemException, PersistenceException;
public Page<MailbagVO> getMailbagsinCarrierContainer(ContainerDetailsVO containerDetailsVO,int pageNumber)
	   throws SystemException, PersistenceException;
public Page<DSNVO>  getMailbagsinCarrierContainerdsnview(ContainerDetailsVO containerDetailsVO,int pageNumber)
	   throws SystemException, PersistenceException;
public Collection<DSNVO>  getDSNsForContainer(ContainerDetailsVO containerDetailsVO)
	   throws SystemException, PersistenceException;
public Collection<DSNVO> getDSNsForCarrier(ContainerDetailsVO containerDetailsVO)
		   throws SystemException, PersistenceException;
/**
 * 
 * 	Method		:	MailTrackingDefaultsDAO.listFlightDetails
 *	Added by 	:	A-8164 on 25-Sep-2018
 * 	Used for 	:
 *	Parameters	:	@param mailArrivalVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	Collection<MailArrivalVO>
 */
public Page<MailArrivalVO> listFlightDetails(MailArrivalVO mailArrivalVO)throws SystemException;

public Collection<MailArrivalVO> listManifestDetails(MailArrivalVO mailArrivalVO)throws SystemException;

/**
 * 
 * 	Method		:	MailTrackingDefaultsDAO.findArrivedContainersForInbound
 *	Added by 	:	A-8164 on 29-Dec-2018
 * 	Used for 	:
 *	Parameters	:	@param mailArrivalFilterVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	Collection<ContainerDetailsVO>
 */
public Page<ContainerDetailsVO> findArrivedContainersForInbound(MailArrivalFilterVO mailArrivalFilterVO)throws SystemException;

/**
 * 
 * 	Method		:	MailTrackingDefaultsDAO.findArrivedMailbagsForInbound
 *	Added by 	:	A-8164 on 29-Dec-2018
 * 	Used for 	:
 *	Parameters	:	@param mailArrivalFilterVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	Collection<ContainerDetailsVO>
 */
public Page<MailbagVO> findArrivedMailbagsForInbound(MailArrivalFilterVO mailArrivalFilterVO)throws SystemException;

/**
 * 
 * 	Method		:	MailTrackingDefaultsDAO.findArrivedDsnsForInbound
 *	Added by 	:	A-8164 on 29-Dec-2018
 * 	Used for 	:
 *	Parameters	:	@param mailArrivalFilterVO
 *	Parameters	:	@return 
 *	Return type	: 	Collection<DSNVO>
 */
public Page<DSNVO> findArrivedDsnsForInbound(MailArrivalFilterVO mailArrivalFilterVO)throws SystemException;

/**
 * 
 * 	Method		:	MailTrackingDefaultsDAO.listCarditDsnDetails
 *	Added by 	:	A-8164 on 04-Sep-2019
 * 	Used for 	:	List Cardit DSN Details
 *	Parameters	:	@param dsnEnquiryFilterVO
 *	Parameters	:	@return 
 *	Return type	: 	Page<DSNVO>
 * @throws SystemException 
 */
public Page<DSNVO> listCarditDsnDetails(DSNEnquiryFilterVO dsnEnquiryFilterVO) throws SystemException;


/**
 * @author A-6986
 * @param incentiveConfigurationFilterVO
 * @return
 * @throws SystemException
 * @throws PersistenceException
 */
public Collection<IncentiveConfigurationVO> findIncentiveConfigurationDetails (
		IncentiveConfigurationFilterVO incentiveConfigurationFilterVO)
		throws SystemException, PersistenceException;

/**
 *
 * 	Method		:	MailTrackingDefaultsDAO.findRunnerFlights
 *	Added by 	:	A-5526 on 12-Oct-2018
 * 	Used for 	:   ICRD-239811
 *	Parameters	:	@param runnerFlightFilterVO
 *	Parameters	:	@return
 *	Return type	: 	Page<RunnerFlightVO>
 */
public Page<RunnerFlightVO> findRunnerFlights(RunnerFlightFilterVO runnerFlightFilterVO)
		throws SystemException, PersistenceException;
/**
 *
 * 	Method		:	MailTrackingDefaultsDAO.findRunnerFlights
 *	Added by 	:	A-5526 on 12-Oct-2018
 * 	Used for 	:   ICRD-239811
 *	Parameters	:	@param RunnerFlightVO, RunnerFlightULDVO
 *	Parameters	:	@return
 *	Return type	: 	ContainerVO
 */

public ContainerVO findContainerDetails(RunnerFlightVO runnerFlightVO,
		RunnerFlightULDVO runnerFlightULDVO) throws SystemException, PersistenceException;


/**
 * @author A-8464
 * @param findCloseoutDate
 * @return closeOutDate
 * @throws SystemException
 * @throws PersistenceException
 */
public Timestamp findCloseoutDate(String mailbagId) throws SystemException, PersistenceException ;

/**
 * @author A-8464
 * @param findServiceStandard
 * @return serviceStandard
 * @throws SystemException
 * @throws PersistenceException
 */
public int findServiceStandard(MailbagVO mailbagVo) throws SystemException, PersistenceException;

/**
 *
 * 	Method		:	MailTrackingDefaultsDAO.findMailbagsForTruckFlight
 *	Added by 	:	A-7929 on 23-Oct-2018
 * 	Used for 	:   ICRD-239811
 *	Parameters	:	@param mailbagEnquiryFilterVO, pageNumber
 *	Parameters	:	@return
 *	Return type	: 	Page<MailbagVO>
 * @throws SystemException
 */
public Page<MailbagVO> findMailbagsForTruckFlight(MailbagEnquiryFilterVO mailbagEnquiryFilterVO, int pageNumber) throws SystemException;


/**
 *
 * 	Method		:	MailTrackingDefaultsDAO.findAllMailbagsForTruckFlight
 *	Added by 	:	A-7929 on 23-Oct-2018
 * 	Used for 	:   ICRD-239811
 *	Parameters	:	@param mailbagEnquiryFilterVO, pageNumber
 *	Parameters	:	@return
 *	Return type	: 	Page<MailbagVO>
 * @throws SystemException
 */
public Page<MailbagVO> findAllMailbagsForTruckFlight(MailbagEnquiryFilterVO mailbagEnquiryFilterVO, int pageNumber) throws SystemException;

/**
 *
 * @param filterVO
 * @param pageNumber
 * @return
 * @throws SystemException
 */
public Page<ForceMajeureRequestVO> listForceMajeureApplicableMails(ForceMajeureRequestFilterVO filterVO, int pageNumber)
		 throws  SystemException,PersistenceException ;


/**
 *
 * @param filterVO
 * @return
 * @throws SystemException
 * @throws PersistenceException
 */
public String saveForceMajeureRequest(ForceMajeureRequestFilterVO filterVO)
		 throws  SystemException,PersistenceException;


/**
*
* @param filterVO
* @param pageNumber
* @return
* @throws SystemException
*/
public Page<ForceMajeureRequestVO> listForceMajeureDetails(ForceMajeureRequestFilterVO filterVO, int pageNumber)
		 throws  SystemException,PersistenceException ;

/**
*
* @param filterVO
* @param pageNumber
* @return
* @throws SystemException
*/
public Page<ForceMajeureRequestVO> listForceMajeureRequestIds(ForceMajeureRequestFilterVO filterVO, int pageNumber)
		 throws  SystemException,PersistenceException ;

/**
 * 
 * @param requestVO
 * @throws SystemException
 * @throws PersistenceException
 */
public String updateForceMajeureRequest(ForceMajeureRequestFilterVO requestVO)
		 throws  SystemException,PersistenceException ;


/**
 * @param operationalFlightVO
 * @return
 * @throws SystemException
 * @throws PersistenceException
 */
Collection<ContainerVO> findAllContainersInAssignedFlight(
		OperationalFlightVO operationalFlightVO) throws SystemException,
		PersistenceException;
			
/**
 * @author A-8514 
 * @param scannedMailDetailsVO
 * Added as part of ICRD-229584 
 * @return
 * @throws SystemException
 * @throws PersistenceException
 */
public MailbagInULDForSegmentVO getManifestInfo(ScannedMailDetailsVO scannedMailDetailsVO)throws SystemException, PersistenceException ;

/***
 * @author A-7794
 * @param fileUploadFilterVO
 * @return
 * @throws SystemException
 * @throws PersistenceException
 */
public String processMailDataFromExcel(FileUploadFilterVO fileUploadFilterVO) throws SystemException,
PersistenceException;
/***
 * @author A-7794
 * @param companyCode
 * @param fileType
 * @return
 * @throws SystemException
 */
public Collection<ConsignmentDocumentVO> fetchMailDataForOfflineUpload(String companyCode, String fileType)throws SystemException;


//Added by A-8464 for ICRD-243079
public List<MailMonitorSummaryVO> getServiceFailureDetails(MailMonitorFilterVO filterVO) throws SystemException, PersistenceException;
public List<MailMonitorSummaryVO> getOnTimePerformanceDetails(MailMonitorFilterVO filterVO) throws SystemException, PersistenceException;
public List<MailMonitorSummaryVO> getForceMajeureCountDetails(MailMonitorFilterVO filterVO) throws SystemException, PersistenceException;
public Page<MailbagVO>  getPerformanceMonitorMailbags(MailMonitorFilterVO filterVO,String type,int pageNumber)
		   throws SystemException, PersistenceException;

/**
 * 
 * 	Method		:	MailTrackingDefaultsDAO.validateFrmToDateRange
 *	Added by 	:	A-8527 on 14-March-2019
 * 	Used for 	:	ICRD-262471
 *	Parameters	:	@param uSPSPostalCalendarFilterVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws PersistenceException 
 *	Return type	: 	Collection<USPSPostalCalendarVO>
 */
public Collection<USPSPostalCalendarVO> validateFrmToDateRange(
		USPSPostalCalendarFilterVO uSPSPostalCalendarFilterVO) throws SystemException, PersistenceException;

/**
	 * 
	 * 	Method		:	MailTrackingDefaultsDAO.findMailbagDetailsForMailbagEnquiryHHT
	 *	Added by 	:   A-8464 on 26-Mar-2018
	 * 	Used for 	:	ICRD-273761
	 *	Parameters	:	@param mailbagEnquiryFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException
	 */
	public MailbagVO findMailbagDetailsForMailbagEnquiryHHT(MailbagEnquiryFilterVO mailbagEnquiryFilterVO) 
			throws SystemException, PersistenceException;

/**
 * @author A-7371
 * @param uspsPostalCalendarFilterVO
 * @return
 * @throws SystemException
 * @throws PersistenceException
 */
public USPSPostalCalendarVO findInvoicPeriodDetails(USPSPostalCalendarFilterVO uspsPostalCalendarFilterVO)throws SystemException, PersistenceException;


/**
 * 	Method		:	MailTrackingDefaultsDAO.findDuplicateMailbag
 *	Added by 	:	A-7531 on 16-May-2019
 * 	Used for 	:
 *	Parameters	:	@param companyCode
 *	Parameters	:	@param mailBagId
 *	Parameters	:	@return 
 *	Return type	: 	ArrayList<MailbagVO>
 */
public ArrayList<MailbagVO> findDuplicateMailbag(String companyCode, String mailBagId)throws SystemException;

/**
 * @author A-8061
 * @param mailbagVO
 * @return
 * @throws SystemException
 */
public String findServiceResponsiveIndicator(MailbagVO mailbagVO)throws SystemException;
/**
 * @author A-8923
 * @param mailbagVO
 * @return
 * @throws SystemException
 */
public String findMailboxIdFromConfig(MailbagVO mailbagVO) throws SystemException;
public Page<MailbagVO> findDeviationMailbags(MailbagEnquiryFilterVO mailbagEnquiryFilterVO, int pageNumber) throws SystemException, PersistenceException;



public Collection<MailEventVO> findMailEvent(MailEventPK maileventPK) throws SystemException;


/**
 * 	Method		:	MailTrackingDefaultsDAO.getTempCarditMessages
 *	Added by 	:	A-6287 on 01-Mar-2020
 * 	Used for 	:
 *	Parameters	:	@param companyCode
 *	Parameters	:	@return 
 *	Return type	: 	Collection<CarditTempMsgVO>
 * @throws SystemException 
 */
public Collection<CarditTempMsgVO> getTempCarditMessages(String companyCode,
														 String includeMailBoxIdr,String excludeMailBoxIdr,
														 String includedOrigins,String excludedOrigins,
														 int pageSize,
														 int noOfDays)
		throws SystemException;
	 
public int findbulkcountInFlight(ContainerDetailsVO containerDetailsVO) throws SystemException;

//Added by A-8672 as part of IASCB-42757
public String findRoutingDetailsForConsignment(MailbagVO mailbagVO);
//added as part of	IASCB-47333
public String isValidContainerForULDlevelArrivalOrDelivery(ContainerDetailsVO containerDetailsVO) throws SystemException;

/**
 * @author A-5526
 * Added as part of CRQ IASCB-44518
 * @param containerNumber
 * @param companyCode
 * @return
 * @throws SystemException
 */
public Collection<MailbagVO> findMailbagsFromOALinResditProcessing(String containerNumber,String companyCode) throws SystemException,PersistenceException;

/**
 * @author U-1439
 * @param containerNumber
 * @param companyCode
 * @param fromDate
 * @param toDate
 * @return
 * @throws SystemException
 * @throws PersistenceException
 */
public Collection<MailbagVO> findMailbagsForPABuiltContainerSave(String containerNumber, String companyCode,
		LocalDate fromDate,LocalDate toDate)throws SystemException,PersistenceException;

    /**
     * @author a-9529
     * This method  is used to find out the Mail Bags in the Containers from in bound react screen
     * @param containers
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
    Collection<ContainerDetailsVO> findMailbagsInContainerFromInboundForReact(Collection<ContainerDetailsVO> containers)
            throws SystemException,PersistenceException;

	

public String findContainerInfoForDeviatedMailbag(ContainerDetailsVO containerDetailsVO, long mailSequenceNumber);
/**
 * 
 * @param mailbagVO
 * @return
 * @throws SystemException
 * @throws PersistenceException
 */
public MailbagVO findNotupliftedMailsInCarrierforDeviationlist(MailbagVO mailbagVO)
		throws SystemException, PersistenceException ;
/**
*
* @param filterVO
* @param pageNumber
* @return
* @throws SystemException
*/
public Collection<ForceMajeureRequestVO> findApprovedForceMajeureDetails(String companyCode, String mailBagId, long mailSequenceNumber)
		 throws  SystemException,PersistenceException ;
public String findMailHandoverDetails(MailHandoverVO mailHandoverVO) throws SystemException, PersistenceException;


public String findMailboxIdForPA(MailbagVO mailbagVO)throws SystemException, PersistenceException;

/**
 * @author A-8353 
 * @param MailbagVO
 * @return
 * @throws SystemException
 * @throws PersistenceException
 */
public MailbagInULDForSegmentVO getManifestInfoForNextSeg(MailbagVO mailbagVO)throws SystemException, PersistenceException ;
/**
 * @author A-8353 
 * @param String
 *  @param String
 * @return
 * @throws SystemException
 * 
 */
public String checkMailInULDExistForNextSeg(String containerNumber,String airpotCode,String companyCode) throws SystemException;

/**
 * 
 * @param transferManifestVO
 * @return
 * @throws SystemException
 */
public Collection<ConsignmentDocumentVO> findTransferManifestConsignmentDetails(TransferManifestVO transferManifestVO) throws SystemException;

/**
*
* @param consignmentNumber
* @param companyCode
* @return
* @throws SystemException
* @throws PersistenceException
*/
public ConsignmentDocumentVO findConsignmentScreeningDetails(String consignmentNumber, String companyCode,String poaCode) throws SystemException, PersistenceException;

			
public List<TransferManifestVO> findTransferManifestDetailsForTransfer(
		String companyCode,String tranferManifestId)throws SystemException, PersistenceException;
/**
*
* @param consignmentNumber
* @return
* @throws SystemException
*/
public ConsignmentDocumentVO generateSecurityReport(String consignmentNumber, String paCode) throws SystemException;

/**
 * @author A-8353
 * @param companyCode
 * @param malSeqNum
 * @return
 * @throws SystemException
 * @throws PersistenceException
 */
public String findTransferManifestId(String companyCode,long malSeqNum)throws SystemException, PersistenceException;
/**
 * 
 * @param mailbagVO
 * @return
 * @throws SystemException
 */
public MailbagVO findMailbagBillingStatus(MailbagVO mailbagVO) throws SystemException;

/**
 * 	Method		:	MailTrackingDefaultsDAO.generateConsignmentSummaryReport
 *	Added by 	:	A-9084 on 12-Nov-2020
 * 	Used for 	:
 *	Parameters	:	@param consignmentFilterVO
 *	Parameters	:	@return 
 *	Return type	: 	ConsignmentDocumentVO
 */
public ConsignmentDocumentVO generateConsignmentSummaryReport(ConsignmentFilterVO consignmentFilterVO) throws SystemException;


	public String saveUploadedForceMajeureData(FileUploadFilterVO fileUploadFilterVO) throws SystemException;



	public Collection<ImportFlightOperationsVO> findInboundFlightOperationsDetails(ImportOperationsFilterVO filterVO,
				Collection<ManifestFilterVO> manifestFilterVOs) throws SystemException, PersistenceException;
	public Collection<OffloadULDDetailsVO> findOffloadULDDetailsAtAirport(com.ibsplc.icargo.business.operations.flthandling.vo.OffloadFilterVO filterVO)
			throws SystemException, PersistenceException ;
	public Collection<ManifestVO> findExportFlightOperationsDetails(ImportOperationsFilterVO filterVO,
			Collection<ManifestFilterVO> manifestFilterVOs) throws SystemException, PersistenceException;


	public Collection<ConsignmentDocumentVO> fetchConsignmentDetailsForUpload(FileUploadFilterVO fileUploadFilterVO)
			throws SystemException;


	public Collection<ContainerDetailsVO> findContainerJourneyID(ConsignmentFilterVO consignmentFilterVO) throws SystemException;

	public Collection<MailbagVO> getFoundArrivalMailBags(MailArrivalVO mailArrivalVO)
			throws SystemException, PersistenceException;
	/**
	 * @author A-9084
	 * @param mailAuditFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 * @throws SystemException 
	 */
	public Collection<AuditDetailsVO> findAssignFlightAuditDetails(MailAuditFilterVO mailAuditFilterVO) throws BusinessDelegateException, SystemException;

	public Collection<MailAcceptanceVO>  findContainerVOs(MailAcceptanceVO mailAcceptanceVO)
			throws SystemException, PersistenceException;
	
	public MailbagVO listmailbagSecurityDetails(MailScreeningFilterVO mailScreeningFilterVo) throws SystemException;

    public String findAgentCodeFromUpuCode(String cmpCode,String upuCode) throws SystemException;
	public int findScreeningDetails(String mailBagId,String companyCode) throws SystemException;
	public String findRoutingDetails(String companyCode, long malseqnum) throws SystemException;
   
public Collection<MailbagVO> findAWBAttachedMailbags(MailbagVO mailbag,String consignmentNumber) throws SystemException;
	public Collection<MailInConsignmentVO> findMailInConsignment(ConsignmentFilterVO consignmentFilterVO) throws SystemException;  
    /**
    * @author A-8353
    * @param cmpcod
    * @param malSeqNum
    * @param agentId
    * @return
    * @throws SystemException
    */
	public ConsignmentScreeningVO findRegulatedCarrierForMailbag(String cmpcod, long malSeqNum)
			throws SystemException;
   

public MailbagVO findMailbagDetails(String mailId,String companyCode) throws SystemException;

/**
 * @author U-1532
 * findLatestContainerAssignment
 * @param scannedMailDetailsVO
 * @return
 */
	public ContainerAssignmentVO findLatestContainerAssignmentForUldDelivery(ScannedMailDetailsVO scannedMailDetailsVO) throws SystemException,PersistenceException;



/**
 * @author A-10647
 * @param FlightLoadPlanContainerVO
 * @return FlightLoadPlanContainerVOs
 * @throws SystemException
 */
	public Collection<FlightLoadPlanContainerVO> findPreviousLoadPlanVersionsForContainer(
			FlightLoadPlanContainerVO loadPlanVO)
			throws SystemException;


 /**
    * @author A-9477
    * @param SearchContainerFilterVO
    * @return FlightLoadPlanContainerVO
    * @throws SystemException
    */	
Collection<FlightLoadPlanContainerVO> findLoadPlandetails(SearchContainerFilterVO searchContainerFilterVO)
			throws SystemException;
			
public Collection<ConsignmentScreeningVO> findRAacceptingForMailbag(String companyCode, long mailSequenceNumber)
			throws SystemException;

public String findRoutingDetailsForMailbag(String companyCode, long malseqnum, String airportCode) throws SystemException; 	
  /**
    * @author A-8353
    * @param consignmentScreeningVO
    * @return
 * @throws SystemException 
    */
	public long findLatestRegAgentIssuing(ConsignmentScreeningVO consignmentScreeningVO) throws SystemException;

    /**
     * @author A-8353
     * @param companyCode
     * @param screeningLocation
     * @return 
     * @throws SystemException 
     */
    public Collection<ConsignmentScreeningVO> findScreeningMethodsForStampingRegAgentIssueMapping(ConsignmentScreeningVO consignmentScreeningVO) throws SystemException;
    
    public Collection<com.ibsplc.icargo.business.operations.flthandling.vo.OperationalFlightVO> fetchMailIndicatorForProgress(
			Collection<FlightListingFilterVO> flightListingFilterVOs) throws SystemException;

	/**
	 * @param companyCode
	 * @return PostalAdministrationVO
	 * @throws SystemException
	 * @throws PersistenceException
	 * @author 204082
	 * Added for IASCB-159276 on 27-Sep-2022
	 */
	public Collection<PostalAdministrationVO> getPADetails(String companyCode) throws SystemException, PersistenceException;


	/**
	 * @param companyCode
	 * @return PostalAdministrationVO
	 * @throws SystemException
	 * @throws PersistenceException
	 * @author 204083
	 * Added for IASCB-159276 on 27-Sep-2022
	 */
	Collection<OfficeOfExchangeVO> getOfficeOfExchangeDetails(String companyCode) throws SystemException, PersistenceException;


	/**
    * @author A-8353
    * @param securityScreeningValidationFilterVO
    * @throws SystemException 
    */
	public Collection<SecurityScreeningValidationVO>  checkForSecurityScreeningValidation(
			SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO) throws SystemException;

	/**
	 * @param mailMasterDataFilterVO
	 * @return MailbagDetailsVo
	 * @throws SystemException
	 * @throws PersistenceException
	 * @author 204082
	 * Added for IASCB-159267 on 20-Oct-2022
	 */
	public Collection<MailbagDetailsVO> getMailbagDetails(MailMasterDataFilterVO mailMasterDataFilterVO) throws SystemException, PersistenceException;


	/**
	 * @param destinationAirportCode
	 * @return
	 * @throws SystemException
	 * @author 204084
	 * Added as part of CRQ IASCB-164529
	 */
	public Collection<RoutingIndexVO> getPlannedRoutingIndexDetails(String destinationAirportCode) throws SystemException;
public Collection<ConsignmentDocumentVO> generateCN46ConsignmentReport(

			ConsignmentFilterVO consignmentFilterVO) throws SystemException,
			PersistenceException;
public Collection<ConsignmentDocumentVO> generateCN46ConsignmentSummaryReport(ConsignmentFilterVO consignmentFilterVO) throws SystemException;

	/**
	 * @param companyCode
	 * @return MailSubClassVO
	 * @throws SystemException
	 * @throws PersistenceException
	 * @author 204084
	 * Added for IASCB-172483 on 15-Oct-2022
	 */
	public Collection<MailSubClassVO> getSubclassDetails(String companyCode) throws SystemException, PersistenceException;

	/**
	 * @param companyCode
	 * @param airportCode
	 * @return MailbagVO
	 * @throws SystemException
	 * @author 204084
	 * Added as part of CRQ IASCB-162362
	 */
	Collection<MailbagVO> getMailbagDetailsForValidation(String companyCode, String airportCode) throws SystemException;

	/**
	 * @param companyCode
	 * @param airportCode
	 * @return OfficeOfExchangeVO
	 * @throws RemoteException
	 * @author 204082
	 * Added for IASCB-164537 on 09-Nov-2022
	 */
	public Collection<OfficeOfExchangeVO> getExchangeOfficeDetails(String companyCode, String airportCode) throws SystemException;
	public Collection<ConsignmentDocumentVO> findCN46TransferManifestDetails(TransferManifestVO transferManifestVO) throws SystemException;
 /**s
 * @author U-1532
 * @param companyCode
 * @param paCode
 * @return
 * @throws SystemException
 * @throws PersistenceException
 * Added for CRQ ICRD-111886 by A-5526
 */
String findDensityfactorForPA(String companyCode, String paCode)
		throws SystemException, PersistenceException;   
/**
     * @author A-8353
     * @param companyCode
     * @param sequencenum
     * @throws SystemException 
     */
	public String findApplicableRegFlagForMailbag(String companyCode, long sequencenum) throws SystemException;
	
	
	 /**
 * @author A-10555
 * @param flightFilterVOs
 * @throws SystemException
 * @throws PersistenceException
 */
	public Collection<MailAcceptanceVO> fetchFlightPreAdviceDetails(Collection<FlightFilterVO> flightFilterVOs) throws SystemException, PersistenceException;


	public Collection<OperationalFlightVO> findFlightsForMailInboundAutoAttachAWB(
			MailInboundAutoAttachAWBJobScheduleVO mailInboundAutoAttachAWBJobScheduleVO) throws SystemException;
			
	Page<MailTransitVO> findMailTransit(MailTransitFilterVO mailTransitFilterVO, int pageNumber) throws SystemException, PersistenceException;
/**
	 * 
	 * 	Method		:	MailTrackingDefaultsDAO.findMailbagDetailsForMailInboundHHT
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException
	 */
	public MailbagVO findMailbagDetailsForMailInboundHHT(MailbagEnquiryFilterVO mailbagEnquiryFilterVO) 
			throws SystemException, PersistenceException;
	
   MailbagVO findMailConsumed(MailTransitFilterVO filterVo) throws SystemException;
   
   public Collection<CarditPawbDetailsVO> findMailbagsForPAWBCreation(int noOfDays) throws SystemException;
	
	/**
	 * @author a-9998
	 * @param carditVO
	 * @return AWBDetailVO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	AWBDetailVO findMstDocNumForAWBDetails(CarditVO carditVO) throws SystemException,
			PersistenceException;
	public Collection<MailResditAddressVO> findMailResditAddtnlAddressDetails(String companyCode,List<Long> addtnAddres)
			throws SystemException,PersistenceException;
	MailManifestVO findCN46ManifestDetails(OperationalFlightVO opFlightVO)
			throws SystemException, PersistenceException;
	public Collection<ConsignmentRoutingVO> findConsignmentRoutingVosForMailbagScreening(String companyCode,Long malseqnum) throws SystemException,PersistenceException;
   /**
	 * @author a-10383 This method Checks whether the container is already
	 *         assigned to a flight/destn from the current airport
	 * @param companyCode
	 * @param containerNumber
	 * @param pol
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	ContainerAssignmentVO findContainerWeightCapture(String companyCode,
			String containerNumber) throws SystemException,
			PersistenceException;
}




