<%--
* Project	 		: iCargo
* Module Code & Name		: ListInvoic.jsp
* Date				: 13-Dec-2018
* Author(s)			: A-8527
--%>

<%@ include file="/jsp/includes/ux/tlds.jsp" %>

<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.ux.ListInvoicForm" %>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import="com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicFilterVO"%>
<%@ page import="com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicVO"%>
<%@ page info="lite"%>


<html:html>
<head>


	<title><common:message bundle="ListInvoicResourceBundle" key="mailtracking.mra.gpareporting.title.ListInvoic" />
	</title>
	<meta name="decorator" content="mainpanelux">
	<common:include type="script" src="/js/mail/mra/gpareporting/ux/ListInvoic_Script.jsp"/>
</head>
<body>

<business:sessionBean id="ListInvoicVOSession"
	moduleName="mail.mra"
	screenID="mail.mra.gpareporting.ux.listinvoic"
	method="get"
	attribute="listinvoicvos" />
	<business:sessionBean id="OneTimeValue"
	moduleName="mail.mra"
	screenID="mail.mra.gpareporting.ux.listinvoic"
	method="get"
	attribute="status" />
<business:sessionBean id="filterVOSession"
	moduleName="mail.mra"
	screenID="mail.mra.gpareporting.ux.listinvoic"
	method="get"
	attribute="filterParamValues" />




  <bean:define id="form" name="ListInvoicForm"  type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.ux.ListInvoicForm" toScope="page" />

 <ihtml:form action="/mail.mra.gpareporting.ux.listinvoic.screenload.do">
 <ihtml:hidden property="displayPage" />
 <ihtml:hidden property="lastPageNum" />
 <ihtml:hidden property="selectCheckBox" />
 <ihtml:hidden property="selectedValues" />
 <ihtml:hidden property="actionFlag" />
<ihtml:hidden property="invoicId" />
  <!--CONTENT STARTS-->
