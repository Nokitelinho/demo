
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import = "java.util.Calendar" %>
<%@ page import = "com.ibsplc.icargo.framework.util.FormatConverter" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

<%@ page import="org.apache.struts.action.ActionMessages"%>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.ListEmbargoRulesForm"%>


	
<bean:define id="form" name="ListEmbargoRulesForm" toScope="page" type="com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.ListEmbargoRulesForm"/>	
	
<business:sessionBean id="levelCode"	moduleName="reco.defaults"
										screenID="reco.defaults.listembargo"
										method="get"
										attribute="levelCode" />
<business:sessionBean id="globalParameters"
										moduleName="reco.defaults"
										screenID="reco.defaults.listembargo"
										method="get"
										attribute="globalParameters" />
<business:sessionBean id="status"
										moduleName="reco.defaults"
										screenID="reco.defaults.listembargo"
										method="get"
										attribute="status" />
<business:sessionBean id="embargoDetails"
										moduleName="reco.defaults"
										screenID="reco.defaults.listembargo"
										method="get"
										attribute="EmabrgoDetailVOs" />

<business:sessionBean id="ruleType"
										moduleName="reco.defaults"
										screenID="reco.defaults.listembargo"
										method="get"
										attribute="ruleType" />
<business:sessionBean id="geographicLevelType" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="geographicLevelType"/>
<business:sessionBean id="categories" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="categoryTypes" />
<business:sessionBean id="complianceTypes" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="complianceTypes" />
<business:sessionBean id="applicableTransactionsList" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="applicableTransactions" />
<business:sessionBean id="embargoParameters" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="embargoParameters" />
<business:sessionBean id="flightTypes" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="flightTypes" />
<business:sessionBean id="uldPos" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="uldPos" />
<business:sessionBean id="mailClass" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="mailClass" />
<business:sessionBean id="mailCategory" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="mailCategory" />
<business:sessionBean id="mailSubClassGrp" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="mailSubClassGrp" />
<business:sessionBean id="serviceCargoClass" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="serviceCargoClass" />
<business:sessionBean id="shipmentType" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="shipmentType" />
<business:sessionBean id="serviceType" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="serviceType" />
<business:sessionBean id="serviceTypeForTechnicalStop" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="serviceTypeForTechnicalStop" />
<business:sessionBean id="unPg" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="unPg" />
<business:sessionBean id="subRisk" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="subRisk" />


	<div class="ic-row">
						<div class="ic-input ic-split-15">
							<label><common:message  key="listembargo.fromdate"/></label>
							<ibusiness:calendar id="startDate" property="startDate" type="image" componentID="CMP_Reco_Defaults_ListEmbargo_StartDate" style="width:47%" value="<%=form.getStartDate()%>"/>
						</div>
						<div class="ic-input ic-split-15">
						<label><common:message  key="listembargo.todate"/></label>
						<ibusiness:calendar id="endDate" componentID="CMP_Reco_Defaults_ListEmbargo_EndDate" property="endDate" style="width:47%"  type="image" value="<%=form.getEndDate()%>"/>
						</div>
						<div class="ic-input ic-split-15">
							<label><common:message  key="listembargo.parametercode"/></label>
							<ihtml:select property="parameterCode" style="width:50%" styleClass="parameterCode" componentID="CMP_Reco_Defaults_ListEmbargo_ParameterCode"  onchange="javasciript:changeParameterValues(this)">
								<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
								<logic:present name="embargoParameters">
									<logic:iterate id="embargoParameter" name="embargoParameters">
										<bean:define id="embargoParameterCode" name="embargoParameter" property="fieldValue" />
										<html:option value="<%=(String)embargoParameterCode %>"><bean:write name="embargoParameter" property="fieldDescription" /></html:option>
									</logic:iterate>
								</logic:present>
							</ihtml:select>
						</div>
						<div class="ic-input ic-split-15">
						
							  <jsp:include page="ListEmbargo_Tableparam2.jsp"/>
						</div>
							<div id="parameter_control_GOODS" class="parameter_control" style="display:none">
								<label><common:message key="listembargo.parametervalue"/></label>
								<ihtml:text property="natureOfGoods"  componentID="CMP_Reco_Defaults_ListEmbargo_Filter_NatureOfGoods" />
							</div>
							<div id="parameter_control_PKGINS" class="parameter_control" style="display:none">
								<label><common:message key="listembargo.parametervalue"/></label>
								<ihtml:text property="packingInstruction"  componentID="CMP_Reco_Defaults_ListEmbargo_Filter_PI" />
							</div>
							<div id="parameter_control_UNNUM" class="parameter_control" style="display:none">
								<label><common:message key="listembargo.parametervalue"/></label>
								<ihtml:text property="unNumber"   onkeyup="validateaplphanum(this);" componentID="CMP_Reco_Defaults_ListEmbargo_Filter_UNNumber" />
							</div>
							<div id="parameter_control_DAT" class="parameter_control" style="display:none">
								<label><common:message key="listembargo.parametervalue"/></label>
								<ihtml:text property="dates"  componentID="CMP_Reco_Defaults_ListEmbargo_Filter_Dates" onblur="restrictValues(this);" onchange="dateCheck(this);"/>
							</div>
							<div id="parameter_control_TIM" class="parameter_control" style="display:none">
								<label><common:message key="listembargo.parametervalue"/></label>
								<ihtml:text property="time"  componentID="CMP_Reco_Defaults_ListEmbargo_Time" onchange="validateTime(this);"/>
							</div>
							<div id="parameter_control_AWBPRE" class="parameter_control" style="display:none">
								<label><common:message key="listembargo.parametervalue"/></label>
								<ihtml:text property="awbPrefix" onblur="restrictValues(this);" onkeyup="validateNumber(this);" componentID="CMP_Reco_Defaults_ListEmbargo_Filter_AWBPrefix" maxlength="3" />
							</div>
							<div id="parameter_control_WGT" class="parameter_control" style="display:none">
								<div class="ic-row">
									<label><common:message key="listembargo.parametervalue"/></label>
									<ihtml:text property="weight"  onkeyup="restrictToSingleNumber(this);"   componentID="CMP_Reco_Defaults_ListEmbargo_Filter_Weight" />
								</div>
							</div>
							<div id="parameter_control_NUMSTP" class="parameter_control" style="display:none">
								<div class="ic-row">
									<label><common:message key="listembargo.parametervalue"/></label>
									<ihtml:text property="numberOfstops"  onkeyup="restrictToSingleNumber(this);"   componentID="CMP_Reco_Defaults_ListEmbargo_Filter_NumberOfStops" />
								</div>
							</div>
							<div id="parameter_control_VOL" class="parameter_control" style="display:none">
								<div class="ic-row">
									<label><common:message key="listembargo.parametervalue"/></label>
									<ihtml:text property="volume"  onkeyup="restrictToSingleNumber(this);"   componentID="CMP_Reco_Defaults_ListEmbargo_Filter_Volume" />
								</div>
							</div>
							<div id="parameter_control_LEN" class="parameter_control" style="display:none">
								<div class="ic-row">
									<label><common:message key="listembargo.parametervalue"/></label>
									<ihtml:text property="parameterLength"  onkeyup="validateNumber(this);restrictToSingleNumber(this);" componentID="CMP_Reco_Defaults_ListEmbargo_Filter_Length"  />
								</div>
							</div>
							<div id="parameter_control_WID" class="parameter_control" style="display:none">
								<div class="ic-row">
								<label><common:message key="listembargo.parametervalue"/></label>
									<ihtml:text property="width"   onkeyup="validateNumber(this);restrictToSingleNumber(this);" componentID="CMP_Reco_Defaults_ListEmbargo_Filter_Width" />
								</div>
							</div>
							<div id="parameter_control_HGT" class="parameter_control" style="display:none">
								<div>
									<label><common:message key="listembargo.parametervalue"/></label>
									<ihtml:text property="height"  onkeyup="validateNumber(this);restrictToSingleNumber(this);" componentID="CMP_Reco_Defaults_ListEmbargo_Filter_Height"  />
								</div>
							</div>
							<div id="parameter_control_UNCLS" class="parameter_control" style="display:none">
								<div>
									<label><common:message key="listembargo.parametervalue"/></label>
									<ihtml:text property="unIds" componentID="CMP_Reco_Defaults_ListEmbargo_UNIDS"  />
								</div>
							</div>
							<div id="parameter_control_FLTNUM" class="parameter_control" style="display:none">
								<label><common:message key="listembargo.parametervalue"/></label>
								<ihtml:text property="carrierCode"  style="width:15%" maxlength="3" onkeyup="validateaplphanum(this);" onblur="restrictSpecialChar(this);" componentID="CMP_Reco_Defaults_ListEmbargo_Filter_FlightCarrierCode" />&nbsp
								<ihtml:text property="flightNumber"  style="width:25%" maxlength="5" onkeyup="validateaplphanum(this);" onblur="restrictSpecialChar(this);checkFlightNumber(this);" componentID="CMP_Reco_Defaults_ListEmbargo_Filter_FlightNumber" />
							</div>

							<div id="parameter_control_FLTTYP" class="parameter_control" style="display:none">	
								<label><common:message key="listembargo.parametervalue"/></label>
								<ihtml:select property="flightType"  componentID="CMP_Reco_Defaults_ListEmbargo_Filter_FlightType" >
									<logic:present name="flightTypes">
										<logic:iterate id="type" name="flightTypes">
											<bean:define id="typevalue" name="type" property="fieldValue" />
											<html:option value="<%=(String)typevalue %>"><bean:write name="type" property="fieldDescription" /></html:option>
										</logic:iterate>
									</logic:present>  		
								</ihtml:select>
							</div>
							<div id="parameter_control_SPLIT" class="parameter_control" style="display:none">	
								<label><common:message key="listembargo.parametervalue"/></label>
								<ihtml:select property="splitIndicator"  componentID="CMP_Reco_Defaults_ListEmbargo_Filter_SplitIndicator" >
								<html:option value="Y">Y</html:option>
								</ihtml:select>
							</div>
							<div id="parameter_control_FLTOWR" class="parameter_control" style="display:none">	
								<label><common:message key="listembargo.parametervalue"/></label>
								<ihtml:text property="flightOwner"  onblur="restrictValues(this);" componentID="CMP_Reco_Defaults_ListEmbargo_Filter_FlightOwner" /><div class="lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" id="flghtOwnr"  class="flghtOwnr"  onclick="displayLOV('showAirline.do','N','Y','showAirline.do',flightOwner,'Airline','1','flightOwner','',0);"/></div>
							</div>
							<div id="parameter_control_CAR" class="parameter_control" style="display:none">
								<label><common:message key="listembargo.parametervalue"/></label>
								<ihtml:text property="carrier"  onblur="restrictValues(this);" componentID="CMP_Reco_Defaults_ListEmbargo_Filter_Carrier" /><div class="lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" width="22" onclick="displayLOV('showAirline.do','N','Y','showAirline.do',carrier,'carrier','1','carrier','',0);" height="22" id="carrier"/></div>
							</div>
							<div id="parameter_control_COM" class="parameter_control" style="display:none">	
								<label><common:message key="listembargo.parametervalue"/></label>
								<ihtml:text property="commodity"  componentID="CMP_Reco_Defaults_ListEmbargo_Filter_Commodity" /><div class="lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" width="22" onclick="displayLOV('showCommodity.do','N','Y','showCommodity.do',commodity,'commodity','1','commodity','',0);" height="22" id="commodity"/></div>
							</div>
							<div id="parameter_control_ULDPOS" class="parameter_control" style="display:none">	
								<label><common:message key="listembargo.parametervalue"/></label>
								<ihtml:select property="uldPos" componentID="CMP_Reco_Defaults_ListEmbargo_Filter_UldPos" >
									<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
									<logic:present name="uldPos">
										<logic:iterate id="type" name="uldPos">
											<bean:define id="typevalue" name="type" property="fieldValue" />
											<html:option value="<%=(String)typevalue %>"><bean:write name="type" property="fieldDescription" /></html:option>
										</logic:iterate>
									</logic:present>		
								</ihtml:select>
							</div>	
							<div id="parameter_control_ULDTYP" class="parameter_control" style="display:none">	
								<label><common:message key="listembargo.parametervalue"/></label>
								<ihtml:text property="uldTyp"  componentID="CMP_Reco_Defaults_ListEmbargo_Filter_UldTypeCode" /><div class="lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" width="22" onclick="displayLOV('showUld.do','N','Y','showUld.do',uldTyp,'uldTyp','1','uldTyp','',0);" height="22" id="uldTyp"/></div>
							</div>
							<div id="parameter_control_PAYTYP" class="parameter_control" style="display:none">	
								<label><common:message key="listembargo.parametervalue"/></label>
								<ihtml:text property="paymentType"  componentID="CMP_Reco_Defaults_ListEmbargo_Filter_PaymentType" /><div class="lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" width="22" onclick="displayOneTimeLOV('screenloadOneTime.do','N','Y','screenloadOneTime.do','','','Payment Type','1','paymentType','paymentType',0,'reco.defaults.paymenttype','');" height="22" id="paymentType"/></div>
							</div>
							<div id="parameter_control_PRD" class="parameter_control" style="display:none">	
								<label><common:message key="listembargo.parametervalue"/></label>
								<ihtml:text property="productCode"  componentID="CMP_Reco_Defaults_ListEmbargo_Filter_Product" onblur="restrictValues(this);" /><div class="lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" width="22" onclick="displayProductLov('products.defaults.screenloadProductLov.do',productCode.value,'productCode','','','0','productCode','Y',0)" height="22" id="productCode"/></div>
							</div>
							<div id="parameter_control_SCC" class="parameter_control" style="display:none">	
								<label><common:message key="listembargo.parametervalue"/></label>
								<ihtml:text property="scc" onblur="restrictValues(this);" componentID="CMP_Reco_Defaults_ListEmbargo_Filter_SCC" /><div class="lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" width="22" onclick="displayLOV('showScc.do','N','Y','showScc.do',scc,'scc','1','scc','',0);" height="22" id="scc"/></div>
							</div>
							<div id="parameter_control_SCCGRP" class="parameter_control" style="display:none">	
								<label><common:message key="listembargo.parametervalue"/></label>
								<ihtml:text property="sccGroup" componentID="CMP_Reco_Defaults_ListEmbargo_Filter_SccGroup" /><div class="lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" width="22" onclick="showParameterGroupLov('shared.generalmastergrouping.screenloadgroupnamelov.do','N',sccGroup,'SCCGRP','EMB','sccGroup','1',0);" height="22" id="sccGroup"/></div>
							</div>
							<div id="parameter_control_AGT" class="parameter_control" style="display:none">	
								<label><common:message key="listembargo.parametervalue"/></label>
								<ihtml:text property="agentCode" onblur="restrictValues(this);" componentID="CMP_Reco_Defaults_ListEmbargo_Filter_AGT" />
								<div class="lovImg"><img  src="<%=request.getContextPath()%>/images/lov.png"  onclick= "showAgentLOV(this)" id="agentLOV" width="20" height="20" /></div>
								
							</div>
							<div id="parameter_control_AGTGRP" class="parameter_control" style="display:none">	
								<label><common:message key="listembargo.parametervalue"/></label>
								<ihtml:text property="agentGroup" componentID="CMP_Reco_Defaults_ListEmbargo_Filter_AgtGroup" />
								<div class="lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" width="22" onclick="showParameterGroupLov('shared.generalmastergrouping.screenloadgroupnamelov.do','N',agentGroup,'AGTGRP','EMB','agentGroup','1',0);" height="22" id="agentGroup"/></div>
							</div>
							<div id="parameter_control_MALCAT" class="parameter_control" style="display:none">	
								<label><common:message key="listembargo.parametervalue"/></label>
								<ihtml:select property="mailCategory"  componentID="CMP_Reco_Defaults_ListEmbargo_Filter_MailCategory" >
									<logic:present name="mailCategory">
										<logic:iterate id="mailCat" name="mailCategory">
											<bean:define id="malcat" name="mailCat" property="fieldValue" />
											<html:option value="<%=(String)malcat %>"><bean:write name="mailCat" property="fieldDescription" /></html:option>
										</logic:iterate>
									</logic:present> 													
								</ihtml:select>
							</div>
							<div id="parameter_control_MALCLS" class="parameter_control" style="display:none">	
								<label><common:message key="listembargo.parametervalue"/></label>
								<ihtml:select property="mailClass"  componentID="CMP_Reco_Defaults_ListEmbargo_Filter_MailClass" >
									<logic:present name="mailClass">
										<logic:iterate id="mailClas" name="mailClass">
											<bean:define id="malcls" name="mailClas" property="fieldValue" />
											<html:option value="<%=(String)malcls %>"><bean:write name="mailClas" property="fieldDescription" /></html:option>
										</logic:iterate>
									</logic:present> 													
								</ihtml:select>
							</div>
							<div id="parameter_control_MALSUBCLSGRP" class="parameter_control" style="display:none">	
								<label><common:message key="listembargo.parametervalue"/></label>
								<ihtml:select property="mailSubClsGrp"  componentID="CMP_Reco_Defaults_ListEmbargo_Filter_MailSubClsGrp" >
									<logic:present name="mailSubClassGrp">
										<logic:iterate id="mailsubclsgrp" name="mailSubClassGrp">
											<bean:define id="malsbclsg" name="mailsubclsgrp" property="fieldValue" />
											<html:option value="<%=(String)malsbclsg %>"><bean:write name="mailsubclsgrp" property="fieldDescription" /></html:option>
										</logic:iterate>
									</logic:present> 															
								</ihtml:select>
							</div>	
							<div id="parameter_control_MALSUBCLS" class="parameter_control" style="display:none">	
								<label><common:message key="listembargo.parametervalue"/></label>
								<ihtml:text property="mailSubClass"  componentID="CMP_Reco_Defaults_ListEmbargo_Filter_MailSubclass" />
								<div class="lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" width="22" onclick="displayLOV('mailtracking.defaults.subclaslov.list.do','N','Y','mailtracking.defaults.subclaslov.list.do',mailSubClass,'mailSubClass','1','mailSubClass','',0);"  height="22" id="mailSubClass"/></div>
							</div>
								<div id="parameter_control_ARLGRP" class="parameter_control" style="display:none">	
								<label><common:message key="listembargo.parametervalue"/></label>
								<ihtml:text property="airlineGroup" componentID="CMP_Reco_Defaults_ListEmbargo_Filter_AirlineGroup" /><div class="lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" width="22" onclick="showParameterGroupLov('shared.generalmastergrouping.screenloadgroupnamelov.do','N',airlineGroup,'ARLGRP','EMB','airlineGroup','1',0);" height="22" id="airlineGroup"/></div>
							</div>
							<div id="parameter_control_ULD" class="parameter_control" style="display:none">	
								<label><common:message key="listembargo.parametervalue"/></label>
								<ihtml:text property="uldType"  componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_Uld" />
									<div class= "lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" width="22" onclick="displayLOV('shared.uld.findUldTypeAndGroup.do','N','Y','shared.uld.findUldTypeAndGroup.do',uldType,'uldType','1','uldType','',0,null,null,null,null,10);" height="22" id="uldType"/></div>	
							</div>
							<div id="parameter_control_ACRTYPGRP" class="parameter_control" style="display:none">	
								<label><common:message key="listembargo.parametervalue"/></label>
								<ihtml:text property="aircraftGroupType"  componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_AircraftGroupType" />
									<div class= "lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" width="22" onclick="showParameterGroupLov('shared.generalmastergrouping.screenloadgroupnamelov.do','Y',aircraftGroupType,'ACRTYPGRP','EMB','aircraftGroupType','1',0);" height="22" id="aircraftGroupType"/></div>	
							</div>
							<div id="parameter_control_ACRTYP" class="parameter_control" style="display:none">	
								<label><common:message key="listembargo.parametervalue"/></label>
								<ihtml:text property="aircraftType"  componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_AircraftType" />
									<div class= "lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" width="22" onclick="displayLOV('showAircraft.do','Y','Y','showAircraft.do',aircraftType,'aircraftType','1','aircraftType','',0,null,null,null,null,10);" height="22" id="aircraftType"/></div>	
							</div>
							<div id="parameter_control_SRVCRGCLS" class="parameter_control" style="display:none">
								<label><common:message key="listembargo.parametervalue"/></label>							
								<ihtml:select property="serviceCargoClass"  componentID="CMP_Reco_Defaults_ListEmbargo_Filter_ServiceCargoClass">
									<logic:present name="serviceCargoClass">
										<logic:iterate id="srvClas" name="serviceCargoClass">
											<bean:define id="srvcls" name="srvClas" property="fieldValue" />
											<html:option value="<%=(String)srvcls %>"><bean:write name="srvClas" property="fieldDescription" /></html:option>
										</logic:iterate>
									</logic:present> 						
								</ihtml:select>
							</div>
							<div id="parameter_control_ACRCLS" class="parameter_control" style="display:none">	
								<label><common:message key="listembargo.parametervalue"/></label>
								<ihtml:text property="aircraftClassification" componentID="CMP_Reco_Defaults_ListEmbargo_Filter_AircraftClassification" onblur="restrictValues(this);" />
								<div class= "lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" width="20" onclick="displayOneTimeLOV('screenloadOneTime.do','N','Y','screenloadOneTime.do','','','Aircraft Classification','1','aircraftClassification','aircraftClassification',0,'shared.aircraft.aircraftClassification','');" height="20" id="aircraftClassification"/></div>
							</div>
							<div id="parameter_control_SHP" class="parameter_control" style="display:none">		
								<label><common:message key="listembargo.parametervalue"/></label>
								<ihtml:text property="shipperCode" componentID="CMP_Reco_Defaults_ListEmbargo_Shipper" onblur="restrictValues(this);"/>
							   <div class="lovImg"><img  src="<%=request.getContextPath()%>/images/lov.png"  onclick= "showShipperLOV(this)" id="shipperLOV" width="20" height="20" /></div>
							   
							</div>
							<div id="parameter_control_SHPGRP" class="parameter_control" style="display:none">	
								<label><common:message key="listembargo.parametervalue"/></label>
								<ihtml:text property="shipperGroup" componentID="CMP_Reco_Defaults_ListEmbargo_ShipperGroup" onblur="restrictSpecialChar(this);" />
								<div class= "lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" width="20" onclick="showParameterGroupLov('shared.generalmastergrouping.screenloadgroupnamelov.do','Y',shipperGroup,'SHPCODGRP','EMB','shipperGroup','1',0);" height="20" id="shipperGroup"/></div>
							</div>
							<div id="parameter_control_CNS" class="parameter_control" style="display:none">
								<label><common:message key="listembargo.parametervalue"/></label>
								<ihtml:text property="consigneeCode" componentID="CMP_Reco_Defaults_ListEmbargo_Consignee" onblur="restrictValues(this);"/>
							   <div class="lovImg"><img  src="<%=request.getContextPath()%>/images/lov.png"  onclick= "showConsigneeLOV(this)" id="consigneeLOV" width="20" height="20" /></div>
							</div>
							<div id="parameter_control_CNSGRP" class="parameter_control" style="display:none">		
								<label><common:message key="listembargo.parametervalue"/></label>
								<ihtml:text property="consigneeGroup" componentID="CMP_Reco_Defaults_ListEmbargo_ConsigneeGroup" onblur="restrictSpecialChar(this);"/>
								<div class= "lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" width="20" onclick="showParameterGroupLov('shared.generalmastergrouping.screenloadgroupnamelov.do','Y',consigneeGroup,'CNSGRP','EMB','consigneeGroup','1',0);" height="20" id="consigneeGroup"/></div>
							</div>
							<div id="parameter_control_SHPTYP" class="parameter_control" style="display:none">
								<label><common:message key="listembargo.parametervalue"/></label>
								<ihtml:select property="shipmentType"  componentID="CMP_Reco_Defaults_ListEmbargo_Filter_ShipmentType">
									<logic:present name="shipmentType">
										<logic:iterate id="shipmntTyp" name="shipmentType">
											<bean:define id="shptyp1" name="shipmntTyp" property="fieldValue" />
											<html:option value="<%=(String)shptyp1%>"><bean:write name="shipmntTyp" property="fieldDescription" /></html:option>
										</logic:iterate>
									</logic:present> 						
								</ihtml:select>
							</div>
							<div id="parameter_control_SRVCTYP" class="parameter_control" style="display:none">
								<label><common:message key="listembargo.parametervalue"/></label>
								<ihtml:select property="serviceType"  componentID="CMP_Reco_Defaults_ListEmbargo_Filter_ServiceType">
									<logic:present name="serviceType">
										<logic:iterate id="srvcTyp" name="serviceType">
											<bean:define id="srvctyp1" name="srvcTyp" property="fieldValue" />
											<html:option value="<%=(String)srvctyp1%>"><bean:write name="srvcTyp" property="fieldValue" /></html:option>
										</logic:iterate>
									</logic:present> 						
								</ihtml:select>
							</div>
							<div id="parameter_control_SRVCTYPFRTECSTP" class="parameter_control" style="display:none">
								<label><common:message key="listembargo.parametervalue"/></label>
								<ihtml:select property="serviceTypeForTechnicalStop"  componentID="CMP_Reco_Defaults_ListEmbargo_Filter_ServiceTypeForTechnicalStop">
									<logic:present name="serviceTypeForTechnicalStop">
										<logic:iterate id="srvcTypForTechnicalStop" name="serviceTypeForTechnicalStop">
											<bean:define id="srvcTypForTechnicalStop2" name="srvcTypForTechnicalStop" property="fieldValue" />
											<html:option value="<%=(String)srvcTypForTechnicalStop2%>"><bean:write name="srvcTypForTechnicalStop" property="fieldValue" /></html:option>
										</logic:iterate>
									</logic:present> 						
								</ihtml:select>
							</div>
							<div id="parameter_control_PKGGRP" class="parameter_control" style="display:none">
								<label><common:message key="listembargo.parametervalue"/></label>
								<ihtml:select property="unPg"  componentID="CMP_Reco_Defaults_ListEmbargo_Filter_UnPg">
									<logic:present name="unPg">
										<logic:iterate id="pkggrp" name="unPg">
											<bean:define id="pkggrp1" name="pkggrp" property="fieldValue" />
											<html:option value="<%=(String)pkggrp1%>"><bean:write name="pkggrp" property="fieldValue" /></html:option>
										</logic:iterate>
									</logic:present> 						
								</ihtml:select>
							</div>
							<div id="parameter_control_SUBRSK" class="parameter_control" style="display:none">
								<label><common:message key="listembargo.parametervalue"/></label>
								<ihtml:text property="subRisk" onkeyup="validateNumber(this);" componentID="CMP_Reco_Defaults_ListEmbargo_Filter_SubRisk" />
							</div>
							<div id="parameter_control_CNSL" class="parameter_control" style="display:none">	
								<label><common:message key="listembargo.parametervalue"/></label>
								<ihtml:select property="consol"  componentID="CMP_Reco_Defaults_ListEmbargo_Filter_Consol" >
								<html:option value="Y">Yes</html:option>
								<html:option value="N">No</html:option>
								</ihtml:select>
							</div>
							<ihtml:hidden property="parameterValue"/>
						</div>
						<div class="ic-input ic-split-19">
							<label><common:message  key="listembargo.applicabletransactions"/></label>
										<ihtml:select style="width:50%"
											property="applicableTransactions" 
											componentID="CMP_Reco_Defaults_ListEmbargo_ApplicableTransactions" >
											 <ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
											<logic:present name="applicableTransactionsList">								    
													  <logic:iterate id="applicableTransactions" name="applicableTransactionsList">
														   <bean:define id="applicabletransactionsvalue" name="applicableTransactions" property="fieldValue" />
														   <html:option value="<%=(String)applicabletransactionsvalue %>"><bean:write name="applicableTransactions" property="fieldDescription" /></html:option>
													</logic:iterate>
											</logic:present>
										</ihtml:select>		
						</div>
					