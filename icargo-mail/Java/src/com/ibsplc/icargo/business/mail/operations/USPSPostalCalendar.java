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
import javax.persistence.Version;
import com.ibsplc.icargo.business.mail.operations.vo.USPSPostalCalendarFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.USPSPostalCalendarVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.audit.Audit;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
/**
 *
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.USPSPostalCalendar.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	04-Jul-2018	:	Draft
 */
@Entity
@Table(name = "MALCALMST")
public class USPSPostalCalendar {
	private USPSPostalCalendarPK uSPSPostalCalendarPK;
	private static final String MODULE_NAME = "mail.operations";
	private String period;
	private Calendar fromDate;
	private Calendar toDate;
	private Calendar cgrSubmission;
	private Calendar cgrDeleteCutOff;
	private Calendar cutWeek1;
	private Calendar cutWeek2;
	private Calendar cutWeek3;
	private Calendar cutWeek4;
	private Calendar cutWeek5;
	private Calendar paymEffectiveDate;
	private Calendar incCalcDate;
	private Calendar incEffectiveDate;
	private Calendar incRecvDate;
	private String lstUpdUsr;
	private Calendar lstUpdTime;
	private int tagIdx;
	//Added by A-8527 for ICRD-262451 starts
	private Calendar clmGenDate;
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "gpacod", column = @Column(name = "GPACOD")),
			@AttributeOverride(name = "filterCalender", column = @Column(name = "CALTYP")),
			@AttributeOverride(name = "serialNumber", column = @Column(name = "SERNUM")) })
	public USPSPostalCalendarPK getuSPSPostalCalendarPK() {
		return uSPSPostalCalendarPK;
	}
	public void setuSPSPostalCalendarPK(USPSPostalCalendarPK uSPSPostalCalendarPK) {
		this.uSPSPostalCalendarPK = uSPSPostalCalendarPK;
	}
	public USPSPostalCalendar(){
	}
	@Column(name = "CALPRD")
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	@Column(name = "PRDFRM")
	@Temporal(TemporalType.DATE)
	public Calendar getFromDate() {
		return fromDate;
	}
	public void setFromDate(Calendar fromDate) {
		this.fromDate = fromDate;
	}
	@Column(name = "PRDTOO")
	@Temporal(TemporalType.DATE)
	public Calendar getToDate() {
		return toDate;
	}
	public void setToDate(Calendar toDate) {
		this.toDate = toDate;
	}
	@Column(name = "CGRSUBDAT")
	@Temporal(TemporalType.DATE)
	public Calendar getCgrSubmission() {
		return cgrSubmission;
	}
	public void setCgrSubmission(Calendar cgrSubmission) {
		this.cgrSubmission = cgrSubmission;
	}
	@Column(name = "CGRDELCUTOFFDAT")
	@Temporal(TemporalType.DATE)
	public Calendar getCgrDeleteCutOff() {
		return cgrDeleteCutOff;
	}
	public void setCgrDeleteCutOff(Calendar cgrDeleteCutOff) {
		this.cgrDeleteCutOff = cgrDeleteCutOff;
	}
	@Column(name = "CUTOFFWEKFIR")
	@Temporal(TemporalType.DATE)
	public Calendar getCutWeek1() {
		return cutWeek1;
	}
	public void setCutWeek1(Calendar cutWeek1) {
		this.cutWeek1 = cutWeek1;
	}
	@Column(name = "CUTOFFWEKSEC")
	@Temporal(TemporalType.DATE)
	public Calendar getCutWeek2() {
		return cutWeek2;
	}
	public void setCutWeek2(Calendar cutWeek2) {
		this.cutWeek2 = cutWeek2;
	}
	@Column(name = "CUTOFFWEKTHR")
	@Temporal(TemporalType.DATE)
	public Calendar getCutWeek3() {
		return cutWeek3;
	}
	public void setCutWeek3(Calendar cutWeek3) {
		this.cutWeek3 = cutWeek3;
	}
	@Column(name = "CUTOFFWEKFOU")
	@Temporal(TemporalType.DATE)
	public Calendar getCutWeek4() {
		return cutWeek4;
	}
	public void setCutWeek4(Calendar cutWeek4) {
		this.cutWeek4 = cutWeek4;
	}
	@Column(name = "CUTOFFWEKFIV")
	@Temporal(TemporalType.DATE)
	public Calendar getCutWeek5() {
		return cutWeek5;
	}
	public void setCutWeek5(Calendar cutWeek5) {
		this.cutWeek5 = cutWeek5;
	}
	@Column(name = "PAYEFTDAT")
	@Temporal(TemporalType.DATE)
	@Audit(name="Payment and Disincentive EFT")
	public Calendar getPaymEffectiveDate() {
		return paymEffectiveDate;
	}
	public void setPaymEffectiveDate(Calendar paymEffectiveDate) {
		this.paymEffectiveDate = paymEffectiveDate;
	}
	@Column(name = "INCCALDAT")
	@Temporal(TemporalType.DATE)
	@Audit(name="Incentive Calculation")
	public Calendar getIncCalcDate() {
		return incCalcDate;
	}
	public void setIncCalcDate(Calendar incCalcDate) {
		this.incCalcDate = incCalcDate;
	}
	@Column(name = "INCEFTDAT")
	@Temporal(TemporalType.DATE)
	@Audit(name="Incentive EFT")
	public Calendar getIncEffectiveDate() {
		return incEffectiveDate;
	}
	public void setIncEffectiveDate(Calendar incEffectiveDate) {
		this.incEffectiveDate = incEffectiveDate;
	}
	@Column(name = "INCRCVDAT")
	@Temporal(TemporalType.DATE)
	@Audit(name="Incentive Receive")
	public Calendar getIncRecvDate() {
		return incRecvDate;
	}
	public void setIncRecvDate(Calendar incRecvDate) {
		this.incRecvDate = incRecvDate;
	}
	@Column(name = "LSTUPDUSR")
	public String getLstUpdUsr() {
		return lstUpdUsr;
	}
	public void setLstUpdUsr(String lstUpdUsr) {
		this.lstUpdUsr = lstUpdUsr;
	}
	@Version
	@Column(name = "LSTUPDTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLstUpdTime() {
		return lstUpdTime;
	}
	public void setLstUpdTime(Calendar lstUpdTime) {
		this.lstUpdTime = lstUpdTime;
	}
	@Column(name = "TAGIDX")
	public int getTagIdx() {
		return tagIdx;
	}
	public void setTagIdx(int tagIdx) {
		this.tagIdx = tagIdx;
	}
	//Added by A-8527 for ICRD-262451 starts
	@Column(name = "CLMGENDAT")
	@Audit(name="Claim Generation Date")
	public Calendar getClmGenDate() {
		return clmGenDate;
	}
	public void setClmGenDate(Calendar clmGenDate) {
		this.clmGenDate = clmGenDate;
	}
	//Added by A-8527 for ICRD-262451 Ends
	public static String getModuleName() {
		return MODULE_NAME;
	}
	public USPSPostalCalendar(USPSPostalCalendarVO uSPSPostalCalendarVO)
			throws SystemException {
		populatePK(uSPSPostalCalendarVO);
		populateAtrributes(uSPSPostalCalendarVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
	}
	public static Collection<USPSPostalCalendarVO> listPostalCalendarDetails(
			USPSPostalCalendarFilterVO uSPSPostalCalendarFilterVO)
			throws SystemException {
		try {
			return constructDAO().listPostalCalendarDetails(uSPSPostalCalendarFilterVO);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	public static MailTrackingDefaultsDAO constructDAO() throws SystemException {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MailTrackingDefaultsDAO.class.cast(em.getQueryDAO(MODULE_NAME));
		} catch (PersistenceException ex) {
			ex.getErrorCode();
			throw new SystemException(ex.getMessage());
		}
	}
	public void remove() throws SystemException {
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			throw new SystemException(removeException.getErrorCode(),
					removeException);
		}
	}
	public static USPSPostalCalendar find(USPSPostalCalendarPK uSPSPostalCalendarPK)
			throws SystemException, FinderException {
		EntityManager em = PersistenceController.getEntityManager();
		return em.find(USPSPostalCalendar.class, uSPSPostalCalendarPK);
	}
	public void update(USPSPostalCalendarVO uSPSPostalCalendarVO)
			throws SystemException {
		populateAtrributes(uSPSPostalCalendarVO);
		this.setLstUpdTime(uSPSPostalCalendarVO.getLstUpdTime());
	}
	/**
	 *
	 * 	Method		:	USPSPostalCalendar.populateAtrributes
	 *	Added by 	:	A-8164 on 04-Jul-2018
	 * 	Used for 	:	ICRD-236925
	 *	Parameters	:	@param uSPSPostalCalendarVO
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	void
	 */
	private void populateAtrributes(USPSPostalCalendarVO uSPSPostalCalendarVO)
			throws SystemException {
		this.setLstUpdTime((uSPSPostalCalendarVO.getLstUpdTime()).toCalendar());
		this.setLstUpdUsr(uSPSPostalCalendarVO.getLstUpdUsr());
		this.setPeriod(uSPSPostalCalendarVO.getPeriods());
		this.setFromDate((new LocalDate
				(LocalDate.NO_STATION,Location.NONE,false).setDate(uSPSPostalCalendarVO.getFromDates())).toCalendar());
		this.setToDate((new LocalDate
				(LocalDate.NO_STATION,Location.NONE,false).setDate(uSPSPostalCalendarVO.getToDates())).toCalendar());
		if(((null!=uSPSPostalCalendarVO.getCgrSubmissions()))&&
				(0<uSPSPostalCalendarVO.getCgrSubmissions().length()))
			this.setCgrSubmission((new LocalDate
					(LocalDate.NO_STATION,Location.NONE,false).setDate(uSPSPostalCalendarVO.getCgrSubmissions())).toCalendar());
		if(((null!=uSPSPostalCalendarVO.getCgrDeleteCutOffs()))&&
				(0<uSPSPostalCalendarVO.getCgrDeleteCutOffs().length()))
			this.setCgrDeleteCutOff((new LocalDate
					(LocalDate.NO_STATION,Location.NONE,false).setDate(uSPSPostalCalendarVO.getCgrDeleteCutOffs())).toCalendar());
		if(((null!=uSPSPostalCalendarVO.getCutWeek1s()))&&
				(0<uSPSPostalCalendarVO.getCutWeek1s().length()))
			this.setCutWeek1((new LocalDate
					(LocalDate.NO_STATION,Location.NONE,false).setDate(uSPSPostalCalendarVO.getCutWeek1s())).toCalendar());
		if(((null!=uSPSPostalCalendarVO.getCutWeek2s()))&&
				(0<uSPSPostalCalendarVO.getCutWeek2s().length()))
			this.setCutWeek2((new LocalDate
					(LocalDate.NO_STATION,Location.NONE,false).setDate(uSPSPostalCalendarVO.getCutWeek2s())).toCalendar());
		if(((null!=uSPSPostalCalendarVO.getCutWeek3s()))&&
				(0<uSPSPostalCalendarVO.getCutWeek3s().length()))
			this.setCutWeek3((new LocalDate
					(LocalDate.NO_STATION,Location.NONE,false).setDate(uSPSPostalCalendarVO.getCutWeek3s())).toCalendar());
		if(((null!=uSPSPostalCalendarVO.getCutWeek4s()))&&
				(0<uSPSPostalCalendarVO.getCutWeek4s().length()))
			this.setCutWeek4((new LocalDate
					(LocalDate.NO_STATION,Location.NONE,false).setDate(uSPSPostalCalendarVO.getCutWeek4s())).toCalendar());
		if(((null!=uSPSPostalCalendarVO.getCutWeek5s()))&&
				(0<uSPSPostalCalendarVO.getCutWeek5s().length()))
			this.setCutWeek5((new LocalDate
					(LocalDate.NO_STATION,Location.NONE,false).setDate(uSPSPostalCalendarVO.getCutWeek5s())).toCalendar());
		if(((null!=uSPSPostalCalendarVO.getPaymEffectiveDates()))&&
				(0<uSPSPostalCalendarVO.getPaymEffectiveDates().length()))
			this.setPaymEffectiveDate((new LocalDate
					(LocalDate.NO_STATION,Location.NONE,false).setDate(uSPSPostalCalendarVO.getPaymEffectiveDates())).toCalendar());
		if(((null!=uSPSPostalCalendarVO.getIncCalcDates()))&&
				(0<uSPSPostalCalendarVO.getIncCalcDates().length()))
			this.setIncCalcDate((new LocalDate
					(LocalDate.NO_STATION,Location.NONE,false).setDate(uSPSPostalCalendarVO.getIncCalcDates())).toCalendar());
		if(((null!=uSPSPostalCalendarVO.getIncEffectiveDates()))&&
				(0<uSPSPostalCalendarVO.getIncEffectiveDates().length()))
			this.setIncEffectiveDate((new LocalDate
					(LocalDate.NO_STATION,Location.NONE,false).setDate(uSPSPostalCalendarVO.getIncEffectiveDates())).toCalendar());
		if(((null!=uSPSPostalCalendarVO.getIncRecvDates()))&&
				(0<uSPSPostalCalendarVO.getIncRecvDates().length()))
			this.setIncRecvDate((new LocalDate
					(LocalDate.NO_STATION,Location.NONE,false).setDate(uSPSPostalCalendarVO.getIncRecvDates())).toCalendar());
		//Added by A-8527 for ICRD-262451
		if(((null!=uSPSPostalCalendarVO.getClmGenDate()))&&
				(0<uSPSPostalCalendarVO.getClmGenDate().length()))
			this.setClmGenDate((new LocalDate
					(LocalDate.NO_STATION,Location.NONE,false).setDate(uSPSPostalCalendarVO.getClmGenDate())).toCalendar());
	}
	/**
	 *
	 * 	Method		:	USPSPostalCalendar.populatePK
	 *	Added by 	:	A-8164 on 04-Jul-2018
	 * 	Used for 	:	ICRD-236925
	 *	Parameters	:	@param uSPSPostalCalendarVO
	 *	Return type	: 	void
	 */
	private void populatePK(USPSPostalCalendarVO uSPSPostalCalendarVO) {
		USPSPostalCalendarPK uSPSPostalCalendarPK = new USPSPostalCalendarPK();
		uSPSPostalCalendarPK.setCompanyCode(uSPSPostalCalendarVO.getCompanyCode());
		uSPSPostalCalendarPK.setGpacod(uSPSPostalCalendarVO.getGpacod());
		uSPSPostalCalendarPK.setFilterCalender(uSPSPostalCalendarVO.getFilterCalender());
		this.setuSPSPostalCalendarPK(uSPSPostalCalendarPK);
	}
	/**
	 *
	 * 	Method		:	USPSPostalCalendar.validateFrmToDateRange
	 *	Added by 	:	A-8527 on 14-March-2019
	 * 	Used for 	:	ICRD-262471
	 *	Parameters	:	@param uSPSPostalCalendarVO
	 *	Return type	: 	void
	 */
	public static Collection<USPSPostalCalendarVO> validateFrmToDateRange(
			USPSPostalCalendarFilterVO uSPSPostalCalendarFilterVO)
			throws SystemException {
		try {
			return constructDAO().validateFrmToDateRange(uSPSPostalCalendarFilterVO);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	/**
	 *
	 * @author A-7371
	 * @param uspsPostalCalendarFilterVO
	 * @return
	 * @throws SystemException
	 */
	public static USPSPostalCalendarVO findInvoicPeriodDetails(USPSPostalCalendarFilterVO uspsPostalCalendarFilterVO)throws SystemException  {
		try {
			return constructDAO().findInvoicPeriodDetails(uspsPostalCalendarFilterVO);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
}