<%--************************************************************
* Project	 			: iCargo
* Module Code & Name	: mra-gpabilling
* File Name				: InvoiceLOV.jsp
* Date					: 21/05/2018
* Author(s)				: A-7531
 ****************************************************************--%>
<%
	response.setDateHeader("Expires",0);
	response.setHeader("Pragma","no-cache");

	if (request.getProtocol().equals("HTTP/1.1")) {
		response.setHeader("Cache-Control","no-cache");
	}
%>
<%@ page language="java" %>
<%@ include file="/jsp/includes/ux/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ux.InvoiceLovForm" %>
<%@ page import="com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceLovVO" %>
<%@ page info="lite" %>			
				
	
		
				
	
<html:html>
<head> 
		
	 
	
	
			
	
<common:include type="script" src="/js/mail/mra/gpabilling/ux/InvoiceLOV_Script.jsp"/>
<title><common:message   bundle="invoicelovux" scope="request"/></title>
	<meta name="decorator" content="popuppanelux">




	
</head>


<body id="bodyStyle">
   <bean:define id="strAction" name="InvoiceLovFormUX"  property="lovaction"  toScope="page"/>
   
	


<ihtml:form action="/showNewInvoiceLOV.do" >

<bean:define id="InvoiceLovForm" name="InvoiceLovFormUX" type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ux.InvoiceLovForm" toScope="page" />

