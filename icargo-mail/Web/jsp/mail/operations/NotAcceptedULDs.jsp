<%--
/***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  Mailtracking
* File Name				:  NotAcceptedULDs.jsp
* Date					:  26-June-2006
* Author(s)				:  A-2047
*************************************************************************/
--%>


 <%@ page language="java" %>
 <%@ include file="/jsp/includes/tlds.jsp"%>
 <%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAcceptanceForm"%>
 <%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO"%>

	 <html:html>

 <head> 
		
			
	
 	<title><common:message bundle="mailAcceptanceResources" key="mailtracking.defaults.notacceptedulds.lbl.title" /></title>
 	<meta name="decorator" content="popup_panel">
 	<common:include type="script" src="/js/mail/operations/NotAcceptedULDs_Script.jsp"/>
 </head>

  <body>
	
	

  	<bean:define id="form"
  		 name="MailAcceptanceForm"
  		 type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAcceptanceForm"
 		 toScope="page" />

 	<business:sessionBean id="containerDetailsVOs"
 		 moduleName="mail.operations"
 		 screenID="mailtracking.defaults.mailacceptance"
 		 method="get"
 	 	 attribute="containerDetailsVOs"/>

 <div class="iCargoPopUpContent">

		<ihtml:form action="/mailtracking.defaults.mailacceptance.closeflight.do"  styleClass="ic-main-form">
		<ihtml:hidden property="fromScreen" />
		<ihtml:hidden property="uldsSelectedFlag" />
		<ihtml:hidden property="uldsPopupCloseFlag" />
		
	<div class="ic-content-main">
		<span class="ic-page-title ic-display-none">
			<common:message key="mailtracking.defaults.notacceptedulds.lbl.pagetitle" />
		</span>
		<div class="ic-main-container">	
			<div class="ic-row">
				<div class="tableContainer" id="div1"   style="width:100%; height:230px; ">

					<table  class="fixed-header-table" >
					<!--DWLayoutTable-->
						<thead>
							<tr class="iCargoTableHeadingLeft">
								<td width="6%" height="21" class="iCargoTableHeaderLabel">
									<input type="checkbox" name="checkAll" value="checkbox">
								</td>
								<td width="30%" class="iCargoTableHeaderLabel">
									<common:message key="mailtracking.defaults.notacceptedulds.lbl.uldno" />
								</td>
								<td width="32%" class="iCargoTableHeaderLabel">
									<common:message key="mailtracking.defaults.notacceptedulds.lbl.pou" />
								</td>
								<td width="32%" class="iCargoTableHeaderLabel">
									<common:message key="mailtracking.defaults.notacceptedulds.lbl.destination" />
								</td>
							</tr>
						</thead>
						<tbody>
							<logic:present name="containerDetailsVOs">
								<logic:iterate id ="containerDetailsVO" name="containerDetailsVOs" type="ContainerDetailsVO" indexId="rowCount">
								
										<tr class=iCargoTableDataRow1 >
											<td class="iCargoTableDataTd" >
												<div class="ic-center">
													<ihtml:checkbox property="selectULDs" value="<%=String.valueOf(rowCount)%>"/>
												</div>
											</td>
											<td class="iCargoTableDataTd ic-center" >
												<bean:write name="containerDetailsVO" property="containerNumber"/>
											</td>
											<td class="iCargoTableDataTd ic-center" >
												<bean:write name="containerDetailsVO" property="pou"/>
											</td>
											<td class="iCargoTableDataTd ic-center" >
												<bean:write name="containerDetailsVO" property="destination"/>
											</td>
										</tr>
									
								</logic:iterate>
							</logic:present>
						</tbody>
					</table>
				</div>	
			</div>
		</div>	
		<div class="ic-foot-container">
			<div class="ic-button-container">		
				<ihtml:nbutton property="btOk" componentID="BUT_MAILTRACKING_DEFAULTS_NOTACCEPEDULDS_OK">
					<common:message key="mailtracking.defaults.notacceptedulds.btn.ok" />
				</ihtml:nbutton>

				<ihtml:nbutton property="btCancel" componentID="BTN_MAILTRACKING_DEFAULTS_ACCEPTMAIL_CANCEL">
					<common:message key="mailtracking.defaults.notacceptedulds.btn.cancel" />
				</ihtml:nbutton>
			</div>
		</div>
	</div>
		                      	
		</ihtml:form>
</div>

 		
	</body>

 </html:html>




