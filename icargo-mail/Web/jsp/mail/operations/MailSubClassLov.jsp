<%--
/***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  Mailtracking
* File Name				:  MailSubClassLov.jsp
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
<%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.MailSubClassVO"%>
<%@ page import="java.util.Collection" %>

<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailSubClassLovForm" %>

<logic:present name="MailSubClassLovForm">
	<bean:define id="form"
		name="MailSubClassLovForm"
		toScope="page"
		type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailSubClassLovForm"/>
</logic:present>
		
	
<html:html>
	<head> 
		<%@ include file="/jsp/includes/customcss.jsp" %>
		<title><common:message  bundle="mailsubclassLovResources"  key="mailtracking.defaults.subclaslov.lbl.title" scope="request"/></title>
		<meta name="decorator" content="popup_panel">
		<common:include type="script" src="/js/mail/operations/MailSubClassLov_Script.jsp"/>
		
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
				<common:message  bundle="mailsubclassLovResources"  key="mailtracking.defaults.subclaslov.lbl.title" scope="request"/>
			</span>
			<div class="ic-head-container">		
				<div class="ic-filter-panel">
					<div class="ic-input-container">
						<div class="ic-row marginT5">
							<div class="ic-input">
							   
								<label>
									<common:message  key="mailtracking.defaults.subclaslov.lbl.code" scope="request"/>
								</label>
									<ihtml:text property="code" name="form" componentID="TXT_MAILTRACKING_DEFAULTS_SUBCLASLOV_CODE"  maxlength="20"/>
							</div>	
							<div class="ic-input">							
								<label>
									<common:message  key="mailtracking.defaults.subclaslov.lbl.description" scope="request"/>
								</label>
									<ihtml:text property="description" name="form" componentID="TXT_MAILTRACKING_DEFAULTS_SUBCLASLOV_NAME"  maxlength="100"/>
								
							   </div>
						
							<div class="ic-button-container">
								<ihtml:nbutton property="btnList" accesskey="L" componentID="BUT_MAILTRACKING_DEFAULTS_SUBCLASLOV_LIST">
										<common:message  key="mailtracking.defaults.subclaslov.btn.btlist" />
									</ihtml:nbutton>
										<ihtml:nbutton property="btnClear" accesskey="C" componentID="BUT_MAILTRACKING_DEFAULTS_SUBCLASLOV_CLEAR">
										<common:message  key="mailtracking.defaults.subclaslov.btn.btclear" />
									</ihtml:nbutton>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="ic-main-container">
				<div class="ic-row">
				<logic:present name="form" property="subClassLovPage" >
					<bean:define id="lovList" name="form" property="subClassLovPage" toScope="page"/>
					<logic:present name="lovList">
						<bean:define id="multiselect" name="form" property="multiselect" />
						<logic:equal name="form" property="pagination" value="Y">
							<!-- -PAGINATION TAGS -->
							<bean:define id="lastPageNum" name="form" property="lastPageNum" />
							<div class="ic-row">
								<div class="ic-col-50"><!--Modified by a-7531-->
									<common:paginationTag pageURL="javascript:preserveSelectedvalues('lastPageNum','displayPage')" name="lovList"
													display="label" labelStyleClass="iCargoResultsLabel" lastPageNum="<%=(String)lastPageNum %>" />
												
								</div>
								<div class="ic-col-50 paddL20"><!--Modified by a-7531-->
									<common:paginationTag pageURL="javascript:preserveSelectedvalues('lastPageNum','displayPage')" name="lovList" display="pages" linkStyleClass="iCargoLink" disabledLinkStyleClass="iCargoLink"
													lastPageNum="<%=(String)lastPageNum%>"/>
								</div>
							</div>
							</logic:equal>
							</logic:present>
				</logic:present>
				<bean:define id="strFormCount" name="form" property="formCount"  />
				<bean:define id="strMultiselect" name="form" property="multiselect" />
				<bean:define id="strLovTxtFieldName" name="form" property="lovTxtFieldName"  />
				<bean:define id="strLovDescriptionTxtFieldName" name="form" property="lovDescriptionTxtFieldName" />
				<bean:define id="strSelectedValues" name="form" property="selectedValues" />
				<bean:define id="arrayIndex" name="form" property="index"/>
						<logic:notPresent name="form" property="subClassLovPage" >
				<div class="ic-row">
							&nbsp;
							</div>
						</logic:notPresent>
				</div>
				<div class="ic-row">
				<!--Modified by A-7938 for ICRD-243745-->
					<div class="tableContainer" id="div1" style="height:240px;">
						<table class="fixed-header-table">
							<thead>
								<tr>

									<td width="10%"> &nbsp;</td>
									<td width="45%" class="iCargoLabelleftAligned"><common:message  key="mailtracking.defaults.subclaslov.lbl.code" scope="request"/></td>
									<td width="45%" class="iCargoLabelLeftAligned"><common:message  key="mailtracking.defaults.subclaslov.lbl.description" scope="request"/></td>
								</tr>
							</thead>
							<logic:present name="form" property="subClassLovPage">
								<bean:define id="lovList" name="form" property="subClassLovPage" toScope="page"/>
								<logic:present name="lovList">
								<tbody>
									<% int i=0;%>
										<logic:iterate id = "val" name="lovList" indexId="indexId">
											
												
													<logic:notEqual name="form" property="multiselect" value="Y">
														<tr ondblclick="setValueOnDoubleClick('<%=((MailSubClassVO)val).getCode()%>','<%=((MailSubClassVO)val).getDescription()%>',
														'<%= strLovTxtFieldName%>','<%=strLovDescriptionTxtFieldName %>',<%=arrayIndex%>)">
													</logic:notEqual>
													<logic:equal name="form" property="multiselect" value="Y">
													<tr>
														<td>
															
															<%

															if(((String)strSelectedValues).contains(((MailSubClassVO)val).getCode())){ %>
																<input type="checkbox" name="selectCheckBox" value="<%=((MailSubClassVO)val).getCode()%>"  checked="checked"/>
															<%}else{ %>
																<input type="checkbox" name="selectCheckBox" value="<%=((MailSubClassVO)val).getCode()%>"  />
															<% } %>
														</td>
													</logic:equal>

													<logic:notEqual name="form" property="multiselect" value="Y">
															<td>
																<%String checkVal = ((MailSubClassVO)val).getCode();%>

																
															<%

															if(   ((String)strSelectedValues).equals(((MailSubClassVO)val).getCode()  )){ %>
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
														<bean:write name="val" property="description"/>
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
								<div id="selectiondiv" class="multiselectPanelStyle" name="MailSubClass">
								</div>
							</td>
						</tr>
					</logic:equal>
				</div>
			</div>
			<div class="ic-foot-container">
				<div class="ic-button-container">
					<div class="ic-row">
						<input type="button" name="btnOk" value="OK" title="Ok" class="iCargoButtonSmall" onclick="setValueForDifferentModes('<%=strMultiselect%>','<%=strFormCount%>','<%=strLovTxtFieldName%>','<%=strLovDescriptionTxtFieldName %>',<%=arrayIndex%>)" />
						<ihtml:nbutton property="btnClose" accesskey="O" componentID="BUT_MAILTRACKING_DEFAULTS_SUBCLASLOV_CLOSE">
						<common:message  key="mailtracking.defaults.subclaslov.btn.btcancel" />
						</ihtml:nbutton>
					</div>
				</div>
			</div>
		</div>	

	</ihtml:form>
	</div>
	
	</body>
</html:html>


