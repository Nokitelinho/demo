

<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  Mailtracking
* File Name				:  ViewDSNDamageDetails.jsp
* Date					:  27-July-2006
* Author(s)				:  A-2047
*************************************************************************/
 --%>


 <%@ page language="java" %>
 <%@ include file="/jsp/includes/tlds.jsp"%>
 <%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.operations.DamagedDSNForm"%>
 <%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.DamagedDSNDetailVO"%>
 <%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.DamagedDSNVO"%>
 <%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>

		
	 <html:html>

 <head> 
	
			
	
 	<title><common:message bundle="damagedDSNResources" key="mailtracking.defaults.damagedDSN.lbl.title" /></title>
 	<meta name="decorator" content="popup_panel">
 	<common:include type="script" src="/js/mail/operations/ViewDSNDamageDetails_Script.jsp"/>
 </head>

 <body>
	
	
	

	<bean:define id="form"
		 name="DamagedDSNForm"
		 type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.DamagedDSNForm"
		 toScope="page" />

	<business:sessionBean id="damagedDSNVO"
		 moduleName="mail.operations"
		 screenID="mailtracking.defaults.damageddsn"
		 method="get"
	 	 attribute="damagedDSNVO"/>

	<div class="iCargoPopUpContent" style="overflow:auto;height:380px;">
		<ihtml:form action="/mailtracking.defaults.damageddsn.screenload.do" styleClass="ic-main-form">
		
		
		<div class="ic-content-main">
				<div class="ic-row">
					<span class="ic-page-title "><common:message key="mailtracking.defaults.damagedDSN.lbl.heading" />
					</span>
				</div>
				<div class="ic-main-container">
				<div class="ic-row">
						<div class="ic-input ic-split-12"><label>
                    							<common:message key="mailtracking.defaults.damagedDSN.lbl.dsn" /></label>
												<logic:present name="damagedDSNVO" property="dsn">
													<common:display name="damagedDSNVO" property="dsn"/>
												</logic:present>
						</div>						
                    						<div class="ic-input ic-split-14"><label>
                    							<common:message key="mailtracking.defaults.damagedDSN.lbl.ooe" /></label>
                    							<logic:present name="damagedDSNVO" property="originExchangeOffice">
                    								<common:display name="damagedDSNVO" property="originExchangeOffice" />
												</logic:present>
                    						</div>
                    						<div class="ic-input ic-split-14"><label>
                    							<common:message key="mailtracking.defaults.damagedDSN.lbl.doe" /></label>
                    							<logic:present name="damagedDSNVO" property="destinationExchangeOffice">
													<common:display name="damagedDSNVO" property="destinationExchangeOffice"/>
												</logic:present>
                    						</div>
											<div class="ic-input ic-split-12"><label>
												<common:message key="mailtracking.defaults.damagedDSN.lbl.year" /></label>
												<logic:present name="damagedDSNVO" property="year">
													<common:display name="damagedDSNVO" property="year"/>
												</logic:present>
											</div>
											<div class="ic-input ic-split-14"><label>
												<common:message key="mailtracking.defaults.damagedDSN.lbl.mailclas" /></label>
												<logic:present name="damagedDSNVO" property="mailClass">
													<common:display name="damagedDSNVO" property="mailClass"/>
												</logic:present>
											</div>
											<div class="ic-input ic-split-30"><label>
												<common:message key="mailtracking.defaults.damagedDSN.lbl.consignmentno" /></label>
											
												<logic:present name="damagedDSNVO" property="consignmentNumber">
													<common:display name="damagedDSNVO" property="consignmentNumber"/>
												</logic:present>
											</div>
										</tr>
                					</table>
									</div>
									<div class="ic-row">
									<div class="ic-row ">
									<label><h4><common:message key="mailtracking.defaults.damagedDSN.lbl.damagedetails" /></h4></label>
									</div>
									<div class="tableContainer" id="div1" style="height:230px">

                        							<table class="fixed-header-table">
                          							<!--DWLayoutTable-->
                          								<thead>
															<tr class="iCargoTableHeadingLeft">
																<td width="8%" >
																	<common:message key="mailtracking.defaults.damagedDSN.lbl.damagecode" />
																</td>
														  		<td width="15%" >
														  			<common:message key="mailtracking.defaults.damagedDSN.lbl.damagedesc" />
														  		</td>
														  		<td width="8%" >
														  			<common:message key="mailtracking.defaults.damagedDSN.lbl.damagebags" />
														  		</td>
														  		<td width="9%">
														  			<common:message key="mailtracking.defaults.damagedDSN.lbl.damagewt" />
														  		</td>
														  		<td width="8%" >
														  			<common:message key="mailtracking.defaults.damagedDSN.lbl.returnedbag" />
														  		</td>
														  		<td width="9%" >
														  			<common:message key="mailtracking.defaults.damagedDSN.lbl.returnedwt" />
														  		</td>
														  		<td width="8%" >
														  			<common:message key="mailtracking.defaults.damagedDSN.lbl.airport" />
														  		</td>
														  		<td width="8%" >
														  			<common:message key="mailtracking.defaults.damagedDSN.lbl.pacode" />
														  		</td>
														  		<td width="17%" >
														  			<common:message key="mailtracking.defaults.damagedDSN.lbl.date" />
														  		</td>
														  		<td width="10%" >
														  			<common:message key="mailtracking.defaults.damagedDSN.lbl.user" />
																</td>
															</tr>
														</thead>
													  	<tbody>
															<logic:present name="damagedDSNVO" property="damagedDsnDetailVOs">
																<bean:define id="damagedDsnDetailVOs" name="damagedDSNVO" property="damagedDsnDetailVOs"/>

																	<logic:iterate id ="damagedDSNDetailVO" name="damagedDsnDetailVOs" type="DamagedDSNDetailVO" indexId="rowCount">
																			<tr>
																				<td  class="iCargoTableDataTd">
																					<logic:present name="damagedDSNDetailVO" property="damageCode">
																						<bean:write name="damagedDSNDetailVO" property="damageCode"/>
																					</logic:present>
																				</td>
																				<td class="iCargoTableDataTd">
																					<logic:present name="damagedDSNDetailVO" property="damageDescription">
																						<bean:write name="damagedDSNDetailVO" property="damageDescription"/>
																					</logic:present>
																				</td>
																				<td class="iCargoTableDataTd">
																					<logic:present name="damagedDSNDetailVO" property="damagedBags">
																						<bean:write name="damagedDSNDetailVO" property="damagedBags"/>
																					</logic:present>
																				</td>
																				<td class="iCargoTableDataTd" >
																				<logic:present name="damagedDSNDetailVO" property="damagedWeight">
																						<common:write name="damagedDSNDetailVO" property="damagedWeight" unitFormatting="true"/><!--modified by A-7371-->
																					</logic:present>
																				</td>
																				<td class="iCargoTableDataTd" >
																					<logic:present name="damagedDSNDetailVO" property="returnedBags">
																						<bean:write name="damagedDSNDetailVO" property="returnedBags"/>
																					</logic:present>
																				</td>
																				<td class="iCargoTableDataTd" >
																					<logic:present name="damagedDSNDetailVO" property="returnedWeight">
																						<common:write name="damagedDSNDetailVO" property="returnedWeight" unitFormatting="true"/><!--modified by A-7371-->
																					</logic:present>
																				</td>
																				<td class="iCargoTableDataTd" >
																					<logic:present name="damagedDSNDetailVO" property="airportCode">
																						<bean:write name="damagedDSNDetailVO" property="airportCode"/>
																					</logic:present>
																				</td>
																				<td class="iCargoTableDataTd" >
																					<logic:present name="damagedDSNDetailVO" property="returnedPaCode">
																						<bean:write name="damagedDSNDetailVO" property="returnedPaCode"/>
																					</logic:present>
																				</td>
																				<td class="iCargoTableDataTd" >
																					<logic:present name="damagedDSNDetailVO" property="damageDate">
																						<bean:define id="damageDate" name="damagedDSNDetailVO" property="damageDate"/>
																						<%=((LocalDate)damageDate).toDisplayFormat("dd-MMM-yyyy")%>
																					</logic:present>
																				</td>
																				<td class="iCargoTableDataTd">
																					<logic:present name="damagedDSNDetailVO" property="damageUser">
																						<bean:write name="damagedDSNDetailVO" property="damageUser"/>
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
				<ihtml:nbutton property="btOk" componentID="BUT_MAILTRACKING_DEFAULTS_DMGDSN_OK">
														<common:message key="mailtracking.defaults.damagedDSN.btn.ok" />
													</ihtml:nbutton>
				</div>
				</div>
		</div>
		
		
				</ihtml:form>
	</div>

				
		
	</body>

 </html:html>
