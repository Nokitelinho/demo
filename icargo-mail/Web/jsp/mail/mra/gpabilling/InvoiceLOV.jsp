<%--************************************************************
* Project	 			: iCargo
* Module Code & Name	: mra-gpabilling
* File Name				: InvoiceLOV.jsp
* Date					: 08/02/2007
* Author(s)				: A-2408
 ****************************************************************--%>
<%
	response.setDateHeader("Expires",0);
	response.setHeader("Pragma","no-cache");

	if (request.getProtocol().equals("HTTP/1.1")) {
		response.setHeader("Cache-Control","no-cache");
	}
%>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.InvoiceLovForm" %>
<%@ page import="com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceLovVO" %>
		
				
	
<html:html>
<head> 
	
	
			
	


	<meta name="decorator" content="popuppanelrestyledui">
<common:include type="script" src="/js/mail/mra/gpabilling/InvoiceLOV_Script.jsp"/>



	
</head>

<body>
	
	



<div id="pageDiv"  class="iCargoPopUpContent">


<ihtml:form action="/showInvoiceLOV.do" styleclass="ic-main-form">

<bean:define id="InvoiceLovForm" name="GPAInvoiceLovForm" type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.InvoiceLovForm" toScope="page" />

<logic:present name="invoiceLovForm"  property="lovaction" >
<bean:define id="lovaction" name="invoiceLovForm" property=" lovaction" toScope="page"/>
</logic:present>
<!--  Hidden to store the selections in case of pagination-->
<ihtml:hidden name="InvoiceLovForm" property="lovaction"  />
<ihtml:hidden name="InvoiceLovForm" property="selectedValues"  />
<ihtml:hidden name="InvoiceLovForm" property="lastPageNum" />
<ihtml:hidden name="InvoiceLovForm" property="displayPage" />
<ihtml:hidden name="InvoiceLovForm" property="multiselect" />
<ihtml:hidden name="InvoiceLovForm" property="pagination" />
<ihtml:hidden name="InvoiceLovForm" property="formCount" />
<ihtml:hidden name="InvoiceLovForm" property="lovTxtFieldName" />
<ihtml:hidden name="InvoiceLovForm" property="lovNameTxtFieldName" />
<ihtml:hidden name="InvoiceLovForm" property="lovDescriptionTxtFieldName" />
<ihtml:hidden name="InvoiceLovForm" property="index" />

