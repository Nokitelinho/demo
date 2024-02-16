package com.ibsplc.neoicargo.cca.exception;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorVO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CcaBusinessException extends BusinessException {

    private static final long serialVersionUID = -5763701795599498483L;

    public CcaBusinessException(ErrorVO errorVO) {
        super(errorVO);
    }
}
