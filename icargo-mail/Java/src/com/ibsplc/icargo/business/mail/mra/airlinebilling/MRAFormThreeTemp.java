/*

 * MRAFormThreeTemp.java Created on July 29,2008

 *

 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.

 *

 * This software is the proprietary information of IBS Software Services (P) Ltd.

 * Use is subject to license terms.

 */
package com.ibsplc.icargo.business.mail.mra.airlinebilling;

import java.util.Calendar;

import javax.persistence.Entity;

import javax.persistence.Table;

import javax.persistence.AttributeOverrides;

import javax.persistence.Temporal;

import javax.persistence.TemporalType;


import javax.persistence.AttributeOverride;

import javax.persistence.Column;

import javax.persistence.EmbeddedId;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineForBillingVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling.MRAAirlineBillingDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

import com.ibsplc.xibase.server.framework.persistence.CreateException;

import com.ibsplc.xibase.server.framework.persistence.EntityManager;

import com.ibsplc.xibase.server.framework.persistence.PersistenceController;

import com.ibsplc.xibase.server.framework.persistence.PersistenceException;

import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;

import com.ibsplc.xibase.util.log.Log;

import com.ibsplc.xibase.util.log.factory.LogFactory;
/**

 * @author a-3108

 */

@Staleable
@Table(name="MALMRAARLBLGTMP")
@Deprecated
@Entity

public class MRAFormThreeTemp {
	
	private MRAFormThreeTempPK mRAFormThreeTempPK;

	

	private String airlineCode;

	

	private Calendar creationDate;
	
	private String airlineBillingStatus;
	
	
	private double miscellaneousBillingAmount;



	private double totalBillingAmount;



	private double creditBillingAmount;



	private double netBillingValue;

	

	private String airlineNumber;

	

	private String operationFlag;

	
	private Calendar lstUpdTimFrmFormThree;
	
	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";
	

	/**
	 * @return Returns the lstUpdTimFrmFormThree.
	 */
	//@Column(name = "FRMTHRLSTUPDTIM")
	@Column(name = "FRMTHRLSTUPDTIM")        //Modified by A-7929 as part of ICRD-265471
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLstUpdTimFrmFormThree() {
		return lstUpdTimFrmFormThree;
	}
	
	/**
	 * @param lstUpdTimFrmFormThree The lstUpdTimFrmFormThree to set.
	 */
	public void setLstUpdTimFrmFormThree(Calendar lstUpdTimFrmFormThree) {
		this.lstUpdTimFrmFormThree = lstUpdTimFrmFormThree;
	}

	/**

	 * @return Returns the airlineBillingStatus.

	 */

	@Column(name="FORTHRSTA")

	public String getAirlineBillingStatus() {

		return airlineBillingStatus;

	}

	/**

	 * @param airlineBillingStatus The airlineBillingStatus to set.

	 */

	public void setAirlineBillingStatus(String airlineBillingStatus) {

		this.airlineBillingStatus = airlineBillingStatus;

	}









	/**

	 * @return Returns the airlineCode.

	 */

	@Column(name="ARLCOD")

	public String getAirlineCode() {

		return airlineCode;

	}









	/**

	 * @param airlineCode The airlineCode to set.

	 */

	public void setAirlineCode(String airlineCode) {

		this.airlineCode = airlineCode;

	}









	/**

	 * @return Returns the airlineNumber.

	 */

	@Column(name="ARLNUM")

	public String getAirlineNumber() {

		return airlineNumber;

	}









	/**

	 * @param airlineNumber The airlineNumber to set.

	 */

	public void setAirlineNumber(String airlineNumber) {

		this.airlineNumber = airlineNumber;

	}




	/**

	 * @return Returns the creationDate.

	 */

	@Column(name="CREDAT")


	@Temporal(TemporalType.DATE)
	public Calendar getCreationDate() {

		return creationDate;

	}









	/**

	 * @param creationDate The creationDate to set.

	 */

	public void setCreationDate(Calendar creationDate) {

		this.creationDate = creationDate;

	}









	/**

	 * @return Returns the creditBillingAmount.

	 */

	//@Column(name="INWCRDAMT")
	@Column(name="INWCRDAMTLSTCUR")           //Modified by A-7929 as part of ICRD-265471
	public double getCreditBillingAmount() {

		return creditBillingAmount;

	}









	/**

	 * @param creditBillingAmount The creditBillingAmount to set.

	 */

	public void setCreditBillingAmount(double creditBillingAmount) {

		this.creditBillingAmount = creditBillingAmount;

	}









	/**

	 * @return Returns the formThreeTempPK.

	 */

