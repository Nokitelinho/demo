package com.ibsplc.neoicargo.stock.exception;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorVO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class StockBusinessException extends BusinessException {

  private static final long serialVersionUID = -5763701795599498483L;

  public StockBusinessException(ErrorVO errorVO) {
    super(errorVO);
  }
}
