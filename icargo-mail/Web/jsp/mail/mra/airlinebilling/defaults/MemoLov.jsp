<%--****************************************************************
* Project	 				: iCargo
* Module Code & Name				: Mra-AirlineBilling-Defaults
* File Name					: MemoLov.jsp
* Date						: 30/11/2006
* Author(s)					: A-2524
 *********************************************************************--%>
<%
	response.setDateHeader("Expires",0);
	response.setHeader("Pragma","no-cache");

	if (request.getProtocol().equals("HTTP/1.1")) {
		response.setHeader("Cache-Control","no-cache");
	}
%>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.MemoLOVForm" %>
<%@ page import="com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.MemoLovVO" %>

	
<html:html>
<head> 
	
	
<common:include type="script" src="/js/mail/mra/airlinebilling/defaults/MemoLov_Script.jsp" />
<title><common:message  bundle="MemoLovResource" key="mra.airlinebilling.defaults.memolov.title" scope="request"/></title>
<meta name="decorator" content="popuppanelrestyledui"> <!-- Modified by A-8236 for ICRD-252198 -->
</head>

<body id="bodyStyle">
	
	
	
<!--CONTENT STARTS-->
<div id="divmain" class="iCargoPopUpContent"  >
<ihtml:form action="/mra.airlinebilling.defaults.showMemoLov.do" styleClass="ic-main-form">

	<bean:define id="MemoLovForm" name="MemoLOVForm"
		     type ="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.MemoLOVForm"/>
	<ihtml:hidden  property="lovaction"  />
	<ihtml:hidden  property="selectedValues"  />
	<ihtml:hidden  property="lastPageNum" />
	<ihtml:hidden  property="displayPage" />
	<ihtml:hidden  property="multiselect" />
	<ihtml:hidden  property="pagination" />
	<ihtml:hidden  property="formCount" />
	<ihtml:hidden  property="lovTxtFieldName" />
	<ihtml:hidden  property="lovDescriptionTxtFieldName" />
	<ihtml:hidden  property="index" />
	<ihtml:hidden  property="strClearancePeriod" />
	<ihtml:hidden  property="strInvoiceNumber" />
	<ihtml:hidden  property="strAirlineCode" />
	<ihtml:hidden  property="strMemoNo" />

	<%String strLovNameTxtFieldName = "";%>
	
	<div class="ic-content-main">
			<span class="ic-page-title ic-display-none">
				<common:message key="mra.airlinebilling.defaults.memolov.page"/>
			</span>
	<div class="ic-head-container">		
					<div class="ic-filter-panel">
						<div class="ic-row">
							<div class="ic-input">
								<label>
									<common:message key="mra.airlinebilling.defaults.memolov.memocode" />&nbsp;
								</label>
									<ihtml:text name="MemoLOVForm" property="code" componentID="CMP_MRA_AIRLINEBILLING_MEMOLOV_MEMONUMBER" maxlength="18" readonly="false"/>
							</div>
							<div class="ic-button-container">
								<ihtml:nbutton property="btnList" componentID="CMP_MRA_AIRLINEBILLING_MEMOLOV_LIST" >
									<common:message key="mra.airlinebilling.defaults.memolov.btn.list"/>
								</ihtml:nbutton>
								<ihtml:nbutton property="btnClear" componentID="CMP_MRA_AIRLINEBILLING_MEMOLOV_CLEAR" >
									<common:message key="mra.airlinebilling.defaults.memolov.btn.clear"/>
								</ihtml:nbutton>
							</div>
						</div>
					</div>	
				
			</div>
			<div class="ic-main-container">
		<div class="ic-row">
	   <logic:present name="MemoLovForm"   property="memoLovVos">
	   		<bean:define id="memoLovVos" name="MemoLovForm"  property="memoLovVos"  toScope="page"/>

	   		
	   			<logic:present name="memoLovVos">
	   			<common:paginationTag
	   				pageURL="javascript:submitList('lastPageNum','displayPage')"
	   				name="memoLovVos"
	   				display="label"
	   				labelStyleClass="iCargoResultsLabel"
	   				lastPageNum="<%=MemoLovForm.getLastPageNum() %>" />

	   			
	   			<div class="ic-button-container"> <!-- Modified by A-8236 for ICRD-252198 -->
	   			<common:paginationTag
	   				pageURL="javascript:submitList('lastPageNum','displayPage')"
	   				name="memoLovVos"
	   				display="pages"
					linkStyleClass="iCargoLink" disabledLinkStyleClass="iCargoLink"	
	   				lastPageNum="<%=MemoLovForm.getLastPageNum()%>"/>
	   			</logic:present>
	   			</div>
	   			
		</logic:present>
		</div>

	  <div class="ic-row">

			<bean:define id="strFormCount" name="MemoLovForm" property="formCount"  />
			<bean:define id="strLovTxtFieldName"  name="MemoLovForm" property="lovTxtFieldName" />
			<bean:define id="strLovDescriptionTxtFieldName" name="MemoLovForm" property="lovDescriptionTxtFieldName" />
			<bean:define id="strSelectedValues" name="MemoLovForm" property="selectedValues" />
			<bean:define id="arrayIndex" name="MemoLovForm" property="index"/>
			<bean:define id="strMultiselect" name="MemoLovForm" property="multiselect" />
				<logic:present name="MemoLovForm" property="lovNameTxtFieldName">
				<bean:define id="strLovNameTextFieldName" name="MemoLovForm" property="lovNameTxtFieldName" />
				<%strLovNameTxtFieldName = (String)strLovNameTextFieldName;%>
				</logic:present>




	 <div class="tableContainer"  id="div1" style="height:330px; width:100%;">
						<table  class="fixed-header-table">
							<thead>
								<tr class="iCargoTableHeadingCenter">
		<td  class="iCargoTableHeader" width="1%" >&nbsp;</td>
		<td  class="iCargoTableHeader" width="5%" ><common:message key="mra.airlinebilling.defaults.memolov.memocode"/></td>
		<td  class="iCargoTableHeader" width="4%"><common:message key="mra.airlinebilling.defaults.memolov.clearanceperiod"/></td>
		<td  class="iCargoTableHeader" width="4%"><common:message key="mra.airlinebilling.defaults.memolov.invoicenumber"/></td>
		<td  class="iCargoTableHeader" width="4%"><common:message key="mra.airlinebilling.defaults.memolov.airlinecode"/></td>
	    </tr>
	    </thead>
		 <tbody>

		<logic:present name="memoLovVos">
		<logic:iterate id="memoLovVo" name="memoLovVos" indexId="rowIndex">
		<common:rowColorTag index="rowIndex">
		<tr bgcolor="<%=color%>">
			<logic:notEqual name="MemoLovForm" property="multiselect" value="Y">
				<a href="#" ondblclick="setValueOnDoubleClick('<%=((MemoLovVO)memoLovVo).getMemoCode()%>','<%=((MemoLovVO)memoLovVo).getClearancePeriod()%>',
				'<%= strLovTxtFieldName%>','<%= strLovDescriptionTxtFieldName%>',<%=arrayIndex%>)"/>
			</logic:notEqual>
			<logic:equal name="MemoLovForm" property="multiselect" value="Y">
			<td width="3%">
			<%
			if(((String)strSelectedValues).contains(((MemoLovVO)memoLovVo).getMemoCode() )){ %>
				<ihtml:hidden name="MemoLovForm" property="description" value="<%=((MemoLovVO)memoLovVo).getClearancePeriod()%>"/>
				<input type="checkbox" name="selectCheckBox" value="<%=(((MemoLovVO)memoLovVo).getMemoCode())%>"  checked="checked"/>
			<%}else{ %>
				<ihtml:hidden name="MemoLovForm" property="description" value="<%=((MemoLovVO)memoLovVo).getClearancePeriod()%>"/>
				<input type="checkbox" name="selectCheckBox" value="<%=((MemoLovVO)memoLovVo).getMemoCode()%>"  />
			<% } %>
			</td>
			</logic:equal>

			<logic:notEqual name="MemoLovForm" property="multiselect" value="Y">
			<td width="3%">
				<%String checkVal = ((MemoLovVO)memoLovVo).getMemoCode()+"-"+((MemoLovVO)memoLovVo).getClearancePeriod();%>

			<%
			if(   ((String)strSelectedValues).equals(( (MemoLovVO)memoLovVo).getMemoCode() )){ %>
				<input type="checkbox" name="selectCheckBox" value="<%=checkVal%>"  onclick="singleSelect('<%=checkVal%>')" checked="checked" />
			<%}else{ %>
				<input type="checkbox" name="selectCheckBox" value="<%=checkVal%>"  onclick="singleSelect('<%=checkVal%>')"/>
			<% } %>


			</td>
			</logic:notEqual>
			<td width="30%" align="left">
				<bean:write name="memoLovVo" property="memoCode"/>
			</td>

			<td width="30%" align="left">
				<bean:write name="memoLovVo" property="clearancePeriod"/>
			</td>
			<td width="26%" align="left">
				<bean:write name="memoLovVo" property="invoiceNumber"/>
			</td>
			<td width="30%" align="left">
				<bean:write name="memoLovVo" property="airlineCode"/>
			</td>
			<logic:notEqual name="MemoLovForm" property="multiselect" value="Y">
			</a>
			</logic:notEqual>
			</tr>

		</common:rowColorTag>
		</logic:iterate>
		</logic:present>

		</tbody>
		</table>
		</div>
		
		</div>
		
			
	
	</div>
	<div class="ic-foot-container">
				<div class="ic-button-container">
				<input type="button" name="btnOk" value="OK" class="iCargoButtonSmall" onclick="setValueForDifferentModes('<%=strMultiselect%>','<%=strFormCount%>','<%=strLovTxtFieldName%>','<%=strLovDescriptionTxtFieldName %>',<%=arrayIndex%>)" />

				<ihtml:nbutton property="btnClose" componentID="CMP_MRA_AIRLINEBILLING_MEMOLOV_CLOSE" >
					<common:message key="mra.airlinebilling.defaults.memolov.btn.close"/>
				</ihtml:nbutton>
			</div>
	</div>
	</div>
</ihtml:form>
</div>
<!---CONTENT ENDS-->

	</body>
</html:html>
