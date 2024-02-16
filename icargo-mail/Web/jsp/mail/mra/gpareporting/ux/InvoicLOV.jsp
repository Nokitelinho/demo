<%--************************************************************
* Project	 			: iCargo
* Module Code & Name	: mra-gpareporting
* File Name				: InvoicLOV.jsp
* Date					: 17/12/2018
* Author(s)				: A-8464
 ****************************************************************--%>
<%
	response.setDateHeader("Expires",0);
	response.setHeader("Pragma","no-cache");

	if (request.getProtocol().equals("HTTP/1.1")) {
		response.setHeader("Cache-Control","no-cache");
	}
%>
<%@ page language="java" %>
<%@ page import="com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicVO"%>
<%@ include file="/jsp/includes/ux/tlds.jsp" %>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.ux.InvoicLovForm" %>

<%@ page info="lite" %>



<html:html>
<head>




<common:include type="script" src="/js/mail/mra/gpareporting/ux/InvoicLOV_Script.jsp"/>
  <title><common:message   bundle="invoiclov" key="mail.mra.gpareporting.invoiclov.lbl.title" scope="request"/></title>
	<meta name="decorator" content="popuppanelux">


</head>


<body id="bodyStyle">
		<logic:present name="popup">
			<div id="walkthroughholder"/>
		</logic:present>


<bean:define id="InvoicLovForm" name="InvoicLovForm" type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.ux.InvoicLovForm" toScope="page" />
 <bean:define id="strAction" name="InvoicLovForm"  property="lovaction"  scope="page" toScope="page"/>

  <ihtml:form action="<%=(String)strAction%>" styleClass="ic-main-form" >

<ihtml:hidden name="InvoicLovForm" property="lovaction"  />
<ihtml:hidden name="InvoicLovForm" property="selectedValues"  />
<ihtml:hidden name="InvoicLovForm" property="lastPageNum" />
<ihtml:hidden name="InvoicLovForm" property="displayPage" />
<ihtml:hidden name="InvoicLovForm" property="multiselect" />
<ihtml:hidden name="InvoicLovForm" property="pagination" />
<ihtml:hidden name="InvoicLovForm" property="formCount" />
<ihtml:hidden name="InvoicLovForm" property="lovTxtFieldName" />
<ihtml:hidden name="InvoicLovForm" property="lovNameTxtFieldName" />
<ihtml:hidden name="InvoicLovForm" property="lovDescriptionTxtFieldName" />
<ihtml:hidden name="InvoicLovForm" property="index" />
	<ihtml:hidden name="InvoicLovForm" property="lovActionType" />



<!--<div tabindex="-1" role="dialog" class="ui-dialog ui-corner-all ui-widget ui-widget-content ui-front ui-draggable ui-resizable" aria-describedby="select_despatch" aria-labelledby="ui-id-5" style="position: absolute; height: auto; width: 720px; display: block; top: 52px; left: 307.5px; z-index: 100;">
    <div class="ui-dialog-titlebar ui-corner-all ui-widget-header ui-helper-clearfix ui-draggable-handle"><span id="ui-id-5" class="ui-dialog-title">Despatch LoV</span>
        <button type="button" class="ui-button ui-corner-all ui-widget ui-button-icon-only ui-dialog-titlebar-close" title="Close"><span class="ui-button-icon ui-icon ui-icon-closethick"></span><span class="ui-button-icon-space"> </span>Close</button>
    </div>
  -->



	<div title="Invoic LOV" class="poppane md" id="select_invoic">


		 <div class="lov-filter">
            <div class="row">
                <div class="col-10">
                    <div class="form-group">
                        <label class="form-control-label"><common:message  key="mail.mra.gpareporting.invoiclov.tooltip.gpacode" scope="request"/></label>
                        <ihtml:text styleClass="form-control" property="gpaCodeFilter" name="InvoicLovForm" componentID="MRA_GPAREPORTING_INVOICLOV_GPACODE"  maxlength="5"/>
                    </div>
				</div>
                <div class="col-7">
                    <div class="form-group">
                        <label class="form-control-label"><common:message  key="mail.mra.gpareporting.invoiclov.tooltip.fromdate" scope="request"/></label>

                                 <ibusiness:litecalendar
											labelStyleClass="form-control-label" name="InvoicLovForm" styleClass="form-control hasDatepicker"
											indexId="templateRowCount" id="fromDateFilter" componentID="MRA_GPAREPORTING_INVOICLOV_FROMDATE"
											property="fromDateFilter"  />

                    </div>
                </div>
                <div class="col-7">
                    <div class="form-group">
                        <label class="form-control-label"><common:message  key="mail.mra.gpareporting.invoiclov.tooltip.todate" scope="request"/></label>

                                      <ibusiness:litecalendar
											labelStyleClass="form-control-label" name="InvoicLovForm" styleClass="form-control hasDatepicker"
											indexId="templateRowCount" id="toDateFilter" componentID="MRA_GPAREPORTING_INVOICLOV_TODATE"
											property="toDateFilter"  />

                    </div>
                </div>

            </div> <!-- row ends-->
			 <div class="btn-row">

                        <ihtml:nbutton styleClass="btn btn-primary" property="btnList" id="btnList" componentID="CMP_MRA_GPAREPORTING_INVOICLOV_LIST"><common:message  key="mail.mra.gpareporting.invoiclov.btn.btlist" /></ihtml:nbutton>
                        <ihtml:nbutton styleClass="btn btn-default" property="btnClear" id="btnClear" componentID="CMP_MRA_GPAREPORTING_INVOICLOV_CLEAR"><common:message  key="mail.mra.gpareporting.invoiclov.btn.btclear" /></ihtml:nbutton>
                    </div>
        </div> <!--Filter ends-->
		  <div class="lov-list">
            <div class="card">
                <div class="card-header card-header-action"><!--pagination begins-->
                    <div class="mega-pagination">

