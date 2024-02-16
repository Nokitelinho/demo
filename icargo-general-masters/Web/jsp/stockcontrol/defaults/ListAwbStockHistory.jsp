<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  SKCM - stock Control
* File Name				:  ListAwbStockHistory.jsp
* Date					:  09-JAN-2008
* Author(s)				:  Dhamodaran
*************************************************************************/
 --%>
 <%@ page language="java" %>
 
 <%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
 
 <%@ include file="/jsp/includes/tlds.jsp" %>
 <%@ page import = "com.ibsplc.icargo.framework.util.time.LocalDate" %>
 <%@ page import="org.apache.struts.Globals"%>
 <%@ page import="java.util.Calendar"%>
 <%@ page import="java.util.ArrayList"%>
 <%@ page import="java.util.Iterator"%>
 <%@ page import="java.util.Collection"%>
 <%@ page import="org.apache.struts.action.ActionMessages"%>
 <%@ page import="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ListAwbStockHistoryForm"%>
 <%@ page import = "com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO" %>

 
<html:html>

<head>
		
	
<bean:define id="form"
	name="ListAwbStockHistoryForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ListAwbStockHistoryForm"
	toScope="page" />

<title class="iCargoHeadingLabel"><common:message bundle="listawbstockhistoryresources" key="stockhistory.title"/></title>
<meta name="decorator" content="mainpanelrestyledui">
	<common:include type="script" src="/js/stockcontrol/defaults/ListAwbStockHistory_Script.jsp" />
	<common:include type="script" src="/js/tabbedpane.js"/>
	<common:include type="script" src="/js/utils.js"/>
	<common:include type="script" src="/js/tabs.js"/>

	
</head>

