<%--
* Project	 		: iCargo
* Module Code & Name            : ULD:UCM Error Log
* File Name			: UCMErrorLog.jsp
* Date				: 19/07/06
* Author(s)			: Anitha George M : A-1862
--%>

<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.UCMErrorLogForm" %>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import = "com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@page import ="java.util.ArrayList"%>
<%@ include file="/jsp/includes/tlds.jsp" %>



<%
response.setDateHeader("Expires",0);
response.setHeader("Pragma","no-cache");

if (request.getProtocol().equals("HTTP/1.1")) { 
	response.setHeader("Cache-Control","no-cache");
}
%>




<html:html>
<head>
		
	
<title>

<bean:message bundle="ucmerrorlogResources" key="uld.defaults.messaging.ucmerrorlog.ucmErrorLogTitle" scope="request"/>

</title>
<meta name="decorator" content="mainpanelrestyledui">
<common:include src="/js/uld/defaults/UCMErrorLog_Script.jsp" type="script"/>



</head>



<body >

	
<%@include file="/jsp/includes/reports/printFrame.jsp" %>

<div  id="pageDiv" class="iCargoContent ic-masterbg" style="overflow:auto">

<bean:define id="form"
	 name="UCMErrorLogForm"  type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.UCMErrorLogForm"
	 toScope="page" />


<business:sessionBean
    id="statusValues"
	moduleName="uld.defaults"
	screenID="uld.defaults.ucmerrorlog"
	method="get"
    attribute="messageStatus"/>

<ihtml:form action="/uld.defaults.messaging.screenloaducmerrorlog.do">

<input type="hidden" name="currentDialogId" />
<input type="hidden" name="currentDialogOption" />

<%--<ihtml:hidden property="screenStatusFlag"/>--%>
<ihtml:hidden property="screenStatusValue"/>
<ihtml:hidden property="pageURL"/>
<ihtml:hidden property="displayPage" />
<ihtml:hidden property="lastPageNumber" />
<ihtml:hidden property="duplicateStatus"/>
<ihtml:hidden property="rowindex"/>
<ihtml:hidden property="reconcileStatus"/>
<ihtml:hidden property="flightValidationStatus"/>
<ihtml:hidden property="mismatchStatus"/>
<ihtml:hidden property="ucmDisableStat"/>
<ihtml:hidden property="listStatus"/>
<ihtml:hidden property="listflag"/>


<%  UCMErrorLogForm frm = (UCMErrorLogForm)request.getAttribute("UCMErrorLogForm");%>

