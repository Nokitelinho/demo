<%--
* Project	 		: iCargo
* Module Code & Name: uld.defaults
* File Name			: MaintainDamage_DamageDetailsPart2.jsp
* Date				: 24-Sep-2018
* Author(s)			: A-7955
 --%>


<%@ page
	import="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ux.misc.MaintainDamageReportForm"%>
<%@ page
	import="com.ibsplc.xibase.server.framework.persistence.query.Page"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page
	import="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO"%>
<%@ page
	import="com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageVO"%>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@page import="java.util.ArrayList"%>
<%@ include file="/jsp/includes/tlds.jsp"%>

	<% ULDDamageVO uLDDamageDtlVO =
  (ULDDamageVO)request.getAttribute("uLDDamageDtlVO");
  String opFlag = request.getAttribute("oprtnFlg").toString();%>
				
				
					<ihtml:hidden property="tempOperationFlag" value="D"/>
					<logic:present name="uLDDamageDtlVO" property="damageReferenceNumber">
						<bean:define id="damageReferenceNumber" name="uLDDamageDtlVO"	property="damageReferenceNumber" />
						<ihtml:hidden property="dmgRefNo" value="<%=String.valueOf(damageReferenceNumber)%>" />											
					</logic:present>
					<logic:notPresent name="uLDDamageDtlVO" property="damageReferenceNumber">
						<ihtml:hidden property="dmgRefNo" value="" />											
					</logic:notPresent>
					<logic:present	name="uLDDamageDtlVO" property="section">
						<bean:define id="section" name="uLDDamageDtlVO" property="section" />
						<ihtml:hidden property="section" styleClass="span8" value="<%=(String) section%>"  />
					</logic:present> 
					<logic:notPresent name="uLDDamageDtlVO" property="section">
						<ihtml:hidden property="section"  value=""  />
					</logic:notPresent>
					<logic:present	name="uLDDamageDtlVO" property="damageDescription">
						<bean:define id="description" name="uLDDamageDtlVO" property="damageDescription" />
						<ihtml:hidden property="description" value="<%=(String) description%>" />
					</logic:present> 
					<logic:notPresent name="uLDDamageDtlVO" property="damageDescription">
						<ihtml:hidden property="description"  value="" />
					</logic:notPresent>
					<logic:present	name="uLDDamageDtlVO" property="severity">
						<bean:define id="severity" name="uLDDamageDtlVO" property="severity" />
						<ihtml:hidden property="severity"  value="<%=(String) severity%>"  />
					</logic:present> 
					<logic:notPresent name="uLDDamageDtlVO" property="severity">
						<ihtml:hidden property="severity" value=""  />
					</logic:notPresent>
					<logic:present	name="uLDDamageDtlVO" property="reportedStation">
						<bean:define id="repStn" name="uLDDamageDtlVO" property="reportedStation" />
						<ihtml:hidden property="repStn"  value="<%=(String) repStn%>"  />
					</logic:present> 
					<logic:notPresent name="uLDDamageDtlVO" property="reportedStation">
						<ihtml:hidden property="repStn"  value=""/>
					</logic:notPresent>
					<logic:present	name="uLDDamageDtlVO" property="reportedDate">
						<bean:define id="awbDate" name="uLDDamageDtlVO" property="reportedDate" />
						<%
							String repoDate = TimeConvertor.toStringFormat(((LocalDate) awbDate).toCalendar(),"dd-MMM-yyyy");
						%>
						<ihtml:hidden property="reportedDate" value="<%=(String) repoDate%>" />
					</logic:present> 
					<logic:notPresent name="uLDDamageDtlVO" property="reportedDate">
						<ihtml:hidden property="reportedDate"  value=""/>
					</logic:notPresent>
					
					<logic:present	name="uLDDamageDtlVO" property="facilityType">
						<bean:define id="facilityType" name="uLDDamageDtlVO" property="facilityType" />
						<ihtml:hidden property="facilityType" value="<%=(String) facilityType%>"  />
					</logic:present> 
					<logic:notPresent name="uLDDamageDtlVO" property="facilityType">
						<ihtml:hidden property="facilityType"  value="" />
					</logic:notPresent>
					<logic:present	name="uLDDamageDtlVO" property="location">
						<bean:define id="location" name="uLDDamageDtlVO" property="location" />
						<ihtml:hidden property="location" value="<%=(String) location%>" />
					</logic:present> 
					<logic:notPresent name="uLDDamageDtlVO" property="location">
						<ihtml:hidden property="location"  value="" />
					</logic:notPresent>
					<logic:present	name="uLDDamageDtlVO" property="partyType">
						<bean:define id="partyType" name="uLDDamageDtlVO" property="partyType" />
						<ihtml:hidden property="partyType" value="<%=(String) partyType%>" />
					</logic:present>
					<logic:notPresent name="uLDDamageDtlVO" property="partyType">
						<ihtml:hidden property="partyType"  value="" />
					</logic:notPresent>
					<logic:present name="uLDDamageDtlVO" property="party">
						<bean:define id="party" name="uLDDamageDtlVO" property="party" />
						<ihtml:hidden property="party"  value="<%=(String) party%>" />										
					</logic:present>
					<logic:notPresent name="uLDDamageDtlVO" property="party">
						<ihtml:hidden property="party" value="" />
					</logic:notPresent>	
					 <logic:present name="uLDDamageDtlVO" property="remarks">
						<bean:define id="remarks" name="uLDDamageDtlVO" property="remarks" />
						<ihtml:hidden property="remarks"  value="<%=(String) remarks%>" />										
					</logic:present>
					<logic:notPresent name="uLDDamageDtlVO" property="remarks">
						<ihtml:hidden property="remarks" value="" />
					</logic:notPresent>	
					
