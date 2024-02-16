<%-------------------------------------------------------------------------------
* Project	 		: iCargo
* Module Code & Name: Uld
* File Name			: MailDiscrepancy.jsp
* Date				: 18-Jan-2007
* Author(s)			: A-2001
 --------------------------------------------------------------------------------%>

<%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.MailDiscrepancyVO" %>
<%@ include file="/jsp/includes/tlds.jsp" %>

<html:html locale="true">
<head> 
	
		<%@ include file="/jsp/includes/customcss.jsp" %>
<title>
	<common:message bundle="mailArrivalResources" key="mailtracking.defaults.discrepancy.lbl.title" />
</title>
<meta name="decorator" content="popup_panel">
<common:include src="/js/mail/operations/MailDiscrepancy_Script.jsp" type="script"/>

</head>
<body>
<bean:define id="form"
			 name="MailArrivalForm"
			 type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailArrivalForm"
             toScope="page" scope="request"/>

	<business:sessionBean id="mailDiscrepancyVOs"
			  moduleName="mail.operations"
			  screenID="mailtracking.defaults.mailarrival"
			  method="get"
			  attribute="mailDiscrepancyVOs" />
			  	<div class="iCargoPopUpContent" >
					<ihtml:form action="/mailtracking.defaults.disrepancy.loaddiscrepancy.do" styleClass="ic-main-form"> 
				<div class="ic-content-main">
				<div class="ic-head-container">
							<span class="ic-page-title ic-display-none"> <common:message key="mailtracking.defaults.discrepancy.lbl.pagetitle" /></span>
					
				</div>
				<div class="ic-main-container">
				<div class="ic-row">
					<div class="tableContainer" id="div1" style="height:150px;">
						<table class="fixed-header-table ic-pad-3">
				   <thead>
                          <tr class="iCargoTableHeadingLeft">
                            <td  class="iCargoTableDataTd" width="75%">
							    <common:message key="mailtracking.defaults.discrepancy.lbl.mailbagid" />
                            </td>
                            <td  class="iCargoTableDataTd" width="25%" >
                            	<common:message key="mailtracking.defaults.discrepancy.lbl.discrepancy" />
                            </td>

                          </tr>
                        </thead>
						                        <tbody>
							<logic:present name="mailDiscrepancyVOs">

								<logic:iterate id="mailDiscrepancyVO" name="mailDiscrepancyVOs" indexId="index">
							
									<tr >
									<td  class="iCargoTableDataTd" >
										<logic:present name="mailDiscrepancyVO" property="mailIdentifier">
											<bean:write name="mailDiscrepancyVO" property="mailIdentifier"/>
										</logic:present>
										</td>
									<td  class="iCargoTableDataTd" >
										<logic:present name="mailDiscrepancyVO" property="discrepancyType">
											<bean:write name="mailDiscrepancyVO" property="discrepancyType"/>
										</logic:present>
									</td>

								  </tr>
								 
							</logic:iterate>
			  			  </logic:present>
						                          </tbody>
				  </table>
				  </div>
				
				</div>
				
				<div class="ic-foot-container">
				<div class="ic-button-container">
				<ihtml:nbutton property="btnClose" componentID="BTN_MAILTRACKING_DEFAULTS_DISCREPANCY_CLOSE">
					<common:message key="mailtracking.defaults.discrepancy.btn.close" />
				</ihtml:nbutton>
				</div>
				</div>
				</div>
				</ihtml:form>
				</div>
	</body>
</html:html>