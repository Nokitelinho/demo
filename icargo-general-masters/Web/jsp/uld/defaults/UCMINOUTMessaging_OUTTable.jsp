<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import = "java.util.Collection" %>

<business:sessionBean id="reconcileVO"
moduleName="uld.defaults"
screenID="uld.defaults.ucminoutmessaging"
method="get" attribute="messageReconcileVO" />

<business:sessionBean id="contentTypes"
moduleName="uld.defaults"
screenID="uld.defaults.ucminoutmessaging"
method="get" attribute="contentType" />

<business:sessionBean id="stations"
moduleName="uld.defaults"
screenID="uld.defaults.ucminoutmessaging"
method="get" attribute="destinations" />

<business:sessionBean id="destinations"
moduleName="uld.defaults"
screenID="uld.defaults.ucminoutmessaging"
method="get" attribute="outDestinations" />

<business:sessionBean id="oneTimeValues"
moduleName="uld.defaults"
screenID="uld.defaults.ucminoutmessaging"
method="get" attribute="oneTimeValues" />

<business:sessionBean id="messageStatus"
moduleName="uld.defaults"
screenID="uld.defaults.ucminoutmessaging"
method="get" attribute="messageStatus" />

<logic:present name="contentTypes">
	<bean:define id="contents" name="contentTypes" toScope="page"/>
			</logic:present>
<div id="div1" class="tableContainer" style="height:330px;">
					<table class="fixed-header-table" id="UCMINOUTMessaging">
                         <thead>
                            <tr class="iCargoTableHeadingLeft">
      <td width="5%" >
      <input name="masterRowId" type="checkbox" value="checkbox"/>
                              </td>
                              <td width="15%"  ><common:message key="uld.defaults.ucminout.uldno" scope="request"/><span class="iCargoMandatoryFieldIcon">*</span></td>
							  <td width="20%"  ><common:message key="uld.defaults.ucminout.dmg" scope="request"/></td>
							<td width="10%"  ><common:message key="uld.defaults.ucminout.content" scope="request"/></td>
                              <td width="20%" ><common:message key="uld.defaults.ucminout.destn" scope="request"/></td>
                              <td width="15%" ><common:message key="uld.defaults.ucminout.source"/></td><!--Added  by A-7359 for ICRD-192413-->
                              <td width="15%" ><common:message key="uld.defaults.ucminout.messagestatus"/></td><!--Added  by A-7359 for ICRD-192413-->
                            </tr>
                          </thead>

