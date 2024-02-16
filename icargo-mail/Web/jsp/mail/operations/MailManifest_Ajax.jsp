<%--
 /***********************************************************************
* Project       	 :  iCargo
* Module Code & Name 	 :  MailTracking
* File Name     	 :  MailManifest_Ajax.jsp
* Date          	 :  31-07-2008
* Author(s)     	 :  Reno K Abraham

*************************************************************************/
 --%>

<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailManifestForm"%>
<%@ page import="com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO"%>
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


<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>	


<bean:define id="MailManifestForm" name="MailManifestForm"
   type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailManifestForm"
   toScope="page" scope="request"/>
<business:sessionBean id="flightValidationVOSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailmanifest" method="get" attribute="flightValidationVO" />
	<logic:present name="flightValidationVOSession">
		<bean:define id="flightValidationVOSession" name="flightValidationVOSession" toScope="page"/>
	</logic:present>
<business:sessionBean id="mailManifestVOSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailmanifest" method="get" attribute="mailManifestVO" />
	<logic:present name="mailManifestVOSession">
		<bean:define id="mailManifestVOSession" name="mailManifestVOSession" toScope="page"/>
	</logic:present>
<business:sessionBean id="oneTimeCatSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailmanifest" method="get" attribute="oneTimeCat" />


<ihtml:form action="mailtracking.defaults.mailmanifest.screenloadmailmanifest.do">

