<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name    :  IN - ULD
* File Name				:  ListUldDiscrepancies.jsp
* Date					:  12-May-2006
* Author(s)				:  A-2052,A-6806
*************************************************************************/
 --%>
 <%@ include file="/jsp/includes/tlds.jsp" %>
 <%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
 <%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
 <%@ page import = "java.util.Calendar" %>
<%@ page import = "com.ibsplc.icargo.framework.util.FormatConverter" %>
 <%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ListULDDiscrepanciesForm" %>
 <%
 	response.setDateHeader("Expires",0);
 	response.setHeader("Pragma","no-cache");

 	if (request.getProtocol().equals("HTTP/1.1")) {
 		response.setHeader("Cache-Control","no-cache");
 	}
 %>

	
	
 <html:html locale="true">
<head>
		
<title>
	<common:message bundle="listulddiscrepancies" key="uld.defaults.listulddiscrepancies.listulddiscrepancies" />
</title>
	<common:include type="script" src="/js/uld/defaults/ListUldDiscrepancies_Script.jsp"/>
	<meta name="decorator" content="mainpanelrestyledui">
</head>
<body>
	
	

 <business:sessionBean id="KEY_DETAILS"
 		               moduleName="uld.defaults"
 		               screenID="uld.defaults.listulddiscrepancies"
 		               method="get"
 		               attribute="ULDDiscrepancyVODetails"/>

<business:sessionBean id="KEY_LIST"
  		              moduleName="uld.defaults"
  		              screenID="uld.defaults.listulddiscrepancies"
  		              method="get"
 		              attribute="ULDDiscrepancyFilterVODetails"/>
 		   
 <business:sessionBean id="oneTimeValues"
		               moduleName="uld.defaults"
 		               screenID="uld.defaults.listulddiscrepancies"
 		               method="get"
		               attribute="oneTimeValues" />

 <bean:define id="form"
 		      name="ListULDDiscrepanciesForm"
 		      type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ListULDDiscrepanciesForm"
 		      toScope="page" />

 <logic:present name="KEY_DETAILS">
 <bean:define id="pageDetails" name="KEY_DETAILS" />
 </logic:present>

 <logic:present name="KEY_LIST">
 <bean:define id="KEY_LIST" name="KEY_LIST" />
</logic:present>
<div class="iCargoContent ic-masterbg">
<ihtml:form action="/uld.defaults.listulddiscrepancies.screenloadulddiscrepancies.do">

