<%--/***********************************************************************
* Project	     	 : iCargo
* Module Code & Name 	 : MRA
* File Name          	 : MCALov.jsp
* Date                 	 : 25-May-2012
* Author(s)              : A-4823
*************************************************************************/
--%>


<%@ page import="com.ibsplc.icargo.business.mail.mra.defaults.vo.CCAdetailsVO"%>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.DSNLovForm"%>


		
			
	
<html:html>
<head> 
	
	
		
			
	
		
			
		<meta name="decorator" content="popuppanelrestyledui">
		<title><common:message  bundle="dsnlov"  key="mailtracking.mra.defaults.maintaincca.lbl.dsnlov" scope="request"/></title>
		
		<common:include type="script" src="/js/mail/mra/defaults/DSNLov_Script.jsp"/>
	</head>
<body id="bodyStyle">
	
	
	
<bean:define id="DSNLovForm"
	 name="DSNLovForm"
	 type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.DSNLovForm"
	 toScope="page" />
	
	
 
	 <bean:define id="strAction" name="DSNLovForm"  property="lovaction" scope="page" toScope="page"/>
	 <div id="pageDiv" class="iCargoPopUpContent" style="height:700px; width:100%;">
	<!--Modified by A-6767 for ICRD-130805 Start-->
	 <ihtml:form action="<%=(String)strAction%>"  styleClass="ic-main-form">
	<!--Modified by A-6767 for ICRD-130805 Ends-->
<ihtml:hidden name="DSNLovForm" property="lovaction"  />
		<ihtml:hidden name="DSNLovForm" property="selectedValues"  />
		<ihtml:hidden name="DSNLovForm" property="lastPageNum" />
		<ihtml:hidden name="DSNLovForm" property="displayPage" />
		<ihtml:hidden name="DSNLovForm" property="multiselect" />
		<ihtml:hidden name="DSNLovForm" property="pagination" />
		<ihtml:hidden name="DSNLovForm" property="formCount" />
		<ihtml:hidden name="DSNLovForm" property="lovTxtFieldName" />
		<ihtml:hidden name="DSNLovForm" property="lovDescriptionTxtFieldName" />
		<ihtml:hidden name="DSNLovForm" property="index" />


		<ihtml:hidden name="DSNLovForm" property="lovActionType" />

<!--LOV-->

	<div class="ic-content-main">
<span class="ic-page-title ic-display-none">
			<label><common:message  key="mailtracking.mra.defaults.maintaincca.lbl.dsnlov" scope="request"/>
								</label>
							</span>
							<div class="ic-head-container">

<div class="ic-filter-panel" >
<div class="ic-input-container">
<div class="ic-row">
                         
							  <div class="ic-row">
								  <div class="ic-input ic-label-33">
			
									<label><common:message  key="mailtracking.mra.defaults.dsnlov.lbl.originOE" scope="request"/></label>

									<ihtml:text property="origin" name="DSNLovForm" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_ORIGIN"  maxlength="6"/>
								</div>
								 <div class="ic-input ic-label-33">
							
							<label><common:message  key="mailtracking.mra.defaults.dsnlov.lbl.destOE" scope="request"/></label>
							<ihtml:text property="destination" name="DSNLovForm" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_DEST"  maxlength="6"/>
							</div>
								<div class="ic-input ic-label-33 ">
							<label><common:message  key="mailtracking.mra.defaults.dsnlov.lbl.category" scope="request"/></label>
						
							<ihtml:text property="category" name="DSNLovForm" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_CATEGORY"  maxlength="1"/>
							</div>
								<div class="ic-input ic-label-33">
							<label><common:message  key="mailtracking.mra.defaults.dsnlov.lbl.subclass" scope="request"/></label>
						
							<ihtml:text property="subclass" name="DSNLovForm" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_SUBCLASS"  maxlength="2"/>
							</div>
							</div>
						 
						
							<div class="ic-row">
																																										
							<div class="ic-input ic-label-33  ">
							<label><common:message  key="mailtracking.mra.defaults.dsnlov.lbl.year" scope="request"/></label>
							
							<ihtml:text property="year" name="DSNLovForm" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_ISSUEDATE"  maxlength="1"/>
							</div>
							<div class="ic-input ic-label-33 ">
							<label><common:message  key="mailtracking.mra.defaults.dsnlov.lbl.dsn" scope="request"/></label>
							
							<ihtml:text property="dsnNumber" name="DSNLovForm" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_DSN"  maxlength="4"/>
							</div>
							<div class="ic-input ic-label-33  ">
							<label><common:message  key="mailtracking.mra.defaults.dsnlov.lbl.rsn" scope="request"/></label>
						
							<ihtml:text property="recepatableSerialNumber" name="DSNLovForm" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_RSN"  maxlength="3"/>
							</div>
							<div class="ic-input ic-label-33  ">
							<label><common:message  key="mailtracking.mra.defaults.dsnlov.lbl.hni" scope="request"/></label>
						
							<ihtml:text property="highestNumberIndicator" name="DSNLovForm" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_HNI"  maxlength="1"/>
							</div>
							</div>
							<div class="ic-row">
						   							
							<div class="ic-input ic-label-33  ">
							<label><common:message  key="mailtracking.mra.defaults.dsnlov.lbl.ri" scope="request"/></label>
					
							<ihtml:text property="registeredIndicator" name="DSNLovForm" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_RI"  maxlength="1"/>
							</div>
													
							<div class="ic-input ic-label-33  ">
							<label><common:message  key="mailtracking.mra.defaults.dsnlov.lbl.condocno" scope="request"/></label>
						
							<ihtml:text property="condocno" name="DSNLovForm" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_CONDOCNO"  maxlength="13"/>
							</div>
							<div class="ic-input ic-label-33  ">
							<label><common:message  key="mailtracking.mra.defaults.dsnlov.tooltip.fromdate" scope="request"/></label>
							
								        <ibusiness:calendar
								     	property="fromDate"
								    	componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_FROMDATE"
						         	    type="image"
						         	    id="fromDate"
						                />
										</div>
										
							<div class="ic-input ic-label-33  ">
							<label><common:message  key="mailtracking.mra.defaults.dsnlov.tooltip.todate" scope="request"/></label>
							
							
							 
								        <ibusiness:calendar
								     	property="toDate"
								    	componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_TODATE"
						         	    type="image"
						         	    id="toDate"
											/>
							
							</div>
							</div>
							<div class="ic-row">
								<div class="ic-button-container">
									<ihtml:nbutton property="btnList" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_LSTBTN">
										<common:message  key="mailtracking.mra.defaults.maintaincca.lbl.button.list" />
									</ihtml:nbutton>
										<ihtml:nbutton property="btnClear" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_CLEARBTN">
										<common:message  key="mailtracking.mra.defaults.maintaincca.lbl.button.clear" />
									</ihtml:nbutton>
								</div>
							</div>
							</div>
							
