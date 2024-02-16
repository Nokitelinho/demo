<%--
* Project	 		: iCargo
* Module Code & Name: mra.defaults
* File Name			: ViewFlightSectorRevenue.jsp
* Date				: 18/08/2008
* Author(s)			: A-3429
 --%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import = "com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import = "java.util.Calendar" %>
<%@ page import="java.util.Formatter" %>
<%@ page import = "com.ibsplc.icargo.framework.util.time.LocalDate" %>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ViewMailFlightSectorRevenueForm"%>
<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%
	String FORMAT_STRING = "%1$-16.2f";
%>



			
	
<html>
<head>
		
	
		
	
	<title><common:message bundle="viewFlightSectorRevenue" key="mailtracking.mra.defaults.viewflightsectorrevenue.lbl.title" /></title>
	<meta name="decorator" content="mainpanel">
	<common:include type="script" src="/js/mail/mra/defaults/ViewFlightSectorRevenue_Script.jsp"/>

</head>


<body class="ic-center" style="width:82%;">
	
	
	

	<bean:define id="form"
			 name="ViewMailFlightSectorRevenueForm"
			 type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ViewMailFlightSectorRevenueForm"
			 toScope="page" />

	<business:sessionBean id="KEY_SECTORDETAILS"
			 moduleName="mailtracking.mra.defaults"
			 screenID="mailtracking.mra.defaults.viewflightsectorrevenue"
			 method="get"
			 attribute="SectorDetails"/>

	<business:sessionBean id="KEY_REVENUEDETAILS"
			 moduleName="mailtracking.mra.defaults"
			 screenID="mailtracking.mra.defaults.viewflightsectorrevenue"
			 method="get"
			 attribute="FlightSectorRevenueDetails"/>

  <!--CONTENT STARTS-->
