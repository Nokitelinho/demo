package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.carditenquiry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.CarditEnquiryModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ScreenLoadCommand extends AbstractCommand{

	private Log log = LogFactory.getLogger("OPERATIONS CARDITENQUIRY NEO");
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException,
			CommandInvocationException {
		this.log.entering("ScreenLoadCommand", "execute");
		LogonAttributes logonAttributes = getLogonAttribute();
	    CarditEnquiryModel carditEnquiryModel = (CarditEnquiryModel)actionContext.getScreenModel();
	    SharedDefaultsDelegate sharedDefaultsDelegate = 
	    	      new SharedDefaultsDelegate();
	     Map<String, Collection<OneTimeVO>> oneTimeValues = null;
	     Map<String, String> systemParameters = null;
	     Collection<String> parameterTypes = new ArrayList();
	     try
	    	 {
	    	      oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(
	    	        logonAttributes.getCompanyCode(), getOneTimeParameterTypes());
	    	     // systemParameters = sharedDefaultsDelegate.findSystemParameterByCodes(parameterTypes);
	    	    }
	    	    catch (BusinessDelegateException e)
	    	    {
	    	      actionContext.addAllError(handleDelegateException(e));
	    	    }
	     updateMilitaryClass((Collection<OneTimeVO>)oneTimeValues.get("mailtracking.defaults.mailclass"));
	     this.log.log(5, new Object[] { "oneTimeValues ---> ", oneTimeValues });
	     this.log.log(5, new Object[] { "LoginAirport ---> ", logonAttributes.getAirportCode() });
	     carditEnquiryModel.setOneTimeValues(MailOperationsModelConverter.constructOneTimeValues(oneTimeValues));
	     carditEnquiryModel.setAirportCode(logonAttributes.getAirportCode());
	     LocalDate currentDate = new LocalDate("***", Location.NONE, false);
	     carditEnquiryModel.setFromDate(currentDate.toDisplayDateOnlyFormat());
	     carditEnquiryModel.setToDate(currentDate.toDisplayDateOnlyFormat());
	     ResponseVO responseVO = new ResponseVO();
	     List<CarditEnquiryModel> results = new ArrayList();
	     results.add(carditEnquiryModel);
	     responseVO.setResults(results);
	     actionContext.setResponseVO(responseVO);
	     this.log.exiting("ScreenLoadCommand", "execute");
	}
	  private Collection<String> getOneTimeParameterTypes()
	  {
	    Collection<String> parameterTypes = new ArrayList();
	    parameterTypes.add("mailtracking.defaults.mailcategory");
		parameterTypes.add("mailtracking.defaults.postaladministration.resditversion");
	    parameterTypes.add("mailtracking.defaults.mailclass");
	    parameterTypes.add("mailtracking.defaults.mailstatus");
	    parameterTypes.add("mailtracking.defaults.resditevent");
	    parameterTypes.add("mailtracking.defaults.carditenquiry.flighttype");
	    parameterTypes.add("mail.operations.mailservicelevels");
	    parameterTypes.add("mailtracking.defaults.carditenquiry.awbattached");
	  //  parameterTypes.add("mailtracking.defaults.carditenquiry.searchmode");
	   
	    return parameterTypes;
	  }

	  private void updateMilitaryClass(Collection<OneTimeVO> mailClasses)
	  {
	    this.log.exiting("ScreenLoadCommand", "updateClass");
	    if ((mailClasses != null) && (mailClasses.size() > 0))
	    {
	      Collection<Collection<OneTimeVO>> mailClassVOs = 
	        new ArrayList();
	      String classDesc = null;
	      String fildDes = null;
	      Collection<OneTimeVO> militaryVOs = 
	        new ArrayList();
	      for (OneTimeVO mailClassVO : mailClasses)
	      {
	        if (mailClassVO.getFieldDescription().equals(classDesc))
	        {
	          militaryVOs.add(mailClassVO);
	        }
	        else
	        {
	          classDesc = mailClassVO.getFieldDescription();
	          militaryVOs = new ArrayList();
	          militaryVOs.add(mailClassVO);
	        }
	        if (militaryVOs.size() > 1)
	        {
	          String fieldDesc = ((OneTimeVO)((ArrayList)militaryVOs).get(0)).getFieldDescription();
	          if (!fieldDesc.equals(fildDes))
	          {
	            mailClassVOs.add(militaryVOs);
	            fildDes = fieldDesc;
	          }
	        }
	      }
	      if (mailClassVOs.size() > 0) {
	        for (Collection<OneTimeVO> oneTimeVOs : mailClassVOs) {
	          if (oneTimeVOs.size() > 0)
	          {
	            mailClasses.removeAll(oneTimeVOs);
	            StringBuilder oneTimeVal = new StringBuilder();
	            for (OneTimeVO militaryVO : oneTimeVOs) {
	              oneTimeVal.append(militaryVO.getFieldValue()).append(",");
	            }
	            OneTimeVO mailClassVO = (OneTimeVO)oneTimeVOs.iterator().next();
	            mailClassVO.setFieldValue(
	              oneTimeVal.substring(0, oneTimeVal.length() - 1).toString());
	            mailClasses.add(mailClassVO);
	          }
	        }
	      }
	    }
	    this.log.exiting("ScreenLoadCommand", "updateClass");
	  }

}
