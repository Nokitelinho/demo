<%--/
*************************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  MRA.AIRLINEBILLING, ListFormOne.jsp
* Date					:  18-June-2008
* Author(s)				:  A-3434
*************************************************************************/
 --%>

 <%@ include file="/jsp/includes/tlds.jsp" %>
 <%@ page import="org.apache.struts.Globals"%>
 <%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.outward.ListMailFormOneForm"%>
 <%@ page import="com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.FormOneVO"%>

		
	
<html:html>
<head>
	
	
	
	
	<common:include type="script" src="/js/mail/mra/airlinebilling/outward/ListFormOne_Script.jsp"/>
	<title><common:message key="mra.outwardbilling.title.listform1" bundle="listForm1bundle" /></title>

	<meta name="decorator" content="mainpanel">

	



</head>

<body style="overflow:auto;">
	
	
	
	
<div id="pageDiv" class="iCargoContent" >
<ihtml:form action="/mailtracking.mra.airlinebilling.outward.listform1.onScreenLoad.do">
	 <bean:define
		 id="listFormOneForm"
		 name="ListMailFormOneForm"
		 type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.outward.ListMailFormOneForm"
	 	 toScope="page" />
	<business:sessionBean
			id="formOneVOs"
			moduleName="mra.airlinebilling"
			screenID="mailtracking.mra.airlinebilling.outward.listform1"
			method="get"
			attribute="formOneVOs" />

	<html:hidden name="listFormOneForm" property="selectedValues"  />
	<html:hidden name="listFormOneForm" property="lastPageNum" />
	<html:hidden name="listFormOneForm" property="displayPage" />
	<html:hidden name="listFormOneForm" property="multiselect"/>
	<html:hidden name="listFormOneForm" property="select"/>