</div>
</div>
</div>
				<!--to add pagination-->
			<div class="ic-main-container">
				<div class="ic-row">		 
				<logic:present name="DSNLovForm" property="dsnLovPage" >
							<bean:define id="lovList" name="DSNLovForm" property="dsnLovPage" toScope="page"/>
							<logic:present name="lovList">
								<bean:define id="multiselect" name="DSNLovForm" property="multiselect" />

								<logic:equal name="DSNLovForm" property="pagination" value="Y">
									<!-- -PAGINATION TAGS -->
									<bean:define id="lastPageNum" name="DSNLovForm" property="lastPageNum" />
									<div style="float:left" >
												<common:paginationTag pageURL="javascript:preserveSelectedvalues('lastPageNum','displayPage')" name="lovList"
													display="label" labelStyleClass="iCargoResultsLabel" lastPageNum="<%=(String)lastPageNum %>" />
									</div>
									
											<div style="float:right" class="paddR5" >
												<common:paginationTag pageURL="javascript:preserveSelectedvalues('lastPageNum','displayPage')" name="lovList" display="pages"
												linkStyleClass="iCargoLink" disabledLinkStyleClass="iCargoLink"	lastPageNum="<%=(String)lastPageNum%>"/>
											</div>
									

									<!--  END -->
								</logic:equal>
							</logic:present>
							</logic:present>
							</div>
					    
			
           <div class="ic-row">
					<bean:define id="strFormCount" name="DSNLovForm" property="formCount"  />
						<bean:define id="strMultiselect" name="DSNLovForm" property="multiselect" />
						<bean:define id="strLovTxtFieldName" name="DSNLovForm" property="lovTxtFieldName"  />
						<bean:define id="strLovDescriptionTxtFieldName" name="DSNLovForm" property="lovDescriptionTxtFieldName" />
						<bean:define id="strSelectedValues" name="DSNLovForm" property="selectedValues" />
						<bean:define id="arrayIndex" name="DSNLovForm" property="index"/>	

	  <div id="div1"  class="tableContainer" style="height:140px">
