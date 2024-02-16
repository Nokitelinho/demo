<%--************************************************************
* Project	 			: iCargo
* Module Code & Name	: mra-gpareporting
* File Name				: InvoiceLOV.jsp
* Date					: 21/05/2018
* Author(s)				: A-7531
 ****************************************************************--%>
<%
	response.setDateHeader("Expires",0);
	response.setHeader("Pragma","no-cache");

	if (request.getProtocol().equals("HTTP/1.1")) {
		response.setHeader("Cache-Control","no-cache");
	}
%>
<%@ page language="java" %>
<%@ page import="com.ibsplc.icargo.business.mail.mra.defaults.vo.CCAdetailsVO"%>
<%@ include file="/jsp/includes/ux/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.ux.DespatchLOVForm" %>

<%@ page info="lite" %>			
				
	
		
					
	
<html:html>
<head> 
	
			
	
<common:include type="script" src="/js/mail/mra/gpareporting/ux/DespatchLOV_Script.jsp"/>
  <title><common:message   bundle="despatchlov" scope="request"/></title> 
	<meta name="decorator" content="popuppanelux"> 

	
</head>


<body id="bodyStyle">

<logic:present name="popup">
			<div id="walkthroughholder"/>
		</logic:present>
	

<bean:define id="DespatchLOVForm" name="DespatchLOVForm" type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.ux.DespatchLOVForm" toScope="page" />
 <bean:define id="strAction" name="DespatchLOVForm"  property="lovaction"  scope="page" toScope="page"/>
 
  <ihtml:form action="<%=(String)strAction%>" styleClass="ic-main-form" >
  
  



<!-- <logic:present name="despatchLOVForm"  property="lovaction" >
<bean:define id="lovaction" name="despatchLOVForm" property="lovaction" toScope="page"/>
</logic:present> -->
<ihtml:hidden name="DespatchLOVForm" property="lovaction"  />
<ihtml:hidden name="DespatchLOVForm" property="selectedValues"  />
<ihtml:hidden name="DespatchLOVForm" property="lastPageNum" />
<ihtml:hidden name="DespatchLOVForm" property="displayPage" />
<ihtml:hidden name="DespatchLOVForm" property="multiselect" />
<ihtml:hidden name="DespatchLOVForm" property="pagination" />
<ihtml:hidden name="DespatchLOVForm" property="formCount" />
<ihtml:hidden name="DespatchLOVForm" property="lovTxtFieldName" />
<ihtml:hidden name="DespatchLOVForm" property="lovNameTxtFieldName" /> 
<ihtml:hidden name="DespatchLOVForm" property="lovDescriptionTxtFieldName" />
<ihtml:hidden name="DespatchLOVForm" property="index" />
	<ihtml:hidden name="DespatchLOVForm" property="lovActionType" />



