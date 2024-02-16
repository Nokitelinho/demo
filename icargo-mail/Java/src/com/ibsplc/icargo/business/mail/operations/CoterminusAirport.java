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

import com.ibsplc.icargo.business.mail.operations.vo.CoTerminusFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.CoTerminusVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


@Entity
@Table(name = "MALCTSARP")
public class CoterminusAirport {
	private CoterminusAirportPK coterminusAirportPK;
	
	private String airportCode;
	private String resditMode;
	private String truckFlag;
	private String lastUpdateUser;

	private Calendar lastUpdateTime;

	private static final String MODULE = "mail.operations";

	private Log log = LogFactory.getLogger("MailTracking_Defaults");

	/*
	 * The Default Constructor
	 */
	public CoterminusAirport() {
	}

	/**
	 * @param coTerminusVO
	 * @throws SystemException
	 */
	public CoterminusAirport(CoTerminusVO coTerminusVO)
			throws SystemException {
		populatePK(coTerminusVO);
		populateAtrributes(coTerminusVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
	}

	/**
	 * @param coTerminusVO
	 */
	private void populatePK(CoTerminusVO coTerminusVO) {
		CoterminusAirportPK coterminusAirportPK = new CoterminusAirportPK();
		coterminusAirportPK.setCompanyCode(coTerminusVO.getCompanyCode());
		coterminusAirportPK.setGpaCode(coTerminusVO.getGpaCode());
		this.setCoterminusAirportPK(coterminusAirportPK);
	}

	/**
	 * @param coTerminusVO
	 * @throws SystemException
	 */
	private void populateAtrributes(CoTerminusVO coTerminusVO)
			throws SystemException {
		
		this.setLastUpdateUser(coTerminusVO.getLastUpdateUser());
		this.setLastUpdateTime(coTerminusVO.getLastUpdateTime());
		this.setAirportCode(coTerminusVO.getCoAirportCodes());
		this.setResditMode(coTerminusVO.getResditModes());
		this.setTruckFlag(coTerminusVO.getTruckFlag());
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
	 * @return Returns the coterminusAirportPK.
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "gpaCode", column = @Column(name = "GPACOD")),
			@AttributeOverride(name = "SERNUM", column = @Column(name = "SERNUM")) })
	public CoterminusAirportPK getCoterminusAirportPK() {
		return coterminusAirportPK;
	}

	@Column(name = "ARPCOD")
	public String getAirportCode() {
		return airportCode;
	}

	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	@Column(name = "RSDMOD")
	public String getResditMode() {
		return resditMode;
	}

	public void setResditMode(String resditMode) {
		this.resditMode = resditMode;
	}

	@Column(name = "TRKFLG")
	public String getTruckFlag() {
		return truckFlag;
	}

	public void setTruckFlag(String truckFlag) {
		this.truckFlag = truckFlag;
	}

	/**
	 * @param coterminusAirportPK
	 *            The coterminusAirportPK to set.
	 */
	public void setCoterminusAirportPK(CoterminusAirportPK coterminusAirportPK) {
		this.coterminusAirportPK = coterminusAirportPK;
	}

	/**
	 * @param coterminusAirportPK
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */

	public static CoterminusAirport find(CoterminusAirportPK coterminusAirportPK)
			throws SystemException, FinderException {
		EntityManager em = PersistenceController.getEntityManager();
		return em.find(CoterminusAirport.class, coterminusAirportPK);
	}

	/**
	 * @param coTerminusVO
	 * @throws SystemException
	 */
	public void update(CoTerminusVO coTerminusVO)
			throws SystemException {
		populateAtrributes(coTerminusVO);
		this.setLastUpdateTime(coTerminusVO.getLastUpdateTime());
	}

	/**
	 * @param companyCode
	 * @param ownCarrierCode
	 * @param airportCode
	 * @return Collection<CoTerminusVO>
	 * @throws SystemException
	 */
	public static Collection<CoTerminusVO> findAllCoTerminusAirports(
			CoTerminusFilterVO filterVO)
			throws SystemException {
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

	public static MailTrackingDefaultsDAO constructDAO() throws SystemException {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MailTrackingDefaultsDAO.class.cast(em.getQueryDAO(MODULE));
		} catch (PersistenceException ex) {
			ex.getErrorCode();
			throw new SystemException(ex.getMessage());
		}
	}

	/**
	 * @throws SystemException
	 */
	public void remove() throws SystemException {
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			throw new SystemException(removeException.getErrorCode(),
					removeException);
		}
	}

	public boolean validateCoterminusairports(String actualAirport,String eventAirport,String eventCode,String paCode,LocalDate dspDate) throws SystemException{
		try {
			return constructDAO().validateCoterminusairports(actualAirport,eventAirport,eventCode,paCode,dspDate);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

}
