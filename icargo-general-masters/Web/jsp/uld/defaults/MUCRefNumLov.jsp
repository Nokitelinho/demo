<%--
**************************************************************************************
* Project	 		: iCargo
* Module Code & Name: Uld
* File Name			: MUCRefNumLov.jsp
* Date				: 07-Aug-2008
* Author(s)			: A-3045
**************************************************************************************
 --%>

<%@ include file="/jsp/includes/tlds.jsp" %>
<html:html locale="true">
<head>
		

	<meta name="decorator" content="popuppanelrestyledui">
	<common:include src="/js/uld/defaults/MUCRefNumLov_Script.jsp" type="script"/>
</head>
<body style="overflow:auto;">

	
	<business:sessionBean
			id="MUCREFLOVS"
			moduleName="uld.defaults"
			screenID="uld.defaults.messaging.muctracking"
			method="get"
			attribute="lovVO" />

	<bean:define id="form"
			 name="MUCTrackingForm"  type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.MUCTrackingForm"
			 toScope="page" />

	<div class="iCargoPopUpContent" style="width:100%;height:100%;" >
		<ihtml:form action="/uld.defaults.messaging.mucrefnolov.okcommand.do" styleClass="ic-main-form">
		<ihtml:hidden property="lastLovPageNum"/>
		<ihtml:hidden property="displayLovPage"/>
		<ihtml:hidden property="lovStatusFlag"/>
		<ihtml:hidden property="mucReferenceNumber"/>
		<input type="hidden" name="currentDialogId" />
		<input type="hidden" name="currentDialogOption" />
		
		<div class="ic-content-main">
			<span class="ic-page-title ic-display-none">
				<common:message  key="uld.defaults.mucrefnolov.invoiceRefLov" />
			</span>
			<div class="ic-head-container">
				<div class="ic-filter-panel">
					<div class="ic-input-container">
						<div class="ic-row">
							<div class=" ic-input ic-split-50 ic-center">
								<label>
									<common:message key="uld.defaults.muctracking.refNumber" scope="request" />
								</label>
								<ihtml:text property="mucReferenceNum"
									componentID="TXT_ULD_DEFAULTS_MUCREFNOLOV_MUCREFNO" maxlength="12"/>
							</div>
							<div class="ic-split-50 ic-input ic-center">
								<label>
									<common:message key="uld.defaults.muctracking.date" scope="request" />
								</label>
								<ibusiness:calendar property="mucLovFilterDate" id="mucLovFilterDate" componentID="CMP_ULD_DEFAULTS_MUCTRACKING_DATE" type="image"/>
							</div>
							<div class="ic-button-container">
								<ihtml:nbutton property="btList" componentID="BTN_ULD_DEFAULTS_MUCREFNOLOV_LIST" >
									<common:message key="uld.defaults.muctracking.list" />
								</ihtml:nbutton>
								<ihtml:nbutton property="btClear" componentID="BTN_ULD_DEFAULTS_MUCREFNOLOV_CLEAR"  >
									<common:message key="uld.defaults.muctracking.clear" />
								</ihtml:nbutton>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="ic-main-container">
				<div class="ic-row">
					
						<logic:present name="MUCREFLOVS">
						<common:paginationTag
							pageURL="uld.defaults.messaging.mucrefnolov.screenload.do"
							name="MUCREFLOVS"
							display="label"
							labelStyleClass="iCargoResultsLabel"
							lastPageNum="<%=form.getLastLovPageNum() %>" />
						</logic:present>
					
					<div class="ic-button-container paddR5">
						<logic:present name="MUCREFLOVS">
							<common:paginationTag
								pageURL="javascript:submitLovPage('lastPageNum','displayPage')"
								name="MUCREFLOVS"
								display="pages"
								labelStyleClass="iCargoResultsLabel"
								disabledLinkStyleClass="iCargoLink"
								lastPageNum="<%=form.getLastLovPageNum()%>"/>
						</logic:present>
					</div>
				</div>
				<div class="ic-row">
					<div class="tableContainer" id="div1"  style="width:100%;height:300px;">
						<table class="fixed-header-table" id="table_1" >
							<thead>
								<tr class="iCargoTableHeadingLeft" >
									<td  width="6%"  class="iCargoTableDataTd">&nbsp;</td>
									<td width="94%">
										<common:message  key="uld.defaults.muctracking.refNumber" />
									</td>
								</tr>
							</thead>
							<tbody>
								<logic:present name="MUCREFLOVS">
								<logic:iterate id="mucListData" name="MUCREFLOVS"  type="java.lang.String" indexId="index">
									<tr>
										<td  class="iCargoTableDataTd">
											<input type="checkbox" name="selectedRowsInLov" value="<%=index%>" onclick="checkBoxValidate('selectedRowsInLov',<%=index%>)"/>
										</td>
										<td  class="iCargoTableDataTd" >
											<logic:present name="mucListData">
												<bean:write name="mucListData"/>
												<%System.out.println((String)mucListData);%>
												<input type="hidden" name="mucRefNumbers" value="<%=(String)mucListData%>"/>
											</logic:present>
										</td>
									</tr>
								</logic:iterate>
								</logic:present>						
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div class="ic-foot-container">
				<div class="ic-button-container paddR5">
					<ihtml:nbutton property="btOk" componentID="BTN_ULD_DEFAULTS_MUCREFNOLOV_OK">
						<common:message  key="uld.defaults.mucrefnolov.Ok" />
					</ihtml:nbutton>
					<ihtml:nbutton property="btClose" componentID="BTN_ULD_DEFAULTS_MUCREFNOLOV_CLOSE">
						<common:message  key="uld.defaults.mucrefnolov.Close" />
					</ihtml:nbutton>
				</div>
			</div>	
		</div>
			 
		</ihtml:form>
	</div>
			
		
	</body>
</html:html>

