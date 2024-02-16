/*
 * EDITransactionVO.java Created on JUN 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.Collection;

import com.ibsplc.icargo.business.capacity.mailbidding.vo.CGRResponseVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * TODO Add the purpose of this class
 *
 * @author A-5991
 *
 */
/*
 *  Revision History
 *--------------------------------------------------------------------------
 *  Revision 	Date      	           		   Author			Description
 * -------------------------------------------------------------------------
 *  0.1			JUN 30, 2016			  		 A-5991			First Draft
 */
public class EDIInterchangeVO extends AbstractVO {


    private String companyCode;
    
    private String sequenceNum;

    private String interchangeCtrlReference;

    /**
     * Syntax id of this interchange
     */
    private String interchangeSyntaxId;

    /**
     * Syntax version
     */
    private int interchangeSyntaxVer;

    /**
     * Recipient idr
     */
    private String recipientId;

    /**
     * Sender id
     */
    private String senderId;

    /**
     * Interchange preparation date
     */
    private LocalDate preparationDate;


    /**
     * Interchange control count
     */
    private int interchangeControlCnt;

    private long messageSequence;

    private String stationCode;

    private Collection<com.ibsplc.icargo.business.mail.operations.vo.CarditVO> carditMessages;
    private Collection<CGRResponseVO> cgrResponseVOs;
    private Collection<String> errorCodes;

    /**
     * @return Returns the companyCode.
     */
    public String getCompanyCode() {
        return companyCode;
    }


    /**
     * @param companyCode The companyCode to set.
     */
    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }


    /**
     * @return Returns the carditMessages.
     */
    public Collection<com.ibsplc.icargo.business.mail.operations.vo.CarditVO> getCarditMessages() {
        return carditMessages;
    }


    /**
     * @param carditMessages The carditMessages to set.
     */
    public void setCarditMessages(Collection<com.ibsplc.icargo.business.mail.operations.vo.CarditVO> carditMessages) {
        this.carditMessages = carditMessages;
    }

    /**
     * @return Returns the interchangeControlCnt.
     */
    public int getInterchangeControlCnt() {
        return interchangeControlCnt;
    }


    /**
     * @param interchangeControlCnt The interchangeControlCnt to set.
     */
    public void setInterchangeControlCnt(int interchangeControlCnt) {
        this.interchangeControlCnt = interchangeControlCnt;
    }


    /**
     * @return Returns the interchangeCtrlReference.
     */
    public String getInterchangeCtrlReference() {
        return interchangeCtrlReference;
    }


    /**
     * @param interchangeCtrlReference The interchangeCtrlReference to set.
     */
    public void setInterchangeCtrlReference(String interchangeCtrlReference) {
        this.interchangeCtrlReference = interchangeCtrlReference;
    }


    /**
     * @return Returns the interchangeSyntaxId.
     */
    public String getInterchangeSyntaxId() {
        return interchangeSyntaxId;
    }


    /**
     * @param interchangeSyntaxId The interchangeSyntaxId to set.
     */
    public void setInterchangeSyntaxId(String interchangeSyntaxId) {
        this.interchangeSyntaxId = interchangeSyntaxId;
    }


    /**
     * @return Returns the interchangeSyntaxVer.
     */
    public int getInterchangeSyntaxVer() {
        return interchangeSyntaxVer;
    }


    /**
     * @param interchangeSyntaxVer The interchangeSyntaxVer to set.
     */
    public void setInterchangeSyntaxVer(int interchangeSyntaxVer) {
        this.interchangeSyntaxVer = interchangeSyntaxVer;
    }


    /**
     * @return Returns the preparationDate.
     */
    public LocalDate getPreparationDate() {
        return preparationDate;
    }


    /**
     * @param preparationDate The preparationDate to set.
     */
    public void setPreparationDate(LocalDate preparationDate) {
        this.preparationDate = preparationDate;
    }


    /**
     * @return Returns the recipientId.
     */
    public String getRecipientId() {
        return recipientId;
    }


    /**
     * @param recipientId The recipientId to set.
     */
    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }


    /**
     * @return Returns the senderId.
     */
    public String getSenderId() {
        return senderId;
    }


    /**
     * @param senderId The senderId to set.
     */
    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }


	/**
	 * @return Returns the messageSeqence.
	 */
	public long getMessageSequence() {
		return messageSequence;
	}


	/**
	 * @param messageSeqence The messageSeqence to set.
	 */
	public void setMessageSequence(long messageSeqence) {
		this.messageSequence = messageSeqence;
	}


	/**
	 * @return Returns the stationCode.
	 */
	public String getStationCode() {
		return stationCode;
	}


	/**
	 * @param stationCode The stationCode to set.
	 */
	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}


	/**
	 * @return the cgrResponseVOs
	 */
	public Collection<CGRResponseVO> getCgrResponseVOs() {
		return cgrResponseVOs;
	}


	/**
	 * @param cgrResponseVOs the cgrResponseVOs to set
	 */
	public void setCgrResponseVOs(Collection<CGRResponseVO> cgrResponseVOs) {
		this.cgrResponseVOs = cgrResponseVOs;
	}


	/**
	 * 	Getter for sequenceNum 
	 *	Added by : A-6287 on 03-Mar-2020
	 * 	Used for :
	 */
	public String getSequenceNum() {
		return sequenceNum;
	}


	/**
	 *  @param sequenceNum the sequenceNum to set
	 * 	Setter for sequenceNum 
	 *	Added by : A-6287 on 03-Mar-2020
	 * 	Used for :
	 */
	public void setSequenceNum(String sequenceNum) {
		this.sequenceNum = sequenceNum;
	}


	/**
	 * @return the errorCodes
	 */
	public Collection<String> getErrorCodes() {
		return errorCodes;
	}


	/**
	 * @param errorCodes the errorCodes to set
	 */
	public void setErrorCodes(Collection<String> errorCodes) {
		this.errorCodes = errorCodes;
	}




}
