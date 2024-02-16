<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  SKCM - Stock Control
* File Name				:  MaintainStockRequest.jsp
* Date					:  02-Sep-2005
* Author(s)				:  Kirupakaran
*************************************************************************/
 --%>

<%@ page language="java" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

<%@ page import = "com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO" %>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.MaintainStockRequestForm" %>

<html:html>

<head>
		
	

<bean:define id="form"
	name="MaintainStockRequestForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.MaintainStockRequestForm"
	toScope="page" />

<title><common:message bundle="<%=form.getBundle()%>" key="maintainstockrequest.title"/></title>


<meta name="decorator" content="mainpanelrestyledui">

<common:include type="script"
	src="/js/stockcontrol/defaults/MaintainStockRequest_Script.jsp" />


</head>

<body id="bodyStyle">
	
		
	
		

<business:sessionBean id="options" moduleName="stockcontrol.defaults"
	screenID="stockcontrol.defaults.maintainstockrequest" method="get"
	attribute="documentTypes"/>

<business:sessionBean id="partnerAirlines"
	moduleName="stockcontrol.defaults"
	screenID="stockcontrol.defaults.maintainstockrequest"
	method="get"
	attribute="partnerAirlines"/>
	
<business:sessionBean id="stockHoldersFromSession"
	moduleName="stockcontrol.defaults"
	screenID="stockcontrol.defaults.maintainstockrequest"
	method="get"
	attribute="prioritizedStockHolders" />



<%
     MaintainStockRequestForm maintainStockRequestForm = (MaintainStockRequestForm)request.getAttribute("MaintainStockRequestForm");

%>

