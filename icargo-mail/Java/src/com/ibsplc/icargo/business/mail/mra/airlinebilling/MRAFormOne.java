/*
 * MRAFormOne.java Created on Jul 28,2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.airlinebilling;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

//import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51DetailsVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.FormOneVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.InterlineFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.InvoiceInFormOneVO;
import com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling.MRAAirlineBillingDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.OptimisticConcurrencyException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
//import java.util.Set;
//import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51DetailsVO;

/**
 * @author A-2391
 * 
 */

@Entity
@Table(name = "MALMRAARLFRMONE")
public class MRAFormOne {

	private MRAFormOnePK formOnePK;
	private String airlineCode;
	private Double exgRateLstBlgCur;
	private Double totAmtBlgCur;
	private String blgCurCod;
	private String lstCurCod;
	private Double totMisAmt;
	private String formOneStatus;
	private String lastUpdatedUser;
	private Calendar lastUpdatedTime;

	private Collection<MRAFormOneInv> formOneInvs;

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";

	private static final String CLASS_NAME = "FormOne";
	private Log log = LogFactory.getLogger("MRA AIRLINEBILLING FormOne");

	/**
	 * Default Constructor
	 */
	public MRAFormOne() {
	}

	/**
	 * Constructor
	 * 
	 * @author A-2408
	 * @param formOneVO
	 * @throws SystemException
	 */
    public MRAFormOne(FormOneVO formOneVO)
    throws SystemException{
		staticLogger().entering("MRAFormOne", "inside MRAFormOne");
		log.log(Log.INFO, "formOneVO in MRAFormOne ", formOneVO);
		MRAFormOnePK constructedformOnePK
    						=new MRAFormOnePK(formOneVO.getCompanyCode(),formOneVO.getAirlineIdr(),
    								formOneVO.getClearancePeriod(),formOneVO.getInterlineBillingType(),
    								formOneVO.getClassType());
		this.setFormOnePK(constructedformOnePK);
		log.log(Log.INFO, "MRAFormOnePK set");
		populateAttributes(formOneVO);

		log.log(Log.INFO, "populateAttributes set");
		try {
			PersistenceController.getEntityManager().persist(this);
    	}
    	catch(CreateException e){
			throw new SystemException(e.getErrorCode());
		}
    	//add <formOneInvVO> if needed
    	/*ArrayList<InvoiceInFormOneVO> formOneInvss=new ArrayList<InvoiceInFormOneVO>(formOneVO.getInvoiceInFormOneVOs());
    	if(formOneInvss!=null && formOneInvss.size()>0){
    		for(InvoiceInFormOneVO formOneInvVO:formOneInvss){
    			MRAFormOneInv formOneInv=new MRAFormOneInv(formOneInvVO);
    			if(getFormOneInvs()==null){
        			setFormOneInvs(new ArrayList<MRAFormOneInv>());
        		}
    			getFormOneInvs().add(formOneInv);
    		}
    	}*/
    }

