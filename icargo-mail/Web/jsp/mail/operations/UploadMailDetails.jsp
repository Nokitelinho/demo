<%--****************************************************
* Project	 		: iCargo
* Module Code & Name: Mailtracking
* File Name			: UploadDetails.jsp
* Date				: 07-Jul-2009

 ***************************************************--%>
 
<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>

<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.UploadMailDetailsForm"%>


	
	
<html:html>
	<head>
		
	
				
		<title><common:message bundle="uploadMailDetailsResources" key="mailtracking.defaults.uploadmaildetails.lbl.title" /></title>
		<meta name="decorator" content="mainpanel">
		<common:include type="script" src="/js/mail/operations/UploadMailDetails_Script.jsp"/>
	</head>
	
	<body id="bodyStyle">
	
	
			   <div class="iCargoContent" id="pageDiv"  style="width:100%;height:expression(((document.body.clientHeight*83)/100)); overflow:auto;">	
		<ihtml:form action="/mailtracking.defaults.upload.do">	
		
		<bean:define id="form" 
					 name="UploadMailDetailsForm"
					 type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.UploadMailDetailsForm"
					 toScope="page"
					 scope="request"/>
		<business:sessionBean id="scannedMailDetailVOs" 
							  moduleName="mail.operations" 
							  screenID="mailtracking.defaults.batchmailupload" 
							  method="get" 
							  attribute="ScannedMailDetailsVOs" />
		<business:sessionBean id="mailbagVOs" 
							  moduleName="mail.operations" 
							  screenID="mailtracking.defaults.batchmailupload" 
							  method="get" 
							  attribute="MailExceptionDetails" />  
			
		<ihtml:hidden property="selectedScannedVOIndx" />
        <ihtml:hidden property="inboundFlightCloseflag" /> 
		<input type="hidden" name="currentDialogId" />
		<input type="hidden" name="currentDialogOption" />
		<ihtml:hidden property="selectedProcessPoint"/>
		    <div class="ic-content-main">
				<span class="ic-page-title ic-display-none">
					<common:message key="mailtracking.defaults.uploadmaildetails.lbl.pageheading" />
				</span>
				    <div class="ic-main-container">
					  <div id="processPoint">
					    <div id="div1" class="tableContainer"  style="width:100%; height:expression((((document.body.clientHeight*30)/100)))">
							<table  id="uploadTable1" class="fixed-header-table" style="width:100%">
							    <thead>
									<tr class="iCargoTableHeadingLeft">
										<td width="1%" class="iCargoTableHeaderLabel">&nbsp;</td>
										<td width="10%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.uploadmaildetails.head.processpoint" /></td>
										<td width="14%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.uploadmaildetails.head.status" /></td>
										<td width="15%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.uploadmaildetails.head.outboundflight" /></td>
										
										<td width="12%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.uploadmaildetails.head.containerno" /></td>
										<td width="12%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.uploadmaildetails.head.scanned" /></td>
										<td width="12%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.uploadmaildetails.head.saved" /></td>
										<td width="12%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.uploadmaildetails.head.exceptions" /></td>											
										<td width="12%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.uploadmaildetails.head.deleted" /></td>
									</tr>
								</thead>
                                <tbody>
									<logic:present name="scannedMailDetailVOs">															         
										<%int scanCount=0;%> 
								       <logic:iterate id="scannedMailDetailVO" name="scannedMailDetailVOs"  type="com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO" >					
										<tr>
											<td class="ic-center">
											  <ihtml:radio property="selectFlightRadio" value="<%=String.valueOf(scanCount)%>"  componentID="CMP_MAILTRACKING_DEFAULTS_UPLOAD_MAIL_DETAILS_RADIOFLIGHT"/></center>
											</td>
												<td class="ic-center">
												  <!-- processpoint -->
												  <logic:present name="scannedMailDetailVO" property="processPoint">
													 <bean:write name="scannedMailDetailVO" property="processPoint"/>
														<bean:define id="processPoint" name="scannedMailDetailVO" property="processPoint" toScope="request" />
														<ihtml:hidden property="processPoints" value="<%=((String)processPoint)%>" />
														<bean:define id="savedBags" name="scannedMailDetailVO" property="savedBags" toScope="request" />
														<ihtml:hidden property="savedCount" value="<%=(String.valueOf(savedBags))%>" />
												   </logic:present>
												
												</td>
														
											<td class="ic-center">
												 <logic:present name="scannedMailDetailVO" property="status">
													 <!--<bean:write name="scannedMailDetailVO" property="status"/>-->
													 <logic:equal  name="scannedMailDetailVO" property="status" value="U" >
														<!-- <img id="isUnSaved" src="<%=request.getContextPath()%>/images/cross.gif" /> -->
														<bean:message bundle='uploadMailDetailsResources' key='mailtracking.defaults.uploadmaildetails.message.unsaved' />
													 </logic:equal>
													 <logic:equal  name="scannedMailDetailVO" property="status" value="S">
														<!-- <img id="isSaved" src="<%=request.getContextPath()%>/images/tick.gif" /> -->
														<bean:message bundle='uploadMailDetailsResources' key='mailtracking.defaults.uploadmaildetails.message.saved' />
													 </logic:equal>
													 <logic:equal  name="scannedMailDetailVO" property="status" value="P">
														<!-- <img id="isPartiallySaved" src="<%=request.getContextPath()%>/images/warning.gif" /> -->
														<bean:message bundle='uploadMailDetailsResources' key='mailtracking.defaults.uploadmaildetails.message.partiallysaved' />
													 </logic:equal>
													 
												   </logic:present>
												</td>	
											
											<td class="ic-center">
											 	
												  <!-- outboundflight -->
												  <%StringBuilder flightNumber=new StringBuilder();%>
													
												  
													<logic:present name="scannedMailDetailVO" property="flightDate">
																																		
													<%flightNumber=flightNumber.append(scannedMailDetailVO.getCarrierCode());%>
														<logic:present name="scannedMailDetailVO" property="flightDate">
															<%flightNumber.append(" ").append(scannedMailDetailVO.getFlightNumber());%>
															<bean:define id="flightDate" name="scannedMailDetailVO" property="flightDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
													 <%flightNumber=flightNumber.append(" ").append(flightDate.toDisplayDateOnlyFormat());%>
														</logic:present>
												   
												   
												   </logic:present>
												   
												   <logic:notPresent name="scannedMailDetailVO" property="flightDate">
														  <logic:present name="scannedMailDetailVO" property="carrierCode">
															 <%flightNumber=flightNumber.append(scannedMailDetailVO.getCarrierCode());%>
															 
														  </logic:present>
														  <logic:present name="scannedMailDetailVO" property="destination">
																<%flightNumber=flightNumber.append("-").append(scannedMailDetailVO.getDestination());%>
														   </logic:present>
													</logic:notPresent>
												   <%=(flightNumber.toString())%>
											   										
											</td>
											
											<td class="ic-center">
											  <logic:present name="scannedMailDetailVO" property="containerNumber">
													 <bean:write name="scannedMailDetailVO" property="containerNumber"/>
												   </logic:present>
											
											</td>
											<td class="ic-center">
											   <logic:present name="scannedMailDetailVO" property="scannedBags">
													 <bean:write name="scannedMailDetailVO" property="scannedBags"/>
												   </logic:present>	
											
											</td>
											<td class="ic-center">
												<logic:present name="scannedMailDetailVO" property="savedBags">
													 <bean:write name="scannedMailDetailVO" property="savedBags"/>
												   </logic:present>	
											</td>
											<td class="ic-center">
												 <!--  exceptionbag count -->
												 <logic:present name="scannedMailDetailVO" property="exceptionBagCout">
													 <bean:write name="scannedMailDetailVO" property="exceptionBagCout"/>
												   </logic:present>	
												</td>
											<td class="ic-center">
												  <!-- deletedexceptionbagcount -->
												 <logic:present name="scannedMailDetailVO" property="deletedExceptionBagCout">
													 <bean:write name="scannedMailDetailVO" property="deletedExceptionBagCout"/>
												   </logic:present>	
																						
											</td>
										</tr>										
																		
									 <%scanCount++;%>
									</logic:iterate>	
								</logic:present> 									   
						    </tbody>	
						</table>
					</div>
					</div>
					<div class="ic-row"> 
					    <div class="ic-button-container">
						    <ihtml:nbutton property="btModify" componentID="BTN_MAILTRACKING_DEFAULTS_UPLOADMAILDETAILS_MODIFY" >
						        <common:message key="mailtracking.defaults.uploadmaildetails.tooltip.modify" />
							</ihtml:nbutton>
							<ihtml:nbutton property="btViewExceptions" componentID="BTN_MAILTRACKING_DEFAULTS_UPLOADMAILDETAILS_VIEWEXCEPTIONS" >
								<common:message key="mailtracking.defaults.uploadmaildetails.btn.viewexceptions" />
							</ihtml:nbutton>
							<ihtml:nbutton property="btSave" componentID="BTN_MAILTRACKING_DEFAULTS_UPLOADMAILDETAILS_SAVE" >
							  <common:message key="mailtracking.defaults.uploadmaildetails.btn.save" />
							</ihtml:nbutton>
						</div>
					</div>
					<div class="ic-row">
					    <h3>
					        <common:message key="mailtracking.defaults.uploadmaildetails.lbl.subheading.mailexceptions" />
						</h3>
					</div>
					<div class="ic-row">
					    <div id="mailExceptions">
							<div id="div2" class="tableContainer" style="width:100%; height:expression((((document.body.clientHeight*30)/100)))" >	
												   <!-- table for mail details starts -->
								<table id="uploadTable2"  class="fixed-header-table">
					                <thead>
										<tr class="iCargoTableHeadingLeft"  >
											<td  width="1%"  class="iCargoTableHeaderLabel"><input type="checkbox" id="uploadMail" name="checkAllUploadMailTag" onclick="updateHeaderCheckBox(this.form,this,this.form.selectedExceptions);"/></td>
											<td  width="8%"  class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.uploadmaildetails.lbl.receptacles.ooe" /></td>
											<td  width="8%"  class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.uploadmaildetails.lbl.receptacles.doe" /></td>
											<td  width="4%"  class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.uploadmaildetails.lbl.receptacles.cat" /></td>
											<td  width="4%"  class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.uploadmaildetails.lbl.receptacles.sc" /></td>
											<td  width="4%"  class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.uploadmaildetails.lbl.receptacles.year" /></td>
											<td  width="6%"  class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.uploadmaildetails.lbl.receptacles.dsn" /></td>
											<td  width="4%"  class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.uploadmaildetails.lbl.receptacles.rsn" /></td>
											<td  width="4%"  class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.uploadmaildetails.lbl.receptacles.hni" /></td>											
											<td width="4%"   class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.uploadmaildetails.lbl.receptacles.ri" /></td>
											<td  width="6%"  class="iCargoTableHeaderLabel">
												<common:message key="mailtracking.defaults.uploadmaildetails.lbl.receptacles.weight" />
													
											</td>											
											<td width="14%"  class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.uploadmaildetails.lbl.receptacles.desdate" /></td>
											 
							
											<td width="33%" class="iCargoTableHeaderLabel" ><common:message key="mailtracking.defaults.uploadmaildetails.lbl.receptacles.exceptions" /></td>
										</tr>
									</thead>
				                    <tbody id="ReceptaclesTableBody" class="scrollContent">
										<logic:present name="mailbagVOs">
											<logic:iterate id="mailbagVO" name="mailbagVOs" indexId="rowCount"  type="com.ibsplc.icargo.business.mail.operations.vo.MailbagVO" >			
												<%
													String acpcolor = "#000000";
												%>
											 
												   <logic:present name="mailbagVO" property="errorType">
																 <logic:notEqual name="mailbagVO" property="errorType" value="EXPWARN">												
																	  <% acpcolor = "#ff0000";%>										      
																   
																 </logic:notEqual>
																 <logic:equal name="mailbagVO" property="errorType" value="EXPWARN">											
																	   <% acpcolor = "#0000CC";%>												  
																	
																 </logic:equal>
													</logic:present>
                                                        <td class="iCargoTableDataTd ic-center" >
															
																 <input type="checkbox"  name="selectedExceptions" value="<%=String.valueOf(rowCount)%>"   /> 
															  
														</td>
													    <td  class="iCargoTableDataTd ic-center" >
														
														 <logic:present name="mailbagVO" property="ooe">
																	 <bean:write name="mailbagVO" property="ooe"/>
														   </logic:present>	
														 </center> 
													    </td>
														  
														<td class="iCargoTableDataTd ic-center">
															
															 <logic:present name="mailbagVO" property="doe">
																		 <bean:write name="mailbagVO" property="doe"/>
															   </logic:present>	
															   
														</td>
														  
														<td  class="iCargoTableDataTd ic-center">
															
															<logic:present name="mailbagVO" property="mailCategoryCode">
																		 <bean:write name="mailbagVO" property="mailCategoryCode"/>
															   </logic:present>	
															   
														</td>
														  
														<td  class="iCargoTableDataTd ic-center">
														  
															<logic:present name="mailbagVO" property="mailSubclass">
																		 <bean:write name="mailbagVO" property="mailSubclass"/>
															   </logic:present>	
														  	   
														</td>
														  
														  <td  class="iCargoTableDataTd ic-center">
																																											
															<logic:present name="mailbagVO" property="despatchSerialNumber">
																<logic:present name="mailbagVO" property="year">
																		<bean:write name="mailbagVO" property="year"/>
																</logic:present>	
															</logic:present>	  
															  
														  </td>
														  
														  <td  class="iCargoTableDataTd ic-center">
														
															 <logic:present name="mailbagVO" property="despatchSerialNumber">
																		 <bean:write name="mailbagVO" property="despatchSerialNumber"/>
															   </logic:present>	
															 
														  </td>
														  
														  <td  class="iCargoTableDataTd ic-center">
															
															 <logic:present name="mailbagVO" property="receptacleSerialNumber">
																		 <bean:write name="mailbagVO" property="receptacleSerialNumber"/>
															   </logic:present>	
															
														  </td>
														  
														  <td  class="iCargoTableDataTd ic-center"> 
														   
															 <logic:present name="mailbagVO" property="highestNumberedReceptacle">
																		 <bean:write name="mailbagVO" property="highestNumberedReceptacle"/>
															   </logic:present>	
														   
														  </td>
														  
														  <td  class="iCargoTableDataTd ic-center">
														
															 <logic:present name="mailbagVO" property="registeredOrInsuredIndicator">
																		 <bean:write name="mailbagVO" property="registeredOrInsuredIndicator"/>
															   </logic:present>	
														   
														  </td>
														  
														  <td  class="iCargoTableDataTd ic-center">
																																										
															<logic:present name="mailbagVO" property="despatchSerialNumber">
															  <logic:present name="mailbagVO" property="weight">
																		 <common:write name="mailbagVO" property="weight" unitFormatting="true"/><!--modified by A-7371-->
															   </logic:present>	
															</logic:present>	 
															 
														  </td>
														  
														  <td  class="iCargoTableDataTd ic-center"
															
															  <logic:present name="mailbagVO" property="scannedDate">
																 <bean:define id="scannedDate" name="mailbagVO" property="scannedDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>	     
																		  <%=(scannedDate.toDisplayFormat())%>
															   </logic:present>	
															  
														  </td>
														  
														  
														  
														  <td class="iCargoTableDataTd ic-center">
															 
															   <logic:present name="mailbagVO" property="errorDescription">
																		 <bean:write name="mailbagVO" property="errorDescription"/>
															   </logic:present>
															
														  </td>
														  
														  
												   </tr>		  
											  </logic:iterate>
										</logic:present>
										<logic:notPresent name="mailbagVOs">
											<tr>
												<td>
													&nbsp;
												</td>
											</tr>
										</logic:notPresent>
									</tbody> 
								</table>
							</div>
					   </div> 
				    </div>
			    </div>
					<div class="ic-foot-container">
					    <div class="ic-button-container">
						    <ihtml:nbutton property="btDelete" componentID="BTN_MAILTRACKING_DEFAULTS_UPLOADMAILDETAILS_DELETE" >
								<common:message key="mailtracking.defaults.uploadmaildetails.btn.delete" />
							</ihtml:nbutton>
							<ihtml:nbutton property="btAcknowledge" componentID="BTN_MAILTRACKING_DEFAULTS_UPLOADMAILDETAILS_ACKNOWLEDGE" >
								<common:message key="mailtracking.defaults.uploadmaildetails.btn.acknowledge" />
							</ihtml:nbutton>
							<ihtml:nbutton property="btClose" componentID="BTN_MAILTRACKING_DEFAULTS_UPLOADMAILDETAILS_CLOSE" >
								<common:message key="mailtracking.defaults.uploadmaildetails.btn.close" />
							</ihtml:nbutton>
						</div>
					</div>
			
			</div>    
		</ihtml:form>
			</div>
	
				

	</body>
</html:html>