<logic:present name="InvoicLovForm" property="invoicLovPage">
    <bean:define id="invoicLovPage" name="InvoicLovForm" property="invoicLovPage" toScope="page" />
    <logic:present name="invoicLovPage">
        <logic:present name="InvoicLovForm" property="multiselect">
            <bean:define id="multiselect" name="InvoicLovForm" property="multiselect" />
        </logic:present>
        <logic:equal name="InvoicLovForm" property="pagination" value="Y">
            <bean:define id="lastPageNum" name="InvoicLovForm" property="lastPageNum" toScope="page" />
			<!-- Modified by A-8527 for ICRD-342733-->
			<common:enhancedPaginationTag pageURL='javascript:submitPage("lastPageNum","displayPage",InvoicLovForm)'
			name="invoicLovPage" id="invoicLovPage"
			lastPageNum="<%=InvoicLovForm.getLastPageNum() %>"
			renderLengthMenu="true" lengthMenuName="defaultPageSize"
			defaultSelectOption="<%=InvoicLovForm.getDefaultPageSize() %>"
			lengthMenuHandler="showEntriesReloading"
			pageNumberFormAttribute="displayPage" />

        </logic:equal>
    </logic:present>
</logic:present>

                    </div>
                </div><!--pagination ends-->
						<bean:define id="strFormCount" name="InvoicLovForm" property="formCount"  />
						<bean:define id="strMultiselect" name="InvoicLovForm" property="multiselect" />
						<bean:define id="strLovTxtFieldName" name="InvoicLovForm" property="lovTxtFieldName"  />
						<bean:define id="strLovDescriptionTxtFieldName" name="InvoicLovForm" property="lovDescriptionTxtFieldName" />
						<bean:define id="strSelectedValues" name="InvoicLovForm" property="selectedValues" />
						<bean:define id="arrayIndex" name="InvoicLovForm" property="index"/>
                <div class="card-body p-0" style="height:225px; overflow-y:auto">


                    <table class="table table-x-md mb-0">
                        <thead>
                            <tr>
                                <th width="35"></th>
                                <th><common:message  key="mail.mra.gpareporting.invoiclov.tooltip.gpacode" scope="request"/></th>
                                <th><common:message  key="mail.mra.gpareporting.invoiclov.tooltip.invoicrefid" scope="request"/></th>
                                <th><common:message  key="mail.mra.gpareporting.invoiclov.tooltip.fromdate" scope="request"/></th>
                                <th><common:message  key="mail.mra.gpareporting.invoiclov.tooltip.todate" scope="request"/></th>
                            </tr>
                        </thead>
                        <tbody>

							<logic:present name="InvoicLovForm" property="invoicLovPage">

												<bean:define id="lovList" name="InvoicLovForm" property="invoicLovPage" toScope="page"/>
								<logic:present name="lovList">
												<% int i=0;%>
												<jsp:setProperty name="InvoicLovForm" property="lovActionType" value="lovList"/>
										<logic:iterate id = "val" name="lovList" indexId="indexId">

											<tr>
											<%=val%>
											<logic:notEqual name="InvoicLovForm" property="multiselect" value="Y">

																<%String checkValue = ((InvoicVO)val).getInvoicRefId();%>

																		<tr ondblclick="setValueOnDoubleClick('<%=strFormCount%>','<%=((InvoicVO)val).getInvoicRefId()%>',
																			'<%= strLovTxtFieldName%>','<%=strLovDescriptionTxtFieldName %>',<%=arrayIndex%>)">
											</logic:notEqual>
											<logic:equal name="InvoicLovForm" property="multiselect" value="Y">



																		<td width="3%">
																			<%
																			if(((String)strSelectedValues).contains(((InvoicVO)val).getInvoicRefId())){ %>
																				<input type="checkbox" name="selectCheckBox" value="<%=((InvoicVO)val).getInvoicRefId()%>"  checked="checked"/>
																			<%}else{ %>
																				<input type="checkbox" name="selectCheckBox" value="<%=((InvoicVO)val).getInvoicRefId()%>"  />
																			<% } %>
																		</td>
										</logic:equal>
										<logic:notEqual name="InvoicLovForm" property="multiselect" value="Y">
																			<td width="3%">
																				<%String checkVal = ((InvoicVO)val).getInvoicRefId();%>


																			<%

																			if(   ((String)strSelectedValues).equals(((InvoicVO)val).getInvoicRefId()  )){ %>
																				<input type="checkbox" name="selectCheckBox" value="<%=checkVal%>"  onclick="lovUtils.singleSelect({checkBoxVal:'<%=checkVal%>'});" checked="checked" />
																			<%}else{ %>
																				<input type="checkbox" name="selectCheckBox" value="<%=checkVal%>"  onclick="lovUtils.singleSelect({checkBoxVal:'<%=checkVal%>'});"/>
																			<% } %>


																			</td>
								</logic:notEqual>

																	<td width="10%" align="left">
																	<bean:write name="val" property="poaCode"/>
																	</td>
																	<td width="10%" align="left">
																	<bean:write name="val" property="invoicRefId"/>
																	</td>
																	<td width="10%" align="left">
																	<!--<bean:write name="val" property="fromDate"/>-->
								<logic:present name="val" property="fromDate">
							    <bean:define id="fromDate" name="val" property="fromDate" type="com.ibsplc.icargo.framework.util.time.LocalDate" />
								<%String frmDat=TimeConvertor.toStringFormat(fromDate.toCalendar(),"dd-MM-yyyy");%>
								 <%=frmDat%>
								 </logic:present>
																	</td>
																	<td width="10%" align="left">
																	<!--<bean:write name="val" property="toDate"/>-->
								<logic:present name="val" property="toDate">
								<bean:define id="toDate" name="val" property="toDate" type="com.ibsplc.icargo.framework.util.time.LocalDate" />
								<%String toDat=TimeConvertor.toStringFormat(toDate.toCalendar(),"dd-MM-yyyy");%>
								 <%=toDat%>
								 </logic:present>
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
            <ihtml:nbutton styleClass="btn btn-primary" property="btnOk" id="btnOk" componentID="CMP_MRA_GPAREPORTING_INVOICLOV_OK"><common:message key="mail.mra.gpareporting.invoiclov.btn.btok" /></ihtml:nbutton>
            <ihtml:nbutton styleClass="btn btn-default" id="btnClose" property="btnClose" componentID="CMP_MRA_GPAREPORTING_INVOICLOV_CLOSE"><common:message key="mail.mra.gpareporting.invoiclov.btn.btclose" /></ihtml:nbutton>
        </div>


    </div> <!--title ends-->




   <!--</div>-->
 <!--</div>-->
	</ihtml:form>


		   <jsp:include page="/jsp/includes/popupFooterSection.jsp"/>

		   <common:registerCharts/>
		   <common:registerEvent />
	</body>


</html:html>
