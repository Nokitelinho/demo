
<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  Mailtracking
* File Name				:  EmptyULDs.jsp
* Date					:  04-Aug-2006
* Author(s)				:  A-2047
*************************************************************************/
 --%>


 <%@ page language="java" %>
 <%@ include file="/jsp/includes/tlds.jsp"%>
 <%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.operations.EmptyULDsForm"%>
 <%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO"%>
 <%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>

	 <html:html>

 <head> 
		
			
	
 	<title><common:message bundle="emptyULDResources" key="mailtracking.defaults.emptyulds.lbl.title" /></title>
 	<meta name="decorator" content="popup_panel">
 	<common:include type="script" src="/js/mail/operations/EmptyULDs_Script.jsp"/>
 </head>

 <body>
	
	

  	<bean:define id="form"
  		 name="EmptyULDsForm"
  		 type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.EmptyULDsForm"
		 toScope="page" />

	<business:sessionBean id="containerDetailsVOs"
		 moduleName="mail.operations"
		 screenID="mailtracking.defaults.emptyulds"
		 method="get"
	 	 attribute="containerDetailsVOs"/>

	<div class="iCargoPopUpContent" style="overflow:auto;height:300px;">
		<ihtml:form action="/mailtracking.defaults.emptyulds.screenload.do" styleclass="ic-main-form">

			<ihtml:hidden property="status" />
			<ihtml:hidden property="fromFlightNumber" />
			<ihtml:hidden property="fromFlightCarrierCode" />
			<ihtml:hidden property="frmassignTo" />
			<ihtml:hidden property="frmFlightDate" />
			<ihtml:hidden property="fromdestination" />
			<ihtml:hidden property="fromScreen" />
			<ihtml:hidden property="toScreen" />
			<div class="ic-content-main">
				<span class="ic-page-title ic-display-none">
					<common:message key="mailtracking.defaults.emptyulds.lbl.heading" />
				</span>
				<div class="ic-main-container">
				<div class="ic-row">
					<h3>
					<common:message key="mailtracking.defaults.emptyulds.lbl.details" />
					</h3>
				</div>
					<div class="ic-row">
						<div class="tableContainer" id="div1" style="width:100%; height:180px; ">
							<table id="tabl" width="100%" class="fixed-header-table">
								<thead>
								<tr class="ic-th-all">
										<th style="width:5%"/>
										<th style="width:19%"/>
										<th style="width:19%"/>
										<th style="width:19%"/>
										<th style="width:10%"/>
										<th style="width:10%"/>
										<th style="width:18%"/>
								</tr>
										<tr class="iCargoTableHeadingLeft">
											<td class="iCargoTableHeaderLabel">
												<input type="checkbox" name="checkAll" value="checkbox">
											</td>
											<td class="iCargoTableHeaderLabel">
												<common:message key="mailtracking.defaults.emptyulds.lbl.uld" />
											</td>
											<td class="iCargoTableHeaderLabel">
												<common:message key="mailtracking.defaults.emptyulds.lbl.pou" />
											</td>
											<td class="iCargoTableHeaderLabel">
												<common:message key="mailtracking.defaults.emptyulds.lbl.dstn" />
											</td>
											<td class="iCargoTableHeaderLabel" colspan="2">
												<common:message key="mailtracking.defaults.emptyulds.lbl.fltno" />
											</td>
											<td class="iCargoTableHeaderLabel">
												<common:message key="mailtracking.defaults.emptyulds.lbl.fltdate" />
											</td>
										</tr>
                          		</thead>
								<tbody>
                          									<logic:present name="containerDetailsVOs">
																<logic:iterate id ="containerDetailsVO" name="containerDetailsVOs" type="ContainerDetailsVO" indexId="rowCount">
																	
																		<tr class=iCargoTableDataRow1>
																			<td class="iCargoTableDataTd ic-center">
																				<div class="ic-center">
																					<ihtml:checkbox property="selectULD" value="<%=String.valueOf(rowCount)%>"/>
																				</div>
																			</td>
																			<td  class="iCargoTableDataTd">
																				<bean:write name="containerDetailsVO" property="containerNumber"/>
																			</td>
																			<td class="iCargoTableDataTd">
																				<bean:write name="containerDetailsVO" property="pou"/>
																			</td>
																			<td class="iCargoTableDataTd">
																				<bean:write name="containerDetailsVO" property="destination"/>
																			</td>
																			<td class="iCargoTableDataTd">
																				<bean:write name="containerDetailsVO" property="carrierCode"/>
																			</td>
																			<td class="iCargoTableDataTd">
																				<bean:write name="containerDetailsVO" property="flightNumber"/>
																			</td>
																			<td class="iCargoTableDataTd">
																				<logic:present name="containerDetailsVO" property="flightDate">
																					<bean:define id="flightDate" name="containerDetailsVO" property="flightDate"/>
																					<%=((LocalDate)flightDate).toDisplayFormat("dd-MMM-yyyy")%>
																				</logic:present>
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
						<ihtml:nbutton property="btUnassign" componentID="BUT_MAILTRACKING_DEFAULTS_EMPTYULD_UNASSIGN">
							<common:message key="mailtracking.defaults.emptyulds.btn.unassign" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btClose" componentID="BUT_MAILTRACKING_DEFAULTS_EMPTYULD_CLOSE">
							<common:message key="mailtracking.defaults.emptyulds.btn.close" />
						</ihtml:nbutton>
					</div>
				</div>
			
			</div>
	
		</ihtml:form>
	</div>

 			
		 
	</body>

 </html:html>
