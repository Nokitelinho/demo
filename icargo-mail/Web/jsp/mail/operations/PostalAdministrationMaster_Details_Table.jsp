<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  Mailtracking
* File Name				:  PostalAdministrationMaster_Details_Table.jsp
* Date					: 18-May-2017
* Author(s)				: A-6850
*************************************************************************/
 --%>
<%@ include file="/jsp/includes/tlds.jsp" %>	
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.operations.PostalAdministrationForm"%>
 <%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO"%>

 <%@ page import = "com.ibsplc.icargo.business.mail.operations.vo.MailEventVO"%>
 <%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
 <%@ page import = "java.util.Calendar" %>
 <%@ page import="java.util.HashMap"%>
 <%@ page import="com.ibsplc.xibase.util.time.TimeConvertor" %>
 
	
	<bean:define id="form"
		 name="PostalAdministrationForm"
		 type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.PostalAdministrationForm"
		 toScope="page" />

	<business:sessionBean id="paVO"
		 moduleName="mail.operations"
		 screenID="mailtracking.defaults.masters.postaladministration"
		 method="get"
	 	 attribute="paVO"/>
	 <business:sessionBean id="postalAdministrationDetailsVOs"
	 		 moduleName="mail.operations"
	 		 screenID="mailtracking.defaults.masters.postaladministration"
	 		 method="get"
	 	 attribute="postalAdministrationDetailsVOs"/>



	<business:sessionBean id="ONETIME_CATEGORY"
		 moduleName="mail.operations"
		 screenID="mailtracking.defaults.masters.postaladministration"
		 method="get"
	 	 attribute="oneTimeCategory"/>
	 <business:sessionBean id="OneTimeValues"
	 	 moduleName="mail.operations"
		 screenID="mailtracking.defaults.masters.postaladministration"
	 	  method="get"
	  attribute="OneTimeValues" />
	  <!--Modified by A-7938 for ICRD-243967-->
	  
