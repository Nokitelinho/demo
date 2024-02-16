<%--
* Project	 		: iCargo
* Module Code & Name: ClaimDetails.jsp
* Date				: 07-March-2019
* Author(s)			: A-8527
--%>

<%@ include file="/jsp/includes/ux/tlds.jsp" %>

<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.ux.ListInvoicForm" %>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import="com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicFilterVO"%>
<%@ page import="com.ibsplc.icargo.business.mail.mra.gpareporting.vo.ClaimDetailsVO"%>
<%@ page info="lite"%>

	
	
<html:html>
<head>
		
	<title><common:message bundle="ClaimDetailsResourceBundle" key="mailtracking.mra.gpareporting.title.ClaimDetails" />
	</title>
	<meta name="decorator" content="mainpanelux">
	<common:include type="script" src="/js/mail/mra/gpareporting/ux/ClaimDetails_Script.jsp"/>
</head>
<body>
		
	
<business:sessionBean id="ListClaimVOSession"
	moduleName="mail.mra"
	screenID="mail.mra.gpareporting.ux.claimDetails"
	method="get"
	attribute="listclaimdtlsvos" />
<business:sessionBean id="filterVOSession"
	moduleName="mail.mra"
	screenID="mail.mra.gpareporting.ux.claimDetails"
	method="get"
	attribute="filterParamValues" />
<business:sessionBean id="OneTimeValue"
	moduleName="mail.mra"
	screenID="mail.mra.gpareporting.ux.claimDetails"
	method="get"
	attribute="status" />
<business:sessionBean id="ClaimOneTimeValue"
	moduleName="mail.mra"
	screenID="mail.mra.gpareporting.ux.claimDetails"
	method="get"
	attribute="claimType" />



  <bean:define id="form" name="ClaimDetailsForm"  type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.ux.ClaimDetailsForm" toScope="page" />

 <ihtml:form action="/mail.mra.gpareporting.ux.claimDetails.screenload.do">
 <ihtml:hidden property="displayPage" />
 <ihtml:hidden property="lastPageNum" />
 <ihtml:hidden property="selectCheckBox" />
 <ihtml:hidden property="selectedValues" />
 <ihtml:hidden property="actionFlag" />
 <!--Added as part of ICRD-343131 -->
 <ihtml:hidden property="fromScreen" />

  <!--CONTENT STARTS-->
