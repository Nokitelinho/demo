package com.ibsplc.icargo.business.master.defaults.orchestration;
import com.ibsplc.icargo.framework.web.command.suggest.vo.SuggestRequestVO;
import com.ibsplc.icargo.framework.web.command.suggest.vo.SuggestResponseVO;
public interface MasterTypeMapper {
public SuggestResponseVO getMappedVO();
public void setMappingVO(SuggestRequestVO requestVO);

}
