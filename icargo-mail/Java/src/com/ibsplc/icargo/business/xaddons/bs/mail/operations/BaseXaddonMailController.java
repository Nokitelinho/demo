/**
 *	Java file	: 	com.ibsplc.icargo.business.xaddons.bs.mail.operations.BaseXaddonMailController.java
 *
 *	Created by	:	a-7779
 *	Created on	:	23-Aug-2017
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.xaddons.bs.mail.operations;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentSummaryVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.CapacityBookingProxyException;
import com.ibsplc.icargo.business.mail.operations.ContainerAssignmentException;
import com.ibsplc.icargo.business.mail.operations.DuplicateDSNException;
import com.ibsplc.icargo.business.mail.operations.DuplicateMailBagsException;
import com.ibsplc.icargo.business.mail.operations.FlightClosedException;
import com.ibsplc.icargo.business.mail.operations.ForceAcceptanceException;
import com.ibsplc.icargo.business.mail.operations.InvalidFlightSegmentException;
import com.ibsplc.icargo.business.mail.operations.MailBookingException;
import com.ibsplc.icargo.business.mail.operations.MailController;
import com.ibsplc.icargo.business.mail.operations.MailDefaultStorageUnitException;
import com.ibsplc.icargo.business.mail.operations.MailMLDBusniessException;
import com.ibsplc.icargo.business.mail.operations.MailTrackingBusinessException;
import com.ibsplc.icargo.business.mail.operations.MailUploadController;
import com.ibsplc.icargo.business.mail.operations.Mailbag;
import com.ibsplc.icargo.business.mail.operations.MailbagPK;
import com.ibsplc.icargo.business.mail.operations.ULDDefaultsProxyException;
import com.ibsplc.icargo.business.mail.operations.errorhandling.MailHHTBusniessException;
import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailFlightSummaryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OfficeOfExchangeVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.converter.MailtrackingDefaultsVOConverter;
import com.ibsplc.icargo.business.operations.flthandling.vo.ManifestFilterVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.ShipmentManifestVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.UldManifestVO;
import com.ibsplc.icargo.business.operations.shipment.vo.AcceptanceFilterVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentFilterVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentSummaryVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.xaddons.bs.mail.operations.proxy.BsmailOperationProxy;
import com.ibsplc.icargo.business.xaddons.bs.mail.operations.proxy.FlightOperationsProxy;
import com.ibsplc.icargo.business.xaddons.bs.mail.operations.proxy.OperationsFltHandlingProxy;
import com.ibsplc.icargo.business.xaddons.bs.mail.operations.proxy.OperationsShipmentProxy;
import com.ibsplc.icargo.business.xaddons.bs.mail.operations.proxy.SharedAirlineProxy;
import com.ibsplc.icargo.business.xaddons.bs.mail.operations.vo.MailBookingDetailVO;
import com.ibsplc.icargo.business.xaddons.bs.mail.operations.vo.MailBookingFilterVO;
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.OptimisticConcurrencyException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.persistence.tx.Transaction;
import com.ibsplc.xibase.server.framework.persistence.tx.TransactionProvider;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 *	Java file	: 	com.ibsplc.icargo.business.xaddons.bs.mail.operations.BaseXaddonMailController.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	a-8061	:	13-Apr-2018	:	Draft
 */
@Module("bsmail")
@SubModule("operations")
public class BaseXaddonMailController extends MailController{

	private Log log = LogFactory.getLogger("BSMAIL");
	private static final String CLASS_NAME = "BaseXaddonMailController";
	private static final String STATUS_DETACH="xaddons.bs.mail.operations.awb.status.dettach";
	   public static final String STATUS_EXECUTED = "E";
	   private static final String STATUS_ROUTE_INCOMPLETE="xaddons.bs.mail.operations.awb.status.incomplete";
	   public static final String STATUS_AGG_TYPE_LEASE = "L";

	   private static final String ERROR_INVALID_EXCHANGE="xaddons.bs.mail.operations.invalidexchange";
	   private static final String ERROR_INVALID_DSN="xaddons.bs.mail.operations.invaliddsn";
	   

	/**
	 *
	 * 	Method		:	BaseXaddonMailController.findMailBookingAWBs
	 *	Added by 	:	a-7779 on 24-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@param mailBookingFilterVO
	 *	Parameters	:	@return
	 *	Return type	: 	Collection<MailBookingFlightDetailVO>
	 */
	public Page<MailBookingDetailVO> findMailBookingAWBs(MailBookingFilterVO mailBookingFilterVO,int pageNumber)throws SystemException{

		Page<MailBookingDetailVO> mailBookingDetailVOs = null;
		return MailBookingDetail.findMailBookingAWBs(mailBookingFilterVO, pageNumber);

	}

	/**
	 *
	 * 	Method		:	BaseXaddonMailController.saveMailBookingDetails
	 *	Added by 	:	a-7779 on 28-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@param mailbagVOs
	 *	Parameters	:	@param mailBookingFlightDetailVO
	 *	Return type	: 	void
	 * @throws SystemException
	 */
	public int saveMailBookingDetails(Collection<MailbagVO> mailbagVOs,MailBookingDetailVO mailBookingDetailVO,CarditEnquiryFilterVO carditEnquiryFilterVO) throws SystemException{
		//FlightValidationVO flightValidationVO=validateMailBookingDetailVO(mailBookingDetailVO);

		Collection<MailbagVO> mailbagVOsInCsg=null;
int mailCountFromSyspar=carditEnquiryFilterVO.getMailCount();
		if(mailbagVOs.isEmpty()){
			carditEnquiryFilterVO.setConsignmentLevelAWbAttachRequired(MailConstantsVO.FLAG_YES);
			mailbagVOsInCsg =findCarditMails(carditEnquiryFilterVO,0);
			if(mailbagVOsInCsg!=null && !mailbagVOsInCsg.isEmpty()){
				mailbagVOs.addAll(mailbagVOsInCsg);
			}
		}
		if(mailbagVOs!=null && mailbagVOs.size()>0 ){
			if(mailCountFromSyspar>0 && mailbagVOs.size()>mailCountFromSyspar){
				throw new SystemException("mailtracking.defaults.searchconsignment.selectedmailcountexceeded");
			}
		}
		
//validate destination of AWB
		int mismatchCount=0;
		if(mailBookingDetailVO!=null && mailBookingDetailVO.isDestinationCheckReq()){
		mismatchCount=validateAWBDestination(mailbagVOs,mailBookingDetailVO);
		}
		if(mismatchCount==0){
		Collection<MailBookingDetailVO> mailBkgDetailVOs = new ArrayList<MailBookingDetailVO>();
		mailBkgDetailVOs.add(mailBookingDetailVO);
		/*if(flightValidationVO!=null){
			mailBookingDetailVO.setSegementserialNumber(flightValidationVO.getLegSerialNumber());
		}*/

		
		//if(mailBookingDetailVO.getBookingFlightDate()!=null && mailBookingDetailVO.getBookingFlightDate().equals(mailBookingDetailVO.getSelectedFlightDate()))
		//{

		//FlightValidationVO flightValidationVOs=validateFlightDateandTime(mailBookingDetailVO);
		validateFlightDateandTime(mailBookingDetailVO);

		/*if(flightValidationVOs!=null)
		{
			mailBookingDetailVO.setBookingFlightDate(flightValidationVO.getFlightDate());
			mailBookingDetailVO.setBookingFlightNumber(flightValidationVOs.getFlightNumber());
		}*/

		//}
		Collection<MailBookingDetailVO> mailBookingDetailVOs=null;
		
		if(mailBookingDetailVO.isSplitBooking()){
		mailBookingDetailVOs = populateSplitBookedDetails(mailBookingDetailVO,mailbagVOs);
		}else{
			mailBookingDetailVOs = populateBookedDetails(mailBookingDetailVO,mailbagVOs);
		}
		if(mailBookingDetailVOs!=null && mailBookingDetailVOs.size()>0){
			for(MailBookingDetailVO mailBookingDetailVOAttached : mailBookingDetailVOs){
				
				if(mailBookingDetailVOAttached.getMasterDocumentNumber()==null || mailBookingDetailVOAttached.getMasterDocumentNumber().isEmpty()){
					new MailBookingDetail(mailBookingDetailVOAttached);
			}
		}
		}
		
		new MailController().attachAWBForMail(mailBkgDetailVOs,mailbagVOs);
		MailController mailController = (MailController)SpringAdapter.getInstance().getBean("mAilcontroller");
		mailController.flagHistoryForMailAwbAttachment(mailbagVOs);
		}
       return mismatchCount;
	}





	private int validateAWBDestination(Collection<MailbagVO> mailbagVOs, MailBookingDetailVO mailBookingDetailVO) throws SystemException {
		int mismatchCount=0;
		if(mailbagVOs!=null && mailbagVOs.size()>0){
			
			for(MailbagVO mailbagVO:mailbagVOs){
				String mailDestination=null;
				OfficeOfExchangeVO officeOfExchangeVO=null;
				
					officeOfExchangeVO =new MailController().validateOfficeOfExchange(mailbagVO.getCompanyCode(), mailbagVO.getDoe());
				
				if(officeOfExchangeVO!=null && officeOfExchangeVO.getAirportCode()!=null && !officeOfExchangeVO.getAirportCode().isEmpty()){
					mailDestination=officeOfExchangeVO.getAirportCode();
				}else{
					mailDestination=new MailController().findNearestAirportOfCity(mailbagVO.getCompanyCode(),mailbagVO.getDoe());
				}
				if(mailDestination!=null && mailBookingDetailVO.getAwbDestination()!=null && !mailDestination.equalsIgnoreCase(mailBookingDetailVO.getAwbDestination())){
					mismatchCount=mismatchCount+1;
				}
			}
			
		}
		return mismatchCount;
	}

	/**
	 * 	Method		:	BaseXaddonMailController.validateAndUpdateMailBookingDetailVO
	 *	Added by 	:	A-7531 on 25-Sep-2017
	 * 	Used for 	:
	 *	Parameters	:	@param mailBookingDetailVO
	 *	Return type	: 	void
	 */
	private FlightValidationVO validateMailBookingDetailVO(
			MailBookingDetailVO mailBookingDetailVO) {
		  Collection<FlightValidationVO> flightValidationVOs = null;
		  if(mailBookingDetailVO.getBookingFlightNumber()!=null){
		  try {

			flightValidationVOs=new MailController().validateFlight(constructFlightFilterVO(mailBookingDetailVO));

		} catch (SystemException e) {
			e.getMessage();
		}
		  for (FlightValidationVO flightValidationVO : flightValidationVOs) {
	          if (flightValidationVO.getFlightSequenceNumber() == mailBookingDetailVO
	                  .getBookingFlightSequenceNumber()) {
	              return flightValidationVO;
	          }
	      }
		  }
	      return null;

	}

