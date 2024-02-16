/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.RoutingIndexLeg.java
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

import java.util.Calendar;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.icargo.business.mail.operations.vo.RoutingIndexLegVO;

import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;

import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.server.framework.persistence.keygen.provider.Criterion;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.RoutingIndexLeg.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7531	:	31-Aug-2018	:	Draft
 */
@Entity
@Table(name = "MALRTGIDXLEG")
@Staleable
public class RoutingIndexLeg {
	
	private static final String MAIL_OPERATIONS = "mail.operations";
	private static final String KEY_ROUTEINDEXSERNUM_INTERCHANGE="ROUTEINDEXSERNUM_INTERCHANGE";

	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	
	
	private String legAirStopScanInd;
	private String legArvTime;
	private String legDepTime;
	private String legDlvTime;
	private String legDstn;
	private String legEqpmnt;
	private String legFlight;
	private int legStopNum;
	private String legOrg;
	private String legRoute;
	private String legTranportCode;
	private Calendar lastUpdateTime;
	private String lastUpdateUser;
	private int tagIndex;
	private RoutingIndexLegPK routingIndexLegPK;
	

	
	
	
	
	/**
	 * 	Getter for legAirStopScanInd 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	 @Column(name="LEGAIRSTPSCNIND")
	public String getLegAirStopScanInd() {
		return legAirStopScanInd;
	}
	/**
	 *  @param legAirStopScanInd the legAirStopScanInd to set
	 * 	Setter for legAirStopScanInd 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setLegAirStopScanInd(String legAirStopScanInd) {
		this.legAirStopScanInd = legAirStopScanInd;
	}
	/**
	 * 	Getter for legArvTime 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	 @Column(name="LEGARVTIM")
	public String getLegArvTime() {
		return legArvTime;
	}
	/**
	 *  @param legArvTime the legArvTime to set
	 * 	Setter for legArvTime 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setLegArvTime(String legArvTime) {
		this.legArvTime = legArvTime;
	}
	/**
	 * 	Getter for legDepTime 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	 @Column(name="LEGDEPTIM")
	public String getLegDepTime() {
		return legDepTime;
	}
	/**
	 *  @param legDepTime the legDepTime to set
	 * 	Setter for legDepTime 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setLegDepTime(String legDepTime) {
		this.legDepTime = legDepTime;
	}
	/**
	 * 	Getter for legDlvTime 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	 @Column(name="LEGDLVTIM")
	public String getLegDlvTime() {
		return legDlvTime;
	}
	/**
	 *  @param legDlvTime the legDlvTime to set
	 * 	Setter for legDlvTime 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setLegDlvTime(String legDlvTime) {
		this.legDlvTime = legDlvTime;
	}
	/**
	 * 	Getter for legDstn 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	 @Column(name="LEGDST")
	public String getLegDstn() {
		return legDstn;
	}
	/**
	 *  @param legDstn the legDstn to set
	 * 	Setter for legDstn 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setLegDstn(String legDstn) {
		this.legDstn = legDstn;
	}
	/**
	 * 	Getter for legEqpmnt 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	 @Column(name="LEGEQP")
	public String getLegEqpmnt() {
		return legEqpmnt;
	}
	/**
	 *  @param legEqpmnt the legEqpmnt to set
	 * 	Setter for legEqpmnt 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setLegEqpmnt(String legEqpmnt) {
		this.legEqpmnt = legEqpmnt;
	}
	/**
	 * 	Getter for legFlight 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	 @Column(name="LEGFLT")
	public String getLegFlight() {
		return legFlight;
	}
	/**
	 *  @param legFlight the legFlight to set
	 * 	Setter for legFlight 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setLegFlight(String legFlight) {
		this.legFlight = legFlight;
	}
	/**
	 * 	Getter for legStopNum 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	 @Column(name="LEGNUMSTP")
	public int getLegStopNum() {
		return legStopNum;
	}
	/**
	 *  @param legStopNum the legStopNum to set
	 * 	Setter for legStopNum 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setLegStopNum(int legStopNum) {
		this.legStopNum = legStopNum;
	}
	/**
	 * 	Getter for legOrg 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	 @Column(name="LEGORG")
	public String getLegOrg() {
		return legOrg;
	}
	/**
	 *  @param legOrg the legOrg to set
	 * 	Setter for legOrg 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setLegOrg(String legOrg) {
		this.legOrg = legOrg;
	}
	/**
	 * 	Getter for legRoute 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	 @Column(name="LEGRTG")
	public String getLegRoute() {
		return legRoute;
	}
	/**
	 *  @param legRoute the legRoute to set
	 * 	Setter for legRoute 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setLegRoute(String legRoute) {
		this.legRoute = legRoute;
	}
	/**
	 * 	Getter for legTranportCode 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	 @Column(name="LEGTSPCOD")
	public String getLegTranportCode() {
		return legTranportCode;
	}
	/**
	 *  @param legTranportCode the legTranportCode to set
	 * 	Setter for legTranportCode 
	 *	Added by : A-7531 on 31-Aug-2018
	 * 	Used for :
	 */
	public void setLegTranportCode(String legTranportCode) {
		this.legTranportCode = legTranportCode;
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
	
	
	
	
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "routingIndex", column = @Column(name = "RTGIDX")),
			@AttributeOverride(name = "routingSeqNum", column = @Column(name = "RTGSEQNUM")), 
			@AttributeOverride(name = "routingSerNum", column = @Column(name = "RTGSERNUM")) })
	public RoutingIndexLegPK getRoutingIndexLegPK() {
		return routingIndexLegPK;
	}
	public void setRoutingIndexLegPK(RoutingIndexLegPK routingIndexLegPK) {
		this.routingIndexLegPK = routingIndexLegPK;
	}
	
	