<tbody id ="ucmoutbody">
<%int index = 0;%>
                           <logic:present name="reconcileVO" property="reconcileDetailsVOs" >
							   <bean:define id="reconcileDetails" name="reconcileVO"  property="reconcileDetailsVOs"/>
								<logic:iterate id="reconcileDetailsVO" name="reconcileDetails" indexId="nIndex" type="com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileDetailsVO">
								<common:rowColorTag index="nIndex">
							   <logic:present name="reconcileDetailsVO" property="operationFlag">
		<bean:define id="flag" name="reconcileDetailsVO" property="operationFlag"/>
	<logic:notEqual name="flag" value="D">
                            <tr>
                             <logic:present name="reconcileDetailsVO" property="operationFlag">
								<ihtml:hidden property="operationFlag" value="<%=reconcileDetailsVO.getOperationFlag()%>"/>
								</logic:present>
								<logic:notPresent name="reconcileDetailsVO" property="operationFlag">
									<ihtml:hidden property="operationFlag" value="NA"/>
								 </logic:notPresent>
								 <logic:present name="reconcileDetailsVO" property="errorCode" >
								 <ihtml:hidden property="errorCodes" indexId="nIndex" styleId="errorCode" value="<%=reconcileDetailsVO.getErrorCode()%>" />
								 </logic:present>
								 <logic:notPresent name="reconcileDetailsVO" property="errorCode" >
								 <ihtml:hidden property="errorCodes"  indexId="nIndex" styleId="errorCode" value="" />
								 </logic:notPresent>

		<td  class="iCargoTableDataTd">
		<center>
		<!-- Modified as part of bug ICRD-238949 -->
		<input type="checkbox" name="selectedRows" value="<%=String.valueOf(index)%>" />
		<!--html:checkbox property="selectedRows" value="<!-%=String.valueOf(index)%>" /-->
		<!-- Modified as part of bug ICRD-238949 ends -->
		<ihtml:hidden property="hiddenOpFlag" value="U"/>
		</center>
		</td>

	        <td  class="iCargoTableDataTd" align="center">
	        <center>
                              <logic:present name="reconcileDetailsVO" property="uldNumber">
                             	<logic:present name="reconcileDetailsVO" property="sequenceNumber">
                             		<bean:define id="seqNumber" name="reconcileDetailsVO" property="sequenceNumber" />
                             			<logic:match name="seqNumber" value="0">
                             				
											<ibusiness:uld id="uldno" styleClass="iCargoEditableTextFieldRowColor1" uldProperty="uldNumbers" uldValue="<%=reconcileDetailsVO.getUldNumber()%>" componentID="CMP_ULD_DEFAULTS_UCMINOUT_ULDNUMBER" style="width:130px;text-transform: uppercase" maxlength="12"/>
                             			</logic:match>
                             			<logic:notMatch name="seqNumber" value="0">
                             				
											<ibusiness:uld id="uldno" styleClass="iCargoEditableTextFieldRowColor1" uldProperty="uldNumbers" uldValue="<%=reconcileDetailsVO.getUldNumber()%>" componentID="CMP_ULD_DEFAULTS_UCMINOUT_ULDNUMBER" style="width:130px;text-transform: uppercase" maxlength="12" readonly="true"/>
                             			</logic:notMatch>
                             	</logic:present>
                             	<logic:notPresent name="reconcileDetailsVO" property="sequenceNumber">
									<ibusiness:uld id="uldno" styleClass="iCargoEditableTextFieldRowColor1" uldProperty="uldNumbers" uldValue="<%=reconcileDetailsVO.getUldNumber()%>" componentID="CMP_ULD_DEFAULTS_UCMINOUT_ULDNUMBER" style="width:130px;text-transform: uppercase" maxlength="12"/>
                             	</logic:notPresent>
                                </logic:present>

                              <logic:notPresent name="reconcileDetailsVO" property="uldNumber" >
                                
								<ibusiness:uld id="uldno" uldProperty="uldNumbers" styleClass="iCargoEditableTextFieldRowColor1" componentID="CMP_ULD_DEFAULTS_UCMINOUT_ULDNUMBER" style="width:130px;text-transform: uppercase" maxlength="12"/>
			</logic:notPresent>
		</center>
                              </td>

		<td  class="iCargoTableTd ic-center">
		<center>
                                <logic:present name="reconcileDetailsVO" property="damageStatus">
										<bean:define id="dmgStatus" name="reconcileDetailsVO" property="damageStatus"/>
											<logic:equal name="dmgStatus" value="Y">
												<input type="checkbox" name="damageCodes" checked value="<%=String.valueOf(nIndex)%>" />
											</logic:equal>
											<logic:notEqual name="dmgStatus" value="Y">
												<input type="checkbox" name="damageCodes" value="<%=String.valueOf(nIndex)%>"  />
											</logic:notEqual>
											</logic:present>
											<logic:notPresent name="reconcileDetailsVO" property="damageStatus">
											<input type="checkbox" name="damageCodes" value="<%=String.valueOf(nIndex)%>"  />
										</logic:notPresent>
		</center>
		</td>

							  <td class="iCargoTableDataTd">
							  <center>
							  <!--Added by A-7359 for ICRD-256142 starts here-->
								<logic:present name="reconcileDetailsVO" property="content">
								<bean:define id="contentsfromVo" name="reconcileDetailsVO" property="content"/>
								<%String uldSource = "";%>
								<logic:present name="reconcileDetailsVO" property="uldSource">
									<bean:define id="src" name="reconcileDetailsVO" property="uldSource"/>
									<% uldSource = src.toString();%>
												</logic:present>
								<% if("UCM".equals(uldSource) || "MFT".equals(uldSource) ||  "MAL".equals(uldSource)){%>
								  	<logic:present name="contents">
										 <logic:iterate id="content_iter" name="contents">
											<logic:equal name="content_iter" property="fieldValue" value="<%=(String)contentsfromVo%>" >
												 <bean:write name="content_iter" property="fieldDescription" />
												 <ihtml:hidden property="contentInd" value="<%=(String)contentsfromVo%>" /> 
											</logic:equal>
										 </logic:iterate>
									</logic:present>
								<%}else{%>	
								<ihtml:select property="contentInd" styleClass="iCargoMediumComboBox" value="<%=(String)contentsfromVo%>">
									<bean:define id="contents" name="contentTypes" toScope="page"/>
									<ihtml:options collection="contents" property="fieldValue" labelProperty="fieldDescription"/>
										</ihtml:select>
								 <%}%>
								</logic:present>
								<logic:notPresent name="reconcileDetailsVO" property="content">
									<ihtml:select property="contentInd"  styleClass="iCargoMediumComboBox" >
									 <bean:define id="contents" name="contentTypes" toScope="page"/>
									 <ihtml:options collection="contents" property="fieldValue" labelProperty="fieldDescription"/>
									</ihtml:select>
								</logic:notPresent>
										</center>
								<!--Added by A-7359 for ICRD-256142 ends here-->
							 </td>

		<td  class="iCargoTableDataTd">
		<center>
							<!--Added by A-7359 for ICRD-256142 starts here-->
							  <logic:present name="reconcileDetailsVO" property="pou">
								<bean:define id="poufromVo" name="reconcileDetailsVO" property="pou"/>
							<%String uldSource = "";%>
								<logic:present name="reconcileDetailsVO" property="uldSource">
									<bean:define id="src" name="reconcileDetailsVO" property="uldSource"/>
									<% uldSource = src.toString();%>
								</logic:present>
								<% if("UCM".equals(uldSource) || "MFT".equals(uldSource) ||  "MAL".equals(uldSource)){%>
								  	<logic:present name="stations">
									<bean:define id="stationsFromSession" name="stations"/>
									<% Collection<String> stns=(ArrayList<String>)stationsFromSession;
									 String voPou=(String)poufromVo;
									  if(stns.contains(voPou)){%>
									   <bean:define id="displayPou" value="<%=(String)poufromVo%>" />
									  <bean:write name="displayPou" />
									   <ihtml:hidden property="pou" value="<%=(String)poufromVo%>" /> 
									  <%}%>
									</logic:present>
								<%}else{%>
							  	 <ihtml:select property="pou" value="<%=reconcileDetailsVO.getPou()%>" styleClass="iCargoMediumComboBox">
							  	 <logic:present name="stations">
							  	 	<ihtml:options name="stations" />
							  	 </logic:present>
							  	 </ihtml:select>
								<%}%>
							   </logic:present>
                                <logic:notPresent name="reconcileDetailsVO" property="pou">
									 <ihtml:select value=" " property="pou"  styleClass="iCargoMediumComboBox">
										 <logic:present name="stations">
										<ihtml:options name="stations" />
										</logic:present>
							  	 </ihtml:select>
			</logic:notPresent>
			<!--Added by A-7359 for ICRD-256142 ends here-->
				</center>
                              </td>
			<td class="iCargoTableDataTd">
		<center>
										<logic:present name="reconcileDetailsVO" property="uldSource">
												<bean:define id="source" name="reconcileDetailsVO" property="uldSource" />
												  <logic:present name="oneTimeValues">
												<bean:define id="onetimemaps" name="oneTimeValues"/>
													<logic:iterate id ="onetimemap" name="onetimemaps">
													<bean:define id="keymap" name="onetimemap" property="key"/>
														<logic:equal name="keymap" value ="uld.defaults.ucminout.uldsourceforucm">
														 <bean:define id="valuemap" name="onetimemap" property="value" type="java.util.Collection"/>
													<logic:iterate id="value" name="valuemap" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
														<logic:present name="value" property="fieldValue" >
																<bean:define id="fieldValue" name="value" property="fieldValue"/>
																     <logic:equal name="value" property="fieldValue" value="<%=(String)source%>" >
																	     <bean:write name="value" property="fieldDescription" />
																          </logic:equal>
																	</logic:present>
																	</logic:iterate>
																	</logic:equal>
																	</logic:iterate>
															</logic:present>
																		<ihtml:hidden property="uldSource" value="<%=(String)source%>" />  <!-- Added as part of bug ICRD-223807-->
																	</logic:present>
											<logic:notPresent name="reconcileDetailsVO" property="uldSource">
											<ihtml:hidden property="uldSource" value="" />
											</logic:notPresent>			
													</center>
													</td>
			<td class="iCargoTableDataTd">
			<center>
										<logic:present name="reconcileDetailsVO" property="uldStatus">
												<bean:define id="status" name="reconcileDetailsVO" property="uldStatus" />
												  <logic:present name="oneTimeValues">
												<bean:define id="onetimemaps" name="oneTimeValues"/>
													<logic:iterate id ="onetimemap" name="onetimemaps">
													<bean:define id="keymap" name="onetimemap" property="key"/>
														<logic:equal name="keymap" value ="uld.defaults.ucminout.uldsentstatus">
														 <bean:define id="valuemap" name="onetimemap" property="value" type="java.util.Collection"/>
													<logic:iterate id="value" name="valuemap" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
														<logic:present name="value" property="fieldValue" >
																<bean:define id="fieldValue" name="value" property="fieldValue"/>
																     <logic:equal name="value" property="fieldValue" value="<%=(String)status%>" >
																	     <bean:write name="value" property="fieldDescription" />
																          </logic:equal>
																	</logic:present>
																	</logic:iterate>
																	</logic:equal>
																	</logic:iterate>
															</logic:present>
																	  <ihtml:hidden property="uldStatus" value="<%=(String)status%>" />
														</logic:present>
															<logic:notPresent name="reconcileDetailsVO" property="uldStatus">
												<ihtml:hidden property="uldStatus" value="" />
											</logic:notPresent>	
														</center>
                              </td>
                            </tr>
    </logic:notEqual>
	    <logic:equal name="flag" value="D">
                            <ihtml:hidden property="uldNumbers" value="<%=reconcileDetailsVO.getUldNumber()%>" />
                            <ihtml:hidden property="pou" value="<%=reconcileDetailsVO.getPou()%>" />
                            <ihtml:hidden property="operationFlag" value="D"/>
                            <ihtml:hidden property="contentInd" value=""/>
                            <logic:present name="reconcileDetailsVO" property="errorCode" >
								 <ihtml:hidden property="errorCodes" indexId="nIndex" styleId="errorCode" value="<%=reconcileDetailsVO.getErrorCode()%>" />
						 </logic:present>
    </logic:equal>
						 </logic:present>
                            </common:rowColorTag>
    <%index++;%>
							</logic:iterate>
							</logic:present>

