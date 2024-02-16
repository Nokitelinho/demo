<%-------------------------------------------------------------------------------
* Project	 		: iCargo
* Module Code & Name: Mail Tracking
* File Name			: ConfigureResdit.jsp
* Date				: 11-Feb-2016
* Author(s)			: A-5219
 ---------------------------------------------------------------------------------%>

<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.business.mail.operations.vo.ResditTransactionDetailVO"%>

<html:html locale="true">

<head>
	
	<title>
		<common:message bundle="configureresditResources" key="mailtracking.defaults.configureresdit.lbl.pagetitle" />
	</title>
	<meta name="decorator" content="mainpanelrestyledui">
	<common:include type="script" src="/js/mail/operations/ConfigureResdit_Script.jsp" />


</head>
<body class="ic-center" style="width:73%;">
	
	
	<div id="mainDiv"  class="iCargoContent" style="width:100%; z-index:1;overflow:auto;height:100%">

		<ihtml:form action="/mailtracking.defaults.configureresdit.screenloadconfigureresdit.do" >


			<bean:define id="form"
				name="ConfigureResditForm"
				type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ConfigureResditForm"
				toScope="page"
				scope="request"/>


			<business:sessionBean id="oneTimeValues"
					  moduleName="mail.operations"
					  screenID="mailtracking.defaults.configureresdit"
					  method="get"
					  attribute="oneTimeVOs" />
			<business:sessionBean id="resditConfigurationVO"
					  moduleName="mail.operations"
					  screenID="mailtracking.defaults.configureresdit"
					  method="get"
					  attribute="resditConfigurationVO" />

			<ihtml:hidden property="reserved"/>
			<ihtml:hidden property="assigned"/>
			<ihtml:hidden property="uplifted"/>
			<ihtml:hidden property="handedoverRecieved"/>
			<ihtml:hidden property="handedover"/>
			<ihtml:hidden property="departed"/>
			<ihtml:hidden property="delivered"/>
			<ihtml:hidden property="readyForDelivery"/>
			<ihtml:hidden property="transportCompleted"/>
			<ihtml:hidden property="mailArrived"/>
			<ihtml:hidden property="returned"/>


			<div class="ic-content-main">
				<div class="ic-head-container">
					<div class="ic-filter-panel">
						<div class="ic-row ic-input ic-split-15 ic-mandatory">
							<label><common:message key="mailtracking.defaults.configureresdit.lbl.airline" /></label>
							<ihtml:text property="airline" componentID="CMP_MailTracking_Defaults_ConfigureResdit_Airline" maxlength="4"/>
							<div class="lovImg"><img id="airlineLOV" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" value="port"/></div>
						</div>
						<div class="ic-button-container">
							<ihtml:nbutton property="btList"  componentID="CMP_MailTracking_Defaults_ConfigureResdit_btList" accesskey="L">
								<common:message key="mailtracking.defaults.configureresdit.btn.list" />
							</ihtml:nbutton>
							<ihtml:nbutton property="btClear"  componentID="CMP_MailTracking_Defaults_ConfigureResdit_btClear" accesskey="C">
								<common:message key="mailtracking.defaults.configureresdit.btn.clear" />
							</ihtml:nbutton>
						</div>
					</div>		
				</div>
				<div class="ic-main-container">
					<div class="tableContainer ic-pad-5"  style="height:730px">
					<table  class="fixed-header-table" >
							<thead>
								<tr class="ic-th-all">
									<th style="width: 10%" />
									<th style="width: 8%" />
									<th style="width: 9%" />
									<th style="width: 8%" />
									<th style="width: 9%" />
									<th style="width: 8%" />
									<th style="width: 9%" />
									<th style="width: 8%" />
									<th style="width: 9%" />
									<th style="width: 8%" />
									<th style="width: 9%" />
									<th style="width: 8%" />
								</tr>
								<tr>
									<td  class="iCargoTableHeaderLabel" >
										<lable><common:message key="mailtracking.defaults.configureresdit.lbl.milestones" /><span></span></label>
									</td>
									<td  class="iCargoTableHeaderLabel">
										<common:message key="mailtracking.defaults.configureresdit.lbl.reserved" /><span></span>
									</td>
									<td class="iCargoTableHeaderLabel">
										<common:message key="mailtracking.defaults.configureresdit.lbl.assigned" /><span></span>
									</td>
									<td class="iCargoTableHeaderLabel">
										<common:message key="mailtracking.defaults.configureresdit.lbl.uplifted" /><span></span>
									</td>
									<td  class="iCargoTableHeaderLabel">
										<common:message key="mailtracking.defaults.configureresdit.lbl.handedoverrecieved" /><span></span>
									</td>
									<td class="iCargoTableHeaderLabel" >
										<common:message key="mailtracking.defaults.configureresdit.lbl.handedoverdelivered" /><span></span>
									</td>
									<td class="iCargoTableHeaderLabel" >
										<common:message key="mailtracking.defaults.configureresdit.lbl.departed" /><span></span>
									</td>
									<td class="iCargoTableHeaderLabel" >
										<common:message key="mailtracking.defaults.configureresdit.lbl.delivered" /><span></span>
									</td>
									<td class="iCargoTableHeaderLabel" >
										<common:message key="mailtracking.defaults.configureresdit.lbl.readyfordelivery" /><span></span>
									</td>
									<td class="iCargoTableHeaderLabel" >
										<common:message key="mailtracking.defaults.configureresdit.lbl.transportcompleted" /><span></span>
									</td>
									<td class="iCargoTableHeaderLabel" >
										<common:message key="mailtracking.defaults.configureresdit.lbl.mailarrived" /><span></span>
									</td>
									<td class="iCargoTableHeaderLabel" >
										<common:message key="mailtracking.defaults.configureresdit.lbl.returned" /><span></span>
									</td>
								</tr>
							</thead>
							<tbody>
								<logic:present name="resditConfigurationVO" property="resditTransactionDetails">
									<bean:define id="resditTransactionDetails" name="resditConfigurationVO" property="resditTransactionDetails" />
									<logic:iterate id="resditTransactionVO" name="resditTransactionDetails" indexId="index" type="ResditTransactionDetailVO">
										<common:rowColorTag index="index">
											<tr  bgcolor="<%=color%>" >
												<td class="iCargoTableDataTd"  >
													<div align="center" >
														<logic:present name="resditTransactionVO" property="transaction">
															<logic:present name="oneTimeValues">
																<logic:iterate id="oneTimeValue" name="oneTimeValues">
																	<bean:define id="parameterCode" name="oneTimeValue" property="key" />
																	<logic:equal name="parameterCode" value="mailtracking.defaults.resdit.transaction">
																		<bean:define id="parameterValues" name="oneTimeValue" property="value" />
																			<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																				<logic:present name="parameterValue">
																					<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
																					<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
																						<logic:equal name="resditTransactionVO" property="transaction" value="<%=(String)fieldValue%>">
																							<%=(String)fieldDescription%>
																						</logic:equal>
																				</logic:present>
																			</logic:iterate>
																	</logic:equal>
																</logic:iterate>
															</logic:present>
														</logic:present>
													</div>
												</td>
												<td class="iCargoTableDataTd">
													<logic:present name="resditTransactionVO" property="receivedResditFlag">
														<div align="center">
															<ihtml:checkbox name="resditTransactionVO" property="receivedResditFlag" value="Y" componentID="MailTracking_Defaults_ConfigureResdit_Reserved"/>
														</div>
													</logic:present>
												</td>
												<td class="iCargoTableDataTd">
													<logic:present name="resditTransactionVO" property="assignedResditFlag">
														<div align="center">
															<ihtml:checkbox name="resditTransactionVO" property="assignedResditFlag" value="Y" componentID="MailTracking_Defaults_ConfigureResdit_Assigned"/>
														</div>
													</logic:present>
												</td>
												<td class="iCargoTableDataTd" >
													<logic:present name="resditTransactionVO" property="upliftedResditFlag">
														<div align="center">
															<ihtml:checkbox name="resditTransactionVO" property="upliftedResditFlag" value="Y" componentID="MailTracking_Defaults_ConfigureResdit_Uplifted"/>
														</div>
													</logic:present>
												</td>
												<td class="iCargoTableDataTd">
													<logic:present name="resditTransactionVO" property="handedOverReceivedResditFlag">
														<div align="center">
															<ihtml:checkbox name="resditTransactionVO" property="handedOverReceivedResditFlag" value="Y" componentID="MailTracking_Defaults_ConfigureResdit_HandedoverRecieved"/>
														</div>
													</logic:present>
												</td>
												<td class="iCargoTableDataTd">
													<logic:present name="resditTransactionVO" property="handedOverResditFlag">
														<div align="center">
															<ihtml:checkbox name="resditTransactionVO" property="handedOverResditFlag" value="Y" componentID="MailTracking_Defaults_ConfigureResdit_HandedoverDelivered"/>
														</div>
													</logic:present>
												</td>
												<td class="iCargoTableDataTd">
													<logic:present name="resditTransactionVO" property="loadedResditFlag">
														<div align="center">
															<ihtml:checkbox name="resditTransactionVO" property="loadedResditFlag" value="Y" componentID="MailTracking_Defaults_ConfigureResdit_HandedoverDelivered"/>
														</div>
													</logic:present>
												</td>
												<td class="iCargoTableDataTd">
													<logic:present name="resditTransactionVO" property="deliveredResditFlag">
														<div align="center">
															<ihtml:checkbox name="resditTransactionVO" property="deliveredResditFlag" value="Y" componentID="MailTracking_Defaults_ConfigureResdit_HandedoverDelivered"/>
														</div>
													</logic:present>
												</td>
												<td class="iCargoTableDataTd">
													<logic:present name="resditTransactionVO" property="readyForDeliveryFlag">
														<div align="center">
															<ihtml:checkbox name="resditTransactionVO" property="readyForDeliveryFlag" value="Y" componentID="MailTracking_Defaults_ConfigureResdit_ReadyForDeliveryFlag"/>
														</div>
													</logic:present>								
												</td>
												<td class="iCargoTableDataTd">								
													<logic:present name="resditTransactionVO" property="transportationCompletedResditFlag">
														<div align="center">
															<ihtml:checkbox name="resditTransactionVO" property="transportationCompletedResditFlag" value="Y" componentID="MailTracking_Defaults_ConfigureResdit_TransportationCompletedResditFlag"/>
														</div>
													</logic:present>								
												</td>
												<td class="iCargoTableDataTd">								
													<logic:present name="resditTransactionVO" property="arrivedResditFlag">
														<div align="center">
															<ihtml:checkbox name="resditTransactionVO" property="arrivedResditFlag" value="Y" componentID="MailTracking_Defaults_ConfigureResdit_ArrivedResditFlag"/>
														</div>
													</logic:present>								
												</td>
												<td class="iCargoTableDataTd">
													<logic:present name="resditTransactionVO" property="returnedResditFlag">
														<div align="center">
															<ihtml:checkbox name="resditTransactionVO" property="returnedResditFlag" value="Y" componentID="MailTracking_Defaults_ConfigureResdit_HandedoverDelivered"/>
														</div>
													</logic:present>
												</td>
											</tr>
										</common:rowColorTag>
									</logic:iterate>
								</logic:present>
							</tbody>	
						</table>
				</div>
				</div>
				<div class="ic-foot-container">
					<div class="ic-button-container ic-pad-5">
						<ihtml:nbutton property="btSave"  componentID="CMP_MailTracking_Defaults_ConfigureResdit_btSave" accesskey="S">
							<common:message key="mailtracking.defaults.configureresdit.btn.save" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btClose"  componentID="CMP_MailTracking_Defaults_ConfigureResdit_btClose" accesskey="O">
							<common:message key="mailtracking.defaults.configureresdit.btn.close" />
						</ihtml:nbutton>
					</div>
				</div>	
			</div>	
	</ihtml:form>
</div>
	</body>
</html:html>
