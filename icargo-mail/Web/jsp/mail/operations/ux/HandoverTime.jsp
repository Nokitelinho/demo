<%@ include file="/jsp/includes/ux/tlds.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import = "com.ibsplc.icargo.framework.util.time.LocalDate" %>
<%@ page import = "java.util.Collection" %>	
<%@ page info="lite" %>
<%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.MailHandoverVO"%>
<head>
	<common:include type="script" src="/js/mail/operations/ux/MailPerformance_Script.jsp" />
</head>
	<business:sessionBean id="mailHandoverVOs"
		 moduleName="mail.operations"
		 screenID="mail.operations.ux.mailperformance"
		 method="get"
		 attribute="mailHandoverVOs"/>

	<business:sessionBean id="serviceLevel"
		 moduleName="mail.operations"
		 screenID="mail.operations.ux.mailperformance"
		 method="get"
		 attribute="serviceLevel"/>

	<business:sessionBean id="mailClassOneTIme"
		 moduleName="mail.operations"
		 screenID="mail.operations.ux.mailperformance"
		 method="get"
		 attribute="mailClasses"/>
<bean:define id="form"
		name="MailPerformanceForm" 
		type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.MailPerformanceForm" 
		toScope="page" />


<div class="card-header d-flex justify-content-end">
                    <div class="tool-bar align-items-center  pad-y-2sm">
                        <ihtml:nbutton id="btnHotAdd" styleClass="btn btn-primary"  property="btnHotAdd" componentID="BUT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_ADD" accesskey="A">
						<common:message key="mailtracking.defaults.ux.mailperformance.btn.add" />
						</ihtml:nbutton>
						
						 <ihtml:nbutton property="btnDelete" id="btnHotDelete" styleClass="btn btn-default" componentID="BUT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_DELETE" accesskey="D">
						<common:message key="mailtracking.defaults.ux.mailperformance.btn.delete" />
						</ihtml:nbutton>
			</div>
					<!-- Pagination Starts -->
					<!-- <div class="card-header card-header-action pad-y-xs">-->
					<!--//modified  by A-8527 for ICRD-352718-->
						<div class="mega-pagination">
							<logic:present name="mailHandoverVOs">	
							  <bean:define id="handoverList" name="mailHandoverVOs"/>
								<common:enhancedPaginationTag
								pageURL='javascript:submithandoverPage("lastPageNum","displayPage")'
								name="handoverList" id="handoverList"
								styleClass="ic-pagination-lg"
								lastPageNum="<%=form.getLastPageNum() %>"
								renderLengthMenu="true" lengthMenuName="defaultPageSize" 
								defaultSelectOption="<%=form.getDefaultPageSize() %>"  
								lengthMenuHandler="showhandoverEntriesReloading" pageNumberFormAttribute="displayPage"
								/>
				</logic:present>
				
			</div>
		<!--</div>-->
					<!-- Pagination Ends -->
	</div>
                <div class="card-body p-0">
				<div id="dataTableContainer" class="dataTableContainer tablePanel" style="width:100%">
					<table class="table m-0 w-100" id="handoverTimeTable">
                        <thead>
                            <tr>
								<th class="text-center check-box-cell" ><input type="checkbox" onclick="updateHeaderCheckBox(this.form,this,this.form.hoRowId);" ></th>
                                <th><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.airportcode" /></th>
                                <th><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.exchangeOffice" /></th>
								<th><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.mailclass" /></th>
								<th><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.mailsubclass" /></th>
                                <th><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.handovertime" /></th>
                                <th style="width:3%"></th>
                            </tr>
                        </thead>
						<tbody id="handoverTimeTableBody">
						<% int rowNum=0;%>
						<logic:present name="mailHandoverVOs"  >
							<logic:iterate id="mailHandoverVO" name="mailHandoverVOs" indexId="currentIndex" >
                            <tr>
									<logic:present name="mailHandoverVO" property="hoOperationFlags"> 
										<bean:define id="hoOperationFlags" name="mailHandoverVO" property="hoOperationFlags" toScope="request" />
										<logic:notEqual name="mailHandoverVO" property="hoOperationFlags" value="D">
										
										<ihtml:hidden property="hoOperationFlags" value="D"/>
											<td class="text-center">
												&nbsp;&nbsp; <ihtml:checkbox id="<%=String.valueOf(currentIndex)%>" property="hoRowId" value="<%=String.valueOf(currentIndex)%>"/>
											</td>
											<td>
												<logic:equal name="mailHandoverVO" property="hoOperationFlags" value="I">
				<div class="input-group">
														<ihtml:text style="width:100px" name="mailHandoverVO"  property="hoAirportCodes" maxlength="3" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_ARPCOD"  styleClass="form-control" /> 
														<div class="input-group-append">									
															<ihtml:nbutton styleClass="btn secondary btn-secondary btn-icon secondary"
															id="airLOV" property="airLOV">
																<i class="icon ico-expand" ></i>
															</ihtml:nbutton>
														</div>
													</div>
												</logic:equal>
												<logic:notEqual name="mailHandoverVO" property="hoOperationFlags" value="I">
												<div class="input-group">
														<ihtml:text style="width:100px" name="mailHandoverVO" maxlength="3"  property="hoAirportCodes" readonly="true"  componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_ARPCOD"  styleClass="form-control" /> 
														<div class="input-group-append">									
															<ihtml:nbutton styleClass="btn secondary btn-secondary btn-icon secondary"
															id="airLOV" property="airLOV">
																<i class="icon ico-expand" ></i>
															</ihtml:nbutton>
														</div>
													</div>
												</logic:notEqual>
											</td>
											<td>
											<logic:equal name="mailHandoverVO" property="hoOperationFlags" value="I">
				<div class="input-group">
														<ihtml:text style="width:100px" name="mailHandoverVO"  property="exchangeOffice" maxlength="6" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_EXGOFC"  styleClass="form-control" /> 
														<div class="input-group-append">									
															<ihtml:nbutton styleClass="btn secondary btn-secondary btn-icon secondary"
															id="exgofcLOV" property="exgofcLOV">
																<i class="icon ico-expand" ></i>
															</ihtml:nbutton>
														</div>
													</div>
											</logic:equal>
											<logic:notEqual name="mailHandoverVO" property="hoOperationFlags" value="I">
												<div class="input-group">
														<ihtml:text style="width:100px" name="mailHandoverVO"  property="exchangeOffice" readonly="true"  componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_EXGOFC"  styleClass="form-control" /> 
														<div class="input-group-append">									
															<ihtml:nbutton styleClass="btn secondary btn-secondary btn-icon secondary"
															id="exgofcLOV" property="exgofcLOV">
																<i class="icon ico-expand" ></i>
															</ihtml:nbutton>
														</div>
													</div>
												</logic:notEqual>
											</td>
											<td>
											<logic:equal name="mailHandoverVO" property="hoOperationFlags" value="I">
											<bean:define id="mailClass" name="mailHandoverVO" property="mailClass" />
												<ihtml:select property="hoMailClasses" styleClass="form-control" style="width:150px"  componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_MALCLASS" >
													<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
						<logic:present name="mailClassOneTIme">
							<logic:iterate id="mailClassVO" name="mailClassOneTIme">
								<bean:define id="fieldValue" name="mailClassVO" property="fieldValue" />
																
																<%
																	String isSelected = "";
																	if(mailClass != null && mailClass.equals(fieldValue)){
																	isSelected = "selected";
																	}
																%>
																<option  value="<%=(String)fieldValue %>" <%=isSelected%> > <bean:write name="mailClassVO" property="fieldValue" /></option>
															</logic:iterate>
														</logic:present>
												</ihtml:select>
											</logic:equal>
											<logic:notEqual name="mailHandoverVO" property="hoOperationFlags" value="I">
											<bean:define id="mailClass" name="mailHandoverVO" property="mailClass" />
												<bean:define id="mailClass" name="mailHandoverVO" property="mailClass" />
												<ihtml:select property="hoMailClasses" styleClass="form-control"  disabled="true" style="width:150px"  componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_MALCLASS" >
													<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
														<logic:present name="mailClassOneTIme">
															<logic:iterate id="mailClassVO" name="mailClassOneTIme">
																<bean:define id="fieldValue" name="mailClassVO" property="fieldValue" />
																<%
																	String isSelected = "";
																	if(mailClass != null && mailClass.equals(fieldValue)){
																	isSelected = "selected";
																	}
																%>
																<option  value="<%=(String)fieldValue %>" <%=isSelected%> > <bean:write name="mailClassVO" property="fieldValue" /></option>
															</logic:iterate>
														</logic:present>
												</ihtml:select>
											</logic:notEqual>
											</td>
											<td>
											<logic:equal name="mailHandoverVO" property="hoOperationFlags" value="I">
				<div class="input-group">
														<ihtml:text style="width:100px" name="mailHandoverVO"  property="mailSubClass" maxlength="2" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_MALSUBCLS"  styleClass="form-control" /> 
														<div class="input-group-append">									
															<ihtml:nbutton styleClass="btn secondary btn-secondary btn-icon secondary"
															id="exgofcLOV" property="exgofcLOV">
																<i class="icon ico-expand" ></i>
															</ihtml:nbutton>
														</div>
													</div>
												</logic:equal>
												<logic:notEqual name="mailHandoverVO" property="hoOperationFlags" value="I">
												<div class="input-group">
														<ihtml:text style="width:100px" name="mailHandoverVO" maxlength="2"  property="mailSubClass" readonly="true"  componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_MALSUBCLS"  styleClass="form-control" /> 
														<div class="input-group-append">									
															<ihtml:nbutton styleClass="btn secondary btn-secondary btn-icon secondary"
															id="exgofcLOV" property="exgofcLOV">
																<i class="icon ico-expand" ></i>
															</ihtml:nbutton>
														</div>
													</div>
											</logic:notEqual>
											</td>
											<td>
											<logic:equal name="mailHandoverVO" property="hoOperationFlags" value="I">
												<ihtml:text property="handoverTimes" name="mailHandoverVO" styleClass="form-control" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_HANDOVERTIME" style="width:70px" />
											</logic:equal>
											<logic:notEqual name="mailHandoverVO" property="hoOperationFlags" value="I">
												<ihtml:text property="handoverTime" name="mailHandoverVO" styleClass="form-control" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_HANDOVERTIME" readonly="true" style="width:70px" />
											</logic:notEqual>
											</td>
											<td><a id="delete<%=currentIndex%>" onclick="deleteRow2(this)"><i class="icon ico-del"></i></a></td>
										</logic:notEqual>
										<logic:equal name="mailHandoverVO" property="hoOperationFlags" value="D">
											<ihtml:hidden property="hoAirportCodes" name="mailHandoverVO"/>
											<ihtml:hidden property="exchangeOffice" name="mailHandoverVO"/>
											<ihtml:hidden property="mailClass" name="mailHandoverVO"/>
											<ihtml:hidden property="mailSubClass" name="mailHandoverVO"/>
											<ihtml:hidden property="handoverTimes" name="mailHandoverVO"/>
										</logic:equal>
										<ihtml:hidden property="hoOperationFlags" value="<%=((String)hoOperationFlags)%>" />
									</logic:present>
									<logic:notPresent name="mailHandoverVO" property="hoOperationFlags">
										<td class="text-center">
											&nbsp;&nbsp; <ihtml:checkbox property="hoRowId" id="<%=String.valueOf(currentIndex)%>" value="<%=String.valueOf(currentIndex)%>" disabled="false"/>
										</td>
										<td>
											<ihtml:text property="hoAirportCodes" styleClass="form-control" name="mailHandoverVO" componentID="TXT_MAILTRACKING_DEFAULTS_MAILPERFORMANCE_ARPCOD" style="width:230px" readonly="true"/>
										</td>
										<td>
											<ihtml:text property="exchangeOffice" styleClass="form-control" name="mailHandoverVO" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_EXGOFC" style="width:230px" readonly="true"/>
										</td>
										<td>
											
									
											<bean:define id="mailClass" name="mailHandoverVO" property="mailClass" />
											<ihtml:select property="hoMailClasses" styleClass="form-control" style="width:150px"  componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_MALCLASS" disabled="true">
													<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
														<logic:present name="mailClassOneTIme">
															<logic:iterate id="mailClassVO" name="mailClassOneTIme">
																<bean:define id="fieldValue" name="mailClassVO" property="fieldValue" />
																<%
																	String isSelected = "";
																	if(mailClass != null && mailClass.equals(fieldValue)){
																	isSelected = "selected";
																	}
																%>
																<option  value="<%=(String)fieldValue %>" <%=isSelected%> > <bean:write name="mailClassVO" property="fieldValue" /></option>
															</logic:iterate>
														</logic:present>
												</ihtml:select>
										</td>
										<td>
											<ihtml:text property="mailSubClass" styleClass="form-control" name="mailHandoverVO" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_MALSUBCLS" style="width:230px" readonly="true"/>
										</td>
										<td>
											<ihtml:text property="handoverTimes" name="mailHandoverVO" styleClass="form-control" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_HANDOVERTIME" style="width:70px" readonly="true" />
										</td>
										<td><a id="delete<%=currentIndex%>" onclick="deleteRow2(this)"><i class="icon ico-del"></i></a></td>
										<ihtml:hidden property="hoOperationFlags" value="N" />
									</logic:notPresent>
                            </tr>
							</logic:iterate>
						</logic:present>
						    <tr template="true" id="handoverTimeTemplateRow" style="display:none">
								<ihtml:hidden property="hoOperationFlags" value="NOOP" />
								<td class="text-center">
									<input type="checkbox"  name="hoRowId"/>
								</td>
								<td>						
									<div class="input-group">
										<ihtml:text style="width:100px" value="" property="hoAirportCodes" maxlength="3" id="hoAirportCodes" componentID="TXT_MAILTRACKING_DEFAULTS_MAILPERFORMANCE_ARPCOD" /> 
										<div class="input-group-append">									
											<ihtml:nbutton styleClass="btn secondary btn-secondary btn-icon secondary"
												  id="hoAirportLOV" property="hoAirportLOV">
												  <i class="icon ico-expand" ></i>
											 </ihtml:nbutton>
										</div>
									</div>
								</td>
								<td>
									<div class="input-group">
										<ihtml:text style="width:100px" value="" property="exchangeOffice" maxlength="6" id="exchangeOffice" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_EXGOFC" /> 
										<div class="input-group-append">									
											<ihtml:nbutton styleClass="btn secondary btn-secondary btn-icon secondary"
												  id="exchangeOfficeLOV" property="exchangeOfficeLOV">
												  <i class="icon ico-expand" ></i>
											 </ihtml:nbutton>
										</div>
									</div>
								</td>
								<td>
									<ihtml:select property="hoMailClasses" value="" styleClass="form-control" style="width:150px" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_MALCLASS"  >			<!--Modified by A-8399 as part of ICRD-293432-->
										<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
											<logic:present name="mailClassOneTIme">
												<logic:iterate id="mailClassVO" name="mailClassOneTIme">
													<bean:define id="fieldValue" name="mailClassVO" property="fieldValue" />
													<ihtml:option value="<%=(String)fieldValue %>"><bean:write name="mailClassVO" property="fieldValue" /></ihtml:option>
							</logic:iterate>
						</logic:present>
					</ihtml:select>
								</td>
								<td>
									<div class="input-group">
										<ihtml:text style="width:100px" value="" property="mailSubClass" id="mailSubClass" maxlength="2" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_MALSUBCLS" /> 
										<div class="input-group-append">									
											<ihtml:nbutton styleClass="btn secondary btn-secondary btn-icon secondary"
												  id="subClassLOV" property="subClassLOV">
												  <i class="icon ico-expand" ></i>
											 </ihtml:nbutton>
										</div>
									</div>
								</td>
								<td>
									
									<ibusiness:releasetimer property="handoverTimes" styleClass="form-control" componentID="TXT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_HANDOVERTIME" id="handoverTimes"  type="asTimeComponent"  value="" style="width:70px"/>
								</td>
								<td><a id="delete" onclick="deleteRow2(this)"><i class="icon ico-del"></i></a></td>	 
                            </tr>
                        </tbody>
                    </table>
					</div>
</div>


