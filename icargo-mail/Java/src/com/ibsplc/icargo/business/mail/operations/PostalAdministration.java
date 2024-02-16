/*
 * PostalAdministration.java Created on June 27, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import java.util.Calendar;
import java.util.Collection;
import java.util.Set;
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
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GenerateInvoiceFilterVO;
import com.ibsplc.icargo.business.mail.operations.cache.PostalAdministrationCache;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailEventVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.cache.CacheFactory;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-3109
 */
@Entity
@Table(name = "MALPOAMST")
public class PostalAdministration {

	private static final String MODULE_NAME = "mail.operations";
	private Log log = LogFactory.getLogger("MAIL operations");

	private PostalAdministrationPK postalAdministrationPK;

	/**
	 * paName
	 */
	private String paName;

	/**
	 * address
	 */
	private String address;

	/**
	 * country Code
	 */
	private String countryCode;

	/**
	 * partialResdit
	 */
	private String partialResdit;

	/**
	 * msgEventLocationNeeded
	 */
	private String msgEventLocationNeeded;

	/**
	 * mailEvents
	 */
	//private Set<MailEvent> mailEvents;

	/**
	 * settlementCurrencyCode
	 */
	private String settlementCurrencyCode;

	/**
	 * baseType
	 */
	private String baseType;

	/**
	 * aiport code
	 */
	private String billingSource;

	/**
	 * billingFrequency
	 */
	private String billingFrequency;

	/**
	 * lastUpdateUser
	 */
	private String lastUpdateUser;

	/**
	 * lastUpdateTime
	 */
	private Calendar lastUpdateTime;

	/**
	 * conPerson
	 */
	private String conPerson;

	/**
	 * state
	 */
	private String state;

	/**
	 * country
	 */
	private String country;

	/**
	 * mobile
	 */
	private String mobile;

	/**
	 * postCod
	 */
	private String postCod;

	/**
	 * phone1
	 */
	private String phone1;

	/**
	 * phone2
	 */
	private String phone2;

	/**
	 * fax
	 */
	private String fax;

	/**
	 * email
	 */
	private String email;

	/**
	 * city
	 */
	private String city;

	/**
	 * remarks
	 */
	private String remarks;

	/**
	 * debInvCode
	 */
	private String debInvCode;

	/**

	 * status
	 */
	private String status;

	/**
	 * residtVersion
	 */
	private String residtVersion;

	/**
	 * account number
	 */
	private String accNum;

	/**
	 * vat Number
	 */
	private String vatNumber;

	/**
	 * autoEmailReqd
	 */
	private String autoEmailReqd;

	/**
	 * dueInDays
	 */
	private int dueInDays;

	/**
	 * isM39Compliant
	 */
	private String isM39Compliant;

	/**
	 * gibCustomerFlag
	 */
	private String gibCustomerFlag;

	//Added by A-6991 for the ICRD-211662 
	private String proformaInvoiceRequired;
	//added by A-7371 for  ICRD-212135
	private int resditTriggerPeriod;

	private String latValLevel;

	//added by a-7531 for icrd-235799
	 private String settlementLevel;
	 private double tolerancePercent;
	 private double toleranceValue;
	 private double maxValue;
	       
	 private int dupMailbagPeriod;//added by A-8353 for ICRD-230449
	 //Added as part of IASCB-853
		
	   private String secondaryEmail1;
	   private String secondaryEmail2;
	
	
	
	@Column(name = "GIBFLG")
	public String getGibCustomerFlag() {
		return gibCustomerFlag;
	}

	public void setGibCustomerFlag(String gibCustomerFlag) {
		this.gibCustomerFlag = gibCustomerFlag;
	}

	/**
	 * @return Returns the baseType.
	 */
	@Column(name = "BASTYP")
	public String getBaseType() {
		return baseType;
	}

	/**
	 * @param baseType
	 *            The baseType to set.
	 */
	public void setBaseType(String baseType) {
		this.baseType = baseType;
	}

	/**
	 * Is MessagingEnabled For PA
	 */
	private String messagingEnabled;

	/**
	 * @return Returns the messagingEnabled.
	 */
	@Column(name = "MSGENBFLG")
	public String getMessagingEnabled() {
		return messagingEnabled;
	}

