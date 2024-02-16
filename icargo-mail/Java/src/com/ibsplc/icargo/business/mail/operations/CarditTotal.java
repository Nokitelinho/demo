/*
 * CarditTotal.java Created on Jun 7, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.icargo.business.mail.operations.vo.CarditTotalVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This entity persists the totals information in a Cardit
 * 
 * @author A-1739
 * 
 */
/*
 * Revision History
 * --------------------------------------------------------------------------
 * Revision Date Author Description
 * ------------------------------------------------------------------------- 0.1
 * Jun 7, 2006 A-1739 First Draft
 *  		  July 16, 2007			A-1739		EJB3Final changes
 */
@Entity
@Table(name = "MALCDTTOT")
@Staleable
public class CarditTotal {

	/**
	 * Number of receptacles if value is null default value is set as -1
	 */
	private long numberOfReceptacles;

	/**
	 * Weight of receptacles If value is null default value is set as -1
	 */
	private double weightOfReceptacles;

	private CarditTotalPK carditTotalPK;

	public CarditTotal() {

	}

	public CarditTotal(CarditPK carditPK, CarditTotalVO carditTotalVO)
			throws SystemException {
		populatePK(carditPK, carditTotalVO);
		populateAttributes(carditTotalVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getMessage(),
					createException);
		}
	}

	/**
	 * @param carditPK
	 * @param carditTotalVO
	 */
	private void populatePK(CarditPK carditPK, CarditTotalVO carditTotalVO) {
		carditTotalPK = new CarditTotalPK();
		carditTotalPK.setCompanyCode(   carditPK.getCompanyCode());
		carditTotalPK.setCarditKey(   carditPK.getCarditKey());
		carditTotalPK.setMailClassCode(   carditTotalVO.getMailClassCode());
	}

	/**
	 * @param carditTotalVO
	 */
	private void populateAttributes(CarditTotalVO carditTotalVO) {
		if(null!=carditTotalVO.getNumberOfReceptacles()){
			setNumberOfReceptacles(Long.parseLong(carditTotalVO.getNumberOfReceptacles()));
		}
		//setWeightOfReceptacles(carditTotalVO.getWeightOfReceptacles()); 
		if(carditTotalVO.getWeightOfReceptacles()!=null){    
			setWeightOfReceptacles(Double.parseDouble(carditTotalVO.getWeightOfReceptacles().getFormattedSystemValue()));//added by A-7371
		}
	}

	/**
	 * @author A-3227
	 * @param carditContPK
	 * @return
	 * @throws FinderException
	 * @throws SystemException
	 */
	public static CarditTotal find(CarditTotalPK carditTotPK)
	throws FinderException, SystemException{
		return PersistenceController.getEntityManager().find(CarditTotal.class, carditTotPK);
	}
	/**
	 * @author A-3227
	 * @throws SystemException
	 */
	public void remove() throws SystemException {
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			throw new SystemException(removeException.getMessage(),
					removeException);
		}
	}
	/**
	 * @return Returns the carditTotalPK.
	 */
	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
		@AttributeOverride(name = "carditKey", column = @Column(name = "CDTKEY")),
		@AttributeOverride(name = "mailClassCode", column = @Column(name = "MALCLS"))})
	public CarditTotalPK getCarditTotalPK() {
		return carditTotalPK;
	}

	/**
	 * @param carditTotalPK
	 *            The carditTotalPK to set.
	 */
	public void setCarditTotalPK(CarditTotalPK carditTotalPK) {
		this.carditTotalPK = carditTotalPK;
	}

	/**
	 * @return Returns the numberOfReceptacles.
	 */
	@Column(name = "NUMRCP")
	public long getNumberOfReceptacles() {
		return numberOfReceptacles;
	}

	/**
	 * @param numberOfReceptacles
	 *            The numberOfReceptacles to set.
	 */
	public void setNumberOfReceptacles(long numberOfReceptacles) {
		this.numberOfReceptacles = numberOfReceptacles;
	}

	/**
	 * @return Returns the weightOfReceptacles.
	 */
	@Column(name = "RCPWGT")
	public double getWeightOfReceptacles() {
		return weightOfReceptacles;
	}

	/**
	 * @param weightOfReceptacles
	 *            The weightOfReceptacles to set.
	 */
	public void setWeightOfReceptacles(double weightOfReceptacles) {
		this.weightOfReceptacles = weightOfReceptacles;
	}
	/**
	 * This method is used to find the Cardit Totals if any present ...
	 * @author a-1936
	 * @param companyCode
	 * @param carditKey
	 * @param mailClassCode
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static CarditTotal find(String companyCode, String carditKey,
			String mailClassCode) throws SystemException,
			FinderException {
		    Log  logger= LogFactory.getLogger("MAILTRACKING_DEFAULTS");
		    CarditTotalPK carditTotPk = new CarditTotalPK();
		    carditTotPk.setCompanyCode(companyCode);
		    carditTotPk.setCarditKey(carditKey);
		    carditTotPk.setMailClassCode(mailClassCode);
		    logger.log(Log.FINE,"The Company Code "+ carditTotPk.getCompanyCode());
		    logger.log(Log.FINE,"The CarditKey "+ carditTotPk.getCarditKey());
		    logger.log(Log.FINE,"The mailClassCode "+ carditTotPk.getMailClassCode());
		    EntityManager em = PersistenceController.getEntityManager();
		   return em.find(CarditTotal.class, carditTotPk);
	}
}
