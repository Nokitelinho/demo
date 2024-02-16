package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.outbound.report;

import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.spring.command.AbstractPrintCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.OutboundModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOutboundModelConverter;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import java.util.ArrayList;
import java.util.Map;


public class CN46PrintCommand  extends AbstractPrintCommand{
  private static final Log LOGGER = LogFactory.getLogger("MAILTRACKING");
  private static final String REPORT_ID = "RPRMAL002";
  
  private static final String PRODUCTCODE = "mail";
  private static final String SUBPRODUCTCODE = "operations";
  private static final String ACTION = "generateCN46ReportForFlightlevel";
  
  public void execute(ActionContext actionContext) throws BusinessDelegateException {
    OutboundModel outboundModel = (OutboundModel)actionContext.getScreenModel();
    
    LogonAttributes logonAttributes = getLogonAttribute();
    
    OperationalFlightVO operationalFlightVO = null;
    operationalFlightVO = MailOutboundModelConverter.constructOperationalFlightVO(outboundModel.getMailAcceptance(), logonAttributes);
    operationalFlightVO.setConsignemntPresent(findSystemParameterValue("mail.operations.printCN46forAllMailbags"));
    operationalFlightVO.setFromScreen(MailConstantsVO.MAIL_OUTBOUND_SCRIDR);
    ReportSpec reportSpec = getReportSpec();
    reportSpec.setReportId(REPORT_ID);
    reportSpec.setProductCode(PRODUCTCODE);
    reportSpec.setSubProductCode(SUBPRODUCTCODE);
    reportSpec.setPreview(true);
    reportSpec.addFilterValue(operationalFlightVO);
    reportSpec.setResourceBundle("cN46ManifestResources");
    reportSpec.setAction(ACTION);


    
    try {
      generateReport(actionContext);
    } catch (CommandInvocationException e) {
    	LOGGER.log(Log.FINE, "REPORT ERROR",e);
    } 
  }
  
  private static String findSystemParameterValue(String syspar) throws BusinessDelegateException {
    String sysparValue = null;
    ArrayList<String> systemParameters = new ArrayList<>();
    systemParameters.add(syspar);
    Map<String, String> systemParameterMap = new SharedDefaultsDelegate()
      .findSystemParameterByCodes(systemParameters);
    if (systemParameterMap != null) {
      sysparValue = systemParameterMap.get(syspar);
    }
    return sysparValue;
  }
}

