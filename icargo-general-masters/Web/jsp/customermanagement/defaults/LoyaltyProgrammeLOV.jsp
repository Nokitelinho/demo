<%
response.setHeader("Pragma", "No-cache");
response.setDateHeader("Expires", 0);
response.setHeader("Cache-Control", "no-cache");
%>


<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:
* File Name				:  LoyaltyProgrammeLOV.jsp
* Date					:
* Author(s)				:  A-1862
*************************************************************************/
 --%>
<%@ page language="java" %>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import = "java.util.Calendar" %>
<%@ page import = "com.ibsplc.icargo.framework.util.FormatConverter" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyProgrammeVO" %>

<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.loyalty.LoyaltyProgrammeLovForm" %>

<html:html>
<head>
	
	<title>
		<common:message bundle="listLoyaltyLOV" key="customermanagement.defaults.loyaltyprogrammeLOV" />
	</title>
	<meta name="decorator" content="popup_panel">
	<common:include type="script" src="/js/customermanagement/defaults/LoyaltyProgrammeLOV_Script.jsp"/>
</head>

<body id="bodyStyle">
	
<business:sessionBean id="CollectionCharterRefNoLOVs"
		   moduleName="customermanagement.defaults"
		   screenID="customermanagement.defaults.maintainloyalty"
		   method="get"
		   attribute="loyaltyProgrammeLovVOs"/>

<bean:define id="form" name="loyaltyProgrammeLovForm"  type="com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.loyalty.LoyaltyProgrammeLovForm" toScope="page" />
<div class="iCargoPopUpContent" style="overflow:auto;height:410px;"   >
<ihtml:form action="/customermanagement.defaults.lov.screenloadloyaltyproglov.do" styleClass="ic-main-form">


<ihtml:hidden property="companyCode" />

<ihtml:hidden property="displayPage" />
<ihtml:hidden property="lastPageNum" />
<ihtml:hidden property="rowCount" />
<ihtml:hidden property="formNumber"/>
<ihtml:hidden property="textfiledObj" />
<ihtml:hidden property="textfiledDesc" />
<ihtml:hidden property="textfiledFromDate" />
<ihtml:hidden property="textfiledToDate" />

<bean:define name="form" property="textfiledObj" id="textfiledObj" />
<bean:define name="form" property="formNumber" id="formNumber" />

