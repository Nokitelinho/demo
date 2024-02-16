<%--
* Project	 		: iCargo
* Module Code & Name		: GenerateandListClaim.jsp
* Date				: 04-March-2019
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

	<title><common:message bundle="GenerateandListClaimResourceBundle" key="mailtracking.mra.gpareporting.title.GenerateandListClaim" />
	</title>
	<meta name="decorator" content="mainpanelux">
	<common:include type="script" src="/js/mail/mra/gpareporting/ux/GenerateandListClaim_Script.jsp"/>
</head>
<body>
	
<business:sessionBean id="ListClaimVOSession"
	moduleName="mail.mra"
	screenID="mail.mra.gpareporting.ux.generateandlistclaim"
	method="get"
	attribute="listclaimbulkvos" />
<business:sessionBean id="filterVOSession"
	moduleName="mail.mra"
	screenID="mail.mra.gpareporting.ux.generateandlistclaim"
	method="get"
	attribute="filterParamValues" />




  <bean:define id="form" name="GenerateandListClaimForm"  type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.ux.GenerateandListClaimForm" toScope="page" />

 <ihtml:form action="/mail.mra.gpareporting.ux.generateandlistclaim.screenload.do">
 <ihtml:hidden property="displayPage" />
 <ihtml:hidden property="lastPageNum" />
 <ihtml:hidden property="selectCheckBox" />
 <ihtml:hidden property="selectedValues" />
 <ihtml:hidden property="actionFlag" />

  <!--CONTENT STARTS-->