	private FlightFilterVO constructFlightFilterVO(
            MailBookingDetailVO mailBookingDetailVO) {
      FlightFilterVO flightFilterVO = new FlightFilterVO();
      flightFilterVO.setCompanyCode(mailBookingDetailVO.getCompanyCode());
      flightFilterVO.setFlightCarrierId(mailBookingDetailVO.getBookingFlightCarrierid());
      flightFilterVO.setFlightNumber(mailBookingDetailVO.getBookingFlightNumber());
      flightFilterVO.setFlightDate(mailBookingDetailVO.getBookingFlightDate());
      flightFilterVO.setAirportCode(mailBookingDetailVO.getAwbOrgin())  ;
      flightFilterVO.setStation(mailBookingDetailVO.getBookingStation());
      flightFilterVO.setFlightSequenceNumber(mailBookingDetailVO.getBookingFlightSequenceNumber());
      return flightFilterVO;
}
	/**
	 *
	 * 	Method		:	BaseXaddonMailController.validateFlightDateandTime
	 *	Added by 	:	A-7531 on 17-Oct-2017
	 * 	Used for 	:
	 *	Parameters	:	@param mailBookingDetailVO
	 *	Parameters	:	@return
	 *	Return type	: 	FlightValidationVO
	 * @throws SystemException
	 */
	private void validateFlightDateandTime(
			MailBookingDetailVO mailBookingDetailVO) throws SystemException {

		 Collection<FlightValidationVO> flightValidationVOs = null;
		 MailBookingDetailVO mailBookingDetailVOToCompare = null;
		 LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
					.getLogonAttributesVO();
		 Collection<FlightFilterVO> flightFilterVOs = null;
		  try {
			  flightFilterVOs=constructFlightFilterVOs(mailBookingDetailVO);
			if(flightFilterVOs!=null && flightFilterVOs.size()>0){
			  for(FlightFilterVO flightFilterVO:flightFilterVOs){
				  flightValidationVOs=new MailController().validateFlight(flightFilterVO);
				  if (flightValidationVOs!= null && flightValidationVOs.size()>0) {
						  for (FlightValidationVO flightValidationVO : flightValidationVOs) {
							  boolean isFirstFlight = false;

						if ((logonAttributes.getOwnAirlineCode()
								.equals(flightValidationVO.getCarrierCode()))||
								((!logonAttributes.getOwnAirlineCode().equals(flightValidationVO.getCarrierCode()) &&
											flightValidationVO.getAgreementType() != null && STATUS_AGG_TYPE_LEASE.equals(flightValidationVO.getAgreementType()) ))) {

							  if(mailBookingDetailVOToCompare==null){
								  isFirstFlight =true;
								  mailBookingDetailVOToCompare = new MailBookingDetailVO();
								  mailBookingDetailVOToCompare.setBookingFlightDate(flightValidationVO.getStd());
								  mailBookingDetailVO.setBookingFlightNumber(flightFilterVO.getFlightNumber());
								  mailBookingDetailVO.setBookingCarrierCode(flightFilterVO.getCarrierCode());
								  mailBookingDetailVO.setBookingFlightCarrierid(flightFilterVO.getFlightCarrierId());
								  mailBookingDetailVO.setBookingFlightSequenceNumber(Integer.parseInt(flightFilterVO.getFlightSequenceNumber()+""));
								  mailBookingDetailVO.setOrigin(flightFilterVO.getSegmentOrigin());
								  mailBookingDetailVO.setDestination(flightFilterVO.getSegmentDestination());

							  }

							  if(!isFirstFlight){
								   String firstDate=mailBookingDetailVOToCompare.getBookingFlightDate().toDisplayFormat("dd-MMM-yyy HH:mm:ss").toString();
								   String secondDate=flightValidationVO.getStd().toDisplayFormat("dd-MMM-yyy HH:mm:ss").toString();
								  if(DateUtilities.isGreaterThan(firstDate,secondDate,"dd-MMM-yyy HH:mm:ss")){
									  mailBookingDetailVOToCompare.setBookingFlightDate(flightValidationVO.getStd());
									  mailBookingDetailVO.setBookingFlightNumber(flightFilterVO.getFlightNumber());
									  mailBookingDetailVO.setBookingCarrierCode(flightFilterVO.getCarrierCode());
									  mailBookingDetailVO.setBookingFlightCarrierid(flightFilterVO.getFlightCarrierId());
									  mailBookingDetailVO.setBookingFlightSequenceNumber(Integer.parseInt(flightFilterVO.getFlightSequenceNumber()+""));
									  mailBookingDetailVO.setOrigin(flightFilterVO.getSegmentOrigin());
									  mailBookingDetailVO.setDestination(flightFilterVO.getSegmentDestination());
								  }
							  }
							  }
						  }
				  	}
			}
			}

		} catch (SystemException e) {
			e.getMessage();
		}
		  mailBookingDetailVO.setSegementserialNumber(getSegmentSerialNumber(mailBookingDetailVO));
		  
		  if(mailBookingDetailVO!=null){
			  for(MailBookingDetailVO bookedFlightVO :mailBookingDetailVO.getBookedFlights() ){
				  bookedFlightVO.setCompanyCode(mailBookingDetailVO.getCompanyCode());
				  bookedFlightVO.setSegementserialNumber(getSegmentSerialNumber(bookedFlightVO));
			  }
			  
			  
		  }
		  
	}
	/**
	* @author A-7779
	* @param mailBookingDetailVO
	* @return
	*/
	private int getSegmentSerialNumber(MailBookingDetailVO mailBookingDetailVO) {
		Collection<FlightSegmentSummaryVO> segmentSummaryVos = null;
		int segmentSerialNumber = 0; 
		try {
			segmentSummaryVos = new FlightOperationsProxy()
					.findFlightSegments(
							mailBookingDetailVO.getCompanyCode(), mailBookingDetailVO.getBookingFlightCarrierid(), mailBookingDetailVO.getBookingFlightNumber(),
							mailBookingDetailVO.getBookingFlightSequenceNumber());
		} catch (SystemException e) {
			log.log(Log.FINEST, "System Exception in getSegmentSerialNumber");
		}
		if (segmentSummaryVos != null && segmentSummaryVos.size() > 0) {
			for (FlightSegmentSummaryVO segmentVo : segmentSummaryVos) {
				if (segmentVo.getSegmentOrigin() != null
						&& segmentVo.getSegmentDestination() != null) {
					if (segmentVo.getSegmentOrigin().equals(mailBookingDetailVO.getOrigin())
							&& segmentVo.getSegmentDestination().equals(mailBookingDetailVO.getDestination())) {
						segmentSerialNumber = segmentVo
								.getSegmentSerialNumber();
						log.log(Log.FINE, "THE POL IS", mailBookingDetailVO.getOrigin());
						log.log(Log.FINE, "THE POU IS", mailBookingDetailVO.getDestination());
						log.log(Log.FINE, "THE SEGSERNUM FOR THE POL-POU",
								segmentSerialNumber);
						break;
					}
				}
			}
		}
			return segmentSerialNumber;
}
	/**
	 *
	 * 	Method		:	BaseXaddonMailController.constructFlightFilterVO
	 *	Added by 	:	A-7531 on 20-Oct-2017
	 * 	Used for 	:
	 *	Parameters	:	@param mailBookingDetailVO
	 *	Parameters	:	@return
	 *	Return type	: 	FlightFilterVO
	 */
	private Collection<FlightFilterVO> constructFlightFilterVOs(
            MailBookingDetailVO mailBookingDetailVO) {
		Collection<FlightFilterVO> flightFilterVOs = new ArrayList<FlightFilterVO>();
		FlightFilterVO flightFilterVO=null;
		String[] flightNumbr=mailBookingDetailVO.getSelectedFlightNumber().split(",");

		 for(int i=0;i<flightNumbr.length;i++)
	     {
			 flightFilterVO= new FlightFilterVO();
			 String[] flightDetails=flightNumbr[i].split(" ");

			 if(flightDetails[0]!=null && flightDetails[0].trim().length() > 0){

		      flightFilterVO.setCompanyCode(mailBookingDetailVO.getCompanyCode());
		      flightFilterVO.setFlightCarrierId(Integer.parseInt(flightDetails[3]));
		      flightFilterVO.setCarrierCode(flightDetails[2]);
		      flightFilterVO.setFlightNumber(flightDetails[0].toString());
		      flightFilterVO.setFlightDate(mailBookingDetailVO.getBookingFlightDate());
		      flightFilterVO.setAirportCode(mailBookingDetailVO.getAwbOrgin())  ;
		      flightFilterVO.setStation(mailBookingDetailVO.getBookingStation());
		      flightFilterVO.setFlightSequenceNumber(Integer.parseInt(flightDetails[4]));
		      flightFilterVO.setSegmentOrigin(flightDetails[5]);
		      flightFilterVO.setSegmentDestination(flightDetails[6]);
		      flightFilterVOs.add(flightFilterVO);
			 }
	     }
      return flightFilterVOs;

}

	/**
	 *
	 * 	Method		:	BaseXaddonMailController.populateBookedFlightDetails
	 *	Added by 	:	a-7779 on 24-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:
	 *	Return type	: 	void
	 */
	public Collection<MailBookingDetailVO> populateBookedDetails(MailBookingDetailVO mailBookingDetailVO,Collection<MailbagVO> mailbagVOs)
																														throws SystemException{

		Collection<MailBookingDetailVO> mailBookingDetailVOs = new ArrayList<MailBookingDetailVO>();
		MailBookingDetailVO mailBookingDetailVOToAttach = null;
		Mailbag mailbag=null;
		if(mailbagVOs!=null && mailbagVOs.size()>0){
			for(MailbagVO mailbagVO : mailbagVOs){
				
				for (MailBookingDetailVO bookedFlight : mailBookingDetailVO.getBookedFlights()){
				
				mailBookingDetailVOToAttach = new MailBookingDetailVO();

				//added by  a-8061 for ICRD-229330 begin
			//	mailBookingDetailVOToAttach = mailBookingDetailVO;
				 try {
					BeanUtils.copyProperties(mailBookingDetailVOToAttach, bookedFlight);
				} catch (IllegalAccessException e) {
					throw new SystemException(e.getMessage());
				} catch (InvocationTargetException e) {
					throw new SystemException(e.getMessage());
				}

				//added by  a-8061 for ICRD-229330 end

				mailBookingDetailVOToAttach.setBookingStatus(MailConstantsVO.MAIL_STATUS_AWB_BOOKED);
				mailBookingDetailVOToAttach.setCompanyCode(mailBookingDetailVO.getCompanyCode());
				long mailSequenceNumber=0;
				try {
					 mailSequenceNumber=Mailbag.findMailBagSequenceNumberFromMailIdr(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode());
				} catch (SystemException e) {
					throw new SystemException(e.getMessage());
				}
				mailBookingDetailVOToAttach.setMailSequenceNumber(mailSequenceNumber);
				mailBookingDetailVOToAttach.setMasterDocumentNumber(mailbagVO.getDocumentNumber());
				
				mailbagVO.setMailSequenceNumber(mailSequenceNumber);
				mailBookingDetailVOs.add(mailBookingDetailVOToAttach);
				
				}
			}
		}
		return mailBookingDetailVOs;
	}
	
	
	/**
	 * @author A-8061
	 * @param mailBookingDetailVO
	 * @param mailbagVOs
	 * @return
	 * @throws SystemException
	 */
	public Collection<MailBookingDetailVO> populateSplitBookedDetails(MailBookingDetailVO mailBookingDetailVO,Collection<MailbagVO> mailbagVOs)
			throws SystemException{

			Collection<MailBookingDetailVO> mailBookingDetailVOs = new ArrayList<MailBookingDetailVO>();
			MailBookingDetailVO mailBookingDetailVOToAttach = null;
			Mailbag mailbag=null;
			if(mailbagVOs!=null && mailbagVOs.size()>0){
			for(MailbagVO mailbagVO : mailbagVOs){
			mailBookingDetailVOToAttach = new MailBookingDetailVO();

				//added by  a-8061 for ICRD-229330 begin
			//	mailBookingDetailVOToAttach = mailBookingDetailVO;
				 try {
					BeanUtils.copyProperties(mailBookingDetailVOToAttach, mailBookingDetailVO);
				} catch (IllegalAccessException e) {
					throw new SystemException(e.getMessage());
				} catch (InvocationTargetException e) {
					throw new SystemException(e.getMessage());
				}

				//added by  a-8061 for ICRD-229330 end

				mailBookingDetailVOToAttach.setBookingStatus(MailConstantsVO.MAIL_STATUS_AWB_BOOKED);
				long mailSequenceNumber=0;
				try {
					 mailSequenceNumber=Mailbag.findMailBagSequenceNumberFromMailIdr(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode());
				} catch (SystemException e) {
					throw new SystemException(e.getMessage());
				}
				mailBookingDetailVOToAttach.setMailSequenceNumber(mailSequenceNumber);
				mailBookingDetailVOToAttach.setMasterDocumentNumber(mailbagVO.getDocumentNumber());
				mailbagVO.setMailSequenceNumber(mailSequenceNumber);
				mailBookingDetailVOs.add(mailBookingDetailVOToAttach);
			}
		}
		return mailBookingDetailVOs;
	}

	

