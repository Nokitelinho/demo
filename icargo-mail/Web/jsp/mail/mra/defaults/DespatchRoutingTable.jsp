
 <%@ include file="/jsp/includes/tlds.jsp"%>
 <%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.DespatchRoutingForm"%>

<bean:define id="form"
	name="DespatchRoutingForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.DespatchRoutingForm"
	toScope="page" />

 <business:sessionBean id="dsnRoutingVOs"
	 moduleName="mailtracking.mra.defaults"
	 screenID="mailtracking.mra.defaults.despatchrouting"
	 method="get"
	 attribute="DSNRoutingVOs" />

 <business:sessionBean id="KEY_WEIGHTROUNDINGVO"
		  moduleName="mailtracking.mra.defaults"
		  screenID="mailtracking.mra.defaults.despatchrouting"
		  method="get"
		  attribute="weightRoundingVO" />

<business:sessionBean id="agreementTypesSession"
          moduleName="mailtracking.mra.defaults"
 		  screenID="mailtracking.mra.defaults.despatchrouting"
		  method="get"
		  attribute="agreementTypes" />

<business:sessionBean id="blockspaceTypesSession"
          moduleName="mailtracking.mra.defaults"
 		  screenID="mailtracking.mra.defaults.despatchrouting"
		  method="get"
		  attribute="blockSpaceTypes" />




					<tbody id="RoutingDetailsBody">
					<%int count=0;%>
					<logic:present name="dsnRoutingVOs">
					<logic:iterate id="vo" name="dsnRoutingVOs" indexId="rowCount"  type="com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNRoutingVO" >

					<%boolean showFlg=false;%>
					<logic:equal name="vo" property="operationFlag" value="">
					<%showFlg=true;%>
					</logic:equal>
					<logic:equal name="vo" property="operationFlag" value="U">
					<%showFlg=true;%>
					</logic:equal>




					<%if(showFlg){%>
					<html:hidden property="hiddenOpFlag" value="U"/>

					<tr>
						<td class="ic-center">
						<input type="checkbox" name="checkBoxForFlight"   value="<%=String.valueOf(count)%>" />
						</td>
						<logic:present name="vo" property="flightNumber">
						<td class="iCargoTableTd">
						<ibusiness:flightnumber id="flightCarrierId"
						carrierCodeProperty="flightCarrierCode"
						flightCodeProperty="flightNumber"
						componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_FLIGHTNO"
						carrierCodeStyleClass="iCargoTextFieldVerySmall"
						flightCodeStyleClass="iCargoTextFieldSmall"
						carriercodevalue="<%=vo.getFlightCarrierCode()%>" flightcodevalue="<%=vo.getFlightNumber()%>" indexId="rowCount" />
						</td>
						</logic:present>
						<logic:notPresent name="vo" property="flightNumber">
						<td class="iCargoTableTd">
						<ibusiness:flightnumber id="flightCarrierId"
						carrierCodeProperty="flightCarrierCode"
						flightCodeProperty="flightNumber"
						componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_FLIGHTNO"
						carrierCodeStyleClass="iCargoTextFieldVerySmall"
						flightCodeStyleClass="iCargoTextFieldSmall" carriercodevalue="" flightcodevalue=""  indexId="rowCount" />
						</td>
						</logic:notPresent>




						<logic:present name="vo" property="departureDate">
						<td class="iCargoTableTd" >
						<ibusiness:calendar id="departureDate"
						property="departureDate" value="<%=vo.getDepartureDate().toDisplayDateOnlyFormat()%>"
						componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_DEPDATE" indexId="rowCount"
						type="image"
						maxlength="11"/>
						</td>
						</logic:present>
						<logic:notPresent name="vo" property="departureDate">
						<td class="iCargoTableTd">
						<ibusiness:calendar id="departureDate"
						property="departureDate" indexId="rowCount"
						componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_DEPDATE"
						type="image"
						maxlength="11"
						  value=""/>
						</td>
						</logic:notPresent>

						<td class="ic-center" >
						<logic:present name="vo" property="flightType">
						<logic:equal name="vo" property="flightType" value="T">
						<input type="checkbox" disabled='true' checked='true'>
						</logic:equal>
						<logic:notEqual name="vo" property="flightType" value="T">
						<input type="checkbox" disabled='true' >
						</logic:notEqual>
						</logic:present>
						<logic:notPresent name="vo" property="flightType">
						<input type="checkbox" disabled='true' >
						</logic:notPresent>
						</td>


						<logic:present name="vo" property="pol">
						<td class="iCargoTableTd">
						<ihtml:text property="pol" name="vo"  componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_POL" maxlength="4" />
						<div class="lovImgTbl valignT">
						<img src="<%=request.getContextPath()%>/images/lov.png" name="airportlov" id="airportlov<%=rowCount%>" height="16" width="16" /></td>
						</div>
						</logic:present>
						<logic:notPresent name="vo" property="pol">
						<td class="iCargoTableTd">
						<ihtml:text property="pol" name="form"  componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_POL" maxlength="4" />
						<div class="lovImgTbl valignT">
						<img src="<%=request.getContextPath()%>/images/lov.png" name="airportlov" id="airportlov<%=rowCount%>" height="16" width="16" /></td>
						</div>
						</logic:notPresent>


						<logic:present name="vo" property="pou">
						<td class="iCargoTableTd">
						<ihtml:text property="pou" name="vo"   componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_POU" maxlength="4" />
						<div class="lovImgTbl valignT">
						<img src="<%=request.getContextPath()%>/images/lov.png" name="poulov" id="poulov<%=rowCount%>" height="16" width="16" />
						</div>
						</td>
						</logic:present>
						<logic:notPresent name="vo" property="pou" >
						<td class="iCargoTableTd">
						<ihtml:text property="pou" name="form"  componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_POU" maxlength="4" />
						<div class="lovImgTbl valignT">
						<img src="<%=request.getContextPath()%>/images/lov.png" name="poulov" id="poulov<%=rowCount%>" height="16" width="16" />
						</div>
						</td>
						</logic:notPresent>


						<logic:present name="vo" property="acctualnopieces">
								<logic:present name="vo" property="ownairlinecode">
								  <%String owncarcod=vo.getOwnairlinecode();%>
									<logic:present name="vo" property="nopieces">
									<td class="iCargoTableTd">
									 <logic:equal name="vo" property="flightCarrierCode" value="<%=owncarcod%>">
									   <ihtml:text property="nopieces" value="<%=String.valueOf(vo.getNopieces())%>"  componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_PIECES" />
									 </logic:equal>
									  <logic:notEqual name="vo" property="flightCarrierCode" value="<%=owncarcod%>">
									    <ihtml:text property="nopieces" value="<%=String.valueOf(vo.getAcctualnopieces())%>" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_PIECES" disabled="true" />
									 </logic:notEqual>
									</td>
								</logic:present>
							 </logic:present>
						</logic:present>
						<logic:notPresent name="vo" property="nopieces">
						<td class="iCargoTableTd">
						<ihtml:text property="nopieces" value=""  componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_PIECES"  />
						</td>
						</logic:notPresent>



						<td  class="iCargoTableTd">

						<ihtml:hidden property="bsaReference" value="<%=vo.getBsaReference()%>"/>

							<logic:notPresent name="vo"  property="blockSpaceType">
						<ihtml:select property="blockSpaceType" indexId="rowCount"  componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_BLOCKSPACE_TYPE" value="">
						<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
						<logic:present name="blockspaceTypesSession">

									<logic:iterate id="blockspaceTypeVO" name="blockspaceTypesSession">
												<bean:define id="fieldValue" name="blockspaceTypeVO" property="fieldValue" />
												<html:option value="<%=(String)fieldValue %>"><bean:write name="blockspaceTypeVO" property="fieldDescription" /></html:option>
									</logic:iterate>
						</logic:present>
						</ihtml:select>
						</logic:notPresent>
						<logic:present  name="vo"  property="blockSpaceType">

							<bean:define id="blockSpaceType_val" name="vo"  property="blockSpaceType"  toScope="page"/>
							<ihtml:select property="blockSpaceType"  indexId="rowCount" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_BLOCKSPACE_TYPE" value="<%=(String)blockSpaceType_val%>">
			                     <ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
						          <logic:present name="blockspaceTypesSession">

									<logic:iterate id="blockspaceTypeVO" name="blockspaceTypesSession">
												<bean:define id="fieldValue" name="blockspaceTypeVO" property="fieldValue" />
												<html:option value="<%=(String)fieldValue %>"><bean:write name="blockspaceTypeVO" property="fieldDescription" /></html:option>
									</logic:iterate>
					              </logic:present>
						    </ihtml:select>
					    </logic:present>
						</td>

									<logic:present name="vo" property="weight">
										<td class="iCargoTableTd">

									   		<logic:present name="KEY_WEIGHTROUNDINGVO">
												<bean:define id="sampleStdWtVo" name="KEY_WEIGHTROUNDINGVO" />
												<% request.setAttribute("sampleStdWt",sampleStdWtVo); %>
												<ibusiness:unitdef id="weight" unitTxtName="weight" label=""  unitReq = "false" dataName="sampleStdWt"
													unitValueStyle="iCargoEditableTextFieldRowColor1" title="Weight"
													unitValue="<%=String.valueOf(vo.getWeight())%>"

													indexId="index" styleId="statedWt" disabled="true" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_WEIGHT"/>
											</logic:present>
										</td>
						</logic:present>

						<logic:notPresent name="vo" property="weight">
						<td class="iCargoTableTd" >
						<ibusiness:unitdef id="weight" unitTxtName="weight" label=""  unitReq = "false" dataName="sampleStdWt"
													unitValueStyle="iCargoEditableTextFieldRowColor1" title="Weight"
													unitValue=""
													indexId="index" styleId="statedWt" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_WEIGHT"/>
						</td>
						</logic:notPresent>
						<td class="iCargoTableTd">
						<logic:notPresent name="vo"  property="agreementType">
						<ihtml:select property="agreementType" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_AGREEMENT_TYPE">
						<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
						<logic:present name="agreementTypesSession">

									<logic:iterate id="agreementTypeVO" name="agreementTypesSession">
												<bean:define id="fieldValue" name="agreementTypeVO" property="fieldValue" />
												<html:option value="<%=(String)fieldValue %>"><bean:write name="agreementTypeVO" property="fieldDescription" /></html:option>
									</logic:iterate>
						</logic:present>
						</ihtml:select>
						</logic:notPresent>
						<logic:present  name="vo"  property="agreementType">
							<bean:define id="agreementType_val" name="vo"  property="agreementType"  toScope="page"/>
							<ihtml:select property="agreementType" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_AGREEMENT_TYPE" value="<%=(String)agreementType_val%>">
			                     <ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
						          <logic:present name="agreementTypesSession">
									<logic:iterate id="agreementTypeVO" name="agreementTypesSession">
												<bean:define id="fieldValue" name="agreementTypeVO" property="fieldValue" />
												<html:option value="<%=(String)fieldValue %>"><bean:write name="agreementTypeVO" property="fieldDescription" /></html:option>
									</logic:iterate>
					              </logic:present>
						    </ihtml:select>
					    </logic:present>

						</td>
						<td class="iCargoTableTd">
							<center>
								<ihtml:text property="source" value="<%=String.valueOf(vo.getSource())%>"  componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_RTGSOURCE" disabled="true"/>
							</center>
						</td>

						</tr>
						<%}%>



						<logic:equal name="vo" property="operationFlag" value="I">
						<html:hidden property="hiddenOpFlag" value="I"/>

						<tr>
							<td width="5%" class="iCargoTableTd"><center>
							<input type="checkbox" name="checkBoxForFlight" value="<%=String.valueOf(count)%>"   /></center>
							</td>

							<td width="17%" class="iCargoTableTd">
							<logic:present name="vo" property="flightNumber">
							<ibusiness:flightnumber id="flightCarrierId"
							carrierCodeProperty="flightCarrierCode"
							flightCodeProperty="flightNumber"
							componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_FLIGHTNO"
							carrierCodeStyleClass="iCargoTextFieldVerySmall"
							flightCodeStyleClass="iCargoTextFieldSmall"
							carriercodevalue="<%=vo.getFlightCarrierCode()%>" flightcodevalue="<%=vo.getFlightNumber()%>" indexId="rowCount" />
							</logic:present>
							<logic:notPresent name="vo" property="flightNumber">
							<ibusiness:flightnumber id="flightCarrierId"
							carrierCodeProperty="flightCarrierCode"
							flightCodeProperty="flightNumber"
							componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_FLIGHTNO"
							carrierCodeStyleClass="iCargoTextFieldVerySmall"
							flightCodeStyleClass="iCargoTextFieldSmall" carriercodevalue="" flightcodevalue=""  indexId="rowCount"  />
							</logic:notPresent>

							</td>


							<td width="17%" class="iCargoTableTd">

							<logic:present name="vo" property="departureDate">
							<ibusiness:calendar id="departureDate"
							property="departureDate" value="<%=vo.getDepartureDate().toDisplayDateOnlyFormat()%>"
							componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_DEPDATE" indexId="rowCount"
							type="image"
							maxlength="11"
							/>
							</logic:present>
							<logic:notPresent name="vo" property="departureDate">
							<ibusiness:calendar id="departureDate" value=""
							property="departureDate"
							componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_DEPDATE" indexId="rowCount"
							type="image"
							maxlength="11"
							/>
							</logic:notPresent>
							</td>

							<td class="ic-center" >
							<input type="checkbox" disabled='true' >
							</td>

							<td width="14%" class="iCargoTableTd">
							<logic:present name="vo" property="pol">
							<ihtml:text property="pol" name="vo"  componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_POL" indexId="rowCount" maxlength="4" />
							<div class="lovImgTbl valignT">
							<img src="<%=request.getContextPath()%>/images/lov.png" name="airportlov" id="airportlov<%=rowCount%>" height="16" width="16" />
							</div>
							</logic:present>
							<logic:notPresent name="vo" property="pol">
							<ihtml:text property="pol" name="form"  componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_POL" indexId="rowCount" maxlength="4" />
							<div class="lovImgTbl valignT">
							<img src="<%=request.getContextPath()%>/images/lov.png" name="airportlov"  id="airportlov<%=rowCount%>" height="16" width="16" />
							</div>
							</logic:notPresent>


							</td>

							<td  width="14%" class="iCargoTableTd">
							<logic:present name="vo" property="pou">
							<ihtml:text property="pou" name="vo" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_POU" indexId="rowCount" maxlength="4" />
							<div class="lovImgTbl valignT">
							<img src="<%=request.getContextPath()%>/images/lov.png" id="poulov<%=rowCount%>" name="poulov" height="16" width="16" />
							</div>
							</logic:present>
							<logic:notPresent name="vo" property="pou">
							<ihtml:text property="pou" name="form"  componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_POU" indexId="rowCount" maxlength="4" />
							<div class="lovImgTbl valignT">
							<img src="<%=request.getContextPath()%>/images/lov.png" id="poulov<%=rowCount%>" name="poulov" height="16" width="16" />
							</div>
							</logic:notPresent>
							</td>

							  <logic:present name="vo" property="acctualnopieces">
										<logic:present name="vo" property="ownairlinecode">
										  <%String owncarcod=vo.getOwnairlinecode();%>
											<logic:present name="vo" property="nopieces">
											<td width="14%" class="iCargoTableTd">
											 <logic:equal name="vo" property="flightCarrierCode" value="<%=owncarcod%>">
											   <ihtml:text property="nopieces" value="<%=String.valueOf(vo.getNopieces())%>"  componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_PIECES"/>
											 </logic:equal>
											  <logic:notEqual name="vo" property="flightCarrierCode" value="<%=owncarcod%>">
											    <ihtml:text property="nopieces" value="<%=String.valueOf(vo.getAcctualnopieces())%>" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_PIECES"/>
											 </logic:notEqual>
											</td>
										</logic:present>
									 </logic:present>
									 <logic:notPresent name="vo" property="ownairlinecode">
									 <td  width="14%" class="iCargoTableTd">
										 <logic:equal name="vo" property="acctualnopieces" value="0">
											 <ihtml:text property="nopieces" value="" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_PIECES"/>
										 </logic:equal>
									 </td>
									 </logic:notPresent>
								</logic:present>
								<logic:notPresent name="vo" property="nopieces">
								<td width="14%" class="iCargoTableTd">
								<ihtml:text property="nopieces" value=""  componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_PIECES" />
								</td>
								</logic:notPresent>


								<td  width="14%" class="iCargoTableTd">
							<logic:notPresent name="vo"  property="blockSpaceType">
						<ihtml:select property="blockSpaceType" indexId="rowCount" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_BLOCKSPACE_TYPE" value="">
						<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
						<logic:present name="blockspaceTypesSession">

									<logic:iterate id="blockspaceTypeVO" name="blockspaceTypesSession">
												<bean:define id="fieldValue" name="blockspaceTypeVO" property="fieldValue" />
												<html:option value="<%=(String)fieldValue %>"><bean:write name="blockspaceTypeVO" property="fieldDescription" /></html:option>
									</logic:iterate>
						</logic:present>
						</ihtml:select>
						</logic:notPresent>
						<logic:present  name="vo"  property="blockSpaceType">

							<bean:define id="blockSpaceType_val" name="vo"  property="blockSpaceType"  toScope="page"/>
							<ihtml:select property="blockSpaceType" indexId="rowCount"  componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_BLOCKSPACE_TYPE" value="<%=(String)blockSpaceType_val%>">
			                     <ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
						          <logic:present name="blockspaceTypesSession">

									<logic:iterate id="blockspaceTypeVO" name="blockspaceTypesSession">
												<bean:define id="fieldValue" name="blockspaceTypeVO" property="fieldValue" />
												<html:option value="<%=(String)fieldValue %>"><bean:write name="blockspaceTypeVO" property="fieldDescription" /></html:option>
									</logic:iterate>
					              </logic:present>
						    </ihtml:select>
					    </logic:present>
						</td>


								<logic:present name="vo" property="acctualweight">
									<logic:present name="vo" property="ownairlinecode">
										  <%String owncarcod = vo.getOwnairlinecode();%>
											<logic:present name="vo" property="weight">
												<td class="iCargoTableTd">
												<logic:equal name="vo" property="flightCarrierCode" value="<%=owncarcod%>">
												<bean:define id="sampleStdWtVo" name="KEY_WEIGHTROUNDINGVO" />
												<% request.setAttribute("sampleStdWt",sampleStdWtVo); %>
													<ibusiness:unitdef id="weight" unitTxtName="weight" label=""  unitReq = "false" dataName="sampleStdWt"
													unitValueStyle="iCargoEditableTextFieldRowColor1" title="Weight"
													unitValue="<%=String.valueOf(vo.getWeight())%>"
													indexId="index" styleId="statedWt" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_WEIGHT"/>
												 </logic:equal>
												  <logic:notEqual name="vo" property="flightCarrierCode" value="<%=owncarcod%>">
												  <bean:define id="sampleStdWtVo" name="KEY_WEIGHTROUNDINGVO" />
												<% request.setAttribute("sampleStdWt",sampleStdWtVo); %>
													<ibusiness:unitdef id="weight" unitTxtName="weight" label=""  unitReq = "false" dataName="sampleStdWt"
													unitValueStyle="iCargoEditableTextFieldRowColor1" title="Weight"
													unitValue="<%=String.valueOf(vo.getAcctualweight())%>"
													indexId="index" styleId="statedWt" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_WEIGHT"/>
												 </logic:notEqual>
												</td>
												<ihtml:text property="displayWgtUnit" value="<%=String.valueOf(vo.getDisplayWgtUnit())%>"   />
											</logic:present>
									 </logic:present>
									 <logic:notPresent name="vo" property="ownairlinecode">
										 <td width="19%" class="iCargoTableTd">
											 <logic:equal name="vo" property="acctualweight" value="0.0">
											 <bean:define id="sampleStdWtVo" name="KEY_WEIGHTROUNDINGVO" />
												<% request.setAttribute("sampleStdWt",sampleStdWtVo); %>
												 <ibusiness:unitdef id="weight" unitTxtName="weight" label=""  unitReq = "false" dataName="sampleStdWt"
													unitValueStyle="iCargoEditableTextFieldRowColor1" title="Weight"
													unitValue=""
													indexId="index" styleId="statedWt" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_WEIGHT"/>
											 </logic:equal>
										 </td>
									 </logic:notPresent>
								</logic:present>



								<logic:notPresent name="vo" property="weight">
								<td width="19%" class="iCargoTableTd">

									<logic:present name="KEY_WEIGHTROUNDINGVO">
										<bean:define id="sampleStdWtVo" name="KEY_WEIGHTROUNDINGVO" />
										<% request.setAttribute("sampleStdWt",sampleStdWtVo); %>
										<ibusiness:unitdef id="weight" unitTxtName="weight" label=""  unitReq = "false" dataName="sampleStdWt"
											unitValueStyle="iCargoEditableTextFieldRowColor1" title="Weight"
											unitValue="0.0"
											indexId="index" styleId="statedWt" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_WEIGHT"/>
									</logic:present>

								<%--
								<ihtml:text property="weight" value="" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_WEIGHT" />
								--%>
								</td>
								</logic:notPresent>
                       <td class="iCargoTableTd" width="10%">
						<logic:notPresent name="vo"  property="agreementType">
						<ihtml:select property="agreementType" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_AGREEMENT_TYPE" value="">
						<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
						<logic:present name="agreementTypesSession">

									<logic:iterate id="agreementTypeVO" name="agreementTypesSession">
												<bean:define id="fieldValue" name="agreementTypeVO" property="fieldValue" />
												<html:option value="<%=(String)fieldValue %>"><bean:write name="agreementTypeVO" property="fieldDescription" /></html:option>
									</logic:iterate>
						</logic:present>
						</ihtml:select>
						</logic:notPresent>
						<logic:present  name="vo"  property="agreementType">

							<bean:define id="agreementType_val" name="vo"  property="agreementType"  toScope="page"/>
							<ihtml:select property="agreementType" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_AGREEMENT_TYPE" value="<%=(String)agreementType_val%>">
			                     <ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
						          <logic:present name="agreementTypesSession">
								  nnnnn
									<logic:iterate id="agreementTypeVO" name="agreementTypesSession">
												<bean:define id="fieldValue" name="agreementTypeVO" property="fieldValue" />
												<html:option value="<%=(String)fieldValue %>"><bean:write name="agreementTypeVO" property="fieldDescription" /></html:option>
									</logic:iterate>
					              </logic:present>
						    </ihtml:select>
					    </logic:present>
						</td>
						<td class="iCargoTableTd">
							<ihtml:text property="source" value=" "  componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_RTGSOURCE" disabled="true"/>
						</td>
							</tr>

							</logic:equal>

							<logic:equal name="vo" property="operationFlag" value="D">
							<tr style="display:none">
									<html:hidden property="hiddenOpFlag" value="D"/>
									<td class="iCargoTableTd" style="display:none" >
					  				 <input type="checkbox" name="checkBoxForFlight" value="<%=String.valueOf(count)%>" />
				       				 </td>
									<ihtml:hidden property="flightCarrierCode" value="<%=vo.getFlightCarrierCode()%>"/>
									<ihtml:hidden property="flightNumber" value="<%=vo.getFlightNumber()%>"/>
									<ihtml:hidden property="departureDate" value="<%=vo.getDepartureDate().toDisplayDateOnlyFormat()%>"/>
									<ihtml:hidden property="pol" value="<%=vo.getPol()%>"/>
									<ihtml:hidden property="pou" value="<%=vo.getPou()%>"/>
									<ihtml:hidden property="nopieces" value="<%=String.valueOf(vo.getNopieces())%>"/>
									<ihtml:hidden property="weight" value="<%=String.valueOf(vo.getWeight())%>"/>
									<ihtml:hidden property="agreementType" value="<%=String.valueOf(vo.getAgreementType())%>"/>
									<ihtml:hidden property="blockSpaceType" value="<%=String.valueOf(vo.getBlockSpaceType())%>"/>
									<ihtml:hidden property="source" value="<%=String.valueOf(vo.getSource())%>"/>
							</tr>
							</logic:equal>
							<%count++;%>
							</logic:iterate>
						</logic:present>
					</tbody>