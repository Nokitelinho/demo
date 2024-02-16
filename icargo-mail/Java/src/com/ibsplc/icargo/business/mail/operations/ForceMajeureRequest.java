/**
 * 
 */
package com.ibsplc.icargo.business.mail.operations;

import java.util.Calendar;
import java.util.Collection;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.ibsplc.icargo.business.mail.operations.vo.ForceMajeureRequestFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.ForceMajeureRequestVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5219
 *
 */

@Table(name = "MALFORMJRREQMST")
@Entity
@Staleable
public class ForceMajeureRequest {
	
	private static final String MODULE_NAME = "mail.operations";
	
	private static final String CLASS_NAME = "ForceMajeureRequest";
	
	private static final Log log = LogFactory.getLogger("MAIL FORCE_MAJEURE_REQUEST");
	
	private ForceMajeureRequestPK forceMajeureRequestPK;
	
	private long mailSeqNumber;
	
	private String mailID;
	
	private String airportCode;
	
	private String carrierCode;
	
	private String flightNumber;
	
	private Calendar flightDate;
	
	private Calendar fromDate;
	
	private Calendar toDate;
	
	private String source;
	
	private double weight;
	
	private String originAirport;
	
	private String destinationAirport;
	
	private String consignmentDocNumber;
	
	private String status;
	
	private String requestRemarks;
	
	private String approvalRemarks;
	
	private String filterParameters;
	
	private String lastUpdatedUser;
	
	private Calendar lastUpdatedTime;
	
	private Calendar requestDate;
	
	
	/**
	 * 
	 */
	public ForceMajeureRequest(){
		
	}
	