<div class="main-container">
        <header>

	<div class="flippane animated fadeInDown" id="headerForm" style="display: block;">
        <a href="#" class="flipper" flipper="headerData"><i class="icon ico-close-fade"></i></a>
        <div class="pad-x-md pad-t-md pad-b-2xs">
            <div class="row">
					<div class="col-3 col-sm-4 col-md-3">
						<div class="form-group">
                        <label class="form-control-label"><common:message key="mail.mra.gpareporting.ux.listinvoic.lbl.fromDate" scope="request"/></label>
                           &nbsp;<span class="text-red d-inline-block">*</span>
                            <div class="input-group">
							<logic:present name="filterVOSession" property="fromDate">
							<bean:define id="fromDate" name="filterVOSession" property="fromDate" toScope="page"/>
							<ibusiness:litecalendar tabindex="1" id="fromDate" property="fromDate" componentID="CMP_Mail_MRA_GPAreporting_Listinvoic_FromDate" value="<%=String.valueOf(fromDate)%>" readonly="false"/>
							</logic:present>
							<logic:notPresent name="filterVOSession" property="fromDate">
                              <ibusiness:litecalendar tabindex="1" id="fromDate" property="fromDate" componentID="CMP_Mail_MRA_GPAreporting_Listinvoic_FromDate" />
							  </logic:notPresent>
                            </div>
						</div>
					</div>
					<div class="col-3 col-sm-4 col-md-3">
                        <div class="form-group">
                        <label class="form-control-label"><common:message key="mail.mra.gpareporting.ux.listinvoic.lbl.toDate" scope="request"/></label>
						&nbsp;<span class="text-red d-inline-block">*</span>
                                <div class="input-group">
								<logic:present name="filterVOSession" property="toDate">
							<bean:define id="toDate" name="filterVOSession" property="toDate" toScope="page"/>
							  <ibusiness:litecalendar tabindex="2" id="toDate"  labelStyleClass="form-control-label" componentID="CMP_Mail_MRA_GPAreporting_Listinvoic_Todate" property="toDate" onblur="autoFillDate(this)" value="<%=String.valueOf(toDate)%>" readonly="false" />
							</logic:present>
							<logic:notPresent name="filterVOSession" property="toDate">
                                   <ibusiness:litecalendar tabindex="2" id="toDate"  labelStyleClass="form-control-label" componentID="CMP_Mail_MRA_GPAreporting_Listinvoic_Todate" property="toDate" onblur="autoFillDate(this)" />
							</logic:notPresent>
                                </div>
                        </div>
                     </div>

					<div class="col-3 col-sm-4 col-md-3">
						<div class="form-group">
                        <label class="form-control-label"> <common:message key="mail.mra.gpareporting.ux.listinvoic.lbl.paCode" scope="request" /></label>
                                <div class="input-group">
								<logic:present name="filterVOSession" property="gpaCode">
							<bean:define id="gpaCode" name="filterVOSession" property="gpaCode" toScope="page"/>
                                 <ihtml:text tabindex="3" inputGroup="true" buttonIconClass="icon ico-expand" labelStyleClass="form-control-label" property="paCode" id="paCode" styleClass="form-control" maxlength="5"   componentID="CMP_Mail_MRA_GPAreporting_Listinvoic_paCode" value="<%=String.valueOf(gpaCode)%>" readonly="false"  />
							</logic:present>
							<logic:notPresent name="filterVOSession" property="gpaCode">
                                 <ihtml:text tabindex="3" inputGroup="true" buttonIconClass="icon ico-expand" labelStyleClass="form-control-label" property="paCode" id="paCode" styleClass="form-control" maxlength="5"   componentID="CMP_Mail_MRA_GPAreporting_Listinvoic_paCode"/>
							 </logic:notPresent>
                                </div>
                        </div>
                    </div>
					<div class="col-3 col-sm-4 col-md-4">
                        <div class="form-group">
                        <label class="form-control-label"> <common:message key="mail.mra.gpareporting.ux.listinvoic.lbl.invoicID" scope="request" /></label>
                                <div class="input-group">
								<logic:present name="filterVOSession" property="invoicRefId">
							<bean:define id="invoicRefId" name="filterVOSession" property="invoicRefId" toScope="page"/>
                                 <ihtml:text tabindex="4" inputGroup="true" buttonIconClass="icon ico-expand" labelStyleClass="form-control-label" property="invoicRefId" id="invoicRefId" styleClass="form-control" maxlength="36"   componentID="CMP_Mail_MRA_GPAreporting_Listinvoic_InvoicID" value="<%=String.valueOf(invoicRefId)%>" readonly="false"  />
							</logic:present>
							<logic:notPresent name="filterVOSession" property="invoicRefId">
                                 <ihtml:text tabindex="4" inputGroup="true" buttonIconClass="icon ico-expand" labelStyleClass="form-control-label" property="invoicRefId" id="invoicRefId" styleClass="form-control" maxlength="36"   componentID="CMP_Mail_MRA_GPAreporting_Listinvoic_InvoicID"/>
							 </logic:notPresent>
                                </div>
                        </div>
                    </div>
					<div class="col-3 col-sm-4 col-md-5">
						<div class="form-group">
                        <label class="form-control-label"> <common:message key="mail.mra.gpareporting.ux.listinvoic.lbl.filnam" scope="request" /></label>
                                <div class="input-group">
								<logic:present name="filterVOSession" property="fileName">
							<bean:define id="fileName" name="filterVOSession" property="fileName" toScope="page"/>
                                 <ihtml:text tabindex="5" labelStyleClass="form-control-label" property="fileName" id="fileName" styleClass="form-control" style="text-transform:none;" componentID="CMP_Mail_MRA_GPAreporting_Listinvoic_filNam" value="<%=String.valueOf(fileName)%>" readonly="false"  />
							</logic:present>
							<logic:notPresent name="filterVOSession" property="fileName">
                                 <ihtml:text tabindex="5"  labelStyleClass="form-control-label" property="fileName" id="fileName" styleClass="form-control" style="text-transform:none;" componentID="CMP_Mail_MRA_GPAreporting_Listinvoic_filNam" />
							 </logic:notPresent>
                                </div>
                        </div>
                    </div>
					<div class="col-3 col-sm-4 col-md-3">
                        <div class="form-group">
                            <label class="form-control-label"><common:message key="mail.mra.gpareporting.ux.listinvoic.lbl.status" /></label>
							 <% String invoicstatusValue = ""; %>
								<logic:present name="filterVOSession" property="invoicfilterStatus">
								<bean:define id="Status" name="filterVOSession" property="invoicfilterStatus" toScope="page"/>
								<% invoicstatusValue = String.valueOf(Status); %>
								</logic:present>
								<logic:notPresent name="filterVOSession" property="invoicfilterStatus">
								</logic:notPresent>
							<ihtml:select tabindex="6" property="status" styleClass="form-control" componentID="CMP_Mail_Mail_MRA_GPAreporting_Listinvoic_status" value="<%=invoicstatusValue %>" >
							<ihtml:option value="ALL">All</ihtml:option>
								<logic:present name="OneTimeValue">
								<logic:iterate id="status" name="OneTimeValue">
								<bean:define id="fieldValue" name="status" property="fieldValue" />
								<bean:define id="fieldDescription" name="status" property="fieldDescription"/>
								<html:option value="<%=(String)fieldValue %>"><%=String.valueOf(fieldDescription)%></html:option>
								</logic:iterate>
								</logic:present>
								</ihtml:select>
                        </div>
                    </div>
					<div class="col">
						<div class="mar-t-md">
					     <ihtml:nbutton styleClass="btn btn-primary flipper" flipper="headerData" property="btnList" id="btnList" componentID="CMP_Mail_MRA_GPAreporting_Listinvoic_List" accesskey="L">
							<common:message key="mail.mra.gpareporting.ux.listinvoic.btn.list" />
						</ihtml:nbutton>

						<ihtml:nbutton styleClass="btn btn-default" property="btnClear" id="btnClear" componentID="CMP_Mail_MRA_GPAreporting_Listinvoic_Clear" accesskey="C">
							<common:message key="mail.mra.gpareporting.ux.listinvoic.btn.clear" />
						</ihtml:nbutton>
						</div>
					</div>
			</div>
		</div>
	</div>

	<div class="flippane animated fadeInDown" id="headerData" style="display: none;">
                <div class="pad-md">
                    <div class="row">
					<logic:present name="filterVOSession">
						<bean:define id="InvoicFilterVO" name="filterVOSession" toScope="page" />
                        <div class="col-4">
                            <label class="form-control-label"><common:message key="mail.mra.gpareporting.ux.listinvoic.lbl.fromDate"/></label>
                            <div class="form-control-data"><bean:write name="InvoicFilterVO" property="fromDate"/> </div>
                        </div>
                        <div class="col-4">
                            <label class="form-control-label"><common:message key="mail.mra.gpareporting.ux.listinvoic.lbl.toDate"/></label>
                            <div class="form-control-data"><bean:write name="InvoicFilterVO" property="toDate"/> </div>
                        </div>
                        <div class="col-3">
                            <label class="form-control-label"><common:message key="mail.mra.gpareporting.ux.listinvoic.lbl.paCode"/></label>
                            <div class="form-control-data" style="white-space:pre:wrap"><bean:write name="InvoicFilterVO" property="gpaCode"/>
                        </div>
                        </div>
                        <div class="col-5">
                            <label class="form-control-label"><common:message key="mail.mra.gpareporting.ux.listinvoic.lbl.invoicID"/></label>
                            <div class="form-control-data" >
							<pre class="h6"> <bean:write name="InvoicFilterVO" property="invoicRefId"/> </pre>
							</div>
                        </div>
						<div class="col-4">
                            <label class="form-control-label"><common:message key="mail.mra.gpareporting.ux.listinvoic.lbl.filnam"/></label>
                            <div class="form-control-data"><bean:write name="InvoicFilterVO" property="fileName"/> </div>
                        </div>
                        <div class="col-4">
                            <label class="form-control-label"><common:message key="mail.mra.gpareporting.ux.listinvoic.lbl.status"/></label>
							 <% String invoicstatus = ""; %>
							<bean:define id="Status" name="filterVOSession" property="invoicfilterStatus" toScope="page"/>
							<logic:iterate id="status" name="OneTimeValue">
								<logic:equal name="status" property="fieldValue" value="<%=(String)Status %>">
								<bean:define id="fieldDescription" name="status" property="fieldDescription"/>
								<% invoicstatus = String.valueOf(fieldDescription); %>
								</logic:equal>
								</logic:iterate>
                            <div class="form-control-data"><%=invoicstatus%> </div>
                        </div>
					</logic:present>
                    </div>
                </div>
                <a href="#" class="flipper" flipper="headerForm"><i class="icon ico-pencil-rounded-orange" id="edittab"></i></a>
            </div>
        </header>

		<section class="mar-y-md">
            <div class="card">
				<div class="card-header card-header-action">
                    <div class="col">
                        <h4>Invoic List</h4>
                    </div>
					<logic:present  name="ListInvoicVOSession">
					   <div class="mega-pagination">
									<common:enhancedPaginationTag
									pageURL='javascript:submitPage(lastPageNum,displayPage)'
							        name="ListInvoicVOSession" id="ListInvoicVOSession"
									exportToExcel="true"
									exportTableId="newForceTable"
									exportAction="mail.mra.gpareporting.ux.listinvoic.list.do"
							        lastPageNum="<%=form.getLastPageNum() %>"
							        renderLengthMenu="true" lengthMenuName="defaultPageSize"
							        defaultSelectOption="<%=form.getDefaultPageSize() %>"
							        lengthMenuHandler="showEntriesforlisting" pageNumberFormAttribute="displayPage"/>
                    </div>
					</logic:present>
                    </div>

				<div class="card-body p-0">
				 <div id="dataTableContainer" class="dataTableContainer tablePanel w-100 tableonly-container">
                    <table id="newForceTable" class="table mb-0 w-100">
                        <thead>
                            <tr>
                                <th class="text-center check-box-cell" style="width:3%">
                                   <input type="checkbox" name="checkall" onclick="updateHeaderCheckBox(this.form,this,this.form.selectCheckBox);"></td>
                                </th>
                                <th style="width:10%"><common:message key="mail.mra.gpareporting.ux.listinvoic.lbl.invoicRefId" /></th>
                                <th class="text-center" style="width:6%" class="text-center"><common:message key="mail.mra.gpareporting.ux.listinvoic.lbl.paCode"/></th>

                                <th class="text-center" style="width:6%"><common:message key="mail.mra.gpareporting.ux.listinvoic.lbl.fromDate" /></th>
                                <th class="text-center" style="width:6%"><common:message key="mail.mra.gpareporting.ux.listinvoic.lbl.toDate" /></th>
								 <th style="width:8%" class="text-center"><common:message key="mail.mra.gpareporting.ux.listinvoic.lbl.invoicDate" /></th>
                                <th class="text-center" style="width:8%"><common:message key="mail.mra.gpareporting.ux.listinvoic.lbl.totalamount" /></th>
								<th class="text-center" style="width:8%"><common:message key="mail.mra.gpareporting.ux.listinvoic.lbl.noofmails" /></th>
								<th class="text-center" style="width:7%"><common:message key="mail.mra.gpareporting.ux.listinvoic.lbl.paytype" /></th>
                                <th style="width:8%" class="text-center"><common:message key="mail.mra.gpareporting.ux.listinvoic.lbl.status" /></th>
								<th style="width:10%" class="text-center"><common:message key="mail.mra.gpareporting.ux.listinvoic.lbl.filnam" /></th>
                                <th style="width:20%" class="text-center"><common:message key="mail.mra.gpareporting.ux.listinvoic.lbl.remarks" /></th>
                            </tr>
                        </thead>
                        <tbody>
						<logic:present name="ListInvoicVOSession">
							<logic:iterate id ="InvoicVO" name="ListInvoicVOSession" type="com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicVO" indexId="rowCount">
                            <tr>
							<td class="text-center check-box-cell">
							<%String checkVal = ((InvoicVO)InvoicVO).getInvoicStatusCode();%>
							<%String payType = ((InvoicVO)InvoicVO).getPayType();%>
							<%String refNo = ((InvoicVO)InvoicVO).getInvoicRefId();%>
                                    <input type="checkbox" name="selectCheckBox" value="<%=String.valueOf(rowCount)%>" indexId="rowCount" onclick="checkOnEnablebtn(this.form,'<%=checkVal%>','<%=String.valueOf(rowCount)%>','<%=refNo%>','<%=payType%>');"/>
                            </td>
							<logic:equal name="InvoicVO" property="invoicStatusCode" value="RJ">
							<td><span class="d-block">
							<pre class="d-flex fs12 text-link m-0" style="font-family: inherit;"><bean:write name="InvoicVO" property="invoicRefId"/></pre></span></td>
							</logic:equal>
							<logic:notEqual name="InvoicVO" property="invoicStatusCode" value="RJ">
							<td><span class="d-block"><a class="d-inline-flex" href="#" onclick="openInvoicScreen('<%=refNo%>','<%=checkVal%>','<%=payType%>');">
							<pre class="d-flex fs12 text-link m-0" style="font-family: inherit;"><bean:write name="InvoicVO" property="invoicRefId"/></pre></a></span></td>
							</logic:notEqual>
							<td class="text-center"><span class="d-block"><bean:write name="InvoicVO" property="poaCode"/></span></td>

                                <td class="text-center"><span class="d-block">
								<logic:present name="InvoicVO" property="fromDate">
								<bean:define id="fromDate" name="InvoicVO" property="fromDate" type="com.ibsplc.icargo.framework.util.time.LocalDate" />
								<%String frmDat=TimeConvertor.toStringFormat(fromDate.toCalendar(),"dd-MM-yyyy");%>
								 <%=frmDat%>
								</logic:present>
								</span></td>
                                <td class="text-center"><span class="d-block">
								<logic:present name="InvoicVO" property="toDate">
								<bean:define id="toDate" name="InvoicVO" property="toDate" type="com.ibsplc.icargo.framework.util.time.LocalDate" />
								<%String toDat=TimeConvertor.toStringFormat(toDate.toCalendar(),"dd-MM-yyyy");%>
								 <%=toDat%>
								</logic:present>
								</span></td>
								 <td class="text-center"><span class="d-block">
								<logic:present name="InvoicVO" property="invoiceDate">
								<bean:define id="invoiceDate" name="InvoicVO" property="invoiceDate" type="com.ibsplc.icargo.framework.util.time.LocalDate" />
								<%String invoicDat=TimeConvertor.toStringFormat(invoiceDate.toCalendar(),"dd-MM-yyyy");%>
								 <%=invoicDat%>
								</logic:present>
								</span></td>
                                <td class="text-center"><span class="d-block"><bean:write name="InvoicVO" property="totalamount"/></span></td>
								<td class="text-center"><span class="d-block"><bean:write name="InvoicVO" property="numOfMailbags"/></span></td>
								<td class="text-center"><span class="d-block"><bean:write name="InvoicVO" property="payType"/></span></td>
								  <td class="text-center">
								  <logic:equal name="InvoicVO" property="invoicStatusCode" value="NW">
                                    <span class="badge badge-pill high-light badge-active pad-x-2xs pad-y-2xs"><bean:write name="InvoicVO" property="invoicStatus"/>
									</span>
									</logic:equal>
									<logic:equal name="InvoicVO" property="invoicStatusCode" value="IN">
                                    <span class="badge badge-pill high-light badge-active pad-x-2xs pad-y-2xs"><bean:write name="InvoicVO" property="invoicStatus"/>
									</span>
									</logic:equal>
									<logic:equal name="InvoicVO" property="invoicStatusCode" value="PR">
									<span class="badge badge-pill high-light badge-info pad-x-2xs pad-y-2xs"><bean:write name="InvoicVO" property="invoicStatus"/></span>
									</logic:equal>
									<logic:equal name="InvoicVO" property="invoicStatusCode" value="PE">
									<span class="badge badge-pill high-light badge-alert pad-x-2xs pad-y-2xs"><bean:write name="InvoicVO" property="invoicStatus"/></span>
									</logic:equal>
									<logic:equal name="InvoicVO" property="invoicStatusCode" value="RJ">
									<span class="badge badge-pill high-light badge-alert pad-x-2xs pad-y-2xs"><bean:write name="InvoicVO" property="invoicStatus"/></span>
									</logic:equal>
                                </td>
								<td class="text-center"><span class="d-block"><bean:write name="InvoicVO" property="fileName"/></span></td>
                                <td class="text-center"><span class="d-block"><bean:write name="InvoicVO" property="remarks"/></span></td>

                            </tr>
							</logic:iterate>
							</logic:present>
						</tbody>
                    </table>
				 </div>
			</div>
			</div>
			</section>
				<footer>
						<ihtml:nbutton styleClass="btn btn-default" id="btnReject" property="btnReject"
						componentID="CMP_Mail_MRA_GPAreporting_Listinvoic_Reject" accesskey="P">
						<common:message	key="mail.mra.gpareporting.ux.listinvoic.btn.reject" />
						</ihtml:nbutton>

						<ihtml:nbutton styleClass="btn btn-primary" id="btnProcess" property="btnProcess"
						componentID="CMP_Mail_MRA_GPAreporting_Listinvoic_Process"	accesskey="A">
						<common:message	key="mail.mra.gpareporting.ux.listinvoic.btn.process" />
						</ihtml:nbutton>

						<ihtml:nbutton styleClass="btn btn-default" id="btninvoicdtls" property="btninvoicdtls"
						componentID="CMP_Mail_MRA_GPAreporting_Listinvoic_InvoicDtls" accesskey="P">
						<common:message	key="mail.mra.gpareporting.ux.listinvoic.btn.InvoicDtls" />
						</ihtml:nbutton>

						<ihtml:nbutton styleClass="btn btn-default" id="btnClose" property="btnClose"
						componentID="CMP_Mail_MRA_GPAreporting_Listinvoic_close" accesskey="O">
						<common:message	key="mail.mra.gpareporting.ux.listinvoic.btn.close" />
						</ihtml:nbutton>
				</footer>
	</div>
	</ihtml:form>

	</body>
</html:html>