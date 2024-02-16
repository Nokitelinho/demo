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
<%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.OfficeOfExchangeVO"%>
<%@ page info="lite" %>
<%@ page import="java.util.Collection" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.OfficeOfExchangeUxLovForm" %>
<html>
<head> 

		<title><common:message  bundle="oeUxLovResources"  key="mailtracking.defaults.oelov.lbl.title" scope="request"/></title>
		<meta name="decorator" content="popuppanelux">
		<common:include type="script" src="/js/mail/operations/ux/OfficeOfExchangeUxLov_Script.jsp"/>
</head>
<body>
		<logic:present name="popup">
			<div id="walkthroughholder"/>
		</logic:present>
	<logic:present name="OfficeOfExchangeUxLovForm">
	<bean:define id="form"
		name="OfficeOfExchangeUxLovForm"
		toScope="page"
		type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.OfficeOfExchangeUxLovForm"/>
</logic:present>
	<bean:define id="strAction" name="form"  property="lovaction" scope="page" toScope="page"/>
		 <ihtml:form action="<%=(String)strAction%>" styleClass="ic-main-form" style="height:100%;">
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
	<div class="lov-popup-content ui-dialog-content ui-widget-content" id="select_gpa" style="width: 100%">
		 <div class="lov-filter lov-filter-oneline">
            <div class="row">
                <div class="col-4">
                    <div class="form-group">
					  <label class="form-control-label"><common:message  key="mailtracking.defaults.oelov.lbl.code" scope="request"/></label>
                        <ihtml:text styleClass="form-control" property="code" name="form" componentID="TXT_MAILTRACKING_DEFAULTS_UX_OELOV_CODE"  maxlength="20"/>
                    </div>
                </div>
                <div class="col-4">
                    <div class="form-group">
					  <label class="form-control-label"><common:message  key="mailtracking.defaults.oelov.lbl.description" scope="request"/></label>
                        <ihtml:text styleClass="form-control" property="description" name="form" componentID="TXT_MAILTRACKING_DEFAULTS_UX_OELOV_NAME"  maxlength="100"/>
                    </div>
                </div>
				<div class="col-5">
                    <div class="form-group">
					  <label class="form-control-label"><common:message  key="mailtracking.defaults.oelov.lbl.airportCode" scope="request"/></label>
							<div class="input-group">
                                <ihtml:text property="airportCode"  styleClass="form-control" componentID="TXT_MAILTRACKING_DEFAULTS_UX_OELOV_AIRPORT" maxlength="4"/>
                                  <div class="input-group-append">
                                  <button type="button" class="btn btn-icon" id='airportCode'><i class="icon ico-expand">
								  </i></button>
                                  </div>
                            </div>
                    </div>
                </div>
				<div class="col-5">
                    <div class=" form-group">
							<label class="form-control-label"><common:message  key="mailtracking.defaults.oelov.lbl.poaCode" /></label>
                                <div class="input-group">
									<ihtml:text property="poaCode"  styleClass="form-control" componentID="TXT_MAILTRACKING_DEFAULTS_UX_OELOV_POACOD" maxlength="6"/>
										<div class="input-group-append">
										   <button type="button" class="btn btn-icon" id='poaCode'><i class="icon ico-expand"></i></button>
										</div>
								</div>
                    </div>
                </div>
                <div class="col">
                    <div class="mar-t-md">
                    <ihtml:nbutton styleClass="btn btn-primary" property="btnList" accesskey="L" componentID="BUT_MAILTRACKING_DEFAULTS_UX_OELOV_LIST">
										<common:message  key="mailtracking.defaults.oelov.btn.btlist" />
									</ihtml:nbutton>
										<ihtml:nbutton styleClass="btn btn-default" property="btnClear" accesskey="C" componentID="BUT_MAILTRACKING_DEFAULTS_UX_OELOV_CLEAR">
										<common:message  key="mailtracking.defaults.oelov.btn.btclear" />
									</ihtml:nbutton>
				</div>
			</div>
        </div>
        </div>
		<div class="lov-list">
        <div class="card">
            <div class="card-header card-header-action ">
                <div class="mega-pagination">
					  <logic:present name="form" property="oeLovPage" >
						<bean:define id="lovList" name="form" property="oeLovPage" toScope="page"/>
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
            <div class="card-body " >
				<div id="dataTableContainer" class="dataTableContainer tablePanel" >
                <table id="oeTable" class="table table-x-md mb-0 w-100" >
					<thead>
                        <tr>
						<th width="35"></th>  <!-- Modified by A-8893 for IASCB-25484	-->
                            <th><common:message  key="mailtracking.defaults.oelov.lbl.code" scope="request"/></th>
                            <th><common:message  key="mailtracking.defaults.oelov.lbl.description" scope="request"/></th>
							<th><common:message  key="mailtracking.defaults.oelov.lbl.airportCode" scope="request"/></th>
                            <th><common:message  key="mailtracking.defaults.oelov.lbl.poaCode" scope="request"/></th>
					</tr>
					</thead>
					<tbody id="oeTableBody">
					<logic:present name="form" property="oeLovPage">
								<bean:define id="lovList" name="form" property="oeLovPage" toScope="page"/>
							<logic:present name="lovList">
                       <% int i=0;%>
											<logic:iterate id = "val" name="lovList" indexId="indexId">
													<logic:notEqual name="form" property="multiselect" value="Y">
													<tr  ondblclick="setValueOnDoubleClick('<%=strFormCount%>','<%=((OfficeOfExchangeVO)val).getCode()%>',
															'<%= strLovTxtFieldName%>','<%=strLovDescriptionTxtFieldName %>',<%=arrayIndex%>)">
													</logic:notEqual>
													<logic:equal name="form" property="multiselect" value="Y">
													<tr>
														<td>
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
															<%
															if(   ((String)strSelectedValues).equals(((OfficeOfExchangeVO)val).getCode()  )){ %>
																<input type="checkbox" name="selectCheckBox" value="<%=checkVal%>"   checked="checked" onclick="singleSelectRow('<%=checkVal%>');" />
															<%}else{ %>
																<input type="checkbox" name="selectCheckBox" value="<%=checkVal%>" onclick="singleSelectRow('<%=checkVal%>');" />
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
					</logic:present>
							</logic:present>
					</tbody>
					</table>
					</div>
				</div>
							</div>
							</div>
					
		<div class="lov-btn-row">		
							<input type="button" id="btnOk" name="btnOk" value="OK" title="Ok" class="btn btn-primary" 
								/>
								<input type="button" id="btnClose" name="btnClose" value="Close" title="Close" class="btn btn-default" 
								/>
			</div>
		
	</div>
	</ihtml:form>
	</body>
</html>