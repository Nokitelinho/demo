<%--
* Project	 		: iCargo
* Module Code & Name: uld.defaults
* File Name			: MaintainDamageReport.jsp
* Date				: 23-Oct-2017
* Author(s)			: A-7627
 --%>

<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ux.misc.MaintainDamageReportForm" %>
<%@ page import="com.ibsplc.xibase.server.framework.persistence.query.Page"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO"%>
<%@ page import = "com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@page import ="java.util.ArrayList"%>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page info="lite"%>


<bean:define id="form" name="maintainDamageReportRevampForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ux.misc.MaintainDamageReportForm"
	toScope="page" />


<html:html>
<head>


	
<title>
	<bean:message bundle="maintainDamageReportUXResources" key="uld.defaults.ux.maintaindamagereport.lbl.maintainDamageReportTitle" scope="request"/>
</title>


<common:include type="css" src="/css/colorbox.css"/>
<common:include type="script" src="/js/jquery.colorbox-min.js"/>
<common:include type="script" src="/js/uld/defaults/ux/MaintainDamageReport_Script.jsp"/>

<logic:notEqual name="maintainDamageReportRevampForm" property="screenMode" value="uldacceptance" >
	<logic:notEqual name="maintainDamageReportRevampForm" property="screenMode" value="popupMode" >
	<meta name="decorator" content="mainpanel">
</logic:notEqual>
	<logic:equal name="maintainDamageReportRevampForm" property="screenMode" value="popupMode">
		<bean:define id="popup" value="true" />
		<meta name="decorator" content="popup_panel">
	</logic:equal>

</logic:notEqual>

<logic:equal name="maintainDamageReportRevampForm" property="screenMode" value="uldacceptance">
	<bean:define id="popup" value="true" />
	<meta name="decorator" content="popup_panel">
</logic:equal>
</head>
<body>

	

