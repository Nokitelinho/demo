/*
 * MRAAirlineBilling.java Created on Feb 15, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.airlinebilling;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.MiscFileFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.InvoiceLovFilterVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.sis.SISMessageVO;
import com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling.MRAAirlineBillingDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;

/**
 * @author A-1946
 *
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * -------------------------------------------------------------------------
 * 0.1			  feb 15, 2007			    A-1946		Created
 * 
 */

@Staleable
@Table(name="MTKARLBLGMST")
@Entity
@Deprecated
public class MRAAirlineBilling {

    private static final String MODULE_NAME = "mail.mra.airlinebilling";

    // private Log log = LogFactory.getLogger("MAilTRACKING  MRA");

    private MRAAirlineBillingPK mraAirlineBillingPK;

    private String airlineCode;

	//private String airlineNumber;

	//private int totalPiecesReceived;

	//private double totalWeightReceived;

	private double totalBillableWeight;

	private double totalBilledWeight;

	private double totalBillableAmount;

	private double totalBilledAmount;

//	private Set<AirlineBillingDetailVO> airlineBillingDetailVOs;

	/**
	 * @return Returns the airlineBillingDetailVOs.
	 */
//	public Set<AirlineBillingDetailVO> getAirlineBillingDetailVOs() {
//		return airlineBillingDetailVOs;
//	}

	/**
	 * @param airlineBillingDetailVOs The airlineBillingDetailVOs to set.
	 */
//	public void setAirlineBillingDetailVOs(
//			Set<AirlineBillingDetailVO> airlineBillingDetailVOs) {
//		this.airlineBillingDetailVOs = airlineBillingDetailVOs;
//	}

	/**
	 * @return Returns the airlineCode.
	 */
	@Column(name = "ARLCOD")
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
	/*@Column(name = "ARLNUM")
	public String getAirlineNumber() {
		return airlineNumber;
	}

	*//**
	 * @param airlineNumber The airlineNumber to set.
	 *//*
	public void setAirlineNumber(String airlineNumber) {
		this.airlineNumber = airlineNumber;
	}*/

	/**
	 * @return Returns the totalBillableAmount.
	 */
	@Column(name = "TOTBLBAMT")
	public double getTotalBillableAmount() {
		return totalBillableAmount;
	}

	/**
	 * @param totalBillableAmount The totalBillableAmount to set.
	 */
	public void setTotalBillableAmount(double totalBillableAmount) {
		this.totalBillableAmount = totalBillableAmount;
	}

	/**
	 * @return Returns the totalBillableWeight.
	 */
	@Column(name = "TOTBLBWGT")
	public double getTotalBillableWeight() {
		return totalBillableWeight;
	}

	/**
	 * @param totalBillableWeight The totalBillableWeight to set.
	 */
	public void setTotalBillableWeight(double totalBillableWeight) {
		this.totalBillableWeight = totalBillableWeight;
	}

	/**
	 * @return Returns the totalBilledAmount.
	 */
	@Column(name = "TOTBLDAMT")
	public double getTotalBilledAmount() {
		return totalBilledAmount;
	}

	/**
	 * @param totalBilledAmount The totalBilledAmount to set.
	 */
	public void setTotalBilledAmount(double totalBilledAmount) {
		this.totalBilledAmount = totalBilledAmount;
	}

	/**
	 * @return Returns the totalBilledWeight.
	 */
	@Column(name = "TOTBLDWGT")
	public double getTotalBilledWeight() {
		return totalBilledWeight;
	}

	/**
	 * @param totalBilledWeight The totalBilledWeight to set.
	 */
	public void setTotalBilledWeight(double totalBilledWeight) {
		this.totalBilledWeight = totalBilledWeight;
	}
	/**
	 * @return Returns the mraAirlineBillingPK.
	 */
    @EmbeddedId
	@AttributeOverrides({
        @AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
        @AttributeOverride(name = "airlineIdentifier", column = @Column(name = "ARLIDR")),
        @AttributeOverride(name = "billingBasis", column = @Column(name = "BLGBAS"))})
	public MRAAirlineBillingPK getMraAirlineBillingPK() {
		return mraAirlineBillingPK;
	}

	/**
	 * @param mraAirlineBillingPK The mraAirlineBillingPK to set.
	 */
	public void setMraAirlineBillingPK(MRAAirlineBillingPK mraAirlineBillingPK) {
		this.mraAirlineBillingPK = mraAirlineBillingPK;
	}

	/**
	 * 
	 * @return mraAirlineBillingDAO
	 * @throws SystemException
	 */
	private static MRAAirlineBillingDAO constructMRAAirlineBillingDAO() throws SystemException {

		MRAAirlineBillingDAO mraAirlineBillingDAO = null;
			try {
				EntityManager em = PersistenceController.getEntityManager();
				mraAirlineBillingDAO = MRAAirlineBillingDAO.class.cast
											(em.getQueryDAO(MODULE_NAME));
			}catch (PersistenceException e) {
				throw new SystemException(e.getMessage(),e);
			}

		return mraAirlineBillingDAO;
	}
	
	/**
	 * method to generate outward billing invoice 
     * @param invoiceFilterVO
     * @return String
     * @throws SystemException
     */
    public static String generateOutwardBillingInvoice(InvoiceLovFilterVO invoiceFilterVO)
    	throws SystemException {
    	String outParameter = null;
		return constructMRAAirlineBillingDAO().generateOutwardBillingInvoice(invoiceFilterVO);
    }

    /**
	 * method to generate outward billing invoice 
     * @param invoiceFilterVO
     * @return String
     * @throws SystemException
     */
    public static String generateOutwardBillingInvoiceForMail(InvoiceLovFilterVO invoiceFilterVO)
    	throws SystemException {
    	String outParameter = null;
		return constructMRAAirlineBillingDAO().generateOutwardBillingInvoice(invoiceFilterVO);
    }
    
    /**
	 * 	Method		:	MRABillingMaster.generateIsFile
	 *	Added by 	:	A-7794 on 24-Jul-2018
	 * 	Used for 	:	ICRD-265471
	 *	Parameters	:	@param ISFileFilterVO
	 *	Return type	: 	Collection<SISMessageVO>
	 */
	public static Collection<SISMessageVO> generateIsFile(MiscFileFilterVO fileFilterVO)throws SystemException{
		Collection<SISMessageVO> sisMessageVOs = new ArrayList<SISMessageVO>();
		try {
			sisMessageVOs = constructMRAAirlineBillingDAO().generateIsFile(fileFilterVO);
		} catch (PersistenceException persistenceException) {
			// TODO Auto-generated catch block
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
		return sisMessageVOs;
		
	}

}
