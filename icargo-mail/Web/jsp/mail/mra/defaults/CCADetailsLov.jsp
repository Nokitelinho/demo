<%--/***********************************************************************
* Project	     	 : iCargo
* Module Code & Name 	 : MRA
* File Name          	 : CCADetailsLov.jsp
* Date                 	 : 21-July-2008
* Author(s)              : A-3447
*************************************************************************/
--%>

<%@ page language="java" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO" %>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import=" com.ibsplc.icargo.business.mail.mra.defaults.vo.CCAdetailsVO"%>
<%@ page import="java.util.Formatter" %>

<html:html>
<head> 
	
	
	
	
<title>iCargo:CCADetailsLov</title>
 	<title><common:message bundle="maintainCCA" key="mailtracking.mra.defaults.maintaincca.lbl.availablemca" /></title>
<meta name="decorator" content="popuppanelrestyledui">
<common:include type="script" src="/js/mail/mra/defaults/CCADetailsLov_Script.jsp"/>
</head>
<body>
	
	

<bean:define id="form"
	 name="mraMaintainCCAForm"
	 type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRAMaintainCCAForm"
	 toScope="page" />


	 <business:sessionBean id="OneTimeValues"
	 	 moduleName="mailtracking.mra.defaults"
		 screenID="mailtracking.mra.defaults.maintaincca"
	 	  method="get"
	  attribute="OneTimeVOs" />
	<business:sessionBean id="ccaFilterVO"
		moduleName="mailtracking.mra.gpabilling"
		screenID="mailtracking.mra.defaults.maintaincca"
	   	method="get" attribute="maintainCCAFilterVO" />
	<business:sessionBean id="cCAdetailsVO"
	   	moduleName="mailtracking.mra.gpabilling"
	   	screenID="mailtracking.mra.defaults.maintaincca"
	   	method="get" attribute="cCAdetailsVO" />
	<business:sessionBean id="cCAdetailsVOs"
	   	moduleName="mailtracking.mra.gpabilling"
	 	screenID="mailtracking.mra.defaults.maintaincca"
	 	method="get" attribute="cCAdetailsVOs" />

	<business:sessionBean id="KEY_WEIGHTROUNDINGVO"
		  moduleName="mailtracking.mra.defaults"
		  screenID="mailtracking.mra.defaults.maintaincca"
		  method="get"
		  attribute="weightRoundingVO" />
	<business:sessionBean id="KEY_VOLUMEROUNDINGVO"
		  moduleName="mailtracking.mra.defaults"
		  screenID="mailtracking.mra.defaults.maintaincca"
		  method="get"
		  attribute="volumeRoundingVO" />
	<business:sessionBean id="KEY_SYSPARAMETERS"
		moduleName="mailtracking.mra.defaults"
		screenID="mailtracking.mra.defaults.maintaincca"
		method="get" attribute="systemparametres" />
<div class="iCargoPopUpContent">
<ihtml:form action="/mailtracking.mra.defaults.showccas.screenload.do" styleClass="ic-main-form">
<ihtml:hidden property="comboFlag"/>
<ihtml:hidden property="showpopUP"/>
<ihtml:hidden property="selectedRow"/>
<ihtml:hidden property="popupon"/>
<ihtml:hidden property="count"/>
<ihtml:hidden property="closeFlag"/>
<input type="hidden" name="currentDialogOption" />
<input type="hidden" name="currentDialogId" />
<ihtml:hidden property="dsnPopupFlag" />
<ihtml:hidden property="createCCAFlg" />
<ihtml:hidden property="fromScreen" />
<ihtml:hidden property="usrCCANumFlg" />
<ihtml:hidden property="autoratedFlag" />
<ihtml:hidden property="rateAuditedFlag" />
<ihtml:hidden property="showDsnPopUp" />
<ihtml:hidden property="disableFlag" />
<ihtml:hidden property="dsnDate" />
<ihtml:hidden property="overrideRounding" value="N" />   
<%String roundingReq="true";%>

<logic:present name="KEY_SYSPARAMETERS">
		<logic:iterate id="sysPar" name="KEY_SYSPARAMETERS">
			<bean:define id="parameterCode" name="sysPar" property="key"/>
			<logic:equal name="parameterCode" value="mailtracking.mra.overrideroundingvalue">
				<bean:define id="parameterValue" name="sysPar" property="value"/>			
					<logic:notEqual name="parameterValue" value="N">
						<%roundingReq="false";%>	
					</logic:notEqual>
			</logic:equal>
		</logic:iterate>
