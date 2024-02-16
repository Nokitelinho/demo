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

import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mailmasters.vo.CoTerminusFilterVO;
import com.ibsplc.neoicargo.mailmasters.vo.CoTerminusVO;
import com.ibsplc.neoicargo.mailmasters.dao.MailMastersDAO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@IdClass(CoterminusAirportPK.class)
@SequenceGenerator(name = "MALCTSARPSEQ", initialValue = 1, allocationSize = 1, sequenceName = "malctsarp_seq")
@Table(name = "MALCTSARP")
public class CoterminusAirport  extends BaseEntity implements Serializable {

	@Column(name = "ARPCOD")
	private String airportCode;
	@Column(name = "RSDMOD")
	private String resditMode;
	@Column(name = "TRKFLG")
	private String truckFlag;

	@Id
	@Column(name = "GPACOD")
	private String gpaCode;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MALCTSARPSEQ")
	@Column(name = "SERNUM")
	private long sernum;
	private static final String MODULE = "mail.masters";

	public CoterminusAirport() {
	}

	/** 
	* @param coTerminusVO
	* @throws SystemException
	*/
	public CoterminusAirport(CoTerminusVO coTerminusVO) {
		populatePK(coTerminusVO);
		populateAtrributes(coTerminusVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException ex) {
			throw new SystemException(ex.getErrorCode(), ex.getMessage(), ex);
		}
	}

	/** 
	* @param coTerminusVO
	*/
	private void populatePK(CoTerminusVO coTerminusVO) {

		this.setCompanyCode(coTerminusVO.getCompanyCode());
		this.setGpaCode(coTerminusVO.getGpaCode());
	}

	/** 
	* @param coTerminusVO
	* @throws SystemException
	*/
	private void populateAtrributes(CoTerminusVO coTerminusVO) {
		this.setLastUpdatedUser(coTerminusVO.getLastUpdateUser());
		this.setLastUpdatedTime(Timestamp.valueOf(coTerminusVO.getLastUpdateTime().toLocalDateTime()));
		this.setAirportCode(coTerminusVO.getCoAirportCodes());
		this.setResditMode(coTerminusVO.getResditModes());
		this.setTruckFlag(coTerminusVO.getTruckFlag());
	}

	/** 
	* @param coterminusAirportPK
	* @return
	* @throws SystemException
	* @throws FinderException
	*/
	public static CoterminusAirport find(CoterminusAirportPK coterminusAirportPK) throws FinderException {
		EntityManager em = PersistenceController.getEntityManager();
		return em.find(CoterminusAirport.class, coterminusAirportPK);
	}

	/** 
	* @param coTerminusVO
	* @throws SystemException
	*/
	public void update(CoTerminusVO coTerminusVO) {
		populateAtrributes(coTerminusVO);
		this.setLastUpdatedTime(Timestamp.valueOf(coTerminusVO.getLastUpdateTime().toLocalDateTime()));
	}

	/**
	* @return Collection<CoTerminusVO>
	* @throws SystemException
	*/
	public static Collection<CoTerminusVO> findAllCoTerminusAirports(CoTerminusFilterVO filterVO) {
		try {
			return constructDAO().findAllCoTerminusAirports(filterVO);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/** 
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

	public boolean validateCoterminusairports(String actualAirport, String eventAirport, String eventCode,
			String paCode, ZonedDateTime dspDate) {
		try {
			return constructDAO().validateCoterminusairports(actualAirport, eventAirport, eventCode, paCode, dspDate);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}


}