<div class="tableContainer" id="div1" style="height:95px;">
			<table class="fixed-header-table ic-pad-3">
				<thead>
					<tr>
						<td  height="20" class="iCargoTableHeaderLabel" width="3%"><input type="checkbox" name="checkAll" value="checkbox"></td>
						<td class="iCargoTableHeaderLabel"width="5%"><common:message key="mailtracking.defaults.pamaster.lbl.category"/></td>
						<td class="iCargoTableHeaderLabel" width="5%"><common:message key="mailtracking.defaults.pamaster.lbl.subclas"/></td>
						<td class="iCargoTableHeaderLabel" width="7%"><common:message key="mailtracking.defaults.pamaster.lbl.recieved"/></td>
						<td class="iCargoTableHeaderLabel" width="10%"><common:message key="mailtracking.defaults.pamaster.lbl.handoverreceived"/></td>
						<td class="iCargoTableHeaderLabel" width="5%"><common:message key="mailtracking.defaults.pamaster.lbl.uplifted"/></td>
						<td class="iCargoTableHeaderLabel" width="5%"><common:message key="mailtracking.defaults.pamaster.lbl.loaded"/></td>
						<td class="iCargoTableHeaderLabel" width="5%"><common:message key="mailtracking.defaults.pamaster.lbl.assigned"/></td>
						<td class="iCargoTableHeaderLabel" width="5%"><common:message key="mailtracking.defaults.pamaster.lbl.returned"/></td>
						<td class="iCargoTableHeaderLabel" width="10%"><common:message key="mailtracking.defaults.pamaster.lbl.onlinehandover"/></td>
						<td class="iCargoTableHeaderLabel" width="10%"><common:message key="mailtracking.defaults.pamaster.lbl.handovertransfered"/></td>
						<td class="iCargoTableHeaderLabel" width="10%"><common:message key="mailtracking.defaults.pamaster.lbl.pending"/></td>
						<td class="iCargoTableHeaderLabel" width="5%"><common:message key="mailtracking.defaults.pamaster.lbl.readyfordelivery"/></td>
						<td class="iCargoTableHeaderLabel" width="5%"><common:message key="mailtracking.defaults.pamaster.lbl.transportcompleted"/></td>
						<td class="iCargoTableHeaderLabel" width="5%"><common:message key="mailtracking.defaults.pamaster.lbl.mailarrived"/></td>
						<td class="iCargoTableHeaderLabel" width="5%"><common:message key="mailtracking.defaults.pamaster.lbl.delivered"/></td>
					</tr>
				</thead>
				<tbody id="paTableBody">
					<logic:present name="paVO" property="mailEvents">
						<bean:define id="mailEvents" name="paVO" property="mailEvents"/>
						<logic:iterate id ="mailEventVO" name="mailEvents" type="MailEventVO" indexId="rowCount">
							<common:rowColorTag index="rowCount">
								<tr  style="background:<%=color%>">
									<logic:present name="mailEventVO" property="operationFlag">
										<bean:define id="operationFlag" name="mailEventVO" property="operationFlag" toScope="request" />
										<logic:notEqual name="mailEventVO" property="operationFlag" value="D">
											<td class="iCargoTableDataTd ic-center">
												<ihtml:checkbox property="rowId" value="<%=String.valueOf(rowCount)%>"/>
											</td>
											<td class="iCargoTableDataTd">
												<logic:equal name="mailEventVO" property="operationFlag" value="I">
													<ihtml:select name="mailEventVO" property="mailCategory" componentID="COMB_MAILTRACKING_DEFAULTS_PAMASTER_CATEGORY">
														<ihtml:option value=""></ihtml:option>
														<logic:present name="ONETIME_CATEGORY">

															<logic:iterate id="oneTimeCategory" name="ONETIME_CATEGORY" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">

																<logic:present name="oneTimeCategory" property="fieldValue">

																	<bean:define id="fieldDescription" name="oneTimeCategory" property="fieldDescription"/>
																	<bean:define id="fieldValue" name="oneTimeCategory" property="fieldValue"/>


																	<ihtml:option value="<%=(String)fieldValue%>"><%=(String)fieldValue%></ihtml:option>

																</logic:present>

															</logic:iterate>

														</logic:present>

													</ihtml:select>

												</logic:equal>
												<logic:notEqual name="mailEventVO" property="operationFlag" value="I">
													<logic:present name="mailEventVO" property="mailCategory">

														<bean:define id="mailCategory" name="mailEventVO" property="mailCategory"/>
														<logic:present name="ONETIME_CATEGORY">

															<logic:iterate id="oneTimeCategory" name="ONETIME_CATEGORY" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">

																<logic:present name="oneTimeCategory" property="fieldValue">

																	<bean:define id="fieldValue" name="oneTimeCategory" property="fieldValue"/>

																	<logic:equal name="oneTimeCategory" property="fieldValue" value="<%=(String)mailCategory%>" >

																		<bean:write name="oneTimeCategory" property="fieldValue"/>
																		<ihtml:hidden property="mailCategory" name="mailEventVO"/>
																	</logic:equal>

																</logic:present>

															</logic:iterate>

														</logic:present>
													</logic:present>
													<logic:notPresent name="mailEventVO" property="mailCategory">
														<ihtml:text property="mailCategory" name="mailEventVO" readonly="true" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_CLASS"/>
													</logic:notPresent>
												</logic:notEqual>
											</td>
											<td class="iCargoTableDataTd">
												<logic:equal name="mailEventVO" property="operationFlag" value="I">
													<ihtml:text property="mailClass" name="mailEventVO" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_CLASS" maxlength="2"/>
													<div class="lovImgTbl valignT"><img name="classLOV" id="classLOV<%=rowCount%>" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" /></div>
												</logic:equal>
												<logic:notEqual name="mailEventVO" property="operationFlag" value="I">
													<ihtml:text property="mailClass" name="mailEventVO" readonly="true" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_CLASS"/>
												</logic:notEqual>
											</td>
											<td class="iCargoTableDataTd ic-center">

													<input type="checkbox" name="recieved" value="true" <%if(mailEventVO.isReceived()){%>checked<%}%>/>

											</td>
											<td class="iCargoTableDataTd ic-center">

													<input type="checkbox" name="handoverReceived" value="true" <%if(mailEventVO.isHandedOverReceivedResditFlag()){%>checked<%}%>/>

											</td>
											<td class="iCargoTableDataTd ic-center">

													<input type="checkbox" name="uplifted" value="true" <%if(mailEventVO.isUplifted()){%>checked<%}%>/>

											</td>
											<td class="iCargoTableDataTd ic-center">

													<input type="checkbox" name="loaded" value="true" <%if(mailEventVO.isLoadedResditFlag()){%>checked<%}%>/>

											</td>
											<td class="iCargoTableDataTd ic-center">

													<input type="checkbox" name="assigned" value="true" <%if(mailEventVO.isAssigned()){%>checked<%}%>/>

											</td>
											<td class="iCargoTableDataTd ic-center">

													<input type="checkbox" name="returned" value="true" <%if(mailEventVO.isReturned()){%>checked<%}%>/>

											</td>
											<td class="iCargoTableDataTd ic-center">

													<input type="checkbox" name="onlineHandover" value="true" <%if(mailEventVO.isOnlineHandedOverResditFlag()){%>checked<%}%>/>

											</td>
											<td class="iCargoTableDataTd ic-center">

													<input type="checkbox" name="handedOver" value="true" <%if(mailEventVO.isHandedOver()){%>checked<%}%>/>

											</td>
											<td class="iCargoTableDataTd ic-center">

													<input type="checkbox" name="pending" value="true" <%if(mailEventVO.isPending()){%>checked<%}%>/>

											</td>
											<!-- Added by A-5201 -->
											<td class="iCargoTableDataTd ic-center">
													<input type="checkbox" name="readyForDelivery" value="true" <%if(mailEventVO.isReadyForDelivery()){%>checked<%}%>/>
											</td>
											<td class="iCargoTableDataTd ic-center">
													<input type="checkbox" name="transportationCompleted" value="true" <%if(mailEventVO.isTransportationCompleted()){%>checked<%}%>/>
											</td>
											<td class="iCargoTableDataTd ic-center">
													<input type="checkbox" name="arrived" value="true" <%if(mailEventVO.isArrived()){%>checked<%}%>/>
											</td>
											<td class="iCargoTableDataTd ic-center">

													<input type="checkbox" name="delivered" value="true" <%if(mailEventVO.isDelivered()){%>checked<%}%>/>

											</td>

										</logic:notEqual>
										<logic:equal name="mailEventVO" property="operationFlag" value="D">
											<ihtml:hidden property="mailCategory" name="mailEventVO"/>
											<ihtml:hidden property="mailClass" name="mailEventVO"/>
											<ihtml:hidden property="recieved"/>
											<ihtml:hidden property="handoverReceived"/>
											<ihtml:hidden property="loaded"/>
											<ihtml:hidden property="onlineHandover"/>
											<ihtml:hidden property="uplifted"/>
											<ihtml:hidden property="assigned"/>
											<ihtml:hidden property="returned"/>
											<ihtml:hidden property="handedOver"/>
											<ihtml:hidden property="pending"/>
											<ihtml:hidden property="readyForDelivery"/>
											<ihtml:hidden property="transportationCompleted"/>
											<ihtml:hidden property="arrived"/>																		
											<ihtml:hidden property="delivered"/>
										</logic:equal>

										<ihtml:hidden property="operationFlag" value="<%=((String)operationFlag)%>" />
									</logic:present>
									<logic:notPresent name="mailEventVO" property="operationFlag">
										<td class="iCargoTableDataTd ic-center">

												<ihtml:checkbox property="rowId" value="<%=String.valueOf(rowCount)%>"/>

										</td>
										<td class="iCargoTableDataTd">

										
											<logic:present name="mailEventVO" property="mailCategory">
												<bean:define id="mailCategory" name="mailEventVO" property="mailCategory"/>
														<ihtml:select  componentID="COMB_MAILTRACKING_DEFAULTS_PAMASTER_CATEGORY"  property="mailCategory"    value="<%=mailCategory.toString()%>"  disabled="true">
												<logic:present name="ONETIME_CATEGORY">

													<logic:iterate id="oneTimeCategory" name="ONETIME_CATEGORY" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">

														<logic:present name="oneTimeCategory" property="fieldValue">

															<bean:define id="fieldValue" name="oneTimeCategory" property="fieldValue"/>
