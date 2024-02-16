<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ConsignmentForm"%>
<%@ page import="com.ibsplc.xibase.server.framework.persistence.query.Page"%>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Iterator"%>
<%@ include file="/jsp/includes/tlds.jsp" %>



<business:sessionBean id="conDocVOSession" moduleName="mail.operations" screenID="mailtracking.defaults.consignment" method="get" attribute="consignmentDocumentVO" />
<business:sessionBean id="oneTimeCatSession" moduleName="mail.operations" screenID="mailtracking.defaults.consignment" method="get" attribute="oneTimeCat" />
<business:sessionBean id="oneTimeRISession" moduleName="mail.operations" screenID="mailtracking.defaults.consignment" method="get" attribute="oneTimeRSN" />
<business:sessionBean id="oneTimeHNISession" moduleName="mail.operations" screenID="mailtracking.defaults.consignment" method="get" attribute="oneTimeHNI" />
<business:sessionBean id="oneTimeMailClassSession" moduleName="mail.operations" screenID="mailtracking.defaults.consignment" method="get" attribute="oneTimeMailClass" />
<business:sessionBean id="oneTimeTypeSession" moduleName="mail.operations" screenID="mailtracking.defaults.consignment" method="get" attribute="oneTimeType" />
<bean:define id="ConsignmentForm" name="ConsignmentForm" type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ConsignmentForm" toScope="page" scope="request"/>
				
						
				<div class="ic-row ic-input-round-border">
					<div class="ic-row">
						<div class="ic-col-50">
							<h4>
								<common:message key="mailtracking.defaults.consignment.lbl.mailbagdtls" />
							</h4>
						</div>
						<div class="ic-col-50">
							<div class="ic-button-container">
								<a class="iCargoLink" href="#" id="lnkAddMail"><common:message key="mailtracking.defaults.consignment.lnk.add" /></a>
								
								<a class="iCargoLink" href="#" id="lnkAddMultiple"><common:message key="mailtracking.defaults.consignment.link.addMultiple" /></a>
								
								<a class="iCargoLink" href="#" id="lnkDeleteMail"><common:message key="mailtracking.defaults.consignment.lnk.delete" /></a>
							</div>
						</div>
					</div>
					<logic:present name="conDocVOSession" property="mailInConsignmentVOs">
								<bean:define id="mailInconsignmentPage" name="conDocVOSession" property="mailInConsignmentVOs"/>
					</logic:present>
					<div class="ic-row paddR5">
						 <logic:present name="conDocVOSession" property="mailInConsignmentVOs">

								<common:paginationTag pageURL="javascript:submitList('lastPageNum','displayPage')"
								name="mailInconsignmentPage"
								display="label"
								labelStyleClass="iCargoResultsLabel"
								lastPageNum="<%=((ConsignmentForm)ConsignmentForm).getLastPageNum() %>" />

						</logic:present>

					   <logic:notPresent name="conDocVOSession" property="mailInConsignmentVOs">
							&nbsp;
					   </logic:notPresent>
					   
					   <div class="ic-button-container">
						 <logic:present name="conDocVOSession" property="mailInConsignmentVOs">
							  <common:paginationTag
							  pageURL="javascript:submitList('lastPageNum','displayPage')"
							  name="mailInconsignmentPage"
							  display="pages"
							  linkStyleClass="iCargoLink"
							  disabledLinkStyleClass="iCargoLink"
							  lastPageNum="<%=((ConsignmentForm)ConsignmentForm).getLastPageNum() %>" 
							  exportToExcel="true"
							  exportTableId="consignmentTable"
							  exportAction="mailtracking.defaults.consignment.listconsignment.do"/>
						 </logic:present>

						  <logic:notPresent name="conDocVOSession" property="mailInConsignmentVOs">
							 &nbsp;
						  </logic:notPresent>
					   </div>
					</div>
					<div id="mailDiv">
					<div class="ic-row">
						<div class="tableContainer"  style="height:400px;"> 
							<table class="fixed-header-table" id="consignmentTable">
								<thead>
									<tr>
										<td class="iCargoTableHeaderLabel" width="2%">
													<input name="masterMail" type="checkbox" onclick="updateHeaderCheckBox(this.form,this,this.form.selectMail);" />
												</td>
												<!-- modified width by A-8149 for ICRD-261108-->
												<td width="17%">
													<common:message key="mailtracking.defaults.consignment.lbl.mailbagid"/><span class="iCargoMandatoryFieldIcon">*</span>
												</td>												
												<!-- modified width by A-8149 for ICRD-261108  -->												
												<td width="7%">
													<common:message key="mailtracking.defaults.consignment.lbl.ooe"/><span class="iCargoMandatoryFieldIcon">*</span>
												</td>
												<!--  modified width by A-8149 for ICRD-261108 -->
												<td width="7%">
													<common:message key="mailtracking.defaults.consignment.lbl.doe"/><span class="iCargoMandatoryFieldIcon">*</span>
												</td>
												<td width="5%">
													<common:message key="mailtracking.defaults.consignment.lbl.cat"/>
												</td>
												<td width="5%">
													<common:message key="mailtracking.defaults.consignment.lbl.class"/>
												</td>
												<td width="6%">
													<common:message key="mailtracking.defaults.consignment.lbl.sc"/>
												</td>
												<td width="4%">
													<common:message key="mailtracking.defaults.consignment.lbl.yr"/><span class="iCargoMandatoryFieldIcon">*</span>
												</td>
												<!-- modified width by A-8149 for ICRD-261108  -->
												<td width="4%">
													<common:message key="mailtracking.defaults.consignment.lbl.dsn"/><span class="iCargoMandatoryFieldIcon">*</span>
												</td>
												<!-- modified width by A-8149 for ICRD-261108  -->
												<td width="4%">
													<common:message key="mailtracking.defaults.consignment.lbl.rsn"/>
												</td>
												<!--  modified width by A-8149 for ICRD-261108 -->
												<td width="5%">
													<common:message key="mailtracking.defaults.consignment.lbl.numbags"/><span class="iCargoMandatoryFieldIcon">*</span>
												</td>
												<td width="4%">
													<common:message key="mailtracking.defaults.consignment.lbl.hni"/>
												</td>
												<td width="4%">
													<common:message key="mailtracking.defaults.consignment.lbl.ri"/>
												</td>
												<td width="14%">
													<common:message key="mailtracking.defaults.consignment.lbl.wt"/><span class="iCargoMandatoryFieldIcon">*</span>
												</td>
												       <common:xgroup>
                                                          <common:xsubgroup id="TURKISH_SPECIFIC">
														  <ihtml:hidden  property="reportFlag" value="Y"/>
												<td width="10%">
													<common:message key="mailtracking.defaults.consignment.lbl.decalredvalue"/>
												</td>
												<td width="8%">
													<common:message key="mailtracking.defaults.consignment.lbl.currencycode"/>
												</td>
												          </common:xsubgroup> 
                                                        </common:xgroup >
												<!--  modified width by A-8149 for ICRD-261108 -->
												<td width="8%">
													<common:message key="mailtracking.defaults.consignment.lbl.uldnum"/>
												</td>
									</tr>
								</thead>
								<tbody id="mailTableBody">
										<% int tabNum = 7; %>
											<logic:present name="conDocVOSession" property="mailInConsignmentVOs">
												<bean:define id="mailInConsignmentVOs" name="conDocVOSession" property="mailInConsignmentVOs" scope="page" toScope="page"/>
												<% int row = ((Collection)mailInConsignmentVOs).size();%>
												<logic:iterate id="mailInConsignmentVO" name="mailInConsignmentVOs" indexId="index">
												<common:rowColorTag index="index">
													<% tabNum = tabNum + row * index; %>
													<%boolean toDisable = false;%>
													<%boolean toDisableWt = false;%>
													<logic:notEqual name="mailInConsignmentVO" property="operationFlag" value="D">
														<logic:notEqual name="mailInConsignmentVO" property="operationFlag" value="I">
															<% toDisable = true;%>
														</logic:notEqual>
														<logic:present name="mailInConsignmentVO" property="receptacleSerialNumber">
															<%
																if(toDisable){
																	toDisableWt = true;
																}
															%>
												 	    </logic:present>
														<logic:present name="mailInConsignmentVO" property="operationFlag">
															<bean:define id="operationFlag" name="mailInConsignmentVO" property="operationFlag" toScope="request" />
															<ihtml:hidden property="mailOpFlag" value="<%=((String)operationFlag)%>" />
														</logic:present>
														<logic:notPresent name="mailInConsignmentVO" property="operationFlag">
															<ihtml:hidden property="mailOpFlag" value="N" />
														</logic:notPresent>
									<tr>
															<td class="ic-center">
																<input name="selectMail" type="checkbox" value="<%=String.valueOf(index)%>" />
															</td>
															<td>
																<logic:notPresent name="mailInConsignmentVO" property="mailId">
																	<ihtml:text property="mailbagId" componentID="CMP_MAILTRACKING_DEFAULTS_CONSIGMENT_MAILBAGID" value="" maxlength="29"  readonly="<%=toDisable%>" indexId="index" style="width:210px"/> <!-- modified. A-8164 for ICRD 257671-->
																</logic:notPresent>
																<logic:present name="mailInConsignmentVO" property="mailId">
																	<bean:define id="mailbagId" name="mailInConsignmentVO" property="mailId" toScope="page"/>
																	<ihtml:text property="mailbagId" value="<%=(String)mailbagId%>" componentID="CMP_MAILTRACKING_DEFAULTS_CONSIGMENT_MAILBAGID" maxlength="29"  readonly="<%=toDisable%>" indexId="index" style="width:210px"/> <!-- modified. A-8164 for ICRD 257671-->
																</logic:present>
															</td>															
															<td>
																<logic:notPresent name="mailInConsignmentVO" property="originExchangeOffice">
																	<ihtml:text property="originOE" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_OOE" value="" maxlength="6"  readonly="<%=toDisable%>" indexId="index" style="width:70px"/>
																</logic:notPresent>
																<logic:present name="mailInConsignmentVO" property="originExchangeOffice">
																	<bean:define id="ooe" name="mailInConsignmentVO" property="originExchangeOffice" toScope="page"/>
																	<ihtml:text property="originOE" value="<%=(String)ooe%>" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_OOE" maxlength="6"  readonly="<%=toDisable%>" indexId="index" style="width:70px"/>
																</logic:present>
																<%if(toDisable){%>
																	<img name="OOELov" id="OOELov<%=index%>" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" disabled>
																<%}else{%>
																	<img name="OOELov" id="OOELov<%=index%>" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16">
																<%}%>
															</td>
															<td>
																<logic:notPresent name="mailInConsignmentVO" property="destinationExchangeOffice">
																	<ihtml:text property="destinationOE" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_DOE" value="" maxlength="6"  readonly="<%=toDisable%>" indexId="index" style="width:70px"/>
																</logic:notPresent>
																<logic:present name="mailInConsignmentVO" property="destinationExchangeOffice">
																	<bean:define id="doe" name="mailInConsignmentVO" property="destinationExchangeOffice" toScope="page"/>
																	<ihtml:text property="destinationOE" value="<%=(String)doe%>" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_DOE" maxlength="6"  readonly="<%=toDisable%>" indexId="index" style="width:70px"/>
																</logic:present>
																<%if(toDisable){%>
																	<img name="DOELov" id="DOELov<%=index%>" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" disabled>
																<%}else{%>
																	<img name="DOELov" id="DOELov<%=index%>" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16">
																<%}%>
															</td>
															<td>
																<% String catValue = ""; %>
																<logic:present name="mailInConsignmentVO" property="mailCategoryCode">
																	<bean:define id="mailCatCode" name="mailInConsignmentVO" property="mailCategoryCode" toScope="page"/>
																	<% catValue = (String) mailCatCode; %>
																</logic:present>
																<ihtml:select property="category" componentID="CMB_MAILTRACKING_DEFAULTS_CONSIGNMENT_CAT" value="<%=catValue%>" style="width:35px"  disabled="<%=toDisable%>" indexId="index">
																	<bean:define id="oneTimeCatSess" name="oneTimeCatSession" toScope="page" />
																	<logic:iterate id="oneTimeCatVO" name="oneTimeCatSess" >
																		<bean:define id="fieldValue" name="oneTimeCatVO" property="fieldValue" toScope="page" />
																		<html:option value="<%=(String)fieldValue %>">
																			<bean:write name="oneTimeCatVO" property="fieldValue"/>
																		</html:option>
																	</logic:iterate>
																</ihtml:select>
															</td>
															<td>
																<% String classValue = ""; %>
																<logic:present name="mailInConsignmentVO" property="mailClass">
																	<bean:define id="mailClass" name="mailInConsignmentVO" property="mailClass" toScope="page"/>
																	<% classValue = (String) mailClass; %>
																</logic:present>
																<ihtml:select property="mailClass" componentID="CMB_MAILTRACKING_DEFAULTS_CONSIGNMENT_CLASS" value="<%=classValue%>" style="width:35px"  disabled="<%=toDisable%>" indexId="index">
																	<bean:define id="oneTimeMailClassSess" name="oneTimeMailClassSession" toScope="page" />
																	<logic:iterate id="oneTimeMailClassVO" name="oneTimeMailClassSess" >
																		<bean:define id="fieldValue" name="oneTimeMailClassVO" property="fieldValue" toScope="page" />
																		<html:option value="<%=(String)fieldValue %>">
																			<bean:write name="oneTimeMailClassVO" property="fieldValue"/>
																		</html:option>
																	</logic:iterate>
																</ihtml:select>
															</td>
															<td>
																<% String subclassValue = ""; %>
																	<logic:notPresent name="mailInConsignmentVO" property="mailSubclass">
																		<ihtml:text property="subClass" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_SC" value="" maxlength="2"  readonly="<%=toDisable%>" indexId="index" style="width:35px"/>
																	</logic:notPresent>
																	<logic:present name="mailInConsignmentVO" property="mailSubclass">
																		<bean:define id="mailSubclass" name="mailInConsignmentVO" property="mailSubclass" toScope="page"/>
																		<% subclassValue = (String) mailSubclass;
																		int arrays=subclassValue.indexOf("_");
																		if(arrays==-1){%>
																			<ihtml:text property="subClass" value="<%=(String)mailSubclass%>" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_SC" maxlength="2"  readonly="<%=toDisable%>" indexId="index" style="width:35px"/>
																		<%}else{%>
																			<ihtml:text property="subClass" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_SC" value="" maxlength="2" readonly="<%=toDisable%>" indexId="index" style="width:35px"  />
																		<%}%>
																	</logic:present>
																	<%if(toDisable){%>
																		<img name="SCLov" id="SCLov<%=index%>" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" disabled>
																	<%}else{%>
																		<img name="SCLov" id="SCLov<%=index%>" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16">
																	<%}%>
															</td>
															<td>
																<logic:notPresent name="mailInConsignmentVO" property="year">
																	<ihtml:text property="year" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_YR" value="" maxlength="1" style="width:20px;text-align:right"  readonly="<%=toDisable%>" indexId="index" />
																</logic:notPresent>
																<logic:present name="mailInConsignmentVO" property="year">
																	<bean:define id="year" name="mailInConsignmentVO" property="year" toScope="page"/>
																	<ihtml:text property="year" value="<%=year.toString()%>" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_YR" maxlength="1" style="width:20px;text-align:right"  readonly="<%=toDisable%>" indexId="index"  />
																</logic:present>
															</td>
															<td>
																<logic:notPresent name="mailInConsignmentVO" property="dsn">
																	<ihtml:text property="dsn" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_DSN" value="" maxlength="4"  readonly="<%=toDisable%>"  style = "text-align:right" indexId="index"/>
																</logic:notPresent>
																<logic:present name="mailInConsignmentVO" property="dsn">
																	<bean:define id="dsn" name="mailInConsignmentVO" property="dsn" toScope="page"/>
																	<ihtml:text property="dsn" value="<%=(String)dsn%>" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_DSN" maxlength="4"  readonly="<%=toDisable%>"  style = "text-align:right" indexId="index"/>
																</logic:present>
															</td>
															<td>
																<logic:notPresent name="mailInConsignmentVO" property="receptacleSerialNumber">
																	<ihtml:text property="rsn" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_RSN" value="" maxlength="3"  readonly="<%=toDisable%>"  style = "text-align:right" indexId="index"/>
																</logic:notPresent>
																<logic:present name="mailInConsignmentVO" property="receptacleSerialNumber">
																	<bean:define id="rsn" name="mailInConsignmentVO" property="receptacleSerialNumber" toScope="page"/>
																	<ihtml:text property="rsn" value="<%=(String)rsn%>" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_RSN" maxlength="3"  readonly="<%=toDisable%>"  style = "text-align:right" indexId="index"/>
																</logic:present>
															</td>
															<td>
																<logic:notPresent name="mailInConsignmentVO" property="statedBags">
																	<ihtml:text property="numBags" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_NUMBAG" value="1" maxlength="5"  readonly="<%=toDisableWt%>"  style = "text-align:right"/>
																</logic:notPresent>
																<logic:present name="mailInConsignmentVO" property="statedBags">
																	<bean:define id="statedBags" name="mailInConsignmentVO" property="statedBags" toScope="page"/>
																	<ihtml:text property="numBags" value="<%=statedBags.toString()%>" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_NUMBAG"  readonly="<%=toDisableWt%>" maxlength="5"  style = "text-align:right"/>
																</logic:present>
															</td>
															<td>
																<% String hniValue = ""; %>
																<logic:present name="mailInConsignmentVO" property="highestNumberedReceptacle">
																	<bean:define id="hni" name="mailInConsignmentVO" property="highestNumberedReceptacle" toScope="page"/>
																	<% hniValue = (String) hni; %>
																</logic:present>
																<ihtml:select property="mailHI" componentID="CMB_MAILTRACKING_DEFAULTS_CONSIGNMENT_HNI" value="<%=hniValue%>" style="width:40px"  disabled="<%=toDisable%>" indexId="index">
																	<bean:define id="oneTimeHNISess" name="oneTimeHNISession" toScope="page" />
																	<ihtml:option value="">
																		<common:message key="combo.select"/>
																	</ihtml:option>
																	<logic:iterate id="oneTimeHNIVO" name="oneTimeHNISess" >
																		<bean:define id="fieldValue" name="oneTimeHNIVO" property="fieldValue" toScope="page" />
																		<html:option value="<%=(String)fieldValue %>">
																			<bean:write name="oneTimeHNIVO" property="fieldValue"/>
																		</html:option>
																	</logic:iterate>
												    			</ihtml:select>
															</td>
															<td>
															    <% String riValue = ""; %>
															    <logic:present name="mailInConsignmentVO" property="registeredOrInsuredIndicator">
																	<bean:define id="ri" name="mailInConsignmentVO" property="registeredOrInsuredIndicator" toScope="page"/>
																	<% riValue = (String) ri; %>
															    </logic:present>
															    <ihtml:select property="mailRI" componentID="CMB_MAILTRACKING_DEFAULTS_CONSIGNMENT_RI" value="<%=riValue%>" style="width:40px"  disabled="<%=toDisable%>" indexId="index">
																	<bean:define id="oneTimeRISess" name="oneTimeRISession" toScope="page" />
																	<ihtml:option value="">
																		<common:message key="combo.select"/>
																	</ihtml:option>
																	<logic:iterate id="oneTimeRIVO" name="oneTimeRISess" >
																		<bean:define id="fieldValue" name="oneTimeRIVO" property="fieldValue" toScope="page" />
																		<html:option value="<%=(String)fieldValue %>">
																			<bean:write name="oneTimeRIVO" property="fieldValue"/>
																		</html:option>
																	</logic:iterate>
																</ihtml:select>
															</td>
															<td>
													           
															    <logic:present name="mailInConsignmentVO" property="statedWeight">
																	<bean:define id="mailWeight" name="mailInConsignmentVO" property="statedWeight" toScope="page" type="com.ibsplc.icargo.framework.util.unit.Measure"/>
																	 
																	
																	<% request.setAttribute("weight",mailWeight); %>
																		
																       <ibusiness:unitCombo  unitTxtName="weight" style="background :'<%=color%>';text-align:right" disabled="<%=toDisable%>"  label="" title="Stated Weight" 
																	   unitValue="<%=String.valueOf(mailWeight.getDisplayValue())%>"
																	   dataName="weight"
																	   unitValueStyle="iCargoTextFieldSmall " unitListName="weightUnit" unitTypeStyle="iCargoMediumComboBox" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_WT" unitListValue="<%=(String)mailWeight.getDisplayUnit()%>"  tabindex="0" unitTypePassed="MWT"/>
																	       
													</logic:present>
												    			
															</td><!--modified by A-8353 for ICRD-274933-->
															
														<common:xgroup>
                                                            <common:xsubgroup id="TURKISH_SPECIFIC">
														   
														  
														<td>
													            <logic:notPresent name="mailInConsignmentVO" property="declaredValue">
																<ibusiness:moneyEntry id="declaredValue" property="declaredValue" moneyproperty="declaredValue" formatMoney="true" value="0" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_DECLAREDVALUE" style = "text-align:right" />
															    </logic:notPresent>
																<logic:present name="mailInConsignmentVO" property="declaredValue">
																	<ibusiness:moneyEntry name="mailInConsignmentVO" id="declaredValue" moneyproperty="declaredValue"  property="declaredValue" formatMoney="true"   componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_DECLAREDVALUE" style = "text-align:right"/>
																</logic:present>
																
															    
															</td>
															
															<td>
															 <logic:notPresent name="mailInConsignmentVO" property="currencyCode">
													            <ihtml:text property="currencyCode" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_CURCOD" value=""  maxlength="3"/>
					                                          <img name="currencyCodeLov" id="currencyCodeLov<%=index%>" type="currencyCodeLov" src="<%= request.getContextPath()%>/images/lov.png" width="16" height="16" border="0" />
															  </logic:notPresent>
															   <logic:present name="mailInConsignmentVO" property="currencyCode">
															   <bean:define id="curCod" name="mailInConsignmentVO" property="currencyCode" toScope="page"/>
															    <ihtml:text property="currencyCode" value="<%=(String)curCod%>" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_CURCOD"  maxlength="3"/>
																<img name="currencyCodeLov" id="currencyCodeLov<%=index%>" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16">
															   </logic:present>
															  
															</td>
															

                                                            </common:xsubgroup> 
                                                        </common:xgroup >

															<td>
													            <logic:notPresent name="mailInConsignmentVO" property="uldNumber">
																	<ibusiness:uld id="uldNum" uldProperty="uldNum" style="text-transform:uppercase;" indexId="index" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_ULDNUM" maxlength="12" uldValue=""/>
															    </logic:notPresent>
															    <logic:present name="mailInConsignmentVO" property="uldNumber">
																	<bean:define id="uldNum" name="mailInConsignmentVO" property="uldNumber" toScope="page"/>
																	<ibusiness:uld id="uldNum" uldProperty="uldNum" style="text-transform:uppercase;" indexId="index" componentID="TXT_MAILTRACKING_DEFAULTS_CONSIGNMENT_ULDNUM" maxlength="12" uldValue="<%=(String)uldNum%>"/>
												    			</logic:present>
															</td>
								</tr>
													</logic:notEqual>
													<logic:equal name="mailInConsignmentVO" property="operationFlag" value="D">

														<bean:define id="originExchangeOffice" name="mailInConsignmentVO" property="originExchangeOffice" toScope="page"/>
														<bean:define id="destinationExchangeOffice" name="mailInConsignmentVO" property="destinationExchangeOffice" toScope="page"/>
														<bean:define id="mailCatCode" name="mailInConsignmentVO" property="mailCategoryCode" toScope="page"/>
														<bean:define id="mailClass" name="mailInConsignmentVO" property="mailClass" toScope="page"/>
														<bean:define id="mailSubclass" name="mailInConsignmentVO" property="mailSubclass" toScope="page"/>
														<bean:define id="year" name="mailInConsignmentVO" property="year" toScope="page"/>
														<bean:define id="dsn" name="mailInConsignmentVO" property="dsn" toScope="page"/>
														<bean:define id="receptacleSerialNumber" name="mailInConsignmentVO" property="receptacleSerialNumber" toScope="page"/>
														<bean:define id="statedBags" name="mailInConsignmentVO" property="statedBags" toScope="page"/>
														<bean:define id="highestNumberedReceptacle" name="mailInConsignmentVO" property="highestNumberedReceptacle" toScope="page"/>
														<bean:define id="registeredOrInsuredIndicator" name="mailInConsignmentVO" property="registeredOrInsuredIndicator" toScope="page"/>
														<bean:define id="statedWeight" name="mailInConsignmentVO" property="statedWeight" toScope="page" type="com.ibsplc.icargo.framework.util.unit.Measure"/><!--A-7371-->
														<bean:define id="declaredValue" name="mailInConsignmentVO" property="declaredValue" toScope="page"/>
														<bean:define id="currencyCode" name="mailInConsignmentVO" property="currencyCode" toScope="page"/>
														<bean:define id="uldNumber" name="mailInConsignmentVO" property="uldNumber" toScope="page"/>
														  
														<ihtml:hidden property="originOE" value="<%=originExchangeOffice.toString()%>"/>
														<ihtml:hidden property="destinationOE" value="<%=destinationExchangeOffice.toString()%>"/>
														<ihtml:hidden property="category" value="<%=mailCatCode.toString()%>"/>
														<ihtml:hidden property="mailClass" value="<%=mailClass.toString()%>"/>
														<ihtml:hidden property="subClass" value="<%=mailSubclass.toString()%>"/>
														<ihtml:hidden property="year" value="<%=year.toString()%>"/>
														<ihtml:hidden property="dsn" value="<%=dsn.toString()%>"/>
														<ihtml:hidden property="rsn" value="<%=receptacleSerialNumber.toString()%>"/>
														<ihtml:hidden property="numBags" value="<%=statedBags.toString()%>"/>
														<ihtml:hidden property="mailHI" value="<%=highestNumberedReceptacle.toString()%>"/>
														<ihtml:hidden property="mailRI" value="<%=registeredOrInsuredIndicator.toString()%>"/>
														<ihtml:hidden property="weight" value="<%=String.valueOf(statedWeight.getDisplayValue())%>"/><!--A-7371-->
														<common:xgroup>
                                                          <common:xsubgroup id="TURKISH_SPECIFIC">
														<ihtml:hidden property="declaredValue" value="<%=declaredValue.toString()%>"/>
														<ihtml:hidden property="currencyCode" value="<%=currencyCode.toString()%>"/>
														  </common:xsubgroup> 
                                                        </common:xgroup >

														<ihtml:hidden property="uldNum" value="<%=uldNumber.toString()%>"/>		
																											
														<ihtml:hidden property="mailOpFlag" value="D" />
													</logic:equal>				  
													</common:rowColorTag>
												</logic:iterate>
											</logic:present>
									
									 <!-- templateRow -->
										<jsp:include page="Consigment_MailDetails_Template.jsp" /> 				
									
			   <!--template row ends-->
									
								</tbody>
							</table>
						</div>
						</div>
					</div>
				</div>
			
			