<%
response.setHeader("Pragma", "No-cache");
response.setDateHeader("Expires", 0);
response.setHeader("Cache-Control", "no-cache");
%>


<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:
* File Name				:  RunningLoyaltyProgrammeLOV.jsp
* Date					:
* Author(s)				:  A-2122
*************************************************************************/
 --%>
<%@ page language="java" %>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import = "java.util.Calendar" %>
<%@ page import = "com.ibsplc.icargo.framework.util.FormatConverter" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyProgrammeVO" %>

<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.loyalty.RunningLoyaltyProgrammeLovForm" %>

<html:html>
<head>			
	
<title>
<common:message bundle="listRunningLoyaltyLOVResources" key="customermanagement.defaults.runningloyaltyprogrammeLOV.lbl.title" />
</title>
<meta name="decorator" content="popup_panel">
<common:include type="script" src="/js/customermanagement/defaults/RunningLoyaltyProgrammeLOV_Script.jsp"/>
</head>

<body id="bodyStyle">
	
	
<business:sessionBean id="KEY_LOYALTYLIST"
		   moduleName="customermanagement.defaults"
		   screenID="customermanagement.defaults.maintainloyalty"
		   method="get"
		   attribute="runningLoyaltyProgrammeLovVOs"/>

<bean:define id="form" name="runningLoyaltyProgrammeLovForm"  type="com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.loyalty.RunningLoyaltyProgrammeLovForm" toScope="page" />
<div class="iCargoPopUpContent" style="height:390px;"   >
<ihtml:form action="/customermanagement.defaults.lov.screenloadrunningloyaltyproglov.do" styleClass="ic-main-form">


<ihtml:hidden property="companyCode" />
<ihtml:hidden property="displayPage" />
<ihtml:hidden property="lastPageNum" />
<ihtml:hidden property="rowCount" />
<ihtml:hidden property="formNumber"/>
<ihtml:hidden property="textfiledObj" />
<ihtml:hidden property="textfiledDesc" />
<ihtml:hidden property="textfiledFromDate" />
<ihtml:hidden property="textfiledToDate" />


<div class="ic-content-main">
	<span class="ic-page-title ic-display-none">
		 <common:message  key="customermanagement.defaults.runningloyaltyprogrammeLOV.lbl.screentitle" />
	</span>
	<div class="ic-main-container">
		<div class="ic-row" id="listTable">
			<div class="ic-col-100">
				<div class="ic-row">
					<div class="ic-col-75">
						<logic:present name="KEY_LOYALTYLIST">
							<common:paginationTag 
								pageURL="customermanagement.defaults.lov.screenloadrunningloyaltyproglov.do"
								name="KEY_LOYALTYLIST"
								display="label"
								labelStyleClass="iCargoResultsLabel"
								lastPageNum="<%=form.getLastPageNum() %>" />
						</logic:present>
					</div>
					<div class="ic-col-25">
						<div class="ic-button-container">
							<logic:present name="KEY_LOYALTYLIST">
								<common:paginationTag
									pageURL="javascript:submitPage('lastPageNum','displayPage')"
									name="KEY_LOYALTYLIST"
									display="pages"
									linkStyleClass="iCargoLink"
									disabledLinkStyleClass="iCargoLink"
									lastPageNum="<%=form.getLastPageNum()%>"/>
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
										<common:message key="customermanagement.defaults.runningloyaltyprogrammeLOV.lbl.prgname" />
									</td>
									<td width="60%" class="iCargoTableHeadingLeft">
										<common:message key="customermanagement.defaults.runningloyaltyprogrammeLOV.lbl.prgDesc" />
									</td>
								</tr>
							</thead>
							<tbody>
								<logic:present name="KEY_LOYALTYLIST">
									<%int index = 0;%>
									<logic:iterate id="iterator" name="KEY_LOYALTYLIST" indexId="nIndex">
										<bean:define id="name" name="iterator" property="loyaltyProgrammeCode" />
										<tr>
											<%String  desc=((LoyaltyProgrammeVO)iterator).getLoyaltyProgrammeDesc();%>
											<%String fromDateStr="";%>
											<logic:present  name="iterator" property="fromDate">
												<bean:define id="fromDate" name="iterator" 
													property="fromDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
												<% fromDateStr = TimeConvertor.toStringFormat(fromDate.toCalendar(),						TimeConvertor.CALENDAR_DATE_FORMAT);
												%>										
											</logic:present>
											<%String toDateStr="";%>
											<logic:present  name="iterator" property="toDate">
												<bean:define id="toDate" name="iterator" 
													property="toDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
												<% 
												toDateStr = TimeConvertor.toStringFormat(toDate.toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);
												%>										
											</logic:present>				
											<% String value=(String)name+","+(String)desc+","+(String)fromDateStr+","+(String)toDateStr;
											%>
											<td class="iCargoTableTd">
												<input type="checkbox" name="suChecked" value="<%=value%>"/>
											</td>
											<td class="iCargoTableTd">
												<bean:write name="iterator" property="loyaltyProgrammeCode"/>
											</td >
											<td class="iCargoTableTd" >
												<bean:write name="iterator" property="loyaltyProgrammeDesc"/>
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
				<ihtml:nbutton property="btOk" componentID="CMP_CUST_DEFAULTS_LOYALTYPOPUP_OK_BTN"  tabindex="5">
					<common:message key="customermanagement.defaults.runningloyaltyprogrammeLOV.btn.btOk" />
				</ihtml:nbutton>
				<ihtml:nbutton property="btClose" componentID="CMP_CUST_DEFAULTS_LOYALTYPOPUP_CLOSE_BTN"  tabindex="6">
					<common:message key="customermanagement.defaults.runningloyaltyprogrammeLOV.btn.btClose" />
				</ihtml:nbutton>
			</div>
		</div>
	</div>
</div>
</ihtml:form>
</div>

	</body>
</html:html>