<bean:define id="fieldDescription" name="oneTimeCategory" property="fieldDescription"/>
															
<ihtml:option value="<%=(String)fieldValue%>"><%=(String)fieldValue%></ihtml:option>
																
														

														</logic:present>

													</logic:iterate>

												</logic:present>
														</ihtml:select>
											</logic:present>
										
											<logic:notPresent name="mailEventVO" property="mailCategory">
<ihtml:text property="mailCategory" name="mailEventVO" readonly="true" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_CLASS"/>
											</logic:notPresent>

																									
																	
										</td>
										<td class="iCargoTableDataTd">

											<ihtml:text property="mailClass" name="mailEventVO" readonly="true" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_CLASS"/>
<div class="lovImgTbl valignT"><img name="classLOV"  src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" /></div>
										</td>
										<td class="iCargoTableDataTd ic-center">
												<input type="checkbox" name="recieved" value="true" <%if(mailEventVO.isReceived()){%>checked<%}%>/>

										</td>
										<td class="iCargoTableDataTd ic-center">
												<input type="checkbox" name="handoverReceived" value="true" <%if(mailEventVO.isHandedOverReceivedResditFlag()){%>checked<%}%>/>

										</td>
										<td class="iCargoTableDataTd ic-center">

												<input type="checkbox" name="uplifted" value="true" <%if(mailEventVO.isUplifted()){%>checked<%}%>/>

										</td>
										<td class="iCargoTableDataTd ic-center">

												<input type="checkbox" name="loaded" value="true" <%if(mailEventVO.isLoadedResditFlag()){%>checked<%}%>/>

										</td>
										<td class="iCargoTableDataTd ic-center">

												<input type="checkbox" name="assigned" value="true" <%if(mailEventVO.isAssigned()){%>checked<%}%>/>

										</td>
										<td class="iCargoTableDataTd ic-center">

												<input type="checkbox" name="returned" value="true" <%if(mailEventVO.isReturned()){%>checked<%}%>/>

										</td>
										<td class="iCargoTableDataTd ic-center">

												<input type="checkbox" name="onlineHandover" value="true" <%if(mailEventVO.isOnlineHandedOverResditFlag()){%>checked<%}%>/>

										</td>
										<td class="iCargoTableDataTd ic-center">

												<input type="checkbox" name="handedOver" value="true" <%if(mailEventVO.isHandedOver()){%>checked<%}%>/>

										</td>
										<td class="iCargoTableDataTd ic-center">

												<input type="checkbox" name="pending" value="true" <%if(mailEventVO.isPending()){%>checked<%}%>/>

										</td>
										<!-- Added by A-5201 -->
										<td class="iCargoTableDataTd ic-center">
												<input type="checkbox" name="readyForDelivery" value="true" <%if(mailEventVO.isReadyForDelivery()){%>checked<%}%>/>
										</td>
										<td class="iCargoTableDataTd ic-center">
												<input type="checkbox" name="transportationCompleted" value="true" <%if(mailEventVO.isTransportationCompleted()){%>checked<%}%>/>
										</td>
										<td class="iCargoTableDataTd ic-center">
												<input type="checkbox" name="arrived" value="true" <%if(mailEventVO.isArrived()){%>checked<%}%>/>
										</td>
										<td class="iCargoTableDataTd ic-center">

												<input type="checkbox" name="delivered" value="true" <%if(mailEventVO.isDelivered()){%>checked<%}%>/>

										</td>
										<ihtml:hidden property="operationFlag" value="U" />
									</logic:notPresent>
								</tr>
							</common:rowColorTag>
						</logic:iterate>
					</logic:present>




				<!-- templateRow -->
				<tr template="true" id="paTemplateRow" style="display:none">
					<ihtml:hidden property="operationFlag" value="NOOP" />
					<td class="iCargoTableDataTd ic-center">

						  <input type="checkbox" name="rowId">
					</td>
					<td class="iCargoTableDataTd">

						<ihtml:select name="mailEventVO" property="mailCategory" componentID="COMB_MAILTRACKING_DEFAULTS_PAMASTER_CATEGORY" value="">
						<ihtml:option value=""></ihtml:option>
						<logic:present name="ONETIME_CATEGORY">

							<logic:iterate id="oneTimeCategory" name="ONETIME_CATEGORY" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">

								<logic:present name="oneTimeCategory" property="fieldValue">

									<bean:define id="fieldDescription" name="oneTimeCategory" property="fieldDescription"/>
									<bean:define id="fieldValue" name="oneTimeCategory" property="fieldValue"/>


									<ihtml:option value="<%=(String)fieldValue%>"><%=(String)fieldValue%></ihtml:option>

								</logic:present>

							</logic:iterate>

						</logic:present>

						</ihtml:select>

					</td>
					<td class="iCargoTableDataTd">
						<ihtml:text property="mailClass" componentID="TXT_MAILTRACKING_DEFAULTS_PAMASTER_CLASS" maxlength="2" value=""/>
						<div class="lovImgTbl valignT"><img name="classLOV" id="classLOV" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" onClick="displayLOV('mailtracking.defaults.subclaslov.list.do','N','Y','mailtracking.defaults.subclaslov.list.do',targetFormName.mailClass.value,'mailClass','1','mailClass','',0)"/></div>

					</td>
					<td class="iCargoTableDataTd ic-center">

							<input type="checkbox" name="recieved" value="" />

					</td>
					<td class="iCargoTableDataTd ic-center">

							<input type="checkbox" name="handoverReceived" value="" />

					</td>
					<td class="iCargoTableDataTd ic-center">

							<input type="checkbox" name="uplifted" value="" />

					</td>
					<td class="iCargoTableDataTd ic-center">

							<input type="checkbox" name="loaded" value="" />

					</td>
					<td class="iCargoTableDataTd ic-center">

							<input type="checkbox" name="assigned" value="" />

					</td>
					<td class="iCargoTableDataTd ic-center">

							<input type="checkbox" name="returned" value="" />

					</td>
					<td class="iCargoTableDataTd ic-center">

							<input type="checkbox" name="onlineHandover" value="" />

					</td>
					<td class="iCargoTableDataTd ic-center">

							<input type="checkbox" name="handedOver" value="" />

					</td>
					<td class="iCargoTableDataTd ic-center">

							<input type="checkbox" name="pending" value="" />

					</td>
					<!--A-5201-->
					<td class="iCargoTableDataTd ic-center">
							<input type="checkbox" name="readyForDelivery" value="" />
					</td>
					<td class="iCargoTableDataTd ic-center">
							<input type="checkbox" name="transportationCompleted" value="" />
					</td>
					<td class="iCargoTableDataTd ic-center">
							<input type="checkbox" name="arrived" value="" />
					</td>
					<td class="iCargoTableDataTd ic-center">

							<input type="checkbox" name="delivered" value="" />

					</td>
				</tr>
				<!--template row ends-->

				</tbody>
			</table>
		</div>