<div class="ic-content-main">
	<span class="ic-page-title"><common:message key="mra.outwardbilling.listform1.pagetitle"/></span>
		<div class="ic-head-container">
			<div class="ic-filter-panel">
				<div class="ic-input-container">
					<div class="ic-row ic-label-40">
						<div class="ic-input ic-mandatory ic-split-25">
							<label><common:message key="mra.outwardbilling.listform1.clearanceperiod"/></label>
							<ihtml:text componentID="CMP_MRA_AIRLINEBILLING_OUTWARD_CLEARANCEPERIOD" property="clearancePeriod" maxlength="10" />
							<img name="clearancePeriodLOV" src="<%=request.getContextPath()%>/images/lov.gif"/>
						</div>
						<div class="ic-input ic-split-25" >
							<label><common:message key="mra.outwardbilling.listform1.airlinecode"/></label>
							<ihtml:text componentID="CMP_MRA_AIRLINEBILLING_OUTWARD_AIRLINECODE" property="airlineCodeFilterField" maxlength="3" />
							<img name="airlinecodelov" id="airlinecodelov" height="16" src="<%=request.getContextPath()%>/images/lov.gif" width="16" />
						</div>
						<div class="ic-input ic-split-25" >
						<div id="ListFormOneParent">
							<label><common:message key="mra.outwardbilling.listform1.airlineno"/></label>
							<ihtml:text componentID="CMP_MRA_AIRLINEBILLING_OUTWARD_AIRLINENO" property="airlineNumber" maxlength="3" />
							<img name="airlinenolov" id="airlinenolov" height="16" src="<%=request.getContextPath()%>/images/lov.gif" width="16" />
						</div>
						</div>
						<div class="ic-input ic-split-25" >
								<div class="ic-button-container">				
							<ihtml:nbutton property="btnList" componentID="CMP_MRA_AIRLINEBILLING_OUTWARD_LIST" >
								  <common:message key="mra.outwardbilling.listform1.button.list" />
								</ihtml:nbutton>
								<ihtml:nbutton property="btnClear" componentID="CMP_MRA_AIRLINEBILLING_OUTWARD_CLEAR" >
								  <common:message key="mra.outwardbilling.listform1.button.clear" />
							</ihtml:nbutton>
							 </div>
						</div>
					</div>
				</div>
			 </div>
		</div>
		<div class="ic-main-container">
			<div class="ic-row">
				<h4><common:message key="mra.outwardbilling.listform1.form1details" /></h4>
			</div>
				  
			
							<logic:present name="formOneVOs">
							<logic:present name="listFormOneForm" property="lastPageNum">
							<bean:define name="listFormOneForm" property="lastPageNum" id="lastPageNum"/>
						<div class="ic-row">
							   <common:paginationTag
							   pageURL="mra.airlinebilling.outward.listformones"
							   name="formOneVOs"
							   display="label"
							   labelStyleClass="iCargoResultsLabel"
							   lastPageNum="<%=(String)lastPageNum %>" />
						    <div class="ic-button-container">
							   <common:paginationTag
							   pageURL="javascript:submitPage1('lastPageNum','displayPage')"
							   linkStyleClass="iCargoLink"
							   disabledLinkStyleClass="iCargoLink"
							   name="formOneVOs"
							   display="pages"
							   lastPageNum="<%=(String)lastPageNum%>"
							   exportToExcel="true"
							   exportTableId="Form-1Details"
							   exportAction="mra.airlinebilling.outward.listformones.do"/>
						   </div>
						   </div>
						   </logic:present>
						   </logic:present>
						<logic:notPresent name="formOneVOs">
						
						   &nbsp;
						   
						</logic:notPresent>
						  
							<logic:present name="listFormOneForm" property="selectedValues">
							<bean:define name="listFormOneForm" property="selectedValues" id="strSelectedValues" />
							<bean:define name="listFormOneForm" property="pageNumber" id="pageNumber" />
							<div class="ic-row">
							<div class="tableContainer" id="div1" style="height:660px">
								<table  class="fixed-header-table" id="Form-1Details"   >
								  <thead>
								   <tr>
									<td class="iCargoTableHeader" width="5%" ><input type="checkbox" name="allCheck" /></td>

									<td class="iCargoTableHeader" width="15%" >
										<common:message key="mra.outwardbilling.listform1.toairline"/>
									</td>

									<td class="iCargoTableHeader" width="18%">
										<common:message key="mra.outwardbilling.listform1.totalamountinlistingcurrency"/>
									</td>


									<td class="iCargoTableHeader" width="15%">
										<common:message key="mra.outwardbilling.listform1.exchangerate"/>
									</td>


									<td class="iCargoTableHeader" width="15%">
										<common:message key="mra.outwardbilling.listform1.billingcurrency"/>
									</td>


									<td class="iCargoTableHeader" width="17%">
										<common:message key="mra.outwardbilling.listform1.totalamountinbillingcurrency"/>
									</td>

									<td class="iCargoTableHeader" width="15%">
										<common:message key="mra.outwardbilling.listform1.class"/>
									</td>
								</tr>
								</thead>
								
								<tbody>
									<logic:present name="formOneVOs">
									<logic:iterate id = "formOneVoItr" name="formOneVOs" indexId="rowCount" scope="page" >
										
											<tr>
												 <td class="ic-center">
												  <html:checkbox  property="selectCheckBox" value="<%=String.valueOf(rowCount)%>" />
												 </td>
												 <td class="iCargoTableDataTd" >
												 
												  <common:write name="formOneVoItr" property="airlineCode"/>
												  <ihtml:hidden name="formOneVoItr" property="airlineCode" />
												  
												 </td>

												  <td class="iCargoTableDataTd">
												  
												  <bean:define id ="listingTotalAmt" name="formOneVoItr" property="listingTotalAmt" />
													<ibusiness:moneyDisplay showCurrencySymbol="false" name="formOneVoItr" property="listingTotalAmt"/>
												  
												  </td>
												 <td class="iCargoTableDataTd">
												 
												  <common:write name="formOneVoItr" property="exchangeRateBillingCurrency" format="####.00000"/>
												  
												 </td>
												 <td class="iCargoTableDataTd">
												 
												  <common:write name="formOneVoItr" property="billingCurrency"/>
												   
												 </td>

												 <td class="iCargoTableDataTd">
												 
												   <bean:define id ="billingTotalAmt" name="formOneVoItr" property="billingTotalAmt" />
													<ibusiness:moneyDisplay showCurrencySymbol="false" name="formOneVoItr"  property="billingTotalAmt"/>
												 
												  </td>
												 <td class="iCargoTableDataTd">
												 
												  <common:write name="formOneVoItr" property="classType"/>
												  <ihtml:hidden name="formOneVoItr" property="classType" />
												   
												 </td>
										   </tr>
										   
									</logic:iterate>
									</logic:present>

								</tbody>
								</table>
							 </div>
							 </div>
							 </logic:present>
						   
						  </div> 
							<div class="ic-foot-container">
								<div class="ic-row">
									<div class="ic-button-container">
										<ihtml:nbutton property="btnView" componentID="CMP_MRA_AIRLINEBILLING_OUTWARD_VIEW" >
											  <common:message key="mra.outwardbilling.listform1.button.view" />
										</ihtml:nbutton>
										<ihtml:nbutton property="btnClose" componentID="CMP_MRA_AIRLINEBILLING_OUTWARD_CLOSE" >
											<common:message key="mra.outwardbilling.listform1.button.close" />
										</ihtml:nbutton>
									</div>
								</div>
							</div>
	</div>
</ihtml:form>
</div>

	</body>
</html:html>

