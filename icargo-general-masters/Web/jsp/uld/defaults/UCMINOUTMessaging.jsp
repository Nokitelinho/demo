<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  IN - ULD Management
* File Name				:  UCMINOUTMessaging.jsp
* Date					:  19-Jul-2006
* Author(s)				:  Pradeep S
*************************************************************************/
 --%>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.UCMINOUTForm"%>

<%@ include file="/jsp/includes/tlds.jsp" %>

<html:html locale="true">
<head>
	
	 <%@ include file="/jsp/includes/customcss.jsp" %>
<title>iCargo:Send UCM IN/OUT Message</title>
<meta name="decorator" content="mainpanel" >
<common:include type="script" src="/js/uld/defaults/UCMINOUTMessaging_Script.jsp"/>

</head>
	<body id="bodyStyle">
	
	<bean:define id="form"
	 name="ucmInOutForm"
	 type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.UCMINOUTForm"
	 toScope="page" />

<div class="iCargoContent ic-masterbg" style="width:100%;">
<ihtml:form action="uld.defaults.ucminout.screenloaducminoutmessaging.do">
<business:sessionBean id="flightValidationVO"
moduleName="uld.defaults"
screenID="uld.defaults.ucminoutmessaging"
method="get" attribute="flightValidationVOSession" />

<business:sessionBean id="reconcileVO"
moduleName="uld.defaults"
screenID="uld.defaults.ucminoutmessaging"
method="get" attribute="messageReconcileVO" />

<business:sessionBean id="contentTypes"
moduleName="uld.defaults"
screenID="uld.defaults.ucminoutmessaging"
method="get" attribute="contentType" />

<business:sessionBean id="stations"
moduleName="uld.defaults"
screenID="uld.defaults.ucminoutmessaging"
method="get" attribute="destinations" />

<business:sessionBean id="destinations"
moduleName="uld.defaults"
screenID="uld.defaults.ucminoutmessaging"
method="get" attribute="outDestinations" />

