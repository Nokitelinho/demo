package com.ibsplc.icargo.services.uld.defaults.micro;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.beanutils.BeanUtils;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAirportLocationVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageRepairDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDiscrepancyFilterVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDiscrepancyVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionFilterVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionListVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDReturnTxnVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDListFilterVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDListVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDValidationVO;
import com.ibsplc.icargo.business.uld.defaults.vo.micro.server.RecordULDMovementMicroVO;
import com.ibsplc.icargo.business.uld.defaults.vo.micro.server.TransactionListMicroVO;
import com.ibsplc.icargo.business.uld.defaults.vo.micro.server.TransactionMicroVO;
import com.ibsplc.icargo.business.uld.defaults.vo.micro.server.ULDAirportLocationMicroVO;
import com.ibsplc.icargo.business.uld.defaults.vo.micro.server.ULDDamageRepairDetailsMicroVO;
import com.ibsplc.icargo.business.uld.defaults.vo.micro.server.ULDDiscrepancyFilterMicroVO;
import com.ibsplc.icargo.business.uld.defaults.vo.micro.server.ULDDiscrepancyMicroVO;
import com.ibsplc.icargo.business.uld.defaults.vo.micro.server.ULDListFilterMicroVO;
import com.ibsplc.icargo.business.uld.defaults.vo.micro.server.ULDListMicroPageVO;
import com.ibsplc.icargo.business.uld.defaults.vo.micro.server.ULDListMicroVO;
import com.ibsplc.icargo.business.uld.defaults.vo.micro.server.ULDMicroVO;
import com.ibsplc.icargo.business.uld.defaults.vo.micro.server.ULDTransactionDetailsMicroVO;
import com.ibsplc.icargo.business.uld.defaults.vo.micro.server.ULDValidationMicroVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.services.soap.SOAPEndPoint;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageFilterVO;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;


