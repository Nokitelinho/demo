<%--
* Project 			 : iCargo
* Module Code & Name : CustomerManagement-Defaults
* File Name 		 : MaintainCustomerRegistration_Ajax.jsp
* Date 				 : 27-August-2014
* Author(S) 		 : A-5163
 --%>

<%@page language="java"%>
<%@include file="/jsp/includes/tlds.jsp"%>
<%@include file="/jsp/includes/ajaxPageHeader.jsp"%>

	<bean:define id="form" name="MaintainCustomerRegistrationForm" type="com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.MaintainRegCustomerForm" toScope="page" />
	<business:sessionBean id="customerVO" moduleName="customermanagement.defaults" screenID="customermanagement.defaults.maintainregcustomer" method="get" attribute="customerVO"/>	
	<ihtml:form action="customermanagement.defaults.onscreenloadcustomerauditversions.do">
		<div id="recreatedCustomerAuditVersions">
			<logic:present name="customerVO" property="isHistoryPopulated">
				<bean:define id="isHistoryPresent" name="customerVO" property="isHistoryPopulated"/>
				<logic:equal name="isHistoryPresent" value="Y">
					<div class="ic-row">
						<div class="ic-input ic-split-50" >
							<label>
								<common:message key="customermanagement.defaults.customerregistration.version"/>
							</label>
							</div>
							<div class="ic-input ic-split-50" >
							<common:directlinknavigationtag
								pageURL="/customermanagement.defaults.relistcustomerdetailshistory.do?statusFlag=LNKNAV"
								displayPage="<%=form.getDisplayPopupPage()%>"
								totalRecords="<%=form.getTotalViewRecords()%>"/>
						</div>
					</div>						
				</logic:equal>		
			</logic:present>
		</div>		
	</ihtml:form>
	
<%@include file="/jsp/includes/ajaxPageFooter.jsp"%>