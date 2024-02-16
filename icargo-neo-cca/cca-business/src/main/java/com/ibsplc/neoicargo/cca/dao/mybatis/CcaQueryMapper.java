package com.ibsplc.neoicargo.cca.dao.mybatis;

import com.ibsplc.neoicargo.cca.modal.CcaDataFilter;
import com.ibsplc.neoicargo.cca.modal.CcaSelectFilter;
import com.ibsplc.neoicargo.cca.vo.CCAListViewFilterVO;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.cca.vo.CcaListViewVO;
import com.ibsplc.neoicargo.cca.vo.CcaNumbersNodeVO;
import com.ibsplc.neoicargo.cca.vo.GetCcaListMasterVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface CcaQueryMapper {

    CCAMasterVO getCCADetails(@Param("ccaDataFilter") CcaDataFilter ccaDataFilter);

    List<CCAMasterVO> getRelatedCCA(@Param("ccaDataFilter") CcaDataFilter ccaDataFilter);

    Set<GetCcaListMasterVO> getCCAList(@Param("ccaDataFilters") List<CcaDataFilter> ccaDataFilters);

    List<CcaListViewVO> findCcaMasterListView(@Param("ccaListViewFilterVO") CCAListViewFilterVO ccaListViewFilterVO,
                                              @Param("limit") int limit, @Param("offset") int offset);

    Long findTotalElements(@Param("ccaListViewFilterVO") CCAListViewFilterVO ccaListViewFilterVO);

    List<CcaNumbersNodeVO> getCcaNumbers(
            @Param("ccaSelectFilter") CcaSelectFilter ccaSelectFilter,
            @Param("companyCode") String companyCode);

}