	/**
	 *
	 * 	Method		:	BaseXaddonMailController.dettachMailBookingDetails
	 *	Added by 	:	a-7779 on 28-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@param mailbagVOs
	 *	Return type	: 	void
	 * @throws SystemException
	 * @throws FinderException
	 */
	public void dettachMailBookingDetails(Collection<MailbagVO> mailbagVOs,CarditEnquiryFilterVO carditEnquiryFilterVO) throws SystemException{
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		int mailCountFromSyspar=carditEnquiryFilterVO.getMailCount();
		
		Collection<MailbagVO> mailbagVOsInCsg=null;

		if(mailbagVOs.isEmpty()){
			carditEnquiryFilterVO.setConsignmentLevelAWbAttachRequired(MailConstantsVO.FLAG_YES);
			mailbagVOsInCsg =findCarditMails(carditEnquiryFilterVO,0);
			if(mailbagVOsInCsg!=null && !mailbagVOsInCsg.isEmpty()){
				//mailbagVOs.addAll(mailbagVOsInCsg);  
				for(MailbagVO mailbagVO :mailbagVOsInCsg){
					if(!"Y".equals(mailbagVO.getAccepted())){
					mailbagVOs.add(mailbagVO);
					}
				}
			}
		}
		if(mailbagVOs!=null && mailbagVOs.size()>0 ){
			if(mailCountFromSyspar>0 && mailbagVOs.size()>mailCountFromSyspar){
				throw new SystemException("mailtracking.defaults.searchconsignment.selectedmailcountexceeded");
			}
		}

		if(mailbagVOs!=null && mailbagVOs.size()>0){
			for(MailbagVO mailbagVO : mailbagVOs){

				//added by a-8061 for ICRD-229273 starts

		    	ShipmentFilterVO shipmentFilterVO = new ShipmentFilterVO();
		    	shipmentFilterVO.setCompanyCode(mailbagVO.getCompanyCode());
		    	shipmentFilterVO.setShipmentPrefix(mailbagVO.getShipmentPrefix());
		    	shipmentFilterVO.setMasterDocumentNumber(mailbagVO.getDocumentNumber());
		    	shipmentFilterVO.setDocumentNumber(mailbagVO.getDocumentNumber());

		    	shipmentFilterVO.setOwnerId(mailbagVO.getDocumentOwnerIdr());
		    	shipmentFilterVO.setSequenceNumber(mailbagVO.getSequenceNumber());
		    	shipmentFilterVO.setDuplicateNumber(mailbagVO.getDuplicateNumber());

		    	Collection<ShipmentVO> shipmentVOs =  new OperationsShipmentProxy().findShipments(shipmentFilterVO) ;
		    	if(shipmentVOs!=null && shipmentVOs.size()>0){
		    		for(ShipmentVO shipmentVO : shipmentVOs){
		    			if(STATUS_EXECUTED.equals(shipmentVO.getShipmentStatus())){
		    				ErrorVO errorVO = new ErrorVO(STATUS_DETACH);
		    				errors.add(errorVO);
		    				throw new SystemException(errors);
		    			}
		    		}
		    	}
		    	//added by a-8061 for ICRD-229273 end

		    	//a-7531 added for ICRD-265325 starts 
		    	LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
		    			.getLogonAttributesVO();
		    	AcceptanceFilterVO aceptanceFilterVO = new AcceptanceFilterVO();
		    	aceptanceFilterVO.setCompanyCode(mailbagVO.getCompanyCode());
		    	aceptanceFilterVO.setShipmentPrefix(mailbagVO.getShipmentPrefix());
		    	aceptanceFilterVO.setDocumentNumber(mailbagVO.getDocumentNumber());
		    	aceptanceFilterVO.setOwnerId(mailbagVO.getDocumentOwnerIdr());
		    	aceptanceFilterVO.setSequenceNumber((int)mailbagVO.getSequenceNumber());
		    	aceptanceFilterVO.setDuplicateNumber(mailbagVO.getDuplicateNumber());
		    	aceptanceFilterVO.setAirportCode(logonAttributes.getAirportCode());
		    	/*AcceptanceVO acceptanceVO = new OperationsShipmentProxy().findAcceptanceDetails(aceptanceFilterVO) ;
	
		    	if(acceptanceVO!=null&&acceptanceVO.getAcceptedPieces()>0){
		    		ErrorVO errorVO = new ErrorVO(STATUS_DETACH);
    				errors.add(errorVO);
    				throw new SystemException(errors);
		    	}*/

		    	//a-7531 added for ICRD-265325 end 	

				long mailSequenceNumber = Mailbag.findMailBagSequenceNumberFromMailIdr(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode());
			Collection<MailBookingDetailVO> mailBookingDetailVOs=new MailBookingDetail().fetchBookedFlightDetailsForMailbag(mailSequenceNumber);
				if (mailBookingDetailVOs!=null){
					for(MailBookingDetailVO mailBookingDetailVO:mailBookingDetailVOs){



						if(MailConstantsVO.MAIL_STATUS_AWB_BOOKED.equals(mailBookingDetailVO.getBookingStatus())||MailConstantsVO.MAIL_STATUS_NEW.equals(mailBookingDetailVO.getBookingStatus()))
						{
				MailBookingDetail mailBookingFlightDetail = null;
				MailBookingDetailPK mailBookingDetailPK = new MailBookingDetailPK();
						mailBookingDetailPK.setCompanyCode(mailBookingDetailVO.getCompanyCode());
				mailBookingDetailPK.setMailSequenceNumber(mailSequenceNumber);
						mailBookingDetailPK.setFlightNumber(mailBookingDetailVO.getBookingFlightNumber());
						mailBookingDetailPK.setFlightCarrierId(mailBookingDetailVO.getBookingFlightCarrierid());
						mailBookingDetailPK.setFlightSequenceNumber(mailBookingDetailVO.getBookingFlightSequenceNumber());
						mailBookingDetailPK.setSegementserialNumber(mailBookingDetailVO.getSegementserialNumber());
						mailBookingDetailPK.setSerialNumber(mailBookingDetailVO.getSerialNumber());
				try {
					mailBookingFlightDetail = MailBookingDetail.find(mailBookingDetailPK);
							if (mailBookingFlightDetail!=null){
							mailBookingFlightDetail.remove();
							}
				} catch (FinderException e) {
							mailBookingFlightDetail=null;
						}

					}
						else {
							throw new SystemException(errors);
						}



					}
				}
				else{
					log.entering("dettachMailBookingDetails", "dettachMailBookingDetailsElsee");
					throw new SystemException(errors);


				}


			}
				new MailController().dettachMailBookingDetails(mailbagVOs);



		}
	}



	/**
	 *
	 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailController#fetchBookedFlightDetails(java.lang.String, java.lang.String, java.lang.String)
	 *	Added by 			: a-7779 on 29-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param shipmentPrefix
	 *	Parameters	:	@param masterDocumentNumber
	 *	Parameters	:	@return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<MailBookingDetailVO> fetchBookedFlightDetails(String companyCode,String shipmentPrefix,String masterDocumentNumber) throws SystemException{

		log.entering("BaseXaddonMailController", "fetchBookedFlightDetails");
		Collection<MailBookingDetailVO> mailBookingDetailVOs = null;
		try {
			mailBookingDetailVOs =  new MailBookingDetail()
			.fetchBookedFlightDetails(companyCode, shipmentPrefix,
					masterDocumentNumber);
		} catch (PersistenceException e) {
			throw new SystemException(e.getMessage());
		}

		log.exiting("BaseXaddonMailController", "fetchBookedFlightDetails");
		return mailBookingDetailVOs;
	}


	/**
	 * @author A-8061
	 * @param mailBookingDetailVOs
	 * @param shipmentOrigin
	 * @param shipmentDestination
	 * @return
	 */
	private boolean isValidRoute(Collection<MailBookingDetailVO> mailBookingDetailVOs,String shipmentOrigin,String shipmentDestination){

		String tmpOrigin="";
		String tmpDestination="";

		for( MailBookingDetailVO mailBookingDetailVO:mailBookingDetailVOs){
			if("".equals(tmpOrigin)){
				tmpOrigin=mailBookingDetailVO.getOrigin();
				tmpDestination=mailBookingDetailVO.getDestination();
			}
			else{
				if(tmpDestination.equals(mailBookingDetailVO.getOrigin())){
					tmpDestination=mailBookingDetailVO.getDestination();
				}
				else{
					return false;
				}
			}
		}

		if(tmpOrigin.equals(shipmentOrigin)&&tmpDestination.equals(shipmentDestination)){
			return true;
		}else{
			return false;
		}

	}



