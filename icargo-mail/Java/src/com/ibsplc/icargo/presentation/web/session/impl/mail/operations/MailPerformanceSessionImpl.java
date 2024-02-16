
package com.ibsplc.icargo.presentation.web.session.impl.mail.operations;

import java.util.ArrayList;

import com.ibsplc.icargo.business.mail.operations.vo.CoTerminusVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailPerformanceSession;


public class MailPerformanceSessionImpl extends AbstractScreenSession implements
		MailPerformanceSession {

	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.mailperformance";
	
	private static final String CO_TERMINUS_VOS = "coTerminusVOs";
	private static final String AIRPORT = "airport";
	private static final String KEY_RESDIT = "resditevent";
	/**
	 * @return Screen Id
	 */
	public String getScreenID() {
		
		return SCREEN_ID;
	}

	/**
	 * @return Module Name
	 */
	public String getModuleName() {
		
		return MODULE_NAME;
	}
	
	/**
     * @return ArrayList<CoTerminusVO>
     */
	public ArrayList<CoTerminusVO> getCoTerminusVOs() {
		return (ArrayList<CoTerminusVO>)getAttribute(CO_TERMINUS_VOS);
	}
	
	/**
     * @param coTerminusVOs
     */
	public void setCoTerminusVOs(ArrayList<CoTerminusVO> coTerminusVOs) {
		setAttribute(CO_TERMINUS_VOS,coTerminusVOs);
	}

	/**
	 * @return String Airport
	 */
	public String getAirport() {
		return (String)getAttribute(AIRPORT);
	}
	
	/**
     * @param Airport
     */
	public void setAirport(String Airport) {
		setAttribute(AIRPORT,Airport);
		
	}	

	public void setResditModes(ArrayList<OneTimeVO> resditModes) {
		setAttribute(KEY_RESDIT, resditModes);
	}
	
	public void removeResditMode() {
		removeAttribute(KEY_RESDIT);
	}
	public ArrayList<OneTimeVO> getResditModes() {
		return (ArrayList<OneTimeVO>)getAttribute(KEY_RESDIT);
	}
}