</logic:present>

  
  <div class="ic-content-main">
			<div class="ic-head-container">
		<div class="ic-filter-panel">
			<div class="ic-input-container">
				<div class="ic-row">
					<h4>
					<common:message key="mailtracking.mra.defaults.maintaincca.lbl.availablemca" />
					</h4>					
				</div>
							<div class="ic-row">
				   		   	<fieldset class="ic-field-set" >
								<legend	>
									<common:message key="mailtracking.mra.defaults.maintaincca.lbl.despatchdetails" />
								</legend>
					   			 <div class="ic-input ic-split-60">
										<label><common:message key="mailtracking.mra.defaults.maintaincca.lbl.despatch" /></label>
										<logic:present name="cCAdetailsVO" property="billingBasis">
												<common:display property="billingBasis" name="cCAdetailsVO" />
											</logic:present>
											<logic:notPresent name="cCAdetailsVO" property="billingBasis">
							   			  		
											</logic:notPresent>
									</div>
											
							   			
							   			<div class="ic-input ic-split-40">
							   			<common:message key="mailtracking.mra.defaults.maintaincca.lbl.despdate" />
							   			
											<logic:present name="cCAdetailsVO" property="dsnDate">
										  			<common:display property="dsnDate" name="cCAdetailsVO" />
											</logic:present>
											<logic:notPresent name="cCAdetailsVO" property="dsnDate">
							   			  		<common:display property="dsnDate" name="form" />
											</logic:notPresent>
							   			</div>
							   		
			
						   		
					   			<!--DSN DTLS LEGEND TABLE  ENDS-->
				   			</fieldset>
						</div>
					
						<div class="ic-row">
				   		  	<fieldset class="ic-field-set" >
								<legend	>
									<common:message key="mailtracking.mra.defaults.maintaincca.lbl.billprd" />
								</legend>
					   			 <div class="ic-input ic-split-60">
							   			<common:message key="mailtracking.mra.defaults.maintaincca.lbl.frm" />
							   			
											<logic:present name="cCAdetailsVO" property="billingPeriodFrom">
												<bean:define id="billingPeriodFrom" name="cCAdetailsVO" property="billingPeriodFrom" />
										  			<common:display property="billingPeriodFrom" name="cCAdetailsVO" />
											</logic:present>
											<logic:notPresent name="cCAdetailsVO" property="billingPeriodFrom">
							   			  		<common:display property="bilfrmdate" name="form" />
											</logic:notPresent>
							   			</div>
							   			 <div class="ic-input ic-split-40">
							   			<common:message key="mailtracking.mra.defaults.maintaincca.lbl.to" />
							   			
											<logic:present name="cCAdetailsVO" property="billingPeriodTo">
												<bean:define id="billingPeriodTo" name="cCAdetailsVO" property="billingPeriodTo" />
										  			<common:display property="billingPeriodTo" name="cCAdetailsVO" />
											</logic:present>
											<logic:notPresent name="cCAdetailsVO" property="billingPeriodTo">
							   			  		<common:display property="biltodate" name="form" />
											</logic:notPresent>
							   			</div>
						   			
				   			</fieldset>
						</div>
</div>
</div>
</div>
		<div class="ic-main-container">
	<div class="ic-row">
				<common:message key="mailtracking.mra.defaults.maintaincca.lbl.mcasfordespatch" />
			</div>
		
		 <div id="div1" class="tableContainer" style="height:300px">
	 <table width="100%" class="fixed-header-table" >
 	 <thead>
 	 <tr class="iCargoTableHeadingLeft">
  		<td width="15%"align="center" rowspan="2">
  			<input type="checkbox" name="check">
  		</td>
 		 <td align="center" rowspan="2">
 			<common:message key="mailtracking.mra.defaults.maintaincca.lbl.mcanum" />
  		</td>
		<td  align="center" rowspan="2">
 			<common:message key="mailtracking.mra.defaults.maintaincca.lbl.mcaissdate" />
  		</td>
		<td align="center" rowspan="2">
 			<common:message key="mailtracking.mra.defaults.maintaincca.lbl.mcaamt" />
  		</td>
		<td  align="center" rowspan="2">
 			<common:message key="mailtracking.mra.defaults.maintaincca.lbl.status" />
  		</td>
	</tr>

	</thead>
