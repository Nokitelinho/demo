 <!--
* Project	 		: iCargo
* Module Code & Name: Mailtracking Mra defaults
* File Name			: MaintainCCA.jsp
* Date				: 19/12/2013
* Author            : Mini S Nair A-5526
-->

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRAMaintainCCAForm"%>
                    
<html:html>
<business:sessionBean id="cCAdetailsVO"
	   	moduleName="mailtracking.mra.gpabilling"
	   	screenID="mailtracking.mra.defaults.maintaincca"
	   	method="get" attribute="cCAdetailsVO" />	
		
		<business:sessionBean id="KEY_WEIGHTROUNDINGVO"
		  moduleName="mailtracking.mra.defaults"
		  screenID="mailtracking.mra.defaults.maintaincca"
		  method="get"
		  attribute="weightRoundingVO" />
		 <business:sessionBean id="KEY_SYSPARAMETERS"
  	moduleName="mailtracking.mra.defaults"
  	screenID="mailtracking.mra.defaults.maintaincca"
	method="get" attribute="systemparametres" />	
		
	<bean:define id="form"
		 name="mraMaintainCCAForm"
		 type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRAMaintainCCAForm"
		 toScope="page" />		
	
<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>




<ihtml:form action="/mailtracking.mra.defaults.maintaincca.screenload.do">
<%String roundingReq="true";%>
<logic:present name="KEY_SYSPARAMETERS">
		<logic:iterate id="sysPar" name="KEY_SYSPARAMETERS">
			<bean:define id="parameterCode" name="sysPar" property="key"/>
			<logic:equal name="parameterCode" value="mailtracking.mra.overrideroundingvalue">
				<bean:define id="parameterValue" name="sysPar" property="value"/>			
					<logic:notEqual name="parameterValue" value="N">
				<%form.setOverrideRounding("Y");%>
					</logic:notEqual>
			</logic:equal>
		</logic:iterate>
	</logic:present>