<%@include file="/jsp/includes/reports/printFrame.jsp" %>
<business:sessionBean id="oneTimeValues" moduleName="uld.defaults"	
		screenID="uld.defaults.ux.maintaindamagereport"	method="get" attribute="oneTimeValues" />
	
	<business:sessionBean id="uLDDamageVO" moduleName="uld.defaults"
		screenID="uld.defaults.ux.maintaindamagereport" method="get" attribute="uLDDamageVO" />

	<business:sessionBean id="nonOperationalDamageCodes" moduleName="uld.defaults" 
		screenID="uld.defaults.ux.maintaindamagereport"	method="get" attribute="nonOperationalDamageCodes" />
      
	<bean:define id="nonOperationalDamageCodes" name="nonOperationalDamageCodes"/>

	<business:sessionBean id="uldNumbers" moduleName="uld.defaults" 
		screenID="uld.defaults.ux.maintaindamagereport"	method="get" attribute="uldNumbers"/>
	
	<logic:present name="uLDDamageVO">
	    <bean:define id="uLDDamageVO" name="uLDDamageVO" />
	</logic:present>
	
	<div class="container pull-left">	
	<ihtml:form action="/uld.defaults.ux.screenloadmaintaindamagereport.do" method="POST" enctype="multipart/form-data"  styleClass="ic-main-form">
	
		<bean:define id="form" 
		name="maintainDamageReportRevampForm"
		type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ux.misc.MaintainDamageReportForm"
		toScope="page" />
	
		<ihtml:hidden property="screenStatusFlag"/>
		<ihtml:hidden property="statusFlag"/>
		<ihtml:hidden property="saveStatus"/>
		<ihtml:hidden property="flag"/>
		<ihtml:hidden property="pageURL"/>
		<ihtml:hidden property="selectedULDNos"/>
		<ihtml:hidden property="fromScreen"/>
		<ihtml:hidden property="displayPage"/>
		<ihtml:hidden property="lastPageNum"/>
		<ihtml:hidden property="currentPageNum"/>
		<ihtml:hidden property="totalRecords"/>
		<ihtml:hidden property="picturePresent"/>
		<ihtml:hidden property="seqNum"/>
		<input type="hidden" name="currentDialogId" />
		<input type="hidden" name="currentDialogOption" />
		<ihtml:hidden property="screenStatusValue"/>
		<ihtml:hidden property="screenReloadStatus"/>
		<ihtml:hidden property="damageStatusFlag"/>
		<ihtml:hidden property="allChecked"/>
		<ihtml:hidden property="screenMode"/>
		<ihtml:hidden property="totalDamagePoints"/> 
		<ihtml:hidden property="selectedDamageRowId" />
		<ihtml:hidden property="selectedRepairRowId" />
		<ihtml:hidden property="currentStation" />
		<ihtml:hidden property="imageIndex"/>
		<input type="hidden" name="nonOperationalDamageCodes" value="<%=(String)nonOperationalDamageCodes%>" />

	     <%
	        MaintainDamageReportForm maintainDamageReportForm = (MaintainDamageReportForm)request.getAttribute("maintainDamageReportForm");
	     %>
							 
	<logic:notPresent name="popup">	
	<div class="portlet flippane animated fadeInDown">
		<div class="portlet_content row">
		<div class="card highlight  form-body">
			<div class="col col-2">
			<label><bean:message bundle="maintainDamageReportUXResources" key="uld.defaults.maintaindamagereport.lbl.UldNo" /><b>*</b></label>           
				<logic:present name="uLDDamageVO" property="uldNumber">
					<bean:define id="uldNumber" name="uLDDamageVO" property="uldNumber" />
					<ibusiness:uld id="uldno" uldProperty="uldNumber" uldValue="<%=(String) uldNumber%>" styleClass="all-caps"  maxlength="12" componentID="TXT_ULD_DEFAULTS_MAINTAINDMG_ULDNO" />
				</logic:present>
				<logic:notPresent name="uLDDamageVO" property="uldNumber">
					<ibusiness:uld id="uldno" uldProperty="uldNumber" componentID="TXT_ULD_DEFAULTS_MAINTAINDMG_ULDNO" styleClass="all-caps" maxlength="12" />
				</logic:notPresent>
			</div>
			<span class="col col-4 m-t-20"> 
				<ihtml:nbutton property="btnList" styleClass="btn primary "	id="button_list" componentID="CMP_ULD_DEFAULTS_UX_MAINTAINDMG_LIST_BTN">
					<bean:message bundle="maintainDamageReportUXResources" key="uld.defaults.maintaindmgrep.btn.list" />
				</ihtml:nbutton> 
				<ihtml:button property="btnClear" styleClass="btn default"	id="clear_button" componentID="CMP_ULD_DEFAULTS_UX_MAINTAINDMG_CLEAR_BTN">
					<bean:message bundle="maintainDamageReportUXResources" key="uld.defaults.maintaindmgrep.btn.clear" />
				</ihtml:button>
			</span>
         </div>
		</div>
	</div>
	</logic:notPresent>
	<div id="damageDtlDiv" style="overflow:auto;overflow-y:auto;height:73vh;margin-top:5px;margin-bottom:5px;">
	   	<div class="portlet form-body">
		<logic:present name="popup">
			<div class="card row highlight">
				<div class="col col-5">
					<span class="col col-5"><label>ULD Number <b>*</b></label>
						<logic:present name="uLDDamageVO" property="uldNumber">
							<bean:define id="uldNumber" name="uLDDamageVO" property="uldNumber" />
							<ibusiness:uld id="uldno" uldProperty="uldNumber" uldValue="<%=(String) uldNumber%>"/>
						</logic:present>
					</span>
				</div>
			</div>
		</logic:present>
		<div class="header-section">
			<h2><bean:message bundle="maintainDamageReportUXResources" key="uld.defaults.maintaindamagereport.lbl.damageDetailsHeading" /></h2>
		</div>
		<div class="row card">
			<span class="col col-2">
				<label><bean:message bundle="maintainDamageReportUXResources" key="uld.defaults.maintaindamagereport.lbl.DamageStatus" /> <b>*</b></label>														 
				<logic:present name="uLDDamageVO" property="damageStatus">
					<bean:define id="damageStatus" name="uLDDamageVO" property="damageStatus" />
					<ihtml:select property="damageStatus" componentID="CMB_ULD_DEFAULTS_UX_MAINTAINDMG_DMGSTA" value="<%=(String) damageStatus%>">
						<ihtml:option value=""><common:message key="combo.select" /></ihtml:option>
						<logic:present name="oneTimeValues">
							<logic:iterate id="oneTimeValue" name="oneTimeValues">
								<bean:define id="parameterCode" name="oneTimeValue"	property="key" />
								<logic:equal name="parameterCode" value="uld.defaults.damageStatus">
									<bean:define id="parameterValues" name="oneTimeValue" property="value" />
									<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
										<logic:present name="parameterValue">
											<bean:define id="fieldValue" name="parameterValue"	property="fieldValue" />
											<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription" />
											<html:option value="<%=(String) fieldValue%>"><%=(String) fieldDescription%></html:option>
										</logic:present>
									</logic:iterate>
								</logic:equal>
							</logic:iterate>
						</logic:present>
					</ihtml:select>
				</logic:present>
				<logic:notPresent name="uLDDamageVO" property="damageStatus">
					<ihtml:select property="damageStatus" componentID="CMB_ULD_DEFAULTS_UX_MAINTAINDMG_DMGSTA">
						<ihtml:option value=""><common:message key="combo.select" /></ihtml:option>
						<logic:present name="oneTimeValues">
							<logic:iterate id="oneTimeValue" name="oneTimeValues">
								<bean:define id="parameterCode" name="oneTimeValue"	property="key" />
								<logic:equal name="parameterCode" value="uld.defaults.damageStatus">
									<bean:define id="parameterValues" name="oneTimeValue" property="value" />
									<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
										<logic:present name="parameterValue">
											<bean:define id="fieldValue" name="parameterValue"	property="fieldValue" />
											<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription" />
											<html:option value="<%=(String) fieldValue%>"><%=(String) fieldDescription%></html:option>
										</logic:present>
									</logic:iterate>
								</logic:equal>
							</logic:iterate>
						</logic:present>
					</ihtml:select>
				</logic:notPresent>
			</span> 
			<span class="col col-2">
				<label><bean:message bundle="maintainDamageReportUXResources" key="uld.defaults.maintaindamagereport.lbl.OverallStatus" /><b>*</b></label> 
				<logic:present name="uLDDamageVO" property="overallStatus">
					<bean:define id="overallStatus" name="uLDDamageVO"	property="overallStatus" />
					<ihtml:select property="overallStatus"	componentID="CMB_ULD_DEFAULTS_UX_MAINTAINDMG_OVRSTA" value="<%=(String) overallStatus%>">
						<ihtml:option value=""><common:message key="combo.select" /></ihtml:option>
						<logic:present name="oneTimeValues">
							<logic:iterate id="oneTimeValue" name="oneTimeValues">
								<bean:define id="parameterCode" name="oneTimeValue"	property="key" />
								<logic:equal name="parameterCode" value="uld.defaults.operationalStatus">
									<bean:define id="parameterValues" name="oneTimeValue" property="value" />
									<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
										<logic:present name="parameterValue">
											<bean:define id="fieldValue" name="parameterValue"	property="fieldValue" />
											<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription" />
											<html:option value="<%=(String) fieldValue%>"><%=(String) fieldDescription%></html:option>
										</logic:present>
									</logic:iterate>
								</logic:equal>
							</logic:iterate>
						</logic:present>
					</ihtml:select>
				</logic:present>
				<logic:notPresent name="uLDDamageVO" property="overallStatus">
					<ihtml:select property="overallStatus" componentID="CMB_ULD_DEFAULTS_UX_MAINTAINDMG_OVRSTA">
						<ihtml:option value=""><common:message key="combo.select" /></ihtml:option>
						<logic:present name="oneTimeValues">
							<logic:iterate id="oneTimeValue" name="oneTimeValues">
								<bean:define id="parameterCode" name="oneTimeValue"	property="key" />
								<logic:equal name="parameterCode" value="uld.defaults.operationalStatus">
									<bean:define id="parameterValues" name="oneTimeValue" property="value" />
									<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
										<logic:present name="parameterValue">
											<bean:define id="fieldValue" name="parameterValue"	property="fieldValue" />
											<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription" />
											<html:option value="<%=(String) fieldValue%>"><%=(String) fieldDescription%></html:option>
										</logic:present>
									</logic:iterate>
								</logic:equal>
							</logic:iterate>
						</logic:present>
					</ihtml:select>
				</logic:notPresent> 
			</span>
			<!--<span class="col col-2">
				<label><bean:message bundle="maintainDamageReportUXResources" key="uld.defaults.maintaindamagereport.lbl.Upload" /></label>
				<ihtml:file property="dmgPicture" styleClass="iCargoTextFieldExtraLong" title="Select an damage picture" maxlength="75" style="width:180px"/>
			</span> -->
		</div>				  
		<div id="div1" class="row datagrid">
			<jsp:include page="MaintainDamage_DamageDetails.jsp" />
		</div>
		<div class="row text-right m-t-10 card">
			<ihtml:nbutton property="btnaddDamage" styleClass="btn primary"	id="btnaddDamage" componentID="CMP_ULD_UX_DEFAULTS_MAINTAINDMG_ADDDMG_BTN" value="Add New Damage"></ihtml:nbutton>
		</div>
		</div>	
		<div class="portlet form-body">
			<div class="header-section">
				<h2><bean:message bundle="maintainDamageReportUXResources" key="uld.defaults.maintaindamagereport.lbl.repairDetailsHeading" /></h2>
			</div>	
			<div class="row datagrid">
				<jsp:include page="MaintainDamage_RepairDetails.jsp" />
			</div>	
			<div class="row text-right m-t-10 card">
				<ihtml:nbutton property="btnaddRepair" styleClass="btn primary"	id="btnaddRepair" componentID="CMP_ULD_DEFAULTS_UX_MAINTAINDMG_ADDREPAIR_BTN" value="Add New Repair"></ihtml:nbutton>
			</div>	
			<div class="row card">
			<span class="col col-2"> 
				<label><bean:message bundle="maintainDamageReportUXResources" key="uld.defaults.maintaindamagereport.lbl.RepairStat" /> <b>*</b></label>
				<logic:present name="uLDDamageVO" property="repairStatus">
					<bean:define id="repairStatus" name="uLDDamageVO" property="repairStatus" />
					<ihtml:select property="repairStatus" componentID="CMB_ULD_DEFAULTS_UX_MAINTAINDMG_RPRSTA" value="<%=(String) repairStatus%>">						<html:option value="">--Select--</html:option>
						<logic:present name="oneTimeValues">
							<logic:iterate id="oneTimeValue" name="oneTimeValues">
								<bean:define id="parameterCode" name="oneTimeValue"	property="key" />
								<logic:equal name="parameterCode" value="uld.defaults.repairStatus">
									<bean:define id="parameterValues" name="oneTimeValue" property="value" />
									<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
										<logic:present name="parameterValue">
											<bean:define id="fieldValue" name="parameterValue"	property="fieldValue" />
											<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription" />
											<html:option value="<%=(String) fieldValue%>"><%=(String) fieldDescription%></html:option>
										</logic:present>
									</logic:iterate>
								</logic:equal>
							</logic:iterate>
						</logic:present>
					</ihtml:select>
				</logic:present> 
				<logic:notPresent name="uLDDamageVO" property="repairStatus">
					<ihtml:select property="repairStatus"	componentID="CMB_ULD_DEFAULTS_UX_MAINTAINDMG_RPRSTA">
						<html:option value="">--Select--</html:option>
						<logic:present name="oneTimeValues">
							<logic:iterate id="oneTimeValue" name="oneTimeValues">
								<bean:define id="parameterCode" name="oneTimeValue"	property="key" />
								<logic:equal name="parameterCode" value="uld.defaults.repairStatus">
									<bean:define id="parameterValues" name="oneTimeValue"	property="value" />
									<logic:iterate id="parameterValue" name="parameterValues"	type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
										<logic:present name="parameterValue">
											<bean:define id="fieldValue" name="parameterValue"	property="fieldValue" />
											<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription" />
											<html:option value="<%=(String) fieldValue%>"><%=(String) fieldDescription%></html:option>
										</logic:present>
									</logic:iterate>
								</logic:equal>
							</logic:iterate>
						</logic:present>
					</ihtml:select>
				</logic:notPresent>
			</span> 
			<span class="col col-2">
				<label><bean:message bundle="maintainDamageReportUXResources" key="uld.defaults.maintaindamagereport.lbl.Supervisor" /><b>*</b></label>
				<logic:present name="uLDDamageVO"	property="supervisor"> 
				<bean:define id="supervisor" name="uLDDamageVO" property="supervisor" />
					<ihtml:text property="supervisor" componentID="TXT_ULD_DEFAULTS_MAINTAINDMG_SUPERVISOR" name="maintainDamageReportForm" maxlength="15" value="<%=(String) supervisor%>" />
				</logic:present> 
				<logic:notPresent name="uLDDamageVO" property="supervisor">
					<bean:define id="userid" name="form" property="supervisor" />
					<ihtml:text property="supervisor" componentID="TXT_ULD_DEFAULTS_MAINTAINDMG_SUPERVISOR"	name="maintainDamageReportForm" maxlength="15" value="<%=(String) userid%>" />
				</logic:notPresent> 
			</span> 
			<span class="col col-2">
				<label><bean:message bundle="maintainDamageReportUXResources" key="uld.defaults.maintaindamagereport.lbl.InvestigationRep" /><b>*</b></label> 
				<logic:present	name="uLDDamageVO" property="investigationReport">
					<bean:define id="investigationReport" name="uLDDamageVO" property="investigationReport" />
					<ihtml:text property="invRep" componentID="TXT_ULD_DEFAULTS_MAINTAINDAMAGE_REMARKS"	value="<%=(String) investigationReport%>" />
				</logic:present> 
				<logic:notPresent name="uLDDamageVO" property="investigationReport">
					<ihtml:text property="invRep" componentID="TXT_ULD_DEFAULTS_MAINTAINDAMAGE_REMARKS" />
				</logic:notPresent> 
			</span> 
			<span class="col col-2 m-t-20"> 
				<ihtml:nbutton property="btnDispTot" styleClass="btn primary" id="btnDispTot" componentID="CMP_ULD_DEFAULTS_UX_MAINTAINDMG_DISPTOT_BTN">
					<common:message key="uld.defaults.maintaindmgrep.btn.display" />
				</ihtml:nbutton>
			</span> 
			<span class="col col-2">
				<label> <bean:message bundle="maintainDamageReportUXResources"	key="uld.defaults.maintaindamagereport.lbl.TotAmt" /> <b>*</b></label>
				<ihtml:text property="totAmt" componentID="TXT_ULD_DEFAULTS_MAINTAINDMG_TOTAMT"	name="maintainDamageReportRevampForm" maxlength="12" disabled="true" style="text-align: right" />
			</span>
			</div>	
		</div>
		</div>		
		<div class="btmbtnpane btm-fixed">					
			<ihtml:nbutton property="btnPrint" styleClass="btn default"	id="btnPrint" componentID="CMP_ULD_UX_DEFAULTS_MAINTAINDMG_PRINT_BTN">
				<bean:message bundle="maintainDamageReportUXResources" key="uld.defaults.maintaindmgrep.btn.print" />
			</ihtml:nbutton>
			<ihtml:nbutton property="btnSave" styleClass="btn primary"	id="Save_button" componentID="CMP_ULD_DEFAULTS_UX_MAINTAINDMG_SAVE_BTN">
				<bean:message bundle="maintainDamageReportUXResources"	key="uld.defaults.maintaindmgrep.btn.save" />
			</ihtml:nbutton>
			<ihtml:button property="btnClose" styleClass="btn default"	componentID="CMP_ULD_DEFAULTS_UX_MAINTAINDMG_CLOSE_BTN">
				<bean:message bundle="maintainDamageReportUXResources"	key="uld.defaults.maintaindmgrep.btn.close" />
			</ihtml:button>
		</div>			
				
		 <jsp:include page="/jsp/includes/filevalidator/FileValidatorApplet.jsp"/>
		

 </ihtml:form>	
</div>




		

				
		
	</body>
</html:html>

