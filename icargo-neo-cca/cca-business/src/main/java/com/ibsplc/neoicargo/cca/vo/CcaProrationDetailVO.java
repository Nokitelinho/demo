package com.ibsplc.neoicargo.cca.vo;

import com.ibsplc.neoicargo.common.modal.BaseModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CcaProrationDetailVO extends BaseModel {

    private static final long serialVersionUID = 1L;

    private String flightDate;

    private String flownCarrier;

    private String payableCarrier;

    private String flightNumber;

    private String sectorFrom;

    private String sectorTo;

    private String payReceive;

    private String prorationType;

    private double prorationPercentage;

    private double freightCharge;

    private double iscPercentage;

    private double amount;

    private double otherCharges;

    private double ccFees;

    private double netAmount;

    private int pieces;

    private double grossWeight;

    private double chargeWeight;

    private String debitCreditFlag;

    private double otherChargesSecurity;

    private double otherChargesFuel;

    private double otherChargesTotal;

}
