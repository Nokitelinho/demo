<%--
********************************************************************
* Project	 		: iCargo
* Module Code & Name  : Customer Management
* File Name			: MaintainCustomerRegistration_IACCCSFDetails.jsp
* Date				: 26-May-2009
* Author(s)			: A-3045
********************************************************************
--%>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.MaintainRegCustomerForm"%>
<%@ page import ="java.util.ArrayList"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>

<business:sessionBean id="customerVO"
	moduleName="customermanagement.defaults"
	screenID="customermanagement.defaults.maintainregcustomer"
	method="get" attribute="customerVO"/>
<business:sessionBean id="customerCertificateTypes"
	moduleName="customermanagement.defaults"
	screenID="customermanagement.defaults.maintainregcustomer"
    method="get" attribute="certificateTypes"/>
<bean:define id="form"
	name="MaintainCustomerRegistrationForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.MaintainRegCustomerForm"
	toScope="page" />
	 
	<div class="ic-row">		
		<div class="ic-col-50">
		<div class="ic-section ic-pad-2">
		<div class="ic-row">
		<div class="ic-button-container">
			<ul class="ic-list-link">
				<li>
					<a class="iCargoLink" href="#" id="addCertifications">
						<common:message key="customermanagement.defaults.customerregistration.certifications.lnk.add"/>
					</a>
				</li>|
				<li>
					<a class="iCargoLink" href="#" id="deleteCertifications">
						<common:message key="customermanagement.defaults.customerregistration.certifications.lnk.delete"/>
					</a> 
				</li>
			</ul>			
		</div>
		</div>
		<div class="ic-row">
		<div class="tableContainer" style="height:150px;">
			<table id="CertificationDetailsTable" class="fixed-header-table">
				<thead>
					<tr class="iCargoTableHeadingLeft">
						<td style="width:6%" class="iCargoTableHeaderLabel ic-center">
							<input type="checkbox" name="checkAllCertificates" onclick="updateHeaderCheckBox(this.form, this, this.form.rowIdCertifications);"/>
						</td>
						<td style="width:25%" class="iCargoTableHeaderLabel">
							<common:message key="customermanagement.defaults.customerregistration.lbl.iacccsf.certificatetype"/>
						</td>
						<td style="width:23%" class="iCargoTableHeaderLabel">
							<common:message key="customermanagement.defaults.customerregistration.lbl.iacccsf.certificatenumber"/>
						</td>
						<td style="width:23%" class="iCargoTableHeaderLabel">
							<common:message key="customermanagement.defaults.customerregistration.lbl.iacccsf.validfrom"/>
						</td>
						<td style="width:23%" class="iCargoTableHeaderLabel">
							<common:message key="customermanagement.defaults.customerregistration.lbl.iacccsf.validtill"/>
						</td>
						</tr>
					</thead>
					<tbody id="certificateDetailsTableBody">
						<logic:present name="customerCertificateTypes">
							<bean:define id="certificateTypes" name="customerCertificateTypes"/>
						</logic:present>
						<logic:present name="customerVO" property="customerCertificateDetails">
							<bean:define id="customerCertificateVOs" name="customerVO"  property="customerCertificateDetails"/>
							<logic:iterate id="customerCertificateVO" name="customerCertificateVOs" 
								indexId="nIndex" type="com.ibsplc.icargo.business.shared.customer.vo.CustomerCertificateVO">
								<logic:present name="customerCertificateVO" property="certificateType">
									<bean:define id="certificateType" name="customerCertificateVO" 
										property="certificateType" type="java.lang.String"/>
								</logic:present>
								<logic:notPresent name="customerCertificateVO" property="certificateType">
									<bean:define id="certificateType" type="java.lang.String" value="NA"/>
								</logic:notPresent>
								<logic:notEqual name="certificateType" value="CSF">

									<logic:present name="customerCertificateVO" property="operationFlag">
										<bean:define id="certificateOpFlag" name="customerCertificateVO" property="operationFlag"/>
									</logic:present>
									<logic:notPresent name="customerCertificateVO" property="operationFlag">
										<bean:define id="certificateOpFlag"  value="NA"/>
									</logic:notPresent>
									<bean:define id="certificateSeqNumber" name="customerCertificateVO" property="sequenceNumber"/>
									<ihtml:hidden property="certificateSequenceNumber" value="<%=String.valueOf(certificateSeqNumber)%>"/>
									<logic:notMatch name="certificateOpFlag" value="D">
										<logic:match name="certificateOpFlag" value="I">
											<ihtml:hidden property="hiddenOpFlagForCertificate" value="I"/>
										</logic:match>
										<logic:notMatch name="certificateOpFlag" value="I">
											<ihtml:hidden property="hiddenOpFlagForCertificate" value="U"/>
										</logic:notMatch>
									<tr>
										<td class="iCargoTableDataTd ic-center">
											<ihtml:checkbox property="rowIdCertifications" onclick="toggleTableHeaderCheckbox('rowIdCertifications', this.form.checkAllCertificates);" value="<%=String.valueOf(nIndex)%>"/>
										</td>
										<td class="iCargoTableDataTd">
											<logic:present name="customerCertificateVO" property="certificateType">
												<bean:define id="certificateType" name="customerCertificateVO" 
													property="certificateType" type="java.lang.String"/>
												<ihtml:select property="customerCertificateType" value="<%=(String)certificateType%>" componentID="CMB_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CERTIFICATE_TYPE" indexId="nIndex">
													<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
													<logic:present name="certificateTypes">
														<ihtml:options collection="certificateTypes" 
															property="fieldValue" labelProperty="fieldDescription"/>
													</logic:present>
												</ihtml:select>
											</logic:present>
											<logic:notPresent name="customerCertificateVO" property="certificateType">
												<ihtml:select property="customerCertificateType" componentID="CMB_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CERTIFICATE_TYPE" indexId="nIndex">
													<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
													<logic:present name="certificateTypes">
														<ihtml:options collection="certificateTypes" 
															property="fieldValue" labelProperty="fieldDescription"/>
													</logic:present>
												</ihtml:select>
											</logic:notPresent>
										</td>
										<td class="iCargoTableDataTd">
											<logic:present name="customerCertificateVO" property="certificateNumber">
												<bean:define id="certificateNum" name="customerCertificateVO" property="certificateNumber"/>												
												<ihtml:text property="customerCertificateNumber" maxlength="50" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CERTIFICATE_NUMBER" value="<%=String.valueOf(certificateNum)%>" indexId="nIndex"/>
											</logic:present>
											<logic:notPresent name="customerCertificateVO" property="certificateNumber">
												<ihtml:text property="customerCertificateNumber" maxlength="50" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CERTIFICATE_NUMBER" indexId="nIndex" value=""/>
											</logic:notPresent>
										</td>
										<td class="iCargoTableDataTd">
											<logic:present name="customerCertificateVO" property="validityStartDate">
												<bean:define id="validFrom" name="customerCertificateVO" 
													property="validityStartDate" type="LocalDate"/>
												<%String formattedValidFrom = validFrom.toDisplayFormat("dd-MMM-yyyy");%>
												<ibusiness:calendar id="certificateValidFrom" property="certificateValidFrom" componentID="CAL_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CERTIFICATE_VALIDFROM" indexId="nIndex" value="<%=formattedValidFrom%>" type="image"/>
											</logic:present>
											<logic:notPresent name="customerCertificateVO" property="validityStartDate">
												<ibusiness:calendar id="certificateValidFrom" property="certificateValidFrom" componentID="CAL_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CERTIFICATE_VALIDFROM" indexId="nIndex" value="" type="image"/>
											</logic:notPresent>
										</td>
										<td class="iCargoTableDataTd">
											<logic:present name="customerCertificateVO" property="validityEndDate">
												<bean:define id="validTo" name="customerCertificateVO" 
													property="validityEndDate" type="LocalDate"/>
												<%String formattedValidTo = validTo.toDisplayFormat("dd-MMM-yyyy");%>
												<ibusiness:calendar id="certificateValidTo" property="certificateValidTo" componentID="CAL_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CERTIFICATE_VALIDTO" indexId="nIndex" value="<%=formattedValidTo%>" type="image"/>
											</logic:present>
											<logic:notPresent name="customerCertificateVO" property="validityEndDate">
												<ibusiness:calendar id="certificateValidTo" property="certificateValidTo" componentID="CAL_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CERTIFICATE_VALIDTO" indexId="nIndex" value="" type="image"/>
											</logic:notPresent>
										</td>
									</tr>
									</logic:notMatch>
									<logic:match name="certificateOpFlag" value="D" >
										<ihtml:hidden property="hiddenOpFlagForCertificate" value="D"/>
										<ihtml:hidden property="customerCertificateType" value=""/>
										<ihtml:hidden property="customerCertificateNumber" value=""/>
										<ihtml:hidden property="certificateValidFrom" value=""/>
										<ihtml:hidden property="certificateValidTo" value=""/>
										<logic:present name="customerCertificateVO" property="certificateType">
											<bean:define id="certificateType" name="customerCertificateVO" 
												property="certificateType" type="java.lang.String"/>
											<ihtml:hidden property="customerCertificateType" value="<%=(String)certificateType%>"/>
										</logic:present>
										<logic:present name="customerCertificateVO" property="certificateNumber">
											<bean:define id="certificateNum" name="customerCertificateVO" property="certificateNumber"/>
											<ihtml:hidden property="customerCertificateNumber" value="<%=(String)certificateNum%>"/>
										</logic:present>
										<logic:present name="customerCertificateVO" property="validityStartDate">
											<bean:define id="validFrom" name="customerCertificateVO" 
												property="validityStartDate" type="LocalDate"/>
											<%String formattedValidFrom = validFrom.toDisplayFormat("dd-MMM-yyyy");%>
											<ihtml:hidden property="certificateValidFrom" value="<%=formattedValidFrom %>"/>
										</logic:present>
										<logic:present name="customerCertificateVO" property="validityEndDate">
											<bean:define id="validTo" name="customerCertificateVO" property="validityEndDate" type="LocalDate"/>
											<%String formattedValidTo = validTo.toDisplayFormat("dd-MMM-yyyy");%>
											<ihtml:hidden property="certificateValidTo" value="<%=formattedValidTo %>"/>
										</logic:present>
									</logic:match>			

								</logic:notEqual>
							</logic:iterate>
						</logic:present>						
						<!--Template Row Starts-->
						<tr template="true" style="display:none" id="certificateDetailsTemplateRow">
							<ihtml:hidden property="hiddenOpFlagForCertificate" value="NOOP"/>
							<td class="iCargoTableDataTd ic-center">
								<ihtml:checkbox property="rowIdCertifications" 
