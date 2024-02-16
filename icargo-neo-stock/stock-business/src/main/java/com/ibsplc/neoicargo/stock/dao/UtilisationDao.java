package com.ibsplc.neoicargo.stock.dao;

import com.ibsplc.neoicargo.stock.vo.StockAllocationVO;
import com.ibsplc.neoicargo.stock.vo.filter.UtilisationFilterVO;
import java.util.List;

public interface UtilisationDao {

  long findStockUtilisationForRange(UtilisationFilterVO utilisationFilterVO);

  List<Long> validateStockPeriod(StockAllocationVO stockAllocationVO, int stockIntroductionPeriod);
}
