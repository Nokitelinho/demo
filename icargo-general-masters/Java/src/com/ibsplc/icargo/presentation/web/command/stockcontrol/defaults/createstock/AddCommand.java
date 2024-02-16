package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.createstock;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.CreateStockForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.CreateStockSession;
/**
 * @author A-1952
 *
 */
public class AddCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
/**
 * The execute method in BaseCommand
 * @author A-1952
 * @param invocationContext
 * @throws CommandInvocationException
 */

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("AddCommand","execute");

		CreateStockForm frm = (CreateStockForm) invocationContext.screenModel;

		RangeVO row = new RangeVO();
		row.setStartRange("");
		row.setEndRange("");
		row.setNumberOfDocuments(0);

		CreateStockSession session =(CreateStockSession) getScreenSession( "stockcontrol.defaults",
		                                                           "stockcontrol.defaults.createstock");

		if(session.getCollectionRangeVO()==null){

			Collection<RangeVO> rangeVO = new ArrayList<RangeVO> ();
			rangeVO.add(row);
			session.setCollectionRangeVO(rangeVO);

		}
		else{

				Collection<RangeVO> rangeVO = session.getCollectionRangeVO();
				String[] rangeFrom=frm.getRangeFrom();
				String[] rangeTo=frm.getRangeTo();
				String[] noOfDocs=frm.getNoOfDocs();

                int count=0;

				   for(RangeVO range:rangeVO){

						range.setStartRange(rangeFrom[count]);
						range.setEndRange(rangeTo[count]);
						if(!"".equals(String.valueOf(noOfDocs[count]))){
							range.setNumberOfDocuments(Integer.parseInt(noOfDocs[count]));
						}

						count++;

			}
			rangeVO.add(row);

		session.setCollectionRangeVO(rangeVO);
	}
	
	if("popup".equals(session.getMode())){
		invocationContext.target = "screenloadpopup_success";
	} else {
		log.exiting("AddCommand","execute");
	}
	invocationContext.target = "screenload_success";
}
}