<div class="main-container">
        <header>

	 <div class="flippane animated fadeInDown" id="headerForm">
        <a href="#" class="flipper" flipper="headerData">
		<logic:present name="filterVOSession" property="fromDate">
		<i class="icon ico-close-fade"  id="closetab"></i>
		</logic:present>	
		</a>
        <div class="pad-md pad-b-3xs">
            <div class="row">
				<div class="col-3">
                    <div class="form-group">
                        <label class="form-control-label">
						<common:message key="mail.mra.gpareporting.ux.claimDetails.lbl.paCode" scope="request"/>
						</label>
                            <div class="input-group">
							<logic:present name="filterVOSession" property="gpaCode">
							<bean:define id="gpaCode" name="filterVOSession" property="gpaCode" toScope="page"/>
                                 <ihtml:text tabindex="1" inputGroup="true" buttonIconClass="icon ico-expand" labelStyleClass="form-control-label" property="paCode" id="paCode" styleClass="form-control" maxlength="5"   componentID="CMP_Mail_MRA_GPAreporting_ClaimDetails_PaCode" value="<%=String.valueOf(gpaCode)%>" readonly="false"  />
							</logic:present>	
							<logic:notPresent name="filterVOSession" property="gpaCode">
                                <ihtml:text tabindex="1" inputGroup="true" buttonIconClass="icon ico-expand" labelStyleClass="form-control-label" property="paCode" id="paCode" styleClass="form-control" maxlength="5"   componentID="CMP_Mail_MRA_GPAreporting_ClaimDetails_PaCode"/>	
							 </logic:notPresent>  
                            </div>
					</div>
				</div>
				<div class="col-3">
                    <div class="form-group">
                        <label class="form-control-label"><common:message key="mail.mra.gpareporting.ux.claimDetails.lbl.fromDate" scope="request"/></label>
						&nbsp;<span class="text-red d-inline-block">*</span>
                            <div class="input-group">
							<logic:present name="filterVOSession" property="fromDate">
							<bean:define id="fromDate" name="filterVOSession" property="fromDate" toScope="page"/>
								<ibusiness:litecalendar tabindex="5" id="fromDate"  labelStyleClass="form-control-label" componentID="CMP_Mail_MRA_GPAreporting_ClaimDetails_FromDate" property="fromDate" onblur="autoFillDate(this)" value="<%=String.valueOf(fromDate)%>" readonly="false" />
							</logic:present>
							<logic:notPresent name="filterVOSession" property="fromDate">
                                <ibusiness:litecalendar tabindex="5" id="fromDate"  labelStyleClass="form-control-label" componentID="CMP_Mail_MRA_GPAreporting_ClaimDetails_FromDate" property="fromDate" onblur="autoFillDate(this)" />
							</logic:notPresent> 
                            </div>
                    </div>
                </div>
				<div class="col-3">
                    <div class="form-group">
                        <label class="form-control-label"><common:message key="mail.mra.gpareporting.ux.claimDetails.lbl.toDate" scope="request"/></label>
						&nbsp;<span class="text-red d-inline-block">*</span>
                            <div class="input-group">
							<logic:present name="filterVOSession" property="toDate">
							<bean:define id="toDate" name="filterVOSession" property="toDate" toScope="page"/>
								<ibusiness:litecalendar tabindex="5" id="toDate"  labelStyleClass="form-control-label" componentID="CMP_Mail_MRA_GPAreporting_ClaimDetails_Todate" property="toDate" onblur="autoFillDate(this)" value="<%=String.valueOf(toDate)%>" readonly="false" />
							</logic:present>
							<logic:notPresent name="filterVOSession" property="toDate">
                                <ibusiness:litecalendar tabindex="5" id="toDate"  labelStyleClass="form-control-label" componentID="CMP_Mail_MRA_GPAreporting_ClaimDetails_Todate" property="toDate" onblur="autoFillDate(this)" />
							</logic:notPresent> 
                            </div>
                    </div>
                </div>
				<div class="col-4">
                    <div class="form-group">
                        <label class="form-control-label"><common:message key="mail.mra.gpareporting.ux.claimDetails.lbl.mailId" scope="request"/></label>
						<div class="input-group">
							<logic:present name="filterVOSession" property="mailbagId">
							<bean:define id="mailbagId" name="filterVOSession" property="mailbagId" toScope="page"/>
								<ihtml:text tabindex="5" id="mailbagId" inputGroup="true" buttonIconClass="icon ico-expand" labelStyleClass="form-control-label" componentID="CMP_Mail_MRA_GPAreporting_ClaimDetails_MailId" styleClass="form-control" maxlength="30" property="mailId"  value="<%=String.valueOf(mailbagId)%>" readonly="false" />
							</logic:present>
							<logic:notPresent name="filterVOSession" property="mailbagId">
                                <ihtml:text tabindex="5" id="mailbagId" inputGroup="true" buttonIconClass="icon ico-expand" labelStyleClass="form-control-label" componentID="CMP_Mail_MRA_GPAreporting_ClaimDetails_MailId" styleClass="form-control" maxlength="30"  property="mailId" />
							</logic:notPresent> 
                            </div>
                    </div>
                </div>
				<!--<div class="col-3">
                        <div class="form-group">
                            <label class="form-control-label"><common:message key="mail.mra.gpareporting.ux.claimDetails.lbl.status" /></label>
							 <% String invoicstatusValue = ""; %>
								<logic:present name="filterVOSession" property="invoicfilterStatus">
								<bean:define id="Status" name="filterVOSession" property="invoicfilterStatus" toScope="page"/>
								<% invoicstatusValue = String.valueOf(Status); %>
								</logic:present>
								<logic:notPresent name="filterVOSession" property="invoicfilterStatus">
								</logic:notPresent>
							<ihtml:select tabindex="5" property="status" styleClass="form-control" componentID="CMP_Mail_MRA_GPAreporting_ClaimDetails_status" value="<%=invoicstatusValue %>" >
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
                </div>   Commented as part of ICRD-343866--> 
				<div class="col-3">
                        <div class="form-group">
                            <label class="form-control-label"><common:message key="mail.mra.gpareporting.ux.claimDetails.lbl.claimtype" /></label>
							 <% String claimIdValue = ""; %>
								<logic:present name="filterVOSession" property="claimtype">
								<bean:define id="claimtype" name="filterVOSession" property="claimtype" toScope="page"/>
								<% claimIdValue = String.valueOf(claimtype); %>
								</logic:present>
								<logic:notPresent name="filterVOSession" property="claimtype">
								</logic:notPresent>
							<ihtml:select multiSelect="true"  multiSelectNoneSelectedText="Select" 	multiple="multiple"  property="claimtype" styleClass="form-control" componentID="CMP_Mail_MRA_GPAreporting_ClaimDetails_claimType">
								<logic:present name="ClaimOneTimeValue">
								<logic:iterate id="claimtype" name="ClaimOneTimeValue">
								<bean:define id="fieldValue" name="claimtype" property="fieldValue" />
								<bean:define id="fieldDescription" name="claimtype" property="fieldDescription"/>
								<html:option value="<%=(String)fieldValue %>"><%=String.valueOf(fieldDescription)%></html:option>
								</logic:iterate>
								</logic:present>
								</ihtml:select>
								
                        </div>
                </div>
				<div class="col-3">
                        <div class="form-group">
                            <label class="form-control-label">File Name</label>
								<logic:present name="filterVOSession" property="claimFileName">
							<bean:define id="claimFileName" name="filterVOSession" property="claimFileName" toScope="page"/>
								<ihtml:text tabindex="5" id="claimFileName"  labelStyleClass="form-control-label" componentID="CMP_Mail_MRA_GPAreporting_ClaimDetails_MailId" styleClass="form-control" maxlength="30" property="claimFileName"  value="<%=String.valueOf(claimFileName)%>" readonly="false" /> 
							</logic:present>
							<logic:notPresent name="filterVOSession" property="claimFileName">
                                <ihtml:text tabindex="5" id="claimFileName"  labelStyleClass="form-control-label" componentID="CMP_Mail_MRA_GPAreporting_ClaimDetails_MailId" styleClass="form-control" maxlength="30"  property="claimFileName" />
							</logic:notPresent>
                        </div>
                </div>
			
			</div>
		</div>
		<div class="btn-row">

					    <ihtml:nbutton styleClass="btn btn-primary flipper" flipper="headerData" property="btnList" id="btnList" componentID="CMP_Mail_MRA_GPAreporting_ClaimDetails_List" accesskey="L">
							<common:message key="mail.mra.gpareporting.ux.claimDetails.btn.list" />
						</ihtml:nbutton>
						
						<ihtml:nbutton styleClass="btn btn-default" property="btnClear" id="btnClear" componentID="CMP_Mail_MRA_GPAreporting_ClaimDetails_Clear" accesskey="C">
							<common:message key="mail.mra.gpareporting.ux.claimDetails.btn.clear" />
						</ihtml:nbutton>
				</div>
	</div>	
	
	<div class="flippane animated fadeInDown" id="headerData" style="display: none;">
             <div class="pad-md">
                <div class="row">
					<logic:present name="filterVOSession">
						<bean:define id="InvoicFilterVO" name="filterVOSession" toScope="page" />
						<logic:notEqual name="InvoicFilterVO" property="gpaCode" value="">
						<div class="col-4">
                            <label class="form-control-label"><common:message key="mail.mra.gpareporting.ux.claimDetails.lbl.paCode"/></label>
                            <div class="form-control-data"><bean:write name="InvoicFilterVO" property="gpaCode"/> </div>
                        </div>
						</logic:notEqual>
                        <div class="col-4">
                            <label class="form-control-label"><common:message key="mail.mra.gpareporting.ux.claimDetails.lbl.fromDate"/></label>
                            <div class="form-control-data"><bean:write name="InvoicFilterVO" property="fromDate"/> </div>
                        </div>
                        <div class="col-4">
                            <label class="form-control-label"><common:message key="mail.mra.gpareporting.ux.claimDetails.lbl.toDate"/></label>
                            <div class="form-control-data"><bean:write name="InvoicFilterVO" property="toDate"/> </div>
                        </div>
						<logic:notEqual name="InvoicFilterVO" property="mailbagId"  value="">
                         <div class="col-5">
                            <label class="form-control-label"><common:message key="mail.mra.gpareporting.ux.claimDetails.lbl.mailId"/></label>
                            <div class="form-control-data"><bean:write name="InvoicFilterVO" property="mailbagId"/> </div>
                        </div>
						</logic:notEqual>
						<!--<logic:notEqual name="InvoicFilterVO" property="invoicfilterStatus" value="">
						 <div class="col-4">
                            <label class="form-control-label"><common:message key="mail.mra.gpareporting.ux.claimDetails.lbl.status"/></label>
                            <div class="form-control-data"><bean:write name="InvoicFilterVO" property="invoicfilterStatus"/> </div>
                        </div>
						</logic:notEqual>  Commented as part of ICRD-343866 -->
						<logic:notEqual name="InvoicFilterVO" property="claimType" value="">
						<div class="col-4">
                            <label class="form-control-label"><common:message key="mail.mra.gpareporting.ux.claimDetails.lbl.claimtype"/></label>
                            <div class="form-control-data"><bean:write name="InvoicFilterVO" property="claimType"/> </div>
                        </div>	
						</logic:notEqual>
						<logic:notEqual name="InvoicFilterVO" property="claimFileName" value="">
						<div class="col-4">
                            <label class="form-control-label"><common:message key="mail.mra.gpareporting.ux.claimDetails.lbl.claimFileName" /></label>
                            <div class="form-control-data"><bean:write name="InvoicFilterVO" property="claimFileName"/> </div>
                        </div>	
						</logic:notEqual>
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
                        <h4>List Claim</h4>
                    </div>

					<logic:present  name="ListClaimVOSession">
					    <div class="mega-pagination">
									<common:enhancedPaginationTag
									pageURL='javascript:submitPage(lastPageNum,displayPage)'
							        name="ListClaimVOSession" id="ListClaimVOSession"
									exportToExcel="true" 
									exportTableId="newClaimTable" 
									exportAction="mail.mra.gpareporting.ux.claimDetails.list.do" 
							        lastPageNum="<%=form.getLastPageNum() %>"
							        renderLengthMenu="true" lengthMenuName="defaultPageSize" 
							        defaultSelectOption="<%=form.getDefaultPageSize() %>"  
							        lengthMenuHandler="showEntriesforlisting" pageNumberFormAttribute="displayPage"/>
						</div>
					</logic:present>
				</div>
			<div class="card-body p-0">
				<div id="dataTableContainer" class="dataTableContainer tablePanel w-100 tableonly-container">
                    <table id="newClaimTable" class="table m-0 w-100">
                        <thead>
                            <tr>
                                <th><common:message key="mail.mra.gpareporting.ux.claimDetails.lbl.paCode"/></th>
								<th><common:message key="mail.mra.gpareporting.ux.claimDetails.lbl.mailId" /></th>
								<th><common:message key="mail.mra.gpareporting.ux.claimDetails.lbl.mailbagDate" /></th>
                                <th><common:message key="mail.mra.gpareporting.ux.claimDetails.lbl.fromDate" /></th>
                                <th><common:message key="mail.mra.gpareporting.ux.claimDetails.lbl.toDate" /></th>
								<th><common:message key="mail.mra.gpareporting.ux.claimDetails.lbl.claimtype" /></th>
                                <th><common:message key="mail.mra.gpareporting.ux.claimDetails.lbl.claimamount" /></th>
                                <th><common:message key="mail.mra.gpareporting.ux.claimDetails.lbl.currency" /></th>
								 <th><common:message key="mail.mra.gpareporting.ux.claimDetails.lbl.claimSubDate" /></th>
								  <th><common:message key="mail.mra.gpareporting.ux.claimDetails.lbl.claimFileName" /></th>
                            </tr>
                        </thead>
                        <tbody>
						<logic:present name="ListClaimVOSession">
							<logic:iterate id ="claimdetailsvo" name="ListClaimVOSession" type="com.ibsplc.icargo.business.mail.mra.gpareporting.vo.ClaimDetailsVO" indexId="rowCount">
                            <tr>
							<td><span class="d-block"><bean:write name="claimdetailsvo" property="gpaCode"/></a>
                             </td>
							 <td><span class="d-block">
							<bean:write name="claimdetailsvo" property="mailBagId"/>
                            </span></td>
							<td><span class="d-block">
								<logic:present name="claimdetailsvo" property="receivedDate">
								<bean:define id="mailDate" name="claimdetailsvo" property="receivedDate" type="com.ibsplc.icargo.framework.util.time.LocalDate" />
								<%String mailbagDat=TimeConvertor.toStringFormat(mailDate.toCalendar(),"dd-MM-yyyy");%>
								<%=mailbagDat%>
								</logic:present>
							</span></td>
                            <td><span class="d-block">
								<logic:present name="claimdetailsvo" property="fromDate">
								<bean:define id="fromDate" name="claimdetailsvo" property="fromDate" type="com.ibsplc.icargo.framework.util.time.LocalDate" />
								<%String frmDat=TimeConvertor.toStringFormat(fromDate.toCalendar(),"dd-MM-yyyy");%>
								<%=frmDat%>
								</logic:present>
							</span></td>
                            <td><span class="d-block">
								<logic:present name="claimdetailsvo" property="toDate">
								<bean:define id="toDate" name="claimdetailsvo" property="toDate" type="com.ibsplc.icargo.framework.util.time.LocalDate" />
								<%String toDat=TimeConvertor.toStringFormat(toDate.toCalendar(),"dd-MM-yyyy");%>
								<%=toDat%>
								</logic:present>
							</span></td>
							<td><span class="d-block">
							<bean:write name="claimdetailsvo" property="claimType"/>
							</span></td>
                            <td><span class="d-block">
							<bean:write name="claimdetailsvo" property="claimAmount"/>
							</span></td>
				            <td><span class="d-block">
							<bean:write name="claimdetailsvo" property="currency"/>
							</span></td>
							<td><span class="d-block">
							<logic:present name="claimdetailsvo" property="claimSubDate">
							<bean:define id="claimSubDate" name="claimdetailsvo" property="claimSubDate" type="com.ibsplc.icargo.framework.util.time.LocalDate" />
								<%String claimDat=TimeConvertor.toStringFormat(claimSubDate.toCalendar(),"dd-MM-yyyy");%>
								<%=claimDat%>
							</logic:present>
							</span></td>
							<td><span class="d-block">
							<logic:present name="claimdetailsvo" property="claimFilename">
							<bean:write name="claimdetailsvo" property="claimFilename"/>
							</logic:present>
							</span></td>
                            </tr>
							</logic:iterate>
							</logic:present>
						</tbody>
                    </table>	
				 </div>	
			
			</div>
			</section>
			<footer>
									
				<ihtml:nbutton styleClass="btn btn-default" id="btnClose" property="btnClose"
				componentID="CMP_Mail_MRA_GPAreporting_ClaimDetails_Close" accesskey="C">
				<common:message	key="mail.mra.gpareporting.ux.claimDetails.btn.close" />
				</ihtml:nbutton>
			</footer>
	</div>
	</ihtml:form>

	</body>
</html:html>