	/**
	 * @param messagingEnabled
	 *            The messagingEnabled to set.
	 */
	public void setMessagingEnabled(String messagingEnabled) {
		this.messagingEnabled = messagingEnabled;
	}

	/**
	 * @return Returns the address.
	 */
	@Column(name = "POAADR")
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            The address to set.
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return Returns the countryCode.
	 */
	@Column(name = "CNTCOD")
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * @param countryCode
	 *            The countryCode to set.
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * @return Returns the lastUpdateTime.
	 */
	@Version
	@Column(name = "LSTUPDTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * @param lastUpdateTime
	 *            The lastUpdateTime to set.
	 */
	public void setLastUpdateTime(Calendar lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	/**
	 * @return Returns the lastUpdateUser.
	 */

	@Column(name = "LSTUPDUSR")
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	/**
	 * @param lastUpdateUser
	 *            The lastUpdateUser to set.
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	/**
	 * @return Returns the conPerson.
	 */
	@Column(name = "CONPSN")
	public String getConPerson() {
		return conPerson;
	}

	/**
	 * @param conPerson
	 *            The conPerson to set.
	 */
	public void setConPerson(String conPerson) {
		this.conPerson = conPerson;
	}

	/**
	 * @return Returns the city.
	 */
	@Column(name = "CTYNAM")
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            The city to set.
	 */
	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "STANAM")
	/**
	 * @return Returns the state.
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            The state to set.
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return Returns the country.
	 */
	@Column(name = "CNTNAM")
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            The country to set.
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return Returns the email.
	 */
	@Column(name = "EMLADR")
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            The email to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "FAX")
	/**
	 * @return Returns the fax.
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @param fax
	 *            The fax to set.
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "MOBNUM")
	/**
	 * @return Returns the mobile.
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile
	 *            The mobile to set.
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return Returns the phone1.
	 */
	@Column(name = "PHNONE")
	public String getPhone1() {
		return phone1;
	}

	/**
	 * @param phone1
	 *            The phone1 to set.
	 */
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	/**
	 * @return Returns the phone2.
	 */
	@Column(name = "PHNTWO")
	public String getPhone2() {
		return phone2;
	}

	/**
	 * @param phone2
	 *            The phone2 to set.
	 */
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	/**
	 * @return Returns the postCod.
	 */
	@Column(name = "POSCOD")
	public String getPostCod() {
		return postCod;
	}

	/**
	 * @param postCod
	 *            The postCod to set.
	 */
	public void setPostCod(String postCod) {
		this.postCod = postCod;
	}

