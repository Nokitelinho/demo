<%--/***********************************************************************
* Project	     	 : iCargo
* Module Code & Name 	 : mra
* File Name          	 : ChangeBillingStatus.jsp
* Date                 	 : 5-Jan-2009
* Author(s)              : A-3434
*************************************************************************/
--%>
<%@ page language="java" %>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import = "java.util.Calendar" %>
<%@ page import="com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO"%>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ChangeBillingStatusPopupForm" %>
<%@ page import="com.ibsplc.icargo.presentation.web.session.impl.mail.mra.defaults.ChangeBillingStatusSessionImpl" %>

<bean:define id="form"
		 name="ChangeBillingStatusPopupForm"
		 type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ChangeBillingStatusPopupForm"
		 toScope="page" />
		
			
	
<html:html>
<head> 
	
		
			
	

<%@ include file="/jsp/includes/customcss.jsp" %>
<title><common:message bundle="<%=form.getBundle()%>" key="mailtracking.mra.defaults.changestatus.title" /></title>
<meta name="decorator" content="popup_panel">

<common:include type="script" src="/js/mail/mra/defaults/ChangeBillingStatus_Script.jsp" />
</head>

<body>
	
	

<business:sessionBean id="onetimemap"
	  	moduleName="mailtracking.mra.defaults"
	  	screenID="mailtracking.mra.defaults.changestatus"
		method="get" attribute="BillingStatus" />


<div class="iCargoPopUpContent" style="height:200px; overflow:auto;">
  <ihtml:form action="/mailtracking.mra.defaults.changestatuspopup.do" styleclass="ic-main-form">

<ihtml:hidden property="select"/>
<ihtml:hidden property="screenStatus"/>
<ihtml:hidden property="despatchNumbers"/>
<ihtml:hidden property="fromScreen"/>
<ihtml:hidden property="isBillable"/>
    <div class="ic-content-main">
	    <span class="ic-page-title">
		    <common:message key="mailtracking.mra.defaults.changestatus.chgbillingstatus" />
		</span> 
	        <div class="ic-main-container">
			    <div class="ic-row">
				    <div class="ic-border">
					    <div class="ic-input ic-label-40 ic-split-80 ">
						    <label>
							    <common:message key="mailtracking.mra.defaults.changestatus.dsn" />:
							</label>
							    <div style="word-wrap:break-word;white-space: pre-wrap;"><common:write name="form" property="popupDespatchNumber"/></div>
						</div>
					</div>
				</div>
			    <div class="ic-row">
				    <div class="ic-input ic-label-33  ic-split-60 ">
						<label>
						    <common:message key="mailtracking.mra.defaults.changestatus.billingstatus" />
						</label>
						     <ihtml:select componentID="CMP_MRA_CHGSTATUS_BILLINGSTATUS" property="billingStatus">
								<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
								 <logic:present name="onetimemap">
									<logic:iterate id="oneTimeValue" name="onetimemap">
									<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
									<logic:equal name="parameterCode" value="mra.airlinebilling.billingstatus">
									<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
									<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
									<logic:present name="parameterValue" property="fieldValue">
									<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
									<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
									<ihtml:option value="<%=String.valueOf(fieldValue).toUpperCase() %>"><%=String.valueOf(fieldDescription)%></ihtml:option>
									</logic:present>
									</logic:iterate>
									</logic:equal>
									</logic:iterate>
									</logic:present>

								</ihtml:select>
					</div>
			    </div>
				<div class="ic-row">
				   
					
					 <div class="ic-col-78">	   
						<label>
						    <common:message key="mailtracking.mra.defaults.changestatus.remarks" />
						</label>					 
					 <ihtml:textarea  styleClass="iCargoTextAreaMedium" property="popupRemarks"  componentID="TXTAREA_MRA_CHGSTATUS_REMARKS"  cols="60" rows="3" >
					 		</ihtml:textarea>
						</div>	
					
				</div>
 			</div>
			<div class="ic-foot-container">
			    <div class="ic-button-container paddR5">
				    <ihtml:nbutton property="btnSave" componentID="CMP_MRA_CHGSTATUS_SAVE" >
					    <common:message key="mailtracking.mra.defaults.changestatus.button.save" />
					</ihtml:nbutton>
					<ihtml:nbutton property="btnClose" componentID="CMP_MRA_CHGSTATUS_CLOSE" >
					    <common:message key="mailtracking.mra.defaults.changestatus.button.close" />
					</ihtml:nbutton>
				</div>
			</div>
	
	</div>
</ihtml:form>
</div>

		

	</body>
</html:html>