<div class="ic-content-main bg-white">
<span class="ic-page-title ic-display-none">
					<bean:message bundle="ucmerrorlogResources" key="uld.defaults.messaging.ucmerrorlog.ucmErrorLogTitle" />
				</span>
			    <div class="ic-head-container">	
				      <div class="ic-filter-panel">   
                        <div class="ic-input-container">
			  		    <div class="ic-row">
					    <div class="ic-row ic-label-100">
					 <h3>	 <bean:message bundle="ucmerrorlogResources" key="uld.defaults.messaging.ucmerrorlog.search" />  </h3>
						</div>
						</div>
						<div class="ic-row">
					     <div class="ic-input ic-split-25" >
							    <label>
                            	<common:message key="uld.defaults.messaging.ucmerrorlog.flightno" scope="request"/>
                               </label>
                    	<%String carrierCode=(String)frm.getCarrierCode();%>
						<%String flightNo=(String)frm.getFlightNo();%>
						<%String flightDate=(String)frm.getFlightDate();%>

						 <ibusiness:flightnumber id="flight"
							carrierCodeProperty="carrierCode"
							flightCodeProperty="flightNo"
							carriercodevalue="<%=(String)carrierCode%>"
							flightcodevalue="<%=(String)flightNo%>"
							carrierCodeStyleClass="iCargoTextFieldVerySmall"
							flightCodeStyleClass="iCargoTextFieldSmall"
							componentID="ULD_DEFAULTS_UCMERRORLOG_FLIGHT" />
                           </div>
						   
						   
						    <div class="ic-input ic-split-25" >
						    <label> <common:message key="uld.defaults.ucmerrorlog.lbl.ucmno" /> </label>
                            	<ibusiness:calendar property="flightDate" id="flightDate" type="image" componentID="CMP_ULD_DEFAULTS_UCMINOUT_FLIGHTDATE" />
                             
							</div>
							
							  <div class="ic-input ic-split-25" >
						    <label> 
							 <bean:message bundle="ucmerrorlogResources" key="uld.defaults.messaging.ucmerrorlog.airport" />
							</label>
                            <ihtml:text componentID="TXT_ULD_DEFAULTS_UCMERRORLOG_AIRPORT" property="ucmerrorlogAirport" name="UCMErrorLogForm" style="text-transform : uppercase" maxlength="3" />
					     	 <div class="lovImg"><img src="<%= request.getContextPath()%>/images/lov.png" width="22" height="22"  id="airportLovImg" name="airportLovImg" onclick="displayLOV('showAirport.do','N','Y','showAirport.do',document.forms[1].ucmerrorlogAirport.value,'CurrentAirport','1','ucmerrorlogAirport','',0)" alt="Airport LOV"/></div>
							</div>
							
							
							 
							 <div class="ic-input ic-split-25" >
						    <label> 
							 <bean:message bundle="ucmerrorlogResources" key="uld.defaults.ucmerrorlog.flightdate" /> 
							</label>
                           <ihtml:select property="msgType" name="UCMErrorLogForm" componentID="ULD_DEFAULTS_UCMERRORLOG_MSGTYPE">
							<html:option value="IN"><bean:message bundle="ucmerrorlogResources" key="uld.defaults.ucmerrorlog.in" /> </html:option>
							<html:option value="OUT"><bean:message bundle="ucmerrorlogResources" key="uld.defaults.ucmerrorlog.out" /></html:option>
					        </ihtml:select>
							</div>
							
							
						</div>
						
						
						<div class="ic-row marginT5">
					     <div class="ic-input ic-split-25" >
					<label> 	  <common:message key="uld.defaults.messaging.ucmerrorlog.lbl.fromdate" scope="request"/> </label>
					<ibusiness:calendar property="fromDate" id="FromDate" type="image" componentID="ULD_DEFAULTS_UCMERRORLOG_FROMDATE" />
						 </div>
						 <div class="ic-input ic-split-25" >
						 <label> 	  <common:message key="uld.defaults.messaging.ucmerrorlog.lbl.todate" scope="request"/></label>
						 	<ibusiness:calendar property="toDate" id="ToDate" type="image" componentID="ULD_DEFAULTS_UCMERRORLOG_TODATE" />
						 </div>
						<div class="ic-input ic-split-25" >
						<label> 	   <common:message key="uld.defaults.messaging.ucmerrorlog.lbl.msgSatus" scope="request"/></label>
						<ihtml:select property="msgStatus" name="UCMErrorLogForm" componentID="ULD_DEFAULTS_UCMERRORLOG_MSGSTATUS">
							 <logic:present name="statusValues">
										<logic:iterate id="parameterValue" name="statusValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
											<logic:present name="parameterValue">
											<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
												<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
												<html:option value="<%=(String)fieldValue%>">
													<%=(String)fieldDescription%>
												</html:option>
											</logic:present>
										</logic:iterate>
							</logic:present>
							<html:option value="ALL"><bean:message bundle="ucmerrorlogResources" key="uld.defaults.ucmerrorlog.all" /></html:option>
							</ihtml:select>
						 </div>
						 <div class="ic-input ic-split-25" >
						 
						 <div class="ic-button-container">
						 <ihtml:nbutton property="btnList" componentID="BTN_ULD_DEFAULTS_UCMERRORLOG_LIST" accesskey="L">
								<bean:message bundle="ucmerrorlogResources" key="uld.defaults.ucmerrorlog.btn.list" />
								</ihtml:nbutton>
								<ihtml:nbutton property="btnClear" componentID="BTN_ULD_DEFAULTS_UCMERRORLOG_CLEAR" accesskey="C">
								<bean:message bundle="ucmerrorlogResources" key="uld.defaults.ucmerrorlog.btn.clear" />
								</ihtml:nbutton>
						  </div>
						 </div>
                      </div>
				 </div>
				  </div>
 </div>

			  <div class="ic-main-container">		
					    <div class="ic-row">
                     <h3>   <common:message  key="ULD.DEFAULTS.UCMlog" scope="request"/>   </h3>
                        </div>
			           <!--  -->
					  <div class="ic-row paddR5">



			<business:sessionBean id="ULDFlightMessageReconcileVOs" moduleName="uld.defaults" screenID="uld.defaults.ucmerrorlog" method="get" attribute="ULDFlightMessageReconcileVOs" />
			   <logic:present name="ULDFlightMessageReconcileVOs">
			  <bean:define id="ULDFlightMessageReconcileVOs" name="ULDFlightMessageReconcileVOs" type="com.ibsplc.xibase.server.framework.persistence.query.Page"/>
			  </logic:present>
			 
				<business:sessionBean id="ULDFlightMessageReconcileVOs" moduleName="uld.defaults" screenID="uld.defaults.ucmerrorlog" method="get" attribute="ULDFlightMessageReconcileVOs" />
				<logic:present name="ULDFlightMessageReconcileVOs">
				<bean:define id="ULDFlightMessageReconcileVOs" name="ULDFlightMessageReconcileVOs" type="com.ibsplc.xibase.server.framework.persistence.query.Page"/>
				<%if (ULDFlightMessageReconcileVOs.size()>0){%>
					<common:paginationTag
					name="ULDFlightMessageReconcileVOs"
					pageURL="javascript:submitUCMErrorLog('lastPageNum','displayPage')"
					display="label"
					linkStyleClass="iCargoLink"
					disabledLinkStyleClass="iCargoLink"
					labelStyleClass="iCargoResultsLabel"
					lastPageNum="<%=frm.getLastPageNumber() %>"/>
				<%} %>
				

			<div class="ic-button-container">
				<%if (ULDFlightMessageReconcileVOs.size()>0){%>
					<common:paginationTag
					name="ULDFlightMessageReconcileVOs"
					
					pageURL="javascript:submitUCMErrorLog('lastPageNum','displayPage')"
					display="pages"
					linkStyleClass="iCargoLink"
					disabledLinkStyleClass="iCargoLink"
					lastPageNum="<%=frm.getLastPageNumber()%>"
					exportToExcel="true"
					exportTableId="ucmErrorTable"
					exportAction="uld.defaults.messaging.listucmerrorlog.do"/>

				<%} %>
				</div>
				</logic:present>
				
				</div>  
				<!--  -->

                    <div class="ic-row">

						<div class="tableContainer"  id="div1"  style="height:480px; ">
						  <table width="100%" class="fixed-header-table"  id="ucmErrorTable">
							<thead>
							  <tr class="iCargoTableHeadingLeft">
                                 <td width="4%" class="iCargoTableHeader">
					             <input type="checkbox" name="selectedUCMErrorLogAll" value="checkbox" onclick="updateHeaderCheckBox(this.form,document.forms[1].allCheck,document.forms[1].selectedUsers)" /><span></span>
                                </td>
                            <td width="10%">
                        	<common:message key="uld.defaults.ucmerrorlog.lbl.ucmno" scope="request"/>
                           <span></span></td>
						   <td width="10%"><common:message key="uld.defaults.ucmerrorlog.lbl.fltno" scope="request"/> <span></span></td>
                             <td width="10%">
                        	<common:message key="uld.defaults.ucmerrorlog.lbl.date" scope="request"/>
                           <span></span></td>
                            <td width="10%">
                        	<common:message key="uld.defaults.ucmerrorlog.lbl.type" scope="request"/>
                           <span></span></td>
                            <td width="10%">
                             <common:message key="uld.defaults.ucmerrorlog.lbl.station" scope="request"/>
                           <span></span></td>
                             <td width="10%">
                            <common:message key="uld.defaults.ucmerrorlog.lbl.errrordesc" scope="request"/>
                           <span></span></td>
						   <td width="10%">
                            <common:message key="uld.defaults.scmreconcile.lbl.msgsnd" scope="request" />
                           <span></span></td>
						   <td width="10%">
                            <common:message key="uld.defaults.ucmerrorlog.lbl.reconcile" scope="request"/>
                           <span></span></td>
                            

			  		  </tr>
					  </thead>
				  <tbody>
  				<logic:present name="ULDFlightMessageReconcileVOs">
				    <bean:define id="requestPage" name="ULDFlightMessageReconcileVOs" type="com.ibsplc.xibase.server.framework.persistence.query.Page"/>

				    <logic:iterate  id="ULDFlightMessageReconcileVO"  name="requestPage" type="com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileVO" indexId="indexId">
				    <common:rowColorTag index="indexId">

				    <%
					int rows = 1;
					String tableClass = "";
					String fltDate = "";
					if (ULDFlightMessageReconcileVO.getFlightDate()!= null)
					{
						int index = ULDFlightMessageReconcileVO.getFlightDate().toDefaultStringFormat().indexOf(",");
						fltDate = ULDFlightMessageReconcileVO.getFlightDate().toDefaultStringFormat().substring(0 ,index);
					}


					%>

					 <tr bgcolor="<%=color%>">


                                  <td  align="center" width="4%" height="19"  class="iCargoTableTd">
                                    <div class="ic-center"><ihtml:checkbox property="selectedUCMErrorLog" value="<%=String.valueOf(indexId)%>" onclick="toggleTableHeaderCheckbox('selectedUCMErrorLog',this.form.selectedUCMErrorLogAll)"/></div>
                                    </td>
                                  <td   class="iCargoTableDataTd ic-center">
                                  
					<logic:present name="ULDFlightMessageReconcileVO" property="sequenceNumber">
					<bean:write name="ULDFlightMessageReconcileVO" property="sequenceNumber"/>
					</logic:present>
				   
				  </td>
				  <td   class="iCargoTableDataTd ic-center">
				    
					<logic:present name="ULDFlightMessageReconcileVO" property="carrierCode">
					<bean:write name="ULDFlightMessageReconcileVO" property="carrierCode"/>
					</logic:present> 
				 
                                  
					<logic:present name="ULDFlightMessageReconcileVO" property="flightNumber">
					<bean:write name="ULDFlightMessageReconcileVO" property="flightNumber"/>
					</logic:present>
				    
				   </td>
                                  <td   class="iCargoTableDataTd ic-center">
                                  
					<logic:present name="ULDFlightMessageReconcileVO" property="flightDate">
					<%=fltDate%>
					</logic:present>
				   
				   </td>

                                  <td   class="iCargoTableDataTd ic-center">
				    
					<logic:present name="ULDFlightMessageReconcileVO" property="messageType">
					<bean:write name="ULDFlightMessageReconcileVO" property="messageType"/>
					</logic:present>
				   
				   </td>
				  <td   class="iCargoTableDataTd ic-center">
				    
					<logic:present name="ULDFlightMessageReconcileVO" property="airportCode">
					<bean:write name="ULDFlightMessageReconcileVO" property="airportCode"/>
					</logic:present>
				    
				   </td>
				  <td   class="iCargoTableDataTd ic-center">
				 
					<logic:present name="ULDFlightMessageReconcileVO" property="errorCode">
					<ihtml:hidden name="errorCodes" property="errorCodes" value="<%=ULDFlightMessageReconcileVO.getErrorCode()%>"/>

					<logic:equal name="ULDFlightMessageReconcileVO" property="errorCode" value="E9">
					<bean:message bundle="ucmerrorlogResources" key="uld.defaults.ucmerrorlog.errorcode.error9" />
					</logic:equal>
					<logic:equal name="ULDFlightMessageReconcileVO" property="errorCode" value="E1">
					<bean:message bundle="ucmerrorlogResources" key="uld.defaults.ucmerrorlog.errorcode.error1" />
					</logic:equal>
					<logic:equal name="ULDFlightMessageReconcileVO" property="errorCode" value="E2">
					<bean:message bundle="ucmerrorlogResources" key="uld.defaults.ucmerrorlog.errorcode.error2" />
			         	</logic:equal>
			        	<logic:equal name="ULDFlightMessageReconcileVO" property="errorCode" value="E13">
				 	 	<bean:message bundle="ucmerrorlogResources" key="uld.defaults.ucmerrorlog.inagainstdupout" />
			        	 </logic:equal>
					</logic:present>
				   

					</td>
					<td>
					 
						<logic:present name="ULDFlightMessageReconcileVO" property="messageSendFlag">
								  <bean:define id="status" name="ULDFlightMessageReconcileVO" property="messageSendFlag"/>
											<logic:present name="statusValues">
											<bean:define id="statusType" name="statusValues"/>

											<logic:iterate id="actualStatus" name="statusType">
											<bean:define id="onetimevo" name="actualStatus" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO"/>

											<%if(onetimevo.getFieldValue().equals(status)){%>
											<bean:write name="onetimevo" property="fieldDescription"/>

											<%}%>
											</logic:iterate>
											</logic:present>
											</logic:present>
											

									 </td>


					<bean:define id="strMultiselect" name="indexId" />
				  <td   class="iCargoTableDataTd ic-center">
				  
				  <logic:notPresent name="ULDFlightMessageReconcileVO" property="errorCode">
				  <ihtml:hidden name="errorCodes" property="errorCodes" value="NA"/>

					<logic:equal name="ULDFlightMessageReconcileVO" property="hasUldErrors" value="true">
					<ihtml:button
					indexId="indexId"
					styleId="errorcodelov"
					property="errorcodelov" componentID="BTN_ULD_DEFAULTS_UCMERRORLOG_ERRORBTN"
					onclick="showULDDetails('errorcodelov',this)"  >


					<bean:message bundle="ucmerrorlogResources" key="uld.defaults.ucmerrorlog.errorcode.viewulderrorlog" />
					</ihtml:button>
					</logic:equal>

					<logic:equal name="ULDFlightMessageReconcileVO" property="hasUldErrors" value="false">
					&nbsp;
					</logic:equal>

				    </logic:notPresent>


				   

				   

				    </td>



					<logic:equal name="ULDFlightMessageReconcileVO" property="hasUldErrors" value="true">
					<ihtml:hidden property="hasULDError" value="true" />
					</logic:equal>
					<logic:equal name="ULDFlightMessageReconcileVO" property="hasUldErrors" value="false">
					<ihtml:hidden property="hasULDError" value="false" />
					</logic:equal>
					<logic:present name="ULDFlightMessageReconcileVO" property="errorCode" >
					<ihtml:hidden property="hasUCMError" value="true" />
					</logic:present>
					<logic:notPresent name="ULDFlightMessageReconcileVO" property="errorCode" >
					<ihtml:hidden property="hasUCMError" value="false" />
					</logic:notPresent>

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
				    <div class="ic-row">
						<div class="ic-button-container paddR5">
				<ihtml:nbutton	property="btnReconcileUCM" componentID="BTN_ULD_DEFAULTS_UCMERRORLOG_ERRORBTN" accesskey="R">
					<bean:message bundle="ucmerrorlogResources" key="uld.defaults.ucmerrorlog.errorcode.reconcile" />
				</ihtml:nbutton>
			    <ihtml:nbutton property="btnSend" componentID="BTN_ULD_DEFAULTS_UCMERRORLOG_SEND" accesskey="S">
					<bean:message bundle="ucmerrorlogResources" key="uld.defaults.ucmerrorlog.btn.send" />
				</ihtml:nbutton>
				<ihtml:nbutton property="btnViewULDs" componentID="BTN_ULD_DEFAULTS_UCMERRORLOG_VIEW" accesskey="V">
					<bean:message bundle="ucmerrorlogResources" key="uld.defaults.ucmerrorlog.btn.view" />
				</ihtml:nbutton>
                <ihtml:nbutton property="btnClose" componentID="BTN_ULD_DEFAULTS_UCMERRORLOG_CLOSE" accesskey="O">
					<bean:message bundle="ucmerrorlogResources" key="uld.defaults.ucmerrorlog.btn.close" />
				</ihtml:nbutton>

		                 </div>
					</div>

                 </div> 



</div>
</ihtml:form>
</div>
				
		
	</body>
</html:html>

