<%--
 /***********************************************************************
* Project       	 :  iCargo
* Module Code & Name 	 :  MailTracking
* File Name     	 :  AcceptDespatch.jsp
* Date          	 :  22-June-2007
* Author(s)     	 :  Roopak V.S.

*************************************************************************/
 --%>

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

<bean:define id="MailAcceptanceForm" name="MailAcceptanceForm"
   type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAcceptanceForm"
   toScope="page" scope="request"/>
<business:sessionBean id="ContainerDetailsVOsSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailacceptance" method="get" attribute="containerDetailsVOs" />
	<logic:present name="ContainerDetailsVOsSession">
		<bean:define id="ContainerDetailsVOsSession" name="ContainerDetailsVOsSession" toScope="page"/>
	</logic:present>
<business:sessionBean id="ContainerDetailsVOSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailacceptance" method="get" attribute="containerDetailsVO" />
	<logic:present name="ContainerDetailsVOSession">
		<bean:define id="ContainerDetailsVOSession" name="ContainerDetailsVOSession" toScope="page"/>
	</logic:present>
<business:sessionBean id="warehouseSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailacceptance" method="get" attribute="warehouse" />
	<logic:present name="warehouseSession">
		<bean:define id="warehouseSession" name="warehouseSession" toScope="page"/>
	</logic:present>
