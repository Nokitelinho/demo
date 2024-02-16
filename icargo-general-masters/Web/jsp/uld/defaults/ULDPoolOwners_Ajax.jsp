<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  IN - ULD Management
* File Name				:  ULDPoolOwners.jsp
* Date					:  11-Aug-2006
* Author(s)				:  Pradeep S
*************************************************************************/
 --%>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ULDPoolOwnersForm"%>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>

<ihtml:form action="uld.defaults.screenloaduldpoolowners.do">
<ihtml:hidden property="linkStatus"/>
<ihtml:hidden property="flag"/>
<ihtml:hidden property="popupFlag"/>
<ihtml:hidden property="selectedRow"/>
<ihtml:hidden property="hiddenOperationFlag" />
<business:sessionBean id="poolOwnerVO"
	moduleName="uld.defaults"
	screenID="uld.defaults.uldpoolowners"
	method="get" attribute="UldPoolOwnerVO" />

 					  <div id="uldPoolOwnerChilddiv" >
							<div class="tableContainer" id="div1" style="height: 750px" >
								<table  width="100%" class="fixed-header-table" id="uldPoolOwnerTable" >
								  <thead>
									<tr class="iCargoTableHeadingLeft">
									 <td width="3%"> <input type="checkbox" name="masterRowId" onclick="updateHeaderCheckBox(this.form,this.form.masterRowId,this.form.selectedRows)"/></td>
									  <td width="17%"  ><common:message key="uld.defaults.uldpoolowners.airline1" scope="request"/><span class="iCargoMandatoryFieldIcon">*</span></td>
									  <td width="17%"  ><common:message key="uld.defaults.uldpoolowners.airline2" scope="request"/><span class="iCargoMandatoryFieldIcon">*</span></td>
									  <td width="17%"  ><common:message key="uld.defaults.uldpoolowners.airport" scope="request"/></td>
									  <td width="46%" ><common:message key="uld.defaults.uldpoolowners.remarks" scope="request"/></td>
								   </tr>
								  </thead>
              
						  <tbody id="ULDPoolTableBody" >
							<logic:present name="poolOwnerVO">
							<%int i=0;%>
							  <bean:define id="pooldetails" name="poolOwnerVO"/>
							  <%System.out.println("pool size"+pooldetails);%>
								<logic:iterate id="poolOwnerVOs" name="pooldetails" indexId="nIndex" type="com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDPoolOwnerVO">
							
								<logic:present name="poolOwnerVOs" property="operationFlag">
								<bean:define id="opFlag" name="poolOwnerVOs" property="operationFlag"/>
				 				</logic:present>
								<logic:notPresent name="poolOwnerVOs" property="operationFlag">
							   <bean:define id="opFlag"  value="NA"/>
							   </logic:notPresent>
							   
							    <logic:notMatch name="opFlag" value="D">
								
								<tr>
                             <logic:present name="poolOwnerVOs" property="operationFlag">
								<ihtml:hidden property="operationFlag" value="<%=poolOwnerVOs.getOperationFlag()%>"/>
								<ihtml:hidden property="hiddenOperationFlag" value="<%=poolOwnerVOs.getOperationFlag()%>" />
							 </logic:present>

							 <logic:notPresent name="poolOwnerVOs" property="operationFlag">
								<ihtml:hidden property="operationFlag" value="NA"/>
								<ihtml:hidden property="hiddenOperationFlag" value="NA" />
							 </logic:notPresent>

											<td class="ic-center"> <ihtml:checkbox property="selectedRows"  onclick="toggleTableHeaderCheckbox('selectedRows',this.form.masterRowId)" value="<%=String.valueOf(nIndex)%>" /></td>
											<td  class="iCargoTableDataTd " > 
											<div class=" ic-center ic-input">
												<logic:present name="poolOwnerVOs" property="airlineOne">

											<logic:match name="opFlag" value="I">
											<ihtml:text styleClass="iCargoEditableTextFieldRowColor1" maxlength="3"  indexId="nIndex" styleId="airlineOne" property="airlineOne"  componentID="TXT_ULD_DEFAULTS_POOLAJX_AIRLINE1"   value="<%=poolOwnerVOs.getAirlineOne()%>" style="width:60px" />
										    <div class="lovImgTbl">
										    <img  id="selectairline<%=nIndex.toString()%>"
										        src="<%=request.getContextPath()%>/images/lov.png"
										        width="16" height="16" alt="Airline LOV"
													onclick="viewAirlineLov('selectairline',this)" /></div>
											</logic:match>
											<logic:notMatch name="opFlag" value="I">
											<ihtml:text styleClass="iCargoEditableTextFieldRowColor1" maxlength="3"  indexId="nIndex" styleId="airlineOne" property="airlineOne" componentID="TXT_ULD_DEFAULTS_POOLAJX_AIRLINE1"  readonly="true"  value="<%=poolOwnerVOs.getAirlineOne()%>" style="width:60px" />
										    <div class="lovImgTbl">
										    <img  id="selectairlinedisabled<%=nIndex.toString()%>"
										        src="<%=request.getContextPath()%>/images/lov.png"
										        width="16" height="16" alt="Airline LOV" /></div>
											</logic:notMatch>

									          </logic:present>
							  		          <logic:notPresent name="poolOwnerVOs" property="airlineOne" >
											  <logic:match name="opFlag" value="I">
											<ihtml:text styleClass="iCargoEditableTextFieldRowColor1" maxlength="3"  indexId="nIndex" styleId="airlineOne" property="airlineOne" componentID="TXT_ULD_DEFAULTS_POOLAJX_AIRLINE1"   value="<%=poolOwnerVOs.getAirlineOne()%>" style="width:60px" />
										    <div class="lovImgTbl">
										    <img  id="selectairline<%=nIndex.toString()%>"
										        src="<%=request.getContextPath()%>/images/lov.png"
										        width="16" height="16" alt="Airline LOV"
													onclick="viewAirlineLov('selectairline',this)" /></div>
											</logic:match>
											<logic:notMatch name="opFlag" value="I">
											<ihtml:text styleClass="iCargoEditableTextFieldRowColor1" maxlength="3"  indexId="nIndex" styleId="airlineOne" property="airlineOne" componentID="TXT_ULD_DEFAULTS_POOLAJX_AIRLINE1"  readonly="true"  value="<%=poolOwnerVOs.getAirlineOne()%>" style="width:60px" />
										    <div class="lovImgTbl">
										    <img id="selectairline<%=nIndex.toString()%>"
										        src="<%=request.getContextPath()%>/images/lov.png"
										        width="16" height="16" alt="Airline LOV" /></div>
											</logic:notMatch>
							  				     <div class="lovImgTbl">
							  				     <img name="selectairlinedisabled" id="selectairlinedisabled<%=nIndex.toString()%>"
											     src="<%=request.getContextPath()%>/images/lov.png"
										         width="16" height="16"  alt="Airline LOV"/></div>
									          </logic:notPresent>
							  		        </div>
							                </td>

											<td  class="iCargoTableDataTd" > 
											<div class=" ic-center ic-input">
											<logic:present name="poolOwnerVOs" property="airlineTwo">
											<logic:match name="opFlag" value="I">
											<ihtml:text styleClass="iCargoEditableTextFieldRowColor1" maxlength="3"  indexId="nIndex" styleId="airlineTwo" property="airlineTwo" componentID="TXT_ULD_DEFAULTS_POOLAJX_AIRLINE2" value="<%=poolOwnerVOs.getAirlineTwo()%>" style="width:60px" onblur="populateAirport(this);" />
											<div class="lovImgTbl">
											<img  id="selectairlineone<%=nIndex.toString()%>"
										        src="<%=request.getContextPath()%>/images/lov.png"
										        width="16" height="16" alt="Airline LOV" 
												onclick="viewAirlineLovone('selectairlineone',this)"/></div>
										    </logic:match>
											<logic:notMatch name="opFlag" value="I">
											<ihtml:text styleClass="iCargoEditableTextFieldRowColor1" maxlength="3"  indexId="nIndex" styleId="airlineTwo" property="airlineTwo" componentID="TXT_ULD_DEFAULTS_POOLAJX_AIRLINE2"  readonly="true"  value="<%=poolOwnerVOs.getAirlineTwo()%>" style="width:60px"  onblur="populateAirport(this);"/>
											<div class="lovImgTbl">
											<img  id="selectairlineonedisabled<%=nIndex.toString()%>"
										        src="<%=request.getContextPath()%>/images/lov.png"
										        width="16" height="16" alt="Airline LOV" /></div>
										    </logic:notMatch>
							  			 	</logic:present>
											<logic:notPresent name="poolOwnerVOs" property="airlineTwo" >
											 <logic:match name="opFlag" value="I">
											<ihtml:text styleClass="iCargoEditableTextFieldRowColor1" maxlength="3"  indexId="nIndex" styleId="airlineTwo" property="airlineTwo" componentID="TXT_ULD_DEFAULTS_POOLAJX_AIRLINE2" value="<%=poolOwnerVOs.getAirlineTwo()%>" style="width:60px" onblur="populateAirport(this);"/>
										    <div class="lovImgTbl">
										    <img  id="selectairlineone<%=nIndex.toString()%>"
										        src="<%=request.getContextPath()%>/images/lov.png"
										        width="16" height="16" alt="Airline LOV" 
												onclick="viewAirlineLovone('selectairlineone',this)"/></div>
										    </logic:match>
											<logic:notMatch name="opFlag" value="I">
											<ihtml:text styleClass="iCargoEditableTextFieldRowColor1" maxlength="3"  indexId="nIndex" styleId="airlineTwo" property="airlineTwo" componentID="TXT_ULD_DEFAULTS_POOLAJX_AIRLINE2"  readonly="true"  value="<%=poolOwnerVOs.getAirlineTwo()%>" style="width:60px" onblur="populateAirport(this);" />
										    <div class="lovImgTbl">
										    <img  id="selectairlineonedisabled<%=nIndex.toString()%>"
										        src="<%=request.getContextPath()%>/images/lov.png"
										        width="16" height="16" alt="Airline LOV" /></div>
										    </logic:notMatch>
							  				</logic:notPresent>
                                             </div>
							                </td>

											<td class="iCargoTableDataTd" >
											<div class=" ic-center ic-input">											
											 <logic:present name="poolOwnerVOs" property="airport">
											 <logic:match name="opFlag" value="I">
											<ihtml:text styleClass="iCargoEditableTextFieldRowColor1" maxlength="3"  indexId="nIndex" styleId="airport" property="airport" componentID="TXT_ULD_DEFAULTS_POOLAJX_AIRPORT"  value="<%=poolOwnerVOs.getAirport()%>" style="width:60px" />
											<div class="lovImgTbl">
											<img id="airportLovImg<%=nIndex.toString()%>"
												src="<%= request.getContextPath()%>/images/lov.png" 
												width="16" height="16" alt="Airport LOV"
												onclick="viewAirport('airportLovImg',this)"/></div>
										    </logic:match>
											<logic:notMatch name="opFlag" value="I">
											<ihtml:text styleClass="iCargoEditableTextFieldRowColor1" maxlength="3"  indexId="nIndex" styleId="airport" property="airport" componentID="TXT_ULD_DEFAULTS_POOLAJX_AIRPORT"  readonly="true"   value="<%=poolOwnerVOs.getAirport()%>" style="width:60px" />
											<div class="lovImgTbl">
											<img id="airportLovImgDisabled<%=nIndex.toString()%>"
												src="<%= request.getContextPath()%>/images/lov.png" 
												width="16" height="16" alt="Airport LOV"/></div>
										    </logic:notMatch>
											</logic:present>
											 <logic:notPresent name="poolOwnerVOs" property="airport" >
											 <logic:match name="opFlag" value="I">
											<ihtml:text styleClass="iCargoEditableTextFieldRowColor1" maxlength="3"  indexId="nIndex" styleId="airport" property="airport" componentID="TXT_ULD_DEFAULTS_POOLAJX_AIRPORT"  value="<%=poolOwnerVOs.getAirport()%>" style="width:60px" />
											<div class="lovImgTbl">
											<img id="airportLovImg<%=nIndex.toString()%>" 
												src="<%= request.getContextPath()%>/images/lov.png" 
												width="16" height="16" alt="Airport LOV"
												onclick="viewAirport('airportLovImg',this)"/></div>
										    </logic:match>
											<logic:notMatch name="opFlag" value="I">
											<ihtml:text styleClass="iCargoEditableTextFieldRowColor1" maxlength="3"  indexId="nIndex" styleId="airport" property="airport" componentID="TXT_ULD_DEFAULTS_POOLAJX_AIRPORT"  readonly="true"   value="" style="width:60px" />
											<div class="lovImgTbl">
											<img id="airportLovImgDisabled<%=nIndex.toString()%>" 
												src="<%= request.getContextPath()%>/images/lov.png" 
												width="16" height="16" alt="Airport LOV"/></div>
										    </logic:notMatch>
										    </logic:notPresent>
											</div>
											</td>

											<td class="iCargoTableDataTd" > 
											<div class=" ic-center ic-input">
											  <logic:present name="poolOwnerVOs" property="remarks">
											  <ihtml:text styleClass="iCargoEditableTextFieldRowColor1" maxlength="500"  indexId="nIndex" styleId="remarks" property="remarks"   value="<%=poolOwnerVOs.getRemarks()%>" style="width:300px" />
										     </logic:present>
											 <logic:notPresent name="poolOwnerVOs" property="remarks" >
											  <ihtml:text styleClass="iCargoEditableTextFieldRowColor1" maxlength="500" property="remarks" indexId="nIndex" styleId="remarks"  value="" style="width:300px" />
											</logic:notPresent>
											</div>
								            </td>
										</tr>
										
									</logic:notMatch>
									 <logic:match name="opFlag" value="D">
										<ihtml:hidden property="airport" indexId="nIndex" styleId="airport"  value="<%=poolOwnerVOs.getAirport()%>" />
										<ihtml:hidden property="remarks" indexId="nIndex"  styleId="remarks" value="<%=poolOwnerVOs.getRemarks()%>" />
										<ihtml:hidden property="airlineOne" />
										<ihtml:hidden property="airlineTwo" />
										<ihtml:hidden property="operationFlag" value="D"/>
										<ihtml:hidden property="hiddenOperationFlag" value="D" />
									</logic:match>
								
								</logic:iterate>
							</logic:present>

				         </tbody>
                        </table>
					  </div>
					  </div>
						
					</ihtml:form>
				<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>

