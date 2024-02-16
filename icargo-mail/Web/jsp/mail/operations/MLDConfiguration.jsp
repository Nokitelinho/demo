<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  Mailtracking
* File Name				:  MLDConfiguration.jsp
* Date					:  15-December-2015
* Author(s)				:  A-5526
*************************************************************************/
 --%>


<%@ page language="java"%>
<%@ include file="/jsp/includes/tlds.jsp"%>
<%@ page
	import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MLDConfigurationForm"%>
<%@ page
	import="com.ibsplc.icargo.business.mail.operations.vo.MLDConfigurationVO"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.ArrayList"%>


<html:html>

<head>





<title><common:message bundle="MLDConfigurationResources"
	key="mailtracking.defaults.mldconfiguration.lbl.title" /></title>
<meta name="decorator" content="mainpanelrestyledui">
<common:include type="script"
	src="/js/mail/operations/MLDConfiguration_Script.jsp" />
</head>

<body>


<bean:define id="form" name="MLDConfigurationForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MLDConfigurationForm"
	toScope="page" />

<business:sessionBean id="mLDConfigurationVOs"
	moduleName="mail.operations"
	screenID="mailtracking.defaults.mldconfiguration" method="get"
	attribute="mLDConfigurationVOs" />
<div class="iCargoContent ic-masterbg"><ihtml:form
	action="/mailtracking.defaults.mldconfiguartion.screenload.do">
	<ihtml:hidden property="isAllocatedCheck" />
	<ihtml:hidden property="isUpliftedCheck" />
	<ihtml:hidden property="isdlvCheck" />
	<ihtml:hidden property="isHndcheck" />
	<ihtml:hidden property="isReceivedCheck" />
	<ihtml:hidden property="isStagedCheck" />
	<ihtml:hidden property="isNestedCheck" />
	<ihtml:hidden property="isReceivedFromFightCheck" />
	<ihtml:hidden property="isTransferredFromOALcheck" />
	<ihtml:hidden property="isReceivedFromOALcheck" />
	<ihtml:hidden property="isReturnedCheck" />
	<ihtml:hidden property="listFlag" />
	
	<div class="ic-content-main"><span class="ic-page-title"><common:message
		key="mailtracking.defaults.mldconfiguration.lbl.heading"
		scope="request" /></span>




	<div class="ic-head-container">
	<div class="ic-row">
	<div class="ic-filter-panel">
	<div class="ic-row">
	<div class="ic-col">
	<h4><common:message
		key="mailtracking.defaults.mldconfiguration.lbl.search"
		scope="request" /></h4>
	</div>
	</div>

	<div class="ic-input-container ic-label-30">
	<div class="ic-row">
	<div class="ic-input ic-split-20"><label><common:message
		key="mailtracking.defaults.mldconfiguration.lbl.carrier"
		scope="request" /></label> <ihtml:text property="carrier"
		componentID="MAILTRACKING_DEFAULTS_MLDCONFIGURATION_CARRIER"
		maxlength="3" /> 
		<div class= "lovImg">
		<img id="airlineLov" height="22px"
		src="<%=request.getContextPath()%>/images/lov.png" width="22px"
		onclick="displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.carrier.value,'Airline','1','carrier','',0)"
		title="<common:message key="mailtracking.defaults.mldconfiguration.lbl.carrier" />" />
	</div>
	</div>
	<div class="ic-input ic-split-20"><label><common:message
		key="mailtracking.defaults.mldconfiguration.lbl.airport"
		scope="request" /></label> <ihtml:text property="airport"
		componentID="MAILTRACKING_DEFAULTS_MLDCONFIGURATION_AIRPORT"
		maxlength="4" /> 
		<div class= "lovImg">
		<img id="airportCodelov" height="22px"
		src="<%=request.getContextPath()%>/images/lov.png" width="22px"
		onclick="displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.airport.value,'Airport','1','airport','',0)"
		title="<common:message key="mailtracking.defaults.mldconfiguration.lbl.airport" />" />
	</div>
	</div>
	
	 <div class="ic-input ic-mandatory ic-split-20">
			<label>	
				<common:message key="mailtracking.defaults.mldconfiguration.lbl.mldversion" />
			</label>
			<ihtml:text property="mldversion" styleClass="iCargoTextFieldLong" componentID="MAILTRACKING_DEFAULTS_MLDCONFIGURATION_MLDVERSION" maxlength="1"/>
			<div class="lovImg"><img id="mldversionLov" src="<%=request.getContextPath()%>/images/lov.png" width="22px" 
			height="22px"  onclick="displayOneTimeLOV('screenloadOneTime.do','N','Y','screenloadOneTime.do',targetFormName.elements.mldversion.value,
	targetFormName.elements.mldversion.value,'MldVersions','0','mldversion','mldversion',0,'mailtracking.defaults.mldversions','')"/></div>
	
		</div>
	<div class="ic-button-container"><ihtml:nbutton property="btList"
		componentID="MAILTRACKING_DEFAULTS_MLDCONFIGURATION_BTN_LIST"
		accesskey="L">
		<common:message key="mailtracking.defaults.mldconfiguration.btn.list" />
	</ihtml:nbutton> <ihtml:nbutton property="btClear"
		componentID="MAILTRACKING_DEFAULTS_MLDCONFIGURATION_BTN_CLEAR"
		accesskey="C">
		<common:message key="mailtracking.defaults.mldconfiguration.btn.clear" />
	</ihtml:nbutton></div>
	
	</div>
	
	</div>

	</div>
	</div>
	</div>


	<div class="ic-main-container">

	<div class="ic-row">
	<div class="ic-col">
	<h4><common:message
		key="mailtracking.defaults.mldconfiguration.lbl.details"
		scope="request" /></h4>
	</div>
	</div>
	<div class="ic-row">
	<div class="ic-col-78">
	<div class="ic-button-container paddR10"><a href="#" id="addLink"
		value="add" name="add" class="iCargoLink"> <common:message
		key="mailtracking.defaults.mldconfiguration.lbl.add" scope="request" />
	</a> | <a href="#" id="deleteLink" value="delete" name="delete"
		class="iCargoLink"> <common:message
		key="mailtracking.defaults.mldconfiguration.lbl.delete"
		scope="request" /> </a></div>
	</div>
	</div>
	<div class="ic-row">
	<div class="tableContainer" id="div1" style="height: 550px;">
	<table class="fixed-header-table" id="listProductTable">

		<thead>
			<tr class="ic-th-all">
				<th style="width: 2%" />
				<th style="width: 6%" />
				<th style="width: 6%" />
				<th style="width: 8%" />
			</tr>
			<tr>
				<td class="iCargoTableHeader" tabindex="-1" rowspan="2" style="text-align: center;"><input
					type="checkbox" name="checkAll" value="checkbox"
					onclick="updateHeaderCheckBox(this.form,document.forms[1].checkAll,document.forms[1].rowId)"></td>
				<td class="iCargoTableHeaderLabel" rowspan="2"><common:message
					key="mailtracking.defaults.mldconfiguration.lbl.carrier" /><span></span></td>
				<td class="iCargoTableHeaderLabel" rowspan="2"><common:message
					key="mailtracking.defaults.mldconfiguration.lbl.airport" /><span></span></td>
				<td class="iCargoTableHeaderLabel" colspan="11" style="height: 20px;"><common:message
					key="mailtracking.defaults.mldconfiguration.lbl.mldmessagemodes" /><span></span></td>

			</tr>
			<tr style="height: 20px;">
				<td class="iCargoTableHeaderLabel"><common:message
					key="mailtracking.defaults.mldconfiguration.lbl.rec" /><span></span></td>
				<td class="iCargoTableHeaderLabel"><common:message
					key="mailtracking.defaults.mldconfiguration.lbl.rct" /><span></span></td>
				<td class="iCargoTableHeaderLabel"><common:message
					key="mailtracking.defaults.mldconfiguration.lbl.ret" /><span></span></td>
				<td class="iCargoTableHeaderLabel"><common:message
					key="mailtracking.defaults.mldconfiguration.lbl.nst" /><span></span></td>							
				<td class="iCargoTableHeaderLabel"><common:message
					key="mailtracking.defaults.mldconfiguration.lbl.all" /><span></span></td>
				<td class="iCargoTableHeaderLabel"><common:message
					key="mailtracking.defaults.mldconfiguration.lbl.stg" /><span></span></td>
				<td class="iCargoTableHeaderLabel"><common:message
					key="mailtracking.defaults.mldconfiguration.lbl.upl" /><span></span></td>
				<td class="iCargoTableHeaderLabel"><common:message
					key="mailtracking.defaults.mldconfiguration.lbl.hnd" /><span></span></td>
				<td class="iCargoTableHeaderLabel"><common:message
					key="mailtracking.defaults.mldconfiguration.lbl.rcf" /><span></span></td>
				<td class="iCargoTableHeaderLabel"><common:message
					key="mailtracking.defaults.mldconfiguration.lbl.tfd" /><span></span></td>
				<td class="iCargoTableHeaderLabel"><common:message
					key="mailtracking.defaults.mldconfiguration.lbl.dlv" /><span></span></td>
			</tr>
		</thead>


		<tbody id="mldConfigurationTableBody">






			<logic:present name="mLDConfigurationVOs">
				<logic:iterate id="mLDConfigurationVO" name="mLDConfigurationVOs"
					type="MLDConfigurationVO" indexId="rowCount">
					<common:rowColorTag index="rowCount">

						<tr>


							<td class="iCargoTableDataTd" style="text-align: center;"><input type="checkbox"
								name="rowId" property="rowId"
								value="<%=String.valueOf(rowCount)%>"
								onclick="toggleTableHeaderCheckbox('rowId',document.forms[1].checkAll)" />
							<logic:equal name="mLDConfigurationVO" property="operationFlag"
								value="I">
								<ihtml:hidden property="operationFlag" name="operationFlag"
									value="I" />
							</logic:equal> <logic:notEqual name="mLDConfigurationVO"
								property="operationFlag" value="I">
								<ihtml:hidden property="operationFlag" name="operationFlag"
									value="N" />
							</logic:notEqual></td>

							<td class="iCargoTableDataTd"><logic:present
								name="mLDConfigurationVO" property="carrierCode">
								<bean:define id="carrierCode" name="mLDConfigurationVO"
									property="carrierCode" />
								<logic:notEqual name="mLDConfigurationVO"
									property="operationFlag" value="I">
									<ihtml:text property="carrierCode" name="mLDConfigurationVO"
										value="<%=(String)carrierCode%>"
										componentID="MAILTRACKING_DEFAULTS_MLDCONFIGURATION_CARRIER"
										readonly="true" />
								</logic:notEqual>
								<logic:equal name="mLDConfigurationVO" property="operationFlag"
									value="I">
									<ihtml:text property="carrierCode" name="mLDConfigurationVO"
										value="<%=(String)carrierCode%>"
										componentID="MAILTRACKING_DEFAULTS_MLDCONFIGURATION_CARRIER"
										maxlength="3" />
									<div class= "lovImgTbl">
									<img id="airlineLov" height="16px"
										src="<%=request.getContextPath()%>/images/lov.png" width="16px"
										onclick="displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.carrierCode.value,'Airline','1','carrierCode','',<%=rowCount%>)"
										title="<common:message key="mailtracking.defaults.mldconfiguration.lbl.carrier" />" />
									</div>	
								</logic:equal>


							</logic:present> <logic:notPresent name="mLDConfigurationVO"
								property="carrierCode">

								<ihtml:text property="carrierCode" name="mLDConfigurationVO"
									componentID="MAILTRACKING_DEFAULTS_MLDCONFIGURATION_CARRIER" />
							</logic:notPresent></td>
							<td class="iCargoTableDataTd"><logic:present
								name="mLDConfigurationVO" property="airportCode">
								<bean:define id="airportCode" name="mLDConfigurationVO"
									property="airportCode" />
								<logic:notEqual name="mLDConfigurationVO"
									property="operationFlag" value="I">
									<ihtml:text property="airportCode" name="mLDConfigurationVO"
										value="<%=(String)airportCode%>"
										componentID="MAILTRACKING_DEFAULTS_MLDCONFIGURATION_AIRPORT"
										readonly="true" />
								</logic:notEqual>
								<logic:equal name="mLDConfigurationVO" property="operationFlag"
									value="I">
									<ihtml:text property="airportCode" name="mLDConfigurationVO"
										value="<%=(String)airportCode%>"
										componentID="MAILTRACKING_DEFAULTS_MLDCONFIGURATION_AIRPORT"
										maxlength="4" />
									<div class= "lovImgTbl">
									<img id="airportCodelov" height="16px"
										src="<%=request.getContextPath()%>/images/lov.png" width="16px"
										onclick="displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.airportCode.value,'Airport','1','airportCode','',<%=rowCount%>)"
										title="<common:message key="mailtracking.defaults.mldconfiguration.lbl.airport" />" />
									</div>
								</logic:equal>
							</logic:present> <logic:notPresent name="mLDConfigurationVO"
								property="airportCode">

								<ihtml:text property="airportCode" name="mLDConfigurationVO"
									componentID="MAILTRACKING_DEFAULTS_MLDCONFIGURATION_AIRPORT" />
							</logic:notPresent></td>

							<td class="iCargoTableDataTd" style="text-align: center;" >
							<%String receivedRequired="";%> <logic:present
								name="mLDConfigurationVO" property="receivedRequired">
								<bean:define id="receivedReqd" name="mLDConfigurationVO"
									property="receivedRequired" />
								<% receivedRequired=(String)receivedReqd;%>
							</logic:present> 
							<ihtml:checkbox name="mLDConfigurationVO" property="receivedRequired" value="Y"
							onchange="operationFlagChangeOnEdit(this,'operationFlag')" />

							</td>
							<td class="iCargoTableDataTd" style="text-align: center;">
							<%String receivedFromOALRequired="";%> <logic:present
								name="mLDConfigurationVO" property="receivedFromOALRequired">
								<bean:define id="receivedFromOALReqd" name="mLDConfigurationVO"
									property="receivedFromOALRequired" />
								<% receivedFromOALRequired=(String)receivedFromOALReqd;%>
							</logic:present> 
							<ihtml:checkbox name="mLDConfigurationVO" property="receivedFromOALRequired" value="Y"
							onchange="operationFlagChangeOnEdit(this,'operationFlag')" />

							</td>
							<td class="iCargoTableDataTd" style="text-align: center;" >
							<%String returnedRequired="";%> <logic:present
								name="mLDConfigurationVO" property="returnedRequired">
								<bean:define id="returnedReqd" name="mLDConfigurationVO"
									property="returnedRequired" />
								<% returnedRequired=(String)returnedReqd;%>
							</logic:present>
							<ihtml:checkbox name="mLDConfigurationVO" property="returnedRequired" value="Y"
					          onchange="operationFlagChangeOnEdit(this,'operationFlag')" />

							</td>
							<td class="iCargoTableDataTd" style="text-align: center;" >
							<%String nestedRequired="";%> <logic:present
								name="mLDConfigurationVO" property="nestedRequired">
								<bean:define id="nestedReqd" name="mLDConfigurationVO"
									property="nestedRequired" />
								<% nestedRequired=(String)nestedReqd;%>
							</logic:present> 
							<ihtml:checkbox name="mLDConfigurationVO" property="nestedRequired" value="Y"
							onchange="operationFlagChangeOnEdit(this,'operationFlag')" />

							</td>

							<td class="iCargoTableDataTd" style="text-align: center;">
							<%String allocatedRequired="";%> <logic:present
								name="mLDConfigurationVO" property="allocatedRequired">
								<bean:define id="alocationReqd" name="mLDConfigurationVO"
									property="allocatedRequired" />
								<% allocatedRequired=(String)alocationReqd;%>
							</logic:present> 
							<ihtml:checkbox name="mLDConfigurationVO" property="allocatedRequired" value="Y"
							onchange="operationFlagChangeOnEdit(this,'operationFlag')" />

							</td>
							<td class="iCargoTableDataTd" style="text-align: center;">
							<%String stagedRequired="";%> <logic:present
								name="mLDConfigurationVO" property="stagedRequired">
								<bean:define id="stagedReqd" name="mLDConfigurationVO"
									property="stagedRequired" />
								<% stagedRequired=(String)stagedReqd;%>
							</logic:present> 
							<ihtml:checkbox name="mLDConfigurationVO" property="stagedRequired" value="Y"
							onchange="operationFlagChangeOnEdit(this,'operationFlag')" />
							</td>
							<td class="iCargoTableDataTd" style="text-align: center;">
							<%String upliftedRequired="";%> <logic:present
								name="mLDConfigurationVO" property="upliftedRequired">
								<bean:define id="upliftReqd" name="mLDConfigurationVO"
									property="upliftedRequired" />
								<% upliftedRequired=(String)upliftReqd;%>
							</logic:present> 
							<ihtml:checkbox name="mLDConfigurationVO" property="upliftedRequired" value="Y"
							onchange="operationFlagChangeOnEdit(this,'operationFlag')" />

							</td>

							<td class="iCargoTableDataTd" style="text-align: center;">
							<%String hndRequired="";%> <logic:present name="mLDConfigurationVO"
								property="hNDRequired">
								<bean:define id="hndReqd" name="mLDConfigurationVO"
									property="hNDRequired" />
								<% hndRequired=(String)hndReqd;%>
							</logic:present> 
							<ihtml:checkbox name="mLDConfigurationVO" property="hNDRequired" value="Y" 
						     onchange="operationFlagChangeOnEdit(this,'operationFlag')" />
							</td>

							<td class="iCargoTableDataTd" style="text-align: center;">
							<%String receivedFromFightRequired="";%> <logic:present name="mLDConfigurationVO"
								property="receivedFromFightRequired">
								<bean:define id="receivedFromFightReqd" name="mLDConfigurationVO"
									property="receivedFromFightRequired" />
								<% receivedFromFightRequired=(String)receivedFromFightReqd;%>
							</logic:present> 
							<ihtml:checkbox name="mLDConfigurationVO" property="receivedFromFightRequired" value="Y"
							onchange="operationFlagChangeOnEdit(this,'operationFlag')" />

							</td>
							<td class="iCargoTableDataTd" style="text-align: center;">
							<%String transferredFromOALRequired="";%> <logic:present
								name="mLDConfigurationVO" property="transferredFromOALRequired">
								<bean:define id="transferredFromOALReqd" name="mLDConfigurationVO"
									property="transferredFromOALRequired" />
								<% transferredFromOALRequired=(String)transferredFromOALReqd;%>
							</logic:present> 
							<ihtml:checkbox name="mLDConfigurationVO" property="transferredFromOALRequired" value="Y"
							onchange="operationFlagChangeOnEdit(this,'operationFlag')" />

							</td>
							<td class="iCargoTableDataTd" style="text-align: center;">
							<%String deliveredRequired="";%> <logic:present
								name="mLDConfigurationVO" property="deliveredRequired">
								<bean:define id="deliveredReqd" name="mLDConfigurationVO"
									property="deliveredRequired" />
								<% deliveredRequired=(String)deliveredReqd;%>
							</logic:present> 
							<ihtml:checkbox name="mLDConfigurationVO" property="deliveredRequired" value="Y" onchange="operationFlagChangeOnEdit(this,'operationFlag')"  />

							</td>



						</tr>






					</common:rowColorTag>
				</logic:iterate>
			</logic:present>

			<!-- templateRow -->

			<tr template="true" id="mldConfigurationTemplateRow"
				style="display: none">





				<td class="iCargoTableDataTd" style="text-align: center;"><input type="checkbox"
					name="rowId" value=""
					onclick="toggleTableHeaderCheckbox('rowId',document.forms[1].checkAll)" />
				<ihtml:hidden property="operationFlag" name="operationFlag"
					value="NOOP" /></td>

				<td class="iCargoTableDataTd ">
				<div class="ic-input">
				   <ihtml:text
					property="carrierCode" value=""
					componentID="MAILTRACKING_DEFAULTS_MLDCONFIGURATION_CARRIER"
					maxlength="3"/>
                    <div class= "lovImgTbl">					
					<img id="airlineLovRow"
					 height="16px" src="<%=request.getContextPath()%>/images/lov.png" width="16px"
					onclick="invokeAirlineLOV(this);" />
					</div>
				</div>
				</td>

				<td class="iCargoTableDataTd">
				<div class="ic-input">
				<ihtml:text
					property="airportCode" value=""
					componentID="MAILTRACKING_DEFAULTS_MLDCONFIGURATION_AIRPORT"
					maxlength="4" style="width:50px" /> 
					<div class= "lovImgTbl">	
					<img id="airportCodelovRow"
					 height="16px"src="<%=request.getContextPath()%>/images/lov.png" width="16px"
					onclick="invokeAirportLOV(this);" /> 
					</div>
				</div>	
				</td>

				<td class="iCargoTableDataTd" style="text-align: center;">
				<div><input type="checkbox" name="receivedRequired" value="N"
					title="Received" /></div>
				</td>
				<td class="iCargoTableDataTd" style="text-align: center;">
				<div><input type="checkbox" name="receivedFromOALRequired" value="N"
					title="receivedFromOALRequired" /></div>
				</td>				
				<td class="iCargoTableDataTd" style="text-align: center;">
				<div><input type="checkbox" name="returnedRequired" value="N"
					title="returnedRequired" /></div>
				</td>
				<td class="iCargoTableDataTd" style="text-align: center;">
				<div><input type="checkbox" name="nestedRequired" value="N"
					title="nestedRequired" /></div>
				</td>
				<td class="iCargoTableDataTd" style="text-align: center;">
				<div><input type="checkbox" name="allocatedRequired" value="N"
					title="Allocated" /></div>
				</td>
				<td class="iCargoTableDataTd" style="text-align: center;">
				<div><input type="checkbox" name="stagedRequired" value="N"
					title="stagedRequired" /></div>
				</td>
				<td class="iCargoTableDataTd" style="text-align: center;">
				<div><input type="checkbox" name="upliftedRequired" value="N"
					title="Uplifted" /></div>
				</td>
				<td class="iCargoTableDataTd"style="text-align: center;">
				<div><input type="checkbox" name="hNDRequired" value="N"
					title="HND" /></div>
				</td>
				<td class="iCargoTableDataTd" style="text-align: center;">
				<div><input type="checkbox" name="receivedFromFightRequired" value="N"
					title="receivedFromFightRequired" /></div>
				</td>
				<td class="iCargoTableDataTd" style="text-align: center;">
				<div><input type="checkbox" name="transferredFromOALRequired" value="N"
					title="transferredFromOALRequired" /></div>
				</td>
				<td class="iCargoTableDataTd" style="text-align: center;">
				<div><input type="checkbox" name="deliveredRequired" value="N"
					title="Delivered" /></div>
				</td>


			</tr>

			<!--template row ends-->
		</tbody>



	</table>
	</div>
	</div>
	</div>


	<div class="ic-foot-container">
	<div class="ic-row">
	<div class="ic-button-container paddR10"><ihtml:nbutton property="btSave"
		componentID="MAILTRACKING_DEFAULTS_MLDCONFIGURATION_BTN_SAVE"
		accesskey="S">
		<common:message key="mailtracking.defaults.mldconfiguration.btn.save" />
	</ihtml:nbutton> <ihtml:nbutton property="btClose"
		componentID="MAILTRACKING_DEFAULTS_MLDCONFIGURATION_BTN_CLOSE"
		accesskey="C">
		<common:message key="mailtracking.defaults.mldconfiguration.btn.close" />
	</ihtml:nbutton></div>
	</div>
	</div>





	</div>
</ihtml:form></div>


	</body>
</html:html>