onclick="toggleTableHeaderCheckbox('rowIdCertifications', this.form.checkAllCertificates);"  style="text-align:center"/>
							</td>
							<td class="iCargoTableDataTd">								
								<ihtml:select property="customerCertificateType" componentID="CMB_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CERTIFICATE_TYPE" indexId="nIndex">
									<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
									<logic:present name="certificateTypes">
										<ihtml:options collection="certificateTypes" property="fieldValue" labelProperty="fieldDescription"/>
									</logic:present>
								</ihtml:select>								
							</td>
							<td class="iCargoTableDataTd">
								<ihtml:text property="customerCertificateNumber" maxlength="50" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CERTIFICATE_NUMBER" value=""/>
							</td>
							<td class="iCargoTableDataTd">
								<ibusiness:calendar id="certificateValidFrom" property="certificateValidFrom" componentID="CAL_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CERTIFICATE_VALIDFROM" value="" type="image"/>
							</td>
							<td class="iCargoTableDataTd">
								<ibusiness:calendar id="certificateValidTo" property="certificateValidTo" componentID="CAL_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CERTIFICATE_VALIDTO" value="" type="image"/>
							</td>
						</tr>
						<!--Template Row Ends--->
					</tbody>
			</table>
			</div>
			</div>
			</div>
		</div>
		
																		



								   
																		



								   
				</div>