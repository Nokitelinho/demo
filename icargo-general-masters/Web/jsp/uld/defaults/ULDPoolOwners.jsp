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

<html:html locale="true">
<head>
		
	
<title><bean:message bundle="uldPoolOwnersResources" key="uld.defaults.uldpoolowners.title" /></title>
<meta name="decorator" content="mainpanelrestyledui" >
<common:include type="script" src="/js/uld/defaults/ULDPoolOwners_Script.jsp"/>

</head>
	<body class="ic-center" style="width:65%;">
	
	
	<bean:define id="form"
	 name="ULDPoolOwnersForm"
	 type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ULDPoolOwnersForm"
	 toScope="page" />

<div class="iCargoContent" id="pageDiv" >

<ihtml:form action="uld.defaults.screenloaduldpoolowners.do">


<business:sessionBean id="poolOwnerVO"
	moduleName="uld.defaults"
	screenID="uld.defaults.uldpoolowners"
	method="get" attribute="UldPoolOwnerVO" />

<ihtml:hidden property="linkStatus"/>
<ihtml:hidden property="flag"/>
<ihtml:hidden property="popupFlag"/>
<ihtml:hidden property="selectedRow"/>
<ihtml:hidden property="hiddenOperationFlag" />

<input type="hidden" name="currentDialogId" />
<input type="hidden" name="currentDialogOption" />

   	   <div class="ic-content-main">
			<span class="ic-page-title"><common:message key="uld.defaults.uldpoolowners.pagetitle" scope="request"/></td></span>
				<div class="ic-head-container">
					<div class="ic-filter-panel">
						<div class="ic-input-container">
							<div class="ic-row">
								<div class="ic-section ic-border">
									<div class="ic-col-50">
										 <fieldset class="ic-field-set">
										 <legend><common:message key="uld.defaults.uldpoolowners.participatingairlines" /></legend>
										   <div class="ic-row ic-label-45">
												<div class="ic-input ic-split-50">
													<label><common:message key="uld.defaults.uldpoolowners.airlines" /></label>
													<ihtml:text property="firstAirline"  tabindex="1" maxlength="3" componentID="TXT_ULD_DEFAULTS_POOLOWN_AIRLINE1"  />
													<div class="lovImg">
													<img  id="firstairlinelov"
													src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" alt="Airline LOV" />
												</div>
												</div>
												<div class="ic-input ic-split-50 marginT15">
													 <ihtml:text property="secondAirline"  tabindex="2" maxlength="3" componentID="TXT_ULD_DEFAULTS_POOLOWN_AIRLINE2"  />
													 <div class="lovImg">
													 <img  id="secondairlinelov"
													  src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" alt="Airline LOV" />
													</div>
												</div>
											</div>
										</fieldset>
									</div>
					
									<div class="ic-col-25">
										<div class="ic-row ic-label-45 marginT20">
											<div class="ic-input ic-split-100">
												<label><common:message key="uld.defaults.uldpoolowners.airport" /></label>
												<ihtml:text property="polAirport" tabindex="3" componentID="TXT_ULD_DEFAULTS_AIRPORT" maxlength="3" />
												<div class="lovImg">
												<img  id="airportlov"
												src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" alt="Airport LOV" />
											</div>
										</div>
									</div>
									</div>
									<div class="ic-col-25 marginT20">
										<div class="ic-button-container paddR5  ">	
										<ihtml:nbutton property="btnList" tabindex="4" componentID="BTN_ULD_DEFAULTS_POOLOWN_LIST" accesskey="L">
											<common:message key="uld.defaults.uldpoolowners.list" scope="request"/>
										</ihtml:nbutton>
										<ihtml:nbutton property="btnClear" tabindex="5" componentID="BTN_ULD_DEFAULTS_POOLOWN_CLEAR" accesskey="C">
										  <common:message key="uld.defaults.uldpoolowners.clear" scope="request"/>
										</ihtml:nbutton>
									</div>
								</div>
							</div>
						</div>
					</div>
				 </div>
				 </div>
				<div class="ic-main-container">
					<div class="ic-row">
						<h4>
							<common:message key="uld.defaults.uldpoolowners.details" scope="request"/>
						</h4>
					</div>
					<div class="ic-button-container paddR5">
						<a href="#" id="addPool" name="addPool" class="iCargoLink"><common:message key="uld.defaults.uldpoolowners.add" scope="request"/> </a> | 
						<a href="#" id="deletePool" name="deletePool" class="iCargoLink"><common:message key="uld.defaults.uldpoolowners.delete" scope="request"/></a>
					</div>
						 
                   <div class="ic-row">
						  <div id = "uldPoolOwnerParentdiv">
							<div class="tableContainer"  id="div1" style="height: 550px" >
								<table class="fixed-header-table" id="uldPoolOwnerTable" >
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
													onclick="viewAirlineLov('selectairline',this)" />
													</div>
											</logic:match>
											<logic:notMatch name="opFlag" value="I">
											<ihtml:text styleClass="iCargoEditableTextFieldRowColor1" maxlength="3"  indexId="nIndex" styleId="airlineOne" property="airlineOne" componentID="TXT_ULD_DEFAULTS_POOLAJX_AIRLINE1"  readonly="true"  value="<%=poolOwnerVOs.getAirlineOne()%>" style="width:60px" />
										    <div class="lovImgTbl">
										    <img  id="selectairlinedisabled<%=nIndex.toString()%>"
										        src="<%=request.getContextPath()%>/images/lov.png"
										        width="16" height="16" alt="Airline LOV" />
										</div>	
											</logic:notMatch>

									          </logic:present>
							  		          <logic:notPresent name="poolOwnerVOs" property="airlineOne" >
											  <logic:match name="opFlag" value="I">
											<ihtml:text styleClass="iCargoEditableTextFieldRowColor1" maxlength="3"  indexId="nIndex" styleId="airlineOne" property="airlineOne" componentID="TXT_ULD_DEFAULTS_POOLAJX_AIRLINE1"   value="<%=poolOwnerVOs.getAirlineOne()%>" style="width:60px" />
										    <div class="lovImgTbl">
										    <img  id="selectairline<%=nIndex.toString()%>"
										        src="<%=request.getContextPath()%>/images/lov.png"
										        width="16" height="16" alt="Airline LOV"
													onclick="viewAirlineLov('selectairline',this)" />
													</div>
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

											<td  class="iCargoTableDataTd " > 
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

											<td class="iCargoTableDataTd " > 
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

											<td class="iCargoTableDataTd " > 
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
			 </div>
			  </div>

       <div class="ic-foot-container">
				<div class="ic-button-container paddR5">
				<ihtml:nbutton property="btnSegmentException" componentID="BTN_ULD_DEFAULTS_ULDPOOLOWN_SEGMENTEXCEPTION" accesskey="G">
							 <common:message key="uld.defaults.uldpoolowners.segmentexception" scope="request"/>
  				</ihtml:nbutton>

                 <ihtml:nbutton property="btnSave" componentID="BTN_ULD_DEFAULTS_ULDPOOLOWN_SAVE" accesskey="S">
							 <common:message key="uld.defaults.uldpoolowners.save" scope="request"/>
  				</ihtml:nbutton>

  				 <ihtml:nbutton property="btnClose" componentID="BTN_ULD_DEFAULTS_ULDPOOLOWN_CLOSE" accesskey="O">
						<common:message key="uld.defaults.uldpoolowners.close" scope="request"/>
				</ihtml:nbutton>

  				</div>
         </div>
		 </div>
		

        </ihtml:form>
        </div>

		
	</body>
</html:html>

