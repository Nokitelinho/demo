/*
 * DSN.java Created on Jun 27, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;




import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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

import com.ibsplc.icargo.business.mail.operations.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingIndexVO;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.audit.Audit;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.keygen.provider.Criterion;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-5991 This class Master for a DSN . Total bags /Wt for a DSN is
 *         stored , whether it is PLT enabled or disabled. It has summary
 *         information for a DSN at an airport.
 */
@Entity
@Table(name = "MALDSNMST")
public class DSN {

	private Log log = LogFactory.getLogger("MAIL_OPERATIONS");

	private static final String MODULE = "mail.operations";
	
	private static final String HYPHEN = "-";
	private static final String USPS_INTERNATIONAL_PA="mailtracking.defaults.uspsinternationalpa";
	private static final String USPS_DOMESTIC_PA = "mailtracking.domesticmra.usps";
	private static final String DESPATCH_SERIAL_NUMBER="0001";
	private static final String RECEPTACLE_SERIAL_NUMBER="001";

	 private Map<String,String> exchangeOfficeMap;




	private int bagCount;

	private double weight;

	private Set<Mailbag> mailbags;

	private String pltEnableFlag;

	private String mailClass;

	private Calendar lastUpdateTime;

	private String lastUpdateUser;


	/**
	 * @return Returns the bags.
	 */
	@Column(name = "BAGCNT")
	@Audit(name = "bagCount")
	public int getBagCount() {
		return bagCount;
	}

	/**
	 * @param bags
	 *            The bags to set.
	 */
	public void setBagCount(int bags) {
		this.bagCount = bags;
	}

