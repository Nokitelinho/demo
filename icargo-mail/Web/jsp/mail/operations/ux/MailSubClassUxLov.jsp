<%--
/***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  Mail
* File Name				:  OfficeOfExchangeUxLov.jsp
* Date					:  04-Jul-2018
* Author(s)				:  A-8164
*************************************************************************/
--%>
<%
	response.setDateHeader("Expires",0);
	response.setHeader("Pragma","no-cache");
	if (request.getProtocol().equals("HTTP/1.1")) {
		response.setHeader("Cache-Control","no-cache");
	}
%>
<%@ include file="/jsp/includes/ux/tlds.jsp" %>
<%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.MailSubClassVO"%>
<%@ page info="lite" %>
<%@ page import="java.util.Collection" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.MailSubClassUxLovForm" %>
<html:html>
<head> 

		<title><common:message  bundle="mailsubclassUxLovResources"  key="mailtracking.defaults.subclaslov.lbl.title" scope="request"/></title>
		<meta name="decorator" content="popuppanelux">
		<common:include type="script" src="/js/mail/operations/ux/MailSubClassUxLov_Script.jsp"/>
</head>
<body id="bodyStyle">
	<logic:present name="MailSubClassUxLovForm">
	<bean:define id="form"
		name="MailSubClassUxLovForm"
		toScope="page"
		type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.MailSubClassUxLovForm"/>
</logic:present>
	<bean:define id="strAction" name="form"  property="lovaction" scope="page" toScope="page"/>
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
		<ihtml:hidden name="form" property="screenMode" />
		<!--  END -->
	<div class="poppane md" id="select_gpa">
		 <div class="pad-md pad-b-xs">
            <div class="row">
                <div class="col-4">
                    <div class="form-group">
					  <label class="form-control-label"><common:message  key="mailtracking.defaults.subclaslov.lbl.code" scope="request"/></label>
                        <ihtml:text styleClass="form-control" property="code" name="form" componentID="TXT_MAILTRACKING_DEFAULTS_UX_SUBCLASLOV_CODE"  maxlength="20"/>
                    </div>
                </div>
                <div class="col-7">
                    <div class="form-group">
					  <label class="form-control-label"><common:message  key="mailtracking.defaults.subclaslov.lbl.description" scope="request"/></label>
                        <ihtml:text styleClass="form-control" property="description" name="form" componentID="TXT_MAILTRACKING_DEFAULTS_UX_SUBCLASLOV_NAME"  maxlength="100"/>
                    </div>
                </div>
                <div class="col text-right mar-t-lg">
                    <ihtml:nbutton styleClass="btn btn-primary" property="btnList" accesskey="L" componentID="BUT_MAILTRACKING_DEFAULTS_UX_SUBCLASLOV_LIST">
										<common:message  key="mailtracking.defaults.subclaslov.btn.btlist" />
									</ihtml:nbutton>
										<ihtml:nbutton styleClass="btn btn-default" property="btnClear" accesskey="C" componentID="BUT_MAILTRACKING_DEFAULTS_UX_SUBCLASLOV_CLEAR">
										<common:message  key="mailtracking.defaults.subclaslov.btn.btclear" />
									</ihtml:nbutton>
				</div>
			</div>
        </div>
        <div class="card">
            <div class="card-header card-header-action pad-y-xs">
                <div class="mega-pagination">
					  <logic:present name="form" property="subClassLovPage" >
						<bean:define id="lovList" name="form" property="subClassLovPage" toScope="page"/>
						<logic:present name="lovList">
							<bean:define id="multiselect" name="form" property="multiselect" />
								<logic:equal name="form" property="pagination" value="Y">
								<bean:define id="lastPageNum" name="form" property="lastPageNum" />
								<bean:define id="defaultPageSize" name="form" property="defaultPageSize" />
								<bean:define id="displayPage" name="form" property="displayPage" />
									<common:enhancedPaginationTag 
										name="lovList"
										id="lovList"
										lastPageNum="<%=(String)lastPageNum %>"
										styleClass="ic-pagination-lg"
										renderLengthMenu="true" lengthMenuName="defaultPageSize"
										lengthMenuHandler="showEntriesReloading"
										defaultSelectOption="<%=(String) defaultPageSize %>" 
										pageNumberFormAttribute="displayPage"
										pageURL='javascript:preserveSelectedvalues(lastPageNum,displayPage)'	/>
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
            </div>
            <div class="card-body p-0" id="subClass" style="max-height:250px; overflow-y:auto">
				<div id="dataTableContainer" class="dataTableContainer tablePanel" style="width:100%">
                <table id="subClassTable" class="table table-x-md m-0" style="width:100%" >
					<thead>
                        <tr>
						<th width="35"></th>   <!-- Modified by A-8893 for IASCB-25484	-->
                            <th><common:message  key="mailtracking.defaults.subclaslov.lbl.code" scope="request"/></th>
                            <th><common:message  key="mailtracking.defaults.subclaslov.lbl.description" scope="request"/></th>
					</tr>
					</thead>
					<tbody id="subClassTableBody">
					<logic:present name="form" property="subClassLovPage">
								<bean:define id="lovList" name="form" property="subClassLovPage" toScope="page"/>
							<logic:present name="lovList">
                       <% int i=0;%>
											<logic:iterate id = "val" name="lovList" indexId="indexId">
													<logic:notEqual name="form" property="multiselect" value="Y">
													<tr  ondblclick="setValueOnDoubleClick('<%=strFormCount%>','<%=((MailSubClassVO)val).getCode()%>',
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
																<input type="checkbox" name="selectCheckBox" value="<%=checkVal%>"  onclick="singleSelectRow('<%=checkVal%>');"  checked="checked" />
															<%}else{ %>
																<input type="checkbox" name="selectCheckBox" value="<%=checkVal%>"  onclick="singleSelectRow('<%=checkVal%>');" />
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
					</logic:present>
							</logic:present>
					</tbody>
					</table>
					</div>
				</div>
							</div>
		<footer class="mar-t-2sm">
            <div class="btn-row col">		
							<input type="button" id="btnOk" name="btnOk" value="OK" title="Ok" class="btn btn-primary" 
								/>
								<input type="button" id="btnClose" name="btnClose" value="Close" title="Close" class="btn btn-default" 
								/>
			</div>
		</footer>
	</div>
	</ihtml:form>
	</body>
</html:html>