package com.ibsplc.icargo.presentation.web.controller.mail.mra.gpareporting.invoicenquiry;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.controller.AbstractActionController;
import com.ibsplc.icargo.framework.web.spring.resource.ICargoResourceBundle;
import com.ibsplc.icargo.presentation.web.model.mail.mra.gpareporting.InvoicEnquiryModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;


/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.controller.mail.mra.gpareporting.invoicenquiry.InvoicEnquiryActionController.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7929	:	25-Nov-2018	:	Draft
 */




@Controller
@RequestMapping("mail/mra/gpareporting/invoicenquiry")
public class InvoicEnquiryActionController extends AbstractActionController<InvoicEnquiryModel> {

	
	
	
    @Resource(name="invoicEnquiryResourceBundle")
    ICargoResourceBundle invoicEnquiryResourceBundle;

	
	@RequestMapping("/screenload")
	public @ResponseBody ResponseVO fetchDetailsOnScreenload(@RequestBody InvoicEnquiryModel invoicEnquiryModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.mra.gpareporting.ux.invoicenquiry.fetchdetailsonscreenload",invoicEnquiryModel);
	}
	
	
	@RequestMapping("/list")
	public @ResponseBody ResponseVO fetchInvoicDetailsForEnquiry(@RequestBody InvoicEnquiryModel invoicEnquiryModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.mra.gpareporting.ux.invoicenquiry.fetchmailbagdetailsforenquiry",invoicEnquiryModel);
	}
	
	@RequestMapping("/reprocess")
	public @ResponseBody ResponseVO reprocessInvoicMails(@RequestBody InvoicEnquiryModel invoicEnquiryModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.mra.gpareporting.ux.invoicenquiry.reprocessinvoicmails",invoicEnquiryModel);
	}
	
	@RequestMapping("/save")
	public @ResponseBody ResponseVO fetchClaimAmountForSave(@RequestBody InvoicEnquiryModel invoicEnquiryModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.mra.gpareporting.ux.invoicenquiry.fetchclaimamountforsave",invoicEnquiryModel);
	}

	@RequestMapping("/saveRemarks")
	public @ResponseBody ResponseVO fetchRemarksForSave(@RequestBody InvoicEnquiryModel invoicEnquiryModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.mra.gpareporting.ux.invoicenquiry.fetchremarksforsave",invoicEnquiryModel);
	}
	@RequestMapping("/saveGroupRemarks")
	public @ResponseBody ResponseVO fetchgroupremarksforsave(@RequestBody InvoicEnquiryModel invoicEnquiryModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.mra.gpareporting.ux.invoicenquiry.fetchgroupremarksforsave",invoicEnquiryModel);
	}
	@RequestMapping("/raiseClaim")
	public @ResponseBody ResponseVO raiseClaim(@RequestBody InvoicEnquiryModel invoicEnquiryModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.mra.gpareporting.ux.invoicenquiry.raiseClaim",invoicEnquiryModel);
	}
	@RequestMapping("/accept")
	public @ResponseBody ResponseVO acceptClaim(@RequestBody InvoicEnquiryModel invoicEnquiryModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.mra.gpareporting.ux.invoicenquiry.acceptClaim",invoicEnquiryModel);
	}

	@RequestMapping("/moveto")
	public @ResponseBody ResponseVO moveto(@RequestBody InvoicEnquiryModel invoicEnquiryModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.mra.gpareporting.ux.invoicenquiry.moveto",invoicEnquiryModel);
	}

	@Override
	public ICargoResourceBundle getResourceBundle() {
		return invoicEnquiryResourceBundle;
		
		
		
		
	}
	}