<!--  END -->
     <div class="ic-content-main">
		<span class="ic-page-title ic-display-none">
		    <common:message   key="mailtracking.mra.gpabilling.invoicelov" scope="request"/>
		</span>
		<div class="ic-head-container">		
			<div class="ic-filter-panel">
				<div class="ic-input ic-split-60 ic-label-40">
				    <label>
				        <common:message   key="mailtracking.mra.gpabilling.invnumber" scope="request"/>
					</label>
					    <ihtml:text property="code" name="InvoiceLovForm" componentID="MRA_GPABILLING_INVLOV_INVNUM"  maxlength="20" style="width:150px"/>
				</div>
				<div class="ic-input ic-split-40 ic-label-40">
					<label>
					    <common:message   key="mailtracking.mra.gpabilling.gpaCode" scope="request"/>
					</label> 
                        <ihtml:text property="gpaCodeFilter" name="InvoiceLovForm" componentID="MRA_GPABILLING_INVLOV_GPACODE"  maxlength="20"/>					
				</div>
				<div class="ic-row">
				    <div class="ic-button-container">
					    <ihtml:nbutton property="btnList" componentID="CMP_MRA_GPABILLING_INVLOV_LIST" >
						    <common:message   key="mailtracking.mra.gpabilling.invoicelov.btn.btlist" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btnClear" componentID="CMP_MRA_GPABILLING_INVLOV_CLEAR">
						    <common:message   key="mailtracking.mra.gpabilling.invoicelov.btn.btclear" />
						</ihtml:nbutton>
					</div>
				</div>
			</div>
		</div>
		<div class="ic-main-container">  
		    <logic:present name="InvoiceLovForm" property="invoiceLovPage" >
				<bean:define id="invoiceLovPage"  name="InvoiceLovForm" property="invoiceLovPage" toScope="page"/>
				<logic:present name="invoiceLovPage">
					<logic:present name="InvoiceLovForm" property="multiselect">
						<bean:define id="multiselect" name="InvoiceLovForm" property="multiselect" />
					</logic:present>
						<logic:equal name="InvoiceLovForm" property="pagination" value="Y">
							<!-- -PAGINATION TAGS -->
							<div class="ic-col-35">
								<bean:define id="lastPageNum" name="InvoiceLovForm" property="lastPageNum" toScope="page"/>
									<common:paginationTag pageURL="javascript:preserveSelectedvalues('lastPageNum','displayPage')"
									name="invoiceLovPage" display="label" labelStyleClass="iCargoResultsLabel"
									lastPageNum="<%=(String)lastPageNum %>" />
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</div>
							<div class="ic-right paddR5">
								<common:paginationTag pageURL="javascript:preserveSelectedvalues('lastPageNum','displayPage')"
								name="invoiceLovPage" display="pages"	linkStyleClass="iCargoLink" disabledLinkStyleClass="iCargoLink"
								lastPageNum="<%=(String)lastPageNum%>"/>
							</div>
						</logic:equal>
				</logic:present>
		    </logic:present>
				<bean:define id="strFormCount" name="InvoiceLovForm" property="formCount"  />
				<bean:define id="strMultiselect" name="InvoiceLovForm" property="multiselect" />
				<%String strLovTxtField = "";%>
				<logic:present name="InvoiceLovForm" property="lovTxtFieldName">
				<bean:define id="strLovTxtFieldName" name="InvoiceLovForm" property="lovTxtFieldName"  />
				<%strLovTxtField=strLovTxtFieldName.toString();%>
				</logic:present>
				<%String strTxtFieldName = "";%>
				<logic:present name="InvoiceLovForm" property="lovDescriptionTxtFieldName">
					<bean:define id="strLovDescriptionTxtFieldName" name="InvoiceLovForm" property="lovDescriptionTxtFieldName" />
					<%strTxtFieldName=strLovDescriptionTxtFieldName.toString();%>
				</logic:present>
				<bean:define id="strSelectedValues" name="InvoiceLovForm" property="selectedValues" />
				<bean:define id="arrayIndex" name="InvoiceLovForm" property="index"/>
                		<div class="tableContainer" id="div1" style="width:100%;height:250px;">
							<table id="lovListTable"class="fixed-header-table">
								<thead>
									<tr class="iCargoTableHeadingCenter" >
										<td width="5%"> &nbsp;</td>
										<td width="25%" class="iCargoLabelleftAligned">
										<common:message   key="mailtracking.mra.gpabilling.invnumber" scope="request"/>
										</td>
										<td width="35%" class="iCargoLabelLeftAligned">
										<common:message   key="mailtracking.mra.gpabilling.gpaCode" scope="request"/>
										</td>
										<td width="35%" class="iCargoLabelLeftAligned">
										<common:message   key="mailtracking.mra.gpabilling.billingperiod" scope="request"/>
										</td>
									</tr>
								</thead>
									<logic:present name="InvoiceLovForm" property="invoiceLovPage">
										 <bean:define id="lovList" name="InvoiceLovForm" property="invoiceLovPage" toScope="page"/>
											<logic:present name="lovList">
								<tbody>
									<% int i=0;%>
									<logic:iterate id = "val" name="lovList" indexId="indexId">
                                    	<logic:notEqual name="InvoiceLovForm" property="multiselect" value="Y">
										<tr href="#" ondblclick="setValueOnDoubleClick('<%=((InvoiceLovVO)val).getInvoiceNumber()%>','<%=((InvoiceLovVO)val).getGpaCode()%>',
															'<%= strLovTxtField%>','<%=strTxtFieldName %>',<%=arrayIndex%>)"/>
										</logic:notEqual>
										<logic:equal name="InvoiceLovForm" property="multiselect" value="Y">
										<td width="3%">

											<%if(((String)strSelectedValues).contains(((InvoiceLovVO)val).getInvoiceNumber())){ %>

											<input type="checkbox" name="selectCheckBox" value="<%=((InvoiceLovVO)val).getInvoiceNumber()%>"  checked="checked"/>
											<%}else{ %>
											<input type="checkbox" name="selectCheckBox" value="<%=((InvoiceLovVO)val).getInvoiceNumber()%>"  />
											<% } %>
										</td>
										</logic:equal>
										<%String checkVal = "";%>

										<logic:notEqual name="InvoiceLovForm" property="multiselect" value="Y">

										<td width="3%">
											<%checkVal = ((InvoiceLovVO)val).getInvoiceNumber()+"&"+((InvoiceLovVO)val).getGpaCode();

											%>

										

											<%

											if(   ((String)strSelectedValues).equals(((InvoiceLovVO)val).getInvoiceNumber()  )){ %>
											<input type="checkbox" name="selectCheckBox" value="<%=checkVal%>"  onclick="singleSelect('<%=checkVal%>');" checked="checked" />
											<%}else{ %>
											<input type="checkbox" name="selectCheckBox" value="<%=checkVal%>"  onclick="singleSelect('<%=checkVal%>');"/>
											<% } %>
										</td>
										</logic:notEqual>
										<!-- table -->


										<td align="ic-left">
											<bean:write name="val" property="invoiceNumber"/>
										</td>
										<td align="ic-left">
											<bean:write name="val" property="gpaCode"/>
										</td>
										<td align="ic-left">
											<bean:write name="val" property="billingPeriod"/>
										</td>
									</tr>
								
									</logic:iterate>
			                    </tbody>			
							 </logic:present>
					      </logic:present>
							</table>
					    </div>
				                      
		</div>
		<div class="ic-foot-container">
				<div class="ic-button-container paddR5">
					<input type="button" name="okButton" value="OK" class="iCargoButtonSmall" onclick="setValueOnOk('<%=strMultiselect%>','<%=strFormCount%>','<%=strLovTxtField%>','<%=strTxtFieldName %>',<%=arrayIndex%>)"/>
					<ihtml:nbutton property="btnClose" componentID="CMP_MRA_GPABILLING_INVLOV_CLOSE">
						<common:message   key="mailtracking.mra.gpabilling.invoicelov.btn.btclose" />
					</ihtml:nbutton>
				</div>
			</div>
	</div>
</ihtml:form>
</div>
	</body>
</html:html>


