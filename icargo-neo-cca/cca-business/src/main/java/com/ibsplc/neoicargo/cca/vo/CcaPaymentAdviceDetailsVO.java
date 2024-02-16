package com.ibsplc.neoicargo.cca.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class CcaPaymentAdviceDetailsVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String shipmentPrefix;

    private String masterDocumentNumber;

    private String stationCode;

    private String paymentAdviceNumber;

    private String paymentAdviceType;

    private String accountNumber;

    private String customerName;

    private String paymentType;

    private Double finalAmount;

    private String customerCode;

    private Double totalAmount;

    private String status;

    private String importExportFlag;

}
