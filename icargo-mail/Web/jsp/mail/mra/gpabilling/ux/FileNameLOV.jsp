<%
	response.setDateHeader("Expires",0);
	response.setHeader("Pragma","no-cache");
	if (request.getProtocol().equals("HTTP/1.1")) {
		response.setHeader("Cache-Control","no-cache");
	}
%>

<%@ include file="/jsp/includes/ux/tlds.jsp" %>
<%@ page info="lite" %>
<%@ page import="java.util.Collection" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ux.FileNameLovForm" %>
<%@ page import="com.ibsplc.icargo.business.mail.mra.gpabilling.vo.FileNameLovVO" %>

			
	

		
					
	
<html:html>
<head>
	 		

		<title><common:message bundle="filenamelovResources" key ="mail.mra.gpabilling.ux.lbl.filename" scope="request"/></title>
		<meta name="decorator" content="popuppanelux">
		<common:include type="script" src="/js/mail/mra/gpabilling/ux/FileNameLOV_Script.jsp" />
</head>
<body id="bodyStyle">
		
		<logic:present name="FileNameLovForm">
		<bean:define id="form"
		name="FileNameLovForm"
		toScope="page"
		type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ux.FileNameLovForm"/>
		</logic:present>
		
		
		<ihtml:form action="/mail.mra.gpabilling.ux.filenamelov.list.do" styleClass="ic-main-form">
		<ihtml:hidden name="form" property="lovAction"  />
		<ihtml:hidden name="form" property="selectedValues"  />
		<ihtml:hidden name="form" property="lastPageNum" />
		<ihtml:hidden name="form" property="displayPage" />
		<ihtml:hidden name="form" property="multiselect" />
		<ihtml:hidden name="form" property="pagination" />
		<ihtml:hidden name="form" property="formCount" />
		<ihtml:hidden name="form" property="lovTxtFieldName" />
		<ihtml:hidden name="form" property="lovDescriptionTxtFieldName" />
		<ihtml:hidden name="form" property="index" />
		
		
		<div class="lov-popup-content" id="file-name">
			<div class="lov-filter lov-filter-oneline">
            <div class="mb-3">
                <div class="pad-b-3xs pl-1"><strong>Billing Period</strong></div>
                <div class="card px-3 pt-2 rounded">
                    <div class="row">
                <div class="col-8">
                    <div class="form-group">
                        <label class="form-control-label"><common:message key="mail.mra.gpabilling.ux.lbl.fromdate" scope="request"/><span class="mandatory">*</span></label>
                        <div class="input-group">
							<ibusiness:litecalendar
							labelStyleClass="form-control-label" styleClass="form-control hasDatepicker" id="fromDate" componentID="MAIL_MRA_GPABILLING_UX_FILENAME_FROMDATE" 
							property="fromDate"  />
						</div>
                    </div>
                </div>
                 <div class="col-8">
                    <div class="form-group">
                        <label class="form-control-label"><common:message key="mail.mra.gpabilling.ux.lbl.todate" scope="request"/><span class="mandatory">*</span></label>
                        <div class="input-group">
							<ibusiness:litecalendar
							labelStyleClass="form-control-label" styleClass="form-control hasDatepicker" id="toDate" componentID="MAIL_MRA_GPABILLING_UX_FILENAME_TODATE" 
							property="toDate"   />
						</div>
                    </div>
                </div>
            </div>
            </div>
            </div>
			<div class ="row">
				<div class="col-6">
                    <div class="form-group">
                        <label class="form-control-label"><common:message key="mail.mra.gpabilling.ux.lbl.periodnumber" scope="request"/></label>
						<ihtml:text styleClass="form-control" property="periodNumber" componentID="MAIL_MRA_GPABILLING_UX_FILENAME_PERIODNUMBER"/>
                    </div>
                </div>
				<div class="col text-right mar-t-lg">
                    <ihtml:nbutton styleClass="btn btn-primary" property="btnList" accesskey="L" componentID="MAIL_MRA_GPABILLING_UX_FILENAME_LIST">
						<common:message  key="mail.mra.gpabilling.ux.lbl.list" />
						</ihtml:nbutton>
						<ihtml:nbutton styleClass="btn btn-default" property="btnClear" accesskey="C" componentID="MAIL_MRA_GPABILLING_UX_FILENAME_CLEAR">
						<common:message  key="mail.mra.gpabilling.ux.lbl.clear" />
					</ihtml:nbutton>
				</div>
			</div>
        </div>

		
		<div class="lov-list">
        <div class="card">
            <div class="card-header card-header-action pad-y-xs">
                <div class="mega-pagination">
				<logic:present name="form" property="fileNameLovPage" >
						<bean:define id="lovList" name="form" property="fileNameLovPage" toScope="page"/>
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
								defaultSelectOption="<%=(String)defaultPageSize %>"
								pageNumberFormAttribute="displayPage"
								pageURL="javascript:preserveSelectedvalues(lastPageNum,displayPage)"/>
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

			<div class="card-body p-0" id="findName" style="max-height:200px; overflow-y:auto">
			<div id="dataTableContainer" class="dataTableContainer tablePanel" style="width:100%">
				<table id="fileNameTable" class="table table-x-md m-0" style="width:100%" >
					<thead>
                        <tr>
							<th class="check-box-cell"></th>
                            <th><common:message  key="mail.mra.gpabilling.ux.lbl.periodnumber" scope="request"/></th>
                            <th><common:message  key="mail.mra.gpabilling.ux.lbl.frombillingdate" scope="request"/></th>
							<th><common:message  key="mail.mra.gpabilling.ux.lbl.tobillingdate" scope="request"/></th>
							<th><common:message  key="mail.mra.gpabilling.ux.lbl.filename" scope="request"/></th>
					</tr>
					</thead>
					<logic:present name="form" property="fileNameLovPage">
							<bean:define id="fileNameLovVOs" name="form" property="fileNameLovPage" toScope="page"/>
						<tbody id="findNameTableBody">					
							<logic:present name="fileNameLovVOs">
								<logic:iterate id="fileNameLovVO" name="fileNameLovVOs" type="com.ibsplc.icargo.business.mail.mra.gpabilling.vo.FileNameLovVO" indexId="indexId">
												<logic:notEqual name="form" property="multiselect" value="Y">
															<tr  ondblclick="setValueOnDoubleClick('<%=((FileNameLovVO)fileNameLovVO).getFileName()%>','<%=strFormCount%>',
																	'<%= strLovTxtFieldName%>','<%=strLovDescriptionTxtFieldName %>',<%=arrayIndex%>)">
															</tr>
												</logic:notEqual>
													<logic:equal name="form" property="multiselect" value="Y">
													
														<td>
															<%
															if(((String)strSelectedValues).contains(((FileNameLovVO)fileNameLovVO).getFileName())){ %>
																<input type="checkbox" name="selectCheckBox" value="<%=((FileNameLovVO)fileNameLovVO).getFileName()%>"  checked="checked"/>
															<%}else{ %>
																<input type="checkbox" name="selectCheckBox" value="<%=((FileNameLovVO)fileNameLovVO).getFileName()%>"  />
															<% } %>
														</td>
													</logic:equal>
													<logic:notEqual name="form" property="multiselect" value="Y">
															<td>
																<%String checkVal = ((FileNameLovVO)fileNameLovVO).getFileName();%>
															<%
															if(   ((String)strSelectedValues).equals(((FileNameLovVO)fileNameLovVO).getFileName()  )){ %>
																<input type="checkbox" name="selectCheckBox" value="<%=checkVal%>"   checked="checked" onclick="singleSelectRow('<%=checkVal%>');" />	 <!-- Modified by A-8164 for ICRD 272988	-->
															<%}else{ %>
																<input type="checkbox" name="selectCheckBox" value="<%=checkVal%>" onclick="singleSelectRow('<%=checkVal%>');" />			<!-- Modified by A-8164 for ICRD 272988	-->
															<% } %>
															</td>
													</logic:notEqual>								
									<td><bean:write name="fileNameLovVO" property="periodNumber"/></td>
									<td><bean:write name="fileNameLovVO" property="fromDate"/></td>
									<td><bean:write name="fileNameLovVO" property="toDate"/></td>
									<td><bean:write name="fileNameLovVO" property="fileName"/></td>		
								</tr>
								</logic:iterate>
							</logic:present>
					</tbody>
					</logic:present>
					</table>
			</div>
			</div>
		</div>	
		</div>	
			<div class="lov-btn-row">		
				<input type="button" id="btnOk" name="btnOk" value="OK" title="Ok" class="btn btn-primary" onclick="setValue()" />
				<input type="button" id="btnClose" name="btnClose" value="Close" title="Close" class="btn btn-default" />
			</div>     
		</div>
		</ihtml:form>
		
	</body>

</html:html>		
