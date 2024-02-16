package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.viewrange;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ViewRangeSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ViewRangeForm;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author a-5160
 * The Class ListAWBStockCommand.java.
 */
public class ListAWBStockCommand extends BaseCommand {
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {	
		
		ViewRangeForm viewRangeForm = (ViewRangeForm) invocationContext.screenModel;
		ViewRangeSession sessionView = (ViewRangeSession)getScreenSession("stockcontrol.defaults",
 											"stockcontrol.defaults.viewrange");
		HashMap<Integer, List<String>> awbStockMap = new HashMap<Integer, List<String>>();
		List<String> awbStock = null;
		List<String> awbNumbers = null;
		int count = 0;
		int multiplier = 0;
		int addMultiplier = 0;
		int defaultPageSize = 0;
		int startIndex = 0;
		int endIndex = 0;
		Integer pageNumber = 1;
		int checkDigit=7;
		int displayPage = Integer.parseInt(viewRangeForm.getDisplayPage());
		Collection<String> utilisedDocuments = null;
		ArrayList<RangeVO> ranges = (ArrayList<RangeVO>) sessionView.getStockRangeVO().getAvailableRanges();
		RangeVO selectedRange = ranges.get(Integer.parseInt(viewRangeForm.getConfirmedCheck()));		
		// Added by A-5639 for BUG_ICRD-ICRD-249334
		if(!"exportexcel".equals(viewRangeForm.getActiontype())){
			multiplier = 25;
			addMultiplier = 25;
			defaultPageSize = 25;
		}else if("exportexcel".equals(viewRangeForm.getActiontype())){
			multiplier = 75;
			addMultiplier = 75;
			defaultPageSize = 75;
			startIndex = 75 * (displayPage - 1) + 1;
			endIndex = 75 * displayPage + 1;
		}	
		
		utilisedDocuments= selectedRange.getMasterDocumentNumbers();
		
		awbStock = new ArrayList<String>();
		for(int i=Integer.parseInt(selectedRange.getStartRange().substring(0,7));i<=Integer.parseInt(selectedRange.getEndRange().substring(0,7));i++){
			String currentDoc = String.format("%07d",i);
			StringBuilder awbNumber= new StringBuilder();
			awbNumber.append(currentDoc).append(i%checkDigit);
			if(count>multiplier){
				pageNumber++;
				//multiplier = multiplier+25;
				multiplier = multiplier + addMultiplier;
			}
			
			if(utilisedDocuments==null || !utilisedDocuments.contains(currentDoc)){
				//StringBuilder range= new StringBuilder();									
				//int appendRange = i%checkDigit ;
				//range.append(selectedDoc);
				//.append(appendRange);
				awbStock.add(awbNumber.toString());
				if(awbStockMap.containsKey(pageNumber)){
					awbNumbers = awbStockMap.get(pageNumber);									
					awbNumbers.add(awbNumber.toString());
					awbStockMap.put(pageNumber, awbNumbers);
				}
				else{
					awbNumbers = new ArrayList<String>();									
					awbNumbers.add(awbNumber.toString());
					awbStockMap.put(pageNumber, awbNumbers);
				}
			count++;
			}							
			
		}	
		if(awbStock.size()>0){
			sessionView.setTotalRecords(awbStock.size());
		}
		Page<String> page = null;
		if (awbStock.size() > 0
				&& !"exportexcel".equals(viewRangeForm.getActiontype())) {		
			boolean hasNextPage = false;
			if (pageNumber > 1 && displayPage != pageNumber) {
			hasNextPage = true;
			}
			page = new Page<String>(awbStockMap.get(displayPage),
					Integer.parseInt(viewRangeForm.getDisplayPage()),
					defaultPageSize, awbStockMap.get(displayPage).size(),
					pageNumber, multiplier + 1, hasNextPage,
					sessionView.getTotalRecords());
		} else if (awbStock.size() > 0
				&& "exportexcel".equals(viewRangeForm.getActiontype())) {
			// Added by A-5153 for BUG_ICRD-ICRD-192150
			// page = new
			// Page<String>(awbStock,Integer.parseInt(viewRangeForm.getDisplayPage()),76,awbStock.size(),pageNumber,multiplier+1,hasNextPage,sessionView.getTotalRecords());
			boolean isNextPage = false;
			if (sessionView.getTotalRecords() > displayPage * defaultPageSize) {
				isNextPage = true;
			}
			page = new Page<String>(awbStockMap.get(displayPage),
					Integer.parseInt(viewRangeForm.getDisplayPage()),
					defaultPageSize, awbStockMap.get(displayPage).size(),
					startIndex, endIndex, isNextPage,
					sessionView.getTotalRecords());
		}
		sessionView.setAWBStockPage(page);
	
		invocationContext.target = "screenload_success";
	}
	

}