	/**
	 * @return Returns the dsnPk.
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "dsn", column = @Column(name = "DSN")),
			@AttributeOverride(name = "originExchangeOffice", column = @Column(name = "ORGEXGOFC")),
			@AttributeOverride(name = "destinationExchangeOffice", column = @Column(name = "DSTEXGOFC")),
			@AttributeOverride(name = "mailCategoryCode", column = @Column(name = "MALCTGCOD")),
			@AttributeOverride(name = "mailSubclass", column = @Column(name = "MALSUBCLS")),
			@AttributeOverride(name = "year", column = @Column(name = "YER")) })



	/**
	 * @return Returns the mailbags.
	 */
	@OneToMany
	@JoinColumns( {
			@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = true),
			@JoinColumn(name = "DSN", referencedColumnName = "DSN", insertable = false, updatable = true),
			@JoinColumn(name = "ORGEXGOFC", referencedColumnName = "ORGEXGOFC", insertable = false, updatable = true),
			@JoinColumn(name = "DSTEXGOFC", referencedColumnName = "DSTEXGOFC", insertable = false, updatable = true),
			@JoinColumn(name = "MALCTG", referencedColumnName = "MALCTGCOD", insertable = false, updatable = true),
			@JoinColumn(name = "MALSUBCLS", referencedColumnName = "MALSUBCLS", insertable = false, updatable = true),
			@JoinColumn(name = "YER", referencedColumnName = "YER", insertable = false, updatable = true) })
	public Set<Mailbag> getMailbags() {
		return mailbags;
	}

	/**
	 * @param mailbags
	 *            The mailbags to set.
	 */
	public void setMailbags(Set<Mailbag> mailbags) {
		this.mailbags = mailbags;
	}

	/**
	 * @return Returns the pltEnableFlag.
	 */
	@Column(name = "PLTENBFLG")
	@Audit(name = "pltEnableFlag")
	public String getPltEnableFlag() {
		return pltEnableFlag;
	}

	/**
	 * @param pltEnableFlag
	 *            The pltEnableFlag to set.
	 */
	public void setPltEnableFlag(String pltEnableFlag) {
		this.pltEnableFlag = pltEnableFlag;
	}

	/**
	 * @return Returns the weight.
	 */
	@Column(name = "BAGWGT")
	@Audit(name = "weight")
	public double getWeight() {
		return weight;
	}

	/**
	 * @param weight
	 *            The weight to set.
	 */
	public void setWeight(double weight) {
		this.weight = weight;
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
	 * @return Returns the mailClass.
	 */
     @Column(name="MALCLS")
	public String getMailClass() {
		return mailClass;
	}

	/**
	 * @param mailClass The mailClass to set.
	 */
	public void setMailClass(String mailClass) {
		this.mailClass = mailClass;
	}
	
	
	public DSN() {
	}

	public DSN(DSNVO dsnVO) throws SystemException {
		log.entering("DSN", "init");
		populateAttributes(dsnVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getMessage(),
					createException);
		}
		
		log.exiting("DSN", "init");
	}



	/**
	 * A-5991
	 *
	 * @param dsnVO
	 */
	private void populateAttributes(DSNVO dsnVO) {
		setPltEnableFlag(dsnVO.getPltEnableFlag());
		//setWeight(dsnVO.getWeight());
		if(dsnVO.getWeight()!=null){
			setWeight(dsnVO.getWeight().getSystemValue());//added by A-7371
			}
		setBagCount(dsnVO.getBags());
		setMailClass(dsnVO.getMailClass());
	}

	/**
	 *
	 * @return MailTrackingDefaultsDAO
	 * @throws SystemException
	 */
	private static MailTrackingDefaultsDAO constructDAO()
			throws SystemException {
		MailTrackingDefaultsDAO mailTrackingDefaultsDAO = null;
		try {
			EntityManager em = PersistenceController.getEntityManager();
			mailTrackingDefaultsDAO = MailTrackingDefaultsDAO.class.cast(em
					.getQueryDAO(MODULE));
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
		return mailTrackingDefaultsDAO;
	}

	/**
	 * A-5991
	 *
	 * @throws SystemException
	 */
	public void remove() throws SystemException {
	
		if(this.getMailbags()!=null){
			for(Mailbag mailbag : this.getMailbags()){
				mailbag.remove();
			}
		}
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}

	}

	/**
	 * @author A-1739
	 * @param dsnVO
	 * @throws SystemException
	 * @throws DuplicateMailBagsException
	 */
	public void saveArrivalDetails(DSNVO dsnVO)
	throws SystemException, DuplicateMailBagsException {
		log.entering("DSN", "saveArrivalDetails");
		//mail revamp
		/*if (OPERATION_FLAG_UPDATE.equals(dsnVO.getOperationFlag())) {
			// the DSNARP for INSERT will be created by populateChildren of DSN
			for (DSNAtAirportVO dsnAtAirportVO : dsnVO.getDsnAtAirports()) {
				updateDSNAtAirportForArrival(dsnAtAirportVO);
			}
		}*/
		Collection<MailbagVO> mailbagVOs = dsnVO.getMailbags();
		if (mailbagVOs != null && mailbagVOs.size() > 0) {
			if (getMailbags() == null) {
				mailbags = new HashSet<Mailbag>();
			}
			for (MailbagVO mailbagVO : mailbagVOs) {
				if (mailbagVO.getOperationalFlag() != null) {
					 boolean isNew = false;
					 Mailbag mailbag = null;
					//Modified for ICRD-126626
					 try {
						mailbag = findMailbag(constructMailbagPK(mailbagVO));
					} catch (FinderException e) {
						log.log(Log.FINE,"FinderException");
					 }
					 if (mailbag==null) {
						  isNew = true;
					 }
					if (OPERATION_FLAG_INSERT.equals(
							mailbagVO.getOperationalFlag())) {
							if(!isNew) {
								if(!(MailConstantsVO.MAIL_STATUS_NEW.equals(mailbag.getLatestStatus())) && MailConstantsVO.OPERATION_INBOUND.equals(
										mailbag.getOperationalStatus()) && 
										mailbag.getScannedPort().equals(
													mailbagVO.getScannedPort())) {
									throw new DuplicateMailBagsException(
											DuplicateMailBagsException.
											DUPLICATEMAILBAGS_EXCEPTION,
											new Object[] { mailbagVO.getMailbagId() });
								}
							} else {
								
								String paCode=findSystemParameterValue(USPS_DOMESTIC_PA);
								if(mailbagVO.getMailbagId().length()==12 ||mailbagVO.getMailbagId().length()==10 && paCode.equals(mailbagVO.getPaCode())){
									String routIndex=mailbagVO.getMailbagId().substring(4,8);
									
									
									Collection<RoutingIndexVO> routingIndexVOs=new ArrayList <RoutingIndexVO>();
									RoutingIndexVO routingIndexFilterVO=new RoutingIndexVO();
									routingIndexFilterVO.setCompanyCode(mailbagVO.getCompanyCode());
									routingIndexFilterVO.setRoutingIndex(routIndex);
									routingIndexFilterVO.setScannedDate(mailbagVO.getScannedDate());
									routingIndexVOs=findRoutingIndex(routingIndexFilterVO);
									
									if(routingIndexVOs.size()>0){
										constructRouteIndexMailbagID(routingIndexVOs,mailbagVO);
									}
									
								}
								//Added for ICRD-243469 starts
								/*String serviceLevel = null;
								serviceLevel = findMailServiceLevel(mailbagVO);
								
								if(serviceLevel!=null){
									mailbagVO.setMailServiceLevel(serviceLevel);
								}*/
								//Added for ICRD-243469 ends
								//Added by A-7794 as part of ICRD-232299
								String scanWaved = constructDAO().checkScanningWavedDest(mailbagVO);
								if(scanWaved != null){
									mailbagVO.setScanningWavedFlag(scanWaved);
								}
								//ICRD-341146 Begin 
								if(new MailController().isUSPSMailbag(mailbagVO)){
									mailbagVO.setOnTimeDelivery(MailConstantsVO.FLAG_NO);
								}else{
									mailbagVO.setOnTimeDelivery(MailConstantsVO.FLAG_NOT_AVAILABLE);
								}
								//ICRD-341146 End
								
								mailbagVO.setMailbagDataSource(mailbagVO.getLatestStatus());
								MailController.calculateAndUpdateLatestAcceptanceTime(mailbagVO);
								mailbag = new Mailbag(mailbagVO);
								 mailbags.add(mailbag);
							}
					}
					/*
					 * Added By Karthick V 
					 * Under  the Assunption that if the lastUpdateTime is not there in the MailBag 
					 * Its a New MailBag being Arrived at the Port (Found MailBag ) so simple Insert happens
					 * else the MailBag is  actually getting Updated.
					 */
					if(mailbagVO.getLastUpdateTime()!=null && ! OPERATION_FLAG_INSERT.equals(
							mailbagVO.getOperationalFlag())){
						mailbag.setLastUpdateTime(mailbagVO.getLastUpdateTime().toCalendar());
					}
					if(mailbag != null){
						try{
						mailbag.updateArrivalDetails(mailbagVO);
						}catch(Exception exception){
							log.log(Log.FINE,"Exception in MailController at initiateArrivalForFlights for Offline *Flight* with Mailbag "+mailbagVO);
							continue;
						}
						/*
						 * Updating Consignment Details for mailbag 
						 */
						DocumentController docController = new DocumentController();
						MailInConsignmentVO mailInConsignmentVO = null;
						mailInConsignmentVO = docController
								.findConsignmentDetailsForMailbag(mailbagVO
										.getCompanyCode(), mailbagVO
										.getMailbagId(),null);
						if (mailInConsignmentVO != null) {
							mailbag.setConsignmentNumber(mailInConsignmentVO.getConsignmentNumber());
							mailbag.setConsignmentSequenceNumber(mailInConsignmentVO.getConsignmentSequenceNumber());
							mailbag.setPaCode(mailInConsignmentVO.getPaCode());
						}
					}
//					if(isNew) {
//						//audit for created mailbag
//						performMailbagAudit(mailbagAuditVO, mailbag,
//								AuditVO.CREATE_ACTION, null);
//					} else {
//						//audit for updated mailbag
//						performMailbagAudit(mailbagAuditVO, mailbag,
//								AuditVO.UPDATE_ACTION, null);
//					}
				}
			}
		}
		log.exiting("DSN", "saveArrivalDetails");
	}
	/**
	 * @author a-1936 This method is used to construct the MailkBagPK
	 * @param mailbagVO
	 * @return
	 */
	private MailbagPK constructMailbagPK(MailbagVO mailbagVO) {
		MailbagPK mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode(   mailbagVO.getCompanyCode());
		mailbagPK.setMailSequenceNumber(findMailSequenceNumber(mailbagVO.getMailbagId(),mailbagVO.getCompanyCode()));
		return mailbagPK;
	}
	/**
	 * A-1739
	 *
	 * @param dsnAtAirportVO
	 * @throws SystemException
	 */
	//mail revamp
	/*private void updateDSNAtAirportForArrival(DSNAtAirportVO dsnAtAirportVO)
			throws SystemException {
		log.entering("DSN", "updateDSNAtAirportForArrival");
		try {
			DSNAtAirport dsnAtArp = DSNAtAirport
					.find(constructDSNArpPK(dsnAtAirportVO));
			dsnAtArp.updateArrivalDetails(dsnAtAirportVO);
		} catch (FinderException exception) {
			// arrived airport will be a different one
			if (getDsnAtAirports() == null) {
				dsnAtAirports = new HashSet<DSNAtAirport>();
			}
			dsnAtAirports.add(new DSNAtAirport(getDsnPk(), dsnAtAirportVO));
		}
		log.exiting("DSN", "updateDSNAtAirportForArrival");
	}*/
	/**
	 * A-1739
	 *
	 * @param dsnAtArpVO
	 * @return
	 */
	//mail revamp
	/*private DSNAtAirportPK constructDSNArpPK(DSNAtAirportVO dsnAtArpVO) {
		dsnPk = getDsnPk();
		DSNAtAirportPK dsnArpPK = new DSNAtAirportPK();
		dsnArpPK.setCompanyCode(   dsnPk.getCompanyCode());
		dsnArpPK.setDsn( dsnPk.getDsn());
		dsnArpPK.setOriginOfficeOfExchange(   dsnPk.getOriginExchangeOffice());
		dsnArpPK.setDestinationOfficeOfExchange(   dsnPk.getDestinationExchangeOffice());
		//Added to Include the DSN PK
		dsnArpPK.setMailSubclass(   dsnPk.getMailSubclass());
		dsnArpPK.setMailCategoryCode( dsnPk.getMailCategoryCode());
		dsnArpPK.setYear(   dsnPk.getYear());
		dsnArpPK.setAirportCode(   dsnAtArpVO.getAirportCode());
		//Added By Karthick V to include the Mail Class tat has to be
		//Used wenever a new DSNARP is inserted or Updated ...
		dsnAtArpVO.setMailClass(dsnArpPK.getMailSubclass() .substring(0,1));
		return dsnArpPK;
	}*/
	/**
	 * @author A-1739
	 * @param mailbagPK
	 * @return
	 * @throws FinderException
	 * @throws SystemException
	 */
	public static Mailbag findMailbag(MailbagPK mailbagPK)
			throws FinderException, SystemException {
		return Mailbag.find(mailbagPK);
	}
	/**
	 * @author A-5991	
	 * @param mailIdr
	 * @param companyCode
	 * @return
	 */
	private long findMailSequenceNumber(String mailIdr,String companyCode){
		try {
			return Mailbag.findMailBagSequenceNumberFromMailIdr(mailIdr, companyCode);
		} catch (SystemException e) {
			log.log(Log.FINE,"SystemException at findMailBagSequenceNumberFromMailIdr");
		}
		return 0;
	}
		
		
		
		
			
			
				
			
			
			
			
			
			
		
	
	private String findSystemParameterValue(String syspar)
			throws SystemException {
		String sysparValue = null;
		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add(syspar);
		HashMap<String, String> systemParameterMap = Proxy.getInstance().get(SharedDefaultsProxy.class)
				.findSystemParameterByCodes(systemParameters);
		log.log(Log.FINE, " systemParameterMap ", systemParameterMap);
		if (systemParameterMap != null) {
			sysparValue = systemParameterMap.get(syspar);
		}
		return sysparValue;
	}
	
	
	public static Collection<RoutingIndexVO> findRoutingIndex(RoutingIndexVO routingIndexVO) throws SystemException {
		Collection<RoutingIndexVO> routingIndexVOs = null;
			try {
				routingIndexVOs = constructDAO().findRoutingIndex(
						routingIndexVO);
			} catch (PersistenceException ex) {
				throw new SystemException(ex.getErrorCode(), ex);
			}
			return routingIndexVOs;
		}
	

	public Map<String,String> findOfficeOfExchangeForPA(String companyCode,
			String paCode) throws SystemException {
		log.entering(MODULE, "findOfficeOfExchangeForAirports");
		return new MailController().findOfficeOfExchangeForPA(companyCode,paCode);
	}
		
	
	private static String generateDespatchSerialNumber(String currentKey,MailbagVO maibagVO)
			throws SystemException {
			String key=null;
			StringBuilder keyCondition = new StringBuilder();
			keyCondition.append(maibagVO.getYear());
			Criterion criterion = KeyUtils.getCriterion(maibagVO.getCompanyCode(),
					"DOM_USPS_DSN", keyCondition.toString());
			//Code to be modified once framework issue is fixed
			key=KeyUtils.getKey(criterion);
			if(MailConstantsVO.FLAG_YES.equals(currentKey)&&
					!"1".equals(key)){
				key=String.valueOf(Long.parseLong(key)-1);
				KeyUtils.resetKey(criterion, key);
			}
		return checkLength(key, 4);
	}
	
