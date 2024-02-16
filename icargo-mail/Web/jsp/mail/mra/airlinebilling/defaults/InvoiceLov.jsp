<%--****************************************************************
* Project	 				: iCargo
* Module Code & Name				: Mra-AirlineBilling-Defaults
* File Name					: InvoiceLov.jsp
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
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.InvoiceLOVForm" %>
<%@ page import="com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineInvoiceLovVO" %>


<html:html>
<head> 
	
	
		
		
			
	
<common:include type="script" src="/js/mail/mra/airlinebilling/defaults/InvoiceLov_Script.jsp" />
<title><common:message   bundle="MRAArlBlgInvoiceLovResource" key="mra.airlinebilling.defaults.invoicelov.title" scope="request"/></title>
<meta name="decorator" content="popuppanelrestyledui">
</head>

<body id="bodyStyle">
	
	
	
	
<!--CONTENT STARTS-->
<business:sessionBean
		  id="oneTimeValues"
		  moduleName="mailtracking.mra.airlinebilling"
		  screenID="mailtracking.mra.airlinebilling.defaults.invoicelov"
		  method="get"
    		  attribute="billingType" />

<div id="divmain" class="iCargoPopUpContent">
<ihtml:form action="/mra.airlinebilling.defaults.showInvoiceLov.do" styleClass="ic-main-form">

	<bean:define id="InvoiceLovForm" name="InvLovForm"
		     type ="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.InvoiceLOVForm"/>
	<ihtml:hidden  property="lovaction"  />
	<ihtml:hidden  property="selectedValues"  />
	<ihtml:hidden  property="lastPageNum" />
	<ihtml:hidden  property="displayPage" />
	<ihtml:hidden  property="multiselect" />
	<ihtml:hidden  property="pagination" />
	<ihtml:hidden  property="formCount" />
	<ihtml:hidden  property="lovTxtFieldName" />
	<ihtml:hidden  property="lovDescriptionTxtFieldName" />
	<ihtml:hidden  property="lovNameTxtFieldName" />
	<ihtml:hidden  property="lovNameDateFieldName" />
	<ihtml:hidden  property="index" />
	<ihtml:hidden  property="strClearancePeriod" />
	<ihtml:hidden  property="strInvoiceNumber" />
	<ihtml:hidden  property="strAirlineCode" />
	<ihtml:hidden  property="strBillingType" />
	<ihtml:hidden  property="strDateField" />
	<%String checkVal = "";%>
	<%String strLovNameTxtFieldName = "";%>
    <%String strLovDateField = "";%>
	<div class="ic-content-main">
			<span class="ic-page-title ic-display-none">
				<common:message key="mra.airlinebilling.defaults.invoicelov.page" />
			</span>
			<div class="ic-head-container">		
				<div class="ic-filter-panel">
				<div class="ic-row ic-label-50">
							<div class="ic-input ic-split-50">
								<label>
					<common:message key="mra.airlinebilling.defaults.invoicelov.invoicenumber" />
								</label>
								<ihtml:text name="InvoiceLovForm" property="code" componentID="CMP_MRA_AIRLINEBILLING_INVOICELOV_INVOICENUMBER" maxlength="14" readonly="false"/>
							</div>
					<div class="ic-input ic-split-50">
								<label>
									  <common:message key="mra.airlinebilling.defaults.invoicelov.billingtype" />
								</label>
								<ihtml:select property="billingType" componentID="CMP_MRA_AIRLINEBILLING_INVOICELOV_BILLINGTYPE">
								<logic:present name="oneTimeValues">
									<ihtml:option value="">Both</ihtml:option>
									<logic:iterate id="oneTimeValue" name="oneTimeValues">
										<bean:define id="fieldValue" name="oneTimeValue" property="fieldValue"/>
										<bean:define id="fieldDescription" name="oneTimeValue" property="fieldDescription"/>
										<ihtml:option value="<%=(String)fieldValue %>"><bean:write name="fieldDescription"/></ihtml:option>
									</logic:iterate>
								</logic:present>
								</ihtml:select>
							</div>
							
							
						</div>
				<div class="ic-row ic-label-50">
							<div class="ic-input ic-split-50">
								<label>
									 <common:message key="mra.airlinebilling.defaults.invoicelov.clearanceperiod" />
								</label>
								<ihtml:text name="InvoiceLovForm" property="clearancePeriod" componentID="CMP_MRA_AIRLINEBILLING_INVOICELOV_CLEARANCEPERIOD" maxlength="10" readonly="false" />
							</div>
					<div class="ic-input ic-split-50">
								<label>
									 <common:message key="mra.airlinebilling.defaults.invoicelov.airlinecode" />
								</label>
								<ihtml:text name="InvoiceLovForm" property="airlineCode" componentID="CMP_MRA_AIRLINEBILLING_INVOICELOV_AIRLINECODE" maxlength="3" readonly="false" />
							</div>
				</div>
				<div class="ic-row">
							<div class="ic-button-container">
								
									<ihtml:nbutton property="btnList" componentID="CMP_MRA_AIRLINEBILLING_INVOICELOV_LIST" >
										<common:message key="mra.airlinebilling.defaults.invoicelov.btn.list"/>
									</ihtml:nbutton>
									<ihtml:nbutton property="btnClear" componentID="CMP_MRA_AIRLINEBILLING_INVOICELOV_CLEAR" >
										<common:message key="mra.airlinebilling.defaults.invoicelov.btn.clear"/>
									</ihtml:nbutton>
									</div>
								
							</div>
						
					
				</div>
			</div>
			<div class="ic-main-container">
			<div class="ic-row">
				<logic:present name="InvoiceLovForm"  property="invoiceLovVos">
				<bean:define id="invoiceLovVos" name="InvoiceLovForm"  property="invoiceLovVos"  toScope="page"/>

					<logic:present name="invoiceLovVos">
					<common:paginationTag
						pageURL="javascript:submitList('lastPageNum','displayPage')"
						name="invoiceLovVos"
						display="label"
						labelStyleClass="iCargoResultsLabel"
						lastPageNum="<%=InvoiceLovForm.getLastPageNum() %>" />
					<div class="ic-button-container">
					<common:paginationTag
						pageURL="javascript:submitList('lastPageNum','displayPage')"
						name="invoiceLovVos"
						display="pages"
						linkStyleClass="iCargoLink" disabledLinkStyleClass="iCargoLink"	
						lastPageNum="<%=InvoiceLovForm.getLastPageNum()%>"/>
					</div>
					</logic:present>
					</logic:present>
					</div>
			<div class="ic-row">

				<bean:define id="strFormCount" name="InvoiceLovForm" property="formCount"  />
				<bean:define id="strLovTxtFieldName"  name="InvoiceLovForm" property="lovTxtFieldName" />
				<logic:present name="InvoiceLovForm" property="lovNameDateFieldName">
				<bean:define id="strLovDateFieldName"  name="InvoiceLovForm" property="lovNameDateFieldName" />
				<%strLovDateField = (String)strLovDateFieldName;%>
				</logic:present>
				<bean:define id="strLovDescriptionTxtFieldName" name="InvoiceLovForm" property="lovDescriptionTxtFieldName" />
				<bean:define id="strlovCodeNameTxtFieldName" name="InvoiceLovForm" property="lovNameTxtFieldName" />

				<bean:define id="strSelectedValues" name="InvoiceLovForm" property="selectedValues" />
				<bean:define id="arrayIndex" name="InvoiceLovForm" property="index"/>
				<bean:define id="strMultiselect" name="InvoiceLovForm" property="multiselect" />
					<logic:present name="InvoiceLovForm" property="lovNameTxtFieldName">
					<bean:define id="strLovNameTextFieldName" name="InvoiceLovForm" property="lovNameTxtFieldName" />
					<%strLovNameTxtFieldName = (String)strLovNameTextFieldName;%>
					</logic:present>
			</div>
				<div class="ic-row">
			<div class="tableContainer" id="div1" style="height:220px">
	    <table class="fixed-header-table" id="clearanceperiod" >
							<thead>
								<tr>
		<td  class="iCargoTableHeader" width="1%" ></td>
		<td  class="iCargoTableHeader" width="5%" ><common:message key="mra.airlinebilling.defaults.invoicelov.invoicenumber"/></td>
		<td  class="iCargoTableHeader" width="4%"><common:message key="mra.airlinebilling.defaults.invoicelov.clearanceperiod"/></td>
		<td  class="iCargoTableHeader" width="4%"><common:message key="mra.airlinebilling.defaults.invoicelov.billingtype"/></td>
		<td  class="iCargoTableHeader" width="4%"><common:message key="mra.airlinebilling.defaults.invoicelov.airlinecode"/></td>
								</tr>
							</thead>
							<tbody>
								<logic:present name="invoiceLovVos">
								<logic:iterate id="invoiceLovVo" name="invoiceLovVos" indexId="rowIndex">
									<tr>
										<logic:notEqual name="InvoiceLovForm" property="multiselect" value="Y">
											<a href="#" ondblclick="setAllValuesOnDoubleClick('<%=((AirlineInvoiceLovVO)invoiceLovVo).getInvoiceNumber()%>','<%=((AirlineInvoiceLovVO)invoiceLovVo).getClearancePeriod()%>','<%=((AirlineInvoiceLovVO)invoiceLovVo).getAirlineCode()%>','<%=((AirlineInvoiceLovVO)invoiceLovVo).getInvoiceDate()%>','<%= strLovTxtFieldName%>','<%=strLovDescriptionTxtFieldName%>','<%=strlovCodeNameTxtFieldName%>',<%=arrayIndex%>)"/>
										</logic:notEqual>
										<logic:equal name="InvoiceLovForm" property="multiselect" value="Y">
			<td>
										<%
										if(((String)strSelectedValues).contains(((AirlineInvoiceLovVO)invoiceLovVo).getInvoiceNumber() )){ %>
											<ihtml:hidden name="InvoiceLovForm" property="description" value="<%=((AirlineInvoiceLovVO)invoiceLovVo).getClearancePeriod()%>"/>
											<input type="checkbox" name="selectCheckBox" value="<%=(((AirlineInvoiceLovVO)invoiceLovVo).getInvoiceNumber())%>"  checked="checked"/>
										<%}else{ %>
											<ihtml:hidden name="InvoiceLovForm" property="description" value="<%=((AirlineInvoiceLovVO)invoiceLovVo).getClearancePeriod()%>"/>
											<input type="checkbox" name="selectCheckBox" value="<%=((AirlineInvoiceLovVO)invoiceLovVo).getInvoiceNumber()%>"  />
										<% } %>
										</td>
										</logic:equal>

										<logic:notEqual name="InvoiceLovForm" property="multiselect" value="Y">
			<td>
										<logic:present name="invoiceLovVo" property="airlineCode">
											<% checkVal = ((AirlineInvoiceLovVO)invoiceLovVo).getInvoiceNumber()+"~"+((AirlineInvoiceLovVO)invoiceLovVo).getClearancePeriod()+"~"+((AirlineInvoiceLovVO)invoiceLovVo).getAirlineCode()+"~"+((AirlineInvoiceLovVO)invoiceLovVo).getInvoiceDate();%>
										</logic:present>
										<logic:notPresent name="invoiceLovVo" property="airlineCode">
														<% checkVal = ((AirlineInvoiceLovVO)invoiceLovVo).getInvoiceNumber()+"~"+((AirlineInvoiceLovVO)invoiceLovVo).getClearancePeriod()+"~"+" "+"~"+" ";%>
										</logic:notPresent>
                                        
										<%
										if(   ((String)strSelectedValues).equals(( (AirlineInvoiceLovVO)invoiceLovVo).getInvoiceNumber() )){ %>
										
											<input type="checkbox" name="selectCheckBox" value="<%=checkVal%>"  onclick="singleSelect('<%=checkVal%>')" checked="checked" />
										
										<%}else{ %>
											<input type="checkbox" name="selectCheckBox" value="<%=checkVal%>"  onclick="singleSelect('<%=checkVal%>')"/>
										
										<% } %>


										</td>
										
										</logic:notEqual>
										<logic:notEqual name="InvoiceLovForm" property="multiselect" value="Y">
										<logic:present name="invoiceLovVo" property="invoiceDate">
										
											<% checkVal = ((AirlineInvoiceLovVO)invoiceLovVo).getInvoiceNumber()+"~"+((AirlineInvoiceLovVO)invoiceLovVo).getClearancePeriod()+"~"+((AirlineInvoiceLovVO)invoiceLovVo).getAirlineCode()+"~"+((AirlineInvoiceLovVO)invoiceLovVo).getInvoiceDate();%>
										</logic:present>
										<logic:notPresent name="invoiceLovVo" property="invoiceDate">
														<% checkVal = ((AirlineInvoiceLovVO)invoiceLovVo).getInvoiceNumber()+"~"+((AirlineInvoiceLovVO)invoiceLovVo).getClearancePeriod()+"~"+" "+"~"+" ";%>
										</logic:notPresent>

										<%
										if(   ((String)strSelectedValues).equals(( (AirlineInvoiceLovVO)invoiceLovVo).getInvoiceNumber() )){ %>
											<input type="checkbox" name="selectCheckBox" value="<%=checkVal%>"  onclick="singleSelect('<%=checkVal%>')" checked="checked" />
										
										
										
										<%}else{ %>
										
										
										
											<input type="checkbox" name="selectCheckBox" value="<%=checkVal%>"  onclick="singleSelect('<%=checkVal%>')"/>
											
											
										<% } %>
                                        
										</logic:notEqual>
										
										

			                             <td>
											<bean:write name="invoiceLovVo" property="invoiceNumber"/>
										</td>
			                             <td>
											<bean:write name="invoiceLovVo" property="clearancePeriod"/>

										</td>
			                            <td>

										<logic:present name="invoiceLovVo" property="interlineBillingType">&nbsp;
												<logic:equal name="invoiceLovVo" property="interlineBillingType" value="I">
													<common:message key="mra.airlinebilling.defaults.invoicelov.billingtype.inward"/>
												</logic:equal>
												<logic:equal name="invoiceLovVo" property="interlineBillingType" value="O">
													<common:message key="mra.airlinebilling.defaults.invoicelov.billingtype.ouward"/>
												</logic:equal>
										</logic:present>

										</td>
			                             <td>
											<bean:write name="invoiceLovVo" property="airlineCode"/>
										</td>
										<logic:notEqual name="InvoiceLovForm" property="multiselect" value="Y">
										</a>
										</logic:notEqual>
									</tr>

									</logic:iterate>
								</logic:present>
							</tbody>
						</table>
					</div>
			</div>
			<div class="ic-row">
						 <%String arr[] ={"",""};%>
						<%String emty="";%>
						
						<%if(checkVal!=null && ""!=checkVal){
							 arr= checkVal.split("~");
					  }
						  else{
							arr = new String[4];  
							arr[0]=emty;
							arr[1]=emty;
							arr[2]=emty;
							arr[3]=emty;
						  }
						  %>
						 
				</div>

			</div>
			<div class="ic-foot-container">
				<div class="ic-button-container">
					<input type="button" name="btnOk" value="OK" class="iCargoButtonSmall" onclick="setValueofInvoiceNoAndClearancePrd('<%=strMultiselect%>','<%=strFormCount%>','<%=strLovTxtFieldName%>','<%=strLovDescriptionTxtFieldName %>','<%=strlovCodeNameTxtFieldName %>','<%=strLovDateField%>','<%=arrayIndex%>','<%=arr[0]%>','<%=arr[1]%>','<%=arr[2]%>','<%=arr[3]%>')" />
					<ihtml:nbutton property="btnClose" componentID="CMP_MRA_AIRLINEBILLING_INVOICELOV_CLOSE" >
						<common:message key="mra.airlinebilling.defaults.invoicelov.btn.close"/>
					</ihtml:nbutton>
				</div>
			</div>
		</div>
</ihtml:form>
</div>
<!---CONTENT ENDS-->


	</body>
</html:html>