	/**
	 * @return the lastUpdatedUser
	 */
	@Column(name = "LSTUPDUSR")
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}

	/**
	 * @param lastUpdatedUser
	 *            the lastUpdatedUser to set
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}

	/**
	 * @return the lastUpdateTime
	 * 
	 */
	@Version
	@Column(name = "LSTUPDTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	/**
	 * @param lastUpdatedTime
	 *            The lastUpdatedTime to set.
	 */
	public void setLastUpdatedTime(Calendar lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	@Column(name = "ARLCOD")
	/**
	 * @return Returns the airlineCode.
	 */
	public String getAirlineCode() {
		return airlineCode;
	}

	/**
	 * @param airlineCode
	 *            The airlineCode to set.
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}

	/**
	 * @return Returns the blgCurCod.
	 */
	@Column(name = "BLGCURCOD")
	public String getBlgCurCod() {
		return blgCurCod;
	}

	/**
	 * @param blgCurCod
	 *            The blgCurCod to set.
	 */
	public void setBlgCurCod(String blgCurCod) {
		this.blgCurCod = blgCurCod;
	}

	/**
	 * @return Returns the exgRateLstBlgCur.
	 */
	@Column(name = "EXGRATLSTBLGCUR")
	public Double getExgRateLstBlgCur() {
		return exgRateLstBlgCur;
	}

	/**
	 * @param exgRateLstBlgCur
	 *            The exgRateLstBlgCur to set.
	 */
	public void setExgRateLstBlgCur(Double exgRateLstBlgCur) {
		this.exgRateLstBlgCur = exgRateLstBlgCur;
	}

	/**
	 * @return Returns the formOnePK.
	 */

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "airlineIdentifier", column = @Column(name = "ARLIDR")),
			@AttributeOverride(name = "clearancePeriod", column = @Column(name = "CLRPRD")),
			@AttributeOverride(name = "intBillingType", column = @Column(name = "INTBLGTYP")),
			@AttributeOverride(name = "classType", column = @Column(name = "CLSTYP")) })
	public MRAFormOnePK getFormOnePK() {
		return formOnePK;
	}

	/**
	 * @param formOnePK
	 *            The formOnePK to set.
	 */
	public void setFormOnePK(MRAFormOnePK formOnePK) {
		this.formOnePK = formOnePK;
	}

	/**
	 * @return Returns the formOneInvs.
	 */
	@OneToMany
	@JoinColumns({
			@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
			@JoinColumn(name = "ARLIDR", referencedColumnName = "ARLIDR", insertable = false, updatable = false),
			@JoinColumn(name = "CLRPRD", referencedColumnName = "CLRPRD", insertable = false, updatable = false),
			@JoinColumn(name = "INTBLGTYP", referencedColumnName = "INTBLGTYP", insertable = false, updatable = false),
			@JoinColumn(name = "CLSTYP", referencedColumnName = "CLSTYP", insertable = false, updatable = false) })
	public Collection<MRAFormOneInv> getFormOneInvs() {
		return formOneInvs;
	}

	/**
	 * @param formOneInvs
	 *            The formOneInvs to set.
	 */
	public void setFormOneInvs(Collection<MRAFormOneInv> formOneInvs) {
		this.formOneInvs = formOneInvs;
	}

	/**
	 * @return Returns the formOneStatus.
	 */
	@Column(name = "FRMONESTA")
	public String getFormOneStatus() {
		return formOneStatus;
	}

	/**
	 * @param formOneStatus
	 *            The formOneStatus to set.
	 */
	public void setFormOneStatus(String formOneStatus) {
		this.formOneStatus = formOneStatus;
	}

	/**
	 * @return Returns the lstCurCod.
	 */
	@Column(name = "LSTCURCOD")
	public String getLstCurCod() {
		return lstCurCod;
	}

	/**
	 * @param lstCurCod
	 *            The lstCurCod to set.
	 */
	public void setLstCurCod(String lstCurCod) {
		this.lstCurCod = lstCurCod;
	}

	/**
	 * @return Returns the totAmtBlgCur.
	 */
	@Column(name = "TOTAMTBLGCUR")
	public Double getTotAmtBlgCur() {
		return totAmtBlgCur;
	}

	/**
	 * @param totAmtBlgCur
	 *            The totAmtBlgCur to set.
	 */
	public void setTotAmtBlgCur(Double totAmtBlgCur) {
		this.totAmtBlgCur = totAmtBlgCur;
	}

	/**
	 * @return Returns the totAmtLstCur.
	 */
	//@Column(name = "TOTMISAMT")
	@Column(name = "TOTMISAMTLSTCUR")     //Modified by A-7929 as part of ICRD-265471
	public Double getTotMisAmt() {
		return totMisAmt;
	}

	/**
	 * @param totMisAmt
	 *            The totMisAmt to set.
	 */
	public void setTotMisAmt(Double totMisAmt) {
		this.totMisAmt = totMisAmt;
	}

	private static Log staticLogger() {
		return LogFactory.getLogger(MODULE_NAME);
	}

	/**
	 * @param formOneVO
	 */
	private void populateAttributes(FormOneVO formOneVO) throws SystemException {
		log.log(Log.INFO, "entering populateAttributes");
		this.setAirlineCode(formOneVO.getAirlineCode());
		this.setBlgCurCod(formOneVO.getBillingCurrency());
		this.setExgRateLstBlgCur(formOneVO.getExchangeRateBillingCurrency());
		this.setFormOneStatus(formOneVO.getFormOneStatus());
		this.setLstCurCod(formOneVO.getListingCurrency());
		if (formOneVO.getBillingTotalAmt() != null) {
			this.setTotAmtBlgCur(formOneVO.getBillingTotalAmt().getAmount());
		}
		if (formOneVO.getMissAmount() != null) {
			this.setTotMisAmt(formOneVO.getMissAmount().getAmount());
		}
		this.setLastUpdatedUser(formOneVO.getLastUpdateUser());
		this.setLastUpdatedTime(formOneVO.getLastUpdateTime());
		if (formOneVO.getInvoiceInFormOneVOs() != null
				&& formOneVO.getInvoiceInFormOneVOs().size() > 0) {
			populateChild(formOneVO.getInvoiceInFormOneVOs());
		}
		log.log(Log.INFO, "exiting populateAttributes");

	}

	/**
	 * 
	 * @param companyCode
	 * @param airlineIdentifier
	 * @param clearancePeriod
	 * @param intBlgType
	 * @param classType
	 * @return
	 * @throws FinderException
	 * @throws SystemException
	 */
    public static MRAFormOne find(String companyCode,
			int airlineIdentifier, String clearancePeriod,
			String intBlgType, String classType)
			throws FinderException, SystemException {
    	MRAFormOnePK formOnePKk = new MRAFormOnePK(
				companyCode, airlineIdentifier, clearancePeriod,
				intBlgType, classType);
		MRAFormOne foundEntity = null;
		return PersistenceController.getEntityManager().find(MRAFormOne.class, formOnePKk);
	}

	/**
	 * 
	 * @param formOneVO
	 */
	public void update(FormOneVO formOneVO) throws SystemException {

		populateAttributes(formOneVO);

	}

	/**
	 * 
	 * @throws RemoveException
	 * @throws SystemException
	 */
	public void remove() throws RemoveException, SystemException {
		Collection<MRAFormOneInv> formOneInvss = this.formOneInvs;
		if (formOneInvss != null && formOneInvss.size() > 0) {
			for (MRAFormOneInv formOneInv : formOneInvss) {
				formOneInv.remove();
			}
		}

		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (OptimisticConcurrencyException ex) {
			throw new SystemException(ex.getMessage(), ex);
		}

	}

	/**
	 * 
	 * @return mraAirlineBillingDAO
	 * @throws SystemException
	 */
	private static MRAAirlineBillingDAO constructMRAAirlineBillingDAO() throws SystemException {

		MRAAirlineBillingDAO mraAirlineBillingDAO = null;
		try {
			EntityManager em = PersistenceController.getEntityManager();
				mraAirlineBillingDAO = MRAAirlineBillingDAO.class.cast
											(em.getQueryDAO(MODULE_NAME));
		} catch (PersistenceException e) {
			throw new SystemException(e.getMessage(), e);
		}

		return mraAirlineBillingDAO;
	}


	/**
	 * 
	 * @param formOneVO
	 * @return
	 * @throws SystemException
	 */
	public static FormOneVO listFormOneDetails(FormOneVO formOneVO)
	throws SystemException{
		
		FormOneVO formonevo=null;
    	
		
		return constructMRAAirlineBillingDAO().listFormOneDetails(formOneVO);
	}
	/**

	 *

	 * @param interlineFilterVo

	 * @return

	 * @throws SystemException

	 */

	public static FormOneVO findFormOneDetails(InterlineFilterVO interlineFilterVo) throws SystemException {
		Log log = LogFactory.getLogger("MRA AIRLINEBILLING");
		log.entering("MRAFormOne", "findFormOneDetails");
		
		MRAAirlineBillingDAO mraAirlineBillingDAO = constructMRAAirlineBillingDAO();

		return mraAirlineBillingDAO.findFormOneDetails(interlineFilterVo);

		

	}

	public void populateChild(Collection<InvoiceInFormOneVO> formOneInvVos)
			throws SystemException {
		MRAFormOneInv formOneInv = null;
		try {
			for (InvoiceInFormOneVO formOneInvVO : formOneInvVos) {
				if (formOneInvVO.getOperationFlag() != null
						&& ("I").equals(formOneInvVO.getOperationFlag())) {
					formOneInv = new MRAFormOneInv(formOneInvVO);
					if (getFormOneInvs() == null) {
						setFormOneInvs(new ArrayList<MRAFormOneInv>());
						log.log(Log.INFO, " -- adding for first time ---- ");
					}
					getFormOneInvs().add(formOneInv);
				} else if (formOneInvVO.getOperationFlag() != null
						&& ("U".equals(formOneInvVO.getOperationFlag()))) {
					log.log(Log.INFO,
							" -- inside update of child in parent---- ");
					formOneInv = MRAFormOneInv.find(
							formOneInvVO.getCompanyCode(),
							formOneInvVO.getAirlineIdentifier(),
							formOneInvVO.getClearancePeriod(),
							formOneInvVO.getIntBlgTyp(),
							formOneInvVO.getClassType(),
							formOneInvVO.getInvoiceNumber());
					log.log(Log.INFO, " --  child entity found ---- ");
					formOneInv.update(formOneInvVO);
					log.log(Log.INFO, " --  child entity updated ---- ");
				} else if (formOneInvVO.getOperationFlag() != null
						&& ("D").equals(formOneInvVO.getOperationFlag())) {
					formOneInv = MRAFormOneInv.find(
							formOneInvVO.getCompanyCode(),
							formOneInvVO.getAirlineIdentifier(),
							formOneInvVO.getClearancePeriod(),
							formOneInvVO.getIntBlgTyp(),
							formOneInvVO.getClassType(),
							formOneInvVO.getInvoiceNumber());
					formOneInv.remove();
					AirlineCN51Summary c51Sum = null;
					c51Sum = AirlineCN51Summary.find(
							formOneInvVO.getCompanyCode(),
							formOneInvVO.getAirlineIdentifier(),
							formOneInvVO.getIntBlgTyp(),
							formOneInvVO.getInvoiceNumber(),
							formOneInvVO.getClearancePeriod());
					if (c51Sum.getInvSrc() != null) {
						if (("D").equals(c51Sum.getInvSrc())) {
							c51Sum.remove();
						}
					}
				}
			}
		} catch (FinderException finderException) {
			log.log(Log.SEVERE, "FINDER EXCEPTION OCCURED IN update of formOne");

		} catch (RemoveException removeException) {
			log.log(Log.SEVERE, "RemoveException OCCURED IN update of formOne");

		}

	}

}
