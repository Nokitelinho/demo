<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ include file="/jsp/includes/tlds.jsp" %>

<business:sessionBean
		id="LIST_FILTERVO"
		moduleName="uld.defaults"
		screenID="uld.defaults.listuld"
		method="get"
		attribute="listFilterVO" />
<business:sessionBean
		id="oneTimeValues"
		moduleName="uld.defaults"
		screenID="uld.defaults.listuld"
		method="get"
		attribute="oneTimeValues" />	
<business:sessionBean
		id="displayOALULDCheckBox"
		moduleName="uld.defaults"
		screenID="uld.defaults.listuld"
		method="get"
		attribute="displayOALULDCheckBox" />		
	

				<div class="ic-row ic-label-45">
							<div class="ic-input ic-split-14">
								<label><common:message  key="uld.defaults.listUld.lbl.UldNo"/></label>
								<logic:present name="LIST_FILTERVO" property="uldNumber">
								<ibusiness:uld id="uldno" uldProperty="uldNumber" componentID="TXT_ULD_DEFAULTS_LISTULD_ULDNO" style="text-transform: uppercase"/>
								</logic:present>
								<logic:notPresent name="LIST_FILTERVO" property="uldNumber">
								<ibusiness:uld id="uldno" uldProperty="uldNumber" componentID="TXT_ULD_DEFAULTS_LISTULD_ULDNO" style="text-transform: uppercase"/>
								</logic:notPresent>
							</div>
							<div class="ic-input ic-split-14">
								<label><common:message  key="uld.defaults.listUld.lbl.UldGroupCode"/></label>
								<logic:present name="LIST_FILTERVO" property="uldGroupCode">
								<ihtml:text name="LIST_FILTERVO"   property="uldGroupCode" componentID="TXT_ULD_DEFAULTS_LISTULD_ULDGROUP" maxlength="5" />
								</logic:present>
								<logic:notPresent name="LIST_FILTERVO" property="uldGroupCode">
								<ihtml:text name="listULDForm" property="uldGroupCode" componentID="TXT_ULD_DEFAULTS_LISTULD_ULDGROUP" value="" maxlength="5" />
								</logic:notPresent>
								<div class="lovImg">
								<img src="<%= request.getContextPath()%>/images/lov.png" width="22" height="22" onclick="displayLOV('FindUldGroupLov.do','N','Y','FindUldGroupLov.do',document.forms[1].uldGroupCode.value,'uldGroupCode','1','uldGroupCode','',0)" alt="ULD GroupCode LOV"/>
								</div>
							</div>
							<div class="ic-input ic-split-14">
								<label><common:message  key="uld.defaults.listUld.lbl.UldTypeCode"/></label>
								<logic:present name="LIST_FILTERVO" property="uldTypeCode">
								<ihtml:text name="LIST_FILTERVO" property="uldTypeCode" componentID="TXT_ULD_DEFAULTS_LISTULD_ULDTYPECODE"  maxlength="3" />
								</logic:present>
								<logic:notPresent name="LIST_FILTERVO" property="uldTypeCode">
								<ihtml:text property="uldTypeCode" componentID="TXT_ULD_DEFAULTS_LISTULD_ULDTYPECODE" value="" maxlength="3" />
								</logic:notPresent>
								<div class="lovImg">
								<img src="<%= request.getContextPath()%>/images/lov.png" width="22" height="22" onclick="displayLOV('showUld.do','N','Y','showUld.do',document.forms[1].uldTypeCode.value,'UldType','1','uldTypeCode','')" alt="ULD TypeCode LOV" />
								</div>
							</div>
							<div class="ic-input ic-split-14">
								<label><common:message  key="uld.defaults.listUld.lbl.AirlineCode"/></label>
								<logic:present name="LIST_FILTERVO" property="airlineCode">
								<ihtml:text name="LIST_FILTERVO" property="airlineCode" componentID="TXT_ULD_DEFAULTS_LISTULD_AIRLINECODE" maxlength="3" />
								</logic:present>
								<logic:notPresent name="LIST_FILTERVO" property="airlineCode">
								<ihtml:text property="airlineCode" componentID="TXT_ULD_DEFAULTS_LISTULD_AIRLINECODE" value="" maxlength="3" />
								</logic:notPresent>
								<div class="lovImg">
								<img src="<%= request.getContextPath()%>/images/lov.png" width="22" height="22"  id="airlineLovImg" name="airlineLovImg"  alt="Airline LOV"/>
								</div>
							</div>
							<div class="ic-input ic-split-14">
						
								<label><common:message  key="uld.defaults.listUld.lbl.ownerAirline"/></label>
								<logic:present name="LIST_FILTERVO" property="ownerAirline">
								<ihtml:text name="LIST_FILTERVO" property="ownerAirline" componentID="TXT_ULD_DEFAULTS_LISTULD_OWNERAIRLINE" maxlength="3" />
								</logic:present>
								<logic:notPresent name="LIST_FILTERVO" property="ownerAirline">
								<ihtml:text property="ownerAirline" componentID="TXT_ULD_DEFAULTS_LISTULD_OWNERAIRLINE" value="" maxlength="3" />
								</logic:notPresent>
								<div class="lovImg">
								<img src="<%= request.getContextPath()%>/images/lov.png" width="22" height="22"  id="airlineLov" name="airlineLov" onclick="displayLOV('showAirline.do','N','Y','showAirline.do',document.forms[1].ownerAirline.value,'Airline','1','ownerAirline','',0)" alt="Airline LOV"/>
								</div>
							</div>
							<div class="ic-input ic-split-14">
								<label><common:message  key="uld.defaults.listUld.lbl.Manufacturer"/></label>
								<input type="hidden" name="manufacturerDesc" value=""/>	
								<logic:present name="LIST_FILTERVO" property="manufacturer">
								<ihtml:text name="LIST_FILTERVO" property="manufacturer" componentID="TXT_ULD_DEFAULTS_LISTULD_MANUFACTURER" maxlength="25" />
						</logic:present>
						<logic:notPresent name="LIST_FILTERVO" property="manufacturer">
						<ihtml:text property="manufacturer" componentID="TXT_ULD_DEFAULTS_LISTULD_MANUFACTURER" value="" maxlength="25" />
						</logic:notPresent>
				<!-- <img src="<%=request.getContextPath()%>/images/lov.gif" width="18" height="18" tabindex="11"
					name="manufacturerLovImg"
					onclick="displayLOV('uld.defaults.findmanufacturerlov.do','N','N','uld.defaults.findmanufacturerlov.do',document.forms[1].manufacturer.value,'manufacturer','1','manufacturer','',0)" alt="Manufacturer LOV"/> -->
					<div class="lovImg">
					<img  id="manufacturerLovImg" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" onclick="displayOneTimeLOV('screenloadOneTime.do','N','Y','screenloadOneTime.do',document.forms[1].manufacturer.value,document.forms[1].manufacturerDesc.value,'Manufacturer','1','manufacturer','manufacturerDesc','0','uld.defaults.manufacturer','')" alt="Manufacturer LOV"/>
					</div>
							</div>
					<div class="ic-input ic-split-11 inline_filedset marginT20">
					 <logic:present name="displayOALULDCheckBox">
					 <logic:equal name ="displayOALULDCheckBox" value = "Y">
							  <ihtml:checkbox property="oalUldOnly" componentID="CMB_ULD_DEFAULTS_LISTULD_OALULDONLY"/>
							  <label>
									 <common:message key="uld.defaults.listUld.lbl.oalUldOnly"/>
							  </label>
							  </logic:equal>
						</logic:present>	  					
							</div>
						</div>
						<div class="ic-row ic-label-45">
							<div class="ic-input ic-split-14">
							<label><common:message  key="uld.defaults.listUld.lbl.DamagedStatus"/></label>
							<logic:present name="LIST_FILTERVO" property="damageStatus">
						<ihtml:select name="LIST_FILTERVO" property="damageStatus" componentID="CMB_ULD_DEFAULTS_LISTULD_DAMAGEDSTATUS">
						<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
						  <logic:present name="oneTimeValues">
							<logic:iterate id="oneTimeValue" name="oneTimeValues">
							<bean:define id="parameterCode" name="oneTimeValue" property="key" />
								<logic:equal name="parameterCode" value="uld.defaults.damageStatus">
								<bean:define id="parameterValues" name="oneTimeValue" property="value" />
									<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
										<logic:present name="parameterValue">
										<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
											<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
											<html:option value="<%=(String)fieldValue%>">
												<%=(String)fieldDescription%>
											</html:option>
										</logic:present>
									</logic:iterate>
								</logic:equal>
							</logic:iterate>
						   </logic:present>
						</ihtml:select>
						</logic:present>
						<logic:notPresent name="LIST_FILTERVO" property="damageStatus">
						<ihtml:select property="damageStatus" componentID="CMB_ULD_DEFAULTS_LISTULD_DAMAGEDSTATUS" value="">
						<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
						  <logic:present name="oneTimeValues">
							<logic:iterate id="oneTimeValue" name="oneTimeValues">
							<bean:define id="parameterCode" name="oneTimeValue" property="key" />
								<logic:equal name="parameterCode" value="uld.defaults.damageStatus">
								<bean:define id="parameterValues" name="oneTimeValue" property="value" />
									<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
										<logic:present name="parameterValue">
										<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
											<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
											<html:option value="<%=(String)fieldValue%>">
												<%=(String)fieldDescription%>
											</html:option>
										</logic:present>
									</logic:iterate>
								</logic:equal>
							</logic:iterate>
						   </logic:present>
						</ihtml:select>
						</logic:notPresent>
							</div>
							<div class="ic-input ic-split-14">
							<label><common:message  key="uld.defaults.listUld.lbl.OverallStatus" /></label>
							<logic:present name="LIST_FILTERVO" property="overallStatus">
                    	<ihtml:select name="LIST_FILTERVO" property="overallStatus" componentID="CMB_ULD_DEFAULTS_LISTULD_OVERALLSTATUS">
                    	<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
						  <logic:present name="oneTimeValues">
							<logic:iterate id="oneTimeValue" name="oneTimeValues">
							<bean:define id="parameterCode" name="oneTimeValue" property="key" />
								<logic:equal name="parameterCode" value="uld.defaults.operationalStatus">
								<bean:define id="parameterValues" name="oneTimeValue" property="value" />
									<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
										<logic:present name="parameterValue">
										<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
											<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
											<html:option value="<%=(String)fieldValue%>">
												<%=(String)fieldDescription%>
											</html:option>
										</logic:present>
									</logic:iterate>
								</logic:equal>
							</logic:iterate>
						  </logic:present>
					 	</ihtml:select>
						</logic:present>
						<logic:notPresent name="LIST_FILTERVO" property="overallStatus">
						<ihtml:select property="overallStatus" componentID="CMB_ULD_DEFAULTS_LISTULD_OVERALLSTATUS" value="">
						<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
						  <logic:present name="oneTimeValues">
							<logic:iterate id="oneTimeValue" name="oneTimeValues">
							<bean:define id="parameterCode" name="oneTimeValue" property="key" />
								<logic:equal name="parameterCode" value="uld.defaults.operationalStatus">
								<bean:define id="parameterValues" name="oneTimeValue" property="value" />
									<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
										<logic:present name="parameterValue">
										<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
											<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
											<html:option value="<%=(String)fieldValue%>">
												<%=(String)fieldDescription%>
											</html:option>
										</logic:present>
									</logic:iterate>
								</logic:equal>
							</logic:iterate>
						  </logic:present>
					 	</ihtml:select>
                    	</logic:notPresent>
							</div>
							<div class="ic-input ic-split-14">
								<label> <common:message  key="uld.defaults.listUld.lbl.CleanlinessStatus" /></label>
								<logic:present name="LIST_FILTERVO" property="cleanlinessStatus">
                    	<ihtml:select name="LIST_FILTERVO" property="cleanlinessStatus" componentID="CMB_ULD_DEFAULTS_LISTULD_CLEANLINESSSTATUS">
                    	<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
						  <logic:present name="oneTimeValues">
							<logic:iterate id="oneTimeValue" name="oneTimeValues">
							<bean:define id="parameterCode" name="oneTimeValue" property="key" />
								<logic:equal name="parameterCode" value="uld.defaults.cleanlinessStatus">
								<logic:present name="oneTimeValue">
									<bean:define id="parameterValues" name="oneTimeValue" property="value" />
									<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
										<logic:present name="parameterValue">
										<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
											<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
											<html:option value="<%=(String)fieldValue%>">
												<%=(String)fieldDescription%>
											</html:option>
										</logic:present>
									</logic:iterate>
								</logic:present>
								</logic:equal>
							</logic:iterate>
						  </logic:present>
						</ihtml:select>
						</logic:present>
						<logic:notPresent name="LIST_FILTERVO" property="cleanlinessStatus">
						<ihtml:select property="cleanlinessStatus" componentID="CMB_ULD_DEFAULTS_LISTULD_CLEANLINESSSTATUS" value="">
						<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
						  <logic:present name="oneTimeValues">
							<logic:iterate id="oneTimeValue" name="oneTimeValues">
							<bean:define id="parameterCode" name="oneTimeValue" property="key" />
								<logic:equal name="parameterCode" value="uld.defaults.cleanlinessStatus">
								<bean:define id="parameterValues" name="oneTimeValue" property="value" />
									<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
										<logic:present name="parameterValue">
										<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
											<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
											<html:option value="<%=(String)fieldValue%>">
												<%=(String)fieldDescription%>
											</html:option>
										</logic:present>
									</logic:iterate>
								</logic:equal>
							</logic:iterate>
						  </logic:present>
						</ihtml:select>
                    	</logic:notPresent>
							</div>
							<div class="ic-input ic-split-14">
								<label><common:message key="uld.defaults.listUld.lbl.agentCode" /></label>
								<logic:present name="LIST_FILTERVO" property="agentCode">
									<ihtml:text name="LIST_FILTERVO" property="agentCode" componentID="TXT_ULD_DEFAULTS_LISTULD_AGENTCODE" maxlength="15" />
									</logic:present>
								<logic:notPresent name="LIST_FILTERVO" property="agentCode">
								<ihtml:text property="agentCode" componentID="TXT_ULD_DEFAULTS_LISTULD_AGENTCODE" value="" maxlength="15" />
								</logic:notPresent>
								<div class="lovImg">
								<img src="<%= request.getContextPath()%>/images/lov.png" width="22" height="22" name="agentlov" id="agentlov"/>
								</div>
							</div>
							<div class="ic-input ic-split-14">
									<label><common:message  key="uld.defaults.listUld.lbl.CurrentStation" /></label>
									<logic:present name="LIST_FILTERVO" property="currentStation">
							<ihtml:text name="LIST_FILTERVO" property="currentStation" componentID="TXT_ULD_DEFAULTS_LISTULD_CURRENTSTATION" maxlength="3" />
							</logic:present>
							<logic:notPresent name="LIST_FILTERVO" property="currentStation">
							<ihtml:text property="currentStation" componentID="TXT_ULD_DEFAULTS_LISTULD_CURRENTSTATION" value="" maxlength="3"  />
							</logic:notPresent>
							<div class="lovImg">
							<img src="<%= request.getContextPath()%>/images/lov.png" width="22" height="22" id="airportLovImg" name="airportLovImg" onclick="displayLOV('showAirport.do','N','Y','showAirport.do',document.forms[1].currentStation.value,'CurrentStation','1','currentStation','',0)" alt="Airport LOV"/>
							</div>
							</div>
							<div class="ic-input ic-split-14">
								<label><common:message  key="uld.defaults.listUld.lbl.OwnerStation" /></label>
									<logic:present name="LIST_FILTERVO" property="ownerStation">
						<ihtml:text name="LIST_FILTERVO" property="ownerStation" componentID="TXT_ULD_DEFAULTS_LISTULD_OWNERSTATION" maxlength="3"  />
						</logic:present>
						<logic:notPresent name="LIST_FILTERVO" property="ownerStation">
						<ihtml:text property="ownerStation" componentID="TXT_ULD_DEFAULTS_LISTULD_OWNERSTATION" value="" maxlength="3"  />
						</logic:notPresent>
						<div class="lovImg">
						<img src="<%= request.getContextPath()%>/images/lov.png" width="22" height="22" name="airportLov" id="airportLov" onclick="displayLOV('showAirport.do','N','Y','showAirport.do',document.forms[1].ownerStation.value,'OwnerStation','1','ownerStation','',0)" alt="Airport LOV"/>
						</div>
							</div>
						</div>