<ihtml:hidden  property="revisedChargeGrossWeignt"  />
				 <div id="ajaxDiv">
												<table  align="left" height="50%" width="100%" >
													<tr height= "1" class="iCargoFieldSet" >
													<td  width="10%">
															&nbsp;<common:message key="mailtracking.mra.defaults.maintaincca.lbl.details" />
													</td>
													</tr>
														<tr>
															<td width="40%">
																<table >
																	<thead >
																		<tr class="iCargoTableHeadingLeft" >
																			<td class="iCargoTableHeaderLabel ic-middle" style="height:27px"  >
																			<div align="left" >
																				<common:message key="mailtracking.mra.defaults.maintaincca.lbl.currency" />
																			</div>
																		</td>
																		</tr>
																		<tr  class="iCargoTableHeadingLeft">
																			<td class="iCargoTableHeaderLabel ic-middle" style="height:27px" >
																				<div align="left">
																				<common:message key="mailtracking.mra.defaults.maintaincca.lbl.gpacode" />
																			    </div>
																			</td>
																		</tr>
																		<tr  class="iCargoTableHeadingLeft">
																			<td class="iCargoTableHeaderLabel ic-middle" style="height:27px" >
																				<div align="left">
																				<common:message key="mailtracking.mra.defaults.maintaincca.lbl.rate" />
																			    </div>
																			</td>
																		</tr>
																		<tr  class="iCargoTableHeadingLeft">
																			<td class="iCargoTableHeaderLabel ic-middle" style="height:27px" >
																				<div align="left">
																				<common:message key="mailtracking.mra.defaults.maintaincca.lbl.weight" />
																				</div>
																			</td>
																		</tr>
																		<tr  class="iCargoTableHeadingLeft">
																			<td class="iCargoTableHeaderLabel ic-middle" style="height:27px" >
																				<div align="left">
																			<common:message key="mailtracking.mra.defaults.maintaincca.lbl.mailchgs" />
																			</div>
																		</td>
																		</tr>
																		<tr  class="iCargoTableHeadingLeft">
																			<td class="iCargoTableHeaderLabel ic-middle" style="height:27px" >
																				<div align="left">
																			<common:message key="mailtracking.mra.defaults.maintaincca.lbl.surchgs" />
																			</div>
																		</td>
																		</tr>
																		<tr  class="iCargoTableHeadingLeft">
																			<td class="iCargoTableHeaderLabel ic-middle" style="height:27px">
																				<div align="left">
																				<common:message key="mailtracking.mra.defaults.maintaincca.lbl.tax" />
																				</div>
																			</td>
																		</tr>
																		<tr  class="iCargoTableHeadingLeft">
																			<td class="iCargoTableHeaderLabel ic-middle" style="height:27px">
																				<div align="left">
																				<common:message key="mailtracking.mra.defaults.maintaincca.lbl.netvalue" />
																				</div>
																			</td>
																		</tr>
																	</thead>
																</table>
															</td>
															<td width="50%">
									   							<table >
																	<tbody>
																		<tr style="height:27px">
																			<td width="50%">
																			<logic:notPresent name="cCAdetailsVO" property="revContCurCode">	
										  										<ihtml:text property="revCurCode" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_REVCURR" maxlength="3"/>
																				<div class="lovImgTbl valignT">
																				<img name="currLov" id="currLov" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" value="lov2"/>
																				</div>
																			  </logic:notPresent>
																			<logic:present name="cCAdetailsVO" property="revContCurCode">	
																			<ihtml:text property="revCurCode" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_REVCURR" maxlength="3"/>
																			<div class="lovImgTbl valignT">
																			<img name="currLov" id="currLov" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" value="lov2"/>	
																			</div>
																			  </logic:present>
																			  </td>
																			  <td width="50%" class="iCargoTableDataTd">
																			<logic:notPresent name="cCAdetailsVO" property="contCurCode">				
										  												<bean:write property="curCode" name="form" />
																			  </logic:notPresent>
																			<logic:present name="cCAdetailsVO" property="contCurCode">	
																						<bean:define id="contCurCode" name="cCAdetailsVO" property="contCurCode" />
										  												<bean:write property="contCurCode" name="cCAdetailsVO" />
																			  </logic:present>
																			</td>
																			</tr>
																			<tr style="height:27px">
																			<td  width="50%">
																			<logic:present name="cCAdetailsVO" property="revGpaCode">
																					<ihtml:text property="revGpaCode"  componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_REVGPACODE" maxlength="5"/>
																					<div class="lovImgTbl valignT">
																					<img name="gpacodelov" id="gpacodelov" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" value="lov2"/>
																					</div>
																				</logic:present>
																				<logic:notPresent name="cCAdetailsVO" property="revGpaCode">
										  											<ihtml:text property="revGpaCode" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_REVGPACODE" maxlength="5"/>
																					<div class="lovImgTbl valignT">
																					<img name="gpacodelov" id="gpacodelov" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" value="lov2"/>
																					</div>
																				</logic:notPresent>
																				</td>
																			<td width="50%" class="iCargoTableDataTd">	
																				<logic:present name="cCAdetailsVO" property="gpaCode">
																					<bean:define id="gpaCode" name="cCAdetailsVO" property="gpaCode" />
										  												<bean:write property="gpaCode" name="cCAdetailsVO"/>
																				</logic:present>
																				<logic:notPresent name="cCAdetailsVO" property="gpaCode">
										  											<bean:write property="gpaCode" name="form" />
																				</logic:notPresent>
																			</td>
																		</tr>
																		<tr style="height:27px">
																		 <td width="50%" style="height:27px">
																			<logic:notPresent name="cCAdetailsVO" property="revisedRate">
                                                                             <ihtml:text property="revisedRate" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_REVRATE" style="text-align:right" value="0"
