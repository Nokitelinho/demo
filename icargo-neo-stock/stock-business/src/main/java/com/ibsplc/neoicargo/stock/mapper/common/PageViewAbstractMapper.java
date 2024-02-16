package com.ibsplc.neoicargo.stock.mapper.common;

import com.ibsplc.neoicargo.common.modal.BaseModel;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import java.util.List;

/**
 * A parent generic interface that provides common abstract methods for converting between Model and
 * Page objects
 *
 * @param <M> Model class object
 */
public interface PageViewAbstractMapper<M extends BaseModel> {

  default Page<M> mapVosToPageView(
      List<M> results,
      Integer pageNumber,
      Integer actualPageSize,
      Integer totalRecordCount,
      Integer defaultPageSize) {
    int startIndex = (pageNumber - 1) * defaultPageSize + 1;
    int endIndex = startIndex + defaultPageSize - 1;
    boolean hasNextPage = endIndex < totalRecordCount;

    return new Page<>(
        results,
        pageNumber,
        defaultPageSize,
        actualPageSize,
        startIndex,
        endIndex,
        hasNextPage,
        totalRecordCount);
  }
}
