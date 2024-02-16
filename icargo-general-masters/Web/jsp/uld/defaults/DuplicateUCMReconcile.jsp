<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  IN - ULD Management
* File Name				:  DuplicateUCmReconcile.jsp
* Date					:  19-Jul-2006
* Author(s)				:  Pradeep S
*************************************************************************/
 --%>
<%@ page language="java" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.UCMErrorLogForm"%>
<%@ page import="com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileDetailsVO"%>
<%@ include file="/jsp/includes/tlds.jsp" %>
<html:html>
	<head>
		
			

		<title>iCargo : Reconcile UCM</title>
		<meta name="decorator" content="popup_panel" >
			<common:include type="script" src="/js/uld/defaults/DuplicateUCMReconcile_Script.jsp"/>
	</head>
	<body id="bodyStyle">
	
		<bean:define id="form"
			 name="UCMErrorLogForm"
			 type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.UCMErrorLogForm"
			 toScope="page" />

		<div  id="pageDiv" class="iCargoPopupContent"  style="height:380px">
			
			<ihtml:form action="uld.defaults.messaging.screenloadduplicateucmreconcile.do" styleClass="ic-main-form">
			<ihtml:hidden property="canClose"/>
			<ihtml:hidden property="mismatchStatus"/>

			 <input type="hidden" name="currentDialogOption" />
			 <input type="hidden"  name="currentDialogId" />



			<business:sessionBean id="reconcileVO1"
				moduleName="uld.defaults"
				screenID="uld.defaults.ucmerrorlog"
				method="get" attribute="uCM1ReconcileVO" />

			<business:sessionBean id="reconcileVO2"
				moduleName="uld.defaults"
				screenID="uld.defaults.ucmerrorlog"
				method="get" attribute="uCM2ReconcileVO" />


				<div class="ic-content-main">
			  	 
					 	 <div class="ic-head-container" >	
				      <div class="ic-filter-panel">   
                        <div class="ic-input-container">
			  		    <div class="ic-row">
					    <div class="ic-row ic-label-100">
              			<logic:present name="reconcileVO1" property="messageType">
							<logic:present name="reconcileVO2" property="messageType">
								<bean:define id="msgType1" name="reconcileVO1" property="messageType"/>
								<bean:define id="msgType2" name="reconcileVO2" property="messageType"/>
									<logic:equal name="msgType1" value="OUT">
										<logic:equal name="msgType2" value="OUT">
										<common:message key="uld.defaults.messaging.ucmerrorlog.duplicate" scope="request"/> OUT
										</logic:equal>
									</logic:equal>
										<logic:equal name="msgType1" value="IN">
											<logic:equal name="msgType2" value="IN">
													<common:message key="uld.defaults.messaging.ucmerrorlog.duplicate" scope="request"/> IN
													</logic:equal>
												</logic:equal>

								</logic:present>
              			</logic:present>
              			<logic:notPresent name="reconcileVO2" property="flightNumber">
              				MisMatch
              			</logic:notPresent>
              			</div>
			  		</div>

                     <div class="ic-row">
					    <div class="ic-row ic-label-100">
			            <h3> Flight Details </h3>
			            </div>
					</div>	
             		

					<div class="ic-row">
					     <div class="ic-input" >
						          <% String carrierCode=form.getCarrierCode(); %>
								  <% String flightNo=form.getFlightNo(); %>
								  <%String flightDate=(String)form.getFlightDate();%>
							         <label>
								     <common:message key="uld.defaults.ucmerrorlog.lbl.fltno" scope="request"/></td>
									 </label>
										<ibusiness:flight id="flight"
										readonly="true"
											 carrierCodeProperty="carrierCode"
											flightCodeProperty="flightNo"
											calendarProperty="flightDate"
											carriercodevalue="<%=(String)carrierCode%>"
											flightcodevalue="<%=(String)flightNo%>"
											calendarValue="<%=(String)flightDate%>"
											calendarfieldMandatory="false"
											type="image"
											carrierCodeStyleClass="iCargoTextFieldVerySmall"
											flightCodeStyleClass="iCargoTextFieldSmall"

											componentID="CMP_ULD_DEFAULTS_DUPLICATEUCMRECONCILE_FLIGHT" />
						</div>

                  	</div>
				  			</div>
						</div>
					</div>
					
					
				<div class="ic-main-container" >		
					  <div class="ic-row">
				         <h3>
                    		<common:message key="uld.defaults.ucmerrorlog.lbl.ucmdetails" scope="request"/>
						</h3>
					   </div>
					   <div class="ic-row">
					           
								     <div class="ic-col-50">
					                    <div class="ic-row">
					                           <h4>
																  <logic:notPresent name="reconcileVO2" property="flightNumber">
						  						  								UCM - IN (
              			    </logic:notPresent>

						  <common:message key="uld.defaults.ucmerrorlog.lbl.ucm" scope="request"/>
						  -
						  <logic:present name="reconcileVO1" property="sequenceNumber">
						  <bean:write name="reconcileVO1"  property="sequenceNumber"/>
						  </logic:present>
						  <logic:notPresent name="reconcileVO2" property="flightNumber">
						  	)
						  	</logic:notPresent>
						                      </h4>
                                       </div>
					                    <div class="ic-row">
                                                 <div class="tableContainer"  id="div1"  style="height:150px;overflow:hidden">
																  <table class="fixed-header-table">
																	<thead>
																	  <tr class="iCargoTableHeadingLeft">
																		 <td width="14%" class="iCargoTableHeader">
																		 </td>
																		   <td width="43%">
																		  <common:message key="uld.defaults.ucmerrorlog.lbl.uldnumber" scope="request"/>
																		   <span></span></td>
																		   <td width="43%">
																		 <common:message key="uld.defaults.ucmerrorlog.lbl.pou" scope="request"/>
																		   <span></span></td>
																		   </tr>
																	</thead>
																	<tbody>
																					<logic:present name="reconcileVO1" property="reconcileDetailsVOs" >
																					   <bean:define id="reconcileDetails1" name="reconcileVO1"  property="reconcileDetailsVOs" />
																						<logic:iterate id="reconcileDetailsVO1" name="reconcileDetails1" indexId="nIndex" type="com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileDetailsVO">
																						
																					   <logic:present name="reconcileDetailsVO1" property="operationFlag">
																					   <bean:define id="opFlag" name="reconcileDetailsVO1" property="operationFlag"/>

																					  </logic:present>
																			   <logic:notPresent name="reconcileDetailsVO1" property="operationFlag">
																			   <bean:define id="opFlag"  value="NA"/>

																			   </logic:notPresent>

																			   <logic:notEqual name="opFlag" value="D">



																			<tr >
																  


																		  <td  class="iCargoTableTd ic-center">
																			<ihtml:checkbox property="selectedUcmsFirst"  value="<%=String.valueOf(nIndex)%>" onclick="selectCheckWithSameULDNumber(this)"/></td>
																		  <td  class="iCargoTableDataTd ic-center" >
																		  <logic:present name="reconcileDetailsVO1" property="uldNumber">
																			  <ihtml:text styleClass="iCargoEditableTextFieldRowColor1"  maxlength="12" property="uldNumbersFirst"  readonly="true" value="<%=reconcileDetailsVO1.getUldNumber()%>" style="width:130px;" />
																		</logic:present>
																			<logic:notPresent name="reconcileDetailsVO1" property="uldNumber" >
																				<ihtml:text styleClass="iCargoEditableTextFieldRowColor1" maxlength="12" property="uldNumbersFirst"  readonly="true" value="" style="width:130px;" />
																		</logic:notPresent>
																	  </td>
																	  <td class="ic-center">
																	  <logic:present name="reconcileDetailsVO1" property="pou">
																			   <ihtml:hidden property="pouFirst" value="<%=reconcileDetailsVO1.getPou()%>" />
																				<bean:write name="reconcileDetailsVO1" property="pou" />
																		</logic:present>
																		
																		</td>
																		</tr>
																	</logic:notEqual>

																
																	</logic:iterate>
																	</logic:present>

																	 </tbody>
																  </table>
			                              </div>
						      
							  </div>
						</div>
						 
						  <div class="ic-col-50">
                                        <div class="ic-row">
					                           <h4>
																  <common:message key="uld.defaults.ucmerrorlog.lbl.ucm" scope="request"/> -
																	<logic:notPresent name="reconcileVO2" property="flightNumber">
																		OUT
																		</logic:notPresent>
																  <logic:present name="reconcileVO2" property="sequenceNumber">
																	UCMs(
																		<bean:write name="reconcileVO2"  property="sequenceNumber"/>
																		)
																	</logic:present>
						                      </h4>
                                       </div>

                                     <div class="ic-row">
														<div class="tableContainer" id="div1" style="height:150px;overflow:hidden">
												  <table class="fixed-header-table">
													<thead>
													  <tr class="iCargoTableHeadingLeft">
														 <td width="14%" class="iCargoTableHeader">
														 </td>
													<td width="43%">
														  <common:message key="uld.defaults.ucmerrorlog.lbl.uldnumber" scope="request"/>
														   <span></span></td>
														   <td width="43%">
														 <common:message key="uld.defaults.ucmerrorlog.lbl.pou" scope="request"/>
														   <span></span></td>
														   </tr>
														   </thead>
														<tbody>
															<logic:present name="reconcileVO2" property="reconcileDetailsVOs" >
															   <bean:define id="reconcileDetails2" name="reconcileVO2"  property="reconcileDetailsVOs"/>
																<logic:iterate id="reconcileDetailsVO2" name="reconcileDetails2" indexId="nIndex1" type="com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileDetailsVO">
																
																   <logic:present name="reconcileDetailsVO2" property="operationFlag">
																	   <bean:define id="opFlag" name="reconcileDetailsVO2" property="operationFlag"/>

														  </logic:present>
															   <logic:notPresent name="reconcileDetailsVO2" property="operationFlag">
															   <bean:define id="opFlag"  value="NA"/>

															   </logic:notPresent>

															   <logic:notMatch name="opFlag" value="D">



															<tr >

																<td  class="iCargoTableTd">
																		<ihtml:checkbox property="selectedUcmsSecond"  value="<%=String.valueOf(nIndex1)%>" onclick="selectCheckWithSameULDNumber2(this)"/>
																</td>
																<td  class="iCargoTableDataTd ic-center">
																	  <logic:present name="reconcileDetailsVO2" property="uldNumber">
																			  <ihtml:text styleClass="iCargoEditableTextFieldRowColor1" indexId="nIndex1" styleId="ucm" maxlength="12"  readonly="true" property="uldNumbersSecond" value="<%=reconcileDetailsVO2.getUldNumber()%>" style="width:130px;" />
																	</logic:present>
																	<logic:notPresent name="reconcileDetailsVO2" property="uldNumber" >
																				<ihtml:text styleClass="iCargoEditableTextFieldRowColor1" maxlength="12" indexId="nIndex1" styleId="ucm" property="uldNumbersSecond"  readonly="true" value="" style="width:130px;" />
																	 </logic:notPresent>
																
															  </td>
															  <td class="ic-center">
																  <logic:present name="reconcileDetailsVO2" property="pou">
																  <ihtml:hidden property="pouSecond" value="<%=reconcileDetailsVO2.getPou()%>" />
																	<bean:write name="reconcileDetailsVO2" property="pou" />
																</logic:present>
																
															  </td>
														</tr>
												</logic:notMatch>

											
											</logic:iterate>
											</logic:present>

									 </tbody>

										</table>
									  </div>
			             </div>
						  </div>
			    

			  </div>

	 </div>
				   
				   
				   
				   
				<div class="ic-foot-container">						
				    <div class="ic-row">
						<div class="ic-button-container">   
				   
				    	  <ihtml:nbutton  property="btnOK" componentID="BTN_ULD_DEFAULTS_DUPLICATEUCMRECONCILE_OK">
                        <common:message key="uld.defaults.ucmerrorlog.btn.ok" scope="request"/>
                        </ihtml:nbutton>
                        <ihtml:nbutton property="btnClose" componentID="BTN_ULD_DEFAULTS_DUPLICATEUCMRECONCILE_CLOSE">
                        <common:message key="uld.defaults.ucmerrorlog.btn.close" scope="request"/>
                        </ihtml:nbutton>
                     </div>
			     </div>
			  </div>

</div>
</ihtml:form>
	</div>		
		 
	</body>
</html:html>