@Module("uld")
@SubModule("defaults")
public class UldDefaultsSOAPEndPoint
	extends SOAPEndPoint implements  UldDefaultsSOAPIntf {
	/*
	 * Format of a timestamp. Used for converting a LocalDate object representing
     * a Timestamp to a String. eg; for passing the timestamp to an HHT client 
     */
    private static final String TIMESTAMP_FORMAT = "dd-MM-yyyy HH:mm:ss.SSS";

	private Log log= LogFactory.getLogger("ULD_DEFAULTS");
	private static final String MODULE="UldDefaultsSOAPEndPoint";
	private static final String ULD_TRANSACTION_REF_NUMBER = "uld.transaction.refnumber";
	/**
	 * message standard to be set
	 */
	private static final String MESSAGE_STANDARD = "AHM";
	/**
	 * message type to be set
	 */
	private static final String MESSAGE_TYPE = "LUC";
	/**
	 * @author A-2052 This method is used to validate the ULD number.
	 * @param uldNumber
	 * @param companyCode
	 * @throws RemoteException
	 */

	public String validateULDFormat(String companyCode,String uldNumber) throws RemoteException {
		log.log(Log.FINE,
						"<<----------------UldDefaultsSOAPEndPoint------------>>validateULDFormat");
		log.log(Log.FINE, " uldNumber ...\n", uldNumber);
		Boolean found = false;
		String flag = "N";
		found = (Boolean) despatchRequest("validateULDFormat", companyCode,uldNumber);
		log.log(Log.FINE, "found------------>>", found);
		if (found) {
			// found= new Boolean(false);
			flag = "Y";
		} else {
			flag = "N";
		}
		log.log(Log.FINE, "flag-before return----------->>", flag);
		return flag;
	}

	/**
	 * @author A-2052 This method is used to save the ULD number.
	 * @param uldVO
	 * @throws RemoteException
	 */
	public void setLocationForULD(ULDMicroVO uldVO) throws RemoteException {
		log.log(Log.FINE,
				"<<----------------setLocationForULD------------>>>>>>", uldVO);
		log.log(Log.FINE,
						"@@@@@@@@@@ Before despatching ---> >>>>>>>setLocationForULD");
		//LocalDate localDate = new LocalDate(LocalDate.NO_STATION,Location.NONE, true);
		Collection<ULDVO> vos = new ArrayList<ULDVO>();
		ULDVO vo = new ULDVO();
		vo.setCompanyCode(uldVO.getCompanyCode());
    	vo.setCurrentStation(uldVO.getCurrentStation());
    	vo.setLocationType(uldVO.getLocationType());
    	vo.setFacilityType(uldVO.getLocationType());
    	vo.setLocation(uldVO.getLocation());
    	vo.setUldNumber(uldVO.getUldNumber());
    	vo.setRemarks(uldVO.getRemarks());
    	vo.setOperationalFlag(AbstractVO.OPERATION_FLAG_UPDATE);
    	vo.setLastUpdateUser(uldVO.getLastUpdateUser());
    	//vo.setLastUpdateTime(localDate.setDateAndTime(uldVO.getLastUpdateTime()));
    	//code is modified for Optimistic Concurrency for bug int_ULD512 a-2620
    	if (uldVO.getLastUpdateTime() != null
				&& uldVO.getLastUpdateTime().trim().length() > 0) {
			log.log(Log.FINE, " <<< LastUpdateTime  not  null ...new>>>");
			LocalDate localDate = new LocalDate(LocalDate.NO_STATION,
				Location.NONE, true);
		vo.setLastUpdateTime(localDate.setDateAndTime(uldVO.getLastUpdateTime(),TIMESTAMP_FORMAT));
		log.log(Log.FINE, " LastUpdateTime  ", vo.getLastUpdateTime());
		log.log(Log.FINE, " LastUpdateTime  ", vo.getLastUpdateTime().toTimeStampFormat());
		log.log(Log.FINE, " LastUpdateTime  ", TimeConvertor.toStringFormat(vo.getLastUpdateTime(), "dd-MMM-yyyy HH:mm:ss:SSS"));
    	}
    	log.log(Log.FINE, "setLocationForULD------------>>>>>>", vo);
		vos.add(vo);
    	log.log(Log.FINE,
				"<<----------------setLocationForULD-------vos----->>", vos);
		despatchRequest("setLocationForULD", vos);
	}

	/**
	 * @author A-2052 This method is used to list the ULD number.
	 * @param uldNumber
	 * @throws RemoteException
	 */
	public ULDListMicroVO findULDList(String companyCode,String uldNumber) throws RemoteException {
		log.log(Log.FINE, "<<----------------findULDList------------>>");
		ULDListFilterVO vo = new ULDListFilterVO();
		ULDListMicroVO uldListMicroVO = new ULDListMicroVO();
		vo.setCompanyCode(companyCode);
		vo.setUldNumber(uldNumber);
		vo.setUldRangeTo(-1);
		vo.setUldRangeFrom(-1);
		log.log(Log.FINE, "vo------------>>", vo);
		log.log(Log.FINE, "@@ Before despatching ---> >>>>>>>findULDList");
		Page<ULDListVO> page = despatchRequest("findULDList", vo, 1);
		if (page != null && page.size() > 0) {
			for (ULDListVO uldListVO : page) {
				log.log(Log.FINE,
						"uldListVO from delegate!!!!------------>>>>>>>>>>",
						uldListVO);
				uldListMicroVO = new ULDListMicroVO();
				uldListMicroVO.setCompanyCode(uldListVO.getCompanyCode());
				uldListMicroVO.setUldNumber(uldListVO.getUldNumber());
				uldListMicroVO.setCurrentStation(uldListVO
						.getCurrentStation());
				uldListMicroVO.setLocation(uldListVO.getLocation());
				uldListMicroVO.setOverallStatus(uldListVO.getOverallStatus());
				uldListMicroVO.setOwnerStation(uldListVO.getOwnerStation());
				uldListMicroVO.setPartyLoaned(uldListVO.getPartyLoaned());
				uldListMicroVO.setPartyBorrowed(uldListVO.getPartyBorrowed());
				log.log(Log.FINE, "uldListMicroVO.getPartyLoaned**********",
						uldListMicroVO.getPartyLoaned());
				log.log(Log.FINE, "uldListMicroVO.getPartyBorrowed***********",
						uldListMicroVO.getPartyBorrowed());
				uldListMicroVO.setDamageStatus(uldListVO.getDamageStatus());
				//uldListMicroVO.setLocationType(uldListVO.getFacilityType());
				uldListMicroVO.setLastUpdateUser(uldListVO.getLastUpdateUser());
				//modified for optimestic concurrency
				/*if(uldListVO.getLastUpdateTime() != null){
					uldListMicroVO.setLastUpdateTime(uldListVO.getLastUpdateTime().toDisplayFormat());
				}*/
				uldListMicroVO.setLastUpdateTime(uldListVO.getLastUpdateTime() == null ? null
						: uldListVO.getLastUpdateTime().toTimeStampFormat());
				//ends here
				break;
			}
		}
		log.log(Log.FINE, "uldListMicroVO------------>>", uldListMicroVO);
		return uldListMicroVO;
	}

	/**
	 * @author A-2037
	 * This  is  used to record the ULD Movement Details
	 * @param uldNOs
	 * @param uldMovementVos
	 * @throws RemoteException
	 */
	public void saveULDMovement(RecordULDMovementMicroVO recordULDMovementMicroVO)
	throws RemoteException{

		log.entering(MODULE," Inside UldDefaultsMiscSOAPEndPoint");
		//log.entering(MODULE,"GOT---> >>>>>>>recordULDMovementMicroVO"+
		//		recordULDMovementMicroVO);

		String uldNO=recordULDMovementMicroVO.getUldNumber();
		Collection<String> uldNOs=new ArrayList<String>();
		uldNOs.add(uldNO);

		Collection<ULDMovementVO> uLDMovementVOs=null;

		if(recordULDMovementMicroVO.getULDMovementMicroVOs()!=null &&
				recordULDMovementMicroVO.getULDMovementMicroVOs().length>0){


					log.entering(MODULE," Inside if>>>>>>>>>>>>>>>>>>>>>>");

			uLDMovementVOs=new ArrayList<ULDMovementVO>();
			ULDMovementVO uLDMovementVO=null;

			for(int i=0;i<recordULDMovementMicroVO.
			getULDMovementMicroVOs().length;i++){

				log.entering(MODULE," Inside for#######################");

				LocalDate repDate=new LocalDate("TRV",Location.STN,false);
				uLDMovementVO=new ULDMovementVO();

				uLDMovementVO.setLastUpdatedUser(
										recordULDMovementMicroVO.getULDMovementMicroVOs()[i].
						getLastUpdatedUser());

				uLDMovementVO.setCompanyCode(
				recordULDMovementMicroVO.getULDMovementMicroVOs()[i].
							getCompanyCode());


				uLDMovementVO.setCarrierCode(
						recordULDMovementMicroVO.getULDMovementMicroVOs()[i].
						getCarrierCode());
				uLDMovementVO.setContent(
						recordULDMovementMicroVO.getULDMovementMicroVOs()[i].
						getContent());
				uLDMovementVO.setCurrentStation(
						recordULDMovementMicroVO.getULDMovementMicroVOs()[i].
						getCurrentStation());
				uLDMovementVO.setDummyMovement(
						recordULDMovementMicroVO.getULDMovementMicroVOs()[i].
						isDummyMovement());
				uLDMovementVO.setFlightCarrierIdentifier(
						recordULDMovementMicroVO.getULDMovementMicroVOs()[i].
						getFlightCarrierIdentifier());

				repDate.setDate(recordULDMovementMicroVO.
						getULDMovementMicroVOs()[i].getFlightDate());
				uLDMovementVO.setFlightDate(repDate);
				uLDMovementVO.setFlightDateString(
						recordULDMovementMicroVO.getULDMovementMicroVOs()[i].
						getFlightDateString());
				uLDMovementVO.setFlightNumber(
						recordULDMovementMicroVO.getULDMovementMicroVOs()[i].
						getFlightNumber());
				uLDMovementVO.setPointOfLading(
						recordULDMovementMicroVO.getULDMovementMicroVOs()[i].
						getPointOfLading());
				uLDMovementVO.setPointOfUnLading(
						recordULDMovementMicroVO.getULDMovementMicroVOs()[i].
						getPointOfUnLading());
				uLDMovementVO.setRemark(
						recordULDMovementMicroVO.getULDMovementMicroVOs()[i].
						getRemark());
				uLDMovementVO.setUldNumber(
						recordULDMovementMicroVO.getULDMovementMicroVOs()[i].
						getUldNumber());

				uLDMovementVO.setUpdateCurrentStation(
						recordULDMovementMicroVO.getULDMovementMicroVOs()[i].
						isUpdateCurrentStation());


				uLDMovementVOs.add(uLDMovementVO);
			}

		}
		log.entering(MODULE,"Before depatching");

		despatchRequest("saveULDMovement",uldNOs,uLDMovementVOs);
		log.entering(MODULE,"after depatching");

	}


	/**
	 * @author A-1868
	 * This method is used to save the ULD discrepancy details.
	 * @param uldDiscrepancyMicroVO
	 * @throws RemoteException
	 */
	public String saveULDDiscrepancyDetails(ULDDiscrepancyMicroVO uldDiscrepancyMicroVO)
		throws RemoteException {
				ULDDiscrepancyVO uldDiscrepancyVO=new ULDDiscrepancyVO();
				//Collection<ULDDiscrepancyVO> uldDiscrepancyVOColl=new ArrayList<ULDDiscrepancyVO>();
				uldDiscrepancyVO.setCompanyCode(uldDiscrepancyMicroVO.getCompanyCode());
				uldDiscrepancyVO.setUldNumber(uldDiscrepancyMicroVO.getUldNumber());
				uldDiscrepancyVO.setDiscrepencyCode(uldDiscrepancyMicroVO.getDiscrepencyCode());
				LocalDate ld1 = new LocalDate(uldDiscrepancyMicroVO.getAirportCode(),Location.ARP,false);
				uldDiscrepancyVO.setDiscrepencyDate(ld1.setDate(uldDiscrepancyMicroVO.getDiscrepencyDate()));
				uldDiscrepancyVO.setFacilityType(uldDiscrepancyMicroVO.getFacilityType());
				uldDiscrepancyVO.setLocation(uldDiscrepancyMicroVO.getLocation());
				uldDiscrepancyVO.setReportingStation(uldDiscrepancyMicroVO.getReportingStation());
				uldDiscrepancyVO.setRemarks(uldDiscrepancyMicroVO.getRemarks());
				uldDiscrepancyVO.setOperationFlag(ULDDiscrepancyVO.OPERATION_FLAG_INSERT);
    			log.log(Log.FINE,
						"\n saveULDDiscrepencyForHHT------------>>\n",
						uldDiscrepancyVO);
			String returnValue  = despatchRequest("saveULDDiscrepencyForHHT",uldDiscrepancyVO);
			log.log(Log.FINE,
					"\n saveULDDiscrepencyForHHT-------returnValue----->>\n",
					returnValue);
			return returnValue;
	}

	/**
	 * @author A-2052 This method is used to list the ULD number.
	 * @param uldNumber
	 * @throws RemoteException
	 */
	public TransactionListMicroVO listULDTransactionDetails(String companyCode,String uldNumber,String txntype)
			throws RemoteException {
		//return a vo having airlineid,double n txnstatus
		log.log(Log.FINE, "<<----------------listULDTransactionDetails------------>>");
		TransactionFilterVO vo = new TransactionFilterVO();
		TransactionListMicroVO transactionListMicroVO = new TransactionListMicroVO();
		TransactionListVO transactionListVO  = new TransactionListVO();
		vo.setCompanyCode(companyCode);
		vo.setUldNumber(uldNumber);
		vo.setTransactionType(txntype);
		vo.setTransactionStatus("ALL");
		vo.setPageNumber(1);
		log.log(Log.FINE, "@@ Before despatching ---> >>>>>>>findULDList", vo);
		transactionListVO = despatchRequest("listULDTransactionDetails", vo);
		if (transactionListVO != null) {
			log.log(Log.FINE, "uldListVO from delegate------------>>",
					transactionListVO);
			Collection<ULDTransactionDetailsVO> col = transactionListVO.getTransactionDetailsPage();
			log.log(Log.FINE, "col------------>>", col);
			if(col !=null){
				for(ULDTransactionDetailsVO uldTransactionDetailsVO : col){
					log.log(Log.FINE, "uldTransactionDetailsVO------------>>",
							uldTransactionDetailsVO);
					transactionListMicroVO.setCompanyCode(uldTransactionDetailsVO.getCompanyCode());
					transactionListMicroVO.setUldNumber(uldTransactionDetailsVO.getUldNumber());
					transactionListMicroVO.setTransactionType(uldTransactionDetailsVO.getTransactionType());
					transactionListMicroVO.setOperationalFlag(uldTransactionDetailsVO.getOperationalFlag());
					transactionListMicroVO.setReturnStationCode(uldTransactionDetailsVO.getReturnStationCode());
					transactionListMicroVO.setCurrency(uldTransactionDetailsVO.getCurrency());
					transactionListMicroVO.setDemurrageAmount(uldTransactionDetailsVO.getDemurrageAmount());
					transactionListMicroVO.setDamageStatus(uldTransactionDetailsVO.getDamageStatus());
					transactionListMicroVO.setTransactionStatus(uldTransactionDetailsVO.getTransactionStatus());
					transactionListMicroVO.setOperationalAirlineIdentifier(uldTransactionDetailsVO.getOperationalAirlineIdentifier());
					transactionListMicroVO.setTransactionRefNumber(uldTransactionDetailsVO.getTransactionRefNumber());
					log.log(Log.FINE,
							"transactionListMicroVO------------>>000000000",
							transactionListMicroVO);
					if(uldTransactionDetailsVO.getTransactionDate() != null){
						log
								.log(
										Log.FINE,
										"uldTransactionDetailsVO.getTransactionDate()----",
										uldTransactionDetailsVO.getTransactionDate());
						transactionListMicroVO.setTransactionDate(uldTransactionDetailsVO.getTransactionDate().toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
						log.log(Log.FINE,
								"transactionListMicroVO.getTransactionDate()",
								transactionListMicroVO.getTransactionDate());
					}
					log.log(Log.FINE,
							"transactionListMicroVO------------>>111111111",
							transactionListMicroVO);
					log.log(Log.FINE,
							"transactionListMicroVO------------>>22222222",
							transactionListMicroVO);
					if(uldTransactionDetailsVO.getReturnDate() != null){
						log.log(Log.FINE,
								"uldTransactionDetailsVO.getReturnDate()----",
								uldTransactionDetailsVO.getReturnDate());
						transactionListMicroVO.setReturnDate(uldTransactionDetailsVO.getReturnDate().toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
						log.log(Log.FINE,
								"transactionListMicroVO.getReturnDate()",
								transactionListMicroVO.getReturnDate());
					}
					log.log(Log.FINE,
							"transactionListMicroVO------------>>33333",
							transactionListMicroVO);
					transactionListMicroVO.setPartyType(uldTransactionDetailsVO.getPartyType());
					transactionListMicroVO.setUldType(uldTransactionDetailsVO.getUldType());
					transactionListMicroVO.setTransactionNature(uldTransactionDetailsVO.getTransactionNature());
					transactionListMicroVO.setFromPartyCode(uldTransactionDetailsVO.getFromPartyCode());
					transactionListMicroVO.setToPartyCode(uldTransactionDetailsVO.getToPartyCode());
					transactionListMicroVO.setFromPartyIdentifier(uldTransactionDetailsVO.getFromPartyIdentifier());
					transactionListMicroVO.setToPartyIdentifier(uldTransactionDetailsVO.getToPartyIdentifier());
					transactionListMicroVO.setTransactionStationCode(uldTransactionDetailsVO.getTransactionStationCode());
					transactionListMicroVO.setTransationPeriod(uldTransactionDetailsVO.getTransationPeriod());
					transactionListMicroVO.setTransactionRemark(uldTransactionDetailsVO.getTransactionRemark());
					transactionListMicroVO.setCapturedRefNumber(uldTransactionDetailsVO.getCapturedRefNumber());
					transactionListMicroVO.setInvoiceStatus(uldTransactionDetailsVO.getInvoiceStatus());
					transactionListMicroVO.setWaived(uldTransactionDetailsVO.getWaived());
					transactionListMicroVO.setTaxes(uldTransactionDetailsVO.getTaxes());
					transactionListMicroVO.setReturnedBy(uldTransactionDetailsVO.getReturnedBy());
					transactionListMicroVO.setOtherCharges(uldTransactionDetailsVO.getOtherCharges());
					transactionListMicroVO.setTotal(uldTransactionDetailsVO.getTotal());
					transactionListMicroVO.setInvoiceRefNumber(uldTransactionDetailsVO.getInvoiceRefNumber());
					transactionListMicroVO.setPaymentStatus(uldTransactionDetailsVO.getPaymentStatus());
					transactionListMicroVO.setReturnRemark(uldTransactionDetailsVO.getReturnRemark());
					transactionListMicroVO.setControlReceiptNumber(uldTransactionDetailsVO.getControlReceiptNumber());
					transactionListMicroVO.setNumberMonths(uldTransactionDetailsVO.getNumberMonths());
					transactionListMicroVO.setSelectNumber(uldTransactionDetailsVO.getSelectNumber());
					transactionListMicroVO.setRepairSeqNumber(uldTransactionDetailsVO.getRepairSeqNumber());
					transactionListMicroVO.setStrTxnDate(uldTransactionDetailsVO.getStrTxnDate());
					transactionListMicroVO.setStrRetDate(uldTransactionDetailsVO.getStrRetDate());
					transactionListMicroVO.setReturnPartyCode(uldTransactionDetailsVO.getReturnPartyCode());
					transactionListMicroVO.setReturnPartyIdentifier(uldTransactionDetailsVO.getFromPartyIdentifier());
					transactionListMicroVO.setAgreementNumber(uldTransactionDetailsVO.getAgreementNumber());
					transactionListMicroVO.setToPartyName(uldTransactionDetailsVO.getToPartyName());
					transactionListMicroVO.setFromPartyName(uldTransactionDetailsVO.getFromPartyName());
					transactionListMicroVO.setCurrOwnerCode(uldTransactionDetailsVO.getFromPartyIdentifier());
					transactionListMicroVO.setTxStationCode(uldTransactionDetailsVO.getTxStationCode());
					transactionListMicroVO.setLastUpdateUser(uldTransactionDetailsVO.getLastUpdateUser());
					if(uldTransactionDetailsVO.getLastUpdateTime() != null){
						transactionListMicroVO.setLastUpdateTime(uldTransactionDetailsVO.getLastUpdateTime().toDisplayFormat());
					}


					}
			}
		}
		log.log(Log.FINE, "transactionListMicroVO------------>>44",
				transactionListMicroVO);
		return transactionListMicroVO;
	}

	/**
	 * @author A-2052 This method is used to save the Return ULD Transaction.
	 * @param vo
	 * @throws RemoteException
	 */
	public void saveReturnTransaction(TransactionListMicroVO vo) throws RemoteException {
		log.log(Log.FINE, "<<----------------saveReturnTransaction------------>>");
		log
				.log(
						Log.FINE,
						"@@ Before despatching ---> >>>>>>>saveReturnTransaction--TransactionListMicroVO",
						vo);
		LocalDate localDate = new LocalDate(LocalDate.NO_STATION,Location.NONE, true);

		LogonAttributes logonAttributes=null;
			try {
				logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
			} catch (SystemException e) {
				// To be reviewed Auto-generated catch block
				e.getMessage();
			}
		/*TransactionListVO transactionListVO = new TransactionListVO();
		Collection<ULDTransactionDetailsVO> vos = new ArrayList<ULDTransactionDetailsVO>();
		ULDTransactionDetailsVO uldTransactionDetailsVO = new ULDTransactionDetailsVO();
		//uldTransactionDetailsVO.setCompanyCode(vo.getCompanyCode());
		//uldTransactionDetailsVO.setOperationalAirlineIdentifier(vo.getOperationalAirlineIdentifier());
		//uldTransactionDetailsVO.setTransactionStatus(vo.getTransactionStatus());
		//uldTransactionDetailsVO.setUldNumber(vo.getUldNumber());
		//uldTransactionDetailsVO.setOperationalFlag(vo.getOperationalFlag());
		//uldTransactionDetailsVO.setTransactionType(vo.getTransactionType());
		//uldTransactionDetailsVO.setReturnStationCode(vo.getReturnStationCode());
		//uldTransactionDetailsVO.setCurrency(vo.getCurrency());
		//uldTransactionDetailsVO.setDamageStatus(vo.getDamageStatus());
		//uldTransactionDetailsVO.setDemurrageAmount(vo.getDemurrageAmount());
		//uldTransactionDetailsVO.setTransactionRefNumber(vo.getTransactionRefNumber());
		//uldTransactionDetailsVO.setPartyType(vo.getPartyType());
		log.log(Log.FINE, "saveReturnTransaction------------>>uldTransactionDetailsVO"
				+uldTransactionDetailsVO);

		LocalDate currentdate=new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
		uldTransactionDetailsVO.setReturnDate(currentdate);

		log.log(Log.FINE, "uldTransactionDetailsVO.setReturnDate---------->>"
				+uldTransactionDetailsVO.getReturnDate());
		log.log(Log.FINE, "saveReturnTransaction------------>>uldTransactionDetailsVO"
				+uldTransactionDetailsVO);



		uldTransactionDetailsVO.setCompanyCode(vo.getCompanyCode());
		uldTransactionDetailsVO.setUldNumber(vo.getUldNumber());
		uldTransactionDetailsVO.setTransactionType(vo.getTransactionType());
		uldTransactionDetailsVO.setOperationalFlag(vo.getOperationalFlag());
		uldTransactionDetailsVO.setReturnStationCode(vo.getReturnStationCode());
		uldTransactionDetailsVO.setCurrency(vo.getCurrency());
		uldTransactionDetailsVO.setDemurrageAmount(vo.getDemurrageAmount());
		uldTransactionDetailsVO.setDamageStatus(vo.getDamageStatus());
		uldTransactionDetailsVO.setTransactionStatus(vo.getTransactionStatus());
		uldTransactionDetailsVO.setOperationalAirlineIdentifier(vo.getOperationalAirlineIdentifier());
		uldTransactionDetailsVO.setTransactionRefNumber(vo.getTransactionRefNumber());
		log.log(Log.FINE, "uldTransactionDetailsVO------------>>000000000" + uldTransactionDetailsVO);
		//LocalDate currentdate=new LocalDate(vo.getTransactionDate(),Location.ARP,true);
		log.log(Log.FINE, "vo.getTransactionDate()------------>>000000000"+vo.getTransactionDate() );
		if(vo.getTransactionDate().trim().length()>0){
			LocalDate txnDate = new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
			log.log(Log.FINE, "txnDate------------>>000000000"+txnDate);
			uldTransactionDetailsVO.setTransactionDate(txnDate.setDate(vo.getTransactionDate()));
			log.log(Log.FINE, "uldTransactionDetailsVO---------"+uldTransactionDetailsVO);
		}
		//uldTransactionDetailsVO.setTransactionDate(vo.getTransactionDate().toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));

		log.log(Log.FINE, "uldTransactionDetailsVO------------>>111111111" + uldTransactionDetailsVO);
		log.log(Log.FINE, "uldTransactionDetailsVO------------>>22222222" + uldTransactionDetailsVO);
		uldTransactionDetailsVO.setPartyType(vo.getPartyType());
		uldTransactionDetailsVO.setUldType(vo.getUldType());
		uldTransactionDetailsVO.setTransactionNature(vo.getTransactionNature());
		uldTransactionDetailsVO.setFromPartyCode(vo.getFromPartyCode());
		uldTransactionDetailsVO.setToPartyCode(vo.getToPartyCode());
		uldTransactionDetailsVO.setFromPartyIdentifier(vo.getFromPartyIdentifier());
		log.log(Log.FINE, "uldTransactionDetailsVO------------>>33333333333" + uldTransactionDetailsVO);
		uldTransactionDetailsVO.setToPartyIdentifier(vo.getToPartyIdentifier());
		uldTransactionDetailsVO.setTransactionStationCode(vo.getTransactionStationCode());
		uldTransactionDetailsVO.setTransationPeriod(vo.getTransationPeriod());
		uldTransactionDetailsVO.setTransactionRemark(vo.getTransactionRemark());
		uldTransactionDetailsVO.setCapturedRefNumber(vo.getCapturedRefNumber());
		log.log(Log.FINE, "uldTransactionDetailsVO------------>>444444" + uldTransactionDetailsVO);
		uldTransactionDetailsVO.setInvoiceStatus(vo.getInvoiceStatus());
		uldTransactionDetailsVO.setWaived(vo.getWaived());
		uldTransactionDetailsVO.setTaxes(vo.getTaxes());
		uldTransactionDetailsVO.setReturnedBy(vo.getReturnedBy());
		uldTransactionDetailsVO.setOtherCharges(vo.getOtherCharges());
		uldTransactionDetailsVO.setTotal(vo.getTotal());
		uldTransactionDetailsVO.setInvoiceRefNumber(vo.getInvoiceRefNumber());
		uldTransactionDetailsVO.setPaymentStatus(vo.getPaymentStatus());
		uldTransactionDetailsVO.setReturnRemark(vo.getReturnRemark());
		uldTransactionDetailsVO.setControlReceiptNumber(vo.getControlReceiptNumber());
		uldTransactionDetailsVO.setNumberMonths(vo.getNumberMonths());
		uldTransactionDetailsVO.setSelectNumber(vo.getSelectNumber());
		uldTransactionDetailsVO.setRepairSeqNumber(vo.getRepairSeqNumber());
		log.log(Log.FINE, "uldTransactionDetailsVO------------>>55555555" + uldTransactionDetailsVO);
		uldTransactionDetailsVO.setStrTxnDate(vo.getStrTxnDate());
		uldTransactionDetailsVO.setStrRetDate(vo.getStrRetDate());
		uldTransactionDetailsVO.setReturnPartyCode(vo.getReturnPartyCode());
		uldTransactionDetailsVO.setReturnPartyIdentifier(vo.getReturnPartyIdentifier());
		uldTransactionDetailsVO.setAgreementNumber(vo.getAgreementNumber());
		uldTransactionDetailsVO.setToPartyName(vo.getToPartyName());
		uldTransactionDetailsVO.setFromPartyName(vo.getFromPartyName());
		uldTransactionDetailsVO.setCurrOwnerCode(vo.getCurrOwnerCode());
		log.log(Log.FINE, "uldTransactionDetailsVO------------>>56666" + uldTransactionDetailsVO);
		uldTransactionDetailsVO.setTxStationCode(vo.getTxStationCode());
		uldTransactionDetailsVO.setLastUpdateUser(vo.getLastUpdateUser());


		vos.add(uldTransactionDetailsVO);
		log.log(Log.FINE, "saveReturnTransaction------------>>vos"
				+vos);
		transactionListVO.setUldTransactionsDetails(vos);
		transactionListVO.setReturnStationCode(vo.getReturnStationCode());
		transactionListVO.setTransactionType(vo.getTransactionType());

		/*AccessoryTransactionVO accessoryTransactionVO = new AccessoryTransactionVO();
		Collection<AccessoryTransactionVO> accessoryTransactionVOs
					= new ArrayList<AccessoryTransactionVO>();
		accessoryTransactionVO.setCompanyCode(vo.getCompanyCode());
		accessoryTransactionVO.setOperationalAirlineIdentifier(vo.getOperationalAirlineIdentifier());
		accessoryTransactionVO.setOperationalFlag(vo.getOperationalFlag());
		accessoryTransactionVO.setReturnStation(vo.getReturnStationCode());
		accessoryTransactionVO.setTransactionType(vo.getTransactionType());
		accessoryTransactionVO.setTransactionRefNumber(vo.getTransactionRefNumber());
		accessoryTransactionVO.setPartyType(vo.getPartyType());

		accessoryTransactionVOs.add(accessoryTransactionVO);*/
		/*log.log(Log.FINE, "<<----------------listULDTransactionDetails inside save------------>>");
		TransactionFilterVO filtervo = new TransactionFilterVO();
		TransactionListVO transactionListVo  = new TransactionListVO();
		Collection<AccessoryTransactionVO> accessoryTransactionVOs = new ArrayList<AccessoryTransactionVO>();
		filtervo.setCompanyCode(vo.getCompanyCode());
		filtervo.setUldNumber(vo.getUldNumber());
		filtervo.setTransactionType(vo.getTransactionType());
		filtervo.setTransactionStatus("T");
		filtervo.setPageNumber(1);
		log.log(Log.FINE, "vo------------>>" + filtervo);
		log.log(Log.FINE, "@@ Before despatching ---> >>>>>>>findULDList");
		transactionListVo = despatchRequest("listULDTransactionDetails", filtervo);
		if (transactionListVo != null) {
			log.log(Log.FINE, "uldListVO from delegate------------>>"
						+ transactionListVo);
			 accessoryTransactionVOs = transactionListVo.getAccessoryTransactions();
		}
		transactionListVO.setAccessoryTransactions(accessoryTransactionVOs);*/
		/*transactionListVO.setAccessoryTransactions(null);
		log.log(Log.FINE, "before despatching---saveReturnTransaction------------>>transactionListVO"
				+transactionListVO);
				//calculate charge
				log.log(Log.FINE, "calculateReturnULDCharges  before save------------>>vos"
				+vos);
			Collection<ULDTransactionDetailsVO> newULDTxnDetailsVOs
				= new ArrayList<ULDTransactionDetailsVO>();
			newULDTxnDetailsVOs = 	despatchRequest("calculateReturnULDCharges", vos);
		log.log(Log.FINE, "calculateReturnULDCharges  after save------------>>newULDTxnDetailsVOs"
				+newULDTxnDetailsVOs);
		if(newULDTxnDetailsVOs == null || newULDTxnDetailsVOs.size() == 0){
				log.log(Log.FINE," <<----------------noagreement-->>error");
				SOAPUtils.raiseSoapFault("noagreement","noagreement");
		}
		transactionListVO.setUldTransactionsDetails(newULDTxnDetailsVOs);
		log.log(Log.FINE," <<----------------after calculate now into save---->>"+transactionListVO);
		despatchRequest("saveReturnTransaction", transactionListVO);

		*/
		//method changed to returnuldfromoperatons

		Collection<ULDReturnTxnVO> uldReturnTxnVOs =
				new ArrayList<ULDReturnTxnVO>();
		ULDReturnTxnVO uldReturnTxnVO = new ULDReturnTxnVO () ;

		uldReturnTxnVO.setCompanyCode(vo.getCompanyCode());
		uldReturnTxnVO.setUldNumber(vo.getUldNumber());
		uldReturnTxnVO.setTransactionType(vo.getTransactionType());
		//from partycode givesd current arp code
		uldReturnTxnVO.setTransactionAirportCode(vo.getFromPartyCode());
		LocalDate currentdate=new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
		uldReturnTxnVO.setTransactionDate(currentdate);
		uldReturnTxnVO.setPartyType(vo.getPartyType());
		uldReturnTxnVO.setPartyCode(vo.getReturnPartyCode());
		uldReturnTxnVO.setReturnRemark(vo.getReturnRemark());
		//uldReturnTxnVO.setLastUpdateUser(vo.getLastUpdateUser());
		//uldReturnTxnVO.setLastUpdateTime(localDate.setDateAndTime(vo.getLastUpdateTime()));

		log
				.log(
						Log.FINE,
						"<<----------------returnULDFromOperations11-------uldReturnTxnVO----->>",
						uldReturnTxnVO);
		uldReturnTxnVOs.add(uldReturnTxnVO);
		log
				.log(
						Log.FINE,
						"<<----------------returnULDFromOperations  before settg to server------------>>",
						uldReturnTxnVOs);
		despatchRequest("returnULDFromOperations", uldReturnTxnVOs);

	}

	/**
	 * @author A-2052 This method is used to list the List ULD.
	 * @param uldListFilterMicroVO
	 * @return
	 * @throws RemoteException
	 */
	public ULDListMicroPageVO findULDList(ULDListFilterMicroVO uldListFilterMicroVO) throws RemoteException {
		log.log(Log.FINE, "<<----------------findULDList------------>>");
		log.log(Log.FINE, "@@ Before despatching ---> >>>>>>>findULDList",
				uldListFilterMicroVO);
		Page<ULDListVO> uldListVOs = new Page<ULDListVO>(
				new ArrayList<ULDListVO>(), 0, 0, 0, 0, 0, false);
		ULDListMicroPageVO uldListMicroPageVO = new ULDListMicroPageVO();
		ULDListFilterVO uldListFilterVO = new ULDListFilterVO();
		uldListFilterVO.setCompanyCode(uldListFilterMicroVO.getCompanyCode());
		uldListFilterVO.setUldNumber(uldListFilterMicroVO.getUldNumber());
		uldListFilterVO.setUldTypeCode(uldListFilterMicroVO.getUldTypeCode());
		uldListFilterVO.setAirlineCode(uldListFilterMicroVO.getAirlineCode());
		uldListFilterVO.setOverallStatus(uldListFilterMicroVO.getOverallStatus());
		uldListFilterVO.setAirlineidentifier(uldListFilterMicroVO.getAirlineidentifier());
		uldListFilterVO.setUldRangeTo(-1);
		uldListFilterVO.setUldRangeFrom(-1);
		log.log(Log.FINE, "findULDList------------>>", uldListFilterVO);
		uldListVOs = despatchRequest("findULDList", uldListFilterVO,
				uldListFilterMicroVO.getDisplayPage());
		Collection<ULDListMicroVO> ULDListMicroVOs = new ArrayList<ULDListMicroVO>();
		if(uldListVOs != null && uldListVOs.size()> 0) {
			uldListMicroPageVO.setAbsoluteIndex(uldListVOs.getAbsoluteIndex());
			uldListMicroPageVO.setActualPageSize(uldListVOs.getActualPageSize());
			uldListMicroPageVO.setDefaultPageSize(uldListVOs.getDefaultPageSize());
			uldListMicroPageVO.setEndIndex(uldListVOs.getEndIndex());
			uldListMicroPageVO.setPageNumber(uldListVOs.getPageNumber());
			uldListMicroPageVO.setStartIndex(uldListVOs.getStartIndex());
			uldListMicroPageVO.setNextPage(uldListVOs.hasNextPage());
			log.log(Log.FINE, "uldListVOs------------>>", uldListVOs);
			for(ULDListVO vo:uldListVOs){
				ULDListMicroVO uldListMicroVO = new ULDListMicroVO();
				uldListMicroVO.setCompanyCode(vo.getCompanyCode());
				uldListMicroVO.setUldNumber(vo.getUldNumber());
				uldListMicroVO.setPartyLoaned(vo.getPartyLoaned());
				uldListMicroVO.setPartyBorrowed(vo.getPartyBorrowed());
				uldListMicroVO.setDamageStatus(vo.getDamageStatus());
				uldListMicroVO.setOverallStatus(vo.getOverallStatus());
				uldListMicroVO.setCurrentStation(vo.getCurrentStation());
				uldListMicroVO.setLocation(vo.getLocation());
				ULDListMicroVOs.add(uldListMicroVO);
			}
			log.log(Log.FINE, "ULDListMicroVOs------------>>", ULDListMicroVOs);
			ULDListMicroVO[] uldListMicroVO =
				ULDListMicroVOs.toArray(new ULDListMicroVO[ULDListMicroVOs.size()]);
			uldListMicroPageVO.setULDListMicroVOs(uldListMicroVO);
		}
		log.log(Log.FINE, "uldListMicroPageVO------------>>",
				uldListMicroPageVO);
		return uldListMicroPageVO;
	}


	/**
	 * @author A-2006
	 * This method is used to save the ULD Damage details.
	 * @param ULDDamageMicroVO
	 */
	public void saveULDDamage(ULDDamageRepairDetailsMicroVO uldDamageRepairDetailsMicroVO)
	 	throws RemoteException{

		LogonAttributes logonAttributes=null;
		try {
			logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		} catch (SystemException e) {
			// To be reviewed Auto-generated catch block
			e.getMessage();
		}



		log
				.log(
						Log.INFO,
						"Before depatching>>>>>>.saveULDDamage>>>>uldDamageRepairDetailsMicroVO",
						"", uldDamageRepairDetailsMicroVO);
		ULDDamageRepairDetailsVO uldDamageRepairDetailsVO=new ULDDamageRepairDetailsVO();
		 uldDamageRepairDetailsVO.setCompanyCode(uldDamageRepairDetailsMicroVO.getCompanyCode());
		 uldDamageRepairDetailsVO.setCurrentStation(uldDamageRepairDetailsMicroVO.getCurrentStation());
		 uldDamageRepairDetailsVO.setUldNumber(uldDamageRepairDetailsMicroVO.getUldNumber());
		 uldDamageRepairDetailsVO.setLastUpdatedUser(uldDamageRepairDetailsMicroVO.getLastUpdatedUser());
		 uldDamageRepairDetailsVO.setDamageStatus(uldDamageRepairDetailsMicroVO.getDamageStatus());
		 uldDamageRepairDetailsVO.setOverallStatus(uldDamageRepairDetailsMicroVO.getOverallStatus());

		 //added for optimistic concurrency implementation <HHT327>  
		 if (uldDamageRepairDetailsMicroVO.getLastUpdatedTime() != null 
				 && uldDamageRepairDetailsMicroVO.getLastUpdatedTime().trim().length() > 0) {

				LocalDate localDate = new LocalDate(LocalDate.NO_STATION,Location.NONE, true);
				uldDamageRepairDetailsVO.setLastUpdatedTime(localDate.setDateAndTime
						(uldDamageRepairDetailsMicroVO.getLastUpdatedTime(),TIMESTAMP_FORMAT));

			}

		 //To Do>>>>>Set other fields to damageVo


		 Collection<ULDDamageVO> uLDDamageVOs=null;

		 if(uldDamageRepairDetailsMicroVO.getUldDamageMicroVOs() != null  &&
				 uldDamageRepairDetailsMicroVO. getUldDamageMicroVOs().length>0){

			  uLDDamageVOs=new ArrayList<ULDDamageVO>();
			 ULDDamageVO damageVo=null;

			 for ( int i=0;i<uldDamageRepairDetailsMicroVO. getUldDamageMicroVOs().length;i++){
				 LocalDate repDate=new LocalDate(logonAttributes.getStationCode(),Location.STN,false);


				 damageVo=new ULDDamageVO();
				 damageVo.setDamageCode(uldDamageRepairDetailsMicroVO. getUldDamageMicroVOs()[i].getDamageCode());
				 damageVo.setPosition(uldDamageRepairDetailsMicroVO. getUldDamageMicroVOs()[i].getPosition());
				 damageVo.setSeverity(uldDamageRepairDetailsMicroVO. getUldDamageMicroVOs()[i].getSeverity());
				 damageVo.setReportedStation(uldDamageRepairDetailsMicroVO. getUldDamageMicroVOs()[i].getReportedStation());
				 damageVo.setReportedDate(repDate);
				 damageVo.setRemarks(uldDamageRepairDetailsMicroVO. getUldDamageMicroVOs()[i].getRemarks());
				 damageVo.setOperationFlag(ULDDamageVO.OPERATION_FLAG_INSERT);
				 damageVo.setLastUpdateUser(logonAttributes.getUserId());



				 //To Do>>>>>Set other fields to damageVo



				 uLDDamageVOs.add(damageVo);
			 }


		 }
		 uldDamageRepairDetailsVO.setUldDamageVOs(uLDDamageVOs);



		 log
				.log(
						Log.INFO,
						"Before depatching>>>>>>.saveULDDamage++++++++>>>>uldDamageRepairDetailsVO",
						"", uldDamageRepairDetailsVO);
			despatchRequest("saveULDDamage",uldDamageRepairDetailsVO);

	 }


	/**
	 * @author A-2006 This method is used to validate ULD No
	 * @param companyCode, uldNumber
	 * @return ULDValidationMicroVO
	 * @throws RemoteException
	 */


	 public ULDValidationMicroVO validateULD(String companyCode, String uldNumber)
		throws RemoteException {

		ULDValidationMicroVO uldValidationMicroVO=null;

			log.log(Log.FINE,">>Inside UldDefaultsSOAPEndPoint");

			log.log(Log.FINE, " GOT ...uldNumber>>>>>>>>>>.\n", uldNumber);
			ULDValidationVO uldValidationVO=
				                                despatchRequest("validateULD",companyCode,uldNumber);

			if(uldValidationVO!=null){

				log.log(Log.FINE, "@@uldValidationVO", uldValidationVO);
				uldValidationMicroVO=new ULDValidationMicroVO();
				uldValidationMicroVO.setUldNumber(uldNumber);

				uldValidationMicroVO.setCurrentStation(uldValidationVO.getCurrentStation());
				uldValidationMicroVO.setOverallStatus(uldValidationVO.getOverallStatus());
				uldValidationMicroVO.setDamageStatus(uldValidationVO.getDamageStatus());


			 log.log(Log.FINE,
					"@@ after  despatching ---> uldValidationMicroVO",
					uldValidationMicroVO);
			}



		log.log(Log.FINE, "@@B4 Returning---> uldValidationMicroVO",
				uldValidationMicroVO);
		return uldValidationMicroVO;
	}





	/**
	 * @author A-2052 This method is used to validate facility Code
	 * @param uldVO
	 * @return
	 * @throws RemoteException
	 */
	public String listULDAirportLocation(ULDMicroVO uldVO) throws RemoteException {
		log.log(Log.FINE, "<<----------------listULDAirportLocation------------>>");
		log.log(Log.FINE,
				"@@ Before despatching ---> >>>>>>>listULDAirportLocation",
				uldVO);
		String validFacilityCode="false";
		Collection<ULDAirportLocationVO> uldAirportLocationVOs= null;
		uldAirportLocationVOs = despatchRequest("listULDAirportLocation", uldVO.getCompanyCode(),
				uldVO.getCurrentStation(),uldVO.getLocationType());
		log.log(Log.FINE, "validFacilityCode------------>>11",
				uldAirportLocationVOs);
		for(ULDAirportLocationVO uldAirportLocationVO:uldAirportLocationVOs){
			if(uldAirportLocationVO.getAirportCode()!=null &&
					uldAirportLocationVO.getAirportCode().equals(uldVO.getCurrentStation().toUpperCase()) &&
					uldAirportLocationVO.getFacilityType()!=null &&
					uldAirportLocationVO.getFacilityType().equals(uldVO.getLocationType().toUpperCase())	){
				log.log(Log.FINE, "uldAirportLocationVO------------&&&&&&&>>",
						uldAirportLocationVO);
				log.log(Log.FINE, "uldVO------------******>>", uldVO);
				if(uldAirportLocationVO.getFacilityCode()!=null &&
						uldAirportLocationVO.getFacilityCode().equals(uldVO.getLocation().toUpperCase())){
						validFacilityCode="true";
						log.log(Log.FINE, "validFacilityCode------------>>11",
								validFacilityCode);
						break;
				}
			}
	    }
		log.log(Log.FINE, "validFacilityCode------------>>", validFacilityCode);
		return validFacilityCode;
	}

	/**
	 * @author A-2052 This method is used to populate facility Code lov
	 * @param fieldTypes
	 * @return
	 * @throws RemoteException
	 */
	public ULDAirportLocationMicroVO[] listULDAirportLocation
		(String companyCode, String currentStation,String facilityType)
			throws RemoteException {
		Collection<ULDAirportLocationVO> uldAirportLocationVOs= new ArrayList<ULDAirportLocationVO>();
		ArrayList<ULDAirportLocationMicroVO> uldAirportLocationMicroVOs =
			new ArrayList<ULDAirportLocationMicroVO>();
		ULDAirportLocationMicroVO uldAirportLocationMicroVO = new ULDAirportLocationMicroVO();
		log.log(Log.FINE,
				"<<----UldDefaultsSOAPEndPoint----->>listULDAirportLocation");
		log.log(Log.FINE,
				"<<----UldDefaultsSOAPEndPoint----->>before despatch",
				companyCode, "and ", currentStation, "and ", facilityType);
		uldAirportLocationVOs = despatchRequest(
				"listULDAirportLocation", companyCode,currentStation, facilityType);

		log
				.log(
						Log.FINE,
						"<<----UldDefaultsSOAPEndPoint---After despatch..uldAirportLocationVOs------>>",
						uldAirportLocationVOs);
		if(uldAirportLocationVOs.size()>0){
		for (ULDAirportLocationVO vo : uldAirportLocationVOs){
			uldAirportLocationMicroVO = new ULDAirportLocationMicroVO();
			uldAirportLocationMicroVO.setCompanyCode(vo.getCompanyCode());
			uldAirportLocationMicroVO.setFacilityType(vo.getFacilityType());
			uldAirportLocationMicroVO.setFacilityCode(vo.getFacilityCode());
			log.log(Log.FINE, "uldAirportLocationMicroVO------>>",
					uldAirportLocationMicroVO);
			uldAirportLocationMicroVOs.add(uldAirportLocationMicroVO);
		}
		}
		ULDAirportLocationMicroVO[] uldAirportLocationMicroVos = uldAirportLocationMicroVOs
				.toArray(new ULDAirportLocationMicroVO[uldAirportLocationMicroVOs.size()]);
		log
				.log(
						Log.FINE,
						"<<----UldDefaultsSOAPEndPoint---return uldAirportLocationMicroVos------>>",
						uldAirportLocationMicroVos);
		return uldAirportLocationMicroVos;
	}


	/**
	 * @author A-2052 This method is used to save Loan Borrow ULD Transaction.
	 * @param transactionMicroVO
	 * @throws RemoteException
	 */
	public String saveULDTransaction(TransactionMicroVO transactionMicroVO)
		throws RemoteException {
		String error= null;
		log.log(Log.FINE,
				"<<----------------saveULDTransaction------------>>>>>>",
				transactionMicroVO);
		log.log(Log.FINE,
						"@@@@@@@@@@ Before despatching ---> >>>>>>>saveULDTransaction");
		LogonAttributes logonAttributes=null;
		try {
			logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		} catch (SystemException e) {
			e.getMessage();
		}
		log.log(Log.FINE,
						"@@@@@@@@@@ Before despatching ---> >>>>>>>LocalDate");
		LocalDate currentdate=new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
		log.log(Log.FINE,
						"@@@@@@@@@@ Before despatching ---> 111111>>>>>>>LocalDate");
		TransactionVO transactionVO = new TransactionVO ();
		transactionVO.setCompanyCode(transactionMicroVO.getCompanyCode());
		transactionVO.setTransactionType(transactionMicroVO.getTransactionType());
		transactionVO.setTransactionNature(transactionMicroVO.getTransactionNature());
		log.log(Log.FINE, "transactionMicroVO------2", transactionMicroVO);
		transactionVO.setTransactionStation(transactionMicroVO.getTransactionStation());
		transactionVO.setPartyType(transactionMicroVO.getPartyType());
		transactionVO.setFromPartyCode(transactionMicroVO.getFromPartyCode());
		transactionVO.setFromPartyName(transactionMicroVO.getFromPartyName());
		transactionVO.setToPartyCode(transactionMicroVO.getToPartyCode());
		transactionVO.setToPartyName(transactionMicroVO.getToPartyName());
		log.log(Log.FINE, "transactionMicroVO------3.getStrTransactionDate()",
				transactionMicroVO.getStrTransactionDate());
		transactionVO.setStrTransactionDate(transactionMicroVO.getStrTransactionDate());
		log.log(Log.FINE, "transactionMicroVO------3a", transactionMicroVO);
		transactionVO.setOperationalFlag(transactionMicroVO.getOperationalFlag());
		log.log(Log.FINE, "transactionMicroVO------3b", transactionMicroVO);
		transactionVO.setOperationalAirlineIdentifier(transactionMicroVO.getOperationalAirlineIdentifier());
		log.log(Log.FINE, "transactionMicroVO------3c", transactionMicroVO);
		log.log(Log.FINE, "transactionMicroVO------3d", transactionMicroVO);
		transactionVO.setTransactionRemark(transactionMicroVO.getTransactionRemark());
		log.log(Log.FINE, "transactionMicroVO------3e", transactionMicroVO);
		transactionVO.setCurrOwnerCode(transactionMicroVO.getCurrOwnerCode());
		log.log(Log.FINE, "transactionMicroVO------3f", transactionMicroVO);
		transactionVO.setTransactionStatus(transactionMicroVO.getTransactionStatus());
		log.log(Log.FINE, "transactionMicroVO------4", transactionMicroVO);
		ULDTransactionDetailsMicroVO[] uldTransactionDetailsMicroVOs =
			transactionMicroVO.getULDTransactionDetailsMicroVOs();
		log.log(Log.FINE,
				"saveULDTransaction-------uldTransactionDetailsMicroVOs",
				uldTransactionDetailsMicroVOs);
		int index = uldTransactionDetailsMicroVOs.length ;
		log.log(Log.FINE, "saveULDTransaction-------index", index);
		ULDTransactionDetailsVO uldTransactionDetailsVO = new ULDTransactionDetailsVO();
		Collection<ULDTransactionDetailsVO> uldTransactionDetailsVOs =
			new ArrayList<ULDTransactionDetailsVO>();
			for(int i = 0; i <index ; i++){
				log.log(Log.FINE, "saveULDTransaction-------i", i);
				uldTransactionDetailsVO.setCompanyCode(uldTransactionDetailsMicroVOs[i].getCompanyCode());
				uldTransactionDetailsVO.setUldNumber(uldTransactionDetailsMicroVOs[i].getUldNumber());
				uldTransactionDetailsVO.setTransactionRefNumber(uldTransactionDetailsMicroVOs[i].getTransactionRefNumber());
				uldTransactionDetailsVO.setTransactionType(uldTransactionDetailsMicroVOs[i].getTransactionType());
				uldTransactionDetailsVO.setUldType(uldTransactionDetailsMicroVOs[i].getUldType());
				uldTransactionDetailsVO.setTransactionNature(uldTransactionDetailsMicroVOs[i].getTransactionNature());
				uldTransactionDetailsVO.setPartyType(uldTransactionDetailsMicroVOs[i].getPartyType());
				uldTransactionDetailsVO.setFromPartyCode(uldTransactionDetailsMicroVOs[i].getFromPartyCode());
				uldTransactionDetailsVO.setFromPartyName(uldTransactionDetailsMicroVOs[i].getFromPartyName());
				uldTransactionDetailsVO.setToPartyCode(uldTransactionDetailsMicroVOs[i].getToPartyCode());
				uldTransactionDetailsVO.setToPartyName(uldTransactionDetailsMicroVOs[i].getToPartyName());
				uldTransactionDetailsVO.setFromPartyIdentifier(uldTransactionDetailsMicroVOs[i].getFromPartyIdentifier());
				uldTransactionDetailsVO.setToPartyIdentifier(uldTransactionDetailsMicroVOs[i].getToPartyIdentifier());
				log.log(Log.FINE,
						"saveULDTransaction-------0uldTransactionDetailsVO",
						uldTransactionDetailsVO);
				uldTransactionDetailsVO.setTransactionStationCode(uldTransactionDetailsMicroVOs[i].getTransactionStationCode());
				uldTransactionDetailsVO.setTransationPeriod(uldTransactionDetailsMicroVOs[i].getTransationPeriod());
				uldTransactionDetailsVO.setTransactionRemark(uldTransactionDetailsMicroVOs[i].getTransactionRemark());
				uldTransactionDetailsVO.setCapturedRefNumber(uldTransactionDetailsMicroVOs[i].getCapturedRefNumber());
				uldTransactionDetailsVO.setDamageStatus(uldTransactionDetailsMicroVOs[i].getDamageStatus());
				uldTransactionDetailsVO.setReturnStationCode(uldTransactionDetailsMicroVOs[i].getReturnStationCode());
				uldTransactionDetailsVO.setReturnedBy(uldTransactionDetailsMicroVOs[i].getReturnedBy());
				uldTransactionDetailsVO.setCurrOwnerCode(uldTransactionDetailsMicroVOs[i].getCurrOwnerCode());
				uldTransactionDetailsVO.setOperationalFlag(uldTransactionDetailsMicroVOs[i].getOperationalFlag());
				uldTransactionDetailsVO.setOperationalAirlineIdentifier(uldTransactionDetailsMicroVOs[i].getOperationalAirlineIdentifier());
				uldTransactionDetailsVO.setTxStationCode(uldTransactionDetailsMicroVOs[i].getTxStationCode());
				uldTransactionDetailsVO.setTransactionRemark(transactionMicroVO.getTransactionRemark());
				uldTransactionDetailsVO.setCurrOwnerCode(uldTransactionDetailsMicroVOs[i].getCurrOwnerCode());
				uldTransactionDetailsVO.setTransactionStatus(uldTransactionDetailsMicroVOs[i].getTransactionStatus());
				uldTransactionDetailsVO.setLastUpdateUser(uldTransactionDetailsMicroVOs[i].getLastUpdateUser());
				uldTransactionDetailsVO.setControlReceiptNumber(uldTransactionDetailsMicroVOs[i].getControlReceiptNumber());
				uldTransactionDetailsVO.setUldConditionCode(uldTransactionDetailsMicroVOs[i].getUldConditionCode());

				log.log(Log.FINE,
						"saveULDTransaction-------uldTransactionDetailsVO",
						uldTransactionDetailsVO);
				uldTransactionDetailsVO.setTransactionDate(currentdate);
				uldTransactionDetailsVO.setReturnDate(currentdate);
				log
						.log(
								Log.FINE,
								"saveULDTransaction-------111222uldTransactionDetailsVO",
								uldTransactionDetailsVO);
				uldTransactionDetailsVOs.add(uldTransactionDetailsVO);
				
				log
						.log(
								Log.FINE,
								"uldTransactionDetailsMicroVOs[i].getToPartyIdentifier() -=-=->",
								uldTransactionDetailsMicroVOs[i].getToPartyIdentifier());
				int oprId;
				oprId= (Integer)despatchRequest("findOperationalAirlineForULD", transactionMicroVO.getCompanyCode(),
						uldTransactionDetailsMicroVOs[i].getUldNumber());
				log.log(Log.FINE, "oprId -=-=->", oprId);
				if(uldTransactionDetailsMicroVOs[i].getToPartyIdentifier() == oprId && uldTransactionDetailsMicroVOs[i].getToPartyIdentifier()!= 0){
					//todo
					log.log(Log.FINE, "error here !!!!! -=-=->oprId");
					return "cannotloan";
				}
				log.log(Log.FINE, "going for validateULDForWarehouseOccupancy -=-=->");
				Collection<ULDValidationVO> validationvo = new ArrayList<ULDValidationVO>();
	    		ULDValidationVO vo=new ULDValidationVO();
	    		vo.setUldNumber(uldTransactionDetailsMicroVOs[i].getUldNumber().toUpperCase());
	    		vo.setCompanyCode(uldTransactionDetailsMicroVOs[i].getCompanyCode());
	    		validationvo.add(vo);
	    		log.log(Log.FINE, "validationvo -=-=->", validationvo);
				despatchRequest("validateULDForWarehouseOccupancy",validationvo);
	    		log.log(Log.FINE, "going for findAgreementNumberForTransaction -=-=->");
	    		Collection<String> agreementNumbers= despatchRequest("findAgreementNumberForTransaction", uldTransactionDetailsVOs);
	    		log.log(Log.FINE, "agreementNumbers-=-=->", agreementNumbers);
				
		}
		transactionVO.setUldTransactionDetailsVOs(uldTransactionDetailsVOs);
		log
				.log(
						Log.FINE,
						"<<----------------saveULDTransaction-------transactionVO----->>",
						transactionVO);
		transactionVO.setTransactionDate(currentdate);
		log.log(Log.FINE,
				"\n\n\n\n\nsaveULDTransaction------000-transactionVO----->>",
				transactionVO);
		Collection<ErrorVO> errorsaftersave = new ArrayList<ErrorVO>();
		ArrayList<String> txnRefNo = new ArrayList<String>();
		
		
		Collection<String> crnNums=despatchRequest("checkForDuplicateCRN", transactionVO.getCompanyCode(),transactionVO);
		
		if (crnNums != null && crnNums.size() > 0 ) {
			//todo
			log.log(Log.FINE, "error here !!!!! -=-=->crnNums");
			return "duplicateCRNexists";
		}
		
		errorsaftersave = despatchRequest("saveULDTransaction", transactionVO);
		log.log(Log.FINE, "saveULDTransaction-------errorsaftersave----->>",
				errorsaftersave);
		if (errorsaftersave != null && errorsaftersave.size() > 0) {
			log.log(Log.FINE, "\n\n\n\n ****ERROR PRESENT******");
			Collection<ErrorVO> errorsnew = new ArrayList<ErrorVO>();
			for (ErrorVO errorvo : errorsaftersave) {
				if (ULD_TRANSACTION_REF_NUMBER.equals(errorvo.getErrorCode())) {
					Object[] object = errorvo.getErrorData();
					String uld = (String) object[0];
					log.log(Log.FINE, "\n\n\n\n ****uld******", uld);
					txnRefNo.add(uld);
				}
			}
			log.log(Log.FINE, "txnRefNo------------------>", txnRefNo);
		}
		/*log.log(Log.FINE, "***********transactionVO.getTransactionType()^^^^^^^^^&&&&&&&\n\n\n\n\n\n\n\n\n"
					+ transactionVO.getTransactionType());
		if("L".equals(transactionVO.getTransactionType())) {

				LUCMessageVO messageVO = new LUCMessageVO();
				messageVO.setCarrierCode(logonAttributes.getOwnAirlineCode());
				messageVO.setAirlineCode(logonAttributes.getOwnAirlineCode());
				messageVO.setCompanyCode(logonAttributes.getCompanyCode());
				messageVO.setMessageStandard(MESSAGE_STANDARD);
				messageVO.setMessageType(MESSAGE_TYPE);
				messageVO.setLastUpdateUser(logonAttributes.getUserId());
				LocalDate dateOfTransfer = new LocalDate(logonAttributes.getAirportCode(),Location.ARP, true);
				// dateOfTransfer.setDate(dateOfTransfer.toDisplayDateOnlyFormat(),CALENDAR_DATE_FORMAT);
				log.log(Log.FINE, "date of transfer------->" + dateOfTransfer);

				Collection<LUCULDDetailsVO> uldDetailsVOs = new ArrayList<LUCULDDetailsVO>();
				StringBuilder dateoFTransfer = new StringBuilder();
				dateoFTransfer.append(dateOfTransfer.toString().substring(0, 2));
				dateoFTransfer.append(dateOfTransfer.toString().substring(3, 6)
						.toUpperCase());
				dateoFTransfer.append(dateOfTransfer.toString().substring(9, 11));
				log.log(Log.FINE,"date of transfer after formatting--------------->"+dateoFTransfer);
				StringBuilder time = new StringBuilder();
				//time.append(String.valueOf(LocalDate.HOUR_OF_DAY));
				//time.append(String.valueOf(LocalDate.MINUTE));
				time.append(dateOfTransfer.toString().substring(12, 14));
				time.append(dateOfTransfer.toString().substring(15, 17));

				log.log(Log.FINE, "time of transfer---------------->" + time);
				log.log(Log.FINE, "transactionVO.getUldTransactionDetailsVOs()------------------>"
							+ transactionVO.getUldTransactionDetailsVOs());
				for(ULDTransactionDetailsVO detailsVO :transactionVO.getUldTransactionDetailsVOs()){
				log.log(Log.FINE, "detailsVO------------------>"
							+ detailsVO);
					LUCULDDetailsVO uldDetailsVO = new LUCULDDetailsVO();
					log.log(Log.FINE, "dateoFTransfer.toString()------------------>"
							+ dateoFTransfer.toString());
					uldDetailsVO.setDateofTransfer(dateoFTransfer.toString());
					log.log(Log.FINE, "detailsVO.getTxStationCode()------------------>"
							+ detailsVO.getTxStationCode());
					uldDetailsVO.setDestinationLocation(detailsVO.getTxStationCode());
					log.log(Log.FINE, "detailsVO.getTxStationCode()------------------>"
							+ detailsVO.getTxStationCode());
					uldDetailsVO.setLocationOftransfer(logonAttributes.getAirportCode());
					log.log(Log.FINE, "logonAttributes.getAirportCode()------------------>"
							+ logonAttributes.getAirportCode());
					uldDetailsVO.setTimeofTransfer(Integer.parseInt(time.toString()));
					log.log(Log.FINE, "uldDetailsVO.getTimeofTransfer()------------------>"
							+ uldDetailsVO.getTimeofTransfer());
					log.log(Log.FINE, "time.toString()------------------>"
							+ time.toString());
					LUCULDIdentificationVO identificationVO = new LUCULDIdentificationVO();
					log.log(Log.FINE, "detailsVO.getUldNumber()------------------>"
							+ detailsVO.getUldNumber());
					String uldNumber = detailsVO.getUldNumber();
					log.log(Log.FINE, "txnRefNo------------------>"
							+ txnRefNo);
					for(String str:txnRefNo){
					String[] uldnum=str.split("~");
					log.log(Log.FINE, "str------------------>"
							+ str);
					log.log(Log.FINE, "uldNumber------------------>"
							+ uldNumber);
					log.log(Log.FINE, "uldnum[0]----------------->"
							+ uldnum[0]);
					log.log(Log.FINE, "uldnum[1]----------------->"
							+ uldnum[1]);
					log.log(Log.FINE, "Integer.parseInt(uldnum[1])----------------->"
							+ Integer.parseInt(uldnum[1]));
					if(uldNumber.equals(uldnum[0])){
						identificationVO.setTransactionRefNumber((int)Integer.parseInt(uldnum[1]));
					}
			}


			identificationVO.setUldType(uldNumber.substring(0, 3));
			identificationVO.setUldOwnerCode(uldNumber.substring(uldNumber
					.length() - 2));
			identificationVO.setUldSerialNumber(uldNumber.substring(3,
					uldNumber.length() - 2));
			uldDetailsVO.setUldIdentificationVO(identificationVO);

			LUCReceivingPartyDetailsVO recevingPartyDetailsVO = new LUCReceivingPartyDetailsVO();
			recevingPartyDetailsVO.setCarrierCode(detailsVO
					.getToPartyCode().toUpperCase());
			uldDetailsVO.setReceivingPartyDetailsVO(recevingPartyDetailsVO);
			LUCTransferringPartyDetailsVO transferringPartyDetailsVO = new LUCTransferringPartyDetailsVO();
			transferringPartyDetailsVO.setCarrierCode(detailsVO
					.getFromPartyCode().toUpperCase());
			uldDetailsVO
					.setTransferringPartyDetailsVO(transferringPartyDetailsVO);
			LUCULDConditionDetailsVO conditionDetailsVO = new LUCULDConditionDetailsVO();
				if ("Y".equals(detailsVO.getDamageStatus())) {
					conditionDetailsVO.setDamageCode("DAM");
				} else {
					conditionDetailsVO.setDamageCode("SER");
				}
			uldDetailsVO.setUldConditionDetailsVO(conditionDetailsVO);
			uldDetailsVOs.add(uldDetailsVO);
				}
			log.log(Log.FINE, "uld details vos------------------->"
							+ uldDetailsVOs);
			messageVO.setUldDetails(uldDetailsVOs);
			messageVO.setStationCode(logonAttributes.getAirportCode());
			log.log(Log.FINE, "MESSAGE VO TO SERVER===============" + messageVO);
			despatchRequest("generateLUCMessage", messageVO);
		}*/
		return error;
	}

	/**
	 * @author A-2052 This method is used to list the ULD number for hht.
	 * @param uldNumber
	 * @throws RemoteException
	 */
	public ULDListMicroVO findULDListForHHT(String companyCode,String uldNumber) throws RemoteException {
		log.log(Log.FINE, "<<----------------findULDListForHHT------------>>");
		log.log(Log.FINE,
				"@@ Before despatching ---> >>>>>>>findULDListForHHT",
				companyCode, ",", uldNumber);
		ULDListVO vo= despatchRequest("findULDListForHHT", companyCode, uldNumber);

		log.log(Log.FINE,
				"<<----------------findULDListForHHT------------>>vo", vo);
		ULDListMicroVO uldListMicroVO = new ULDListMicroVO();
		try {
			BeanUtils.copyProperties(uldListMicroVO,vo);
		} catch (IllegalAccessException e) {
			// To be reviewed Auto-generated catch block
			e.getMessage();
		} catch (InvocationTargetException e) {
			// To be reviewed Auto-generated catch block
			e.getMessage();
		}
		log.log(Log.FINE, "uldListMicroVO------------>>return", uldListMicroVO);
		return uldListMicroVO;
	}


	/**
	 * @author A-2052 This method is used to list ULD No
	 * @param companyCode, uldNumber
	 * @return findULDDetails
	 * @throws RemoteException
	 */


	 public ULDListMicroVO findULDDetails(String companyCode, String uldNumber)
		throws RemoteException {

		 ULDListMicroVO uldListMicroVO=null;

			log.log(Log.FINE,">>Inside UldDefaultsSOAPEndPoint----->>>findULDDetails");

			log.log(Log.FINE, " GOT ...uldNumber>>>>>>>>>>.\n", uldNumber);
			ULDVO uldVO=
				                                despatchRequest("findULDDetails",companyCode,uldNumber);

			log.log(Log.FINE, "@@ after  despatching ---> uldVO", uldVO);
			if(uldVO!=null){

				uldListMicroVO = new ULDListMicroVO();
				uldListMicroVO.setCompanyCode(uldVO.getCompanyCode());
				uldListMicroVO.setUldNumber(uldVO.getUldNumber());
				uldListMicroVO.setUldContour(uldVO.getUldContour());
				uldListMicroVO.setTareWeight(uldVO.getDisplayTareWeight());
				uldListMicroVO.setLocation(uldVO.getLocation());
				uldListMicroVO.setCurrentStation(uldVO.getCurrentStation());
				uldListMicroVO.setOverallStatus(uldVO.getOverallStatus());
				uldListMicroVO.setMaxGrossWt(uldVO.getDisplayStructuralWeight());

			 log.log(Log.FINE, "@@ after  despatching ---> uldListMicroVO",
					uldListMicroVO);
			}



		log.log(Log.FINE, "@@B4 Returning---> uldListMicroVO", uldListMicroVO);
		return uldListMicroVO;
	}

	 /**
	  *
	  * @param discrepancyVO
	  * @throws RemoteException
	  */
	 public void captureMissingULD(ULDDiscrepancyMicroVO discrepancyMicroVO)throws RemoteException {

			LogonAttributes logonAttributes=null;
			try {
				logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
			} catch (SystemException e) {
				e.getMessage();
			}

		 ULDDiscrepancyVO uldDiscrepancyVO=new ULDDiscrepancyVO();
		uldDiscrepancyVO.setCompanyCode(discrepancyMicroVO.getCompanyCode());
		uldDiscrepancyVO.setDiscrepencyCode(discrepancyMicroVO.getDiscrepencyCode());
		uldDiscrepancyVO.setUldNumber(discrepancyMicroVO.getUldNumber());
		uldDiscrepancyVO.setReportingStation(discrepancyMicroVO.getReportingStation());
		uldDiscrepancyVO.setRemarks(discrepancyMicroVO.getRemarks());

		LocalDate descDate=new LocalDate(uldDiscrepancyVO.getReportingStation(),Location.ARP,false);
        uldDiscrepancyVO.setDiscrepencyDate(descDate);
        LocalDate lastupdateTime=new LocalDate(uldDiscrepancyVO.getReportingStation(),Location.ARP,true);
        uldDiscrepancyVO.setLastUpdatedTime(lastupdateTime);
        uldDiscrepancyVO.setLastUpdatedUser(logonAttributes.getUserId());


		log.log(Log.FINE,
				"<<----------------captureMissingULD-------vo----->>",
				uldDiscrepancyVO);
		despatchRequest("captureMissingULD", uldDiscrepancyVO);

  }
	/**
	 *
	 */
	public  void  updateULDInventory(ULDDiscrepancyMicroVO discrepancyMicroVO)throws RemoteException {

			LogonAttributes logonAttributes=null;
			try {
				logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
			} catch (SystemException e) {
				e.getMessage();
			}

		 ULDDiscrepancyVO uldDiscrepancyVO=new ULDDiscrepancyVO();
			uldDiscrepancyVO.setCompanyCode(discrepancyMicroVO.getCompanyCode());
			uldDiscrepancyVO.setDiscrepencyCode(discrepancyMicroVO.getDiscrepencyCode());
			uldDiscrepancyVO.setUldNumber(discrepancyMicroVO.getUldNumber());
			uldDiscrepancyVO.setReportingStation(discrepancyMicroVO.getReportingStation());
			uldDiscrepancyVO.setRemarks(discrepancyMicroVO.getRemarks());


			LocalDate descDate=new LocalDate(uldDiscrepancyVO.getReportingStation(),Location.ARP,false);
	        uldDiscrepancyVO.setDiscrepencyDate(descDate);
	        LocalDate lastupdateTime=new LocalDate(uldDiscrepancyVO.getReportingStation(),Location.ARP,true);
	        uldDiscrepancyVO.setLastUpdatedTime(lastupdateTime);
	        uldDiscrepancyVO.setLastUpdatedUser(logonAttributes.getUserId());


			log.log(Log.FINE,
					"<<----------------captureMissingULD-------vo----->>",
					uldDiscrepancyVO);
			despatchRequest("updateULDInventory", uldDiscrepancyVO);


	}


	/**
	  * @author A-2572
	  * @param discrepancyVO
	  * @throws RemoteException
	  */
	public ULDDamageRepairDetailsMicroVO findULDDamageDetails(String companyCode,String uldNumber)
		throws RemoteException{
			log.log(Log.FINE, "<<---findULDDamageDetails--->>");

			ULDDamageRepairDetailsMicroVO uldDamageRepairDetailsMicroVO=null;
			ULDDamageFilterVO uldDamageFilterVO=new ULDDamageFilterVO();
			uldDamageFilterVO.setCompanyCode(companyCode);
			uldDamageFilterVO.setUldNumber(uldNumber);

			ULDDamageRepairDetailsVO uldDamageRepairDetailsVO=despatchRequest("findULDDamageDetails",uldDamageFilterVO);
			log.log(Log.FINE, "<<---findULDDamageDetails--->>",
					uldDamageRepairDetailsVO);
			if(uldDamageRepairDetailsVO!=null){
			uldDamageRepairDetailsMicroVO=new ULDDamageRepairDetailsMicroVO();
			uldDamageRepairDetailsMicroVO.setCompanyCode(uldDamageRepairDetailsVO.getCompanyCode());
			uldDamageRepairDetailsMicroVO.setCurrentStation(uldDamageRepairDetailsVO.getCurrentStation());
			uldDamageRepairDetailsMicroVO.setDamageStatus(uldDamageRepairDetailsVO.getDamageStatus());
			uldDamageRepairDetailsMicroVO.setDamgePicture(uldDamageRepairDetailsVO.getDamgePicture());
			uldDamageRepairDetailsMicroVO.setInvestigationReport(uldDamageRepairDetailsVO.getInvestigationReport());
			//modofied for optimistic concurrency implementation   <HHT327>
			uldDamageRepairDetailsMicroVO.setLastUpdatedTime(
					uldDamageRepairDetailsVO.getLastUpdatedTime() == null ? null 
							: uldDamageRepairDetailsVO.getLastUpdatedTime().toTimeStampFormat());
			//uldDamageRepairDetailsMicroVO.setLastUpdatedTime(uldDamageRepairDetailsVO.getLastUpdatedTime());
			uldDamageRepairDetailsMicroVO.setLastUpdatedUser(uldDamageRepairDetailsVO.getLastUpdatedUser());
			uldDamageRepairDetailsMicroVO.setOverallStatus(uldDamageRepairDetailsVO.getOverallStatus());
			uldDamageRepairDetailsMicroVO.setSupervisor(uldDamageRepairDetailsVO.getSupervisor());
			uldDamageRepairDetailsMicroVO.setUldNumber(uldDamageRepairDetailsVO.getUldNumber());

			}
			log.log(Log.FINE,
					"<<--returning---uldDamageRepairDetailsMicroVO--->>",
					uldDamageRepairDetailsMicroVO);
			return uldDamageRepairDetailsMicroVO;

	}
	/**
	 * This method returns the ULDStockStatus for HHT
	 * @author A-2052
	 * @param uLDDiscrepancyFilterVO
	 * @return ULDDiscrepancyVO
	 * @throws BusinessDelegateException
	 */
	public ULDDiscrepancyMicroVO findULDStockStatusForHHT(ULDDiscrepancyFilterMicroVO
			uldDiscrepancyFilterMicroVO)throws RemoteException {
		
		log.log(Log.FINE,
				"\n<<----------------findULDStockStatusForHHT------------>>\n",
				uldDiscrepancyFilterMicroVO);
		ULDDiscrepancyVO uldDiscrepancyVO = null;
		ULDDiscrepancyMicroVO uldDiscrepancyMicroVO = null;
		
		ULDDiscrepancyFilterVO uldDiscrepancyFilterVO = new ULDDiscrepancyFilterVO();
		uldDiscrepancyFilterVO.setCompanyCode(uldDiscrepancyFilterMicroVO.getCompanyCode());
		uldDiscrepancyFilterVO.setUldNumber(uldDiscrepancyFilterMicroVO.getUldNumber());
		uldDiscrepancyFilterVO.setAgentCode(uldDiscrepancyFilterMicroVO.getAgentCode());
		uldDiscrepancyFilterVO.setAgentLocation(uldDiscrepancyFilterMicroVO.getAgentLocation());
		uldDiscrepancyFilterVO.setAirlineCode(uldDiscrepancyFilterMicroVO.getAirlineCode());
		uldDiscrepancyFilterVO.setOwnerStation(uldDiscrepancyFilterMicroVO.getOwnerStation());
		uldDiscrepancyFilterVO.setReportingStation(uldDiscrepancyFilterMicroVO.getReportingStation());
		uldDiscrepancyFilterVO.setUldOwnerIdentifier(uldDiscrepancyFilterMicroVO.getUldOwnerIdentifier());
		uldDiscrepancyFilterVO.setFacilityType(uldDiscrepancyFilterMicroVO.getFacilityType());

		log.log(Log.FINE, "before despatchg------------>>\n",
				uldDiscrepancyFilterVO);
		uldDiscrepancyVO = despatchRequest("findULDStockStatusForHHT", uldDiscrepancyFilterVO);

		if(uldDiscrepancyVO != null){
			
			uldDiscrepancyMicroVO = new ULDDiscrepancyMicroVO();
			
		uldDiscrepancyMicroVO.setAirportCode(uldDiscrepancyVO.getOwnerStation());
		uldDiscrepancyMicroVO.setCompanyCode(uldDiscrepancyVO.getCompanyCode());
		uldDiscrepancyMicroVO.setDiscrepencyCode(uldDiscrepancyVO.getDiscrepencyCode());
		if(uldDiscrepancyVO.getDiscrepencyDate() != null){
			log.log(Log.FINE, "uldDiscrepancyVO.getDiscrepencyDate()----",
					uldDiscrepancyVO.getDiscrepencyDate());
			uldDiscrepancyMicroVO.setDiscrepencyDate(uldDiscrepancyVO.getDiscrepencyDate().toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
			log.log(Log.FINE, "uldDiscrepancyMicroVO.setDiscrepencyDate..",
					uldDiscrepancyMicroVO.getDiscrepencyDate());
		}
		uldDiscrepancyMicroVO.setRemarks(uldDiscrepancyVO.getRemarks());
		uldDiscrepancyMicroVO.setReportingStation(uldDiscrepancyVO.getReportingStation());
		uldDiscrepancyMicroVO.setUldNumber(uldDiscrepancyVO.getUldNumber());
		uldDiscrepancyMicroVO.setUldStatus(uldDiscrepancyVO.getUldStatus());
		uldDiscrepancyMicroVO.setFacilityType(uldDiscrepancyVO.getFacilityType());
		uldDiscrepancyMicroVO.setLocation(uldDiscrepancyVO.getLocation());
		log.log(Log.FINE, "uldDiscrepancyMicroVO-------returng----->>",
				uldDiscrepancyMicroVO);
		}
		return uldDiscrepancyMicroVO;
		
	}
	/**
	 * 
	 * @param uldDiscrepancyMicroVO
	 * @return
	 * @throws RemoteException
	 * @author a-2572
	 */
	public String updateULDStockStatusForHHT(ULDDiscrepancyMicroVO uldDiscrepancyMicroVO) throws RemoteException {
		log.log(Log.FINE, "<<-----updateULDStockStatusForHHT------->>",
				uldDiscrepancyMicroVO);
		ULDDiscrepancyVO uldDiscrepancyVO = new ULDDiscrepancyVO();
		uldDiscrepancyVO.setOwnerStation(uldDiscrepancyMicroVO.getAirportCode());
		uldDiscrepancyVO.setCompanyCode(uldDiscrepancyMicroVO.getCompanyCode());
		uldDiscrepancyVO.setDiscrepencyCode(uldDiscrepancyMicroVO.getDiscrepencyCode());
		uldDiscrepancyVO.setRemarks(uldDiscrepancyMicroVO.getRemarks());
		uldDiscrepancyVO.setReportingStation(uldDiscrepancyMicroVO.getReportingStation());
		uldDiscrepancyVO.setUldNumber(uldDiscrepancyMicroVO.getUldNumber());
		uldDiscrepancyVO.setUldStatus(uldDiscrepancyMicroVO.getUldStatus());
		uldDiscrepancyVO.setFacilityType(uldDiscrepancyMicroVO.getFacilityType());
		uldDiscrepancyVO.setLocation(uldDiscrepancyMicroVO.getLocation());
		uldDiscrepancyVO.setAgentCode(uldDiscrepancyMicroVO.getAgentCode());
		uldDiscrepancyVO.setLastUpdatedUser(uldDiscrepancyMicroVO.getLastUpdatedUser());
		LocalDate localDate = new LocalDate(uldDiscrepancyMicroVO.getAirportCode(),Location.ARP,false);
		uldDiscrepancyVO.setDiscrepencyDate(localDate);
		log.log(Log.FINE, "<---uldDiscrepancyVO---b4 despatch--->>",
				uldDiscrepancyMicroVO);
		String message= despatchRequest("updateULDStockStatusForHHT", uldDiscrepancyVO);
		log.log(Log.FINE, "<--returning-message---->>", message);
		return message;
	}
	
	
	/**
	 * 
	 * @param companyCode
	 * @param carrierCode
	 * @return
	 * @throws RemoteException
	 * @author a-2547
	 */
	public String findCRNForULDTransaction(String companyCode,String carrierCode)throws RemoteException {
		log.log(Log.FINE, "in endpoint findCRNForULDTransaction-=-=->",
				companyCode, carrierCode);
		String crnNumber = despatchRequest("findCRNForULDTransaction", companyCode,carrierCode);
		log.log(Log.FINE, "crnNumber -=-=->", crnNumber);
		return crnNumber;
	}
	
	
	
		
	
}
