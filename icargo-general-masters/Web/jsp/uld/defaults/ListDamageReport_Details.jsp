<%--
/***********************************************************************
* Project       	 :  iCargo
* Module Code & Name 	 :  Uld
* File Name     	 :  ListDamageReport.jsp
* Date          	 :  06-February-2006
* Author(s)     	 :  Ayswarya.V.Thampy,a-6806
*************************************************************************/
 --%>

<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import = "java.util.Calendar" %>
<%@ page import = "com.ibsplc.icargo.framework.util.FormatConverter" %>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListDamageReportForm" %>
<%@ include file="/jsp/includes/tlds.jsp" %>


<bean:define id="form"
		 name="ListDamageReportForm"
		 type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListDamageReportForm"
		 toScope="request" />

<business:sessionBean id="RepairStatusCollection"
			  moduleName="uld.defaults"
			  screenID="uld.defaults.listdamagereport"
			  method="get"
			  attribute="RepairStatus" />

<business:sessionBean id="StatusCollection"
			  moduleName="uld.defaults"
			  screenID="uld.defaults.listdamagereport"
			  method="get"
			  attribute="UldStatus" />

<business:sessionBean id="DamageStatusCollections"
			  moduleName="uld.defaults"
			  screenID="uld.defaults.listdamagereport"
			  method="get"
			  attribute="DamageStatus" />

<business:sessionBean id="CollectionULDDamageRepairDetailsVOs"
			  moduleName="uld.defaults"
			  screenID="uld.defaults.listdamagereport"
			  method="get"
			  attribute="ULDDamageRepairDetailsVOs" />

<business:sessionBean id="DamageFilterVO"
			  moduleName="uld.defaults"
			  screenID="uld.defaults.listdamagereport"
			  method="get"
			  attribute="ULDDamageFilterVO" />

<business:sessionBean id="facilityTypeCollections"
			  moduleName="uld.defaults"
			  screenID="uld.defaults.listdamagereport"
			  method="get"
			  attribute="FacilityType" />

