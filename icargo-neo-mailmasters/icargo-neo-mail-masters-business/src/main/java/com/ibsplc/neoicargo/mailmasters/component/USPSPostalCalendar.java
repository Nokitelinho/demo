package com.ibsplc.neoicargo.mailmasters.component;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Collection;
import javax.persistence.*;

import com.ibsplc.neoicargo.framework.tenant.audit.Audit;
import com.ibsplc.neoicargo.framework.tenant.audit.Auditable;
import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import java.time.LocalDateTime;

import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mailmasters.vo.USPSPostalCalendarFilterVO;
import com.ibsplc.neoicargo.mailmasters.vo.USPSPostalCalendarVO;
import com.ibsplc.neoicargo.mailmasters.dao.MailMastersDAO;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** 
 * Java file	: 	com.ibsplc.icargo.business.mail.operations.USPSPostalCalendar.java Version		:	Name	:	Date			:	Updation --------------------------------------------------- 0.1		:	A-8164	:	04-Jul-2018	:	Draft
 */
@Setter
@Getter
@Entity
@IdClass(USPSPostalCalendarPK.class)
@SequenceGenerator(name = "MALCALMSTSEQ", initialValue = 1, allocationSize = 1, sequenceName = "MALCALMST_SEQ")
@Table(name = "MALCALMST")
@NoArgsConstructor
@Auditable(eventName="postalCalendarUpdate",isParent = true)
public class USPSPostalCalendar extends BaseEntity implements Serializable {

	@Id
	@Column(name = "GPACOD")
	private String gpacod;
	@Id
	@Column(name = "CALTYP")
	private String filterCalender;
	@Id
	@Column(name = "SERNUM")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MALCALMSTSEQ")
	private long serialNumber;

	private static final String MODULE_NAME = "mail.masters";
	@Column(name = "CALPRD")
	private String period;
	@Column(name = "PRDFRM")
	private LocalDateTime fromDate;
	@Column(name = "PRDTOO")
	private LocalDateTime toDate;
	@Column(name = "CGRSUBDAT")
	private LocalDateTime cgrSubmission;
	@Column(name = "CGRDELCUTOFFDAT")
	private LocalDateTime cgrDeleteCutOff;
	@Column(name = "CUTOFFWEKFIR")
	private LocalDateTime cutWeek1;
	@Column(name = "CUTOFFWEKSEC")
	private LocalDateTime cutWeek2;
	@Column(name = "CUTOFFWEKTHR")
	private LocalDateTime cutWeek3;
	@Column(name = "CUTOFFWEKFOU")
	private LocalDateTime cutWeek4;
	@Column(name = "CUTOFFWEKFIV")
	private LocalDateTime cutWeek5;
	@Audit(name="paymEffectiveDate",changeGroupId="postal-calendar-master")
	@Column(name = "PAYEFTDAT")
	private LocalDateTime paymEffectiveDate;
	@Audit(name="incCalcDate",changeGroupId="postal-calendar-master")
	@Column(name = "INCCALDAT")
	private LocalDateTime incCalcDate;
	@Audit(name="incEffectiveDate",changeGroupId="postal-calendar-master")
	@Column(name = "INCEFTDAT")
	private LocalDateTime incEffectiveDate;
	@Audit(name="incRecvDate",changeGroupId="postal-calendar-master")
	@Column(name = "INCRCVDAT")
	private LocalDateTime incRecvDate;
	@Column(name = "TAGIDX")
	private int tagIdx;
	@Audit(name="clmgendat",changeGroupId="postal-calendar-master")
	@Column(name = "CLMGENDAT")
	private LocalDateTime clmGenDate;

	public USPSPostalCalendar(USPSPostalCalendarVO uSPSPostalCalendarVO) {
		populatePK(uSPSPostalCalendarVO);
		populateAtrributes(uSPSPostalCalendarVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException ex) {
			throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
		}
	}

	public static Collection<USPSPostalCalendarVO> listPostalCalendarDetails(
			USPSPostalCalendarFilterVO uSPSPostalCalendarFilterVO) {
		try {
			return constructDAO().listPostalCalendarDetails(uSPSPostalCalendarFilterVO);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	public static MailMastersDAO constructDAO() {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MailMastersDAO.class.cast(em.getQueryDAO(MODULE_NAME));
		} catch (PersistenceException ex) {
			ex.getErrorCode();
			throw new SystemException(ex.getMessage());
		}
	}

	public void remove() {
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			throw new SystemException(removeException.getErrorCode(), removeException.getMessage(), removeException);
		}
	}