<business:sessionBean id="oneTimeValues"
moduleName="uld.defaults"
screenID="uld.defaults.ucminoutmessaging"
method="get" attribute="oneTimeValues" />
<business:sessionBean id="messageStatus"
moduleName="uld.defaults"
screenID="uld.defaults.ucminoutmessaging"
method="get" attribute="messageStatus" />
<ihtml:hidden property="duplicateFlightStatus"/>
<ihtml:hidden property="viewUldStatus"/>
<ihtml:hidden property="linkStatus"/>
<ihtml:hidden property="damagedStatus"/>
<input type="hidden" name="currentDialogId" />
<input type="hidden" name="currentDialogOption" />
<ihtml:hidden property="outConfirmStatus"/>
<ihtml:hidden property="ucmVOStatus" />
<ihtml:hidden property="pouStatus" />
<ihtml:hidden property="messageTypeStatus" />
<ihtml:hidden property="orgDestStatus" />
<ihtml:hidden property="ucmBlockStatus" />
<ihtml:hidden property="addStatus" />
<ihtml:hidden property="isUldViewed" />
<ihtml:hidden property="isUcmSent" />
<div class="ic-content-main">
		<span class="ic-page-title"><common:message key="uld.defaults.ucminout.pagetitle" scope="request"/></span>
            <div class="ic-head-container">
				<div class="ic-filter-panel">
					<div class="ic-input-container">
					<div class="ic-row">
						<div class="ic-section marginT5">
							<div class="ic-input ic-split-20 "></div>
							<div class="ic-input ic-split-30 inline_filedset marginT5">
						<ihtml:radio property="ucmOut"  value="OUT"  componentID="CMP_ULD_DEFAULTS_UCMINOUT_RADIOUCMOUT" onchange="valueChange()"/>
							<label style="margin-top:3px"><common:message key="uld.defaults.ucminout.ucmout" scope="request"/></label>
                     </div>
					 <div class="ic-input ic-split-20 "></div>
					<div class="ic-input ic-split-30 inline_filedset marginT5">
						<ihtml:radio property="ucmOut" value="IN" componentID="CMP_ULD_DEFAULTS_UCMINOUT_RADIOUCMIN" onchange="valueChange()"/></td>
						<label style="margin-top:3px"><common:message key="uld.defaults.ucminout.ucmin" scope="request"/></label>
                    </div>
                  </div>
                </div>
            <div class="ic-row">
                <h4><common:message key="uld.defaults.ucminout.flightdet" scope="request"/></h4>
            </div>
           <div class="ic-row">
				<div class="ic-section ic-border ">
					<div class="ic-input ic-split-50 ic-mandatory booked_flight">
						
		  							<%String carrierCode=(String)form.getCarrierCode();%>
		  							<%String flightNo=(String)form.getFlightNo();%>
		  							<%String flightDate=(String)form.getFlightDate();%>

		  								<ibusiness:flight
		  								id="flightNo"
		  								carrierCodeMaxlength="3"
		  								flightCodeMaxlength="5"
		  								carrierCodeProperty="carrierCode"
		  								flightCodeProperty="flightNo"
		  								calendarProperty="flightDate"
		  								carriercodevalue="<%=(String)carrierCode%>"
		  								flightcodevalue="<%=(String)flightNo%>"
		  								calendarValue="<%=(String)flightDate%>"
		  								carrierCodeStyleClass="iCargoTextFieldVerySmall"
		  								flightCodeStyleClass="iCargoTextFieldSmall"
		  								type="image"
		  								componentID="CMP_ULD_DEFAULTS_UCMINOUT_FLIGHT" />
		  		              </div>
					<div class="ic-input ic-split-25 ic-mandatory">
						<label>	<common:message key="uld.defaults.ucminout.origin" scope="request"/></label>
					<ihtml:text componentID="CMP_ULD_DEFAULTS_UCMINOUT_ORIGIN" property="origin" name="form" style="text-transform : uppercase" maxlength="3" />
					<div class="lovImg">
					<img src="<%= request.getContextPath()%>/images/lov.png" width="22" height="22" border="0" onclick="displayLOV('showAirport.do','N','Y','showAirport.do',document.forms[1].origin.value,'Origin','1','origin','',0)" alt="Airport LOV"/>
					</div>
				      </div>	
				<div class="ic-input ic-split-25">
					<div class="ic-button-container paddR5">	
		  						<ihtml:nbutton property="btnList"  componentID="BTN_ULD_DEFAULTS_UCMINOUT_LIST" accesskey="L">
		  									<common:message key="uld.defaults.ucminout.list" scope="request"/>
		  						</ihtml:nbutton>
		  					  <ihtml:nbutton property="btnClear" componentID="BTN_ULD_DEFAULTS_UCMINOUT_CLEAR" accesskey="C">
		  									 <common:message key="uld.defaults.ucminout.clear" scope="request"/>
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
					<div class="ic-input ic-split-100">
					<div id="FLIGHTDETAILS"  >


                    <logic:present name="flightValidationVO">
							<common:displayFlightStatus flightStatusDetails="flightValidationVO" />
					</logic:present>
					<logic:notPresent name="flightValidationVO">
					<table style="width:100%" class="iCargoBorderLessTable" cellspacing="0px" cellpadding="0px">
					 					</table>
					</logic:notPresent>
					</div>
					</div>
				</div>



			<tr>
			<div class="ic-row marginT5">
                  <div class="ic-section ic-border ">
				  <div class="ic-input ic-split-25 ic-label-45">
				  </div>
					<div class="ic-input ic-split-25 ic-label-45">
                           <label><common:message key="uld.defaults.ucminout.destn" scope="request"/></label>
			                    <ihtml:select property="destination"  styleClass="iCargoMediumComboBox">
			                     <logic:present name="destinations">
			                     	<ihtml:option value="ALL">
			                     	</ihtml:option>
									<ihtml:options name="destinations" />
								 </logic:present>
								 </ihtml:select>
			        </div>
					<div class="ic-input ic-split-40 ic-label-45">
					<span style="padding-left:1px"><common:message key="uld.defaults.ucminout.totaluldinflight" scope="request"/><bean:write name="reconcileVO" property="totalUldCount" />
					<span style="padding-left:10px"><common:message key="uld.defaults.ucminout.ucmreportedulds" scope="request"/> <bean:write name="reconcileVO" property="ucmReportedCount" />
                    <span style="padding-left:10px"><common:message key="uld.defaults.ucminout.manuallyaddeduls" scope="request"/> <bean:write name="reconcileVO" property="manualUldCount" />
					</div>
				<div class="ic-input ic-split-50 ic-label-45">
					<div class="ic-button-container">	
					 <ihtml:nbutton property="btnView" componentID="BTN_ULD_DEFAULTS_UCMINOUT_VIEWULDS" accesskey="V">
						<common:message key="uld.defaults.ucminout.viewulds" scope="request"/>
				</ihtml:nbutton>
				</div>
                  </div>                
			</div>
			</div>
			<div id="ucmOutTable"class="ic-row">
			<div class="ic-row">
			<div class="ic-input ic-split-80  marginT5">
				<h4><common:message key="uld.defaults.ucminout.ulds" scope="request"/></h4>
			</div>
			<div class="ic-input ic-split-20">
				<div class="ic-button-container">	
				<a href="#" class="iCargoLink" id="addUld" name="addUld" onclick="addDetails()">
				<common:message key="uld.defaults.ucminout.add" scope="request"/>
				</a>
				<a href="#" class="iCargoLink" id="deleteUld" name="deleteUld" onclick="deleteDetails()">
				<common:message key="uld.defaults.ucminout.delete" scope="request"/>
				</a>
				</div>
			</div>
			

				<jsp:include page="UCMINOUTMessaging_OUTTable.jsp"/>	
			</div>	 
			</div>
			<!--Added by A-7359 for ICRD-259943 starts here 
				Please use version tree to find all changes for this bug -->			
            <div id="ucmInTable"class="ic-row" style="display:none;">
			<div class="ic-row">
			<div class="ic-input ic-split-80  marginT5">
				<h4><common:message key="uld.defaults.ucminout.ulds" scope="request"/></h4>
			</div>
			<div class="ic-input ic-split-20">
				<div class="ic-button-container">	
				<a href="#" class="iCargoLink" id="addInUld" name="addInUld" onclick="addInDetails()">
				<common:message key="uld.defaults.ucminout.add" scope="request"/>
				</a>
				<a href="#" class="iCargoLink" id="deleteInUld" name="deleteInUld" onclick="deleteInDetails()">
				<common:message key="uld.defaults.ucminout.delete" scope="request"/>
				</a>
				</div>
			</div>
			<!--Added by A-7359 for ICRD-259943 ends here-->	
				<div id="div1" class="tableContainer" style="height:330px;">
					<table class="fixed-header-table" id="UCMINOUTMessaging">
						   <thead>
                            <tr class="iCargoTableHeadingLeft">
							     <td width="5%" >
      <input name="masterRowId" type="checkbox" value="checkbox"/>
                              </td>
                              <td width="20%"  ><common:message key="uld.defaults.ucminout.uldno" scope="request"/><span class="iCargoMandatoryFieldIcon">*</span></td>
							  <td width="30%"  ><common:message key="uld.defaults.ucminout.dmg" scope="request"/></td>
							<td width="25%"  ><common:message key="uld.defaults.ucminout.content" scope="request"/></td>
                              <td width="20%" ><common:message key="uld.defaults.ucminout.destn" scope="request"/></td>
							 </tr>
                          </thead>
