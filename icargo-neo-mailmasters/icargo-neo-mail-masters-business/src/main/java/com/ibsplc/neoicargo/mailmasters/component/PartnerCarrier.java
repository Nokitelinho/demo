package com.ibsplc.neoicargo.mailmasters.component;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import javax.persistence.*;

import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mailmasters.component.proxy.SharedAirlineProxy;
import com.ibsplc.neoicargo.mailmasters.vo.PartnerCarrierVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.neoicargo.mailmasters.dao.MailMastersDAO;
import lombok.extern.slf4j.Slf4j;
import com.ibsplc.neoicargo.mailmasters.exception.SharedProxyException;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import lombok.Getter;
import lombok.Setter;

/** 
 * @author a-1303
 */
@Setter
@Getter
@Slf4j
@Entity
@IdClass(PartnerCarrierPK.class)
@Table(name = "MALPRTCAR")
public class PartnerCarrier  extends BaseEntity implements Serializable {

	@Id
	@Column(name = "OWNCARCOD")
	private String ownCarrierCode;
	@Id
	@Column(name = "PRTCARCOD")
	private String partnerCarrierCode;
	@Id
	@Column(name = "ARPCOD")
	private String airportCode;
	@Column(name = "PRTCARIDR")
	private int partnerCarrierId;
	@Column(name = "PRTCARNAM")
	private String partnerCarrierName;
	private static final String MODULE = "mail.masters";

	public PartnerCarrier() {
	}

	/** 
	* @param partnerCarrierVo
	* @throws SystemException
	*/
	public PartnerCarrier(PartnerCarrierVO partnerCarrierVo) {
		populatePK(partnerCarrierVo);
		populateAtrributes(partnerCarrierVo);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException ex) {
			throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
		}
	}

	/** 
	* @param partnerCarrierVo
	*/
	private void populatePK(PartnerCarrierVO partnerCarrierVo) {
		this.setCompanyCode(partnerCarrierVo.getCompanyCode());
		this.setAirportCode(partnerCarrierVo.getAirportCode());
		this.setOwnCarrierCode(partnerCarrierVo.getOwnCarrierCode());
		this.setPartnerCarrierCode(partnerCarrierVo.getPartnerCarrierCode());
	}

	/** 
	* @author A-1936
	* @param partnerCarrierVo
	* @throws SystemException
	*/
	private void populateAtrributes(PartnerCarrierVO partnerCarrierVo) {
		this.setPartnerCarrierId(Integer.parseInt(partnerCarrierVo.getPartnerCarrierId()));
		this.setPartnerCarrierName(partnerCarrierVo.getPartnerCarrierName());
		this.setLastUpdatedUser(partnerCarrierVo.getLastUpdateUser());
	}
	/**
	* @param partnerCarrierPk
	* @return
	* @throws SystemException
	* @throws FinderException
	*/
	public static PartnerCarrier find(PartnerCarrierPK partnerCarrierPk) throws FinderException {
		EntityManager em = PersistenceController.getEntityManager();
		return em.find(PartnerCarrier.class, partnerCarrierPk);
	}

	/** 
	* @author A-1936
	* @param partnerCarrierVo
	* @throws SystemException
	*/
	public void update(PartnerCarrierVO partnerCarrierVo) {
		populateAtrributes(partnerCarrierVo);
		this.setLastUpdatedTime(Timestamp.valueOf(partnerCarrierVo.getLastUpdateTime().toLocalDateTime()));
	}

	/** 
	* @author a-1876 This method is used to list the PartnerCarriers.
	* @param companyCode
	* @param ownCarrierCode
	* @param airportCode
	* @return Collection<PartnerCarrierVO>
	* @throws SystemException
	*/
	public static Collection<PartnerCarrierVO> findAllPartnerCarriers(String companyCode, String ownCarrierCode,
			String airportCode) {
		try {
			return constructDAO().findAllPartnerCarriers(companyCode, ownCarrierCode, airportCode);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/** 
	* @author a-1936 methods the DAO instance ..
	* @return
	* @throws SystemException
	*/
	public static MailMastersDAO constructDAO() {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MailMastersDAO.class.cast(em.getQueryDAO(MODULE));
		} catch (PersistenceException ex) {
			ex.getErrorCode();
			throw new SystemException(ex.getMessage());
		}
	}

	/** 
	* @throws SystemException
	*/
	public void remove() {
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			throw new SystemException(removeException.getErrorCode(), removeException.getMessage(), removeException);
		}
	}

	/** 
	* @author A-1936 This method is used to validatePartnerCarrier
	* @param companyCode
	* @param airlineCode
	* @return
	* @throws SystemException
	*/
	public AirlineValidationVO validatePartnerCarrier(String companyCode, String airlineCode) {
		SharedAirlineProxy sharedAirlineProxy = ContextUtil.getInstance().getBean(SharedAirlineProxy.class);
		AirlineValidationVO airlineValidationVO = null;
		try {
			airlineValidationVO = sharedAirlineProxy.validateAlphaCode(companyCode, airlineCode);
		} catch (SharedProxyException ex) {
			log.info("<<<<<<INVALID AIRLINE >>>>>");
		}
		return airlineValidationVO;
	}
}