<business:sessionBean id="oneTimeCatSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailacceptance" method="get" attribute="oneTimeCat" />
<business:sessionBean id="oneTimeRISession" moduleName="mail.operations" screenID="mailtracking.defaults.mailacceptance" method="get" attribute="oneTimeRSN" />
<business:sessionBean id="oneTimeHNISession" moduleName="mail.operations" screenID="mailtracking.defaults.mailacceptance" method="get" attribute="oneTimeHNI" />
<business:sessionBean id="oneTimeMailClassSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailacceptance" method="get" attribute="oneTimeMailClass" />
<business:sessionBean id="oneTimeContainerTypeSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailacceptance" method="get" attribute="oneTimeContainerType" />
<business:sessionBean id="pousSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailacceptance" method="get" attribute="pous" />



	
	<table  class="fixed-header-table" style="width:180%">
              <thead>
                <tr class="iCargoTableHeadingLeft">
                  <td width="3%" class="iCargoTableHeaderLabel"><div align="center"><input type="checkbox" name="masterDespatch" onclick="updateHeaderCheckBox(this.form,this,this.form.selectDespatch);"/></div></td>
                  <td width="13%"class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.acceptmail.lbl.condocno"/> <span class="iCargoMandatoryFieldIcon">*</span> </td>
                  <td width="16%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.acceptmail.lbl.date"/> <span class="iCargoMandatoryFieldIcon">*</span> </td>
                  <td width="11%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.acceptmail.lbl.pa"/> <span class="iCargoMandatoryFieldIcon">*</span> </td>
                  <td width="11%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.acceptmail.lbl.origin"/><span class="iCargoMandatoryFieldIcon">*</span> </td>
                  <td width="11%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.acceptmail.lbl.destination"/> <span class="iCargoMandatoryFieldIcon">*</span> </td>
                  <td width="6%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.acceptmail.lbl.cat"/>  </td>
                  <td width="6%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.acceptmail.lbl.class"/>  </td>
                  <td width="8%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.acceptmail.lbl.sc"/>  </td>
                  <td width="6%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.acceptmail.lbl.dsn"/><span class="iCargoMandatoryFieldIcon">*</span>  </td>
                  <td width="5%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.acceptmail.lbl.year"/><span class="iCargoMandatoryFieldIcon">*</span> </td>
               	  <td width="7%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.acceptmail.lbl.stdbags"/><span class="iCargoMandatoryFieldIcon">*</span> </td>
                  <td width="17%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.acceptmail.lbl.stdwt"/>  </td>
                  <td width="7%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.acceptmail.lbl.accbags"/><span class="iCargoMandatoryFieldIcon">*</span> </td>
                  <td width="17%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.acceptmail.lbl.accwt"/>  </td>
                  <td width="17%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.acceptmail.lbl.stdvolume"/> </td>  
                  <td width="17%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.acceptmail.lbl.accvolume"/> </td>       
                </tr>
              </thead>
	              <tbody id="despatchTableBody">
              
              <% int despatch = 0;%>
               <logic:present name="ContainerDetailsVOSession" property="desptachDetailsVOs">
		 <bean:define id="desptachDetailsVOsColl" name="ContainerDetailsVOSession" property="desptachDetailsVOs" scope="page" toScope="page"/>
		 <% int row = ((Collection)desptachDetailsVOsColl).size();%>
		 <logic:iterate id="desptachDetailsVO" name="desptachDetailsVOsColl" indexId="index">
		 
		 <% despatch++;%>
		 <%boolean toDisable = false;%>
		 <logic:notEqual name="desptachDetailsVO" property="operationalFlag" value="I">
		     <% toDisable = true;%>
		 </logic:notEqual>
			 
			 <logic:present name="desptachDetailsVO" property="operationalFlag">
				<bean:define id="operationFlag" name="desptachDetailsVO" property="operationalFlag" toScope="request" />
				<ihtml:hidden property="despatchOpFlag" value="<%=((String)operationFlag)%>" />
			 </logic:present>
			 <logic:notPresent name="desptachDetailsVO" property="operationalFlag">
				<ihtml:hidden property="despatchOpFlag" value="N" />
			 </logic:notPresent>
			 
			 
		 <tr class="iCargoTableCellsLeftRowColor1">
                  <td >
                    <% String despatchKey=(String.valueOf(despatch));%>
	            <input type="checkbox" name="selectDespatch" value="<%=despatchKey%>"/>
                  </td>
		<logic:present name="desptachDetailsVO" property="displayLabel"> 
			<logic:equal name="desptachDetailsVO" property="displayLabel" value="Y">
                  <td>
					<bean:write name="desptachDetailsVO" property="consignmentNumber"/>
					<bean:define id="consignmentNumber" name="desptachDetailsVO" property="consignmentNumber" toScope="page"/>
					<ihtml:hidden property="conDocNo" value="<%=(String)consignmentNumber%>"/>
				</td>
				<td>					
					<bean:define id="consignmentDate" name="desptachDetailsVO" property="consignmentDate" toScope="page"/>
					<%String consignDt=TimeConvertor.toStringFormat(((LocalDate)consignmentDate).toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
					<%= consignDt %>
					<ihtml:hidden property="despatchDate" value="<%=consignDt%>"/>					   		
				</td>
				<td style="width:150px;" >
					<bean:write name="desptachDetailsVO" property="paCode"/>
					<bean:define id="paCode" name="desptachDetailsVO" property="paCode" toScope="page"/>
					<ihtml:hidden property="despatchPA" value="<%=String.valueOf(paCode)%>"/>					   		
				</td>
				<td>
					<bean:write name="desptachDetailsVO" property="originOfficeOfExchange"/>
					<bean:define id="originOfficeOfExchange" name="desptachDetailsVO" property="originOfficeOfExchange" toScope="page"/>
					<ihtml:hidden property="despatchOOE" value="<%=String.valueOf(originOfficeOfExchange)%>"/>					   		
				</td>
				<td>
					<bean:write name="desptachDetailsVO" property="destinationOfficeOfExchange"/>
					<bean:define id="destinationOfficeOfExchange" name="desptachDetailsVO" property="destinationOfficeOfExchange" toScope="page"/>
					<ihtml:hidden property="despatchDOE" value="<%=String.valueOf(destinationOfficeOfExchange)%>"/>					   		
				</td>
				<td>
					<bean:write name="desptachDetailsVO" property="mailCategoryCode"/>
					<bean:define id="mailCategoryCode" name="desptachDetailsVO" property="mailCategoryCode" toScope="page"/>
					<ihtml:hidden property="despatchCat" value="<%=String.valueOf(mailCategoryCode)%>"/>					   		
				</td>
				<td>
					<bean:write name="desptachDetailsVO" property="mailClass"/>
					<bean:define id="mailClass" name="desptachDetailsVO" property="mailClass" toScope="page"/>
					<ihtml:hidden property="despatchClass" value="<%=String.valueOf(mailClass)%>"/>					   		
				</td>
				<td>
					<bean:write name="desptachDetailsVO" property="mailSubclass"/>
					<bean:define id="mailSubclass" name="desptachDetailsVO" property="mailSubclass" toScope="page"/>
					<ihtml:hidden property="despatchSC" value="<%=String.valueOf(mailSubclass)%>"/>					   		
				</td>
				<td>
					<bean:write name="desptachDetailsVO" property="dsn"/>
					<bean:define id="dsn" name="desptachDetailsVO" property="dsn" toScope="page"/>
					<ihtml:hidden property="despatchDSN" value="<%=String.valueOf(dsn)%>"/>					   		
				</td>
				<td>
					<bean:write name="desptachDetailsVO" property="year"/>
					<bean:define id="year" name="desptachDetailsVO" property="year" toScope="page"/>
					<ihtml:hidden property="despatchYear" value="<%=String.valueOf(year)%>"/>					   		
				</td>
				<td>
					<bean:write name="desptachDetailsVO" property="statedBags"/>
					<bean:define id="statedBags" name="desptachDetailsVO" property="statedBags" toScope="page"/>
					<ihtml:hidden property="statedNoBags" value="<%=String.valueOf(statedBags)%>"/>					   		
				</td>
				<td>
					<bean:write name="desptachDetailsVO" property="statedWeight"/>
					<bean:define id="statedWeight" name="desptachDetailsVO" property="statedWeight" toScope="page"/>
					<ihtml:hidden property="statedWt" value="<%=String.valueOf(statedWeight)%>"/>					   		
				</td>
			</logic:equal>
			<logic:equal name="desptachDetailsVO" property="displayLabel" value="N">
				<td>
                    <logic:notPresent name="desptachDetailsVO" property="consignmentNumber">
				       		<ihtml:text property="conDocNo" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_CONDOCNO" value="" readonly="<%=toDisable%>"  style="width:100px" maxlength="13"/>
                    </logic:notPresent>
                    <logic:present name="desptachDetailsVO" property="consignmentNumber">
			<bean:define id="consignmentNum" name="desptachDetailsVO" property="consignmentNumber" toScope="page"/>
						<ihtml:text property="conDocNo" value="<%=(String)consignmentNum%>" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_CONDOCNO" readonly="<%=toDisable%>"  style="width:100px" maxlength="13"/>
		    </logic:present>
                  </td>
                  <td>
                    <logic:notPresent name="desptachDetailsVO" property="consignmentDate">
			<ibusiness:calendar property="despatchDate" id="despatchDate" indexId="index" type="image" componentID="CMB_MAILTRACKING_DEFAULTS_ACCEPTMAIL_DSNDATE"  value="" readonly="<%=toDisable%>"/>
		    </logic:notPresent>
		    <logic:present name="desptachDetailsVO" property="consignmentDate">
			<bean:define id="consignmentDate" name="desptachDetailsVO" property="consignmentDate" toScope="page"/>
			<%String consignDt=TimeConvertor.toStringFormat(((LocalDate)consignmentDate).toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
			<ibusiness:calendar property="despatchDate" id="despatchDate" indexId="index" value="<%=(String)consignDt%>" type="image" componentID="CMB_MAILTRACKING_DEFAULTS_ACCEPTMAIL_DSNDATE"  readonly="<%=toDisable%>"/>
		    </logic:present>
                  </td>
                  <td >
		    <logic:notPresent name="desptachDetailsVO" property="paCode">
			<ihtml:text property="despatchPA" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_DSNPA" value="" maxlength="5" readonly="<%=toDisable%>" />
		    </logic:notPresent>
		    <logic:present name="desptachDetailsVO" property="paCode">
			<bean:define id="paCode" name="desptachDetailsVO" property="paCode" toScope="page"/>
			<ihtml:text property="despatchPA" value="<%=(String)paCode%>" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_DSNPA" maxlength="5" readonly="<%=toDisable%>" />
		    </logic:present>
		    <%if(toDisable){%>
						<img name="despatchPALov" id="despatchPALov<%=index%>" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16" disabled>
		    <%}else{%>
						<img name="despatchPALov" id="despatchPALov<%=index%>" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16">
		    <%}%>
                  </td>
                  <td >
                    <logic:notPresent name="desptachDetailsVO" property="originOfficeOfExchange">
			<ihtml:text property="despatchOOE" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_DSNORIGIN" value="" maxlength="6" readonly="<%=toDisable%>" />
		    </logic:notPresent>
		    <logic:present name="desptachDetailsVO" property="originOfficeOfExchange">
			<bean:define id="originOE" name="desptachDetailsVO" property="originOfficeOfExchange" toScope="page"/>
			<ihtml:text property="despatchOOE" value="<%=(String)originOE%>" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_DSNORIGIN" maxlength="6" readonly="<%=toDisable%>" />
		    </logic:present>
		    <%if(toDisable){%>
						<img name="despatchOOELov" id="despatchOOELov<%=index%>" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16" disabled>
		    <%}else{%>
						<img name="despatchOOELov" id="despatchOOELov<%=index%>" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16">
		    <%}%>
                  </td>
                  <td >
                    <logic:notPresent name="desptachDetailsVO" property="destinationOfficeOfExchange">
			<ihtml:text property="despatchDOE" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_DSNDESTN" value="" maxlength="6" readonly="<%=toDisable%>" />
		    </logic:notPresent>
		    <logic:present name="desptachDetailsVO" property="destinationOfficeOfExchange">
			<bean:define id="destinationOE" name="desptachDetailsVO" property="destinationOfficeOfExchange" toScope="page"/>
			<ihtml:text property="despatchDOE" value="<%=(String)destinationOE%>" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_DSNDESTN" maxlength="6" readonly="<%=toDisable%>" />
		    </logic:present>
		    <%if(toDisable){%>
						<img name="despatchDOELov" id="despatchDOELov<%=index%>" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16" disabled>
		    <%}else{%>
						<img name="despatchDOELov" id="despatchDOELov<%=index%>" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16" >
		    <%}%>
                  </td>
                  <td>
                  <% String catDesValue = ""; %>
		    <logic:present name="desptachDetailsVO" property="mailCategoryCode">
			<bean:define id="desCtgyCode" name="desptachDetailsVO" property="mailCategoryCode" toScope="page"/>
			<% catDesValue = (String) desCtgyCode; %>
		    </logic:present>
		    <ihtml:select property="despatchCat" componentID="CMB_MAILTRACKING_DEFAULTS_ACCEPTMAIL_DSNCAT" value="<%=catDesValue%>" disabled="<%=toDisable%>"  style="width:35px">
			<bean:define id="oneTimeCatSess" name="oneTimeCatSession" toScope="page" />
			<logic:iterate id="oneTimeCatVO" name="oneTimeCatSess" >
			  <bean:define id="fieldValue" name="oneTimeCatVO" property="fieldValue" toScope="page" />
			     <html:option value="<%=(String)fieldValue %>"><bean:write name="oneTimeCatVO" property="fieldValue"/></html:option>
			</logic:iterate>
		    </ihtml:select>
                  </td>
                  <td>
                    <% String classValue = ""; %>
		    <logic:present name="desptachDetailsVO" property="mailClass">
			<bean:define id="mailclass" name="desptachDetailsVO" property="mailClass" toScope="page"/>
			<% classValue = (String) mailclass; %>
		    </logic:present>
		    <ihtml:select property="despatchClass" componentID="CMB_MAILTRACKING_DEFAULTS_ACCEPTMAIL_CLASS" value="<%=classValue%>" disabled="<%=toDisable%>"  style="width:35px">
			<bean:define id="oneTimeMailClassSess" name="oneTimeMailClassSession" toScope="page" />
			<logic:iterate id="oneTimeMailClassVO" name="oneTimeMailClassSess" >
			  <bean:define id="fieldValue" name="oneTimeMailClassVO" property="fieldValue" toScope="page" />
			     <html:option value="<%=(String)fieldValue %>"><bean:write name="oneTimeMailClassVO" property="fieldValue"/></html:option>
			</logic:iterate>
		    </ihtml:select>
		  </td>
		  <td>
		   <% String subclassValue = ""; %>
			  <logic:notPresent name="desptachDetailsVO" property="mailSubclass">
				<ihtml:text property="despatchSC" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_DESPATCHSC" value="" maxlength="2" readonly="<%=toDisable%>"  />
			</logic:notPresent>
			<logic:present name="desptachDetailsVO" property="mailSubclass">
			<bean:define id="despatchSubclass" name="desptachDetailsVO" property="mailSubclass" toScope="page"/>
			<% subclassValue = (String) despatchSubclass;
						   int arrays=subclassValue.indexOf("_");
						   if(arrays==-1){%>
				<ihtml:text property="despatchSC" value="<%=(String)despatchSubclass%>" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_DESPATCHSC" maxlength="2" readonly="<%=toDisable%>" />
				<%}else{%>
				<ihtml:text property="despatchSC" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_DESPATCHSC" value="" maxlength="2" readonly="<%=toDisable%>"  />
				<%}%>
			</logic:present>
			<%if(toDisable){%>
						<img name="despatchSCLov" id="despatchSCLov<%=index%>" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16"  disabled>
			<%}else{%>
						<img name="despatchSCLov" id="despatchSCLov<%=index%>" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16" >
			<%}%>
		</td>
                  <td>
                  <logic:notPresent name="desptachDetailsVO" property="dsn">
			<ihtml:text property="despatchDSN" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_DSN" value="" maxlength="4" readonly="<%=toDisable%>" />
		  </logic:notPresent>
		  <logic:present name="desptachDetailsVO" property="dsn">
			<bean:define id="dsn" name="desptachDetailsVO" property="dsn" toScope="page"/>
			<ihtml:text property="despatchDSN" value="<%=(String)dsn%>" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_DSN" maxlength="4" readonly="<%=toDisable%>" />
		  </logic:present>
                  </td>
                  <td>
		    <logic:notPresent name="desptachDetailsVO" property="year">
			<ihtml:text property="despatchYear" indexId="index" styleId="despatchYear" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_DESPATCHYR" value="" maxlength="1" readonly="<%=toDisable%>"  style="width:20px"/>
		  </logic:notPresent>
		  <logic:present name="desptachDetailsVO" property="year">
			<bean:define id="year" name="desptachDetailsVO" property="year" toScope="page"/>
			<ihtml:text property="despatchYear" indexId="index" styleId="despatchYear" value="<%=year.toString()%>" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_DESPATCHYR" maxlength="1" readonly="<%=toDisable%>"  style="width:20px"/>
		  </logic:present>
                  </td>
                  <td>
                  <logic:notPresent name="desptachDetailsVO" property="statedBags">
						<ihtml:text property="statedNoBags" indexId="index" styleId="statedNoBags" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_STDNUMBAG" value="" readonly="<%=toDisable%>"  style="width:50px" maxlength="4"/>
		  </logic:notPresent>
		  <logic:present name="desptachDetailsVO" property="statedBags">
			<bean:define id="statedBags" name="desptachDetailsVO" property="statedBags" toScope="page"/>
						<ihtml:text property="statedNoBags" indexId="index" styleId="statedNoBags" value="<%=statedBags.toString()%>" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_STDNUMBAG" readonly="<%=toDisable%>"  style="width:50px" maxlength="4"/>  
		  </logic:present>
                  </td>
                  <td>
                  <logic:notPresent name="desptachDetailsVO" property="statedWeight">
							<ibusiness:unitdef id="statedWt" unitTxtName="statedWt" label="" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_STDWGT"  unitReq = "false" dataName="statedWt"
								 unitValueStyle="iCargoEditableTextFieldRowColor1" title="Stated Weight"
								unitValue="0.0" 
								indexId="index" styleId="statedWt" />
						
		  </logic:notPresent>
		  <logic:present name="desptachDetailsVO" property="statedWeight">
			<bean:define id="statedWeight" name="desptachDetailsVO" property="statedWeight" toScope="page" type="com.ibsplc.icargo.framework.util.unit.Measure"/><!-- A-7371--> 
							<% request.setAttribute("sampleStdWt",statedWeight); %>
							<ibusiness:unitdef id="statedWt" unitTxtName="statedWt" label="" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_STDWGT" unitReq = "false" dataName="sampleStdWt"
								 unitValueStyle="iCargoEditableTextFieldRowColor1" title="Stated Weight"
								unitValue="<%=statedWeight.toString()%>" 
								indexId="index" styleId="statedWt" readonly="<%=toDisable%>"/>
														
		  </logic:present>
                  </td>
			</logic:equal>
		</logic:present>
                  <td>
                  <logic:notPresent name="desptachDetailsVO" property="acceptedBags">
				<ihtml:text property="accNoBags" indexId="index" styleId="accNoBags" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_NUMBAGS" value=""  style="width:40px" maxlength="4"/>
		  </logic:notPresent>
		  <logic:present name="desptachDetailsVO" property="acceptedBags">
			<bean:define id="acceptedBags" name="desptachDetailsVO" property="acceptedBags" toScope="page"/>
				<ihtml:text property="accNoBags" indexId="index" styleId="accNoBags" value="<%=acceptedBags.toString()%>" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_NUMBAGS"  style="width:40px" maxlength="4"/>
		  </logic:present>
                  </td>
                  <td>
                  <logic:notPresent name="desptachDetailsVO" property="acceptedWeight">
			<bean:define id="acceptedWeight" name="desptachDetailsVO" property="acceptedWeight" toScope="page" type="com.ibsplc.icargo.framework.util.unit.Measure"/><!-- A-7371--> 
					<% request.setAttribute("sampleStdWt",acceptedWeight); %>
					<ibusiness:unitdef id="accWt" unitTxtName="accWt" label="" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_ACCWGT"  unitReq = "false" dataName="sampleStdWt"
						 unitValueStyle="iCargoEditableTextFieldRowColor1" title="Accepted Weight"
						unitValue="0.0" 
						indexId="index" styleId="accWt" />
				
			   <%--
				<ihtml:text property="accWt" indexId="index" styleId="accWt" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_ACCWGT" value=""  style="width:50px" maxlength="5"/>		     
	  		  --%>
		  </logic:notPresent>
		  <logic:present name="desptachDetailsVO" property="acceptedWeight">
			<bean:define id="acceptedWeight" name="desptachDetailsVO" property="acceptedWeight" toScope="page" type="com.ibsplc.icargo.framework.util.unit.Measure"/><!-- A-7371--> 
					<% request.setAttribute("sampleStdWt",acceptedWeight); %>
					<ibusiness:unitdef id="accWt" unitTxtName="accWt" label="" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_ACCWGT"  unitReq = "false" dataName="sampleStdWt"
						 unitValueStyle="iCargoEditableTextFieldRowColor1" title="Accepted Weight"
						unitValue="<%=acceptedWeight.toString()%>" 
						indexId="index" styleId="accWt" />
		  </logic:present>
				 <%--		
				<ihtml:text property="accWt" indexId="index" styleId="accWt" value="<%=acceptedWeight.toString()%>" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_ACCWGT"  style="width:50px" maxlength="5"/>
				--%>
	                  </td>
	                  <td>
	                    <logic:notPresent name="desptachDetailsVO" property="statedVolume">
						<bean:define id="sampleStdVolVo" name="desptachDetailsVO" property="statedVolume" />
									<% request.setAttribute("sampleStdVol",sampleStdVolVo); %>
									<ibusiness:unitdef id="stdVolume" unitTxtName="stdVolume" label=""  unitReq = "false" dataName="sampleStdVol"
										unitValueMaxLength="8" unitValueStyle="iCargoEditableTextFieldRowColor1" title="Stated Volume"
										unitValue="0.0" 
										indexId="index" styleId="stdVolume" />
								
							 <%--						
							<ihtml:text property="stdVolume" indexId="index" styleId="stdVolume" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_STDVOLUME" value=""  style="width:50px" maxlength="5"/>
							--%>
			 		    </logic:notPresent>
			  			<logic:present name="desptachDetailsVO" property="statedVolume">
						<bean:define id="statedVolume" name="desptachDetailsVO" property="statedVolume" toScope="page" type="com.ibsplc.icargo.framework.util.unit.Measure"/><!-- A-7371--> 
									<% request.setAttribute("sampleStdVol",statedVolume); %>
									<ibusiness:unitdef id="stdVolume" unitTxtName="stdVolume" label=""  unitReq = "false" dataName="sampleStdVol"
										unitValueMaxLength="8" unitValueStyle="iCargoEditableTextFieldRowColor1" title="Stated Volume"
										unitValue="<%=statedVolume.toString()%>" 
										indexId="index" styleId="stdVolume" readonly="<%=toDisable%>" />
								</logic:present>
							 <%--					
							<ihtml:text property="stdVolume" indexId="index" styleId="stdVolume" value="<%=statedVolume.toString()%>" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_STDVOLUME"  style="width:50px" maxlength="5"/>
							--%>
	                  </td>
	                  
	              <td>
					<logic:notPresent name="desptachDetailsVO" property="acceptedVolume">
							<bean:define id="sampleStdVolVo" name="desptachDetailsVO" property="acceptedVolume" />
							<% request.setAttribute("sampleStdVol",sampleStdVolVo); %>
							<ibusiness:unitdef id="accVolume" unitTxtName="accVolume" label=""  unitReq = "false" dataName="sampleStdVol"
								unitValueMaxLength="8" unitValueStyle="iCargoEditableTextFieldRowColor1" title="Accepted Volume"
								unitValue="0.0" 
								indexId="index" styleId="accVolume" />
						
					</logic:notPresent>
					<logic:present name="desptachDetailsVO" property="acceptedVolume">
					<bean:define id="acceptedVolume" name="desptachDetailsVO" property="acceptedVolume" toScope="page"/>
							<% request.setAttribute("sampleStdVol",acceptedVolume); %>
							<ibusiness:unitdef id="accVolume" unitTxtName="accVolume" label=""  unitReq = "false" dataName="sampleStdVol"
								unitValueMaxLength="8" unitValueStyle="iCargoEditableTextFieldRowColor1" title="Accepted Volume"
								unitValue="<%=acceptedVolume.toString()%>" 
								indexId="index" styleId="accVolume" readonly="<%=toDisable%>" />
						</logic:present>
	            </td>
	                  
	                </tr>
	               </logic:iterate>
		       </logic:present>
		       
		       <!-- templateRow -->
			<tr template="true" id="despatchTemplateRow" style="display:none">
	
			    <ihtml:hidden property="despatchOpFlag" value="NOOP" />
	
			  <td  class="iCargoTableDataTd">
				<input type="checkbox" name="selectDespatch" >
			  </td>
	
			  <td><ihtml:text property="conDocNo" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_CONDOCNO" value="" style="width:100px" maxlength="13"/></td>
			  
			  <td><ibusiness:calendar property="despatchDate" id="despatchDate" indexId="index" type="image" componentID="CMB_MAILTRACKING_DEFAULTS_ACCEPTMAIL_DSNDATE"  value="" /></td>
			  
			  <td  >
			    <ihtml:text property="despatchPA" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_DSNPA" value="" maxlength="5" />
			    <img name="despatchPALov" id="despatchPALov" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16" >
			  </td>
			  
			  <td >
			    <ihtml:text property="despatchOOE" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_DSNORIGIN" value="" maxlength="6" />
			    <img name="despatchOOELov" id="despatchOOELov" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16" >
			  </td>
			  
			  <td >
			    <ihtml:text property="despatchDOE" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_DSNDESTN" value="" maxlength="6" />
			    <img name="despatchDOELov" id="despatchDOELov" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16" >
			  </td>
			  
			  <td>
			    <ihtml:select property="despatchCat" componentID="CMB_MAILTRACKING_DEFAULTS_ACCEPTMAIL_DSNCAT" value="" style="width:35px">
				<bean:define id="oneTimeCatSess" name="oneTimeCatSession" toScope="page" />
				<logic:iterate id="oneTimeCatVO" name="oneTimeCatSess" >
				  <bean:define id="fieldValue" name="oneTimeCatVO" property="fieldValue" toScope="page" />
				     <html:option value="<%=(String)fieldValue %>"><bean:write name="oneTimeCatVO" property="fieldValue"/></html:option>
				</logic:iterate>
			    </ihtml:select>
			  </td>
			  
			  <td>
			    <ihtml:select property="despatchClass" componentID="CMB_MAILTRACKING_DEFAULTS_ACCEPTMAIL_CLASS" value="" style="width:35px">
				<bean:define id="oneTimeMailClassSess" name="oneTimeMailClassSession" toScope="page" />
				<logic:iterate id="oneTimeMailClassVO" name="oneTimeMailClassSess" >
				  <bean:define id="fieldValue" name="oneTimeMailClassVO" property="fieldValue" toScope="page" />
				     <html:option value="<%=(String)fieldValue %>"><bean:write name="oneTimeMailClassVO" property="fieldValue"/></html:option>
				</logic:iterate>
			    </ihtml:select>
			  </td>
	
			  <td>
			     <ihtml:text property="despatchSC" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_DESPATCHSC" value="" maxlength="2" />
			      <img name="despatchSCLov" id="despatchSCLov" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16" >
			   </td>
	
			  <td>
				<ihtml:text property="despatchDSN" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_DSN" value="" maxlength="4" />
			  </td>
			  <td>
				<ihtml:text property="despatchYear" indexId="index" styleId="despatchYear" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_DESPATCHYR" value="" maxlength="1" style="width:20px"/>
			  </td>
			  <td>
				<ihtml:text property="statedNoBags" indexId="index" styleId="statedNoBags" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_STDNUMBAG" value="" style="width:50px" maxlength="4"/>
			  </td>
			  <td>
				
				
		       <logic:present name="desptachDetailsVO" property="statedWeight">
			     <bean:define id="statedWeight" name="desptachDetailsVO" property="statedWeight" toScope="page" type="com.ibsplc.icargo.framework.util.unit.Measure"/><!-- A-7371--> 
					<% request.setAttribute("sampleStdWt",statedWeight); %>
					<ibusiness:unitdef id="statedWt" unitTxtName="statedWt" label="" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_STDWGT"  unitReq = "false" dataName="sampleStdWt"
						 unitValueStyle="iCargoEditableTextFieldRowColor1" title="Stated Weight"
						unitValue="0.0" 
						indexId="index" styleId="statedWt" />
				</logic:present>
																				
				
				<%--
					<ihtml:text property="statedWt" indexId="index" styleId="statedWt" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_STDWGT" value="" style="width:50px" maxlength="5"/>
					
				--%>	
			  </td>
			  <td>
				<ihtml:text property="accNoBags" indexId="index" styleId="accNoBags" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_NUMBAGS" value=""  style="width:40px" maxlength="4"/>
			  </td>
			  <td>
			  
			  
			   <logic:present name="desptachDetailsVO" property="acceptedWeight">
			<bean:define id="acceptedWeight" name="desptachDetailsVO" property="acceptedWeight" toScope="page" type="com.ibsplc.icargo.framework.util.unit.Measure"/><!-- A-7371--> 
					<% request.setAttribute("sampleStdWt",acceptedWeight); %>
					<ibusiness:unitdef id="accWt" unitTxtName="accWt" label="" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_ACCWGT"  unitReq = "false" dataName="sampleStdWt"
						 unitValueStyle="iCargoEditableTextFieldRowColor1" title="Accepted Weight"
						unitValue="0.0" 
						indexId="index" styleId="accWt" />
				</logic:present>
			  
			  <%--
			  	<ihtml:text property="accWt" indexId="index" styleId="accWt" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_ACCWGT" value=""  style="width:50px" maxlength="5"/>
			  
			  --%>
			  </td>
			  <td>	  
			   <logic:present name="desptachDetailsVO" property="statedVolume">
					<bean:define id="statedVolume" name="desptachDetailsVO" property="statedVolume" toScope="page" type="com.ibsplc.icargo.framework.util.unit.Measure"/><!-- A-7371--> 
					<% request.setAttribute("sampleStdVol",statedVolume); %>
					<ibusiness:unitdef id="stdVolume" unitTxtName="stdVolume" label=""  unitReq = "false" dataName="sampleStdVol"
						unitValueMaxLength="8" unitValueStyle="iCargoEditableTextFieldRowColor1" title="Stated Volume"
						unitValue="0.0" 
						indexId="index" styleId="stdVolume" />
				</logic:present>
			 </td>	
			 
			  <td>	
				  <logic:present name="desptachDetailsVO" property="statedVolume">
					<bean:define id="statedVolume" name="desptachDetailsVO" property="statedVolume" toScope="page" type="com.ibsplc.icargo.framework.util.unit.Measure"/><!-- A-7371--> 
					<% request.setAttribute("sampleStdVol",statedVolume); %>
					<ibusiness:unitdef id="accVolume" unitTxtName="accVolume" label=""  unitReq = "false" dataName="sampleStdVol"
						unitValueMaxLength="8" unitValueStyle="iCargoEditableTextFieldRowColor1" title="Accepted Volume"
						unitValue="0.0" 
						indexId="index" styleId="stdVolume" />
				</logic:present>
			 </td>	
			  
			    <%--
			  	<ihtml:text property="stdVolume" indexId="index" styleId="stdVolume" componentID="TXT_MAILTRACKING_DEFAULTS_ACCEPTMAIL_STDVOLUME" value=""  style="width:50px" maxlength="5"/>
			    --%>
			 
			
			</tr>
			<!--template row ends-->
		       
		       
		       
	      </tbody>
            </table>