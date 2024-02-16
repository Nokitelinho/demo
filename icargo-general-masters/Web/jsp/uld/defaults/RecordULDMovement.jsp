   <%--
    * Project	 		: iCargo
    * Module Code & Name:  ULD Management
    * File Name			: RecordULDMovement.jsp
    * Date				: 31-Jan-2006
    * Author(s)			: A-1936
    --%>
   <%
	response.setDateHeader("Expires",0);
	response.setHeader("Pragma","no-cache");

	if (request.getProtocol().equals("HTTP/1.1")) { 
		response.setHeader("Cache-Control","no-cache");
	}
 %>
<%@ page language="java" %>
<%@ page import="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO"%>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.RecordULDMovementForm"%>
<%@ page import="com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementVO"%>
<%@ include file="/jsp/includes/tlds.jsp" %>

<html:html locale="true">


<head>
	
<title>
   <common:message bundle="recordULDMovementResources" key="uld.defaults.recordULDMovement.lbl.pagetitle" />
 </title>


 <logic:equal name="recordULDMovementForm" property="screenName" value="screenLoad">
   <meta name="decorator" content="mainpanelrestyledui"/>
  </logic:equal>

  <logic:notEqual name="recordULDMovementForm" property="screenName" value="screenLoad">
      <bean:define id="popup" value="true" />
      <meta name="decorator" content="popuppanelrestyledui"/>
  </logic:notEqual>

 <common:include type="script" src="/js/uld/defaults/RecordULDMovement_Script.jsp"/>


</head>

<body onload="stripedTable(); addIEonScroll();" style="overflow:auto">

  <bean:define id="form"
   			 name="recordULDMovementForm"
   			 type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.RecordULDMovementForm"
 			 toScope="page" />

 <business:sessionBean id="oneTimeContent"
 			moduleName="uld.defaults"
 			screenID="uld.defaults.misc.recorduldmovement"
 			method="get"
 			attribute="oneTimeContent" />


 <business:sessionBean id="uldMovement"
 					   moduleName="uld.defaults"
 					   screenID="uld.defaults.misc.recorduldmovement"
 					   method="get"
 					   attribute="ULDMovementVOs"/>


 <business:sessionBean id="uldNumber"
 					   moduleName="uld.defaults"
 					   screenID="uld.defaults.misc.recorduldmovement"
 					   method="get"
 					   attribute="ULDNumbers"/>



 <logic:present name="oneTimeContent">
   <bean:define id="oneTimeContent" name="oneTimeContent" />
 </logic:present>


 <logic:present name="oneTimeContent">
 	<bean:define id="oneTimeContent" name="oneTimeContent" />
 </logic:present>



  <logic:notEqual name="recordULDMovementForm" property="screenName" value="screenLoad">
          <div class="iCargoPopUpContent "  style="width:100%;height:100%;overflow:auto;">
  </logic:notEqual>

  <logic:equal name="recordULDMovementForm" property="screenName" value="screenLoad">
      <div class="iCargoContent ic-masterbg"  style="width:100%;height:100%;overflow:auto;">
  </logic:equal>

 <ihtml:form action="uld.defaults.misc.screenloadrecorduldmovement.do" styleclass="ic-main-form">
 <input type="hidden" name="flag" />
  <ihtml:hidden property="discrepancyDate"/>
   <ihtml:hidden property="discrepancyCode"/>
  <ihtml:hidden property="flagForCheck"/>
   <ihtml:hidden property="flagForUldDiscrepancy"/>
  <ihtml:hidden property="discrepancyStatus"/>
 <ihtml:hidden property="screenName"/>
 <ihtml:hidden property="pageurl"/>
 <ihtml:hidden property="messageStatus"/>
 <ihtml:hidden property="dummyCheckedIndex"/>
 <ihtml:hidden property="errorFlag"/>
 <ihtml:hidden property="overrideError"/>