<%! int newFlg=0; %>
	<tbody>
	<logic:present name="cCAdetailsVOs">
		<logic:iterate id="cCAdetailsVO" name="cCAdetailsVOs" indexId="rowId">
		 <logic:equal name="cCAdetailsVO" property="ccaType" value="N">
		   <% newFlg=1; %>
		  </logic:equal> 
		  <logic:notEqual name="cCAdetailsVO" property="ccaType" value="N">
		    <logic:equal name="cCAdetailsVO" property="ccaType" value="P">
		    <% newFlg=1; %>
		     </logic:equal> 
		   </logic:notEqual>
		  		 
		<tr class="iCargoTableDataRow1">
			<td >
				<div align="center">
					<input type="checkbox" name="rowCount" value="<%=String.valueOf(rowId)%>" onclick="checkBoxValidate('rowCount',<%=rowId%>)"/>
				</div>
			</td>
			<td >	&nbsp;
				<bean:write name="cCAdetailsVO" property="ccaRefNumber"/>
			</td>
			<td>	&nbsp;
				<bean:write name="cCAdetailsVO" property="issueDate"/>
			</td>
			<td>	&nbsp;
			<logic:present name="cCAdetailsVO" property="diffAmount">
				<ibusiness:moneyDisplay showCurrencySymbol="false" name="cCAdetailsVO"  moneyproperty="diffAmount" property="diffAmount" roundMoney="<%=roundingReq%>"/>
			</logic:present>
			<logic:notPresent name="cCAdetailsVO" property="diffAmount">
					0.0					
			</logic:notPresent>
			</td>
			<td>	&nbsp;
				
							<logic:present name="cCAdetailsVO" property="ccaStatus">
							 <bean:define id="ccaStatus" name="cCAdetailsVO" property="ccaStatus"/>
							 <ihtml:hidden property="ccaStatus" value="<%=String.valueOf(ccaStatus)%>"/>
							<logic:present name="OneTimeValues">
							<bean:define id="onetimemaps" name="OneTimeValues"/>
							<logic:iterate id ="onetimemap" name="onetimemaps">
								 <bean:define id="keymap" name="onetimemap" property="key"/>
								 <logic:equal name="keymap" value ="mra.defaults.ccastatus">
									 <bean:define id="valuemap" name="onetimemap" property="value" type="java.util.Collection"/>
										<logic:iterate id="value" name="valuemap" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
										<bean:define id="value1" name="value" property="fieldValue" />
										<logic:equal name="value1" value="<%=String.valueOf(ccaStatus)%>">
										 <%=value.getFieldDescription()%>
										</logic:equal>
										</logic:iterate>
										</logic:equal>	
								</logic:iterate>  
							</logic:present>
						</logic:present>
						 
			</td>
		</tr>
			</logic:iterate>
			</logic:present>

		</tbody>

	</table>
	</div>
	

 </div>
 	<div class="ic-foot-container">
		<div class="ic-button-container">
		<ihtml:nbutton property="btOk" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_OK" >
			<bean:message bundle="maintainCCA" key="mailtracking.mra.defaults.maintaincca.lbl.ok" />
		</ihtml:nbutton>
		<% if(newFlg==1){ 
		newFlg=0;
		%>		
		<ihtml:nbutton property="btncreateCCA" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_CREATECCA" disabled="true" >		
			<bean:message bundle="maintainCCA" key="mailtracking.mra.defaults.maintaincca.lbl.createcca" scope="request" />
		</ihtml:nbutton>
		<% }else { %>
		<ihtml:nbutton property="btncreateCCA" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_CREATECCA"  >		
			<bean:message bundle="maintainCCA" key="mailtracking.mra.defaults.maintaincca.lbl.createcca" scope="request" />
		</ihtml:nbutton>
		<% } %>		
		<ihtml:nbutton property="btCancel" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_CANCEL" >
		<bean:message bundle="maintainCCA" key="mailtracking.mra.defaults.maintaincca.lbl.cancel" />
		</ihtml:nbutton>

	</div>
 		</div>
 	</div>
  </ihtml:form>
  </div>
			

	</body>
</html:html>
