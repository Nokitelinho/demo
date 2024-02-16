<%--
/***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  Mailtracking
* File Name				:  MilaBoxIdLov.jsp
* Date					:  05-05-2016
* Author(s)				:  A-5931
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

<%@ page import="java.util.Collection" %>
<%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.MailBoxIdLovVO"%>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailboxIdLovForm" %>



<logic:present name="MailboxIdLovForm">
	<bean:define id="form"
		name="MailboxIdLovForm"
		toScope="page"
		type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailboxIdLovForm"/>
</logic:present>	
	
<html:html>
	<head> 
			
		
			
	
		<title><common:message  bundle="mailBoxIdLovResources"  key="mailtracking.defaults.mailboxidlov.lbl.title" scope="request"/></title>
		<meta name="decorator" content="popuppanelrestyledui">
		<common:include type="script" src="/js/mail/operations/MailBoxIdLov_Script.jsp"/>
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
					<common:message  key="mailtracking.defaults.mailboxidlov.lbl.heading" scope="request"/>
				</span>	
				<div class="ic-head-container">
					<div class="ic-filter-panel">
					<div class="ic-input-container">
							<div class="ic-row">
								<div class="ic-input ">
									<label>
										<common:message  key="mailtracking.defaults.mailboxidlov.lbl.code" scope="request"/>
									</label>
									<ihtml:text property="code" name="form" componentID="TXT_MAILTRACKING_DEFAULTS_MAILBOXIDLOV_CODE"  maxlength="20"/>
								</div>
								<div class="ic-input">
									<label><common:message  key="mailtracking.defaults.mailboxidlov.lbl.description" scope="request"/></label>
									<ihtml:text property="description" name="form" componentID="TXT_MAILTRACKING_DEFAULTS_MAILBOXIDLOV_NAME"  maxlength="100"/>
								</div>
								<div class="ic-button-container">
									<ihtml:nbutton property="btnList" componentID="BUT_MAILTRACKING_DEFAULTS_MAILBOXIDLOV_LIST">
										<common:message  key="mailtracking.defaults.mailboxidlov.btn.btlist" />
									</ihtml:nbutton>
										<ihtml:nbutton property="btnClear" componentID="BUT_MAILTRACKING_DEFAULTS_MAILBOXIDLOV_CLEAR">
										<common:message  key="mailtracking.defaults.mailboxidlov.btn.btclear" />
									</ihtml:nbutton>
								</div>
						</div>
					</div>
				</div>
			</div>
			<div class="ic-main-container">
				<div class="ic-row">
					<logic:present name="form" property="mailboxidLovPage" >
						<bean:define id="lovList" name="form" property="mailboxidLovPage" toScope="page"/>
						<logic:present name="lovList">
							<bean:define id="multiselect" name="form" property="multiselect" />

							<logic:equal name="form" property="pagination" value="Y">
								<!-- -PAGINATION TAGS -->
								<bean:define id="lastPageNum" name="form" property="lastPageNum" />
							<div class="ic-row">
								<div class="ic-col-60">
									<common:paginationTag pageURL="javascript:preserveSelectedvalues('lastPageNum','displayPage')" name="lovList"
										display="label" labelStyleClass="iCargoResultsLabel" lastPageNum="<%=(String)lastPageNum %>" />
												
								</div>
								<div class="ic-col-40">
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
					<div class="tableContainer"  id="div1" style="height:300px; width:100%;">
						<table  class="fixed-header-table">
							<thead>
								<tr class="iCargoTableHeadingCenter" >

									<td width="3%"> &nbsp;</td>

									<td width="26%" class="iCargoLabelleftAligned"><common:message  key="mailtracking.defaults.mailboxidlov.lbl.code" scope="request"/></td>
									<td width="30%" class="iCargoLabelLeftAligned"><common:message  key="mailtracking.defaults.mailboxidlov.lbl.description" scope="request"/></td>
								</tr>
							</thead>
								<tbody>
									<logic:present name="form" property="mailboxidLovPage">
									<bean:define id="lovList" name="form" property="mailboxidLovPage" toScope="page"/>
									<logic:present name="lovList">
									<% int i=0;%>
										<logic:iterate id = "val" name="lovList" indexId="indexId">
											<common:rowColorTag index="indexId">
												<tr bgcolor="<%=color%>">
													<logic:notEqual name="form" property="multiselect" value="Y">
														<a href="#" ondblclick="setValueOnDoubleClick('<%=((MailBoxIdLovVO)val).getMailboxCode()%>','<%=((MailBoxIdLovVO)val).getMailboxDescription()%>',
															'<%= strLovTxtFieldName%>','<%=strLovDescriptionTxtFieldName %>',<%=arrayIndex%>)"/>
													</logic:notEqual>
													<logic:equal name="form" property="multiselect" value="Y">
														<td width="3%">
															<%

															if(((String)strSelectedValues).contains(((MailBoxIdLovVO)val).getMailboxCode())){ %>
																<input type="checkbox" name="selectCheckBox" value="<%=((MailBoxIdLovVO)val).getMailboxCode()%>"  checked="checked"/>
															<%}else{ %>
																<input type="checkbox" name="selectCheckBox" value="<%=((MailBoxIdLovVO)val).getMailboxCode()%>"  />
															<% } %>
														</td>
													</logic:equal>

													<logic:notEqual name="form" property="multiselect" value="Y">
															<td width="3%">
																<%String checkVal = ((MailBoxIdLovVO)val).getMailboxCode()+"-"+((MailBoxIdLovVO)val).getMailboxDescription();%>

															<%

															if(   ((String)strSelectedValues).equals(((MailBoxIdLovVO)val).getMailboxCode()  )){ %>
																<input type="checkbox" name="selectCheckBox" value="<%=checkVal%>"  onclick="singleSelect('<%=checkVal%>');" checked="checked" />
															<%}else{ %>
																<input type="checkbox" name="selectCheckBox" value="<%=checkVal%>"  onclick="singleSelect('<%=checkVal%>');"/>
															<% } %>


															</td>
													</logic:notEqual>

													<td width="26%" align="left">
														<bean:write name="val" property="mailboxCode"/>
													</td>
													<td width="30%" align="left">
														<bean:write name="val" property="mailboxDescription"/>
													</td>
													<logic:notEqual name="form" property="multiselect" value="Y">
														</a>
													</logic:notEqual>
												</tr>
											</common:rowColorTag>
										</logic:iterate>
										</logic:present>
										</logic:present>
									</tbody>
									
							
						</table>
					</div>	
				</div>	
			</div>
			<div class="ic-foot-container">
				<div class="ic-button-container">
					<input type="button" name="btnOk" value="OK" class="iCargoButtonSmall" onclick="setValueForDifferentModes('<%=strMultiselect%>','<%=strFormCount%>','<%=strLovTxtFieldName%>','<%=strLovDescriptionTxtFieldName %>',<%=arrayIndex%>)" />
					<ihtml:nbutton property="btnClose" componentID="BUT_MAILTRACKING_DEFAULTS_MAILBOXIDLOV_CLOSE">
						<common:message  key="mailtracking.defaults.mailboxidlov.btn.btcancel" />
					</ihtml:nbutton>
				</div>
			</div>	
		</div>		
	</ihtml:form>	
</div>							
			
		 
				
	</body>
</html:html>


