<%--/***********************************************************************
* Project	     	 : iCargo
* Module Code & Name 	 : mra
* File Name          	 : DespatchRouting.jsp
* Date                 	 : 02-Sep-2008
* Author(s)              : A-3229
*************************************************************************/
--%>
<%@ page language="java" %>
 <%@ include file="/jsp/includes/tlds.jsp"%>
 <%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.DespatchRoutingForm"%>
 


	<%-- added as part of ICRD-357234 Start --%>
	<%boolean tkFlag = true;%>						
	<common:xgroup>				
		<common:xsubgroup id="TURKISH_SPECIFIC">						
			<% tkFlag = false;%>
		</common:xsubgroup>
	</common:xgroup >
	<%-- added as part of ICRD-357234 End --%>


<html:html>	
 <head>
		
	
		
	



 	<title><common:message bundle="despatchroutingresources" key="mailtracking.mra.defaults.despatchrouting.title" /></title>
 	<meta name="decorator" content="mainpanelrestyledui">
 	<common:include type="script" src="/js/mail/mra/defaults/DespatchRouting_Script.jsp"/>
 </head>

<body>
	



<bean:define id="form"
	name="DespatchRoutingForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.DespatchRoutingForm"
	toScope="page" />

 <business:sessionBean id="dsnRoutingVOs"
	 moduleName="mailtracking.mra.defaults"
	 screenID="mailtracking.mra.defaults.despatchrouting"
	 method="get"
	 attribute="DSNRoutingVOs" />
	 
 <business:sessionBean id="KEY_WEIGHTROUNDINGVO"
		  moduleName="mailtracking.mra.defaults"
		  screenID="mailtracking.mra.defaults.despatchrouting"
		  method="get"
		  attribute="weightRoundingVO" />

<business:sessionBean id="agreementTypesSession" 
          moduleName="mailtracking.mra.defaults"
 		  screenID="mailtracking.mra.defaults.despatchrouting" 
		  method="get" 
		  attribute="agreementTypes" />

<business:sessionBean id="blockspaceTypesSession" 
          moduleName="mailtracking.mra.defaults"
 		  screenID="mailtracking.mra.defaults.despatchrouting" 
		  method="get" 
		  attribute="blockSpaceTypes" />
		  
<business:sessionBean
  		id="KEY_MAILSOURCE"
  		moduleName="mailtracking.mra"
  		screenID="mailtracking.mra.defaults.despatchrouting"
  		method="get"
  		attribute="mailSources" />



<div id="mainDiv"  class="iCargoContent">
<!--hidden fields-->
<ihtml:form action="/mailtracking.mra.defaults.despatchrouting.screenLoad.do">

