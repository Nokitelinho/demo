<%--
* Project	 		: iCargo
* Module Code & Name:
* File Name			:
* Date				: 26-Oct-2015
* Author(s)			: A-6767
 --%>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.AgreementNoLovForm" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<html:html>
<head>
<meta name="decorator" content="popuppanelrestyledui" >
<common:include type="script" src="/js/uld/defaults/AgreementNoLov_Script.jsp"/>
</head>
<body >
	
<bean:define id="form"
	name="agreementNoLovForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.AgreementNoLovForm"
	toScope="page" />

<logic:present name="agreementNoLovForm" property="pageAgreementLov">
		<bean:define id="pageAgreementLov"
		name="agreementNoLovForm"  property="pageAgreementLov"
		toScope="page" />
</logic:present>

<div class="iCargoPopUpContent">
	<ihtml:form action="/uld.defaults.screenloadagreementnolov.do" styleclass="ic-main-form">
		<ihtml:hidden property="lastPageNumber"/>
		<ihtml:hidden property="displayPage"/>
		<div class="ic-content-main">
			<span class="ic-page-title ic-display-none">
				<common:message key="uld.defaults.agreementlov.agreementnumbers" scope="request"/>
				</span>					
				<div class="ic-head-container">	
					<div class="ic-filter-panel">
						<div class="ic-input-container ">
							<div class="ic-row">
								<div class="ic-input ic-split-35px ic-right">
									<label>
										<common:message key="uld.defaults.agreementlov.agreementnumber" scope="request"/>
									</label>
										<ihtml:text property="agreementNo" componentID="TXT_ULD_DEFAULTS_AGREEMENTLOV_AGREEMENTNUMBER" style="text-transform:uppercase" maxlength="12"/>			
								</div>	
								<div class="ic-input ic-split-30px ic-center">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								</div>	
								
								
								<div class="ic-input ic-split-50px ">
									<label>
										<common:message key="uld.defaults.agreementlov.partycode" scope="request"/>
									</label>
										<ihtml:text property="partyCode" componentID="TXT_ULD_DEFAULTS_AGREEMENTLOV_PARTYCODE" style="text-transform:uppercase" maxlength="12"/>	
								</div>	
							</div>
							<div class="ic-row">
								<div class="ic-input ic-col-30">
									<label>
										<common:message key="uld.defaults.agreementlov.partyname" scope="request"/>
									</label>
									<div>
									<ihtml:text property="partyName" componentID="TXT_ULD_DEFAULTS_AGREEMENTLOV_PARTYNAME" style="text-transform:uppercase" maxlength="12"/>		
								</div>
							</div>
							</div>
							<div class="ic-row">
								<div class="ic-col-100">
									<div class="ic-button-container">
										<ihtml:nbutton property="btList" componentID="BTN_ULD_DEFAULTS_AGREEMENTLOV_LIST" >
											<common:message key="uld.defaults.agreementlov.btn.btlist" />
										</ihtml:nbutton>
										<ihtml:nbutton property="btClear" componentID="BTN_ULD_DEFAULTS_AGREEMENTLOV_CLEAR"  >
											<common:message key="uld.defaults.agreementlov.btn.btclear" />
										</ihtml:nbutton>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="ic-main-container">
					<div class="ic-row">
						<div class="ic-col-40">
							<logic:present name="pageAgreementLov">
								<common:paginationTag
								pageURL="uld.defaults.screenloadagreementnolov.do"
								name="pageAgreementLov"
								display="label"
								labelStyleClass="iCargoResultsLabel"
								lastPageNum="<%=form.getLastPageNumber() %>" />
							</logic:present>
						</div>
						<div class="ic-col-60 ic-right">
							<logic:present name="pageAgreementLov">
								<common:paginationTag
								pageURL="javascript:callFunction('lastPageNum','displayPage')"
								name="pageAgreementLov"
								display="pages"
								linkStyleClass="iCargoLink"
								 disabledLinkStyleClass="iCargoLink"
								lastPageNum="<%=form.getLastPageNumber()%>" />
							</logic:present>							
						</div>
					</div>
					<div class="ic-row">
						<div class="tableContainer"  id="div1" style="width:100%;height:260px"> 
							<table class="fixed-header-table">
								<thead>
									<tr class="iCargoTableHeadingCenter">
									<td width="7%" class="iCargoLabelleftAligned"></td>
									<td width="30%" class="iCargoLabelleftAligned"><common:message key="uld.defaults.agreementlov.tbagreementnumber" scope="request"/></td>
									<td width="30%" class="iCargoLabelLeftAligned"><common:message key="uld.defaults.agreementlov.tbpartycode" scope="request"/></td>
									<td width="30%" class="iCargoLabelLeftAligned"><common:message key="uld.defaults.agreementlov.tbpartyname" scope="request"/></td>
									</tr>
								</thead>
							   <tbody>
									<logic:present name="pageAgreementLov">
										<logic:iterate id="ULDAgreementVO" name="pageAgreementLov" indexId="nIndex">
												<bean:define id="name" name="ULDAgreementVO" property="agreementNumber" />
											<tr ondblclick="setValueOnDoubleClick('<%=name%>','<%=name%>','agreementNumber','agreementNumber',0)" />
												<td  class="iCargoTableDataTd ic-center">
													<ihtml:checkbox property="numChecked" value="<%=(String)name%>" onclick="singleSelect(this)" styleId="<%=(String)name%>"/>
												</td>
												<td width="20%" class="iCargoTableDataTd">
													<bean:write name="ULDAgreementVO" property="agreementNumber"/>
												</td>
												<td width="30%" class="iCargoTableDataTd">
													<bean:write name="ULDAgreementVO" property="partyCode"/>
												</td>
												<td width="40%" class="iCargoTableDataTd">
													<bean:write name="ULDAgreementVO" property="partyName"/>
												</td>
											</tr>
						
										</logic:iterate>
									</logic:present>
									<logic:notPresent name="pageAgreementLov">
										
									</logic:notPresent>
								</tbody>
							</table>
						</div>		
					</div>
				</div>
				<div class="ic-foot-container">
					<div class="ic-button-container">
						<ihtml:button property="btok" styleClass="iCargoButtonSmall" value="OK">
						</ihtml:button>
						<ihtml:button property="btclose" styleClass="iCargoButtonSmall" value="Close">
						</ihtml:button>
					</div>
				</div>
		</div>
	</ihtml:form>
</div>
		   <jsp:include page="/jsp/includes/popupFooterSection.jsp"/>
		   <logic:present name="icargo.uilayout">
		       <logic:equal name="icargo.uilayout" value="true">
		       <jsp:include page="/jsp/includes/popupfooter_new_ui.jsp" />
		       </logic:equal>

		       <logic:notEqual name="icargo.uilayout" value="true">
		       <jsp:include page="/jsp/includes/popupfooter_new.jsp" />
		       </logic:notEqual>
		   </logic:present>
		   <common:registerCharts/>
		   <common:registerEvent />
	</body>

</html:html>