<!--<div tabindex="-1" role="dialog" class="ui-dialog ui-corner-all ui-widget ui-widget-content ui-front ui-draggable ui-resizable" aria-describedby="select_despatch" aria-labelledby="ui-id-5" style="position: absolute; height: auto; width: 720px; display: block; top: 52px; left: 307.5px; z-index: 100;">
    <div class="ui-dialog-titlebar ui-corner-all ui-widget-header ui-helper-clearfix ui-draggable-handle"><span id="ui-id-5" class="ui-dialog-title">Despatch LoV</span>
        <button type="button" class="ui-button ui-corner-all ui-widget ui-button-icon-only ui-dialog-titlebar-close" title="Close"><span class="ui-button-icon ui-icon ui-icon-closethick"></span><span class="ui-button-icon-space"> </span>Close</button>
    </div>
  -->  
  <!--<div class="lov-popup-content ui-dialog-content ui-widget-content" id="select_despatch" style="width: 720px; min-height: 107px; max-height: none; height: auto;">-->
	


	<div title="Despatch" class="poppane md" id="select_despatch">
	     
		 
		 <div class="lov-filter">
            <div class="row">
                <div class="col-5">
                    <div class="form-group">
                        <label class="form-control-label"><common:message  key="mailtracking.mra.defaults.dsnlov.lbl.originOE" scope="request"/></label>
                        <ihtml:text styleClass="form-control" property="origin" name="DespatchLOVForm" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_ORIGIN"  maxlength="6"/>
                    </div>
				</div>	
			    <div class="col-5">
							<div class="form-group">
								<label class="form-control-label"><common:message  key="mailtracking.mra.defaults.dsnlov.lbl.destOE" scope="request"/></label>
								<ihtml:text styleClass="form-control" property="destination" name="DespatchLOVForm" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_DEST"  maxlength="6"/>
							</div>
				</div>
				<div class="col-5">
                    <div class="form-group">
                        <label class="form-control-label"><common:message  key="mailtracking.mra.defaults.dsnlov.lbl.category" scope="request"/></label>
                        <ihtml:text styleClass="form-control" property="category" name="DespatchLOVForm" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_CATEGORY"  maxlength="1"/>
                    </div>
                </div>
                <div class="col-5">
                    <div class="form-group">
                        <label class="form-control-label"><common:message  key="mailtracking.mra.defaults.dsnlov.lbl.subclass" scope="request"/></label>
                        <ihtml:text styleClass="form-control" property="subclass" name="DespatchLOVForm" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_SUBCLASS"  maxlength="2"/>
                    </div>
                </div>
                <div class="col-4">
                    <div class="form-group">
                        <label class="form-control-label"><common:message  key="mailtracking.mra.defaults.dsnlov.lbl.year" scope="request"/></label>
                        <ihtml:text styleClass="form-control" property="year" name="DespatchLOVForm" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_ISSUEDATE"  maxlength="1"/>
                    </div>
                </div>
                <div class="col-5">
                    <div class="form-group">
                        <label class="form-control-label"><common:message  key="mailtracking.mra.defaults.dsnlov.lbl.dsn" scope="request"/></label>
                        <ihtml:text styleClass="form-control" property="dsnNumber" name="DespatchLOVForm" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_DSN"  maxlength="4"/>
                    </div>
                </div>
                <div class="col-5">
                    <div class="form-group">
                        <label class="form-control-label"><common:message  key="mailtracking.mra.defaults.dsnlov.lbl.rsn" scope="request"/></label>
                        <ihtml:text styleClass="form-control" property="recepatableSerialNumber" name="DespatchLOVForm" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_RSN"  maxlength="3"/>
                    </div>
                </div>
                <div class="col-5">
                    <div class="form-group">
                        <label class="form-control-label"><common:message  key="mailtracking.mra.defaults.dsnlov.lbl.hni" scope="request"/></label>
                        <ihtml:text styleClass="form-control" property="highestNumberIndicator" name="DespatchLOVForm" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_HNI"  maxlength="1"/>
                    </div>
                </div>
                <div class="col-5">
                    <div class="form-group">
                        <label class="form-control-label"><common:message  key="mailtracking.mra.defaults.dsnlov.lbl.ri" scope="request"/></label>
                        <ihtml:text styleClass="form-control" property="registeredIndicator" name="DespatchLOVForm" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_RI"  maxlength="1"/>
                    </div>
                </div>
                <div class="col-4">
                    <div class="form-group">
                        <label class="form-control-label"><common:message  key="mailtracking.mra.defaults.dsnlov.lbl.condocno" scope="request"/></label>
                        <ihtml:text styleClass="form-control"  property="condocno" name="DespatchLOVForm" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_CONDOCNO"  maxlength="13"/>
                    </div>
                </div>
                <div class="col-5">
                    <div class="form-group">
                        <label class="form-control-label"><common:message  key="mailtracking.mra.defaults.dsnlov.tooltip.fromdate" scope="request"/>*</label>
     
                                 <ibusiness:litecalendar
											labelStyleClass="form-control-label" name="DespatchLOVForm" styleClass="form-control hasDatepicker" 
											indexId="templateRowCount" id="fromDate" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_FROMDATE" 
											property="fromDate"  />  

                    </div>
                </div>
                <div class="col-5">
                    <div class="form-group">
                        <label class="form-control-label"><common:message  key="mailtracking.mra.defaults.dsnlov.tooltip.todate" scope="request"/>*</label>
 
                                      <ibusiness:litecalendar
											labelStyleClass="form-control-label" name="DespatchLOVForm" styleClass="form-control hasDatepicker" 
											indexId="templateRowCount" id="toDate" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_TODATE" 
											property="toDate"  /> 
                    
                    </div>
                </div>
                <div class="col">
                    <div class="mar-t-md">
                        <ihtml:nbutton styleClass="btn btn-primary" property="btnList" id="btnList" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_LSTBTN"><common:message  key="mailtracking.mra.defaults.maintaincca.lbl.button.list" /></ihtml:nbutton>
                        <ihtml:nbutton styleClass="btn btn-default" property="btnClear" id="btnClear" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_CLEARBTN"><common:message  key="mailtracking.mra.defaults.maintaincca.lbl.button.clear" /></ihtml:nbutton>
                    </div>
                </div>
            </div> <!-- row ends-->
        </div> <!--Filter ends-->
		  <div class="lov-list">
            <div class="card">
                <div class="card-header card-header-action"><!--pagination begins-->	
                    <div class="mega-pagination">
						
