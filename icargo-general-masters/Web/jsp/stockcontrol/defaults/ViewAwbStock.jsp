<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  IN - Inventory Control
* File Name				:  ViewRange.jsp
* Date					:  21-Jan-2008
* Author(s)				:  Dhamodaran

*************************************************************************/
 --%>
<%@ page language="java" %>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import = "com.ibsplc.icargo.framework.util.time.LocalDate" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Collection"%>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="org.apache.struts.action.ActionMessages"%>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ViewAwbStockForm" %>
<%@ page import = "com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO" %>

 		

<html:html>
<head>
		
			
	
		


<bean:define id="form"
	name="ViewAwbStockForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ViewAwbStockForm"
	toScope="page" />

<title><common:message bundle="<%=form.getBundle()%>" key="viewawbstock.title"/></title>
<meta name="decorator" content="popuppanelrestyledui">
<common:include type="script" src="/js/stockcontrol/defaults/ViewAwbStock_Script.jsp" />

</head>
<body >
	
	
	
 <business:sessionBean id="stockRangeHistoryVOs"
		     moduleName="stockcontrol.defaults"
		     screenID="stockcontrol.defaults.viewawbstock" 
		     method="get"
		     attribute="StockRangeHistoryVOs"/>
		     
		     
 <business:sessionBean id="oneTimeStockUtilizationStatus"
      	      moduleName="stockcontrol.defaults"
      	      screenID="stockcontrol.defaults.listawbstockhistory" 
      	      method="get"
     	      attribute="oneTimeStockUtilizationStatus"/>

	
 <business:sessionBean id="oneTimeStockStatus"
	      moduleName="stockcontrol.defaults"
	      screenID="stockcontrol.defaults.listawbstockhistory" 
	      method="get"
      	      attribute="oneTimeStockStatus"/>
	
 <div id="mainDiv" class="iCargoPopUpContent" style="width:100%;height:290px"> 
<ihtml:form action="/stockcontrol.defaults.screenloadviewawbstock.do" styleClass="ic-main-form">

<div class="ic-content-main">
	<span class="ic-page-title ic-display-none">
    	<common:message key="viewawbstock.AwbHistory"/>
	</span>			
	<div class="ic-main-container">
		<div class="ic-row">
			<div class="ic-col-100">
				<div class="ic-input-container">
					<div class="ic-input ic-split-100">
						
						<ibusiness:awb awpProperty="awp"
						componentID="TXT_STOCKCONTROL_DEFAULTS_VIEWAWBSTOCK_AWBNUMBER"
						id="awb"
						awbProperty="awb"
						readonly="true"
						isCheckDigitMod="false"/>
					</div>
				</div>
			</div>
		</div>
		<div class="ic-row">
			<div id="stockDetailsTableDiv" class="tableContainer" style="height:210px;">
				<table id="DisplayAwbStockTable"  class="fixed-header-table" >
				   <thead>
						<tr class="iCargoTableHeadingCenter">
							<td width="20%"><common:message key="viewawbstock.Date" scope="request"/><span></span></td>
							<td width="20%"><common:message key="viewawbstock.Status" scope="request"/><span></span></td>
							<td width="20%"><common:message key="viewawbstock.AwbRangeFrom" scope="request"/><span></span></td>
							<td width="20%"><common:message key="viewawbstock.AwbRangeTo" scope="request"/><span></span></td>
							<td width="20%"><common:message key="viewawbstock.StockBy" scope="request"/><span></span></td>

						</tr> 
					</thead>
					<tbody>
			    	    <business:sessionBean id="stockRangeHistoryVOs"
					   moduleName="stockcontrol.defaults"
					   screenID="stockcontrol.defaults.viewawbstock" 
					   method="get"
					   attribute="StockRangeHistoryVOs"/>
							    		     



					<logic:present name="stockRangeHistoryVOs">
						<logic:iterate id="stockRangeHistoryVO" name="stockRangeHistoryVOs" type="com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRangeHistoryVO" indexId="nIndex">
						    
								<tr>
									<td class="iCargoTableDataTd">																			
										<bean:write name="stockRangeHistoryVO" property="transDateStr"/></td>										
									<td class="iCargoTableDataTd">									
										<bean:write name="stockRangeHistoryVO" property="status"/></td>
									<td class="iCargoTableDataTd">
										<bean:write name="stockRangeHistoryVO" property="fromStockHolderCode"/></td>
									<td class="iCargoTableDataTd">
										<bean:write name="stockRangeHistoryVO" property="toStockHolderCode" /></td>
									<td class="iCargoTableDataTd">
										<bean:write name="stockRangeHistoryVO" property="lastUpdateUser" /></td>
																		
								</tr>

						   
						</logic:iterate>
					</logic:present>
				
					</tbody>			   
				</table>
			</div>
		</div>
	</div>
	<div class="ic-foot-container">
		<div class="ic-row">
			<div class="ic-button-container">
				<ihtml:button property="btnClose"
          		componentID="BTN_STOCKCONTROL_DEFAULTS_VIEWAWBSTOCK_CLOSE">
          		<common:message key="viewawbstock.close"/>
				</ihtml:button>
			</div>
		</div>
	</div>
</div>

    
       
	  
          	


</ihtml:form>
</div>   
	</body>
</html:html>

