package com.ibsplc.neoicargo.cca.util;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.masters.ParameterService;
import com.ibsplc.neoicargo.masters.ParameterType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.ibsplc.neoicargo.cca.util.CcaUtil.isNullOrEmpty;

@Slf4j
@Component
@RequiredArgsConstructor
public class CcaParameterUtil {

    private final ParameterService parameterService;

    public String getSystemParameter(String parameterCode, ParameterType parameterType) {
        return isNullOrEmpty(parameterCode)
                ? null
                : getParameter(parameterCode, parameterType);
    }

    private String getParameter(String parameterCode, ParameterType parameterType) {
        try {
            return parameterService.getParameter(parameterCode, parameterType);
        } catch (BusinessException e) {
            log.warn("Cannot get parameter. Cause: ", e);
            return null;
        }
    }
}