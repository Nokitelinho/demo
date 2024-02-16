package com.ibsplc.neoicargo.stock.component.feature.validatestock.validator;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.services.icargo.ServiceProxy;
import com.ibsplc.neoicargo.framework.orchestration.Validator;
import com.ibsplc.neoicargo.stock.mapper.DocumentFilterMapper;
import com.ibsplc.neoicargo.stock.model.DocumentValidation;
import com.ibsplc.neoicargo.stock.vo.filter.DocumentFilterVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Slf4j
@Component("blacklistValidator")
@RequiredArgsConstructor
public class BlackListValidator extends Validator<DocumentFilterVO> {

  @Value("${EBL_URL_NBRIDGE}")
  private String eblUrl;

  public static final String CHECK_BLACKLIST_URL = "bookings/check/blacklist/";

  private final ServiceProxy<Object> serviceProxy;
  private final DocumentFilterMapper documentFilterMapper;

  public void validate(DocumentFilterVO documentFilterVO) throws BusinessException {
    log.info("blacklistValidator Invoked");
    var validateDocumentUrl =
        eblUrl.concat(CHECK_BLACKLIST_URL).concat(documentFilterVO.getMstdocnum());
    var documentFilter = documentFilterMapper.mapVoToModel(documentFilterVO);

    var documentValidation =
        serviceProxy.dispatch(
            validateDocumentUrl, HttpMethod.POST, documentFilter, DocumentValidation.class);

    if (documentValidation.isBlacklistFlag()) {
      throw new BusinessException("AWB_BLACKLISTED", "AWB is blacklisted");
    }
  }
}
