/**
 * 
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.approverlov;

import java.util.ArrayList;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.MaintainStockHolderSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ApproverLovForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1870
 *
 */
public class SelectFromLovCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		
		log.entering("SelectFromLovCommand","lovcommand");
		
		ApproverLovForm approverLovForm = (ApproverLovForm) invocationContext.screenModel;
		/**
		 * Added by A-4772 for ICRD-9882.Changed the 
		 * Screen id value as per standard for UISK009
		 */
		MaintainStockHolderSession session= getScreenSession( "stockcontrol.defaults","stockcontrol.defaults.maintainstockholder");
		String[] checkBox=approverLovForm.getCheckBox();
		ArrayList<StockVO> stockVo=(ArrayList<StockVO>)session.getStockVO();
		int id=Integer.parseInt(session.getId());
		
		StockVO stockVO=new StockVO();
		stockVO=stockVo.get(id);
		stockVO.setStockApproverCode(checkBox[0]);
		session.setStockVO(stockVo);
		approverLovForm.setSelectedValues("Y");
		approverLovForm.setIsValueSelected(Boolean.valueOf(true));
		invocationContext.target = "list_success";
	}

}