<logic:present name="DespatchLOVForm" property="dsnLovPage">
    <bean:define id="dsnLovPage" name="DespatchLOVForm" property="dsnLovPage" toScope="page" />
    <logic:present name="dsnLovPage">
        <logic:present name="DespatchLOVForm" property="multiselect">
            <bean:define id="multiselect" name="DespatchLOVForm" property="multiselect" />
        </logic:present>
        <logic:equal name="DespatchLOVForm" property="pagination" value="Y">
            <bean:define id="lastPageNum" name="DespatchLOVForm" property="lastPageNum" toScope="page" />

            <common:enhancedPaginationTag pageURL='javascript:submitPage("lastPageNum","displayPage")' 
			name="dsnLovPage" id="dsnLovPage" 
			lastPageNum="<%=DespatchLOVForm.getLastPageNum() %>" 
			renderLengthMenu="true" lengthMenuName="defaultPageSize" 
			defaultSelectOption="<%=DespatchLOVForm.getDefaultPageSize() %>" 
			lengthMenuHandler="showEntriesReloading" 
			pageNumberFormAttribute="displayPage" />
			
        </logic:equal>
    </logic:present>
</logic:present>
						
                    </div>
                </div><!--pagination ends-->
						<bean:define id="strFormCount" name="DespatchLOVForm" property="formCount"  />
						<bean:define id="strMultiselect" name="DespatchLOVForm" property="multiselect" />
						<bean:define id="strLovTxtFieldName" name="DespatchLOVForm" property="lovTxtFieldName"  />
						<bean:define id="strLovDescriptionTxtFieldName" name="DespatchLOVForm" property="lovDescriptionTxtFieldName" />
						<bean:define id="strSelectedValues" name="DespatchLOVForm" property="selectedValues" />
						<bean:define id="arrayIndex" name="DespatchLOVForm" property="index"/>	
               <div class="card-body p-0" style="height:225px; overflow-y:auto">
			
                    <table class="table table-x-md mb-0">
                        <thead>
                            <tr>
                                <th width="35"></th>
                                <th><common:message  key="mailtracking.mra.defaults.dsnlov.lbl.originOE" scope="request"/></th>
                                <th><common:message  key="mailtracking.mra.defaults.dsnlov.lbl.destOE" scope="request"/></th>
                                <th><common:message  key="mailtracking.mra.defaults.dsnlov.lbl.category" scope="request"/></th>
                                <th><common:message  key="mailtracking.mra.defaults.dsnlov.lbl.subclass" scope="request"/></th>
                                <th><common:message  key="mailtracking.mra.defaults.dsnlov.lbl.year" scope="request"/></th>
                                <th><common:message  key="mailtracking.mra.defaults.dsnlov.lbl.dsn" scope="request"/></th>
                                <th><common:message  key="mailtracking.mra.defaults.dsnlov.lbl.rsn" scope="request"/></th>
                                <th><common:message  key="mailtracking.mra.defaults.dsnlov.lbl.hni" scope="request"/></th>
                                <th><common:message  key="mailtracking.mra.defaults.dsnlov.lbl.ri" scope="request"/></th>
                                <th><common:message  key="mailtracking.mra.defaults.dsnlov.lbl.condocno" scope="request"/></th>
                            </tr>
                        </thead>
                        <tbody>
				
							<logic:present name="DespatchLOVForm" property="dsnLovPage">
							
												<bean:define id="dsnList" name="DespatchLOVForm" property="dsnLovPage" toScope="page"/>
								<logic:present name="dsnList">
												<% int i=0;%>
												<jsp:setProperty name="DespatchLOVForm" property="lovActionType" value="dsnList"/>
										<logic:iterate id = "val" name="dsnList" indexId="indexId">
										
											<tr>
											<%=val%>											
											<logic:notEqual name="DespatchLOVForm" property="multiselect" value="Y">
																
																<%String checkValue = ((CCAdetailsVO)val).getBillingBasis();%>
																
																		<tr ondblclick="setValueOnDoubleClick('<%=strFormCount%>','<%=checkValue%>',
																			'<%= strLovTxtFieldName%>','<%=strLovDescriptionTxtFieldName %>',<%=arrayIndex%>)">
											</logic:notEqual>
											<logic:equal name="DespatchLOVForm" property="multiselect" value="Y">
						
						
                
																		<td width="3%">
																			<%
																			if(((String)strSelectedValues).contains(((CCAdetailsVO)val).getBillingBasis())){ %>
																				<input type="checkbox" name="selectCheckBox" value="<%=((CCAdetailsVO)val).getBillingBasis()%>"  checked="checked"/>
																			<%}else{ %>
																				<input type="checkbox" name="selectCheckBox" value="<%=((CCAdetailsVO)val).getBillingBasis()%>"  />
																			<% } %>
																		</td>
								</logic:equal>
								<logic:notEqual name="DespatchLOVForm" property="multiselect" value="Y">
																			<td width="3%">
																				<%String checkVal = ((CCAdetailsVO)val).getBillingBasis();%>

																				
																			<%

																			if(   ((String)strSelectedValues).equals(((CCAdetailsVO)val).getDsnNo()  )){ %>
																				<input type="checkbox" name="selectCheckBox" value="<%=checkVal%>"  onclick="lovUtils.singleSelect({checkBoxVal:'<%=checkVal%>'});" checked="checked" />
																			<%}else{ %>
																				<input type="checkbox" name="selectCheckBox" value="<%=checkVal%>"  onclick="lovUtils.singleSelect({checkBoxVal:'<%=checkVal%>'});"/>
																			<% } %>


																			</td>
								</logic:notEqual>
																	
																	
																	<td width="10%" align="left">
																	<bean:write name="val" property="origin"/>
																	</td>
																	<td width="10%" align="left">
																	<bean:write name="val" property="destination"/>
																	</td>
																	<td width="10%" align="left">
																	<bean:write name="val" property="category"/>
																	</td>
																	<td width="10%" align="left">
																	<bean:write name="val" property="subClass"/>
																	</td>
																	<td width="10%" align="left">
																	<bean:write name="val" property="year"/>
																	</td>
																	<td width="10%" align="left">
																	<bean:write name="val" property="dsnNo"/>
																	</td>
																	<td width="10%" align="left">
																	<bean:write name="val" property="rsn"/>
																	</td>
																	<td width="10%" align="left">
																	<bean:write name="val" property="hni"/>
																	</td>
																	<td width="10%" align="left">
																	<bean:write name="val" property="regind"/>
																	</td>
																	<td width="10%" align="left">
																	<bean:write name="val" property="csgDocumentNumber"/>
																	</td>
																	
												</tr>
										</logic:iterate>
													
									</logic:present>
								</logic:present>						
                        </tbody>
                    </table>
                </div>
            </div>
        </div><!--list ends-->
		
        <div class="lov-btn-row  border-top">
            <ihtml:nbutton styleClass="btn btn-primary" property="btnOk" id="btnOk" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_OK" onclick="setValueForDifferentModes('<%=strMultiselect%>','<%=strFormCount%>','<%=strLovTxtFieldName%>','<%=strLovDescriptionTxtFieldName%>',<%=arrayIndex%>)">Ok</ihtml:nbutton>
            <ihtml:nbutton styleClass="btn btn-default" id="btnClose" property="btnClose" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_CANCEL"><common:message key="mailtracking.mra.defaults.maintaincca.lbl.cancel" /></ihtml:nbutton>
        </div>
		
    </div> <!--title ends-->
		 	
		 
	     
	       
   <!--</div> -->
 <!--</div>-->
	</ihtml:form> 
	
					
		   <jsp:include page="/jsp/includes/popupFooterSection.jsp"/>

		   <common:registerEvent />

</body>


</html:html>