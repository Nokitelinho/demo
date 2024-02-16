<%
	response.setDateHeader("Expires",0);
	response.setHeader("Pragma","no-cache");

	if (request.getProtocol().equals("HTTP/1.1")) {
		response.setHeader("Cache-Control","no-cache");
	}
%>
<%@ include file="/jsp/includes/tlds.jsp" %>

<%@ page import="java.util.Collection" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ManufacturerLovForm" %>


<html:html>
<head>
<title>Select Manufacturer</title>

<meta name="decorator" content="popup_panel">

</head>

<body>
	<div class="iCargoPopUpContent">
		<ihtml:form action="/uld.defaults.findmanufacturerlov.do" styleclass="ic-main-form">
			<html:hidden name="ManufacturerLovForm" property="selectedValues"  />
			<html:hidden name="ManufacturerLovForm" property="formCount" />
			<html:hidden name="ManufacturerLovForm" property="lovTxtFieldName" />
			<html:hidden name="ManufacturerLovForm" property="index" />
			<bean:define id="strFormCount" name="ManufacturerLovForm" property="formCount"  />
			<bean:define id="strLovTxtFieldName"  name="ManufacturerLovForm" property="lovTxtFieldName" />
			<bean:define id="strSelectedValues" name="ManufacturerLovForm" property="selectedValues" />
			<bean:define id="arrayIndex" name="ManufacturerLovForm" property="formCount"/>
			<bean:define id="fieldIndex" name="ManufacturerLovForm" property="index"/>

			<div class="ic-content-main">
				<span class="ic-page-title ic-display-none">
					Manufacturer
				</span>
				
				<div class="ic-main-container">
					<div id="div1" class="tableContainer"  style="height:340px;width:100%">
						<table cellspacing="0" class="fixed-header-table">
						<thead>
							<tr class="iCargoTableHeadingLeft">
							<td width="3%"> &nbsp;</td>
							<td width="26%" class="iCargoLabelleftAligned">Manufacturer</td>
							</tr>
						 </thead>
						 <tbody>

						<logic:present name="ManufacturerLovForm" property="manufacturer">
							<bean:define id="manuf" name="ManufacturerLovForm"  property="manufacturer"  toScope="page"/>
							<logic:iterate id = "val" name="manuf" indexId="nIndex">
							
							   <tr>
							   <a href="#" ondblclick="setValueOnDoubleClick('<%=val%>','<%=val%>',
											'<%= strLovTxtFieldName%>',document.forms[0].lovTxtFieldName.value,'<%=fieldIndex%>')" tabindex="-1"/>


									<td width="3%" class="iCargoTableDataTd">




									   <input type="checkbox" name="selectCheckBox" value="<%=val%>" onclick="singleSelect('<%=val%>')" />
									</td>
									<td width="26%" align="left" class="iCargoTableDataTd">
									   <%=val%>
									</td>
							  </tr>
								
							</logic:iterate>
							</logic:present>

						 </tbody>
						 </table>
					</div>
				</div>
				
				<div class="ic-foot-container">
					<div class="ic-button-container">
						<input type="button" name="okBut"  onclick="setValue('<%=strLovTxtFieldName%>',document.forms[0].lovTxtFieldName.value,'<%=fieldIndex%>')" value="OK" class="iCargoButtonSmall" />
				<input type="button" name="closeBut"  value="Close" onclick="window.close()"  class="iCargoButtonSmall" />
					</div>
				</div>
			</div>

		</ihtml:form>
	</div>
</body>
</html:html>


