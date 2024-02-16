<%--/***********************************************************************
* Project	     	 	 : iCargo
* Module Code & Name 	 : Mail
* File Name          	 : CaptureCN51Details.jsp
* Date                 	 : 04-DEC-2008
* Author(s)              : A-3227
*************************************************************************/
--%>
<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import = "java.util.Calendar" %>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CaptureCN51Form" %>
<%@ page import = "com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51DetailsVO" %>


		
<html:html>
	<head>
		
			
	
		<title><common:message bundle="capturecn51resources" key="mailtracking.mra.defaults.capturecn51.title.capturecn51details"/></title>
		<meta name="decorator" content="popuppanelrestyledui">
		<common:include type="script" src="/js/mail/mra/airlinebilling/defaults/CaptureCN51Details_Script.jsp" />
	</head>
	<body>
		
		<logic:present name="popup">
			<div id="walkthroughholder"> </div>
		</logic:present>
		
		<business:sessionBean
		  		id="KEY_ONETIMEVALUES"
		  		moduleName="mailtracking.mra.airlinebilling.defaults"
		  		screenID="mailtracking.mra.airlinebilling.defaults.capturecn51"
		  		method="get"
		  		attribute="oneTimeValues" />
		
		<business:sessionBean
		  		id="KEY_DETAILS"
		  		moduleName="mailtracking.mra.airlinebilling.defaults"
		  		screenID="mailtracking.mra.airlinebilling.defaults.capturecn51"
		  		method="get"
		  		attribute="cn51Details" />
		  		
		<business:sessionBean
		  		id="KEY_CN51DETAILVO"
		  		moduleName="mailtracking.mra.airlinebilling.defaults"
		  		screenID="mailtracking.mra.airlinebilling.defaults.capturecn51"
		  		method="get"
		  		attribute="currentCn51Detail" />
		<business:sessionBean
		  		id="KEY_SELECTED_CN51DETAIL_VOS"
		  		moduleName="mailtracking.mra.airlinebilling.defaults"
		  		screenID="mailtracking.mra.airlinebilling.defaults.capturecn51"
		  		method="get"
		  		attribute="selectedCn51DetailsVOs" />
		<business:sessionBean id="KEY_WEIGHTROUNDINGVO"
		        moduleName="mailtracking.mra.airlinebilling.defaults"
		        screenID="mailtracking.mra.airlinebilling.defaults.capturecn51"
		        method="get"
		        attribute="weightRoundingVO" />
		 <bean:define 
				id="CaptureCN51Form" 
				name="CaptureCN51Form"
			  	type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CaptureCN51Form"
				toScope="page" />  		
		
		<bean:define 
				id="airlineCN51DetailsVO" 
				name="KEY_CN51DETAILVO"
			  	type="com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51DetailsVO"
				toScope="page" /> 
			
			
		<div class="iCargoPopUpContent" id="div_main" style="height:95%" >
			<ihtml:form action="/mailtracking.mra.airlinebilling.defaults.capturecn51.screenloadcn51details.do" styleClass="ic-main-form">
			
				<ihtml:hidden property="lastPageNum"/>
				<ihtml:hidden property="currentPageNum"/>
				<ihtml:hidden property="totalRecords"/>
				<ihtml:hidden property="displayPage"/>	
				<ihtml:hidden property="popUpCloseFlag"/>
				<ihtml:hidden property="blgCurCode"/>
				<ihtml:hidden property="screenFlg" />

				<div class="ic-content-main">
				
					<div class="ic-main-container">
						<div class="ic-row">
							<div class="ic-button-container">
								<logic:present name="KEY_SELECTED_CN51DETAIL_VOS">
									 <common:popuppaginationtag
											pageURL="javascript:selectNextRateLineDetails('lastPageNum','displayPage');"
											linkStyleClass="iCargoResultsLabel"
											disabledLinkStyleClass="iCargoResultsLabel"
											displayPage="<%=CaptureCN51Form.getDisplayPage()%>"
											totalRecords="<%=CaptureCN51Form.getTotalRecords()%>" />
								</logic:present>
							</div>
						</div>
						<div class="ic-row ic-border">
							<div class="ic-input ic-split-33 ">
								<common:message   key="mailtracking.mra.defaults.capturecn51.lbl.carriageFrom" />
								<%String carFrom = "";%>
								<logic:present name="airlineCN51DetailsVO">
									<logic:present name="airlineCN51DetailsVO" property="carriagefrom" >
										<bean:define id="carriagefrom" name="KEY_CN51DETAILVO" property="carriagefrom"/>
										<%carFrom = String.valueOf(carriagefrom);%>
									 </logic:present>
								 </logic:present>				
								 
								<ihtml:text property="carriagesFrom" name="CaptureCN51Form" value="<%=carFrom%>" componentID="CMP_MAILTRACKING_MRA_INWARDBILLING_CAPTURECN51_CARRIAGEFROMFILTER"  maxlength="4" />
								<img name="carriageFromlov" id="carriageFromlov" height="16" src="<%=request.getContextPath()%>/images/lov.gif" width="16" alt="" />
							</div>
							<div class="ic-input ic-split-33 ">
								<common:message   key="mailtracking.mra.defaults.capturecn51.lbl.carriageTo" />
								<%String carTo = "";%>
								<logic:present name="airlineCN51DetailsVO">
									<logic:present name="airlineCN51DetailsVO" property="carriageto" >
										<bean:define id="carriageto" name="KEY_CN51DETAILVO" property="carriageto"/>
										<%carTo = String.valueOf(carriageto);%>
									 </logic:present>
								 </logic:present>				
								<ihtml:text property="carriagesTo" name="CaptureCN51Form" value="<%=carTo%>" componentID="CMP_MAILTRACKING_MRA_INWARDBILLING_CAPTURECN51_CARRIAGETOFILTER"  maxlength="4" />
								<img name="carriageTolov" id="carriageTolov" height="16" src="<%=request.getContextPath()%>/images/lov.gif" width="16" alt="" />
							</div>
							<div class="ic-input ic-split-33 ">
								<common:message   key="mailtracking.mra.defaults.capturecn51.lbl.category" />
								<%String mailCat = "";%>
								<logic:present name="airlineCN51DetailsVO">
									<logic:present name="airlineCN51DetailsVO" property="mailcategory" >
										<bean:define id="mailcategory" name="KEY_CN51DETAILVO" property="mailcategory"/>
										<%mailCat = String.valueOf(mailcategory);%>
									 </logic:present>
								 </logic:present>											 
								<ihtml:select property="categories" componentID="CMP_MAILTRACKING_MRA_INWARDBILLING_CAPTURECN51_CATEGORY" value="<%=mailCat%>">
									<logic:present name="KEY_ONETIMEVALUES">
									<html:option value=""></html:option>
										<logic:iterate id="oneTimeValue" name="KEY_ONETIMEVALUES">
											<bean:define id="parameterCode" name="oneTimeValue" property="key" />
											<logic:equal name="parameterCode" value="mailtracking.defaults.mailcategory">
												<bean:define id="parameterValues" name="oneTimeValue" property="value" />
												<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
													<logic:present name="parameterValue">
													<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
													<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
														<html:option value="<%=(String)fieldValue%>">
															<%=(String)fieldDescription%>
														</html:option>
													</logic:present>
												</logic:iterate>
											</logic:equal>
										</logic:iterate>
									</logic:present>
								</ihtml:select>
							</div>
						</div>
						<div class="ic-row ">
							<h4><common:message key="mailtracking.mra.airlinebilling.defaults.capturecn51.wt" /></h4>
						</div>
						<div class="ic-row" >
							<div id="div1" class="tableContainer" style="height:100%;width:100%;">
							<% String subClassGroup=""; %>
							<logic:present	name="airlineCN51DetailsVO" property="mailsubclass">
								<bean:define id="mailsubclass" name="airlineCN51DetailsVO" property="mailsubclass" />
								<% subClassGroup=mailsubclass.toString(); %>
								<bean:define id="subClass" value="<%=subClassGroup%>"/>
							</logic:present>
							
							<table class="icargotable1">
								<thead>	
								
									<tr>
										<td  class="iCargoTableHeader"><common:message key="mailtracking.mra.airlinebilling.defaults.capturecn51.lc/ao"/></td>
										<td  class="iCargoTableHeader"><common:message key="mailtracking.mra.airlinebilling.defaults.capturecn51.cp"/></td>
										<!--<td  class="iCargoTableHeader"><common:message key="mailtracking.mra.airlinebilling.defaults.capturecn51.sal"/></td>-->
									    <td  class="iCargoTableHeader"><common:message key="mailtracking.mra.airlinebilling.defaults.capturecn51.sv"/></td>
										<td  class="iCargoTableHeader"><common:message key="mailtracking.mra.airlinebilling.defaults.capturecn51.ems"/></td>
								  	</tr>
								</thead>
								<tbody>
									<tr>
										<td>
											<%String localtotalLCWeight = "0";%>
											<logic:present	name="airlineCN51DetailsVO" property="totalweight">
												<bean:define id="totalweight" name="airlineCN51DetailsVO" property="totalweight" />
												
																	
												<logic:equal name="subClass" value="LC">
															
													<%
													localtotalLCWeight=((Double)totalweight).toString();
													%>
                                                   
													
												</logic:equal>
																
																
																	
											</logic:present>
											<logic:present name="KEY_WEIGHTROUNDINGVO">
																		<bean:define id="sampleStdWtVo" name="KEY_WEIGHTROUNDINGVO" />
																		<% request.setAttribute("sampleStdWt",sampleStdWtVo); %>
																		<ibusiness:unitdef id="wtLCAO" unitTxtName="wtLCAO" componentID="CMP_MAILTRACKING_MRA_INWARDBILLING_CAPTURECN51_WEIGHT" label=""  unitReq = "false" dataName="sampleStdWt"
																			 unitValueStyle="iCargoEditableTextFieldRowColor1" title="LC/AO"
																			unitValue="<%=localtotalLCWeight%>" style="background :'<%=color%>';text-align:right"
																			indexId="rowCount" styleId="weight" />
																		
																		
																		
																		</logic:present>
																	
										</td>
										<td>
											<%String localtotalCPWeight="0";%>
											<logic:present	name="airlineCN51DetailsVO" property="totalweight">
												<bean:define id="totalweight" name="airlineCN51DetailsVO" property="totalweight" />
												<logic:equal name="subClass" value="CP">
													<%
													localtotalCPWeight=((Double)totalweight).toString();
													
													%>
													
												</logic:equal>
											</logic:present>
											<logic:present name="KEY_WEIGHTROUNDINGVO">
																		<bean:define id="sampleStdWtVo" name="KEY_WEIGHTROUNDINGVO" />
																		<% request.setAttribute("sampleStdWt",sampleStdWtVo); %>
																		<ibusiness:unitdef id="wtCP" unitTxtName="wtCP" componentID="CMP_MAILTRACKING_MRA_INWARDBILLING_CAPTURECN51_WEIGHTCP" label=""  unitReq = "false" dataName="sampleStdWt"
																			 unitValueStyle="iCargoEditableTextFieldRowColor1" title="CP"
																			unitValue="<%=localtotalCPWeight%>" style="background :'<%=color%>';text-align:right"
																			onblur="weightCheck('wtCP')" indexId="rowCount" styleId="weight" />
																		
																		
																		
																		</logic:present>
											
										</td>

										
										<td>
											<%String localtotalSVWeight="0";%>
											<logic:present	name="airlineCN51DetailsVO" property="totalweight">
												<bean:define id="totalweight" name="airlineCN51DetailsVO" property="totalweight" />
												<logic:equal name="subClass" value="SV">
												<%
												localtotalSVWeight=((Double)totalweight).toString();
												%>
												</logic:equal>
											</logic:present>
											<logic:present name="KEY_WEIGHTROUNDINGVO">
																		<bean:define id="sampleStdWtVo" name="KEY_WEIGHTROUNDINGVO" />
																		<% request.setAttribute("sampleStdWt",sampleStdWtVo); %>
																		<ibusiness:unitdef id="wtSv" unitTxtName="wtSv" componentID="CMP_MAILTRACKING_MRA_INWARDBILLING_CAPTURECN51_WEIGHTSV" label=""  unitReq = "false" dataName="sampleStdWt"
																			 unitValueStyle="iCargoEditableTextFieldRowColor1" title="SV"
																			unitValue="<%=localtotalSVWeight%>" style="background :'<%=color%>';text-align:right"
																			onblur="weightCheck('wtSv')" indexId="rowCount" styleId="weight" />
																		
																		
																		
																		</logic:present>
										</td>
										<td>
											<%String localtotalEMSWeight="0";%>
											<logic:present	name="airlineCN51DetailsVO" property="totalweight">
												<bean:define id="totalweight" name="airlineCN51DetailsVO" property="totalweight" />
												<logic:equal name="subClass" value="EMS">
												<%
												localtotalEMSWeight=((Double)totalweight).toString();
												%>
												</logic:equal>
											</logic:present>
											<logic:present name="KEY_WEIGHTROUNDINGVO">
																		<bean:define id="sampleStdWtVo" name="KEY_WEIGHTROUNDINGVO" />
																		<% request.setAttribute("sampleStdWt",sampleStdWtVo); %>
																		<ibusiness:unitdef id="wtEms" unitTxtName="wtEms" componentID="CMP_MAILTRACKING_MRA_INWARDBILLING_CAPTURECN51_WEIGHTEMS" label=""  unitReq = "false" dataName="sampleStdWt"
																			 unitValueStyle="iCargoEditableTextFieldRowColor1" title="EMS"
																			unitValue="<%=localtotalEMSWeight%>" style="background :'<%=color%>';text-align:right"
																			onblur="weightCheck('wtEms')" indexId="rowCount" styleId="weight" />
																		
																		
																		
																		</logic:present>
										</td>										
									</tr>
								</tbody>
							</table>
							</div>
						</div>
						<div class="ic-row ic-border">
							<div class="ic-input ic-split-50 ">
								<common:message key="mailtracking.mra.airlinebilling.defaults.capturecn51.rate"/>&nbsp;<span class="iCargoMandatoryFieldIcon">*</span>
								<logic:present	name="airlineCN51DetailsVO" property="applicablerate">
									<%String rateStr="0";%>
									<bean:define id="applicablerate" name="airlineCN51DetailsVO" property="applicablerate" />
									<% rateStr=((Double)applicablerate).toString(); %>
									<ihtml:text  indexId="rowCount"  property="rate" value="<%=rateStr%>"
									onchange="change(this.rowCount)" componentID="CMP_MAILTRACKING_MRA_INWARDBILLING_CAPTURECN51_RATE"/>
								</logic:present>
								<logic:notPresent name="airlineCN51DetailsVO" property="applicablerate" >
									<ihtml:text  indexId="rowCount"  property="rate" value=""
									componentID="CMP_MAILTRACKING_MRA_INWARDBILLING_CAPTURECN51_RATE"/>
								</logic:notPresent>
							</div>
							<div class="ic-input ic-split-50 ">
								<common:message key="mailtracking.mra.airlinebilling.defaults.capturecn51.amount"/>
								<logic:present name="airlineCN51DetailsVO" property="totalamountincontractcurrency" >
									<ibusiness:moneyEntry indexId="rowCount"  formatMoney="true" componentID="CMP_MAILTRACKING_MRA_INWARDBILLING_CAPTURECN51_AMOUNT" id="totalamountincontractcurrency"
									 name="airlineCN51DetailsVO" moneyproperty="totalamountincontractcurrency"  property="totalAmount"  maxlength="20" style="text-align : right;border: 0px;background:"/>
								</logic:present>
								<logic:notPresent name="airlineCN51DetailsVO" property="totalamountincontractcurrency" >
									<ibusiness:moneyEntry indexId="rowCount"  formatMoney="true" componentID="CMP_MAILTRACKING_MRA_INWARDBILLING_CAPTURECN51_AMOUNT" id="totalamountincontractcurrency"
									 name="airlineCN51DetailsVO" moneyproperty="totalamountincontractcurrency"  property="totalAmount"  maxlength="20" style="text-align : right;border: 0px;background:"/>											
								</logic:notPresent>
							</div>
						</div>
					
					</div>
						<div class="ic-foot-container paddR5">
						 <div class="ic-button-container">
							<ihtml:nbutton property="btnNew"  componentID="CMP_MAILTRACKING_MRA_INWARDBILLING_CAPTURECN51_DETAILS_NEW"  >
								<common:message  key="mailtracking.mra.defaults.capturecn51.capturecn51details.btn.new"/>
							</ihtml:nbutton>
							<ihtml:nbutton property="btSave" componentID="CMP_MAILTRACKING_MRA_INWARDBILLING_CAPTURECN51_DETAILS_OK">
							  <common:message   key="mailtracking.mra.defaults.capturecn51.capturecn51details.btn.ok" />
							</ihtml:nbutton>
							<ihtml:nbutton property="btClose" componentID="CMP_MAILTRACKING_MRA_INWARDBILLING_CAPTURECN51_DETAILS_CLOSE">
							  <common:message   key="mailtracking.mra.defaults.capturecn51.capturecn51details.btn.close" />
							</ihtml:nbutton>	
						 </div>
						</div>
					</div>	
			</ihtml:form>
		</div>
					
		 
		  
	</body>
</html:html>