<ihtml:hidden property="initialFocus" />
<ihtml:hidden property="duplicateFlightStatus" />
<ihtml:hidden property="disableSaveFlag" />
<ihtml:hidden property="uldsSelectedFlag" />
<ihtml:hidden property="uldsPopupCloseFlag" />
<ihtml:hidden property="openAttachAWB" />
<ihtml:hidden property="pou" />
<ihtml:hidden property="pol" />
<ihtml:hidden property="operationalStatus" />
<ihtml:hidden property="mailFlightSummary" />
<ihtml:hidden property="type" />
<ihtml:hidden property="parentContainer" />
<ihtml:hidden property="selectChild" />
<ihtml:hidden property="autoAttach" />
<ihtml:hidden property="shipmentDesc" />

    <div id="_mailManifest_table">
		<div class="tableContainer" id="div2"   style="width:100%;overflow:auto;height:650px;">     
			<table  id="mailmanifest" class="fixed-header-table">
				<thead>
				  <tr class="iCargoTableHeadingCenter">
				    <td width="7%" class="iCargoTableTd">
								<a href="#" class= "ic-tree-table-expand"></a>
								
					</td>
				    <td width="30%" class="iCargoTableTd"><common:message key="mailtracking.defaults.mailmanifest.lbl.uld" /></td>
				    <td width="15%" class="iCargoTableTd"><common:message key="mailtracking.defaults.mailmanifest.lbl.pou" /></td>
				    <td width="15%" class="iCargoTableTd"><common:message key="mailtracking.defaults.mailmanifest.lbl.destination" /></td>
				    <td width="17%" class="iCargoTableTd"><common:message key="mailtracking.defaults.mailmanifest.lbl.numbags" /></td>
				    <td width="16%" class="iCargoTableTd"><common:message key="mailtracking.defaults.mailmanifest.lbl.wt" /></td>
				  </tr>
				</thead>
				<tbody>
					<% int i = 0;%>
					<logic:present name="mailManifestVOSession" property="containerDetails">
						<bean:define id="containerDetailsVOsColl" name="mailManifestVOSession" property="containerDetails" scope="page" toScope="page"/>

						 <% Collection<String> selectedrows = new ArrayList<String>(); %>
						 <logic:present name="MailManifestForm" property="selectMail" >
						 <%
							String[] selectedRows = MailManifestForm.getSelectMail();
							for (int j = 0; j < selectedRows.length; j++) {
								selectedrows.add(selectedRows[j]);
							}
						%>
						</logic:present>
						<logic:iterate id="containerDetailsVO" name="containerDetailsVOsColl">
							<!--Parent Rows -->
									<tr class="iCargoTableDataRow1" id="container<%=i%>" class="ic-table-row-main">
										<td class="iCargoTableDataTd" width="8%">
											 <div>
												<a href="#"  class="ic-tree-table-expand tier1"></a> &nbsp;&nbsp;&nbsp;&nbsp;
										  <bean:define id="compcode" name="containerDetailsVO" property="companyCode" toScope="page"/>
										  <% String primaryKey = String.valueOf(i);%>
												  <%
											if(selectedrows.contains(primaryKey)){
										  %>

											<input type="checkbox" name="selectMail" value="<%=primaryKey%>" checked />
										  <%
											}
											else{
										  %>
											<input type="checkbox" name="selectMail" value="<%=primaryKey%>" />

										  <%
											}
										  %>
										 </div>	
								</td>
								<td class="iCargoTableDataTd">
								 <logic:present name="containerDetailsVO" property="paBuiltFlag">
									 <logic:equal name="containerDetailsVO" property="paBuiltFlag" value="Y">
										<bean:write name="containerDetailsVO" property="containerNumber"/>
										<common:message key="mailtracking.defaults.mailmanifest.sb"/>
									 </logic:equal>
									 <logic:equal name="containerDetailsVO" property="paBuiltFlag" value="N">
										<bean:write name="containerDetailsVO" property="containerNumber"/>
									 </logic:equal>
								 </logic:present>
								 <logic:notPresent name="containerDetailsVO" property="paBuiltFlag">
									<bean:write name="containerDetailsVO" property="containerNumber"/>	
								 </logic:notPresent>	
								</td>
								<td class="iCargoTableDataTd"><bean:write name="containerDetailsVO" property="pou"/></td>
								<td class="iCargoTableDataTd"><bean:write name="containerDetailsVO" property="destination"/></td>
								<td class="iCargoTableDataTd"><bean:write name="containerDetailsVO" property="totalBags" format="####"/></td>
										<td class="iCargoTableDataTd"> 
										<common:write name="containerDetailsVO" property="totalWeight" unitFormatting="true" /></td> 
							 </tr>
							 <!--Child Rows -->
									<tr id="container<%=i%>-<%=i%>" class="ic-table-row-sub">
							    <td colspan="10"><div class="tier4"><a href="#" ></a></div>
											<table width="100%"><tr><td>
												<table>
													<thead>
														<tr class="iCargoTableHeadingCenter">
																		<td width="3%" class="iCargoTableTd ic-center"><input type="checkbox" name="masterDSN" value="<%=primaryKey%>" onclick="updateHeaderCheckBoxForTreeTable(this);"/></td>
																		<td width="8%" class="iCargoTableTd"><common:message key="mailtracking.defaults.mailmanifest.lbl.dsn" /></td>
																<td width="9%" class="iCargoTableTd"><common:message key="mailtracking.defaults.mailmanifest.lbl.origin" /></td>
																<td width="9%" class="iCargoTableTd"><common:message key="mailtracking.defaults.mailmanifest.lbl.destnoe" /></td>
																<td width="12%" class="iCargoTableTd"><common:message key="mailtracking.defaults.mailmanifest.lbl.cat" /></td>
																		<td width="5%" class="iCargoTableTd"><common:message key="mailtracking.defaults.mailmanifest.lbl.class" /></td>
																<td width="6%" class="iCargoTableTd"><common:message key="mailtracking.defaults.mailmanifest.lbl.sc" /></td>
																		<td width="5%" class="iCargoTableTd"><common:message key="mailtracking.defaults.mailmanifest.lbl.year" /></td>
																<td width="7%" class="iCargoTableTd"><common:message key="mailtracking.defaults.mailmanifest.lbl.numbags" /></td>
																		<td width="7%" class="iCargoTableTd"><common:message key="mailtracking.defaults.mailmanifest.lbl.wt" /></td>
																		<td width="8%" class="iCargoTableTd"><common:message key="mailtracking.defaults.mailmanifest.lbl.awb" /></td>
																		<td width="10%" class="iCargoTableTd"><common:message key="mailtracking.defaults.mailmanifest.lbl.csgdocnum" /></td>
																		<td width="5%" class="iCargoTableTd">PA Code</td>
																<td width="2%" class="iCargoTableTd"><common:message key="mailtracking.defaults.mailmanifest.lbl.plt" /></td>
																		<td width="4%" class="iCargoTableTd"><common:message key="mailtracking.defaults.mailmanifest.lbl.route" /></td>
														</tr>
													</thead>
													<tbody>
														<logic:present name="containerDetailsVO" property="dsnVOs">
															<bean:define id="dsnVOsColl" name="containerDetailsVO" property="dsnVOs" scope="page" toScope="page"/>

															<% Collection<String> selecteddsns = new ArrayList<String>(); %>
														 	<logic:present name="MailManifestForm" property="selectDSN" >
														 		 <%
														 			String[] selectedDsns = MailManifestForm.getSelectDSN();
														 			for (int j = 0; j < selectedDsns.length; j++) {
														 				selecteddsns.add(selectedDsns[j]);
														 			}
														 		%>
															</logic:present>
															<logic:iterate id="dsnVO" name="dsnVOsColl" indexId="index">
																		<tr class="ic-table-row-sub">
																			<td class="iCargoTableDataTd">
																  	<%
																  	    String primaryKeyDSN = i + "-" + String.valueOf(index);
																  	%>

																	 <%
																		if(selecteddsns.contains(primaryKeyDSN)){
																	 %>

																		<input type="checkbox" name="selectDSN" value="<%=primaryKeyDSN%>" checked onclick="toggleTableHeaderCheckboxForTreeTable(this);event.cancelBubble=true" />
																	 <%
																		}
																		else{
																	 %>
																		<input type="checkbox" name="selectDSN" value="<%=primaryKeyDSN%>" onclick="toggleTableHeaderCheckboxForTreeTable(this);event.cancelBubble=true"/>

																	 <%
																		}
															  		 %>
																	</td>
																	<td class="iCargoTableDataTd"><bean:write name="dsnVO" property="dsn"/></td>
																	<td class="iCargoTableDataTd"><bean:write name="dsnVO" property="originExchangeOffice"/></td>
																	<td class="iCargoTableDataTd"><bean:write name="dsnVO" property="destinationExchangeOffice"/></td>
																	<td class="iCargoTableDataTd">
																	    <logic:present name="dsnVO" property="mailCategoryCode">
																	    	<%
																				String categoryCode = "";
																			%>
																			<logic:present name="dsnVO" property="mailClass">
																				<bean:define id="mailClass" name="dsnVO" property="mailClass"/>
																					<%
																						for(int cls=0;cls < MailConstantsVO.MILITARY_CLASS.length;cls++){
																							if(MailConstantsVO.MILITARY_CLASS[cls].equals(mailClass)){
																								categoryCode = "M";
																							}
																						}
																					%>
																			</logic:present>
																			<% if(categoryCode.trim().length() == 0){ %>
																				<logic:iterate id="oneTimeCatSess" name="oneTimeCatSession" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																					<bean:define id="fieldValue" name="oneTimeCatSess" property="fieldValue"/>
																					<bean:define id="fieldDescription" name="oneTimeCatSess" property="fieldDescription"/>
																					<logic:equal name="dsnVO" property="mailCategoryCode" value="<%=(String)fieldValue%>">
																						<%=(String)fieldDescription%>
																					</logic:equal>
																				</logic:iterate>
																			<% }else{ %>
																				MILITARY
																			<%}%>
																	    </logic:present>
																	</td>
																	<td class="iCargoTableDataTd"><bean:write name="dsnVO" property="mailClass"/></td>
																	<td class="iCargoTableDataTd">
																	 <% String subclassValue = ""; %>
																		<logic:notPresent name="dsnVO" property="mailSubclass">
																		&nbsp;
																		</logic:notPresent>
																		<logic:present name="dsnVO" property="mailSubclass">
																		<bean:define id="despatchSubclass" name="dsnVO" property="mailSubclass" toScope="page"/>
																		<% subclassValue = (String) despatchSubclass;
																		int arrays=subclassValue.indexOf("_");
																		if(arrays==-1){%>

																		<bean:write name="dsnVO" property="mailSubclass"/>
																		<%}else{%>
																		&nbsp;
																		<%}%>
																	</logic:present>
												   					</td>
																	<td class="iCargoTableDataTd"><bean:write name="dsnVO" property="year"/></td>
																	<td class="iCargoTableDataTd"><bean:write name="dsnVO" property="bags" format="####"/></td>
																	<td class="iCargoTableDataTd">  <common:write name="dsnVO" property="weight" unitFormatting="true" />
																	</td>
																	<td class="iCargoTableDataTd">
																	<logic:present name="dsnVO" property="documentOwnerCode">
																	     <bean:write name="dsnVO" property="documentOwnerCode"/><label>-</label>
																	     <bean:write name="dsnVO" property="masterDocumentNumber"/>
																	</logic:present>	 
																	</td>
																	<td class="iCargoTableDataTd">
																		<logic:present name="dsnVO" property="csgDocNum">
																		<bean:define id="csgDocNum" name="dsnVO" property="csgDocNum"/>
																		<%String field=csgDocNum.toString();%>

																		<ihtml:hidden property="csgDocNum" value="<%=field%>"/>
																		</logic:present>
																		<logic:notPresent name="dsnVO" property="csgDocNum">
																		<ihtml:hidden property="csgDocNum" value="N"/>
																		</logic:notPresent>
																	<bean:write name="dsnVO" property="csgDocNum"/>
																	</td>
																	<td class="iCargoTableDataTd">
																		<logic:present name="dsnVO" property="paCode">
																		<bean:define id="paCode" name="dsnVO" property="paCode"/>
																		<%String field=paCode.toString();%>

																		<ihtml:hidden property="paCod" value="<%=field%>"/>
																		</logic:present>
																		<logic:notPresent name="dsnVO" property="paCode" >
																		<ihtml:hidden property="paCod" value="N"/>
																		</logic:notPresent>
																	<bean:write name="dsnVO" property="paCode"/>
																	</td>
																	<td class="iCargoTableDataTd">
																		<div class="ic-center">
																			 <logic:notPresent name="dsnVO" property="pltEnableFlag">
																				<!--<input type="checkbox" name="isPrecarrAwb" value="false" disabled="true"/>-->
																					<img id="isnotPltEnabled" src="<%=request.getContextPath()%>/images/icon_off.gif" />
																			 </logic:notPresent>
																			 <logic:present name="dsnVO" property="pltEnableFlag">
																				<logic:equal name="dsnVO" property="pltEnableFlag" value="Y" >
																				       <!--<input type="checkbox" name="isPrecarrAwb" value="true" checked disabled="true"/>-->
																				       <img id="isPltEnabled" src="<%=request.getContextPath()%>/images/icon_on.gif" />
																				</logic:equal>
																				<logic:equal name="dsnVO" property="pltEnableFlag" value="N">
																				     <!--<input type="checkbox" name="isPrecarrAwb" value="false" disabled="true"/>-->
																					<img id="isnotPltEnabled" src="<%=request.getContextPath()%>/images/icon_off.gif" />
																				</logic:equal>
																			 </logic:present>
																		 </div>
																    </td>
																	<td class="iCargoTableDataTd">
																		<div class="ic-center">
																			 <logic:notPresent name="dsnVO" property="routingAvl">
																				<!--<input type="checkbox" name="routingAvl" value="false" disabled="true"/>-->
																					<img id="routingNotAvl" src="<%=request.getContextPath()%>/images/icon_off.gif" />
																					<ihtml:hidden property="hiddenRoutingAvl" value="N" />
																			 </logic:notPresent>
																			 <logic:present name="dsnVO" property="routingAvl">
																				<logic:equal name="dsnVO" property="routingAvl" value="Y" >
																					<!--<input type="checkbox" name="routingAvl" value="true" checked disabled="true"/>-->
																				       <img id="routingAvl" src="<%=request.getContextPath()%>/images/icon_on.gif" />
																					<ihtml:hidden property="hiddenRoutingAvl" value="Y" />
																				</logic:equal>
																				<logic:equal name="dsnVO" property="routingAvl" value="N">
																					<!--<input type="checkbox" name="routingAvl" value="false" disabled="true"/>-->
																					<img id="routingNotAvl" src="<%=request.getContextPath()%>/images/icon_off.gif" />
																					<ihtml:hidden property="hiddenRoutingAvl" value="N" />
																				</logic:equal>
																			 </logic:present>
																		 </div>
																	</td>
																</tr>
															</logic:iterate>
														</logic:present>
													</tbody>
												</table>
											
								</td>
							</tr>
						</table>
							    	</td>
							</tr>
							<% i++;%>
						</logic:iterate>
					</logic:present>
					<tr></tr>
				</tbody>
			</table>
		</div>
		</div>

</ihtml:form>

<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>