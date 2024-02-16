<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  IN - Inventory Control
* File Name				:  MaintainProduct_General_Tab.jsp
* Date					:  15-July-2001
* Author(s)				:  Amritha S

*************************************************************************/
 --%>
<%@ page import="com.ibsplc.icargo.business.products.defaults.vo.ProductParamterVO"%>

<%@ include file="/jsp/includes/tlds.jsp" %>

<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Collection" %>

<business:sessionBean id="dynamicOptions"
	moduleName="product.defaults"
	screenID="products.defaults.maintainproduct"
	method="get"
	attribute="dynamicDocType" />

<logic:present name="dynamicOptions">
	<bean:define id="dynamicOptions" name="dynamicOptions"/>
</logic:present>

<business:sessionBean id="productVO"
	moduleName="product.defaults"
	screenID="products.defaults.maintainproduct"
	method="get"
	attribute="productVO" />
<logic:present name="productVO">
	<bean:define id="productVO" name="productVO"/>
</logic:present>
<business:sessionBean id="parameterVOs"
	moduleName="product.defaults"
	screenID="products.defaults.maintainproduct"
	method="get"
	attribute="productParamters" />
<logic:present name="parameterVOs">
	<bean:define id="parameterVOs" name="parameterVOs" toScope="page"/>
</logic:present>
<bean:define id="form"
	name="MaintainProductForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainProductForm"
	toScope="page" />
	
		<div class="ic-row">
			<div class="ic-col-45 ic-label-15">
			<div class="ic-input-round-border" style="height:197px">
				<div class="ic-split-100" >
					<label style="padding-left:5px;">
						 <common:message  key="products.defaults.Icon" scope="request"/>
					</label>
					<ihtml:file property="icon" styleClass="iCargoTextFieldExtraLong" title="Select an icon for the product" maxlength="75"/>
				</div>
				<div class="ic-input ic-split-100">
					<label>
						<common:message  key="products.defaults.status" scope="request"/>
					</label>
					<ihtml:text property="productStatus" componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINPRD_PRDSTATUS" value="<%=form.getProductStatus()%>"
						readonly="true" maxlength="10"/>
				</div>
				<div class="ic-input ic-split-100">
					<label>
						 <common:message  key="products.defaults.Desc" scope="request"/>
					</label>
					<ihtml:text property="productDesc" componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINPRD_PRDDESCRIPTION" maxlength="100" />
				</div>
				<div class="ic-input ic-split-100 ic-label-100">
					<label>
						 <common:message  key="products.defaults.DetDesc" scope="request"/>
					</label>
					<ihtml:textarea property="detailDesc" componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINPRD_PRDLONGDESCRIPTION" rows="2" cols="55"></ihtml:textarea>
				</div>
			</div>
			</div>
			<div class="ic-col-55">
                <div class="ic-input-round-border" style="height: 197px;">
                <div class="ic-col-50">
                    <div class="ic-input ic-split-100 ic_inline_chcekbox">
                        <ihtml:checkbox property="rateDefined" componentID="CHK_PRODUCTS_DEFAULTS_MAINTAINPRD_RATEDEFINED"/>
                        <label>
                             <common:message  key="products.defaults.RateDef" scope="request"/>
                        </label>
                    </div>
                    <!--commented as part of ICRD-166985
                        div class="ic-input ic-split-100">
                        <ihtml:checkbox property="coolProduct" componentID="CHK_PRODUCTS_DEFAULTS_MAINTAINPRD_COOLCHKBOX"/>
                        <label>
                            <common:message  key="products.defaults.label.coolcheckbox" scope="request"/>
                        </label>
                    </div-->
                    <div class="ic-input ic-split-100 ic_inline_chcekbox">
                              <ihtml:checkbox property="bookingMand"
                              componentID="CHK_PRODUCTS_DEFAULTS_MAINTAINPRD_BOOKINGMAND"/>
                              <label>
                              <common:message  key="products.defaults.bookingmandatory" scope="request"/>
                              </label>
                           </div>
					<div class="ic-input ic-split-100">
							<ihtml:checkbox property="displayInPortal" componentID="CHK_PRODUCTS_DEFAULTS_MAINTAINPRD_PORTALDISPLAYCHKBOX"/>
							<label>
								<common:message  key="products.defaults.label.portaldisplaycheckbox" scope="request"/>
							</label>
						</div>
                    <div class="ic-input ic-split-100 ic_inline_chcekbox">
                         <ihtml:checkbox property="proactiveMilestoneEnabled" componentID="CHK_PRODUCTS_DEFAULTS_MAINTAINPRD_ISPROACTIVEMILESTONEENABLED"/>
                        <label>
                             <common:message  key="products.defaults.enableProactiveMilestoneManagement" scope="request"/>
                        </label>
                    </div>
					<!-- Added as part of CR ICRD-237928 by A-8154 : Starts -->
                    <div class="ic-input ic-split-100 ic_inline_chcekbox">
                         <ihtml:checkbox property="overrideCapacity" componentID="CHK_PRODUCTS_DEFAULTS_MAINTAINPRD_ISOVERRIDECAPACITYCHECK"/>
                        <label>
                             <common:message  key="products.defaults.overrideCapacityCheck" scope="request"/>
                        </label>
                    </div>
					<!-- Added as part of CR ICRD-237928 by A-8154 : Ends -->
                     <div class="ic-input ic-split-100">
							<ihtml:text property="productPriority" componentID="CHK_PRODUCTS_DEFAULTS_MAINTAINPRD_PRODUCTPRIORITY"
											maxlength="2" />
							<label>
								<common:message  key="products.defaults.label.productpriority" scope="request"/>
							</label>
						</div>
                </div>
                <div class="ic-col-50">
                        <div class="ic-input ic-split-100">
                            <label class="ic-label-30"> 
                                                <common:message key="products.defaults.productcategory" /></label>
                                        <ihtml:select 
                                            multiSelect="true" 
                                            indexId="rowCountForType"
                                            multiSelectNoneSelectedText="----Select-------------------" 
                                            multiSelectMinWidth="165" 
                                            multiple="multiple" 
                                            property="productCategory" 
                                            name="form" id="CMP_PRODUCTS_DEFAULTS_MAINTAINPRD_CATEGORY"
                                            componentID="CMP_PRODUCTS_DEFAULTS_MAINTAINPRD_CATEGORY" onchange="setProductCategory()" >
                                            <business:sessionBean id="productCategoryOneTimes" moduleName="product.defaults"
                                                screenID="products.defaults.maintainproduct"
                                                method="get"
                                                attribute="productCategories" />
                                            <logic:present name="productCategoryOneTimes" >
                                                    <logic:iterate id="oneTimeVO" name="productCategoryOneTimes">
                                                        <bean:define id="defaultValue" name="oneTimeVO" property="fieldValue" />
                                                        <bean:define id="displayValue" name="oneTimeVO" property="fieldDescription" />
                                                        <html:option value="<%=(String)defaultValue%>"><%=(String)displayValue%></html:option>
                                                    </logic:iterate>
                                                </logic:present>
                                            </ihtml:select>	
                                        </div>
                    </div>

                    <div class="ic-input ic-split-95 marginT10">
                        <fieldset class="ic-field-set " >
                            <legend class="iCargoLegend">
                                <common:message  key="products.defaults.stock" scope="request"/>
                            </legend>
                            <div class="ic-input ic-split-75 ic-center marginT5">
                                <logic:present  name="dynamicOptions">
                                    <bean:define name="dynamicOptions" id="list" type="java.util.HashMap"/>
                                    <ibusiness:dynamicoptionlist  collection="list"
                                        id="docType"
                                        firstlistname="docType"
                                        componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINPRD_DYNAMICOPTIONLIST"
                                        subDocTypeMandatory="false"
                                        docTypeMandatory="false"
                                        lastlistname="subType" firstoptionlabel="Doc. Type"
                                        lastoptionlabel="Sub Type"
                                        optionstyleclass="iCargoComboBox"
                                        labelstyleclass="iCargoLabelRightAligned"
                                        firstselectedvalue="<%=form.getDocType()%>"
                                        lastselectedvalue="<%=form.getSubType()%>"
                                        docTypeTitle="products.defaults.docTypeTitle.tooltip"
                                        subDocTypeTitle="products.defaults.subDocTypeTitle.tooltip"/>
                                </logic:present>
                            </div>
                        </fieldset>
                    </div>
                </div>
			</div>
		</div>
		<div class="ic-row">
			<div class="ic-col-15">
				<div class="ic-section ic-pad-3">
					<div class="ic-button-container paddR5">
						<a href="#" class="iCargoLink" onclick="addDataFromLOV1('products.defaults.beforeOpenTransportModeLov.do',
							'products.defaults.screenloadtransportmodelov.do',
							'products.defaults.selectTransportModeNew.do','','div1')">
							<common:message  key="products.defaults.add" scope="request"/>
						</a>
						|
						<a href="#" class="iCargoLink" onclick="deleteTableRow1('products.defaults.deleteTransportModeNew.do','transportModeCheck','div1')">
							<common:message  key="products.defaults.delete" scope="request"/>
						</a>
					</div>
					<div class="tableContainer paddL5" id="div1" style="height:175px">
						<table class="fixed-header-table" id="transportModeTable">
							<thead>
								<tr class="iCargoTableHeadingLeft">
									<td width="16%" class="iCargoTableHeadingCenter">
										<ihtml:checkbox  property="checkAllTransportmode" onclick="updateHeaderCheckBox(this.form,this,this.form.transportModeCheck)"
											componentID="CHK_PRODUCTS_DEFAULTS_MAINTAINPRD_TRANSPORTMODE"/>
									</td>
									<td width="84%" >
										<common:message  key="products.defaults.TransMode" scope="request"/>  
										<span class="iCargoMandatoryFieldIcon">*</span> &nbsp; &nbsp;
									</td>									 
								</tr>
							</thead>
							<tbody>
								<business:sessionBean id="transportModeVOsFromSession" moduleName="product.defaults"
									screenID="products.defaults.maintainproduct"
									method="get"
									attribute="productTransportModeVOs" />
								<logic:present name="transportModeVOsFromSession" >
									<bean:define id="transportModes" name="transportModeVOsFromSession"  />
									<logic:iterate id="transportMode" name="transportModes" indexId="rowCount" >
									  	<logic:present name="transportMode" property="operationFlag">
											<bean:define id="opFlag" name="transportMode" property="operationFlag" />
											<logic:notEqual value="D" name="transportMode" property="operationFlag" >
												<logic:present name="transportMode" property="transportMode">
													<bean:define id="mode" name="transportMode" property="transportMode"/>
													<tr>
														<td class="iCargoTableTd" style="text-align:center">
															<ihtml:checkbox property="transportModeCheck" 
																value="<%=(String)mode%>" 
																onclick="toggleTableHeaderCheckbox('transportModeCheck',this.form.checkAllTransportmode)"
																componentID="CHK_PRODUCTS_DEFAULTS_MAINTAINPRD_TRANSPORTMODE"/>
														</td>
														<td class="iCargoTableTd"><bean:write name="transportMode" property="transportMode" />
															<html:hidden value="<%=(String)mode%>" property="transportMode"/>
														</td>
													</tr>
												</logic:present>
											</logic:notEqual>
										</logic:present>
										<logic:notPresent name="transportMode" property="operationFlag">
											<logic:present name="transportMode" property="transportMode">
												<bean:define id="mode" name="transportMode" property="transportMode"/>
												<tr>
													<td class="iCargoTableTd" style="text-align:center">
														<ihtml:checkbox property="transportModeCheck" 
															value="<%=(String)mode%>" 	onclick="toggleTableHeaderCheckbox('transportModeCheck',this.form.checkAllTransportmode)"
															componentID="CHK_PRODUCTS_DEFAULTS_MAINTAINPRD_TRANSPORTMODE"/>
													</td>
													<td class="iCargoTableTd"><bean:write name="transportMode" property="transportMode" />
														<html:hidden value="<%=(String)mode%>" property="transportMode"/>
													</td>
												</tr>
											</logic:present>
										</logic:notPresent>
									</logic:iterate>
								</logic:present>
							</tbody>
						</table>
					</div>
				</div>
			</div>			
			<div class="ic-col-15">
				<div class="ic-section ic-pad-3">
					<div class="ic-button-container paddR5">
						<a href="#" class="iCargoLink" onclick="addDataFromLOV1('products.defaults.beforeOpenPriorityLov.do',
							'products.defaults.screenloadprioritylov.do',
							'products.defaults.selectPriorityNew.do','','div2')" >
							 <common:message  key="products.defaults.add" scope="request"/>
						</a>
						| 
						<a href="#" class="iCargoLink" onclick="deleteTableRow1('products.defaults.deletePriorityNew.do','priorityCheck','div2')">
							<common:message  key="products.defaults.delete" scope="request"/>
						</a>
					</div>
					<div class="tableContainer" id="div2" style="height:175px;">
						<table class="fixed-header-table" id="priorityTable">
							<thead>
								<tr class="iCargoTableHeadingLeft">
									<td width="16%" class="iCargoTableHeadingCenter ic-center">
										<input type="checkbox" name="checkAllPriority"
											onclick="updateHeaderCheckBox(this.form,this,this.form.priorityCheck)"
											title='<common:message  bundle="MaintainProduct" key="products.defaults.priotity"
											scope="request"/>'/>
									</td>
									<td width="84%">
										Priority <span class="iCargoMandatoryFieldIcon">*</span> &nbsp; &nbsp;
									</td>
								</tr>
							</thead>
									<tbody>
										<business:sessionBean id="priorityVOsFromSession" moduleName="product.defaults"
											screenID="products.defaults.maintainproduct"
											method="get"
											attribute="productPriorityVOs" />
										<logic:present name="priorityVOsFromSession" >
											<bean:define id="priorities" name="priorityVOsFromSession" />
											<logic:iterate id="priority" name="priorities" indexId="rowCount1" >
												<logic:present name="priority" property="operationFlag">
													<bean:define id="opFlag" name="priority" property="operationFlag" />
													<logic:notEqual value="D" name="priority" property="operationFlag" >
														<logic:present name="priority" property="priority">
															<bean:define id="priorityCode" name="priority" property="priority"/>
															<tr>
																<td  class="iCargoTableTd ic-center">
																	<div>
																		<ihtml:checkbox property="priorityCheck"
																			value="<%=(String)priorityCode%>" onclick="toggleTableHeaderCheckbox('priorityCheck',this.form.checkAllPriority)" componentID="CHK_PRODUCTS_DEFAULTS_MAINTAINPRD_PRIORITY"/>
																	</div>
																</td>
																<td class="iCargoTableTd"><bean:write name="priority" property="priorityDisplay" />
																	<html:hidden value="<%=(String)priorityCode%>" property="priority"/>
																</td>
															</tr>
														</logic:present>
													</logic:notEqual>
												</logic:present>
												<logic:notPresent name="priority" property="operationFlag">
													<logic:present name="priority" property="priority">
														<bean:define id="priorityCode" name="priority" property="priority"/>
														<tr>
															<td  class="iCargoTableTd ic-center">
																<div>
																	<ihtml:checkbox property="priorityCheck"
																		value="<%=(String)priorityCode%>" onclick="toggleTableHeaderCheckbox('priorityCheck',this.form.checkAllPriority)" componentID="CHK_PRODUCTS_DEFAULTS_MAINTAINPRD_PRIORITY"/>
																</div>
															</td>
															<td class="iCargoTableTd"><bean:write name="priority" property="priorityDisplay" />
																<html:hidden value="<%=(String)priorityCode%>" property="priority"/>
															</td>
														</tr>
													</logic:present>
												</logic:notPresent>
											</logic:iterate>
										</logic:present>
									</tbody>
								</table>
							</div>
						</div>
            </div>
			<div class="ic-col-15">
				<div class="ic-section ic-pad-3">
					<div class="ic-button-container paddR5">
						<a href="#" class="iCargoLink" onclick="addDataFromLOV1('products.defaults.beforeOpenSCCLov.do',
							'products.defaults.screenloadSccLov.do',
							'products.defaults.selectSccNew.do','','div3')">
							 <common:message  key="products.defaults.add" scope="request"/>
						 </a>
						| 
						<a href="#" class="iCargoLink" onclick="deleteTableRow1('products.defaults.deleteSCCNew.do','sccCheck','div3')">
							<common:message  key="products.defaults.delete" scope="request"/>
						</a>
					</div>
					<div class="tableContainer" id="div3" style="height:175px">
							  <table class="fixed-header-table" id="sccTable">
							  <thead>
								  <tr class="iCargoTableHeadingLeft">
									<td width="16%" class="iCargoTableHeadingCenter" style="text-align:center">
									<input type="checkbox"
									name="checkAllScc"
									onclick="updateHeaderCheckBox(this.form,this,this.form.sccCheck)"
									title='<common:message  bundle="MaintainProduct" key="products.defaults.SCC"
									scope="request"/>'
									/></td>
									<td width="84%">
									<common:message  key="products.defaults.SCC" scope="request"/> <span class="iCargoMandatoryFieldIcon">*</span> &nbsp; &nbsp;</td>
								  </tr>
								  </thead>

								<tbody>

									  <business:sessionBean id="sccVOsFromSession" moduleName="product.defaults"
										screenID="products.defaults.maintainproduct"
										method="get"
										attribute="productSccVOs" />
											<logic:present name="sccVOsFromSession" >
											  <bean:define id="sccList" name="sccVOsFromSession" />
												<logic:iterate id="sccVO" name="sccList" indexId="rowCount2" >
													<logic:present name="sccVO" property="operationFlag">
														<bean:define id="opFlag" name="sccVO" property="operationFlag" />

														<logic:notEqual value="D" name="sccVO" property="operationFlag" >
														<logic:present name="sccVO" property="scc">
														<bean:define id="sccCode" name="sccVO" property="scc"/>
															<tr>
																<td  class="iCargoTableTd" style="text-align:center">
																<ihtml:checkbox property="sccCheck" value="<%=(String)sccCode%>" onclick="toggleTableHeaderCheckbox('sccCheck',this.form.checkAllScc)"
																componentID="CHK_PRODUCTS_DEFAULTS_MAINTAINPRD_SCC" /></td>
																<td class="iCargoTableTd"><bean:write name="sccVO" property="scc"/>
																<html:hidden value="<%=(String)sccCode%>" property="sccCode"/></td>
															</tr>
														 </logic:present>
													</logic:notEqual>
												</logic:present>
												<logic:notPresent name="sccVO" property="operationFlag">
													<logic:present name="sccVO" property="scc">
													<bean:define id="sccCode" name="sccVO" property="scc"/>
														<tr>
															<td class="iCargoTableTd" style="text-align:center">
															<ihtml:checkbox property="sccCheck" value="<%=(String)sccCode%>" onclick="toggleTableHeaderCheckbox('sccCheck',this.form.checkAllScc)"
															componentID="CHK_PRODUCTS_DEFAULTS_MAINTAINPRD_SCC"/></td>
															<td class="iCargoTableTd"><bean:write name="sccVO" property="scc"/>
															<html:hidden value="<%=(String)sccCode%>" property="sccCode"/></td>
														</tr>
													 </logic:present>
												</logic:notPresent>
											</logic:iterate>
										</logic:present>
									</tbody>
								  </table>
								</div>
				</div>
			</div>
			<div class="ic-col-25">
				<div class="ic-section ic-pad-3">
					<div class="ic-button-container paddR5">
						 <a href="#" class="iCargoLink" onclick="addDataFromLOV1('products.defaults.beforeOpenServiceLov.do',
							'products.defaults.screenloadservicelov.do',
							'products.defaults.selectServiceNew.do','','div4')">
							<common:message  key="products.defaults.add" scope="request"/>
						</a>
						|
						<a href="#" class="iCargoLink" onclick="deleteTableRow1('products.defaults.servicesDeleteNew.do','productServiceCheck','div4')">
							<common:message  key="products.defaults.delete" scope="request"/>
						</a>
					</div>
					<div class="tableContainer" id="div4" style="height:175px">

							  <table class="fixed-header-table" id="serviceTable">
							  <thead>
									<tr class="iCargoTableHeadingLeft">
									<td width="8%" class="iCargoTableHeadingCenter">
									<input type="checkbox"
									name="checkAllService"
									title='<common:message  bundle="MaintainProduct" key="products.defaults.service"
									scope="request"/>'
									onclick="updateHeaderCheckBox(this.form,this,this.form.productServiceCheck)"  /></td>

									<td width="30%">
									<common:message  key="products.defaults.ServiceCode" scope="request"/></td>
									<td width="62%">
									<common:message  key="products.defaults.ServiceDesc" scope="request"/>
									</td>
								  </tr>
								  </thead>
								<tbody>
												<business:sessionBean id="serviceVOsFromSession" moduleName="product.defaults"
												screenID="products.defaults.maintainproduct"
												method="get"
											attribute="productServiceVOs" />
												<logic:present name="serviceVOsFromSession"  >
													<bean:define id="selectedServices" name="serviceVOsFromSession" />
													<logic:iterate id="service" name="selectedServices" indexId="rowCount3">
														<logic:present name="service" property="operationFlag">
															<bean:define id="opFlag" name="service" property="operationFlag" />
															<logic:notEqual value="D" name="service" property="operationFlag" >
																<logic:present name="service" property="serviceCode">
																	<bean:define id="serviceCode" name="service" property="serviceCode" />
																 <tr>
																	<td class="iCargoTableTd">
																	<ihtml:checkbox property="productServiceCheck"
																	value="<%=(String)serviceCode%>" onclick="toggleTableHeaderCheckbox('productServiceCheck',this.form.checkAllService)"
																	componentID="CHK_PRODUCTS_DEFAULTS_MAINTAINPRD_SERVICE"/>
																	</td>
																	<td class="iCargoTableTd"><bean:write name="serviceCode" />

																	<html:hidden property="productServices" value="<%=(String)serviceCode%>"/></td>
																	</logic:present>

																	<logic:present name="service" property="serviceDescription">
																		<bean:define id="serviceDesc" name="service" property="serviceDescription" />
																		<td class="iCargoTableTd"><bean:write name="serviceDesc" /></td>
																  </logic:present>

																  <logic:notPresent name="service" property="serviceDescription">
																		<td class="iCargoTableTd" >&nbsp;</td>
																  </logic:notPresent>
																</tr>
																</logic:notEqual>
																</logic:present>
																<logic:notPresent name="service" property="operationFlag">
																<logic:present name="service" property="serviceCode">
																	<bean:define id="serviceCode" name="service" property="serviceCode" />
																  <tr >
																	<td width="8%" class="iCargoTableTd">
																	<ihtml:checkbox property="productServiceCheck" onclick="toggleTableHeaderCheckbox('productServiceCheck',this.form.checkAllService)"
																	componentID="CHK_PRODUCTS_DEFAULTS_MAINTAINPRD_SERVICE"
																	value="<%=(String)serviceCode%>" />
																	</td>
																	<td width="30%" class="iCargoTableTd"><bean:write name="serviceCode" />
																	<html:hidden property="productServices" value="<%=(String)serviceCode%>"/></td>
																	</logic:present>
																	<logic:present name="service" property="serviceDescription">
																		<bean:define id="serviceDesc" name="service" property="serviceDescription" />

																		<td class="iCargoTableTd"><bean:write name="serviceDesc" /></td>
																  </logic:present>
																  <logic:notPresent name="service" property="serviceDescription">
																		<td class="iCargoTableTd">&nbsp;</td>
																  </logic:notPresent>
																</tr>
																</logic:notPresent>
															</logic:iterate>
														</logic:present>
								</tbody>
								  </table>
								</div>
				</div>
			</div>
		<div class="ic-col-26 ic-border">
			<div class="ic-row">
				<div class="ic-input ic-split-50">
					<h4><common:message key="product.default.maintainproduct_General.lbl.Parameter" /></h4>
				</div>
			</div>
			<div class="ic-row">
				<div id="div3" class="tableContainer" style="height:175px;">
					<table class="fixed-header-table" id="tableid3">
						<thead>
							<tr class="iCargoTableHeadingLeft">
								<td width="60%" class="iCargoTableHeaderLabel" data-ic-filter="productParameterDescription">
									<common:message key="product.default.maintainproduct_General.lbl.ParameterDesc" />
								</td>
								<td width="40%" class="iCargoTableHeaderLabel">
									<common:message key="product.default.maintainproduct_General.lbl.ParameterValue" />
								</td>
							</tr>
						</thead>
						<tbody>
							<logic:present name="productVO" property="productParamters">
								<%int count = 0;%>
									<bean:define id="parameters" name="productVO" property="productParamters"/>
										<logic:iterate id="parameter" name="parameters" indexId="rowCount" >
											<tr>
												<td style="height:30px;" class="iCargoTableDataTd" data-ic-filter="productParameterDescription">
													<%
													String parameterDescription = ((ProductParamterVO)parameter).getParameterDescription();
													parameterDescription=(parameterDescription == null)?"":parameterDescription;
													String parameterCode = ((ProductParamterVO)parameter).getParameterCode();
													parameterCode=(parameterCode == null)?"":parameterCode;
													String parameterValue = ((ProductParamterVO)parameter).getParameterValue();
													parameterValue=(parameterValue == null)?"":parameterValue;
													%>
														<ihtml:text property="paramCode" value="<%=(String)parameterCode%>" componentID="CMP_PRODUCTS_DEFAULTS_MAINTAINPRD_ParamDesc" readonly="true" tabindex="-1"/>
												</td>
												<td class="iCargoTableDataTd" >
													<logic:present name="parameter" property="lov" >
														<bean:define id="lovvalue" name="parameter" property="lov" />
															<logic:present name="lovvalue">
																<logic:present name="currentVO" property="displayType" >
																					<bean:define id="displayName" name="parameter" property="displayType" />
																						<logic:equal name="displayName" value="M">
																							<ihtml:select
																							name="parameter"
																							property="paramValue"
																							componentID="CMP_PRODUCTS_DEFAULTS_MAINTAINPRD_ParamValue"
																							multiSelect="true"
																							multiple="multiple"
																							multiSelectMinWidth="120"
																							indexId="rowCount"
																							tabindex="33">
																								 <logic:present name="lovvalue">
																									 <logic:iterate id="msgsta_it" name="lovvalue">
																									 <bean:define id="fieldValue" name="msgsta_it"/>
																									 <bean:define id="fieldDescription" name="msgsta_it"/>
																									 <ihtml:option value="<%=(String)fieldValue%>"><%=(String)fieldDescription%></ihtml:option>
																									 </logic:iterate>
																									</logic:present>
																								 </ihtml:select>
																						</logic:equal>
																</logic:present>
																<logic:notPresent name="currentVO" property="displayType">
																			<ihtml:select property="paramValue" value="<%=(String)parameterValue%>" componentID="CMP_PRODUCTS_DEFAULTS_MAINTAINPRD_ParamValueCombo" indexId="rowCount" tabindex="33">
																		<ihtml:option value=""><common:message key="combo.select"/>
																		</ihtml:option>
																			<logic:iterate id="msgsta_it" name="lovvalue">
																				<ihtml:option value="<%=(String)msgsta_it%>"><%=(String)msgsta_it%>
																				</ihtml:option>
																			</logic:iterate>
																	</ihtml:select>
																</logic:notPresent>	
															</logic:present>
													</logic:present>
												</td>
										</tr>
									</logic:iterate>
							</logic:present>
					</tbody>																
				</table>
			</div>
		 </div>
	</div>
		<!-- Ends ICRD ICRD-259237-->
		</div>
		<div class="ic-row" >
		<div class="ic-input-round-border">
			<div class="ic-section ic-marg-3 ic-pad-3">
			<div class="ic-col-45">
				<label>
					 <common:message  key="products.defaults.handlinginfo" scope="request"/>
				</label>
				<ihtml:textarea componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINPRD_HANDLINGINFO" property="handlingInfo" rows="2" cols="40" ></ihtml:textarea>
			</div>
			<div class="ic-col-55">
				<label>
					 <common:message  key="products.defaults.Remarks" scope="request"/>
				</label>
				 <ihtml:textarea property="remarks" componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINPRD_REMARKS" rows="2" cols="40" ></ihtml:textarea>
			</div>
		</div></div>
		</div>