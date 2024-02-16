<%--
* Project	 		: iCargo
* Module Code & Name: mra-airlinebilling
* File Name			: CaptureFormOne.jsp
* Date				: 28-June-2008
* Author(s)			: A-2391
--%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import = "java.util.Calendar" %>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.InvoiceInFormOneVO" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.CaptureMailFormOneForm" %>


<%@ page import="java.util.Formatter" %>

<%
   String FORMAT_STRING = "%1$-16.2f";
   String FORMAT_STRING_EXCHANGERATE = "%1$-16.5f"; 
%>


	
	
<html:html locale="true">
<head>

	
	<title><bean:message bundle="captureFormOnebundle" key="mra.inwardbilling.title.captureformone" /></title>

	<meta name="decorator" content="mainpanel">
	<common:include type="script" src="/js/mail/mra/airlinebilling/inward/CaptureFormOne_Script.jsp" />

</head>

<body>
	
	
	
<business:sessionBean id="InvoiceInFormOneVO"
	     	     moduleName="mailtracking.mra.defaults"
	     	     screenID="mailtracking.mra.airlinebilling.inward.captureformone"
	     	     method="get"
	     attribute="formOneInvVOs"/>



<business:sessionBean id="onetimemap"
  moduleName="mailtracking.mra.defaults"
 screenID="mailtracking.mra.airlinebilling.inward.captureformone"
  method="get"  attribute="OneTimeMap" />


<bean:define id="form" name="CaptureMailFormOneForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.CaptureMailFormOneForm"
	toScope="page" />

   <div id="mainDiv" class="iCargoContent" style="overflow:auto;">

  <ihtml:form action="/mailtracking.mra.airlinebilling.inward.captureformone.onScreenLoad.do">

 <ihtml:hidden name="form" property="blgCurCode" value="USD"/>
<ihtml:hidden name="form" property="rowCount"/>
<ihtml:hidden name="form" property="formOneOpFlag"/>
<ihtml:hidden name="form" property="listFlag"/>
<ihtml:hidden name="form" property="selectedRow"/>
<ihtml:hidden name="form" property="buttonFlag"/>
<ihtml:hidden name="form" property="airlineInvalidFlag"/>
<ihtml:hidden name="form" property="linkDisable" />
<ihtml:hidden name="form" property="processedFlag" />
<div class="ic-content-main">
   <div class="ic-head-container">
		   <div class="ic-filter-panel" id="mainTable">
		   <div class="ic-input-container">
		   <div class="ic-row ">
		   
				
		
						
							<div class="ic-input ic-mandatory ic-split-35 ">
						
					   <label> 	<common:message   key="mra.inwardbilling.captureformone.clearanceperiod" scope="request"/></label>
					
				<ihtml:text property="clearancePeriod" name="CaptureMailFormOneForm" componentID="CMP_MRA_AIRLINEBILLING_INWARD_CLEARANCEPERIOD"  maxlength="10" />
						<img name="clearancePeriodlov" id="clearancePeriodlov" height="16" src="<%=request.getContextPath()%>/images/lov.gif" width="16" />
			
						
					
					</div>
				<div class="ic-input ic-mandatory ic-split-20 ">
						
					   <label> <common:message   key="mra.inwardbilling.captureformone.airlinecode" scope="request"/></label>
					
				<ihtml:text property="airlineCode" name="CaptureMailFormOneForm" componentID="CMP_MRA_AIRLINEBILLING_INWARD_AIRLINECODE"  maxlength="2" value="<%=(String)form.getAirlineCode()%>"/>
					   <img name="airlinecodelov" id="airlinelov" height="16" src="<%=request.getContextPath()%>/images/lov.gif" width="16" />
				
				</div>
					<div class="ic-input  ic-split-20">
						
					   <label>
					 <common:message   key="mra.inwardbilling.captureformone.airlineno" scope="request"/>
					</label>
				
					 <ihtml:text property="airlineNo" name="CaptureMailFormOneForm" componentID="CMP_MRA_AIRLINEBILLING_INWARD_AIRLINENO"  maxlength="4" value="<%=(String)form.getAirlineNo()%>"/>
						<img name="airlinenumberlov" id="airlinelov" height="16" src="<%=request.getContextPath()%>/images/lov.gif" width="16" />
					
					</div>
								<div class="ic-input  ic-split-25 ">
										
					   <label>
					  	<common:message   key="mra.inwardbilling.captureformone.invoicestatus" scope="request"/>
					</label>
			
				<ihtml:select property="invoiceStatus"
						componentID="CMP_MRA_AIRLINEBILLING_INWARD_INVOICESTATUS" style="width:100px"
						>
						<% System.out.println("*******-------->>no values");%>
						<ihtml:option value=""></ihtml:option>
						<logic:present name="onetimemap">
						<% System.out.println("-------->>no values");%>
						<logic:iterate id="oneTimeValue" name="onetimemap">
						<% System.out.println("*******-------->>no values");%>
						<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
						<logic:equal name="parameterCode" value="mailtracking.mra.despatchstatus">
						<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
						<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
						<logic:present name="parameterValue" property="fieldValue">
						<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
						<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
						<ihtml:option value="<%=String.valueOf(fieldValue).toUpperCase() %>"><%=String.valueOf(fieldDescription)%></ihtml:option>
						</logic:present>
						</logic:iterate>
						</logic:equal>
						</logic:iterate>
						</logic:present>
						<logic:notPresent name="OneTimeValues">
						<% System.out.println("no values");%>
						</logic:notPresent>

					</ihtml:select>				
			</div>
			
					
				
			
				 </div>
					<div class="ic-row ">
							<div class="ic-button-container" >
							<ihtml:nbutton property="btList" componentID="CMP_MRA_AIRLINEBILLING_INWARD_LIST">
						  <common:message   key="mra.inwardbilling.captureformone.button.list" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btClear" componentID="CMP_MRA_AIRLINEBILLING_INWARD_CLEAR">
						  <common:message   key="mra.inwardbilling.captureformone.button.clear" />
						</ihtml:nbutton>
					</div>
				
					
		    </div>	        
	      
