<%--
/***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  IN - Inventory Control
* File Name				:  MaintainStockHolder.jsp
* Date					:  2-sep-2005
* Author(s)				:  Akhila S

*************************************************************************/
 --%>

<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockVO"%>
<%@ page import="java.util.ArrayList"%>

<html:html>
<head>
		
	
	
<bean:define id="form"
	name="MaintainStockHolderForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.MaintainStockHolderForm"
	toScope="page" />

<title><common:message bundle="<%=form.getBundle()%>" key="stockholder.title"/>
</title>
<meta name="decorator" content="mainpanelrestyledui">
 <script>    
	function viewDetailsOnDblClick(){
		return;
	}
</script>
<common:include type="script" src="/js/stockcontrol/defaults/MaintainStockHolder_Script.jsp" />

</head>

<body id="bodyStyle">
		
	
	
	


<business:sessionBean id="stockHolderType" moduleName="stockcontrol.defaults" screenID="stockcontrol.defaults.maintainstockholder" method="get" attribute="prioritizedStockHolders" />

<logic:present name="stockHolderType" >
<bean:define id="stockHolderTypeOnetime" name="stockHolderType"  />
</logic:present>

<div class="iCargoContent ic-masterbg" id="pageDiv" style="height:100%">
	<ihtml:form action="stockcontrol.defaults.screenloadmaintainstockholder.do">
	<html:hidden property="checkedReorder"  />
    <html:hidden property="checkedAutoRequest" />
    <html:hidden property="nextAction" />
	<html:hidden property="id" />
	<html:hidden property="documentType"  />
	<html:hidden property="documentSubType" />
	<input type="hidden" name="currentDialogId" />
	<input type="hidden" name="currentDialogOption" />
	<ihtml:hidden property="fromStockHolderList" />
	<ihtml:hidden property="listSuccessful" />
	<ihtml:hidden property="approverCode" />
	<ihtml:hidden property="checkedAutoPopulate" />

	<div class="ic-content-main">
		<span class="ic-page-title ic-display-none">     
			<bean:message bundle="<%=form.getBundle()%>"  key="maintainstockholder.pagetitle"/>
		</span>
  
	  <business:sessionBean id="mode" moduleName="stockcontrol.defaults" screenID="stockcontrol.defaults.maintainstockholder" method="get" attribute="mode" />
	  <business:sessionBean id="partnerAirlines"
		moduleName="stockcontrol.defaults"
		screenID="stockcontrol.defaults.maintainstockholder"
		method="get"
		attribute="partnerAirlines"/>
	  <logic:present name="mode" >
	  <bean:define id="modes" name="mode"  />
	 </logic:present>
         <%
         	boolean isNonEditable = false;
         %>
         <logic:equal name="modes" value="U">

 		<%
 			isNonEditable = true;
 		%>
	</logic:equal>
		<div class="ic-head-container">
			<%--<div class="ic-row">
				<h4><bean:message  bundle="<%=form.getBundle()%>" key="maintainstockholder.pagetitle"/></h4>
			</div> --%>
			<div class="ic-filter-panel">
                <div class="ic-row"><h4><bean:message  bundle="<%=form.getBundle()%>" key="maintainstockholder.pagetitle"/></h4></div>
				<div class="ic-row">
				<div class="ic-input-container">
					<div class="ic-col-12">
													
							<div class="ic-input ic-mandatory ic-split-100 ic-label-80">
								<label>
									<bean:message  bundle="<%=form.getBundle()%>" key="maintainstockholder.stockholdertype"/>
								</label>
								<ihtml:select property="stockHolderType"
								componentID="CMB_STOCKCONTROL_DEFAULTS_MAINTAINSTOCKHOLDER_STOCKHOLDERTYPE"
								disabled="<%=isNonEditable%>" >
								<logic:present name="stockHolderTypeOnetime" >
								<html:option value=""><common:message key="combo.select"/></html:option>
								  <logic:iterate id="oneTimeVO" name="stockHolderTypeOnetime">
									  <bean:define id="defaultValue" name="oneTimeVO" property="stockHolderType" />
									<bean:define id="diaplayValue" name="oneTimeVO" property="stockHolderDescription" />
									<html:option value="<%=(String)defaultValue%>"><%=(String)diaplayValue%></html:option>
								 </logic:iterate>
								</logic:present>
								</ihtml:select>
							</div>
							
				    </div>				
					<div class="ic-col-12">
							<div class="ic-input ic-mandatory ic-split-100 ic-label-80">
								<label>
									<bean:message bundle="<%=form.getBundle()%>" key="maintainstockholder.stockholdercode"/>
								</label>
								<ihtml:text property="stockHolderCode"
								componentID="TXT_STOCKCONTROL_DEFAULTS_MAINTAINSTOCKHOLDER_STOCKHOLDERCODE"
								maxlength="12"
								readonly="<%=isNonEditable%>"
								/>
							</div>
							
					</div>
					<div class="ic-col-14">
							<div class="ic-input ic-mandatory ic-split-100 ic-label-80">
								<label>
									<bean:message bundle="<%=form.getBundle()%>" key="maintainstockholder.stockholdername"/>
								</label>
								<ihtml:text property="stockHolderName"
								componentID="TXT_STOCKCONTROL_DEFAULTS_MAINTAINSTOCKHOLDER_STOCKHOLDERNAME"
								maxlength="150"
								/>
							</div>
							
					</div>
                    <div class="ic-col-21">
                        <div class="ic-input ic-mandatory ic-split-82 ic-label-80">
								<label>
									<bean:message bundle="<%=form.getBundle()%>" key="maintainstockholder.controlprivilege"/>
								</label>
								<ihtml:text property="controlPrivilege"
								componentID="TXT_STOCKCONTROL_DEFAULTS_MAINTAINSTOCKHOLDER_CONTROLPRIVILEGE"
								maxlength="25"
								/>
							</div>
                    </div>
                    <div class="ic-col-21">
                        <div class="ic-input ic-split-100 ic-label-35" >
								<label>
									<bean:message bundle="<%=form.getBundle()%>" key="contact"/>
								</label>
								<ihtml:text property="contact"
								componentID="TXT_STOCKCONTROL_DEFAULTS_MAINTAINSTOCKHOLDER_CONTACT"
								maxlength="300"/>
							</div>
                        
                    </div>
                    <div class="ic-col-20">
                        <div class="ic-input ic-split-100 ic-label-25" >
								<div class="ic-button-container" >
								<%--Modified by a-4562 for Icrd-8946 starts	--%>	  
								  <ihtml:nbutton property="btList"
									componentID="TXT_STOCKCONTROL_DEFAULTS_MAINTAINSTOCKHOLDER_DETAILS"  accesskey="L">
										<common:message key="stockcontrol.defaults.maintainstockholder.lbl.details"/>
								  </ihtml:nbutton>
								  <ihtml:nbutton property="btClear"
										componentID="BTN_STOCKCONTROL_DEFAULTS_MAINTAINSTOCKHOLDER_CLEAR" accesskey="C" >
										<common:message key="maintainstockholder.clear"/>
								</ihtml:nbutton>
								 <%--Modified by a-4562 for Icrd-8946 ends--%>
								</div>
							</div>
                    </div>
				</div>
			</div>							
		</div>
		</div>
		<div class="ic-main-container">
			<div class="ic-row">
				<div class="ic-input ic-button-container paddR15">
					<a href="#" class="iCargoLink" onclick="selectAction('add')" ><bean:message bundle= "<%=form.getBundle()%>" key="maintainstockholder.add"/> </a>  |
                     <a href="#" class="iCargoLink"  onclick="deleteTableRow('stockcontrol.defaults.deleterow.do','checkBox')" ><bean:message bundle= "<%=form.getBundle()%>" key="maintainstockholder.delete"/></a>
				</div>				
			</div>
			<div class="ic-row" id="listTable">
				<div class="ic-col-100">
					<div class="ic-row">
						<div id="tableContainer" class="tableContainer"  style="height:700px;">    <%--  Modified the height value by A-5200 for the BUG-ICRD-24913 --%>
						
			<table id="stockHolderTable" class="fixed-header-table" >
			<thead >
              <tr >
                <th class="ic-center" style="font:12px;" width="5%"><input type="checkbox" name="checkAll" value="checkbox" onclick="updateHeaderCheckBox(this.form,this,this.form.checkBox)"/></th>
                <th title="Document Type|Document Sub Type" style="font:12px" width="15%"> <bean:message bundle= "<%=form.getBundle()%>" key="maintainstockholder.doctypesubtype"/><span class="iCargoMandatoryFieldIcon">*</span> </th>
		<th title="Approver" style="font:12px" width="10%"> <bean:message bundle= "<%=form.getBundle()%>" key="maintainstockholder.approver"/>        </th>
		<th title="AWB Prefix" style="font:12px" width="11%"> <bean:message bundle="<%=form.getBundle()%>" key="stockcontrol.defaults.maintainstockholder.awbprefix.lbl"/></th>
                <th title="Reorder Level" style="font:12px" width="10%"> <bean:message bundle= "<%=form.getBundle()%>" key="maintainstockholder.reorderlevel"/>    </th>
                <th title="Reorder Quantity" style="font:12px" width="10%"> <bean:message bundle= "<%=form.getBundle()%>" key="maintainstockholder.reorderqty"/>      </th>
                <th title="Reorder Alert" style="font:12px" width="10%"> <bean:message bundle= "<%=form.getBundle()%>" key="maintainstockholder.reorderalert"/>    </th>
                <th title="Auto Stock Request" style="font:12px" width="10%"> <bean:message bundle= "<%=form.getBundle()%>" key="maintainstockholder.autostockrequest"/></th>
 		<th title="Autoprocess Quantity" style="font:12px" width="10%"> <bean:message bundle= "<%=form.getBundle()%>" key="stockcontrol.defaults.maintainstockholder.lbl.autoprocessqty"/> </th>
				<th title="Auto Populate" style="font:12px" width="10%"><bean:message bundle= "<%=form.getBundle()%>" key="stockcontrol.defaults.maintainstockholder.lbl.autopopulate"/></th>
                <th title="Remarks" style="font:12px" width="10%"> <bean:message bundle= "<%=form.getBundle()%>" key="maintainstockholder.remarks"/>         </th>
              </tr></thead><tbody class="iCargoBorderedTable" >
 	 	 	          <business:sessionBean id="stockVO" moduleName="stockcontrol.defaults" screenID="stockcontrol.defaults.maintainstockholder" method="get" attribute="stockVO" />

                      <business:sessionBean id="map" moduleName="stockcontrol.defaults" screenID="stockcontrol.defaults.maintainstockholder" method="get" attribute="map" />

 	 	 	           <logic:present name="stockVO" >
 	 	 	            <bean:define id="stockVo" name="stockVO"  />


					<logic:iterate id="vo" name="stockVo" indexId="index">
						<logic:present name="vo" property="operationFlag">
							<html:hidden property="isRowModified" value="0" />
							<logic:equal name="vo" property="operationFlag" value="D">
								<html:hidden property="docType" />
								<html:hidden property="docSubType" />
								<html:hidden property="approver" />
								<html:hidden property="awbPrefix" />
								<html:hidden property="reorderLevel" />
								<html:hidden property="reorderQuantity" />
								<html:hidden property="reorderAlertFlag" />
								<html:hidden property="autoRequestFlag" />
								<html:hidden property="autoprocessQuantity" />
								<html:hidden property="remarks" />
								<html:hidden property="autoPopulateFlag" />
							</logic:equal>
							<logic:notEqual name="vo" property="operationFlag" value="D">
