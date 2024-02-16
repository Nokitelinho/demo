<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>

<business:sessionBean id="levelCode" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="levelCode" />
<business:sessionBean id="embargoDetails" moduleName="reco.defaults"	screenID="reco.defaults.maintainembargo"	method="get" attribute="embargoParameterVos" />
<business:sessionBean id="applicableValues"	moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="applicableCode" />
<business:sessionBean id="embargoStatus" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="embargoStatus" />
<business:sessionBean id="embargoVo" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="embargoVo" />
<business:sessionBean id="categories" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="categoryTypes" />
<business:sessionBean id="complianceTypes" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="complianceTypes" />
<business:sessionBean id="applicableTransactionsList" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="applicableTransactions" />
<bean:define id="form" name="MaintainEmbargoRulesForm" toScope="page" type="com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.MaintainEmbargoRulesForm"/>

			<% 	String embargoStatusField="";
				String embargoLevel="";
				String embargoStartDate="";
				String embargoEndDate="";
				boolean embargoSuspend=false;
			%>

					<div class="ic-input-container ic-input-round-border">
						<div class="ic-row">
							<common:xgroup>
								<common:xsubgroup id="COMPLIANCE">
									<div class="ic-input ic-mandatory ic-split-15 ic-label-30">
										<label>
											<common:message key="maintainembargo.category" />
										</label>
										<logic:present name="embargoVo" property="category">
											<bean:define id="category" name ="embargoVo" property="category" type="String" />
											<ihtml:select property="category"  componentID="CMP_Reco_Defaults_MaintainEmbargo_Category" value="<%=(String)category%>" >
											<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
												  <logic:present name="categories">
													<logic:iterate id="catgry" name="categories">
														<bean:define id="catgryvalue" name="catgry" property="fieldValue" />
														<html:option value="<%=(String)catgryvalue %>"><bean:write name="catgry" property="fieldDescription" /></html:option>
													</logic:iterate>
												  </logic:present>                 
											</ihtml:select>
										</logic:present>
										<logic:notPresent name="embargoVo" property="category">
											<ihtml:select property="category"  componentID="CMP_Reco_Defaults_MaintainEmbargo_Category">
											 <ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
												  <logic:present name="categories">
													<logic:iterate id="catgry" name="categories">
														<bean:define id="catgryvalue" name="catgry" property="fieldValue" />
														<html:option value="<%=(String)catgryvalue %>"><bean:write name="catgry" property="fieldDescription" /></html:option>
													</logic:iterate>
												  </logic:present>                 
											</ihtml:select>
										</logic:notPresent>
										
									</div>
								</common:xsubgroup>
							</common:xgroup >
							<div class="ic-split-15 ic-input">
                                <div class="ic-mandatory ic-label-45 multi-select-wrap">
								<label>
									<common:message key="maintainembargo.applicabletransactions"/>
								</label>
								<ihtml:select multiSelect="true" 
											multiSelectNoneSelectedText="Select" 
											multiSelectMinWidth="160" 
											multiple="multiple" 
											property="applicableTransactions" 
											name="embargoVo"
											componentID="CMP_Reco_Defaults_MaintainEmbargo_ApplicableTransactions" >
										<logic:present name="applicableTransactionsList">								    
											<logic:iterate id="applicableTransactions" name="applicableTransactionsList">
											   <bean:define id="applicabletransactionsvalue" name="applicableTransactions" property="fieldValue" />
											   <html:option value="<%=(String)applicabletransactionsvalue %>"><bean:write name="applicableTransactions" property="fieldDescription" /></html:option>
											</logic:iterate>
										</logic:present>                 
								</ihtml:select>
							</div>
                            </div>
							<div class="ic-input ic-mandatory ic-split-10 ic-label-45">
								<label>
									<common:message key="maintainembargo.level"/>
								</label>
								<logic:equal name="form" property="isScreenload" value="true">
									<% embargoStatusField="";%>
								</logic:equal>
								<ihtml:select name="embargoVo" property="embargoLevel" componentID="CMP_Reco_Defaults_MaintainEmbargo_EmbargoLevel" >
									<logic:present name="embargoVo" property="embargoLevel">
										<bean:define id="embargoLevelField" name="embargoVo" property="embargoLevel"/>
										<% embargoLevel=(String)embargoLevelField; %>
										<logic:equal name="embargoVo" property="operationalFlag" value="I">
											<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
										</logic:equal>
										<logic:present name="levelCode">
											<logic:iterate id="levelCodeIterator" name="levelCode" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
												<logic:present name="levelCodeIterator" property="fieldValue">
													<bean:define id="fieldValue" name="levelCodeIterator" property="fieldValue"/>
													<bean:define id="fieldDescription" name="levelCodeIterator" property="fieldDescription"/>
													<logic:equal name="levelCodeIterator" property="fieldValue" value="<%=embargoLevel%>">
														<html:option value="<%=embargoLevel%>"><%=(String)fieldDescription%></html:option>
													</logic:equal>
												</logic:present>
											</logic:iterate>
										</logic:present>
									</logic:present>

									<logic:present name="embargoVo" property="embargoLevel">
										<bean:define id="embargoLevelField" name="embargoVo" property="embargoLevel"/>
											<% embargoLevel=(String)embargoLevelField; %>
										<logic:present name="levelCode">
											<logic:iterate id="levelCodeIteratorValue" name="levelCode" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
												<logic:present name="levelCodeIteratorValue" property="fieldValue">
													<bean:define id="fieldValue" name="levelCodeIteratorValue" property="fieldValue"/>
													<bean:define id="fieldDescription" name="levelCodeIteratorValue" property="fieldDescription"/>
													 <logic:notEqual name="levelCodeIteratorValue" property="fieldValue" value="<%=embargoLevel%>">
														  <html:option value="<%=(String)fieldValue%>"><%=(String)fieldDescription%></html:option>
													 </logic:notEqual>
												</logic:present>
											</logic:iterate>
										</logic:present>
									</logic:present>
								</ihtml:select>
							</div>
							<div class="ic-input ic-split-20 ic-label-30">
								<label>
									<common:message key="maintainembargo.status"/>
								</label>
								<logic:present name="embargoVo" property="status">
									<logic:present name="embargoStatus">
										<logic:iterate id="statusVo" name="embargoStatus" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
											<logic:present name="statusVo" property="fieldValue">
												<bean:define id="fieldValue" name="statusVo" property="fieldValue"/>
												<bean:define id="fieldDescription" name="statusVo" property="fieldDescription"/>
												<logic:equal name="embargoVo" property="status" value="<%=(String)fieldValue%>">
													<%embargoStatusField=(String)fieldDescription;%>
												</logic:equal>
											</logic:present>
										 </logic:iterate>
									</logic:present>
								</logic:present>
								<ihtml:text property="status" componentID="CMP_Reco_Defaults_MaintainEmbargo_EmbargoStatus" value="<%=embargoStatusField%>" maxlength="8" readonly="true" />
							</div>
							<common:xgroup>
								<common:xsubgroup id="COMPLIANCE">
									<div class="ic-input ic-mandatory ic-split-10 ic-label-30">
										<label>
											<common:message key="maintainembargo.compliancetype" />
										</label>
										<logic:present name="embargoVo" property="complianceType">
											<bean:define id="comType" name ="embargoVo" property="complianceType" type="String" />
											<ihtml:select property="complianceType" componentID="CMP_Reco_Defaults_MaintainEmbargo_ComplianceType" value="<%=(String)comType%>" >
											<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
												  <logic:present name="complianceTypes">
													<logic:iterate id="comtype" name="complianceTypes">
														<bean:define id="comtypevalue" name="comtype" property="fieldValue" />
														<logic:notEqual name="comtypevalue" value="COM">
														<html:option value="<%=(String)comtypevalue %>"><bean:write name="comtype" property="fieldDescription" /></html:option>
														</logic:notEqual>
													</logic:iterate>
											</logic:present>
										</ihtml:select>			  
										</logic:present>
										<logic:notPresent name="embargoVo" property="complianceType">
											<ihtml:select property="complianceType" componentID="CMP_Reco_Defaults_MaintainEmbargo_ComplianceType">
												<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
												  <logic:present name="complianceTypes">
													<logic:iterate id="comtype" name="complianceTypes">
														<bean:define id="comtypevalue" name="comtype" property="fieldValue" />
														<logic:notEqual name="comtypevalue" value="COM">
														<html:option value="<%=(String)comtypevalue %>"><bean:write name="comtype" property="fieldDescription" /></html:option>
														</logic:notEqual>
													</logic:iterate>
												  </logic:present>                 
											</ihtml:select>
										</logic:notPresent>
									</div>
								</common:xsubgroup>
							</common:xgroup>
							<div class="ic-input ic-mandatory ic-split-10 ic-label-45">
								<label>
									<common:message key="maintainembargo.startdate"/>
								</label>
								<logic:present name="embargoVo" property="startDate">
									<bean:define id="embargoLocalStartDate" name="embargoVo" property="startDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
									<% embargoStartDate=TimeConvertor.toStringFormat(embargoLocalStartDate.toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
								</logic:present>
								<ibusiness:calendar id="startDate" property="startDate" componentID="CMP_Reco_Defaults_MaintainEmbargo_EmbargoStartDate" type="image" value="<%=embargoStartDate %>" />
							</div>
							<div class="ic-input ic-mandatory ic-split-10 ic-label-45">
								<label>
									<common:message key="maintainembargo.enddate"/>
								</label>
								<logic:present name="embargoVo" property="endDate">
									<bean:define id="embargoLocalEndDate" name="embargoVo" property="endDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
									<% embargoEndDate=TimeConvertor.toStringFormat(embargoLocalEndDate.toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
								</logic:present>
								<ibusiness:calendar id="endDate" property="endDate" value="<%=embargoEndDate %>" type="image" componentID="CMP_Reco_Defaults_MaintainEmbargo_EmbargoEndDate" />
							</div>
							<div class="ic-inline-label">
                                <div class="ic-input ic-split-10 marginT20">
								<logic:present name="embargoVo" property="isSuspended">
									<bean:define id="embargoIsSuspended" name="embargoVo" property="isSuspended"/>
									<% embargoSuspend=(Boolean)embargoIsSuspended; %>
								</logic:present>
								<% if(embargoSuspend){ %>
									<input type="checkbox" name="isSuspended" style="width:15px;border: 0px;" title="Suspend Embargo"  checked="checked"/>
								<% }
								else {%>
									<input type="checkbox" name="isSuspended" style="width:15px;border: 0px;" title="Suspend Embargo"  />
								<% } %>
								<label>
									<common:message key="maintainembargo.suspend"/> 
								</label>
							     </div>
                            </div>
							
						</div>
					</div>