<div id="pageDiv" class="iCargoContent">
	<ihtml:form action="/mailtracking.mra.defaults.viewflightsectorrevenue.screenload.do">

		<ihtml:hidden name="form" property="duplicateFlightFlag"/>
		<ihtml:hidden name="form" property="selectedDsn"/>
		<ihtml:hidden property="fromScreen"/>
		<ihtml:hidden property="sectorCtrlFlg"/>
		 <div class="ic-content-main">
			<span class="ic-page-title ic-display-none"><common:message key="mailtracking.mra.defaults.viewflightsectorrevenue.lbl.heading" /></span>
			<div class="ic-head-container">
				<div class="ic-filter-panel">
					<div class="ic-row ic-border">
						<div class="ic-input ic-split-30 ic-mandatory">
							<common:message key="mailtracking.mra.defaults.viewflightsectorrevenue.lbl.flightnumber" scope="request"/>
							<ibusiness:flightnumber carrierCodeProperty="carrierCode"
							  id="flightNo"
							  flightCodeProperty="flightNo"
							  componentID="CMP_MAILTRACKING_MRA_DEFAULTS_FLIGHTNO"
							  carrierCodeStyleClass="iCargoTextFieldVerySmall"
							  flightCodeStyleClass="iCargoTextFieldSmall"
							  />
						</div>
						<div class="ic-input ic-split-30 ic-mandatory">
							<common:message key="mailtracking.mra.defaults.viewflightsectorrevenue.lbl.flightdate" scope="request"/>
							<ibusiness:calendar
								property="flightDate"
								componentID="CMP_MAILTRACKING_MRA_DEFAULTS_FLIGHTDATE"
								type="image"
								id="flightDate"
							/>
						</div>
						<div class="ic-button-container">
							<ihtml:nbutton property="btnList" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_LSTBTN" >
								<common:message key="mailtracking.mra.defaults.viewflightsectorrevenue.lbl.button.list" />
							</ihtml:nbutton>
							<ihtml:nbutton property="btnClear" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_CLEARBTN" >
								<common:message key="mailtracking.mra.defaults.viewflightsectorrevenue.lbl.button.clear" />
							</ihtml:nbutton>
						</div>	
						<div class="ic-input ic-split-30 ">	
							<common:message key="mailtracking.mra.defaults.viewflightsectorrevenue.lbl.flightstatus" scope="request"/>
							<ihtml:text componentID="CMP_MAILTRACKING_MRA_DEFAULTS_FLIGHTSTATUS" property="flightStatus" readonly="true" />
						</div>
					</div>
				</div>
				<div class="ic-row ic-border">
					<div class="ic-input ic-split-30 ic-mandatory">
						<common:message key="mailtracking.mra.defaults.viewflightsectorrevenue.lbl.sector" scope="request"/>
						<ihtml:select property="sector" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_SECTOR" >
							<logic:present name="KEY_SECTORDETAILS">
								<logic:iterate id="segment" name="KEY_SECTORDETAILS">
								  <logic:present name="segment" property="segmentOrigin">
								  <logic:present name="segment" property="segmentDestination">
									<bean:define id="segmentSerialNumber" name="segment" property="segmentSerialNumber" />
									<bean:define id="segmentOrigin" name="segment" property="segmentOrigin" />
									<bean:define id="segmentDestination" name="segment" property="segmentDestination" />
									<%String str= segmentOrigin+"-"+segmentDestination;%>

									<html:option value="<%=str%>">
										<bean:write name="segment" property="segmentOrigin"/>
										-
										<bean:write name="segment" property="segmentDestination"/>
									</html:option>
								  </logic:present>
								  </logic:present>
							    </logic:iterate>
						    </logic:present>
							<logic:notEqual name="ViewMailFlightSectorRevenueForm"  property="listSegmentsFlag" value="Y">
								<html:option value="">
								</html:option>
							</logic:notEqual>
						</ihtml:select>
					</div>
					<div class="ic-input ic-split-30 ">
						 <common:message key="mailtracking.mra.defaults.viewflightsectorrevenue.lbl.flightsectorstatus" scope="request"/>
						 <ihtml:text componentID="CMP_MAILTRACKING_MRA_DEFAULTS_FLIGHTSECTORSTATUS" property="flightSectorStatus" readonly="true" />
					</div>
					<div class="ic-input ic-split-30 ">
						<ihtml:nbutton property="btnRevdtl" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_REVDTLBTN" >
							<common:message key="mailtracking.mra.defaults.viewflightsectorrevenue.lbl.button.revenuedetails" />
						</ihtml:nbutton>
					</div>
				</div>
			</div>
			<div class="ic-main-container">
				<div class="ic-row">
					<h4><common:message key="mailtracking.mra.defaults.viewflightsectorrevenue.lbl.dtlheading" /></h4>
				</div>
				<div class="ic-row">
					<div class="ic-section ic-border">
					<div id="div2" class="tableContainer" style="width:100%;height:370px;">
					   <table class="fixed-header-table">
						   <thead>
								<tr>
									<td  class="iCargoTableHeader"  width="2%"><input type="checkbox" name="headChk"  value="checkbox" onclick="updateHeaderCheckBox(this.form,this.form.headChk,this.form.selectedRows)"/>
									<td class="iCargoTableHeader"   width="16%">
										<common:message key="mailtracking.mra.defaults.viewflightsectorrevenue.lbl.dsn" />
									</td>

									<td class="iCargoTableHeader"   width="16%">
									<common:message key="mailtracking.mra.defaults.viewflightsectorrevenue.lbl.errorpresent" /></td>

									<td class="iCargoTableHeader" width="16%">
									<common:message key="mailtracking.mra.defaults.viewflightsectorrevenue.lbl.grossweight" /></td>

									<td class="iCargoTableHeader" width="16%">
									<common:message key="mailtracking.mra.defaults.viewflightsectorrevenue.lbl.currency" /></td>

									<td class="iCargoTableHeader" width="16%">
									<common:message key="mailtracking.mra.defaults.viewflightsectorrevenue.lbl.weightcharge" /></td>

									<td class="iCargoTableHeader" width="16%">
									<common:message key="mailtracking.mra.defaults.viewflightsectorrevenue.lbl.netrevenue" /></td>
								 </tr>
		  			 		</thead>
						   <tbody>
							   <logic:present name="KEY_REVENUEDETAILS">
								 <logic:iterate id = "sectorRevenueDetailsVO" name="KEY_REVENUEDETAILS" indexId="rowCount" scope="page" type="com.ibsplc.icargo.business.mail.mra.defaults.vo.SectorRevenueDetailsVO">
								  <tr class="iCargoTableDataRow1">
									<td  class="iCargoTableTd"> <ihtml:checkbox property="selectedRows"  onclick="toggleTableHeaderCheckbox('selectedRows',this.form.headChk)" value="<%=String.valueOf(rowCount)%>" /></td>
									<td align="center">
									  <center>
										<logic:present name="sectorRevenueDetailsVO" property="dsn">
										<bean:write name="sectorRevenueDetailsVO" property="dsn"/>
										</logic:present>
									  </center>
					    			</td>
					    			<td>
					    				<center>
					    				<logic:present name="sectorRevenueDetailsVO" property="errorPresent">
										 <bean:define id="errorPresent" name="sectorRevenueDetailsVO" property="errorPresent" />
											<logic:equal name ="errorPresent" value="M" >
												<input name="errorPresent" type="checkbox"  checked="checked"  value="M" disabled="true" onclick="singleSelect(this)"/>
											</logic:equal>
											<logic:equal name="errorPresent" value="D">
												  <input name="errorPresent"  type="checkbox" value="D"  disabled ="true" onclick="singleSelect(this)"/>
											</logic:equal>
									   </logic:present>
					    			   </center>
					    			</td>
					    			<td align="center">
									  <center>
										<logic:present name="sectorRevenueDetailsVO" property="grossWeight">
										<bean:write name="sectorRevenueDetailsVO" property="grossWeight" format="####.00"/>
										</logic:present>
									  </center>
					    			</td>
					    			<td align="center">
									  <center>
										<logic:present name="sectorRevenueDetailsVO" property="currency">
										<bean:write name="sectorRevenueDetailsVO" property="currency"/>
										</logic:present>
									  </center>
					    			</td>
					    			<td align="center">
									  <center>
										<logic:present name="sectorRevenueDetailsVO" property="weightChargeBase">
										<ibusiness:moneyDisplay showCurrencySymbol="false" name="sectorRevenueDetailsVO"  moneyproperty="weightChargeBase" property="weightChargeBase" />																			
										</logic:present>
									  </center>
					    			</td>
					    			<td align="center">
									  <center>
										<logic:present name="sectorRevenueDetailsVO" property="netRevenue">
											<ibusiness:moneyDisplay showCurrencySymbol="false" name="sectorRevenueDetailsVO"  moneyproperty="netRevenue" property="netRevenue" />																			
										</logic:present>
									  </center>
					    			</td>
					    		  </tr>
					    		 </logic:iterate>
							   </logic:present>

							</tbody>
							<!--Footer Begins-->
							<tfoot>
								<tr>
									<td colspan="2">&nbsp;</td>
									<td >&nbsp;</td>
									<td  id="grossWeight">
										<bean:define id ="grossWeight"  value="<%=new Formatter().format(FORMAT_STRING,form.getTotGrossWeight()).toString().trim()%>"/>
										<common:write  name="grossWeight" format="####.00"/>
									</td>
									<td >&nbsp;</td>
									<td  id="weightCharge">
										<bean:define id ="weightCharge"  value="<%=new Formatter().format(FORMAT_STRING,form.getTotWeightCharge()).toString().trim()%>"/>
										<common:write  name="weightCharge" />
									</td>
									<td  id="netRevenue">
										<bean:define id ="netRevenue"  value="<%=new Formatter().format(FORMAT_STRING,form.getTotNetRevenue()).toString().trim()%>"/>
										<common:write  name="netRevenue" />
									</td>
								</tr>
							</tfoot>
							 <!--Footer Ends-->
						</table>
					</div>
						<div class="ic-button-container">
				<ihtml:nbutton property="btnViewExceptions" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_VIEWEXCEPTIONS" >
					<common:message key="mailtracking.mra.defaults.viewflightsectorrevenue.lbl.button.viewExceptions"/>
				</ihtml:nbutton>
				<ihtml:nbutton property="btnAccDetails" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_LISTACCDTLSBTN">
				<common:message key="mailtracking.mra.defaults.viewflightsectorrevenue.lbl.button.listaccdtl" />
				</ihtml:nbutton>

				<ihtml:nbutton property="btnClose" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_CLOSEBTN">
				<common:message key="mailtracking.mra.defaults.viewflightsectorrevenue.lbl.button.close" />
				</ihtml:nbutton>
			</div>
					</div>
				</div>	
			</div>
		</div>
	</ihtml:form>
</div>	
		
	
	</body>
</html>
