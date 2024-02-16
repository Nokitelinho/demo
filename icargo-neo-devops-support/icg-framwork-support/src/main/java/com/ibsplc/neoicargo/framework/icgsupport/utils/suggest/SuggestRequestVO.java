/**
 * 
 */
package com.ibsplc.neoicargo.framework.icgsupport.utils.suggest;

import java.util.Map;

import com.ibsplc.neoicargo.framework.icgsupport.vo.AbstractVO;

import lombok.Getter;
import lombok.Setter;

/**
 * @author A-1759
 *
 */
@Getter
@Setter
public class SuggestRequestVO extends AbstractVO {

	String masterType;
	String codeVal;
	String decriptionKey;
	String isMutiple;
	Map<String, String> customFilter;
}
