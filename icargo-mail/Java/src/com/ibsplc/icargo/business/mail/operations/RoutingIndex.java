/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.RoutingIndex.java
 *
 *	Created by	:	A-7531
 *	Created on	:	31-Aug-2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
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

import com.ibsplc.icargo.business.mail.operations.vo.RoutingIndexLegVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingIndexVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;

import com.ibsplc.xibase.server.framework.persistence.PersistenceController;

import com.ibsplc.xibase.server.framework.persistence.keygen.provider.Criterion;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.RoutingIndex.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7531	:	31-Aug-2018	:	Draft
 */

@Entity
@Table(name = "MALRTGIDX")
public class RoutingIndex {
	
	private static final String MODULE_NAME = "mail.operations";

	private static final String KEY_ROUTEINDEXSEQNUM_INTERCHANGE =
			"ROUTEINDEXSEQNUM_INTERCHANGE";
	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	
	private String clsOutTim;
	private int dayCount;
	private String contractType;
	private String destination;
	private int equitableTender;
	private String finalDlvTime;
	private String hazardousIndicator;
	private Calendar lastUpdateTime;
	private String lastUpdateUser;
	private String mailClass;
	private int mailCost;
	private double maxWgt;
	private double minWgt;
	private int onetimePercent;
	private String origin;
	private String perishableIndicator;
	private Calendar plannedDisDate;
	private Calendar plannedEffectiveDate;
	private String planRouteGenInd;
	private String planRouteScnInd;
	private String priorityCode;
	private int tagIndex;
	private String wgtUnit;
	private String dayOfOperation;
	private Set<RoutingIndexLeg>  routingIndexLeg; 

	private RoutingIndexPK routingIndexPK;
	