/>
																			 </logic:notPresent>

																			<logic:present name="cCAdetailsVO" property="revisedRate">
																																									<bean:define id="revisedRate" name="cCAdetailsVO" property="revisedRate" toScope="page" type="java.lang.Double"/>
																																						
				<logic:equal name="revisedRate" value="0.0">
				<ihtml:text id="revisedRate" property="revisedRate" value="0"
				componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_REVRATE" style="text-align:right" />
				</logic:equal>
				<logic:notEqual name="revisedRate" value="0.0">
				<ihtml:text id="revisedRate"  property="revisedRate" value="<%=String.valueOf(revisedRate)%>"
				componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_REVRATE" style="text-align:right" />
				</logic:notEqual>
																			  </logic:present>
																			</td>
																			<td  width="50%" class="iCargoTableDataTd" style="height:27px">
																				<logic:present name="cCAdetailsVO" property="rate">
																					<bean:define id="rate" name="cCAdetailsVO" property="rate" />
																					 <common:write name="cCAdetailsVO" property="rate" unitFormatting="true" />
										  												
																				</logic:present>
																				<logic:notPresent name="cCAdetailsVO" property="rate">
																				<common:write name="form" property="rate" />
										  											
																				</logic:notPresent>
																			</td>
																		</tr>
																		<tr style="height:27px">
																		<td width="50%">
																			<logic:notPresent name="cCAdetailsVO" property="revGrossWeight">

																				<logic:present name="KEY_WEIGHTROUNDINGVO">

																					<bean:define id="revGrossWeightID" name="KEY_WEIGHTROUNDINGVO" />
																						<% request.setAttribute("grossWeight",revGrossWeightID); %>
																							<ibusiness:unitdef id="statedWt" unitTxtName="revGrossWeight" label="" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_GROSSWGT"  unitReq = "false" dataName="grossWeight"
																							 title="Revised Weight"
																							unitValue="0.0" style="text-align:right"
																							indexId="index" styleId="grossweight" />
																				</logic:present>
																			  </logic:notPresent>

																			<logic:present name="cCAdetailsVO" property="revGrossWeight">
																						<bean:define id="revGrossWeightID" name="cCAdetailsVO" property="revGrossWeight" toScope="page"/>

																						<logic:present name="KEY_WEIGHTROUNDINGVO">

																							<bean:define id="revGrossWeight" name="KEY_WEIGHTROUNDINGVO" />
																								<% request.setAttribute("grossWeight",revGrossWeight); %>
																								
																									<!--<ibusiness:unitdef id="statedWt" unitTxtName="revGrossWeight" label="" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_GROSSWGT"  unitReq = "false" dataName="grossWeight"
																									 title="Revised Weight"
																									unitValue="<%=revGrossWeightID.toString()%>" style="text-align:right"
																									indexId="index" styleId="grossweight"/>-->
																									
																									<ihtml:text property="revGrossWeight" name="revGrossWeight" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_GROSSWGT" value="<%=revGrossWeightID.toString()%>"
																									/>
																									
																									
																						</logic:present>

																			  </logic:present>




																			</td>
																			<td  width="50%" class="iCargoTableDataTd">
																				<logic:present name="cCAdetailsVO" property="grossWeight">
																					<bean:define id="grossWeight" name="cCAdetailsVO" property="grossWeight" />
																					 <common:write name="cCAdetailsVO" property="grossWeight" unitFormatting="false" />
										  												
																				</logic:present>
																				<logic:notPresent name="cCAdetailsVO" property="grossWeight">
																				<common:write name="form" property="grossWeight" unitFormatting="false" />
										  											
																				</logic:notPresent>
																			</td>
																		</tr>
																		<tr style="height:27px">
																			<td width="50%">
																				<logic:present name="cCAdetailsVO" property="revChgGrossWeight">
																				<logic:equal name="form" property="overrideRounding" value = "Y">
												 <ibusiness:moneyEntry name="cCAdetailsVO" id="revChgGrossWe" moneyproperty="revChgGrossWeight"  property="revChgGrossWeight" formatMoney="true"   componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_REVWGTCHARGE" overrideRounding = "true"/>
												 	</logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
												 <ibusiness:moneyEntry name="cCAdetailsVO" id="revChgGrossWe" moneyproperty="revChgGrossWeight"  property="revChgGrossWeight" formatMoney="true"   componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_REVWGTCHARGE"/>
													</logic:notEqual>
																				</logic:present>
																				<logic:notPresent name="cCAdetailsVO" property="revChgGrossWeight">
 																				 <ibusiness:moneyEntry  id="revChgGrossWe"  property="revChgGrossWeight" formatMoney="false" value="0"  componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_REVWGTCHARGE" />
																				</logic:notPresent>
																			</td>
																			<td width="50%" class="iCargoTableDataTd">
																				<logic:present name="cCAdetailsVO" property="chgGrossWeight">
																				<logic:equal name="form" property="overrideRounding" value = "Y">
													<ibusiness:moneyDisplay showCurrencySymbol="false" name="cCAdetailsVO"  moneyproperty="chgGrossWeight" property="chgGrossWeight" overrideRounding = "true"/>
																				</logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
													<ibusiness:moneyDisplay showCurrencySymbol="false" name="cCAdetailsVO"  moneyproperty="chgGrossWeight" property="chgGrossWeight"/>
													</logic:notEqual>
													
																				</logic:present>
																				<logic:notPresent name="cCAdetailsVO" property="chgGrossWeight">
																				0.0					
																				</logic:notPresent>
																			</td>
																		</tr>
																		<tr style="height:27px">
																			<td width="50%">
																				<logic:present name="cCAdetailsVO" property="otherRevChgGrossWgt">
																				<logic:equal name="form" property="overrideRounding" value = "Y">
													   <ibusiness:moneyEntry name="cCAdetailsVO" id="otherRevChgGrossWg" moneyproperty="otherRevChgGrossWgt" formatMoney="true"  property="otherRevChgGrossWgt"    componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_REVOTHCHARGE" readonly="true" overrideRounding = "true"/>
													   </logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
													   <ibusiness:moneyEntry name="cCAdetailsVO" id="otherRevChgGrossWg" moneyproperty="otherRevChgGrossWgt" formatMoney="true"  property="otherRevChgGrossWgt"    componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_REVOTHCHARGE" readonly="true"/>
													</logic:notEqual>
																				</logic:present>
																				<logic:notPresent name="cCAdetailsVO" property="otherRevChgGrossWgt">
												 <ibusiness:moneyEntry  id="otherRevChgGrossWg"  property="otherRevChgGrossWgt" formatMoney="false" value="0"  componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_REVOTHCHARGE" readonly="true"/>
																				</logic:notPresent>
																			</td>
																			<td width="50%" class="iCargoTableDataTd">
																				<logic:present name="cCAdetailsVO" property="otherChgGrossWgt">
					<logic:equal name="form" property="overrideRounding" value = "Y">

													<ibusiness:moneyDisplay showCurrencySymbol="false" name="cCAdetailsVO"  moneyproperty="otherChgGrossWgt" property="otherChgGrossWgt" overrideRounding = "true"/>
														</logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
													<ibusiness:moneyDisplay showCurrencySymbol="false" name="cCAdetailsVO"  moneyproperty="otherChgGrossWgt" property="otherChgGrossWgt"/>

													</logic:notEqual>
																				</logic:present>
																				<logic:notPresent name="cCAdetailsVO" property="otherChgGrossWgt">
																				0.0					
																				</logic:notPresent>
																			</td>
																		</tr>
																		<tr style="height:27px">
																			<td  width="50%">
																				<logic:present name="cCAdetailsVO" property="revTax">
																				<logic:equal name="form" property="overrideRounding" value = "Y">
													<ibusiness:moneyEntry name="cCAdetailsVO" id="revTax" moneyproperty="revTax"  property="revTax" formatMoney="true"   componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_REVWGTCHARGE" disabled="true" overrideRounding = "true"/>
													</logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
							<ibusiness:moneyEntry name="cCAdetailsVO" id="revTax" moneyproperty="revTax"  property="revTax" formatMoney="true"   componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_REVWGTCHARGE" disabled="true"/>
													</logic:notEqual>
													
																				</logic:present>
																				<logic:notPresent name="cCAdetailsVO" property="revTax">
 																				 <ibusiness:moneyEntry  id="revTax"  property="revTax" formatMoney="false" value="0"  componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_REVWGTCHARGE" disabled="true"/>
																				</logic:notPresent>
																			</td>
																			<td  width="50%" class="iCargoTableDataTd">
																				<logic:present name="cCAdetailsVO" property="tax">
										<logic:equal name="form" property="overrideRounding" value = "Y">
													<ibusiness:moneyDisplay showCurrencySymbol="false" name="cCAdetailsVO"  moneyproperty="tax" property="tax" overrideRounding = "true"/>
													</logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
													<ibusiness:moneyDisplay showCurrencySymbol="false" name="cCAdetailsVO"  moneyproperty="tax" property="tax"/>
													</logic:notEqual>
													
																							</logic:present>
																				<logic:notPresent name="cCAdetailsVO" property="tax">
																					0
																							</logic:notPresent>

																			</td>
																		</tr>
																		
																		<tr style="height:27px">
																			<td  width="50%">
																				<logic:present name="cCAdetailsVO" property="differenceAmount">
										  <logic:equal name="form" property="overrideRounding" value = "Y">
													<ibusiness:moneyEntry name="cCAdetailsVO" id="differenceAmount" moneyproperty="differenceAmount"  property="differenceAmount" formatMoney="true"   componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_REVWGTCHARGE" disabled="true" overrideRounding = "true"/>
												</logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
													<ibusiness:moneyEntry name="cCAdetailsVO" id="differenceAmount" moneyproperty="differenceAmount"  property="differenceAmount" formatMoney="true"   componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_REVWGTCHARGE" disabled="true"/>
													</logic:notEqual>
																				</logic:present>
																				<logic:notPresent name="cCAdetailsVO" property="differenceAmount">
 																				 <ibusiness:moneyEntry  id="differenceAmount"  property="differenceAmount" formatMoney="false" value="0.00"  componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_NETVALUE" disabled="true"/>
																				</logic:notPresent>
																			</td>   
											                                    <td width="50%">
																				<!--Commented for ICRD-141841-->
																				<!--<logic:present name="cCAdetailsVO" property="netAmount">cc
																					<ibusiness:moneyDisplay showCurrencySymbol="false" name="cCAdetailsVO"  moneyproperty="netAmount" property="netAmount" />
																				</logic:present>
																				<logic:notPresent name="cCAdetailsVO" property="netAmount">
																				0.0					
																				</logic:notPresent>-->
																			</td>
																		</tr>
																		<!--
																		</table>-->
																		


																	</tbody>
																	</table>
																	</td>
																	</tr>
																</table>
															
													</div>
</ihtml:form>
<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>
</html:html>