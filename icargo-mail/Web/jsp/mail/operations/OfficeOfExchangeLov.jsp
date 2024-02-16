<%--
/***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  Mailtracking
* File Name				:  OfficeOfExchangeLov.jsp
* Date					:  21-June-2006,27-Oct-2015
* Author(s)				:  A-2047,A-6770
*************************************************************************/
--%>
<%
	response.setDateHeader("Expires",0);
	response.setHeader("Pragma","no-cache");

	if (request.getProtocol().equals("HTTP/1.1")) {
		response.setHeader("Cache-Control","no-cache");
	}
%>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.OfficeOfExchangeVO"%>
<%@ page import="java.util.Collection" %>

<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.OfficeOfExchangeLovForm" %>

<logic:present name="OfficeOfExchangeLovForm">
	<bean:define id="form"
		name="OfficeOfExchangeLovForm"
		toScope="page"
		type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.OfficeOfExchangeLovForm"/>
</logic:present>
	
	
<html>
	<head> 
		
		<title>
			<common:message  bundle="oeLovResources"  key="mailtracking.defaults.oelov.lbl.title" scope="request"/>
		</title>
		<meta name="decorator" content="popuppanelrestyledui">
		<common:include type="script" src="/js/mail/operations/OfficeOfExchangeLov_Script.jsp"/>
	</head>
	
	<body>
		<bean:define id="strAction" name="form"  property="lovaction" scope="page" toScope="page"/>
		<div class="iCargoPopUpContent">
			<ihtml:form action="<%=(String)strAction%>" styleClass="ic-main-form">
			
		<!--  Hidden to store the selections in case of pagination-->

		<ihtml:hidden name="form" property="lovaction"  />
		<ihtml:hidden name="form" property="selectedValues"  />
		<ihtml:hidden name="form" property="lastPageNum" />
		<ihtml:hidden name="form" property="displayPage" />
		<ihtml:hidden name="form" property="multiselect" />
		<ihtml:hidden name="form" property="pagination" />
		<ihtml:hidden name="form" property="formCount" />
		<ihtml:hidden name="form" property="lovTxtFieldName" />
		<ihtml:hidden name="form" property="lovDescriptionTxtFieldName" />
		<ihtml:hidden name="form" property="index" />
		
		<!--  END -->
		
		<span class="ic-page-title ic-display-none">
			<common:message  key="mailtracking.defaults.oelov.lbl.heading" scope="request"/>
		</span>
		
		<div class="ic-head-container">
			<div class="ic-filter-panel">
				<div class="ic-row">
					<div class="ic-col-15">
							<label>
								<common:message  key="mailtracking.defaults.oelov.lbl.code" scope="request"/>
							</label></br>
							<ihtml:text property="code" name="form" componentID="TXT_MAILTRACKING_DEFAULTS_OELOV_CODE"  maxlength="10"/>
						</div>
					<div class="ic-col-30">
							<label>
								<common:message  key="mailtracking.defaults.oelov.lbl.description" scope="request"/>
							</label></br>
							<ihtml:text property="description" name="form" componentID="TXT_MAILTRACKING_DEFAULTS_OELOV_NAME"  maxlength="100"/>
						</div>
					<div class="ic-col-40">
					   <label>
								<common:message  key="mailtracking.defaults.oelov.lbl.airportCode" scope="request"/>
						</label></br>
							<ihtml:text  property="airportCode" name="form" componentID="TXT_MAILTRACKING_DEFAULTS_OEMASTER_ARPCOD"  maxlength="4"/>
						<div class="lovImg">
								<img id="arpcodeLov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" onclick="displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.airportCode.value,'Airport','1','airportCode','',0);"/>
							</div>
                     </div>
                     <div class="ic-col-40">
					         <label>
								<common:message  key="mailtracking.defaults.oelov.lbl.poaCode" scope="request"/>
					          </label></br>
							  <ihtml:text  property="poaCode" name="form" componentID="TXT_MAILTRACKING_DEFAULTS_OEMASTER_PACODE"  maxlength="5"/>
						<div class="lovImg">
								<img  id="paLOV" value="paLOV" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" onClick="displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',targetFormName.elements.poaCode.value,'PA','0','poaCode','',0);"/>
						</div>
                    </div>			 
						<div class="ic-button-container">
							<ihtml:nbutton property="btnList" componentID="BUT_MAILTRACKING_DEFAULTS_OELOV_LIST" accesskey="L">
										<common:message  key="mailtracking.defaults.oelov.btn.btlist" />
									</ihtml:nbutton>
										<ihtml:nbutton property="btnClear" componentID="BUT_MAILTRACKING_DEFAULTS_OELOV_CLEAR" accesskey="C">
										<common:message  key="mailtracking.defaults.oelov.btn.btclear" />
							</ihtml:nbutton>
						</div>
					</div>
				</div>
			</div>
		<div class="ic-main-container">
		<logic:present name="form" property="oeLovPage" >
			<bean:define id="lovList" name="form" property="oeLovPage" toScope="page"/>
				<logic:present name="lovList">
					<bean:define id="multiselect" name="form" property="multiselect" />

						<logic:equal name="form" property="pagination" value="Y">
						<!-- -PAGINATION TAGS -->
							<bean:define id="lastPageNum" name="form" property="lastPageNum" />
								<div class="ic-row">
								<div class="ic-col-50">
									<common:paginationTag pageURL="javascript:preserveSelectedvalues('lastPageNum','displayPage')" name="lovList"
													display="label" labelStyleClass="iCargoResultsLabel" lastPageNum="<%=(String)lastPageNum %>" />
								</div>
								<div class="ic-col-50">					
									<div class="ic-button-container">
										<common:paginationTag pageURL="javascript:preserveSelectedvalues('lastPageNum','displayPage')" name="lovList" display="pages" linkStyleClass="iCargoLink" disabledLinkStyleClass="iCargoLink"
													lastPageNum="<%=(String)lastPageNum%>"/>
									</div>
								</div>
								</div>
								</logic:equal>
							</logic:present>
						</logic:present>
						<logic:notPresent name="form" property="oeLovPage" >
							<div class="ic-row">
							&nbsp;
							</div>
						</logic:notPresent>
						<bean:define id="strFormCount" name="form" property="formCount"  />
						<bean:define id="strMultiselect" name="form" property="multiselect" />
						<bean:define id="strLovTxtFieldName" name="form" property="lovTxtFieldName"  />
						<bean:define id="strLovDescriptionTxtFieldName" name="form" property="lovDescriptionTxtFieldName" />
						<bean:define id="strSelectedValues" name="form" property="selectedValues" />
						<bean:define id="arrayIndex" name="form" property="index"/>
								<div class="ic-row">
								<!--Modified by A-7938 for ICRD-243745-->
									<div class="tableContainer" style="height:240px" >
										<table class="fixed-header-table" >
											<thead>
												<tr>
													<td width="5%"></td>
													<td width="25%"><common:message  key="mailtracking.defaults.oelov.lbl.code" scope="request"/></td>
													<td width="25%"><common:message  key="mailtracking.defaults.oelov.lbl.description" scope="request"/></td>
													<td width="25%"><common:message  key="mailtracking.defaults.oelov.lbl.airportCode" scope="request"/></td>
													<td width="25%"><common:message  key="mailtracking.defaults.oelov.lbl.poaCode" scope="request"/></td>
												</tr>
											</thead>
											<tbody>
												<logic:present name="form" property="oeLovPage">
													<bean:define id="lovList" name="form" property="oeLovPage" toScope="page"/>
														<logic:present name="lovList">
															<logic:iterate id = "val" name="lovList" indexId="indexId">
												
																	<logic:notEqual name="form" property="multiselect" value="Y">
																		<tr ondblclick="setValueOnDoubleClick('<%=((OfficeOfExchangeVO)val).getCode()%>','<%=((OfficeOfExchangeVO)val).getCodeDescription()%>',
																			'<%= strLovTxtFieldName%>','<%=strLovDescriptionTxtFieldName %>',<%=arrayIndex%>)">
																	</logic:notEqual>
																	<logic:equal name="form" property="multiselect" value="Y">
																	<tr>
																		<td>
																			<%--<input type="checkbox" name="selectCheckBox" value="<%=((OfficeOfExchangeVO)val).getCode()%>" onclick="updateSelectedValues(this.form)"> --%>
																			<%

																			if(((String)strSelectedValues).contains(((OfficeOfExchangeVO)val).getCode())){ %>
																				<input type="checkbox" name="selectCheckBox" value="<%=((OfficeOfExchangeVO)val).getCode()%>"  checked="checked"/>
																			<%}else{ %>
																				<input type="checkbox" name="selectCheckBox" value="<%=((OfficeOfExchangeVO)val).getCode()%>"  />
																			<% } %>
																		</td>
																	</logic:equal>
																	<logic:notEqual name="form" property="multiselect" value="Y">
																		<td>
																				<%String checkVal = ((OfficeOfExchangeVO)val).getCode();%>

																				<%--<input type="checkbox" name="selectCheckBox" value="<%=checkVal%>"  onclick="singleSelect('<%=checkVal%>');"/>--%>
																			<%

																			if(   ((String)strSelectedValues).equals(((OfficeOfExchangeVO)val).getCode()  )){ %>
																				<input type="checkbox" name="selectCheckBox" value="<%=checkVal%>"  onclick="singleSelect('<%=checkVal%>');" checked="checked" />
																			<%}else{ %>
																				<input type="checkbox" name="selectCheckBox" value="<%=checkVal%>"  onclick="singleSelect('<%=checkVal%>');"/>
																			<% } %>


																		</td>
																	</logic:notEqual>
																	<td>
																		<bean:write name="val" property="code"/>
																	</td>
																	<td>
																		<bean:write name="val" property="codeDescription"/>
																	</td>
																	<td>
																		<bean:write name="val" property="airportCode"/>
																	</td>
																	<td>
																		<bean:write name="val" property="poaCode"/>
																	</td>
												</tr>
												 </logic:iterate>
											</tbody>
											  </logic:present>
											</logic:present>
										</table>
									</div>
								</div>
							</div>
							<div class="ic-foot-container" >
								<div class="ic-row">
									<div class="ic-button-container">
										<input type="button" title ="Ok" accesskey="O" name="btnOk" value="OK" class="iCargoButtonSmall" onclick="setValueForDifferentModes('<%=strMultiselect%>','<%=strFormCount%>','<%=strLovTxtFieldName%>','<%=strLovDescriptionTxtFieldName %>',<%=arrayIndex%>)" />
									<ihtml:nbutton property="btnClose" accesskey="S" componentID="BUT_MAILTRACKING_DEFAULTS_OELOV_CLOSE">
										<common:message  key="mailtracking.defaults.oelov.btn.btcancel" />
									</ihtml:nbutton>
									</div>
								</div>
							</div>
			</ihtml:form>
		</div>
	</body>
</html>