	/**
	 *
	 * 	Method		:	BaseXaddonMailController.saveMailBookingFlightDetails
	 *	Added by 	:	a-7779 on 30-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@param mailbagVOs
	 *	Return type	: 	void
	 * @throws SystemException
	 */
	public void saveMailBookingFlightDetails(Collection<MailbagVO> mailbagVOs,Collection<MailBookingDetailVO> selectedMailBookingDetailVOs) throws SystemException{

		log.entering("BaseXaddonMailController", "saveMailBookingFlightDetails");

			//a-8061  for ICRD-236369 begin
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

			String shipmentOrigin="";
			String shipmentDestination="";
			if(mailbagVOs!=null && mailbagVOs.size()>0){
				for(MailbagVO mailbagVO : mailbagVOs){
				   	ShipmentFilterVO shipmentFilterVO = new ShipmentFilterVO();
			    	shipmentFilterVO.setCompanyCode(mailbagVO.getCompanyCode());
			    	shipmentFilterVO.setShipmentPrefix(mailbagVO.getShipmentPrefix());
			    	shipmentFilterVO.setMasterDocumentNumber(mailbagVO.getDocumentNumber());
			    	shipmentFilterVO.setDocumentNumber(mailbagVO.getDocumentNumber());
			    	shipmentFilterVO.setOwnerId(mailbagVO.getDocumentOwnerIdr());
			    	shipmentFilterVO.setSequenceNumber(mailbagVO.getSequenceNumber());
			    	shipmentFilterVO.setDuplicateNumber(mailbagVO.getDuplicateNumber());
			    	Collection<ShipmentVO> shipmentVOs =  new OperationsShipmentProxy().findShipments(shipmentFilterVO) ;
			    	if(shipmentVOs!=null && shipmentVOs.size()>0){
			    		for(ShipmentVO shipmentVO : shipmentVOs){
			    			shipmentOrigin=shipmentVO.getOrigin();
			    			shipmentDestination=shipmentVO.getDestination();
			    			}
			    	}
					break;
				}
			}
			if(!isValidRoute(selectedMailBookingDetailVOs, shipmentOrigin, shipmentDestination)){
				ErrorVO errorVO = new ErrorVO(STATUS_ROUTE_INCOMPLETE);
				errors.add(errorVO);
				throw new SystemException(errors);
			}

			//a-8061  for ICRD-236369 end

			if(mailbagVOs!=null && mailbagVOs.size()>0){
				for(MailbagVO mailbagVO : mailbagVOs){
					//Added by A-7540 for ICRD-253854
					/*if("N".equals(mailbagVO.getAccepted()))
					{
					mailbagVO.setMailStatus(MailConstantsVO.MAIL_STATUS_AWB_BOOKED);
					}
					else
					{
					mailbagVO.setMailStatus(MailConstantsVO.MAIL_STATUS_ACCEPTED);
					}*/
					Mailbag mailbag=null;
					long mailSequenceNumber = Mailbag.findMailBagSequenceNumberFromMailIdr(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode());
					MailbagPK mailbagPk = new MailbagPK();
					mailbagPk.setCompanyCode(mailbagVO.getCompanyCode());
					mailbagPk.setMailSequenceNumber(mailSequenceNumber);
					try {
						mailbag=Mailbag.find(mailbagPk);
					} catch (FinderException e) {
						throw new SystemException(e.getMessage());
					}
					//Added by A-7540 for ICRD-253854
					if("N".equals(mailbagVO.getAccepted()))
					{
						if(mailbag.getLatestStatus()!=null &&mailbag.getLatestStatus().trim().length()>0){//modified as by A-7371 as part of ICRD-264283
							mailbag.setLatestStatus(mailbag.getLatestStatus());
						}
					}
		

					//added by A-8061 as part of ICRD-229572 begin
				/*	mailbag.setFlightNumber(mailBookingDetailVO.getBookingFlightNumber());
					mailbag.setFlightSequenceNumber(mailBookingDetailVO.getBookingFlightSequenceNumber());
					mailbag.setSegmentSerialNumber(mailBookingDetailVO.getSegementserialNumber());
					mailbag.setCarrierId(mailBookingDetailVO.getBookingFlightCarrierid());*/
					//mailbag.saveMailBookingFlightDetails(mailbagVOs);
					Collection<MailBookingDetailVO> mailBookingDetailVOs=new MailBookingDetail().fetchBookedFlightDetailsForMailbag(mailSequenceNumber);
					if (mailBookingDetailVOs!=null){
						for(MailBookingDetailVO mailBookingDetailVOInMailbag:mailBookingDetailVOs){
							if(MailConstantsVO.MAIL_STATUS_AWB_BOOKED.equals(mailBookingDetailVOInMailbag.getBookingStatus())||MailConstantsVO.MAIL_STATUS_NEW.equals(mailBookingDetailVOInMailbag.getBookingStatus()))
							{
								MailBookingDetail mailBookingFlightDetail = null;
								MailBookingDetailPK mailBookingDetailPK = new MailBookingDetailPK();
								mailBookingDetailPK.setCompanyCode(mailBookingDetailVOInMailbag.getCompanyCode());
								mailBookingDetailPK.setMailSequenceNumber(mailSequenceNumber);
								mailBookingDetailPK.setFlightNumber(mailBookingDetailVOInMailbag.getBookingFlightNumber());
								mailBookingDetailPK.setFlightCarrierId(mailBookingDetailVOInMailbag.getBookingFlightCarrierid());
								mailBookingDetailPK.setFlightSequenceNumber(mailBookingDetailVOInMailbag.getBookingFlightSequenceNumber());
								mailBookingDetailPK.setSegementserialNumber(mailBookingDetailVOInMailbag.getSegementserialNumber());
								mailBookingDetailPK.setSerialNumber(mailBookingDetailVOInMailbag.getSerialNumber());
									try {
											mailBookingFlightDetail = MailBookingDetail.find(mailBookingDetailPK);
												if (mailBookingFlightDetail!=null){
												mailBookingFlightDetail.remove();

												}
									} catch (FinderException e) {
										//new MailBookingDetail(mailBookingDetailVO);
											}
							}
						}
					}
					for(MailBookingDetailVO mailBookingDetailVO : selectedMailBookingDetailVOs){
						//Added by A-7540 for ICRD-253854
					//	if("N".equals(mailbagVO.getAccepted()))
					//	{
						mailBookingDetailVO.setBookingStatus(MailConstantsVO.MAIL_STATUS_AWB_BOOKED);
						
					//	}
						//else
					//	{
						//mailBookingDetailVO.setBookingStatus(MailConstantsVO.MAIL_STATUS_ACCEPTED);
						//}
						mailBookingDetailVO.setMailSequenceNumber(mailSequenceNumber);
						mailBookingDetailVO.setCompanyCode(mailbagVO.getCompanyCode());
						new MailBookingDetail(mailBookingDetailVO);
					}
					//added by A-8061 as part of ICRD-229572 end
				}
			}
			log.exiting("BaseXaddonMailController", "saveMailBookingFlightDetails");
		}




/**
 *
 * 	Method		:	BaseXaddonMailController.performMailAWBTransactionsFlow
 *	Added by 	:	a-7779 on 13-Sep-2017
 * 	Used for 	:
 *	Parameters	:	@param mailFlightSummaryVO
 *	Parameters	:	@param eventCode
 *	Parameters	:	@throws SystemException
 *	Return type	: 	void
 */
public void performMailAWBTransactionsFlow(MailFlightSummaryVO mailFlightSummaryVO,String eventCode)
		throws SystemException{
	log.entering("BaseXaddonMailController", "performMailAWBTransactions");
	LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
			.getLogonAttributesVO();

	mailFlightSummaryVO.setEventCode(eventCode);//a-8061 added as part of  ICRD-249916
	if("ACP".equals(eventCode)){
		if(mailFlightSummaryVO!=null && mailFlightSummaryVO.getShipmentSummaryVOs()!=null && mailFlightSummaryVO.getShipmentSummaryVOs().size()>0){

			//fetching carriercode using carrierId modified for ICRD-236397
	        AirlineValidationVO airlineValidationVO;

			airlineValidationVO = new SharedAirlineProxy().findAirline(mailFlightSummaryVO.getCompanyCode(),
					mailFlightSummaryVO.getCarrierId());
			//fetching flight details
            FlightFilterVO filghtFilterVO = new FlightFilterVO();
            filghtFilterVO.setCompanyCode(mailFlightSummaryVO.getCompanyCode());
            filghtFilterVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
            filghtFilterVO.setFlightCarrierId(mailFlightSummaryVO.getCarrierId());
             filghtFilterVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
            filghtFilterVO.setDirection(FlightFilterVO.OUTBOUND);
            Collection<FlightValidationVO> flightValidationVOs = new FlightOperationsProxy().validateFlightForAirport(filghtFilterVO);
            List<String> awbKey = new ArrayList<String>();
			for(ShipmentSummaryVO shipmentSummaryVO : mailFlightSummaryVO.getShipmentSummaryVOs()){
				boolean blockReceivedResdit = false;
				if(MailConstantsVO.FLAG_YES.equals(shipmentSummaryVO.getTranshipmentFlag()) ){//added by A-7371 as part of ICRD-256798
					blockReceivedResdit = true;  
				}
				ScannedMailDetailsVO scannedMailDetailsVO = new Mailbag().findAwbAtachedMailbagDetails
						(shipmentSummaryVO,mailFlightSummaryVO);
				
					/*In case of OFFLOAD AND REASSIGN TO ANOTHER FLIGHT : After acceptance AWB offloaded from flight F1 and re attached mail bags in another flight F2 
					 * while re manifesting F1 mail bags will be offload from mail module(offload to carrier) and the mal status will be ASG
					 * while manifesting F2 offloaded mail bags should be reassign to F2 
					 * OFLMAILBAG: setting from  AWBAttachedMailbagDetailsMapper
					 * */
					if (scannedMailDetailsVO != null && scannedMailDetailsVO.getMailDetails() != null
							&& !scannedMailDetailsVO.getMailDetails().isEmpty()
							&& (MailConstantsVO.MAIL_STATUS_ASSIGNED
									.equals(((ArrayList<MailbagVO>) scannedMailDetailsVO.getMailDetails()).get(0)
											.getMailStatus()) || MailConstantsVO.MAIL_STATUS_OFFLOADED
									.equals(((ArrayList<MailbagVO>) scannedMailDetailsVO.getMailDetails()).get(0)
											.getMailStatus()))
							&& "OFLMAILBAG".equals(((ArrayList<MailbagVO>) scannedMailDetailsVO.getMailDetails()).get(0)
									.getOffloadedRemarks())) {
					
					boolean success=true;
					scannedMailDetailsVO.setProcessPoint(eventCode);
					scannedMailDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
					scannedMailDetailsVO.setCarrierCode(mailFlightSummaryVO.getCarrierCode());
					scannedMailDetailsVO.setToCarrierCode(mailFlightSummaryVO.getCarrierCode());
					for(MailbagVO mailbag:scannedMailDetailsVO.getMailDetails()){

						 if(flightValidationVOs!=null &&flightValidationVOs.size()>0){
							 mailbag.setLegSerialNumber(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getLegSerialNumber());
						 }
						 mailbag.setCarrierCode(mailFlightSummaryVO.getCarrierCode());
						 mailbag.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
						 mailbag.setFlightDate(mailFlightSummaryVO.getFlightDate());
						 mailbag.setCarrierId(mailFlightSummaryVO.getCarrierId());
						 mailbag.setFinalDestination(mailbag.getPou());
						 mailbag.setInventoryContainer(mailbag.getContainerNumber());
						 mailbag.setInventoryContainerType(mailbag.getContainerType());
						 mailbag.setOperationalStatus(MailConstantsVO.OPERATION_OUTBOUND);

					 }
					scannedMailDetailsVO.setToCarrierid(mailFlightSummaryVO.getCarrierId());
					scannedMailDetailsVO.setToFlightNumber(mailFlightSummaryVO.getFlightNumber());
					scannedMailDetailsVO.setToFlightDate(mailFlightSummaryVO.getFlightDate());
					scannedMailDetailsVO.setToFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
					scannedMailDetailsVO.setOperationTime( new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
					scannedMailDetailsVO.setScannedContainerDetails(null);
	
					
					
					if(mailFlightSummaryVO.getPou().equals(shipmentSummaryVO.getDestination())){
					scannedMailDetailsVO.setPou(mailFlightSummaryVO.getPou());
					scannedMailDetailsVO.setDestination(mailFlightSummaryVO.getPou());
					}else{
						String routes[] = mailFlightSummaryVO.getRoute().split("-");
								if(Arrays.asList(routes).contains(shipmentSummaryVO.getDestination())){
									scannedMailDetailsVO.setPou(shipmentSummaryVO.getDestination());
									scannedMailDetailsVO.setDestination(shipmentSummaryVO.getDestination());
								}else{
									scannedMailDetailsVO.setPou(mailFlightSummaryVO.getPou());
									scannedMailDetailsVO.setDestination(mailFlightSummaryVO.getPou());
								}
					}
					
					
					if(flightValidationVOs!=null && !flightValidationVOs.isEmpty()){
					       scannedMailDetailsVO.setLegSerialNumber(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getLegSerialNumber());
					       scannedMailDetailsVO.setFlightDate(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getFlightDate());
					       
					       
									}
									if(scannedMailDetailsVO.getMailDetails()!=null && scannedMailDetailsVO.getMailDetails().size()>0){
										ManifestFilterVO manifestFilterVO = new ManifestFilterVO();
										manifestFilterVO.setManifestPrint(false);
										manifestFilterVO.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
										manifestFilterVO.setCarrierId(scannedMailDetailsVO.getCarrierId());
										manifestFilterVO.setFlightNumber(scannedMailDetailsVO.getFlightNumber());
										manifestFilterVO.setFlightSequenceNumber(scannedMailDetailsVO.getFlightSequenceNumber());
										manifestFilterVO.setPointOfLading(logonAttributes.getAirportCode());
										Collection<UldManifestVO> uldManifestVOs = new OperationsFltHandlingProxy().findManifestShipmentDetails(manifestFilterVO);
										Collection<ShipmentManifestVO> manifestShipmentDetails = new ArrayList<ShipmentManifestVO>();
										if(uldManifestVOs!=null && uldManifestVOs.size()>0){
											for(UldManifestVO uldManifestVO : uldManifestVOs){
												manifestShipmentDetails.addAll(uldManifestVO.getManifestShipmentDetails());
											}
										}
										if(manifestShipmentDetails!=null && manifestShipmentDetails.size()>0){
											for(ShipmentManifestVO shipmentManifestVO : manifestShipmentDetails){
												if(shipmentSummaryVO.getShipmentPrefix().equals(shipmentManifestVO.getShipmentPrefix()) &&
														shipmentSummaryVO.getMasterDocumentNumber().equals(shipmentManifestVO.getMasterDocumentNumber())){
													for(MailbagVO mailBagVO:scannedMailDetailsVO.getMailDetails()){
														mailBagVO.setContainerNumber(shipmentManifestVO.getUldNumber());
														scannedMailDetailsVO.setContainerNumber(shipmentManifestVO.getUldNumber());
														if(MailConstantsVO.CONST_BULK.equals(shipmentManifestVO.getUldNumber())){
															mailBagVO.setContainerType(MailConstantsVO.BULK_TYPE);
															scannedMailDetailsVO.setContainerType(MailConstantsVO.BULK_TYPE);
														}else{
															mailBagVO.setContainerType(MailConstantsVO.ULD_TYPE);
															scannedMailDetailsVO.setContainerType(MailConstantsVO.ULD_TYPE);
															scannedMailDetailsVO.setDestination(shipmentSummaryVO.getDestination());
														}
													}
												}
											}
										}
									}
									
					
			        
						ContainerDetailsVO containerDetailsVO =new ContainerDetailsVO ();
						containerDetailsVO.setCarrierCode(mailFlightSummaryVO.getCarrierCode());
						containerDetailsVO.setCarrierId(mailFlightSummaryVO.getCarrierId());
						containerDetailsVO.setContainerNumber(scannedMailDetailsVO.getContainerNumber());
						containerDetailsVO.setCompanyCode(mailFlightSummaryVO.getCompanyCode());
						containerDetailsVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
						containerDetailsVO.setFlightDate(mailFlightSummaryVO.getFlightDate());
						containerDetailsVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
						containerDetailsVO.setSegmentSerialNumber(1);
						containerDetailsVO.setLegSerialNumber(mailFlightSummaryVO.getLegSerialNumber());
						containerDetailsVO.setPou(mailFlightSummaryVO.getFinalDestination());
						containerDetailsVO.setAssignedUser(logonAttributes.getUserId());
						containerDetailsVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
						containerDetailsVO.setContainerType(scannedMailDetailsVO.getContainerType());
						containerDetailsVO.setDestination(scannedMailDetailsVO.getDestination());
						containerDetailsVO.setPou(scannedMailDetailsVO.getPou());
						//scannedMailDetailsVO.setPou(mailFlightSummaryVO.getFinalDestination());
						scannedMailDetailsVO.setValidatedContainer(containerDetailsVO);
						success = saveContainerforReassign(containerDetailsVO,mailFlightSummaryVO);
						scannedMailDetailsVO.setScannedContainerDetails(null);
						
						scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_REASSIGNMAIL);	
						
				
					scannedMailDetailsVO.setScannedContainerDetails(null);
					if(success){
					try {
					
					new MailUploadController().saveAndProcessMailBags(scannedMailDetailsVO);
				
					} catch (MailHHTBusniessException e) {
						throw new SystemException(e.getMessage());
					} catch (MailMLDBusniessException e) {
						throw new SystemException(e.getMessage());
					}
					catch (MailTrackingBusinessException e) {
						throw new SystemException(e.getMessage());
					}
					catch(RemoteException e){
						throw new SystemException(e.getMessage());
					}
					catch(ForceAcceptanceException e){
						throw new SystemException(e.getMessage());
					}
				}


				}

				else{//Mail acceptance
	
				String cargoOpsKey = new StringBuilder("").append(shipmentSummaryVO.getShipmentPrefix())
						.append("~").append(shipmentSummaryVO.getMasterDocumentNumber())
						.append("~").append(shipmentSummaryVO.getDuplicateNumber())
						.append("~").append(shipmentSummaryVO.getSequenceNumber()).toString();
				awbKey.add(cargoOpsKey);
				if(scannedMailDetailsVO!=null){
				scannedMailDetailsVO.setProcessPoint(eventCode);
				scannedMailDetailsVO.setAirportCode(logonAttributes.getAirportCode());
				
				
				
				if(mailFlightSummaryVO.getPou().equals(shipmentSummaryVO.getDestination())){
				scannedMailDetailsVO.setPou(mailFlightSummaryVO.getPou());
				scannedMailDetailsVO.setDestination(mailFlightSummaryVO.getPou());
				}else{
					//A-8061 added for ICRD-253703 starts
					String routes[] = mailFlightSummaryVO.getRoute().split("-");
					/*	IF case
					 *  Flight F1 Route A-B-C and AWB orign is A and Destination is C ,
					 *  POU of  mailFlightSummaryVO is B so we r setting shipmentSummaryVO's destination as scannedMailDetailsVO POU.
					 *  else case
					 *  AWB A-C , F1 A-B and F2 B-C . while accepting mailbag in flight F1 then scannedMailDetailsVO  POU should be mailFlightSummaryVO POU , if we take
					 *  shipmentSummaryVO POU then we cannot arrive mail bag at station B .
					 */
							if(Arrays.asList(routes).contains(shipmentSummaryVO.getDestination())){
								scannedMailDetailsVO.setPou(shipmentSummaryVO.getDestination());
								scannedMailDetailsVO.setDestination(shipmentSummaryVO.getDestination());
							}else{
								scannedMailDetailsVO.setPou(mailFlightSummaryVO.getPou());
								scannedMailDetailsVO.setDestination(mailFlightSummaryVO.getPou());
							}
					//A-8061 added for ICRD-253703 end
				}
				
				
				scannedMailDetailsVO.setPol(logonAttributes.getAirportCode());
				scannedMailDetailsVO.setMailSource("EXPFLTFIN_ACPMAL");
				scannedMailDetailsVO.setCarrierCode(airlineValidationVO.getAlphaCode());
				if(flightValidationVOs!=null && !flightValidationVOs.isEmpty()){
       scannedMailDetailsVO.setLegSerialNumber(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getLegSerialNumber());
       scannedMailDetailsVO.setFlightDate(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getFlightDate());
				}
				if(scannedMailDetailsVO.getMailDetails()!=null && scannedMailDetailsVO.getMailDetails().size()>0){
					ManifestFilterVO manifestFilterVO = new ManifestFilterVO();
					manifestFilterVO.setManifestPrint(false);
					manifestFilterVO.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
					manifestFilterVO.setCarrierId(scannedMailDetailsVO.getCarrierId());
					manifestFilterVO.setFlightNumber(scannedMailDetailsVO.getFlightNumber());
					manifestFilterVO.setFlightSequenceNumber(scannedMailDetailsVO.getFlightSequenceNumber());
					manifestFilterVO.setPointOfLading(logonAttributes.getAirportCode());
					Collection<UldManifestVO> uldManifestVOs = new OperationsFltHandlingProxy().findManifestShipmentDetails(manifestFilterVO);
					Collection<ShipmentManifestVO> manifestShipmentDetails = new ArrayList<ShipmentManifestVO>();
					if(uldManifestVOs!=null && uldManifestVOs.size()>0){
						for(UldManifestVO uldManifestVO : uldManifestVOs){
							manifestShipmentDetails.addAll(uldManifestVO.getManifestShipmentDetails());
						}
					}
					if(manifestShipmentDetails!=null && manifestShipmentDetails.size()>0){
						for(ShipmentManifestVO shipmentManifestVO : manifestShipmentDetails){
							if(shipmentSummaryVO.getShipmentPrefix().equals(shipmentManifestVO.getShipmentPrefix()) &&
									shipmentSummaryVO.getMasterDocumentNumber().equals(shipmentManifestVO.getMasterDocumentNumber())){
								for(MailbagVO mailBagVO:scannedMailDetailsVO.getMailDetails()){
									if(blockReceivedResdit){
										mailBagVO.setBlockReceivedResdit(blockReceivedResdit);
									}
									mailBagVO.setContainerNumber(shipmentManifestVO.getUldNumber());
									scannedMailDetailsVO.setContainerNumber(shipmentManifestVO.getUldNumber());
									if(MailConstantsVO.CONST_BULK.equals(shipmentManifestVO.getUldNumber())){
										mailBagVO.setContainerType(MailConstantsVO.BULK_TYPE);
										scannedMailDetailsVO.setContainerType(MailConstantsVO.BULK_TYPE);
									}else{
										mailBagVO.setContainerType(MailConstantsVO.ULD_TYPE);
										scannedMailDetailsVO.setContainerType(MailConstantsVO.ULD_TYPE);
										scannedMailDetailsVO.setDestination(shipmentSummaryVO.getDestination());//a-8061 added for ICRD-255007
									}
								}
							}
						}
					}
				}

				}


			try {
				new MailUploadController().saveAcceptanceFromUpload(scannedMailDetailsVO,logonAttributes);
				checkforATDCaptureFlight(mailFlightSummaryVO);//added by A-7371 as part of ICRD-233074 for triggering uplift resdit for ATD capture in Export Manifest Screen
				//success =true;
			} catch (MailHHTBusniessException e) {
				throw new SystemException(e.getMessage());
			} catch (MailMLDBusniessException e) {
				throw new SystemException(e.getMessage());
			} catch (ForceAcceptanceException e) {
				throw new SystemException(e.getMessage());
			}



		}
			
			

		}
			boolean offloadReq = false;
			try{
				ScannedMailDetailsVO scnMailDetailsVO = new Mailbag().findAwbAtachedMailbagDetails
						(null,mailFlightSummaryVO);
				Collection<MailbagVO> mailbags = new ArrayList<MailbagVO>();
				int count = 0;
				double weight= 0.0;
				
				boolean isFlightClosed = false;
				OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
				operationalFlightVO.setCarrierId(mailFlightSummaryVO.getCarrierId());
				operationalFlightVO.setCompanyCode(mailFlightSummaryVO.getCompanyCode());
				operationalFlightVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
				operationalFlightVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
				operationalFlightVO.setDirection(FlightFilterVO.OUTBOUND);
				try {
					isFlightClosed = new MailController().isFlightClosedForOperations(
							operationalFlightVO);
				} catch (SystemException systemException) {
					 log.log(Log.SEVERE, "SystemException",systemException.getMessage());
				}	
				
				if(scnMailDetailsVO != null && scnMailDetailsVO.getMailDetails() != null
						&& !scnMailDetailsVO.getMailDetails().isEmpty()){
					for(MailbagVO mailVO : scnMailDetailsVO.getMailDetails()){
						if(mailVO.getShipmentPrefix() != null && mailVO.getDocumentNumber() != null
								&& mailVO.getDuplicateNumber() > 0 && mailVO.getSequenceNumber() > 0){
							String mailOpsKey = new StringBuilder("").append(mailVO.getShipmentPrefix())
									.append("~").append(mailVO.getDocumentNumber())
									.append("~").append(mailVO.getDuplicateNumber())
									.append("~").append(mailVO.getSequenceNumber()).toString();
							if(!awbKey.contains(mailOpsKey)){
								offloadReq = true;
								count++;
								weight = weight+ mailVO.getWeight().getDisplayValue();
								mailVO.setCarrierCode(mailFlightSummaryVO.getCarrierCode());
								mailVO.setLegSerialNumber(mailFlightSummaryVO.getLegSerialNumber());
								mailVO.setScannedUser(logonAttributes.getUserId());
								mailVO.setLastUpdateUser(logonAttributes.getUserId());
								if(isFlightClosed){
								mailVO.setOffloadedReason(MailConstantsVO.CARGO_OPS_DEFAULT_OFFLOAD_REASON_CODE);
								mailVO.setOffloadedRemarks(MailConstantsVO.CARGO_OPS_DEFAULT_OFFLOAD_REASON);
								}
								mailVO.setFlightDate(mailFlightSummaryVO.getFlightDate());
								mailVO.setLastUpdateTime( new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
								mailbags.add(mailVO);

							}
	
						}
					}
					scnMailDetailsVO.setMailDetails(mailbags);
				}
				

				if(offloadReq){

				if(isFlightClosed){	
				scnMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_OFFLOADED);
				}else{
				scnMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_REASSIGNMAIL);		
				}
				
				scnMailDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
				scnMailDetailsVO.setLegSerialNumber(mailFlightSummaryVO.getLegSerialNumber());
				scnMailDetailsVO.setCarrierCode(mailFlightSummaryVO.getCarrierCode());
				scnMailDetailsVO.setScannedUser(logonAttributes.getUserId());
				scnMailDetailsVO.setAcceptedFlag(MailConstantsVO.FLAG_YES);
				scnMailDetailsVO.setMailSource(MailConstantsVO.MAIL_SOURCE_MAWB_OFFLOAD);
				if(scnMailDetailsVO.getScannedContainerDetails() != null &&
						!scnMailDetailsVO.getScannedContainerDetails().isEmpty()){
					String containerNumber = "";
					Collection<ContainerVO> contVOs = new ArrayList<ContainerVO>();
					for(ContainerVO containerVO : scnMailDetailsVO.getScannedContainerDetails()){
						if(!containerNumber.equals(scnMailDetailsVO.getContainerNumber())){
							containerNumber = scnMailDetailsVO.getContainerNumber();
						containerVO.setAcceptanceFlag(MailConstantsVO.FLAG_YES);
						containerVO.setCarrierCode(mailFlightSummaryVO.getCarrierCode());
						containerVO.setLegSerialNumber(mailFlightSummaryVO.getLegSerialNumber());
						containerVO.setFinalDestination(mailFlightSummaryVO.getPou());
						
						if(isFlightClosed){
						containerVO.setOffloadedReason(MailConstantsVO.CARGO_OPS_DEFAULT_OFFLOAD_REASON_CODE);
						containerVO.setOffloadedRemarks(MailConstantsVO.CARGO_OPS_DEFAULT_OFFLOAD_REASON);
						containerVO.setOffload(true);
						containerVO.setMailSource(MailConstantsVO.MAIL_SOURCE_MAWB_OFFLOAD);
						}
						
						containerVO.setBags(count);
						containerVO.setWeight(new Measure(UnitConstants.WEIGHT,weight));
						containerVO.setFlightDate(mailFlightSummaryVO.getFlightDate());
						containerVO.setLastUpdateTime( new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
						containerVO.setLastUpdateUser(logonAttributes.getUserId());
						containerVO.setScannedDate( new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
						contVOs.add(containerVO);
						}
					}
					scnMailDetailsVO.setScannedContainerDetails(contVOs);
				}

					new MailUploadController().saveAndProcessMailBags(scnMailDetailsVO);


		        //PersistenceController.getEntityManager().flush();
                              checkforATDCaptureFlight(mailFlightSummaryVO);//added by A-7371 as part of ICRD-233074 for triggering uplift resdit for ATD capture in Export Manifest Screen

				}

				} catch (MailHHTBusniessException e) {
					throw new SystemException(e.getMessage());
				} catch (MailMLDBusniessException e) {
					throw new SystemException(e.getMessage());
				} catch (MailTrackingBusinessException e) {
					throw new SystemException(e.getMessage());
				} catch(RemoteException e){
					throw new SystemException(e.getMessage());
				} catch(ForceAcceptanceException e){
					throw new SystemException(e.getMessage());
				}
			}
			else{

				boolean isFlightClosed = false;
				OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
				operationalFlightVO.setCarrierId(mailFlightSummaryVO.getCarrierId());
				operationalFlightVO.setCompanyCode(mailFlightSummaryVO.getCompanyCode());
				operationalFlightVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
				operationalFlightVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
				operationalFlightVO.setDirection(FlightFilterVO.OUTBOUND);
				try {
					isFlightClosed = new MailController().isFlightClosedForOperations(
							operationalFlightVO);
				} catch (SystemException systemException) {
					 log.log(Log.SEVERE, "SystemException",systemException.getMessage());
				}

				
			ScannedMailDetailsVO scannedMailDetailsVO = new Mailbag().findAwbAtachedMailbagDetails
					(null,mailFlightSummaryVO);

			if(scannedMailDetailsVO != null && scannedMailDetailsVO.getMailDetails() != null
					&& !scannedMailDetailsVO.getMailDetails().isEmpty()){
				if(isFlightClosed){	
			scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_OFFLOADED);
				}else{
			scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_REASSIGNMAIL);		
				}
			scannedMailDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
			scannedMailDetailsVO.setLegSerialNumber(mailFlightSummaryVO.getLegSerialNumber());
			scannedMailDetailsVO.setCarrierCode(mailFlightSummaryVO.getCarrierCode());
			scannedMailDetailsVO.setScannedUser(logonAttributes.getUserId());
			scannedMailDetailsVO.setAcceptedFlag(MailConstantsVO.FLAG_YES);
			scannedMailDetailsVO.setMailSource(MailConstantsVO.MAIL_SOURCE_MAWB_OFFLOAD);
			int count = 0;
			double weight= 0.0;
				for(MailbagVO mailVO : scannedMailDetailsVO.getMailDetails()){
					count++;
					weight = weight+ mailVO.getWeight().getDisplayValue();
					mailVO.setCarrierCode(mailFlightSummaryVO.getCarrierCode());
					mailVO.setLegSerialNumber(mailFlightSummaryVO.getLegSerialNumber());
					mailVO.setScannedUser(logonAttributes.getUserId());
					mailVO.setLastUpdateUser(logonAttributes.getUserId());
					if(isFlightClosed){
					mailVO.setOffloadedReason(MailConstantsVO.CARGO_OPS_DEFAULT_OFFLOAD_REASON_CODE);
					mailVO.setOffloadedRemarks(MailConstantsVO.CARGO_OPS_DEFAULT_OFFLOAD_REASON);
					}
					mailVO.setFlightDate(mailFlightSummaryVO.getFlightDate());
					mailVO.setLastUpdateTime( new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
				}

			if(scannedMailDetailsVO.getScannedContainerDetails() != null &&
					!scannedMailDetailsVO.getScannedContainerDetails().isEmpty()){
					String containerNumber = "";
					Collection<ContainerVO> contVOs = new ArrayList<ContainerVO>();
				for(ContainerVO containerVO : scannedMailDetailsVO.getScannedContainerDetails()){
						if(!containerNumber.equals(scannedMailDetailsVO.getContainerNumber())){
							containerNumber = scannedMailDetailsVO.getContainerNumber();
							
							
					containerVO.setAcceptanceFlag(MailConstantsVO.FLAG_YES);
					containerVO.setCarrierCode(mailFlightSummaryVO.getCarrierCode());
					containerVO.setLegSerialNumber(mailFlightSummaryVO.getLegSerialNumber());
					containerVO.setFinalDestination(mailFlightSummaryVO.getPou());
						
						if(isFlightClosed){
					containerVO.setOffloadedReason(MailConstantsVO.CARGO_OPS_DEFAULT_OFFLOAD_REASON_CODE);
					containerVO.setOffloadedRemarks(MailConstantsVO.CARGO_OPS_DEFAULT_OFFLOAD_REASON);
					containerVO.setOffload(true);
					containerVO.setMailSource(MailConstantsVO.MAIL_SOURCE_MAWB_OFFLOAD);
						}
					containerVO.setBags(count);
					containerVO.setWeight(new Measure(UnitConstants.WEIGHT,weight));
					containerVO.setFlightDate(mailFlightSummaryVO.getFlightDate());
					containerVO.setLastUpdateTime( new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
					containerVO.setLastUpdateUser(logonAttributes.getUserId());
					containerVO.setScannedDate( new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
						contVOs.add(containerVO);
						}
					}
					scannedMailDetailsVO.setScannedContainerDetails(contVOs);
				}

				
			try {
				new MailUploadController().saveAndProcessMailBags(scannedMailDetailsVO);
			} catch (MailHHTBusniessException e) {
				throw new SystemException(e.getMessage());
			} catch (MailMLDBusniessException e) {
				throw new SystemException(e.getMessage());
			} catch (MailTrackingBusinessException e) {
				throw new SystemException(e.getMessage());
			} catch(RemoteException e){
				throw new SystemException(e.getMessage());
			}catch(ForceAcceptanceException e){
				throw new SystemException(e.getMessage());
			}
			checkforATDCaptureFlight(mailFlightSummaryVO);
		}
			
			
			
			}

	}else if("ARR".equals(eventCode)){
           //fetching carriercode using carrierId
	        AirlineValidationVO airlineValidationVO;

		airlineValidationVO = new SharedAirlineProxy().findAirline(mailFlightSummaryVO.getCompanyCode(),
				mailFlightSummaryVO.getCarrierId());
			//fetching flight details
			FlightFilterVO filghtFilterVO = new FlightFilterVO();
			filghtFilterVO.setCompanyCode(mailFlightSummaryVO.getCompanyCode());
			filghtFilterVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
			filghtFilterVO.setFlightCarrierId(mailFlightSummaryVO.getCarrierId());
			filghtFilterVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
			filghtFilterVO.setDirection(FlightFilterVO.INBOUND);
           Collection<FlightValidationVO> flightValidationVOs = new FlightOperationsProxy().validateFlightForAirport(filghtFilterVO);
		for(ShipmentSummaryVO shipmentSummaryVO : mailFlightSummaryVO.getShipmentSummaryVOs()){
			ScannedMailDetailsVO scannedMailDetailsVO = new Mailbag().findAwbAtachedMailbagDetails
					(shipmentSummaryVO,mailFlightSummaryVO);
			if(scannedMailDetailsVO != null && scannedMailDetailsVO.getMailDetails() != null
					&& !scannedMailDetailsVO.getMailDetails().isEmpty()){
				if(MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getStatus())){
					continue;
				}
			String awbKey = new StringBuilder("").append(shipmentSummaryVO.getShipmentPrefix())
					.append("~").append(shipmentSummaryVO.getMasterDocumentNumber())
					.append("~").append(shipmentSummaryVO.getDuplicateNumber())
					.append("~").append(shipmentSummaryVO.getSequenceNumber()).toString();
			//Added by A-5219 for ICRD-256200 start
            String containerNumber = "";
			if(mailFlightSummaryVO.getUldAwbMap() != null && !mailFlightSummaryVO.getUldAwbMap().isEmpty()){
				containerNumber = mailFlightSummaryVO.getUldAwbMap().get(awbKey);
			}
			if(scannedMailDetailsVO.getContainerNumber() == null){
				scannedMailDetailsVO.setFoundArrival(true);
				scannedMailDetailsVO.setContainerNumber(containerNumber);
			}
			//Added by A-5219 for ICRD-256200 end
			scannedMailDetailsVO.setProcessPoint(eventCode);
			scannedMailDetailsVO.setAirportCode(logonAttributes.getAirportCode());
			if(MailConstantsVO.CONST_BULK.equals(containerNumber)){
				scannedMailDetailsVO.setContainerType(MailConstantsVO.BULK_TYPE);
			}else{
				scannedMailDetailsVO.setContainerType(MailConstantsVO.ULD_TYPE);
			}

			scannedMailDetailsVO.setCarrierCode(airlineValidationVO.getAlphaCode());
			if(flightValidationVOs!=null && !flightValidationVOs.isEmpty()){
           scannedMailDetailsVO.setLegSerialNumber(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getLegSerialNumber());
           scannedMailDetailsVO.setFlightDate(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getFlightDate());
			}
           for(MailbagVO mailbagVO : scannedMailDetailsVO.getMailDetails()){
				mailbagVO.setCarrierCode(airlineValidationVO.getAlphaCode());
				mailbagVO.setContainerNumber(scannedMailDetailsVO.getContainerNumber());
				mailbagVO.setContainerType(scannedMailDetailsVO.getContainerType());
				mailbagVO.setUldNumber(scannedMailDetailsVO.getContainerNumber());
				mailbagVO.setLegSerialNumber(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getLegSerialNumber());
				mailbagVO.setFlightDate(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getFlightDate());
			}
           for(ContainerVO containerVO : scannedMailDetailsVO.getScannedContainerDetails()){
        	   containerVO.setContainerNumber(scannedMailDetailsVO.getContainerNumber());
        	   containerVO.setType(scannedMailDetailsVO.getContainerType());
			}
			try {
				new MailUploadController().saveArrivalFromUpload(scannedMailDetailsVO,logonAttributes);
			} catch (MailHHTBusniessException e) {
				throw new SystemException(e.getMessage());
			} catch (MailMLDBusniessException e) {
				throw new SystemException(e.getMessage());
			}catch (ForceAcceptanceException e) {
				throw new SystemException(e.getMessage());
			}
		}
		}
	}else if("DLV".equals(eventCode)){
		for(ShipmentSummaryVO shipmentSummaryVO : mailFlightSummaryVO.getShipmentSummaryVOs()){
			/*ScannedMailDetailsVO scannedMailDetailsVO = new Mailbag().findAwbAtachedMailbagDetails
					(shipmentSummaryVO,mailFlightSummaryVO.getFlightNumber(),mailFlightSummaryVO.getFlightSequenceNumber());*/
			ScannedMailDetailsVO scannedMailDetailsVO = new Mailbag().findAwbAtachedMailbagDetails
					(shipmentSummaryVO,mailFlightSummaryVO);
			scannedMailDetailsVO.setProcessPoint(eventCode);
			scannedMailDetailsVO.setAirportCode(logonAttributes.getAirportCode());
			
			//Added by A-8149 for ICRD-257715 starts
			FlightFilterVO flightFilterVO=null;
			for(MailbagVO mailbagVo:scannedMailDetailsVO.getMailDetails()){
				
				flightFilterVO = new FlightFilterVO();
				flightFilterVO.setCompanyCode(mailbagVo.getCompanyCode());
				flightFilterVO.setFlightNumber(mailbagVo.getFlightNumber());
				flightFilterVO.setFlightCarrierId(mailbagVo.getCarrierId());
				flightFilterVO.setFlightSequenceNumber(mailbagVo.getFlightSequenceNumber());
				flightFilterVO.setDirection(FlightFilterVO.INBOUND);
	           Collection<FlightValidationVO> flightValidationVOs = new FlightOperationsProxy().validateFlightForAirport(flightFilterVO);
	         
	           if(flightValidationVOs!=null && !flightValidationVOs.isEmpty()){
	        	   mailbagVo.setCarrierCode(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getCarrierCode());
	        	   mailbagVo.setFlightDate(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getFlightDate());
	          	}
	           
			}
			//Added by A-8149 for ICRD-257715 ends
			
			
			
			try {
				new MailUploadController().saveDeliverFromUpload(scannedMailDetailsVO,logonAttributes);
			} catch (MailHHTBusniessException e) {
				throw new SystemException(e.getMessage());
			} catch (MailMLDBusniessException e) {
				throw new SystemException(e.getMessage());
			}catch (ForceAcceptanceException e) {
				throw new SystemException(e.getMessage());
			}
		}
	}else if("TRA".equals(eventCode)||  "RMPTRA".equals(eventCode)){

		if( "RMPTRA".equals(eventCode)){//Ramp Transfer
		
				performMailAWBTransactionsFlow( mailFlightSummaryVO, "ARR");
				mailFlightSummaryVO.setEventCode("TRA");
		}

		FlightFilterVO filghtFilterVO = new FlightFilterVO();
		filghtFilterVO.setCompanyCode(mailFlightSummaryVO.getCompanyCode());
		filghtFilterVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
		filghtFilterVO.setFlightCarrierId(mailFlightSummaryVO.getCarrierId());
		filghtFilterVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
		filghtFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);
		filghtFilterVO.setAirportCode(logonAttributes.getAirportCode());
        Collection<FlightValidationVO> flightValidationVOs = new FlightOperationsProxy().validateFlightForAirport(filghtFilterVO);
        
		
		for(ShipmentSummaryVO shipmentSummaryVO : mailFlightSummaryVO.getShipmentSummaryVOs()){
			ScannedMailDetailsVO scannedMailDetailsVO = new Mailbag().findAwbAtachedMailbagDetails
					(shipmentSummaryVO,mailFlightSummaryVO);
			boolean success=true;
			scannedMailDetailsVO.setProcessPoint(eventCode);


			scannedMailDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
			scannedMailDetailsVO.setCarrierCode(mailFlightSummaryVO.getCarrierCode());
			scannedMailDetailsVO.setToCarrierCode(mailFlightSummaryVO.getToCarrierCode());
			for(MailbagVO mailbag:scannedMailDetailsVO.getMailDetails()){

				 if(flightValidationVOs!=null &&flightValidationVOs.size()>0){
					 mailbag.setLegSerialNumber(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getLegSerialNumber());
				 }
				 mailbag.setCarrierCode(mailFlightSummaryVO.getCarrierCode());
				 mailbag.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
				 mailbag.setFlightDate(mailFlightSummaryVO.getFlightDate());
				 mailbag.setArrivedFlag(MailConstantsVO.FLAG_YES);
				 mailbag.setCarrierId(mailFlightSummaryVO.getCarrierId());
				 mailbag.setFinalDestination(mailbag.getPou());
				 mailbag.setInventoryContainer(mailbag.getContainerNumber());
				 mailbag.setInventoryContainerType(mailbag.getContainerType());
				 mailbag.setOperationalStatus(MailConstantsVO.OPERATION_INBOUND);

			 }
			scannedMailDetailsVO.setToCarrierid(mailFlightSummaryVO.getToCarrierId());
			scannedMailDetailsVO.setToFlightNumber(mailFlightSummaryVO.getToFlightNumber());
			scannedMailDetailsVO.setToFlightDate(mailFlightSummaryVO.getToFlightDate());
			scannedMailDetailsVO.setToFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
			scannedMailDetailsVO.setOperationTime( new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
			scannedMailDetailsVO.setScannedContainerDetails(null);
	        boolean isWetLeased=false;
	        isWetLeased=checkForWetleasedFlight(mailFlightSummaryVO,logonAttributes);//added by A-7371 for ICRD-256262
			//added by A-7371 as part of ICRD-231527 starts ends
			if(logonAttributes.getOwnAirlineCode().equals(scannedMailDetailsVO.getToCarrierCode()) ||isWetLeased ){
				ContainerDetailsVO containerDetailsVO =new ContainerDetailsVO ();
				containerDetailsVO.setCarrierCode(mailFlightSummaryVO.getCarrierCode());
				containerDetailsVO.setCarrierId(mailFlightSummaryVO.getCarrierId());
				containerDetailsVO.setContainerNumber(mailFlightSummaryVO.getToContainerNumber());
				containerDetailsVO.setCompanyCode(mailFlightSummaryVO.getCompanyCode());
				containerDetailsVO.setFlightNumber(mailFlightSummaryVO.getToFlightNumber());
				containerDetailsVO.setFlightDate(mailFlightSummaryVO.getToFlightDate());
				containerDetailsVO.setFlightSequenceNumber(mailFlightSummaryVO.getToFlightSequenceNumber());
				containerDetailsVO.setSegmentSerialNumber(mailFlightSummaryVO.getToSegmentSerialNumber());
				containerDetailsVO.setLegSerialNumber(mailFlightSummaryVO.getToLegSerialNumber());
				containerDetailsVO.setPou(mailFlightSummaryVO.getFinalDestination());
				containerDetailsVO.setAssignedUser(logonAttributes.getUserId());
				containerDetailsVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
				containerDetailsVO.setContainerType(MailConstantsVO.ULD_TYPE);//reassign is not possible to bulk from operations side
				containerDetailsVO.setDestination(mailFlightSummaryVO.getFinalDestination());
				//scannedMailDetailsVO.setPou(mailFlightSummaryVO.getFinalDestination());
				scannedMailDetailsVO.setValidatedContainer(containerDetailsVO);
				success = saveContainerforTransfer(containerDetailsVO,mailFlightSummaryVO);
				scannedMailDetailsVO.setScannedContainerDetails(null);
			}
			scannedMailDetailsVO.setScannedContainerDetails(null);
			if(success){
			try {
				new MailUploadController().saveTransferFromUpload(scannedMailDetailsVO,logonAttributes);
			} catch (MailHHTBusniessException e) {
				throw new SystemException(e.getMessage());
			} catch (MailMLDBusniessException e) {
				throw new SystemException(e.getMessage());
			} catch (MailTrackingBusinessException e) {
				throw new SystemException(e.getMessage());
			}catch (ForceAcceptanceException e) {
				throw new SystemException(e.getMessage());
			}
		}
	}
	}//OFL condition added for ICRD-253863 by A-5219
	else if (MailConstantsVO.MAIL_STATUS_OFFLOADED.equals(eventCode)){
		for(ShipmentSummaryVO shipmentSummaryVO : mailFlightSummaryVO.getShipmentSummaryVOs()){
			ScannedMailDetailsVO scannedMailDetailsVO = new Mailbag().findAwbAtachedMailbagDetails
					(shipmentSummaryVO,mailFlightSummaryVO);
			scannedMailDetailsVO.setProcessPoint(eventCode);
			scannedMailDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
			scannedMailDetailsVO.setLegSerialNumber(mailFlightSummaryVO.getLegSerialNumber());
			scannedMailDetailsVO.setCarrierCode(mailFlightSummaryVO.getCarrierCode());
			scannedMailDetailsVO.setScannedUser(logonAttributes.getUserId());
			scannedMailDetailsVO.setAcceptedFlag(MailConstantsVO.FLAG_YES);
			scannedMailDetailsVO.setMailSource("OPSOFL");
			int count = 0;
			double weight= 0.0;
			if(scannedMailDetailsVO.getMailDetails() != null && !scannedMailDetailsVO.getMailDetails().isEmpty()){
				for(MailbagVO mailVO : scannedMailDetailsVO.getMailDetails()){
					count++;
					if(mailVO.getWeight() != null){
						weight = weight+ mailVO.getWeight().getDisplayValue();
					}
					mailVO.setCarrierCode(mailFlightSummaryVO.getCarrierCode());
					mailVO.setLegSerialNumber(mailFlightSummaryVO.getLegSerialNumber());
					mailVO.setScannedUser(logonAttributes.getUserId());
					mailVO.setLastUpdateUser(logonAttributes.getUserId());
					mailVO.setOffloadedReason("62");
					mailVO.setOffloadedRemarks("Unknown");
					mailVO.setFlightDate(mailFlightSummaryVO.getFlightDate());
					mailVO.setLastUpdateTime( new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
				}
			}
			if(scannedMailDetailsVO.getScannedContainerDetails() != null &&
					!scannedMailDetailsVO.getScannedContainerDetails().isEmpty()){
				for(ContainerVO containerVO : scannedMailDetailsVO.getScannedContainerDetails()){
					containerVO.setAcceptanceFlag(MailConstantsVO.FLAG_YES);
					containerVO.setCarrierCode(mailFlightSummaryVO.getCarrierCode());
					containerVO.setLegSerialNumber(mailFlightSummaryVO.getLegSerialNumber());
					containerVO.setFinalDestination(shipmentSummaryVO.getDestination());
					containerVO.setOffloadedReason("62");
					containerVO.setOffloadedRemarks("Unknown");
					containerVO.setOffload(true);
					containerVO.setBags(count);
					containerVO.setWeight(new Measure(UnitConstants.WEIGHT,weight));
					containerVO.setFlightDate(mailFlightSummaryVO.getFlightDate());
					containerVO.setLastUpdateTime( new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
					containerVO.setLastUpdateUser(logonAttributes.getUserId());
					containerVO.setScannedDate( new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
				}
			}
			try {
				new MailUploadController().saveAndProcessMailBags(scannedMailDetailsVO);
			} catch (MailHHTBusniessException e) {
				throw new SystemException(e.getMessage());
			} catch (MailMLDBusniessException e) {
				throw new SystemException(e.getMessage());
			} catch (MailTrackingBusinessException e) {
				throw new SystemException(e.getMessage());
			} catch(RemoteException e){
				throw new SystemException(e.getMessage());
		} catch (ForceAcceptanceException e) {
				throw new SystemException(e.getMessage());
		}
	}
}
	log.exiting("BaseXaddonMailController", "performMailAWBTransactions");
}
/**
 *
 * 	Method		:	BaseXaddonMailController.checkforATDCaptureFlight
 *	Added by 	:	A-7371 on 24-Nov-2017
 * 	Used for 	:
 *	Parameters	:	@param mailFlightSummaryVO
 *	Parameters	:	@throws SystemException
 *	Return type	: 	void
 */
protected void checkforATDCaptureFlight(MailFlightSummaryVO mailFlightSummaryVO) throws SystemException{

	log.entering("BaseXaddonMailController", "checkforATDCaptureFlight");
	FlightFilterVO flightFilterVO = new FlightFilterVO();
	flightFilterVO.setCompanyCode(mailFlightSummaryVO.getCompanyCode());
	flightFilterVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
	flightFilterVO.setFlightCarrierId(mailFlightSummaryVO.getCarrierId());
	flightFilterVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
	flightFilterVO.setStation(mailFlightSummaryVO.getPol());
	flightFilterVO.setDirection(FlightFilterVO.OUTBOUND);

   Collection<FlightValidationVO> flightValidationVOs = new FlightOperationsProxy().validateFlightForAirport(flightFilterVO);
   Collection<OperationalFlightVO> operationalFlightVOs=new ArrayList<OperationalFlightVO>() ;
  for( FlightValidationVO flightValidationVO:flightValidationVOs){
       if(flightValidationVO.getAtd()!=null){


    	   OperationalFlightVO operationalFlightVO=MailtrackingDefaultsVOConverter.constructOperationalFlightVO(flightValidationVO);
    	   operationalFlightVOs.add(operationalFlightVO);
    	   try{
    		   new MailController().flagUpliftedResditForMailbags(operationalFlightVOs);
    	   }catch(SystemException e){
    		  log.log(Log.SEVERE, "SystemException",e.getMessage());
    	   }


       }
  }

  log.exiting("BaseXaddonMailController", "checkforATDCaptureFlight");

}
/**
 * 	Method		:	BaseXaddonMailController.persistContainerforTransfer
 *	Added by 	:	A-7371 on 05-Dec-2017
 * 	Used for 	:
 *	Parameters	:
 *	Return type	: 	void
 * @throws SystemException
 */
protected boolean saveContainerforTransfer(ContainerDetailsVO containerDetailsVO,MailFlightSummaryVO mailFlightSummaryVO) throws SystemException {
	log.entering("BaseXaddonMailController", "saveContainerforTransfer");
		try {
		return new BsmailOperationProxy().saveContainerTransfer(containerDetailsVO,mailFlightSummaryVO);
	} catch (ProxyException proxyException) {
		throw new SystemException(proxyException.getErrors());
	} 
}

public boolean saveContainerTransfer(ContainerDetailsVO containerDetailsVO,MailFlightSummaryVO mailFlightSummaryVO) throws SystemException {
	log.entering("BaseXaddonMailController", "persistContainerforTransfer");
	LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
			.getLogonAttributesVO();

	
	Collection<ContainerDetailsVO> containers=new ArrayList<ContainerDetailsVO>();
    containerDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
	containerDetailsVO.setContainerNumber(mailFlightSummaryVO.getToContainerNumber());
	//containerDetailsVO.setContainerType("U");
	
	if(MailConstantsVO.CONST_BULK.equals(containerDetailsVO.getContainerNumber())){
			containerDetailsVO.setContainerType(MailConstantsVO.BULK_TYPE);
		}else{
			containerDetailsVO.setContainerType(MailConstantsVO.ULD_TYPE);
		}
	
	
	containerDetailsVO.setCarrierId(mailFlightSummaryVO.getToCarrierId());
	containerDetailsVO.setPol(logonAttributes.getAirportCode());
	containerDetailsVO.setSegmentSerialNumber(mailFlightSummaryVO.getToSegmentSerialNumber());
	containerDetailsVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
	containerDetailsVO.setAcceptedFlag("Y");
	containerDetailsVO.setArrivedStatus("N");
	containerDetailsVO.setTransactionCode(MailConstantsVO.MAIL_TXNCOD_ASG);
	containerDetailsVO.setAssignmentDate(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
	containerDetailsVO.setOperationFlag("I");
	containerDetailsVO.setContainerOperationFlag("I");
	containerDetailsVO.setAssignedUser(logonAttributes.getUserId());
	containerDetailsVO.setDestination(mailFlightSummaryVO.getFinalDestination());
	containers.add(containerDetailsVO);
	MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();
	mailAcceptanceVO.setFlightCarrierCode(mailFlightSummaryVO.getToCarrierCode());
	mailAcceptanceVO.setFlightNumber(mailFlightSummaryVO.getToFlightNumber());
	mailAcceptanceVO.setFlightSequenceNumber(mailFlightSummaryVO.getToFlightSequenceNumber());
	mailAcceptanceVO.setCarrierId(mailFlightSummaryVO.getToCarrierId());
	mailAcceptanceVO.setLegSerialNumber(mailFlightSummaryVO.getToLegSerialNumber());//modified by A-7371 for ICRD-243240
	mailAcceptanceVO.setFlightDate(mailFlightSummaryVO.getToFlightDate());
	mailAcceptanceVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
	mailAcceptanceVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
	mailAcceptanceVO.setAcceptedUser(logonAttributes.getUserId().toUpperCase());
	mailAcceptanceVO.setCompanyCode(logonAttributes.getCompanyCode());
	mailAcceptanceVO.setPol(logonAttributes.getAirportCode());
	mailAcceptanceVO.setPreassignNeeded(false);
	mailAcceptanceVO.setDestination(mailFlightSummaryVO.getFinalDestination());
	 mailAcceptanceVO.setContainerDetails(containers);
	boolean success = false;
	Transaction tx = null;
	TransactionProvider tm = PersistenceController.getTransactionProvider();
	tx = tm.getNewTransaction(false);
			try {
				new MailController().saveAcceptanceDetails(mailAcceptanceVO);
				success=true;
			} catch (DuplicateMailBagsException | FlightClosedException
					| ContainerAssignmentException
					| InvalidFlightSegmentException | ULDDefaultsProxyException
					| DuplicateDSNException | CapacityBookingProxyException
					| MailBookingException | MailDefaultStorageUnitException e) {
				throw new SystemException(e.getMessage());
			}
	finally {
        try{
               if (success) {
                      tx.commit();
                     return success;
               }
			tx.rollback();
        } catch(OptimisticConcurrencyException concurrencyException){
        throw new SystemException(concurrencyException.getErrorCode(), concurrencyException);
        }

	}
	return success;
}

/**
 * 	Method		:	BaseXaddonMailController.persistContainerforTransfer
 *	Added by 	:	A-8061 on 25-APR-2018
 * 	Used for 	:
 *	Parameters	:
 *	Return type	: 	void
 * @throws SystemException
 */
protected boolean saveContainerforReassign(ContainerDetailsVO containerDetailsVO,MailFlightSummaryVO mailFlightSummaryVO) throws SystemException {
	log.entering("BaseXaddonMailController", "saveContainerforReassign");
	try {
	return new BsmailOperationProxy().saveContainerforReassigns(containerDetailsVO,mailFlightSummaryVO);
} catch (ProxyException proxyException) {
	throw new SystemException(proxyException.getErrors());
} 
}

public boolean saveContainerforReassigns(ContainerDetailsVO containerDetailsVO,MailFlightSummaryVO mailFlightSummaryVO) throws SystemException
{

	log.entering("BaseXaddonMailController", "persistContainerforTransfer");
	LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
			.getLogonAttributesVO();

	Collection<ContainerDetailsVO> containers=new ArrayList<ContainerDetailsVO>();
    containerDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
	containerDetailsVO.setCarrierId(mailFlightSummaryVO.getCarrierId());
	containerDetailsVO.setPol(logonAttributes.getAirportCode());
	containerDetailsVO.setSegmentSerialNumber(1);
	containerDetailsVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
	containerDetailsVO.setAcceptedFlag(MailConstantsVO.FLAG_YES);
	containerDetailsVO.setArrivedStatus(MailConstantsVO.FLAG_NO);
	containerDetailsVO.setTransactionCode(MailConstantsVO.MAIL_TXNCOD_ASG);
	containerDetailsVO.setAssignmentDate(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
	containerDetailsVO.setOperationFlag("I");
	containerDetailsVO.setContainerOperationFlag("I");
	containerDetailsVO.setAssignedUser(logonAttributes.getUserId());
	
	containers.add(containerDetailsVO);

	MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();
	mailAcceptanceVO.setFlightCarrierCode(mailFlightSummaryVO.getCarrierCode());
	mailAcceptanceVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
	mailAcceptanceVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
	mailAcceptanceVO.setCarrierId(mailFlightSummaryVO.getCarrierId());
	mailAcceptanceVO.setLegSerialNumber(mailFlightSummaryVO.getLegSerialNumber());
	mailAcceptanceVO.setFlightDate(mailFlightSummaryVO.getFlightDate());
	mailAcceptanceVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
	mailAcceptanceVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
	mailAcceptanceVO.setAcceptedUser(logonAttributes.getUserId().toUpperCase());
	mailAcceptanceVO.setCompanyCode(logonAttributes.getCompanyCode());
	mailAcceptanceVO.setPol(logonAttributes.getAirportCode());
	mailAcceptanceVO.setPreassignNeeded(false);
	mailAcceptanceVO.setDestination(mailFlightSummaryVO.getFinalDestination());
	 mailAcceptanceVO.setContainerDetails(containers);
	boolean success = false;
	Transaction tx = null;
	TransactionProvider tm = PersistenceController.getTransactionProvider();
	tx = tm.getNewTransaction(false);
			try {
				new MailController().saveAcceptanceDetails(mailAcceptanceVO);
				success=true;
			} catch (DuplicateMailBagsException | FlightClosedException
					| ContainerAssignmentException
					| InvalidFlightSegmentException | ULDDefaultsProxyException
					| DuplicateDSNException | CapacityBookingProxyException
					| MailBookingException | MailDefaultStorageUnitException e) {
				throw new SystemException(e.getMessage());
			}
	finally {
        try{
               if (success) {
                      tx.commit();
                     return success;
               }
			tx.rollback();
        } catch(OptimisticConcurrencyException concurrencyException){
        throw new SystemException(concurrencyException.getErrorCode(), concurrencyException);
        }

	}
	return success;
}

/**
 * @author A-7371
 * @param mailFlightSummaryVO
 * @return
 * @throws SystemException 
 */
protected boolean checkForWetleasedFlight(MailFlightSummaryVO mailFlightSummaryVO,LogonAttributes logonAttributes ) throws SystemException {
	
	FlightFilterVO filghtFilterVO = new FlightFilterVO();
	filghtFilterVO.setCompanyCode(mailFlightSummaryVO.getCompanyCode());
	filghtFilterVO.setFlightNumber(mailFlightSummaryVO.getToFlightNumber());
	filghtFilterVO.setFlightCarrierId(mailFlightSummaryVO.getToCarrierId());
	filghtFilterVO.setFlightSequenceNumber(mailFlightSummaryVO.getToFlightSequenceNumber());
	filghtFilterVO.setDirection(MailConstantsVO.OPERATION_OUTBOUND);
	filghtFilterVO.setAirportCode(logonAttributes.getAirportCode());
	 Collection<FlightValidationVO> flightValidationVOs = new FlightOperationsProxy().validateFlightForAirport(filghtFilterVO);
	if (flightValidationVOs!=null &&flightValidationVOs.size()>0 
				&& FlightValidationVO.FLIGHT_AGRMNT_TYP_LEASED.equals(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getAgreementType())){
		return true;
	}
	return false;
}

/**
 * @author A-7371
 * @return
 * @throws SystemException
 */
public boolean findMailBagsforReassign(ScannedMailDetailsVO scannedMailDetailsVO,ShipmentSummaryVO shipmentSummaryVO,MailFlightSummaryVO mailFlightSummaryVO) throws SystemException {

	/*here we find entire mailbags under a AWB with ACP status , what we do is that while when reassign transaction comes,
	we will compare the flights and if its is different flight to be which finalise transaction is undergoing with ACP status
	then it is Reassign scenario */
	LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
  			.getLogonAttributesVO();
	List<MailbagVO> mailbagVOs=null;
	try {
		mailbagVOs=Mailbag.findMailBagsforReassign(shipmentSummaryVO,mailFlightSummaryVO);
	} catch (PersistenceException | SystemException e) {

		 throw new SystemException(e.getMessage());
	}
     if(scannedMailDetailsVO.getMailDetails()!=null  && scannedMailDetailsVO.getMailDetails().size()>0
                       && mailbagVOs!=null && mailbagVOs.size()>0){
	            for(MailbagVO mailbagInTransaction:scannedMailDetailsVO.getMailDetails()){

		            for(MailbagVO mailbag:mailbagVOs){
		             if(mailbag.getMailSequenceNumber()==mailbagInTransaction.getMailSequenceNumber()){

				           if(mailbag.getFlightNumber()!=null&&mailbagInTransaction.getFlightNumber()!=null
						       && !mailbag.getFlightNumber().equals(mailbagInTransaction.getFlightNumber())){
					         return true;
				            }else if(mailbag.getFlightSequenceNumber()!=mailbagInTransaction.getFlightSequenceNumber()){
					        return true;
				             }
		              
		           
	            }
	     }
	            }
     } 
     //Added as part of bug ICRD-333932 by A-5526 starts
     if(scannedMailDetailsVO.getMailDetails()!=null  && scannedMailDetailsVO.getMailDetails().size()>0){
		         
    	 boolean reassignFlag=false;        		             
    	 for(MailbagVO mailbagInTransaction:scannedMailDetailsVO.getMailDetails()){      
				          
		  				//Added as part of BUG ICRD-332154 by A-5526 starts
		  				MailbagPK mailPK = new MailbagPK();
		  				Mailbag mail=null;      
						mailPK.setCompanyCode(mailbagInTransaction.getCompanyCode());
						mailPK.setMailSequenceNumber(mailbagInTransaction.getMailSequenceNumber());
						try {
							 mail = Mailbag.find(mailPK);  
						}catch (FinderException e) {                
							mail=null;        
						}
						
						
						if(MailConstantsVO.MAIL_STATUS_AWB_BOOKED.equals(mail.getLatestStatus())){
						   reassignFlag=false;
						   return false;
						}
						   if(mail!=null && MailConstantsVO.OPERATION_OUTBOUND.equals(mail.getOperationalStatus()) &&
										//mail.getUldNumber()!=null && !mail.getUldNumber().equals(mailbagInTransaction.getContainerNumber()) &&
										mail.getFlightNumber().equals(mailFlightSummaryVO.getFlightNumber()) && 
										mail.getFlightSequenceNumber()==mailFlightSummaryVO.getFlightSequenceNumber() &&
										mail.getScannedPort().equals(logonAttributes.getAirportCode())){
							       
								    reassignFlag = true;
			      
			             }     
    	 }
    	 
    	 return reassignFlag;
    	 
     }      
		  				 
   //Added as part of bug ICRD-333932 by A-5526 ends     

	return false;
}
/**
 * @author A-8061
 * @param selectedMailBagVO
 */
public void validateMailTags(Collection<MailbagVO> selectedMailBagVO) throws SystemException {
	
	
	OfficeOfExchangeVO officeOfExchangeVO=null;
	for(MailbagVO mailbagVO :selectedMailBagVO){
		try {
			officeOfExchangeVO =new MailController().validateOfficeOfExchange(mailbagVO.getCompanyCode(), mailbagVO.getOoe());
		} catch (SystemException systemException) {
			systemException.printStackTrace();
		}
		
		if(officeOfExchangeVO==null){
			throw new SystemException(ERROR_INVALID_EXCHANGE);
		}
		
		try {
			officeOfExchangeVO =new MailController().validateOfficeOfExchange(mailbagVO.getCompanyCode(), mailbagVO.getDoe());
		} catch (SystemException systemException) {
			systemException.printStackTrace();
		}
		if(officeOfExchangeVO==null){
			throw new SystemException(ERROR_INVALID_EXCHANGE);
		}
		
		try{
			Integer.parseInt(mailbagVO.getDespatchSerialNumber().trim());
		}catch(Exception exception){
			throw new SystemException(ERROR_INVALID_DSN);
		}
	
	}

}
/**
 * @author A-5526
 * @param mailFlightSummaryVO
 * @throws SystemException
 */
public void unassignEmptyULDs(MailFlightSummaryVO mailFlightSummaryVO) throws SystemException {
	OperationalFlightVO operationalFlightVO= new OperationalFlightVO();
	   operationalFlightVO.setCompanyCode(mailFlightSummaryVO.getCompanyCode());
	   operationalFlightVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
	   operationalFlightVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
	   operationalFlightVO.setCarrierId(mailFlightSummaryVO.getCarrierId());
	   operationalFlightVO.setPol(mailFlightSummaryVO.getAirportCode());
	
	new MailController().unassignEmptyUldsinMailFlight(operationalFlightVO);

}

}
