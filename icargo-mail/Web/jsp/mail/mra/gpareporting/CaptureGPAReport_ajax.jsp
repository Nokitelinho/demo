<%--******************************************************************************************
* Project	 		: iCargo
* Module Code & Name		: CaptureGPAReport.jsp
* Date				: 12-Feb-2007
* Author(s)			: A-2257

 ******************************************************************************************--%>

<%@ include file="/jsp/includes/tlds.jsp" %>

<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.CaptureGPAReportForm" %>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import = "java.util.Calendar" %>
<%@ page import="java.util.Formatter" %>

<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>

<business:sessionBean id="GPAREPORTINGFILTERVO"
	moduleName="mailtracking.mra"
	screenID="mailtracking.mra.gpareporting.capturegpareport"
	method="get"
	attribute="gPAReportingFilterVO" />



<business:sessionBean id="GPAREPORTINGDETAILSPAGE"
	moduleName="mailtracking.mra"
	screenID="mailtracking.mra.gpareporting.capturegpareport"
	method="get"
	attribute="gPAReportingDetailsPage" />

<business:sessionBean id="MAILSTATUS_ONETIME"
	moduleName="mailtracking.mra"
	screenID="mailtracking.mra.gpareporting.capturegpareport"
	method="get"
	attribute="mailStatus" />

<business:sessionBean id="MAILCATEGORY_ONETIME"
	moduleName="mailtracking.mra"
	screenID="mailtracking.mra.gpareporting.capturegpareport"
	method="get"
	attribute="mailCategory" />

<business:sessionBean id="HIGHESTNUM_ONETIME"
	moduleName="mailtracking.mra"
	screenID="mailtracking.mra.gpareporting.capturegpareport"
	method="get"
	attribute="heighestNum" />

<business:sessionBean id="REGINSIND_ONETIME"
	moduleName="mailtracking.mra"
	screenID="mailtracking.mra.gpareporting.capturegpareport"
	method="get"
	attribute="regOrInsInd" />


  <!--CONTENT STARTS-->
<div id="contentdiv" class="iCargoContent" style="overflow:auto; width:100%;height:expression(((document.body.clientHeight)*95)/100)">


  <bean:define id="form" name="CaptureGPAReportForm"  type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.CaptureGPAReportForm" toScope="page" />


 <ihtml:form action="/mailtracking.mra.gpareporting.capturegpareport.screenload.do">

 <ihtml:hidden property="displayPage" />
 <ihtml:hidden property="lastPageNum" />
 <ihtml:hidden property="screenFlag" />
 <ihtml:hidden property="popUpStatusFlag" />
 <ihtml:hidden property="selectedRows" />
 <ihtml:hidden property="screenStatusFlag" />
 <ihtml:hidden property="basistype" />
 <ihtml:hidden property="gpaselect" />
 <ihtml:hidden property="currencyCode"/>
<ihtml:hidden property="showDsnPopUp"/>
<ihtml:hidden property="dsnFlag"/>

 <% String FORMAT_STRING = "%1$-16.2f";    %>