<!-- templateRow -->
<tr template="true" id="ucmoutTemplateRow" style="display:none">
	<td  class="iCargoTableDataTd" >
	<center>  
		<!-- Modified as part of bug ICRD-238949 -->
		<input type="checkbox" name="selectedRows" value="<%=String.valueOf(index)%>" />
		<!--html:checkbox property="selectedRows" value="<!--%=String.valueOf(index)%>" /-->
		<!-- Modified as part of bug ICRD-238949 ends-->
		<ihtml:hidden property="hiddenOpFlag" value="NOOP"/>
	</center>
	</td>

	<td  class="iCargoTableDataTd" align="center">
	<center>
		<ibusiness:uld id="uldno" uldProperty="uldNumbers" uldValue="" styleClass="iCargoEditableTextFieldRowColor1" componentID="CMP_ULD_DEFAULTS_UCMINOUT_ULDNUMBER" style="width:130px;text-transform: uppercase" maxlength="12"/>
	</center>
	</td>

	<td  class="iCargoTableTd ic-center">
	<center>
		<input type="checkbox" name="damageCodes" value="" />
	</center>
	</td>

	<td class="iCargoTableDataTd">
	<center>
	<ihtml:select property="contentInd" styleClass="iCargoMediumComboBox" value="X">
		<bean:define id="contents" name="contentTypes" toScope="page"/>
			<ihtml:options collection="contents" property="fieldValue" labelProperty="fieldDescription"/>
	</ihtml:select>
	</center>
	</td>

	<td  class="iCargoTableDataTd">
	<center>
		<ihtml:select property="pou"  styleClass="iCargoMediumComboBox">
		 <logic:present name="stations">
			<ihtml:options name="stations" />
		</logic:present>
		</ihtml:select>
	</center>
	</td>
	<td class="iCargoTableDataTd">
		<center>
												  <logic:present name="oneTimeValues">
												<bean:define id="onetimemaps" name="oneTimeValues"/>
													<logic:iterate id ="onetimemap" name="onetimemaps">
													<bean:define id="keymap" name="onetimemap" property="key"/>
														<logic:equal name="keymap" value ="uld.defaults.ucminout.uldsourceforucm">
														 <bean:define id="valuemap" name="onetimemap" property="value" type="java.util.Collection"/>
													<logic:iterate id="value" name="valuemap" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
														<logic:present name="value" property="fieldValue" >
																<bean:define id="fieldValue" name="value" property="fieldValue"/>
																
																     <logic:equal name="value" property="fieldValue" value="MAN" >
																	     <bean:write name="value" property="fieldDescription" />
																		 <bean:define id="src" name="value" property="fieldDescription" />
																		 <ihtml:hidden property="uldSource" value="<%=(String)src%>" />
																          </logic:equal>
																	</logic:present>
																	</logic:iterate>
																	</logic:equal>
																	</logic:iterate>
														</logic:present>
													</center>
													</td>
			<td class="iCargoTableDataTd">
			<center>
										
												  <logic:present name="oneTimeValues">
												<bean:define id="onetimemaps" name="oneTimeValues"/>
													<logic:iterate id ="onetimemap" name="onetimemaps">
													<bean:define id="keymap" name="onetimemap" property="key"/>
														<logic:equal name="keymap" value ="uld.defaults.ucminout.uldsentstatus">
														 <bean:define id="valuemap" name="onetimemap" property="value" type="java.util.Collection"/>
													<logic:iterate id="value" name="valuemap" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
														<logic:present name="value" property="fieldValue" >
																<bean:define id="fieldValue" name="value" property="fieldValue"/>
																
																     <logic:equal name="value" property="fieldValue" value="N" >
																	     <bean:write name="value" property="fieldDescription" />
																		<bean:define id="stats" name="value" property="fieldDescription" />
																		 <ihtml:hidden property="uldStatus" value="<%=(String)stats%>" />
																          </logic:equal>
																	</logic:present>
																	</logic:iterate>
																	</logic:equal>
																	</logic:iterate>
															</logic:present>
														
														</center>
													</td>				
</tr>
<!-- template Row over -->

                          </tbody>
                        </table>
					</div>