<div class="main-container">
        <header>

	 <div class="flippane animated fadeInDown" id="headerForm">
        <a href="#" class="flipper" flipper="headerData">
		<logic:present name="filterVOSession" property="gpaCode">
		<i class="icon ico-close-fade" id="closetab"></i>
		</logic:present>
		</a>
        <div class="pad-md">
            <div class="row">
				<div class="col-3">
                    <div class="form-group">
                        <label class="form-control-label">
						<common:message key="mail.mra.gpareporting.ux.generateandlistclaim.lbl.paCode" scope="request"/>
						</label>&nbsp;<span class="text-red d-inline-block">*</span>
                            <div class="input-group">
							<logic:present name="filterVOSession" property="gpaCode">
							<bean:define id="gpaCode" name="filterVOSession" property="gpaCode" toScope="page"/>
                                 <ihtml:text tabindex="1" inputGroup="true" buttonIconClass="icon ico-expand" labelStyleClass="form-control-label" property="paCode" id="paCode" styleClass="form-control" maxlength="5"   componentID="CMP_Mail_MRA_GPAreporting_GenerateandListClaim_PaCode" value="<%=String.valueOf(gpaCode)%>" readonly="false"  />
							</logic:present>	
							<logic:notPresent name="filterVOSession" property="gpaCode">
                                <ihtml:text tabindex="1" inputGroup="true" buttonIconClass="icon ico-expand" labelStyleClass="form-control-label" property="paCode" id="paCode" styleClass="form-control" maxlength="5"   componentID="CMP_Mail_MRA_GPAreporting_GenerateandListClaim_PaCode"/>	
							 </logic:notPresent>  
                            </div>
					</div>
				</div>
				<div class="col-3">
                    <div class="form-group">
                        <label class="form-control-label"><common:message key="mail.mra.gpareporting.ux.generateandlistclaim.lbl.fromDate" scope="request"/></label>
						&nbsp;<span class="text-red d-inline-block">*</span>
                            <div class="input-group">
							<logic:present name="filterVOSession" property="fromDate">
							<bean:define id="fromDate" name="filterVOSession" property="fromDate" toScope="page"/>
								<ibusiness:litecalendar tabindex="5" id="fromDate"  labelStyleClass="form-control-label" componentID="CMP_Mail_MRA_GPAreporting_GenerateandListClaim_FromDate" property="fromDate" onblur="autoFillDate(this)" value="<%=String.valueOf(fromDate)%>" readonly="false" />
							</logic:present>
							<logic:notPresent name="filterVOSession" property="fromDate">
                                <ibusiness:litecalendar tabindex="5" id="fromDate"  labelStyleClass="form-control-label" componentID="CMP_Mail_MRA_GPAreporting_GenerateandListClaim_FromDate" property="fromDate" onblur="autoFillDate(this)" />
							</logic:notPresent> 
                            </div>
                    </div>
                </div>
				<div class="col-3">
                    <div class="form-group">
                        <label class="form-control-label"><common:message key="mail.mra.gpareporting.ux.generateandlistclaim.lbl.toDate" scope="request"/></label>
						&nbsp;<span class="text-red d-inline-block">*</span>
                            <div class="input-group">
							<logic:present name="filterVOSession" property="toDate">
							<bean:define id="toDate" name="filterVOSession" property="toDate" toScope="page"/>
								<ibusiness:litecalendar tabindex="5" id="toDate"  labelStyleClass="form-control-label" componentID="CMP_Mail_MRA_GPAreporting_GenerateandListClaim_Todate" property="toDate" onblur="autoFillDate(this)" value="<%=String.valueOf(toDate)%>" readonly="false" />
							</logic:present>
							<logic:notPresent name="filterVOSession" property="toDate">
                                <ibusiness:litecalendar tabindex="5" id="toDate"  labelStyleClass="form-control-label" componentID="CMP_Mail_MRA_GPAreporting_GenerateandListClaim_Todate" property="toDate" onblur="autoFillDate(this)" />
							</logic:notPresent> 
                            </div>
                    </div>
                </div>
									
				<div class="col">
					<div class="mar-t-md">

						<ihtml:nbutton styleClass="btn btn-default" property="btnGenClaimResdit" id="btnGenClaimResdit" componentID="CMP_Mail_MRA_GPAreporting_GenerateandListClaim_GenClaimResdit" accesskey="R">
							<common:message key="mail.mra.gpareporting.ux.generateandlistclaim.btn.genclaimresdit" />
						</ihtml:nbutton>
						
						<ihtml:nbutton styleClass="btn btn-default poptrigger"  property="btnGenClaim" id="btnGenClaim" componentID="CMP_Mail_MRA_GPAreporting_GenerateandListClaim_GenClaim" accesskey="C">
							<common:message key="mail.mra.gpareporting.ux.generateandlistclaim.btn.genclaim" />
						</ihtml:nbutton>
						
					     <ihtml:nbutton styleClass="btn btn-primary flipper" flipper="headerData" property="btnList" id="btnList" componentID="CMP_Mail_MRA_GPAreporting_GenerateandListClaim_List" accesskey="L">
							<common:message key="mail.mra.gpareporting.ux.generateandlistclaim.btn.list" />
						</ihtml:nbutton>
						
						<ihtml:nbutton styleClass="btn btn-default" property="btnClear" id="btnClear" componentID="CMP_Mail_MRA_GPAreporting_GenerateandListClaim_Clear" accesskey="C">
							<common:message key="mail.mra.gpareporting.ux.generateandlistclaim.btn.clear" />
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
                            <label class="form-control-label"><common:message key="mail.mra.gpareporting.ux.generateandlistclaim.lbl.paCode"/></label>
                            <div class="form-control-data"><bean:write name="InvoicFilterVO" property="gpaCode"/> </div>
                        </div>
                        <div class="col-4">
                            <label class="form-control-label"><common:message key="mail.mra.gpareporting.ux.generateandlistclaim.lbl.fromDate"/></label>
                            <div class="form-control-data"><bean:write name="InvoicFilterVO" property="fromDate"/> </div>
                        </div>
                        <div class="col-4">
                            <label class="form-control-label"><common:message key="mail.mra.gpareporting.ux.generateandlistclaim.lbl.toDate"/></label>
                            <div class="form-control-data"><bean:write name="InvoicFilterVO" property="toDate"/> </div>
                        </div>
                         
					</logic:present>
                </div>
            </div>
                <a href="#" class="flipper" flipper="headerForm"><i class="icon ico-pencil-rounded-orange" id="edittab"></i></a>
            </div>
        </header>

		<section class="mar-y-md">
            <div class="card">
				<div class="card-header d-flex justify-content-end">
                    <h4 class="col">List Claim</h4>
					<logic:present  name="ListClaimVOSession">
					    <div class="pagination align-middle align-items-center mar-r-2xs">
									<common:enhancedPaginationTag
									pageURL='javascript:submitPage("lastPageNum","displayPage")'
							        name="ListClaimVOSession" id="ListClaimVOSession"
									lastPageNum="<%=form.getLastPageNum() %>"
							        renderLengthMenu="true" lengthMenuName="defaultPageSize" 
							        defaultSelectOption="<%=form.getDefaultPageSize() %>"  
							        lengthMenuHandler="showEntriesforlisting" pageNumberFormAttribute="displayPage"/>
						</div>
					</logic:present>  
				
			</div>
			<div class="card-body p-0">
				<div id="dataTableContainer" class="dataTableContainer tablePanel w-100 tableonly-container">
                    <table id="newClaimTable" class="table mb-0 w-100">
                        <thead>
                            <tr>
                                <th class="text-center check-box-cell">
                                  
                                </th>
                                <th><common:message key="mail.mra.gpareporting.ux.generateandlistclaim.lbl.paCode"/></th>
                                <th><common:message key="mail.mra.gpareporting.ux.generateandlistclaim.lbl.fromDate" /></th>
                                <th><common:message key="mail.mra.gpareporting.ux.generateandlistclaim.lbl.toDate" /></th>
								<th><common:message key="mail.mra.gpareporting.ux.generateandlistclaim.lbl.noofmailbags" /></th>
                                <th><common:message key="mail.mra.gpareporting.ux.generateandlistclaim.lbl.claimamount" /></th>
                                <th class="w-20"><common:message key="mail.mra.gpareporting.ux.generateandlistclaim.lbl.currency" /></th>
                            </tr>
                        </thead>
                        <tbody>
						<logic:present name="ListClaimVOSession">
							<logic:iterate id ="claimdetailsvo" name="ListClaimVOSession" type="com.ibsplc.icargo.business.mail.mra.gpareporting.vo.ClaimDetailsVO" indexId="rowCount">
                            <tr>
							<td class="text-center check-box-cell">
							<%String paCod =((ClaimDetailsVO)claimdetailsvo).getGpaCode();%>
							<%String rfmDate =  TimeConvertor.toStringFormat(((ClaimDetailsVO)claimdetailsvo).getFromDate(),"dd-MMM-yyyy");%>
							<%String rtoDate =  TimeConvertor.toStringFormat(((ClaimDetailsVO)claimdetailsvo).getToDate(),"dd-MMM-yyyy");%>
							<input type="checkbox" name="selectCheckBox" value="<%=String.valueOf(rowCount)%>" indexId="rowCount" onclick="setvalueOnClick('<%=paCod%>','<%=rfmDate%>','<%=rtoDate%>');"/>
                            </td>
							<td>
							<span class="d-block"><bean:write name="claimdetailsvo" property="gpaCode"/></span>
                             </td>
                            <td><span class="d-block">
								<logic:present name="claimdetailsvo" property="fromDate">
								<bean:define id="fromDate" name="claimdetailsvo" property="fromDate" type="com.ibsplc.icargo.framework.util.time.LocalDate" />
								<%String frmDat=TimeConvertor.toStringFormat(fromDate.toCalendar(),"dd-MMM-yyyy");%>
								<%=frmDat%>
								</logic:present>
							</span></td>
                            <td><span class="d-block">
								<logic:present name="claimdetailsvo" property="toDate">
								<bean:define id="toDate" name="claimdetailsvo" property="toDate" type="com.ibsplc.icargo.framework.util.time.LocalDate" />
								<%String toDat=TimeConvertor.toStringFormat(toDate.toCalendar(),"dd-MMM-yyyy");%>
								<%=toDat%>
								</logic:present>
							</span></td>
							<td><span class="d-block">
							<bean:write name="claimdetailsvo" property="noOfMailbags"/>
							</span></td>
                            <td><span class="d-block">
							<bean:write name="claimdetailsvo" property="claimAmount"/>
							</span></td>
				            <td><span class="d-block">
							<bean:write name="claimdetailsvo" property="currency"/>
							</span></td>

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
						
				<ihtml:nbutton styleClass="btn btn-primary" id="btnclaimdtls" property="btnclaimdtls" 
				componentID="CMP_Mail_MRA_GPAreporting_GenerateandListClaim_claimDtls" accesskey="D">
				<common:message	key="mail.mra.gpareporting.ux.generateandlistclaim.btn.claimDetails" />
				</ihtml:nbutton>
						
				<ihtml:nbutton styleClass="btn btn-default" id="btnClose" property="btnClose"
				componentID="CMP_Mail_MRA_GPAreporting_GenerateandListClaim_close" accesskey="C">
				<common:message	key="mail.mra.gpareporting.ux.generateandlistclaim.btn.close" />
				</ihtml:nbutton>
			</footer>
	
	</ihtml:form>
	</div>

	
	</body>
</html:html>
