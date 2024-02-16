package com.ibsplc.neoicargo.stock.component.feature.validatestock;

import static com.ibsplc.neoicargo.stock.util.StockConstant.HYPHEN;

import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.services.icargo.ServiceProxy;
import com.ibsplc.neoicargo.framework.orchestration.AbstractFeature;
import com.ibsplc.neoicargo.framework.orchestration.FeatureConfigSource;
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
@Component("validateStockFeature")
@FeatureConfigSource("stock/validatestock")
@RequiredArgsConstructor
public class ValidateStockFeature extends AbstractFeature<DocumentFilterVO> {

  @Value("${EBL_URL_NBRIDGE}")
  private String eblUrl;

  public static final String AWB_STOCK_CHECK_URL = "bookings/customer/awb/stockcheck/";

  private final ServiceProxy<Object> serviceProxy;
  private final DocumentFilterMapper documentFilterMapper;

  @Override
  public DocumentValidation perform(DocumentFilterVO documentFilterVO) {
    log.info("validateStockFeature Invoked");
    var validateDocumentUrl =
        eblUrl
            .concat(AWB_STOCK_CHECK_URL)
            .concat(documentFilterVO.getPrefix())
            .concat(HYPHEN)
            .concat(documentFilterVO.getMstdocnum());
    var documentFilter = documentFilterMapper.mapVoToModel(documentFilterVO);

    var documentValidation =
        serviceProxy.dispatch(
            validateDocumentUrl, HttpMethod.POST, documentFilter, DocumentValidation.class);

    if (Objects.isNull(documentValidation)) {
      throw new SystemException("EBL_BKG_027", "No data found");
    } else {
      documentValidation.setDocumentNumber(
          documentFilterVO.getPrefix() + HYPHEN + documentFilterVO.getMstdocnum());
    }

    return documentValidation;
  }
}