<ihtml:hidden property="listStatus" />
<ihtml:hidden property="fromList" />
<ihtml:hidden property="buttonStatusFlag" />
<ihtml:hidden property="displayPage" />
<ihtml:hidden property="lastPageNum" />
<ihtml:hidden property="discDisableStat" />
<input type="hidden" name="mySearchEnabled" />
<input type="hidden" name="currentDialogId" />
<input type="hidden" name="currentDialogOption" />

	<div class="ic-content-main">
		<div class="ic-row">
			<span class="ic-page-title ic-display-none"><common:message bundle="listulddiscrepancies" key="uld.defaults.listulddiscrepancies.listulddiscrepancies" /></span>
		</div>
		<div class="ic-head-container" > 
		<div class="ic-filter-panel">
			<div class="ic-row">	
	    	<h4>
	    		<common:message key="uld.defaults.listulddiscrepancies.searchcriteria"/>
	    	</h4>
			</div>
			<div class="ic-row">
				<div class="ic-input ic-split-16">
	            <label>
					<common:message key="uld.defaults.listulddiscrepancies.lbl.uldno"/>
				</label>
					<logic:present name="KEY_LIST" property="uldNumber">
						<bean:define id="uldNumber" name="KEY_LIST" property="uldNumber"/>
						<ibusiness:uld id="uldnumber" uldProperty="uldNo" uldValue="<%=(String)uldNumber%>"  componentID="TXT_ULD_DEFAULTS_LISTULDDISCREPANCIES_ULDNO" style="text-transform: uppercase"/>
					</logic:present>

					<logic:notPresent name="KEY_LIST" property="uldNumber">
						<ibusiness:uld id="uldnumber" uldProperty="uldNo" componentID="TXT_ULD_DEFAULTS_LISTULDDISCREPANCIES_ULDNO" style="text-transform: uppercase"/>
					</logic:notPresent>
				</div>
				<div class="ic-input ic-split-16">
				<label>
				 <common:message key="uld.defaults.listulddiscrepancies.lbl.airlinecode"/>
				</label>
				<logic:present name="KEY_LIST" property="airlineCode">
					<bean:define id="airlineCode" name="KEY_LIST" property="airlineCode"/>
					<ihtml:text componentID="TXT_ULD_DEFAULTS_LISTULDDISCREPANCIES_AIRLINECODE" property="airlineCode" value="<%=(String)airlineCode%>" maxlength="3"/>
				</logic:present>
				<logic:notPresent name="KEY_LIST" property="airlineCode">
					<ihtml:text componentID="TXT_ULD_DEFAULTS_LISTULDDISCREPANCIES_AIRLINECODE" property="airlineCode" value="" maxlength="3"/>
				</logic:notPresent>
				<div class="lovImg">
					<img  id="airlinecodelov" src="<%=request.getContextPath()%>/images/lov.png" width="18" height="18" alt="Airline LOV"/>
				</div>
				</div>
				<div class="ic-input ic-split-16">
				<label>
				<common:message key="uld.defaults.listulddiscrepancies.lbl.reportingstation"/>
				</label>
				<logic:present name="KEY_LIST" property="reportingStation">
				<bean:define id="reportingStation" name="KEY_LIST" property="reportingStation"/>
				<ihtml:text componentID="TXT_ULD_DEFAULTS_LISTULDDISCREPANCIES_REPORTINGSTATN" property="reportingStation" value="<%=(String)reportingStation%>" maxlength="3" />
				</logic:present>
				<logic:notPresent name="KEY_LIST" property="reportingStation">
				<ihtml:text componentID="TXT_ULD_DEFAULTS_LISTULDDISCREPANCIES_REPORTINGSTATN" property="reportingStation" value="" maxlength="3"/>
				</logic:notPresent>
				<div class="lovImg">
					<img  id="reportingstationlov" src="<%=request.getContextPath()%>/images/lov.png" width="18" height="18" alt="Airport LOV"/>
				</div>
				</div>
				<div class="ic-input ic-split-16">
				<label>
				<common:message key="uld.defaults.listulddiscrepancies.lbl.ownerstation"/>
				</label>
				<logic:present name="KEY_LIST" property="ownerStation">
				<bean:define id="ownerStation" name="KEY_LIST" property="ownerStation"/>
				<ihtml:text componentID="TXT_ULD_DEFAULTS_LISTULDDISCREPANCIES_OWNERSTATION" property="ownerStation" value="<%=(String)ownerStation%>" maxlength="3"/>
				</logic:present>
				<logic:notPresent name="KEY_LIST" property="ownerStation">
				<ihtml:text componentID="TXT_ULD_DEFAULTS_LISTULDDISCREPANCIES_OWNERSTATION" property="ownerStation" value="" maxlength="3"/>
				</logic:notPresent>
				<div class="lovImg">
					<img  id="ownerstationlov" src="<%=request.getContextPath()%>/images/lov.png" width="18" height="18" alt="Airport LOV"/>
				</div>
			</div>
				<div class="ic-input ic-split-16">
				<label>
				<common:message key="uld.defaults.maintainulddiscrepancies.DiscrepancyCode"/>
				</label>
				<ihtml:select name="KEY_LIST" property="discrepancyStatus" componentID="CMB_ULD_DEFAULTS_LISTULDDISCREPANCIES_DISCREPANCYCODE">
								<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
								  <logic:present name="oneTimeValues">
									<logic:iterate id="oneTimeValue" name="oneTimeValues">
									<bean:define id="parameterCode" name="oneTimeValue" property="key" />
										<logic:equal name="parameterCode" value="uld.defaults.discrepancyCode">
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
				<div class="ic-row ic-input ic-split-20">
			<div class="ic-button-container">
				<ihtml:nbutton property="btnList" accesskey="L" componentID="BTN_ULD_DEFAULTS_LISTULDDISCREPANCIES_LIST">
				<common:message key="uld.defaults.listulddiscrepancies.btn.btlist" />
				</ihtml:nbutton>

				<ihtml:nbutton property="btnClear" accesskey="C" componentID="BTN_ULD_DEFAULTS_LISTULDDISCREPANCIES_CLEAR">
				<common:message key="uld.defaults.listulddiscrepancies.btn.btclear" />
				</ihtml:nbutton>
			</div>
		</div>
			</div>
		
		

	</div>
	</div>
	<div class="ic-main-container">
	<div class="ic-row">
			<h4>
		<common:message key="uld.defaults.listulddiscrepancies.lbl.ulddiscrepancies"/>
			</h4>
			</div>
		<div class="ic-row">	
			<div class="ic-col-75">
				<logic:present name="pageDetails">
					<common:paginationTag
					pageURL="javascript:selectNextDetail('lastPageNum','displayPage')"
					name="KEY_DETAILS"
					display="label"
					labelStyleClass="iCargoResultsLabel"
					lastPageNum="<%=form.getLastPageNum() %>" />
				</logic:present>
			</div>
			<div class="ic-col-25 ic-leftmargin">
			<logic:present name="pageDetails">
				<common:paginationTag
				pageURL="javascript:selectNextDetail('lastPageNum','displayPage')"
				name="KEY_DETAILS"
				display="pages"
				linkStyleClass="iCargoLink"
				disabledLinkStyleClass="iCargoLink"
				lastPageNum="<%=form.getLastPageNum() %>" 
				exportToExcel="true"
				exportTableId="ulddiscrepencies"
				exportAction="uld.defaults.listulddiscrepancies.listulddiscrepancies.do"/>
			</logic:present>
			</div>
		</div>
		
		<div class="ic-row">
		
			<div class="tableContainer"  id="div1"  style="height:700px; ">
			
				<table class="fixed-header-table"  id="ulddiscrepencies">
					<thead>
						<tr class="iCargoTableHeadingLeft">
							<td width="5%" class="ic-center">
							<input type="checkbox" name="masterRowId" value="checkbox" /><span></span>
							</td>

							<td width="12%">
							<common:message key="uld.defaults.listulddiscrepancies.lbl.uldno"/><span></span>
							</td>

							<td width="12%">
							<common:message key="uld.defaults.listulddiscrepancies.lbl.discrepancycode"/><span></span>
							</td>

							<td width="13%">
							<common:message key="uld.defaults.listulddiscrepancies.lbl.discrepancydate"/><span></span>
							</td>

							<td width="14%">
							<common:message key="uld.defaults.listulddiscrepancies.lbl.reportingstation"/><span></span>
							</td>
							<td width="14%">
							<!--<common:message key="uld.defaults.listulddiscrepancies.lbl.discrepancycode"/>-->
							Faclility Type<span></span>
							</td>
							<td width="14%">
							<!--<common:message key="uld.defaults.listulddiscrepancies.lbl.discrepancycode"/>-->
							Location<span></span>
							</td>
							<td width="16%" >
							<common:message key="uld.defaults.listulddiscrepancies.lbl.remarks"/><span></span>
							</td>
						</tr>
				</thead>
				<tbody>
					<logic:present name="pageDetails">
					<bean:define id="discDetails" name="pageDetails" />
					<logic:iterate id="iterator" name="discDetails" indexId="repindex">
					<logic:present name="iterator" property="operationFlag">
					<logic:notEqual name="iterator" property="operationFlag" value="D">
				<tr>	
				<td class="iCargoTableDataTd ic-center">
					<html:checkbox property="rowId" value="<%=String.valueOf(repindex)%>"/>
				</td>
				<td class="iCargoTableDataTd ic-center" >
					<logic:present name="iterator" property="uldNumber">
					<bean:write name="iterator" property="uldNumber"/>
					</logic:present>
				</td>
				<td class="iCargoTableDataTd ic-center" >
					<logic:present  name="iterator" property="discrepencyCode">
					<bean:define id="discrepencyCode" name="iterator" property="discrepencyCode"/>
					
					<logic:present name="oneTimeValues">
						<logic:iterate id="oneTimeValue" name="oneTimeValues">
						<bean:define id="parameterCode" name="oneTimeValue" property="key" />
							<logic:equal name="parameterCode" value="uld.defaults.discrepancyCode">
								<logic:present name="oneTimeValue">
									<bean:define id="parameterValues" name="oneTimeValue" property="value" />
										<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
											<logic:present name="parameterValue">
											<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
												<logic:equal name="fieldValue" value="<%=String.valueOf(discrepencyCode)%>">
													<common:write name="parameterValue" property="fieldDescription"/>
					</logic:equal>
					</logic:present>
										</logic:iterate>
								</logic:present>
							</logic:equal>
						</logic:iterate>
					</logic:present>

					</logic:present>
				</td>
				<td class="iCargoTableDataTd ic-center">
					<logic:present  name="iterator" property="discrepencyDate">
					<bean:define id="discrepencyDate" name="iterator" property="discrepencyDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
					<%String rDate = TimeConvertor.toStringFormat(discrepencyDate.toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
					<%=(String)rDate%>
					</logic:present>
				</td>
				<td class="iCargoTableDataTd ic-center">
					<logic:present name="iterator" property="reportingStation">
					<bean:write name="iterator" property="reportingStation"/>
					</logic:present>
				</td>
				<td class="iCargoTableDataTd ic-center">
					<logic:present name="iterator" property="facilityType">	
					<bean:define id="facilityType" name="iterator" property="facilityType" />				 
					<input type="hidden" name="hiddenFacility" value="<%=(String)facilityType%>"/>
						<logic:present name="oneTimeValues">
							<logic:iterate id="oneTimeValue" name="oneTimeValues">
							<bean:define id="parameterCode" name="oneTimeValue" property="key"/>								
								<logic:equal name="parameterCode" value="uld.defaults.facilitytypes">									
								<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
									<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
										<logic:present name="parameterValue">
											<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
											<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
												<logic:equal name="iterator" property="facilityType" value="<%=(String)fieldValue%>">												
												<%=(String)fieldDescription%>
												</logic:equal>
										</logic:present>
									</logic:iterate>
								</logic:equal>
							</logic:iterate>
						</logic:present>
						<logic:notPresent name="oneTimeValues">
					    </logic:notPresent>
					</logic:present>
				    <logic:notPresent name="iterator" property="iterator" >
					<input type="hidden" name="hiddenFacility" value=""/>
					</logic:notPresent>
				</td>
				<td class="iCargoTableDataTd ic-center" >
					<logic:present name="iterator" property="location">
					<bean:write name="iterator" property="location"/>
					</logic:present>
				</td>
				<td class="iCargoTableDataTd ic-center" >
					<logic:present name="iterator" property="remarks">
					<bean:write name="iterator" property="remarks"/>
					</logic:present>
				</td>
				</tr>
				 </logic:notEqual>
				 <logic:equal name="iterator" property="operationFlag" value="D">
					<html:hidden name="iterator" property="uldNumber" />
					<html:hidden name="iterator" property="discrepencyCode" />
					<html:hidden name="iterator" property="discrepencyDate" />
					<html:hidden name="iterator" property="reportingStation" />
					<html:hidden name="iterator" property="remarks" />
				 </logic:equal>
				 <bean:define id="operationFlg" name="iterator" property="operationFlag" />
				 <html:hidden property="saveOpFlag" value="<%=(String)operationFlg%>"/>
				</logic:present>

				 <logic:notPresent name="iterator" property="operationFlag">
				<tr>
				<td class="ic-center">
				<html:checkbox property="rowId" value="<%=String.valueOf(repindex)%>"/>
				</td>
				<td class="iCargoTableDataTd ic-center" >
				<logic:present name="iterator" property="uldNumber">
					<bean:write name="iterator" property="uldNumber"/>
				</logic:present>
				</td>
				<td class="iCargoTableDataTd ic-center" >
				<logic:present  name="iterator" property="discrepencyCode">
					<bean:define id="discrepencyCode" name="iterator" property="discrepencyCode"/>
						
					<logic:present name="oneTimeValues">
						<logic:iterate id="oneTimeValue" name="oneTimeValues">
						<bean:define id="parameterCode" name="oneTimeValue" property="key" />
							<logic:equal name="parameterCode" value="uld.defaults.discrepancyCode">
								<logic:present name="oneTimeValue">
									<bean:define id="parameterValues" name="oneTimeValue" property="value" />
										<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
											<logic:present name="parameterValue">
											<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
												<logic:equal name="fieldValue" value="<%=String.valueOf(discrepencyCode)%>">
													<common:write name="parameterValue" property="fieldDescription"/>
						</logic:equal>
											</logic:present>
										</logic:iterate>
								</logic:present>
						</logic:equal>
						</logic:iterate>
					</logic:present>	
						
					
				</logic:present>
				</td>
				<td class="iCargoTableDataTd ic-center" >
					<logic:present  name="iterator" property="discrepencyDate">
					<bean:define id="discrepencyDate" name="iterator" property="discrepencyDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
					<%String rDate = TimeConvertor.toStringFormat(discrepencyDate.toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
					<%=(String)rDate%>
					</logic:present>
				</td>
				<td class="iCargoTableDataTd ic-center" >
					<logic:present name="iterator" property="reportingStation">
					<bean:write name="iterator" property="reportingStation"/>
					</logic:present>
				<td class="iCargoTableDataTd ic-center" >
					<logic:present name="iterator" property="facilityType">
						<bean:define id="facilityType" name="iterator" property="facilityType" />				 
						<input type="hidden" name="hiddenFacility" value="<%=(String)facilityType%>"/>
						<logic:present name="oneTimeValues">
							<logic:iterate id="oneTimeValue" name="oneTimeValues">
							<bean:define id="parameterCode" name="oneTimeValue" property="key"/>								
								<logic:equal name="parameterCode" value="uld.defaults.facilitytypes">									
								<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
									<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
										<logic:present name="parameterValue">
										<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
											<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
											<logic:equal name="iterator" property="facilityType" value="<%=(String)fieldValue%>">												
											<%=(String)fieldDescription%>
											</logic:equal>
										</logic:present>
									</logic:iterate>
								</logic:equal>
							</logic:iterate>
						</logic:present>
						<logic:notPresent name="oneTimeValues">
						</logic:notPresent>
					</logic:present>
					<logic:notPresent name="iterator" property="iterator" >
						<input type="hidden" name="hiddenFacility" value=""/>
					</logic:notPresent>
				</td>
				<td class="iCargoTableDataTd ic-center" >
				    <logic:present name="iterator" property="location">
					<bean:write name="iterator" property="location"/>
					</logic:present>
				</td>
                </td>
				<td class="iCargoTableDataTd ic-center" >
					<logic:present name="iterator" property="remarks">
					<bean:write name="iterator" property="remarks"/>
					</logic:present>
				</td>
				</tr>
				<html:hidden property="saveOpFlag" value="N"/>
				</logic:notPresent>
				</logic:iterate>
				</logic:present>
				</tbody>
			</table>
			
			</div>
		</div>
	</div>
	<div class="ic-foot-container">
		<div class="ic-row">
		<div class="ic-button-container paddR5">
				<ihtml:nbutton property="btnCloseDiscrepancy" accesskey="Y" componentID="BTN_ULD_DEFAULTS_LISTULDDISCREPANCIES_CLOSEDISCREPANCY">
				<common:message key="uld.defaults.listulddiscrepancies.btn.btclosediscrepancy" />
				</ihtml:nbutton>
	
				<%--<ihtml:nbutton property="btnRecordMovement" accesskey="M" componentID="BTN_ULD_DEFAULTS_LISTULDDISCREPANCIES_RECORDMOVEMENT">
				<common:message key="uld.defaults.listulddiscrepancies.btn.btrecordmovement" />
				</ihtml:nbutton>--%>
	
				<ihtml:nbutton property="btnCreate" accesskey="R" componentID="BTN_ULD_DEFAULTS_LISTULDDISCREPANCIES_CREATE">
				<common:message key="uld.defaults.listulddiscrepancies.btn.btcreate" />
				</ihtml:nbutton>

				<ihtml:nbutton property="btnDetails" accesskey="D" componentID="BTN_ULD_DEFAULTS_LISTULDDISCREPANCIES_DETAILS">
				<common:message key="uld.defaults.listulddiscrepancies.btn.btdetails" />
				</ihtml:nbutton>

				<ihtml:nbutton property="btnClose" accesskey="O" componentID="BTN_ULD_DEFAULTS_LISTULDDISCREPANCIES_CLOSE">
				<common:message key="uld.defaults.listulddiscrepancies.btn.btclose" />
				</ihtml:nbutton>
		</div>
		</div>
	</div>
	</div>
</ihtml:form>
</div>

				
	
	</body>
</html:html>

