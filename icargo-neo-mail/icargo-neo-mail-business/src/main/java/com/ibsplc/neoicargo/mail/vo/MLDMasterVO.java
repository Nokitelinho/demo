package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author A-3109
 */
@Setter
@Getter
public class MLDMasterVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String barcodeValue;
	private String containerType;
	private String companyCode;
	private String mailSource;
	private long messageSequence;
	private int serialNumber;
	private String barcodeType;
	private String weightCode;
	private Quantity weight;
	private String messageVersion;
	private String eventCOde;
	private String reasonCode;
	private ZonedDateTime scanTime;
	private String senderAirport;
	private String receiverAirport;
	private String destAirport;
	private String expectedInd;
	private String uldNumber;
	private String uldWeightCode;
	private Quantity uldWeight;
	private String lastUpdatedUser;
	private ZonedDateTime lastUpdateTime;
	private String processStatus;
	private String messagingMode;
	private String addrCarrier;
	private long mailSequenceNumber;
	boolean allocationRequired;
	boolean recRequired;
	boolean upliftedRequired;
	boolean hNdRequired;
	boolean dLVRequired;
	boolean sTGRequired;
	boolean nSTRequired;
	boolean rCFRequired;
	boolean tFDRequired;
	boolean rCTRequired;
	boolean rETRequired;
	private String transactionLevel;
	private ZonedDateTime msgTimUTC;
	private MLDDetailVO mldDetailVO;

	public boolean ishNdRequired() {
		return hNdRequired;
	}

	public void sethNdRequired(boolean hNdRequired) {
		this.hNdRequired = hNdRequired;
	}

	public boolean isdLVRequired() {
		return dLVRequired;
	}

	public void setdLVRequired(boolean dLVRequired) {
		this.dLVRequired = dLVRequired;
	}

	public boolean issTGRequired() {
		return sTGRequired;
	}

	public void setsTGRequired(boolean sTGRequired) {
		this.sTGRequired = sTGRequired;
	}

	public boolean isnSTRequired() {
		return nSTRequired;
	}

	public void setnSTRequired(boolean nSTRequired) {
		this.nSTRequired = nSTRequired;
	}

	public boolean isrCFRequired() {
		return rCFRequired;
	}

	public void setrCFRequired(boolean rCFRequired) {
		this.rCFRequired = rCFRequired;
	}

	public boolean istFDRequired() {
		return tFDRequired;
	}

	public void settFDRequired(boolean tFDRequired) {
		this.tFDRequired = tFDRequired;
	}

	public boolean isrCTRequired() {
		return rCTRequired;
	}

	public void setrCTRequired(boolean rCTRequired) {
		this.rCTRequired = rCTRequired;
	}

	public boolean isrETRequired() {
		return rETRequired;
	}

	public void setrETRequired(boolean rETRequired) {
		this.rETRequired = rETRequired;
	}
}
