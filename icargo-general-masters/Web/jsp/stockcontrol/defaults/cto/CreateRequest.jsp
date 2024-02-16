<%--
* Project	 		: iCargo
* Module Code & Name: STK & StockControl
* File Name			: CreateRequest.jsp
* Date				: 30-09-2015
* Author(s)			: A-5153
 --%>
 
<%@ include file="/jsp/includes/tlds.jsp" %>



<html:html>

	<head>
		
		
		<bean:define id="form" name="CreateStockRequestForm" toScope="page" type="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.CreateStockRequestForm"/>

		<title><common:message bundle="createrequestresources" key="createstock.page.title"/></title>
		<meta name="decorator" content="popuppanelrestyledui">
		<common:include type="script" src="/js/stockcontrol/defaults/cto/CreateStock_Script.jsp"/>
	</head>
	
	<body style="overflow:auto;">
	
	
	<ibusiness:sessionBean id="documentValues" moduleName="stockcontrol.defaults" screenID="stockcontrol.defaults.cto.createstockrequest" method="get" attribute="documentValues" />
	<ibusiness:sessionBean id="onetimeValues" moduleName="stockcontrol.defaults" screenID="stockcontrol.defaults.cto.createstockrequest" method="get" attribute="onetimeValues" />

	
	<div class="iCargoPopUpContent" style="width:100%; overflow:auto;">
		<ihtml:form action="/stockcontrol.defaults.cto.createstockrequestscreenload.do" styleClass="ic-main-form">
      		<ihtml:hidden  property="afterSave" />
	
		<div class="ic-content-main">
			<span class="ic-page-title ic-display-none">
				<common:message key="stockcontrol.createstockrequest.title"/>
			</span>
			<div class="ic-head-container">
				<div class="ic-input-container ic-input-round-border">
					<div class="ic-row">
						<div class="ic-input ic-split-15 ic-mandatory ic-label-45">
							<label>
								<common:message key="stockcontrol.createstockrequest.airline"/>
							</label>
							<ihtml:text property="airline" componentID="CMP_Stock_Defaults_CreateRequest_Airline" />
						</div>
						<div class="ic-input ic-split-85">
                            <div class="ic-input-round-border marginT10" style="wudth:450px">
                                <div class="inline_filedset marginT5 marginB5">
							<logic:present name="documentValues">
								<bean:define id="map" name="documentValues" type="java.util.HashMap" toScope="page"/>
								<ibusiness:dynamicoptionlist componentID= "CMP_StockControl_Defaults_CreateRequest_DynamicOptionList"
										id=""
										collection="map"
										firstlistname="documentType"
										lastlistname="docSubType"
										firstoptionlabel="Doc Type"
										lastoptionlabel="Doc SubType"
										firstselectedvalue="<%=form.getDocumentType()%>"
										lastselectedvalue="<%=form.getDocSubType()%>"
										labelstyleclass="iCargoLabelRightAligned"
										optionstyleclass="iCargoBigComboBox"
										docTypeTitle="stockcontrol.defaults.createstock.tooltip.doctype"
										subDocTypeTitle="stockcontrol.defaults.createstock.tooltip.docsubtype"/>
							</logic:present>
						</div>
					</div>
				</div>
			</div>
				</div>
			</div>
			<div class="ic-main-container">
				<div class="ic-input-container ic-input-round-border">
					<div class="ic-row">
						<div class="ic-input ic-split-30 ic-label-55">
							<label>
								<common:message key="stockcontrol.createstockrequest.mode"/>
							</label>
							<html:select property="modeOfCommunication" styleClass="iCargoMediumComboBox">
								<logic:present name="onetimeValues">
									<logic:iterate id="onetimeValue" name="onetimeValues">
										<bean:define id="fieldValue" name="onetimeValue" property="fieldValue"/>
										<bean:define id="fieldDescription" name="onetimeValue" property="fieldDescription"/>
										<html:option value="<%=(String)fieldValue%>"><%=(String)fieldDescription%></html:option>
									</logic:iterate>
								</logic:present>
							</html:select>
						</div>
						<div class="ic-input ic-split-55 ic-mandatory ic-label-20">
							<label>
								<common:message key="stockcontrol.createstockrequest.address"/>
							</label>
							<ihtml:text property="address" componentID="CMP_Stock_Defaults_CreateRequest_Address" />
						</div>
					</div>
					<div class="ic-row">
						<div class="ic-input ic-split-100 ic-label-20">
							<label>
								<common:message key="stockcontrol.createstockrequest.content"/>
							</label>
							<ihtml:textarea property="content" cols="52" rows="2"/>
						</div>
					</div>
				</div>
			</div>
			<div class="ic-foot-container">
				<div class="ic-row">
					<div class="ic-button-container">
						<ihtml:button property="btOK" componentID="CMP_Stock_Defaults_CreateRequest_OK">
							<common:message key="stockcontrol.createstockrequest.ok"/>
						</ihtml:button>
						<ihtml:button property="btClose" componentID="CMP_Stock_Defaults_CreateRequest_Close">
							<common:message key="stockcontrol.createstockrequest.close"/>
						</ihtml:button>
					</div>
				</div>
			</div>
		</div>
	
	
		</ihtml:form>
	</div>
	
		
	</body>
</html:html>