<input type="hidden" name="currentDialogOption" />
<input type="hidden" name="currentDialogId" />
	<div class="ic-content-main  bg-white">
			
			<div class="ic-main-container">
				<div class="ic-row marginT5">
				<div class="ic-col-75">
				<fieldset class="ic-field-set">
					<legend>
						<!--Flight Details -->
						<common:message bundle="recordULDMovementResources" key="uld.defaults.recordULDMovement.flightdetails" />
					</legend>
	      					     
				<div class="ic-row">
					<div class="ic-button-container">
						<a href="#" id="addFlight" name="addFlight" class="iCargoLink"><common:message  key="uld.defaults.recordULDMovement.lbl.add" /> </a> |
						<a href="#" id="deleteFlight" name="deleteFlight" class="iCargoLink"><common:message key="uld.defaults.recordULDMovement.lbl.delete" /></a> &nbsp;
					</div>
				</div>
				<div class="ic-row ic-pad-3">
				<!--Added by A-7426 as part of ICRD-197739 starts-->
					<logic:notEqual name="recordULDMovementForm" property="screenName" value="screenLoad">
						<div id="div1" class="tableContainer" style="height: 350px">
					  </logic:notEqual>
					  <logic:equal name="recordULDMovementForm" property="screenName" value="screenLoad">
				    <div id="div1" class="tableContainer" style="height: 600px">
					  </logic:equal>
				<!--Added by A-7426 as part of ICRD-197739 ends-->
					<table  class="fixed-header-table">
					<thead>
					  <tr class="iCargoTableHeadingLeft" >
						<td class="iCargoTableHeader" rowspan="2" width="5%"><input type="checkbox" name="allChecked" value="checkbox" onclick="updateHeaderCheckBox(this.form,this.form.allChecked,this.form.checkForDelete)"/></td>
						<td class="iCargoTableHeader" colspan="2" width="20%" >
							 <common:message  key="uld.defaults.recordULDMovement.lbl.flight" />
						 </td>

						<td class="iCargoTableHeader" width="20%"  rowspan="2">
							<common:message  key="uld.defaults.recordULDMovement.lbl.flightdate" /><span class="iCargoMandatoryFieldIcon">*</span>
						</td>

						<td class="iCargoTableHeader" width="10%"  rowspan="2">
							<common:message  key="uld.defaults.recordULDMovement.lbl.dummymovement" />
						</td>

						<td  class="iCargoTableHeader" width="13%"  rowspan="2">
							 <common:message  key="uld.defaults.recordULDMovement.lbl.pol" /><span class="iCargoMandatoryFieldIcon">*</span>
						</td>

						<td class="iCargoTableHeader" width="13%"  rowspan="2">
							<common:message  key="uld.defaults.recordULDMovement.lbl.pou" /><span class="iCargoMandatoryFieldIcon">*</span>
						</td>

						<td class="iCargoTableHeader" width="20%" rowspan="2">
							<common:message  key="uld.defaults.recordULDMovement.lbl.content" /><span class="iCargoMandatoryFieldIcon">*</span>

						</td>

					  </tr>
					  <tr >
						<td class="iCargoTableHeader" width="11%" >
							  <common:message  key="uld.defaults.recordULDMovement.lbl.carriercode" /><span class="iCargoMandatoryFieldIcon">*</span>
						</td>
						<td class="iCargoTableHeader" width="9%" >
							  <common:message  key="uld.defaults.recordULDMovement.lbl.no" /><span class="iCargoMandatoryFieldIcon">*</span>
						</td>

					  </tr>
					</thead>
														<tbody id ="adduldmovementbody">
										<%int count = 0;%>
										 <logic:present  name="uldMovement">
											<bean:define id="uldMovement" name="uldMovement" />
											   <logic:iterate id="vo" name="uldMovement" indexId="index" type="ULDMovementVO">
											 
										<tr>
										<td  class="iCargoTableDataTd ic-center">
											<ihtml:hidden property="hiddenOpFlag" value="I"/>
											<html:checkbox property="checkForDelete" value="<%=String.valueOf(count)%>" onclick="toggleTableHeaderCheckbox('checkForDelete',this.form.allChecked)"/>
										</td>
										<td  class="iCargoTableDataTd ic-center">
											<ihtml:text styleId="carrierCode" indexId="index" name="vo" property="carrierCode" componentID="TXT_ULD_DEFAULTS_RECORDULD_CARRIER_CODE"  maxlength="3"   style="width:30px; background : #FFFFCC;"/>
											<logic:notEqual name="recordULDMovementForm" property="screenName" value="screenLoad">
											<div class="lovImgTbl valignT">
												<img src="<%=request.getContextPath()%>/images/lov_sm.png" onclick="displayLOV('showAirline.do','N','Y','showAirline.do',recordULDMovementForm.carrierCode.value,'CarrierCode','0','carrierCode','',<%=(String.valueOf(count))%>);" alt="Airline LOV" width="16" height="16"/>
											</div>
											</logic:notEqual>
											<logic:equal name="recordULDMovementForm" property="screenName" value="screenLoad">
											<div class="lovImgTbl valignT">
												<img src="<%=request.getContextPath()%>/images/lov_sm.png" onclick="displayLOV('showAirline.do','N','Y','showAirline.do',document.forms[1].carrierCode.value,'CarrierCode','1','carrierCode','',<%=(String.valueOf(count))%>);" alt="Airline LOV" width="16" height="16"/>
												</div>
											</logic:equal>
										</td>
										<td class="iCargoTableDataTd ic-center" >
											<ihtml:text styleId="flightNumber" indexId="index"  name="vo" property="flightNumber" componentID="TXT_ULD_DEFAULTS_RECORDULD_FLIGHTNO"  maxlength="8"  style="width:60px; background : #FFFFCC;"/>
										</td>
										<td class="iCargoTableDataTd ic-center" >
											<ibusiness:calendar id="flightDate" property="flightDateString" indexId="index" componentID="TXT_ULD_DEFAULTS_RECORDULD_DATE" value="<%=(String)vo.getFlightDateString()%>" type="image" style="background : #FFFFCC;" />
										</td>
										<td  class="iCargoTableDataTd ic-center">
										<div style="ic-center">
											 <bean:define id="check" name="vo" property="dummyMovement"/>
											 <logic:equal name="check" value="true">
													<input type="checkbox" name="dummyMovement"  value="<%=String.valueOf(count)%>" checked="checked" />
											 </logic:equal>
											 <logic:notEqual name="check" value="true">
													<input type="checkbox" name="dummyMovement"  value="<%=String.valueOf(count)%>" />
											 </logic:notEqual>
										</div>
										</td>

										<td  class="iCargoTableDataTd ic-center" >
										   <ihtml:text styleId="pointOfLading"  indexId="index" name="vo"    property="pointOfLading" componentID="TXT_ULD_DEFAULTS_RECORDULD_POL"   style="width:30px; background : #FFFFCC;" maxlength="3"/>
											<logic:notEqual name="recordULDMovementForm" property="screenName" value="screenLoad">
											<div class="lovImgTbl valignT">
										   <img src="<%=request.getContextPath()%>/images/lov_sm.png" width="16" height="16" onclick="displayLOV('showAirport.do','N','Y','showAirport.do',recordULDMovementForm.pointOfLading.value,'POL','0','pointOfLading','',<%=(String.valueOf(count))%>);" alt="Airport LOV"/>
										   </div>
										   </logic:notEqual>
											<logic:equal name="recordULDMovementForm" property="screenName" value="screenLoad">
											<div class="lovImgTbl valignT" >
												<img src="<%=request.getContextPath()%>/images/lov_sm.png" width="16" height="16" onclick="displayLOV('showAirport.do','N','Y','showAirport.do',document.forms[1].pointOfLading.value,'POL','1','pointOfLading','',<%=(String.valueOf(count))%>);" alt="Airport LOV"/>
												</div>
										   </logic:equal>
										</td>

										<td  class="iCargoTableDataTd ic-center" >
										  <ihtml:text  styleId="pointOfUnLading" indexId="index" name="vo"   property="pointOfUnLading" componentID="TXT_ULD_DEFAULTS_RECORDULD_CARRIER_POU" maxlength="3"  style="width:30px; background : #FFFFCC;"/>
										  <logic:notEqual name="recordULDMovementForm" property="screenName" value="screenLoad">
										  <div class="lovImgTbl valignT">
												<img src="<%=request.getContextPath()%>/images/lov_sm.png" width="16" height="16" onclick="displayLOV('showAirport.do','N','Y','showAirport.do',recordULDMovementForm.pointOfUnLading.value,'POU','0','pointOfUnLading','',<%=(String.valueOf(count))%>);" alt="Airport LOV"/>
												</div>
										  </logic:notEqual>

										  <logic:equal name="recordULDMovementForm" property="screenName" value="screenLoad">
										  <div class="lovImgTbl valignT">
											<img src="<%=request.getContextPath()%>/images/lov_sm.png" width="16" height="16" onclick="displayLOV('showAirport.do','N','Y','showAirport.do',document.forms[1].pointOfUnLading.value,'POU','1','pointOfUnLading','',<%=(String.valueOf(count))%>);" alt="Airport LOV"/>
											</div>
										  </logic:equal>

										</td>

										<td class="iCargoTableDataTd ic-center">
										<ihtml:select name="vo" property="content" componentID="TXT_ULD_DEFAULTS_RECORDULD_CONTENT" style="background : #FFFFCC;">
										  <logic:present name="oneTimeContent">
												 <logic:iterate id="msgsta_it" name="oneTimeContent">
													 <bean:define id="fieldVal" name="msgsta_it" property="fieldValue" />
														 <html:option value="<%=(String)fieldVal%>">
														   <bean:write name="msgsta_it" property="fieldDescription" />
														</html:option>
												 </logic:iterate>
											</logic:present>
										</ihtml:select>
										</td>
	                                    </tr>
	                                    <%count++;%>
	                                    
	      				               </logic:iterate>
	      						     </logic:present>

										<!-- templateRow -->
										<tr template="true" id="adduldmovementTemplateRow" style="display:none">
											<ihtml:hidden property="hiddenOpFlag" value="NOOP"/>
											<td class="iCargoTableDataTd ic-center" >
												<html:checkbox property="checkForDelete" value="<%=String.valueOf(count)%>" onclick="toggleTableHeaderCheckbox('checkForDelete',this.form.allChecked)"/>
											</td>
											<td  class="iCargoTableDataTd ic-center">
												<ihtml:text styleId="carrierCode" value="" indexId="index" property="carrierCode" componentID="TXT_ULD_DEFAULTS_RECORDULD_CARRIER_CODE"  maxlength="3"   style="width:30px;"/>
												<logic:notEqual name="recordULDMovementForm" property="screenName" value="screenLoad">
												<div class="lovImgTbl valignT">
													<img width="14" height="14" id="airlineLov" src="<%=request.getContextPath()%>/images/lov_sm.png" onclick="showAirlineLov(this)" alt="Airline LOV"/>
												</div>
												</logic:notEqual>
												<logic:equal name="recordULDMovementForm" property="screenName" value="screenLoad">
												<div class="lovImgTbl valignT">
													<img width="14" height="14" id="airlineScreenloadLov" src="<%=request.getContextPath()%>/images/lov_sm.png" onclick="showAirlineScreenloadLov(this)" alt="Airline LOV"/>
												</div>
												</logic:equal>
											</td>
												<td class="iCargoTableDataTd ic-center" >
												<ihtml:text styleId="flightNumber" value="" indexId="index" property="flightNumber" componentID="TXT_ULD_DEFAULTS_RECORDULD_FLIGHTNO"  maxlength="5"  style="width:60px;"/>
											</td>
												<td class="iCargoTableDataTd ic-center" >
												<ibusiness:calendar id="flightDate" property="flightDateString" indexId="index" componentID="TXT_ULD_DEFAULTS_RECORDULD_DATE" value="" type="image" />
												</td>

											<td  class="iCargoTableDataTd ic-center">
												<div style="ic-center">
													<input type="checkbox" name="dummyMovement"  value="" />
												</div>
											</td>

											<td  class="iCargoTableDataTd ic-center" >
												   <ihtml:text styleId="pointOfLading" value="" indexId="index" property="pointOfLading" componentID="TXT_ULD_DEFAULTS_RECORDULD_POL"   style="width:30px;" maxlength="3"/>
												<logic:notEqual name="recordULDMovementForm" property="screenName" value="screenLoad">
												<div class="lovImgTbl valignT">
													<img width="14" height="14" id="airportLov" src="<%=request.getContextPath()%>/images/lov_sm.png" onclick="showAirportLov(this)" alt="Airport LOV" alt="Airport LOV"/>
												</div>	
												</logic:notEqual>
												<logic:equal name="recordULDMovementForm" property="screenName" value="screenLoad">
												<div class="lovImgTbl valignT">
													<img width="14" height="14" id="airportScreenloadLov" src="<%=request.getContextPath()%>/images/lov_sm.png" onclick="showAirportScreenloadLov(this)" alt="Airport LOV"/>
												</div>	
												</logic:equal>
											</td>

												<td  class="iCargoTableDataTd ic-center" >
												  <ihtml:text  styleId="pointOfUnLading" value="" indexId="index"  property="pointOfUnLading" componentID="TXT_ULD_DEFAULTS_RECORDULD_CARRIER_POU" maxlength="3"  style="width:30px;"/>
												  <logic:notEqual name="recordULDMovementForm" property="screenName" value="screenLoad">
												  <div class="lovImgTbl valignT">
													<img width="14" height="14" id="airportpouLov" src="<%=request.getContextPath()%>/images/lov_sm.png" onclick="showAirportPOULov(this)" alt="Airport LOV"/>
												</div>	
												  </logic:notEqual>

												  <logic:equal name="recordULDMovementForm" property="screenName" value="screenLoad">
												  <div class="lovImgTbl valignT">
													<img width="14" height="14" id="airportpouScreenloadLov" src="<%=request.getContextPath()%>/images/lov_sm.png" onclick="showAirportPOUScreenloadLov(this)" alt="Airport LOV"/>
													</div>
												  </logic:equal>

												</td>

											  <td class="iCargoTableDataTd ic-center">
												 <ihtml:select value="" property="content" componentID="TXT_ULD_DEFAULTS_RECORDULD_CONTENT">
												 <logic:present name="oneTimeContent">
													 <logic:iterate id="msgsta_it" name="oneTimeContent">
														 <bean:define id="fieldVal" name="msgsta_it" property="fieldValue" />
															 <html:option value="<%=(String)fieldVal%>">
															   <bean:write name="msgsta_it" property="fieldDescription" />
															</html:option>
													 </logic:iterate>
												</logic:present>
												</ihtml:select>
											 </td>
										</tr>
										<!-- template Row over -->


	                                 </tbody>
								

					</table>
					</div>
				</div>
					
				</fieldset>
				</div>
				<div class="ic-col-25">
				<fieldset class="ic-field-set">
					<legend>
						<!--ULD Details -->
						<common:message bundle="recordULDMovementResources" key="uld.defaults.recordULDMovement.ulddetails" />
					</legend>
				<div class="ic-row">
					<div class="ic-button-container paddR5">
					 <a href="#" id="addLink" name="addLink" class="iCargoLink">
	                                   <common:message  key="uld.defaults.recordULDMovement.lbl.addflight" />
	      						     </a> 
									 |
	      						   <a href="#" id="deleteLink" name="deleteLink" class="iCargoLink">
	      						      <common:message  key="uld.defaults.recordULDMovement.lbl.deleteflight" />
	      						    </a>
	      					             
				</div>
				</div>
				<div class="ic-row ic-pad-3">
				<!--Added by A-7426 as part of ICRD-197739 starts-->
				    <logic:notEqual name="recordULDMovementForm" property="screenName" value="screenLoad">
						<div id="div1" class="tableContainer" style="height: 350px">
					  </logic:notEqual>
					  <logic:equal name="recordULDMovementForm" property="screenName" value="screenLoad">
				    <div id="div1" class="tableContainer" style="height: 600px">
					  </logic:equal>
				<!--Added by A-7426 as part of ICRD-197739 ends-->
					<table  class="fixed-header-table">
					<thead>
	                    <tr class="iCargoTableHeadingLeft">
						<td width="10%">
							 <input name="allCheck" type="checkbox" />
						</td>

						<td >
						<common:message  key="uld.defaults.recordULDMovement.lbl.uldno" /><span class="iCargoMandatoryFieldIcon">*</span>
						</td>
	                    </tr>
	                </thead>
					<tbody id ="adduldnumberbody">
						<%int countUld=0;%>
	                        <logic:present  name="uldNumber">
							<bean:define id="uldNumber" name="uldNumber" />
	      			       <logic:iterate id="uld" name="uldNumber" indexId="index">
	      				

							<tr>
							<td  class="iCargoTableDataTd ic-center" >
								<ihtml:hidden property="hiddenOpFlagForULD" value="I"/>
								<html:checkbox property="checkForUld" value="<%=String.valueOf(countUld)%>" />
							</td>
							<td  class="iCargoTableDataTd ic-center" >
								<ibusiness:uld id="uldno" uldProperty="uldNumber" styleClass="iCargoEditableTextFieldRowColor1" uldValue="<%= String.valueOf(uld)%>" componentID="TXT_ULD_DEFAULTS_RECORDULD_ULDNO" style="text-transform: uppercase; background : #FFFFCC !important;"/>
			
							</td>
							</tr>
							<%countUld++;%>
	      				 
	      				       </logic:iterate>
	                                                 </logic:present>

								<!-- templateRow -->
								<tr template="true" id="adduldnumberTemplateRow" style="display:none">
									<td class="iCargoTableDataTd ic-center" >
										<ihtml:hidden property="hiddenOpFlagForULD" value="NOOP"/>
										<html:checkbox property="checkForUld" value="<%=String.valueOf(countUld)%>" />
									</td>
									<td class="iCargoTableTd ic-center">
									<ibusiness:uld id="uldno" uldProperty="uldNumber" styleClass="iCargoEditableTextFieldRowColor1"  componentID="TXT_ULD_DEFAULTS_RECORDULD_ULDNO" style="text-transform: uppercase; background : #FFFFCC !important;" uldValue="" />
										
									</td>
								</tr>
								<!-- template Row over -->

	                 </tbody>
					
				</table>
					</div>
				</div>
					
				</fieldset>	
				</div>
				</div>
				
				<div class="ic-input-container ic-round-border">
					<div class="ic-row">
						<div class="ic-input ic-split-30 ic_inline_chcekbox marginT15">
						<ihtml:checkbox property="updateCurrentStation" componentID="TXT_ULD_DEFAULTS_RECORDULD_CHKCURRENTARP"/>
							<label>
							<common:message bundle="recordULDMovementResources" key="uld.defaults.recordULDMovement.updatecurrentairport" />
							</label>
							
							
						</div>
						<div class="ic-input ic-mandatory ic-split-25">
						<label>
						<common:message bundle="recordULDMovementResources" key="uld.defaults.recordULDMovement.currentairport" />
						</label>
							<ihtml:text  property="currentStation"  componentID="TXT_ULD_DEFAULTS_RECORDULD_CURRENT_STATION"  maxlength="3" />
							<div class="lovImg">
							<img id="ownerairportlovImgTbl" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" alt="Airport LOV"/>
							</div>
						</div>
						<!-- As part of 145312, mandatory class has been removed by A-6770 -->
						<div class="ic-input ic-split-45">
						<label>
						<common:message bundle="recordULDMovementResources" key="uld.defaults.recordULDMovement.remarks" />
						</label>
							 <ihtml:textarea property="remarks" rows="2" cols="80" componentID="TXT_ULD_DEFAULTS_RECORDULD_REMARKS"></ihtml:textarea>
						</div>
					</div>
					
			
			
				</div>
				
				
				
			</div>
			<div class="ic-foot-container">
				<div class="ic-button-container paddR5">
					<ihtml:nbutton   property="btnSave"  componentID="BTN_ULD_DEFAULTS_RECORDULD_SAVE" accesskey="S"><common:message  key="uld.defaults.recordULDMovement.btn.Save" /></ihtml:nbutton>
					<ihtml:nbutton   property="btnClose"  componentID="BTN_ULD_DEFAULTS_RECORDULD_CLOSE" accesskey="O"><common:message  key="uld.defaults.recordULDMovement.btn.Close" /></ihtml:nbutton>
             
				</div>
			</div>
		</div>
	
</ihtml:form>
</div>
	</div>
</div>
</div>
</body>

</html:html>

