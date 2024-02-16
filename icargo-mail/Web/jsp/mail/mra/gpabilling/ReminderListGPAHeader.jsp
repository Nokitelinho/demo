<%--
* 	Project			: iCargo
*	Module Name		: cra.agentbilling.defaults
*	File Name		: ReminderlistHeader.jsp
*	Date			: 01-12-2018
*	Author(s)		: a-8086
--%>


<%@ include file="/jsp/includes/tlds.jsp"%>
<%@ page import ="com.ibsplc.icargo.presentation.web.struts.form.cra.agentbilling.defaults.ReminderListForm"%>
<%@ page language="java"%>
<%@ include file="/jsp/includes/tlds.jsp"%>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import = "java.util.Calendar" %>

			<bean:define id="form" name="ReminderListForm" type="com.ibsplc.icargo.presentation.web.struts.form.cra.agentbilling.defaults.ReminderListForm" toScope="page" />
			<business:sessionBean id="REMINDER_DTLS"
			moduleName="cra.agentbilling"
			screenID="cra.agentbilling.defaults.reminderlist"
			method="get"
			attribute="reminderDetailsVOs"/>
			<business:sessionBean id="RANKS"
				moduleName="cra.agentbilling"
				screenID="cra.agentbilling.defaults.reminderlist"
				method="get"
				attribute="ranks"/>
			<business:sessionBean id="OneTimeValues"
			  moduleName="cra.agentbilling"
			  screenID="cra.agentbilling.defaults.reminderlist"
			  method="get"
			  attribute="OneTimeValues" />
			<div class="ic-row">
						<div class="ic-col">
							<h4>
							<common:message key="cra.agentbilling.defaults.reminderlist.invoices" />
							</h4>
						</div>
					</div>

					<div class="ic-row">
								<div class="ic-col-50">
									<logic:present name="REMINDER_DTLS">
										<bean:define id="voFromSession" name="REMINDER_DTLS"/>
										<logic:present name="voFromSession">
										<common:paginationTag pageURL="cra.agentbilling.defaults.reminderlist.list.do"
						  			name="voFromSession"
						  			display="label"
						  			labelStyleClass="iCargoResultsLabel"
						  			lastPageNum="<%=form.getLastPageNumber() %>"/>
									</logic:present>
									<logic:notPresent name="voFromSession">
									&nbsp;
									</logic:notPresent>
									</logic:present>
								</div>
								<div class="ic-col-50">
									<div class="ic-button-container">
										<logic:present name="REMINDER_DTLS">
											<bean:define id="voFromSession" name="REMINDER_DTLS"/>
												<logic:present name="voFromSession">
													<common:paginationTag pageURL="javascript:submitPage('lastPageNum','displayPage')"
													name="voFromSession"
													linkStyleClass="iCargoLink" disabledLinkStyleClass="iCargoLink"
													display="pages"
													lastPageNum="<%=form.getLastPageNumber()%>"
													exportToExcel="true"
													exportTableId="reminderListDetailsMRA"
													exportAction="cra.agentbilling.defaults.reminderlist.list.do?navigationMode=LINK"/>
												</logic:present>
												<logic:notPresent name="voFromSession">
												&nbsp;
												</logic:notPresent>
										</logic:present>
									</div>
								</div>
					</div>
					<div class="ic-row">
						<div class="tableContainer" id="div1" style="height:495px">
							<table class="fixed-header-table" id="reminderListDetailsMRA">
								<thead>
									<tr class="iCargoTableHeadingLeft">
										<td width="3%">
											<input type="checkbox" name="allCheck" value="checkbox" onclick="updateHeaderCheckBox(this.form,document.forms[1].elements.allCheck,document.forms[1].elements.selectGPACheckBox)" />
										</td>
										<td width="24%" class="iCargoTableHeader">
											<common:message key="cra.agentbilling.defaults.reminderlist.lbl.mailbag"/>

										</td>
										<td width="7%" class="iCargoTableHeader">
											<common:message key="cra.agentbilling.defaults.reminderlist.lbl.mcano"/>

										</td>
										<td width="8%" class="iCargoTableHeader">
											<common:message key="cra.agentbilling.defaults.reminderlist.lbl.gpacod"/>
										</td>
										<td width="8%" class="iCargoTableHeader">
											<common:message key="cra.agentbilling.defaults.reminderlist.lbl.typeofbilling"/>
										</td>
										<td width="8%" class="iCargoTableHeader">
											<common:message key="cra.agentbilling.defaults.reminderlist.lbl.reminderstatus"/>
										</td>
										<td width="8%" class="iCargoTableHeader">
											<common:message key="cra.agentbilling.defaults.reminderlist.lbl.mailbagdate"/>
										</td>
										<td width="10%" class="iCargoTableHeader">
											<common:message key="cra.agentbilling.defaults.reminderlist.lbl.origindestination"/>
										</td>
										<td width="5%" class="iCargoTableHeader">
											<common:message key="cra.agentbilling.defaults.reminderlist.lbl.currency"/>
										</td>
										<td width="8%" class="iCargoTableHeader">
											<common:message key="cra.agentbilling.defaults.reminderlist.lbl.billedamount"/>
										</td>
										<td width="8%" class="iCargoTableHeader">
											<common:message key="cra.agentbilling.defaults.reminderlist.lbl.mcaamount"/>
										</td>
										<td width="8%" class="iCargoTableHeader">
											<common:message key="cra.agentbilling.defaults.reminderlist.lbl.settlementamount"/>
										</td>
										<td width="8%" class="iCargoTableHeader">
											<common:message key="cra.agentbilling.defaults.reminderlist.lbl.dueamount"/>
										</td>
										<td width="8%" class="iCargoTableHeader">
											<common:message key="cra.agentbilling.defaults.reminderlist.lbl.reminderno"/>
										</td>
										<logic:notEqual  name="form" property="typeOfBilling" value="CE">
										<td id="remark1" width="12%" class="iCargoTableHeader">
											<common:message key="cra.agentbilling.defaults.reminderlist.lbl.remarks"/>
										</td>
										</logic:notEqual>
									 </tr>
								</thead>
								<tbody>
								<% boolean flag=false;%>
									<logic:present name="REMINDER_DTLS">
									<% int remarksCount=0;%>
										<logic:iterate id ="reminderList" name="REMINDER_DTLS" type="com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.ReminderDetailsVO" indexId="index">
											<tr id="<%=index%>" >
												<td class="ic-center">
												<logic:present name="RANKS">
												<logic:iterate id ="rank" name="RANKS" >
													<%
													   if(((String)rank).equals(reminderList.getRank())){
															flag= true;
															break;
													   }else{
															flag= false;
													   }
													   %>
												 </logic:iterate>
													<%if(flag==true){%>
														<input type="checkbox" name="selectGPACheckBox"  value="<%=String.valueOf(index)%>" onclick="toggleTableHeaderCheckbox('selectGPACheckBox',document.forms[1].elements.allCheck)" checked="true" />
													<%}else if(flag==false) {%>
													<input type="checkbox" name="selectGPACheckBox"  value="<%=String.valueOf(index)%>" onclick="toggleTableHeaderCheckbox('selectGPACheckBox',document.forms[1].elements.allCheck)" />
													<% } %>
												</logic:present>
												<logic:notPresent name="RANKS">
													<input type="checkbox" name="selectGPACheckBox"  value="<%=String.valueOf(index)%>" onclick="toggleTableHeaderCheckbox('selectGPACheckBox',document.forms[1].elements.allCheck)" />
												</logic:notPresent>
												</td>
												<td>
													<logic:present	name="reminderList" property="mailbagId">
													<bean:define id="mailbagIds" name="reminderList" property="mailbagId"/>
													<ihtml:text name="form" styleClass="hidden"  property="mailbagId" value="<%=String.valueOf(mailbagIds)%>" />
														<bean:write name="reminderList" property="mailbagId"/>
													</logic:present>
													<logic:notPresent name="reminderList" property="mailbagId">
													</logic:notPresent>
												</td>
												<td>
													<logic:present	name="reminderList" property="ccaRefNumbers">
														<bean:write name="reminderList" property="ccaRefNumbers"/>
													</logic:present>
													<logic:notPresent name="reminderList" property="ccaRefNumbers">
													</logic:notPresent>
												</td>
												<td>
													<logic:present	name="reminderList" property="gpaCode">
														<bean:write name="reminderList" property="gpaCode"/>
													</logic:present>
													<logic:notPresent name="reminderList" property="gpaCode">
													</logic:notPresent>
												</td>
												<td>
												<logic:present	name="reminderList" property="invoiceSequenceNo">
													<bean:define id="invoiceSequenceNos" property="invoiceSequenceNo" name="reminderList"/>
													<ihtml:text name="form" styleClass="hidden"  property="invoiceSequenceNo" value="<%=String.valueOf(invoiceSequenceNos)%>" />
														<bean:define id="invoiceSerialNos" property="invoiceSerialNo" name="reminderList"/>
													<ihtml:text name="form" styleClass="hidden" property="invoiceSerialNo" value="<%=String.valueOf(invoiceSerialNos)%>" />
												</logic:present>
												<logic:present	name="reminderList" property="typeOfBilling">
												<bean:define id="stt" name="reminderList" property="typeOfBilling"/>
												<logic:present name="OneTimeValues">
												<bean:define id="OneTimeValuesMap1" name="OneTimeValues" type="java.util.HashMap" />
												<logic:iterate id="oneTimeValue1" name="OneTimeValuesMap1">
													<bean:define id="parameterCode1" name="oneTimeValue1" property="key"/>
													<logic:equal name="parameterCode1" value="cra.agentbilling.reminderbillingtypes">
														<bean:define id="parameterValues2" name="oneTimeValue1" property="value"/>
														<logic:iterate id="parameterValue1" name="parameterValues2" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
															<logic:present name="parameterValue1" property="fieldValue">
																<bean:define id="fieldValue" name="parameterValue1" property="fieldValue"/>
																<bean:define id="fieldDescription" name="parameterValue1" property="fieldDescription"/>
															<logic:equal name="fieldValue" value="<%=stt.toString()%>">
															<%=String.valueOf(fieldDescription)%>
															</logic:equal>
													</logic:present>
														</logic:iterate>
													</logic:equal>
												</logic:iterate>
												</logic:present>
												</logic:present>
													<logic:notPresent	name="reminderList" property="typeOfBilling">
														<ihtml:hidden name="form" property="typeOfBilling"  value="" />
													</logic:notPresent>
												</td>
												<td>
													<bean:write name="reminderList" property="reminderStatus"/>
												</td>
												<td>
													<logic:present	name="reminderList" property="issueDate">
													<%String issDate = TimeConvertor.toStringFormat(((LocalDate)reminderList.getIssueDate()).toCalendar(),"dd-MMM-yyyy"); %>
													<%=issDate%>
													</logic:present>
													<logic:notPresent name="reminderList" property="issueDate">
													</logic:notPresent>
												</td>
												<td>
													<logic:present	name="reminderList" property="originCode">
														<bean:write name="reminderList" property="originCode"/>&nbsp-
													</logic:present>
													<logic:notPresent name="reminderList" property="originCode">
													</logic:notPresent>
													<logic:present	name="reminderList" property="destinationCode">
														<bean:write name="reminderList" property="destinationCode"/>
													</logic:present>
													<logic:notPresent name="reminderList" property="destinationCode">
													</logic:notPresent>
												</td>
												<td>
													<logic:present	name="reminderList" property="originCurrency">
														<bean:write name="reminderList" property="originCurrency"/>
													</logic:present>
													<logic:notPresent  name="reminderList" property="originCurrency">
													</logic:notPresent>
												</td>
												<td style="text-align:right" data-number="true">
												
													<logic:equal name="form" property="gpaRebillRound" value="">
													<logic:present	name="reminderList" property="billedAmount">
													<ibusiness:moneyDisplay showCurrencySymbol="false" name="reminderList"  property="billedAmount" />
													</logic:present>
													</logic:equal>
													<logic:notEqual name="form" property="gpaRebillRound" value="">
													
													<logic:present	name="reminderList" property="dueAmount">
													<bean:define id="dueAmt" name="reminderList" property="dueAmount"/>
													<ibusiness:moneyDisplay showCurrencySymbol="false" name="reminderList"  property="dueAmount" />										
													</logic:present>
													
													</logic:notEqual>
													
													<logic:notPresent  name="reminderList" property="billedAmount">
													</logic:notPresent>
												</td>
												<td style="text-align:right" data-number="true">
												<logic:equal name="form" property="gpaRebillRound" value="">
													<logic:present	name="reminderList" property="ccaAmount">
													<ibusiness:moneyDisplay showCurrencySymbol="false" name="reminderList"  property="ccaAmount" />
													</logic:present></logic:equal>
													<logic:notPresent  name="reminderList" property="ccaAmount">
													</logic:notPresent>
												</td>
												<td style="text-align:right" data-number="true">
												<logic:equal name="form" property="gpaRebillRound" value="">
													<logic:present	name="reminderList" property="settlementAmount">
													<ibusiness:moneyDisplay showCurrencySymbol="false" name="reminderList"  property="settlementAmount" />
													</logic:present></logic:equal>
													<logic:notPresent name="reminderList" property="settlementAmount">
													</logic:notPresent>
												</td>
												<td style="text-align:right" data-number="true">
													<logic:present	name="reminderList" property="dueAmount">
														<bean:define id="dueAmt" name="reminderList" property="dueAmount"/>
														<ibusiness:moneyDisplay showCurrencySymbol="false" name="reminderList"  property="dueAmount" />	
													</logic:present>
												</td>
												<td style="text-align:center">
													<logic:present	name="reminderList" property="reminderNumber">
														<bean:define id="num" property="reminderNumber" name="reminderList"/>
														<ihtml:hidden name="form" property="reminderNumber"  value="<%=num.toString()%>" />
														<bean:write name="reminderList" property="reminderNumber"/>
													</logic:present>
												</td>
												<logic:notEqual  name="form" property="typeOfBilling" value="CE">
												<td style="text-align:center">
												<a href="#" class="iCargoLink" onclick="javaScript:onRemarksLink(<%=remarksCount%>)" name="remarksLink" id="remarksLink">Add</a>
												</td>
												</logic:notEqual><%remarksCount++;%>
											</tr>
										</logic:iterate>
										<input type="hidden" name="totalRowCount" value="<%=remarksCount%>"/>
									</logic:present>
									<logic:notPresent name="REMINDER_DTLS">
									</logic:notPresent>
								</tbody>
							</table>
						</div>
					</div>