	public static USPSPostalCalendar find(USPSPostalCalendarPK uSPSPostalCalendarPK) throws FinderException {
		EntityManager em = PersistenceController.getEntityManager();
		return em.find(USPSPostalCalendar.class, uSPSPostalCalendarPK);
	}

	public void update(USPSPostalCalendarVO uSPSPostalCalendarVO) {
		populateAtrributes(uSPSPostalCalendarVO);
		this.setLastUpdatedTime(Timestamp.valueOf(uSPSPostalCalendarVO.getLstUpdTime().toLocalDateTime()));
	}

	/** 
	* Method		:	USPSPostalCalendar.populateAtrributes Added by 	:	A-8164 on 04-Jul-2018 Used for 	:	ICRD-236925 Parameters	:	@param uSPSPostalCalendarVO Parameters	:	@throws SystemException Return type	: 	void
	*/
	private void populateAtrributes(USPSPostalCalendarVO uSPSPostalCalendarVO) {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		this.setLastUpdatedTime(Timestamp.valueOf(uSPSPostalCalendarVO.getLstUpdTime().toLocalDateTime()));
		this.setLastUpdatedUser(uSPSPostalCalendarVO.getLstUpdUsr());
		this.setPeriod(uSPSPostalCalendarVO.getPeriods());
		//TODO: Neo code to be corrected
		ZonedDateTime fromDateToSave = localDateUtil.getLocalDate(null, true);
		fromDateToSave = LocalDate.withDate(fromDateToSave, uSPSPostalCalendarVO.getFromDates());
		this.setFromDate(fromDateToSave.toLocalDateTime());
		ZonedDateTime toDateToSave = localDateUtil.getLocalDate(null, true);
		toDateToSave = LocalDate.withDate(toDateToSave, uSPSPostalCalendarVO.getToDates());
		this.setToDate(toDateToSave.toLocalDateTime());
		ZonedDateTime cgrSubmissionDate = localDateUtil.getLocalDate(null, true);
		if (((null != uSPSPostalCalendarVO.getCgrSubmissions()))
				&& (0 < uSPSPostalCalendarVO.getCgrSubmissions().length())) {
			cgrSubmissionDate = LocalDate.withDate(cgrSubmissionDate, uSPSPostalCalendarVO.getCgrSubmissions());
			this.setCgrSubmission(cgrSubmissionDate.toLocalDateTime());
		}
		ZonedDateTime cgrDeleteCutOff = localDateUtil.getLocalDate(null, true);
		if (((null != uSPSPostalCalendarVO.getCgrDeleteCutOffs()))
				&& (0 < uSPSPostalCalendarVO.getCgrDeleteCutOffs().length())) {
			cgrDeleteCutOff = LocalDate.withDate(cgrDeleteCutOff, uSPSPostalCalendarVO.getCgrDeleteCutOffs());
			this.setCgrDeleteCutOff(cgrDeleteCutOff.toLocalDateTime());
		}
		ZonedDateTime cutWeek1 = localDateUtil.getLocalDate(null, true);
		if (((null != uSPSPostalCalendarVO.getCutWeek1s())) && (0 < uSPSPostalCalendarVO.getCutWeek1s().length())) {
			cutWeek1 = LocalDate.withDate(cutWeek1, uSPSPostalCalendarVO.getCutWeek1s());
			this.setCutWeek1(cutWeek1.toLocalDateTime());
		}
		ZonedDateTime cutWeek2 = localDateUtil.getLocalDate(null, true);
		if (((null != uSPSPostalCalendarVO.getCutWeek2s())) && (0 < uSPSPostalCalendarVO.getCutWeek2s().length())) {
			cutWeek2 = LocalDate.withDate(cutWeek2, uSPSPostalCalendarVO.getCutWeek2s());
			this.setCutWeek2(cutWeek2.toLocalDateTime());
		}
		ZonedDateTime cutWeek3 = localDateUtil.getLocalDate(null, true);
		if (((null != uSPSPostalCalendarVO.getCutWeek3s())) && (0 < uSPSPostalCalendarVO.getCutWeek3s().length())) {
			cutWeek3 = LocalDate.withDate(cutWeek3, uSPSPostalCalendarVO.getCutWeek3s());
			this.setCutWeek3(cutWeek3.toLocalDateTime());
		}
		ZonedDateTime cutWeek4 = localDateUtil.getLocalDate(null, true);
		if (((null != uSPSPostalCalendarVO.getCutWeek4s())) && (0 < uSPSPostalCalendarVO.getCutWeek4s().length())) {
			cutWeek4 = LocalDate.withDate(cutWeek4, uSPSPostalCalendarVO.getCutWeek4s());
			this.setCutWeek4(cutWeek4.toLocalDateTime());
		}
		ZonedDateTime cutWeek5 = localDateUtil.getLocalDate(null, true);
		if (((null != uSPSPostalCalendarVO.getCutWeek5s())) && (0 < uSPSPostalCalendarVO.getCutWeek5s().length())) {
			cutWeek5 = LocalDate.withDate(cutWeek5, uSPSPostalCalendarVO.getCutWeek5s());
			this.setCutWeek5(cutWeek5.toLocalDateTime());
		}
		ZonedDateTime paymEffectiveDate = localDateUtil.getLocalDate(null, true);
		if (((null != uSPSPostalCalendarVO.getPaymEffectiveDates()))
				&& (0 < uSPSPostalCalendarVO.getPaymEffectiveDates().length())) {
			paymEffectiveDate = LocalDate.withDate(paymEffectiveDate, uSPSPostalCalendarVO.getPaymEffectiveDates());
			this.setPaymEffectiveDate(paymEffectiveDate.toLocalDateTime());
		}
		ZonedDateTime incCalcDate = localDateUtil.getLocalDate(null, true);
		if (((null != uSPSPostalCalendarVO.getIncCalcDates())) && (0 < uSPSPostalCalendarVO.getIncCalcDates().length())){
			incCalcDate = LocalDate.withDate(incCalcDate, uSPSPostalCalendarVO.getIncCalcDates());
		this.setIncCalcDate(incCalcDate.toLocalDateTime());
		}
		ZonedDateTime incEffectiveDate = localDateUtil.getLocalDate(null, true);
		if (((null != uSPSPostalCalendarVO.getIncEffectiveDates()))
				&& (0 < uSPSPostalCalendarVO.getIncEffectiveDates().length())) {
			incEffectiveDate = LocalDate.withDate(incEffectiveDate, uSPSPostalCalendarVO.getIncEffectiveDates());
			this.setIncEffectiveDate(incEffectiveDate.toLocalDateTime());
		}
		ZonedDateTime incRecvDate = localDateUtil.getLocalDate(null, true);
		if (((null != uSPSPostalCalendarVO.getIncRecvDates())) && (0 < uSPSPostalCalendarVO.getIncRecvDates().length())) {
			incRecvDate = LocalDate.withDate(incRecvDate, uSPSPostalCalendarVO.getIncRecvDates());
			this.setIncRecvDate(incRecvDate.toLocalDateTime());
		}
		ZonedDateTime clmGenDate = localDateUtil.getLocalDate(null, true);
		if (((null != uSPSPostalCalendarVO.getClmGenDate())) && (0 < uSPSPostalCalendarVO.getClmGenDate().length())) {
			clmGenDate = LocalDate.withDate(clmGenDate, uSPSPostalCalendarVO.getClmGenDate());
			this.setClmGenDate(clmGenDate.toLocalDateTime());
		}
	}

