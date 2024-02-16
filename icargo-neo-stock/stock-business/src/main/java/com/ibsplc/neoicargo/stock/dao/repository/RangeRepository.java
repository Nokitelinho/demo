package com.ibsplc.neoicargo.stock.dao.repository;

import com.ibsplc.neoicargo.stock.dao.entity.Range;
import com.ibsplc.neoicargo.stock.vo.filter.RangeFilterVO;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RangeRepository extends JpaRepository<Range, Long> {

  @Query(
      value =
          "select * from stkrng "
              + "where cmpcod = :#{#filterVO.companyCode} "
              + "and arlidr = :#{#filterVO.airlineIdentifier} "
              + "and doctyp = :#{#filterVO.documentType} "
              + "and docsubtyp = :#{#filterVO.documentSubType} "
              + "and stkhldcod = :#{#filterVO.stockHolderCode} "
              + "and (ascstarng between :#{#filterVO.asciiStartRange} "
              + "and :#{#filterVO.asciiEndRange} "
              + "or ascendrng between :#{#filterVO.asciiStartRange} "
              + "and :#{#filterVO.asciiEndRange} "
              + "or :#{#filterVO.asciiStartRange} between ascstarng and ascendrng "
              + "or :#{#filterVO.asciiEndRange} between ascstarng and ascendrng)",
      nativeQuery = true)
  Optional<Range> findRange(@Param("filterVO") RangeFilterVO filterVO);
}
