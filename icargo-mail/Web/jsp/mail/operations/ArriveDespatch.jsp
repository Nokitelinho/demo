<%--
 /***********************************************************************
* Project       	 :  iCargo
* Module Code & Name 	 :  MailTracking
* File Name     	 :  ArriveDespatch.jsp
* Date          	 :  22-Jun-2007
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

<bean:define id="MailArrivalForm"
          name="MailArrivalForm"
          type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailArrivalForm"
          toScope="page" scope="request"/>
          
<business:sessionBean id="ContainerDetailsVOsSession"
          moduleName="mail.operations"
          screenID="mailtracking.defaults.mailarrival"
          method="get"
          attribute="containerDetailsVOs" />
<logic:present name="ContainerDetailsVOsSession">
	<bean:define id="ContainerDetailsVOsSession" name="ContainerDetailsVOsSession" toScope="page"/>
</logic:present>

<business:sessionBean id="ContainerDetailsVOSession"
          moduleName="mail.operations"
          screenID="mailtracking.defaults.mailarrival"
          method="get"
          attribute="containerDetailsVO" />
<logic:present name="ContainerDetailsVOSession">
	<bean:define id="ContainerDetailsVOSession" name="ContainerDetailsVOSession" toScope="page"/>
</logic:present>

<business:sessionBean id="oneTimeCatSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailarrival" method="get" attribute="oneTimeCat" />
<business:sessionBean id="oneTimeRISession" moduleName="mail.operations" screenID="mailtracking.defaults.mailarrival" method="get" attribute="oneTimeRSN" />
<business:sessionBean id="oneTimeHNISession" moduleName="mail.operations" screenID="mailtracking.defaults.mailarrival" method="get" attribute="oneTimeHNI" />
<business:sessionBean id="oneTimeMailClassSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailarrival" method="get" attribute="oneTimeMailClass" />
<business:sessionBean id="polSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailarrival" method="get" attribute="pols" />
<business:sessionBean id="oneTimeContainerTypeSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailarrival" method="get" attribute="oneTimeContainerType" />

<div id="pane1" > 

            <div class="tableContainer" id="div1"  style="height:245px;">
	      <table class="fixed-header-table" style="width:133%">

                <thead>
                    <tr >
                  	<td  width="3%"><div><input type="checkbox" name="masterDespatch" onclick="updateHeaderCheckBox(this.form,this,this.form.selectDespatch);"/></div></td>
                  	<td width="12%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.arrivemail.lbl.condocno" /><span class="iCargoMandatoryFieldIcon">*</span> </td>
                  	<td width="14%"class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.arrivemail.lbl.date" /><span class="iCargoMandatoryFieldIcon">*</span> </td>
                  	<td width="10%"class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.arrivemail.lbl.pa" /><span class="iCargoMandatoryFieldIcon">*</span>  </td>
                  	<td width="10%"class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.arrivemail.lbl.origin" /><span class="iCargoMandatoryFieldIcon">*</span>  </td>
                  	<td width="10%"class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.arrivemail.lbl.destination" /><span class="iCargoMandatoryFieldIcon">*</span>  </td>
                  	<td width="5%"class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.arrivemail.lbl.cat" />  </td>
					<td width="5%"class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.arrivemail.lbl.class" />  </td>
					<td width="7%"class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.arrivemail.lbl.sc" />  </td>
                  	<td width="5%"class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.arrivemail.lbl.dsn" /> <span class="iCargoMandatoryFieldIcon">*</span> </td>
                  	<td width="3%"class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.arrivemail.lbl.year" /> <span class="iCargoMandatoryFieldIcon">*</span> </td>
                  	<td width="5%"class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.arrivemail.lbl.mftdbags"/>  </td>
                  	<td width="5%"class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.arrivemail.lbl.mftdwt" /> </td>
                  	<td width="8%"class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.arrivemail.lbl.recvdbags" />  </td>
                  	<td width="14%"class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.arrivemail.lbl.recvdwt" />  </td>
                  	<td width="8%"class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.arrivemail.lbl.delvdbags" />  </td>
                  	<td width="9%"class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.arrivemail.lbl.delvdwt" />  </td>
                    </tr>
                </thead>

                <tbody id="despatchTableBody">

                <% int tabNum = 8; %>
                <% int despatch = 0; %>

                <logic:present name="ContainerDetailsVOSession" property="desptachDetailsVOs">
            	 <bean:define id="desptachDetailsVOsColl" name="ContainerDetailsVOSession" property="desptachDetailsVOs" scope="page" toScope="page"/>
		<logic:present name="desptachDetailsVOsColl" property="desptachDetailsVO">
		 <% int row = ((Collection)desptachDetailsVOsColl).size();%>

		 <logic:iterate id="desptachDetailsVO" name="desptachDetailsVOsColl" indexId="index">

		 <% tabNum = tabNum + row * despatch; %>
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
		 
		 <tr >

		  <logic:present name="desptachDetailsVO" property="operationalFlag">
		  <bean:define id="operationalFlag" name="desptachDetailsVO" property="operationalFlag" />
		  <html:hidden property="despOprFlag" value="<%=(String)operationalFlag%>"/>
		  </logic:present>
		  <logic:notPresent name="desptachDetailsVO" property="operationalFlag">
		  <html:hidden property="despOprFlag" value="NA"/>
		  </logic:notPresent>

                  <td  class="iCargoTableDataTd ic-center"><div>
                    <% String despatchKey=(String.valueOf(despatch));%>
	            <input type="checkbox" name="selectDespatch" value="<%=despatchKey%>"/>
                 </div> </td>


		<logic:present name="desptachDetailsVO" property="displayLabel"> 
			<logic:equal name="desptachDetailsVO" property="displayLabel" value="Y">
				<td class="iCargoTableDataTd">
					<bean:write name="desptachDetailsVO" property="consignmentNumber"/>
					<bean:define id="consignmentNumber" name="desptachDetailsVO" property="consignmentNumber" toScope="page"/>
					<ihtml:hidden property="conDocNo" value="<%=String.valueOf(consignmentNumber)%>"/>					   		
				</td>
				<td class="iCargoTableDataTd" style="width:8%;">					
					<bean:define id="consignmentDate" name="desptachDetailsVO" property="consignmentDate" toScope="page"/>
					<%String consignDt=TimeConvertor.toStringFormat(((LocalDate)consignmentDate).toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
					<%= consignDt %>
					<ihtml:hidden property="despatchDate" value="<%=consignDt%>"/>					   		
				</td>
				<td class="iCargoTableDataTd">
					<bean:write name="desptachDetailsVO" property="paCode"/>
					<bean:define id="paCode" name="desptachDetailsVO" property="paCode" toScope="page"/>
					<ihtml:hidden property="despatchPA" value="<%=String.valueOf(paCode)%>"/>					   		
				</td>
				<td class="iCargoTableDataTd">
					<bean:write name="desptachDetailsVO" property="originOfficeOfExchange"/>
					<bean:define id="originOfficeOfExchange" name="desptachDetailsVO" property="originOfficeOfExchange" toScope="page"/>
					<ihtml:hidden property="despatchOOE" value="<%=String.valueOf(originOfficeOfExchange)%>"/>					   		
				</td>
				<td class="iCargoTableDataTd">
					<bean:write name="desptachDetailsVO" property="destinationOfficeOfExchange"/>
					<bean:define id="destinationOfficeOfExchange" name="desptachDetailsVO" property="destinationOfficeOfExchange" toScope="page"/>
					<ihtml:hidden property="despatchDOE" value="<%=String.valueOf(destinationOfficeOfExchange)%>"/>					   		
				</td>
				<td class="iCargoTableDataTd">
					<bean:write name="desptachDetailsVO" property="mailCategoryCode"/>
					<bean:define id="mailCategoryCode" name="desptachDetailsVO" property="mailCategoryCode" toScope="page"/>
					<ihtml:hidden property="despatchCat" value="<%=String.valueOf(mailCategoryCode)%>"/>					   		
				</td>
				<td class="iCargoTableDataTd">
					<bean:write name="desptachDetailsVO" property="mailClass"/>
					<bean:define id="mailClass" name="desptachDetailsVO" property="mailClass" toScope="page"/>
					<ihtml:hidden property="despatchClass" value="<%=String.valueOf(mailClass)%>"/>					   		
				</td>
				<td class="iCargoTableDataTd">
					<bean:write name="desptachDetailsVO" property="mailSubclass"/>
					<bean:define id="mailSubclass" name="desptachDetailsVO" property="mailSubclass" toScope="page"/>
					<ihtml:hidden property="despatchSC" value="<%=String.valueOf(mailSubclass)%>"/>					   		
				</td>
				<td class="iCargoTableDataTd">
					<bean:write name="desptachDetailsVO" property="dsn"/>
					<bean:define id="dsn" name="desptachDetailsVO" property="dsn" toScope="page"/>
					<ihtml:hidden property="despatchDSN" value="<%=String.valueOf(dsn)%>"/>					   		
				</td>
				<td class="iCargoTableDataTd">
					<bean:write name="desptachDetailsVO" property="year"/>
					<bean:define id="year" name="desptachDetailsVO" property="year" toScope="page"/>
					<ihtml:hidden property="despatchYear" value="<%=String.valueOf(year)%>"/>					   		
				</td>
			</logic:equal>
			<logic:equal name="desptachDetailsVO" property="displayLabel" value="N">		
				 <td class="iCargoTableDataTd">
				 	<logic:notPresent name="desptachDetailsVO" property="consignmentNumber">
				 		<ihtml:text property="conDocNo" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_CONDOCNO" value="" readonly="<%=toDisable%>"  style="width:100px" maxlength="13"/>
				 	</logic:notPresent>
				 	<logic:present name="desptachDetailsVO" property="consignmentNumber">
						<bean:define id="consignmentNum" name="desptachDetailsVO" property="consignmentNumber" toScope="page"/>
						<ihtml:text property="conDocNo" value="<%=(String)consignmentNum%>" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_CONDOCNO" readonly="<%=toDisable%>"  style="width:100px" maxlength="13"/>
				 	</logic:present>
				  </td>
				  <td class="iCargoTableDataTd">
				   	<logic:notPresent name="desptachDetailsVO" property="consignmentDate">
						<ibusiness:calendar property="despatchDate" id="despatchDate" indexId="index" type="image" componentID="CMB_MAILTRACKING_DEFAULTS_ARRIVEMAIL_DSNDATE"  value="" readonly="<%=toDisable%>"/>
				   	</logic:notPresent>
				   	<logic:present name="desptachDetailsVO" property="consignmentDate">
						<bean:define id="consignmentDate" name="desptachDetailsVO" property="consignmentDate" toScope="page"/>
						<%String consignDt=TimeConvertor.toStringFormat(((LocalDate)consignmentDate).toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
						<ibusiness:calendar property="despatchDate" id="despatchDate" indexId="index" value="<%=(String)consignDt%>" type="image" componentID="CMB_MAILTRACKING_DEFAULTS_ARRIVEMAIL_DSNDATE"  readonly="<%=toDisable%>"/>
				   	 </logic:present>
				  </td>
				  <td class="iCargoTableDataTd">
				  	<logic:notPresent name="desptachDetailsVO" property="paCode">
						<ihtml:text property="despatchPA" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_DSNPA" value="" maxlength="5" readonly="<%=toDisable%>" />
				  	</logic:notPresent>
				  	<logic:present name="desptachDetailsVO" property="paCode">
						<bean:define id="paCode" name="desptachDetailsVO" property="paCode" toScope="page"/>
						<ihtml:text property="despatchPA" value="<%=(String)paCode%>" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_DSNPA" maxlength="5" readonly="<%=toDisable%>" />
				   	</logic:present>
				   	 <%if(toDisable){%>
						<img name="despatchPALov" id="despatchPALov<%=index%>" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16" disabled>
				    	<%}else{%>
						<img name="despatchPALov" id="despatchPALov<%=index%>" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16">
					<%}%>
				  </td>
				  <td class="iCargoTableDataTd">
					<logic:notPresent name="desptachDetailsVO" property="originOfficeOfExchange">
						<ihtml:text property="despatchOOE" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_DSNOOE" value="" maxlength="6" readonly="<%=toDisable%>" />
					</logic:notPresent>
					<logic:present name="desptachDetailsVO" property="originOfficeOfExchange">
						<bean:define id="originOE" name="desptachDetailsVO" property="originOfficeOfExchange" toScope="page"/>
						<ihtml:text property="despatchOOE" value="<%=(String)originOE%>" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_DSNOOE" maxlength="6" readonly="<%=toDisable%>" />
					</logic:present>
					<%if(toDisable){%>
						<img name="despatchOOELov" id="despatchOOELov<%=index%>" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16" disabled>
					<%}else{%>
						<img name="despatchOOELov" id="despatchOOELov<%=index%>" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16">
					<%}%>
				  </td>
				  <td class="iCargoTableDataTd">
				  	<logic:notPresent name="desptachDetailsVO" property="destinationOfficeOfExchange">
						<ihtml:text property="despatchDOE" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_DSNDOE" value="" maxlength="6" readonly="<%=toDisable%>" />
				  	</logic:notPresent>
				  	<logic:present name="desptachDetailsVO" property="destinationOfficeOfExchange">
						<bean:define id="destinationOE" name="desptachDetailsVO" property="destinationOfficeOfExchange" toScope="page"/>
						<ihtml:text property="despatchDOE" value="<%=(String)destinationOE%>" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_DSNDOE" maxlength="6" readonly="<%=toDisable%>" />
				   	</logic:present>
				    	<%if(toDisable){%>
						<img name="despatchDOELov" id="despatchDOELov<%=index%>" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16" disabled>
				   	 <%}else{%>
						<img name="despatchDOELov" id="despatchDOELov<%=index%>" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16">
				    	<%}%>
				  </td>
				  <td class="iCargoTableDataTd">
					<% String catDesValue = ""; %>
					<logic:present name="desptachDetailsVO" property="mailCategoryCode">
						<bean:define id="desCtgyCode" name="desptachDetailsVO" property="mailCategoryCode" toScope="page"/>
						<% catDesValue = (String) desCtgyCode; %>
					</logic:present>
					<ihtml:select property="despatchCat" componentID="CMB_MAILTRACKING_DEFAULTS_ARRIVEMAIL_DSNCAT" value="<%=catDesValue%>" disabled="<%=toDisable%>"  style="width:35px">
						<bean:define id="oneTimeCatSess" name="oneTimeCatSession" toScope="page" />
						<logic:iterate id="oneTimeCatVO" name="oneTimeCatSess" >
							<bean:define id="fieldValue" name="oneTimeCatVO" property="fieldValue" toScope="page" />
							<html:option value="<%=(String)fieldValue %>"><bean:write name="oneTimeCatVO" property="fieldValue"/></html:option>
						</logic:iterate>
					</ihtml:select>
				  </td>
				  <td class="iCargoTableDataTd">
					<% String classValue = ""; %>
					<logic:present name="desptachDetailsVO" property="mailClass">
						<bean:define id="mailclass" name="desptachDetailsVO" property="mailClass" toScope="page"/>
						<% classValue = (String) mailclass; %>
					</logic:present>
					<ihtml:select property="despatchClass" componentID="CMB_MAILTRACKING_DEFAULTS_ARRIVEMAIL_CLASS" value="<%=classValue%>" disabled="<%=toDisable%>"  style="width:35px">
						<bean:define id="oneTimeMailClassSess" name="oneTimeMailClassSession" toScope="page" />
						<logic:iterate id="oneTimeMailClassVO" name="oneTimeMailClassSess" >
							<bean:define id="fieldValue" name="oneTimeMailClassVO" property="fieldValue" toScope="page" />
							<html:option value="<%=(String)fieldValue %>"><bean:write name="oneTimeMailClassVO" property="fieldValue"/></html:option>
						</logic:iterate>
					</ihtml:select>
				  </td>
				  <td class="iCargoTableDataTd">
					<% String subclassValue = ""; %>
					<logic:notPresent name="desptachDetailsVO" property="mailSubclass">
						<ihtml:text property="despatchSC" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_DESPATCHSC" value="" maxlength="2" readonly="<%=toDisable%>"  />
					</logic:notPresent>
					<logic:present name="desptachDetailsVO" property="mailSubclass">
						<bean:define id="mailSubclass" name="desptachDetailsVO" property="mailSubclass" toScope="page"/>
						<% subclassValue = (String) mailSubclass;
						   int arrays=subclassValue.indexOf("_");
						   if(arrays==-1){%>
							<ihtml:text property="despatchSC" value="<%=(String)mailSubclass%>" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_DESPATCHSC" maxlength="2" readonly="<%=toDisable%>" />
						   <%}else{%>
							<ihtml:text property="despatchSC" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_DESPATCHSC" value="" maxlength="2" readonly="<%=toDisable%>"  />
						   <%}%>
					</logic:present>
					  <%if(toDisable){%>
						 <img name="despatchSCLov" id="despatchSCLov<%=index%>" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16" disabled>
					  <%}else{%>
						 <img name="despatchSCLov" id="despatchSCLov<%=index%>" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16">
					  <%}%>
				</td>
				<td class="iCargoTableDataTd">
					<logic:notPresent name="desptachDetailsVO" property="dsn">
						<ihtml:text property="despatchDSN" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_DSN" value="" maxlength="4" readonly="<%=toDisable%>" />
					</logic:notPresent>
					<logic:present name="desptachDetailsVO" property="dsn">
						<bean:define id="dsn" name="desptachDetailsVO" property="dsn" toScope="page"/>
						<ihtml:text property="despatchDSN" value="<%=(String)dsn%>" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_DSN" maxlength="4" readonly="<%=toDisable%>" />
					</logic:present>
				</td>
				<td class="iCargoTableDataTd">
					<logic:notPresent name="desptachDetailsVO" property="year">
						<ihtml:text property="despatchYear" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_DESPATCHYR" value="" maxlength="1" readonly="<%=toDisable%>"  style="width:20px"/>
					</logic:notPresent>
					<logic:present name="desptachDetailsVO" property="year">
						<bean:define id="year" name="desptachDetailsVO" property="year" toScope="page"/>
						<ihtml:text property="despatchYear" value="<%=year.toString()%>" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_DESPATCHYR" maxlength="1" readonly="<%=toDisable%>"  style="width:20px"/>
					</logic:present>
				</td>
			</logic:equal>
		</logic:present>

                  <td class="iCargoTableDataTd" style="text-align:right">
                      <bean:write name="desptachDetailsVO" property="acceptedBags" format="####"/>
                  </td>

                  <td class="iCargoTableDataTd" style="text-align:right">
                      <common:write name="desptachDetailsVO" property="acceptedWeight"  unitFormatting="true"/>
                  </td>

                  <td class="iCargoTableDataTd">
                     <logic:notPresent name="desptachDetailsVO" property="receivedBags">
			  <ihtml:text property="receivedBags" indexId="index" styleId="receivedBags" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_RECVDBAGS" value=""  style="width:60px;text-align:right;" maxlength="5"/>
		     </logic:notPresent>
		     <logic:present name="desptachDetailsVO" property="receivedBags">
			<bean:define id="receivedBags" name="desptachDetailsVO" property="receivedBags" toScope="page"/>
			  <ihtml:text property="receivedBags" indexId="index" styleId="receivedBags" value="<%=receivedBags.toString()%>" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_RECVDBAGS"  style="width:60px;text-align:right;" maxlength="5"/>
		     </logic:present>
                  </td>


                  <td class="iCargoTableDataTd">
                 <logic:notPresent name="desptachDetailsVO" property="receivedWeight">
					<ibusiness:unitdef id="receivedWt" unitTxtName="receivedWt" label=""  unitReq = "false" dataName="sampleStdWt"
						unitValueMaxLength="8" unitValueStyle="iCargoEditableTextFieldRowColor1" title="Received Weight"
						unitValue="0.0" style="background :'<%=color%>';text-align:right;"
						indexId="index" styleId="receivedWt" unitTypePassed="MWT"/>
		            <%--        
					<ihtml:text property="receivedWt" indexId="index" styleId="receivedWt" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_RECVDWT" value=""  style="width:70px;text-align:right;"/>
					--%>
		    </logic:notPresent>
		    <logic:present name="desptachDetailsVO" property="receivedWeight">
		 	<bean:define id="received" name="desptachDetailsVO" property="receivedWeight" toScope="page" type="com.ibsplc.icargo.framework.util.unit.Measure"/>
					<% request.setAttribute("sampleStdWt",received); %>
					<ibusiness:unitdef id="receivedWt" unitTxtName="receivedWt" label=""  unitReq = "false" dataName="sampleStdWt"
						unitValueMaxLength="8" unitValueStyle="iCargoEditableTextFieldRowColor1" title="Received Weight"
						style="background :'<%=color%>';text-align:right;"
						indexId="index" styleId="receivedWt" unitTypePassed="MWT" />
				  
		 	<%--
			<ihtml:text property="receivedWt" indexId="index" styleId="receivedWt" value="<%=received.toString()%>" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_RECVDWT"  style="width:70px;text-align:right;"/>
		    --%>
		    </logic:present>
                  </td>


                  <td class="iCargoTableDataTd">
		    <logic:notPresent name="desptachDetailsVO" property="deliveredBags">
			<ihtml:text property="deliveredBags" indexId="index" styleId="deliveredBags" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_DELVDBAGS" value=""  style="width:60px;text-align:right;" maxlength="5"/>
  		    </logic:notPresent>
		    <logic:present name="desptachDetailsVO" property="deliveredBags">
			<bean:define id="deliveredBags" name="desptachDetailsVO" property="deliveredBags" toScope="page"/>
			<ihtml:text property="deliveredBags" indexId="index" styleId="deliveredBags" value="<%=deliveredBags.toString()%>" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_DELVDBAGS"  style="width:60px;text-align:right;" maxlength="5"/>
		    </logic:present>
		  </td>


		  <td class="iCargoTableDataTd">
		    <logic:notPresent name="desptachDetailsVO" property="deliveredWeight">
			<ihtml:text property="deliveredWt" indexId="index" styleId="deliveredWt" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_DELVDWT" value=""  style="width:70px;text-align:right;"/>
		    </logic:notPresent>
		    <logic:present name="desptachDetailsVO" property="deliveredWeight">
			<bean:define id="deliveredWeight" name="desptachDetailsVO" property="deliveredWeight" toScope="page" type="com.ibsplc.icargo.framework.util.unit.Measure"/>
			<ihtml:text property="deliveredWt" indexId="index" styleId="deliveredWt" value="<%=deliveredWeight.toString()%>" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_DELVDWT"  style="width:70px;text-align:right;"/>
		    </logic:present>
                  </td>

                </tr>
               </logic:iterate>
			    </logic:present>
	       </logic:present>
	       
	       
	       <!-- templateRow -->
		<tr template="true" id="despatchTemplateRow" style="display:none">

			<ihtml:hidden property="despatchOpFlag" value="NOOP" />
			<td  class="iCargoTableDataTd ic-center"><div >

			<input type="checkbox" name="selectDespatch" ></div></td>


			<td>
			       <ihtml:text property="conDocNo" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_CONDOCNO" value="" style="width:100px" maxlength="13"/>
			</td>

			<td>
				<ibusiness:calendar property="despatchDate" id="despatchDate" indexId="index" type="image" componentID="CMB_MAILTRACKING_DEFAULTS_ARRIVEMAIL_DSNDATE"  value="" />
			</td>

			<td>
				<ihtml:text property="despatchPA" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_DSNPA" value="" maxlength="5" />
				<img name="despatchPALov" id="despatchPALov" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16">
			</td>

			<td>
				<ihtml:text property="despatchOOE" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_DSNOOE" value="" maxlength="6" />
				<img name="despatchOOELov" id="despatchOOELov" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16">
			</td>

			<td>
				<ihtml:text property="despatchDOE" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_DSNDOE" value="" maxlength="6" />
				<img name="despatchDOELov" id="despatchDOELov" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16">
			</td>

			<td>
			    <ihtml:select property="despatchCat" componentID="CMB_MAILTRACKING_DEFAULTS_ARRIVEMAIL_DSNCAT" value="" style="width:35px">
				<bean:define id="oneTimeCatSess" name="oneTimeCatSession" toScope="page" />
				<logic:iterate id="oneTimeCatVO" name="oneTimeCatSess" >
				  <bean:define id="fieldValue" name="oneTimeCatVO" property="fieldValue" toScope="page" />
				     <html:option value="<%=(String)fieldValue %>"><bean:write name="oneTimeCatVO" property="fieldValue"/></html:option>
				</logic:iterate>
			    </ihtml:select>
			</td>

			<td>
			    <ihtml:select property="despatchClass" componentID="CMB_MAILTRACKING_DEFAULTS_ARRIVEMAIL_CLASS" value="" style="width:35px">
				<bean:define id="oneTimeMailClassSess" name="oneTimeMailClassSession" toScope="page" />
				<logic:iterate id="oneTimeMailClassVO" name="oneTimeMailClassSess" >
				  <bean:define id="fieldValue" name="oneTimeMailClassVO" property="fieldValue" toScope="page" />
				     <html:option value="<%=(String)fieldValue %>"><bean:write name="oneTimeMailClassVO" property="fieldValue"/></html:option>
				</logic:iterate>
			    </ihtml:select>
			</td>

			<td>
				<ihtml:text property="despatchSC" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_DESPATCHSC" value="" maxlength="2" />
			        <img name="despatchSCLov" id="despatchSCLov" src="<%=request.getContextPath()%>/images/lov.gif" width="16" height="16">
			
			</td>

			<td>
				<ihtml:text property="despatchDSN" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_DSN" value="" maxlength="4" />
			</td>

			<td>
				<ihtml:text property="despatchYear" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_DESPATCHYR" value="" maxlength="1" style="width:20px"/>
			</td>

			<td style="text-align:right;">
			         0
			</td>

			<td style="text-align:right;">
			         0.0
			</td>

			<td>
			        <ihtml:text property="receivedBags" indexId="index" styleId="receivedBags" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_RECVDBAGS" value=""  style="width:60px;text-align:right;" maxlength="5"/>
			</td>

			<td>
			
			    
					<ibusiness:unitdef id="receivedWt" unitTxtName="receivedWt" label=""  unitReq = "false" 
						unitValueMaxLength="8" unitValueStyle="iCargoEditableTextFieldRowColor1" title="Received Weight"
						unitValue="0.0" style="background :'<%=color%>';text-align:right;"
						indexId="index" styleId="receivedWt" unitTypePassed="MWT"  />
			  <%--  
				<ihtml:text property="receivedWt" indexId="index" styleId="receivedWt" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_RECVDWT" value=""  style="width:70px;text-align:right;"/>
			  --%>	
			</td>

			<td>
				<ihtml:text property="deliveredBags" indexId="index" styleId="deliveredBags" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_DELVDBAGS" value=""  style="width:60px;text-align:right;" maxlength="5"/>
			</td>

			<td>
				<ihtml:text property="deliveredWt" indexId="index" styleId="deliveredWt" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_DELVDWT" value=""  style="width:70px;text-align:right;"/>
			</td>

		</tr>
		<!--template row ends-->
	       
	       

	      </tbody>
            </table>

	   </div>

          </div>
