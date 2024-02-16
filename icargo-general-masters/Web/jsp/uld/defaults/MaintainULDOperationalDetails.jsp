<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.MaintainULDForm" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<business:sessionBean
		id="oneTimeValues"
		moduleName="uld.defaults"
		screenID="uld.defaults.maintainuld"
		method="get"
		attribute="oneTimeValues" />


						<div class="ic-row">
								<div class="ic-input ic-split-20 ic-label-45">
								  	<label><common:message key="uld.defaults.maintainUld.lbl.OperationalOwnerAirline" /></label>
								 	<ihtml:text property="operationalOwnerAirlineCode" componentID="TXT_ULD_DEFAULTS_MAINTAINULD_OPERATIONALOWNERAIRLINE" name="maintainULDForm"  maxlength="3" />
									<div class="lovImg">
										<img id="operationOwnerairlineLov" name="operationOwnerairlineLov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22"  />
									</div>
								</div>
								<div class="ic-input ic-split-20 ic-label-45">
								  	<label><common:message key="uld.defaults.maintainUld.lbl.operationalAirlineCode" /></label>
								 	<ihtml:text property="operationalAirlineCode" componentID="TXT_ULD_DEFAULTS_MAINTAINULD_AIRLINECODEOPRNL" name="maintainULDForm"  maxlength="3" />
									<div class="lovImg">
										<img id="operationairlineLov" name="operationairlineLov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22"  />
									</div>
								</div>
								  <div class="ic-input ic-split-20 ic-label-45 ic-mandatory">
								  	<label><common:message key="uld.defaults.maintainUld.lbl.CurrentStation" /></label>
									<ihtml:text property="currentStation" componentID="TXT_ULD_DEFAULTS_MAINTAINULD_CURRENTSTATION" name="maintainULDForm"  maxlength="3" />
									<div class="lovImg">
										<img id="currentairportLov" name="currentairportLov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22"  />
									</div>
								  </div>

								<div class="ic-input ic-split-20 ic-label-45">
										<label><common:message key="uld.defaults.maintainUld.lbl.OwnerStation" /></label>
								 		<ihtml:text property="ownerStation" componentID="TXT_ULD_DEFAULTS_MAINTAINULD_OWNERSTATION" name="maintainULDForm"  maxlength="3" />
									<div class="lovImg">
										<img id="ownerairportLov" name="ownerairportLov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22"  />
									</div>
								</div>
								 <div class="ic-input ic-split-20 ic-label-45">
									<label><common:message key="uld.defaults.maintainUld.lbl.OverallStatus" /></label>
									  <logic:notEmpty name="maintainULDForm" property="overallStatus">

									  <ihtml:select property="overallStatus" componentID="CMB_ULD_DEFAULTS_MAINTAINULD_OVERALLSTATUS">
										  <logic:present name="oneTimeValues">
											<logic:iterate id="oneTimeValue" name="oneTimeValues">
											<logic:present name="oneTimeValue">
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
											</logic:present>
											</logic:iterate>
										  </logic:present>
									 </ihtml:select>
								  </logic:notEmpty>
								  <logic:empty name="maintainULDForm" property="overallStatus">
								   <ihtml:select property="overallStatus" value="O" componentID="CMB_ULD_DEFAULTS_MAINTAINULD_OVERALLSTATUS">
									  <logic:present name="oneTimeValues">
										<logic:iterate id="oneTimeValue" name="oneTimeValues">
										<logic:present name="oneTimeValue">
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
										</logic:present>
										</logic:iterate>
									  </logic:present>
									 </ihtml:select>
									 </logic:empty>
								   </div>

                                </div>
								<div class="ic-row">
								  <div class="ic-input ic-split-20 ic-label-45">
								  		<label><common:message key="uld.defaults.maintainUld.lbl.CleanlinessStatus" /></label>
																		<ihtml:select property="cleanlinessStatus" componentID="CMB_ULD_DEFAULTS_MAINTAINULD_CLEANLINESSSTATUS">
										  <logic:present name="oneTimeValues">
										  	<logic:iterate id="oneTimeValue" name="oneTimeValues">
											<logic:present name="oneTimeValue">
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
										  	 </logic:present>
											</logic:iterate>
										  </logic:present>
										</ihtml:select>
								</div>
								<div class="ic-input ic-split-20 ic-label-45">
										<label><common:message key="uld.defaults.maintainUld.lbl.DamagedStatus" /></label>
								 									<logic:notEmpty name="maintainULDForm" property="damageStatus">
									<ihtml:select property="damageStatus" componentID="CMB_ULD_DEFAULTS_MAINTAINULD_DAMAGEDSTATUS">
									  <logic:present name="oneTimeValues">									  
										<logic:iterate id="oneTimeValue" name="oneTimeValues">
										<logic:present name="oneTimeValue">
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
										</logic:present>
										</logic:iterate>
									   </logic:present>
									</ihtml:select>
								   </logic:notEmpty>
									<logic:empty name="maintainULDForm" property="damageStatus">
										<ihtml:select property="damageStatus" value="N" componentID="CMB_ULD_DEFAULTS_MAINTAINULD_DAMAGEDSTATUS">
										  <logic:present name="oneTimeValues">
											<logic:iterate id="oneTimeValue" name="oneTimeValues">
											 <logic:present name="oneTimeValue">
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
											 </logic:present>
											</logic:iterate>
										   </logic:present>
										</ihtml:select>
									   </logic:empty>
							         </div>
									  <div class="ic-input ic-split-20 ic-label-45">

									<label><common:message key="uld.defaults.maintainUld.lbl.FacilityType" /></label>
							  							  <logic:present name="maintainULDForm" property="facilityType">
								  <logic:notEmpty name="maintainULDForm" property="facilityType">								 
								  <logic:equal name="maintainULDForm" property="facilityType" value="NIL">
								<ihtml:select property="facilityType" componentID="CMB_ULD_DEFAULTS_MAINTAINULD_FACILITYTYPE">
								<html:option value="NIL">NIL</html:option>							
									 <logic:present name="oneTimeValues">
										<logic:iterate id="oneTimeValue" name="oneTimeValues">
											<bean:define id="parameterCode" name="oneTimeValue" property="key" />
											<logic:equal name="parameterCode" value="uld.defaults.facilitytypes">
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
								</logic:equal>
								<logic:notEqual name="maintainULDForm" property="facilityType" value="NIL">
								<ihtml:select property="facilityType" componentID="CMB_ULD_DEFAULTS_MAINTAINULD_FACILITYTYPE">							
								<html:option value=""><common:message key="combo.select"/></html:option>
									 <logic:present name="oneTimeValues">
										<logic:iterate id="oneTimeValue" name="oneTimeValues">
											<bean:define id="parameterCode" name="oneTimeValue" property="key" />
											<logic:equal name="parameterCode" value="uld.defaults.facilitytypes">
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
								</logic:notEqual>
								</logic:notEmpty>
							</logic:present>
							<logic:empty name="maintainULDForm" property="facilityType">
							<ihtml:select property="facilityType" componentID="CMB_ULD_DEFAULTS_MAINTAINULD_FACILITYTYPE">							
							<html:option value=""><common:message key="combo.select"/></html:option>
								 <logic:present name="oneTimeValues">
									<logic:iterate id="oneTimeValue" name="oneTimeValues">
										<bean:define id="parameterCode" name="oneTimeValue" property="key" />
										<logic:equal name="parameterCode" value="uld.defaults.facilitytypes">
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
							</logic:empty>
			
							 </div>
						</div>
							<div class="ic-row">
							   <div class="ic-input ic-split-20 ic-label-45">
									<label><common:message key="uld.defaults.maintainUld.lbl.Location" /></label>
							  		<ihtml:text property="location" componentID="TXT_ULD_DEFAULTS_MAINTAINULD_LOCATION" name="maintainULDForm"  disabled="true" />
									<div class="lovImg">
										<img id="facilitycodelov"  name="facilitycodelov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" />
									</div>
							  </div>

							  <div class="ic-input ic-split-20 ic-label-45">
									<label><common:message key="uld.defaults.maintainUld.lbl.uldnature" /></label>
							 							<ihtml:select property="uldNature" componentID="CMB_ULD_DEFAULTS_MAINTAINULD_ULDNATURE">
								 <logic:present name="oneTimeValues">
									<logic:iterate id="oneTimeValue" name="oneTimeValues">
										<bean:define id="parameterCode" name="oneTimeValue" property="key" />
										<logic:equal name="parameterCode" value="uld.defaults.uldnature">
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

							</div>
							</div>
                           