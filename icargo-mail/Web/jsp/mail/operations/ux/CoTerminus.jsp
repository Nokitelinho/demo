<%@ include file="/jsp/includes/ux/tlds.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import = "com.ibsplc.icargo.framework.util.time.LocalDate" %>
<%@ page import = "java.util.Collection" %>	
<%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.CoTerminusVO"%>
<%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.CoTerminusFilterVO"%>
<%@ page info="lite" %>

			
<head>
	<common:include type="script" src="/js/mail/operations/ux/MailPerformance_Script.jsp" />
</head>
<bean:define id="form"
		name="MailPerformanceForm" 
		type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.MailPerformanceForm" 
		toScope="page" />

		 
		 <business:sessionBean id="resditModeSession" 
         moduleName="mail.operations"
 		 screenID="mail.operations.ux.mailperformance" 
		 method="get" 
		 attribute="resditModes" />
		 
		 
		<business:sessionBean id="coTerminusVOs"
		 moduleName="mail.operations"
		 screenID="mail.operations.ux.mailperformance"
		 method="get"
		 attribute="coTerminusVOs"/>

  <div class="card-header card-header-action">
<div class="col">
                        <h4>Co-Terminus</h4>
                    </div>
                    <div class="card-header-btns">
                        <ihtml:nbutton id="btnCotAdd" styleClass="btn btn-primary mar-r-2xs"  property="btnAdd" componentID="BUT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_ADD" accesskey="A">
						<common:message key="mailtracking.defaults.ux.mailperformance.btn.add" />
						</ihtml:nbutton>
						
						 <ihtml:nbutton property="btnDelete" id="btnCotDelete" styleClass="btn btn-default" componentID="BUT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_DELETE" accesskey="D">
						<common:message key="mailtracking.defaults.ux.mailperformance.btn.delete" />
						</ihtml:nbutton>
                    </div>
                </div>
                <div class="card-body p-0 " id="coTerm">
				 <div id="dataTableContainer" class="dataTableContainer tablePanel" style="width:100%">
                    <table id="coTermTable" class="table table-x-md m-0" style="width:100%">
                        <thead>
                            <tr>
                                <th class="text-center check-box-cell"><input type="checkbox" onclick="updateHeaderCheckBox(this.form,this,this.form.coRowId);"></th>
                                <th><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.airportcodes" /></th>
                                <th><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.resditmodes"  /></th>
                                <th class="text-center" style="width:15%"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.recievedfromtruck" /></th>
                                <th style="width:3%"></th>
                            </tr>
                        </thead>
             
						<tbody id="coTerminusTableBody">
						
												<logic:present name="coTerminusVOs">
												<logic:iterate id ="coTerminusVO" name="coTerminusVOs" type="CoTerminusVO" indexId="rowCount">
												<tr>
												<logic:present name="coTerminusVO" property="coOperationFlag"> 
												<bean:define id="coOperationFlag" name="coTerminusVO" property="coOperationFlag" toScope="request" />
												<bean:define id="seqnum" name="coTerminusVO" property="seqnum" toScope="request" />
												<logic:notEqual name="coTerminusVO" property="coOperationFlag" value="D">
												<td class="text-center ">
													&nbsp;&nbsp; <ihtml:checkbox id="<%=String.valueOf(rowCount)%>" property="coRowId" value="<%=String.valueOf(rowCount)%>"/>
												</td>
												<td >
													<logic:equal name="coTerminusVO" property="coOperationFlag" value="I">
													
													 <div class="input-group">
															<bean:define id="coAirportCodes" name="coTerminusVO" property="coAirportCodes" toScope="request" />
														<ihtml:text property="coAirportCodes" value="<%=String.valueOf(coAirportCodes)%>" styleClass="form-control" componentID="TXT_MAILTRACKING_DEFAULTS_MAILPERFORMANCE_ARPCOD" style="width:130px" />
															<div class="input-group-append">									
																	 <ihtml:nbutton styleClass="btn secondary btn-secondary btn-icon secondary"
																	  id="coAirLOV" property="coAirLOV">
																	  <i class="icon ico-expand" ></i>
																	 </ihtml:nbutton>
																</div>
																</div>
													
													
														
													</logic:equal>
													<logic:notEqual name="coTerminusVO" property="coOperationFlag" value="I">
													<bean:define id="coAirportCodes" name="coTerminusVO" property="coAirportCodes" toScope="request" />
														<ihtml:text property="coAirportCodes" styleClass="form-control" value="<%=String.valueOf(coAirportCodes)%>" readonly="true" componentID="TXT_MAILTRACKING_DEFAULTS_MAILPERFORMANCE_ARPCOD" style="width:130px"/>
														
													</logic:notEqual>
												</td>
												<td > <logic:equal name="coTerminusVO" property="coOperationFlag" value="I">
												<bean:define id="resditModes" name="coTerminusVO" property="resditModes" toScope="request" />
												<ihtml:select property="resditModes" styleClass="form-control" style="width:200px" >
															<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
															<logic:present name="resditModeSession">
															<logic:iterate id="resditmodeVO" name="resditModeSession">
																<bean:define id="fieldValue" name="resditmodeVO" property="fieldValue" />
																<html:option value="<%=(String)fieldValue %>"><bean:write name="resditmodeVO" property="fieldDescription" /></html:option>
															</logic:iterate>
															</logic:present>
															</ihtml:select>
													
													</logic:equal>
													<logic:notEqual name="coTerminusVO" property="coOperationFlag" value="I">
													<logic:present name="coTerminusVO" property="resditModes">
						<bean:define id="resditModes" name="coTerminusVO" property="resditModes" toScope="page"/>

							<ihtml:select property="resditModes"   disabled="true" value="<%= String.valueOf(resditModes) %>" style="width:230px">
								<logic:present name="resditModeSession">
								<bean:define id="resditMode" name="resditModeSession" toScope="page"/>

									<logic:iterate id="onetmvo" name="resditMode">
										
										<bean:define id="value" name="onetmvo" property="fieldValue"/>
										<bean:define id="desc" name="onetmvo" property="fieldDescription"/>
										
										

										<html:option value="<%= value.toString() %>"><%= desc %></html:option>

									</logic:iterate>

								</logic:present>
							</ihtml:select>

						</logic:present>
													</logic:notEqual>
												</td>
												<td class="text-center ">
												<logic:equal name="coTerminusVO" property="coOperationFlag" value="I">
												<logic:equal name="coTerminusVO" property="truckFlag" value="Y">	
													<input type="checkbox" name="truckFlag"
								
								value="<%=String.valueOf(coTerminusVO.getTruckFlag())%>" 
								<logic:match name="coTerminusVO" property="truckFlag" value="Y">checked</logic:match> />
												</logic:equal>
												<logic:equal name="coTerminusVO" property="truckFlag" value="N">
													<ihtml:checkbox property="truckFlag"  value="" />
												</logic:equal>
												</logic:equal>
												<logic:notEqual name="coTerminusVO" property="coOperationFlag" value="I">
												<logic:equal name="coTerminusVO" property="truckFlag" value="Y">	
													<input type="checkbox" name="truckFlag" disabled="true"
								
								value="<%=String.valueOf(coTerminusVO.getTruckFlag())%>" 
								<logic:match name="coTerminusVO" property="truckFlag" value="Y">checked</logic:match> />
												</logic:equal>
												<logic:equal name="coTerminusVO" property="truckFlag" value="N">
													<ihtml:checkbox property="truckFlag"  disabled="true" value="" />
												</logic:equal>
												</logic:notEqual>
												</td>
												<td class="text-center">
														 <a  id="delete<%=rowCount%>" onclick="deleteRow(this)"><i class="icon ico-del"></i></a>
														 </td>
													</logic:notEqual>
												<logic:equal name="coTerminusVO" property="coOperationFlag" value="D">
													<ihtml:hidden property="coAirportCodes" name="coTerminusVO"/>
													<ihtml:hidden property="resditModes" name="coTerminusVO"/>
													<ihtml:hidden property="truckFlag" value="N" />
													
												</logic:equal>
												<ihtml:hidden property="seqnum"  value="<%=(String.valueOf(seqnum))%>" />
												<ihtml:hidden property="coOperationFlag" value="<%=((String)coOperationFlag)%>" />
												</logic:present>
												<logic:notPresent name="coTerminusVO" property="coOperationFlag">
												<bean:define id="seqnum" name="coTerminusVO" property="seqnum" toScope="request" />
												<td class="text-center">
												&nbsp;&nbsp; <ihtml:checkbox property="coRowId" id="<%=String.valueOf(rowCount)%>" value="<%=String.valueOf(rowCount)%>" disabled="false"/>
												</td>
												<td>
												<bean:define id="coAirportCodes" name="coTerminusVO" property="coAirportCodes" toScope="request" />
														<ihtml:text property="coAirportCodes" styleClass="form-control" value="<%=String.valueOf(coAirportCodes)%>" readonly="true" componentID="TXT_MAILTRACKING_DEFAULTS_MAILPERFORMANCE_ARPCOD" style="width:230px"/>
														
													
												</td>
												<td> 
												
						<logic:present name="coTerminusVO" property="resditModes">
						<bean:define id="resditModes" name="coTerminusVO" property="resditModes" toScope="page"/>

							<ihtml:select property="resditModes"   disabled="true" value="<%= String.valueOf(resditModes) %>" style="width:230px">
								<logic:present name="resditModeSession">
								<bean:define id="resditMode" name="resditModeSession" toScope="page"/>

									<logic:iterate id="onetmvo" name="resditMode">
										
										<bean:define id="value" name="onetmvo" property="fieldValue"/>
										<bean:define id="desc" name="onetmvo" property="fieldDescription"/>
										
										

										<html:option value="<%= value.toString() %>"><%= desc %></html:option>

									</logic:iterate>

								</logic:present>
							</ihtml:select>

						</logic:present>
															
															
													
												
												</td>
												<td class="text-center">
												
												<logic:equal name="coTerminusVO" property="truckFlag" value="Y">