<business:sessionBean id="partyTypeCollections"
			  moduleName="uld.defaults"
			  screenID="uld.defaults.listdamagereport"
			  method="get"
			  attribute="PartyType" />
			  
	<div class="ic-row">
		<div class="ic-input ic-split-25 ic-label-40">
			<label>
				<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.uldno" scope="request"/>
			</label>
			<logic:present name="DamageFilterVO" property="uldNumber" >
				<bean:define id="uldNumber" name="DamageFilterVO" property="uldNumber"/>
				<ibusiness:uld id="uldno" uldProperty="uldNo" uldValue="<%=uldNumber.toString()%>"  componentID="ULD_DEFAULTS_ULDNO" style="text-transform: uppercase" maxlength="12" tabindex="1"/>
			</logic:present>
			<logic:notPresent name="DamageFilterVO" property="uldNumber" >
				<ibusiness:uld id="uldno" uldProperty="uldNo" componentID="ULD_DEFAULTS_ULDNO" style="text-transform: uppercase" tabindex="1" maxlength="12"/>
			</logic:notPresent>
		</div>
		
		<div class="ic-input ic-split-25 ic-label-55">
		<label>
		<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.damagerefno" scope="request"/>
		</label>
		<logic:present name="DamageFilterVO" property="damageReferenceNumber" >
		<bean:define id="damageReferenceNumber" name="DamageFilterVO" property="damageReferenceNumber"/>
			<logic:equal name="DamageFilterVO" property="damageReferenceNumber" value="0">
				<ihtml:text componentID="ULD_DEFAULTS_DAMAGEREFNO" property="damageRefNo" tabindex="2" name="ListDamageReportForm" style="text-transform : uppercase" maxlength="5" />
			</logic:equal>
			<logic:notEqual name="DamageFilterVO" property="damageReferenceNumber" value="0">
				<ihtml:text componentID="ULD_DEFAULTS_DAMAGEREFNO" property="damageRefNo" tabindex="2" name="ListDamageReportForm" style="text-transform : uppercase" maxlength="5" value="<%=damageReferenceNumber.toString()%>"/>
			</logic:notEqual>
		</logic:present>
		<logic:notPresent name="DamageFilterVO" property="damageReferenceNumber" >
			<ihtml:text componentID="ULD_DEFAULTS_DAMAGEREFNO" property="damageRefNo" name="ListDamageReportForm" style="text-transform : uppercase" maxlength="5" />
		</logic:notPresent>
		<div class="lovImg">
			<img name="dmglov" id="dmglov" src="<%= request.getContextPath()%>/images/lov.png" width="22" height="22" border="0" alt="Damage Ref No.LOV"/>
		</div>
		</div>

		<div class="ic-input ic-split-25 ic-label-35">
		<label>
		<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.uldtypecode" scope="request"/>
		</label>
		<logic:present name="DamageFilterVO" property="uldTypeCode" >
			<bean:define id="uldTypeCode" name="DamageFilterVO" property="uldTypeCode"/>
			<ihtml:text componentID="ULD_DEFAULTS_ULDTYPENO" property="uldTypeCode" tabindex="3" name="ListDamageReportForm" style="text-transform : uppercase" maxlength="5" value="<%=uldTypeCode.toString()%>"/>
		</logic:present>
		<logic:notPresent name="DamageFilterVO" property="uldTypeCode" >
			<ihtml:text componentID="ULD_DEFAULTS_ULDTYPENO" property="uldTypeCode" tabindex="3" name="ListDamageReportForm" style="text-transform : uppercase" maxlength="5" />
		</logic:notPresent>
		<div class="lovImg">
			<img name="uldlov" id="uldlov" src="<%= request.getContextPath()%>/images/lov.png" width="22" height="22" border="0" alt="ULD Type LOV"/>
		</div>
		</div>
		
		<div class="ic-input ic-split-25 ic-label-30">
		<label>
			<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.currentstation" scope="request"/>
		</label>
		<logic:present name="DamageFilterVO" property="currentStation" >
			<bean:define id="currentStation" name="DamageFilterVO" property="currentStation"/>
			<ihtml:text componentID="ULD_DEFAULTS_CURRENTSTN" property="currentStn" tabindex="4" name="ListDamageReportForm" style="text-transform : uppercase" maxlength="3" value="<%=currentStation.toString()%>"/>
		</logic:present>
		<logic:notPresent name="DamageFilterVO" property="currentStation" >
			<ihtml:text componentID="ULD_DEFAULTS_CURRENTSTN" property="currentStn" tabindex="4" name="ListDamageReportForm" style="text-transform : uppercase" maxlength="3" />
		</logic:notPresent>
		<div class="lovImg">
			<img name="airportlov" id="airportlov" src="<%= request.getContextPath()%>/images/lov.png" width="22" height="22" border="0" alt="Airport LOV"/>
		</div>
		</div>		
	</div>
	<div class="ic-row ">
	
		<div class="ic-input ic-split-25 ic-label-40">
		<label>
			<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.uldstatus" scope="request"/>
		</label>
				<logic:present name="DamageFilterVO" property="uldStatus" >
					<bean:define id="uldStatus" name="DamageFilterVO" property="uldStatus"/>
					<ihtml:select styleClass="iCargoMediumComboBox" componentID ="ULD_DEFAULTS_ULDSTATUS" property="uldStatus" tabindex="5" title="ULD Status" value="<%=uldStatus.toString()%>">
					<ihtml:option value="">ALL</ihtml:option>
						<logic:present name="StatusCollection">
							<logic:iterate id="StatusIterator" name="StatusCollection" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
								<logic:present name="StatusIterator" property="fieldValue">
									<bean:define id="fieldDescription" name="StatusIterator" property="fieldDescription"/>
									<ihtml:option value="<%=StatusIterator.getFieldValue()%>"><%=(String)fieldDescription%></ihtml:option>
								</logic:present>
							</logic:iterate>
						</logic:present>
					</ihtml:select>
				</logic:present>
				<logic:notPresent name="DamageFilterVO" property="uldStatus" >
					<ihtml:select styleClass="iCargoMediumComboBox" property="uldStatus" componentID ="ULD_DEFAULTS_ULDSTATUS"  tabindex="5" title="ULD Status">
						<ihtml:option value="">ALL</ihtml:option>
						<logic:present name="StatusCollection">
							<logic:iterate id="StatusIterator" name="StatusCollection" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
								<logic:present name="StatusIterator" property="fieldValue">
									<bean:define id="fieldDescription" name="StatusIterator" property="fieldDescription"/>
									<ihtml:option value="<%=StatusIterator.getFieldValue()%>"><%=(String)fieldDescription%></ihtml:option>
								</logic:present>
							</logic:iterate>
						</logic:present>
					</ihtml:select>
				</logic:notPresent>
		</div>
		<div class="ic-input ic-split-25 ic-label-55">
		<label>
			<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.damagestatus" scope="request"/>
		</label>
				<logic:present name="DamageFilterVO" property="uldDamageStatus" >
					<bean:define id="uldDamageStatus" name="DamageFilterVO" property="uldDamageStatus"/>
					<ihtml:select styleClass="iCargoMediumComboBox" property="uldDamageStatus" componentID ="ULD_DEFAULTS_DAMAGESTATUS"  tabindex="6" title="Damage status" value="<%=uldDamageStatus.toString()%>">
						<ihtml:option value="">ALL</ihtml:option>
						<logic:present name="DamageStatusCollections">
							<logic:iterate id="StatusIterator" name="DamageStatusCollections" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
								<logic:present name="StatusIterator" property="fieldValue">
									<bean:define id="fieldDescription" name="StatusIterator" property="fieldDescription" />
									<ihtml:option value="<%=StatusIterator.getFieldValue()%>"><%=(String)fieldDescription%></ihtml:option>
								</logic:present>
							</logic:iterate>
						</logic:present>	
						<logic:notPresent name="DamageStatusCollections">
						</logic:notPresent>
					</ihtml:select>
				</logic:present>
				<logic:notPresent name="DamageFilterVO" property="uldDamageStatus" >
					<ihtml:select styleClass="iCargoMediumComboBox" property="uldDamageStatus" componentID ="ULD_DEFAULTS_DAMAGESTATUS"  tabindex="6" title="Damage status">
						<ihtml:option value="">ALL</ihtml:option>
						<logic:present name="DamageStatusCollections">
							<logic:iterate id="StatusIterator" name="DamageStatusCollections" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
								<logic:present name="StatusIterator" property="fieldValue">
									<bean:define id="fieldDescription" name="StatusIterator" property="fieldDescription" />
									<ihtml:option value="<%=StatusIterator.getFieldValue()%>"><%=(String)fieldDescription%></ihtml:option>
								</logic:present>
							</logic:iterate>
						</logic:present>
						<logic:notPresent name="DamageStatusCollections">
						</logic:notPresent>
					</ihtml:select>	
				</logic:notPresent>
			</div>
			<div class="ic-input ic-split-25 ic-label-35">
			<label>
				<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.listdamagereport.lbl.partytype"  scope="request"/>
			</label>
				<logic:notPresent name="DamageFilterVO" property="partyType" >
					<ihtml:select property="partyType" tabindex="7" componentID="CMB_ULD_DEFAULTS_LISTNDMGRPT_PARTYTYPE">
						<html:option value="">ALL</html:option>				
						<logic:present name="partyTypeCollections">
							<ihtml:options collection="partyTypeCollections"  property="fieldValue" name="ListDamageReportForm" labelProperty="fieldDescription" />
						</logic:present>
					</ihtml:select>
				</logic:notPresent>
				<logic:present name="DamageFilterVO" property="partyType" >
					<bean:define id="partyTypeVO" name="DamageFilterVO" property="partyType" />
					<ihtml:select property="partyType" tabindex="7" value="<%=(String)partyTypeVO%>" componentID="CMB_ULD_DEFAULTS_LISTNDMGRPT_PARTYTYPE">
						<html:option value="">ALL</html:option>
						<logic:present name="partyTypeCollections">
							<ihtml:options collection="partyTypeCollections"  property="fieldValue" name="ListDamageReportForm" labelProperty="fieldDescription" />
						</logic:present>
					</ihtml:select>
				</logic:present>				
			</div>
			<div class="ic-input ic-split-25 ic-label-30">
			<label>
				<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.listdamagereport.lbl.party"  scope="request"/>
			</label>
				<logic:notPresent name="DamageFilterVO" property="party" >
					<ihtml:text componentID="TXT_ULD_DEFAULTS_LISTNDMGRPT_PARTY" property="party" tabindex="8" name="ListDamageReportForm" maxlength="12" />
					<div class="lovImg">
						<img src="<%= request.getContextPath()%>/images/lov.png" width="22" height="22" name="partycodelov"  id="partycodelov" alt="Party LOV"/>
					</div>
				</logic:notPresent>
				<logic:present name="DamageFilterVO" property="party" >
					<bean:define id="partyVO" name="DamageFilterVO" property="party" />
					<ihtml:text componentID="TXT_ULD_DEFAULTS_LISTNDMGRPT_PARTY" property="party" tabindex="8" name="ListDamageReportForm" maxlength="12" value="<%=(String)partyVO%>"/>
					<div class="lovImg">
						<img src="<%= request.getContextPath()%>/images/lov.png" width="22" height="22" name="partycodelov"  id="partycodelov" alt="Party LOV"/>
					</div>
				</logic:present>
			</div>
		</div>
		<div class="ic-row">
			<div class="ic-input ic-split-25 ic-label-40">
			<label>
				<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.listdamagereport.lbl.facility" scope="request"/>
			</label>
				<logic:present  name="DamageFilterVO" property="facilityType" >
					<bean:define id="facilityVO" name="DamageFilterVO" property="facilityType" />
					<ihtml:select property="facilityType" tabindex="9" value="<%=(String)facilityVO%>" componentID="CMB_ULD_DEFAULTS_LISTNDMGRPT_FACILITYTYPE">
							<html:option value="">ALL</html:option>
							<logic:present name="facilityTypeCollections">
								<ihtml:options collection="facilityTypeCollections"  property="fieldValue" name="ListDamageReportForm" labelProperty="fieldDescription"  />
							</logic:present>
					</ihtml:select>
				</logic:present>
				<logic:notPresent  name="DamageFilterVO" property="facilityType" >
					<ihtml:select property="facilityType" tabindex="9" componentID="CMB_ULD_DEFAULTS_LISTNDMGRPT_FACILITYTYPE">
							<html:option value="">ALL</html:option>
							<logic:present name="facilityTypeCollections">
								<ihtml:options collection="facilityTypeCollections"  property="fieldValue" name="ListDamageReportForm" labelProperty="fieldDescription"  />
							</logic:present>
					</ihtml:select>
				</logic:notPresent>
			</div>
			<div class="ic-input ic-split-25 ic-label-55">
			<label>
				<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.listdamagereport.lbl.location" scope="request"/>
			</label>
				<logic:notPresent name="DamageFilterVO" property="location" >
					<ihtml:text componentID="TXT_ULD_DEFAULTS_LISTNDMGRPT_LOCATION" property="location" tabindex="10" name="ListDamageReportForm"  maxlength="12"  value=""/>
					<button type="button" class="iCargoLovButton" name="facilitycodelov" id="facilitycodelov"  />
				</logic:notPresent>
				<logic:present name="DamageFilterVO" property="location" >
					<bean:define id="locationVO" name="DamageFilterVO" property="location" />
					<ihtml:text componentID="TXT_ULD_DEFAULTS_LISTNDMGRPT_LOCATION" property="location" tabindex="10" name="ListDamageReportForm"  maxlength="12" value="<%=(String)locationVO%>" />
					<button type="button" class="iCargoLovButton" name="facilitycodelov" id="facilitycodelov"  />
				</logic:present>
			</div>
			<div class="ic-input ic-split-25 ic-label-35">
			<label>
			<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.reportedstation" scope="request"/>
			</label>
				<logic:present name="DamageFilterVO" property="reportedStn" >
					<bean:define id="reportedStn" name="DamageFilterVO" property="reportedStn"/>
					<ihtml:text componentID="ULD_DEFAULTS_REPORTEDSTN" property="reportedStn" tabindex="11" name="ListDamageReportForm" style="text-transform : uppercase" maxlength="3" value="<%=reportedStn.toString()%>"/>
				</logic:present>
				<logic:notPresent name="DamageFilterVO" property="reportedStn" >
					<ihtml:text componentID="ULD_DEFAULTS_REPORTEDSTN" property="reportedStn" tabindex="11" name="ListDamageReportForm" style="text-transform : uppercase" maxlength="3" />
				</logic:notPresent>
				<div class="lovImg">
					<img name="airportlovrep" id="airportlovrep" src="<%= request.getContextPath()%>/images/lov.png" width="22" height="22" border="0" alt="Airport LOV"/>
				</div>
			</div>
		</div>
		<div class="ic-row">
			<div class="ic-input ic-split-25 ic-label-40">
			<label>
			<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.repaireddatefrom" scope="request"/>
			</label>
				<logic:present name="DamageFilterVO" property="fromDate" >
					<bean:define id="fromdateid" name="DamageFilterVO" property="fromDate"/>
					<ibusiness:calendar id="frmDate" property="repairedDateFrom" tabindex="12" type="image" value="<%=(String)fromdateid%>" componentID="ULD_DEFAULTS_REPAIREDDATEFROM"/>
				</logic:present>
				<logic:notPresent name="DamageFilterVO" property="fromDate" >
					<ibusiness:calendar id="frmDate" property="repairedDateFrom" tabindex="12" type="image" value="" componentID="ULD_DEFAULTS_REPAIREDDATEFROM"/>
				</logic:notPresent>
			</div>
			<div class="ic-input ic-split-25 ic-label-55">
			<label>
			<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.repaireddateto" scope="request"/>
			</label>
				<logic:present name="DamageFilterVO" property="ToDate" >
					<bean:define id="toDateid" name="DamageFilterVO" property="ToDate"/>
					<ibusiness:calendar id="toDate" property="repairedDateTo" tabindex="13" type="image" value="<%=(String)toDateid%>" componentID="ULD_DEFAULTS_REPAIREDDATETO"/>
				</logic:present>
				<logic:notPresent name="DamageFilterVO" property="ToDate" >
					<ibusiness:calendar id="toDate" property="repairedDateTo" tabindex="13" type="image" value="" componentID="ULD_DEFAULTS_REPAIREDDATETO"/>
				</logic:notPresent>	
			</div>
			
			<div class="ic-button-container">
				<ihtml:button property="btList" tabindex="14" componentID="ULD_DEFAULTS_LIST">
					<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.list" scope="request"/>
				</ihtml:button>
				<ihtml:button property="btClear" tabindex="15" styleClass="btn-inline btn-secondary" componentID="ULD_DEFAULTS_CLEAR">
					<bean:message bundle="<%=form.getBundle() %>" key="uld.defaults.clear" scope="request"/>
				</ihtml:button>
			</div>
		</div>
		<div class="ic-row ic-label-45" style="padding-top:15px;">	<!--Added by A-7359 for ICRD - 224586 starts here	-->
		</div>
