<%--
* Project	 		: iCargo
* Module Code & Name: STK & StockControl
* File Name			: ViewRequest.jsp
* Date				: 30-09-2015
* Author(s)			: A-5153
 --%>
 
<%@ include file="/jsp/includes/tlds.jsp" %>



<html:html>
	<head>
		
	
		<bean:define id="form" name="ViewStockRequestForm" toScope="page" type="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ViewStockRequestForm"/>

		<title><common:message bundle="viewrequestresources" key="viewstockrequest.page.title"/></title>
		<meta name="decorator" content="popuppanelrestyledui">
		
		<common:include type="script" src="/js/stockcontrol/defaults/cto/ViewRequest_Script.jsp"/>
	</head>
<body style="overflow:auto;">
	
<ibusiness:sessionBean id="requestValues" moduleName="stockcontrol.defaults" screenID="stockcontrol.defaults.cto.viewstockrequest" method="get" attribute="requestValues" />


<div class="iCargoPopUpContent" style="overflow:auto;">
	<ihtml:form action="/stockcontrol.defaults.cto.viewstockrequest.do" styleClass="ic-main-form">
		<div class="ic-content-main">
			<span class="ic-page-title ic-display-none">
				<common:message key="viewstockrequest.title"/>
			</span>
			<div class="ic-head-container">
				<div class="ic-input-container ic-input-round-border">
					<div class="ic-row">
						<div class="ic-input ic-split-30 ic-label-40">
							<label>
								<common:message key="stockcontrol.defaults.viewstockrequest.airline"/>
							</label>
							<ihtml:text property="airline" componentID="CMP_Stock_Defaults_ViewRequest_Airline" readonly="true"/>
						</div>
						<div class="ic-input ic-split-35 ic-label-40">
							<label>
								<common:message key="stockcontrol.defaults.viewstockrequest.doctype"/>
							</label>
							<ihtml:text property="documentType" componentID="CMP_Stock_Defaults_ViewRequest_DocType" readonly="true"/>
						</div>
						<div class="ic-input ic-split-35 ic-label-40">
							<label>
								<common:message key="stockcontrol.defaults.viewstockrequest.docsubtype"/>
							</label>
							<ihtml:text property="docSubType" componentID="CMP_Stock_Defaults_ViewRequest_DocSubType" readonly="true"/>
						</div>
					</div>
				</div>
			</div>
			<div class="ic-main-container">
				<div class="ic-input-container">
					<div class="ic-row">
					
	<div class="tableContainer" id="div1" style="width:100%; height:200px; overflow:auto;"> 
		<table class="fixed-header-table" id="viewRequestTab">
			<thead>
				<tr class="iCargoTableHeadingLeft"> 
					<td class="iCargoTableHeaderLabel" style="width:35%">
						<common:message key="stockcontrol.defaults.viewstockrequest.mode"/></td>                                            
					<td class="iCargoTableHeaderLabel" style="width:65%">
						<common:message key="stockcontrol.defaults.viewstockrequest.content"/></td>
				</tr>
			</thead>
			<tbody>
				<logic:present name="requestValues">
					<logic:iterate id="requestValue" name="requestValues" type="com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestForOALVO" indexId="nIndex">
						<tr> 
							<td class="iCargoTableDataTd">
								<bean:write name="requestValue" property="modeOfCommunication"/>
							</td>
							<td class="iCargoTableDataTd">
								<bean:write name="requestValue" property="content"/>
							</td>
						</tr>
					</logic:iterate>
				</logic:present>                                         
			</tbody>
		</table>
	</div>				
					
					</div>
				</div>
			</div>
			<div class="ic-foot-container">
				<div class="ic-row">
					<div class="ic-button-container">
						<ihtml:button property="btClose" componentID="CMP_Stock_Defaults_ViewRequest_Close">
							<common:message key="stockcontrol.defaults.viewstockrequest.close"/>
						</ihtml:button>
					</div>
				</div>
			</div>
		</div>
	</ihtml:form>
</div>
		

	</body>
</html:html>



