package com.ibsplc.icargo.presentation.web.command.products.defaults.listfeedback;


import java.util.StringTokenizer;

import com.ibsplc.icargo.business.products.defaults.vo.ProductFeedbackVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.ListProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.FeedbackForm;

import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * ScreenloadProductLovCommdand is for screenload action of productLov
 * @author a-1870
 *
 */
public class ViewCommand extends BaseCommand{
	
	/**
     * Log
     */
    private Log log = LogFactory.getLogger("PRODUCTS.DEFAULTS");
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		ListProductSessionInterface session = 
		getScreenSession( "product.defaults","products.defaults.listproducts");
		FeedbackForm feedbackForm = 
				(FeedbackForm)invocationContext.screenModel;
		feedbackForm.setMode("Y");
		String prdCode=feedbackForm.getCode();
		log.log(Log.FINE, "\n\n\n -----prdCode---->", prdCode);
		StringTokenizer tok = new StringTokenizer(prdCode, "-");
		int count = 0;
		String code="";
		String feedbackid="";
		while (tok.hasMoreTokens()) {
			log.log(Log.FINE,"\n\n\n -----has more tokens>" );
			code = tok.nextToken();
			log.log(Log.FINE, "\n\n\n -----code-------->", code);
			feedbackid= tok.nextToken();
			log.log(Log.FINE, "\n\n\n -----feedbackid------->", feedbackid);
			
		}

		
		
		Page<ProductFeedbackVO> page=session.getPageProductFeedbackVO();
		for(ProductFeedbackVO vo:page){
		
		log.log(Log.FINE, "\n\n\n -----vo.getProductCode()---->", vo.getProductCode());
		log.log(Log.FINE, "\n\n\n -----code---->", code);
		log.log(Log.FINE, "\n\n\n ----vo.getFeedbackId()---->", vo.getFeedbackId());
		log.log(Log.FINE, "\n\n\n ----feedbackid--->", feedbackid);
		log.log(Log.FINE, "\n\n\n ---code.equals(vo.getProductCode())->", code.equals(vo.getProductCode()));
		log.log(Log.FINE, "\n\n\n ---feedbackid.equals(vo.getFeedbackId())->",
				feedbackid.trim().equals(String.valueOf(vo.getFeedbackId()).trim()));
			if(code.trim().equals(vo.getProductCode().trim()) && feedbackid.trim().equals(String.valueOf(vo.getFeedbackId()).trim())){
				log.log(
						Log.FINE,"\n\n\n ----setting to form--->" );
				log.log(Log.FINE, "\n\n\n ----vo.getAddress()--->", vo.getAddress());
				feedbackForm.setAddress(vo.getAddress());
				log.log(Log.FINE, "\n\n\n ----vo.getName()--->", vo.getName());
				feedbackForm.setName(vo.getName());
				log
						.log(Log.FINE, "\n\n\n ----getRemarksm--->", vo.getRemarks());
				feedbackForm.setComments(vo.getRemarks());
				log.log(Log.FINE, "\n\n\n ----vo.getEmailId()--->", vo.getEmailId());
				feedbackForm.setEmail(vo.getEmailId());
				
			}
		}
		invocationContext.target="view_success";
	}
	
}
