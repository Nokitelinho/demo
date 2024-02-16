<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  Mailtracking
* File Name				:  ListCCA.jsp
* Date					:  14-June-2006
* Author(s)				:  A-2391
*************************************************************************/
 --%>


 <%@ page language="java" %>
 <%@ include file="/jsp/includes/tlds.jsp"%>
 <%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListCCAForm"%>
 <%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>

<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>

 <html:html>
 <head>
	
		
	
 	
 	<title><common:message bundle="listCCA" key="mailtracking.mra.gpabilling.listmca.lbl.title" /></title>
 	<meta name="decorator" content="mainpanelrestyledui">
 	<common:include type="script" src="/js/mail/mra/defaults/ListCCA_Script.jsp"/>
 </head>

 <body>
	
	
	
	

	<%@include file="/jsp/includes/reports/printFrame.jsp" %>

	<bean:define id="form"
		 name="ListCCAForm"
		 type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListCCAForm"
		 toScope="page" />


	 <business:sessionBean id="OneTimeValues"
	 	 moduleName="mailtracking.mra.defaults"
		 screenID="mailtracking.mra.defaults.listcca"
	 	  method="get"
	  attribute="OneTimeVOs" />

	 <business:sessionBean id="KEY_CCALIST"
		moduleName="mailtracking.mra.defaults"
		screenID="mailtracking.mra.defaults.listcca"
		method="get"
		attribute="CCADetailsVOs" />

	<business:sessionBean
			id="LIST_FILTERVO"
			moduleName="mailtracking.mra.defaults"
			screenID="mailtracking.mra.defaults.listcca"
			method="get"
		attribute="CCAFilterVO" />


 	 <div  class="iCargoContent ic-masterbg" style="overflow:auto;width:100%;height:100%;">
 		<ihtml:form action="/mailtracking.mra.defaults.listcca.screenload.do">
 		<ihtml:hidden property="lastPageNum" />
	    <ihtml:hidden property="displayPage" />
	    <ihtml:hidden property="comboFlag" />
        <input type="hidden" name="mySearchEnabled" />
		
		<div class="ic-content-main">
		<span class="ic-page-title">
			<common:message key="mailtracking.mra.defaults.listcca.lbl.heading" />
		</span>
			<div class="ic-head-container">	 

				<div class="ic-filter-panel">
					<div class="ic-input-container">
						<fieldset class="ic-field-set" >
							<legend><common:message key="mailtracking.mra.gpabilling.listmca.lbl.search" /></legend>
								<div class="ic-row">
									<jsp:include page="ListCCA_Filter.jsp" /> 
									</div>
					
					
										<!--
										<td>
										<fieldset class="iCargoFieldSet"style="width:85%;" height="100%">
										<legend class="iCargoLegend"><common:message key="mailtracking.mra.gpabilling.listmca.lbl.dsndetails" /></legend>
											<table  align="center" style="width:100%;" class="iCargoBorderLessTable" cellpadding="1">
										<td class="iCargoLabelRightAligned">
										  <common:message key="mailtracking.mra.gpabilling.listmca.lbl.dsn" />
										</td>
										
										
										<td>
										<logic:present name="LIST_FILTERVO" property="dsn">
										<bean:define id="dsn" name="LIST_FILTERVO" property="dsn"/>
										  <ihtml:text property="dsn" maxlength="4" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_DSN" value="<%=(String)dsn%>"/>
										  <td>
										 <img src="<%=request.getContextPath()%>/images/lov.png" id="dsnlov" height="22" width="22" alt="" disabled="true"/></td>
										</logic:present>
										<logic:notPresent name="LIST_FILTERVO" property="dsn">
										  <ihtml:text property="dsn" maxlength="4" value="" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_DSN" />
										  <img src="<%=request.getContextPath()%>/images/lov.png" id="dsnlov" height="22" width="22" alt="" /></td>
										</logic:notPresent>
										</td>

										<td class="iCargoLabelRightAligned">
										  <common:message key="mailtracking.mra.gpabilling.listmca.lbl.dsndate" />
										</td>
										<td width="15%" >
										 <logic:present name="LIST_FILTERVO" property="dsnDate">
										 <bean:define id="dsnDate" name="LIST_FILTERVO" property="dsnDate" toScope="page"/>
											<%String dsnDat=TimeConvertor.toStringFormat(((LocalDate)dsnDate).toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
											<ibusiness:calendar
											 property="dsnDate"
											componentID="CMP_MAILTRACKING_MRA_DEFAULTS_DSNDATE"
											 type="image"
											 id="dsnDate"
											 value="<%=(String)dsnDat%>" maxlength="11" />
										 </logic:present>
										 <logic:notPresent name="LIST_FILTERVO" property="dsnDate">
											<ibusiness:calendar
											 property="dsnDate"
											componentID="CMP_MAILTRACKING_MRA_DEFAULTS_DSNDATE"
											 type="image"
											 id="dsnDate"
											 value="" maxlength="11" />
										 </logic:notPresent>
										</td></table>
										</fieldset>-->
              				 
					 
										<div class="ic-row">
											<fieldset class="ic-field-set" >
												<legend><common:message key="mailtracking.mra.gpabilling.listmca.lbl.dsndetails" /></legend>
													<div class="ic-row ic-label-40">
														<div class="ic-input ic-split-12">
															<label><common:message key="mailtracking.mra.gpabilling.listmca.lbl.orgoe" /></label>
															<logic:present name="LIST_FILTERVO" property="originOE">
															<bean:define id="originOE" name="LIST_FILTERVO" property="originOE" toScope="page"/>
															<ihtml:text property="originOfficeOfExchange" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_OOE" value="<%=String.valueOf(originOE)%>" readonly="false" maxlength="6" />
															<div class="lovImg"> 
															<img name="mailOOELov" id="mailOOELov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22">
															</div>
															</logic:present>
															<logic:notPresent name="LIST_FILTERVO" property="originOE">
															<ihtml:text property="originOfficeOfExchange" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_OOE" readonly="false" maxlength="6" />
															<div class="lovImg"> 
															<img name="mailOOELov" id="mailOOELov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22">
															</div>
															</logic:notPresent>
														</div>

														<div class="ic-input ic-split-13">
															<label><common:message key="mailtracking.mra.gpabilling.listmca.lbl.destoe" /></label>
															 <logic:present name="LIST_FILTERVO" property="destinationOE">
															<bean:define id="destinationOE" name="LIST_FILTERVO" property="destinationOE" toScope="page"/>
															<ihtml:text property="destinationOfficeOfExchange" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_DOE" value="<%=String.valueOf(destinationOE)%>" readonly="false" maxlength="6" />
															<div class="lovImg"> 
															<img name="mailOOELov" id="mailOOELov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22">
															</div> 
															</logic:present>
															<logic:notPresent name="LIST_FILTERVO" property="destinationOE"> 
															<ihtml:text property="destinationOfficeOfExchange" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_DOE" readonly="false" maxlength="6" />
															<div class="lovImg"> 
															<img name="mailDOELov" id="mailDOELov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22">
															</div> 
															</logic:notPresent>
														</div>
														<div class="ic-input ic-split-15">
															<label><common:message key="mailtracking.mra.gpabilling.listmca.lbl.mailcategory" /></label>
			
															  <% String categoryCodeValue = ""; %>
																				<logic:present name="LIST_FILTERVO" property="categoryCode">
																				<bean:define id="categoryCode" name="LIST_FILTERVO" property="categoryCode" toScope="page"/>
																					<% categoryCodeValue = (String)categoryCode; %>
																				</logic:present>
																				<logic:notPresent name="LIST_FILTERVO" property="categoryCode">
																				</logic:notPresent>
														
															  <ihtml:select	styleClass="iCargoMediumComboBox"
																					componentID="CMP_MRA_GPABILLING_category"
																					property="mailCategory"
																					value="<%=categoryCodeValue%>"
																					tabindex="30">
																				
																	<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
																	<logic:present name="OneTimeValues">
																		<logic:iterate id="oneTimeValue" name="OneTimeValues">
																		<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
																		<logic:equal name="parameterCode" value="mailtracking.defaults.mailcategory">
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
															<div class="ic-input ic-split-10">
																<label> <common:message key="mailtracking.mra.gpabilling.listmca.lbl.subclass" /></label>
																<logic:present name="LIST_FILTERVO" property="subClass">
																<bean:define id="subClass" name="LIST_FILTERVO" property="subClass" toScope="page"/>
																<ihtml:text property="subClass" componentID="CMP_MRA_GPABILLING_SUBCLASS" value="<%=String.valueOf(subClass)%>" readonly="false" maxlength="2" />
																<div class="lovImg"> 
																<img name="subClassFilterLOV" id="subClassFilterLOV" value="subClassLov" src="<%=request.getContextPath()%>/images/lov.png" width="18" height="18" />
																</div>
																</logic:present>
																<logic:notPresent name="LIST_FILTERVO" property="subClass"> 
																<ihtml:text property="subClass" componentID="CMP_MRA_GPABILLING_SUBCLASS" maxlength="2"/>
																<div class="lovImg"> 
																<img name="subClassFilterLOV" id="subClassFilterLOV" value="subClassLov" src="<%=request.getContextPath()%>/images/lov.png" width="18" height="18" />
																</div>
																</logic:notPresent>
															</div>
			
															<div class="ic-input ic-split-10">
																<label><common:message key="mailtracking.mra.gpabilling.listmca.lbl.year" /></label>
																<logic:present name="LIST_FILTERVO" property="year">
																<bean:define id="year" name="LIST_FILTERVO" property="year" toScope="page"/>
																<ihtml:text property="year" componentID="CMP_MRA_GPABILLING_YEAR" value="<%=String.valueOf(year)%>" readonly="false" maxlength="1" />
																</logic:present>
																<logic:notPresent name="LIST_FILTERVO" property="year"> 
																<ihtml:text property="year" componentID="CMP_MRA_GPABILLING_YEAR" readonly="false" maxlength="1" />				          
																</logic:notPresent>			          
															</div>	
							 
															 <div class="ic-input ic-split-10">
																<label><common:message key="mailtracking.mra.gpabilling.listmca.lbl.dsn" /></label>
																<logic:present name="LIST_FILTERVO" property="dsn">
																<bean:define id="dsn" name="LIST_FILTERVO" property="dsn" toScope="page"/>
																<ihtml:text property="dsn" componentID="CMP_MRA_GPABILLING_DSN" value="<%=String.valueOf(dsn)%>" readonly="false" maxlength="4" />	
																</logic:present>
																<logic:notPresent name="LIST_FILTERVO" property="dsn"> 
																<ihtml:text property="dsn" componentID="CMP_MRA_GPABILLING_DSN" readonly="false" maxlength="4" />				          
																</logic:notPresent>			          
															</div>
									
															 <div class="ic-input ic-split-10">
																<label><common:message key="mailtracking.mra.gpabilling.listmca.lbl.rsn" /></label>
																<logic:present name="LIST_FILTERVO" property="rsn">
																<bean:define id="rsn" name="LIST_FILTERVO" property="rsn" toScope="page"/>
																<ihtml:text property="receptacleSerialNumber" componentID="CMP_MRA_GPABILLING_RSN" value="<%=String.valueOf(rsn)%>" readonly="false" maxlength="3" />	
																</logic:present>
																<logic:notPresent name="LIST_FILTERVO" property="rsn"> 
																<ihtml:text property="receptacleSerialNumber" componentID="CMP_MRA_GPABILLING_RSN" readonly="false" maxlength="3" />				          
																</logic:notPresent>
															 </div>
															<div class="ic-input ic-split-10">
																<label><common:message key="mailtracking.mra.gpabilling.listmca.lbl.hni" /></label>
																<logic:present name="LIST_FILTERVO" property="hni">
																<bean:define id="hni" name="LIST_FILTERVO" property="hni" toScope="page"/>
																<ihtml:text property="highestNumberIndicator" componentID="CMP_MRA_GPABILLING_HNI" value="<%=String.valueOf(hni)%>" readonly="false" maxlength="1" />				          
																</logic:present>
																<logic:notPresent name="LIST_FILTERVO" property="hni"> 
																<ihtml:text property="highestNumberIndicator" componentID="CMP_MRA_GPABILLING_HNI" readonly="false" maxlength="1" />				          
																</logic:notPresent>
															 </div>
															 <div class="ic-input ic-split-10">
																<label><common:message key="mailtracking.mra.gpabilling.listmca.lbl.RI" /></label>
																<logic:present name="LIST_FILTERVO" property="regInd">
																<bean:define id="regInd" name="LIST_FILTERVO" property="regInd" toScope="page"/>
																<ihtml:text property="registeredIndicator" componentID="CMP_MRA_GPABILLING_RI" value="<%=String.valueOf(regInd)%>" readonly="false" maxlength="1" />	
																</logic:present>
																<logic:notPresent name="LIST_FILTERVO" property="regInd"> 
																<ihtml:text property="registeredIndicator" componentID="CMP_MRA_GPABILLING_RI" readonly="false" maxlength="1" />				          
																</logic:notPresent>			          
															 </div>		
														</div>
												</fieldset>
											</div>
										
				   
											<div class="ic-row">
											<div class="ic-col-30">
												<fieldset class="ic-field-set" >
													<legend><common:message key="mailtracking.mra.gpabilling.listmca.lbl.gpadetails" /></legend>
														<div class="ic-row ic-label-30">
															<div class="ic-input ic-split-30">
															<label><common:message key="mailtracking.mra.gpabilling.listmca.lbl.gpacode" /></label>
															<logic:present name="LIST_FILTERVO" property="gpaCode">
															<bean:define id="gpaCode" name="LIST_FILTERVO" property="gpaCode" toScope="page"/>
															<ihtml:text property="gpaCode" maxlength="6" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_GPACODE" value="<%=String.valueOf(gpaCode)%>" readonly="false"/>
															<div class="lovImg"> 
															<img src="<%=request.getContextPath()%>/images/lov.png" id="gpaCodelov" height="22" width="22" disabled="true"/>
															</div> 
															</logic:present>
															<logic:notPresent name="LIST_FILTERVO" property="gpaCode">
															<ihtml:text property="gpaCode" maxlength="6" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_GPACODE" readonly="false"/>
															<div class="lovImg"> 
															<img src="<%=request.getContextPath()%>/images/lov.png" id="gpaCodelov" height="22" width="22" />
															</div>
															</logic:notPresent>
															</div>

															<div class="ic-input ic-split-50">
															
															<div id="gpanameDiv">
															<label><common:message key="mailtracking.mra.gpabilling.listmca.lbl.gpaname" /></label>
															<logic:present name="LIST_FILTERVO" property="gpaName">
															<bean:define id="gpaName" name="LIST_FILTERVO" property="gpaName" toScope="page"/>
															 <ihtml:text property="gpaName" maxlength="50" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_GPANAME" value="<%=String.valueOf(gpaName)%>"  readonly="true"/>
															</logic:present>
															<logic:notPresent name="LIST_FILTERVO" property="gpaName">
															<ihtml:text property="gpaName" maxlength="50" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_GPANAME"  readonly="true"/>
															</logic:notPresent>
															</div>
															</div>															
														</div>
												</fieldset>
											</div>
					<!--<td>
						<fieldset class="iCargoFieldSet" style="width:98%;" height="100%"><legend
							class="iCargoLegend"><common:message key="mailtracking.mra.gpabilling.listmca.lbl.mcaissuedate" /></legend>
							<table  align="center" style="width:100%;" class="iCargoBorderLessTable" cellpadding="1">
								<tr><td class="iCargoLabelRightAligned">
								<common:message key="mailtracking.mra.gpabilling.listmca.lbl.from" />

								</td>
								<td width="15%" >
								 <logic:present name="LIST_FILTERVO" property="fromDate">
									<bean:define id="listFilter"
											 name="LIST_FILTERVO"  type="com.ibsplc.icargo.business.mail.mra.defaults.vo.ListCCAFilterVO"
												toScope="page" />
									<%
										String fDate = "";
									   if(listFilter.getFromDate() != null) {
											fDate = TimeConvertor.toStringFormat(
														listFilter.getFromDate().toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);
										}
									%>
									<ibusiness:calendar type="image" id="frmDate" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_FROMDATE" property="frmDate"  value="<%=fDate%>" maxlength="11"/>
									</logic:present>
									<logic:notPresent name="LIST_FILTERVO" property="fromDate">
									<ibusiness:calendar id="frmDate" type="image"  componentID="CMP_MAILTRACKING_MRA_DEFAULTS_FROMDATE"  property="frmDate" value="" maxlength="11"/>
									</logic:notPresent>
                    			</td>
								<td class="iCargoLabelRightAligned">
								<common:message key="mailtracking.mra.gpabilling.listmca.lbl.to" />
								<span class="iCargoMandatoryFieldIcon">*</span>
								</td>
								<td width="15%" >
								 <logic:present name="LIST_FILTERVO" property="toDate">
									<bean:define id="listFilter"
											 name="LIST_FILTERVO"  type="com.ibsplc.icargo.business.mail.mra.defaults.vo.ListCCAFilterVO"
												toScope="page" />
									<%
										String tDate = "";
									   if(listFilter.getToDate() != null) {
											tDate = TimeConvertor.toStringFormat(
														listFilter.getToDate().toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);
										}
									%>
									<ibusiness:calendar type="image" id="toDate" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_TODATE" property="toDate"  value="<%=tDate%>" maxlength="11"/>
									</logic:present>
									<logic:notPresent name="LIST_FILTERVO" property="toDate">
									<ibusiness:calendar id="toDate" type="image"  componentID="CMP_MAILTRACKING_MRA_DEFAULTS_TODATE"  property="toDate" value="<%= form.getToDate() %>" maxlength="11"/>
									</logic:notPresent>
                    			</td>

								</tr>
							</table>
						   </fieldset>
					</td>-->
					<div class="ic-col-30">
						<fieldset class="ic-field-set">
							<legend class="iCargoLegend"><common:message key="mailtracking.mra.gpabilling.listmca.lbl.gpadetails" /></legend>
								<div class="ic-row ic-label-45">
									<div class="ic-input ic-split-30">
										<label><common:message key="mailtracking.mra.gpabilling.listmca.lbl.origin" /></label>
										 <logic:present name="LIST_FILTERVO" property="origin">
										 <bean:define id="originCode" name="LIST_FILTERVO" property="origin" toScope="page"/>
										 <ihtml:text property="origin" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_ORG" value="<%=String.valueOf(originCode)%>"  maxlength="5" />
										 <div class="lovImg"> 
										 <img height="22" id="stationlov"  src="<%=request.getContextPath()%>/images/lov.png" disabled="true" width="22"  />
										 </div> 
										 </logic:present>
										 <logic:notPresent name="LIST_FILTERVO" property="origin">
										 <ihtml:text property="origin" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_ORG" maxlength="5" />
										<div class="lovImg"> 
										<img height="22" id="stationlov"  src="<%=request.getContextPath()%>/images/lov.png" width="22"  />
										</div>
										 </logic:notPresent>	
									</div>
									<div class="ic-input ic-split-50">
										<label><common:message key="mailtracking.mra.gpabilling.listmca.lbl.destination" /></label>
										<logic:present name="LIST_FILTERVO" property="destination">
										<bean:define id="destnCode" name="LIST_FILTERVO" property="destination" toScope="page"/>
										<ihtml:text property="destination" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_DST" value="<%=String.valueOf(destnCode)%>" maxlength="5" />
										<div class="lovImg"> 
										<img height="22" id="stationCodelov"  src="<%=request.getContextPath()%>/images/lov.png" disabled="true" width="22"  />
										</div>
										</logic:present>
										<logic:notPresent name="LIST_FILTERVO" property="destination">
										<ihtml:text property="destination" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_DST" maxlength="5" />
										<div class="lovImg"> 
										<img height="22" id="stationCodelov"  src="<%=request.getContextPath()%>/images/lov.png" width="22"  />
										</div>
										</logic:notPresent>
									</div>
								</div>
						</fieldset>
					</div>
					<!--Added by A-7540-->
				<div class="ic-col-35">
					<div class="ic-row ic-label-45 paddL20" style="padding-top:25px;">
					    <div class="ic-input ic-split-30">
						<label>	<common:message key="mailtracking.mra.gpabilling.listmca.lbl.mcacreationtype" /></label>
															
															<logic:present name="LIST_FILTERVO" property="mcacreationtype">
															   <ihtml:select name="LIST_FILTERVO"
																styleClass="iCargoMediumComboBox"
																componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MCACREATIONTYPE"
																property="mcacreationtype">
																<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
																<logic:present name="OneTimeValues">
																	<logic:iterate id="oneTimeValue" name="OneTimeValues">
																		<bean:define id="parameterCode" name="oneTimeValue"
																			property="key" />
																		<logic:equal name="parameterCode"
																			value="mailtracking.mra.defaults.mcacreationtype">
																			<bean:define id="parameterValues" name="oneTimeValue"
																				property="value" />
																			<logic:iterate id="parameterValue"
																				name="parameterValues"
																				type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																				<logic:present name="parameterValue"
																					property="fieldValue">
																					<bean:define id="fieldValue" name="parameterValue"
																						property="fieldValue" />
																					<bean:define id="fieldDescription"
																						name="parameterValue" property="fieldDescription" />
																					<ihtml:option
																						value="<%=String.valueOf(fieldValue).toUpperCase() %>">
																						<%=String.valueOf(fieldDescription)%>
																					</ihtml:option>
																				</logic:present>
																			</logic:iterate>
																		</logic:equal>
																	</logic:iterate>
																</logic:present>
																</ihtml:select>
														 </logic:present>
														 <logic:notPresent name="LIST_FILTERVO" property="mcacreationtype">
															   <ihtml:select
																styleClass="iCargoMediumComboBox"
																componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MCACREATIONTYPE"
																property="mcacreationtype">
																<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
																<logic:present name="OneTimeValues">
																	<logic:iterate id="oneTimeValue" name="OneTimeValues">
																		<bean:define id="parameterCode" name="oneTimeValue"
																			property="key" />
																		<logic:equal name="parameterCode"
																			value="mailtracking.mra.defaults.mcacreationtype">
																			<bean:define id="parameterValues" name="oneTimeValue"
																				property="value" />
																			<logic:iterate id="parameterValue"
																				name="parameterValues"
																				type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																				<logic:present name="parameterValue"
																					property="fieldValue">
																					<bean:define id="fieldValue" name="parameterValue"
																						property="fieldValue" />
																					<bean:define id="fieldDescription"
																						name="parameterValue" property="fieldDescription" />
																					<ihtml:option
																						value="<%=String.valueOf(fieldValue).toUpperCase() %>">
																						<%=String.valueOf(fieldDescription)%>
																					</ihtml:option>
																				</logic:present>
																			</logic:iterate>
																		</logic:equal>
																	</logic:iterate>
																</logic:present>
																</ihtml:select>
														  </logic:notPresent>
					</div>
						   </div>      
					  </div>
					</div>
					<div class="ic-button-container">	
						<ihtml:nbutton property="btList" accesskey="L" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_LSTBTN" >
						<common:message key="mailtracking.mra.defaults.listcca.lbl.button.list" />
						</ihtml:nbutton>

						<ihtml:nbutton property="btnClear" accesskey="C" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_CLEARBTN" >
						<common:message key="mailtracking.mra.defaults.listcca.lbl.button.clear" />
						</ihtml:nbutton>

					</div>
				</fieldset>
			</div>
			<div class="ic-row ic-label-45" style="padding-top:15px;">	<!--Added by A-7359 for ICRD - 224586 starts here -->

		</div>
		</div>
	</div>
	<div class="ic-main-container">
	<a class="panel upArrow"  collapseFilter="true"  collapseFilterCallbackFun="callbackListCCA"  href="#"></a><!--Added by A-7929 for ICRD - 224586 -->
		<div class="ic-row ic-bold-label ic-left">
			<h4><common:message key="mailtracking.mra.gpabilling.listmca.lbl.mcadetails" /></h4>
		</div>
		  <div class="ic-row">
			<logic:present name="KEY_CCALIST">
				<common:paginationTag pageURL="javascript:submitList('lastPageNum','displayPage')"
				name="KEY_CCALIST"
				display="label"
				labelStyleClass="iCargoResultsLabel"
				lastPageNum="<%=((ListCCAForm)form).getLastPageNum() %>" />
			</logic:present>

			<logic:notPresent name="KEY_CCALIST">
				&nbsp;
			</logic:notPresent>
		<div class="ic-button-container">
			  <logic:present name="KEY_CCALIST">
			  <common:paginationTag
			  pageURL="javascript:submitList('lastPageNum','displayPage')"
			  name="KEY_CCALIST"
			  display="pages"
			 linkStyleClass="iCargoLink"
			disabledLinkStyleClass="iCargoLink"
			  lastPageNum="<%=form.getLastPageNum() %>" 
			  exportToExcel="true"
			  exportTableId="listCCATable"
			  exportAction="mailtracking.mra.defaults.listcca.list.do"
			  fetchCount="50"/>
			  </logic:present>
		</div>
			<logic:notPresent name="KEY_CCALIST">
				&nbsp;
			</logic:notPresent>
		</div>
			<jsp:include page="ListCCA_Table.jsp" /> 
		</div>
		<div class="ic-foot-container">
			<div class="ic-button-container">
					<ihtml:nbutton property="btnPrint" accesskey="P" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_PRINTBTN">
					<common:message key="mailtracking.mra.gpabilling.listmca.lbl.print" />
					</ihtml:nbutton>

				
					<ihtml:nbutton property="btnDelete" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_DELETEBTN" accesskey="E" >
					<common:message key="mailtracking.mra.gpabilling.listmca.lbl.button.delete" />
					</ihtml:nbutton>

					<ihtml:nbutton property="btnAccept" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_ACCEPTBTN" accesskey="T" >
					<common:message key="mailtracking.mra.gpabilling.listmca.lbl.button.accept" />
					</ihtml:nbutton>

					<ihtml:nbutton property="btnReject" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_REJECTBTN" accesskey="J" >
					<common:message key="mailtracking.mra.gpabilling.listmca.lbl.button.reject" />
					</ihtml:nbutton>
					
					<!--//Modified  compID by A-8527 for ICRD-345683-->
					<ihtml:nbutton property="btnCCADetails" accesskey="M" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MCADTLSBTN">
					<common:message key="mailtracking.mra.gpabilling.listmca.lbl.mcadetails" />
					</ihtml:nbutton>

					<ihtml:nbutton property="btnClose" accesskey="O" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_CLOSEBTN">
					<common:message key="mailtracking.mra.defaults.listcca.lbl.button.close" />
					</ihtml:nbutton>
			</div>
		</div>
	</div>
	</ihtml:form>
</div> 
	
	</body>

 </html:html>