	/**
	 * 	Getter for clsOutTim 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	 @Column(name="CLSOUTTIM")
	public String getClsOutTim() {
		return clsOutTim;
	}
	/**
	 *  @param clsOutTim the clsOutTim to set
	 * 	Setter for clsOutTim 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setClsOutTim(String clsOutTim) {
		this.clsOutTim = clsOutTim;
	}
	
	/**
	 * 	Getter for dayCount 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	
	 @Column(name="DAYCNT")
	public int getDayCount() {
		return dayCount;
	}
	/**
	 *  @param dayCount the dayCount to set
	 * 	Setter for dayCount 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setDayCount(int dayCount) {
		this.dayCount = dayCount;
	}
	/**
	 * 	Getter for dayOfOperation 
	 *	Added by : A-7531 on 03-Jan-2019
	 * 	Used for :
	 */
	 @Column(name="DAYOFOPR")
	public String getDayOfOperation() {
		return dayOfOperation;
	}
	/**
	 *  @param dayOfOperation the dayOfOperation to set
	 * 	Setter for dayOfOperation 
	 *	Added by : A-7531 on 03-Jan-2019
	 * 	Used for :
	 */
	public void setDayOfOperation(String dayOfOperation) {
		this.dayOfOperation = dayOfOperation;
	}
	/**
	 * 	Getter for contractType 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	 @Column(name="CTRTYP")
	public String getContractType() {
		return contractType;
	}
	/**
	 *  @param contractType the contractType to set
	 * 	Setter for contractType 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
	/**
	 * 	Getter for destination 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	 @Column(name="DSTCOD")
	public String getDestination() {
		return destination;
	}
	/**
	 *  @param destination the destination to set
	 * 	Setter for destination 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}
	/**
	 * 	Getter for equitableTender 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	 @Column(name="EQUTEN")
	public int getEquitableTender() {
		return equitableTender;
	}
	/**
	 *  @param equitableTender the equitableTender to set
	 * 	Setter for equitableTender 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setEquitableTender(int equitableTender) {
		this.equitableTender = equitableTender;
	}
	/**
	 * 	Getter for finalDlvTime 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	 @Column(name="FNLDLVTIM")
	public String getFinalDlvTime() {
		return finalDlvTime;
	}
	/**
	 *  @param finalDlvTime the finalDlvTime to set
	 * 	Setter for finalDlvTime 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setFinalDlvTime(String finalDlvTime) {
		this.finalDlvTime = finalDlvTime;
	}
	/**
	 * 	Getter for hazardousIndicator 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	 @Column(name="HAZIND")
	public String getHazardousIndicator() {
		return hazardousIndicator;
	}
	/**
	 *  @param hazardousIndicator the hazardousIndicator to set
	 * 	Setter for hazardousIndicator 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setHazardousIndicator(String hazardousIndicator) {
		this.hazardousIndicator = hazardousIndicator;
	}
	/**
	 * 	Getter for lastUpdateTime 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	 @Column(name="LSTUPDTIM")
	public Calendar getLastUpdateTime() {
		return lastUpdateTime;
	}
	/**
	 *  @param lastUpdateTime the lastUpdateTime to set
	 * 	Setter for lastUpdateTime 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setLastUpdateTime(Calendar lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	/**
	 * 	Getter for lastUpdateUser 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	 @Column(name="LSTUPDUSR")
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	/**
	 *  @param lastUpdateUser the lastUpdateUser to set
	 * 	Setter for lastUpdateUser 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
	/**
	 * 	Getter for mailClass 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	 @Column(name="MALCLS")
	public String getMailClass() {
		return mailClass;
	}
	/**
	 *  @param mailClass the mailClass to set
	 * 	Setter for mailClass 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setMailClass(String mailClass) {
		this.mailClass = mailClass;
	}
	/**
	 * 	Getter for mailCost 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	 @Column(name="MALCST")
	public int getMailCost() {
		return mailCost;
	}
	/**
	 *  @param mailCost the mailCost to set
	 * 	Setter for mailCost 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setMailCost(int mailCost) {
		this.mailCost = mailCost;
	}
	/**
	 * 	Getter for maxWgt 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	 @Column(name="MAXWGT")
	public double getMaxWgt() {
		return maxWgt;
	}
	/**
	 *  @param maxWgt the maxWgt to set
	 * 	Setter for maxWgt 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setMaxWgt(double maxWgt) {
		this.maxWgt = maxWgt;
	}
	/**
	 * 	Getter for minWgt 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	 @Column(name="MINWGT")
	public double getMinWgt() {
		return minWgt;
	}
	/**
	 *  @param minWgt the minWgt to set
	 * 	Setter for minWgt 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setMinWgt(double minWgt) {
		this.minWgt = minWgt;
	}
	/**
	 * 	Getter for onetimePercent 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	 @Column(name="ONETIMPER")
	public int getOnetimePercent() {
		return onetimePercent;
	}
	/**
	 *  @param onetimePercent the onetimePercent to set
	 * 	Setter for onetimePercent 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setOnetimePercent(int onetimePercent) {
		this.onetimePercent = onetimePercent;
	}
	/**
	 * 	Getter for origin 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	 @Column(name="ORGCOD")
	public String getOrigin() {
		return origin;
	}
	/**
	 *  @param origin the origin to set
	 * 	Setter for origin 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	/**
	 * 	Getter for perishableIndicator 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	 @Column(name="PERIND")
	public String getPerishableIndicator() {
		return perishableIndicator;
	}
	/**
	 *  @param perishableIndicator the perishableIndicator to set
	 * 	Setter for perishableIndicator 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setPerishableIndicator(String perishableIndicator) {
		this.perishableIndicator = perishableIndicator;
	}
	/**
	 * 	Getter for plannedDisDate 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	 @Column(name="PLDDISDAT")
	 public Calendar getPlannedDisDate() {
			return plannedDisDate;
		}
	/**
	 *  @param plannedDisDate the plannedDisDate to set
	 * 	Setter for plannedDisDate 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	 public void setPlannedDisDate(Calendar plannedDisDate) {
			this.plannedDisDate = plannedDisDate;
		}
	/**
	 * 	Getter for plannedEffectiveDate 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	 @Column(name="PLDEFFDAT")
	 public Calendar getPlannedEffectiveDate() {
			return plannedEffectiveDate;
		}
	/**
	 *  @param plannedEffectiveDate the plannedEffectiveDate to set
	 * 	Setter for plannedEffectiveDate 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	 public void setPlannedEffectiveDate(Calendar plannedEffectiveDate) {
			this.plannedEffectiveDate = plannedEffectiveDate;
		}
	/**
	 * 	Getter for planRouteGenInd 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	 @Column(name="PLNROUGENIND")
	public String getPlanRouteGenInd() {
		return planRouteGenInd;
	}
	/**
	 *  @param planRouteGenInd the planRouteGenInd to set
	 * 	Setter for planRouteGenInd 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setPlanRouteGenInd(String planRouteGenInd) {
		this.planRouteGenInd = planRouteGenInd;
	}
	/**
	 * 	Getter for planRouteScnInd 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	 @Column(name="PLNROUSCNIND")
	public String getPlanRouteScnInd() {
		return planRouteScnInd;
	}
	/**
	 *  @param planRouteScnInd the planRouteScnInd to set
	 * 	Setter for planRouteScnInd 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setPlanRouteScnInd(String planRouteScnInd) {
		this.planRouteScnInd = planRouteScnInd;
	}
	/**
	 * 	Getter for priorityCode 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	 @Column(name="PRICOD")
	public String getPriorityCode() {
		return priorityCode;
	}
	/**
	 *  @param priorityCode the priorityCode to set
	 * 	Setter for priorityCode 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setPriorityCode(String priorityCode) {
		this.priorityCode = priorityCode;
	}
	
	/**
	 * 	Getter for tagIndex 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	 @Column(name="TAGIDX")
	public int getTagIndex() {
		return tagIndex;
	}
	/**
	 *  @param tagIndex the tagIndex to set
	 * 	Setter for tagIndex 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setTagIndex(int tagIndex) {
		this.tagIndex = tagIndex;
	}
	/**
	 * 	Getter for wgtUnit 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	 @Column(name="WGTUNT")
	public String getWgtUnit() {
		return wgtUnit;
	}
	/**
	 *  @param wgtUnit the wgtUnit to set
	 * 	Setter for wgtUnit 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setWgtUnit(String wgtUnit) {
		this.wgtUnit = wgtUnit;
	}
	
	
	/**
	 * 
	 * 	Method		:	RoutingIndex.getRoutingIndexPK
	 *	Added by 	:	A-7531 on 11-Oct-2018
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	RoutingIndexPK
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "routingIndex", column = @Column(name = "RTGIDX")),
			@AttributeOverride(name = "routingSeqNum", column = @Column(name = "RTGSEQNUM")) })
	public RoutingIndexPK getRoutingIndexPK() {
		return routingIndexPK;
	}
	public void setRoutingIndexPK(RoutingIndexPK routingIndexPK) {
		this.routingIndexPK = routingIndexPK;
	}
	
	
	 
	 @OneToMany
	    @JoinColumns( {
	    @JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable=false, updatable=false),
	    @JoinColumn(name = "RTGIDX", referencedColumnName = "RTGIDX", insertable=false, updatable=false),
	    @JoinColumn(name = "RTGSEQNUM", referencedColumnName = "RTGSEQNUM", insertable=false, updatable=false)})
		public Set<RoutingIndexLeg> getRoutingIndexLeg() {
			return routingIndexLeg;
		}
		
		public void setRoutingIndexLeg(Set<RoutingIndexLeg> routingIndexLeg) {
			this.routingIndexLeg = routingIndexLeg;
		}
	 
	 
	 
	public RoutingIndex(){
		
	}
	
	
	/**
	 * 
	 *	Constructor	: 	@param routingIndexVO
	 *	Constructor	: 	@throws SystemException
	 *	Created by	:	A-7531
	 *	Created on	:	11-Oct-2018
	 */
    public RoutingIndex(RoutingIndexVO routingIndexVO) throws SystemException{
    	
    	generateSequencesForPlannedRouteIndex(routingIndexVO);
    	populatePK(routingIndexVO);
    	populateAttributes(routingIndexVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}
		populateChildTable(routingIndexVO);
		log.exiting(MODULE_NAME, "RoutingIndex");
    	
		
    }
    	
/**
	 * 	Method		:	RoutingIndex.populateChildTable
	 *	Added by 	:	A-7531 on 11-Oct-2018
	 * 	Used for 	:
	 *	Parameters	:	@param routingIndexVO 
	 *	Return type	: 	void
 * @throws SystemException 
	 */
	private void populateChildTable(RoutingIndexVO routingIndexVO) throws SystemException {
		
		Collection<RoutingIndexLegVO> routingIndexLegVOs = routingIndexVO.getRoutingIndexLegVO();
		if(routingIndexLegVOs!=null && routingIndexLegVOs.size()>0){

			Set<RoutingIndexLeg> routingIndexLeg = new HashSet<RoutingIndexLeg>();
			for(RoutingIndexLegVO routingIndexLegVO : routingIndexLegVOs){
				routingIndexLegVO.setCompanyCode(routingIndexVO.getCompanyCode());
				routingIndexLegVO.setRoutingIndex(routingIndexVO.getRoutingIndex());
				routingIndexLegVO.setRoutingSeqNum(routingIndexVO.getRoutingSeqNum());
				RoutingIndexLeg routingIndexLegDetail = new RoutingIndexLeg
						(routingIndexLegVO);
				routingIndexLeg.add(routingIndexLegDetail);

			}
			this.setRoutingIndexLeg(routingIndexLeg);
		}
		
	}
/**
 * 
 * 	Method		:	RoutingIndex.populatePK
 *	Added by 	:	A-7531 on 11-Oct-2018
 * 	Used for 	:
 *	Parameters	:	@param routingIndexVO 
 *	Return type	: 	void
 */
    private void populatePK(RoutingIndexVO routingIndexVO){

    	RoutingIndexPK routingIndexPK = new RoutingIndexPK();
    	routingIndexPK.setCompanyCode(routingIndexVO.getCompanyCode());
    	routingIndexPK.setRoutingIndex(routingIndexVO.getRoutingIndex());
    	routingIndexPK.setRoutingSeqNum(routingIndexVO.getRoutingSeqNum());

		this.routingIndexPK = routingIndexPK;
	}

