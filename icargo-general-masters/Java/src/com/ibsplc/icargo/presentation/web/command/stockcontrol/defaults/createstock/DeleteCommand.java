package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.createstock;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.CreateStockForm;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.CreateStockSession;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-1952
 *
 */
public class DeleteCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
/**
 * The execute method in BaseCommand
 * @author A-1952
 * @param invocationContext
 * @throws CommandInvocationException
 */

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		CreateStockForm frm = (CreateStockForm) invocationContext.screenModel;

		CreateStockSession session =(CreateStockSession) getScreenSession( "stockcontrol.defaults",
		                                                        "stockcontrol.defaults.createstock");


		log.entering("DeleteCommand","execute");
		String[] rangeFrom=frm.getRangeFrom();
		String[] rangeTo=frm.getRangeTo();
		String[] noOfDocs=frm.getNoOfDocs();

		String[] check = frm.getCheck();

		Collection<RangeVO> rangeVO = session.getCollectionRangeVO();
		int count=0;

	    for(RangeVO range:rangeVO){

			range.setStartRange(rangeFrom[count]);
			range.setEndRange(rangeTo[count]);
			if(!"".equals(String.valueOf(noOfDocs[count]))){
				range.setNumberOfDocuments(Integer.parseInt(noOfDocs[count]));
			}

			count++;

       }


		ArrayList<RangeVO> rangeVOList = (ArrayList<RangeVO>)session.getCollectionRangeVO();
		ArrayList<RangeVO> rangeVONewList = new ArrayList<RangeVO>(session.getCollectionRangeVO());

		if(rangeVOList!=null){
		for(int i=0; i<check.length; i++){

			RangeVO vo = rangeVOList.get(Integer.parseInt(check[i]));
			rangeVONewList.remove(vo);

		  }


		}

		session.setCollectionRangeVO(rangeVONewList);

		if("popup".equals(session.getMode())){
			invocationContext.target = "screenloadpopup_success";
		} else {
			log.exiting("DeleteCommand","execute");
		}
		invocationContext.target = "screenload_success";
	}

}
