/**
 * 
 */
package com.ibsplc.neoicargo.framework.icgsupport.utils.suggest;

import com.ibsplc.neoicargo.framework.icgsupport.vo.AbstractVO;

import lombok.Getter;
import lombok.Setter;

/**
 * @author A-1759
 *
 */
@Getter
@Setter
public class SuggestResponseVO extends AbstractVO {

	String code;
	String description;
}
