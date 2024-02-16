<%--************************************************************
* Project	 		: iCargo
* Module Code & Name            : mra-defaults
* File Name			: RateCardLOV.jsp
* Date				: 02/02/2007
* Author(s)			: A-2408
 ****************************************************************--%>
<%
	response.setDateHeader("Expires",0);
	response.setHeader("Pragma","no-cache");

	if (request.getProtocol().equals("HTTP/1.1")) {
		response.setHeader("Cache-Control","no-cache");
	}
%>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.RateCardLOVForm" %>
<%@ page import="com.ibsplc.icargo.business.mail.mra.defaults.vo.RateCardLovVO" %>

		
	
<html:html>
<head> 
	
	

	<title><common:message   bundle="ratecardlov" key="mailtracking.mra.defaults.ratecardlovtitle" scope="request"/></title>
	<meta name="decorator" content="popuppanelrestyledui">
	<common:include type="script" src="/js/mail/mra/defaults/RateCardLOV_Script.jsp"/>

	
	
</head>

<body>
	


<div id="divmain"  class="iCargoPopUpContent" style="overflow:auto;height:160px;">


<ihtml:form action="/showRateCardLOV.do" styleClass="ic-main-form" >

<bean:define id="RateCardLOVForm" name="RateCardLOVForm" type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.RateCardLOVForm" toScope="page" />

<logic:present name="rateCardLOVForm"  property="lovaction" >
<bean:define id="lovaction" name="rateCardLOVForm" property=" lovaction" toScope="page"/>
</logic:present>
<!--  Hidden to store the selections in case of pagination-->
<ihtml:hidden name="RateCardLOVForm" property="lovaction"  />
<ihtml:hidden name="RateCardLOVForm" property="selectedValues"  />
<ihtml:hidden name="RateCardLOVForm" property="lastPageNum" />
<ihtml:hidden name="RateCardLOVForm" property="displayPage" />
<ihtml:hidden name="RateCardLOVForm" property="multiselect" />
<ihtml:hidden name="RateCardLOVForm" property="pagination" />
<ihtml:hidden name="RateCardLOVForm" property="formCount" />
<ihtml:hidden name="RateCardLOVForm" property="lovTxtFieldName" />
<ihtml:hidden name="RateCardLOVForm" property="lovNameTxtFieldName" />
<ihtml:hidden name="RateCardLOVForm" property="lovDescriptionTxtFieldName" />
<ihtml:hidden name="RateCardLOVForm" property="index" />

<!--  END -->


	<div class="ic-content-main">
<span class="ic-page-title ic-display-none">
		<label><common:message   key="mailtracking.mra.defaults.ratecardlov" scope="request"/></td>
	</label>
							</span>
							<div class="ic-head-container">

<div class="ic-filter-panel" >
<div class="ic-input-container">
<div class="ic-row">
							<div class="ic-input ic-label-33  ic-col-50">
				<label><common:message   key="mailtracking.mra.defaults.ratecardid" scope="request"/></label>
				
				<ihtml:text property="code" name="RateCardLOVForm" componentID="MRA_DEFAULTS_RATECARDID"  maxlength="20"/>
				</div>
				
				
				<div class="ic-button-container">
				<ihtml:nbutton property="btnList" componentID="CMP_MRA_DEFAULTS_LIST" accesskey="L">
				<common:message   key="mailtracking.mra.defaults.ratecardlov.btn.btlist" />
				</ihtml:nbutton>
				<!--Modified by A-7938 FOR ICRD-245276-->
				<ihtml:nbutton property="clearButton" componentID="CMP_MRA_DEFAULTS_CLEAR" accesskey="C" styleClass="btn-inline btn-secondary">
				<common:message   key="mailtracking.mra.defaults.ratecardlov.btn.btclear" />
				</ihtml:nbutton>
				</div>
			</div>
			</div>
			</div>
			</div>
		<div class="ic-main-container">
				<div class="ic-row">		 
				<div class="ic-col-100">
		<logic:present name="RateCardLOVForm" property="rateCardLovPage" >

		<bean:define id="rateCardLovPage"  name="RateCardLOVForm" property="rateCardLovPage" toScope="page"/>

			<logic:present name="rateCardLovPage">
			<logic:present name="RateCardLOVForm" property="multiselect">
			<bean:define id="multiselect" name="RateCardLOVForm" property="multiselect" />

			</logic:present>

				<logic:equal name="RateCardLOVForm" property="pagination" value="Y">
				<!-- -PAGINATION TAGS -->

				<bean:define id="lastPageNum" name="RateCardLOVForm" property="lastPageNum" toScope="page"/>

					
						<common:paginationTag pageURL="javascript:preserveSelectedvalues('lastPageNum','displayPage')"
						name="rateCardLovPage" display="label" labelStyleClass="iCargoResultsLabel"
						lastPageNum="<%=(String)lastPageNum %>" />
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<div class="ic-button-container">

						<common:paginationTag pageURL="javascript:preserveSelectedvalues('lastPageNum','displayPage')"
						name="rateCardLovPage" display="pages"	linkStyleClass="iCargoLink" disabledLinkStyleClass="iCargoLink"
						lastPageNum="<%=(String)lastPageNum%>"/>
						</div>
				</logic:equal>
			</logic:present>
		</logic:present>
		</div>
			</div>

	<!--  END -->
 <div class="ic-row">


