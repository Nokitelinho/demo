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
<bean:define id="form"
		 name="listULDForm"  type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ListULDForm"
		 toScope="page" />		
				
<business:sessionBean id="levelTypeOneTime" moduleName="uld.defaults"
                screenID="uld.defaults.listuld" method="get" attribute="levelTypeValues" />		 
				
						<jsp:include page="ListULDFilters_Split.jsp"/>
						
						<div class="ic-row ic-label-45">
							<div class="ic-input ic-split-14">
								
								
					
						
							<label><common:message  key="uld.defaults.listUld.lbl.LastMovementDate" /></label>
							<logic:present name="LIST_FILTERVO" property="lastMovementDate">
                    	<bean:define id="listFilter"
								 name="LIST_FILTERVO"  type="com.ibsplc.icargo.business.uld.defaults.vo.ULDListFilterVO"
		 							toScope="page" />
						<%
							String lastMvntDate = "";
						   if(listFilter.getLastMovementDate() != null) {
								lastMvntDate = TimeConvertor.toStringFormat(
											listFilter.getLastMovementDate().toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);
							}
						%>
                    	<ibusiness:calendar type="image" id="lastMovementDate" componentID="CAL_ULD_DEFAULTS_LISTULD_LASTMOVEMENTDATE" property="lastMovementDate" value="<%=lastMvntDate%>" />
						</logic:present>
						<logic:notPresent name="LIST_FILTERVO" property="lastMovementDate">
						<ibusiness:calendar id="lastMovementDate" type="image" componentID="CAL_ULD_DEFAULTS_LISTULD_LASTMOVEMENTDATE"  property="lastMovementDate" value="<%= form.getLastMovementDate() %>" />
					   	</logic:notPresent>
							</div>
							<div class="ic-input ic-split-14">
							<label> <common:message  key="uld.defaults.listUld.lbl.uldnature" /></label>
							<logic:present name="LIST_FILTERVO" property="uldNature">
						<ihtml:select name="LIST_FILTERVO" property="uldNature" componentID="CMB_ULD_DEFAULTS_LISTULD_ULDNATURE">
						<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
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
						</logic:present>
						<logic:notPresent name="LIST_FILTERVO" property="uldNature">
						<ihtml:select property="uldNature" componentID="CMB_ULD_DEFAULTS_LISTULD_ULDNATURE" value="">
						<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
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
						</logic:notPresent>
							</div>
							<div class="ic-input ic-split-14">
							<label><common:message  key="uld.defaults.listUld.lbl.UldRangeFrom" /></label>
							<logic:present name="LIST_FILTERVO" property="uldRangeFrom">
						<logic:notEqual name="LIST_FILTERVO" property="uldRangeFrom" value="-1">
						<bean:define id="test" name="LIST_FILTERVO" property="uldRangeFrom"/>
						<logic:equal name="LIST_FILTERVO" property="uldRangeFrom" value="0">
							<ihtml:text name="listULDForm" property="uldRangeFrom" componentID="TXT_ULD_DEFAULTS_LISTULD_ULDRANGEFROM" maxlength="5"  />
						</logic:equal>
						<logic:notEqual name="LIST_FILTERVO" property="uldRangeFrom" value="0">
						   <ihtml:text name="LIST_FILTERVO" property="uldRangeFrom" componentID="TXT_ULD_DEFAULTS_LISTULD_ULDRANGEFROM" maxlength="5"  />
						</logic:notEqual>
						</logic:notEqual>
						<logic:equal name="LIST_FILTERVO" property="uldRangeFrom" value="-1">
						<ihtml:text property="uldRangeFrom" name="listULDForm" componentID="TXT_ULD_DEFAULTS_LISTULD_ULDRANGEFROM" maxlength="5"  />
						</logic:equal>
						</logic:present>
						<logic:notPresent name="LIST_FILTERVO" property="uldRangeFrom">
						<ihtml:text property="uldRangeFrom" componentID="TXT_ULD_DEFAULTS_LISTULD_ULDRANGEFROM"  maxlength="5"  />
						</logic:notPresent>
							</div>
							<div class="ic-input ic-split-14">
							<label>	<common:message  key="uld.defaults.listUld.lbl.ULDRangeTo" /></label>
							<logic:present name="LIST_FILTERVO" property="uldRangeTo">
                    	<logic:equal name="LIST_FILTERVO" property="uldRangeTo" value="-1">
                    			<ihtml:text property="uldRangeTo" componentID="TXT_ULD_DEFAULTS_LISTULD_ULDRANGETO" maxlength="5"  />
						</logic:equal>
						<logic:notEqual name="LIST_FILTERVO" property="uldRangeTo" value="-1">
						<logic:equal name="LIST_FILTERVO" property="uldRangeTo" value="0">
							<ihtml:text name="listULDForm" property="uldRangeTo" componentID="TXT_ULD_DEFAULTS_LISTULD_ULDRANGETO" maxlength="5"  />
						</logic:equal>
						<logic:notEqual name="LIST_FILTERVO" property="uldRangeTo" value="0">
						   <ihtml:text name="LIST_FILTERVO" property="uldRangeTo" componentID="TXT_ULD_DEFAULTS_LISTULD_ULDRANGETO" maxlength="5"  />
                    	</logic:notEqual>

						</logic:notEqual>
						</logic:present>
						<logic:notPresent name="LIST_FILTERVO" property="uldRangeTo">
						<ihtml:text property="uldRangeTo" componentID="TXT_ULD_DEFAULTS_LISTULD_ULDRANGETO"  maxlength="5"  />
						</logic:notPresent>
							</div>
							<div class="ic-input ic-split-14">
						
						
							<label><common:message  key="uld.defaults.listUld.lbl.FromDate" /></label>
							<logic:present name="LIST_FILTERVO">
				<logic:present name="LIST_FILTERVO" property="fromDate">
					<bean:define id="listFilter"
								 name="LIST_FILTERVO"  type="com.ibsplc.icargo.business.uld.defaults.vo.ULDListFilterVO"
		 							toScope="page" />
						<%
							String fDate = "";
						   if(listFilter.getFromDate() != null) {
								fDate = TimeConvertor.toStringFormat(
											listFilter.getFromDate().toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);
							}
						%>
					<ibusiness:calendar type="image" id="fromDate" componentID="CAL_ULD_DEFAULTS_LISTULD_FROMDATE" property="fromDate" value="<%=fDate%>" maxlength="11"/>
				</logic:present>
			</logic:present>
						<logic:notPresent name="LIST_FILTERVO" property="fromDate">
						<ibusiness:calendar id="fromDate" type="image" componentID="CAL_ULD_DEFAULTS_LISTULD_FROMDATE"  property="fromDate" value="" maxlength="11"/>
					   	</logic:notPresent>
							</div>
							<div class="ic-input ic-split-14">
							<label><common:message  key="uld.defaults.listUld.lbl.ToDate" /></label>
							<logic:present name="LIST_FILTERVO" property="toDate">
                    	<bean:define id="listFilter"
								 name="LIST_FILTERVO"  type="com.ibsplc.icargo.business.uld.defaults.vo.ULDListFilterVO"
		 							toScope="page" />
						<%
							String tDate = "";
						   if(listFilter.getToDate() != null) {
								tDate = TimeConvertor.toStringFormat(
											listFilter.getToDate().toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);
							}
						%>
                    	<ibusiness:calendar type="image" id="toDate" componentID="CAL_ULD_DEFAULTS_LISTULD_TODATE" property="toDate" value="<%=tDate%>" maxlength="11"/>
						</logic:present>
						<logic:notPresent name="LIST_FILTERVO" property="toDate">
						<ibusiness:calendar id="toDate" type="image" componentID="CAL_ULD_DEFAULTS_LISTULD_TODATE"  property="toDate" value="" maxlength="11"/>
					   	</logic:notPresent>
							</div>
						</div>
						<div class="ic-row ic-label-45">
							<div class="ic-input ic-split-14">
							<label><common:message key="uld.defaults.listUld.lbl.levelType" /></label>
							<logic:present name="LIST_FILTERVO" property="levelType">

					<logic:present name="levelTypeOneTime" >
						<bean:define id="levelTypeOneTimes" name="levelTypeOneTime"  />
					</logic:present>
				<ihtml:select name="LIST_FILTERVO"  property="levelType" componentID="CMB_ULD_DEFAULTS_LISTULD_LEVELTYPE">
							<html:option value=""><common:message key="combo.select"/></html:option>
								 <logic:present name="levelTypeOneTimes" >
										 <logic:iterate id="oneTimeVOs" name="levelTypeOneTimes">
											<bean:define id="defaults" name="oneTimeVOs" property="fieldValue" />
											 <bean:define id="diaplay" name="oneTimeVOs" property="fieldDescription" />
												<html:option value="<%=(String)defaults%>"><%=(String)diaplay%></html:option>
									</logic:iterate>
								  </logic:present>
					</ihtml:select>
				</logic:present>
				<logic:notPresent name="LIST_FILTERVO" property="levelType">

					<logic:present name="levelTypeOneTime" >
							<bean:define id="levelTypeOneTimes" name="levelTypeOneTime"  />
					 </logic:present>

					<ihtml:select property="levelType" componentID="CMB_ULD_DEFAULTS_LISTULD_LEVELTYPE" value="">
				                  	<html:option value=""><common:message key="combo.select"/></html:option>
				                              <logic:present name="levelTypeOneTimes" >
									 <logic:iterate id="oneTimeVOs" name="levelTypeOneTimes">
										<bean:define id="defaults" name="oneTimeVOs" property="fieldValue" />
										 <bean:define id="diaplay" name="oneTimeVOs" property="fieldDescription" />
											<html:option value="<%=(String)defaults%>"><%=(String)diaplay%></html:option>
								</logic:iterate>
							  </logic:present>
					</ihtml:select>
				</logic:notPresent>
							</div>
							<div class="ic-input ic-split-14">
							<label><common:message key="uld.defaults.listUld.lbl.levelValue" /></label>
							<logic:present name="LIST_FILTERVO" property="levelValue">
	  			<bean:define id="levelValue" name="LIST_FILTERVO" property="levelValue"/>
	  			<ihtml:text property="levelValue" componentID="TXT_ULD_DEFAULTS_LISTULD_LEVELVALUE" value="<%=(String)levelValue%>"/>
	  			</logic:present>
	  			<logic:notPresent name="LIST_FILTERVO" property="levelValue">
				<ihtml:text property="levelValue" componentID="TXT_ULD_DEFAULTS_LISTULD_LEVELVALUE" value=""/>
	  			</logic:notPresent>
				<img height="18" src="<%=request.getContextPath()%>/images/lov.gif" width="18" id="levelValuelov"  alt="LevelValue LOV"/>
							</div>
							<div class="ic-input ic-split-14">
						
							<label><common:message key="uld.defaults.listUld.lbl.content" /></label>
							<logic:present name="LIST_FILTERVO" property="content">
						<ihtml:select name="LIST_FILTERVO"  property="content" componentID="CMB_ULD_DEFAULTS_LISTULD_CONTENT">
								<ihtml:option value=""><common:message key="combo.select" /></ihtml:option>
									  <logic:present name="oneTimeValues">
										<logic:iterate id="oneTimeValue" name="oneTimeValues">
											<logic:present name="oneTimeValue">
												<bean:define id="parameterCode" name="oneTimeValue" property="key" />
													<logic:equal name="parameterCode" value="uld.defaults.contentcodes">
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
			</logic:present>
			<logic:notPresent name="LIST_FILTERVO" property="content">
							<ihtml:select property="content" componentID="CMB_ULD_DEFAULTS_LISTULD_CONTENT" value="">
								<ihtml:option value=""><common:message key="combo.select" /></ihtml:option>
									  <logic:present name="oneTimeValues">
										<logic:iterate id="oneTimeValue" name="oneTimeValues">
											<logic:present name="oneTimeValue">
												<bean:define id="parameterCode" name="oneTimeValue" property="key" />
													<logic:equal name="parameterCode" value="uld.defaults.contentcodes">
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
				</logic:notPresent>
							</div>
							<div class="ic-input ic-split-14">
							<label><common:message key="uld.defaults.listUld.lbl.offAirport" /></label>
							<logic:present name="LIST_FILTERVO" property="offairportFlag">
				<bean:define id="offairportFlag" name="LIST_FILTERVO" property="offairportFlag" type="java.lang.String"/>
				<ihtml:select property="offairportFlag" componentID="CMB_ULD_DEFAULTS_LISTULD_OFFAIRPORT" value="<%=(String)offairportFlag%>">
					<ihtml:option value=""><common:message key="combo.select" /></ihtml:option>
					<html:option value="Y">Off Airport</html:option>
					<html:option value="N">On Airport</html:option>
				</ihtml:select>
			</logic:present>
			<logic:notPresent name="LIST_FILTERVO" property="offairportFlag">
				<ihtml:select property="offairportFlag" componentID="CMB_ULD_DEFAULTS_LISTULD_OFFAIRPORT" value="">
					<ihtml:option value=""><common:message key="combo.select" /></ihtml:option>
					<html:option value="Y">Off Airport</html:option>
					<html:option value="N">On Airport</html:option>
				</ihtml:select>
			</logic:notPresent>
							</div>
							<div class="ic-input ic-split-14">
							<label><common:message key="uld.defaults.listUld.lbl.occupiedUld" /></label>
								<ihtml:select name="LIST_FILTERVO" property="occupiedULDFlag" componentID="CMB_ULD_DEFAULTS_LISTULD_OCCUPIED">
					<ihtml:option value=""><common:message key="combo.select" /></ihtml:option>
								  <logic:present name="oneTimeValues">
									<logic:iterate id="oneTimeValue" name="oneTimeValues">
									<bean:define id="parameterCode" name="oneTimeValue" property="key" />
										<logic:equal name="parameterCode" value="uld.defaults.occupiedstatus">
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
							</div>
							<div class="ic-input">
							<label><common:message key="uld.defaults.listUld.lbl.intransitStatus" /></label>
								<ihtml:select name="LIST_FILTERVO" property="inTransitFlag" componentID="CMB_ULD_DEFAULTS_LISTULD_OCCUPIED">
								<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
								  <logic:present name="oneTimeValues">
									<logic:iterate id="oneTimeValue" name="oneTimeValues">
									<bean:define id="parameterCode" name="oneTimeValue" property="key" />
										<logic:equal name="parameterCode" value="uld.defaults.transitstatus">
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
							</div>
							<div class="ic-button-container marginT15">
							<!--Added autoCollapse="true" by A-7359 as part of ICRD-228547-->
							   <ihtml:nbutton property="btList" autoCollapse="true" accesskey="L" componentID="BTN_ULD_DEFAULTS_LISTULD_LIST">
									<common:message  key="uld.defaults.listUld.btn.List" />
								</ihtml:nbutton>
								<ihtml:nbutton property="btClear" accesskey="C" componentID="BTN_ULD_DEFAULTS_LISTULD_CLEAR">
									<common:message  key="uld.defaults.listUld.btn.Clear" />
								</ihtml:nbutton>
						</div>
						</div>
						<div class="ic-row ic-label-45" style="padding-top:10px;">		   <!--Added by A-7359 for ICRD - 224586 starts here -->

						</div>