    /**
     * 
     * 	Method		:	RoutingIndex.populateAttributes
     *	Added by 	:	A-7531 on 11-Oct-2018
     * 	Used for 	:
     *	Parameters	:	@param routingIndexVO 
     *	Return type	: 	void
     */
	private void populateAttributes(RoutingIndexVO routingIndexVO){
		this.setClsOutTim(routingIndexVO.getClsOutTim());
		this.setContractType(routingIndexVO.getContractType());
		this.setDayCount(routingIndexVO.getDayCount());
		this.setDestination(routingIndexVO.getDestination());
		this.setFinalDlvTime(routingIndexVO.getFinalDlvTime());
		this.setHazardousIndicator(routingIndexVO.getHazardousIndicator());
		this.setLastUpdateUser(routingIndexVO.getLastUpdateUser());
		this.setLastUpdateTime(routingIndexVO.getLastUpdateTime());
		this.setMailClass(routingIndexVO.getMailClass());
		this.setMailCost(routingIndexVO.getMailCost());
		this.setMaxWgt(routingIndexVO.getMaxWgt());
		this.setMinWgt(routingIndexVO.getMinWgt());
		this.setOrigin(routingIndexVO.getOrigin());
		this.setPerishableIndicator(routingIndexVO.getPerishableIndicator());
		this.setDayOfOperation(String.valueOf(routingIndexVO.getFrequency()));
		
		 DateFormat df = new SimpleDateFormat("dd-MM-yyyy"); 
		 Calendar calender = Calendar.getInstance();
		 Calendar calender1 = Calendar.getInstance();
		 Date date = null;
		 Date date1 =null;
		
		
		 if(routingIndexVO.getPlannedDisDate()!=null){
			 try {
				 date1 =df.parse(routingIndexVO.getPlannedDisDate());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 calender1.setTime(date1);
			 
		
		this.setPlannedDisDate(calender1);
		}
		

		if(routingIndexVO.getPlannedEffectiveDate()!=null){ 
			 try {
				 date =df.parse(routingIndexVO.getPlannedEffectiveDate());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			calender.setTime(date);
		this.setPlannedEffectiveDate(calender);
		}
		this.setPlanRouteGenInd(routingIndexVO.getPlanRouteGenInd());
		this.setPlanRouteScnInd(routingIndexVO.getPlanRouteScnInd());
		this.setPriorityCode(routingIndexVO.getPriorityCode());
		this.setEquitableTender(routingIndexVO.getEquitableTender());
		this.setWgtUnit(routingIndexVO.getWgtUnit());
		this.setTagIndex(routingIndexVO.getTagIndex());
		this.setOnetimePercent(routingIndexVO.getOnetimePercent());
	}
    
	/**
	 * 
	 * 	Method		:	RoutingIndex.generateSequencesForPlannedRouteIndex
	 *	Added by 	:	A-7531 on 03-Sep-2018
	 * 	Used for 	:
	 *	Parameters	:	@param routingIndexVO
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	private void generateSequencesForPlannedRouteIndex(RoutingIndexVO routingIndexVO)
			throws SystemException {
				log.entering("RoutingIndex", "generateSequencesForPlannedRouteIndex");
				String keyCode=routingIndexVO.getCompanyCode().concat(routingIndexVO.getRoutingIndex());
				Criterion interchangeCriterion = KeyUtils.getCriterion(
						routingIndexVO.getCompanyCode(),
						KEY_ROUTEINDEXSEQNUM_INTERCHANGE,keyCode);

				routingIndexVO.setRoutingSeqNum(
						Integer.parseInt(KeyUtils.getKey(interchangeCriterion)));

				log.exiting("RoutingIndex", "generateSequencesForPlannedRouteIndex");
				
		
			}
	/**
	 * 	Method		:	RoutingIndex.findRoutingDetails
	 *	Added by 	:	A-7531 on 11-Oct-2018
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode2
	 *	Parameters	:	@param routingIndex2
	 *	Parameters	:	@return 
	 *	Return type	: 	RoutingIndexVO
	 */
	/*public static RoutingIndex find(String companyCode, String routingIndex,int routingseqNum)throws SystemException ,FinderException {
	//doubt whether routing seqnumber shpuld be populated
		RoutingIndexPK routingIndexPK = new RoutingIndexPK();
		routingIndexPK.setCompanyCode(companyCode);
		routingIndexPK.setRoutingIndex(routingIndex);
		routingIndexPK.setRoutingSeqNum(routingseqNum);
			EntityManager em = PersistenceController.getEntityManager();
			return em.find(RoutingIndex.class, routingIndexPK);
			
	}*/
	
	
	
	


}
