<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  Mailtracking
* File Name				:  PartnerCarriers.jsp
* Date					:  11-FEB-2016
* Author(s)				:  A-5219
*************************************************************************/
 --%>


 <%@ page language="java" %>
 <%@ include file="/jsp/includes/tlds.jsp"%>
 <%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.operations.PartnerCarriersForm"%>
 <%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.PartnerCarrierVO"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.ArrayList"%>

	 <html:html>

 <head>
 	<title><common:message bundle="partnerCarrierResources" key="mailtracking.defaults.partnercarrier.lbl.title" /></title>
 	<meta name="decorator" content="mainpanelrestyledui">
 	<common:include type="script" src="/js/mail/operations/PartnerCarrier_Script.jsp"/>
 </head>

 <body class="ic-center" style="width:46%;">

	

	<bean:define id="form"
		 name="PartnerCarriersForm"
		 type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.PartnerCarriersForm"
		 toScope="page" />

	<business:sessionBean id="partnerCarrierVOs"
		 moduleName="mail.operations"
		 screenID="mailtracking.defaults.partnercarriers"
		 method="get"
		 attribute="partnerCarrierVOs"/>

	<business:sessionBean id="airport"
		 moduleName="mail.operations"
		 screenID="mailtracking.defaults.partnercarriers"
		 method="get"
		 attribute="airport"/>

	<div id="mainDiv"  class="iCargoContent" >

		<ihtml:form action="/mailtracking.defaults.partnercarrier.screenload.do">

			<ihtml:hidden property="disableSave" />
			<ihtml:hidden property="viewBillingLine" />
			<ihtml:hidden property="hiddenpartnerCarrierCode" />
			<ihtml:hidden property="hiddenpartnerCarrierName" />
			
			<div class="ic-content-main">
			
				<div class="ic-head-container">
					<div class="ic-row">
						<div class="ic-input-container"><h4>
							<common:message key="mailtracking.defaults.partnercarrier.lbl.search" /></h4>
						</div>
					</div>
					<div class="ic-filter-panel">
							<div class ="ic-row">
							<div class="ic-input ic-split-50 ic-mandatory">
								<label><common:message key="mailtracking.defaults.partnercarrier.lbl.airport" /></label>
								<logic:present name="airport">
									<bean:define id="airprt" name="airport" toScope="request" />
									<ihtml:text property="airport" value="<%=(String)airprt%>" componentID="TXT_MAILTRACKING_DEFAULTS_MAILSUBCLASSMASTER_ARP" maxlength="4"/>
								</logic:present>
								<logic:notPresent name="airport">
									<ihtml:text property="airport" componentID="TXT_MAILTRACKING_DEFAULTS_MAILSUBCLASSMASTER_ARP" maxlength="4"/>
								</logic:notPresent>
								<div class="lovImg"><img name="airportLOV" id="airportLOV" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" value="lov"/></div>
							</div>
							
							<div class="ic-input ic-split-50">
								<div class="ic-button-container">
									<ihtml:nbutton property="btList" componentID="BUT_MAILTRACKING_DEFAULTS_PARTNERCARRIER_LIST" accesskey="L">
										<common:message key="mailtracking.defaults.partnercarrier.btn.list" />
									</ihtml:nbutton>
									<ihtml:nbutton property="btClear" componentID="BUT_MAILTRACKING_DEFAULTS_PARTNERCARRIER_CLEAR" accesskey="C">
										<common:message key="mailtracking.defaults.partnercarrier.btn.clear" />
									</ihtml:nbutton>
								</div>
							</div>	
						</div>
					
					</div>
				</div>
				
				<div class="ic-main-container">
					<div class="ic-row">
							<h3>
								<common:message key="mailtracking.defaults.partnercarrier.lbl.details" />
							</h3>
							<div class="ic-button-container ic-pad-5">
								<a id="addLink" name="addLink" class="iCargoLink" href="#" value="add">
									<common:message key="mailtracking.defaults.partnercarrier.lbl.add" />
								</a>
								|
								<a id="deleteLink" name="deleteLink" class="iCargoLink" href="#" value="delete">
									<common:message key="mailtracking.defaults.partnercarrier.lbl.delete"/>
								</a>
						</div>
					</div>
					<div class="ic-row ic-pad-5">
					<div class="tableContainer " id="div1"  style="height:660px;">
						<table  class="fixed-header-table" >
							<thead>
								<tr class="ic-th-all">
									<th style="width: 5%" />
									<th style="width: 48%" />
									<th style="width: 48%" />
								</tr>	
								<tr>
									<td class="iCargoTableHeaderLabel">
										<input type="checkbox" name="checkAll" value="checkbox">
									</td>
									<td class="iCargoTableHeaderLabel">
										<common:message key="mailtracking.defaults.partnercarrier.lbl.code"/>
									</td>
									<td class="iCargoTableHeaderLabel">
										<common:message key="mailtracking.defaults.partnercarrier.lbl.description"/>
									</td>
								</tr>
						</thead>
						 <tbody id="partnerTableBody">
							<logic:present name="partnerCarrierVOs">
							<logic:iterate id ="partnerCarrierVO" name="partnerCarrierVOs" type="PartnerCarrierVO" indexId="rowCount">
							<common:rowColorTag index="rowCount">
							   <tr  style=background:'<%=color%>'>

									<% String primaryKey=String.valueOf(rowCount);%>
									<% Collection<String> selectedrows = new ArrayList<String>(); %>
										 <logic:present name="form" property="rowId" >
											<%
											String[] selectedRows = form.getRowId();
											for (int j = 0; j < selectedRows.length; j++) {
												selectedrows.add(selectedRows[j]);
											}
											%>
										</logic:present>

									<td class="ic-center">
										<%
											if(selectedrows.contains(primaryKey)){
										%>

											<input type="checkbox" name="rowId" value="<%=primaryKey%>" checked>
										<%
											}
											else{
										%>
											<input type="checkbox" name="rowId" value="<%=primaryKey%>" />

										<%
											}
										%>
									<%--<input type="checkbox" name="rowId" value="<%=String.valueOf(rowCount)%>"/>--%>
									</td>
									 <logic:present name="partnerCarrierVO" property="operationFlag">
									<bean:define id="operationFlag" name="partnerCarrierVO" property="operationFlag" toScope="request" />
									<logic:notEqual name="partnerCarrierVO" property="operationFlag" value="D">

									<td class="iCargoTableDataTd">
									<logic:equal name="partnerCarrierVO" property="operationFlag" value="I">
										<ihtml:text property="partnerCarrierCode" name="partnerCarrierVO" componentID="TXT_MAILTRACKING_DEFAULTS_PARTNERCARRIER_CODE" maxlength="3" style="width:35px"/>
										<div class="lovImgTbl valignT"><img name="carrierLOV" id="carrierLOV<%=rowCount%>" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" /></div>
									</logic:equal>
									<logic:notEqual name="partnerCarrierVO" property="operationFlag" value="I">
										<ihtml:text property="partnerCarrierCode" name="partnerCarrierVO" readonly="true" componentID="TXT_MAILTRACKING_DEFAULTS_PARTNERCARRIER_CODE" maxlength="3" style="width:35px"/>
									</logic:notEqual>
								   </td>
								   <td class="iCargoTableDataTd">
									<ihtml:text property="partnerCarrierName" name="partnerCarrierVO" componentID="TXT_MAILTRACKING_DEFAULTS_PARTNERCARRIER_DESC" style="width:230px"/>
								   </td>
								</logic:notEqual>
								<logic:equal name="partnerCarrierVO" property="operationFlag" value="D">
									<ihtml:hidden property="partnerCarrierCode" name="partnerCarrierVO"/>
									<ihtml:hidden property="partnerCarrierName" name="partnerCarrierVO"/>
								</logic:equal>

									<ihtml:hidden property="operationFlag" value="<%=((String)operationFlag)%>" />
								</logic:present>
								<logic:notPresent name="partnerCarrierVO" property="operationFlag">
									<td class="iCargoTableDataTd">
										<ihtml:text property="partnerCarrierCode" name="partnerCarrierVO" readonly="true" componentID="TXT_MAILTRACKING_DEFAULTS_PARTNERCARRIER_CODE" maxlength="3" style="width:35px"/>
									</td>
									<td class="iCargoTableDataTd">
										<ihtml:text indexId="rowCount" property="partnerCarrierName" name="partnerCarrierVO" componentID="TXT_MAILTRACKING_DEFAULTS_PARTNERCARRIER_DESC" style="width:230px"/>
									</td>
									<ihtml:hidden property="operationFlag" value="N" />
								</logic:notPresent>
							</tr>
							 </common:rowColorTag>
						   </logic:iterate>
						 </logic:present>


						<!-- templateRow -->

						<tr template="true" id="partnerTemplateRow" style="display:none">

							<ihtml:hidden property="operationFlag" value="NOOP" />

							<td style="text-align:center"  align="center" class="iCargoTableDataTd"><input type="checkbox" name="rowId"></td>

							<td class="iCargoTableDataTd">
							   <ihtml:text property="partnerCarrierCode" value=""  componentID="TXT_MAILTRACKING_DEFAULTS_PARTNERCARRIER_CODE" maxlength="3" style="width:35px"/>
							  <div class="lovImgTbl valignT"> <img name="carrierLOV" id="carrierLOV" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16"/></div>
							</td>

							<td class="iCargoTableDataTd"><ihtml:text property="partnerCarrierName" value="" componentID="TXT_MAILTRACKING_DEFAULTS_PARTNERCARRIER_DESC" style="width:230px" /></td>

						</tr>

						<!--template row ends-->


						</tbody>
					</table>
				</div>
			</div>	
			</div>
			<div class="ic-foot-container">
				<div class="ic-button-container ic-pad-5">
					<ihtml:nbutton property="btSave" componentID="BUT_MAILTRACKING_DEFAULTS_PARTNERCARRIER_SAVE" accesskey="S">
						<common:message key="mailtracking.defaults.partnercarrier.btn.save" />
					</ihtml:nbutton>
					<ihtml:nbutton property="btClose" componentID="BUT_MAILTRACKING_DEFAULTS_PARTNERCARRIER_CLOSE" accesskey="O">
						<common:message key="mailtracking.defaults.partnercarrier.btn.close" />
					</ihtml:nbutton>
				</div>
			</div>	
		</div>
	</div>		
	</ihtml:form>
		
</div>
	</body>
</html:html>