	/** 
	* Method		:	USPSPostalCalendar.populatePK Added by 	:	A-8164 on 04-Jul-2018 Used for 	:	ICRD-236925 Parameters	:	@param uSPSPostalCalendarVO Return type	: 	void
	*/
	private void populatePK(USPSPostalCalendarVO uSPSPostalCalendarVO) {
		this.setCompanyCode(uSPSPostalCalendarVO.getCompanyCode());
		this.setGpacod(uSPSPostalCalendarVO.getGpacod());
		this.setFilterCalender(uSPSPostalCalendarVO.getFilterCalender());
	}

	/** 
	* Method		:	USPSPostalCalendar.validateFrmToDateRange Added by 	:	A-8527 on 14-March-2019 Used for 	:	ICRD-262471 Parameters	:	@param uSPSPostalCalendarVO Return type	: 	void
	*/
	public static Collection<USPSPostalCalendarVO> validateFrmToDateRange(
			USPSPostalCalendarFilterVO uSPSPostalCalendarFilterVO) {
		try {
			return constructDAO().validateFrmToDateRange(uSPSPostalCalendarFilterVO);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/** 
	* @author A-7371
	* @param uspsPostalCalendarFilterVO
	* @return
	* @throws SystemException
	*/
	public static USPSPostalCalendarVO findInvoicPeriodDetails(USPSPostalCalendarFilterVO uspsPostalCalendarFilterVO) {
		try {
			return constructDAO().findInvoicPeriodDetails(uspsPostalCalendarFilterVO);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	@Override
	public BaseEntity getParentEntity() {
		return this;
	}
	@Override
	public String getBusinessIdAsString(){
		return this.gpacod+","+this.period;
	}

}
