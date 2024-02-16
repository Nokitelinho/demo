package com.ibsplc.neoicargo.stock.util.mock;

import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.business.shared.document.vo.SharedRangeVO;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.Pair;

public class MockProxyVOGenerator {
  public static DocumentVO getFullMockDocumentVO(
      String companyCode,
      String documentType,
      String documentSubType,
      Pair<String, String>... ranges) {
    final var documentVO = new DocumentVO();
    documentVO.setCompanyCode(companyCode);
    documentVO.setDocumentType(documentType);
    documentVO.setDocumentSubType(documentSubType);
    documentVO.setRange(
        Arrays.stream(ranges)
            .map(MockProxyVOGenerator::getFullMockSharedRangeVO)
            .collect(Collectors.toList()));
    return documentVO;
  }

  public static SharedRangeVO getFullMockSharedRangeVO(Pair<String, String> range) {
    final var sharedRangeVO = new SharedRangeVO();
    sharedRangeVO.setFromrange(range.getLeft());
    sharedRangeVO.setToRange(range.getRight());
    return sharedRangeVO;
  }
}
