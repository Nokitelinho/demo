<%@ include file="/jsp/includes/ux/tlds.jsp" %>
<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>
<%@ page language="java" %>
<%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.USPSPostalCalendarVO"%>
<%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.USPSPostalCalendarFilterVO"%>
<%@ page info="lite" %>
<ihtml:form action="/mail.operations.ux.mailperformance.screenload.do" >
<bean:define id="form"
		name="MailPerformanceForm"
		type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.MailPerformanceForm"
		toScope="page" />
<business:sessionBean id="uSPSPostalCalendarVOs"
		 moduleName="mail.operations"
		 screenID="mail.operations.ux.mailperformance"
		 method="get"
		 attribute="uSPSPostalCalendarVOs"/>
		 <logic:present name="uSPSPostalCalendarVOs">
		<bean:define id="uSPSPostalCalendarVOs"
			name="uSPSPostalCalendarVOs" toScope="page" />
		</logic:present>
<div class="card-body p-0" id="ajax_div">
	<ihtml:hidden name="form" property="screenFlag" />
	<ihtml:hidden name="form" property="statusFlag" />
			<div id="dataTableContainer" class="dataTableContainer tablePanel" style="width:100%">
                    <table id="invoicTable" class="table table-x-md m-0" style="width:100%">
                        <thead>
                            <tr >
                                <th class="text-center check-box-cell "><input type="checkbox" name="calCheckAll" onclick="updateHeaderCheckBox(this.form,this,this.form.calRowIds);"</th>
                                <th style="width:80px" ><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.period" /></th>
                                <th style="width:12%"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.fromdate" /></th>
                                <th style="width:12%"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.todate" /></th>
                                <th style="width:12%"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.paymeffectivedates" /></th>
                                <th style="width:12%"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.inccalcDates" /></th>
                                <th style="width:12%"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.inceffectivedates" /></th>
                                <th style="width:12%"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.increcvdates" /></th>
								<th style="width:12%"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.claimgendates" /></th>
                                <th style="width:3%"></th>
                            </tr>
                        </thead>
                        <tbody id="cgrTableBody">
								<logic:present name="uSPSPostalCalendarVOs">
								<logic:iterate id ="uSPSPostalCalendarVO" name="uSPSPostalCalendarVOs" type="USPSPostalCalendarVO" indexId="rowCount">
								<tr >
								<logic:present name="uSPSPostalCalendarVO" property="calOperationFlags">
								<bean:define id="calOperationFlags" name="uSPSPostalCalendarVO" property="calOperationFlags" toScope="request" />
								<bean:define id="calSeqnum" name="uSPSPostalCalendarVO" property="calSeqnum" toScope="request" />
								<logic:notEqual name="uSPSPostalCalendarVO" property="calOperationFlags" value="D">
                                <td class="text-center">
									&nbsp;&nbsp; <ihtml:checkbox id="<%=String.valueOf(rowCount)%>" property="calRowIds" value="<%=String.valueOf(rowCount)%>"/>
                                </td>
								<td>
									<logic:equal name="uSPSPostalCalendarVO" property="calOperationFlags" value="I">
										<ihtml:text property="periods" name="uSPSPostalCalendarVO" styleClass="form-control"  style="width:100px" readonly="true"/>
									</logic:equal>
									<logic:notEqual name="uSPSPostalCalendarVO" property="calOperationFlags" value="I">
										<logic:equal name="uSPSPostalCalendarVO" property="calOperationFlags" value="U">
											<bean:write property="periods" name="uSPSPostalCalendarVO"/>
										</logic:equal>
										<logic:notEqual name="uSPSPostalCalendarVO" property="calOperationFlags" value="U">
										<ihtml:text property="periods" styleClass="form-control" name="uSPSPostalCalendarVO" readonly="true"  style="width:100px"/>
									</logic:notEqual>
									</logic:notEqual>
								</td>
                                <td>
								<logic:equal name="uSPSPostalCalendarVO" property="calOperationFlags" value="I">
										<ihtml:text property="fromDates" name="uSPSPostalCalendarVO" styleClass="form-control"  style="width:100px" readonly="true"/>
									</logic:equal>
									<logic:notEqual name="uSPSPostalCalendarVO" property="calOperationFlags" value="I">
										<logic:equal name="uSPSPostalCalendarVO" property="calOperationFlags" value="U">
											<bean:write property="fromDates"  name="uSPSPostalCalendarVO"  />
										</logic:equal>
										<logic:notEqual name="uSPSPostalCalendarVO" property="calOperationFlags" value="U">
										<ihtml:text property="fromDates" styleClass="form-control" name="uSPSPostalCalendarVO" readonly="true"  style="width:100px"/>
									</logic:notEqual>
									</logic:notEqual>
									</td>
                                <td>
								<logic:equal name="uSPSPostalCalendarVO" property="calOperationFlags" value="I">
										<ihtml:text property="toDates" name="uSPSPostalCalendarVO" styleClass="form-control"  style="width:100px" readonly="true"/>
									</logic:equal>
									<logic:notEqual name="uSPSPostalCalendarVO" property="calOperationFlags" value="I">
										<logic:equal name="uSPSPostalCalendarVO" property="calOperationFlags" value="U">
											<bean:write property="toDates"  name="uSPSPostalCalendarVO"  />
										</logic:equal>
										<logic:notEqual name="uSPSPostalCalendarVO" property="calOperationFlags" value="U">
										<ihtml:text property="toDates" styleClass="form-control" name="uSPSPostalCalendarVO" readonly="true"  style="width:100px"/>
									</logic:notEqual>
									</logic:notEqual>
								</td>
                                <td>
									<logic:equal name="uSPSPostalCalendarVO" property="calOperationFlags" value="I">
										<ihtml:text property="paymEffectiveDates" name="uSPSPostalCalendarVO" styleClass="form-control"  style="width:100px" readonly="true"/>
									</logic:equal>
									<logic:notEqual name="uSPSPostalCalendarVO" property="calOperationFlags" value="I">
										<logic:equal name="uSPSPostalCalendarVO" property="calOperationFlags" value="U">
											<bean:write property="paymEffectiveDates"  name="uSPSPostalCalendarVO" />
										</logic:equal>
										<logic:notEqual name="uSPSPostalCalendarVO" property="calOperationFlags" value="U">
										<ihtml:text property="paymEffectiveDates" styleClass="form-control" name="uSPSPostalCalendarVO" readonly="true"  style="width:100px"/>
									</logic:notEqual>
									</logic:notEqual>
								</td>
                                <td>
									<logic:equal name="uSPSPostalCalendarVO" property="calOperationFlags" value="I">
										<ihtml:text property="incCalcDates" name="uSPSPostalCalendarVO" styleClass="form-control"  style="width:100px" readonly="true"/>
									</logic:equal>
									<logic:notEqual name="uSPSPostalCalendarVO" property="calOperationFlags" value="I">
										<logic:equal name="uSPSPostalCalendarVO" property="calOperationFlags" value="U">
											<bean:write property="incCalcDates"  name="uSPSPostalCalendarVO" />
										</logic:equal>
										<logic:notEqual name="uSPSPostalCalendarVO" property="calOperationFlags" value="U">
										<ihtml:text property="incCalcDates" styleClass="form-control" name="uSPSPostalCalendarVO" readonly="true"  style="width:100px"/>
									</logic:notEqual>
									</logic:notEqual>
								</td>
                                <td>
								<logic:equal name="uSPSPostalCalendarVO" property="calOperationFlags" value="I">
										<ihtml:text property="incEffectiveDates" name="uSPSPostalCalendarVO" styleClass="form-control"  style="width:100px" readonly="true"/>
									</logic:equal>
									<logic:notEqual name="uSPSPostalCalendarVO" property="calOperationFlags" value="I">
										<logic:equal name="uSPSPostalCalendarVO" property="calOperationFlags" value="U">
											<bean:write property="incEffectiveDates"  name="uSPSPostalCalendarVO"  />
										</logic:equal>
										<logic:notEqual name="uSPSPostalCalendarVO" property="calOperationFlags" value="U">
										<ihtml:text property="incEffectiveDates" styleClass="form-control" name="uSPSPostalCalendarVO" readonly="true"  style="width:100px"/>
									</logic:notEqual>
									</logic:notEqual>
								</td>
                                <td>
								<logic:equal name="uSPSPostalCalendarVO" property="calOperationFlags" value="I">
										<ihtml:text property="incRecvDates" name="uSPSPostalCalendarVO" styleClass="form-control"  style="width:100px" readonly="true"/>
									</logic:equal>
									<logic:notEqual name="uSPSPostalCalendarVO" property="calOperationFlags" value="I">
										<logic:equal name="uSPSPostalCalendarVO" property="calOperationFlags" value="U">
											<bean:write property="incRecvDates"  name="uSPSPostalCalendarVO"  />
										</logic:equal>
										<logic:notEqual name="uSPSPostalCalendarVO" property="calOperationFlags" value="U">
										<ihtml:text property="incRecvDates" styleClass="form-control" name="uSPSPostalCalendarVO" readonly="true"  style="width:100px"/>
									</logic:notEqual>
									</logic:notEqual>
								</td>
								 <td>
								<logic:equal name="uSPSPostalCalendarVO" property="calOperationFlags" value="I">
										<ihtml:text property="clmGenDate" name="uSPSPostalCalendarVO" styleClass="form-control"  style="width:100px" readonly="true"/>
									</logic:equal>
									<logic:notEqual name="uSPSPostalCalendarVO" property="calOperationFlags" value="I">
										<logic:equal name="uSPSPostalCalendarVO" property="calOperationFlags" value="U">
											<bean:write property="clmGenDate"  name="uSPSPostalCalendarVO"  />
										</logic:equal>
										<logic:notEqual name="uSPSPostalCalendarVO" property="calOperationFlags" value="U">
										<ihtml:text property="clmGenDate" styleClass="form-control" name="uSPSPostalCalendarVO" readonly="true"  style="width:100px"/>
									</logic:notEqual>
									</logic:notEqual>
								</td>