<input type="checkbox" name="truckFlag"
								
								value="<%=String.valueOf(coTerminusVO.getTruckFlag())%>" disabled="true"
								<logic:match name="coTerminusVO" property="truckFlag" value="Y">checked</logic:match> />
													
												</logic:equal>
												<logic:equal name="coTerminusVO" property="truckFlag" value="N">
												<input type="checkbox" name="truckFlag"
								
								value="<%=String.valueOf(coTerminusVO.getTruckFlag())%>" disabled="true"
								<logic:match name="coTerminusVO" property="truckFlag" value="Y">not checked</logic:match> />
													
												</logic:equal>
												
												
												</td>
												<td class="text-center">
														 <a  id="delete<%=rowCount%>" onclick="deleteRow(this)"><i class="icon ico-del"></i></a>
														 </td>
												<ihtml:hidden property="coOperationFlag" value="N" />
												<ihtml:hidden property="seqnum"  value="<%=(String.valueOf(seqnum))%>" />
												</logic:notPresent>
												</tr>
												
												</logic:iterate>
												</logic:present>
												<tr  template="true" id="coTerminusTemplateRow" style="display:none">
												<ihtml:hidden property="coOperationFlag" value="NOOP" />
												<ihtml:hidden property="seqnum" value="" />
												
											
														 <td class="text-center">
															<input type="checkbox"  name="coRowId"/>
														 </td>
														 <td>
														 <div class="input-group">
														 <ihtml:text style="width:100px"  value="" maxlength="39" property="coAirportCodes" id="coAirportCodes" componentID="TXT_MAILTRACKING_DEFAULTS_MAILPERFORMANCE_ARPCOD" /> 
															<div class="input-group-append">									
											<ihtml:nbutton styleClass="btn secondary btn-secondary btn-icon secondary"
												  id="coAirLOV" property="coAirLOV">
												  <i class="icon ico-expand" ></i>
											 </ihtml:nbutton>
										</div>
															
																</div>
															
															
														 </td>
														 <td>
															<ihtml:select property="resditModes" styleClass="form-control" style="width:200px" >
															<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
															<logic:present name="resditModeSession">
															<logic:iterate id="resditmodeVO" name="resditModeSession">
																<bean:define id="fieldValue" name="resditmodeVO" property="fieldValue" />
																<html:option value="<%=(String)fieldValue %>"><bean:write name="resditmodeVO" property="fieldDescription" /></html:option>
															</logic:iterate>
															</logic:present>
															</ihtml:select>
														 </td>
														 <td  class="text-center">
															<ihtml:checkbox property="truckFlag"  value="N" />
														 </td>
														 <td class="text-center">
														 &nbsp;&nbsp; <a  id="delete" onclick="deleteRow(this)"><i class="icon ico-del"></i></a>
														 </td>
												 </tr>
												</tbody>
                    </table>
					</div>
                </div>