private static String generateReceptacleSerialNumber(String dsn,MailbagVO maibagVO)
			throws SystemException {
			StringBuilder keyCondition = new StringBuilder();
			keyCondition.append(maibagVO.getYear())
            		.append(dsn);
			Criterion criterion = KeyUtils.getCriterion(maibagVO.getCompanyCode(),
					"DOM_USPS_RSN", keyCondition.toString());
			String rsn = checkLength(KeyUtils.getKey(criterion),3);
			
		return rsn;
	}

private static String checkLength(String key,int maxLength){
	String modifiedKey = null;
	StringBuilder buildKey = new StringBuilder();
	modifiedKey = new StringBuilder().append(key).toString();
	int keyLength = modifiedKey.length();
	if(modifiedKey.length() < maxLength){
		int diff = maxLength - keyLength;
		String val = null;
		for(int i=0;i< diff;i++){
			val = buildKey.append("0").toString();
		}
		modifiedKey = 	new StringBuilder().append(val).append(key).toString();
	}
	return modifiedKey;
}
	
public  MailbagVO constructRouteIndexMailbagID( Collection<RoutingIndexVO> routingIndexVOs,MailbagVO mailbagVO) throws SystemException {
	
	String org=null;
	String dest=null;
	
	exchangeOfficeMap=new HashMap<String,String>();
	for(RoutingIndexVO routingIndexVO:routingIndexVOs){
		if(routingIndexVO!=null&&routingIndexVO.getRoutingIndex()!=null){
			
			 org=routingIndexVO.getOrigin();
			 dest=routingIndexVO.getDestination();
			exchangeOfficeMap=findOfficeOfExchangeForPA(mailbagVO.getCompanyCode(),findSystemParameterValue(USPS_DOMESTIC_PA));
			if(exchangeOfficeMap!=null && !exchangeOfficeMap.isEmpty()){if(exchangeOfficeMap!=null && !exchangeOfficeMap.isEmpty()){
	    		if(exchangeOfficeMap.containsKey(org)){
    				
	    			mailbagVO.setOoe(exchangeOfficeMap.get(org));
	    		}
	    		if(exchangeOfficeMap.containsKey(dest)){
	    			mailbagVO.setDoe(exchangeOfficeMap.get(dest));
	    		}
	    	}
		
		mailbagVO.setMailCategoryCode("B");
		String mailClass=mailbagVO.getMailbagId().substring(3,4);
		mailbagVO.setMailClass(mailClass);
		mailbagVO.setMailSubclass(mailClass+"X");
		mailbagVO.setOrigin(org);
		mailbagVO.setDestination(dest);
		
		int lastTwoDigits = Calendar.getInstance().get(Calendar.YEAR) % 100;
		String lastDigitOfYear = String.valueOf(lastTwoDigits).substring(1,2);
		mailbagVO.setYear(Integer.parseInt(lastDigitOfYear));
		mailbagVO.setHighestNumberedReceptacle("9");
		mailbagVO.setRegisteredOrInsuredIndicator("9");
		/*String despacthNumber=generateDespatchSerialNumber(MailConstantsVO.FLAG_YES,mailbagVO);
		String rsn=generateReceptacleSerialNumber(despacthNumber,mailbagVO);
    	if(rsn.length()>3){
		
    		generateDespatchSerialNumber(MailConstantsVO.FLAG_NO,mailbagVO);
    		despacthNumber=(generateDespatchSerialNumber(MailConstantsVO.FLAG_YES,mailbagVO));
    		rsn=generateReceptacleSerialNumber(despacthNumber,mailbagVO);				
			
    	}*/
		
    	mailbagVO.setDespatchSerialNumber(DESPATCH_SERIAL_NUMBER);  
    	mailbagVO.setReceptacleSerialNumber(RECEPTACLE_SERIAL_NUMBER);
    	mailbagVO.setWeight(new Measure(UnitConstants.MAIL_WGT, Double.parseDouble(mailbagVO.getMailbagId().substring(10,12))));
    	mailbagVO.setAcceptedWeight(new Measure(UnitConstants.MAIL_WGT, Double.parseDouble(mailbagVO.getMailbagId().substring(10,12))));	
    	mailbagVO.setStrWeight(new Measure(UnitConstants.MAIL_WGT, Double.parseDouble(mailbagVO.getMailbagId().substring(10,12))));	
			}
	}
		}

	return mailbagVO;
}

	
}
	

