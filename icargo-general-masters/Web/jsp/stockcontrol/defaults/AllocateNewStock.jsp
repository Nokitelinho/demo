<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  SKCM - stock Control
* File Name				:  AllocateNewStock.jsp
* Date					:  20-Sep-2005
* Author(s)				:  Kirupakaran
*************************************************************************/
 --%>
<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@include file="/jsp/includes/reports/printFrame.jsp" %>
<%@ page import="java.util.Map"%>
<%@ page import = "com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO" %>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.AllocateNewStockForm" %>
<%@ page import = "com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO" %>
<html:html>
<head>
		
	
	
<bean:define id="form"
	name="AllocateNewStockForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.AllocateNewStockForm"
	toScope="page" />
<title><common:message bundle="<%=form.getBundle()%>" key="stockholder.title"/></title>
<meta name="decorator" content="mainpanelrestyledui">
<common:include type="script" src="/js/stockcontrol/defaults/AllocateNewStock_Script.jsp" />
<STYLE>
.optionListCombo{
	padding-left = 2cm;
}
</STYLE>
</head>
<body id="bodyStyle">

	
	
	
	
<business:sessionBean id="allocstock"
	moduleName="stockcontrol.defaults"
	screenID="stockcontrol.defaults.allocatenewstock"
	method="get"
	attribute="allocatedRangeVos"/>
<business:sessionBean id="availstock"
	moduleName="stockcontrol.defaults"
	screenID="stockcontrol.defaults.allocatenewstock"
	method="get"
	attribute="availableRangeVos"/>
<business:sessionBean id="options"
	moduleName="stockcontrol.defaults"
	screenID="stockcontrol.defaults.allocatenewstock"
	method="get"
	attribute="documentTypes"/>
<business:sessionBean id="stockHoldersFromSession"
	moduleName="stockcontrol.defaults"
	screenID="stockcontrol.defaults.allocatenewstock"
	method="get"
	attribute="prioritizedStockHolders" />
<business:sessionBean id="stockControlFor"
	moduleName="stockcontrol.defaults"
	screenID="stockcontrol.defaults.allocatenewstock"
	method="get"
	attribute="stockControlFor" />
<business:sessionBean id="partnerAirlines"
	moduleName="stockcontrol.defaults"
	screenID="stockcontrol.defaults.allocatenewstock"
	method="get"
	attribute="partnerAirlines"/>	
<%
 	int availcount;
 	int alloccount;
 	AllocateNewStockForm allocateNewStockForm = (AllocateNewStockForm)request.getAttribute("AllocateNewStockForm");
 %>
