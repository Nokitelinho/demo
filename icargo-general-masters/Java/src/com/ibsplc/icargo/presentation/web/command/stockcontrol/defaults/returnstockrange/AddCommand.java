package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.returnstockrange;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ReturnStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ReturnStockForm;
/**
 * 
 * @author A-1952
 *
 */
public class AddCommand extends BaseCommand {
	
	private static final String BLANK_STRING = "";
	
	/**
	 * The execute method in BaseCommand
	 * @author A-1952
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		ReturnStockSession session = (ReturnStockSession) getScreenSession(
				"stockcontrol.defaults",
				"stockcontrol.defaults.returnstockrange");
	
		ReturnStockForm frm = (ReturnStockForm) invocationContext.screenModel;

		RangeVO row = new RangeVO();
		row.setStartRange(BLANK_STRING);
		row.setEndRange(BLANK_STRING);
		row.setNumberOfDocuments(0);

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
				if (!BLANK_STRING.equals(String.valueOf(noOfDocs[count]))) {
					range.setNumberOfDocuments(Long.valueOf(noOfDocs[count]));
				}

				count++;

			}
			rangeVO.add(row);

			session.setCollectionRangeVO(rangeVO);
		}
		
		invocationContext.target = "screenload_success";
	}
}