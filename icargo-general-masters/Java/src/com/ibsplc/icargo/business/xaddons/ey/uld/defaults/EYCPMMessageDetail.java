package com.ibsplc.icargo.business.xaddons.ey.uld.defaults;

import com.ibsplc.icargo.business.msgbroker.message.vo.xaddons.ey.uld.defaults.CPMBulkFlightDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.OptimisticConcurrencyException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import java.util.Calendar;

/**
 *	Java file	: 	com.ibsplc.icargo.business.xaddons.ey.uld.defaults.EYCPMMessageDetailPK.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7765	:	22-Jul-2020	    :	Draft
 */

@Staleable
@Entity
@Table(name = "XDNULDCPMMSGDTL")
public class EYCPMMessageDetail {

	private static final String MODULE_NAME = "XADDONS.EYULD.DEFAULTS";
	private static final Log LOG = LogFactory.getLogger(MODULE_NAME);
	private static final String CLASS_NAME = "EYCPMMessageDetail";

	private EYCPMMessageDetailPK eyCpmMessageDetailPK;
	private String lastUpdatedUser;
	private Calendar lastUpdatedTime;
	private String location;
	private String pointOfUnloading;
	private double uldWeight;
	private String uldNumber;

	public EYCPMMessageDetail() {}

	@Column(name = "LSTUPDUSR")
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}

	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}

	@Version
	@Column(name = "LSTUPDTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	public void setLastUpdatedTime(Calendar lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	@Column(name = "LOC")
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Column(name = "POU")
	public String getPointOfUnloading() {
		return pointOfUnloading;
	}

	public void setPointOfUnloading(String pointOfUnloading) {
		this.pointOfUnloading = pointOfUnloading;
	}

	@Column(name = "OTHWGT")
	public double getUldWeight() {
		return uldWeight;
	}

	public void setUldWeight(double uldWeight) {
		this.uldWeight = uldWeight;
	}

	@Column(name = "ULDNUM")
	public String getUldNumber() {
		return uldNumber;
	}

	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}
	
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "stationCode", column = @Column(name = "ARPCOD")),
			@AttributeOverride(name = "legSerialNumber", column = @Column(name = "LEGSERNUM")),
			@AttributeOverride(name = "flightSequenceNumber", column = @Column(name = "FLTSEQNUM")),
			@AttributeOverride(name = "flightNumber", column = @Column(name = "FLTNUM")),
			@AttributeOverride(name = "flightCarrierId", column = @Column(name = "FLTCARIDR")),
			@AttributeOverride(name = "compartmentSection", column = @Column(name = "CMPSEC")),
			@AttributeOverride(name = "content", column = @Column(name = "CNT"))})
	public EYCPMMessageDetailPK getEYCPMMessageDetailPK() {
		return eyCpmMessageDetailPK;
	}

	public void setEYCPMMessageDetailPK(EYCPMMessageDetailPK eyCpmMessageDetailPK) {
		this.eyCpmMessageDetailPK = eyCpmMessageDetailPK;
	}

	public EYCPMMessageDetail(CPMBulkFlightDetailsVO cpmBulkFlightDetailsVO)
			throws SystemException, CreateException {
		populatePK(cpmBulkFlightDetailsVO);
		populateAttributes(cpmBulkFlightDetailsVO);
		PersistenceController.getEntityManager().persist(this);
	}

	private void populatePK(CPMBulkFlightDetailsVO cpmBulkFlightDetailsVO) {
		LOG.entering(CLASS_NAME, "populatePK");
		setEYCPMMessageDetailPK(new EYCPMMessageDetailPK(cpmBulkFlightDetailsVO));
		LOG.exiting(CLASS_NAME, "populatePK");
	}

	private void populateAttributes(CPMBulkFlightDetailsVO cpmBulkFlightDetailsVO) throws SystemException {
		LOG.entering(CLASS_NAME, "populateAttributes");
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		setLastUpdatedUser(logonAttributes.getUserId());
		if(cpmBulkFlightDetailsVO.getUldNumber()!=null && !cpmBulkFlightDetailsVO.getUldNumber().isEmpty()){
			setUldNumber(cpmBulkFlightDetailsVO.getUldNumber());
			setUldWeight(cpmBulkFlightDetailsVO.getUldWeight());
		}else{
		setUldWeight(cpmBulkFlightDetailsVO.getBulkWeight());
		}
		setLocation(cpmBulkFlightDetailsVO.getStationCode());
		setPointOfUnloading(cpmBulkFlightDetailsVO.getBulkDestination());
		setLastUpdatedTime(cpmBulkFlightDetailsVO.getLastUpdatedTime());
		LOG.exiting(CLASS_NAME, "populateAttributes");
	}

	public static EYCPMMessageDetail find(EYCPMMessageDetailPK eyCpmMessageDetailPK)
			throws SystemException, FinderException {
		return PersistenceController.getEntityManager().find(EYCPMMessageDetail.class, eyCpmMessageDetailPK);
	}

	public void remove() throws SystemException {
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException e) {
			LOG.log(Log.SEVERE, "RemoveException", e);
			throw new SystemException(e.getErrorCode());
		} catch (OptimisticConcurrencyException e) {
			LOG.log(Log.SEVERE, "OptimisticConcurrencyException", e);
			throw new SystemException(e.getMessage());
		}
	}
}