<table  class="fixed-header-table" id="lovListTable" >
									
											<thead>
											
												
												<td width="5%"> &nbsp;</td>
												<td width="14%" class="iCargoLabelLeftAligned"><common:message  key="mailtracking.mra.defaults.dsnlov.lbl.originOE" scope="request"/></td>
												<td width="14%" class="iCargoLabelLeftAligned"><common:message  key="mailtracking.mra.defaults.dsnlov.lbl.destOE" scope="request"/></td>
												<td width="17%" class="iCargoLabelLeftAligned"><common:message  key="mailtracking.mra.defaults.dsnlov.lbl.category" scope="request"/></td>
												<td width="17%" class="iCargoLabelLeftAligned"><common:message  key="mailtracking.mra.defaults.dsnlov.lbl.subclass" scope="request"/></td>
												<td width="11%" class="iCargoLabelLeftAligned"><common:message  key="mailtracking.mra.defaults.dsnlov.lbl.year" scope="request"/></td>
												<td width="11%" class="iCargoLabelLeftAligned"><common:message  key="mailtracking.mra.defaults.dsnlov.lbl.dsn" scope="request"/></td>
												<td width="11%" class="iCargoLabelLeftAligned"><common:message  key="mailtracking.mra.defaults.dsnlov.lbl.rsn" scope="request"/></td>
												<td width="9%" class="iCargoLabelLeftAligned"><common:message  key="mailtracking.mra.defaults.dsnlov.lbl.hni" scope="request"/></td>
												<td width="9%" class="iCargoLabelLeftAligned"><common:message  key="mailtracking.mra.defaults.dsnlov.lbl.ri" scope="request"/></td>
												<td width="18%" class="iCargoLabelLeftAligned"><common:message  key="mailtracking.mra.defaults.dsnlov.lbl.condocno" scope="request"/></td>
												
											</thead>
											<tbody>	
											
											<logic:present name="DSNLovForm" property="dsnLovPage">
												<bean:define id="lovList" name="DSNLovForm" property="dsnLovPage" toScope="page"/>
												<logic:present name="lovList">
												<% int i=0;%>
												<jsp:setProperty name="DSNLovForm" property="lovActionType" value="dsnList"/>
														<logic:iterate id = "val" name="lovList" indexId="indexId">
															
																
																<logic:notEqual name="DSNLovForm" property="multiselect" value="Y">
																
																<%String checkValue = ((CCAdetailsVO)val).getBillingBasis();%>
																
																		<tr ondblclick="setValueOnDoubleClick('<%=checkValue%>','<%=((CCAdetailsVO)val).getCsgDocumentNumber()%>',
																			'<%= strLovTxtFieldName%>','<%=strLovDescriptionTxtFieldName %>',<%=arrayIndex%>)">
																</logic:notEqual>
																<logic:equal name="DSNLovForm" property="multiselect" value="Y">
																<tr>
																<td width="3%">
																			<%
																			if(((String)strSelectedValues).contains(((CCAdetailsVO)val).getBillingBasis())){ %>
																				<input type="checkbox" name="selectCheckBox" value="<%=((CCAdetailsVO)val).getBillingBasis()%>"  checked="checked"/>
																			<%}else{ %>
																				<input type="checkbox" name="selectCheckBox" value="<%=((CCAdetailsVO)val).getBillingBasis()%>"  />
																			<% } %>
																		</td>
																	</logic:equal>
																	<logic:notEqual name="DSNLovForm" property="multiselect" value="Y">
																			<td width="3%">
																				<%String checkVal = ((CCAdetailsVO)val).getBillingBasis();%>

																				
																			<%

																			if(   ((String)strSelectedValues).equals(((CCAdetailsVO)val).getDsnNo()  )){ %>
																				<input type="checkbox" name="selectCheckBox" value="<%=checkVal%>"  onclick="singleSelect('<%=checkVal%>');" checked="checked" />
																			<%}else{ %>
																				<input type="checkbox" name="selectCheckBox" value="<%=checkVal%>"  onclick="singleSelect('<%=checkVal%>');"/>
																			<% } %>


																			</td>
																	</logic:notEqual>
																	
																	
																	<td width="10%" align="left">
																	<bean:write name="val" property="origin"/>
																	</td>
																	<td width="10%" align="left">
																	<bean:write name="val" property="destination"/>
																	</td>
																	<td width="10%" align="left">
																	<bean:write name="val" property="category"/>
																	</td>
																	<td width="10%" align="left">
																	<bean:write name="val" property="subClass"/>
																	</td>
																	<td width="10%" align="left">
																	<bean:write name="val" property="year"/>
																	</td>
																	<td width="10%" align="left">
																	<bean:write name="val" property="dsnNo"/>
																	</td>
																	<td width="10%" align="left">
																	<bean:write name="val" property="rsn"/>
																	</td>
																	<td width="10%" align="left">
																	<bean:write name="val" property="hni"/>
																	</td>
																	<td width="10%" align="left">
																	<bean:write name="val" property="regind"/>
																	</td>
																	<td width="10%" align="left">
																	<bean:write name="val" property="csgDocumentNumber"/>
																	</td>
																	
															</tr>
														</logic:iterate>
													
												</logic:present>
											</logic:present>																	
					
								</tbody>
							</table>
							</div>
							</div>
							</div>

						<div class="ic-foot-container">
			
					<div class="ic-button-container">

								
									<input type="button" name="btnOk" value="OK" class="iCargoButtonSmall" onclick="setValueForDifferentModes('<%=strMultiselect%>','<%=strFormCount%>','<%=strLovTxtFieldName%>','<%="" %>',<%=arrayIndex%>)" />
								
									<ihtml:button property="btnClose" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_CANCEL" >
									<common:message key="mailtracking.mra.defaults.maintaincca.lbl.cancel" />
									</ihtml:button>
								

							</div>
							</div>
							
							</div>
	
	
  
  </ihtml:form>
	</div>		

	</body>
</html:html>