</div>	
</div>	

	 
	</div>		
	
	 <div class="ic-main-container" >
					<div class="ic-row ic-bold-value">
					<h4>
					<common:message key="mra.inwardbilling.captureformone.form1details" scope="request"/>
					</h4>
			</div>
			<div class="ic-row">
				
				    
			
<div class="ic-col-98">
<div class="ic-button-container" >					 
 <a href="#" class="iCargoLink" id="lnkAdd" name="lnkAdd" onclick="addInv()"><common:message key="mra.inwardbilling.captureformone.lbl.add"/></a>
						| <a href="#" class="iCargoLink" id="lnkDelete" name="lnkDelete" onclick="deleteInv()"><common:message key="mra.inwardbilling.captureformone.lbl.delete"/></a>					 
				</div>	
</div>				
</div>										
				<!--tr style="height:1%;width:100%">						
				</tr-->
			
	   <div class="tableContainer" id="div1" style="height:680px;">
					
							 <table  class="fixed-header-table" id="Form1Details">	
			
			   <thead>
                      					<tr>
                      						<td  class="iCargoTableHeader"></td>


								<td  class="iCargoTableHeader">
								<common:message key="mra.inwardbilling.captureformone.invoiceno" />
								</td>

								<td  class="iCargoTableHeader">
								<common:message key="mra.inwardbilling.captureformone.invoicedate" />
								</td>

								<td class="iCargoTableHeader">
								<common:message key="mra.inwardbilling.captureformone.currencyoflisting"/>
								</td>

								<td class="iCargoTableHeader">
								<common:message key="mra.inwardbilling.captureformone.miscamount"/>
								</td>

								<td  class="iCargoTableHeader">
								<common:message key="mra.inwardbilling.captureformone.exchangerate"/>
								</td>

								<td  class="iCargoTableHeader">
								<common:message key="mra.inwardbilling.captureformone.amountinusd"/>
								</td>



						 		<td  class="iCargoTableHeader">
						 		<common:message key="mra.inwardbilling.captureformone.invoicestatus"/>
						 		</td>

						  		<td  class="iCargoTableHeader">
						  		<common:message key="mra.inwardbilling.captureformone.invoicef1status"/>
						  		</td>

                      		</tr>
                    	</thead>