<tbody id ="ucminbody">
<%int index1 = 0;%>
                           <logic:present name="reconcileVO" property="reconcileDetailsVOs" >
							   <bean:define id="reconcileDetails" name="reconcileVO"  property="reconcileDetailsVOs"/>
							    <logic:notEqual name="messageStatus" value="OUT">
								<logic:iterate id="reconcileDetailsVO" name="reconcileDetails" indexId="nIndex" type="com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileDetailsVO">
								<common:rowColorTag index="nIndex">
							   <logic:present name="reconcileDetailsVO" property="operationFlag">
		<bean:define id="flag" name="reconcileDetailsVO" property="operationFlag"/>
	<logic:equal name="flag" value="I">
                            <tr>
                             <logic:present name="reconcileDetailsVO" property="operationFlag">
								<ihtml:hidden property="operationFlag" value="<%=reconcileDetailsVO.getOperationFlag()%>"/>
								</logic:present>
								<logic:notPresent name="reconcileDetailsVO" property="operationFlag">
									<ihtml:hidden property="operationFlag" value="NA"/>
								 </logic:notPresent>
								 <logic:present name="reconcileDetailsVO" property="errorCode" >
								 <ihtml:hidden property="errorCodes" indexId="nIndex" styleId="errorCode" value="<%=reconcileDetailsVO.getErrorCode()%>" />
								 </logic:present>
								 <logic:notPresent name="reconcileDetailsVO" property="errorCode" >
								 <ihtml:hidden property="errorCodes"  indexId="nIndex" styleId="errorCode" value="" />
								 </logic:notPresent>
		<td  class="iCargoTableDataTd">
		<center>
		<!--<html:checkbox property="selectedRows" value="<%=String.valueOf(index1)%>" />-->
		<input type="checkbox" name="selectedRows" value="<%=String.valueOf(index1)%>" />
		<ihtml:hidden property="hiddenOpFlag" value="I"/>
		</center>
		</td>
	        <td  class="iCargoTableDataTd" align="center">
	        <center>
                              <logic:present name="reconcileDetailsVO" property="uldNumber">
                             	<logic:present name="reconcileDetailsVO" property="sequenceNumber">
                             		<bean:define id="seqNumber" name="reconcileDetailsVO" property="sequenceNumber" />
                             			<logic:match name="seqNumber" value="0">
											<ibusiness:uld id="uldno" styleClass="iCargoEditableTextFieldRowColor1" uldProperty="uldNumbers" uldValue="<%=reconcileDetailsVO.getUldNumber()%>" componentID="CMP_ULD_DEFAULTS_UCMINOUT_ULDNUMBER" style="width:130px;text-transform: uppercase" maxlength="12"/>
                             			</logic:match>
                             			<logic:notMatch name="seqNumber" value="0">
											<ibusiness:uld id="uldno" styleClass="iCargoEditableTextFieldRowColor1" uldProperty="uldNumbers" uldValue="<%=reconcileDetailsVO.getUldNumber()%>" componentID="CMP_ULD_DEFAULTS_UCMINOUT_ULDNUMBER" style="width:130px;text-transform: uppercase" maxlength="12" readonly="true"/>
                             			</logic:notMatch>
                             	</logic:present>
                             	<logic:notPresent name="reconcileDetailsVO" property="sequenceNumber">
									<ibusiness:uld id="uldno" styleClass="iCargoEditableTextFieldRowColor1" uldProperty="uldNumbers" uldValue="<%=reconcileDetailsVO.getUldNumber()%>" componentID="CMP_ULD_DEFAULTS_UCMINOUT_ULDNUMBER" style="width:130px;text-transform: uppercase" maxlength="12"/>
                             	</logic:notPresent>
                                </logic:present>
                              <logic:notPresent name="reconcileDetailsVO" property="uldNumber" >
								<ibusiness:uld id="uldno" uldProperty="uldNumbers" styleClass="iCargoEditableTextFieldRowColor1" componentID="CMP_ULD_DEFAULTS_UCMINOUT_ULDNUMBER" style="width:130px;text-transform: uppercase" maxlength="12"/>
			</logic:notPresent>
		</center>
                              </td>
		<td  class="iCargoTableTd">
		<center>
                                <logic:present name="reconcileDetailsVO" property="damageStatus">
										<bean:define id="dmgStatus" name="reconcileDetailsVO" property="damageStatus"/>
											<logic:equal name="dmgStatus" value="Y">
												<input type="checkbox" name="damageCodes" checked value="<%=String.valueOf(nIndex)%>" />
											</logic:equal>
											<logic:notEqual name="dmgStatus" value="Y">
												<input type="checkbox" name="damageCodes" value="<%=String.valueOf(nIndex)%>"  />
											</logic:notEqual>
											</logic:present>
											<logic:notPresent name="reconcileDetailsVO" property="damageStatus">
											<input type="checkbox" name="damageCodes" value="<%=String.valueOf(nIndex)%>"  />
										</logic:notPresent>
		</center>
		</td>
							  <td class="iCargoTableTd">
								  <ihtml:select property="contentInd" styleClass="iCargoMediumComboBox" value="<%=reconcileDetailsVO.getContent()%>">
												<logic:present name="contentTypes">
													<bean:define id="contents" name="contentTypes"/>
													<ihtml:options collection="contents" property="fieldValue" labelProperty="fieldDescription"/>
												</logic:present>
										</ihtml:select>
							 </td>
		<td  class="iCargoTableDataTd">
							  <logic:present name="reconcileDetailsVO" property="pou">
							  	 <ihtml:select property="pou" value="<%=reconcileDetailsVO.getPou()%>" styleClass="iCargoMediumComboBox">
							  	 <logic:present name="stations">
							  	 	<ihtml:options name="stations" />
							  	 </logic:present>
							  	 </ihtml:select>
							   </logic:present>
                                <logic:notPresent name="reconcileDetailsVO" property="pou">
									 <ihtml:select property="pou"  styleClass="iCargoMediumComboBox">
										 <logic:present name="stations">
										<ihtml:options name="stations" />
										</logic:present>
							  	 </ihtml:select>
			</logic:notPresent>
                              </td>
                            </tr>
    </logic:equal>
    <logic:equal name="flag" value="D">
                            <ihtml:hidden property="uldNumbers" value="<%=reconcileDetailsVO.getUldNumber()%>" />
                            <ihtml:hidden property="pou" value="<%=reconcileDetailsVO.getPou()%>" />
                            <ihtml:hidden property="operationFlag" value="D"/>
                            <ihtml:hidden property="contentInd" value=""/>
                            <logic:present name="reconcileDetailsVO" property="errorCode" >
								 <ihtml:hidden property="errorCodes" indexId="nIndex" styleId="errorCode" value="<%=reconcileDetailsVO.getErrorCode()%>" />
						 </logic:present>
    </logic:equal>