<!--<td><a  id="delete<%=rowCount%>" onclick="deleteRow1(this)"><i class="icon ico-del"></i></a></td>-->
                                <td class="more-options valign-middle text-center toggle-cell">
									<div class="dropdown" id="el">
										 <a class="dropdown-toggle" href="#" id="pop" onclick="pop(this)"><i class ="icon ico-v-ellipsis align-middle"></i></a>
										 <div class="dropdown-box">
											<div class="dropdown-menu">
												<a class="dropdown-item poptrigger" href="#" id="edit<%=rowCount%>" onclick="editPostalCalendar(this)">Edit</a>
												 <a class="dropdown-item poptrigger" href="#" id="delete<%=rowCount%>" onclick="deleteRow1(this)">Delete</a>
											</div>
										 </div>
									</div>
										 </td>
								</logic:notEqual>
								<logic:equal name="uSPSPostalCalendarVO" property="calOperationFlags" value="D">
							<ihtml:hidden property="periods" name="uSPSPostalCalendarVO"/>
							<ihtml:hidden property="fromDates" name="uSPSPostalCalendarVO"/>
							<ihtml:hidden property="toDates" name="uSPSPostalCalendarVO"/>
							<ihtml:hidden property="paymEffectiveDates" name="uSPSPostalCalendarVO"/>
							<ihtml:hidden property="incCalcDates" name="uSPSPostalCalendarVO"/>
							<ihtml:hidden property="incEffectiveDates" name="uSPSPostalCalendarVO"/>
							<ihtml:hidden property="incRecvDates" name="uSPSPostalCalendarVO"/>
							<ihtml:hidden property="clmGenDate" name="uSPSPostalCalendarVO"/>
							</logic:equal>
							<ihtml:hidden property="calSeqnum"  value="<%=(String.valueOf(calSeqnum))%>" />
							<ihtml:hidden property="calOperationFlags" value="<%=((String)calOperationFlags)%>" />
								</logic:present>
								<logic:notPresent name="uSPSPostalCalendarVO" property="calOperationFlags">
								<bean:define id="calSeqnum" name="uSPSPostalCalendarVO" property="calSeqnum" toScope="request" />
							<td class="text-center">
													&nbsp;&nbsp; <ihtml:checkbox property="calRowIds" id="<%=String.valueOf(rowCount)%>" value="<%=String.valueOf(rowCount)%>"/>
												</td>
							<td>
													<bean:write property="periods" name="uSPSPostalCalendarVO"/>
												</td>
								<td>
									<bean:write property="fromDates"  name="uSPSPostalCalendarVO"  />
								</td>
								<td>
													<bean:write property="toDates"  name="uSPSPostalCalendarVO"  />
												</td>
												<td>
													<bean:write property="paymEffectiveDates"  name="uSPSPostalCalendarVO" />
												</td>
												<td>
													<bean:write property="incCalcDates"  name="uSPSPostalCalendarVO" />
												</td>
												<td>
													<bean:write property="incEffectiveDates"  name="uSPSPostalCalendarVO"  />
												</td>
												<td>
													<bean:write property="incRecvDates"  name="uSPSPostalCalendarVO"  />
												</td>
												<td>
													<bean:write property="clmGenDate"  name="uSPSPostalCalendarVO"  />
												</td>
