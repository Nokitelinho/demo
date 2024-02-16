
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations;

import java.util.ArrayList;

import com.ibsplc.icargo.business.mail.operations.vo.CoTerminusVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;


public interface MailPerformanceSession extends ScreenSession {
	
	 /**
     * @return ArrayList<PartnerCarrierVO>
     */
    ArrayList<CoTerminusVO> getCoTerminusVOs();
    
    /**
     * @param partnerCarrierVOs
     */
    void setCoTerminusVOs(ArrayList<CoTerminusVO> coTerminusVOs);

    /**
     * @return ArrayList<PartnerCarrierVO>
     */
    String getAirport();
    
    /**
     * @param partnerCarrierVOs
     */
    void setAirport(String Airport);
    
    public void setResditModes(ArrayList<OneTimeVO> resditModes);
    public void removeResditMode();
    public ArrayList<OneTimeVO> getResditModes() ;
    
}
