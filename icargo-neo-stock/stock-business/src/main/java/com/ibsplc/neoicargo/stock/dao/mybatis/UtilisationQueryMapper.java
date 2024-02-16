package com.ibsplc.neoicargo.stock.dao.mybatis;

import com.ibsplc.neoicargo.stock.vo.filter.UtilisationFilterVO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UtilisationQueryMapper {

  long findStockUtilisationForRange(@Param("utilisationFilterVO") UtilisationFilterVO filter);

  List<Long> validateStockPeriod(@Param("utilisationFilterVO") UtilisationFilterVO filter);
}