<div class="iCargoContent ic-masterbg">
<ihtml:form action="stockcontrol.defaults.screenloadmaintainstockreq.do">
<input type="hidden" name="currentDialogId" />
<input type="hidden" name="currentDialogOption" />
<html:hidden property="partnerPrefix" />
<html:hidden property="buttonStatusFlag" />
<ihtml:hidden property="fromStockRequestList" />
<html:hidden property="mode"/>
<div class="ic-content-main">
	<div class="ic-head-container">
		<!--<div class="ic-row">
			<h4><common:message key="liststockrequest.SearchCriteria" /></h4>
		</div>-->
		<div class="ic-filter-panel">		
            <div class="ic-row"><h4><common:message key="liststockrequest.SearchCriteria" /></h4></div>
			<div class="ic-row ">
				<div class="ic-input-container">
					<div class="ic-col-100">
						<div class="ic-input ic-split-30">								
							<label>
								<common:message key="stockholder.ReqRefNo"/>
							</label>	
							<ihtml:text property="reqRefNo"
							componentID="TXT_STOCKCONTROL_DEFAULTS_MAINTAINSTOCKREQUEST_REQUESTREFERENCENUMBER"
								readonly="false"
							maxlength="20"/>
						</div>
						<div class="ic-input ic-split-30">								
							<label>
								<common:message key="stockholder.Status"/>
							</label>	
							<ihtml:text property="status"
							componentID="CMB_STOCKCONTROL_DEFAULTS_MAINTAINSTOCKREQUEST_STATUS"
							readonly="true" maxlength="12"/>
						</div>
						<div class="ic-input ic-split-40">
							<div class="ic-button-container">
								<ihtml:nbutton property="btnList"  componentID="BTN_STOCKCONTROL_DEFAULTS_MAINTAINSTOCKREQUEST_LIST" accesskey="L" >
					  <common:message key="maintainstockrequest.list"/></ihtml:nbutton>			
					
								 <ihtml:nbutton property="btnClear"  componentID="BTN_STOCKCONTROL_DEFAULTS_MAINTAINSTOCKREQUEST_CLEAR" accesskey="C">
								 <common:message key="maintainstockrequest.clear"/></ihtml:nbutton>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="ic-main-container">
		<div class="ic-row ic-input-round-border">
			<div class="ic-input-container">
				<div class="ic-row">
          <common:xgroup>
               <common:xsubgroup id="S7_SPECIFIC">
					<div class="ic-input ic-split-20 ic-label-30 ic-mandatory">								
						<label>
							<common:message key="stockholder.DateofReq"/>
						</label>	
						<ibusiness:calendar property="dateOfReq"
						id="dateOfReq"
						type="image"
						textStyleClass="iCargoTextFieldMedium"
						title="Date of Request"
							componentID="TXT_STOCKCONTROL_DEFAULTS_MAINTAINSTOCKREQUEST_REQDATE"
						maxlength="11"
							readonly="true"/>
							</div>		
               </common:xsubgroup>
               </common:xgroup >	
					   
          <common:xgroup>
               <common:xsubgroup id="RESTRICT_S7">
                           <div class="ic-input ic-split-33 ic-label-30 ic-mandatory"> 
						     <label>
							    <common:message key="stockholder.DateofReq"/>
						     </label>
                        <ibusiness:calendar property="dateOfReq"
						id="dateOfReq"
						type="image"
						textStyleClass="iCargoTextFieldMedium"
						title="Date of Request"
							componentID="TXT_STOCKCONTROL_DEFAULTS_MAINTAINSTOCKREQUEST_REQDATE"
						maxlength="11"
							readonly="false"/>
					</div>
               </common:xsubgroup>
          </common:xgroup >
					<div class="ic-input ic-split-20 "><div class="ic-center  marginB5">								
						<logic:present name="options">
						  <bean:define name="options" id="list" type="java.util.HashMap"/>
							<!--Modified by A-1927 @ NRT on 13-Jul-2007 for NCA Bug Fix starts-->
							<ibusiness:dynamicoptionlist  collection="list"
								id="docType"
								firstlistname="docType"
								componentID="TXT_STOCKCONTROL_DEFAULTS_MAINTAINSTOCKREQUEST_DYNAMICOPTIONLIST"
								lastlistname="subType" firstoptionlabel="Doc. Type"
								lastoptionlabel="Sub Type"
								optionstyleclass="iCargoMediumComboBox"
								labelstyleclass="iCargoLabelRightAligned"
							    firstselectedvalue="<%=maintainStockRequestForm.getDocType()%>"
							    lastselectedvalue="<%=maintainStockRequestForm.getSubType()%>"
							    docTypeTitle="doctype.tooltip"
							    subDocTypeTitle="subdoctype.tooltip"/>
							 <!--Modified by A-1927 @ NRT on 13-Jul-2007 for NCA Bug Fix ends-->
						   <!--<business:dynamicoptionlist collection="list"
						   firstlistname="docType" lastlistname="subType"
						   firstoptionlabel="Doc Type"  lastoptionlabel="Sub Type"
						   optionstyleclass="iCargoMediumComboBox"
						   labelstyleclass="iCargoLabelRightAligned"
						   firstselectedvalue="<%=maintainStockRequestForm.getDocType()%>"
						   lastselectedvalue="<%=maintainStockRequestForm.getSubType()%>"/>-->
						</logic:present>
                        </div></div>
					<div class="ic-input ic-split-20 ic-label-30  ic-mandatory">								
						<label>
							<common:message key="stockholder.ReqStock"/>
						</label>	
						<ihtml:text property="reqStock"
						componentID="TXT_STOCKCONTROL_DEFAULTS_MAINTAINSTOCKREQUEST_REQUESTEDSTOCK"
						maxlength="8"/>
					</div>
						<div class="ic-input ic-split-20 ic-label-30 ic-mandatory">								
						<label>
							<common:message key="stockholder.StockHolderType"/>
						</label>	
							<ihtml:select property="stockHolderType" componentID="CMB_STOCKCONTROL_DEFAULTS_MAINTAINSTOCKREQUEST_STOCKHOLDERTYPE" value="<%=maintainStockRequestForm.getStockHolderType()%>" >
							<logic:present name="stockHoldersFromSession">
							<bean:define id="stockHolderList" name="stockHoldersFromSession" />
							<html:option value=""><common:message key="combo.select"/></html:option>
							<logic:iterate id="priorityVO" name="stockHolderList" >
							<html:option value= "<%=((StockHolderPriorityVO)priorityVO).getStockHolderType()%>">
							<%=((StockHolderPriorityVO)priorityVO).getStockHolderDescription()%>
								</html:option>
							</logic:iterate>
							</logic:present>
							</ihtml:select>
					</div>
					<div class="ic-input ic-split-20 ic-label-30 ic-mandatory">								
						<label>
							<common:message key="stockholder.StockHolderCode"/>
						</label>	
							<ihtml:text property="code"
						   componentID="TXT_STOCKCONTROL_DEFAULTS_MAINTAINSTOCKREQUEST_STOCKHOLDERCODE"
						   maxlength="12" />
						   <div class= "lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" 
				onclick="displayStockHolderLov('stockcontrol.defaults.screenloadstockholderlov.do');"/></div>
					</div>
				</div>	
				<div class="ic-row">
					<div class="ic-input ic-split-20  ic-label-30 ic_inline_chcekbox marginT15">
						<label>
							<common:message key="stockholder.Manual"/>
						</label>	
						<html:checkbox property="manual" title="Manual"/>
					</div>
			
					<div class="ic-input ic-split-20  ic-label-30 ic_inline_chcekbox marginT15">									
						<label>
							<common:message key="stockcontrol.defaults.maintainstockrequest.partnerAirlines.lbl" />
						</label>	
						<ihtml:checkbox property="partnerAirline" componentID="CHK_STOCKCONTROL_DEFAULTS_CREATESTOCK_PRTARL_CHK" />
					</div>
					<div class="ic-input ic-label-45 ic-split-20">									
						<label>
							<common:message key="stockcontrol.defaults.maintainstockrequest.awbPrefix.lbl" />
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
					<div class="ic-input ic-label-20 ic-split-20">									
						<label>
							<common:message key="stockcontrol.defaults.maintainstockrequest.partnerAirlines.lbl"/>
						</label>
						<ihtml:text property="airlineName" componentID= "CHK_STOCKCONTROL_DEFAULTS_CREATESTOCK_PRTARL" readonly="true"/>
					</div>
						<div class="ic-input ic-label-30 ic-split-20">								
						<label>
							<common:message key="stockholder.Remarks"/>
						</label>	
						 <ihtml:textarea property="remarks"
						cols="60" rows="2" style="width:230px;height:35px"
						componentID="TXT_STOCKCONTROL_DEFAULTS_MAINTAINSTOCKREQUEST_REMARKS"						
						maxlength="250"></ihtml:textarea>
					</div>
				</div>
			</div>	
		</div>
		<div class="ic-row ic-input-round-border">
			<div class="ic-col-100">
				<div class="ic-input ic-split-50">								
					<label>
						<common:message key="stockholder.AllocatedStock"/>
					</label>	
					<ihtml:text property="allocatedStock" componentID="TXT_STOCKCONTROL_DEFAULTS_MAINTAINSTOCKREQUEST_ALLOCATEDSTOCK" readonly="true"
		maxlength="8"/>
				</div>
				<div class="ic-input ic-split-50">								
					<label>
						<common:message key="stockholder.AppRejRemarks"/>
					</label>	
					<ihtml:textarea property="appRejRemarks" cols="60" rows="2"
					componentID="TXT_STOCKCONTROL_DEFAULTS_MAINTAINSTOCKREQUEST_APPREJREMARKS"
					readonly="true">
					</ihtml:textarea>
				</div>
			</div>
		</div>
	</div>
	<div class="ic-foot-container">
		<div class="ic-row">
			<div class="ic-button-container">
				<ihtml:nbutton property="btnSave"  componentID="BTN_STOCKCONTROL_DEFAULTS_MAINTAINSTOCKREQUEST_SAVE" accesskey="S" ><common:message key="maintainstockrequest.save"/></ihtml:nbutton>          

              <ihtml:nbutton property="btnClose" componentID="BTN_STOCKCONTROL_DEFAULTS_MAINTAINSTOCKREQUEST_CLOSE" accesskey="O" ><common:message key="maintainstockrequest.close"/></ihtml:nbutton>
			</div>
		</div>
	</div>
</div>

  </ihtml:form>
</div>
	</body>
</html:html>

