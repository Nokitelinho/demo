package com.ibsplc.neoicargo.cca.exception;

import com.ibsplc.neoicargo.framework.core.lang.error.ErrorType;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorVO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CcaErrors {

    NEO_CCA_001("NEO_CCA_001", "Duplicated charge"),
    NEO_CCA_002("NEO_CCA_002", "Amount cannot be empty"),
    NEO_CCA_003("NEO_CCA_003", "Revised pieces cannot be zero"),
    NEO_CCA_004("NEO_CCA_004", "Origin and Destination cannot be Same"),
    NEO_CCA_005("NEO_CCA_005", "Revised Origin cannot be blank"),
    NEO_CCA_006("NEO_CCA_006", "Revised Destination cannot be blank"),
    NEO_CCA_007("NEO_CCA_007", "Revised Gross Wt cannot be zero"),
    NEO_CCA_008("NEO_CCA_008", "Revised Chg.Wt cannot be zero"),

    NEO_CCA_010("NEO_CCA_010", "Inbound Customer is mandatory for CC/CP/PC AWB's"),
    NEO_CCA_011("NEO_CCA_011", "Inbound Customer not valid at the Destination Airport"),
    NEO_CCA_012("NEO_CCA_012", "CCA Reason is mandatory"),
    NEO_CCA_013("NEO_CCA_013", "Freight Charges should be zero for Service Cargo Shipments"),
    NEO_CCA_014("NEO_CCA_014", "CCA not found"),
    NEO_CCA_015("NEO_CCA_015", "Only CCA's in New status can be Deleted"),
    NEO_CCA_016("NEO_CCA_016", "CCA's can't be Authorized"),
    NEO_CCA_017("NEO_CCA_017", "Gross Weight is greater than Chargeable Weight"),
    NEO_CCA_018("NEO_CCA_018", "Exchange rate missing"),
    NEO_CCA_019("NEO_CCA_019",
            "Please provide the Inbound Customer Details, for supporting Charge Collect – Payment Type"),
    NEO_CCA_020("NEO_CCA_020", "Please provide the Outbound Customer Details"),
    NEO_CCA_021("NEO_CCA_021", "Inbound details are wrong");

    private final String errorCode;
    private final String errorMessage;

    public static ErrorVO constructErrorVO(final String errorCode,
                                           final String message, ErrorType error) {
        final var errorVO = new ErrorVO();
        errorVO.setErrorCode(errorCode);
        errorVO.setDefaultMessage(message);
        errorVO.setErrorType(error);
        return errorVO;
    }

    public static ErrorVO constructErrorVO(CcaErrors error, ErrorType errorType) {
        final var errorVO = new ErrorVO();
        errorVO.setErrorCode(error.errorCode);
        errorVO.setDefaultMessage(error.errorMessage);
        errorVO.setErrorType(errorType);
        return errorVO;
    }

    public static ErrorVO constructErrorVO(final String errorCode,
                                           final String message,
                                           final ErrorType error,
                                           final String[] errorData) {
        final var errorVO = new ErrorVO();
        errorVO.setErrorCode(errorCode);
        errorVO.setErrorGroup(errorCode);
        errorVO.setDefaultMessage(message);
        errorVO.setErrorType(error);
        errorVO.setErrorData(errorData);
        return errorVO;
    }
}