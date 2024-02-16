package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.returnstockrange;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ReturnStockForm;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ReturnStockSession;

/**
 * 
 * @author A-1952
 * 
 */
public class DeleteCommand extends BaseCommand {
	
	private static final String BLANK_STRING="";
	/**
	 * Overriding the execute method in BaseCommand
	 * 
	 * @author A-1952
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		ReturnStockForm frm = (ReturnStockForm) invocationContext.screenModel;

		ReturnStockSession session = (ReturnStockSession) getScreenSession(
				"stockcontrol.defaults",
				"stockcontrol.defaults.returnstockrange");

		String[] rangeFrom = frm.getRangeFrom();
		String[] rangeTo = frm.getRangeTo();
		String[] noOfDocs = frm.getNoofDocs();

		String[] check = frm.getCheck();

		Collection<RangeVO> rangeVO = session.getCollectionRangeVO();
		int count = 0;

		for (RangeVO range : rangeVO) {

			range.setStartRange(rangeFrom[count]);
			range.setEndRange(rangeTo[count]);
			if (!BLANK_STRING.equals(String.valueOf(noOfDocs[count]))) {
				range.setNumberOfDocuments(Integer.parseInt(noOfDocs[count]));
			}

			count++;

		}

		ArrayList<RangeVO> rangeVOList = (ArrayList<RangeVO>) session
				.getCollectionRangeVO();
		ArrayList<RangeVO> rangeVONewList = new ArrayList<RangeVO>(session
				.getCollectionRangeVO());

		if (rangeVOList != null && rangeVOList.size()>0) {
			for (int i = 0; i < check.length; i++) {

				RangeVO vo = rangeVOList.get(Integer.parseInt(check[i]));
				rangeVONewList.remove(vo);

			}

		}

		session.setCollectionRangeVO(rangeVONewList);

		invocationContext.target = "screenload_success";
	}

}