<div class="iCargoContent ic-masterbg" id="pageDiv" style="overflow:auto;"> <ihtml:form action="/stockcontrol.defaults.screenloadallocatenewstock.do">
  <input type="hidden" name="currentDialogId" />
  <input type="hidden" name="currentDialogOption" />
  <html:hidden property="documentRange"/>
  <html:hidden property="isError"/>
  <html:hidden property="buttonStatusFlag" />
  <html:hidden property="reportGenerateMode"/>
  <html:hidden property="statusFlag" />
  <html:hidden property="partnerPrefix" />
  <div class="ic-content-main">
	<span class="ic-page-title ic-display-none">
    	<common:message key="stockholder.AllocateNewStock"/>
	</span>
	<div class="ic-head-container">
		<!-- <div class="ic-row">
			<h4><common:message key="stockholder.search" /></h4>
		</div> -->
		<div class="ic-filter-panel">
            <div class="ic-row"><h4><common:message key="stockholder.search" /></h4></div>
			<div class="ic-row ic-border marginB5" style="width:99.5%;">
				<div class="ic-col-100">
					<div class="ic-input-container">
						<div class="ic-row">
							<div class="ic-input ic-split-30">
                                <logic:present name="options"> <bean:define name="options" id="list" type="java.util.HashMap"/>
								<!-- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
								  <!--Modified by A-1927 @ NRT on 13-Jul-2007 for NCA Bug Fix starts-->
								  <ibusiness:dynamicoptionlist  collection="list"
									id="docType"
									firstlistname="docType"
									componentID="TXT_STOCKCONTROL_DEFAULTS_ALLOCATENEWSTOCK_DYNAMICOPTIONLIST"
									lastlistname="subType" firstoptionlabel="Doc. Type"
									lastoptionlabel="Sub Type"
									optionstyleclass="iCargoMediumComboBox"
									labelstyleclass="iCargoLabelRightAligned optionListCombo"
									firstselectedvalue="<%=allocateNewStockForm.getDocType()%>"
									lastselectedvalue="<%=allocateNewStockForm.getSubType()%>"
									docTypeTitle="doctype.tooltip"
									subDocTypeTitle="subdoctype.tooltip"/>
		
								</logic:present>
                                
							</div>		
							<div class="ic-input ic-mandatory ic-split-17">						
								<label class="ic-label-20">
									<common:message key="stockholder.StockControlFor"/>
								</label>
								<ihtml:text property="stockControlFor" componentID="CMB_STOCKCONTROL_DEFAULTS_ALLOCATENEWSTOCK_STOCKCONTROLFOR"  maxlength="12"/>
								 <div class= "lovImg"><img height="22" src="<%=request.getContextPath()%>/images/lov.png" width="22"
									onclick="displayLov('stockcontrol.defaults.screenloadstockholderlov.do');"/></div>
							</div>
						<!--</div>
						<div class="ic-row"> -->
							<div class="ic-input ic-split-17">						
								<label class="ic-label-20">
									<common:message key="stockholder.StockHolderType"/>
								</label>
								<ihtml:select property="stockHolderType" componentID="CMB_STOCKCONTROL_DEFAULTS_ALLOCATENEWSTOCK_STOCKHOLDERTYPE" >
                  <logic:present name="stockHoldersFromSession"> <bean:define id="stockHolderList" name="stockHoldersFromSession" />
                  <ihtml:option value=""><common:message key="combo.select"/></ihtml:option> <logic:iterate id="priorityVO" name="stockHolderList" >
                  <html:option value= "<%=((StockHolderPriorityVO)priorityVO).getStockHolderType()%>">
                  <%=((StockHolderPriorityVO)priorityVO).getStockHolderDescription()%>
                  </html:option> </logic:iterate> </logic:present> </ihtml:select>
							</div>
							<div class="ic-input ic-split-17 ic-mandatory">						
								<label class="ic-label-30">
									<common:message key="stockholder.StockHolderCode"/>
								</label>					
								<ihtml:text property="stockHolderCode" componentID="TXT_STOCKCONTROL_DEFAULTS_ALLOCATENEWSTOCK_STOCKHOLDERCODE" maxlength="12"/>
								   <div class= "lovImg"><img height="22" src="<%=request.getContextPath()%>/images/lov.png" width="22" onclick="displayLov1('stockcontrol.defaults.screenloadstockholderlov.do');"/></div>
							</div>	
							<div class="ic-input ic-split-18 ic_inline_chcekbox" style="margin-top:18px;">												
								<ihtml:checkbox property="manual" componentID="CHK_STOCKCONTROL_DEFAULTS_ALLOCATENEWSTOCK_MANUAL"/>
								<label >
									 <common:message key="stockholder.Manual"/>
								</label>		
							</div>
						</div>
					</div>		
				</div>	
			</div>	
			<div class="ic-row ic-border marginB5" style="width:99.5%;">
				<div class="ic-col-100">
					<div class="ic-input-container">
						<div class="ic-row">
							<div class="ic-input ic-split-40 ic_inline_chcekbox marginT10">
								<ihtml:checkbox property="partnerAirline" componentID="CHK_STOCKCONTROL_DEFAULTS_CREATESTOCK_PRTARL_CHK" />
								<label>
									<common:message key="stockcontrol.defaults.allocatenewstock.partnerAirlines.lbl" />
								</label>
							</div>	
							<div class="ic-input ic-split-30">
								<label class="ic-label-35">
								<common:message key="stockcontrol.defaults.allocatenewstock.awbPrefix.lbl" />
								</label>
								<ihtml:select property="awbPrefix" componentID="CHK_STOCKCONTROL_DEFAULTS_CREATESTOCK_PRTARL_CMB" disabled='true'>
									<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
									<logic:present name="partnerAirlines">					
										<logic:iterate id="airlineLovVO" name="partnerAirlines" type="com.ibsplc.icargo.business.shared.airline.vo.AirlineLovVO">
											<logic:present name="airlineLovVO" property="airlineNumber">
												<% String value=airlineLovVO.getAirlineNumber()+"-"+airlineLovVO.getAirlineName()+"-"+airlineLovVO.getAirlineIdentifier(); %>
												<ihtml:option value="<%=value%>"><%=airlineLovVO.getAirlineNumber()%></ihtml:option>
											</logic:present>
										</logic:iterate>
									</logic:present>
								</ihtml:select>
							</div>	
							<div class="ic-input ic-split-30">
								<label class="ic-label-20">
								<common:message key="stockcontrol.defaults.allocatenewstock.partnerAirlines.lbl"/>
								</label>
								<ihtml:text property="airlineName" componentID= "CHK_STOCKCONTROL_DEFAULTS_CREATESTOCK_PRTARL" readonly="true"/>
							</div>	
						</div>	
					</div>
				</div>
			</div>
			<div class="ic-row">
				<div class="ic-col-45">
					<div class="ic-section ic-border">
						<div class="ic-row">
							<div class="ic-input-container">
								<h4><common:message key="stockholder.AvailableStock"/></h4>
								<div class="ic-row">
									<div class="ic-input ic-split-25">										
										<label class="ic-label-30">
											<common:message key="stockholder.RangeFrom"/>
										</label>
										<ihtml:text property="rangeFrom" style="text-align:right;" componentID="TXT_STOCKCONTROL_DEFAULTS_ALLOCATENEWSTOCK_RANGEFROM" />
									</div>
									<div class="ic-input ic-split-25">										
										<label>
											<common:message key="stockholder.RangeTo"/>
										</label>
										<ihtml:text property="rangeTo" style="text-align:right;" componentID="TXT_STOCKCONTROL_DEFAULTS_ALLOCATENEWSTOCK_RANGETO" />
									</div>
								<!-- </div>		
								<div class="ic-row"> -->
									<div class="ic-input ic-split-25">										
										<label class="ic-label-30">
											<common:message key="stockholder.NoOfDocs"/>
										</label>
										<ihtml:text property="numberOfDocs" style="text-align:right;" componentID="TXT_STOCKCONTROL_DEFAULTS_ALLOCATENEWSTOCK_NUMBEROFDOCUMENTS" maxlength="8"/>
									</div>
									<div class="ic-input ic-split-25 marginT5">
										<div class="ic-button-container">
											<ihtml:nbutton property="btnlist" componentID="BTN_STOCKCONTROL_DEFAULTS_ALLOCATENEWSTOCK_LIST" accesskey="L">
                  <common:message key="allocatenewstock.list"/> </ihtml:nbutton>
										</div>
									</div>
								</div>	
							</div>	
						</div>	
					</div>	
				</div>
			</div>
		</div>		
	</div>	
	<div class="ic-main-container">
		<div class="ic-row">
			<div class="ic-col-100">
				<div class="ic-button-container paddR5">
					<a class="iCargoLink" href="javascript:onClickAdd();"><common:message key="stockholder.Add"/></a>
				    | <a class="iCargoLink" href="javascript:onClickDelete();"><common:message key="stockholder.Delete"/></a>
				</div>
			</div>
		</div>
		<div class="ic-row">
			<div class="ic-col-45">
			<div id="div1" class="tableContainer paddL5"  style="height:300px;">
              <table id="rangeTable" class="fixed-header-table" style="width:100%">
                <thead>
                  <tr class="iCargoTableHeadingLeft">
                    <td width ="10%" class="iCargoTableHeaderLabel" style="text-align:center"> <input type="checkbox" name="availableCheckAll"  id="availableCheckAll"  onclick="updateHeaderCheckBox(this.form,this.form.availableCheckAll,this.form.availableRangeNo)"/>
                     </td>
                    <td width="20%" class="iCargoTableHeaderLabel" > <common:message key="stockholder.RANGEFROM"/>
                      <span></span> </td>
                    <td width="20%" class="iCargoTableHeaderLabel" > <common:message key="stockholder.RANGETO"/>
                      <span></span> </td>
                    <td width="20%" class="iCargoTableHeaderLabel" style="text-transform:none"> <common:message key="stockholder.NOOFDOCS"/>
                      <span></span> </td>
					  <td width="25%" class="iCargoTableHeaderLabel" > Utilised Documents
                      <span></span> </td>
                  </tr>
                </thead>
                <%	long availTotalNoOfDocs;
					availTotalNoOfDocs=0;
					availcount=0;
					int count=0;
				    %>
                <tbody>
                  <logic:present name="availstock">
                  	<logic:iterate id="availvo" name="availstock" indexId="nIndex">
					  
					  <tr>
						<td  width ="9%" class="iCargoTableDataTd" style="text-align:center;"> 
						
						<input type="checkbox" name="availableRangeNo" id="availableRangeNo"  value="<%=nIndex%>"  onclick="toggleTableHeaderCheckbox('availableRangeNo',this.form.availableCheckAll)"/>
						</td>
						<td width="29%" style="text-align:right;" class="iCargoTableDataTd"><%= ((RangeVO)availvo).getStartRange()%></td>
						<td width="29%" style="text-align:right;" class="iCargoTableDataTd"><%= ((RangeVO)availvo).getEndRange()%></td>
						<td width="22%" style="text-align:right;" class="iCargoTableDataTd"><%=((RangeVO)availvo).getNumberOfDocuments()%></td>
						<td width="22%" style="text-align:center;" class="iCargoTableDataTd"><logic:present name="availvo" property="masterDocumentNumbers">	

										<%String frmRange = (String)((RangeVO)availvo).getStartRange();%>						
												<img id="utilisedInfo" src="<%=request.getContextPath()%>/images/circle_yellow.png" onclick="onClickImage(<%=count%>,<%=frmRange%>);" />   
												
											</logic:present></td>
					  </tr>
					  <%
						availTotalNoOfDocs=availTotalNoOfDocs+((RangeVO)availvo).getNumberOfDocuments();
						availcount++;
						count=count+1;
					  %>
					  
					</logic:iterate>
				  </logic:present>
                </tbody>
              </table>
            </div>
			</div>
			<div class="ic-col-10 ic-center">
				
					 <ihtml:nbutton property="btnMoveRange" value="&gt;&gt;"
                  		componentID="BTN_STOCKCONTROL_DEFAULTS_ALLOCATENEWSTOCK_MOVERANGE"/>
				
			</div>
			<div class="ic-col-45">
				<div id="div2" class="tableContainer paddR5"  style="height:300px;">
            <table class="fixed-header-table" style="width:100%">
              <thead>
                <tr class="iCargoTableHeadingLeft">
                  <td width="10%" style="text-align:center"><!--<ihtml:checkbox property="allocatedCheckAll" componentID="CHK_STOCKCONTROL_DEFAULTS_ALLOCATENEWSTOCK_ALLOCATEDCHECKALL" />
				 <input type="checkbox" id="masterRowId" name="masterRowId"  onclick="updateHeaderCheckBox(this.form,this.form.masterRowId,this.form.checkdryIce)"/>
				  -->
				  <input type="checkbox" name="allocatedCheckAll"   onclick="updateHeaderCheckBox(this.form,this.form.allocatedCheckAll,this.form.allocatedRangeNo)"/>
				  </td>
                  <td width="29%"><common:message key="stockholder.RANGEFROM"/></td>
                  <td width="29%"><common:message key="stockholder.RANGETO"/></td>
                  <td width="22%" style="text-transform:none"><common:message key="stockholder.NOOFDOCS"/></td>
                </tr>
              </thead>
              <tbody id="rangeTableBody">
                <logic:present name="allocstock">
           		    <%
						alloccount=0;
					%>
					<logic:iterate id="vo" name="allocstock" indexId="nIndex">
						
							<logic:present name="vo" property="operationFlag" >
								<bean:define id="flag" name="vo" property="operationFlag" />
								<logic:notEqual name="flag" value="D">
									<tr>
									  <td class="iCargoTableDataTd" style="text-align:center;">
									  <div>
									  <input type="checkbox" id="allocatedRangeNo" name="allocatedRangeNo" value="<%=nIndex.toString()%>"  onclick="toggleTableHeaderCheckbox('allocatedRangeNo',this.form.allocatedCheckAll)"/>
										  <logic:equal name="flag" value="I">
											<ihtml:hidden property="hiddenOpFlag" value="I"/>
										  </logic:equal>
										  <logic:equal name="flag" value="U">
											<ihtml:hidden property="hiddenOpFlag" value="U"/>
										  </logic:equal>
										</div>
									  </td>
									  <td  class="iCargoTableDataTd">
										  <ihtml:text property="stockRangeFrom"
												componentID="TXT_STOCKCONTROL_DEFAULTS_ALLOCATENEWSTOCK_STOCKRANGEFROM"
												value="<%= ((RangeVO)vo).getStartRange() %>"
											     style="text-align:right"
												styleId="stockRangeF"
												indexId="nIndex"/>
									  </td>
									  <td class="iCargoTableDataTd">
										  <ihtml:text property="stockRangeTo"
												  componentID="TXT_STOCKCONTROL_DEFAULTS_ALLOCATENEWSTOCK_STOCKRANGETO"
												 
												   style="text-align:right"
												  value="<%= ((RangeVO)vo).getEndRange() %>"
												  styleId="stockRangeT"
												  indexId="nIndex"/>
									  </td>
									  <td class="iCargoTableDataTd">
										  <ihtml:text property="noOfDocs"
												componentID= "TXT_STOCKCONTROL_DEFAULTS_ALLOCATENEWSTOCK_NUMEBROFDOCS"
												 style="text-align:right"
												styleId="noOfDocs"
												indexId="nIndex"
												maxlength="8"
												readonly="true"
												value="<%= String.valueOf(((RangeVO)vo).getNumberOfDocuments()) %>"/>
									  </td>
									</tr>
								</logic:notEqual>
								<logic:equal name="flag" value="D">
									<ihtml:hidden property="allocatedRangeNo" value="<%= String.valueOf(alloccount) %>"/>
									<ihtml:hidden property="hiddenOpFlag" value="D"/>
									<ihtml:hidden property="stockRangeFrom" value=""/>
									<ihtml:hidden property="stockRangeTo" value=""/>
									<ihtml:hidden property="noOfDocs" value=""/>
								</logic:equal>
							</logic:present>
							<logic:notPresent name="vo" property="operationFlag" >
								<tr>
								  <td  class="iCargoTableDataTd" style="text-align:center;"><div>
									  <ihtml:checkbox property="allocatedRangeNo" componentID="CHK_STOCKCONTROL_DEFAULTS_ALLOCATENEWSTOCK_ALLOCATEDRANGENO" value="<%= String.valueOf(alloccount) %>" />
									  <ihtml:hidden property="hiddenOpFlag" value="U"/>
									  </div>
								  </td>
								  <td  class="iCargoTableDataTd">
									  <ihtml:text property="stockRangeFrom"
											componentID="TXT_STOCKCONTROL_DEFAULTS_ALLOCATENEWSTOCK_STOCKRANGEFROM"
											value="<%= ((RangeVO)vo).getStartRange() %>"
											 style="text-align:right"
											styleId="stockRangeF"
											indexId="nIndex"/>
								  </td>
								  <td class="iCargoTableDataTd">
									  <ihtml:text property="stockRangeTo"
											  componentID="TXT_STOCKCONTROL_DEFAULTS_ALLOCATENEWSTOCK_STOCKRANGETO"
											   style="text-align:right"
											  value="<%= ((RangeVO)vo).getEndRange() %>"
											  styleId="stockRangeT"
											  indexId="nIndex"/>
								  </td>
								  <td class="iCargoTableDataTd">
									  <ihtml:text property="noOfDocs"
											componentID= "TXT_STOCKCONTROL_DEFAULTS_ALLOCATENEWSTOCK_NUMEBROFDOCS"
											 style="text-align:right"
											styleId="noOfDocs"
											indexId="nIndex"
											maxlength="8"
											readonly="true"
											value="<%= String.valueOf(((RangeVO)vo).getNumberOfDocuments()) %>"/>
								  </td>
								</tr>
							</logic:notPresent>
							<%
								alloccount++;
							%>
						
					 </logic:iterate>
                 </logic:present>
				<!-- templateRow starts-->
				<tr template="true" id="rangeTableTemplateRow" style="display:none">
					  <td width="9%" class="iCargoTableDataTd ic-center" >
						<div >
  <input type="checkbox"  name="allocatedRangeNo" id="allocatedRangeNo"  onclick="toggleTableHeaderCheckbox('allocatedRangeNo',this.form.allocatedCheckAll)"/>
						  <ihtml:hidden property="hiddenOpFlag" value="NOOP"/>
						 </div>
					  </td>
					  <td width="29%" class="iCargoTableDataTd">
						  <ihtml:text property="stockRangeFrom"
								componentID="TXT_STOCKCONTROL_DEFAULTS_ALLOCATENEWSTOCK_STOCKRANGEFROM"
								value=""
								 style="text-align:right"
								styleId="stockRangeF"/>
					  </td>
					  <td width="29%" class="iCargoTableDataTd">
						  <ihtml:text property="stockRangeTo"
								  componentID="TXT_STOCKCONTROL_DEFAULTS_ALLOCATENEWSTOCK_STOCKRANGETO"
								   style="text-align:right"
								  value=""
								  styleId="stockRangeT"/>
					  </td>
					  <td width="22%" class="iCargoTableDataTd">
						  <ihtml:text property="noOfDocs"
								componentID= "TXT_STOCKCONTROL_DEFAULTS_ALLOCATENEWSTOCK_NUMEBROFDOCS"
								 style="text-align:right"
								styleId="noOfDocs"
								maxlength="8"
								readonly="true"
								value="0"/>
					  </td>
				</tr>
				<!-- templateRow ends-->
              </tbody>
            </table>
          </div>
			</div>
		</div>
		<div class="ic-row marginB5">
			<div class="ic-col-100">
				<div class="ic-input-container">
						<div class="ic-row">
							<div class="ic-input ic-right ic-split-45">	
								<label>
									<common:message key="stockholder.TotalnoofDocs"/>
								</label>
								<ihtml:text property="availableTotalnoofDocs"  style="text-align:right;"
								  componentID="TXT_STOCKCONTROL_DEFAULTS_ALLOCATENEWSTOCK_AVAILABLETOTALNUMBEROFDOCS"
								  readonly="true"
								  maxlength="8"
								  value="<%=String.valueOf(availTotalNoOfDocs)%>"/>
							</div>
							<div class="ic-input ic-right ic-split-10">	
							</div>
							<div class="ic-input ic-right ic-split-45">	
								<label>
									<common:message key="stockholder.TotalnoofDocs"/>
								</label>
								<ihtml:text property="allocatedTotalnoofDocs"  style="text-align:right;"
								   componentID="TXT_STOCKCONTROL_DEFAULTS_ALLOCATENEWSTOCK_ALLOACTEDTOTALNUMBEROFDOCS"
								   readonly="true"
								   maxlength="8"
								   value="<%=allocateNewStockForm.getAllocatedTotalnoofDocs()%>"/>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>	
		<div class="ic-foot-container">
			<div class="ic-row">
				<div class="ic-button-container paddR5">
					<ihtml:nbutton property="btnSave"
						componentID="BTN_STOCKCONTROL_DEFAULTS_ALLOCATENEWSTOCK_SAVE" accesskey="S" >
					<common:message key="allocatenewstock.save"/> </ihtml:nbutton>&nbsp;
					<ihtml:nbutton property="btnClose"
						componentID="BTN_STOCKCONTROL_DEFAULTS_ALLOCATENEWSTOCK_CLOSE" accesskey="O" >
					<common:message key="allocatenewstock.close"/> </ihtml:nbutton>
				</div>
			</div>
		</div>
  </div>
        
        
            
              
  </ihtml:form></div>
	
				
		
	</body>
</html:html>