<!--<td class="text-center">
														 <a  id="delete<%=rowCount%>" onclick="deleteRow1(this)"><i class="icon ico-del"></i></a>
														 </td>-->
												<td class="more-options valign-middle text-center toggle-cell">
													<div class="dropdown" id="el">
														 <a class="dropdown-toggle" href="#" id="pop" onclick="pop(this)"><i class ="icon ico-v-ellipsis align-middle"></i></a>
														 <div class="dropdown-box">
															<div class="dropdown-menu">
																 <a class="dropdown-item poptrigger" href="#" id="edit<%=rowCount%>" onclick="editPostalCalendar(this)">Edit</a>
																 <a class="dropdown-item poptrigger" href="#" id="delete<%=rowCount%>"  onclick="deleteRow1(this)">Delete</a>
															</div>
														 </div>
													</div>
														 </td>
											<ihtml:hidden property="calOperationFlags" value="N" />
												<ihtml:hidden property="calSeqnum"  value="<%=(String.valueOf(calSeqnum))%>" />
												</logic:notPresent>
						  </tr>
						   </logic:iterate>
							</logic:present>
                           <tr  template="true" id="cgrTemplateRow" style="display:none">
						   <ihtml:hidden property="calOperationFlags" value="NOOP" />
												<ihtml:hidden property="calSeqnum" value="" />
												<td class="text-center">
															<input type="checkbox"  name="calRowIds"/>
														 </td>
							<td>
													<ihtml:text property="periods" tabindex="5" styleClass="form-control" value="" style="width:100%" />
												</td>
							<td>
													<ibusiness:litecalendar tabindex="5" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_CAL"
										labelStyleClass="form-control-label" id="fromDates"   style="width:50px"
										property="fromDates"  indexId="index"  value="" />
												</td>
												<td>
												<ibusiness:litecalendar tabindex="5"
										labelStyleClass="form-control-label" id="toDates"  componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_CAL" style="width:50px"
										property="toDates"  indexId="index"  value="" />
												</td>
							<td>
							<ibusiness:litecalendar tabindex="5"
										labelStyleClass="form-control-label" id="paymEffectiveDates" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_CAL"  style="width:50px"
										property="paymEffectiveDates"  value="" />
												</td>
							<td>
							<ibusiness:litecalendar tabindex="5"
										labelStyleClass="form-control-label" id="incCalcDates" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_CAL" style="width:50px"
										property="incCalcDates"  value="" />
												</td>
							<td>
							<ibusiness:litecalendar tabindex="5"
										labelStyleClass="form-control-label" id="incEffectiveDates"  componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_CAL" style="width:50px"
										property="incEffectiveDates"  value="" />
												</td>
							<td>
							<ibusiness:litecalendar tabindex="5"
										labelStyleClass="form-control-label" id="incRecvDates"  componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_CAL" style="width:50px"
										property="incRecvDates"   value="" />
												</td>
							<!--//Added by A-8527 for ICRD-262451 Starts-->
							<td>
							<ibusiness:litecalendar tabindex="5"
										labelStyleClass="form-control-label" id="clmGenDate"  componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_CAL" style="width:50px"
										property="clmGenDate"   value="" />
												</td>
							<!--//Added by A-8527 for ICRD-262451 Ends-->