<!--Table to be modified STARTS-->
 <table class="fixed-header-table" id="captureGPAReport" >
				  <thead>
				     <tr>
					    <td class="iCargoTableHeader" rowspan="2"  width="4%"></td>
						<td class="iCargoTableHeader" rowspan="2" width="6%">Date</td>

						<td class="iCargoTableHeader" rowspan="2" width="5%">Origin O.E</td>
						<td class="iCargoTableHeader" rowspan="2" width="5%">Destn. O.E</td>

						<td class="iCargoTableHeader"  rowspan="2" width="5%">Mail Category</td>

						<td class="iCargoTableHeader"  rowspan="2" width="5%">Mail Sub Class</td>
						<td class="iCargoTableHeader"  rowspan="2" width="5%">Year</td>
						<td class="iCargoTableHeader" rowspan="2" width="5%">DSN</td>
						<td class="iCargoTableHeader" rowspan="2" width="5%">RSN</td>
						<td class="iCargoTableHeader" rowspan="2" width="5%">HN</td>
						<td class="iCargoTableHeader" rowspan="2" width="5%">RI</td>
				<td style="iCargoLabelCenterAligned"  rowspan="2" width="5%">No of Mail Bags</td>
						<td class="iCargoTableHeader"  rowspan="2" width="5%">Wt. (K.G)</td>
						<td class="iCargoTableHeader"  rowspan="2" width="5%">Rate</td>
						<td class="iCargoTableHeader"  rowspan="2" width="6%">Amount</td>
						<td class="iCargoTableHeader"  rowspan="2" width="6%">Tax</td>
						<td class="iCargoTableHeader"  rowspan="2" width="6%">Total</td>
						<td class="iCargoTableHeader"  rowspan="2" width="6%">Mail Reporting Status</td>
						<td class="iCargoTableHeader"  width="6%" >Flight Details</td>

					 </tr>

				  </thead>
				  <tbody>

				  <% int index=0;  %>

				  <%

					int totNoOfMailBags=0;
					double totweight=0;
					double totrate=0;
					double totamount=0;
					double tottax=0;
					double tottot=0;


				  %>

					<logic:present name="GPAREPORTINGDETAILSPAGE" >
					<bean:define id="GPAREPORTINGDETAILSPAGE" name="GPAREPORTINGDETAILSPAGE" type="java.util.Collection"/>

					<logic:iterate id="GPAREPORTINGDETAILSVO" name="GPAREPORTINGDETAILSPAGE" type="com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingDetailsVO" indexId="rowCount">


					<logic:notEqual name="GPAREPORTINGDETAILSVO" property="operationFlag" value="D" >

					

					<tr >

					       <td class="iCargoTableDataTd ">

							<ihtml:checkbox property="rowCount"  value="<%=String.valueOf(rowCount)%>"/>
						</td>


						<!--<td width="1%" align="center"><input type="checkbox" name="chk" align="middle" value="<%=index%>"  /></td>-->
						<td class="iCargoTableDataTd ">
							<logic:present name="GPAREPORTINGDETAILSVO" property="dsnDate">
								<%
									String dsnDate ="";
									if(GPAREPORTINGDETAILSVO.getDsnDate() != null) {
									dsnDate = GPAREPORTINGDETAILSVO.getDsnDate().toDisplayDateOnlyFormat();
									}
								%>
								 <%= dsnDate %>
							</logic:present>

						</td>
						<td class="iCargoTableDataTd ">
							<logic:present name="GPAREPORTINGDETAILSVO" property="originOfficeExchange">
								<bean:write name="GPAREPORTINGDETAILSVO" property="originOfficeExchange"/>
							</logic:present>
						</td>
						<td class="iCargoTableDataTd ">
							<logic:present name="GPAREPORTINGDETAILSVO" property="destinationOfficeExchange">
								<bean:write name="GPAREPORTINGDETAILSVO" property="destinationOfficeExchange"/>
							</logic:present>
						</td>
						<td class="iCargoTableDataTd ">
							<logic:present name="GPAREPORTINGDETAILSVO" property="mailCategory">

							<logic:iterate id="mailCat" name="MAILCATEGORY_ONETIME">
							<bean:define id="mailCatvalue" name="mailCat" property="fieldValue" />
							     <logic:equal name="GPAREPORTINGDETAILSVO" property="mailCategory" value="<%=(String)mailCatvalue %>" >

								<bean:write name="mailCat" property="fieldDescription"/>
							     </logic:equal>
							</logic:iterate>
							</logic:present>
						</td>
						<td class="iCargoTableDataTd ">
							<logic:present name="GPAREPORTINGDETAILSVO" property="actualMailSubClass">
								<bean:write name="GPAREPORTINGDETAILSVO" property="actualMailSubClass"/>
							</logic:present>
						</td>
						<td class="iCargoTableDataTd ">
							<logic:present name="GPAREPORTINGDETAILSVO" property="year">
								<bean:write name="GPAREPORTINGDETAILSVO" property="year"/>
							</logic:present>
						</td>
						<td class="iCargoTableDataTd ">
							<logic:present name="GPAREPORTINGDETAILSVO" property="dsnNumber">
								<bean:write name="GPAREPORTINGDETAILSVO" property="dsnNumber"/>
							</logic:present>
						</td>
						<td class="iCargoTableDataTd ">
							<logic:present name="GPAREPORTINGDETAILSVO" property="receptacleSerialNumber">
								<bean:write name="GPAREPORTINGDETAILSVO" property="receptacleSerialNumber"/>
							</logic:present>
						</td>
						<td class="iCargoTableDataTd ">
							<logic:present name="GPAREPORTINGDETAILSVO" property="highestNumberedReceptacle">
								<logic:iterate id="highestNum" name="HIGHESTNUM_ONETIME">
								<bean:define id="highestNumvalue" name="highestNum" property="fieldValue" />
								     <logic:equal name="GPAREPORTINGDETAILSVO" property="highestNumberedReceptacle" value="<%=String.valueOf(highestNumvalue)%>" >

									<bean:write name="highestNum" property="fieldDescription"/>
								     </logic:equal>
								</logic:iterate>
							</logic:present>
						</td>
						<td class="iCargoTableDataTd ">
							<logic:present name="GPAREPORTINGDETAILSVO" property="registeredOrInsuredIndicator">
								<logic:iterate id="regInsInd" name="REGINSIND_ONETIME">
								<bean:define id="regInsIndvalue" name="regInsInd" property="fieldValue" />
								     <logic:equal name="GPAREPORTINGDETAILSVO" property="registeredOrInsuredIndicator" value="<%=String.valueOf(regInsIndvalue)%>" >

									<bean:write name="regInsInd" property="fieldDescription"/>
								     </logic:equal>
								</logic:iterate>

							</logic:present>
						</td>
						<td class="iCargoTableDataTd ">
						  
							<logic:present name="GPAREPORTINGDETAILSVO" property="noOfMailBags">
							<bean:define id="noOfMailBags" name="GPAREPORTINGDETAILSVO" property="noOfMailBags" />
							<bean:write name="GPAREPORTINGDETAILSVO" property="noOfMailBags"/>



							</logic:present>
						
						</td>
						<td class="iCargoTableDataTd ">
						  
							<logic:present name="GPAREPORTINGDETAILSVO" property="weight">

								<bean:define id="weight" name="GPAREPORTINGDETAILSVO" property="weight" />
								<%=new Formatter().format(FORMAT_STRING,weight).toString().trim()%>

								<%

								totweight = totweight + GPAREPORTINGDETAILSVO.getWeight();

								%>
							</logic:present>
						
						</td>
						<td class="iCargoTableDataTd ">
						  
							<logic:present name="GPAREPORTINGDETAILSVO" property="rate">
								<bean:define id="rate" name="GPAREPORTINGDETAILSVO" property="rate" />
								<%=new Formatter().format(FORMAT_STRING,rate).toString().trim()%>

								<%

								totrate = totrate + GPAREPORTINGDETAILSVO.getRate();

								%>
							</logic:present>
						
						</td>

						<td class="iCargoTableDataTd ">
						  
							<logic:present name="GPAREPORTINGDETAILSVO" property="amount">
								<ibusiness:moneyDisplay showCurrencySymbol="false" name="GPAREPORTINGDETAILSVO" moneyproperty="amount"  property="amount" />

								<%

								totamount = totamount + GPAREPORTINGDETAILSVO.getAmount().getRoundedAmount();

								%>
							</logic:present>
						
						</td>
						<td class="iCargoTableDataTd ">
						  
							<logic:present name="GPAREPORTINGDETAILSVO" property="tax">
								<bean:define id="tax" name="GPAREPORTINGDETAILSVO" property="tax" />
								<%=new Formatter().format(FORMAT_STRING,tax).toString().trim()%>

								<%

								tottax = tottax + GPAREPORTINGDETAILSVO.getTax();

								%>
							</logic:present>
						
						</td>
						<td class="iCargoTableDataTd ">
						  
							<logic:present name="GPAREPORTINGDETAILSVO" property="total">
								<ibusiness:moneyDisplay showCurrencySymbol="false" name="GPAREPORTINGDETAILSVO" moneyproperty="total"  property="total" />

								<%

								tottot = tottot + GPAREPORTINGDETAILSVO.getTotal().getRoundedAmount();

								%>
							</logic:present>
						</td>
						<td class="iCargoTableDataTd ">
							<logic:present name="GPAREPORTINGDETAILSVO" property="reportingStatus">
							<logic:iterate id="mailStatus" name="MAILSTATUS_ONETIME">
								<bean:define id="mailStatusvalue" name="mailStatus" property="fieldValue" />
								<logic:equal name="GPAREPORTINGDETAILSVO" property="reportingStatus" value="<%=(String)mailStatusvalue %>" >

								<ihtml:hidden name="form" property="mailStatus" value="<%=(String)mailStatusvalue%>"/>
									<%  System.out.println("mailsta***********"+mailStatusvalue); %>

								      <bean:write name="mailStatus"	property="fieldDescription"/>
								</logic:equal>
							</logic:iterate>
							</logic:present>

						</td>
						<td class="iCargoTableDataTd ">
						<logic:present name="GPAREPORTINGDETAILSVO" property="flightDetails">
						<bean:define id="flightDetails" name="GPAREPORTINGDETAILSVO" property="flightDetails" />
						<ihtml:text property="fltdetails" value="<%=(String)flightDetails%>" styleClass="iCargoTextFieldMedium" readonly="true" title="<%=(String)flightDetails%>" style="text-transform : uppercase"  />
						</logic:present>
						</td >


				      </tr>
				    
				    
				      </logic:notEqual>
				       <% index++;  %>
				      </logic:iterate>
				      </logic:present>

				</tbody>
				<tfoot>

					<tr>
						<td >&nbsp;</td>
						<td >&nbsp;</td>
						<td >&nbsp;</td>
						<td >&nbsp;</td>
						<td >&nbsp;</td>
						<td >&nbsp;</td>
						<td >&nbsp;</td>
						<td >&nbsp;</td>
						<td >&nbsp;</td>
						<td >&nbsp;</td>
						<td >&nbsp;</td>
						<td >&nbsp;</td>
						<td >
						  
							<bean:define id="totweightsum" value="<%=new Formatter().format(FORMAT_STRING,totweight).toString().trim()%>"/>
							<common:write name="totweightsum" />
						</td>

						<td >
						  
							<bean:define id="totratesum" value="<%=new Formatter().format(FORMAT_STRING,totrate).toString().trim()%>"/>
							<common:write name="totratesum" />
						</td>
						<td >
						  
							<bean:define id="totamountsum" value="<%=new Formatter().format(FORMAT_STRING,totamount).toString().trim()%>"/>
							<common:write name="totamountsum" />
						</td>
						<td >
						  
							<bean:define id="tottaxsum" value="<%=new Formatter().format(FORMAT_STRING,tottax).toString().trim()%>"/>
							<common:write name="tottaxsum" />
						</td>
						<td >
						  
							<bean:define id="tottotsum" value="<%=new Formatter().format(FORMAT_STRING,tottot).toString().trim()%>"/>
							<common:write name="tottotsum" />
						</td>
						<td >&nbsp;</td>
						<td >&nbsp;</td>




					</tr>
					
			       </tfoot>

				</table>
				</div>
<!--Table to be modified Ends-->

</ihtml:form>
<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>