<ihtml:hidden property="airport" />
<ihtml:hidden property="fromScreen" />
<ihtml:hidden property="owncarcode" />
<ihtml:hidden property="origin" />
<ihtml:hidden property="destn" />
<ihtml:hidden property="oalPcs" />
<ihtml:hidden property="oalwgt" />
<ihtml:hidden property="diplayWgt"/>
<ihtml:hidden property="diplayWgtUnit"/>
<ihtml:hidden property="showDsnPopUp" />

		<div class="ic-content-main">
				<div class="ic-head-container">	
					<span class="ic-page-title ic-display-none">
						<common:message key="mailtracking.mra.defaults.despatchrouting.pagetitle" />
					</span>
					<div class="ic-filter-panel">
						<div class="ic-row">
							<div class="ic-input ic-split-50 ic-mandatory">
								<label><common:message key="mailtracking.mra.defaults.despatchrouting.lbl.dsn" scope="request"/></label>
								<ihtml:text property="dsn" style="pointer-events:auto" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_DSN"  maxlength="29" />
								<div class="lovImg">
								<img src="<%=request.getContextPath()%>/images/lov.png" id="dsnlov" height="22" width="22" alt=""/>
							    </div>
							</div>
							<div class="ic-input ic-split-25">
								<label><common:message   key="mailtracking.mra.defaults.despatchrouting.lbl.dsndate"/></label>
								<ibusiness:calendar id="dsnDate"
								property="dsnDate"
								componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_DSNDATE"
								type="image"
								maxlength="11"
								value="<%=form.getDsnDate()%>" />
							</div>
							<div class="ic-input ic-split-25">
								<div class="ic-button-container">
									<ihtml:nbutton property="btnList" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_LIST" accesskey="L" >
										<common:message key="mailtracking.mra.defaults.despatchrouting.button.list" />
									</ihtml:nbutton>
									<ihtml:nbutton property="btnClear" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_CLEAR" accesskey="C" >
										<common:message key="mailtracking.mra.defaults.despatchrouting.button.clear" />
									</ihtml:nbutton>
								</div>
							</div>
						</div>
					
					</div>
				</div>
				
				<div class="ic-main-container">
					<div class="ic-row">
						<div class="ic-input ic-split-50 ic-mandatory">
						<b><common:message key="mailtracking.mra.defaults.despatchrouting.flightdetails" /></b>
						</div>
						
						<%-- added as part of ICRD-357234 Start --%>
						<%if (tkFlag) { %>
							<div class="ic-input ic-split-50 ic-mandatory">
								<b><common:message key="mailtracking.mra.defaults.despatchrouting.lbl.mailsource" /> :</b>
								<%String source =  form.getExactMailSource();
								if(source == null)
									source = "TEMP";
								%>
								<logic:present name="KEY_MAILSOURCE">
											<logic:iterate id="mailsource" name="KEY_MAILSOURCE">
														<bean:define id="fieldValue" name="mailsource" property="fieldValue" />
													<logic:equal name="fieldValue" value="<%=(String)source%>">
													<bean:write name="mailsource" property="fieldDescription" />
													</logic:equal> 
											</logic:iterate>
								</logic:present>
							</div>
					  <% } %>
					  <%-- added as part of ICRD-357234 End --%>
											
					</div>
					
					<div class="ic-row">
					
						<div class="ic-col-15">
							<div class="ic-input ic-split-100">
								<label><common:message key="mailtracking.mra.defaults.despatchrouting.lbl.transferpa"/></label>
								<ihtml:text property="transferPA" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_TRFPA" style="text-transform : uppercase" tabindex="2" />
								<div class="lovImg">
								<img name="transferPALov" id="transferPALov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" alt="" />
								</div>
							</div>
						</div>	
						<div class="ic-col-15">
							<div class="ic-input ic-split-100">
								<label><common:message key="mailtracking.mra.defaults.despatchrouting.lbl.transferairline"/></label>
								<ihtml:text property="transferAirline" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_TRFARL" style="text-transform : uppercase" tabindex="2" />
								<div class="lovImg">
								<img name="transferAirlineLov" id="transferAirlineLov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" alt="" />
								</div>
							</div>
						</div>	
					
					</div>
					
					
					
					<div class="ic-row paddR5">
						<div class="ic-button-container">
						   <a href="#" class="iCargoLink" id="addLink">
						   <common:message   key="mailtracking.mra.defaults.despatchrouting.add"/></a>
						 | <a href="#" class="iCargoLink" id="deleteLink">
						 <common:message   key="mailtracking.mra.defaults.despatchrouting.delete"/></a>
						 </div>
					</div>
					<div class="ic-row">
					<div id="div1" class="tableContainer" style="height:760px">
					<table class="fixed-header-table">
					<thead>
						<tr class="iCargoTableHeadingLeft">
						<td width="4%"><input id="checkbox" type="checkbox" name="checkAll"  /></td>
						<td width="11%"><common:message key="mailtracking.mra.defaults.despatchrouting.lbl.flightno" /> </td>
						<td width="12%"><common:message key="mailtracking.mra.defaults.despatchrouting.lbl.depdate" /></td>
						<td width="4%"><common:message key="mailtracking.mra.defaults.despatchrouting.lbl.truck" /></td>
						<td width="9%"><common:message key="mailtracking.mra.defaults.despatchrouting.lbl.pol"/></td>
						<td width="9%"><common:message key="mailtracking.mra.defaults.despatchrouting.lbl.pou"/></td>
						<td width="9%"><common:message key="mailtracking.mra.defaults.despatchrouting.lbl.noofpieces"/></td>
						<td width="11%"><common:message key="mailtracking.mra.defaults.despatchrouting.lbl.blockspace"/></td>
						<% if(form.getDiplayWgtUnit()!=null) { %>
						<td width="10%"><common:message key="mailtracking.mra.defaults.despatchrouting.lbl.wieght"/> (<%=form.getDiplayWgtUnit()%>)</td>
						<% }%>
						<% if(form.getDiplayWgtUnit()==null) {%>
						<td width="10%"><common:message key="mailtracking.mra.defaults.despatchrouting.lbl.wieght"/></td>
						<% }%>
						<td width="11%"><common:message key="mailtracking.mra.defaults.despatchrouting.lbl.agreementtype"/></td>
						<td width="10%"><common:message key="mailtracking.mra.defaults.despatchrouting.lbl.source"/></td>
						</tr>
					</thead>
					<jsp:include page="DespatchRoutingTable.jsp" />
						
				

					</table>

					</div>						
					
					
					</div>
						
				</div>
				<div class="ic-foot-container">
					<div class="ic-button-container paddR5">
						<ihtml:nbutton property="btnSave" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_SAVE" accesskey="S" >
							<common:message key="mailtracking.mra.defaults.despatchrouting.button.save" />
						</ihtml:nbutton>

						<ihtml:nbutton property="btnClose" componentID="CMP_MRA_DEFAULTS_DESPATCHROUTING_CLOSE" accesskey="O" >
							<common:message key="mailtracking.mra.defaults.despatchrouting.button.close" />
						</ihtml:nbutton>
					</div>
				</div>
				</div>
		</div>


</div>
	</ihtml:form>
	
	</body>
</html:html>