	/**
	 * @return Returns the remarks.
	 */
	@Column(name = "RMK")
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "DBTINVCOD")
	public String getDebInvCode() {
		return debInvCode;
	}

	public void setDebInvCode(String debInvCode) {
		this.debInvCode = debInvCode;
	}



	@Column(name = "ACTFLG")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "ACCNUM")
	public String getAccNum() {
		return accNum;
	}

	public void setAccNum(String accNum) {
		this.accNum = accNum;
	}

	@Column(name = "RDTVERNUM")
	public String getResidtVersion() {
		return residtVersion;
	}

	public void setResidtVersion(String residtVersion) {
		this.residtVersion = residtVersion;
	}

	/**
	 * @return the vatNumber
	 */
	@Column(name = "VATNUM")
	public String getVatNumber() {
		return vatNumber;
	}

	/**
	 * @param vatNumber
	 *            the vatNumber to set
	 */
	public void setVatNumber(String vatNumber) {
		this.vatNumber = vatNumber;
	}

	/**
	 * Getter for autoEmailReqd
	 */
	@Column(name = "AUTEMLREQ")
	public String getAutoEmailReqd() {
		return autoEmailReqd;
	}

	/**
	 * @param autoEmailReqd
	 *            the autoEmailReqd to set Setter for autoEmailReqd
	 */
	public void setAutoEmailReqd(String autoEmailReqd) {
		this.autoEmailReqd = autoEmailReqd;
	}

	/**
	 * @return Returns the paName.
	 */
	@Column(name = "POANAM")
	public String getPaName() {
		return paName;
	}

	/**
	 * @param paName
	 *            The paName to set.
	 */
	public void setPaName(String paName) {
		this.paName = paName;
	}

	/**
	 * @return Returns the partialResdit.
	 */
	@Column(name = "PRTRDT")
	public String getPartialResdit() {
		return partialResdit;
	}

	/**
	 * @param partialResdit
	 *            The partialResdit to set.
	 */
	public void setPartialResdit(String partialResdit) {
		this.partialResdit = partialResdit;
	}

	/**
	 * @return Returns the msgEventLocationNeeded.
	 */
	@Column(name = "MSGEVTLOC")
	public String getMsgEventLocationNeeded() {
		return msgEventLocationNeeded;
	}

	/**
	 * @param msgEventLocationNeeded
	 *            The msgEventLocationNeeded to set.
	 */
	public void setMsgEventLocationNeeded(String msgEventLocationNeeded) {
		this.msgEventLocationNeeded = msgEventLocationNeeded;
	}

	/**
	 * @param dueInDays
	 *            the dueInDays to set Used for :
	 */
	public void setDueInDays(int dueInDays) {
		this.dueInDays = dueInDays;
	}

	/**
	 * Getter for dueInDays Used for :
	 */
	@Column(name = "DUEDAY")
	public int getDueInDays() {
		return dueInDays;
	}

	/**
	 * @return the isM39Compliant
	 */
	@Column(name = "M39ENBFLG")
	public String getIsM39Compliant() {
		return isM39Compliant;
	}

	/**
	 * @param isM39Compliant
	 *            the isM39Compliant to set
	 */
	public void setIsM39Compliant(String isM39Compliant) {
		this.isM39Compliant = isM39Compliant;
	}
	/**
	 * 	Getter for profomaInvoiceRequired 
	 *	Added by : A-6991 on 28-Aug-2017
	 * 	Used for : ICRD-211662 
	 */
	@Column(name = "PROINVREQ")
	public String getProformaInvoiceRequired() {
		return proformaInvoiceRequired;
	}

	/**
	 *  @param profomaInvoiceRequired the profomaInvoiceRequired to set
	 * 	Setter for profomaInvoiceRequired 
	 *	Added by : A-6991 on 28-Aug-2017
	 * 	Used for : ICRD-211662 
	 */
	public void setProformaInvoiceRequired(String proformaInvoiceRequired) {
		this.proformaInvoiceRequired = proformaInvoiceRequired;
	}
	/**
	 * 
	 * @return resditTriggerPeriod
	 */
	@Column(name = "RDTSNDPRD")
	public int getResditTriggerPeriod() {
		return resditTriggerPeriod;
	}
	/**
	 * 
	 * @param resditTriggerPeriod
	 */
	public void setResditTriggerPeriod(int resditTriggerPeriod) {
		this.resditTriggerPeriod = resditTriggerPeriod;
	}
	
	@Column(name = "LATVALLVL")
	public String getLatValLevel() {
		return latValLevel;
	}

	public void setLatValLevel(String latValLevel) {
		this.latValLevel = latValLevel;
	}

	 @Column(name = "STLLVL")
	public String getSettlementLevel() {
		return settlementLevel;
	}

	public void setSettlementLevel(String settlementLevel) {
		this.settlementLevel = settlementLevel;
	}
	@Column(name = "STLTOLPER")
	public double getTolerancePercent() {
		return tolerancePercent;
	}

	public void setTolerancePercent(double tolerancePercent) {
		this.tolerancePercent = tolerancePercent;
	}
	@Column(name = "STLTOLVAL")
	public double getToleranceValue() {
		return toleranceValue;
	}

	public void setToleranceValue(double toleranceValue) {
		this.toleranceValue = toleranceValue;
	}
	@Column(name = "STLTOLMAXVAL")
	public double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}

	@Column(name="DUPMALPRD")
	public int getDupMailbagPeriod() {
		return dupMailbagPeriod;
	}

	public void setDupMailbagPeriod(int dupMailbagPeriod) {
		this.dupMailbagPeriod = dupMailbagPeriod;
	}
	//Added as part of IASCB-853 starts
     @Column(name="SECEMLADRONE")
     public String getSecondaryEmail1() {
 		return secondaryEmail1;
 	}

 	public void setSecondaryEmail1(String secondaryEmail1) {
 		this.secondaryEmail1 = secondaryEmail1;
 	}

     @Column(name="SECEMLADRTWO")
     public String getSecondaryEmail2() {
 		return secondaryEmail2;
 	}

 	public void setSecondaryEmail2(String secondaryEmail2) {
 		this.secondaryEmail2 = secondaryEmail2;
 	}
 	//Added as part of IASCB-853 ends
	/**
	 * @return Returns the postalAdministrationPK.
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "paCode", column = @Column(name = "POACOD")) })
	public PostalAdministrationPK getPostalAdministrationPK() {
		return postalAdministrationPK;
	}

	/**
	 * @param postalAdministrationPK
	 *            The postalAdministrationPK to set.
	 */
	public void setPostalAdministrationPK(
			PostalAdministrationPK postalAdministrationPK) {
		this.postalAdministrationPK = postalAdministrationPK;
	}

	/**
	 * @return Returns the mailEvents.
	 */

