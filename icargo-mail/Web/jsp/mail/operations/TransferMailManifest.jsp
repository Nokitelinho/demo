<%--
 /***********************************************************************
* Project       	 :  iCargo
* Module Code & Name :  MailTracking
* File Name     	 :  TransferManifest.jsp
* Date          	 :  02-APR-2008
* Author(s)     	 :  Devaprasanth .D

*************************************************************************/
 --%>
 <%@ include file="/jsp/includes/tlds.jsp" %>
  <%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailHandedOverReportForm" %>

 
	
	 <html:html>

  <head>
		
	 	 <title>
 	 <common:message bundle="transferMailManifestResources" key="mailtracking.defaults.transfermailmanifest.lbl.title" />
 	 </title>
  	<meta name="decorator" content="mainpanelrestyledui">
  	<common:include type="script" src="/js/mail/operations/TransferMailManifest_Script.jsp" />
	
  </head>

	<body>
	
	
		<%@include file="/jsp/includes/reports/printFrame.jsp" %>
		<div id="pagDiv" class="iCargoContent ic-masterbg" style="overflow:auto; height:100%;" >
		<ihtml:form action="/mailtracking.defaults.transfermailmanifest.screenload.do" >
 		<ihtml:hidden property="lastPageNum" />
		<ihtml:hidden property="displayPage" />
		<ihtml:hidden property="disableBtn" />
		<input type=hidden name="mySearchEnabled" />
		<%--ihtml:hidden property="screenStatusFlag" /--%>
		<bean:define id="form"
			name="TransferMailManifestForm"
			type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.TransferMailManifestForm"
			toScope="page"
			scope="request"/>
		<business:sessionBean id="transferMailManifestCol" moduleName="mail.operations" screenID="mailtracking.defaults.transfermailmanifest" method="get" attribute="transferManifestVOs" />
		<logic:present name="transferMailManifestCol">
			 <bean:define id="transferMailManifest" name="transferMailManifestCol" toScope="page"/>
	        </logic:present>
	        <div class="ic-content-main">
				<span class="ic-page-title ic-display-none">
					<common:message key="mailtracking.defaults.transfermailmanifest.lbl.transfermailmanifest" />
				</span>
				<div class="ic-head-container">
	
				<div class="ic-filter-panel">
					<div class="ic-input-container">
				<div  class="ic-row">
					<div class="ic-col">
						<h4>
							<common:message key="mailtracking.defaults.transfermailmanifest.lbl.searchcriteria" />
						</h4>
					</div>
				</div>
				
						<div class="ic-row">
							<div class="ic-input ic-split-35 ic-label-30">
									<label>
										<common:message key="mailtracking.defaults.transfermailmanifest.lbl.referenceno" />
									</label>
									<ihtml:text property="referenceNumber" componentID="CMP_MAILTRACKING_DEFAULTS_TRANFERMAILMANIFEST_REFERENCENO" maxlength="10" />
							</div>
							
							<div class="ic-input ic-split-40 ic-label-30 ic-mandatory">
									<label>
										<common:message key="mailtracking.defaults.transfermailmanifest.lbl.fromdate" />
									</label>
									<ibusiness:calendar property="fromDate" id="fromflightDate" type="image" componentID="CMP_MAILTRACKING_DEFAULTS_TRANFERMAILMANIFEST_FROMFLIGHTDATE" />
							</div>
							
							<div class="ic-input ic-split-10 ic-label-30 ic-mandatory">
									<label>
										<common:message key="mailtracking.defaults.transfermailmanifest.lbl.todate" />
									</label>
									<ibusiness:calendar property="toDate" id="toflightDate" type="image" componentID="CMP_MAILTRACKING_DEFAULTS_TRANFERMAILMANIFEST_TOFLIGHTDATE" />
							</div>
						</div>
						<div class="ic-row">
						    <div class="ic-input ic-split-35 ic-label-30">
									<label>
										<common:message key="mailtracking.defaults.transfermailmanifest.tooltip.airportcode" />
									</label>
									<ihtml:text property="airportCode" componentID="CMP_MAILTRACKING_DEFAULTS_TRANFERMAILMANIFEST_AIRPORTCODE" />
									<div class="lovImg">
										<img src="<%=request.getContextPath()%>/images/lov.png" id="airportCodeLov" width="22" height="22" />
									</div>
							</div>
							<div class="ic-input ic-split-40 ic-label-30">
									<label>
										<common:message key="mailtracking.defaults.transfermailmanifest.lbl.inflight" />
									</label>
									<ibusiness:flightnumber id="infltNo" carrierCodeProperty="inCarrierCode"  flightCodeProperty="inFlightNumber" carriercodevalue="<%=form.getInCarrierCode()%>" flightcodevalue="<%=form.getInFlightNumber()%>"  componentID="CMP_MAILTRACKING_DEFAULTS_TRANFERMAILMANIFEST_INFLIGHTNO"/>
							</div>
							
							<div class="ic-input ic-split-10 ic-label-30">
									<label>
										<common:message key="mailtracking.defaults.transfermailmanifest.lbl.inflightdate" />
									</label>
									<ibusiness:calendar property="inFlightDate" id="inflightDate" type="image" componentID="CMP_MAILTRACKING_DEFAULTS_TRANFERMAILMANIFEST_INFLIGHTDATE" />
							</div>
							
						</div>
						<div class="ic-row">
						    <div class="ic-input ic-split-35 ic-label-30">
									<label>
										<common:message  key="mailtracking.defaults.transfermailmanifest.tooltip.transferstatus" scope="request"/>
									</label>
									<!-- Hard coding options due to missing of onetime-->
									 <ihtml:select property="transferStatus" componentID="CMP_MAILTRACKING_DEFAULTS_TRANFERMAILMANIFEST_STATUS" >
										       <html:option value=""><common:message key="combo.select"/></html:option>
											   <html:option value="TRFINT">Transfer Initiated</html:option>
											   <html:option value="TRFREJ">Transfer Rejected</html:option>
											   <html:option value="TRFEND">Transfer Ended</html:option>
									 </ihtml:select>
							</div>
							<div class="ic-input ic-split-40 ic-label-30">
									<label>
										<common:message key="mailtracking.defaults.transfermailmanifest.lbl.outflight" />
									</label>
									<ibusiness:flightnumber id="outfltNo" carrierCodeProperty="outCarrierCode"  flightCodeProperty="outFlightNumber" carriercodevalue="<%=form.getOutCarrierCode()%>" flightcodevalue="<%=form.getOutFlightNumber()%>"  componentID="CMP_MAILTRACKING_DEFAULTS_TRANFERMAILMANIFEST_OUTFLIGHTNO"/>
							</div>
							
							<div class="ic-input ic-split-10 ic-label-30">
									<label>
										<common:message key="mailtracking.defaults.transfermailmanifest.lbl.outflightdate" />
									</label>
									<ibusiness:calendar property="outFlightDate" id="outflightDate" type="image" componentID="CMP_MAILTRACKING_DEFAULTS_TRANFERMAILMANIFEST_OUTFLIGHTDATE" />
							</div>
							<div class="ic-button-container">
									<ihtml:nbutton property="btnList" componentID="CMP_MAILTRACKING_DEFAULTS_TRANFERMAILMANIFEST_LIST" accesskey="L" >
									<common:message key="mailtracking.defaults.transfermailmanifest.btn.list" />
									</ihtml:nbutton>

									<ihtml:nbutton property="btnClear" componentID="CMP_MAILTRACKING_DEFAULTS_TRANFERMAILMANIFEST_CLEAR" accesskey="C" >
									<common:message key="mailtracking.defaults.transfermailmanifest.btn.clear" />
									</ihtml:nbutton>
							</div>
						
						</div>
					</div>
				</div>
				</div>
				
				<div class="ic-main-container">
				 <div class="ic-row">
				 <div class="ic-col-100">
					<%String lstPgNo = "";%>
							<logic:present name="TransferMailManifestForm" property="lastPageNum">
								<bean:define id="lastPg" name="TransferMailManifestForm" property="lastPageNum" scope="request"  toScope="page" />
								<%
									lstPgNo = (String) lastPg;
								%>
							</logic:present>
							<logic:present name="transferMailManifestCol" >
							<bean:define id="pageObj" name="transferMailManifestCol"  />
							  <common:paginationTag pageURL="mailtracking.defaults.transfermailmanifest.list.do"
										name="pageObj"
										display="label"
										labelStyleClass="iCargoResultsLabel"
										lastPageNum="<%=lstPgNo%>" />
										</logic:present>
							<logic:notPresent name="transferMailManifestCol" >
								&nbsp;
							</logic:notPresent>
					        
					          <div class="ic-button-container ic-pad-5">
						   <logic:present name="transferMailManifestCol" >
						     <bean:define id="pageObj1" name="transferMailManifestCol"  />
							<common:paginationTag
							  linkStyleClass="iCargoLink"
							  disabledLinkStyleClass="iCargoLink"
							  pageURL="javascript:submitPage('lastPageNum','displayPage')"
							  name="pageObj1"
							  display="pages"
							  lastPageNum="<%=lstPgNo%>"
							  exportToExcel="true"
							  exportTableId="listuldtable"
							  exportAction="mailtracking.defaults.transfermailmanifest.list.do"/>
						  </logic:present>
						  <logic:notPresent name="transferMailManifestCol" >
							&nbsp;
						  </logic:notPresent>
						  </div>
				 </div>
				 </div>
					 <div class="ic-row">
						 <div class="tableContainer table-border-solid" id="pageDiv" style="width:100%; height:550px;">
							<table  class="fixed-header-table" id="listuldtable" style="width:100%;">
								<thead>
						<tr class="iCargoTableHeadingCenter" >
							  <td  rowspan="2" width = "3%"><input type="checkbox"
											name="checkAll" value="checkbox">  </td>

							  <td rowspan="2" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.transfermailmanifest.lbl.referenceno" /><span></span></td>

							  <td rowspan="2" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.transfermailmanifest.lbl.date" /><span></span></td>
							  <td  rowspan="2" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.transfermailmanifest.lbl.airport" /><span></span></td>
							  <td colspan="3" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.transfermailmanifest.lbl.inflight"/></td>
							  <td colspan="3" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.transfermailmanifest.lbl.outflight"/></td>
							  <td  rowspan="2" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.transfermailmanifest.lbl.nob" /><span></span></td>
							  <td  rowspan="2" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.transfermailmanifest.lbl.weight" /><span></span></td>
							  <td  rowspan="2" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.transfermailmanifest.lbl.trnsferstatus" /><span></span></td>

						</tr>
						<tr class="iCargoTableHeadingCenter" >
							  <td width="10%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.transfermailmanifest.lbl.carrier"/></td>
							  <td width="10%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.transfermailmanifest.lbl.flightno"/></td>
							  <td width="10%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.transfermailmanifest.lbl.date" /></td>
							  <td width="10%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.transfermailmanifest.lbl.carrier" /></td>
							  <td width="10%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.transfermailmanifest.lbl.flightno" /></td>
							  <td width="10%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.transfermailmanifest.lbl.date" /></td>

						</tr>
							</thead>
								 <tbody>
					       <logic:present name="transferMailManifest" >
								<bean:define id="transferManifest" name="transferMailManifest" />
								<logic:iterate id="transferMailManifestVO" name="transferManifest" indexId="rowid" type="com.ibsplc.icargo.business.mail.operations.vo.TransferManifestVO">
								<index="rowid">
                                                                <tr>
								
								<td  class="iCargoTableDataTd" style="text-align:center"><input type="checkbox" name="selectMail" value="<%= rowid.toString() %>"></td>
								<td  class="iCargoTableDataTd"><bean:write name="transferMailManifestVO" property="transferManifestId"/></td>
								<td>
									<logic:present name="transferMailManifestVO" property="transferredDate">
										<%=transferMailManifestVO.getTransferredDate().toDisplayDateOnlyFormat()%>
						   			</logic:present>
								</td>
								<td  class="iCargoTableDataTd"><center><bean:write name="transferMailManifestVO" property="airPort"/></center></td>
								<td  class="iCargoTableDataTd"><center><bean:write name="transferMailManifestVO" property="transferredFromCarCode"/></center></td>
								<td  class="iCargoTableDataTd"><center><bean:write name="transferMailManifestVO" property="transferredFromFltNum"/></center></td>
								<td>
									<logic:present name="transferMailManifestVO" property="fromFltDat">
										<%=transferMailManifestVO.getFromFltDat().toDisplayDateOnlyFormat()%>
						   			</logic:present>
								</td>
								
								<td  class="iCargoTableDataTd"><center><bean:write name="transferMailManifestVO" property="transferredToCarrierCode"/></center></td>
								<td  class="iCargoTableDataTd"><center><bean:write name="transferMailManifestVO" property="transferredToFltNumber"/></center></td>
								<td>
									<logic:present name="transferMailManifestVO" property="toFltDat">
										<%=transferMailManifestVO.getToFltDat().toDisplayDateOnlyFormat()%>
						   			</logic:present>
								</td>
								<td  class="iCargoTableDataTd"><center><bean:write name="transferMailManifestVO" property="totalBags"/></center></td>
								<!-- Modified by A-8236 for ICRD-251462--->
								<td  class="iCargoTableDataTd"><center><common:write name="transferMailManifestVO" property="totalWeight" unitFormatting="true"/></center></td>
								<td  class="iCargoTableDataTd"><center><common:write name="transferMailManifestVO" property="transferStatus" /></center>
								<input type="hidden" name="transferStatus" value="<%=transferMailManifestVO.getTransferStatus()%>">
								</td>
								</tr>
								
								</logic:iterate>
								</logic:present>
					       </tbody>
							</table>
						</div>
					 
					 </div>
				
				</div>
				<div class="ic-foot-container">
				<div class="ic-button-container ic-pad-5">
							<!-- Changed the Button Name from Details to Print -->
						    <!-- Modified by A-6770 for ICRD-135255 starts here -->
							<%StringBuilder printSubMenu=new StringBuilder("");%>
							<%StringBuilder printSubMenuDSN=new StringBuilder("");%>
							<!--<ihtml:nbutton property="btnPrint" componentID="CMP_MAILTRACKING_DEFAULTS_TRANFERMAILMANIFEST_PRINT" accesskey="P">
      							<common:message key="mailtracking.defaults.transfermailmanifest.btn.print" />
      						</ihtml:nbutton>-->
							
							
							<ihtml:multibutton disabled="false" id="btnPrint"  componentID="CMP_MAILTRACKING_DEFAULTS_TRANFERMAILMANIFEST_PRINT" 
							tabIndex="17" mainMenu="mailtracking.defaults.transfermailmanifest.btn.print" 
							subMenu="{label:mailtracking.defaults.transfermailmanifest.btn.dsnlevelsum,jsFunction:printDespatchLevel();,componentID:CMP_MAILTRACKING_DEFAULTS_TRANFERMAILMANIFEST_PRINT},
							{label:mailtracking.defaults.transfermailmanifest.btn.malbaglevelsum,jsFunction:printMailbagLevel();,componentID:CMP_MAILTRACKING_DEFAULTS_TRANFERMAILMANIFEST_PRINT}"/>	
							
							<%printSubMenu.append("{label:mailtracking.defaults.transfermailmanifest.btn.printcn38,jsFunction:printCN('CN38');,componentID:CMP_MAILTRACKING_DEFAULTS_TRANFERMAILMANIFEST_PRINT_CN_38},{label:mailtracking.defaults.transfermailmanifest.btn.printcn37,jsFunction:printCN('CN37');,componentID:CMP_MAILTRACKING_DEFAULTS_TRANFERMAILMANIFEST_PRINT_CN_37},{label:mailtracking.defaults.transfermailmanifest.btn.printcn41,jsFunction:printCN('CN41');,componentID:CMP_MAILTRACKING_DEFAULTS_TRANFERMAILMANIFEST_PRINT_CN_41},{label:mailtracking.defaults.transfermailmanifest.btn.printcn46,jsFunction:printCN('CN46');,componentID:CMP_MAILTRACKING_DEFAULTS_TRANFERMAILMANIFEST_PRINT_CN_46},{label:mailtracking.defaults.transfermailmanifest.btn.printcn47,jsFunction:printCN('CN47');,componentID:CMP_MAILTRACKING_DEFAULTS_TRANFERMAILMANIFEST_PRINT_CN_47}");%>
							<%printSubMenuDSN.append("{label:mailtracking.defaults.transfermailmanifest.btn.printcn38,jsFunction:printCNDSN('CNSummary38');,componentID:CMP_MAILTRACKING_DEFAULTS_TRANFERMAILMANIFEST_PRINT_CN_DSN_38},{label:mailtracking.defaults.transfermailmanifest.btn.printcn37,jsFunction:printCNDSN('CNSummary37');,componentID:CMP_MAILTRACKING_DEFAULTS_TRANFERMAILMANIFEST_PRINT_CN_DSN_37},{label:mailtracking.defaults.transfermailmanifest.btn.printcn41,jsFunction:printCNDSN('CNSummary41');,componentID:CMP_MAILTRACKING_DEFAULTS_TRANFERMAILMANIFEST_PRINT_CN_DSN_41},{label:mailtracking.defaults.transfermailmanifest.btn.printcn46,jsFunction:printCNDSN('CNSummary46');,componentID:CMP_MAILTRACKING_DEFAULTS_TRANFERMAILMANIFEST_PRINT_CN_DSN_46},{label:mailtracking.defaults.transfermailmanifest.btn.printcn47,jsFunction:printCNDSN('CNSummary47');,componentID:CMP_MAILTRACKING_DEFAULTS_TRANFERMAILMANIFEST_PRINT_CN_DSN_47}");%>
							
							<ihtml:multibutton id="" componentID="CMP_MAILTRACKING_DEFAULTS_TRANFERMAILMANIFEST_PRINT_CN"  tabIndex="55" mainMenu="mailtracking.defaults.transfermailmanifest.btn.printcn" accesskeyFlip="P"  
								subMenu="<%=printSubMenu.toString()%>"/> 
		
							
							<ihtml:multibutton id="" componentID="CMP_MAILTRACKING_DEFAULTS_TRANFERMAILMANIFEST_PRINT_CN_DSN"  tabIndex="55" mainMenu="mailtracking.defaults.transfermailmanifest.btn.printcndsn" accesskeyFlip="P"  
								subMenu="<%=printSubMenuDSN.toString()%>"/>	
		
							<!-- Modified by A-6770 for ICRD-135255 ends here -->
							<ihtml:nbutton property="btnReject" componentID="CMP_MAILTRACKING_DEFAULTS_TRANFERMAILMANIFEST_REJECT" accesskey="P" disabled="<%=form.isTransferParameter()%>">
      							<common:message key="mailtracking.defaults.transfermailmanifest.btn.reject" />
      						</ihtml:nbutton>
							<ihtml:nbutton property="btnEnd" 
componentID="CMP_MAILTRACKING_DEFAULTS_TRANFERMAILMANIFEST_END" accesskey="P" disabled="<%=form.isTransferParameter()%>">
      							<common:message key="mailtracking.defaults.transfermailmanifest.btn.end" />
      						</ihtml:nbutton>
      						<ihtml:nbutton property="btnClose" componentID="CMP_MAILTRACKING_DEFAULTS_TRANFERMAILMANIFEST_CLOSE" accesskey="O">
      							<common:message key="mailtracking.defaults.transfermailmanifest.btn.close" />
      						</ihtml:nbutton>				
				</div>
			</div>
			</div>
			</ihtml:form>
		  </div>
		
				
		
	</body>
	 </html:html>