	@EmbeddedId
	@AttributeOverrides({

		@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),

		@AttributeOverride(name="airlineIdentifier", column=@Column(name="ARLIDR")),

		@AttributeOverride(name="clearancePeriod", column=@Column(name="CLRPRD")),

		@AttributeOverride(name="serialNumber", column=@Column(name="SERNUM"))})

	public MRAFormThreeTempPK getMRAFormThreeTempPK() {

		return mRAFormThreeTempPK;

	}









	/**

	 * @param formThreeTempPK The formThreeTempPK to set.

	 */

	public void setMRAFormThreeTempPK(MRAFormThreeTempPK mRAFormThreeTempPK) {

		this.mRAFormThreeTempPK = mRAFormThreeTempPK;

	}



	/**

	 * @return Returns the netBillingValue.

	 */

	//@Column(name="INWNETAMT")
	@Column(name="INWNETAMTLSTCUR")            //Modified by A-7929 as part of ICRD-265471
	public double getNetBillingValue() {

		return netBillingValue;

	}



	/**

	 * @param netBillingValue The netBillingValue to set.

	 */

	public void setNetBillingValue(double netBillingValue) {

		this.netBillingValue = netBillingValue;

	}


	/**

	 * @return Returns the totalBillingAmount.

	 */

	//@Column(name="INWTOTAMT")
	@Column(name="INWTOTAMTLSTCUR")             //Modified by A-7929 as part of ICRD-265471
	public double getTotalBillingAmount() {

		return totalBillingAmount;

	}



	/**

	 * @param totalBillingAmount The totalBillingAmount to set.

	 */

	public void setTotalBillingAmount(double totalBillingAmount) {

		this.totalBillingAmount = totalBillingAmount;

	}
	/**

	 * @return Returns the miscellaneousBillingAmount.

	 */

	//@Column(name="INWMISAMT")       
	@Column(name="INWMISAMTLSTCUR")     //Modified by A-7929 as part of ICRD-265471
	public double getMiscellaneousBillingAmount() {

		return miscellaneousBillingAmount;

	}



	/**

	 * @param miscellaneousBillingAmount The miscellaneousBillingAmount to set.

	 */

	public void setMiscellaneousBillingAmount(double miscellaneousBillingAmount) {

		this.miscellaneousBillingAmount = miscellaneousBillingAmount;

	}



	

	/**

	 * @return Returns the operationFlag.

	 */

	@Column(name="OPRFLG")

	public String getOperationFlag() {

		return operationFlag;

	}



	/**

	 * @param operationFlag The operationFlag to set.

	 */

	public void setOperationFlag(String operationFlag) {

		this.operationFlag = operationFlag;

	}

	/**

	 * @return Returns the operationFlag.

	 */

	

	

		/**

	 * Default Constructor

	 *

	 */

	public MRAFormThreeTemp() {

		

	}
	private static Log localLogger() {

		return LogFactory.getLogger("MRA AIRLINEBILLING");

	}
	private void populatePK(String companyCode,int airlineIdentifier, 

			String clearancePeriod, int serialNumber) 

				throws SystemException{

			localLogger().entering("MRAFormThreeTemp","populatePK");

			MRAFormThreeTempPK formThreeTempPk = new MRAFormThreeTempPK();

			

			formThreeTempPk.setCompanyCode(companyCode);

			formThreeTempPk.setAirlineIdentifier(airlineIdentifier);

			formThreeTempPk.setClearancePeriod(clearancePeriod);

			formThreeTempPk.setSerialNumber(serialNumber);

			

			setMRAFormThreeTempPK(formThreeTempPk);

			localLogger().exiting("mraformThreeTemp","populatePK");

	}

	/**
	 * @return int
	 * @throws SystemException
	 */
	public static int findMaxSerialNumber() throws SystemException{

		localLogger().entering("FormThreeTemp","findMaxSerialNumber");

		int serialNumber = 0;

		EntityManager entityManager =

			PersistenceController.getEntityManager();

		try{

			

			serialNumber = MRAAirlineBillingDAO .class.cast(

					entityManager.getQueryDAO(MODULE_NAME))

					.findMaxSerialNumber();

			localLogger().log(Log.FINE,"Got serial number--->>>>"+serialNumber);

			

		} catch (PersistenceException persistenceException) {

			persistenceException.getErrorCode();

			throw new SystemException(persistenceException.getMessage());

		}

		localLogger().exiting("FormThreeTemp","findMaxSerialNumber");

		return serialNumber;	

	}

	public MRAFormThreeTemp(AirlineForBillingVO airlineForBillingVO, int serialNumber)

	throws SystemException{

try{

	localLogger().entering("MRAFormThreeTemp","MRAFormThreeTemp");
	localLogger().log(Log.FINE,"inside temp***-------->"+airlineForBillingVO.getAirlineNumber());
	populatePK(airlineForBillingVO.getCompanyCode(), airlineForBillingVO.getAirlineIdentifier(),

			airlineForBillingVO.getClearancePeriod(), serialNumber);

	setAirlineBillingStatus(airlineForBillingVO.getStatus());

	setAirlineCode(airlineForBillingVO.getAirlineCode());

	setAirlineNumber(airlineForBillingVO.getAirlineNumber());
	LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,
			Location.NONE, true);
	
	setLstUpdTimFrmFormThree(airlineForBillingVO.getLastUpdateTime());
    

    setCreationDate(currentDate.toCalendar());
    
    

    if(airlineForBillingVO.getNetValueInBilling() != null) {

        setNetBillingValue(airlineForBillingVO.getNetValueInBilling().getAmount());

    }
    if(airlineForBillingVO.getCreditAmountInBilling() != null) {

        setCreditBillingAmount(airlineForBillingVO.getCreditAmountInBilling().getAmount());

    }
    

    if(airlineForBillingVO.getTotalAmountInBilling() != null) {

        setTotalBillingAmount(airlineForBillingVO.getTotalAmountInBilling().getAmount());

    }  
    

    if(airlineForBillingVO.getMiscAmountInBilling() != null) {

        setMiscellaneousBillingAmount(airlineForBillingVO.getMiscAmountInBilling().getAmount());

    }
    setOperationFlag(airlineForBillingVO.getOperationFlag());

	

	PersistenceController.getEntityManager().persist(this);

} catch(CreateException createException) {

	localLogger().log(Log.SEVERE,"CreateException caught,SystemException thrown");

	throw new SystemException(createException.getErrorCode(),

			createException);

}	

localLogger().exiting("MRAFormThreeTemp","MRAFormThreeTemp");

}





}
