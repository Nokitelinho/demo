
<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  Mailtracking
* File Name				:  DamageDetails.jsp
* Date					:  17-July-2006
* Author(s)				:  A-2047
*************************************************************************/
 --%>


 <%@ page language="java" %>
 <%@ include file="/jsp/includes/tlds.jsp"%>
 <%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.operations.DamagedMailBagForm"%>
 <%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.DamagedMailbagVO"%>
 <%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>

	 <html:html>
 <head> 
	
		<%@ include file="/jsp/includes/customcss.jsp" %>
			
	
 	<title><common:message bundle="damagedMailbagResources" key="mailtracking.defaults.damagedMailbag.lbl.title" /></title>
 	<meta name="decorator" content="popup_panel">
 	<common:include type="script" src="/js/mail/operations/ViewDamageDetails_Script.jsp"/>
 </head>
  <body>
	
  	<bean:define id="form"
  		 name="DamagedMailBagForm"
  		 type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.DamagedMailBagForm"
		 toScope="page" />

	<business:sessionBean id="damagedMailbagVOs"
		 moduleName="mail.operations"
		 screenID="mailtracking.defaults.damagedmailbag"
		 method="get"
	 	 attribute="damagedMailbagVOs"/>
		 <div class="iCargoPopUpContent" >
		 <ihtml:form action="/mailtracking.defaults.damagedmailbag.screenload.do" styleClass="ic-main-form">
		 <div class="ic-content-main">
		 <div class="ic-main-container">
		 <div class="ic-row">
		 </div>
		 
		 <div class="ic-row">
			 <span class="ic-page-title ic-display-none">  <common:message key="mailtracking.defaults.damagedMailbag.lbl.heading" />
					</span>
		 </div>
		  <div class="ic-border">
		 <div class="ic-row">
				<bean:write name="form" property="mailbagId"/>
		 </div>
		 </div>
		 <div class="ic-row">
		 <div>
		 <b><common:message key="mailtracking.defaults.damagedMailbag.lbl.details" /></b>
		 </div>
		 <div class="ic-row">
		 <div class="tableContainer" id="div1" align="center"  style="width:100%; height:180px; ">
                        						<table  class="fixed-header-table" >
                          							<!--DWLayoutTable-->
                          								<thead>
                            								<tr class="iCargoTableHeadingLeft">
                              									<td class="iCargoTableHeaderLabel" height="17" >
                              										<common:message bundle="damagedMailbagResources" key="mailtracking.defaults.damagedMailbag.lbl.dmgcode" />
                              									</td>
                              									<td class="iCargoTableHeaderLabel">
                              										<common:message key="mailtracking.defaults.damagedMailbag.lbl.dmgDesc" />
                              									</td>
                              									<td class="iCargoTableHeaderLabel">
                              										<common:message key="mailtracking.defaults.damagedMailbag.lbl.airport" />
                              									</td>
                              									<td class="iCargoTableHeaderLabel">
																	<common:message key="mailtracking.defaults.damagedMailbag.lbl.pacode" />
                              									</td>
                              									<td class="iCargoTableHeaderLabel">
                              										<common:message key="mailtracking.defaults.damagedMailbag.lbl.date" />
                              									</td>
                              									<td class="iCargoTableHeaderLabel">
																    <common:message key="mailtracking.defaults.damagedMailbag.lbl.returmned" />
                              									</td>
                              									<td class="iCargoTableHeaderLabel">
                              										<common:message key="mailtracking.defaults.damagedMailbag.lbl.user" />
                              									</td>
                            								</tr>
                          								</thead>
                          								<tbody>
                          									<logic:present name="damagedMailbagVOs">
																<logic:iterate id ="damagedMailbagVO" name="damagedMailbagVOs" type="DamagedMailbagVO" indexId="rowCount">
																	
																		<tr class=iCargoTableDataRow1 >
																			<td  class="iCargoTableDataTd">
																				<bean:write name="damagedMailbagVO" property="damageCode"/>
																			</td>
																			<td class="iCargoTableDataTd">
																				<bean:write name="damagedMailbagVO" property="damageDescription"/>
																			</td>
																			<td class="iCargoTableDataTd">
																				<bean:write name="damagedMailbagVO" property="airportCode"/>
																			</td>
																			<td class="iCargoTableDataTd">
																				<bean:write name="damagedMailbagVO" property="paCode"/>
																			</td>
																			<td class="iCargoTableDataTd">
																				<logic:present name="damagedMailbagVO" property="damageDate">
																					<bean:define id="damageDate" name="damagedMailbagVO" property="damageDate"/>
																					<%=((LocalDate)damageDate).toDisplayFormat("dd-MMM-yyyy")%>
																				</logic:present>
																			</td>
																			<td class="iCargoTableDataTd">
																				<div align="center">
																					<logic:present name="damagedMailbagVO" property="returnedFlag">
																						<bean:define id="returned" name="damagedMailbagVO" property="returnedFlag"/>
																						<logic:equal name="returned" value="Y">
																							<!--<input name="retFlag" type="checkbox" checked="true" disabled/>-->
																							<img id="isReturned" src="<%=request.getContextPath()%>/images/icon_on.gif" />
																						</logic:equal>
																						<logic:notEqual name="returned" value="Y">
																							<!--<input name="retFlag" type="checkbox" checked="false" disabled/>-->
																							<img id="isNotReturned" src="<%=request.getContextPath()%>/images/icon_off.gif" />
																						</logic:notEqual>
																					</logic:present>
																					<logic:notPresent name="damagedMailbagVO" property="returnedFlag">
																							<!--<input name="retFlag" type="checkbox"  disabled/>-->
																							<img id="isNotReturned" src="<%=request.getContextPath()%>/images/icon_off.gif" />
																					</logic:notPresent>
																				</div>
																			</td>
																			<td class="iCargoTableDataTd">
																				<bean:write name="damagedMailbagVO" property="userCode"/>
																			</td>
																		</tr>
																
																</logic:iterate>
															</logic:present>
													  	</tbody>
                        							</table>
		 </div>
		 </div>
		 </div>
		 </div>
		 <div class="ic-foot-container">
		 <div class="ic-button-container">
		 <ihtml:nbutton property="btOk" componentID="BUT_MAILTRACKING_DEFAULTS_DMGMAILBAG_OK">
														<common:message key="mailtracking.defaults.damagedMailbag.btn.ok" />
													</ihtml:nbutton>
		 </div>
		 </div>
		 </div>
		 </ihtml:form>
		 </div>
		  
	</body>
</html:html>