</logic:present>
	<logic:notPresent name="reconcileDetailsVO" property="operationFlag">
	<tr bgcolor="<%=color%>">
		<td  class="iCargoTableTd">
		<html:checkbox property="selectedRows" value="<%=String.valueOf(index1)%>" />
		<ihtml:hidden property="hiddenOpFlag" value="U"/>
		</td>
		<td  class="iCargoTableDataTd" align="center">
		<center>
			<logic:present name="reconcileDetailsVO" property="uldNumber">
				<logic:present name="reconcileDetailsVO" property="sequenceNumber">
				<bean:define id="seqNumber" name="reconcileDetailsVO" property="sequenceNumber" />
					<logic:match name="seqNumber" value="0">
						<ibusiness:uld id="uldno" uldProperty="uldNumbers" styleClass="iCargoEditableTextFieldRowColor1" uldValue="<%=reconcileDetailsVO.getUldNumber()%>" componentID="CMP_ULD_DEFAULTS_UCMINOUT_ULDNUMBER" style="width:130px;text-transform: uppercase" maxlength="12"/>
                            </logic:match>
					<logic:notMatch name="seqNumber" value="0">
						<ibusiness:uld id="uldno" uldProperty="uldNumbers" styleClass="iCargoEditableTextFieldRowColor1" uldValue="<%=reconcileDetailsVO.getUldNumber()%>" componentID="CMP_ULD_DEFAULTS_UCMINOUT_ULDNUMBER" style="width:130px;text-transform: uppercase" maxlength="12" readonly="true"/>
					</logic:notMatch>
				</logic:present>
				<logic:notPresent name="reconcileDetailsVO" property="sequenceNumber">
					<ibusiness:uld id="uldno" uldProperty="uldNumbers" styleClass="iCargoEditableTextFieldRowColor1" uldValue="<%=reconcileDetailsVO.getUldNumber()%>" componentID="CMP_ULD_DEFAULTS_UCMINOUT_ULDNUMBER" style="width:130px;text-transform: uppercase" maxlength="12"/>
				</logic:notPresent>
			</logic:present>
			<logic:notPresent name="reconcileDetailsVO" property="uldNumber" >
				<ibusiness:uld id="uldno" uldProperty="uldNumbers" styleClass="iCargoEditableTextFieldRowColor1" componentID="CMP_ULD_DEFAULTS_UCMINOUT_ULDNUMBER" style="width:130px;text-transform: uppercase" maxlength="12"/>
			</logic:notPresent>
		</center>
		</td>
		<td  class="iCargoTableTd">
		<center>
			<logic:present name="reconcileDetailsVO" property="damageStatus">
			<bean:define id="dmgStatus" name="reconcileDetailsVO" property="damageStatus"/>
				<logic:equal name="dmgStatus" value="Y">
					<input type="checkbox" name="damageCodes" checked value="<%=String.valueOf(nIndex)%>" />
				</logic:equal>
				<logic:notEqual name="dmgStatus" value="Y">
					<input type="checkbox" name="damageCodes" value="<%=String.valueOf(nIndex)%>"  />
				</logic:notEqual>
			</logic:present>
			<logic:notPresent name="reconcileDetailsVO" property="damageStatus">
				<input type="checkbox" name="damageCodes" value="<%=String.valueOf(nIndex)%>"  />
			</logic:notPresent>
		</center>
		</td>
		<td class="iCargoTableDataTd">
		<ihtml:select property="contentInd" styleClass="iCargoMediumComboBox" value="<%=reconcileDetailsVO.getContent()%>">
			<logic:present name="contentTypes">
				<bean:define id="contents" name="contentTypes"/>
				<ihtml:options collection="contents" property="fieldValue" labelProperty="fieldDescription"/>
			</logic:present>
		</ihtml:select>
		</td>
		<td  class="iCargoTableDataTd">
			<logic:present name="reconcileDetailsVO" property="pou">
				 <ihtml:select property="pou" value="<%=reconcileDetailsVO.getPou()%>" styleClass="iCargoMediumComboBox">
				 <logic:present name="stations">
					<ihtml:options name="stations" />
				 </logic:present>
				 </ihtml:select>
			</logic:present>
			<logic:notPresent name="reconcileDetailsVO" property="pou">
				<ihtml:select property="pou"  styleClass="iCargoMediumComboBox">
				 <logic:present name="stations">
					<ihtml:options name="stations" />
				</logic:present>
			</ihtml:select>
			</logic:notPresent>
		</td>
	 </tr>
	</logic:notPresent>
                            </common:rowColorTag>
    <%index1++;%>
							</logic:iterate>
							   </logic:notEqual>
							</logic:present>
