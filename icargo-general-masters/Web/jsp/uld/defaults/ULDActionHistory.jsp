<%-- *************************************************

* Project	 		: iCargo
* Module Code & Name		: ULD
* File Name			: ULDActionHistory.jsp
* Date				: 14-July-2008
* Author(s)			: A-3093

****************************************************** --%>

<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListULDMovementForm" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="java.util.Calendar"%>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>

<html:html locale="true">
<head>
	
<title>
<common:message bundle="listuldmovementResources" key="uld.defaults.uldactionhistory.title" />
</title>
<meta name="decorator" content="mainpanel">
<common:include type="script" src="/js/uld/defaults/ULDActionHistory_Script.jsp"/>
</head>
<body>
	
	
	
<div class="iCargoPopUpContent" style="overflow:auto;">	
<ihtml:form action="/uld.defaults.findULDActionHistory.do" styleClass="ic-main-form">
<%System.out.println("dd");%>
<business:sessionBean id="KEY_ACTIONHISTORY"
					moduleName="uld.defaults"
					screenID="uld.defaults.misc.listuldmovement" method="get"
					attribute="auditDetailsVO" />
<bean:define id="form" name="listULDMovementForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListULDMovementForm"
	toScope="page" />
	
<business:sessionBean id="oneTimeValues" moduleName="uld.defaults"
				screenID="uld.defaults.misc.listuldmovement" method="get"
				attribute="oneTimeValues" />
				

	<input type="hidden" name="currentDialogId"/>
	<input type="hidden" name="currentDialogOption"/>
<div class="ic-content-main">
		<span class="ic-page-title ic-display-none">
									<common:message key="uld.defaults.uldactionhistory.heading" />
								</span>
		<div class="ic-head-container">
			<div class="ic-filter-panel">
				<div class="ic-input-container">
					<div class="ic-row">
							<div class="ic-input ic-split-100">
								<label>
											<common:message key="uld.defaults.misc.listUldMovement.lbl.ULDNumber" />
								</label>
											<ibusiness:uld id="uldno" uldProperty="uldNumber" uldValue="<%=form.getUldNumber()%>" componentID="CMP_ULD_DEFAULTS_MISC_LISTULDMOVEMENT_ULDNUMBER" style="text-transform: uppercase" disabled="true"/>
										  </div>
										</div>
									</div>
								</div>
							</div>
			<div class="ic-main-container">
			<div class="ic-row">
									<div id="div1" class="tableContainer" style="height:330px">

                      <table id="ULDActionHistory" class="fixed-header-table" >

											 <thead>
												<tr class="iCargoTableHeadingLeft" >
												 
												 <td width="25%"  ><common:message key="uld.defaults.misc.uldactionhistory.lbl.transaction" scope="request"/></td>
												 <td width="25%"  ><common:message key="uld.defaults.misc.uldactionhistory.lbl.date" scope="request"/></td>
												 <td width="25%"  ><common:message key="uld.defaults.misc.uldactionhistory.lbl.airport" scope="request"/></td>
												 <td width="25%"  ><common:message key="uld.defaults.misc.uldactionhistory.lbl.additionalcomment" scope="request"/></td>
												</tr>
											 </thead>
											 <tbody id="ULDActionHistoryTableBody" >
											 <logic:present name="KEY_ACTIONHISTORY">
				<logic:iterate id="iterator" name="KEY_ACTIONHISTORY"
					type=" com.ibsplc.icargo.business.shared.audit.vo.AuditDetailsVO"
					indexId="index">					
					<tr>
					<td class="iCargoTableDataTd">
									<logic:present name="iterator" property="action">
										<bean:write name="iterator" property="action" />
										</logic:present>										
									</td>
					<td class="iCargoTableDataTd">
									<logic:present name="iterator" property="lastUpdateTime">										
										<%
										String lastUpdateTime ="";
										if(iterator.getLastUpdateTime() != null) {
										lastUpdateTime = TimeConvertor.toStringFormat(
													iterator.getLastUpdateTime().toCalendar(),TimeConvertor.ADVANCED_DATE_FORMAT);
										}
									%>
									<%=lastUpdateTime%>
										</logic:present>
									
								</td>	
					<td class="iCargoTableDataTd">
										
										<logic:present name="iterator" property="stationCode">
										<bean:write name="iterator" property="stationCode" />
										</logic:present>
									
								</td>			
								<td class="iCargoTableDataTd">
										
										<logic:present	name="iterator" property="additionalInformation">											
											<bean:define id="content" name="iterator" property="additionalInformation" />
											<logic:present name="oneTimeValues">											
												<logic:iterate id="oneTimeValue" name="oneTimeValues">
													<bean:define id="parameterCode" name="oneTimeValue" property="key" />
														<logic:equal name="parameterCode" value="uld.defaults.contentcodes">
															<bean:define id="parameterValues" name="oneTimeValue" property="value" />
															<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																<logic:present name="parameterValue">
																	<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
																		<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
																		<%if(fieldValue.equals((String)content)){%>
																			<%=(String)fieldDescription%>
																				  <%}%>
																</logic:present>
															</logic:iterate>
														</logic:equal>
												</logic:iterate>
											</logic:present>
										</logic:present>											
																	
								</td>	
								
								
					</logic:iterate>
					 </logic:present>
											 </tbody>
								</table>
							 </div>
						</div>
						</div>
							<div class="ic-foot-container">	
				<div class="ic-button-container">
						 
						<ihtml:nbutton property="btnClose" componentID="TXT_ULD_DEFAULTS_LISTULDMOVEMENT_CLOSE_BUTTON" accesskey="O">
						 <common:message key="uld.defaults.misc.listUldMovement.btn.btClose" scope="request"/>
						 </ihtml:nbutton>
						 </div>
						</div>
				</div>
    </ihtml:form>
    </div>

				
			

		

		
	</body>
</html:html>