public RoutingIndexLeg(){
		
	}

/**
 * 
 *	Constructor	: 	@param routingIndexLegVO
 *	Constructor	: 	@throws SystemException
 *	Created by	:	A-7531
 *	Created on	:	12-Oct-2018
 */
public RoutingIndexLeg(RoutingIndexLegVO routingIndexLegVO) throws SystemException{
	generateSernumForPlannedRouteIndex(routingIndexLegVO);
	populatePK(routingIndexLegVO);
	populateAttributes(routingIndexLegVO);
	try {	

		PersistenceController.getEntityManager().persist(this);
	} catch (CreateException createException) {			
		throw new SystemException(createException.getErrorCode());
	}
	
}

/**
 * 
 * 	Method		:	RoutingIndexLeg.populatePK
 *	Added by 	:	A-7531 on 12-Oct-2018
 * 	Used for 	:
 *	Parameters	:	@param routingIndexLegVO 
 *	Return type	: 	void
 * @throws SystemException 
 */
private void populatePK(RoutingIndexLegVO routingIndexLegVO) throws SystemException{

	RoutingIndexLegPK routingIndexLegPK = new RoutingIndexLegPK();
	routingIndexLegPK.setCompanyCode(routingIndexLegVO.getCompanyCode());
	routingIndexLegPK.setRoutingIndex(routingIndexLegVO.getRoutingIndex());
	routingIndexLegPK.setRoutingSeqNum(routingIndexLegVO.getRoutingSeqNum());
	routingIndexLegPK.setRoutingSerNum(routingIndexLegVO.getRoutingSerNum());


	this.routingIndexLegPK = routingIndexLegPK;
}
	
/**
 * 
 * 	Method		:	RoutingIndexLeg.populateAttributes
 *	Added by 	:	A-7531 on 12-Oct-2018
 * 	Used for 	:
 *	Parameters	:	@param routingIndexLegVO 
 *	Return type	: 	void
 */
	
private void populateAttributes(RoutingIndexLegVO routingIndexLegVO){
	this.setLegAirStopScanInd(routingIndexLegVO.getLegAirStopScanInd());
	this.setLegArvTime(routingIndexLegVO.getLegArvTime());
	this.setLegDepTime(routingIndexLegVO.getLegDepTime());
	this.setLegDlvTime(routingIndexLegVO.getLegDlvTime());
	this.setLegDstn(routingIndexLegVO.getLegDstn());
	this.setLegEqpmnt(routingIndexLegVO.getLegEqpmnt());
	this.setLastUpdateUser(routingIndexLegVO.getLastUpdateUser());
	this.setLastUpdateTime(routingIndexLegVO.getLastUpdateTime());
	this.setLegFlight(routingIndexLegVO.getLegFlight());
	this.setLegStopNum(routingIndexLegVO.getLegStopNum());
	this.setLegOrg(routingIndexLegVO.getLegOrg());
	this.setLegRoute(routingIndexLegVO.getLegRoute());
	this.setLegTranportCode(routingIndexLegVO.getLegTranportCode());
	this.setTagIndex(routingIndexLegVO.getTagIndex());
}


private void generateSernumForPlannedRouteIndex(RoutingIndexLegVO routingIndexLegVO)
		throws SystemException {
			log.entering("RoutingIndexLeg", "generateSequencesForPlannedRouteIndex");
			String keyCode=routingIndexLegVO.getCompanyCode().concat(routingIndexLegVO.getRoutingIndex().concat(String.valueOf(routingIndexLegVO.getRoutingSeqNum())));
			Criterion interchangeCriterion = KeyUtils.getCriterion(
					routingIndexLegVO.getCompanyCode(),
					KEY_ROUTEINDEXSERNUM_INTERCHANGE,keyCode);

			routingIndexLegVO.setRoutingSerNum(
					Integer.parseInt(KeyUtils.getKey(interchangeCriterion)));

			log.exiting("RoutingIndex", "generateSequencesForPlannedRouteIndex");
			
	
		}

/*public static RoutingIndexLeg find(String companyCode, String routingIndex,int routingseqNum,int routingSerNum)throws SystemException ,FinderException {
//doubt whether routing seqnumber shpuld be populated
	RoutingIndexLegPK routingIndexLegPK = new RoutingIndexLegPK();
	routingIndexLegPK.setCompanyCode(companyCode);
	routingIndexLegPK.setRoutingIndex(routingIndex);
	routingIndexLegPK.setRoutingSeqNum(routingseqNum);
	routingIndexLegPK.setRoutingSerNum(routingSerNum);
		EntityManager em = PersistenceController.getEntityManager();
		return em.find(RoutingIndexLeg.class, routingIndexLegPK);
		
}*/

}