<!-- templateRow -->
<tr template="true" id="ucminTemplateRow" style="display:none">
	<td  class="iCargoTableTd">
	<center>
		<html:checkbox property="selectedRows" value="<%=String.valueOf(index1)%>" />
		<ihtml:hidden property="hiddenOpFlag" value="NOOP"/>
	</center>
	</td>
	<td  class="iCargoTableDataTd" align="center">
	<center>
		<ibusiness:uld id="uldno" uldProperty="uldNumbers" uldValue="" styleClass="iCargoEditableTextFieldRowColor1" componentID="CMP_ULD_DEFAULTS_UCMINOUT_ULDNUMBER" style="width:130px;text-transform: uppercase" maxlength="12"/>
	</center>
	</td>
	<td  class="iCargoTableTd">
	<center>
		<input type="checkbox" name="damageCodes" value="" />
	</center>
	</td>
	<td class="iCargoTableDataTd">
	<ihtml:select property="contentInd" styleClass="iCargoMediumComboBox" value="X">
		<logic:present name="contentTypes">
			<bean:define id="contents" name="contentTypes"/>
			<ihtml:options collection="contents" property="fieldValue" labelProperty="fieldDescription"/>
		</logic:present>
	</ihtml:select>
	</td>
	<td  class="iCargoTableDataTd">
		<ihtml:select property="pou"  styleClass="iCargoMediumComboBox">
		 <logic:present name="stations">
			<ihtml:options name="stations" />
		</logic:present>
		</ihtml:select>
		 <ihtml:hidden property="uldSource" value="" />
		  <ihtml:hidden property="uldStatus" value="" />
	</td>