<div class="ic-content-main">
	<span class="ic-page-title ic-display-none">
		<common:message bundle="listLoyaltyLOV" key="customermanagement.defaults.loyaltyprogrammeLOV" />
	</span>
	<div class="ic-head-container">
		<div class="ic-filter-panel">
			<div class="ic-row">
				<div class="ic-col-100">	
					<div class="ic-input-container">
						<div class="ic-row">
							<div class="ic-input ic-split-40">
								<label class="ic-label-25">
									<common:message bundle="listLoyaltyLOV" key="customermanagement.defaults.loyaltyprogrammename" />
								</label>
								<ihtml:text  componentID="CMP_CUST_DEFAULTS_POPUP_LOYALTYNAME" property="loyaltyProgramName"  />
							</div>
							<div class="ic-input ic-split-40">
								<label class="ic-label-25">
									<common:message bundle="listLoyaltyLOV" key="customermanagement.defaults.loyaltyprogrammeDescription" />
								</label>
								<ihtml:text componentID="CMP_CUST_DEFAULTS_POPUP_LOYALTYDESCRIPTION" property="loyaltyProgramDescription"  />
							</div>
							<div class="ic-input ic-split-20">
								<div class="ic-button-container">
									<ihtml:nbutton property="btnList" accesskey="L" componentID="BTN_CUSTOMERMANAGEMENT_DEFAULTS_LISTCUST_LIST">
										<common:message key="customermanagement.defaults.listcustomer.btn.list" scope="request"/>
									</ihtml:nbutton>
									<ihtml:nbutton property="btnClear" accesskey="C" componentID="BTN_CUSTOMERMANAGEMENT_DEFAULTS_LISTCUST_CLEAR">
										<common:message key="customermanagement.defaults.listcustomer.btn.clear" scope="request"/>
									</ihtml:nbutton>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="ic-main-container">
		<div class="ic-row" id="listTable">
			<div class="ic-col-100">
				<div class="ic-row">
					<div class="ic-col-75">
						<logic:present name="CollectionCharterRefNoLOVs">
							<common:paginationTag
								pageURL="javascript:submitList('lastPageNum','displayPage')"
								name="CollectionCharterRefNoLOVs"
								display="label"
								labelStyleClass="iCargoResultsLabel"
								lastPageNum="<%=form.getLastPageNum() %>" />
						</logic:present>
					</div>
					<div class="ic-col-25">
						<div class="ic-button-container">
							<logic:present name="CollectionCharterRefNoLOVs">
								<common:paginationTag
									pageURL="javascript:submitList('lastPageNum','displayPage')"
									name="CollectionCharterRefNoLOVs"
									display="pages"
									linkStyleClass="iCargoLink"
									disabledLinkStyleClass="iCargoLink"
									lastPageNum="<%=form.getLastPageNum() %>"/>
							</logic:present>
						</div>
					</div>
				</div>
				<div class="ic-row">
					<div id="div1" class="tableContainer"  style="height:200px;width:100%;">
						<table class="fixed-header-table" style="width:100%">
							<thead>
								<tr>
									<td width="5%" class="iCargoTableHeadingCenter">
										&nbsp;
									</td>
									<td width="35%" class="iCargoTableHeadingLeft">
										<common:message key="customermanagement.defaults.lbl.prgname" />
									</td>
									<td width="60%" class="iCargoTableHeadingLeft">
										<common:message key="customermanagement.defaults.lbl.prgDesc" />
									</td>
								</tr>
							</thead>
							<tbody>
								<logic:present name="CollectionCharterRefNoLOVs">
									<%int index = 0;%>
									<logic:iterate id="collCharterreferenceNoLovVO" name="CollectionCharterRefNoLOVs" indexId="nIndex">
										<bean:define id="name" name="collCharterreferenceNoLovVO" property="loyaltyProgrammeCode" />
										<a href="#" ondblclick="setLoyalityLovOnDblClick('<%=textfiledObj%>','<%=((LoyaltyProgrammeVO)collCharterreferenceNoLovVO).getLoyaltyProgrammeCode()%>','<%=formNumber%>')"/>
										<tr>
											<a  href="#" ondblclick="setValueOnDoubleClickForLoyaltyPgm()"/>
											<%String  desc=((LoyaltyProgrammeVO)collCharterreferenceNoLovVO).getLoyaltyProgrammeDesc();%>
											<%String fromDateStr="";%>
											<logic:present  name="collCharterreferenceNoLovVO" property="fromDate">
												<bean:define id="fromDate" name="collCharterreferenceNoLovVO" 
													property="fromDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
											<%fromDateStr=TimeConvertor.toStringFormat(fromDate.toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);
											%>
											</logic:present>
											<%String toDateStr="";%>
											<logic:present  name="collCharterreferenceNoLovVO" property="toDate">
												<bean:define id="toDate" name="collCharterreferenceNoLovVO" 
													property="toDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
											<% toDateStr=TimeConvertor.toStringFormat(toDate.toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);
											%>
											</logic:present>
											<% String value=(String)name+","+(String)desc+","+(String)fromDateStr+","+(String)toDateStr;
											%>
											<td class="iCargoTableTd">
												<input type="checkbox" name="suChecked" value="<%=value%>"/>
											</td>
											<td class="iCargoTableTd">
												<bean:write name="collCharterreferenceNoLovVO" property="loyaltyProgrammeCode"/>
											</td >
											<td class="iCargoTableTd" >
												<bean:write name="collCharterreferenceNoLovVO" property="loyaltyProgrammeDesc"/>
											</td>
										</tr>
										<%index++;%>
									</logic:iterate>
								</logic:present>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="ic-foot-container">
		<div class="ic-row">
			<div class="ic-button-container">       
				<ihtml:button property="btOk" componentID="CMP_CUST_DEFAULTS_POPUP_OK_BTN"  tabindex="5">
					<common:message key="customermanagement.defaults.btn.btok" />
				</ihtml:button>
				<ihtml:button property="btClose" componentID="CMP_CUST_DEFAULTS_POPUP_CLOSE_BTN"  tabindex="6">
					<common:message key="customermanagement.defaults.btn.btclose" />
				</ihtml:button>
			</div>
		</div>
	</div>
</div>
</ihtml:form>
</div>

	</body>
</html:html>
