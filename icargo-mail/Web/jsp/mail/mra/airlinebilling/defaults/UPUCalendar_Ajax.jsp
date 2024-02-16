<%--/***********************************************************************
* Project	     	 : iCargo
* Module Code & Name 	 : AirlineBilling
* File Name          	 : UPUCalendar.jsp
* Date                 	 : 27-jan-2007
* Author(s)              : A-2521
*************************************************************************/
--%>
<%
	response.setDateHeader("Expires",0);
	response.setHeader("Pragma","no-cache");

	if (request.getProtocol().equals("HTTP/1.1")) {
		response.setHeader("Cache-Control","no-cache");
	}
%>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>
<%@ page import = "com.ibsplc.icargo.business.mail.mra.airlinebilling.defaults.vo.UPUCalendarVO" %>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>

<div class="iCargoContent" style="overflow:auto;">

	

	 	<business:sessionBean id="KEY_UPUCALENDERVOS" moduleName="mailtracking.mra" screenID="mailtracking.mra.airlinebilling.defaults.upucalendar" method="get" attribute="uPUCalendarVOs" />

		<div class="tableContainer" style="width:99%;height:250px;">
		  <table width="100%" class="icargotable1" id="casscalendar">
		    <thead>
			 <tr>
				<td  class="iCargoTableHeader" width="1%"><input type="checkbox" name="allCheck" value="checkbox" onclick="updateHeaderCheckBox(this.form,document.forms[1].allCheck, document.forms[1].rowCount)" /></td>
				<td  class="iCargoTableHeader" width="5%"><common:message key="mra.airlinebilling.defaults.upucalendar.clearanceperiod"/></td>
				<td  class="iCargoTableHeader" width="4%"><common:message key="mra.airlinebilling.defaults.upucalendar.fromdate"/></td>
				<td  class="iCargoTableHeader" width="4%"><common:message key="mra.airlinebilling.defaults.upucalendar.todate"/></td>
				<td  class="iCargoTableHeader" width="4%"><common:message key="mra.airlinebilling.defaults.upucalendar.submissiondate"/></td>
				<td  class="iCargoTableHeader" width="3%"><common:message key="mra.airlinebilling.defaults.upucalendar.generateafter"/></td>
			 </tr>
		    </thead>
		    <tbody>

				<logic:present name="KEY_UPUCALENDERVOS">
				<logic:iterate id="upuVo" name="KEY_UPUCALENDERVOS" indexId="rowIndex">

					<logic:notEqual property="operationalFlag" name="upuVo"  value="D">
					<html:hidden property="operationalFlag" name="upuVo"/>
					<common:rowColorTag index="rowIndex">

					<tr bgcolor="<%=color%>">

						<td>
						<div align="center">
							<html:checkbox   property="rowCount" value="<%= String.valueOf(rowIndex) %>" />
						</div>
						</td>

						<logic:equal property="operationalFlag" name="upuVo"  value="I">

							<td>
								<logic:present name="upuVo" property="billingPeriod">
									<ihtml:text indexId="rowIndex" name="upuVo" componentID="CMP_MRA_DEFAULTS_UPUCALENDAR_BILLINGPERIOD" property="billingPeriod"  maxlength="10" styleClass="iCargoEditableTextFieldRowColor1" style="background:<%=color%>" readonly="false"/>
								</logic:present>
								<logic:notPresent name="upuVo" property="billingPeriod">
									<ihtml:text indexId="rowIndex" name="upuVo" componentID="CMP_MRA_DEFAULTS_UPUCALENDAR_BILLINGPERIOD" property="billingPeriod"  value="" maxlength="10" styleClass="iCargoEditableTextFieldRowColor1" style="background:<%=color%>" readonly="false"/>
								</logic:notPresent>
							</td>
						</logic:equal>

						<logic:notEqual property="operationalFlag" name="upuVo"  value="I">

							<td>
								<logic:present name="upuVo" property="billingPeriod">
									<ihtml:text indexId="rowIndex" name="upuVo" componentID="CMP_MRA_DEFAULTS_UPUCALENDAR_BILLINGPERIOD" property="billingPeriod"  maxlength="10"  styleClass="iCargoEditableTextFieldRowColor1" style="background:<%=color%>" readonly="true"/>
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
							<ihtml:text indexId="rowIndex" name="upuVo" componentID="CMP_MRA_DEFAULTS_UPUCALENDAR_GENERATENEXT" property="generateAfterToDate"  value="" maxlength="3" styleClass="iCargoEditableTextFieldRowColor1" style="background:<%=color%>" readonly="false"/>
						</logic:equal>

						<logic:notEqual name="upuVo" property="generateAfterToDate" value="0">
							<ihtml:text indexId="rowIndex" name="upuVo" componentID="CMP_MRA_DEFAULTS_UPUCALENDAR_GENERATENEXT" property="generateAfterToDate"  maxlength="3" styleClass="iCargoEditableTextFieldRowColor1" style="background:<%=color%>" readonly="false"/>
						</logic:notEqual>

						</logic:present>
						</td>
					</tr>
					</common:rowColorTag>
					</logic:notEqual>
				</logic:iterate>
				</logic:present>

				</tbody>
			</table>
		</div>


	</div>

	<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>
