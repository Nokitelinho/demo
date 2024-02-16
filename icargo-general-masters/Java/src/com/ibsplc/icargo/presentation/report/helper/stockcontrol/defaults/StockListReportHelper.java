	package com.ibsplc.icargo.presentation.report.helper.stockcontrol.defaults;


import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.framework.report.helper.Help;
import com.ibsplc.icargo.framework.report.helper.Helper;

/**
 * @author A-1747
 *
 */
public class StockListReportHelper {

	@Helper(
			{
					@Help(
							reportId = "RPRSTK003",
							voNames = {"com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockVO",
									"com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockVO"},
							fieldNames = {"reorderLevel","totalStock"}
					)
			}
	)


	/**
	 * Static method to perform the conversion of Local Date to String
	 */
	public static String formatLongVal(Object value, Collection extraInfo) {
		
		String val="";
		if(value!=null){
			val=String.valueOf(value);
		}
		return val;
	}

	@Helper(
			{
					@Help(
							reportId = "RPRSTK003",
							voNames = {"com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockFilterVO",
									"com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockFilterVO"},
							fieldNames = {"documentSubType","documentType"}
					)
			}
	)
	/**
	 * Static method to perform the conversion of Local Date to String
	 */
	public static String formatDoctype(Object value, Collection extraInfo) {

		
		String doctype="";
		Object values= ((ArrayList)extraInfo).get(0);
		Collection<DocumentVO> documentVOs = (Collection<DocumentVO>)values;
		for(DocumentVO documentVO:documentVOs){
			   if(documentVO.getDocumentSubType().equals(value)){
				   doctype = documentVO.getDocumentSubTypeDes();
			   }
		   }

		return doctype;
	}




}
