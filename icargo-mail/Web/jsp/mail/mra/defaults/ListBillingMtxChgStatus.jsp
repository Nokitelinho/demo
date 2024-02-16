<%--******************************************************************
* Project	 				: iCargo
* Module Code & Name				: MRA.DEFAULTS
* File Name					: CopyBillingLine.jsp
* Date						: 26-Dec-2022
* Author(s)					: Teena
 *********************************************************************--%>

<%
	response.setDateHeader("Expires",0);
	response.setHeader("Pragma","no-cache");

	if (request.getProtocol().equals("HTTP/1.1")) {
		response.setHeader("Cache-Control","no-cache");
	}
%>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import = "java.util.Calendar" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListBillingMatrixForm" %>

	
	
<html:html>
<head> 
	 
		
<title>
		<common:message bundle="listbillingmatrix" key="mailtracking.mra.defaults.changestatuspopup.pagetitle" />
	</title>
<meta name="decorator" content="popuppanelrestyledui">
<common:include type="script" src="/js/mail/mra/defaults/ListBillingMtxChgStatus_Script.jsp" />
</head>
<body>
		<logic:present name="popup">
			<div id="walkthroughholder"> </div>
		</logic:present>
	
	
	



<div class="iCargoPopUpContent" style="width:600px;">
 <ihtml:form action="/mailtracking.mra.defaults.listbillingmatrix.changestatuspopup.do" styleClass="ic-main-form" >
 <ihtml:hidden property="changedStatus"/>
 
  

 <div class="ic-content-main">
		
	<div class="container">
		
		
	    <div class="ic-main-container" style="width:570px;> <!--Modified by A-8236 as part of ICRD-250611 -->
		  
		    <div class="ic-row paddL10" >
			<br>
			<table style = "border:.1px groove grey;width:550px;height:110px;border-radius:2px;" id = "newtable">
		    <tr>
		    <td>
					
					<div class="ic-input ic-split-40 ic-mandatory">
					
														
					</div>
					<div class="ic-input ic-split-50 ic-mandatory">
					<label><common:message  key="mailtracking.mra.defaults.listbillingmatrix.status" /></label>
					<business:sessionBean id="OneTimeValues"
									  moduleName="mailtracking.mra.defaults"
									  screenID="mailtracking.mra.defaults.listbillingmatrix"
									  method="get"
									  attribute="oneTimeValues" />

									<ihtml:select property="status" componentID="CMP_MRA_DEFAULTS_LISTBILLINGMATRIX_STATUS" tabindex="3">
										<html:option value="--Select--"></html:option>
										<logic:present name="OneTimeValues" >
										   <bean:define id="oneTimeValuesMap" name="OneTimeValues" type="java.util.HashMap" />
										   <logic:iterate id="oneTimeValues" name="oneTimeValuesMap">
										      <logic:equal name="oneTimeValues" property="key" value="mra.gpabilling.ratestatus" >
										        <logic:iterate id="oneTimeVO" name="oneTimeValues" property="value" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
											   <html:option value="<%=oneTimeVO.getFieldValue()%>"><%=oneTimeVO.getFieldDescription()%></html:option>
										        </logic:iterate>
										      </logic:equal>
							                            </logic:iterate>
										</logic:present>


									</ihtml:select>
														
					</div>
			</td>
			</tr>
		  </table>
		    </div>
		</div>
		
		
		
		
		
		
		
		
		
		<div class="ic-foot-container paddR5">
			<div class="ic-button-container">	
			<ihtml:nbutton property="btnSave" componentID="CMP_MRA_DEFAULTS_LISTBILLINGMATRIX_SAVE_BTN" >
			<common:message  key="mailtracking.mra.defaults.listbillingmatrix.btn.save" />
			</ihtml:nbutton>
           <ihtml:nbutton property="btnClose" componentID="CMP_MRA_DEFAULTS_CLOSE" onclick="window.close();">
		   <common:message  key="mailtracking.mra.defaults.changestatus.button.close"  />
		   </ihtml:nbutton>
		</div>
		</div>
		
		
		</div>
		
		
</ihtml:form>
</div>	
		
	</body>
</html:html>