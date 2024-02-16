<!--ContractID!-->
<%@ include file="/jsp/includes/ux/tlds.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import = "com.ibsplc.icargo.framework.util.time.LocalDate" %>
<%@ page import = "java.util.Collection" %>
<%@ page info="lite" %>
<%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.GPAContractFilterVO"%>
<%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.GPAContractVO"%>
<head>
	<common:include type="script" src="/js/mail/operations/ux/MailPerformance_Script.jsp" />
</head>
<business:sessionBean id="gPAContractVOs"
		 moduleName="mail.operations"
		 screenID="mail.operations.ux.mailperformance"
		 method="get"
		 attribute="gPAContractVOs"/>
<bean:define id="form"
		name="MailPerformanceForm"
		type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.MailPerformanceForm"
		toScope="page" />
	<div class="card-header d-flex justify-content-end">
                    <div class="tool-bar align-items-center  pad-y-2sm">
						  <ihtml:nbutton id="btnContractAdd" styleClass="btn btn-primary mar-r-2xs"  property="btnContractAdd" 		componentID="BUT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_ADD" accesskey="A">
							<common:message key="mailtracking.defaults.ux.mailperformance.btn.add" />
							</ihtml:nbutton>
						 <ihtml:nbutton property="btnDelete" id="btnContractDelete" styleClass="btn btn-default" componentID="BUT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_DELETE" accesskey="D">
						<common:message key="mailtracking.defaults.ux.mailperformance.btn.delete" />
						</ihtml:nbutton>
					 </div>
             </div>
				 <div class="card-body p-0" id="contract">
				  <div id="dataTableContainer" class="dataTableContainer tablePanel" style="width:100%" >
                    <table class="table-x-md m-0 table" id="contractIdTable" style="width:100%">
                        <thead>
                            <tr>
                                <th class="text-center check-box-cell"><input type="checkbox" name="contractIDcheckAll" onclick="updateHeaderCheckBox(this.form,this,this.form.conRowId);"></th>
                                <th><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.origin" /><span class="mandatory">*</span></th>
                                <th><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.destination" /><span class="mandatory">*</span></th>
								<th><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.region" /><span class="mandatory">*</span></th>
                                <th><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.contractid" /><span class="mandatory">*</span></th>
                                <th><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.fromdate" /><span class="mandatory">*</span></th>
                                <th><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.todate" /><span class="mandatory">*</span></th>
								<th><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.amot" /></th>
                               <th style="width:3%"></th>
                            </tr>
                        </thead>
                        <tbody id="contractIdTableBody">
							<logic:present name="gPAContractVOs" >
								<logic:iterate id="GPAContractVO" name="gPAContractVOs" indexId="rowCount" >

									<logic:present name="GPAContractVO" property="conOperationFlags">
										<bean:define id="conOperationFlags" name="GPAContractVO" property="conOperationFlags" toScope="request" />

										<logic:notEqual name="GPAContractVO" property="conOperationFlags" value="D">
										<tr>
											<td class="text-center ">
												&nbsp;&nbsp; <ihtml:checkbox id="<%=String.valueOf(rowCount)%>" property="conRowId" value="<%=String.valueOf(rowCount)%>"/>
											</td>
											<td>
												<logic:equal name="GPAContractVO" property="conOperationFlags" value="I">
												<div class="input-group">
													<ihtml:text property="originAirports" name="GPAContractVO" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_ORIGIN" style="width:130px" styleClass="form-control" />
													<div class="input-group-append">
												 <ihtml:nbutton styleClass="btn secondary btn-secondary btn-icon secondary"
												  property="conOrgairLOV">
												  <i class="icon ico-expand" ></i>
												 </ihtml:nbutton>
											</div>
											</div>
												</logic:equal>
												<logic:notEqual name="GPAContractVO" property="conOperationFlags" value="I">
													<ihtml:text property="originAirports" name="GPAContractVO" readonly="true" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_ORIGIN" style="width:230px" styleClass="form-control" />
												</logic:notEqual>
											</td>
											<td>
												<logic:equal name="GPAContractVO" property="conOperationFlags" value="I">
												<div class="input-group">
													<ihtml:text property="destinationAirports" name="GPAContractVO" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_DESTINATION" style="width:130px" styleClass="form-control" />
													<div class="input-group-append">
												 <ihtml:nbutton styleClass="btn secondary btn-secondary btn-icon secondary"
												   property="conOrgairLOV">
												  <i class="icon ico-expand" ></i>
												 </ihtml:nbutton>
											</div>
											</div>
												</logic:equal>
												<logic:notEqual name="GPAContractVO" property="conOperationFlags" value="I">
													<ihtml:text property="destinationAirports" name="GPAContractVO" readonly="true" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_DESTINATION" style="width:230px" styleClass="form-control" />
												</logic:notEqual>
											</td>
											<td>
												<logic:equal name="GPAContractVO" property="conOperationFlags" value="I">
													<ihtml:text property="regions" name="GPAContractVO" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_REGION" style="width:130px" maxlength="5" styleClass="form-control" />
												</logic:equal>
												<logic:notEqual name="GPAContractVO" property="conOperationFlags" value="I">
													<ihtml:text property="regions" name="GPAContractVO" readonly="true" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_REGION" style="width:130px" maxlength="5" styleClass="form-control" />
												</logic:notEqual>
											</td>
											<td>
												<logic:equal name="GPAContractVO" property="conOperationFlags" value="I">
													<ihtml:text property="contractIDs" name="GPAContractVO" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_CONTRACTID" style="width:130px" maxlength="20" styleClass="form-control" />

												</logic:equal>
												<logic:notEqual name="GPAContractVO" property="conOperationFlags" value="I">
													<ihtml:text property="contractIDs" name="GPAContractVO" readonly="true" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_CONTRACTID" style="width:230px" maxlength="20" styleClass="form-control" />
												</logic:notEqual>
											</td>
											<td>
												<logic:equal name="GPAContractVO" property="conOperationFlags" value="I">

													<ibusiness:litecalendar  name="GPAContractVO"
													styleClass="form-control"  id="cidFromDates" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_VALIDFRM"
													property="cidFromDates" />
												</logic:equal>
												<logic:notEqual name="GPAContractVO" property="conOperationFlags" value="I">
													<ihtml:text property="cidFromDates" name="GPAContractVO" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_VALIDFRM" style="width:130px" styleClass="form-control"  />
												</logic:notEqual>
											</td>
											<td>
												<logic:equal name="GPAContractVO" property="conOperationFlags" value="I">

													<ibusiness:litecalendar name="GPAContractVO"
													styleClass="form-control"  id="cidToDates" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_VALIDTO"
													property="cidToDates" />
												</logic:equal>
												<logic:notEqual name="GPAContractVO" property="conOperationFlags" value="I">
													<ihtml:text property="cidToDates" name="GPAContractVO" readonly="true" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_VALIDTO" style="width:230px" />
												</logic:notEqual>
											</td>
											<td class="text-center" style="text-align:center;width:4%">
												<logic:equal name="GPAContractVO" property="amot" value="Y">
													<input type="checkbox" name="amot"  value="Y" checked="true"/>
												</logic:equal>
												<logic:notEqual name="GPAContractVO" property="amot"
												value="Y">
													<input type="checkbox" name="amot"  value="N"/>
												</logic:notEqual>
											</td>
											<td class="text-center">
												<a  id="delete<%=rowCount%>" onclick="deleteContactRow(this)"><i class="icon ico-del"></i></a>
											</td>
											<ihtml:hidden property="conOperationFlags" value="<%=((String)conOperationFlags)%>" />
											</tr>
										</logic:notEqual>
										<logic:equal name="GPAContractVO" property="conOperationFlags" value="D">
										<tr style="display:none;">
										<td>
											<ihtml:hidden property="originAirports" name="GPAContractVO"/>
											</td>
											<td>
											<ihtml:hidden property="destinationAirports" name="GPAContractVO"/>
											</td>
											<td>
											<ihtml:hidden property="regions" name="GPAContractVO"/>
											</td>
											<td>
											<ihtml:hidden property="contractIDs" name="GPAContractVO"/>
											</td>
											<td>
											<ihtml:hidden property="cidFromDates" name="GPAContractVO"/>
											</td>
											<td>
											<ihtml:hidden property="cidToDates" name="GPAContractVO"/>
											</td>
											<td>
											<ihtml:hidden property="amot" name="GPAContractVO"/>
											</td>
											<td>

											</td>
											<td>

											</td>
										<ihtml:hidden property="conOperationFlags" value="<%=((String)conOperationFlags)%>" />
										</tr>
										</logic:equal>

									</logic:present>
									<logic:notPresent name="GPAContractVO" property="conOperationFlags">
									<tr>
										<td class="text-center ">
												&nbsp;&nbsp; <ihtml:checkbox id="<%=String.valueOf(rowCount)%>" property="conRowId" value="<%=String.valueOf(rowCount)%>"/>
										</td>
										<td>
											<ihtml:text property="originAirports" name="GPAContractVO" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_ORIGIN" style="width:130px" readonly="true" styleClass="form-control" />
										</td>
										<td>
											<ihtml:text property="destinationAirports" name="GPAContractVO" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_DESTINATION" style="width:130px" readonly="true" styleClass="form-control" />
										</td>
										<td>
											<ihtml:text property="regions" name="GPAContractVO" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_REGION" style="width:130px" readonly="true" styleClass="form-control" />
										</td>
										<td>
											<ihtml:text property="contractIDs" name="GPAContractVO" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_CONTRACTID" style="width:130px" readonly="true" styleClass="form-control" />
										</td>
										<td>
											<ihtml:text property="cidFromDates" name="GPAContractVO" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_VALIDFRM" style="width:130px" readonly="true" styleClass="form-control" />
											<!-- <ibusiness:litecalendar tabindex="5"
														styleClass="form-control"  id="cidFromDates" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_VALIDTO"
														property="cidFromDates" readonly="true"  /> -->
										</td>
										<td>
											<ihtml:text property="cidToDates" name="GPAContractVO" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_VALIDTO" style="width:130px" readonly="true" styleClass="form-control" />
										</td>
										<td class="text-center" style="text-align:center">
										    <logic:equal name="GPAContractVO" property="paCode" value="US001">
										    <logic:equal name="GPAContractVO" property="amot" value="Y" >
											    <input type="checkbox" name="amot"  value="Y" checked="true"  />
											</logic:equal>
											<logic:notEqual name="GPAContractVO" property="amot" value="Y">
												<input type="checkbox" name="amot"  value="N"  />
											</logic:notEqual>
                                            </logic:equal>	
											<logic:notEqual name="GPAContractVO" property="paCode" value="US001">
										    <logic:equal name="GPAContractVO" property="amot" value="Y" >
											    <input type="checkbox" name="amot"  value="Y" checked="true" readonly="true" />
											</logic:equal>
											<logic:notEqual name="GPAContractVO" property="amot" value="Y">
												<input type="checkbox" name="amot"  value="N"  readonly="true" />
											</logic:notEqual>
                                            </logic:notEqual>	
										</td>
										<td class="text-center">
											<a  id="delete<%=rowCount%>" onclick="deleteContactRow(this)"><i class="icon ico-del"></i></a>
										</td>
										<ihtml:hidden property="conOperationFlags" value="N" />
										</tr>
									</logic:notPresent>
								</logic:iterate>
							</logic:present >
							<tr  template="true" id="contractIdTemplateRow" style="display:none">
								<ihtml:hidden property="conOperationFlags" value="NOOP" />
									<td class="text-center">
										<input type="checkbox"  name="conRowId"/>
									</td>
									<td>
										<div class="input-group">
											<ihtml:text tabindex="4" value="" property="originAirports" id="originAirports" styleClass="form-control" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_ORIGIN" maxlength="3"/>
											<div class="input-group-append">
												 <ihtml:nbutton styleClass="btn secondary btn-secondary btn-icon secondary"
												  id="conOrgairLOV" property="conOrgairLOV">
												  <i class="icon ico-expand" ></i>
												 </ihtml:nbutton>
											</div>
										</div>
									 </td>
									 <td>
										<div class="input-group">
											<ihtml:text tabindex="4" value="" property="destinationAirports" id="destinationAirports" styleClass="form-control" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_DESTINATION" maxlength="3"/>
											<div class="input-group-append">
												 <ihtml:nbutton styleClass="btn secondary btn-secondary btn-icon secondary"
												  id="conDestairLOV" property="conDestairLOV">
												  <i class="icon ico-expand" ></i>
												 </ihtml:nbutton>
											</div>
										</div>
									 </td>
									 <td>
										<ihtml:text tabindex="4" value="" property="regions" id="regions" styleClass="form-control" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_REGION" maxlength="5"/>
									 </td>
									<td>
										<ihtml:text tabindex="4" value="" property="contractIDs" id="contractIDs" styleClass="form-control" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_CONTRACTID" maxlength="20"/>
									</td>
									<td>
										<ibusiness:litecalendar tabindex="4"
										styleClass="form-control"  id="cidFromDates" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_VALIDTO"
										property="cidFromDates" value="" />
									</td>
									<td>
										<ibusiness:litecalendar   tabindex="4"
										styleClass="form-control"  id="cidToDates" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_VALIDTO"
										property="cidToDates" value="" />
									</td>
									<td class="text-center" style="text-align:center">
									<logic:equal name="form" property="paCodeValue" value="US001">
									    <ihtml:checkbox property="amot"
									    componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_AMOT"/>
									 </logic:equal>	
									<logic:notEqual name="form" property="paCodeValue" value="US001">
									    <ihtml:checkbox property="amot" disabled="true"
									    componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_AMOT"/>
									 </logic:notEqual>	
								    </td>
									 <td class="text-center">
									 &nbsp;&nbsp; <a  id="delete" onclick="deleteContactRow(this)"><i class="icon ico-del"></i></a>
									 </td>
							</tr>
                        </tbody>
                    </table>
                </div>
				</div>