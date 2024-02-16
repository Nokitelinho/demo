package com.ibsplc.neoicargo.stock.component.feature.findnextdocumentnumber;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.services.icargo.ServiceProxy;
import com.ibsplc.neoicargo.stock.mapper.DocumentFilterMapper;
import com.ibsplc.neoicargo.stock.model.DocumentValidation;
import com.ibsplc.neoicargo.stock.vo.filter.DocumentFilterVO;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Slf4j
@Component("findNextDocumentNumberFeature")
@RequiredArgsConstructor
public class FindNextDocumentNumberFeature {

  @Value("${EBL_URL_NBRIDGE}")
  private String eblUrl;

  public static final String FIND_NEXT_DOC_NUM_URL = "bookings/find/nextdocnum";

  private final ServiceProxy<Object> serviceProxy;
  private final DocumentFilterMapper documentFilterMapper;

  public DocumentValidation perform(DocumentFilterVO documentFilterVO) throws BusinessException {
    log.info("FindNextDocumentNumberFeature Invoked");
    DocumentValidation documentValidation;
    var documentNumberURL = eblUrl.concat(FIND_NEXT_DOC_NUM_URL);
    var documentFilter = documentFilterMapper.mapVoToModel(documentFilterVO);

    try {
      documentValidation =
          serviceProxy.dispatch(
              documentNumberURL, HttpMethod.POST, documentFilter, DocumentValidation.class);
    } catch (SystemException se) {
      log.error("FindNextDocumentNumberFeature Exception: ", se);
      throw new SystemException(se.getErrorCode(), se.getMessage());
    }

    if (Objects.isNull(documentValidation)) {
      throw new BusinessException(
          "EBL_BKG_015", "Stock does not exist for the agent. Cannot Proceed");
    }

    return documentValidation;
  }
}
