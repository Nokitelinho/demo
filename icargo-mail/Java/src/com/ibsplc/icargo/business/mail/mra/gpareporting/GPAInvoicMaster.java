/**
 * 
 */
package com.ibsplc.icargo.business.mail.mra.gpareporting;

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

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.persistence.dao.mail.mra.gpareporting.MRAGPAReportingDAO;
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

@Table(name = "MALMRAGPAINCMST")
@Entity
@Staleable
public class GPAInvoicMaster {
	
	private static final String MODULE_NAME = "mail.mra.gpareporting";
	
	private static final String CLASS_NAME = "GPAInvoicMaster";
	
	private static final Log log = LogFactory.getLogger("MAIL LIST_INVOIC_REQUEST");
	
	private GPAInvoicMasterPK gpaInvoicMasterPK;
	
	private String paCode;
	
	private Calendar invoicDate;
	
	private Calendar fromDate;
	
	private Calendar toDate;
	
	private String invoicstatus;
	
	private double totalAmount;
	
	private String invoicListremarks;
	
	private String lastUpdatedUser;
	
	private Calendar lastUpdatedTime;
	

	/**
	 * 
	 */
	public GPAInvoicMaster(){
		
	}
	
	/**
	 * 
	 * @param requestVO
	 * @throws SystemException
	 */
	public GPAInvoicMaster(InvoicVO requestVO)
			throws SystemException {
		log.entering(CLASS_NAME, "GPAInvoicMaster");
		try {
			populatePK(requestVO);
			populateAttributes(requestVO);
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getErrorCode());
		}
		log.exiting(CLASS_NAME, "GPAInvoicMaster");
	}
	@Column(name="POACOD")
	public String getPaCode() {
		return paCode;
	}

	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}
	@Column(name="INVDAT")
	public Calendar getInvoicDate() {
		return invoicDate;
	}

	public void setInvoicDate(Calendar invoicDate) {
		this.invoicDate = invoicDate;
	}
	@Column(name="INVSTA")
	public String getInvoicstatus() {
		return invoicstatus;
	}

	public void setInvoicstatus(String invoicstatus) {
		this.invoicstatus = invoicstatus;
	}
	@Column(name="TOTINVAMT")
	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	@Column(name="RMK")
	public String getInvoicListremarks() {
		return invoicListremarks;
	}

	public void setInvoicListremarks(String invoicListremarks) {
		this.invoicListremarks = invoicListremarks;
	}

	/**
	 * @return the fromDate
	 */
	@Column(name="RPTPRDFRM")
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
	@Column(name="RPTPRDTOO")
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
	 * 
	 * @param requestVO
	 */
	private void populatePK(InvoicVO requestVO)
			throws SystemException {
		log.entering(CLASS_NAME, "populatePK");
		GPAInvoicMasterPK  pk = new GPAInvoicMasterPK ();
		pk.setCompanyCode(requestVO.getCompanyCode());
		pk.setInvoicRefNum(requestVO.getInvoicRefId());
		log.exiting(CLASS_NAME, "populatePK");
	}
	
	/**
	 * @param InvoicVO
	 * @throws SystemException
	 */
	public void update(InvoicVO requestVO)
			throws SystemException {
		log.entering(CLASS_NAME, "update");
		populateAttributes(requestVO);
		log.exiting(CLASS_NAME, "update");
	}
	
	/**
	 * 
	 * @param requestVO
	 */
	private void populateAttributes(InvoicVO requestVO){
		log.entering(CLASS_NAME, "populateAttributes");
		
		setFromDate(requestVO.getFromDate());
		setToDate(requestVO.getToDate());
		setPaCode(requestVO.getPoaCode());
		setInvoicstatus(requestVO.getInvoicStatus());
		setInvoicDate(requestVO.getInvoiceDate());
		setTotalAmount(requestVO.getTotalamount());
		setInvoicListremarks(requestVO.getRemarks());
		setLastUpdatedTime(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		setLastUpdatedUser(requestVO.getLastupdatedUser());
		log.exiting(CLASS_NAME, "populateAttributes");
	}

	/**
	 * @return the gpaInvoicMasterPK
	 */
	
	@EmbeddedId
	@AttributeOverrides( {
		@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
		@AttributeOverride(name = "invoicRefNum", column = @Column(name = "INVREFNUM")),
		@AttributeOverride(name = "sequenceNumber", column = @Column(name = "SERNUM"))})
	
	public GPAInvoicMasterPK getGpaInvoicMasterPK() {
		return gpaInvoicMasterPK;
	}

	/**
	 * @param gpaInvoicMasterPK the gpaInvoicMasterPK to set
	 */
	public void setGpaInvoicMasterPK(GPAInvoicMasterPK gpaInvoicMasterPK) {
		this.gpaInvoicMasterPK = gpaInvoicMasterPK;
	}	
	
	  public static GPAInvoicMaster find(GPAInvoicMasterPK gpaInvoicMasterPK)
		      throws FinderException, SystemException {
		      return PersistenceController.getEntityManager().find(GPAInvoicMaster.class, gpaInvoicMasterPK);
		  }
	/**
	 * 
	 * @return
	 * @throws SystemException
	 */
	private static MRAGPAReportingDAO constructDAO()
			throws SystemException {
		log.entering(CLASS_NAME, "constructDAO");
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MRAGPAReportingDAO.class.cast(em
					.getQueryDAO(MODULE_NAME));
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
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
	 * @return
	 * @throws SystemException
	 */
	/*public static String saveListInvoicProcess(InvoicFilterVO filterVO)
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
	*/
	/**
	 * 
	 * @param filterVO
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 */
	public static Page<InvoicVO> listInvoic(InvoicFilterVO filterVO, int pageNumber)
	throws SystemException{
		log.entering(CLASS_NAME, "listForceMajeureRequestIds");
		try{
			return constructDAO().listInvoic(filterVO,pageNumber);
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
	public static void updateInvoicReject( Collection<InvoicVO> invoicVOs)
		throws SystemException {
		log.entering(CLASS_NAME, "updateInvoicReject");
		GPAInvoicMaster gpaInvoicMaster = null;
		if(invoicVOs!=null && !invoicVOs.isEmpty()){
			for(InvoicVO invoicVO:invoicVOs){
				try {
					gpaInvoicMaster = GPAInvoicMaster.find(constructGPAInvoicMasterPK(invoicVO));
					gpaInvoicMaster.setInvoicstatus("RJ");
				} catch (FinderException e) {
					e.getErrorCode();
				}
			}
		}
	}
	
	private static GPAInvoicMasterPK constructGPAInvoicMasterPK(InvoicVO invoicVO) throws SystemException{
		
		GPAInvoicMasterPK gpaInvoicMasterPK = new GPAInvoicMasterPK();
		gpaInvoicMasterPK.setCompanyCode(invoicVO.getCompanyCode());
		gpaInvoicMasterPK.setInvoicRefNum(invoicVO.getInvoicRefId());
		gpaInvoicMasterPK.setSequenceNumber(invoicVO.getSeqNumber());

		return gpaInvoicMasterPK;
	}

}
