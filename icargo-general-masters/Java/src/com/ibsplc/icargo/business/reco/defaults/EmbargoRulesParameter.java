/*
 * EmbargoParameter.java Created on Jul 13, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary 
 * information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.reco.defaults;


import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.icargo.business.shared.airline.InvalidAirlineException;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.InvalidAirportException;
import com.ibsplc.icargo.business.reco.defaults.proxy.SharedAirlineProxy;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoParameterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoRulesVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;

/**
 * This class handles the persistence of EmbargoParameter
 * 
 * @author A-1358
 * 
 */


@Staleable
@Table(name = "RECPAR")
@Entity
public class EmbargoRulesParameter {

	private EmbargoRulesParameterPK embargoParameterPk;

	private String parameterValues;

	private String applicableLevel;

	

	public EmbargoRulesParameter() {
	}

	public EmbargoRulesParameter(EmbargoParameterVO parameterVO, EmbargoRulesVO embargoVO)
			throws SystemException, CreateException {
		populatePk(embargoVO, parameterVO);
		populateAttributes(parameterVO);

	}

	/**
	 * @return Returns the embargoParameterPk.
	 */

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "embargoReferenceNumber", column = @Column(name = "REFNUM")),
			@AttributeOverride(name = "embargoVersion", column = @Column(name = "VERNUM")),
			@AttributeOverride(name = "parameterCode", column = @Column(name = "PARCOD")),
			@AttributeOverride(name = "parameterType", column = @Column(name = "PARTYP")),
			@AttributeOverride(name = "applicableOn", column = @Column(name = "PARCND"))}

	)
	public EmbargoRulesParameterPK getEmbargoParameterPk() {
		return embargoParameterPk;
	}

	/**
	 * @param embargoParameterPk
	 *            The embargoParameterPk to set.
	 */
	public void setEmbargoParameterPk(EmbargoRulesParameterPK embargoParameterPk) {
		this.embargoParameterPk = embargoParameterPk;
	}

	@Column(name = "APPONN")
	public String getApplicableLevel() {
		return applicableLevel;
	}

	public void setApplicableLevel(String applicableLevel) {
		this.applicableLevel = applicableLevel;
	}
	/**
	 * @return Returns the parameterValues.
	 */

	@Column(name = "PARVAL")
	public String getParameterValues() {
		return parameterValues;
	}

	/**
	 * @param parameterValues
	 *            The parameterValues to set.
	 */
	public void setParameterValues(String parameterValues) {
		this.parameterValues = parameterValues;
	}

	/**
	 * This method populates Pk
	 * 
	 * @param embargoVO
	 * @param embargoParameterVO
	 * @throws SystemException
	 */
	public void populatePk(EmbargoRulesVO embargoVO,
			EmbargoParameterVO embargoParameterVO) throws SystemException {
		EmbargoRulesParameterPK embargoParameterPK = new EmbargoRulesParameterPK();
		embargoParameterPK.setParameterCode(   embargoParameterVO
				.getParameterCode());// generateParameterCode(embargoVO);
		embargoParameterPK.setParameterType(embargoParameterVO.getParameterLevel());
		embargoParameterPK.setApplicableOn(embargoParameterVO.getApplicable());
		embargoParameterPK.setCompanyCode(   embargoVO.getCompanyCode());
		embargoParameterPK.setEmbargoReferenceNumber(   embargoVO
				.getEmbargoReferenceNumber());
		embargoParameterPK.setEmbargoVersion(embargoVO.getEmbargoVersion());
		setEmbargoParameterPk(embargoParameterPK);
	}

	/*
	 * private String generateParameterCode(EmbargoVO embargoVO)throws
	 * SystemException{
	 * 
	 * Criterion criterion =
	 * KeyUtils.getCriterion(embargoVO.getCompanyCode(),EmbargoVO.KEY_EMBARGO_PARAMETER,"DEFAULT");
	 * return embargoVO.getCompanyCode()+KeyUtils.getKey(criterion); }
	 */

	/**

	 * This Method populates the fields of EmbagoParameter BO
	 * @param embargoParameterVO
	 * @throws CreateException
	 * @throws SystemException
	 */
	public void populateAttributes(EmbargoParameterVO embargoParameterVO)
			throws CreateException, SystemException {

		if("FLTNUM".equals(embargoParameterVO.getParameterCode()) && !"-".equals(embargoParameterVO.getParameterValues())) {
			try {
				AirlineValidationVO  airlineValidationVO = validateAlphaCode(embargoParameterVO.getCompanyCode(), embargoParameterVO.getParameterValues().split("~")[0].toUpperCase());
				/*if(airlineValidationVO!=null) {
					StringBuffer paramValue = new StringBuffer(String.valueOf(airlineValidationVO.getAirlineIdentifier()))
					.append("~").append(embargoParameterVO.getFlightNumber());*/
					setParameterValues(embargoParameterVO.getParameterValues().toString());
					
				//}
			} catch (InvalidAirlineException e) {
				throw new SystemException(e.getErrors());
			}
			
		} else {
			setParameterValues(embargoParameterVO.getParameterValues().trim());
		}
		
		
			setApplicableLevel(embargoParameterVO.getApplicableLevel());
		
		PersistenceController.getEntityManager().persist(this);
	}

	
	
	/**
	 *This method updates Embargoparameter Child Table
	 * @param embargoParameterVO
	 * @throws CreateException
	 * @throws SystemException
	 */
	public void update(EmbargoParameterVO embargoParameterVO)
			throws CreateException, SystemException {
		populateAttributes(embargoParameterVO);
	}

	
	
	/**
	 * This method Removes the particular entry from the Database 
	 * @throws RemoveException
	 * @throws SystemException
	 */
	public void remove() throws RemoveException, SystemException {

		PersistenceController.getEntityManager().remove(this);
	}
	
	/**
	 * Used for validating an Airline. Returns NULL if the Airline does not
	 * exist
	 * 
	 * @author A-5186
	 * @param companyCode
	 * @param airlineCode
	 * @return AirportValidationVO
	 * @throws SystemException
	 * @throws InvalidAirportException
	 */
	public AirlineValidationVO validateAlphaCode(String companyCode,
			String airlineCode) throws SystemException, InvalidAirlineException {


			SharedAirlineProxy  sharedAirlineProxy = new SharedAirlineProxy();
			try {
				return sharedAirlineProxy.validateAlphaCode(companyCode, airlineCode);
			} catch (ProxyException e) {
				throw new SystemException(e.getErrors());
			}
	}
}