</tr>
<!-- template Row over -->
                          </tbody>
                        </table>
                      </div>
					  </div>
					  </div>
                 <div class="ic-row marginB5">
				<div class="ic-section ic-border">
					<div class="ic-input ic-split-100 ">
				  <label><common:message key="uld.defaults.ucminout.si" scope="request"/></label>
				    <!-- Modified as part of bug ICRD-238949 -->
				    <logic:present name="reconcileVO" property="specialInformation" >
					<bean:define id="information" name="reconcileVO" property="specialInformation" />
						<ihtml:textarea property="ucmIn" value= "<%=(String)information%>" style="text-transform: uppercase"  componentID="TXT_ULD_DEFAULTS_UCMINOUT_SI" rows="3" cols="140"/>
					</logic:present>
					<logic:notPresent name="reconcileVO" property="specialInformation" >
					  <ihtml:textarea property="ucmIn" componentID="TXT_ULD_DEFAULTS_UCMINOUT_SI" rows="3" cols="140"/>
					</logic:notPresent>
					<!-- Modified as part of bug ICRD-238949 ends-->
				  </div>
				  </div>
				  </div>

		</div>
    </div>
		<div class="ic-foot-container">
		<div class="ic-row">
			<div class="ic-button-container paddR5">
			  <ihtml:nbutton property="btnSend" componentID="BTN_ULD_DEFAULTS_UCMINOUT_SEND" accesskey="S">
							 <common:message key="uld.defaults.ucminout.send" scope="request"/>
  				</ihtml:nbutton>

  				 <ihtml:nbutton property="btnClose" componentID="BTN_ULD_DEFAULTS_UCMINOUT_CLOSE" accesskey="O">
						<common:message key="uld.defaults.ucminout.close" scope="request"/>
				</ihtml:nbutton>

  				</div>
            </div>
    </div>


        </ihtml:form>
        </div>

				
			

		

		
	</body>
</html:html>

