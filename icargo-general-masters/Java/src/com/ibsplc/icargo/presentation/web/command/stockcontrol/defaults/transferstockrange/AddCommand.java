package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.transferstockrange;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.TransferStockForm;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.TransferStockSession;

/**
 * 
 * @author A-1885
 * 
 */
public class AddCommand extends BaseCommand {
	/**
	 * The execute method of Base Command
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * 
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		TransferStockForm frm = (TransferStockForm) invocationContext.screenModel;

		RangeVO row = new RangeVO();
		row.setStartRange("");
		row.setEndRange("");
		row.setNumberOfDocuments(0);

		TransferStockSession session = (TransferStockSession) getScreenSession(
				"stockcontrol.defaults",
				"stockcontrol.defaults.transferstockrange");

		if (session.getCollectionRangeVO() == null) {

			Collection<RangeVO> rangeVO = new ArrayList<RangeVO>();
			rangeVO.add(row);
			session.setCollectionRangeVO(rangeVO);

		} else {

			Collection<RangeVO> rangeVO = session.getCollectionRangeVO();
			String[] rangeFrom = frm.getRangeFrom();
			String[] rangeTo = frm.getRangeTo();
			String[] noOfDocs = frm.getNoofDocs();

			int count = 0;

			for (RangeVO range : rangeVO) {

				range.setStartRange(rangeFrom[count]);
				range.setEndRange(rangeTo[count]);
				if (!"".equals(String.valueOf(noOfDocs[count]))) {
					range.setNumberOfDocuments(Integer
							.parseInt(noOfDocs[count]));
				}

				count++;

			}
			rangeVO.add(row);

			session.setCollectionRangeVO(rangeVO);
		}

		invocationContext.target = "screenload_success";
	}
}