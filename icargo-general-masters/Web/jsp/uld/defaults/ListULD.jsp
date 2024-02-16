<%--
********************************************************************
* Project	 		: iCargo
* Module Code & Name  : Uld
* File Name			: ListUld.jsp
* Date				: 30-Jan-2006
* Author(s)			: A-2001
********************************************************************
--%>

<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ListULDForm" %>
<%@ page import="com.ibsplc.icargo.business.uld.defaults.vo.ULDListVO"%>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ include file="/jsp/includes/tlds.jsp" %>
		
<html:html locale="true">
<head>
		<%@ include file="/jsp/includes/customcss.jsp"%>
		
	
<title>
	<common:message  bundle="listuldResources" key="uld.defaults.listUld.lbl.icargolistuld" />
</title>
<meta name="decorator" content="mainpanel">

<common:include src="/js/uld/defaults/ListULD_Script.jsp" type="script" />

</head>
<body >
	
	
<%@include file="/jsp/includes/reports/printFrame.jsp" %>
<business:sessionBean
		id="oneTimeValues"
		moduleName="uld.defaults"
		screenID="uld.defaults.listuld"
		method="get"
		attribute="oneTimeValues" />

<business:sessionBean
		id="LIST_DISPLAYVOS"
		moduleName="uld.defaults"
		screenID="uld.defaults.listuld"
		method="get"
		attribute="listDisplayPage" />

<business:sessionBean
		id="LIST_FILTERVO"
		moduleName="uld.defaults"
		screenID="uld.defaults.listuld"
		method="get"
		attribute="listFilterVO" />



<bean:define id="form"
		 name="listULDForm"  type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ListULDForm"
		 toScope="page" />
		 <div id="pageDiv" class="iCargoContent ic-masterbg">
	<ihtml:form action="/uld.defaults.screenloadlistuld.do">
	<ihtml:hidden property="lastPageNum"/>
	<ihtml:hidden property="displayPage"/>
	<ihtml:hidden property="statusFlag"/>
	<ihtml:hidden property="screenLoadStatus"/>
	<ihtml:hidden property="disableStatus"/>
	<input type="hidden" name="currentDialogId" />
	<input type="hidden" name="currentDialogOption" />
	<input type="hidden" name="mySearchEnabled" />
	<ihtml:hidden property="comboFlag"/>	 
	<!--Added by A-7359 as part of ICRD-228547 starts-->
	<ihtml:hidden property="autoCollapse" />
	<ihtml:hidden property="filterSummaryDetails" />
	<!--Added by A-7359 aas part of ICRD-228547 ends-->	


<div class="ic-content-main">
	<span class="ic-page-title"><common:message  key="uld.defaults.listUld.lbl.headingListUlds"/></span>
		<div class="ic-head-container" >	

			<div class="ic-filter-panel">
				<div class="ic-input-container">
					<div class="ic-row">
						<!--<h4>Search Criteria</h4> -->
							<h4><common:message  key="uld.defaults.listUld.searchCriteria"/></h4>
					</div>
					<div class="ic-section">
					<jsp:include page="ListULDFilters.jsp"/>
						
				
						
								
								
					
						

						
						



						
						
										
						
					</div>
					</div>
				</div>
			</div>

	  <div class="ic-main-container">	
	   <a class="panel upArrow"  collapseFilter="true"   href="#"></a> <!--Added by A-7359 for ICRD - 224586 starts here -->
					<div class="ic-row">
					<div class="ic-col-50">
						<h4><common:message  key="uld.defaults.listUld.uldDetails"/></h4>
				<div class="ic-row">
											<div class="paddL10">
				   			<logic:present name="LIST_DISPLAYVOS">
			    			<common:paginationTag
			    				pageURL="uld.defaults.listuld.do"
			    				name="LIST_DISPLAYVOS"
			    				display="label"
			    				labelStyleClass="iCargoLink"
			    				lastPageNum="<%=form.getLastPageNum() %>" />
			    			</logic:present>
					</div>
					</div>
					</div>
					<div class="ic-col-50">
						<div class="ic-button-container">
			    			<logic:present name="LIST_DISPLAYVOS">

			    			<common:paginationTag
			    				pageURL="javascript:submitPage('lastPageNum','displayPage')"
			    				name="LIST_DISPLAYVOS"
			    				display="pages"
			    				linkStyleClass="iCargoLink"
			    			    disabledLinkStyleClass="iCargoLink"
			    				lastPageNum="<%=form.getLastPageNum()%>" 
			    				exportToExcel="true"
							exportTableId="listuldtable"
							exportAction="uld.defaults.listuld.do"
								columnSelector="true"
								tableId="listuldtable"/>
			    			</logic:present>
							
			    			
							<logic:notPresent name="LIST_DISPLAYVOS">
								<common:columnSelector name="abc" tableId="listuldtable" id="abc" treetype="false"/>
							</logic:notPresent>
							 
				</div>
					</div>
					</div>		   
				<div class="ic-row">
				 <div  id="container"  class="ic-row">
				<jsp:include page="ListULD_Details.jsp"/>
				</div>
				</div>
				</div>
