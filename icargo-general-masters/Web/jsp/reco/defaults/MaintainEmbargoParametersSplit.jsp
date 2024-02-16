<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.business.reco.defaults.vo.EmbargoParameterVO"%>

<bean:define id="parCode" name="_parCode" />
<bean:define id="parameterValues" name="_parameterValues" />
<bean:define id="embargoParameterVO" name="_embargoParameterVO" />
<business:sessionBean id="flightTypes" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="flightTypes" />
<business:sessionBean id="serviceCargoClass" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="serviceCargoClass" />
<business:sessionBean id="mailClass" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="mailClass" />
<business:sessionBean id="mailCategory" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="mailCategory" />
<business:sessionBean id="mailSubClassGrp" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="mailSubClassGrp" />
<business:sessionBean id="shipmentType" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="shipmentType" />
<business:sessionBean id="serviceType" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="serviceType" />
<business:sessionBean id="uldPositions" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="uldPos" />
<business:sessionBean id="serviceTypeForTechnicalStop" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="serviceTypeForTechnicalStop" />
<business:sessionBean id="unPg" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="unPg" />
<business:sessionBean id="subRisk" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="subRisk" />

<% if ("CAR".equals(parCode)){%>
									<ihtml:text property="carrier" value="<%=(String)parameterValues %>" onblur="restrictValues(this);" styleClass="parameter_value" componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_Carrier" />
									<div class= "lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="20" onclick="displayLOV('showAirline.do','Y','Y','showAirline.do',carrier,'carrier','1','carrier','',0,null,null,null,null,10);" height="20" id="carrier"/></div>										
								<%}else if("DOW".equals(parCode)){%>
								<ibusiness:frequency id="daysOfWeek" property="daysOfOperation" value=""  componentID="CMP_Reco_Defaults_MaintainEmbargo_DaysOfWeek"  name="form" startDay="Mon" classForText="iCargoLabelRightAligned" />
								
								<% }else if ("GOODS".equals(parCode)){%>
								<ihtml:text property="defaultText"  value="<%=(String)parameterValues%>" onblur="restrictValues(this);"  componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_Default_Text" />
								<% }else if ("PKGINS".equals(parCode)){%>
								<ihtml:text property="packingInstruction"  value="<%=(String)parameterValues%>"  
								componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_PI" />
									<% }else if ("UNNUM".equals(parCode)){%>
								<ihtml:text property="unNumber" value="<%=(String)parameterValues %>" onkeyup="validateNumber(this);" onblur="restrictValues(this);" componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_UNNumber" />
								<% }else if ("WGT".equals(parCode)){%>
								<ihtml:text property="weight" value="<%=(String)parameterValues %>" onkeyup="restrictToSingleNumber(this);" componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_Weight" />
								<% }else if ("NUMSTP".equals(parCode)){%>
								<ihtml:text property="numberOfStops" value="<%=(String)parameterValues %>" onkeyup="restrictToSingleNumber(this);" componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_NumberOfStops" />
								<% }else if ("VOL".equals(parCode)){%>
								<ihtml:text property="volume" value="<%=(String)parameterValues %>" onkeyup="restrictToSingleNumber(this);" componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_Volume" />
								<% }else if ("AWBPRE".equals(parCode)){%>
								<ihtml:text property="awbPrefix" value="<%=(String)parameterValues %>" onkeyup="validateNumber(this);restrictAndAppendTokken(this,'4')" onblur="restrictValues(this);restrictLength(this);" componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_AWBPrefix" />
								<% }else if ("DAT".equals(parCode)){%>
								<ihtml:text property="dates" value="<%=(String)parameterValues %>" onblur="restrictValues(this);" componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_Dates" onchange="dateCheck(this);"/><common:message  key="maintainembargo.dateformat"/>
								<% }else if ("LEN".equals(parCode)){%>
								<ihtml:text property="parameterLength" value="<%=(String)parameterValues %>" onkeyup="validateNumber(this);restrictToSingleNumber(this);" componentID="CMP_Reco_Defaults_MaintainEmbargo_Length"  />
								<% }else if ("WID".equals(parCode)){%>
								<ihtml:text property="width" value="<%=(String)parameterValues %>" onkeyup="validateNumber(this);restrictToSingleNumber(this);" componentID="CMP_Reco_Defaults_MaintainEmbargo_Width"  />
								<% }else if ("HGT".equals(parCode)){%>
								<ihtml:text property="height" value="<%=(String)parameterValues %>" onkeyup="validateNumber(this);restrictToSingleNumber(this);" componentID="CMP_Reco_Defaults_MaintainEmbargo_Height"  />
								
								<% }else if ("TIM".equals(parCode)){%>
								<ihtml:text property="time" value="<%=(String)parameterValues %>" componentID="CMP_Reco_Defaults_MaintainEmbargo_Time" onchange="validateTime(this);"/><common:message  key="maintainembargo.timeformat"/>
								<%}else if("UNWGT".equals(parCode)){%>
								<ihtml:text property="unWeight" value="<%=(String)parameterValues %>" onkeyup="restrictToSingleNumber(this);" componentID="CMP_Reco_Defaults_MaintainEmbargo_UnWeight_input" />
								<%}else if("DVCST".equals(parCode)){%>
								<ihtml:text property="dvForCustoms" value="<%=(String)parameterValues %>" componentID="CMP_Reco_Defaults_MaintainEmbargo_DVCST_input" />
								<%}else if("DVCRG".equals(parCode)){%>
								<ihtml:text property="dvForCarriage" value="<%=(String)parameterValues %>" componentID="CMP_Reco_Defaults_MaintainEmbargo_DVCRG_input" />
								<%}else if("UNCLS".equals(parCode)){%>
								<ihtml:text property="unIds" value="<%=(String)parameterValues %>" componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_UNIDs" />
								<% }else if ("COM".equals(parCode)){%>
									<ihtml:text property="commodity" value="<%=(String)parameterValues %>" onblur="restrictValues(this);"  componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_Commodity" />
									<div class= "lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="20" onclick="displayLOV('showCommodity.do','Y','Y','showCommodity.do',commodity,'commodity','1','commodity','',0,null,null,null,null,10);" height="20" id="commodity"/></div>																			
								<% }else if ("ULD".equals(parCode)){%>
									<ihtml:text property="uldType" value="<%=(String)parameterValues %>" onblur="restrictValues(this);"  componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_Uld" />
									<div class= "lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="20" onclick="displayLOV('shared.uld.findUldTypeAndGroup.do','N','Y','shared.uld.findUldTypeAndGroup.do',uldType,'uldType','1','uldType','',0,null,null,null,null,10);" height="20" id="uldType"/></div>											
								
								<% }else if ("ULDTYP".equals(parCode)){%>
									<ihtml:text property="uldTyp" value="<%=(String)parameterValues %>" componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_UldTypeCode"  onblur="restrictValues(this);" style="width:86%" />
									<div class="lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="20"  onclick="displayLOV('showUld.do','Y','Y','showUld.do',uldTyp,'uldTyp','1','uldTyp','',0,null,null,null,null,10)" height="20" id="uldTyp"/></div>													
								<% }else if ("FLTNUM".equals(parCode)){%>
											<%
											EmbargoParameterVO embargoParVO = (EmbargoParameterVO)embargoParameterVO;
											String carrierCode = "";
											if(embargoParVO.getCarrierCode()!=null) {
												carrierCode = embargoParVO.getCarrierCode();
											}
											String flightNumber = "";
											if(embargoParVO.getFlightNumber()!=null) {
												flightNumber = embargoParVO.getFlightNumber();
											}
											%>
									<ihtml:text property="carrierCode" value="<%=(String)carrierCode%>" style="width:12%" maxlength="3" onkeyup="validateaplphanum(this);" onblur="restrictSpecialChar(this);" componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_FlightCarrierCode" />&nbsp
									<ihtml:text property="flightNumber" value="<%=(String)flightNumber%>" style="width:20%" maxlength="5" onkeyup="validateaplphanum(this);" onblur="restrictSpecialChar(this);checkFlightNumber(this);" componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_FlightNumber" />
								
								<% }else if ("FLTOWR".equals(parCode)){%>
									<ihtml:text property="flightOwner" value="<%=(String)parameterValues %>" onblur="restrictValues(this);" componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_FlightOwner" />
									<div class= "lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="20" height="20" id="flghtOwnr"  class="flghtOwnr"  onclick="displayLOV('showAirline.do','Y','Y','showAirline.do',flightOwner,'Airline','1','flightOwner','',0,null,null,null,null,10);"/></div>
								
								<% }else if ("FLTTYP".equals(parCode)){%>
								
									<ihtml:select property="flightType" value="<%=(String)parameterValues %>" componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_FlightType" >
									<logic:present name="flightTypes">
										<logic:iterate id="type" name="flightTypes">
											<bean:define id="typevalue" name="type" property="fieldValue" />
											<html:option value="<%=(String)typevalue %>"><bean:write name="type" property="fieldDescription" /></html:option>
										</logic:iterate>
									</logic:present>  		
									</ihtml:select>
								<% }else if ("ULDPOS".equals(parCode)){%>								
									<ihtml:select property="uldPos" value="<%=(String)parameterValues %>" componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_UldPos" >
										<logic:present name="uldPositions">
											<logic:iterate id="type" name="uldPositions">
											<bean:define id="typevalue" name="type" property="fieldValue" />
											<html:option value="<%=(String)typevalue %>"><bean:write name="type" property="fieldDescription" /></html:option>
										</logic:iterate>
									</logic:present>  		
									</ihtml:select>
								<% }else if ("PAYTYP".equals(parCode)){%>
									<ihtml:text property="paymentType" value="<%=(String)parameterValues %>"   componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_PaymentType" />
									<div class= "lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="20" onclick="displayOneTimeLOV('screenloadOneTime.do','Y','Y','screenloadOneTime.do','','','Payment Type','1','values','values',0,'reco.defaults.paymenttype','');" height="20" id="paymentType"/></div>
								<% }else if ("PRD".equals(parCode)){%>
									<ihtml:text property="productCode" value="<%=(String)parameterValues %>" onblur="restrictValues(this);"   componentID="CMP_Reco_Defaults_MaintainEmbargo_product" />
									<div class= "lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="20" onclick="displayProductLov('products.defaults.screenloadProductLov.do',productCode[0].value,'productName','','','0','productCode','Y',0);" height="20" id="productCode"/></div>
								
								<% }else if ("AGT".equals(parCode)){%>
									<ihtml:text property="agentCode" value="<%=(String)parameterValues %>" onblur="restrictValues(this);"   componentID="CMP_Reco_Defaults_MaintainEmbargo_agent" />
									<div class="lovImgTbl"><img  src="<%=request.getContextPath()%>/images/lov.png"  onclick= "showAgentLOV(this)" id="agentLOV" width="20" height="20" /></div>
								
								<% }else if ("AGTGRP".equals(parCode)){%>
									<ihtml:text property="agentGroup" value="<%=(String)parameterValues %>" onblur="restrictValues(this);"   componentID="CMP_Reco_Defaults_MaintainEmbargo_agentGroup" />
									 <div class="lovImgTbl"><img  src="<%=request.getContextPath()%>/images/lov.png"  onclick= "showAgentLOV(this)" id="agentLOV" width="20" height="20" /></div>
								
								<% }else if ("SCC".equals(parCode)){%>
									<ihtml:text property="scc" name="scc" value="<%=(String)parameterValues %>" onblur="restrictValues(this);"   componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_SCC" />
									<!--img src="<%=request.getContextPath()%>/images/lov.gif" width="16" onclick="showLov(this,targetFormName)" height="16" id="sccvalues"/-->
									<div class= "lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="20" onclick="displayLOV('showScc.do','Y','Y','showScc.do',scc,'scc','1','scc','',0,null,null,null,null,10);" height="20" id="scc"/></div>
								
								<% }else if ("SCCGRP".equals(parCode)){%>
									<ihtml:text property="sccGroup" value="<%=(String)parameterValues %>" componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_SccGroup" />
									<div class= "lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="20" onclick="showParameterGroupLov('shared.generalmastergrouping.screenloadgroupnamelov.do','Y',sccGroup,'SCCGRP','EMB','sccGroup','1',0);" height="20" id="sccGroup"/></div>
								<% }else if ("ARLGRP".equals(parCode)){%>
									<ihtml:text property="airlineGroup" value="<%=(String)parameterValues %>" componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_AirlineGroup" />
									<div class= "lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="20" onclick="showParameterGroupLov('shared.generalmastergrouping.screenloadgroupnamelov.do','Y',airlineGroup,'ARLGRP','EMB','airlineGroup','1',0);" height="20" id="airlineGroup"/></div>
								<% }else if ("ACRTYPGRP".equals(parCode)){%>
									<ihtml:text property="aircraftGroupType" value="<%=(String)parameterValues %>" componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_AircraftGroupType" />
									<div class= "lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="20" onclick="showParameterGroupLov('shared.generalmastergrouping.screenloadgroupnamelov.do','Y',aircraftGroupType,'ACRTYPGRP','EMB','aircraftGroupType','1',0);" height="20" id="aircraftGroupType"/></div>
								<% }else if ("ACRTYP".equals(parCode)){%>
									<ihtml:text property="aircraftType" value="<%=(String)parameterValues %>" componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_AircraftType" />
									<div class= "lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="20" onclick="displayLOV('showAircraft.do','Y','Y','showAircraft.do',aircraftType,'aircraftType','1','aircraftType','',0,null,null,null,null,10);" height="20" id="aircraftType"/></div>
								
								<% }else if ("SPLIT".equals(parCode)){%>
									<ihtml:select property="splitIndicator" value="<%=(String)parameterValues %>"  componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_SplitIndicator" >
										<html:option value="Y">Y</html:option>
									</ihtml:select>
								<% }else if ("MALCAT".equals(parCode)){%>
								<ihtml:select property="mailCategory" value="<%=(String)parameterValues %>"  componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_MailCategory" onblur="restrictValues(this);"   multiSelect="true" multiSelectNoneSelectedText="Select" multiSelectMinWidth="180" multiple="multiple">
									<logic:present name="mailCategory">
										<logic:iterate id="mailCat" name="mailCategory">
											<bean:define id="malcat" name="mailCat" property="fieldValue" />
											<html:option value="<%=(String)malcat %>"><bean:write name="mailCat" property="fieldDescription" /></html:option>
										</logic:iterate>
									</logic:present> 													
								</ihtml:select>
								<% }else if ("MALCLS".equals(parCode)){%>
								<ihtml:select property="mailClass" value="<%=(String)parameterValues %>"  componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_MailClass" onblur="restrictValues(this);"  multiSelect="true" multiSelectNoneSelectedText="Select" multiSelectMinWidth="180" multiple="multiple">
									<logic:present name="mailClass">
										<logic:iterate id="mailClas" name="mailClass">
											<bean:define id="malcls" name="mailClas" property="fieldValue" />
											<html:option value="<%=(String)malcls %>"><bean:write name="mailClas" property="fieldDescription" /></html:option>
										</logic:iterate>
									</logic:present> 													
								</ihtml:select>
								<% }else if ("MALSUBCLSGRP".equals(parCode)){%>
									<ihtml:select property="mailSubClassGrp" value="<%=(String)parameterValues %>"  componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_MailSubClsGrp" onblur="restrictValues(this);"  multiSelect="true" multiSelectNoneSelectedText="Select" multiSelectMinWidth="180" multiple="multiple">
										<logic:present name="mailSubClassGrp">
											<logic:iterate id="mailsubclsgrp" name="mailSubClassGrp">
												<bean:define id="malsbclsg" name="mailsubclsgrp" property="fieldValue" />
												<html:option value="<%=(String)malsbclsg %>"><bean:write name="mailsubclsgrp" property="fieldDescription" /></html:option>
											</logic:iterate>
										</logic:present> 													
									</ihtml:select>
							
								<% }else if ("MALSUBCLS".equals(parCode)){%>
								
									<ihtml:text property="mailSubClass" value="<%=(String)parameterValues %>" componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_Mail_SubClass" onblur="restrictValues(this);"/>
									<div class= "lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="20" onclick="displayLOV('mailtracking.defaults.subclaslov.list.do','Y','Y','mailtracking.defaults.subclaslov.list.do',mailSubClass,'mailSubClass','1','mailSubClass','',0,null,null,null,null,10);"  height="20" id="mailSubClass"/></div>

								
								
								<% }else if ("SRVCRGCLS".equals(parCode)){%>
										<ihtml:select property="serviceCargoClass" value="<%=(String)parameterValues %>"  componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_ServiceCargoClass" onblur="restrictValues(this);"  multiSelect="true" multiSelectNoneSelectedText="Select" multiSelectMinWidth="180" multiple="multiple">
											<logic:present name="serviceCargoClass">
												<logic:iterate id="srvClas" name="serviceCargoClass">
													<bean:define id="srvcls" name="srvClas" property="fieldValue" />
													<html:option value="<%=(String)srvcls %>"><bean:write name="srvClas" property="fieldDescription" /></html:option>
												</logic:iterate>
											</logic:present> 													
										</ihtml:select>
										
								<% }else if ("ACRCLS".equals(parCode)){%>
								 <ihtml:hidden property="aircraftClassification" name="form" value="<%=(String)parameterValues %>" />
										<%
										String parameterDescription = "";
										EmbargoParameterVO embargoParVO = (EmbargoParameterVO)embargoParameterVO;
										if(embargoParVO.getParameterDescription()!=null) {
												parameterDescription = embargoParVO.getParameterDescription();
											}
										%>
									<ihtml:text property="aircraftClassificationDesc"  
									value="<%=(String)parameterDescription  %>" componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_AircraftClassification" />
																
									<div class= "lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="20" onclick="displayOneTimeLOV('screenloadOneTime.do','Y','Y','screenloadOneTime.do','','','Aircraft Classification','1','values','values',0,'shared.aircraft.aircraftClassification','');" height="20" id="aircraftClassification"/></div>
								
								<% }else if ("SHP".equals(parCode)){%>
									<ihtml:text property="shipperCode" value="<%=(String)parameterValues %>" onblur="restrictValues(this);"   componentID="CMP_Reco_Defaults_MaintainEmbargo_Shipper" />
									<div class="lovImgTbl"><img  src="<%=request.getContextPath()%>/images/lov.png"  onclick= "showShipperLOV(this)" id="shipperLOV" width="20" height="20" /></div>
								
								<% }else if ("SHPGRP".equals(parCode)){%>
									<ihtml:text property="shipperGroup" value="<%=(String)parameterValues %>" onblur="restrictValues(this);"   componentID="CMP_Reco_Defaults_MaintainEmbargo_ShipperGroup" />
									 <div class= "lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="20" onclick="showParameterGroupLov('shared.generalmastergrouping.screenloadgroupnamelov.do','Y',shipperGroup,'SHPCODGRP','EMB','shipperGroup','1',0);" height="20" id="shipperGroup"/></div>
									
								<% }else if ("CNS".equals(parCode)){%>
									<ihtml:text property="consigneeCode" value="<%=(String)parameterValues %>" onblur="restrictValues(this);"   componentID="CMP_Reco_Defaults_MaintainEmbargo_Consignee" />
									<div class="lovImgTbl"><img  src="<%=request.getContextPath()%>/images/lov.png"  onclick= "showConsigneeLOV(this)" id="consigneeLOV" width="20" height="20" /></div>
								
								<% }else if ("CNSGRP".equals(parCode)){%>
									<ihtml:text property="consigneeGroup" value="<%=(String)parameterValues %>" onblur="restrictValues(this);"  componentID="CMP_Reco_Defaults_MaintainEmbargo_ConsigneeGroup" />
									 <div class= "lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" width="20" onclick="showParameterGroupLov('shared.generalmastergrouping.screenloadgroupnamelov.do','Y',consigneeGroup,'CNSGRP','EMB','consigneeGroup','1',0);" height="20" id="consigneeGroup"/></div>
									 
								<% }else if ("SHPTYP".equals(parCode)){%>
										<ihtml:select property="shipmentType" value="<%=(String)parameterValues %>"  componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_ShipmentType" onblur="restrictValues(this);"  multiSelect="true" multiSelectNoneSelectedText="Select" multiSelectMinWidth="180" multiple="multiple">
											<logic:present name="shipmentType">
												<logic:iterate id="shipmntTyp" name="shipmentType">
													<bean:define id="shptyp1" name="shipmntTyp" property="fieldValue" />
													<html:option value="<%=(String)shptyp1%>"><bean:write name="shipmntTyp" property="fieldDescription" /></html:option>
												</logic:iterate>
											</logic:present>												
										</ihtml:select>
								<% }else if ("SRVCTYP".equals(parCode)){%>
										<ihtml:select property="serviceType" value="<%=(String)parameterValues %>"  componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_ServiceType" onblur="restrictValues(this);"  multiSelect="true" multiSelectNoneSelectedText="Select" multiSelectMinWidth="180" multiple="multiple">
											<logic:present name="serviceType">
												<logic:iterate id="srvcTyp" name="serviceType">
													<bean:define id="srvctyp1" name="srvcTyp" property="fieldValue" />
													<html:option value="<%=(String)srvctyp1%>"><bean:write name="srvcTyp" property="fieldValue" /></html:option>
												</logic:iterate>
											</logic:present>												
										</ihtml:select>
								<% }else if ("PKGGRP".equals(parCode)){%>
										<ihtml:select property="unPg" value="<%=(String)parameterValues %>"  componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_UnPg" onblur="restrictValues(this);"  multiSelect="true" multiSelectNoneSelectedText="Select" multiSelectMinWidth="180" multiple="multiple">
											<logic:present name="unPg">
												<logic:iterate id="pkggrp" name="unPg">
													<bean:define id="pkggrp1" name="pkggrp" property="fieldValue" />
													<html:option value="<%=(String)pkggrp1%>"><bean:write name="pkggrp" property="fieldValue" /></html:option>
												</logic:iterate>
											</logic:present>												
										</ihtml:select>
										<% }else if ("SRVCTYPFRTECSTP".equals(parCode)){%>
										<ihtml:select property="serviceTypeForTechnicalStop" value="<%=(String)parameterValues %>"  componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_ServiceTypeForTechnicalStop" onblur="restrictValues(this);"  multiSelect="true" multiSelectNoneSelectedText="Select" multiSelectMinWidth="180" multiple="multiple">
											<logic:present name="serviceTypeForTechnicalStop">
												<logic:iterate id="srvcTypFrTecStp" name="serviceTypeForTechnicalStop">
													<bean:define id="srvctypForTechnicalStop1" name="srvcTypFrTecStp" property="fieldValue" />
													<html:option value="<%=(String)srvctypForTechnicalStop1%>"><bean:write name="srvcTypFrTecStp" property="fieldValue" /></html:option>
												</logic:iterate>
											</logic:present>												
										</ihtml:select>
								<% }else if ("SUBRSK".equals(parCode)){%>
									<ihtml:text property="subRisk" value="<%=(String)parameterValues %>" onblur="restrictValues(this);" onkeyup="validateNumber(this);" componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_SubRisk" />
								<% }else if ("CNSL".equals(parCode)){%>
									<ihtml:select property="consol" value="<%=(String)parameterValues %>"  componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_Consol" >
										<html:option value="Y">Yes</html:option>
										<html:option value="N">No</html:option>
									</ihtml:select>		
								<!-- Added by 202766 for IASCB-159851 -->	
								<% }else if ("UNKSHP".equals(parCode)){%>
									<ihtml:select property="unknownShipper" value="<%=(String)parameterValues %>"  componentID="CMP_Reco_Defaults_MaintainEmbargo_Filter_UnknownShipper" >
										<html:option value="Y">Yes</html:option>
										<html:option value="N">No</html:option>
									</ihtml:select>
								<%}%>