//	@OneToMany
//	@JoinColumns({
//			@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
//			@JoinColumn(name = "POACOD", referencedColumnName = "POACOD", insertable = false, updatable = false) })
//	public Set<MailEvent> getMailEvents() {
//		return mailEvents;
//	}

	/**
	 * @param mailEvents
	 *            The mailEvents to set.
	 */
//	public void setMailEvents(Set<MailEvent> mailEvents) {
//		this.mailEvents = mailEvents;
//	}

	/**
	 * Empty Constructor
	 *
	 */
	public PostalAdministration() {

	}

	/**
	 *
	 * @param postalAdministrationVO
	 * @throws SystemException
	 * @throws FinderException
	 */
	public PostalAdministration(PostalAdministrationVO postalAdministrationVO)
			throws SystemException {
		populatePK(postalAdministrationVO);
		populateAttribute(postalAdministrationVO);
		EntityManager em = PersistenceController.getEntityManager();
		try {
			em.persist(this);
		} catch (CreateException createException) {
			// TODO Auto-generated catch block
			createException.getErrorCode();
			throw new SystemException(createException.getErrorCode());
		}
		savePostalAdministration(postalAdministrationVO);

	}

	/**
	 *
	 * @param postalAdministrationVO
	 */
	private void populatePK(PostalAdministrationVO postalAdministrationVO) {
		PostalAdministrationPK postalAdministrationPk = new PostalAdministrationPK();
		postalAdministrationPk.setCompanyCode(postalAdministrationVO
				.getCompanyCode());
		postalAdministrationPk.setPaCode(postalAdministrationVO.getPaCode());
		this.postalAdministrationPK = postalAdministrationPk;
	}

	/**
	 *
	 * @param postalAdministrationVO
	 */
	private void populateAttribute(PostalAdministrationVO postalAdministrationVO) {
		this.setAddress(postalAdministrationVO.getAddress());
		this.setCountryCode(postalAdministrationVO.getCountryCode());
		this.setPaName(postalAdministrationVO.getPaName());

		if(postalAdministrationVO.getMessagingEnabled()!=null){
		this.setMessagingEnabled(postalAdministrationVO.getMessagingEnabled());

		}else{
			this.setMessagingEnabled(MailConstantsVO.FLAG_NO);
		}
		//Added by A-6991 for ICRD-211662
		if(postalAdministrationVO.getProformaInvoiceRequired()!=null){
			this.setProformaInvoiceRequired(postalAdministrationVO.getProformaInvoiceRequired());

			}
		this.setPartialResdit(postalAdministrationVO.isPartialResdit() ? PostalAdministrationVO.FLAG_YES
				: PostalAdministrationVO.FLAG_NO);
		this.setMsgEventLocationNeeded(postalAdministrationVO
				.isMsgEventLocationNeeded() ? PostalAdministrationVO.FLAG_YES
				: PostalAdministrationVO.FLAG_NO);
		if(postalAdministrationVO.getBaseType()!=null){
		this.setBaseType(postalAdministrationVO.getBaseType());
		}else{
			this.setBaseType(PostalAdministrationVO.FLAG_NO);
		}
		this.setLastUpdateUser(postalAdministrationVO.getLastUpdateUser());
		this.setConPerson(postalAdministrationVO.getConPerson());
		this.setCity(postalAdministrationVO.getCity());
		this.setState(postalAdministrationVO.getState());
		this.setCountry(postalAdministrationVO.getCountry());
		this.setMobile(postalAdministrationVO.getMobile());
		this.setPostCod(postalAdministrationVO.getPostCod());
		this.setPhone1(postalAdministrationVO.getPhone1());
		this.setPhone2(postalAdministrationVO.getPhone2());
		this.setFax(postalAdministrationVO.getFax());
		this.setEmail(postalAdministrationVO.getEmail());
		this.setRemarks(postalAdministrationVO.getRemarks());
		if(postalAdministrationVO.getStatus()!=null){
		this.setStatus(postalAdministrationVO.getStatus());
		}else{
			this.setStatus("NEW");
		}
		this.setDebInvCode(postalAdministrationVO.getDebInvCode());
		if(postalAdministrationVO.getGibCustomerFlag()!=null){
		this.setGibCustomerFlag(postalAdministrationVO.getGibCustomerFlag());
		}else{
			this.setGibCustomerFlag(PostalAdministrationVO.FLAG_NO);
		}
		this.setAccNum(postalAdministrationVO.getAccNum());
		this.setResidtVersion(postalAdministrationVO.getResidtversion());
		
		//Added by A-7540
		this.setLatValLevel(postalAdministrationVO.getLatValLevel());

		this.setVatNumber(postalAdministrationVO.getVatNumber());
		if(postalAdministrationVO.getAutoEmailReqd()!=null){
		this.setAutoEmailReqd(postalAdministrationVO.getAutoEmailReqd());
		}
		else{
			this.setAutoEmailReqd(PostalAdministrationVO.FLAG_NO);
		}
		this.setDueInDays(postalAdministrationVO.getDueInDays());
		if ("1.1".equalsIgnoreCase(postalAdministrationVO.getResidtversion()) || MailConstantsVO.M49_1_1.equals(postalAdministrationVO.getResidtversion())) {
			this.setIsM39Compliant(PostalAdministrationVO.FLAG_YES);
		} else {
			this.setIsM39Compliant(PostalAdministrationVO.FLAG_NO);
		}
		this.setResditTriggerPeriod(postalAdministrationVO.getResditTriggerPeriod());
		this.setSettlementLevel(postalAdministrationVO.getSettlementLevel());
		this.setTolerancePercent(postalAdministrationVO.getTolerancePercent());
		this.setToleranceValue(postalAdministrationVO.getToleranceValue());
		this.setMaxValue(postalAdministrationVO.getMaxValue());    
		this.dupMailbagPeriod=postalAdministrationVO.getDupMailbagPeriod();	//added by A-8353 for ICRD-230449
		//Added as part of IASCB-853 starts
		this.setSecondaryEmail1(postalAdministrationVO.getSecondaryEmail1());
		this.setSecondaryEmail2(postalAdministrationVO.getSecondaryEmail2());
		//Added as part of IASCB-853 ends
	}

	/**
	 *
	 * @param postalAdministrationVO
	 * @throws SystemException
	 */
	public void savePostalAdministration(
			PostalAdministrationVO postalAdministrationVO)
			throws SystemException {
		//Collection<MailEventVO> mailEventVOs = postalAdministrationVO
		//		.getMailEvents();
//		if (mailEventVOs != null && mailEventVOs.size() > 0) {
//			for (MailEventVO mailEventVO : mailEventVOs) {
//				new MailEvent(mailEventVO);
//			}
//		}
		Collection<PostalAdministrationDetailsVO> postalAdministrationDetailsVOs = postalAdministrationVO
				.getPaDetails();
		if (postalAdministrationDetailsVOs != null
				&& postalAdministrationDetailsVOs.size() > 0) {
			for (PostalAdministrationDetailsVO postalAdminVO : postalAdministrationDetailsVOs) {
				new PostalAdministrationDetails(postalAdminVO);
			}

		}
	}

	/**
	 *
	 * @param companyCode
	 * @param paCode
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static PostalAdministration find(String companyCode, String paCode)
			throws SystemException, FinderException {
		PostalAdministrationPK postalAdministrationPk = new PostalAdministrationPK();
		postalAdministrationPk.setCompanyCode(companyCode);
		postalAdministrationPk.setPaCode(paCode);
		EntityManager em = PersistenceController.getEntityManager();
		return em.find(PostalAdministration.class, postalAdministrationPk);
	}

	/**
	 *
	 * @throws SystemException
	 * @throws RemoveException
	 */
	public void remove() throws SystemException, RemoveException {
		//removeChild();
		PersistenceController.getEntityManager().remove(this);
		log.log(Log.INFO,"BEFORE flush for PostalAdministrationCache  DELETE");
		CacheFactory factory = CacheFactory.getInstance();
		PostalAdministrationCache cache = factory.getCache(PostalAdministrationCache.ENTITY_NAME);
		cache.invalidateForGroup(PostalAdministrationCache.POSTALADMINISTRATION_CACHE_GROUP);
		cache.invalidateForGroup(PostalAdministrationCache.POSTALADMINISTRATIONPARTYIDENTIFIER_CACHE_GROUP);
	}

	/**
	 *
	 * @throws SystemException
	 * @throws RemoveException
	 */
