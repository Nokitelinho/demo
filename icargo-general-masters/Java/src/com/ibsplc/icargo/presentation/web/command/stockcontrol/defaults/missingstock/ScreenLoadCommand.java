package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.missingstock;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.TransitStockVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ConfirmStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.MissingStockForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
*
* @author A-4443
*
*/
public class ScreenLoadCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
		// TODO Auto-generated method stub
		log.entering("ScreenLoadCommandMissingStockPopUp","execute");
		ConfirmStockSession session= getScreenSession( "stockcontrol.defaults","stockcontrol.defaults.confirmstock");
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
	    LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		MissingStockForm missingStockForm =(MissingStockForm)invocationContext.screenModel;
		session.setMissingStockVOs(null);
		Collection<TransitStockVO> selectedTransitStocks = session.getSelectedTransitStockVOs();
		TransitStockVO stock = (TransitStockVO)((ArrayList)selectedTransitStocks).get(0);
		
		if("confirmStock".equals(missingStockForm.getFromScreen())){
			ConfirmStockSession confirmStockSession= getScreenSession( 
			    	"stockcontrol.defaults","stockcontrol.defaults.confirmstock");
			Collection transitStockDetails = new ArrayList();
			transitStockDetails = confirmStockSession.getSelectedTransitStockVOs();
			TransitStockVO transitStockVO = new TransitStockVO();
			if(transitStockDetails.size()!=0){
			transitStockVO = (TransitStockVO)((ArrayList)transitStockDetails).get(0);
			missingStockForm.setStockHolderCode(stock.getStockHolderCode());
			missingStockForm.setStartRange(stock.getMissingStartRange());
			missingStockForm.setEndRange(stock.getMissingEndRange());
			}
		}
		/* String row[] = missingStockForm.getCheck();
		 log.log(log.FINE,"<<<<<<<<<<------Selected filterVO------>>>>>>>>>");
	        int size = row.length;
	        int count = 0;
	       TransitStockVO transitStockVO = null;
	        Collection<TransitStockVO> transitStockVOs = null;
	        transitStockVOs = session.getTransitStockVOs();
	        for(int i = 0; i < size; i++)
	            if(transitStockVOs != null)
	            {
	                int idx = Integer.parseInt(row[i]);
	                transitStockVO = (TransitStockVO)((ArrayList)transitStockVOs).get(idx);
	            }

	        log.log(5, (new StringBuilder()).append("<<<<<<<<<<------Selected filterVO------>>>>>>>>>").append(transitStockVO).toString());
	        Collection transitStockDetails = new ArrayList();
	        transitStockDetails.add(transitStockVO);
	        session.setSelectedTransitStockVOs(transitStockDetails);
		*/
		 invocationContext.target = "screenloadpopup_success";
	}

}
