package com.ibsplc.neoicargo.mail.component;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mail.vo.MailActivityDetailVO;
import com.ibsplc.neoicargo.mail.dao.MailOperationsDAO;
import lombok.extern.slf4j.Slf4j;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

/** 
 * @author a-3109
 */
@Setter
@Getter
@Slf4j
@Entity
@Table(name = "MALACTDTL")
public class MailActivityDetail {
	private static final String PRODUCT_NAME = "mail.operations";
	private static final String CLASS_NAME = "MailActivityDetail";
	/** 
	* airlineIdentifier
	*/
	@Column(name = "ARLIDR")
	private int airlineIdentifier;
	/** 
	* airlineCode
	*/
	@Column(name = "ARLCOD")
	private String airlineCode;
	/** 
	* gpaCode
	*/
	@Column(name = "GPACOD")
	private String gpaCode;
	/** 
	* slaIdentifier
	*/
	@Column(name = "SLAIDR")
	private String slaIdentifier;
	/** 
	* flightCarrierIdentifier
	*/
	@Column(name = "FLTCARIDR")
	private int flightCarrierIdentifier;
	/** 
	* flightCarrierCode
	*/
	@Column(name = "FLTCARCOD")
	private String flightCarrierCode;
	/** 
	* flightNumber
	*/
	@Column(name = "FLTNUM")
	private String flightNumber;
	/** 
	* mailCategoryCode
	*/
	@Column(name = "MALCTGCOD")
	private String mailCategoryCode;
	/** 
	* plannedTime
	*/
	@Column(name = "PLNTIM")
	//@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime plannedTime;
	/** 
	* actualTime
	*/
	@Column(name = "ACTTIM")
	//@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime actualTime;
	/** 
	* slaStatus
	*/
	@Column(name = "SLASTA")
	private String slaStatus;
	/** 
	* alertStatus
	*/
	@Column(name = "ALRSTA")
	private String alertStatus;
	/** 
	* numberOfChasers
	*/
	@Column(name = "CHRCNT")
	private int numberOfChasers;
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "mailBagNumber", column = @Column(name = "MALBAGNUM")),
			@AttributeOverride(name = "slaActivity", column = @Column(name = "SLAACT")) })
	private MailActivityDetailPK mailActivityDetailPk;

	/** 
	* @return Returns the actualTime.
	*/
	public LocalDateTime getActualTime() {
		return actualTime;
	}

	/** 
	* @param actualTime The actualTime to set.
	*/
	public void setActualTime(LocalDateTime actualTime) {
		this.actualTime = actualTime;
	}

	/** 
	* @return Returns the airlineCode.
	*/
	public String getAirlineCode() {
		return airlineCode;
	}

	/** 
	* @param airlineCode The airlineCode to set.
	*/
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}

	/** 
	* @return Returns the airlineIdentifier.
	*/
	public int getAirlineIdentifier() {
		return airlineIdentifier;
	}

	/** 
	* @param airlineIdentifier The airlineIdentifier to set.
	*/
	public void setAirlineIdentifier(int airlineIdentifier) {
		this.airlineIdentifier = airlineIdentifier;
	}

	/** 
	* @return Returns the flightCarrierCode.
	*/
	public String getFlightCarrierCode() {
		return flightCarrierCode;
	}

	/** 
	* @param flightCarrierCode The flightCarrierCode to set.
	*/
	public void setFlightCarrierCode(String flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
	}

	/** 
	* @return Returns the flightCarrierIdentifier.
	*/
	public int getFlightCarrierIdentifier() {
		return flightCarrierIdentifier;
	}

	/** 
	* @param flightCarrierIdentifier The flightCarrierIdentifier to set.
	*/
	public void setFlightCarrierIdentifier(int flightCarrierIdentifier) {
		this.flightCarrierIdentifier = flightCarrierIdentifier;
	}

	/** 
	* @return Returns the flightNumber.
	*/
	public String getFlightNumber() {
		return flightNumber;
	}

	/** 
	* @param flightNumber The flightNumber to set.
	*/
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	/** 
	* @return Returns the gpaCode.
	*/
	public String getGpaCode() {
		return gpaCode;
	}

	/** 
	* @param gpaCode The gpaCode to set.
	*/
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}

	/** 
	* @return Returns the mailCategoryCode.
	*/
	public String getMailCategoryCode() {
		return mailCategoryCode;
	}

	/** 
	* @param mailCategoryCode The mailCategoryCode to set.
	*/
	public void setMailCategoryCode(String mailCategoryCode) {
		this.mailCategoryCode = mailCategoryCode;
	}

	/** 
	* @return Returns the plannedTime.
	*/
	public LocalDateTime getPlannedTime() {
		return plannedTime;
	}

	/** 
	* @param plannedTime The plannedTime to set.
	*/
	public void setPlannedTime(LocalDateTime plannedTime) {
		this.plannedTime = plannedTime;
	}

	/** 
	* @return Returns the slaIdentifier.
	*/
	public String getSlaIdentifier() {
		return slaIdentifier;
	}

	/** 
	* @param slaIdentifier The slaIdentifier to set.
	*/
	public void setSlaIdentifier(String slaIdentifier) {
		this.slaIdentifier = slaIdentifier;
	}

	/** 
	* @return Returns the slaStatus.
	*/
	public String getSlaStatus() {
		return slaStatus;
	}

	/** 
	* @param slaStatus The slaStatus to set.
	*/
	public void setSlaStatus(String slaStatus) {
		this.slaStatus = slaStatus;
	}

	/** 
	* @return Returns the alertStatus.
	*/
	public String getAlertStatus() {
		return alertStatus;
	}

	/** 
	* @param alertStatus The alertStatus to set.
	*/
	public void setAlertStatus(String alertStatus) {
		this.alertStatus = alertStatus;
	}

	/** 
	* @return Returns the numberOfChasers.
	*/
	public int getNumberOfChasers() {
		return numberOfChasers;
	}

	/** 
	* @param numberOfChasers The numberOfChasers to set.
	*/
	public void setNumberOfChasers(int numberOfChasers) {
		this.numberOfChasers = numberOfChasers;
	}

	/** 
	* @return Returns the mailActivityDetailPk.
	*/
	public MailActivityDetailPK getMailActivityDetailPk() {
		return mailActivityDetailPk;
	}

	/** 
	* @param mailActivityDetailPk The mailActivityDetailPk to set.
	*/
	public void setMailActivityDetailPk(MailActivityDetailPK mailActivityDetailPk) {
		this.mailActivityDetailPk = mailActivityDetailPk;
	}

	/** 
	* Default constructor
	*/
	public MailActivityDetail() {
	}

	/** 
	* @param mailActivityDetailVo
	* @throws SystemException
	*/
	public MailActivityDetail(MailActivityDetailVO mailActivityDetailVo) {
		log.debug(CLASS_NAME + " : " + CLASS_NAME + " Entering");
		MailActivityDetailPK mailActivityDetailPkToPersist = new MailActivityDetailPK(
				mailActivityDetailVo.getCompanyCode(), mailActivityDetailVo.getMailBagNumber(),
				mailActivityDetailVo.getServiceLevelActivity());
		setMailActivityDetailPk(mailActivityDetailPkToPersist);
		populateAttributes(mailActivityDetailVo);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getErrorCode());
		}
		log.debug(CLASS_NAME + " : " + CLASS_NAME + " Exiting");
	}

	/** 
	* @param mailActivityDetailVo
	* @throws SystemException
	*/
	private void populateAttributes(MailActivityDetailVO mailActivityDetailVo) {
		log.debug(CLASS_NAME + " : " + "populateAttributes" + " Entering");
		setActualTime(mailActivityDetailVo.getActualTime());
		setAirlineCode(mailActivityDetailVo.getAirlineCode());
		setAirlineIdentifier(mailActivityDetailVo.getAirlineIdentifier());
		setFlightCarrierCode(mailActivityDetailVo.getFlightCarrierCode());
		setFlightCarrierIdentifier(mailActivityDetailVo.getFlightCarrierId());
		setFlightNumber(mailActivityDetailVo.getFlightNumber());
		setGpaCode(mailActivityDetailVo.getGpaCode());
		setMailCategoryCode(mailActivityDetailVo.getMailCategory());
		setPlannedTime(mailActivityDetailVo.getPlannedTime());
		setSlaIdentifier(mailActivityDetailVo.getSlaIdentifier());
		setSlaStatus(mailActivityDetailVo.getSlaStatus());
		setAlertStatus(mailActivityDetailVo.getAlertStatus());
		if (mailActivityDetailVo.getNumberOfChasers() != 0) {
			setNumberOfChasers(mailActivityDetailVo.getNumberOfChasers());
		}
		log.debug(CLASS_NAME + " : " + "populateAttributes" + " Exiting");
	}

	/** 
	* @throws SystemException
	*/
	public void remove() {
		log.debug(CLASS_NAME + " : " + "remove" + " Entering");
		try {
			PersistenceController.getEntityManager().remove(this);
			log.debug(CLASS_NAME + " : " + "remove" + " Exiting");
		} catch (RemoveException removeException) {
			throw new SystemException(removeException.getErrorCode());
		}
	}

	/** 
	* @param companyCode
	* @param mailBagNumber
	* @param slaActivity
	* @return
	* @throws SystemException
	* @throws FinderException
	*/
	public static MailActivityDetail find(String companyCode, String mailBagNumber, String slaActivity)
			throws FinderException {
		log.debug(CLASS_NAME + " : " + "find" + " Entering");
		MailActivityDetailPK mailActivityDetailPkToFind = new MailActivityDetailPK(companyCode, mailBagNumber,
				slaActivity);
		log.debug(CLASS_NAME + " : " + "find" + " Exiting");
		return PersistenceController.getEntityManager().find(MailActivityDetail.class, mailActivityDetailPkToFind);
	}

	/** 
	* @return
	* @throws SystemException
	*/
	private static MailOperationsDAO constructDAO() {
		try {
			return MailOperationsDAO.class
					.cast(PersistenceController.getEntityManager().getQueryDAO(PRODUCT_NAME));
		} catch (PersistenceException persistenceException) {
			throw new SystemException("No DAO found", persistenceException.getMessage(), persistenceException);
		}
	}

	/** 
	* @author a-2518
	* @param companyCode
	* @param officeOfExchange
	* @return
	* @throws SystemException
	*/
	public static String findPostalAuthorityCode(String companyCode, String officeOfExchange) {
		log.debug(CLASS_NAME + " : " + "findPostalAuthorityCode" + " Entering");
		try {
			return constructDAO().findPostalAuthorityCode(companyCode, officeOfExchange);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getMessage(), persistenceException.getMessage(),
					persistenceException);
		}
	}

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
	*/
	public static MailActivityDetailVO findServiceTimeAndSLAId(String companyCode, String gpaCode, String origin,
			String destination, String mailCategory, String activity, ZonedDateTime scanDate) {
		log.debug(CLASS_NAME + " : " + "findServiceTime" + " Entering");
		try {
			return constructDAO().findServiceTimeAndSLAId(companyCode, gpaCode, origin, destination, mailCategory,
					activity, scanDate);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getMessage(), persistenceException.getMessage(),
					persistenceException);
		}
	}

	public void setPlannedTime(ZonedDateTime plannedTime) {
		if (Objects.nonNull(plannedTime)) {
			this.plannedTime = plannedTime.toLocalDateTime();
		}
	}

	public void setActualTime(ZonedDateTime actualTime) {
		if (Objects.nonNull(actualTime)) {
			this.actualTime = actualTime.toLocalDateTime();
		}
	}
}
