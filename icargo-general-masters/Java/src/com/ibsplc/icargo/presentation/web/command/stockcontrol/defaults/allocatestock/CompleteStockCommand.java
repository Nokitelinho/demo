/**
 *
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.allocatestock;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.AllocateStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.AllocateStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;


/**
 * @author A-1870
 *
 */
public class CompleteStockCommand extends BaseCommand {

	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	    ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
	        LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		    AllocateStockSession session= getScreenSession( "stockcontrol.defaults","stockcontrol.defaults.allocatestock");
			AllocateStockForm allocateStockForm=(AllocateStockForm)invocationContext.screenModel;
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			errors = validateForm(allocateStockForm,session);
			 if (errors != null && errors.size() > 0) {
					invocationContext.addAllError(errors);
					invocationContext.target = "reject_failure";
					return;
				}
			Page<StockRequestVO> pageStockRequestVO=session.getPageStockRequestVO();
			String[] chk=allocateStockForm.getCheckBox();
			Collection<StockRequestVO> stockRequestVo=new ArrayList<StockRequestVO>();
			for(int i=0;i<chk.length;i++){
				for(StockRequestVO stockRequestVO:pageStockRequestVO){
					if(stockRequestVO!=null){
						if(stockRequestVO.getRequestRefNumber().equals(chk[i])){
							stockRequestVO.setCompanyCode(logonAttributes.getCompanyCode());
							stockRequestVO.setLastUpdateUser(logonAttributes.getUserId());
							stockRequestVo.add(stockRequestVO);

						}
					}
				}
			}

			try{
				StockControlDefaultsDelegate stockControlDefaultsDelegate=new StockControlDefaultsDelegate();
				stockControlDefaultsDelegate.completeStockRequests(stockRequestVo);

			}catch(BusinessDelegateException businessDelegateException){
//printStackTrrace()();
   }
                   invocationContext.target ="list_success";
}
private Collection<ErrorVO> validateForm(AllocateStockForm allocateStockForm, AllocateStockSession session){


		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;

		Page<StockRequestVO> pageStockRequestVO=session.getPageStockRequestVO();
		String[] chk=allocateStockForm.getCheckBox();

			for(StockRequestVO StockRequestvo:pageStockRequestVO){
				if(chk[0].equals(StockRequestvo.getRequestRefNumber())){
					 long allocatedStock=StockRequestvo.getAllocatedStock();

					 if(allocatedStock==0){
						    error = new ErrorVO("stockcontrol.defaults.reqcanbecompletedonlyifallocatedstkexists");
							error.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(error);
					 }

					break;

					 }

			}



		return errors;
	}
}
