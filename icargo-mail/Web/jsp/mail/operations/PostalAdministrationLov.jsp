<%--
/***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  Mailtracking
* File Name				:  PostalAdministrationLov.jsp
* Date					:  19-June-2006
* Author(s)				:  A-2047
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
<%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO"%>
<%@ page import="java.util.Collection" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.PostalAdministrationLovForm" %>

<logic:present name="PostalAdministrationLovForm">
	<bean:define id="form"
		name="PostalAdministrationLovForm"
		toScope="page"
		type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.PostalAdministrationLovForm"/>
</logic:present>

		
	
<html:html>
	<head> 
		<%@ include file="/jsp/includes/customcss.jsp" %>
		<title><common:message  bundle="postalAdminLovResources"  key="mailtracking.defaults.palov.lbl.title" scope="request"/></title>
		<meta name="decorator" content="popup_panel">
		
		<common:include type="script" src="/js/mail/operations/PostalAdministrationLov_Script.jsp"/>
		
		
	</head>

<body id="bodyStyle">
	
	<bean:define id="strAction" name="form"  property="lovaction" scope="page" toScope="page"/>
	<div id="divmain" class="iCargoPopUpContent">
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
	
	<div class="ic-content-main">
			<span class="ic-page-title ic-display-none">
				<common:message  bundle="postalAdminLovResources"  key="mailtracking.defaults.palov.lbl.title" scope="request"/>
			</span>
			<div class="ic-head-container">		
				<div class="ic-filter-panel">
					<div class="ic-input-container">
						<div class="ic-row">
							<div class="ic-input">
									<label>
									<common:message  key="mailtracking.defaults.palov.lbl.code" scope="request"/>
									</label>
										<ihtml:text property="code" name="form" componentID="TXT_MAILTRACKING_DEFAULTS_PALOV_CODE"  maxlength="20"/>
								</div>
								<div class="ic-input">
									<label>
									<common:message  key="mailtracking.defaults.palov.lbl.description" scope="request"/>
									</label>
										<ihtml:text property="description" name="form" componentID="TXT_MAILTRACKING_DEFAULTS_PALOV_NAME"  maxlength="100"/>
								</div>
						
						
							<div class="ic-button-container">
								<div class="ic-row ic-right">
									<ihtml:nbutton property="btnList" accesskey="L" componentID="BUT_MAILTRACKING_DEFAULTS_PALOV_LIST">
										<common:message  key="mailtracking.defaults.palov.btn.btlist" />
									</ihtml:nbutton>
										<ihtml:nbutton property="btnClear" accesskey="C" componentID="BUT_MAILTRACKING_DEFAULTS_PALOV_CLEAR">
										<common:message  key="mailtracking.defaults.palov.btn.btclear" />
									</ihtml:nbutton>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="ic-main-container">
				<div class="ic-row">
					<logic:present name="form" property="paLovPage" >
						<bean:define id="lovList" name="form" property="paLovPage" toScope="page"/>
						<logic:present name="lovList">
							<bean:define id="multiselect" name="form" property="multiselect" />
								<logic:equal name="form" property="pagination" value="Y">
									<!-- -PAGINATION TAGS -->
									<bean:define id="lastPageNum" name="form" property="lastPageNum" />
								<div class="ic-row">	
								<!--Modified by A-7938 for ICRD-243746-->
								<div class="ic-col-40">
									<common:paginationTag pageURL="javascript:preserveSelectedvalues('lastPageNum','displayPage')" name="lovList"
									display="label" labelStyleClass="iCargoResultsLabel" lastPageNum="<%=(String)lastPageNum %>" />
											
								</div>
								<!--Modified by A-7938 for ICRD-243746-->
								<div class="ic-col-60 paddL20" style="text-align:right"><!--modified by A-7371 as part of ICRD-249235-->
									<common:paginationTag pageURL="javascript:preserveSelectedvalues('lastPageNum','displayPage')" name="lovList" display="pages"
									labelStyleClass="iCargoLink" linkStyleClass="iCargoLink" disabledLinkStyleClass="iCargoLink"
									lastPageNum="<%=(String)lastPageNum%>"/>
								</div>
								</div>
									<!--  END -->
								</logic:equal>
							</logic:present>
						</logic:present>
						<bean:define id="strFormCount" name="form" property="formCount"  />
						<bean:define id="strMultiselect" name="form" property="multiselect" />
						<bean:define id="strLovTxtFieldName" name="form" property="lovTxtFieldName"  />
						<bean:define id="strLovDescriptionTxtFieldName" name="form" property="lovDescriptionTxtFieldName" />
						<bean:define id="strSelectedValues" name="form" property="selectedValues" />
						<bean:define id="arrayIndex" name="form" property="index"/>
				</div>
				<div class="ic-row">
				<!--Modified by A-7938 for ICRD-243746-->
					<div class="tableContainer" id="div1" style="height:590px;">
						<table class="fixed-header-table" id="lovListTable">
							<thead>
								<tr>
									<td width="10%"> &nbsp;</td>
									<td width="45%" class="iCargoLabelleftAligned"><common:message  key="mailtracking.defaults.palov.lbl.code" scope="request"/></td>
									<td width="45%" class="iCargoLabelLeftAligned"><common:message  key="mailtracking.defaults.palov.lbl.description" scope="request"/></td>
							   </tr>
							</thead>
							<logic:present name="form" property="paLovPage">
								<bean:define id="lovList" name="form" property="paLovPage" toScope="page"/>
									<logic:present name="lovList">
										<tbody>
											<% int i=0;%>
											<logic:iterate id = "val" name="lovList" indexId="indexId">
												
												
													<logic:notEqual name="form" property="multiselect" value="Y">
													<tr  ondblclick="setValueOnDoubleClick('<%=((PostalAdministrationVO)val).getPaCode()%>','<%=((PostalAdministrationVO)val).getPaName()%>',
															'<%= strLovTxtFieldName%>','<%=strLovDescriptionTxtFieldName %>',<%=arrayIndex%>)">
													</logic:notEqual>
													<logic:equal name="form" property="multiselect" value="Y">
													<tr>
														<td>
															
															<%

															if(((String)strSelectedValues).contains(((PostalAdministrationVO)val).getPaCode())){ %>
																<input type="checkbox" name="selectCheckBox" value="<%=((PostalAdministrationVO)val).getPaCode()%>"  checked="checked"/>
															<%}else{ %>
																<input type="checkbox" name="selectCheckBox" value="<%=((PostalAdministrationVO)val).getPaCode()%>"  />
															<% } %>
														</td>
													</logic:equal>
													<logic:notEqual name="form" property="multiselect" value="Y">
															<td>
																<%String checkVal = ((PostalAdministrationVO)val).getPaCode()+"-"+((PostalAdministrationVO)val).getPaName();%>

																
															<%

															if(   ((String)strSelectedValues).equals(((PostalAdministrationVO)val).getPaCode()  )){ %>
																<input type="checkbox" name="selectCheckBox" value="<%=checkVal%>"  onclick="singleSelect('<%=checkVal%>');" checked="checked" />
															<%}else{ %>
																<input type="checkbox" name="selectCheckBox" value="<%=checkVal%>"  onclick="singleSelect('<%=checkVal%>');"/>
															<% } %>
															</td>
													</logic:notEqual>

													<td>
														<bean:write name="val" property="paCode"/>
													</td>
													<td>
														<bean:write name="val" property="paName"/>
													</td>
													
												</tr>
												
											</logic:iterate>
										</tbody>
								</logic:present>
							</logic:present>
						</table>
					</div>
					<logic:equal name="form" property="multiselect" value="Y">
							<tr>
							<td>
							<div id="selectiondiv" class="multiselectPanelStyle" name="PostalAdministration">
							</div>
							</td>
							</tr>
							</logic:equal>
				</div>
			</div>
			<div class="ic-foot-container">
				<div class="ic-button-container paddR5">
					<div class="ic-row">
						<input type="button" name="btnOk" value="OK" title="Ok" class="iCargoButtonSmall" onclick="setValueForDifferentModes('<%=strMultiselect%>','<%=strFormCount%>','<%=strLovTxtFieldName%>','<%=strLovDescriptionTxtFieldName %>',<%=arrayIndex%>)" />
							<ihtml:nbutton property="btnClose" accesskey="O" componentID="BUT_MAILTRACKING_DEFAULTS_PALOV_CLOSE">
								<common:message  key="mailtracking.defaults.palov.btn.btcancel" />
							</ihtml:nbutton>
					</div>
				</div>
			</div>
	</div>
				
	</ihtml:form>
	</div>
	
	</body>
</html:html>