//	private void removeChild() throws SystemException, RemoveException {
//		if (getMailEvents() != null && getMailEvents().size() > 0) {
//			for (MailEvent mailEvent : getMailEvents()) {
//				mailEvent.remove();
//			}
//		}
//	}

	/**
	 *
	 * @param postalAdministrationVO
	 * @throws RemoveException
	 * @throws SystemException
	 * @throws FinderException
	 */
	public void update(PostalAdministrationVO postalAdministrationVO)
			throws SystemException, FinderException {
		populateAttribute(postalAdministrationVO);
		this.setLastUpdateTime(postalAdministrationVO.getLastUpdateTime());
		//updateMailEvent(postalAdministrationVO.getMailEvents());/
		updatePAdetails(postalAdministrationVO.getPaDetails());
		log.log(Log.INFO,"BEFORE flush for PostalAdministrationCache  UPDATE");
		CacheFactory factory = CacheFactory.getInstance();
		PostalAdministrationCache cache = factory.getCache(PostalAdministrationCache.ENTITY_NAME);
		cache.invalidateForGroup(PostalAdministrationCache.POSTALADMINISTRATION_CACHE_GROUP);
		cache.invalidateForGroup(PostalAdministrationCache.POSTALADMINISTRATIONPARTYIDENTIFIER_CACHE_GROUP);

	}

	private void updatePAdetails(
			Collection<PostalAdministrationDetailsVO> paDetailsVos)
			throws SystemException, FinderException {

		if (paDetailsVos != null) {
			for (PostalAdministrationDetailsVO paDetailsVO : paDetailsVos) {

				if (PostalAdministrationDetailsVO.OPERATION_FLAG_DELETE
						.equals(paDetailsVO.getOperationFlag())) {

					PostalAdministrationDetails paDetails = PostalAdministrationDetails
							.find(paDetailsVO.getCompanyCode(),
									paDetailsVO.getPoaCode(),
									paDetailsVO.getParCode(),
									paDetailsVO.getSernum());
					if (paDetails != null) {
						try {

							paDetails.remove();
						} catch (RemoveException ex) {
							throw new SystemException(ex.getMessage(), ex);
						}
					}
				}
			}
			for (PostalAdministrationDetailsVO paDetailsVO : paDetailsVos) {
				if (PostalAdministrationDetailsVO.OPERATION_FLAG_INSERT
						.equals(paDetailsVO.getOperationFlag())) {
					new PostalAdministrationDetails(paDetailsVO);
				}
				if (PostalAdministrationDetailsVO.OPERATION_FLAG_UPDATE
						.equals(paDetailsVO.getOperationFlag())) {

					PostalAdministrationDetails paDetails = PostalAdministrationDetails
							.find(paDetailsVO.getCompanyCode(),
									paDetailsVO.getPoaCode(),
									paDetailsVO.getParCode(),
									paDetailsVO.getSernum());
					paDetails.update(paDetailsVO);
				}
			}
		}

	}

	private void updateMailEvent(Collection<MailEventVO> mailEventVOs)
			throws SystemException{
		if (mailEventVOs != null) {
			for (MailEventVO mailEventVO : mailEventVOs) {

				if (MailEventVO.OPERATION_FLAG_DELETE.equals(mailEventVO
						.getOperationFlag())) {
					MailEvent mailEvent;
					try {
						mailEvent = MailEvent.find(
							mailEventVO.getCompanyCode(),
							mailEventVO.getPaCode(),
							mailEventVO.getMailClass(),
							mailEventVO.getMailCategory());
					} catch (FinderException e) {
						throw new SystemException(e.getMessage(), e);
					}
					try {
						mailEvent.remove();
					} catch (RemoveException ex) {
						throw new SystemException(ex.getMessage(), ex);
					}
				}else if(MailEventVO.OPERATION_FLAG_UPDATE.equals(mailEventVO
						.getOperationFlag())){

				MailEvent mailEvent;
				try{
					mailEvent = MailEvent.find(
						mailEventVO.getCompanyCode(),
						mailEventVO.getPaCode(),
						mailEventVO.getMailClass(),
						mailEventVO.getMailCategory());
					mailEvent.update(mailEventVO);
				}catch (FinderException e){
					new MailEvent(mailEventVO);
				}
				}else if(MailEventVO.OPERATION_FLAG_INSERT.equals(mailEventVO
						.getOperationFlag())) {
					new MailEvent(mailEventVO);
				}


			}
		}
	}

	/**
	 *
	 * @return
	 * @throws SystemException
	 */
	private static MailTrackingDefaultsDAO constructDAO()
			throws SystemException {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MailTrackingDefaultsDAO.class.cast(em
					.getQueryDAO(MODULE_NAME));
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}


	/**
	 * @author A-5526
	 * @param companyCode
	 * @param paCode
	 * @return
	 * @throws SystemException
	 * Added for CRQ ICRD-111886 by A-5526
	 */
	 public static String findPartyIdentifierForPA(String companyCode,
	            String paCode)
	throws SystemException {
	try {
	return constructDAO().findPartyIdentifierForPA(companyCode, paCode);
	} catch(PersistenceException e) {
	throw new SystemException(e.getErrorCode(), e);
	}
	}

	/**
	 * @author A-2037 This method is used to find Local PAs
	 * @param companyCode
	 * @param countryCode
	 * @return Collection<PostalAdministrationVO>
	 * @throws SystemException
	 */
	public static Collection<PostalAdministrationVO> findLocalPAs(
			String companyCode, String countryCode) throws SystemException {
		try {
			return constructDAO().findLocalPAs(companyCode, countryCode);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * @author A-2037 This method is used to find Postal Administration Code
	 *         Details
	 * @param companyCode
	 * @param paCode
	 * @return PostalAdministrationVO
	 * @throws SystemException
	 */
	public static PostalAdministrationVO findPACode(String companyCode,
			String paCode)throws SystemException {
		try {
			return constructDAO().findPACode(companyCode, paCode);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	/**
	 * @author A-2037 Method for PALov containing PACode and PADescription
	 * @param companyCode
	 * @param paCode
	 * @param paName
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 */
	public static Page<PostalAdministrationVO> findPALov(String companyCode,
			String paCode, String paName, int pageNumber,int defaultSize)
			throws SystemException {
		try {
			return constructDAO().findPALov(companyCode, paCode, paName,
					pageNumber,defaultSize);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	   /**
    *
    * @param companyCode
    * @param officeOfExchange
    * @return
    * @throws SystemException
    */
   public static PostalAdministrationVO findPADetails(String companyCode,
                                                      String officeOfExchange)
           throws SystemException {
       try {
           return constructDAO().findPADetails(companyCode, officeOfExchange);
       } catch(PersistenceException e) {
           throw new SystemException(e.getErrorCode(), e);
       }
   }
	/**
	 * 	Method		:	PostalAdministration.findAllPACodes
	 *	Added by 	:	A-4809 on 08-Jan-2014
	 * 	Used for 	:	ICRD-42160
	 *	Parameters	:	@param generateInvoiceFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	Collection<PostalAdministrationVO>
	 */
	public static Collection<PostalAdministrationVO> findAllPACodes(
			GenerateInvoiceFilterVO generateInvoiceFilterVO) throws SystemException{
		try {
			return constructDAO().findAllPACodes(generateInvoiceFilterVO);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
       }
   }
	 /**
	 * @author A-5526
	 * @param companyCode
	 * @param paCode
	 * @return
	 * @throws SystemException
	 * Added for CRQ ICRD-103713 by A-5526
	 */
 public static String findUpuCodeNameForPA(String companyCode,
            String paCode)
throws SystemException {
try {
return constructDAO().findUpuCodeNameForPA(companyCode, paCode);
} catch(PersistenceException e) {
throw new SystemException(e.getErrorCode(), e);
}
}

 
	 public static String findDensityfactorForPA(String companyCode,
	            String paCode)
	throws SystemException {
	try {
	return constructDAO().findDensityfactorForPA(companyCode, paCode);
	} catch(PersistenceException e) {
	throw new SystemException(e.getErrorCode(), e);
	}
	}

}
