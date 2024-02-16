/**
 * 
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.maintainstockholder;

import java.util.ArrayList;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.MaintainStockHolderSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.MaintainStockHolderForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1870
 *
 */
public class DeleteRowCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	private static final String OPERATION_FLAG_DELETE = "D";
	private static final String OPERATION_FLAG_INSERT = "I";
	/**
	 * The execute method in BaseCommand
	 * @author A-1754
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		//log.entering("DeleteComdtyCommand","execute");
		/**
		 * Added by A-4772 for ICRD-9882.Changed the 
		 * Screen id value as per standard for UISK009
		 */
		MaintainStockHolderSession session= getScreenSession( "stockcontrol.defaults","stockcontrol.defaults.maintainstockholder");
		MaintainStockHolderForm maintainStockHolderForm=(MaintainStockHolderForm)invocationContext.screenModel;
		String[] checked=maintainStockHolderForm.getCheckBox();
		ArrayList<StockVO> allChecked = (ArrayList<StockVO>)session.getStockVO();
		ArrayList<StockVO> newSet = 
			new ArrayList<StockVO>(session.getStockVO());
		log.log(Log.FINE,
				"***********************length**********************",
				checked.length);
		for(int i=0;i<checked.length;i++){
			log.log(Log.FINE, "****checked[i]--------------------", checked, i);
			int count = Integer.parseInt(checked[i]);
			StockVO vo = (StockVO)allChecked.get(count);
			if(OPERATION_FLAG_INSERT.equals(vo.getOperationFlag())){
				newSet.remove(vo);
			}else{ 
				newSet.remove(vo);
				vo.setOperationFlag(OPERATION_FLAG_DELETE);
				newSet.add(vo);
			}
		}
		
		if(newSet.size()==0){
			session.setStockVO(null);
		}else{
			session.setStockVO(newSet);
		}
		
		invocationContext.target = "delete_success";
	}
	
}
