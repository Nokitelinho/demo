<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="org.apache.struts.action.ActionMessages"%>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.UPUCalendarForm"%>
<%@ page import = "com.ibsplc.icargo.business.mail.mra.airlinebilling.defaults.vo.UPUCalendarVO" %>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>



		
	
<html>
	<head>

	
		
	
		<common:include type="script" src="/js/mail/mra/airlinebilling/defaults/UPUCalendar_Script.jsp" />
		<title><common:message bundle="UPUCalendarResources" key="mra.airlinebilling.defaults.upucalendar.title" /></title>
		<meta name="decorator" content="mainpanel">
	</head>



	<body style="overflow:auto;">
	
	
		
	
		<%
		boolean nonEditable = true;
		boolean unchanged = false;
		%>
	<business:sessionBean id="KEY_UPUCALENDERVOS" moduleName="mailtracking.mra" screenID="mailtracking.mra.airlinebilling.defaults.upucalendar" method="get" attribute="uPUCalendarVOs" />
	<!--CONTENT STARTS-->
	<div class="iCargoContent">
		<ihtml:form action="/mailtracking.mra.airlinebilling.defaults.onscreenloadUPUCalendar.do">
			<div class="ic-content-main">
				<span class="ic-page-title ic-display-none"><common:message key="mra.airlinebilling.defaults.upucalendar"/></span>
				<div class="ic-head-container">
					<div class="ic-filter-panel">
						<div class="ic-input-container">
							<div class="ic-section ic-border">
								
									<div class="ic-row ">
										<div class="ic-input ic-split-20 ">
										</div>
										<div class="ic-input ic-split-40 ">
											<common:message key="mra.airlinebilling.defaults.upucalendar.fromdate"/>
											<ibusiness:calendar componentID="CMP_MRA_DEFAULTS_UPUCALENDAR_FROM_DATE" property="fromDateLst" type="image" id="fromDateLst" />
										</div>
										<div class="ic-input ic-split-40 ">
											<common:message key="mra.airlinebilling.defaults.upucalendar.todate"/>
											<ibusiness:calendar componentID="CMP_MRA_DEFAULTS_UPUCALENDAR_TO_DATE" property="toDateLst" type="image" id="toDateLst" />	
										</div>
									</div>
									<div class="ic-row ">
										<div class="ic-button-container">
											<ihtml:nbutton property="btnList" accesskey="L" componentID="CMP_MRA_DEFAULTS_UPUCALENDAR_BTLIST" >
												<common:message key="mra.airlinebilling.defaults.btn.list"/>
											</ihtml:nbutton>
											<ihtml:nbutton property="btnClear" accesskey="C" componentID="CMP_MRA_DEFAULTS_UPUCALENDAR_BTCLEAR" >
												<common:message key="mra.airlinebilling.defaults.btn.clear"/>
											</ihtml:nbutton>
										</div>
									</div>
								
							</div>
						</div>
					</div>
				</div>
				<div class="ic-main-container">	
					<div class="ic-row">
						<div>
							<b><common:message key="mra.airlinebilling.defaults.upucalendar.icargoheadingdetail"/></b>
						</div>
						<div class="ic-button-container">
							<a href="#" class="iCargoLink" id="addLink" onclick="onAddLink()">Add
							</a>
							|  <a href="#" class="iCargoLink" id="deleteLink" onclick="onDeleteLink()">Delete
							</a>
						</div>
					</div>		
					<div class="ic-row">	
					       <div class="tableContainer" id="div1" style="width:100%;overflow:auto;height:650px;"> 
							  <table width="100%" class="fixed-header-table" id="casscalendar" >
							    <thead>
								 <tr>
									<td  class="iCargoTableHeader" width="1%" ><input type="checkbox" name="allCheck" value="checkbox" onclick="updateHeaderCheckBox(this.form,document.forms[1].allCheck, document.forms[1].rowCount)" /></td>
									<td  class="iCargoTableHeader" width="5%" ><common:message key="mra.airlinebilling.defaults.upucalendar.clearanceperiod"/></td>
									<td  class="iCargoTableHeader" width="4%"><common:message key="mra.airlinebilling.defaults.upucalendar.fromdate"/></td>
									<td  class="iCargoTableHeader" width="4%"><common:message key="mra.airlinebilling.defaults.upucalendar.todate"/></td>
									<td  class="iCargoTableHeader" width="4%"><common:message key="mra.airlinebilling.defaults.upucalendar.submissiondate"/></td>
									<td  class="iCargoTableHeader" width="3%"><common:message key="mra.airlinebilling.defaults.upucalendar.generateafter"/></td>
								 </tr>
							    </thead>
							    <tbody id="targetWeightTableBody">

									<logic:present name="KEY_UPUCALENDERVOS">
									<logic:iterate id="upuVo" name="KEY_UPUCALENDERVOS" indexId="rowIndex">

										<logic:notEqual property="operationalFlag" name="upuVo"  value="D">
										 <%System.out.println("opflag-->not D");%>
										<html:hidden property="operationalFlag" name="upuVo"/>
										<html:hidden property="lastUpdateTime" name="upuVo"/>
										<html:hidden property="lastUpdateUser" name="upuVo"/>
										 <common:rowColorTag index="rowIndex">
										<% unchanged = false; %>
										<tr bgcolor="<%=color%>">
										

											<td>
											<html:checkbox   property="rowCount" value="<%= String.valueOf(rowIndex) %>" onclick="toggleTableHeaderCheckbox('rowCount',document.forms[1].allCheck)" />
											</td>

											<logic:equal property="operationalFlag" name="upuVo"  value="I">
											  	<td>
													<logic:present name="upuVo" property="billingPeriod">
														<ihtml:text indexId="rowIndex" name="upuVo" componentID="CMP_MRA_DEFAULTS_UPUCALENDAR_BILLINGPERIOD" property="billingPeriod"  maxlength="10" styleClass="iCargoEditableTextFieldRowColor1" readonly="false"/>
													</logic:present>
													<logic:notPresent name="upuVo" property="billingPeriod">
														<ihtml:text indexId="rowIndex" name="upuVo" componentID="CMP_MRA_DEFAULTS_UPUCALENDAR_BILLINGPERIOD" property="billingPeriod"  value="" maxlength="10" styleClass="iCargoEditableTextFieldRowColor1" readonly="false"/>
													</logic:notPresent>
												</td>
											</logic:equal>

											<logic:notEqual property="operationalFlag" name="upuVo"  value="I">

												<td>
													<logic:present name="upuVo" property="billingPeriod">
														<ihtml:text indexId="rowIndex" name="upuVo" componentID="CMP_MRA_DEFAULTS_UPUCALENDAR_BILLINGPERIOD" property="billingPeriod"  maxlength="10"  styleClass="iCargoEditableTextFieldRowColor1" readonly="true"/>
													</logic:present>
												</td>
											</logic:notEqual>

											<td>
											<logic:present name="upuVo" property="fromDate">
											<bean:define id="fromDate" name="upuVo" property="fromDate" type="LocalDate"/>
											<% String fromDate_str = TimeConvertor.toStringFormat(((LocalDate)fromDate).toCalendar(),"dd-MMM-yyyy"); %>
											<ibusiness:calendar indexId="rowIndex"  componentID="CMP_MRA_DEFAULTS_UPUCALENDAR_FROM_DATE_TABLE" property="fromDate" value="<%= fromDate_str %>" type="image" id="fromDateTB" />
											</logic:present>

											<logic:notPresent name="upuVo" property="fromDate">
											<ibusiness:calendar  indexId="rowIndex" componentID="CMP_MRA_DEFAULTS_UPUCALENDAR_FROM_DATE_TABLE" property="fromDate" type="image" value="" id="fromDateDB" />
											</logic:notPresent>

											</td>

											<td>
											<logic:present name="upuVo" property="toDate">
											<bean:define id="toDate" name="upuVo" property="toDate" type="LocalDate"/>
											<% String toDate_str = TimeConvertor.toStringFormat(((LocalDate)toDate).toCalendar(),"dd-MMM-yyyy"); %>
											<ibusiness:calendar  indexId="rowIndex" componentID="CMP_MRA_DEFAULTS_UPUCALENDAR_TO_DATE_TABLE" property="toDate" value="<%= toDate_str %>" type="image" id="toDateTB" />
											</logic:present>

											<logic:notPresent name="upuVo" property="toDate">
												<ibusiness:calendar  indexId="rowIndex" componentID="CMP_MRA_DEFAULTS_UPUCALENDAR_TO_DATE_TABLE" property="toDate" value="" type="image" id="toDateTB" />
											</logic:notPresent>

											</td>

											<td>
											<logic:present name="upuVo" property="submissionDate">
											<bean:define id="submissionDate" name="upuVo" property="submissionDate" type="LocalDate"/>
											<% String submissionDate_str = TimeConvertor.toStringFormat(((LocalDate)submissionDate).toCalendar(),"dd-MMM-yyyy"); %>
											<ibusiness:calendar indexId="rowIndex" componentID="CMP_MRA_DEFAULTS_UPUCALENDAR_SUBMISSION_DATE_TABLE" property="submissionDate"  value="<%= submissionDate_str %>" type="image" id="subDateTB" />
											</logic:present>

											<logic:notPresent name="upuVo" property="submissionDate">
												<ibusiness:calendar indexId="rowIndex" componentID="CMP_MRA_DEFAULTS_UPUCALENDAR_SUBMISSION_DATE_TABLE" property="submissionDate" value="" type="image" id="subDateTB" />
											</logic:notPresent>

											</td>

											<td>
											<logic:present name="upuVo" property="generateAfterToDate">

											<logic:equal name="upuVo" property="generateAfterToDate" value="0">
												<ihtml:text indexId="rowIndex" name="upuVo" componentID="CMP_MRA_DEFAULTS_UPUCALENDAR_GENERATENEXT" property="generateAfterToDate"  value="" maxlength="3" styleClass="iCargoEditableTextFieldRowColor1" readonly="false"/>
											</logic:equal>

											<logic:notEqual name="upuVo" property="generateAfterToDate" value="0">
												<ihtml:text indexId="rowIndex" name="upuVo" componentID="CMP_MRA_DEFAULTS_UPUCALENDAR_GENERATENEXT" property="generateAfterToDate"  maxlength="3" styleClass="iCargoEditableTextFieldRowColor1" readonly="false"/>
											</logic:notEqual>

											</logic:present>
											</td>
										</tr>
										</common:rowColorTag>
										</logic:notEqual>
									</logic:iterate>
									</logic:present>
									
									<!-- template row starts-->
										   <bean:define id="templateRowCount" value="0"/>
										   <tr template="true" id="targetWeightRow" style="display:none">
											<td width="1%" class="iCargoTableDataTd" >
												<html:checkbox property="rowCount"/>
												<ihtml:hidden property="operationalFlag" value="NOOP"/>
												<%System.out.println("opflag-->no op");%>
																													
											</td>
											<td width="1%" class="iCargoTableDataTd">
											       <ihtml:text indexId="templateRowCount"  value="" componentID="CMP_MRA_DEFAULTS_UPUCALENDAR_BILLINGPERIOD" property="billingPeriod"  maxlength="10"  />
											</td>
											<td width="1%" class="iCargoTableDataTd">
											      
											        <ibusiness:calendar   componentID="CMP_MRA_DEFAULTS_UPUCALENDAR_FROM_DATE_TABLE" property="fromDate" value=""  type="image" id="fromDateTB" indexId="templateRowCount"/>
											</td>
											<td width="2%" class="iCargoTableDataTd">
												<ibusiness:calendar   componentID="CMP_MRA_DEFAULTS_UPUCALENDAR_TO_DATE_TABLE" property="toDate" value="" type="image" id="toDateTB" indexId="templateRowCount"/>
											</td>
											<td width="2%" class="iCargoTableDataTd">
												<ibusiness:calendar  componentID="CMP_MRA_DEFAULTS_UPUCALENDAR_SUBMISSION_DATE_TABLE" property="submissionDate"  value="" type="image" id="subDateTB" indexId="templateRowCount"/>
											</td>
											<td width="2%" class="iCargoTableDataTd">
											       <ihtml:text   componentID="CMP_MRA_DEFAULTS_UPUCALENDAR_GENERATENEXT" property="generateAfterToDate"  value="" maxlength="3"  readonly="false" indexId="templateRowCount"/>
											</td>
											
										   </tr>
									<!-- template row ends -->

									</tbody>
								</table>
							</div>
						</div>
						<div class="ic-foot-container">
							<div class="ic-button-container">
								<ihtml:nbutton property="btnSave" accesskey="S" componentID="CMP_MRA_DEFAULTS_UPUCALENDAR_BTSAVE">
									<common:message key="mra.airlinebilling.defaults.btn.save"/>
								</ihtml:nbutton>
								<ihtml:nbutton property="btnClose" accesskey="O" componentID="CMP_MRA_DEFAULTS_UPUCALENDAR_BTCLOSE">
									<common:message key="mra.airlinebilling.defaults.btn.close"/>
								</ihtml:nbutton>
							</div>
						</div>	
						</div>
					</div>	 
		</ihtml:form>
	</div>
	<!---CONTENT ENDS-->
	
		
	</body>
</html>
