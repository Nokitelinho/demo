/**
 * 
 */
package com.ibsplc.icargo.business.mail.operations;


import java.util.Calendar;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.ibsplc.icargo.business.mail.operations.vo.MailServiceStandardFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailServiceStandardVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-8149
 *
 */

@Entity
@Table(name = "MALSRVSTDCFG")
@Staleable
public class MailServiceStandard {

	private static final String MODULE = "mail.operations";
	private MailServiceStandardPK mailServiceStandardPK;
	private String scanningWaived;
	private int serviceStandard;
	private String contractID;
	private int tagIndex;
	//Added by A-7929 as part of IASCB-35577
	private Calendar lastUpdateTime;
	private String lastUpdateUser;
	
	public MailServiceStandard(){
		
	}
	
	public MailServiceStandard(MailServiceStandardVO mailServiceStandardVO)throws SystemException {
		populatePK(mailServiceStandardVO);
		populateAtrributes(mailServiceStandardVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
	}
	
	private void populatePK(MailServiceStandardVO mailServiceStandardVO) {
		MailServiceStandardPK mailServiceStandardPK = new MailServiceStandardPK();
		mailServiceStandardPK.setCompanyCode(mailServiceStandardVO.getCompanyCode());
		mailServiceStandardPK.setGpaCode(mailServiceStandardVO.getGpaCode());
		mailServiceStandardPK.setOriginCode(mailServiceStandardVO.getOriginCode());
		mailServiceStandardPK.setDestCode(mailServiceStandardVO.getDestinationCode());
		mailServiceStandardPK.setServiceLevel(mailServiceStandardVO.getServicelevel());
		this.setMailServiceStandardPK(mailServiceStandardPK);
	}
	
	private void populateAtrributes(MailServiceStandardVO mailServiceStandardVO)
			throws SystemException {
		
		this.setScanningWaived(mailServiceStandardVO.getScanWaived());
		this.setServiceStandard(Integer.parseInt(mailServiceStandardVO.getServicestandard()));
		this.setContractID(mailServiceStandardVO.getContractid());
		//Added by A-7929 as part of IASCB-35577
		this.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		this.setLastUpdateUser(mailServiceStandardVO.getLastUpdateUser());
		this.setTagIndex(0);
		
	}
	
	public static MailServiceStandard find(MailServiceStandardPK mailServiceStandardPK)
			throws SystemException, FinderException {
		EntityManager em = PersistenceController.getEntityManager();
		return em.find(MailServiceStandard.class, mailServiceStandardPK);
	}
	
	public void update(MailServiceStandardVO mailServiceStandardVO)
			throws SystemException {
		populateAtrributes(mailServiceStandardVO);
	}
	
	public void remove() throws SystemException {
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			throw new SystemException(removeException.getErrorCode(),
					removeException);
		}
	}
	
	public static Page<MailServiceStandardVO> listServiceStandardDetails(
			MailServiceStandardFilterVO mailServiceStandardFilterVO,int pageNumeber)
			throws SystemException {
		try {
			return constructDAO().listServiceStandardDetails(mailServiceStandardFilterVO,pageNumeber);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	
	public static MailTrackingDefaultsDAO constructDAO() throws SystemException {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MailTrackingDefaultsDAO.class.cast(em.getQueryDAO(MODULE));
		} catch (PersistenceException ex) {
			ex.getErrorCode();
			throw new SystemException(ex.getMessage());
		}
	}
	
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "gpaCode", column = @Column(name = "GPACOD")),
			@AttributeOverride(name = "originCode", column = @Column(name = "ORGCOD")), 
			@AttributeOverride(name = "destCode", column = @Column(name = "DSTCOD")),
			@AttributeOverride(name = "serviceLevel", column = @Column(name = "SRVLVL")) })
	public MailServiceStandardPK getMailServiceStandardPK() {
		return mailServiceStandardPK;
	}
	public void setMailServiceStandardPK(MailServiceStandardPK mailServiceStandardPK) {
		this.mailServiceStandardPK = mailServiceStandardPK;
	}
	
	@Column(name = "SCNWVDFLG")
	public String getScanningWaived() {
		return scanningWaived;
	}
	public void setScanningWaived(String scanningWaived) {
		this.scanningWaived = scanningWaived;
	}
	
	@Column(name = "SRVSTD")
	public int getServiceStandard() {
		return serviceStandard;
	}
	public void setServiceStandard(int serviceStandard) {
		this.serviceStandard = serviceStandard;
	}
	
	@Column(name = "CTRIDR")
	public String getContractID() {
		return contractID;
	}
	public void setContractID(String contractID) {
		this.contractID = contractID;
	}
	
	@Column(name = "TAGIDX")
	public int getTagIndex() {
		return tagIndex;
	}
	public void setTagIndex(int tagIndex) {
		this.tagIndex = tagIndex;
	}
	
	//Added by A-7929 as part of IASCB-35577
	@Version
	 @Column(name = "LSTUPDTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Calendar lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

@Column(name = "LSTUPDUSR")
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
	
	
	
}
