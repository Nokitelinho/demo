<!--************************************************************
* Project	 			: iCargo
* Module Code & Name	: mail-operations
* File Name				: ForceMajeureRequestidLov.jsp
* Date					: 05/12/2018
* Author(s)				: A-8527
 ****************************************************************-->
<%@ include file="/jsp/includes/ux/tlds.jsp"%>
<%@ page import="java.util.*"%>
<%@ page import = "com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.ForceMajeureRequestForm"%>
<%@ page info="lite"%>
<%@ page import="com.ibsplc.icargo.business.mail.operations.vo.ForceMajeureRequestVO"%>



<html:html>

<head>
			
	

	 

<title>Request ID LOV</title>

	<meta name="decorator" content="popuppanelux">
	<common:include type="script" src="/js/mail/operations/ux/ForceMajeureRequestIdLov_Script.jsp"/>

</head>
<body>
	
	 



	 
		<business:sessionBean id="totalRecordsSession" 
         moduleName="mail.operations"
 		 screenID="mail.operations.ux.forcemajeure" 
		 method="get" 
		 attribute="totalRecords" />
		 
		 <business:sessionBean id="filterparamSession" 
         moduleName="mail.operations"
 		 screenID="mail.operations.ux.forcemajeure" 
		 method="get" 
		 attribute="filterParamValues" />
		 
		 <business:sessionBean id="ForceMajeureRequestVOs"
		 moduleName="mail.operations"
		 screenID="mail.operations.ux.forcemajeure"
		 method="get"
		 attribute="listForceMajeureLovVos"/>		

		<%
		ForceMajeureRequestForm frm = (ForceMajeureRequestForm) (request
					.getAttribute("ForceMajeureRequestForm"));
			String isNonEditable = "true";
		%>
	
	<bean:define id="form" name="ForceMajeureRequestForm" type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.ForceMajeureRequestForm" toScope="page" />
	
	
		 <ihtml:form action="/mail.operations.ux.forcemajeure.reqforcelov.do" styleClass="ic-main-form">
		
		<ihtml:hidden name="form" property="selectedValues"  />
		<ihtml:hidden name="form" property="multiselect" />
		<ihtml:hidden name="form" property="formCount" />
		<ihtml:hidden name="form" property="lovTxtFieldName" />
		<ihtml:hidden name="form" property="lovDescriptionTxtFieldName" />
		<ihtml:hidden name="form" property="index" />
		<ihtml:hidden name="form" property="lastPageNum" />
		<ihtml:hidden name="form" property="displayPage" />
		<ihtml:hidden name="form" property="actionFlag" />
		
		<bean:define id="strFormCount" name="form" property="formCount"  />
		<bean:define id="strLovTxtFieldName" name="form" property="lovTxtFieldName"  />
		<bean:define id="strLovDescriptionTxtFieldName" name="form" property="lovDescriptionTxtFieldName" />
		<bean:define id="strSelectedValues" name="form" property="selectedValues" />
		<bean:define id="arrayIndex" name="form" property="index"/>	
	<div title="Request ID LoV" class="lov-popup-content" id="select_requestedId" >
		<div class="lov-filter lov-filter-oneline">
            <div class="row">
                <div class="col-7"> <!-- Size changed by A-8164 for ICRD-316302 -->
                    <div class="form-group">
                        <label class="form-control-label"><common:message key="mail.operations.ux.ForceMajeure.lbl.from" scope="request" /></label>
                        <div class="input-group">
                              <ibusiness:litecalendar tabindex="5" id="frmDate" 
									labelStyleClass="form-control-label" componentID="CMP_Mail_Operations_ForceMajeure_FromDate"
									property="frmDate" />
                            </div>
                        </div>
                </div>
                <div class="col-7">
                    <div class="form-group">
                        <label class="form-control-label"><common:message key="mail.operations.ux.ForceMajeure.lbl.to" scope="request" /></label>
                        <div class="input-group">
                            <ibusiness:litecalendar tabindex="5" id="toDate"  labelStyleClass="form-control-label" componentID="CMP_Mail_Operations_ForceMajeure_Todate" property="toDate" />
                            </div>
                        </div>
                </div>
                <div class="col-8">
                    <div class="form-group">
                        <label class="form-control-label"><common:message key="mail.operations.ux.ForceMajeure.lbl.reqid" scope="request" /></label>
                    <div class="input-group">
						<ihtml:text tabindex="5"  property="forceid"  id="forceid"  maxlength="25"  componentID="CMP_Mail_Operations_ForceMajeure_Forceid" />
					 </div>
                    </div>
                </div>
			</div>
			<div class="btn-row">
                        <ihtml:nbutton styleClass="btn btn-primary" id="btnListlov" property="btnListlov" componentID="CMP_Mail_Operations_ForceMajeure_LOV_LIST" >
						    <common:message   key="mail.operations.ux.forcemajeure.btn.list" />
						</ihtml:nbutton>
						<ihtml:nbutton  styleClass="btn btn-default" id="btnClearlov" property="btnClearlov" componentID="CMP_Mail_Operations_ForceMajeure_LOV_CLEAR">
						    <common:message   key="mail.operations.ux.forcemajeure.btn.clear" />
							</ihtml:nbutton>
                    </div>
                </div>
        <div class="lov-list">
            <div class="card">
                <div class="card-header card-header-action">
                    <div class="mega-pagination">
                      <div class="ic-col-35">
					  <logic:present name="ForceMajeureRequestVOs">
								<common:enhancedPaginationTag 
										name="ForceMajeureRequestVOs"
										id="ForceMajeureRequestVOs"
										lastPageNum="<%=frm.getLastPageNum() %>"
										styleClass="ic-pagination-lg"
										renderLengthMenu="true" lengthMenuName="defaultPageSize"
										lengthMenuHandler="showEntriesReloading"
										defaultSelectOption="<%=frm.getDefaultPageSize()%>" 
										pageNumberFormAttribute="displayPage"
										pageURL='javascript:submitPage("lastPageNum","displayPage")'	/>
										</logic:present>
							</div>
                    </div>
                </div>
                <div class="card-body p-0" style="max-height:250px; overflow-y:auto">
                    <table class="table table-x-md mb-0" id="lovForceTable">
                        <thead>
                            <tr>
                                <th width="35"></th>
                                <th><common:message   key="mail.operations.ux.ForceMajeure.lbl.reqid" scope="request"/></th>
                                <th style="width:15%"><common:message key="mail.operations.ux.ForceMajeure.lbl.reqDate" scope="request"/></th>
                                <th><common:message   key="mail.operations.ux.ForceMajeure.lbl.filterparam" scope="request"/></th>
                                <th><common:message   key="mail.operations.ux.ForceMajeure.lbl.remarks" scope="request"/></th>
                            </tr>
                        </thead>
                        <tbody>
						<logic:present name="ForceMajeureRequestVOs">
						<logic:iterate id = "ForceMajeureRequestVO" name="ForceMajeureRequestVOs" indexId="index" type="com.ibsplc.icargo.business.mail.operations.vo.ForceMajeureRequestVO">
                            <tr>
                                <td width="3%">
								<%String checkVal = ((ForceMajeureRequestVO)ForceMajeureRequestVO).getForceMajuereID();%>
								<% if(   ((String)strSelectedValues).equals(((ForceMajeureRequestVO)ForceMajeureRequestVO).getForceMajuereID()  )){ %>
                                             <input type="checkbox" name="selectCheckBox" value="<%=checkVal%>"  onclick="lovUtils.singleSelect({checkBoxVal:'<%=checkVal%>'});" checked="checked" />
                                             <%}else{ %>
                                             <input type="checkbox" name="selectCheckBox" value="<%=checkVal%>"  onclick="lovUtils.singleSelect({checkBoxVal:'<%=checkVal%>'});"/>
                                             <% } %>
								<!--<input type="checkbox" name="selectCheckBox" id="selectCheckBox" value="" tabindex="1"/>-->
							</td>
                                <td><bean:write name="ForceMajeureRequestVO" property="forceMajuereID"/></td>
                                <td><logic:present name="ForceMajeureRequestVO" property="requestDate">
							<bean:define id="reqDate" name="ForceMajeureRequestVO" property="requestDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
							<%String requestDat=TimeConvertor.toStringFormat(reqDate.toCalendar(),"dd-MM-yy");%>
                             <%=requestDat%>
							  </logic:present></td>
                                <td><bean:write name="ForceMajeureRequestVO" property="filterParameters"/></td>
                                <td><bean:write name="ForceMajeureRequestVO" property="requestRemarks"/></td>
                            </tr>
                        </logic:iterate> 
						</logic:present>						
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="btn-row">
            <ihtml:nbutton property="btnOk" id="btnOk"  accesskey="K" styleClass="btn btn-primary" tabindex="5" componentID="CMP_Mail_Operations_ForceMajeure_LOV_OK"> <common:message  key="mail.operations.ux.forcemajeure.btn.Ok"/> </ihtml:nbutton> 
			<ihtml:nbutton property="btnClose" id="btnClose"  accesskey="O" styleClass="btn btn-primary" tabindex="5" componentID="CMP_Mail_Operations_ForceMajeure_LOV_close" > <common:message  key="mail.operations.ux.forcemajeure.btn.close"/> </ihtml:nbutton> 
        </div>
		</div>

			
	</ihtml:form>
			
	
	
		

	

		

	</body>
</html:html>