<bean:define id="strFormCount" name="RateCardLOVForm" property="formCount"  />

<bean:define id="strMultiselect" name="RateCardLOVForm" property="multiselect" />
<%String strLovTxtField = "";%>
<logic:present name="RateCardLOVForm" property="lovTxtFieldName">
<bean:define id="strLovTxtFieldName" name="RateCardLOVForm" property="lovTxtFieldName"  />
<%strLovTxtField=strLovTxtFieldName.toString();%>
</logic:present>
<%String strTxtFieldName = "";%>
<logic:present name="RateCardLOVForm" property="lovDescriptionTxtFieldName">
<bean:define id="strLovDescriptionTxtFieldName" name="RateCardLOVForm" property="lovDescriptionTxtFieldName" />

<%strTxtFieldName=strLovDescriptionTxtFieldName.toString();%>
</logic:present>
<bean:define id="strSelectedValues" name="RateCardLOVForm" property="selectedValues" />

<bean:define id="arrayIndex" name="RateCardLOVForm" property="index"/>



		<div id="div1"  class="tableContainer" style="height:288px"> 
<table  class="fixed-header-table" id="lovListTable" >
		





			<thead>
			<tr class="iCargoTableHeadingCenter" >
			<td width="5%"> &nbsp;</td>
			<td width="40%" class="iCargoLabelleftAligned">
			<common:message   key="mailtracking.mra.defaults.ratecardid" scope="request"/>
			</td>
			<td width="45%" class="iCargoLabelLeftAligned">
			<common:message   key="mailtracking.mra.defaults.ratecarddesp" scope="request"/>
			</td>

			</tr>
			</thead>

			<logic:present name="RateCardLOVForm" property="rateCardLovPage">
			 <bean:define id="lovList" name="RateCardLOVForm" property="rateCardLovPage" toScope="page"/>
			<logic:present name="lovList">

			<tbody>
			<% int i=0;%>
			<logic:iterate id = "val" name="lovList" indexId="indexId">
		
			<tr ondblclick="setValueOnDoubleClick('<%=((RateCardLovVO)val).getRateCardID()%>','<%=((RateCardLovVO)val).getDescription()%>',
									'<%= strLovTxtField%>','<%=strTxtFieldName %>',<%=arrayIndex%>)">
				<logic:notEqual name="RateCardLOVForm" property="multiselect" value="Y">
				<a href="#" />
				</logic:notEqual>
				<logic:equal name="RateCardLOVForm" property="multiselect" value="Y">
				<td>
					<%if(((String)strSelectedValues).contains(((RateCardLovVO)val).getRateCardID())){ %>
					<input type="checkbox" name="selectCheckBox" value="<%=((RateCardLovVO)val).getRateCardID()%>"  checked="checked"/>
					<%}else{ %>
					<input type="checkbox" name="selectCheckBox" value="<%=((RateCardLovVO)val).getRateCardID()%>"  />
					<% } %>
				</td>
				</logic:equal>
				<%String checkVal = "";%>

				<logic:notEqual name="RateCardLOVForm" property="multiselect" value="Y">
				<td>
					<%checkVal = ((RateCardLovVO)val).getRateCardID()+"-"+((RateCardLovVO)val).getDescription();

					%>

					<% System.out.println("========------"+checkVal);%>

					<%

					if(   ((String)strSelectedValues).equals(((RateCardLovVO)val).getRateCardID()  )){ %>
					<input type="checkbox" name="selectCheckBox" value="<%=checkVal%>"  onclick="singleSelect('<%=checkVal%>');" checked="checked" />
					<%}else{ %>
					<input type="checkbox" name="selectCheckBox" value="<%=checkVal%>"  onclick="singleSelect('<%=checkVal%>');"/>
					<% } %>
				</td>
				</logic:notEqual>
				<!-- table -->

				<td class="ic-left">
					<bean:write name="val" property="rateCardID"/>
				</td>
				<td class="ic-left">
					<bean:write name="val" property="description"/>
				</td>

				<logic:notEqual name="RateCardLOVForm" property="multiselect" value="Y">
					<%--</a>--%>
				</logic:notEqual>
			</tr>
		
			</logic:iterate>
			</tbody>
</logic:present>
</logic:present>
</table>
</div>

</div>
</div>


<div class="ic-foot-container">
			<div class="ic-row">
					<div class="ic-button-container">



	<input type="button" name="btnOk" title='OK'
		value="OK" class="iCargoButtonSmall"
		onclick="setValueForDifferentModes('<%=strMultiselect%>','<%=strFormCount%>','<%=strLovTxtField%>','<%=strTxtFieldName %>',<%=arrayIndex%>)"  />

	<ihtml:nbutton property="btnClose" componentID="CMP_MRA_DEFAULTS_CLOSE" accesskey="O">
				<common:message   key="mailtracking.mra.defaults.ratecardlov.btn.btclose" />
	</ihtml:nbutton>
	</div>
							</div>
							</div>
							</div>

</ihtml:form>
</div>		
		  
	</body>
</html:html>


