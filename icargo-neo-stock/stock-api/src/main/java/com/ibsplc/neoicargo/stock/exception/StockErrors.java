package com.ibsplc.neoicargo.stock.exception;

import com.ibsplc.neoicargo.framework.core.lang.error.ErrorType;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorVO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StockErrors {

  // MOVE TO PROPERTIE FILE TO SUPPORT LOCALIZATION
  NEO_STOCK_001("NEO_STOCK_001", "Utilisation exists for the selected ranges."),
  NEO_STOCK_002("NEO_STOCK_002", "End range less than start range."),
  NEO_STOCK_003("NEO_STOCK_003", "Stock not found."),
  NEO_STOCK_004("NEO_STOCK_004", "Stock Holder does not exist."),
  NEO_STOCK_005("NEO_STOCK_005", "Range {0} contains blacklisted stock."),
  NEO_STOCK_006("NEO_STOCK_006", "Range {0} not found."),
  NEO_STOCK_007("NEO_STOCK_007", "Stock request not found."),
  NEO_STOCK_008("NEO_STOCK_008", "StockHolder already deleted."),
  NEO_STOCK_009("NEO_STOCK_009", "{0} cannot be reused within {1} years."),
  NEO_STOCK_010("NEO_STOCK_010", "Range {0} already exists in the system."),
  NEO_STOCK_011("NEO_STOCK_011", "Invalid Stock Holder."),
  NEO_STOCK_013("NEO_STOCK_013", "Range {0} already exists in the system."),
  NEO_STOCK_014("NEO_STOCK_014", "HeadQuarter Already Defined."),
  NEO_STOCK_015("NEO_STOCK_015", "stockcontrol.defaults.duplicateagentfound"),
  NEO_STOCK_016("NEO_STOCK_016", "Duplicate Stock holder exists."),
  NEO_STOCK_017("NEO_STOCK_017", "Cannot delete stock holders having stock."),
  NEO_STOCK_018("NEO_STOCK_018", "Invalid stock Format."),
  NEO_STOCK_019(
      "NEO_STOCK_019",
      "The status of the selected request is not NEW is displayed. Cancelation is not completed."),
  NEO_STOCK_020("NEO_STOCK_020", "Already BlackListed Range."),
  NEO_STOCK_021("NEO_STOCK_021", "Invalid Stock Holder For Agent."),
  NEO_STOCK_022("NEO_STOCK_022", "Invalid Stockholder For Monitor."),
  NEO_STOCK_023("NEO_STOCK_023", "AWB number not found in anystock"),
  NEO_STOCK_024("NEO_STOCK_024", "Stock holder not found for agent"),
  NEO_STOCK_025("NEO_STOCK_025", "no stock found for stockholder"),
  NEO_STOCK_026("NEO_STOCK_026", "AWB stock not found for stock holder"),
  NEO_STOCK_027("NEO_STOCK_027", "AWB not existing in any range for stockholder"),
  NEO_STOCK_028("NEO_STOCK_028", "courier not found in any stock"),
  NEO_STOCK_029("NEO_STOCK_029", "courier not available with stockholder"),
  NEO_STOCK_030("NEO_STOCK_030", "EBT not found in any stock"),
  NEO_STOCK_031("NEO_STOCK_031", "EBT not available with stockholder"),
  NEO_STOCK_032("NEO_STOCK_032", "Agent mapping exists for stockholder"),
  NEO_STOCK_033("NEO_STOCK_033", "Stock Holder is an approver"),
  NEO_STOCK_034("NEO_STOCK_034", "Range exists for stock holder"),
  NEO_STOCK_035("NEO_STOCK_035", "Document Owner does not matched with Stockholder Code");

  private final String errorCode;
  private final String errorMessage;

  public static ErrorVO constructErrorVO(
      final String errorCode, final String message, ErrorType error) {
    final var errorVO = new ErrorVO();
    errorVO.setErrorCode(errorCode);
    errorVO.setDefaultMessage(message);
    errorVO.setErrorType(error);
    return errorVO;
  }

  public static ErrorVO constructErrorVO(StockErrors error, ErrorType errorType) {
    final var errorVO = new ErrorVO();
    errorVO.setErrorCode(error.errorCode);
    errorVO.setDefaultMessage(error.errorMessage);
    errorVO.setErrorType(errorType);
    return errorVO;
  }

  public static ErrorVO constructErrorVO(
      final String errorCode,
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
