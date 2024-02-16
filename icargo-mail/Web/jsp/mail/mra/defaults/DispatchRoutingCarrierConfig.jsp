<%--/***********************************************************************
* Project	     	 : iCargo
* Module Code & Name 	 : mra
* File Name          	 : DespatchRoutingCarrierConfig.jsp
* Date                 	 : 10-Jan-2012
* Author(s)              : A-4452
*************************************************************************/
--%>
<%@ page language="java" %>
 <%@ include file="/jsp/includes/tlds.jsp"%>
 <%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRARoutingCarrierForm"%>



<html:html>

 <head>
		
		<%@ include file="/jsp/includes/customcss.jsp" %>
		
	

 	
 	<title><common:message bundle="despatchroutingcarrierconfigresources" key="mailtracking.mra.defaults.despatchroutingcarrierconfig.title" /></title>
 	<meta name="decorator" content="mainpanel">
 	<common:include type="script" src="/js/mail/mra/defaults/DispatchRoutingCarrierConfig_Script.jsp"/>
 </head>

<body class="ic-center" style="width:80%;">
	
	
	
	
<ihtml:form action="/mailtracking.mra.defaults.despatchroutingcarrierconfig.screenLoad.do">


<bean:define id="form"
	name="MRARoutingCarrierForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRARoutingCarrierForm"
	toScope="page" />

 <business:sessionBean id="routingCarrierVOs"
	 moduleName="mailtracking.mra.defaults"
	 screenID="mailtracking.mra.defaults.dispatchroutingcarrierconfig"
	 method="get"
	 attribute="RoutingCarrierVOs" />




<div id="mainDiv"  class="iCargoContent ic-masterbg" style="width:100%; z-index:1;overflow:auto;height:100%">
<!--hidden fields-->

<ihtml:hidden property="checkResult" />

