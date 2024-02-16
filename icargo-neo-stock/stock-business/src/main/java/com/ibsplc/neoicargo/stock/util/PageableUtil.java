package com.ibsplc.neoicargo.stock.util;

import com.ibsplc.neoicargo.stock.vo.PageableViewVO;
import java.util.List;
import lombok.experimental.UtilityClass;
import org.apache.commons.collections.CollectionUtils;

@UtilityClass
public class PageableUtil {

  public static Integer getTotalRecordCount(List<? extends PageableViewVO> results) {
    return CollectionUtils.isNotEmpty(results) ? results.get(0).getTotalRecordCount() : 0;
  }

  public static Integer getPageOffset(int pageNumber, int pageSize) {
    return (pageNumber - 1) * pageSize;
  }
}
