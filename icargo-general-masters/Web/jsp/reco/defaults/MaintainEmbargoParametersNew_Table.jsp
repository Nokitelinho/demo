
<%@ include file="/jsp/includes/tlds.jsp" %>

<%@ page import="org.apache.struts.action.ActionMessages"%>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import="com.ibsplc.icargo.business.reco.defaults.vo.EmbargoParameterVO"%>

<business:sessionBean id="flightTypes" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="flightTypes" />
<business:sessionBean id="uldPos" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="uldPos" />
<business:sessionBean id="dayOfOperationApplicableOn" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="dayOfOperationApplicableOn" />
<business:sessionBean id="applicableLevelsForParameters" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="applicableLevelsForParameters" /> <!-- Added by A-5290 for IASCB-78839 -->
<business:sessionBean id="weightsApplicableOn" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="weightsApplicableOn" />
<business:sessionBean id="embargoVo" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="embargoVo" />
<business:sessionBean id="embargoParameters" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="embargoParameters" />
<business:sessionBean id="mailClass" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="mailClass" />
<business:sessionBean id="mailCategory" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="mailCategory" />
<business:sessionBean id="mailSubClassGrp" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="mailSubClassGrp" />
<business:sessionBean id="serviceCargoClass" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="serviceCargoClass" />
<business:sessionBean id="shipmentType" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="shipmentType" />
<business:sessionBean id="serviceType" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="serviceType" />
<business:sessionBean id="serviceTypeForTechnicalStop" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="serviceTypeForTechnicalStop" />
<business:sessionBean id="unPg" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="unPg" />
<business:sessionBean id="subRisk" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="subRisk" />

	<logic:present name="embargoVo" property="parameters">
											
		<logic:iterate id ="embargoParameterVO" name="embargoVo" property="parameters" indexId="rowCount">
			<logic:present name="embargoParameterVO" property="parameterValues">
				<bean:define id="parValues" name="embargoParameterVO" property="parameterValues" />
				<logic:notEqual name="parValues" value="-">
					<logic:present name="embargoParameterVO" property="operationalFlag">
						<bean:define id="paramflag" name="embargoParameterVO" property="operationalFlag" />
										
						<logic:notEqual name="paramflag" value="D" >
							<logic:equal name="paramflag" value="I">
									<ihtml:hidden name="form" property="paramOperationalFlag" value="I"/>
							</logic:equal>
							<logic:notEqual name="paramflag" value="I">
								<ihtml:hidden name="form" property="paramOperationalFlag" value="U"/>
							</logic:notEqual>
							
					<tr>
						<td>
							<div class="ic-center">
								<ihtml:checkbox property="check" value="<%=String.valueOf(rowCount)%>" onclick="toggleTableHeaderCheckbox('check',this.form.allCheck)"/>
							</div>
						</td>
						<logic:present name="embargoParameterVO" property="parameterCode">
						<td>
							<bean:define id="paramCode" name ="embargoParameterVO" property="parameterCode" type="String" />
							<ihtml:select property="parameterCode" componentID="CMP_Reco_Defaults_MaintainEmbargo_ParameterCode"  value="<%=(String)paramCode%>"  onchange="javasciript:changeParameterValues(this)">
								<logic:present name="embargoParameters">
									<logic:iterate id="embargoParameter" name="embargoParameters">
										<bean:define id="embargoParameterCode" name="embargoParameter" property="fieldValue" />
										<html:option value="<%=(String)embargoParameterCode %>"><bean:write name="embargoParameter" property="fieldDescription" /></html:option>
									</logic:iterate>
								</logic:present>    
							</ihtml:select>
						</td>
						</logic:present>
						
						<logic:notPresent name="embargoParameterVO" property="parameterCode">
						<td class="iCargoTableDataTd">
						<ihtml:select property="parameterCode" componentID="CMP_Reco_Defaults_MaintainEmbargo_ParameterCode"    onchange="javasciript:changeParameterValues(this)">
						<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
							<logic:present name="embargoParameters">
								<logic:iterate id="embargoParameter" name="embargoParameters">
									<bean:define id="embargoParameterCode" name="embargoParameter" property="fieldValue" />
									<html:option value="<%=(String)embargoParameterCode %>"><bean:write name="embargoParameter" property="fieldDescription" /></html:option>
								</logic:iterate>
							</logic:present> 
						</ihtml:select>
						</td>
						</logic:notPresent>

						<logic:present name="embargoParameterVO" property="applicable">
						<bean:define id="appl" name="embargoParameterVO" property="applicable"/>
						<bean:define id="parCode" name="embargoParameterVO" property="parameterCode" />
						<td>
							<% if ("FLTTYP".equals(parCode) ||"CAR".equals(parCode) || "DAT".equals(parCode) || "FLTOWR".equals(parCode) || "GOODS".equals(parCode) || 
							"PAYTYP".equals(parCode) || "PRD".equals(parCode) || "ARLGRP".equals(parCode) || 
							"UNNUM".equals(parCode) || "AWBPRE".equals(parCode) || "COM".equals(parCode) || "MALCAT".equals(parCode)  || "ULDTYP".equals(parCode) ||
							"MALCLS".equals(parCode) || "MALSUBCLS".equals(parCode) || "MALSUBCLSGRP".equals(parCode) || "PKGINS".equals(parCode) || "AGT".equals(parCode) || "AGTGRP".equals(parCode) || "ULD".equals(parCode) || "ACRTYPGRP".equals(parCode) || "ACRTYP".equals(parCode) || "SRVCRGCLS".equals(parCode) ||
							"ACRCLS".equals(parCode) || "SHP".equals(parCode) || "CNS".equals(parCode) || "SHPGRP".equals(parCode) || "CNSGRP".equals(parCode) || "SRVCTYP".equals(parCode) || "SRVCTYPFRTECSTP".equals(parCode) || "PKGGRP".equals(parCode) || "SHPTYP".equals(parCode) || "SUBRSK".equals(parCode) ||  "CNSL".equals(parCode)){%>
							
							<ihtml:select indexId="rowCount" value="<%=(String)appl%>" property="isIncluded" styleClass="applicable"  componentID="CMP_Reco_Defaults_MaintainEmbargo_IsIncluded">																				
								<html:option value="IN">Include</html:option>	
								<html:option value="EX">Exclude</html:option>									
								</ihtml:select>
							<%}else if("FLTNUM".equals(parCode)){%>
							<ihtml:select indexId="rowCount" value="<%=(String)appl%>" property="isIncluded" styleClass="applicable"  componentID="CMP_Reco_Defaults_MaintainEmbargo_IsIncluded">
								<html:option value="IN">Include</html:option>	
								<html:option value="EX">Exclude</html:option>
								<html:option value="LTEQ">Less Than or Equal To</html:option>
								<html:option value="GTEQ">Greater Than or Equal To</html:option>									
								</ihtml:select>
							<%}else if("SPLIT".equals(parCode) || "ULDPOS".equals(parCode)){%>
							<ihtml:select indexId="rowCount" property="isIncluded" value="<%=(String)appl%>" styleClass="applicable"  componentID="CMP_Reco_Defaults_MaintainEmbargo_IsIncluded">											
								<html:option value="IN">Include</html:option>				
							</ihtml:select>
							<%}else if("SCC".equals(parCode) || "SCCGRP".equals(parCode)){%>
							<ihtml:select indexId="rowCount" property="isIncluded" value="<%=(String)appl%>" styleClass="applicable"  componentID="CMP_Reco_Defaults_MaintainEmbargo_IsIncluded">											
								<html:option value="IN">Include</html:option>		
								<html:option value="EX">Exclude</html:option>	
								<html:option value="EXIF">Exclude If</html:option>			
							</ihtml:select>
							<%}else if("UNCLS".equals(parCode)){%>
							<ihtml:select indexId="rowCount" property="isIncluded" value="<%=(String)appl%>" styleClass="applicable"  componentID="CMP_Reco_Defaults_MaintainEmbargo_IsIncluded">											
								<html:option value="IN">Include &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp; </html:option>		
								<html:option value="EX">Exclude</html:option>
							</ihtml:select>
							<%}else if("DVCST".equals(parCode)||"DVCRG".equals(parCode)){%>
							<ihtml:select indexId="rowCount" property="isIncluded" value="<%=(String)appl%>" styleClass="applicable"  componentID="CMP_Reco_Defaults_MaintainEmbargo_DVCRG_input">											
								<html:option value="LTEQ">Less Than or Equal To</html:option>
								<html:option value="GTEQ">Greater Than or Equal To</html:option>
							</ihtml:select>
							<!-- Added by 202766 for IASCB-159851 -->
							<%}else if("UNKSHP".equals(parCode)){%>
								<ihtml:select indexId="rowCount" property="isIncluded" value="<%=(String)appl%>" styleClass="applicable"  componentID="CMP_Reco_Defaults_MaintainEmbargo_IsIncluded">											
								<html:option value="EQ">Equals</html:option>		
							</ihtml:select>
							<%}else{%>
							<ihtml:select indexId="rowCount" value="<%=(String)appl%>" property="isIncluded" styleClass="applicable"  componentID="CMP_Reco_Defaults_MaintainEmbargo_IsIncluded">
								<html:option value="EQ">Equals</html:option>
								<html:option value="LT">Less Than</html:option>
								<html:option value="GT">Greater Than</html:option>
								<html:option value="LTEQ">Less Than or Equal To</html:option>
								<html:option value="GTEQ">Greater Than or Equal To</html:option>
							</ihtml:select>
							<%}%>
							
						</td>
						</logic:present>

						<logic:notPresent name="embargoParameterVO" property="applicable">
						<bean:define id="parCode" name="embargoParameterVO" property="parameterCode" />
						<td>
							<% if ("FLTTYP".equals(parCode) || "CAR".equals(parCode) || "DAT".equals(parCode) || "FLTOWR".equals(parCode) || "GOODS".equals(parCode) || "PAYTYP".equals(parCode) || 
							"PRD".equals(parCode) || "ARLGRP".equals(parCode) || "UNNUM".equals(parCode) || 
							"AWBPRE".equals(parCode) || "FLTNUM".equals(parCode) || "COM".equals(parCode)|| "MALCAT".equals(parCode) ||  "ULDTYP".equals(parCode) ||
							"MALCLS".equals(parCode) || "MALSUBCLS".equals(parCode) || "MALSUBCLSGRP".equals(parCode) || "PKGINS".equals(parCode) || "AGT".equals(parCode) || "AGTGRP".equals(parCode) || "ULD".equals(parCode) || "ACRTYPGRP".equals(parCode)
									|| "ACRTYP".equals(parCode) || "SRVCRGCLS".equals(parCode) || "ACRCLS".equals(parCode) || "SHP".equals(parCode) || "CNS".equals(parCode) || "SHPGRP".equals(parCode) || "CNSGRP".equals(parCode) || "SRVCTYP".equals(parCode) || "SRVCTYPFRTECSTP".equals(parCode) || "PKGGRP".equals(parCode) || "SHPTYP".equals(parCode) || "SUBRSK".equals(parCode) ||  "CNSL".equals(parCode)){%> 
							
							<ihtml:select indexId="rowCount"  property="isIncluded" styleClass="applicable"  componentID="CMP_Reco_Defaults_MaintainEmbargo_IsIncluded">																				
								
								<html:option value="IN">Include</html:option>	
								<html:option value="EX">Exclude</html:option>											
							</ihtml:select>
							<%}else if("SPLIT".equals(parCode) || "ULDPOS".equals(parCode)){%>
							<ihtml:select indexId="rowCount" property="isIncluded"  styleClass="applicable"  componentID="CMP_Reco_Defaults_MaintainEmbargo_IsIncluded">											
								<html:option value="IN">Include</html:option>				
							</ihtml:select>
							<%}else if("SCC".equals(parCode) || "SCCGRP".equals(parCode)){%>
							<ihtml:select indexId="rowCount" property="isIncluded" styleClass="applicable"  componentID="CMP_Reco_Defaults_MaintainEmbargo_IsIncluded">											
								<html:option value="IN">Include</html:option>		
								<html:option value="EX">Exclude</html:option>	
								<html:option value="EXIF">Exclude If</html:option>			
							</ihtml:select>
							<!-- Added by 202766 for IASCB-159851 -->
							<%}else if("UNKSHP".equals(parCode)){%>
								<ihtml:select indexId="rowCount" property="isIncluded" styleClass="applicable"  componentID="CMP_Reco_Defaults_MaintainEmbargo_IsIncluded">											
								<html:option value="EQ">Equals</html:option>		
							</ihtml:select>
							<%}else{%>
							<ihtml:select indexId="rowCount" property="isIncluded" styleClass="applicable"  componentID="CMP_Reco_Defaults_MaintainEmbargo_IsIncluded">
								<html:option value="EQ">Equals</html:option>
								<html:option value="LT">Less Than</html:option>
								<html:option value="GT">Greater Than</html:option>
								<html:option value="LTEQ">Less Than or Equal To</html:option>
								<html:option value="GTEQ">Greater Than or Equal To</html:option>
							</ihtml:select>
							<%}%>
						</td>
						</logic:notPresent>

						<logic:present name="embargoParameterVO" property="parameterValues">
						<bean:define id="parameterValues" name="embargoParameterVO" property="parameterValues" />

						
							<bean:define id="parCode" name="embargoParameterVO" property="parameterCode" />
							
							<td>
								<div class="column4_control_div ic-input">
								<bean:define id="_parCode" name="parCode" toScope="request"/>
								<bean:define id="_parameterValues" name="parameterValues" toScope="request"/>
								
								<bean:define id="_embargoParameterVO" name="embargoParameterVO" toScope="request"/>
								<jsp:include page="MaintainEmbargoParametersSplit.jsp"/>
							
								</div>																							
								<ihtml:hidden property="values" value=""  name="form" styleClass="parameter_value_hidden"/>
							</td>
							
						</logic:present>

						<logic:notPresent name="embargoParameterVO" property="parameterValues">
							<bean:define id="parCode" name="embargoParameterVO" property="parameterCode" />
							<bean:define id="parameterValues" name="embargoParameterVO" property="parameterValues" />
							<bean:define id="parameterDescription" name="embargoParameterVO" property="parameterDescription" />
							
								 
							<td>
								<div class="column4_control_div ic-input">
																												
								</div>																							
								<ihtml:hidden property="values" value="" name="form" styleClass="parameter_value_hidden"/>
							</td>
						</logic:notPresent>
						
							<td>
								<logic:present name="embargoParameterVO" property="applicableLevel">
									<bean:define id="aaplLevel" name="embargoParameterVO" property="applicableLevel" />
									<bean:define id="parCode" name="embargoParameterVO" property="parameterCode" />
									<%if("TIM".equals(parCode) || "DAT".equals(parCode) || "CAR".equals(parCode)
									|| "FLTNUM".equals(parCode)|| "FLTOWR".equals(parCode)|| "FLTTYP".equals(parCode)
									|| "ARLGRP".equals(parCode)||"UNCLS".equals(parCode)  || "ACRTYPGRP".equals(parCode)
									|| "ACRTYP".equals(parCode) || "ACRCLS".equals(parCode)){%>
									<div class="column5_control_div">
								
											<common:message  key="maintainembargo.applicableon"/>
											<ihtml:select property="applicableOn" styleClass="applicableOn" value="<%=(String)aaplLevel%>" componentID="CMP_Reco_Defaults_MaintainEmbargo_ApplicableOn">
												
												
												<logic:present name="applicableLevelsForParameters">
													<logic:iterate id="appl" name="applicableLevelsForParameters">
										<bean:define id="applOn" name="appl" property="fieldValue" />
										<html:option value="<%=(String)applOn %>"><bean:write name="appl" property="fieldDescription" /></html:option>
									</logic:iterate>
												</logic:present>
											</ihtml:select>
									</div> 
									<%}else if("WGT".equals(parCode)){%>
										<div class="column5_control_div">
											<common:message  key="maintainembargo.typeofWeight"/>
											<ihtml:select property="applicableOnParameter" styleClass="applicableOn" componentID="CMP_Reco_Defaults_MaintainEmbargo_ApplicableOn">
												<logic:present name="weightsApplicableOn">
													<logic:iterate id="appl" name="weightsApplicableOn">
										<bean:define id="applOn" name="appl" property="fieldValue" />
										<html:option value="<%=(String)applOn %>"><bean:write name="appl" property="fieldDescription" /></html:option>
									</logic:iterate>
												</logic:present>
											</ihtml:select>
									</div> 
									<%}else{%>
										<div class="column5_control_div" style="display:none;">
											<common:message  key="maintainembargo.applicableon"/>
											<ihtml:select property="applicableOn" styleClass="applicableOn" value="A" componentID="CMP_Reco_Defaults_MaintainEmbargo_ApplicableOn">
												
												
												<logic:present name="applicableLevelsForParameters">
													<logic:iterate id="appl" name="applicableLevelsForParameters">
										<bean:define id="applOn" name="appl" property="fieldValue" />
										<html:option value="<%=(String)applOn %>"><bean:write name="appl" property="fieldDescription" /></html:option>
									</logic:iterate>
												</logic:present>
											</ihtml:select>
											</div>
									<%}%>
								</logic:present>
								<logic:notPresent name="embargoParameterVO" property="applicableLevel">
									<div class="column5_control_div" style="display:none;">
										<common:message  key="maintainembargo.applicableon"/>
										<ihtml:select property="applicableOn" styleClass="applicableOn" componentID="CMP_Reco_Defaults_MaintainEmbargo_ApplicableOn">
											<logic:present name="applicableLevelsForParameters">
												<logic:iterate id="appl" name="applicableLevelsForParameters">
													<bean:define id="applOn" name="appl" property="fieldValue" />
													<html:option value="<%=(String)applOn %>"><bean:write name="appl" property="fieldDescription" /></html:option>
												</logic:iterate>
											</logic:present>
										</ihtml:select>
									</div>
								</logic:notPresent>
								
							</td>
					</tr>
          		</logic:notEqual>
				</logic:present>
								
				
				
				</logic:notEqual>
				

			</logic:present>
		</logic:iterate>
	</logic:present>
          
      	

