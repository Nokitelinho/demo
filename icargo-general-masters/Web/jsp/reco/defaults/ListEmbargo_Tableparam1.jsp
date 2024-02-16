<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="org.apache.struts.action.ActionMessages"%>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.ListEmbargoRulesForm"%>
<business:sessionBean id="embargoDetails"
										moduleName="reco.defaults"
										screenID="reco.defaults.listembargo"
										method="get"
										attribute="EmabrgoDetailVOs" />
<business:sessionBean id="embargoParameters" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="embargoParameters" />
<business:sessionBean id="flightTypes" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="flightTypes" />
<business:sessionBean id="serviceCargoClass" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="serviceCargoClass" />
<business:sessionBean id="shipmentType" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="shipmentType" />
<business:sessionBean id="serviceType" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="serviceType" />
<business:sessionBean id="serviceTypeForTechnicalStop" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="serviceTypeForTechnicalStop" />
<business:sessionBean id="unPg" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="unPg" />
<business:sessionBean id="subRisk" moduleName="reco.defaults" screenID="reco.defaults.listembargo" method="get" attribute="subRisk" />
<bean:define id="form" name="ListEmbargoRulesForm" toScope="page" type="com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.ListEmbargoRulesForm"/>
<bean:define id="embargoDetailsVO" name="_embargoDetailsVO"  />
<td class="iCargoTableDataTd" style="word-wrap: break-word">
											<logic:present name="embargoDetailsVO" property="params" >
												<logic:iterate id="embargoParameterVO" name="embargoDetailsVO" property="params" type="com.ibsplc.icargo.business.reco.defaults.vo.EmbargoParameterVO"  indexId="nIndex" >
												<logic:present name="embargoParameterVO" property="parameterCode">
													<bean:define id="parameterValues" name="embargoParameterVO" property="parameterValues" />
													<bean:define id="paramCode" name="embargoParameterVO" property="parameterCode" />
													<logic:present name="embargoParameters">
														<logic:iterate id="embargoParameter" name="embargoParameters">
														<bean:define id="embargoParameterCode" name="embargoParameter" property="fieldValue" />
														<bean:define id="embargoParameterValue" name="embargoParameter" property="fieldDescription" />
															<%if(embargoParameterCode.equals(paramCode)){%>
															<br><%=(String)embargoParameterValue%>
															<%}%>
														</logic:iterate>
													</logic:present>
													<% if ("CAR".equals(paramCode) || "DAT".equals(paramCode) || "FLTOWR".equals(paramCode) || "GOODS".equals(paramCode) || "PAYTYP".equals(paramCode) || 
													"PRD".equals(paramCode) || "SCC".equals(paramCode) || "SCCGRP".equals(paramCode) || "SPLIT".equals(paramCode) || "UNNUM".equals(paramCode) || 
													"AWBPRE".equals(paramCode) || "FLTNUM".equals(paramCode) ||"COM".equals(paramCode)|| "MALCAT".equals(paramCode) ||
													"MALCLS".equals(paramCode) || "MALSUBCLS".equals(paramCode) || "MALSUBCLSGRP".equals(paramCode)  || "SRVCRGCLS".equals(paramCode)  || "ACRCLS".equals(paramCode) || "SHP".equals(paramCode) || "CNS".equals(paramCode) || "SHPGRP".equals(paramCode) || "CNSGRP".equals(paramCode) || "SHPTYP".equals(paramCode) || "SRVCTYP".equals(paramCode) || "SRVCTYPFRTECSTP".equals(paramCode) || "PKGGRP".equals(paramCode) || "SUBRSK".equals(paramCode) || "CNSL".equals(paramCode)){%>
													<logic:equal name="embargoParameterVO" property="applicable" value="IN">
															<common:message  key="listembargo.include"/>
													</logic:equal>
													<logic:equal name="embargoParameterVO" property="applicable" value="EX">
															<common:message  key="listembargo.exclude"/>
													</logic:equal>
													<logic:equal name="embargoParameterVO" property="applicable" value="EXIF">
															<common:message  key="listembargo.excludeIf"/>
													</logic:equal>
													<%}else if("FLTTYP".equals(paramCode)){%>
													<logic:equal name="embargoParameterVO" property="applicable" value="IN">
															<common:message  key="listembargo.include"/>
													</logic:equal>
													<%}else{%>
													<logic:equal name="embargoParameterVO" property="applicable" value="EQ">
															<common:message  key="listembargo.equals"/>
													</logic:equal>
													<logic:equal name="embargoParameterVO" property="applicable" value="LT">
															<common:message  key="listembargo.lessthan"/>
													</logic:equal>
													<logic:equal name="embargoParameterVO" property="applicable" value="GT">
															<common:message  key="listembargo.greaterthan"/>
													</logic:equal>
													<logic:equal name="embargoParameterVO" property="applicable" value="LTEQ">
															<common:message  key="listembargo.lessthanorequal"/>
													</logic:equal>
													<logic:equal name="embargoParameterVO" property="applicable" value="GTEQ">
															<common:message  key="listembargo.greaterthanorequal"/>
													</logic:equal>
													<%}%>
													<logic:equal name="embargoParameterVO" property="parameterCode" value="FLTNUM">
													<bean:define id="carrierCode" name="embargoParameterVO" property="carrierCode" />
													<bean:define id="flightNumber" name="embargoParameterVO" property="flightNumber" />
													: <%=(String)carrierCode%> <%=(String)flightNumber%>
													</logic:equal>
													<logic:notEqual name="embargoParameterVO" property="parameterCode" value="FLTNUM"> 
													 :	 <%String paramVal;
														 String[] paramVals;						   
														 paramVal=(String)parameterValues;
														 paramVals=paramVal.split(",");
														 for(int i=0;i<paramVals.length;i++){%>
															<%=(String)paramVals[i]%>
															<%if ("ARLGRP".equals(paramCode)|| "SCCGRP".equals(paramCode) || 
															"AGTGRP".equals(paramCode)){%>
																<img id="parameterValues_~|link_~|<%=nIndex%>_~|<%=paramCode%>_~|<%=(String)paramVals[i]%>" src="<%=request.getContextPath()%>/images/info.gif" 
																width="17" height="17"  onclick="viewGroupingDetails(this)"/>
															<%}
															  if(i!=paramVals.length-1){
																  %>,<% 
															  }
														 }%>
													
													 </logic:notEqual>
												</logic:present>
												</logic:iterate>
											</logic:present>
											</td>