<div class="ic-foot-container">	
	<div class="ic-row ic-button-container " style="padding-bottom: 10px!important">
			<common:xgroup>
					<common:xsubgroup id="KZ_SPECIFIC">
							<ihtml:nbutton property="btPrintUldInventory" accesskey="I" componentID="BTN_ULD_DEFAULTS_LISTULD_PRINTULDINVENTORY">
								<common:message  key="uld.defaults.listUld.PrintUldInventory"/> 
							</ihtml:nbutton>
				</common:xsubgroup>
			</common:xgroup >			
			<ihtml:nbutton property="btCreate" accesskey="R" componentID="BTN_ULD_DEFAULTS_LISTULD_CREATE">
				<common:message  key="uld.defaults.listUld.btn.Create"/>
			</ihtml:nbutton>
			<ihtml:nbutton property="btDetails" accesskey="D" componentID="BTN_ULD_DEFAULTS_LISTULD_DETAILS">
				<common:message  key="uld.defaults.listUld.btn.Details"/>
			</ihtml:nbutton>
			<ihtml:nbutton property="btDelete" accesskey="E" componentID="BTN_ULD_DEFAULTS_LISTULD_DELETE">
				<common:message  key="uld.defaults.listUld.btn.Delete"/>
			</ihtml:nbutton>
			<ihtml:nbutton property="btPrint" accesskey="P" style="width:40px" componentID="BTN_ULD_DEFAULTS_LISTULD_PRINT">
				<common:message  key="uld.defaults.listUld.btn.Print"/>
			</ihtml:nbutton>
			<ihtml:nbutton property="btPrintAll" accesskey="N" componentID="BTN_ULD_DEFAULTS_LISTULD_PRINTALL">
				<common:message  key="uld.defaults.listUld.btn.PrintAll"/>
			</ihtml:nbutton>
			<ihtml:nbutton property="btRelocateUld" accesskey="T" componentID="BTN_ULD_DEFAULTS_LISTULD_RECORDULD">
				<common:message  key="uld.defaults.listUld.btn.RecordUld"/>
			</ihtml:nbutton>
			<ihtml:nbutton property="btRelocateUldMovement" accesskey="M" componentID="BTN_ULD_DEFAULTS_LISTULD_RECORDULDMOVEMENT">
				<common:message  key="uld.defaults.listUld.btn.RecordUldMovement"/>
			</ihtml:nbutton>

			<ihtml:nbutton property="btCreateTxn" accesskey="X" componentID="BTN_ULD_DEFAULTS_LISTULD_CREATETXN">
				<common:message  key="uld.defaults.listUld.btn.CreateTransaction"/>
			</ihtml:nbutton>

			<ihtml:nbutton property="btDamageReport" accesskey="G" componentID="BTN_ULD_DEFAULTS_LISTULD_DAMAGEREPORT">
				<common:message  key="uld.defaults.listUld.btn.DamageReport"/>
			</ihtml:nbutton>

			<ihtml:nbutton property="btUldDiscrepency" accesskey="I" componentID="BTN_ULD_DEFAULTS_LISTULD_ULDDISCREPENCYBTN">
				<common:message  key="uld.defaults.listUld.btn.UldDiscrepency"/>
			</ihtml:nbutton>
				
				
				
				
				<ihtml:nbutton property="btGenScm" accesskey="A" componentID="BTN_ULD_DEFAULTS_LISTULD_GENERATESCM">
						<common:message  key="uld.defaults.listUld.btn.generateScm"/>
			</ihtml:nbutton>			
			<ihtml:nbutton property="btOffload" accesskey="Y" componentID="BTN_ULD_DEFAULTS_LISTULD_OFFLOAD">
									<common:message  key="uld.defaults.listUld.btn.offload"/>
			</ihtml:nbutton>

			<ihtml:nbutton property="btClose" accesskey="O" componentID="BTN_ULD_DEFAULTS_LISTULD_CLOSE">
				<common:message  key="uld.defaults.listUld.btn.Close"/>
			</ihtml:nbutton>
				</div>
				</div>
		
	</div>
			
</ihtml:form>
</div>

	
	</body>
</html:html>