<tbody id="targetWeightTableBody">
			                    				<logic:present name="InvoiceInFormOneVO">

			                    				<logic:iterate id="iterator" name="InvoiceInFormOneVO" type="com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.InvoiceInFormOneVO" indexId="rowCount">
			                     				

			                     				<logic:notEqual name="iterator" property="operationFlag" value="D">

										<tr>
										       <td>
										      
										        <ihtml:hidden property="operationFlag" name="form" value="N"/>
										       <html:checkbox property="checkBox" value="<%=String.valueOf(rowCount)%>"/>

										      
										       </td>
										      <td>
											 

											 <ihtml:text name="form" property="invNum" value="<%=iterator.getInvoiceNumber()%>"
											 	componentID="CMP_MRA_AIRLINEBILLING_INWARD_INVOICENUM" maxlength="18"/>
											
										       </td>
										       	<td>
										


											<logic:present name="iterator" property="invoiceDate">
											<bean:define id="invoiceDate" name="iterator" property="invoiceDate" />

											<%

											  String assignedLocalDate = TimeConvertor.toStringFormat(((LocalDate)invoiceDate).toCalendar(),"dd-MMM-yyyy");
											%>

											<ibusiness:calendar
												     property="invDate"
												    componentID="CMP_MRA_AIRLINEBILLING_INWARD_INVOICEDATE"
												     type="image"
												     id="invDateId"

												     value="<%=assignedLocalDate%>" indexId="rowCount"/>
												    <%System.out.println("date "+assignedLocalDate);%>


											 </logic:present>

											  <logic:notPresent name="iterator" property="invoiceDate">
											  <ibusiness:calendar
															     property="invDate"
															    componentID="CMP_MRA_AIRLINEBILLING_INWARD_INVOICEDATE"
															     type="image"
															     id="invDateId"
															     value= "" indexId="rowCount"/>

											 </logic:notPresent>

										
											</td>
											<td>

											

											<ihtml:text name="form" property="curList" value="<%=iterator.getLstCurCode()%>" componentID="CMP_MRA_AIRLINEBILLING_INWARD_LISTCUR" maxlength="3"/>

											

											</td>
											<td>
											<div align="right">
											<logic:present name="iterator" property="totMisAmt" >
												<ibusiness:moneyEntry indexId="rowCount"  formatMoney="true" componentID="CMP_MRA_AIRLINEBILLING_INWARD_MISCAMT" id="misAmt"
												onmoneychange="updateSummary" name="iterator" moneyproperty="totMisAmt"  property="misAmt"  maxlength="20" style="text-align : right;border: 0px;background:"/>
											</logic:present>
											<logic:notPresent name="iterator" property="totMisAmt" >
												<ibusiness:moneyEntry indexId="rowCount"  formatMoney="true" componentID="CMP_MRA_AIRLINEBILLING_INWARD_MISCAMT" id="misAmt"
												onmoneychange="updateSummary" name="iterator" moneyproperty="totMisAmt"  property="misAmt"  maxlength="20" style="text-align : right;border: 0px;background:"/>
											</logic:notPresent>
											</div>
											</td>

											<td>
											

											<ihtml:text  indexId="rowCount" name="form" property="exgRate" styleId="exgRate"
											onchange="change(this.rowCount)" componentID="CMP_MRA_AIRLINEBILLING_INWARD_EXGRATE" maxlength="16" value="<%=new Formatter().format(FORMAT_STRING_EXCHANGERATE,iterator.getExgRate()).toString().trim()%>"/>
											
											
											</td>
											<td>
											<div align="right">
												<logic:present name="iterator" property="billingTotalAmt" >
													<ibusiness:moneyEntry indexId="rowCount"  formatMoney="true" componentID="CMP_MRA_AIRLINEBILLING_INWARD_USDAMT" id="blgAmt"
													onmoneychange="updateSummary" name="iterator" moneyproperty="billingTotalAmt"  property="amtUsd"  maxlength="20" style="text-align : right;border: 0px;background:"/>
												</logic:present>
												<logic:notPresent name="iterator" property="billingTotalAmt" >
													<ibusiness:moneyEntry indexId="rowCount"  formatMoney="true" componentID="CMP_MRA_AIRLINEBILLING_INWARD_USDAMT" id="blgAmt"
													onmoneychange="updateSummary" name="iterator" moneyproperty="billingTotalAmt"  property="amtUsd"  maxlength="20" style="text-align : right;border: 0px;background:"/>
												</logic:notPresent>
											</div>

											</td>
											<td>
											

											<logic:present	name="iterator" property="invStatusdisplay">
													<bean:write name="iterator" property="invStatusdisplay" />
													<bean:define id="invStatusdisplay" name="iterator" property="invStatusdisplay"/>
													<ihtml:hidden property="invStatusdisplay" value="<%=invStatusdisplay.toString()%>"/>

										       </logic:present>

										       <logic:present	name="iterator" property="invStatus">

										       	<bean:define id="invStatus" name="iterator" property="invStatus"/>
										       	<ihtml:hidden property="invStatus" value="<%=invStatus.toString()%>"/>

										       </logic:present>
										       <logic:notPresent name="iterator" property="invStatus">
										       		<ihtml:hidden property="invStatus" value=" "/>
										       </logic:notPresent>
											
											</td>
											<td>
											

											<logic:present	name="iterator" property="invFormoneStatusdisplay">
												<bean:write name="iterator" property="invFormoneStatusdisplay" />
											</logic:present>
											
											</td>
										  </tr>
									  </logic:notEqual>
									  <logic:equal name="iterator" property="operationFlag" value="D">
									                       <%System.out.println("inside equal to D");%>
									               <ihtml:hidden property="operationFlag" value="D"/>
										      <logic:present name="iterator" property="invoiceNumber" >
												<bean:define id="invNum" name="iterator" property="invoiceNumber"/>
													<ihtml:hidden property="invNum" value="<%=invNum.toString()%>"/>
												</logic:present>

												<logic:notPresent name="iterator" property="invoiceNumber">
													<ihtml:hidden property="invNum" value=""/>
										      </logic:notPresent>
									                <logic:present name="iterator" property="invoiceDate" >
												<bean:define id="invDate" name="iterator" property="invoiceDate"/>
													<ihtml:hidden property="invDate" value="<%=invDate.toString()%>"/>
												</logic:present>

												<logic:notPresent name="iterator" property="invoiceDate">
													<ihtml:hidden property="invNum" value=""/>
										      </logic:notPresent>
										       <logic:present name="iterator" property="lstCurCode" >
												<bean:define id="curList" name="iterator" property="lstCurCode"/>
													<ihtml:hidden property="curList" value="<%=curList.toString()%>"/>
												</logic:present>

												<logic:notPresent name="iterator" property="lstCurCode">
													<ihtml:hidden property="curList" value=""/>
										      </logic:notPresent>


										      <logic:present name="iterator" property="totMisAmt" >
												<bean:define id="misAmt" name="iterator" property="totMisAmt"/>
													<ihtml:hidden property="misAmt" value="<%=misAmt.toString()%>"/>
												</logic:present>

												<logic:notPresent name="iterator" property="totMisAmt">
													<ihtml:hidden property="misAmt" value=""/>
										      </logic:notPresent>
										      <logic:present name="iterator" property="exgRate" >
												<bean:define id="exgRate" name="iterator" property="exgRate"/>
													<ihtml:hidden property="exgRate" value="<%=exgRate.toString()%>"/>
												</logic:present>

												<logic:notPresent name="iterator" property="exgRate">
												<ihtml:hidden property="exgRate" value=""/>
										      </logic:notPresent>
										      <logic:present name="iterator" property="billingTotalAmt" >
												<bean:define id="amtUsd" name="iterator" property="billingTotalAmt"/>
													<ihtml:hidden property="amtUsd" value="<%=amtUsd.toString()%>"/>
												</logic:present>

												<logic:notPresent name="iterator" property="billingTotalAmt">
												<ihtml:hidden property="amtUsd" value=""/>
										      </logic:notPresent>
									      		<logic:present name="iterator" property="invStatus" >
												<bean:define id="invStatus" name="iterator" property="invStatus"/>
													<ihtml:hidden property="invStatus" value="<%=invStatus.toString()%>"/>
												</logic:present>

												<logic:notPresent name="iterator" property="invStatus">
												<ihtml:hidden property="invStatus" value=""/>
										      </logic:notPresent>
										       <logic:present name="iterator" property="formOneStatus" >

											<bean:define id="invF1Status" name="iterator" property="invF1Status"/>
												<ihtml:hidden property="invF1Status" value="<%=invF1Status.toString()%>"/>
											</logic:present>





											<logic:notPresent name="iterator" property="formOneStatus">
												<ihtml:hidden property="invF1Status" value=""/>
										      </logic:notPresent>

                     							</logic:equal>
								
								       </logic:iterate>
									</logic:present>
									 <!-- template row starts-->
									 <logic:present name="CaptureFormOneForm" property="blgCurCode">
											<bean:define id="currencyCode" name="CaptureFormOneForm" property="blgCurCode"/>
										</logic:present>
										<logic:notPresent name="CaptureFormOneForm" property="blgCurCode">
												<bean:define id="currencyCode" value=""/>
									</logic:notPresent>
			                    				<%String currencyCode =(String) pageContext.getAttribute("currencyCode");%>
											   <bean:define id="templateRowCount" value="0"/>
												<tr template="true" id="targetWeightRow" style="display:none">
												<td width="1%">

												   <html:checkbox property="checkBox" value=" "/>

													<ihtml:hidden property="operationFlag" value="NOOP"/>
												
												</td>
												<td>
												
												 <ihtml:text indexId="templateRowCount" property="invNum" value=""
												 	componentID="CMP_MRA_AIRLINEBILLING_INWARD_INVOICENUM"  maxlength="18"/>
											

												</td>
												<td>
											
												<ibusiness:calendar

													     property="invDate"
													     componentID="CMP_MRA_AIRLINEBILLING_INWARD_INVOICEDATE"
													     type="image"
													     id="invDateId"
													     indexId="templateRowCount"
													      value= ""

												 />

												
												</td>
												<td>
													
													<ihtml:text  indexId="templateRowCount" property="curList" value=""
														componentID="CMP_MRA_AIRLINEBILLING_INWARD_LISTCUR" maxlength="3"/>
													
												</td>
												<td>

												<div align="right">


												<ibusiness:moneyEntry currencyCode="<%=currencyCode%>" value="0" indexId="templateRowCount"  formatMoney="true"
												name="form" componentID="CMP_MRA_AIRLINEBILLING_INWARD_MISCAMT" id="miscAmt"    property="misAmt"
												onmoneychange="updateSummary" maxlength="20" style="text-align : right;border: 0px;background:"  />

												</div>

												</td>
												<td>
											
												<ihtml:text  indexId="rowCount"  name="form" property="exgRate" value=""
												onchange="change(this.rowCount)" componentID="CMP_MRA_AIRLINEBILLING_INWARD_EXGRATE"/>
												
												</td>
												<td>
												<div align="right">


													<ibusiness:moneyEntry currencyCode="<%=currencyCode%>" value="0" indexId="templateRowCount"  formatMoney="true"
													componentID="CMP_MRA_AIRLINEBILLING_INWARD_USDAMT" id="blgAmt"    property="amtUsd"
													onmoneychange="updateSummary" maxlength="20" style="text-align : right;border: 0px;background:"  />

												</div>

												</td>
												<td>
												<ihtml:hidden property="invStatus" value=""/>
												</td>
												<td>

												</td>

											   </tr>
									<!-- template row ends -->

			                                            </tbody>

			  </table>
	      </div>
      </div>
 <div class="ic-foot-container" >
						<div class="ic-button-container">  

			  <ihtml:nbutton property="btnAccDetails" componentID="CMP_MRA_AIRLINEBILLING_INWARD_LISTACCENTRIES"  >
							  <common:message key="mra.inwardbilling.captureformone.button.listaccentries" />
						        </ihtml:nbutton>
							<ihtml:nbutton property="btRejectFormOne" componentID="CMP_MRA_AIRLINEBILLING_INWARD_REJECTFORM1">
							  <common:message key="mra.inwardbilling.captureformone.button.rejectform1" />
							</ihtml:nbutton>
							<ihtml:nbutton property="btSummary" componentID="CMP_MRA_AIRLINEBILLING_INWARD_CAPTUREINVOICESUMMARY">
							  <common:message key="mra.inwardbilling.captureformone.button.captureinvoicesummary" />
							</ihtml:nbutton>
							<ihtml:nbutton property="btFormthree" componentID="CMP_MRA_AIRLINEBILLING_INWARD_CAPTUREFORM3">
							  <common:message key="mra.inwardbilling.captureformone.button.captureform3" />
							</ihtml:nbutton>
							<ihtml:nbutton property="btSave" componentID="CMP_MRA_AIRLINEBILLING_INWARD_SAVE">
							  <common:message key="mra.inwardbilling.captureformone.button.save" />
							</ihtml:nbutton>
							<ihtml:nbutton property="btClose" componentID="CMP_MRA_AIRLINEBILLING_INWARD_CLOSE">
							  <common:message key="mra.inwardbilling.captureformone.button.close" />
							</ihtml:nbutton>
		 </div>
	  </div>
	</div>
	
	</ihtml:form>
	</div>
	<span style="display:none" id="tmpSpan"></span>
							<!---CONTENT ENDS-->



	</body>
</html:html>