<body id="bodyStyle" >
	
	
	
	
  <%@include file="/jsp/includes/reports/printFrame.jsp" %>

	 <business:sessionBean id="stockRangeHistoryVOs"
		     moduleName="stockcontrol.defaults"
		     screenID="stockcontrol.defaults.listawbstockhistory" 
		     method="get"
		     attribute="PageStockRangeHistoryVOs"/>
		     
	 <business:sessionBean id="oneTimeStockStatus"
	      moduleName="stockcontrol.defaults"
	      screenID="stockcontrol.defaults.listawbstockhistory" 
	      method="get"
      	      attribute="oneTimeStockStatus"/>
      	      
       <business:sessionBean id="oneTimeAwbType"
	      moduleName="stockcontrol.defaults"
	      screenID="stockcontrol.defaults.listawbstockhistory" 
	      method="get"
     	      attribute="oneTimeAwbType"/>
     	      
       <business:sessionBean id="oneTimeStockUtilizationStatus"
      	      moduleName="stockcontrol.defaults"
      	      screenID="stockcontrol.defaults.listawbstockhistory" 
      	      method="get"
     	      attribute="oneTimeStockUtilizationStatus"/>

       <business:sessionBean id="documentTypes"
	      moduleName="stockcontrol.defaults"
	      screenID="stockcontrol.defaults.listawbstockhistory" 
	      method="get"
	      attribute="documentTypes"/>
		  
		<business:sessionBean id="map"
	      moduleName="stockcontrol.defaults"
	      screenID="stockcontrol.defaults.listawbstockhistory"
	      method="get"
	      attribute="map" />

		<business:sessionBean id="partnerAirlines"
	      moduleName="stockcontrol.defaults"
	      screenID="stockcontrol.defaults.listawbstockhistory"
	      method="get"
	      attribute="partnerAirlines"/>




	<%
	 	ListAwbStockHistoryForm listAwbStockHistoryForm = (ListAwbStockHistoryForm)request.getAttribute("ListAwbStockHistoryForm");
 	%>

	<div class="iCargoContent ic-masterbg" style="overflow:auto;width:100%;height:100%;" >
	<ihtml:form action="stockcontrol.defaults.screenloadlistawbstockhistory.do">
	<ihtml:hidden property="onList"/>   
    <html:hidden property="lastPageNum"/>
    <html:hidden property="displayPage"/>
	<html:hidden property="statusFlag" />
	<html:hidden property="navigationMode" />
	<html:hidden property="documentRange"/>
	<html:hidden property="errorFlag"/>
	<html:hidden property="partnerPrefix" />
	<input type="hidden" name="mySearchEnabled" />
	<div class="ic-content-main">
		<div class="ic-head-container" >
			<!--<div class="ic-row">
				<h4><common:message key="stockhistory.ListAwbStockHistory"/></h4>
			</div> -->
			<div class="ic-filter-panel">
				<div class="ic-row"><h4><common:message key="stockhistory.ListAwbStockHistory"/></h4></div>
				<div class="ic-row">
					<div class="ic-input-container">
						<div class="ic-col-20">
							<div class="ic-split-100 ic_inline_chcekbox paddL5" style="padding-top:13px;">
								<ihtml:checkbox property="history"
							componentID="CHK_STOCKCONTROL_DEFAULTS_LISTAWBSTOCKHISTORY_HISTORY"/>
								<label>
									<common:message key="stockhistory.History"/>
								</label>								
							</div>
							
							
							<div id="divid2" style="margin-top:20px;">
							
							<div class="ic-input ic-split-100 ic-label-30">
								<label>
									<common:message key="stockhistory.Status"/>
								</label>
								

									<ihtml:select property="stockStatus"
										componentID="CMB_STOCKCONTROL_DEFAULTS_LISTAWBSTOCKHISTORY_STOCKSTATUS" >
									<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>	
									<logic:present  name="oneTimeStockUtilizationStatus">
										<logic:iterate id="oneTimeStockUtilizationStatus" name="oneTimeStockUtilizationStatus" >
											<html:option value= "<%= ((OneTimeVO)oneTimeStockUtilizationStatus).getFieldValue() %>">
											<%= ((OneTimeVO)oneTimeStockUtilizationStatus).getFieldDescription() %>
											</html:option>
										</logic:iterate>
									</logic:present>
									 </ihtml:select>
								</div>
							</div>
							<div id="divid1" style="display:none"> 
								<div  class="ic-input ic-split-100 ic-label-30">
									<label>
										<common:message key="stockhistory.Status"/>
									</label>
									

								<ihtml:select property="stockStatus"
									componentID="CMB_STOCKCONTROL_DEFAULTS_LISTAWBSTOCKHISTORY_STOCKSTATUS" >

								<logic:present  name="oneTimeStockStatus">
																<ihtml:option value=""></ihtml:option>
									<logic:iterate id="oneTimeStockStatus" name="oneTimeStockStatus" >
									   <logic:notEqual name="oneTimeStockStatus" property ="fieldValue" value="P">
										<html:option value= "<%= ((OneTimeVO)oneTimeStockStatus).getFieldValue() %>">
										<%= ((OneTimeVO)oneTimeStockStatus).getFieldDescription() %>
										</html:option>
									   </logic:notEqual>
									</logic:iterate>
								</logic:present>
								 </ihtml:select>
							</div>
							</div>
							<div class="ic-split-100 ic_inline_chcekbox paddL5 " style="padding-top:13px; margin-top:80px">
								<ihtml:checkbox property="partnerAirline" componentID="CHK_STOCKCONTROL_DEFAULTS_LISTAWBSTOCKHISTORY_PRTARL"/>
								<label>
									<common:message key="stockhistory.partnerairline"/>
								</label>				   		 
							</div>
						</div>
						<div class="ic-col-30">
							<div class="ic-input ic-split-100 ic-label-35" style="margin-left: 10px;">
								<label>
									<common:message key="stockhistory.StockHolder"/>
								</label>
								<ihtml:text property="stockHolderCode"
								componentID="TXT_STOCKCONTROL_DEFAULTS_LISTAWBSTOCKHISTORY_STOCKHOLDERCODE"
								maxlength="12" />
							 <div class= "lovImg"><img height="22" src="<%=request.getContextPath()%>/images/lov.png"width="22" id="stockHolderCodeLov" /></div>
							</div>
							<div class="ic-input ic-split-100 ic-label-35" style="margin-left: 10px;margin-top:10px;">
								<label>
									<common:message key="stockhistory.AwbRangeTo"/>
								</label>	
								<ihtml:text property="rangeTo"
	           					   componentID="TXT_STOCKCONTROL_DEFAULTS_LISTAWBSTOCKHISTORY_RANGETO"
	           					   />
							</div>
							<div class="ic-input ic-split-100 " style="margin-top:20px;margin-left:10px;">
								<logic:present name="map" >
						<bean:define id="maps" name="map" toScope="page" type="java.util.HashMap"/>
								<ibusiness:dynamicoptionlist  collection="maps"
									id="docType"
									firstlistname="docType"
									componentID="TXT_STOCKCONTROL_DEFAULTS_LISTAWB_DYNAMICOPTIONLIST"
									lastlistname="docSubType" firstoptionlabel="Doc. Type"
									lastoptionlabel="Sub Type"
									optionstyleclass="iCargoMediumComboBox"
									labelstyleclass="iCargoLabelRightAligned"
									firstselectedvalue="<%=form.getDocType()%>"
									lastselectedvalue="<%=form.getDocSubType()%>"
									docTypeMandatory="false" 
									subDocTypeMandatory="false" 
									docTypeTitle="doctype.tooltip"
									subDocTypeTitle="subdoctype.tooltip"/>
					</logic:present>
							</div>
							<!--<div class="ic-input ic-split-100 ic-label-35" style="margin-left: 10px;">
								<label>
									<common:message key="stockhistory.partnerairlineawbprefix"/>
								</label>	
								<ihtml:select property="awbPrefix" componentID="CMB_STOCKCONTROL_DEFAULTS_LISTAWBSTOCKHISTORY_PRTARL" disabled='true'>
									<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
									<logic:present name="partnerAirlines">					
										<logic:iterate id="airlineLovVO" name="partnerAirlines" type="com.ibsplc.icargo.business.shared.airline.vo.AirlineLovVO">
											<logic:present name="airlineLovVO" property="airlineNumber">
												<% String value=airlineLovVO.getAirlineNumber()+"-"+airlineLovVO.getAirlineName()+"-"+airlineLovVO.getAirlineIdentifier(); %>
												<ihtml:option value="<%=value%>"><%=airlineLovVO.getAirlineNumber()%></ihtml:option>
											</logic:present>
										</logic:iterate>
									</logic:present>
								</ihtml:select>
							</div> -->
						</div>
						<div class="ic-col-30">
							<div class="ic-input ic-split-100 ic-label-40">
								<label>
									<common:message key="stockhistory.userId"/>
								</label>
								<ihtml:text property="userId" 
								componentID="TXT_STOCKCONTROL_DEFAULTS_LISTAWBSTOCKHISTORY_USRID"
								maxlength="14"/>
							</div>
							<div class="ic-input ic-split-100 ic-mandatory ic-label-40" style="margin-top:10px;">
								<label>
									<common:message key="stockhistory.StartDate"/>
								</label>	
								<ibusiness:calendar property="startDate"
								id="startDate"
								componentID="TXT_STOCKCONTROL_DEFAULTS_LISTAWBSTOCKHISTORY_STARTDATE"
								type="image"
								textStyleClass="iCargoTextFieldMedium"
								value="<%=listAwbStockHistoryForm.getStartDate()%>"
								title="Start Date"
								maxlength="11"/>
							</div>
							<div class="ic-input ic-split-10 ic-label-40">
								<label>&nbsp;
								</label>
							</div>
							<div class="ic-input ic-split-100 ic-label-40">
								<label>
									<common:message key="stockhistory.partnerairline"/>
								</label>	
								<ihtml:text property="airlineName" componentID= "TXT_STOCKCONTROL_DEFAULTS_CREATESTOCK_PRTARL" readonly="true"/>
							</div>
						</div>
						<div class="ic-col-20">
						<div class="ic-input ic-split-100 ic-label-30" style=" !important;">
								<label>
									<common:message key="stockhistory.AwbRange"/>
								</label>
								<ihtml:text property="rangeFrom"
	           					    componentID="TXT_STOCKCONTROL_DEFAULTS_LISTAWBSTOCKHISTORY_RANGEFROM"
	           					   />
							</div>
							
							<div class="ic-input ic-split-100 ic-mandatory ic-label-35" style=" !important;margin-top:10px;"">
								<label>
									<common:message key="stockhistory.EndDate"/>
								</label>	
								<ibusiness:calendar property="endDate"
								id="endDate"
								componentID="TXT_STOCKCONTROL_DEFAULTS_LISTAWBSTOCKHISTORY_ENDDATE"
								type="image"
								textStyleClass="iCargoTextFieldMedium"
								value="<%=listAwbStockHistoryForm.getEndDate()%>"
								title="End Date"
								maxlength="11"/>
							</div>
							<div class="ic-input ic-split-100 ic-label-35">
								<label>&nbsp;
								</label>
							</div>
							<div class="ic-input ic-split-100 ic-label-35">
								<label>
									<common:message key="stockhistory.partnerairlineawbprefix"/>
								</label>	
								<ihtml:select property="awbPrefix" componentID="CMB_STOCKCONTROL_DEFAULTS_LISTAWBSTOCKHISTORY_PRTARL" disabled='true'>
									<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
									<logic:present name="partnerAirlines">					
										<logic:iterate id="airlineLovVO" name="partnerAirlines" type="com.ibsplc.icargo.business.shared.airline.vo.AirlineLovVO">
											<logic:present name="airlineLovVO" property="airlineNumber">
												<% String value=airlineLovVO.getAirlineNumber()+"-"+airlineLovVO.getAirlineName()+"-"+airlineLovVO.getAirlineIdentifier(); %>
												<ihtml:option value="<%=value%>"><%=airlineLovVO.getAirlineNumber()%></ihtml:option>
											</logic:present>
										</logic:iterate>
									</logic:present>
								</ihtml:select>
							</div>				
							<div class="ic-input ic-split-100">
								<div class="ic-button-container">
									<ihtml:nbutton property="btnList" accesskey="L"
										componentID="BTN_STOCKCONTROL_DEFAULTS_LISTAWBSTOCKHISTORY_LIST">
										<common:message key="stockhistory.list"/>
									  </ihtml:nbutton>
										  <ihtml:nbutton property="btnClear" accesskey="C"
											componentID="BTN_STOCKCONTROL_DEFAULTS_LISTAWBSTOCKHISTORY_CLEAR">
											<common:message key="stockhistory.clear"/>
									  </ihtml:nbutton>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="ic-main-container">
		<div class="ic-row">
		<a class="panel upArrow"  collapseFilter="true"  collapseFilterCallbackFun=""  href="#"></a></div>
			<div class="ic-row marginT15" id="listTable">
					<div class="ic-row">
						<div class="ic-col-45">
						<logic:present name="stockRangeHistoryVOs">
						 <bean:define id="pageHistory" name="stockRangeHistoryVOs"/>
						  <logic:present name="pageHistory">


						  <common:paginationTag pageURL="stockcontrol.defaults.listawbstockhistory.do"
						   name="pageHistory"
						   display="label"
						   labelStyleClass="iCargoResultsLabel"
						   lastPageNum="<%=form.getLastPageNum() %>"/>

						 </logic:present>
						  <logic:notPresent name="stockRangeHistoryVOs">
						   &nbsp;
						 </logic:notPresent>				    
						</div>
						<div class="ic-col-55">
							<div class="ic-button-container paddR15">
								<logic:present name="stockRangeHistoryVOs">

								  <common:paginationTag
									pageURL="javascript:submitPage('lastPageNum','displayPage')"
									name="stockRangeHistoryVOs"
									 linkStyleClass="iCargoLink"
										disabledLinkStyleClass="iCargoLink"
									display="pages"
									lastPageNum="<%=form.getLastPageNum()%>"/>
								 </logic:present>
								 <logic:notPresent name="stockRangeHistoryVOs">
									&nbsp;
								 </logic:notPresent>
								</logic:present>							
							</div>
						</div>
					</div>
					<div class="ic-row">
						<div id="AwbStockHistoryDiv" class="tableContainer" style="height:350px">
							<table style="width:100%" id="listAwbStockHistoryTable" class="fixed-header-table">
							   <thead>
								<tr class="iCargoTableHeadingCenter">
									<td  width="12%"><common:message key="stockhistory.fromstockholder" scope="request"/><span></span></td>
									<td  width="12%"><common:message key="stockhistory.tostockholder" scope="request"/><span></span></td>
									<td width="9%"><common:message key="stockhistory.Type" scope="request"/><span></span></td>
									<td width="9%"><common:message key="stockhistory.SubType" scope="request"/><span></span></td>
									<td width="9%"><common:message key="stockhistory.Status" scope="request"/><span></span></td>
									<td width="15%"><common:message key="stockhistory.range" scope="request"/><span></span></td>
									<td width="7%"><common:message key="stockhistory.NoOfDocs" scope="request"/><span></span></td>
									<td width="15%"><common:message key="stockhistory.Date" scope="request"/><span></span></td>
									<td width="12%"><common:message key="stockhistory.remarks" scope="request"/><span></span></td>

								</tr>
							</thead>
							<tbody>
										 
										 
										 
										 
							 <logic:present name="stockRangeHistoryVOs">
							<logic:iterate id="stockRangeHistoryVO" name="stockRangeHistoryVOs" type="com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRangeHistoryVO" indexId="nIndex">
								
									<tr>
										<td class="iCargoTableDataTd">
											<bean:write name="stockRangeHistoryVO" property="fromStockHolderCode" /></td>
										<td class="iCargoTableDataTd">
											<bean:write name="stockRangeHistoryVO" property="toStockHolderCode" /></td>
										<td class="iCargoTableDataTd">
											<bean:write name="stockRangeHistoryVO" property="rangeType" /></td>
										<td class="iCargoTableDataTd">
											<bean:write name="stockRangeHistoryVO" property="documentSubType" /></td>
										<td class="iCargoTableDataTd">
											<bean:write name="stockRangeHistoryVO" property="status"/></td>
										<td class="iCargoTableDataTd">
											<bean:write name="stockRangeHistoryVO" property="awbRange"/></td>
										<td class="iCargoTableDataTd">
											<bean:write name="stockRangeHistoryVO" property="numberOfDocuments"/></td>
										<td class="iCargoTableDataTd">
										
										<% 
											String transactionDate = "";
											if(stockRangeHistoryVO.getTransactionDate()!=null){
												transactionDate=TimeConvertor.toStringFormat(stockRangeHistoryVO.getTransactionDate(),TimeConvertor.CALENDAR_DATE_FORMAT);
											}
										%>
											<%=transactionDate%>
										</td>	
										<td class="iCargoTableDataTd">
											<bean:write name="stockRangeHistoryVO" property="remarks"/></td>							
									</tr>

								
							</logic:iterate>
					</logic:present>

							</tbody>
							</table>
			
						</div>
					</div>
					<div class="ic-row">
						<h4><common:message key="stockhistory.AwbHistory"/></h4>
					</div>
					<div class="ic-row">
						<div class="ic-input-round-border ic-split-50">
							<div class="ic-row">
								<div class="ic-col-100">
									<div class="ic-input ic-split-50">
										<label>
											<common:message key="stockhistory.AwbNumber"/>
										</label>
										<ihtml:text property="awp"
										   componentID="BTN_STOCKCONTROL_DEFAULTS_LISTAWBSTOCKHISTORY_AWBPRF"
											maxlength="3" />
											<ihtml:text property="awb"
										   componentID="BTN_STOCKCONTROL_DEFAULTS_LISTAWBSTOCKHISTORY_MSTDOCNUM"
											maxlength="7" /><!--Modified by A-8146 as part of ICRD-261262-->
									</div>
									<div class="ic-button-container">
										<ihtml:nbutton property="btnDisplay" accesskey="D"
										componentID="BTN_STOCKCONTROL_DEFAULTS_LISTAWBSTOCKHISTORY_DISPLAY">
										<common:message key="stockhistory.display"/>
										</ihtml:nbutton>
									</div>
								</div>
							</div>
						</div>
					</div>
			</div>
		</div>
		<div class="ic-foot-container">
			<div class="ic-row paddR10">
				<div class="ic-button-container">
					<ihtml:nbutton property="btnPrint" accesskey="P"
						componentID="BTN_STOCKCONTROL_DEFAULTS_LISTAWBSTOCKHISTORY_PRINT">
						<common:message key="stockhistory.print"/>
					</ihtml:nbutton>
					<ihtml:nbutton property="btnClose" accesskey="O"
						componentID="BTN_STOCKCONTROL_DEFAULTS_LISTAWBSTOCKHISTORY_CLOSE">
						<common:message key="stockhistory.close"/>
					</ihtml:nbutton>
				</div>
			</div>
		</div>
	</div>

	
   </ihtml:form>
   
  </div>
		
	
				
		
	</body>
</html:html>