<!--<td class="text-center">
														&nbsp;&nbsp; <a  id="delete" onclick="deleteRow1(this)"><i class="icon ico-del"></i></a>
														 </td>-->
												<td class="more-options valign-middle text-center toggle-cell">
													<div class="dropdown" id="el">
														 <a class="dropdown-toggle" href="#" id="pop" onclick="pop(this)"><i class ="icon ico-v-ellipsis align-middle"></i></a>
														 <div class="dropdown-box">
															<div class="dropdown-menu">
																 <a class="dropdown-item poptrigger" href="#" id="edit" onclick="editPostalCalendar(this)">Edit</a>
																 <a class="dropdown-item poptrigger" href="#" id="delete" onclick="deleteRow1(this)">Delete</a>
															</div>
														 </div>
													</div>
														 </td>
										</tr>
                        </tbody>
                    </table>
					 </div>
                </div>
				<div class="card-body p-0" id="ajax_div1">
				<ihtml:hidden name="form" property="screenFlag" />
	<ihtml:hidden name="form" property="statusFlag" />
				<div id="dataTableContainer" class="dataTableContainer tablePanel" style="width:100%">
                    <table id="cgrTable" class="table table-x-md m-0" style="width:100%">
                        <thead>
                            <tr>
                                <th class="text-center check-box-cell"><input type="checkbox" name="calCheckAll" onclick="updateHeaderCheckBox(this.form,this,this.form.calRowIds);"</th>
                                <th style="width:80px"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.period" /></th><!--Modified as part of ICRD-294731-->
                                <th style="width:10%"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.fromdate" /></th>	<!--Modified as part of ICRD-294731-->
                                <th style="width:10%"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.todate" /></th>	<!--Modified as part of ICRD-294731-->
                                <th style="width:11%"> <common:message  key="mailtracking.defaults.ux.mailperformance.lbl.cgrsubmission" /></th><!--Modified as part of ICRD-294731-->
                                <th style="width:12%"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.cgrdeletecutoff" /></th><!--Modified as part of ICRD-294731-->
                                <th style="width:11%"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.cutoffweek1" /></th><!--Modified as part of ICRD-294731-->
                                <th style="width:11%"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.cutoffweek2" /></th><!--Modified as part of ICRD-294731-->
                                <th style="width:11%"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.cutoffweek3" /></th><!--Modified as part of ICRD-294731-->
                                <th style="width:11%"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.cutoffweek4" /></th><!--Modified as part of ICRD-294731-->
                                <th style="width:11%"><common:message  key="mailtracking.defaults.ux.mailperformance.lbl.cutoffweek5" /></th><!--Modified as part of ICRD-294731-->
                                <th style="width:3%"></th>
                            </tr>
                        </thead>
                        <tbody id="cgrTableBody">
								<logic:present name="uSPSPostalCalendarVOs">
								<logic:iterate id ="uSPSPostalCalendarVO" name="uSPSPostalCalendarVOs" type="USPSPostalCalendarVO" indexId="rowCount">
								<tr >
								<logic:present name="uSPSPostalCalendarVO" property="calOperationFlags">
								<bean:define id="calOperationFlags" name="uSPSPostalCalendarVO" property="calOperationFlags" toScope="request" />
								<bean:define id="calSeqnum" name="uSPSPostalCalendarVO" property="calSeqnum" toScope="request" />
								<logic:notEqual name="uSPSPostalCalendarVO" property="calOperationFlags" value="D">
                                <td class="text-center">
									&nbsp;&nbsp; <ihtml:checkbox id="<%=String.valueOf(rowCount)%>" property="calRowIds" value="<%=String.valueOf(rowCount)%>"/>
                                </td>
								<td>
									<logic:equal name="uSPSPostalCalendarVO" property="calOperationFlags" value="I">
										<ihtml:text property="periods" name="uSPSPostalCalendarVO" styleClass="form-control"  style="width:100%" readonly="true"/>
									</logic:equal>
									<logic:notEqual name="uSPSPostalCalendarVO" property="calOperationFlags" value="I">
										<ihtml:text property="periods" styleClass="form-control" name="uSPSPostalCalendarVO" readonly="true"  style="width:100px"/>
									</logic:notEqual>
								</td>
                                <td>
								<logic:equal name="uSPSPostalCalendarVO" property="calOperationFlags" value="I">
										<ihtml:text property="fromDates" name="uSPSPostalCalendarVO" styleClass="form-control"  style="width:100px" readonly="true"/>
									</logic:equal>
									<logic:notEqual name="uSPSPostalCalendarVO" property="calOperationFlags" value="I">
										<ihtml:text property="fromDates" styleClass="form-control" name="uSPSPostalCalendarVO" readonly="true"  style="width:100px"/>
									</logic:notEqual>
									</td>
                                <td>
								<logic:equal name="uSPSPostalCalendarVO" property="calOperationFlags" value="I">
										<ihtml:text property="toDates" name="uSPSPostalCalendarVO" styleClass="form-control"  style="width:100px" readonly="true"/>
									</logic:equal>
									<logic:notEqual name="uSPSPostalCalendarVO" property="calOperationFlags" value="I">
										<ihtml:text property="toDates" styleClass="form-control" name="uSPSPostalCalendarVO" readonly="true"  style="width:100px"/>
									</logic:notEqual>
								</td>
                                <td>
									<logic:equal name="uSPSPostalCalendarVO" property="calOperationFlags" value="I">
										<ihtml:text property="cgrSubmissions" name="uSPSPostalCalendarVO" styleClass="form-control"  style="width:100px" readonly="true"/>
									</logic:equal>
									<logic:notEqual name="uSPSPostalCalendarVO" property="calOperationFlags" value="I">
										<ihtml:text property="cgrSubmissions" styleClass="form-control" name="uSPSPostalCalendarVO" readonly="true"  style="width:100px"/>
									</logic:notEqual>
								</td>
                                <td>
									<logic:equal name="uSPSPostalCalendarVO" property="calOperationFlags" value="I">
										<ihtml:text property="cgrDeleteCutOffs" name="uSPSPostalCalendarVO" styleClass="form-control"  style="width:100px" readonly="true"/>
									</logic:equal>
									<logic:notEqual name="uSPSPostalCalendarVO" property="calOperationFlags" value="I">
										<ihtml:text property="cgrDeleteCutOffs" styleClass="form-control" name="uSPSPostalCalendarVO" readonly="true"  style="width:100px"/>
									</logic:notEqual>
								</td>
                                <td>
								<logic:equal name="uSPSPostalCalendarVO" property="calOperationFlags" value="I">
										<ihtml:text property="cutWeek1s" name="uSPSPostalCalendarVO" styleClass="form-control"  style="width:100px" readonly="true"/>
									</logic:equal>
									<logic:notEqual name="uSPSPostalCalendarVO" property="calOperationFlags" value="I">
										<ihtml:text property="cutWeek1s" styleClass="form-control" name="uSPSPostalCalendarVO" readonly="true"  style="width:100px"/>
									</logic:notEqual>
								</td>
                                <td>
								<logic:equal name="uSPSPostalCalendarVO" property="calOperationFlags" value="I">
										<ihtml:text property="cutWeek2s" name="uSPSPostalCalendarVO" styleClass="form-control"  style="width:100px" readonly="true"/>
									</logic:equal>
									<logic:notEqual name="uSPSPostalCalendarVO" property="calOperationFlags" value="I">
										<ihtml:text property="cutWeek2s" styleClass="form-control" name="uSPSPostalCalendarVO" readonly="true"  style="width:100px"/>
									</logic:notEqual>
								</td>
                                <td>
								<logic:equal name="uSPSPostalCalendarVO" property="calOperationFlags" value="I">
										<ihtml:text property="cutWeek3s" name="uSPSPostalCalendarVO" styleClass="form-control"  style="width:100px" readonly="true"/>
									</logic:equal>
									<logic:notEqual name="uSPSPostalCalendarVO" property="calOperationFlags" value="I">
										<ihtml:text property="cutWeek3s" styleClass="form-control" name="uSPSPostalCalendarVO" readonly="true"  style="width:100px"/>
									</logic:notEqual>
								</td>
                                <td>
								<logic:equal name="uSPSPostalCalendarVO" property="calOperationFlags" value="I">
										<ihtml:text property="cutWeek4s" name="uSPSPostalCalendarVO" styleClass="form-control"  style="width:100px" readonly="true"/>
									</logic:equal>
									<logic:notEqual name="uSPSPostalCalendarVO" property="calOperationFlags" value="I">
										<ihtml:text property="cutWeek4s" styleClass="form-control" name="uSPSPostalCalendarVO" readonly="true"  style="width:100px"/>
									</logic:notEqual>
								</td>
                                <td>
								<logic:equal name="uSPSPostalCalendarVO" property="calOperationFlags" value="I">
										<ihtml:text property="cutWeek5s" name="uSPSPostalCalendarVO" styleClass="form-control"  style="width:100px" readonly="true"/>
									</logic:equal>
									<logic:notEqual name="uSPSPostalCalendarVO" property="calOperationFlags" value="I">
										<ihtml:text property="cutWeek5s" styleClass="form-control" name="uSPSPostalCalendarVO" readonly="true"  style="width:100px"/>
									</logic:notEqual>
								</td>
                                <td><a  id="delete<%=rowCount%>" onclick="deleteRow1(this)"><i class="icon ico-del"></i></a></td>
								</logic:notEqual>
								<logic:equal name="uSPSPostalCalendarVO" property="calOperationFlags" value="D">
							<ihtml:hidden property="periods" name="uSPSPostalCalendarVO"/>
							<ihtml:hidden property="fromDates" name="uSPSPostalCalendarVO"/>
							<ihtml:hidden property="toDates" name="uSPSPostalCalendarVO"/>
							<ihtml:hidden property="cgrSubmissions" name="uSPSPostalCalendarVO"/>
							<ihtml:hidden property="cgrDeleteCutOffs" name="uSPSPostalCalendarVO"/>
							<ihtml:hidden property="cutWeek1s" name="uSPSPostalCalendarVO"/>
							<ihtml:hidden property="cutWeek2s" name="uSPSPostalCalendarVO"/>
							<ihtml:hidden property="cutWeek3s" name="uSPSPostalCalendarVO"/>
							<ihtml:hidden property="cutWeek4s" name="uSPSPostalCalendarVO"/>
							<ihtml:hidden property="cutWeek5s" name="uSPSPostalCalendarVO"/>
							</logic:equal>
							<ihtml:hidden property="calSeqnum"  value="<%=(String.valueOf(calSeqnum))%>" />
							<ihtml:hidden property="calOperationFlags" value="<%=((String)calOperationFlags)%>" />
								</logic:present>
								<logic:notPresent name="uSPSPostalCalendarVO" property="calOperationFlags">
								<bean:define id="calSeqnum" name="uSPSPostalCalendarVO" property="calSeqnum" toScope="request" />
							<td class="text-center">
													&nbsp;&nbsp; <ihtml:checkbox property="calRowIds" id="<%=String.valueOf(rowCount)%>" value="<%=String.valueOf(rowCount)%>"/>
												</td>
							<td>
													<bean:write property="periods" name="uSPSPostalCalendarVO"/>
												</td>
								<td>
									<bean:write property="fromDates"  name="uSPSPostalCalendarVO" />
								</td>
								<td>
													<bean:write property="toDates"  name="uSPSPostalCalendarVO" />
												</td>
												<td>
													<bean:write property="cgrSubmissions"  name="uSPSPostalCalendarVO" />
												</td>
												<td>
													<bean:write property="cgrDeleteCutOffs"  name="uSPSPostalCalendarVO"  />
												</td>
												<td>
													<bean:write property="cutWeek1s"  name="uSPSPostalCalendarVO" />
												</td>
												<td>
													<bean:write property="cutWeek2s"  name="uSPSPostalCalendarVO" />
												</td>
												<td>
													<bean:write property="cutWeek3s"  name="uSPSPostalCalendarVO" />
												</td>
												<td>
													<bean:write property="cutWeek4s"  name="uSPSPostalCalendarVO"  />
												</td>
												<td>
													<bean:write property="cutWeek5s"  name="uSPSPostalCalendarVO" />
												</td>
												<td class="text-center">
														 <a  id="delete<%=rowCount%>" onclick="deleteRow1(this)"><i class="icon ico-del"></i></a>
														 </td>
											<ihtml:hidden property="calOperationFlags" value="N" />
												<ihtml:hidden property="calSeqnum"  value="<%=(String.valueOf(calSeqnum))%>" />
												</logic:notPresent>
						  </tr>
						   </logic:iterate>
							</logic:present>
                           <tr  template="true" id="cgrTemplateRow" style="display:none">
						   <ihtml:hidden property="calOperationFlags" value="NOOP" />
												<ihtml:hidden property="calSeqnum" value="" />
												<td class="text-center">
															<input type="checkbox"  name="calRowIds"/>
														 </td>
							<td>
													<ihtml:text property="periods" tabindex="5" styleClass="form-control" value="" style="width:100%" />
												</td>
							<td>
													<ibusiness:litecalendar tabindex="5" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_CAL"
										labelStyleClass="form-control-label" id="fromDates"
										property="fromDates"   value="" style="width:40px" /><!--Modified as part of ICRD-294731-->
												</td>
												<td>
												<ibusiness:litecalendar tabindex="5"  componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_CAL"
										labelStyleClass="form-control-label" id="toDates"
										property="toDates"    value=""  style="width:40px" /><!--Modified as part of ICRD-294731-->
												</td>
							<td>
							<ibusiness:litecalendar tabindex="5"  componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_CAL"
										labelStyleClass="form-control-label" id="cgrSubmissions"
										property="cgrSubmissions" value=""  style="width:50px"/>
												</td>
							<td>
							<ibusiness:litecalendar tabindex="5"  componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_CAL"
										labelStyleClass="form-control-label" id="cgrDeleteCutOffs"
										property="cgrDeleteCutOffs"   value="" style="width:50px"/>
												</td>
							<td>
							<ibusiness:litecalendar tabindex="5" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_CAL"
										labelStyleClass="form-control-label" id="cutWeek1s"
										property="cutWeek1s"   value="" style="width:50px" />
												</td>
							<td>
							<ibusiness:litecalendar tabindex="5"  componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_CAL"
										labelStyleClass="form-control-label" id="cutWeek2s"
										property="cutWeek2s"   value="" style="width:50px" />
												</td>
							<td>
							<ibusiness:litecalendar tabindex="5" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_CAL"
										labelStyleClass="form-control-label" id="cutWeek3s"
										property="cutWeek3s"    value="" style="width:50px" />
												</td>
							<td>
								<ibusiness:litecalendar tabindex="5" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_CAL"
										labelStyleClass="form-control-label" id="cutWeek4s"
										property="cutWeek4s"   value="" style="width:50px"/>
												</td>
							<td>
							<ibusiness:litecalendar tabindex="5"  componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_CAL"
										labelStyleClass="form-control-label" id="cutWeek5s"
										property="cutWeek5s"    value="" style="width:50px"/>
												</td>
												<td class="text-center">
														&nbsp;&nbsp; <a  id="delete" onclick="deleteRow1(this)"><i class="icon ico-del"></i></a>
														 </td>
										</tr>
                        </tbody>
                    </table>
					 </div>
                </div>
</ihtml:form>
<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>