<%--******************************************************************************************
* Project	 		: iCargo
* Module Code & Name		: ListBillingMatrix.jsp
* Date				: 01-MAR-2007
* Author(s)			: A-2280
 ******************************************************************************************--%>

<%@ include file="/jsp/includes/tlds.jsp" %>


<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListBillingMatrixForm" %>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import = "java.util.Calendar" %>
<%@ page import="java.util.Formatter" %>
<bean:define id="form" name="ListBillingMatrixForm" type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListBillingMatrixForm" toScope="page" />


		
	
<html:html>
<head>

	

	
	<title>
	<common:message bundle="<%=form.getBundle()%>" key="mailtracking.mra.defaults.listbillingmatrix.title" />

	</title>

	<meta name="decorator" content="mainpanelrestyledui">
	<common:include type="script" src="/js/mail/mra/defaults/ListBillingMatrix_Script.jsp" />


</head>

<body style="overflow:auto;">
	
	
	
	

  <!--CONTENT STARTS-->

    <div id="contentdiv" class="iCargoContent ic-masterbg" style="overflow:auto;">
    <ihtml:form action="/mailtracking.mra.defaults.listbillingmatrixscreenload.do" >
    <ihtml:hidden property="displayPage" />
    <ihtml:hidden property="lastPageNum" />
    <ihtml:hidden property="changedStatus"/>
    <ihtml:hidden property="selectedIndexes"/>
	<input type="hidden" name="mySearchEnabled" />
	
	
	<div class="ic-content-main">
		<div class="ic-head-container">	
			<span class="ic-page-title ic-display-none">
				<common:message key="mailtacking.mra.defaults.maintainbillingmatrix.pageTitle" scope="request" />
			</span>
			<div class="ic-filter-panel">
				<div class="ic-row ic-label-30">
				<div class="ic-col-25">
					<div class="ic-input ic-split-100">
					<label><common:message key="mailtracking.mra.defaults.listbillingmatrix.billingmatrixid" /></label>
					<ihtml:text property="billingMatrixId" componentID="CMP_MRA_DEFAULTS_LISTBILLINGMATRIX_BLGMATID" style="text-transform : uppercase"  tabindex="1" />
					<div class="lovImg">
					<img name="billingMatrixLov" id="billingMatrixLov" height="22" src="<%=request.getContextPath()%>/images/lov.png" width="22" alt="" />
					</div>
					</div>
					<div class="ic-input ic-split-100">
					<label><common:message key="mailtracking.mra.defaults.listbillingmatrix.validfrmmdate" /></label>
					<ibusiness:calendar
									componentID="CMP_MRA_DEFAULTS_LISTBILLINGMATRIX_FRMDATE"
									property="validFrom"
									value="<%=form.getValidFrom()%>"
									type="image"
									id="frmDate" tabindex="5"/>
					</div>
				</div>
				
				<div class="ic-col-25">
					<div class="ic-input ic-split-100">
					<label><common:message key="mailtracking.mra.defaults.listbillingmatrix.poacode" /></label>
					<ihtml:text property="poaCode" componentID="CMP_MRA_DEFAULTS_LISTBILLINGMATRIX_PACODE" style="text-transform : uppercase" tabindex="2" />
					<div class="lovImg">
					<img name="poaCodeLov" id="poaCodeLov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" alt="" />
					</div>
					</div>
					<div class="ic-input ic-split-100">
					<label><common:message key="mailtracking.mra.defaults.listbillingmatrix.validtodate" /></label>
					 <ibusiness:calendar
									componentID="CMP_MRA_DEFAULTS_LISTBILLINGMATRIX_TODATE"
									property="validTo"
									value="<%=form.getValidTo()%>"
									type="image"
									id="toDate" tabindex="6"/>
					</div>
				</div>
				<div class="ic-col-25">
					<div class="ic-input ic-split-100">
					<label><common:message key="mailtracking.mra.defaults.listbillingmatrix.status" /></label>
					<business:sessionBean id="OneTimeValues"
									  moduleName="mailtracking.mra.defaults"
									  screenID="mailtracking.mra.defaults.listbillingmatrix"
									  method="get"
									  attribute="oneTimeValues" />

									<ihtml:select property="status" componentID="CMP_MRA_DEFAULTS_LISTBILLINGMATRIX_STATUS" tabindex="3">
										<html:option value=""></html:option>
										<logic:present name="OneTimeValues" >
										   <bean:define id="oneTimeValuesMap" name="OneTimeValues" type="java.util.HashMap" />
										   <logic:iterate id="oneTimeValues" name="oneTimeValuesMap">
										      <logic:equal name="oneTimeValues" property="key" value="mra.gpabilling.ratestatus" >
										        <logic:iterate id="oneTimeVO" name="oneTimeValues" property="value" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
											   <html:option value="<%=oneTimeVO.getFieldValue()%>"><%=oneTimeVO.getFieldDescription()%></html:option>
										        </logic:iterate>
										      </logic:equal>
							                            </logic:iterate>
										</logic:present>


									</ihtml:select>
					</div>
					<div id="divChild" class="ic-input ic-split-100">
							<label><common:message key="mailtracking.mra.defaults.listbillingmatrix.poaname" /></label>
							<ihtml:text property="poaName" readonly="true" componentID="CMP_MRA_DEFAULTS_LISTBILLINGMATRIX_PANAME" style="text-transform : uppercase"  tabindex="-1" />
					</div>
				</div>
				<div class="ic-col-25">
					<div class="ic-input ic-split-100">
					<label><common:message key="mailtracking.mra.defaults.listbillingmatrix.airline" /></label>
					<ihtml:text property="airlineCode" componentID="CMP_MRA_DEFAULTS_LISTBILLINGMATRIX_AIRLINE" style="text-transform : uppercase"  tabindex="4" />
					<div class="lovImg">
					<img name="airlineCodeLov" id="airlineCodeLov" height="22" src="<%=request.getContextPath()%>/images/lov.png" width="22" alt="" />
					</div>
				</div>
					<div class="ic-button-container">
						<ihtml:nbutton property="btnList" accesskey="L" componentID="CMP_MRA_DEFAULTS_LISTBILLINGMATRIX_BTNDETAILS" tabindex="8">
							<common:message key="mailtracking.mra.defaults.listbillingmatrix.btn.details" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btnClear" accesskey="C" componentID="CMP_MRA_DEFAULTS_LISTBILLINGMATRIX_BTNCLEAR" tabindex="9">
							<common:message key="mailtracking.mra.defaults.listbillingmatrix.btn.clear" />
						</ihtml:nbutton>
					</div>
				</div>
				</div>
			</div>
		</div>
		<div class="ic-main-container">
			<div class="ic-row">
					<business:sessionBean id="BillingMatrixVOs"
			     			  moduleName="mailtracking.mra.defaults"
			     			  screenID="mailtracking.mra.defaults.listbillingmatrix"
			     			  method="get"
			  attribute="billingMatrixVOs" />
				<logic:present name="BillingMatrixVOs" >
					 <common:paginationTag pageURL="mailtracking.mra.defaults.listbillingmatrixlist.do"
						name="BillingMatrixVOs" display="label"
						labelStyleClass="iCargoResultsLabel"
						lastPageNum="<%=form.getLastPageNum() %>" />
				  </logic:present>
				  <logic:notPresent name="BillingMatrixVOs">
					&nbsp;
				  </logic:notPresent>
					<div class="ic-button-container paddR5">
					 <logic:present name="BillingMatrixVOs">
						 <common:paginationTag pageURL="javascript:submitPage('lastPageNum','displayPage')"
						  name="BillingMatrixVOs"
						  display="pages"
						  linkStyleClass="iCargoLink"
						  disabledLinkStyleClass="iCargoLink"
						  lastPageNum="<%=form.getLastPageNum()%>" 
						  exportToExcel="true"
						  exportTableId="captureAgtSettlementMemo"
						  exportAction="mailtracking.mra.defaults.listbillingmatrixlist.do"/>
					 </logic:present>
					 <logic:notPresent name="BillingMatrixVOs">
						&nbsp;
					 </logic:notPresent>
					 </div>
				</div>
			
		<div class="ic-row">
			<div class="tableContainer" id="div1" style="height:660px">
			<table class="fixed-header-table" id="captureAgtSettlementMemo" >
			<thead>
			<tr>
			<td  class="iCargoTableHeader" width="4%"><input type="checkbox" name="headChk" onclick="updateHeaderCheckBox(this.form,this.form.headChk,this.form.rowId)" /></td>
			<td  class="iCargoTableHeader" width="12%" ><common:message key="mailtracking.mra.defaults.listbillingmatrix.tbl.billingmatrixid" /><span></span></td>
			<td  class="iCargoTableHeader" width="12%" ><common:message key="mailtracking.mra.defaults.listbillingmatrix.tbl.description" /><span></span></td>
			<td  class="iCargoTableHeader" width="12%"><common:message key="mailtracking.mra.defaults.listbillingmatrix.tbl.postaladmin" /><span></span></td>
			<td  class="iCargoTableHeader" width="12%"><common:message key="mailtracking.mra.defaults.listbillingmatrix.tbl.airline" /><span></span></td>
			<td  class="iCargoTableHeader" width="12%"><common:message key="mailtracking.mra.defaults.listbillingmatrix.tbl.noofbillinglines" /><span></span></td>
			<td  class="iCargoTableHeader" width="12%"><common:message key="mailtracking.mra.defaults.listbillingmatrix.tbl.validfrom" /><span></span></td>
			<td  class="iCargoTableHeader" width="12%"><common:message key="mailtracking.mra.defaults.listbillingmatrix.tbl.validto" /><span></span></td>
			<td  class="iCargoTableHeader" width="12%"><common:message key="mailtracking.mra.defaults.listbillingmatrix.tbl.status" /><span></span></td>
			</tr>
			</thead>
			<tbody>
			<logic:present name="BillingMatrixVOs">
			<logic:iterate id="BillingMatrixVO" name="BillingMatrixVOs" indexId="rowCount" type="com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixVO">
			<tr>
			<td  class="ic-center">
			<input type="checkbox" name="checkboxes" value="<%=String.valueOf(rowCount)%>" onclick="toggleTableHeaderCheckbox('checkboxes',document.forms[1].headChk)" />
			</td>
			<td>
			<logic:present name="BillingMatrixVO" property="billingMatrixId" >
			<common:write name="BillingMatrixVO" property="billingMatrixId" />
			</logic:present>
			<logic:notPresent name="BillingMatrixVO" property="billingMatrixId">
			&nbsp;
			</logic:notPresent>
			</td>
			<td>
			<logic:present name="BillingMatrixVO" property="description" >
			<common:write name="BillingMatrixVO" property="description" />
			</logic:present>
			<logic:notPresent name="BillingMatrixVO" property="description">
			&nbsp;
			</logic:notPresent>
			</td>
			<td>
			<logic:present name="BillingMatrixVO" property="poaCode" >
			<common:write name="BillingMatrixVO" property="poaCode" />
			</logic:present>
			<logic:notPresent name="BillingMatrixVO" property="poaCode">
			&nbsp;
			</logic:notPresent>
			</td>
			<td>
			<logic:present name="BillingMatrixVO" property="airlineCode" >
			<common:write name="BillingMatrixVO" property="airlineCode" />
			</logic:present>
			<logic:notPresent name="BillingMatrixVO" property="airlineCode">
			&nbsp;
			</logic:notPresent>
			</td>
			<td >
			<div align="right">
			<common:write name="BillingMatrixVO" property="totalBillinglines" />
			</div>
			</td>
			<td>
			<logic:present name="BillingMatrixVO" property="validityStartDate" >
			<bean:define id="fromDate" name="BillingMatrixVO" property="validityStartDate" />
			<%  String fromDateStr = TimeConvertor.toStringFormat(((LocalDate)fromDate).toCalendar(),"dd-MMM-yyyy"); %>
			<%=fromDateStr%>
			</logic:present>
			<logic:notPresent name="BillingMatrixVO" property="validityStartDate">
			&nbsp;
			</logic:notPresent>
			</td>
			<td>
			<logic:present name="BillingMatrixVO" property="validityEndDate" >
			<bean:define id="toDate" name="BillingMatrixVO" property="validityEndDate" />
			<%  String toDateStr = TimeConvertor.toStringFormat(((LocalDate)toDate).toCalendar(),"dd-MMM-yyyy"); %>
			<%=toDateStr%>
			</logic:present>
			<logic:notPresent name="BillingMatrixVO" property="validityEndDate">
			&nbsp;
			</logic:notPresent>
			</td>
			<td>
			<logic:present name="BillingMatrixVO" property="billingMatrixStatus" >
			<logic:present name="oneTimeValuesMap" >
			<logic:iterate id="oneTimeValues" name="oneTimeValuesMap">
			<logic:equal name="oneTimeValues" property="key" value="mra.gpabilling.ratestatus" >
			<logic:iterate id="oneTimeVO" name="oneTimeValues" property="value" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
			<logic:equal name="oneTimeVO" property="fieldValue" value="<%=BillingMatrixVO.getBillingMatrixStatus()%>">
			<common:write name="oneTimeVO" property="fieldDescription" />
			</logic:equal>
			</logic:iterate>
			</logic:equal>
			</logic:iterate>
			</logic:present>
			</logic:present>
			<logic:notPresent name="BillingMatrixVO" property="billingMatrixStatus">
			&nbsp;
			</logic:notPresent>
			</td>
			</tr>
			</logic:iterate>
			</logic:present>
			</tbody>
			</table>
			</div>		
		</div>
		</div>
		<div class="ic-foot-container paddR5">
			<div class="ic-button-container">
			<ihtml:nbutton property="btnChangeStatus" accesskey="O" componentID="CMP_MRA_DEFAULTS_LISTBILLINGMATRIX_CHANGESTATUS_BTN" >
					<common:message key="mailtracking.mra.defaults.listbillingmatrix.btn.changestatus" />
				</ihtml:nbutton>
				<ihtml:nbutton property="btnViewBillingLine" accesskey="B" componentID="CMP_MRA_DEFAULTS_LISTBILLINGMATRIX_VIEWBILLINGLINE_BTN" >
					<common:message key="mailtracking.mra.defaults.listbillingmatrix.btn.viewbillingline" />
				</ihtml:nbutton>
				<ihtml:nbutton property="btnClose" accesskey="O" componentID="CMP_MRA_DEFAULTS_LISTBILLINGMATRIX_CLOSE_BTN" >
					<common:message key="mailtracking.mra.defaults.listbillingmatrix.btn.close" />
				</ihtml:nbutton>
			</div>
		</div>
</ihtml:form>
</div>
<!---CONTENT ENDS-->

	
	</body>
</html:html>

	