	/**
	 * 
	 * @param requestVO
	 * @throws SystemException
	 */
	public ForceMajeureRequest(ForceMajeureRequestVO requestVO)
			throws SystemException {
		log.entering(CLASS_NAME, "ForceMajeureRequest");
		try {
			populatePK(requestVO);
			populateAttributes(requestVO);
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getErrorCode());
		}
		log.exiting(CLASS_NAME, "ForceMajeureRequest");
	}
		
	/**
	 * 
	 * @param requestVO
	 */
	private void populatePK(ForceMajeureRequestVO requestVO)
			throws SystemException {
		log.entering(CLASS_NAME, "populatePK");
		ForceMajeureRequestPK pk = new ForceMajeureRequestPK();
		pk.setCompanyCode(requestVO.getCompanyCode());
		pk.setForceMajuereID(requestVO.getForceMajuereID());
		log.exiting(CLASS_NAME, "populatePK");
	}
	
	
	/**
	 * 
	 * @param requestVO
	 */
	private void populateAttributes(ForceMajeureRequestVO requestVO){
		log.entering(CLASS_NAME, "populateAttributes");
		setAirportCode(requestVO.getAirportCode());
		setApprovalRemarks(requestVO.getApprovalRemarks());
		setCarrierCode(requestVO.getCarrierCode());
		setConsignmentDocNumber(requestVO.getConsignmentDocNumber());
		setDestinationAirport(requestVO.getDestinationAirport());
		setFilterParameters(requestVO.getFilterParameters());
		setFlightDate(requestVO.getFlightDate());
		setFlightNumber(requestVO.getFlightNumber());
		setFromDate(requestVO.getFromDate());
		setToDate(requestVO.getToDate());
		setLastUpdatedTime(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		setLastUpdatedUser(requestVO.getLastUpdatedUser());
		setMailID(requestVO.getMailID());
		setMailSeqNumber(requestVO.getMailSeqNumber());
		setOriginAirport(requestVO.getOriginAirport());
		setRequestRemarks(requestVO.getRequestRemarks());
		setSource(requestVO.getSource());
		setStatus(requestVO.getStatus());
		setWeight(requestVO.getWeight().getSystemValue());
		log.exiting(CLASS_NAME, "populateAttributes");
	}

	/**
	 * @return the forceMajeureRequestPK
	 */
	
	@EmbeddedId
	@AttributeOverrides( {
		@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
		@AttributeOverride(name = "forceMajuereID", column = @Column(name = "FORMJRIDR")),
		@AttributeOverride(name = "sequenceNumber", column = @Column(name = "SEQNUM"))})
	
	public ForceMajeureRequestPK getForceMajeureRequestPK() {
		return forceMajeureRequestPK;
	}

	/**
	 * @param forceMajeureRequestPK the forceMajeureRequestPK to set
	 */
	public void setForceMajeureRequestPK(ForceMajeureRequestPK forceMajeureRequestPK) {
		this.forceMajeureRequestPK = forceMajeureRequestPK;
	}

	/**
	 * @return the mailSeqNumber
	 */
	@Column(name="MALSEQNUM")
	public long getMailSeqNumber() {
		return mailSeqNumber;
	}

	/**
	 * @param mailSeqNumber the mailSeqNumber to set
	 */
	public void setMailSeqNumber(long mailSeqNumber) {
		this.mailSeqNumber = mailSeqNumber;
	}

	/**
	 * @return the mailID
	 */
	@Column(name="MALIDR")
	public String getMailID() {
		return mailID;
	}

	/**
	 * @param mailID the mailID to set
	 */
	public void setMailID(String mailID) {
		this.mailID = mailID;
	}

	/**
	 * @return the airportCode
	 */
	@Column(name="ARPCOD")
	public String getAirportCode() {
		return airportCode;
	}

	/**
	 * @param airportCode the airportCode to set
	 */
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	/**
	 * @return the carrierCode
	 */
	@Column(name="FLTCARCOD")
	public String getCarrierCode() {
		return carrierCode;
	}

	/**
	 * @param carrierCode the carrierCode to set
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	/**
	 * @return the flightNumber
	 */
	@Column(name="FLTNUM")
	public String getFlightNumber() {
		return flightNumber;
	}

	/**
	 * @param flightNumber the flightNumber to set
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	/**
	 * @return the flightDate
	 */
	@Column(name="FLTDAT")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getFlightDate() {
		return flightDate;
	}

	/**
	 * @param flightDate the flightDate to set
	 */
	public void setFlightDate(Calendar flightDate) {
		this.flightDate = flightDate;
	}

	/**
	 * @return the fromDate
	 */
	@Column(name="FRMDAT")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(Calendar fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the toDate
	 */
	@Column(name="TOODAT")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getToDate() {
		return toDate;
	}

	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(Calendar toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return the source
	 */
	@Column(name="OPRSRC")
	public String getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @return the weight
	 */
	@Column(name="WGT")
	public double getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * @return the originAirport
	 */
	@Column(name="ORGARPCOD")
	public String getOriginAirport() {
		return originAirport;
	}

	/**
	 * @param originAirport the originAirport to set
	 */
	public void setOriginAirport(String originAirport) {
		this.originAirport = originAirport;
	}

	/**
	 * @return the destinationAirport
	 */
	@Column(name="DSTARPCOD")
	public String getDestinationAirport() {
		return destinationAirport;
	}

	/**
	 * @param destinationAirport the destinationAirport to set
	 */
	public void setDestinationAirport(String destinationAirport) {
		this.destinationAirport = destinationAirport;
	}

	/**
	 * @return the consignmentDocNumber
	 */
	@Column(name="CSGDOCNUM")
	public String getConsignmentDocNumber() {
		return consignmentDocNumber;
	}

	/**
	 * @param consignmentDocNumber the consignmentDocNumber to set
	 */
	public void setConsignmentDocNumber(String consignmentDocNumber) {
		this.consignmentDocNumber = consignmentDocNumber;
	}

	/**
	 * @return the status
	 */
	@Column(name="FORMJRSTA")
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the requestRemarks
	 */
	@Column(name="REQRMK")
	public String getRequestRemarks() {
		return requestRemarks;
	}

	/**
	 * @param requestRemarks the requestRemarks to set
	 */
	public void setRequestRemarks(String requestRemarks) {
		this.requestRemarks = requestRemarks;
	}

	/**
	 * @return the approvalRemarks
	 */
	@Column(name="APRRMK")
	public String getApprovalRemarks() {
		return approvalRemarks;
	}

	/**
	 * @param approvalRemarks the approvalRemarks to set
	 */
	public void setApprovalRemarks(String approvalRemarks) {
		this.approvalRemarks = approvalRemarks;
	}

	/**
	 * @return the filterParameters
	 */
	@Column(name="FILPAR")
	public String getFilterParameters() {
		return filterParameters;
	}

	/**
	 * @param filterParameters the filterParameters to set
	 */
	public void setFilterParameters(String filterParameters) {
		this.filterParameters = filterParameters;
	}

	/**
	 * @return the lastUpdatedUser
	 */
	@Column(name="LSTUPDUSR")
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}

	/**
	 * @param lastUpdatedUser the lastUpdatedUser to set
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}

	/**
	 * @return the lastUpdatedTime
	 */
	@Column(name="LSTUPDTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	/**
	 * @param lastUpdatedTime the lastUpdatedTime to set
	 */
	public void setLastUpdatedTime(Calendar lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}
	
	/**
	 * @return the requestDate
	 */
	@Column(name="REQDAT")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getRequestDate() {
		return requestDate;
	}

	/**
	 * @param requestDate the requestDate to set
	 */
	public void setRequestDate(Calendar requestDate) {
		this.requestDate = requestDate;
	}
	

	/**
	 * 
	 * @return
	 * @throws SystemException
	 */
	private static MailTrackingDefaultsDAO constructDAO()
			throws SystemException {
		log.entering(CLASS_NAME, "constructDAO");
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MailTrackingDefaultsDAO.class.cast(em
					.getQueryDAO(MODULE_NAME));
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	
	
	/**
	 * @param forceMajeureRequestVO
	 * @throws SystemException
	 */
	public void update(ForceMajeureRequestVO requestVO)
			throws SystemException {
		log.entering(CLASS_NAME, "update");
		populateAttributes(requestVO);
		log.exiting(CLASS_NAME, "update");
	}
	
	
	/**
	 * 
	 * @param companyCode
	 * @param forceMajuereID
	 * @param sequenceNumber
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static ForceMajeureRequest find (String companyCode, String forceMajuereID, long sequenceNumber)
			throws SystemException, FinderException {
		log.entering(CLASS_NAME, "find");
		ForceMajeureRequestPK pk = new ForceMajeureRequestPK();
		pk.setCompanyCode(companyCode);
		pk.setForceMajuereID(forceMajuereID);
		pk.setSequenceNumber(sequenceNumber);
		EntityManager em = PersistenceController.getEntityManager();
		return em.find(ForceMajeureRequest.class, pk);
	}
	
	/**
	 * 
	 * @throws RemoveException
	 * @throws SystemException
	 */
	public void remove() throws RemoveException, SystemException {
		log.entering(CLASS_NAME, "remove");
		PersistenceController.getEntityManager().remove(this);
		log.exiting(CLASS_NAME, "remove");
	}
	
	
	/**
	 * 
	 * @param filterVO
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 */
	public static Page<ForceMajeureRequestVO> listForceMajeureApplicableMails
	(ForceMajeureRequestFilterVO filterVO, int pageNumber)
	throws SystemException{
		log.entering(CLASS_NAME, "listForceMajeureApplicableMails");
		try{
			return constructDAO().listForceMajeureApplicableMails(filterVO,pageNumber);
		}
		catch(PersistenceException persistenceException){
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	
	
	/**
	 * 
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 */
	public static String saveForceMajeureRequest(ForceMajeureRequestFilterVO filterVO)
			 throws  SystemException {
		log.entering(CLASS_NAME, "saveForceMajeureRequest");
		String outParameter = "";
    	try{
    		outParameter = constructDAO().saveForceMajeureRequest(filterVO);			
		}
		catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
    	log.exiting(CLASS_NAME, "saveForceMajeureRequest");
    	return outParameter;
	}
	
	
	/**
	 * 
	 * @param filterVO
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 */
	public static Page<ForceMajeureRequestVO> listForceMajeureDetails
	(ForceMajeureRequestFilterVO filterVO, int pageNumber)
	throws SystemException{
		log.entering(CLASS_NAME, "listForceMajeureDetails");
		try{
			return constructDAO().listForceMajeureDetails(filterVO,pageNumber);
		}
		catch(PersistenceException persistenceException){
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	
	
	/**
	 * 
	 * @param filterVO
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 */
	public Page<ForceMajeureRequestVO> listForceMajeureRequestIds
	(ForceMajeureRequestFilterVO filterVO, int pageNumber)
	throws SystemException{
		log.entering(CLASS_NAME, "listForceMajeureRequestIds");
		try{
			return constructDAO().listForceMajeureRequestIds(filterVO,pageNumber);
		}
		catch(PersistenceException persistenceException){
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	
	
	/**
	 * 
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 */
	public static String updateForceMajeureRequest(ForceMajeureRequestFilterVO filterVO)
		throws SystemException {
		log.entering(CLASS_NAME, "updateForceMajeureRequest");
		try{
			return constructDAO().updateForceMajeureRequest(filterVO);
		}catch(PersistenceException persistenceException){
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	
	/**
	 * 
	 * @param filterVO
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 */
	public static Collection<ForceMajeureRequestVO> findApprovedForceMajeureDetails
	(String companyCode, String mailBagId, long mailSequenceNumber)
	throws SystemException{
		log.entering(CLASS_NAME, "listForceMajeureDetails");
		try{
			return constructDAO().findApprovedForceMajeureDetails(companyCode,mailBagId,mailSequenceNumber);
		}
		catch(PersistenceException persistenceException){
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
}
