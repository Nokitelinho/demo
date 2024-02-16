/**
 *
 */
package com.ibsplc.neoicargo.tracking.exception;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorVO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TrackingBusinessException extends BusinessException {

    public TrackingBusinessException(ErrorVO errorVO) {
        super(errorVO.getErrorCode(), errorVO.getDefaultMessage());
    }

    public TrackingBusinessException(List<ErrorVO> errorsList) {
        super(errorsList);
    }

    public TrackingBusinessException(TrackingErrors error) {
        super(error.getErrorCode(), error.getErrorMessage());
    }

}