<input type="hidden" name="currentDialogId" />
<input type="hidden" name="currentDialogOption" />

	<div class="ic-content-main">
		<span class="ic-page-title"><common:message key="mailtracking.mra.defaults.despatchroutingcarrierconfig.pagetitle" /></span>
		<div class="ic-head-container">
			<div class="ic-filter-panel">
				<div class="ic-input-container ic-label-30">
					<div class="ic-row">
						<div class="ic-input ic-split-17">
							<label class="ic-label-45">
								<common:message key="mailtracking.mra.defaults.dispatchroutingcarrierconfig.lbl.origin" scope="request"/>
							</label>
								<ihtml:text property="originCity" name="form" value="<%=form.getOriginCity()%>" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_ORIGIN" styleClass="iCargoTextFieldSmall"  maxlength="3"/>
								<div class= "lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" name="orgairportlov" id="orgairportlov" /></div>
						</div>
						<div class="ic-input ic-split-17">
							<label class="ic-label-45">
								<common:message key="mailtracking.mra.defaults.dispatchroutingcarrierconfig.lbl.destination" scope="request"/>
							</label>
							<ihtml:text property="destCity" name="form" value="<%=form.getDestCity()%>" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_DESTN" styleClass="iCargoTextFieldSmall"  maxlength="3"/>
							<div class= "lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" name="dstairportlov" id="dstairportlov" /></div>
						</div>
						<div class="ic-input ic-split-17">
							<label>
								<common:message key="mailtracking.mra.defaults.dispatchroutingcarrierconfig.lbl.carrier" scope="request"/>
							</label>
								<ihtml:text property="carrier" name="form" value="<%=form.getCarrier()%>" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_CARRIER" styleClass="iCargoTextFieldSmall"  maxlength="2"/>
								<div class= "lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" id="airlineLov" name="airlineLov" /></div>
						</div>
						<div class="ic-input ic-split-17">
							<label>
								<common:message key="mailtracking.mra.defaults.dispatchroutingcarrierconfig.lbl.validfrom" scope="request"/>
							</label>
							<ibusiness:calendar id="validFromDate"	property="validFromDate" value="<%=form.getValidFromDate()%>"
								componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_VALIDFRM" indexId="rowCount"
								type="image" maxlength="11"/>
						</div>
						<div class="ic-input ic-split-17">
							<label>
								<common:message key="mailtracking.mra.defaults.dispatchroutingcarrierconfig.lbl.validto" scope="request"/>
							</label>
								<ibusiness:calendar id="validToDate" property="validToDate" value="<%=form.getValidToDate()%>"
								componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_VALIDTO" indexId="rowCount"
								type="image" maxlength="11"/>
						</div>
						<div class="ic-input ic-split-13">
							<div class="ic-button-container">
								<ihtml:nbutton property="btnList" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_LIST" >
									<common:message key="mailtracking.mra.defaults.dispatchroutingcarrierconfig.button.list" />
								</ihtml:nbutton>

								<ihtml:nbutton property="btnClear" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_CLEAR" >
									<common:message key="mailtracking.mra.defaults.dispatchroutingcarrierconfig.button.clear" />
								</ihtml:nbutton>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="ic-main-container">
			<div class="ic-row">
				<div class="ic-col-100">
					<div class="ic-button-container paddR5">
						<a href="#" class="iCargoLink" id="addLink">
						   <common:message   key="mailtracking.mra.defaults.dispatchroutingcarrierconfig.add"/></a>
						 | <a href="#" class="iCargoLink" id="deleteLink">
						 <common:message   key="mailtracking.mra.defaults.dispatchroutingcarrierconfig.delete"/></a>
					</div>
				</div>
			</div>
			<div class="ic-row">
				<div id="div1" class="tableContainer" style="width:100%;height:700px">
					<table id="RoutingDetailsTable" style="width:100%;height:40px" class="fixed-header-table">
						<thead>
							<tr >
								<td style="width:2%;"  class="iCargoTableHeader"><input type="checkbox" value="checkbox"  name="checkAll"  /></td>
								<td style="width:10%;" class="iCargoTableHeader"><common:message key="mailtracking.mra.defaults.dispatchroutingcarrierconfig.lbl.origin" /> </td>
								<td style="width:10%;" class="iCargoTableHeader"><common:message key="mailtracking.mra.defaults.dispatchroutingcarrierconfig.lbl.destination" /></td>
								<td style="width:10%;" class="iCargoTableHeader"><common:message key="mailtracking.mra.defaults.dispatchroutingcarrierconfig.lbl.sectorfromown"/></td>
								<td style="width:10%;" class="iCargoTableHeader"><common:message key="mailtracking.mra.defaults.dispatchroutingcarrierconfig.lbl.sectortoown"/></td>
								<td style="width:10%;" class="iCargoTableHeader"><common:message key="mailtracking.mra.defaults.dispatchroutingcarrierconfig.lbl.sectorfromownoal"/></td>
								<td style="width:10%;" class="iCargoTableHeader"><common:message key="mailtracking.mra.defaults.dispatchroutingcarrierconfig.lbl.sectortooal"/></td>
								<td style="width:10%;" class="iCargoTableHeader"><common:message key="mailtracking.mra.defaults.dispatchroutingcarrierconfig.lbl.carrier"/></td>
								<td style="width:13%;" class="iCargoTableHeader"><common:message key="mailtracking.mra.defaults.dispatchroutingcarrierconfig.lbl.validfrom"/> </td>
								<td style="width:13%;" class="iCargoTableHeader"><common:message key="mailtracking.mra.defaults.dispatchroutingcarrierconfig.lbl.validto"/> </td>
							</tr>
						</thead>
						<tbody id="RoutingDetailsBody">
							<%int count=0;%>
							<logic:present name="routingCarrierVOs">
								<logic:iterate id="vo" name="routingCarrierVOs" indexId="rowCount"  type="com.ibsplc.icargo.business.mail.mra.defaults.vo.RoutingCarrierVO" >
									<%boolean showFlg=false;%>
									<logic:equal name="vo" property="operationFlag" value="">
										<%showFlg=true;%>
									</logic:equal>
									<logic:equal name="vo" property="operationFlag" value="U">
										<%showFlg=true;%>
									</logic:equal>
									<%if(showFlg){%>
										<html:hidden property="hiddenOpFlag" value="U"/>
										<tr >
											<td class="iCargoTableTd" style="text-align:center;">
												<input type="checkbox" name="checkBoxForRoutingCarrier"   value="<%=String.valueOf(count)%>" />
											</td>
											<logic:present name="vo" property="originCity">
												<td class="iCargoTableTd" >
												<div class="ic-input">
													<ihtml:text property="origincode" name="vo"  value="<%=vo.getOriginCity()%>" indexId="rowCount" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_ORIGIN" styleClass="iCargoTextFieldSmall" maxlength="3" readonly="true"/>
													<div class="lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" name="originLov" id="originLov<%=rowCount%>" disabled="true" /></div>
												</div>
												</td>
											</logic:present>
											<logic:notPresent name="vo" property="originCity">
												<td class="iCargoTableTd" >
												<div class="ic-input">
													<ihtml:text property="origincode" name="form" value=""  indexId="rowCount" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_ORIGIN" styleClass="iCargoTextFieldSmall" maxlength="3" readonly="true"/>
													<div class="lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" name="originLov" id="originLov<%=rowCount%>" disabled="true" /></div>
												</div>
												</td>
											</logic:notPresent>
											<logic:present name="vo" property="destCity">
												<td class="iCargoTableTd" >
												<div class="ic-input">
													<ihtml:text property="destcode" name="vo"  value="<%=vo.getDestCity()%>" indexId="rowCount"  componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_DESTN" styleClass="iCargoTextFieldSmall" maxlength="3" readonly="true"/>
													<div class="lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" name="destinationLov" id="destinationLov<%=rowCount%>" disabled="true"  /></div>
												</div>
												</td>
											</logic:present>
											<logic:notPresent name="vo" property="destCity" >
												<td class="iCargoTableTd" >
												<div class="ic-input">
													<ihtml:text property="destcode" name="form" value="" indexId="rowCount" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_DESTN" styleClass="iCargoTextFieldSmall" maxlength="3" />
													<div class="lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" name="destinationLov" id="destinationLov<%=rowCount%>" disabled="true"  /></div>
												</div>
												</td>
											</logic:notPresent>
											<logic:present name="vo" property="ownSectorFrm">
												<td class="iCargoTableTd" >
												<div class="ic-input">
													<ihtml:text property="ownSectorFrm" name="vo"  value="<%=vo.getOwnSectorFrm()%>" indexId="rowCount" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_OWNSECFRM" styleClass="iCargoTextFieldSmall" maxlength="3" readonly="true"/>
													<div class="lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" name="ownSectorFrmlov" id="ownSectorFrmlov<%=rowCount%>" disabled="true" /></div>
												</div>
												</td>
											</logic:present>
											<logic:notPresent name="vo" property="ownSectorFrm">
												<td class="iCargoTableTd" >
												<div class="ic-input">
													<ihtml:text property="ownSectorFrm" name="form" value=""  indexId="rowCount" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_OWNSECFRM" styleClass="iCargoTextFieldSmall" maxlength="3" readonly="true"/>
													<div class="lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" name="ownSectorFrmlov" id="ownSectorFrmlov<%=rowCount%>" disabled="true"  /></div>
												</div>
												</td>
											</logic:notPresent>
											<logic:present name="vo" property="ownSectorTo">
												<td class="iCargoTableTd" >
												<div class="ic-input">
													<ihtml:text property="ownSectorTo" name="vo"  value="<%=vo.getOwnSectorTo()%>" indexId="rowCount"  componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_OWNSECTO" styleClass="iCargoTextFieldSmall" maxlength="3" readonly="true" />
													<div class="lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" name="ownSectorTolov" id="ownSectorTolov<%=rowCount%>" disabled="true"  /></div>
												</div>
												</td>
											</logic:present>
											<logic:notPresent name="vo" property="ownSectorTo" >
												<td class="iCargoTableTd" >
												<div class="ic-input">
													<ihtml:text property="ownSectorTo" name="form" value=""  indexId="rowCount" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_OWNSECTO" styleClass="iCargoTextFieldSmall" maxlength="3" readonly="true"/>
													<div class="lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" name="ownSectorTolov" id="ownSectorTolov<%=rowCount%>" disabled="true" /></div>
												</div>
												</td>
											</logic:notPresent>
											<logic:present name="vo" property="oalSectorFrm">
												<td class="iCargoTableTd" >
												<div class="ic-input">
													<ihtml:text property="oalSectorFrm" name="vo"  value="<%=vo.getOalSectorFrm()%>" indexId="rowCount" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_OALSECFRM" styleClass="iCargoTextFieldSmall" maxlength="3" readonly="true"/>
													<div class="lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" name="oalSectorFrmlov" id="oalSectorFrmlov<%=rowCount%>" disabled="true" ></div>
												</div>
												</td>
											</logic:present>
											<logic:notPresent name="vo" property="oalSectorFrm">
												<td class="iCargoTableTd" >
												<div class="ic-input">
													<ihtml:text property="oalSectorFrm" name="form" value="" indexId="rowCount"  componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_OALSECFRM" styleClass="iCargoTextFieldSmall" maxlength="3" readonly="true" />
													<div class="lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" name="oalSectorFrmlov" id="oalSectorFrmlov<%=rowCount%>" disabled="true"  /></div>
												</div>
												</td>
											</logic:notPresent>
											<logic:present name="vo" property="oalSectorTo">
												<td class="iCargoTableTd" >
												<div class="ic-input">
													<ihtml:text property="oalSectorTo" name="vo"  value="<%=vo.getOalSectorTo()%>" indexId="rowCount" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_OALSECTO" styleClass="iCargoTextFieldSmall" maxlength="3" readonly="true"/>
													<div class="lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" name="oalSectorTolov" id="oalSectorTolov<%=rowCount%>" disabled="true" /></div>
												</div>
												</td>
											</logic:present>
											<logic:notPresent name="vo" property="oalSectorTo" >
												<td class="iCargoTableTd" >
												<div class="ic-input">
													<ihtml:text property="oalSectorTo" name="form" value="" indexId="rowCount" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_OALSECTO" styleClass="iCargoTextFieldSmall" maxlength="3" readonly="true"/>
													<div class="lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" name="oalSectorTolov" id="oalSectorTolov<%=rowCount%>" disabled="true"  /></div>
												</div>
												</td>
											</logic:notPresent>
											<logic:present name="vo" property="carrier">
												<td class="iCargoTableTd" >
												<div class="ic-input">
													<ihtml:text property="carriercode" name="vo"  value="<%=vo.getCarrier()%>" indexId="rowCount" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_CARRIER" styleClass="iCargoTextFieldSmall" maxlength="3" readonly="true"/>
													<div class="lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" name="airlineLov1" id="airlineLov1<%=rowCount%>" disabled="true"  /></div>
												</div>
												</td>
											</logic:present>
											<logic:notPresent name="vo" property="carrier" >
												<td class="iCargoTableTd" >
												<div class="ic-input">
													<ihtml:text property="carriercode" name="form" value="" indexId="rowCount" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_CARRIER" styleClass="iCargoTextFieldSmall" maxlength="3" readonly="true"/>
													<div class="lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" name="airlineLov1" id="airlineLov1<%=rowCount%>" disabled="true"  /></div>
												</div>
												</td>
											</logic:notPresent>
											<logic:present name="vo" property="validFrom">
												<td class="iCargoTableTd" >
													<ibusiness:calendar id="validFrom"
													property="validFrom" value="<%=vo.getValidFrom().toDisplayDateOnlyFormat()%>"
													componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_VALIDFRM" indexId="rowCount"
													type="image"
													maxlength="11"/>
												</td>
											</logic:present>
											<logic:notPresent name="vo" property="validFrom">
												<td class="iCargoTableTd" >
													<ibusiness:calendar id="validFrom"
													property="validFrom" indexId="rowCount"
													componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_VALIDFRM" 
													type="image"
													maxlength="11"
													  value=""/>
												</td>
											</logic:notPresent>
											<logic:present name="vo" property="validTo">
												<td class="iCargoTableTd" >
													<ibusiness:calendar id="validTo"
													property="validTo" value="<%=vo.getValidTo().toDisplayDateOnlyFormat()%>"
													componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_VALIDTO" indexId="rowCount"
													type="image"
													maxlength="11"/>
												</td>
											</logic:present>
											<logic:notPresent name="vo" property="validTo">
												<td class="iCargoTableTd" >
													<ibusiness:calendar id="validTo"
													property="validTo" indexId="rowCount"
													componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_VALIDTO"
													type="image"
													maxlength="11"
													  value=""/>
												</td>
											</logic:notPresent>
										</tr>
									<%}%>
										<logic:equal name="vo" property="operationFlag" value="I">
											<html:hidden property="hiddenOpFlag" value="I"/>
											<tr style="vertical-align:top;">
												<td class="iCargoTableTd" style="text-align:center"  >
													<input type="checkbox" name="checkBoxForRoutingCarrier"   value="<%=String.valueOf(count)%>" />
												</td>
												<logic:present name="vo" property="originCity">
													<td class="iCargoTableTd" >
													<div class="ic-input">
														<ihtml:text property="origincode" name="vo"  value="<%=vo.getOriginCity()%>" indexId="rowCount" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_ORIGIN" styleClass="iCargoTextFieldSmall" maxlength="3" />
														<div class="lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" name="originLov" id="originLov<%=rowCount%>"  /></div>
													</div>
													</td>
												</logic:present>
												<logic:notPresent name="vo" property="originCity">
													<td class="iCargoTableTd" >
													<div class="ic-input">
														<ihtml:text property="origincode" name="form" value=""  indexId="rowCount" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_ORIGIN" styleClass="iCargoTextFieldSmall" maxlength="3" />
														<div class="lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" name="originLov" id="originLov<%=rowCount%>"  /></div>
													</div>
													</td>
												</logic:notPresent>
												<logic:present name="vo" property="destCity">
													<td class="iCargoTableTd" >
													<div class="ic-input">
														<ihtml:text property="destcode" name="vo"  value="<%=vo.getDestCity()%>" indexId="rowCount" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_DESTN" styleClass="iCargoTextFieldSmall" maxlength="3" />
														<div class="lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" name="destinationLov" id="destinationLov<%=rowCount%>"  /></div>
													</div>
													</td>
												</logic:present>
												<logic:notPresent name="vo" property="destCity" >
													<td class="iCargoTableTd" >
													<div class="ic-input">
														<ihtml:text property="destcode" name="form" value=""  indexId="rowCount" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_DESTN" styleClass="iCargoTextFieldSmall" maxlength="3" />
														<div class="lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" name="destinationLov" id="destinationLov<%=rowCount%>"  /></div>
													</div>
													</div>
													</td>
												</logic:notPresent>
												<logic:present name="vo" property="ownSectorFrm">
													<td class="iCargoTableTd" >
													<div class="ic-input">
														<ihtml:text property="ownSectorFrm" name="vo"  value="<%=vo.getOwnSectorFrm()%>" indexId="rowCount" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_OWNSECFRM" styleClass="iCargoTextFieldSmall" maxlength="3" />
														<div class="lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" name="ownSectorFrmlov" id="ownSectorFrmlov<%=rowCount%>"  /></div>
													</div>
													</td>
												</logic:present>
												<logic:notPresent name="vo" property="ownSectorFrm">
													<td class="iCargoTableTd" >
													<div class="ic-input">
														<ihtml:text property="ownSectorFrm" name="form" value=""  indexId="rowCount" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_OWNSECFRM" styleClass="iCargoTextFieldSmall" maxlength="3" />
														<div class="lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" name="ownSectorFrmlov" id="ownSectorFrmlov<%=rowCount%>"  /></div>
													</div>
													</td>
												</logic:notPresent>
												<logic:present name="vo" property="ownSectorTo">
													<td class="iCargoTableTd" >
													<div class="ic-input">
														<ihtml:text property="ownSectorTo" name="vo"  value="<%=vo.getOwnSectorTo()%>"  indexId="rowCount" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_OWNSECTO" styleClass="iCargoTextFieldSmall" maxlength="3" />
														<div class="lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" name="ownSectorTolov" id="ownSectorTolov<%=rowCount%>"  /></div>
													</div>
													</td>
												</logic:present>
												<logic:notPresent name="vo" property="ownSectorTo" >
													<td class="iCargoTableTd" >
													<div class="ic-input">
														<ihtml:text property="ownSectorTo" name="form" value="" indexId="rowCount" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_OWNSECTO" styleClass="iCargoTextFieldSmall" maxlength="3" />
														<div class="lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" name="ownSectorTolov" id="ownSectorTolov<%=rowCount%>"  /></div>
													</div>
													</td>
												</logic:notPresent>
												<logic:present name="vo" property="oalSectorFrm">
													<td class="iCargoTableTd" >
													<div class="ic-input">
														<ihtml:text property="oalSectorFrm" name="vo"  value="<%=vo.getOalSectorFrm()%>" indexId="rowCount" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_OALSECFRM" styleClass="iCargoTextFieldSmall" maxlength="3" />
														<div class="lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" name="oalSectorFrmlov" id="oalSectorFrmlov<%=rowCount%>"  /></div>
													</div>
													</td>
												</logic:present>
												<logic:notPresent name="vo" property="oalSectorFrm">
													<td class="iCargoTableTd" >
													<div class="ic-input">
															<ihtml:text property="oalSectorFrm" name="form" value="" indexId="rowCount"  componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_OALSECFRM" styleClass="iCargoTextFieldSmall" maxlength="3" />
															<div class="lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" name="oalSectorFrmlov" id="oalSectorFrmlov<%=rowCount%>"  /></div>
													</div>
													</td>
												</logic:notPresent>
												<logic:present name="vo" property="oalSectorTo">
													<td class="iCargoTableTd" >
													<div class="ic-input">
														<ihtml:text property="oalSectorTo" name="vo"  value="<%=vo.getOalSectorTo()%>"  indexId="rowCount" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_OALSECTO" styleClass="iCargoTextFieldSmall" maxlength="3" />
														<div class="lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" name="oalSectorTolov" id="oalSectorTolov<%=rowCount%>"  /></div>
													</div>
													</td>
												</logic:present>
												<logic:notPresent name="vo" property="oalSectorTo" >
													<td class="iCargoTableTd" >
													<div class="ic-input">
														<ihtml:text property="oalSectorTo" name="form" value="" indexId="rowCount"  componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_OALSECTO" styleClass="iCargoTextFieldSmall" maxlength="3" />
														<div class="lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" name="oalSectorTolov" id="oalSectorTolov<%=rowCount%>"  /></div>
													</div>
													</td>
												</logic:notPresent>
												<logic:present name="vo" property="carrier">
													<td class="iCargoTableTd" >
													<div class="ic-input">
														<ihtml:text property="carriercode" name="vo"  value="<%=vo.getCarrier()%>"  indexId="rowCount" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_CARRIER" styleClass="iCargoTextFieldSmall" maxlength="3" />
														<div class="lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" name="airlineLov1" id="airlineLov1<%=rowCount%>"  /></div>
													</div>
													</td>
												</logic:present>
												<logic:notPresent name="vo" property="carrier" >
													<td class="iCargoTableTd" >
													<div class="ic-input">
														<ihtml:text property="carriercode" name="form" value=""  indexId="rowCount" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_CARRIER" styleClass="iCargoTextFieldSmall" maxlength="3" />
														<div class="lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" name="airlineLov1" id="airlineLov1<%=rowCount%>"   /></div>
													</div>
													</td>
												</logic:notPresent>
												<logic:present name="vo" property="validFrom">
													<td class="iCargoTableTd"  >
														<ibusiness:calendar id="validFrom"
														property="validFrom" value="<%=vo.getValidFrom().toDisplayDateOnlyFormat()%>"
														componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_VALIDFRM" indexId="rowCount"
														type="image"
														maxlength="11"/>
													</td>
												</logic:present>
												<logic:notPresent name="vo" property="validFrom">
													<td class="iCargoTableTd" >
														<ibusiness:calendar id="validFrom"
														property="validFrom" indexId="rowCount"
														componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_VALIDFRM" 
														type="image"
														maxlength="11"
														  value=""/>
													</td>
												</logic:notPresent>
												<logic:present name="vo" property="validTo">
													<td class="iCargoTableTd"  >
														<ibusiness:calendar id="validTo"
														property="validTo" value="<%=vo.getValidTo().toDisplayDateOnlyFormat()%>"
														componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_VALIDTO" indexId="rowCount" 
														type="image"
														maxlength="11"/>
													</td>
												</logic:present>
												<logic:notPresent name="vo" property="validTo">
													<td class="iCargoTableTd" >
														<ibusiness:calendar id="validTo"
														property="validTo" indexId="rowCount"
														componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_VALIDTO" 
														type="image"
														maxlength="11"
														  value=""/>
													</td>
												</logic:notPresent>
											</tr>
										</logic:equal>


										<logic:equal name="vo" property="operationFlag" value="D">
												<html:hidden property="hiddenOpFlag" value="D"/>
												 <input type="checkbox" name="checkBoxForRoutingCarrier" value="<%=String.valueOf(count)%>" />
												
												<ihtml:hidden property="origincode" value="<%=vo.getOriginCity()%>"/>
												<ihtml:hidden property="destcode" value="<%=vo.getDestCity()%>"/>
												<ihtml:hidden property="carriercode" value="<%=vo.getCarrier()%>"/>
												<ihtml:hidden property="ownSectorFrm" value="<%=vo.getOwnSectorFrm()%>"/>
												<ihtml:hidden property="ownSectorTo" value="<%=vo.getOwnSectorTo()%>"/>
												<ihtml:hidden property="oalSectorFrm" value="<%=vo.getOalSectorFrm()%>"/>
												<ihtml:hidden property="oalSectorTo" value="<%=vo.getOalSectorTo()%>"/>
												<ihtml:hidden property="validFrom" value="<%=vo.getValidFrom().toDisplayDateOnlyFormat()%>"/>
												<ihtml:hidden property="validTo" value="<%=vo.getValidTo().toDisplayDateOnlyFormat()%>"/>
										</logic:equal>
									<%count++;%>
								</logic:iterate>
							</logic:present>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div class="ic-foot-container">
			<div class="ic-row">
			<div class="ic-button-container paddR5">
				<ihtml:nbutton property="btnSave" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_SAVE" >
					<common:message key="mailtracking.mra.defaults.dispatchroutingcarrierconfig.button.save" />
				</ihtml:nbutton>
				<ihtml:nbutton property="btnClose" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_CLOSE" >
					<common:message key="mailtracking.mra.defaults.dispatchroutingcarrierconfig.button.close" />
				</ihtml:nbutton>
			</div>
		</div>
		</div>
	</div>
</div>

	</ihtml:form>
		

	</body>
</html:html>
