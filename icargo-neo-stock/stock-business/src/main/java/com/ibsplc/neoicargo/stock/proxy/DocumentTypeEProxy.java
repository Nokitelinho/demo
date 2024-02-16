package com.ibsplc.neoicargo.stock.proxy;

import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.business.shared.document.vo.SharedRangeVO;
import com.ibsplc.neoicargo.framework.ebl.api.EProductProxy;
import java.util.Collection;
import java.util.List;

@EProductProxy(module = "shared", submodule = "document", name = "documentTypeEProxy")
public interface DocumentTypeEProxy {
  Void validateRange(DocumentVO documentVO);

  List<SharedRangeVO> mergeRanges(DocumentVO documentVO);

  List<SharedRangeVO> splitRanges(
      Collection<SharedRangeVO> originalRanges, Collection<SharedRangeVO> availableRanges);
}
