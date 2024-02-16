/**
 * 
 */
package com.ibsplc.neoicargo.framework.icgsupport.utils.suggest;

/**
 * @author A-1759
 *
 */
public interface MasterTypeMapper {

	 SuggestResponseVO getMappedVO();
	  
	 void setMappingVO(SuggestRequestVO paramSuggestRequestVO);
}