<%--  Modified by A-5275 for the BUG-ICRD-25633 --%>
								
								<tr >
								
								<bean:define id="operFlag" name="vo" property="operationFlag"/>
								<html:hidden property="OpFlag" value="<%=(String)operFlag%>" />

								
								  <%-- <a href="#" ondblclick="viewDetailsOnDblClick()"/>  --%>
								  <td class="iCargoTableDataTd ic-center" style="text-align:center;">   <%--  Modified by A-5200 for the BUG-ICRD-24913 --%>
									<input type="checkbox" name="checkBox" value="<%=String.valueOf(index)%>"  onclick="toggleTableHeaderCheckbox('checkBox',this.form.checkAll)"/>
								  </td>
								  <td class="iCargoTableDataTd">
								  <logic:present name="map" >
									<bean:define id="docType" name="vo" property="documentType"/>
									<bean:define id="docSubType" name="vo" property="documentSubType"/>
									   <bean:define id="maps" name="map" toScope="page" type="java.util.HashMap"/>
										<%--Modified by A-1927 @ NRT on 13-Jul-2007 for NCA Bug Fix starts--%>
										<ibusiness:dynamicoptionlist  collection="maps"
											id="docType"
											firstlistname="docType"
											componentID="TXT_STOCKCONTROL_DEFAULTS_MAINTAINSTOCKHOLDER_DYNAMICOPTIONLIST"
											lastlistname="docSubType"
											optionstyleclass="iCargoSmallComboBox"
											labelstyleclass="iCargoLabelRightAligned"
											firstselectedvalue="<%=String.valueOf(docType)%>"
											lastselectedvalue="<%=String.valueOf(docSubType)%>"
											indexId="index"
											styleId="docType"
											docTypeTitle="doctype.tooltip"
											subDocTypeTitle="subdoctype.tooltip"/>
										
								</logic:present>
								  </td>
								  <td class="iCargoTableDataTd">
									  <logic:present name="vo" property="stockApproverCode">
										  <ihtml:text property="approver" 
											value="<%=String.valueOf(((StockVO)vo).getStockApproverCode())%>"
											componentID="TXT_STOCKCONTROL_DEFAULTS_MAINTAINSTOCKHOLDER_APPROVER"
											maxlength="12"/>
											<div class= "lovImgTbl" style="padding-top:2px;"><img id="<%=String.valueOf(index)%>"
										     src="<%=request.getContextPath()%>/images/lov.png"
										     width="16" height="16"
										     onclick="displayApproverLov('stockcontrol.defaults.update.do','stockcontrol.defaults.screenloadapproverlov.do',this,<%=String.valueOf(index)%>)" style="vertical-align: middle;"/></div>

									  </logic:present>
									  <logic:notPresent name="vo" property="stockApproverCode">
										  <ihtml:text property="approver"
											value=""
											componentID="TXT_STOCKCONTROL_DEFAULTS_MAINTAINSTOCKHOLDER_APPROVER"
											maxlength="12"/>
											<div class= "lovImgTbl" style="padding-top:2px;"><img id="<%=String.valueOf(index)%>"
										     src="<%=request.getContextPath()%>/images/lov.png"
										     width="16" height="16"
										     onclick="displayApproverLov('stockcontrol.defaults.update.do','stockcontrol.defaults.screenloadapproverlov.do',this,<%=String.valueOf(index)%>)" style="vertical-align: middle;"/></div>

									  </logic:notPresent>

									</td>
								  <td class="iCargoTableDataTd">
										<ihtml:select property="awbPrefix" componentID="CHK_STOCKCONTROL_DEFAULTS_CREATESTOCK_PRTARL_CMB" value="<%=String.valueOf(((StockVO)vo).getAirlineIdentifier())%>">
											<logic:present name="partnerAirlines">
												<logic:iterate id="airlineLovVO" name="partnerAirlines" type="com.ibsplc.icargo.business.shared.airline.vo.AirlineLovVO">
												<logic:present name="airlineLovVO" property="airlineNumber">
													<% String value=""+airlineLovVO.getAirlineIdentifier(); %>
													<ihtml:option value="<%=value%>"><%=airlineLovVO.getAirlineNumber()%></ihtml:option>
												</logic:present>
												</logic:iterate>
											</logic:present>
										</ihtml:select>
									</td>
								  <td class="iCargoTableDataTd">
								  <ihtml:text property="reorderLevel" style="text-align:right;"
									componentID="TXT_STOCKCONTROL_DEFAULTS_MAINTAINSTOCKHOLDER_REORDERLEVEL"
									maxlength="7" value="<%=String.valueOf(((StockVO)vo).getReorderLevel())%>"
									/></td>
								  <td class="iCargoTableDataTd">
								  <ihtml:text property="reorderQuantity" style="text-align:right;width=70px"
									componentID="TXT_STOCKCONTROL_DEFAULTS_MAINTAINSTOCKHOLDER_REORDERQUANTITY"
									maxlength="7" value="<%=String.valueOf(((StockVO)vo).getReorderQuantity())%>"
									/>
								  </td>

								  <td class="iCargoTableDataTd ic-center" style="text-align:center;">  <%--  Modified by A-5200 for the BUG-ICRD-24913 --%>

									  <logic:present name="vo" property="reorderAlertFlag">
										<logic:equal name="vo" property="reorderAlertFlag" value="true">
											<input type="checkbox" name="reorderAlertFlag"   value="true" checked="checked"/>
										</logic:equal>
										<logic:notEqual name="vo" property="reorderAlertFlag" value="true">
											<input type="checkbox" name="reorderAlertFlag"    value="false" />
										</logic:notEqual>

									  </logic:present>
								  </td>


								  <td class="iCargoTableDataTd ic-center" style="text-align:center;">  <%--  Modified by A-5200 for the BUG-ICRD-24913 --%>
									  <logic:present name="vo" property="autoRequestFlag">
										<logic:equal name="vo" property="autoRequestFlag" value="true">
											<input type="checkbox" name="autoRequestFlag"   value="true" checked="checked"/>
										</logic:equal>
										<logic:notEqual name="vo" property="autoRequestFlag" value="true">
											<input type="checkbox" name="autoRequestFlag"    value="false" />
										</logic:notEqual>

									  </logic:present>
								  </td>

								  <td class="iCargoTableDataTd">
								  <ihtml:text name="vo" property="autoprocessQuantity"
											  style="text-align:right;" 
											  componentID="TXT_STOCKCONTROL_DEFAULTS_MAINTAINSTOCKHOLDER_AUTPRCQTY"
											  maxlength = "7"
										/></td>
									<td class="iCargoTableDataTd" style="text-align:center;"> 
										<logic:present name="vo" property="autoPopulateFlag">
										<logic:equal name="vo" property="autoPopulateFlag" value="true">
											<input type="checkbox" name="autoPopulateFlag"   value="true" checked="checked" title="<common:message key="stockcontrol.defaults.maintainstockholder.tooltip.autopopulate"/>"/>
										</logic:equal>
										<logic:notEqual name="vo" property="autoPopulateFlag" value="true">
											<input type="checkbox" name="autoPopulateFlag"    value="false" title="<common:message key="stockcontrol.defaults.maintainstockholder.tooltip.autopopulate"/>"/>
										</logic:notEqual>

									  </logic:present>
									</td>
								  <td class="iCargoTableDataTd">
								  <ihtml:text name="vo" property="remarks"
										
										componentID="TXT_STOCKCONTROL_DEFAULTS_MAINTAINSTOCKHOLDER_REMARKS"
										maxlength="100"
										/></td>
							
							</tr>
						
							</logic:notEqual>

						</logic:present>

						<logic:notPresent name="vo" property="operationFlag">

								
								<tr >
								
								<html:hidden property="OpFlag" value="" />
								<html:hidden property="isRowModified" value="0" />
								
								  <td class="iCargoTableDataTd ic-center" >
									<input type="checkbox" name="checkBox" value="<%=String.valueOf(index)%>" onclick="toggleTableHeaderCheckbox('checkBox',this.form.checkAll)"/>
								  </td>
								  <td class="iCargoTableDataTd">
								  <logic:present name="map" >
									<bean:define id="docType" name="vo" property="documentType"/>
									<bean:define id="docSubType" name="vo" property="documentSubType"/>
									   <bean:define id="maps" name="map" toScope="page" type="java.util.HashMap"/>
										<ibusiness:dynamicoptionlist  collection="maps"
											id="docType"
											firstlistname="docType"
											componentID="TXT_STOCKCONTROL_DEFAULTS_MAINTAINSTOCKHOLDER_DYNAMICOPTIONLIST"
											lastlistname="docSubType"
											optionstyleclass="iCargoSmallComboBox"
											labelstyleclass="iCargoLabelRightAligned"
										    firstselectedvalue="<%=String.valueOf(docType)%>"
											lastselectedvalue="<%=String.valueOf(docSubType)%>"
											indexId="index"
											styleId="docType"
											docTypeTitle="stockcontrol.defaults.maintainstockholder.tooltip.docTypeTitle"
			                                                                subDocTypeTitle="stockcontrol.defaults.maintainstockholder.tooltip.subDocTypeTitle"/>
									   
								</logic:present>
								  </td>
								  <td class="iCargoTableDataTd">
									  <logic:present name="vo" property="stockApproverCode">
										  <ihtml:text property="approver" 
											value="<%=String.valueOf(((StockVO)vo).getStockApproverCode())%>"
											componentID="TXT_STOCKCONTROL_DEFAULTS_MAINTAINSTOCKHOLDER_APPROVER"
											maxlength="12"/>
											<div class= "lovImgTbl" style="padding-top:2px;"><img id="<%=String.valueOf(index)%>"
										     src="<%=request.getContextPath()%>/images/lov.png"
										     width="16" height="16"
										     onclick="displayApproverLov('stockcontrol.defaults.update.do','stockcontrol.defaults.screenloadapproverlov.do',this)" style="vertical-align: middle;"/></div>

									  </logic:present>
									  <logic:notPresent name="vo" property="stockApproverCode">
										  <ihtml:text property="approver" 
											value=""
											componentID="TXT_STOCKCONTROL_DEFAULTS_MAINTAINSTOCKHOLDER_APPROVER"
											maxlength="12"/>
											<div class= "lovImgTbl" style="padding-top:2px;"><img id="<%=String.valueOf(index)%>"
										     src="<%=request.getContextPath()%>/images/lov.png"
										     width="16" height="16"
										     onclick="displayApproverLov('stockcontrol.defaults.update.do','stockcontrol.defaults.screenloadapproverlov.do',this)" style="vertical-align: middle;"/></div>

									  </logic:notPresent>

									</td>
								  <td class="iCargoTableDataTd">
										<ihtml:select property="awbPrefix" componentID="CHK_STOCKCONTROL_DEFAULTS_CREATESTOCK_PRTARL_CMB" value="<%=String.valueOf(((StockVO)vo).getAirlineIdentifier())%>">
											<logic:present name="partnerAirlines">
												<logic:iterate id="airlineLovVO" name="partnerAirlines" type="com.ibsplc.icargo.business.shared.airline.vo.AirlineLovVO">
												<logic:present name="airlineLovVO" property="airlineNumber">
													<% String value=""+airlineLovVO.getAirlineIdentifier(); %>
													<ihtml:option value="<%=value%>"><%=airlineLovVO.getAirlineNumber()%></ihtml:option>
												</logic:present>
												</logic:iterate>
											</logic:present>
										</ihtml:select>
									</td>
								  <td class="iCargoTableDataTd">
								  <ihtml:text property="reorderLevel" style="text-align:right;width=70px"
									componentID="TXT_STOCKCONTROL_DEFAULTS_MAINTAINSTOCKHOLDER_REORDERLEVEL"
									maxlength="7" value="<%=String.valueOf(((StockVO)vo).getReorderLevel())%>"
									/></td>
								  <td class="iCargoTableDataTd">
								  <ihtml:text property="reorderQuantity" style="text-align:right;width=70px"
									componentID="TXT_STOCKCONTROL_DEFAULTS_MAINTAINSTOCKHOLDER_REORDERQUANTITY"
									maxlength="7" value="<%=String.valueOf(((StockVO)vo).getReorderQuantity())%>"
									/>
								  </td>

								  <td class="iCargoTableDataTd ic-center">

									  <logic:present name="vo" property="reorderAlertFlag">
										<logic:equal name="vo" property="reorderAlertFlag" value="true">
											<input type="checkbox" name="reorderAlertFlag"   value="true" checked="checked"/>
										</logic:equal>
										<logic:notEqual name="vo" property="reorderAlertFlag" value="true">
											<input type="checkbox" name="reorderAlertFlag"    value="false" />
										</logic:notEqual>

									  </logic:present>
								  </td>


								  <td class="iCargoTableDataTd ic-center">
									  <logic:present name="vo" property="autoRequestFlag">
										<logic:equal name="vo" property="autoRequestFlag" value="true">
											<input type="checkbox" name="autoRequestFlag"   value="true" checked="checked"/>
										</logic:equal>
										<logic:notEqual name="vo" property="autoRequestFlag" value="true">
											<input type="checkbox" name="autoRequestFlag"    value="false" />
										</logic:notEqual>

									  </logic:present>
								  </td>

								  <td class="iCargoTableDataTd">
								  <ihtml:text name="vo" property="autoprocessQuantity"
											  style="text-align:right;"
											  componentID="TXT_STOCKCONTROL_DEFAULTS_MAINTAINSTOCKHOLDER_AUTPRCQTY"
											  maxlength = "7"
										/></td>
								<td class="iCargoTableDataTd ic-center" style="text-align:center;"> 
										<logic:present name="vo" property="autoPopulateFlag">
										<logic:equal name="vo" property="autoPopulateFlag" value="true">
											<input type="checkbox" name="autoPopulateFlag"   value="true" checked="checked" 
											title="Consider this document subtype for auto-population of AWB"/>
										</logic:equal>
										<logic:notEqual name="vo" property="autoPopulateFlag" value="true">
											<input type="checkbox" name="autoPopulateFlag"    value="false" 
											title="Consider this document subtype for auto-population of AWB" />
										</logic:notEqual>
									  </logic:present>
								</td>
								  <td class="iCargoTableDataTd">
								  <ihtml:text name="vo" property="remarks"
										
										componentID="TXT_STOCKCONTROL_DEFAULTS_MAINTAINSTOCKHOLDER_REMARKS"
										maxlength="100"
										/></td>


								
						</tr >
						
						</logic:notPresent>

					</logic:iterate>


                  </logic:present>


                      </tbody></table>
			</div>
					</div>
				</div>
			</div>
		</div>
		<div class="ic-foot-container">
			<div class="ic-row">
				<div class="ic-button-container paddR10">
					<ihtml:nbutton property="btSave"
						componentID="BTN_STOCKCONTROL_DEFAULTS_MAINTAINSTOCKHOLDER_SAVE" accesskey="S" >
						<common:message key="maintainstockholder.save"/>
					</ihtml:nbutton>
					
					<ihtml:nbutton property="btClose"
						componentID="BTN_STOCKCONTROL_DEFAULTS_MAINTAINSTOCKHOLDER_CLOSE" accesskey="O">
						<common:message key="maintainstockholder.close"/>
					</ihtml:nbutton>
				</div>
			</div>
		</div>
	</div>
 </ihtml:form>
</div>
	</body>
</html:html>