<logic:present name="invoiceLovForm"  property="lovaction" >
<bean:define id="lovaction" name="invoiceLovForm" property="lovaction" toScope="page"/>
</logic:present>
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
	     <div title="Invoice Number" class="poppane md" id="select_invoice_number">
        <div class="pad-md pad-b-3xs">
             <div class="row">
                <div class="col-6">
                    <div class="form-group">
                        <label class="form-control-label"><common:message   key="mailtracking.mra.gpabilling.invnumber" scope="request"/></label>
                      
					  <ihtml:text tabindex="2"  property="code" name="InvoiceLovForm"
							  styleClass="form-control" maxlength="50" 
							  componentID="MRA_GPABILLING_INVLOVUX_INVNUM"/>
                    </div>
                </div>
                <div class="col-6">
                    <div class="form-group">
                        <label class="form-control-label"><common:message   key="mailtracking.mra.gpabilling.gpaCode" scope="request"/></label>
                        <ihtml:text tabindex="2"  property="gpaCodeFilter" name="InvoiceLovForm"
							  styleClass="form-control" maxlength="50" 
							  componentID="MRA_GPABILLING_INVLOVUX_GPACODE"/>
                    </div>
                </div>
                <div class="col mar-t-md">
                  <ihtml:nbutton styleClass="btn btn-primary" id= "btnList" property="btnList" accesskey ="L" componentID="CMP_MRA_GPABILLING_INVLOVUX_LIST" >
										<common:message key="mailtracking.mra.gpabilling.invoicelov.btn.btlist" />
					         </ihtml:nbutton>
                            <ihtml:nbutton styleClass="btn btn-default" id= "btnClear" property="btnClear" accesskey ="L" componentID="CMP_MRA_GPABILLING_INVLOVUX_CLEAR" >
										<common:message key="mailtracking.mra.gpabilling.invoicelov.btn.btclear" />
					                        		</ihtml:nbutton>    
                                
                </div>
           </div>
        </div>
        <div class="card border-0">
            <div class="card-header card-header-action border-top pad-y-xs">
			  <div class="mega-pagination">
			<logic:present name="InvoiceLovForm" property="invoiceLovPage" >
				<bean:define id="invoiceLovPage"  name="InvoiceLovForm" property="invoiceLovPage" toScope="page"/>
				<logic:present name="invoiceLovPage">
				<logic:present name="InvoiceLovForm" property="multiselect">
						<bean:define id="multiselect" name="InvoiceLovForm" property="multiselect" />
					</logic:present>
						<logic:equal name="InvoiceLovForm" property="pagination" value="Y">
				<bean:define id="lastPageNum" name="InvoiceLovForm" property="lastPageNum" toScope="page"/>
				
              
										 <common:enhancedPaginationTag
									pageURL='javascript:submitPage(lastPageNum,displayPage)'
							name="invoiceLovPage" id="invoiceLovPage"
							lastPageNum="<%=InvoiceLovForm.getLastPageNum() %>"
							renderLengthMenu="true" lengthMenuName="defaultPageSize" 
							defaultSelectOption="<%=InvoiceLovForm.getDefaultPageSize() %>"  
							lengthMenuHandler="showEntriesReloading" pageNumberFormAttribute="displayPage"
							/>
										  </logic:equal>
						</logic:present>
		    </logic:present>
			</div>
             </div>
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
            <div class="card-body p-0" >
				 <div class="dataTableContainer tablePanel tableonly-container w-100" id="dataTableContainer">
                <table class="table table-x-md mb-0 w-100" id="lovTable">
                    <thead>
                        <tr>
                            <th class="check-box-cell"></th>
                            <th>Invoice Number</th>
                            <th>GPA Code</th>
                            <th>Billing Period</th>
                        </tr>
                    </thead>
					<logic:present name="InvoiceLovForm" property="invoiceLovPage">
										 <bean:define id="lovList" name="InvoiceLovForm" property="invoiceLovPage" toScope="page"/>
											<logic:present name="lovList">
                    <tbody>
					<logic:iterate id = "val" name="lovList" indexId="indexId">
					                                    	<logic:notEqual name="InvoiceLovForm" property="multiselect" value="Y">
										<tr href="#" ondblclick="setValueOnDoubleClick('<%=strFormCount%>','<%=((InvoiceLovVO)val).getInvoiceNumber()%>','<%=((InvoiceLovVO)val).getGpaCode()%>',
															'<%= strLovTxtField%>','<%=strTxtFieldName %>',<%=arrayIndex%>)"/>
															
													
										</logic:notEqual>
                        <logic:equal name="InvoiceLovForm" property="multiselect" value="Y">
                            <td><%if(((String)strSelectedValues).contains(((InvoiceLovVO)val).getInvoiceNumber())){ %>

											<input type="checkbox" name="selectCheckBox" value="<%=((InvoiceLovVO)val).getInvoiceNumber()%>"  checked="checked"/>
											<%}else{ %>
											<input type="checkbox" name="selectCheckBox" value="<%=((InvoiceLovVO)val).getInvoiceNumber()%>"  />
											<% } %></td>
											</logic:equal>
											
											<%String checkVal = "";%>

										<logic:notEqual name="InvoiceLovForm" property="multiselect" value="Y">

										<td width="3%">
											<%checkVal = ((InvoiceLovVO)val).getInvoiceNumber()+"&"+((InvoiceLovVO)val).getGpaCode();

											%>

										

											<%

											if(   ((String)strSelectedValues).equals(((InvoiceLovVO)val).getInvoiceNumber()  )){ %>
											<input type="checkbox" name="selectCheckBox" value="<%=checkVal%>"  onclick="lovUtils.singleSelect({checkBoxVal:'<%=checkVal%>'});" checked="checked" />
											<%}else{ %>
											<input type="checkbox" name="selectCheckBox" value="<%=checkVal%>"  onclick="lovUtils.singleSelect({checkBoxVal:'<%=checkVal%>'});"/>
											<% } %>
										</td>
										</logic:notEqual>
                            <td><bean:write name="val" property="invoiceNumber"/></td>
                            <td><bean:write name="val" property="gpaCode"/></td>
                            <td><bean:write name="val" property="billingPeriod"/></td>
                        
                       </logic:iterate>
                    </tbody>
					</logic:present>
					</logic:present>
                </table>
				</div>
            </div>

        </div>
        <div class="btn-row">
		
							   <ihtml:nbutton styleClass="btn btn-primary" property="btnOk" id="btnOk" componentID="CMP_MRA_GPABILLING_INVLOVUX_OK" accesskey="K" >
                              <common:message key="mailtracking.mra.gpabilling.invoicelov.btn.btok" />
                           </ihtml:nbutton>  
                            <ihtml:nbutton styleClass="btn btn-default" id="btnCancel" property="btnCancel" accesskey ="L" componentID="CMP_MRA_GPABILLING_INVLOVUX_CANCEL" >
										<common:message key="mailtracking.mra.gpabilling.invoicelov.btn.btcancel" />
					                        		</ihtml:nbutton>  
        
          
        </div>
    </div>
   
	</ihtml:form>
  
		   
	</body>
</html:html>


