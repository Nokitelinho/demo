<%@ include file="/jsp/includes/tlds.jsp" %>
 <%@page import="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO"%>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.MissingUCMListForm"%>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
 <html:html>

 <head>
		
	
		<title><common:message key="uld.defaults.missingucmlist" bundle="UCMMissingResources" scope="request" /></title>
		<common:include type="script" src="/js/uld/defaults/MissingUCMList_Script.jsp" />
		<meta name="decorator" content="mainpanelrestyledui">


</head>

<body style="width:80%; margin: 0 auto;">
	
	<bean:define id="form"
		name="MissingUCMListForm"
		type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.MissingUCMListForm"
			toScope="page" />

			<business:sessionBean id="KEY_UCMMSGLST"
						moduleName="uld.defaults"
						screenID="uld.defaults.missingucmlist"
						method="get"
						attribute="ULDFlightMessageReconcileDetailsVOs" />

	<div  id="pageDiv" class="iCargoContent" style="overflow:auto">
	<ihtml:form action="/uld.defaults.screenloaddmissingucmlist.do">
		<ihtml:hidden property="lastPageNum" />
	    <ihtml:hidden property="displayPage" />


	<div class="ic-content-main">
            <span class="ic-page-title ic-display-none">
					<common:message key="uld.defaults.missingucmlist.missingucmlist" scope="request" />
			</span>
		 <div class="ic-head-container">	

			  		    <div class="ic-row">
					    
		</div>
		<div class="ic-filter-panel">   
<div class="ic-row ic-label-100">
			 <h4> <common:message key="uld.defaults.missingucmlist.searchcriteria" /> </h4>
			</div>		
        <div class="ic-input-container">
			<div class="ic-row ic-label-30">
							<div class="ic-col-33">
									   <div class="ic-input ic-split-100">
						<label>
							<common:message key="uld.defaults.missingucmlist.flightno" />
						</label>
						
							<ibusiness:flightnumber carrierCodeProperty="carrierCode"
								id="flightNumber"
								flightCodeProperty="flightNumber"
								componentID="TXT_ULD_DEFAULTS_MISSINGUCMLIST_FLIGHTNO"
								carrierCodeStyleClass="iCargoTextFieldVerySmall"
								flightCodeStyleClass="iCargoTextFieldSmall"
							/>
						</div>
									 <div class="ic-input ic-split-100 marginT5">

						
						
						<label>	<common:message key="uld.defaults.missingucmlist.origin" /> </label>
						
						
							<ihtml:text componentID="TXT_ULD_DEFAULTS_MISSINGUCMLIST_ORIGIN" property="origin"  />
							<div class="lovImg">
							<img src="<%= request.getContextPath()%>/images/lov.png" tabindex="19"  width="22" height="22" id="airportLovImg" name="airportLovImg" onclick="displayLOV('showAirport.do','N','Y','showAirport.do',document.forms[1].origin.value,'origin','1','origin','',0)" alt="Airport LOV"/>
						</div>
						</div>
									<div class="ic-input ic-split-100  marginT10">
										<ihtml:checkbox  property="ucmOut" componentID="CMB_ULD_DEFAULTS_MISSINGUCMLIST_UCMOUTMISSED"/>  
										<label class=" ic-inline">	<common:message key="uld.defaults.missingucmlist.ucmoutmissed" /></label>
									</div>
						  </div>
						   <div class="ic-col-33">
									   <div class="ic-input ic-split-100">
										 <label>
										   <common:message key="uld.defaults.missingucmlist.fromdate" />
										 </label>						
										<ibusiness:calendar id="fromDate" componentID="CMP_ULD_DEFAULTS_MISSINGUCMLIST_FROMDATE" property="fromDate" type="image" />
									  </div>
									 <div class="ic-input ic-split-100 marginT5">
						<label>	<common:message key="uld.defaults.missingucmlist.destination" /> </label>
						
							<ihtml:text componentID="TXT_ULD_DEFAULTS_MISSINGUCMLIST_DESTINATION" property="destination"  />
							<div class="lovImg">
							<img src="<%= request.getContextPath()%>/images/lov.png" tabindex="19"  width="22" height="22" id="airportLovImg" name="airportLovImg" onclick="displayLOV('showAirport.do','N','Y','showAirport.do',document.forms[1].destination.value,'destination','1','destination','',0)" alt="Airport LOV"/>
						</div>
						</div>
									 <div class="ic-input ic-split-100  ic-inline marginT10">
						
						
							<ihtml:checkbox  property="ucmIn" componentID="CMB_ULD_DEFAULTS_MISSINGUCMLIST_UCMINMISSED"  />
											<label class=" ic-inline">
							<common:message key="uld.defaults.missingucmlist.ucminmissed" />
						</label>
						</div>
						   </div>
							
							<div class="ic-col-33">
									<div class="ic-input ic-split-100">
									<label>
										<common:message key="uld.defaults.missingucmlist.todate" />
									</label>						
										<ibusiness:calendar id="toDate" componentID="CMP_ULD_DEFAULTS_MISSINGUCMLIST_TODATE" property="toDate" type="image" />
									</div>
							</div>
				</div>
				<div class="ic-row">
						<div class="ic-button-container paddR5">
								<ihtml:nbutton property="btList" componentID="BTN_ULD_DEFAULTS_MISSINGUCMLIST_LIST" accesskey="L">
									<common:message  key="uld.defaults.missingucmlist.list" />
								</ihtml:nbutton>
								<ihtml:nbutton property="btClear" componentID="BTN_ULD_DEFAULTS_MISSINGUCMLIST_CLEAR" accesskey="C">
									<common:message key="uld.defaults.missingucmlist.clear" />
								</ihtml:nbutton>

						</div>
					</div>
				</div>
			</div>
		</div>
		
		
		<div class="ic-main-container">		
					    <div class="ic-row">
			                             <logic:present name="KEY_UCMMSGLST">
													<common:paginationTag pageURL="javascript:submitList('lastPageNum','displayPage')"
													name="KEY_UCMMSGLST"
													display="label"
													labelStyleClass="iCargoResultsLabel"
													lastPageNum="<%=form.getLastPageNum() %>" />
												    </logic:present>

												   <logic:notPresent name="KEY_UCMMSGLST">
												 		&nbsp;
												   </logic:notPresent>


										

						<div class="ic-button-container paddR5" id="_paginationLink">
			                           
									  		          <logic:present name="KEY_UCMMSGLST">
									  				  <common:paginationTag
									  				  pageURL="javascript:submitList('lastPageNum','displayPage')"
									  				  name="KEY_UCMMSGLST"
									  				  display="pages"
													  linkStyleClass="iCargoLink"
													  disabledLinkStyleClass="iCargoLink"
									  				  lastPageNum="<%=form.getLastPageNum() %>" 
													  exportToExcel="true"
													  exportTableId="missingUCMTable"
													  exportAction="uld.defaults.listmissingucms.do"/>
									  		           </logic:present>

									  		 	  <logic:notPresent name="KEY_UCMMSGLST">
									  		 		 &nbsp;
									  			  </logic:notPresent>
										
						</div>
			      </div>
  <div class="ic-row">

						<div class="tableContainer"  id="div1"  style="height:500px; ">
						  <table width="100%" class="fixed-header-table"  id="missingUCMTable">
							<thead>
							  <tr class="iCargoTableHeadingLeft">
                                <td width="20%"  >
									<common:message key="uld.defaults.missingucmlist.flightno"/>
                                 <span></span></td>
								<td width="20%"  >
									<common:message key="uld.defaults.missingucmlist.flightDate"/>
                                 <span></span></td>
								<td width="20%" >
									<common:message key="uld.defaults.missingucmlist.origin"/>
                                  <span></span></td>
								<td width="10%" >
									<common:message key="uld.defaults.missingucmlist.ucmoutreceived"/>
                                <span></span></td>
								<td width="20%" >
									<common:message key="uld.defaults.missingucmlist.destination"/>
                                <span></span></td>
								<td width="10%">
									<common:message key="uld.defaults.missingucmlist.ucminreceived"/>
                                <span></span></td>
							</tr>
							</thead>					
							<tbody id="missingUCMTableBody">
							<logic:present name="KEY_UCMMSGLST">
								<logic:iterate id = "ULDFlightMessageReconcileDetailsVOs" name="KEY_UCMMSGLST" indexId="rowCount" scope="page" type="com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileVO">
							<tr>
								<td width="20%"align="center">
									<center>
										<logic:present name="ULDFlightMessageReconcileDetailsVOs" property="flightNumber">

											<!--<bean:define id="flightNumber"
												name="ULDFlightMessageReconcileDetailsVOs"
												property="flightNumber"
											/>-->
											<!--<ihtml:text		indexId="rowCount"
												componentID="TXT_ULD_DEFAULTS_MISSINGUCMLIST_FLIGHTNUMBER"
												property="flightNumbers"
												value="<%=ULDFlightMessageReconcileDetailsVOs.getFlightNumber()%>"
												disabled="true"
											/>-->
											<bean:write name="ULDFlightMessageReconcileDetailsVOs" property="flightNumber"/>

										</logic:present>
										<logic:notPresent name="ULDFlightMessageReconcileDetailsVOs" property="flightNumber">
											<!--<ihtml:text		indexId="rowCount"
												componentID="TXT_ULD_DEFAULTS_MISSINGUCMLIST_FLIGHTNUMBER"
												property="flightNumbers"
												value=""
												disabled="true"
											/>-->

										</logic:notPresent>
									</center>
								</td>
								<td width="20%"align="center">
									<center>
										<logic:present name="ULDFlightMessageReconcileDetailsVOs" property="flightDate" >

											<!--<ihtml:text 		indexId="rowCount"
																componentID="TXT_ULD_DEFAULTS_MISSINGUCMLIST_FLIGHTDATE"
																property="flightDates"
																value="<%=ULDFlightMessageReconcileDetailsVOs.getFlightDate().toDisplayDateOnlyFormat()%>"
																disabled="true"
											/>-->


											<%
												String fltDate ="";
												if(ULDFlightMessageReconcileDetailsVOs.getFlightDate() != null) {
												fltDate = TimeConvertor.toStringFormat(
															ULDFlightMessageReconcileDetailsVOs.getFlightDate().toCalendar(),"dd-MMM-yyyy");
												}
											%>
											<%=fltDate%>



										</logic:present>

										<logic:notPresent name="ULDFlightMessageReconcileDetailsVOs" property="flightDate">
											<!--<ihtml:text 		indexId="rowCount"
																componentID="TXT_ULD_DEFAULTS_MISSINGUCMLIST_FLIGHTDATE"
																property="flightDates"
																value=""


											/>-->
										</logic:notPresent>
									</center>

								</td>
								<td width="20%"align="center">
									<center>
										<logic:present name="ULDFlightMessageReconcileDetailsVOs" property="origin">
											<!--<bean:define id="origin" name="ULDFlightMessageReconcileDetailsVOs" property="origin"/>
												<ihtml:text property="origins" componentID="TXT_ULD_DEFAULTS_MISSINGUCMLIST_ORIGINS" value ="<%=origin.toString()%>" disabled="true"/>-->
												<bean:write name="ULDFlightMessageReconcileDetailsVOs" property="origin" />
										</logic:present>
										<logic:notPresent name="ULDFlightMessageReconcileDetailsVOs" property="origin">
											<!--<ihtml:text property="origins" componentID="TXT_ULD_DEFAULTS_MISSINGUCMLIST_ORIGINS" value ="" />-->
										</logic:notPresent>
									</center>

								</td>
								<td width="10%"align="center">
									<center>
										<logic:present name="ULDFlightMessageReconcileDetailsVOs" property="ucmOutMissed">
											<bean:define id="ucmOutMissed" name="ULDFlightMessageReconcileDetailsVOs" property="ucmOutMissed" />

																<logic:equal name="ucmOutMissed" value="Y">
																	<input type="checkbox" name="ucmOutMissed" checked style="width:50px;" disabled="true"/>

																</logic:equal>
																<logic:equal name="ucmOutMissed" value="N">
																	<input type="checkbox" name="ucmOutMissed"  style="width:50px;" disabled="true"/>
																</logic:equal>
																
																<!--<logic:notEqual name="ucmOutMissed" value="Y">
																	<input type="checkbox" name="ucmOutMissed"  style="width:50px;" disabled="true"/>
																</logic:notEqual>-->

										</logic:present>
										<logic:notPresent name="ULDFlightMessageReconcileDetailsVOs" property="ucmOutMissed">
												<input type="checkbox" name="ucmOutMissed" style="width:50px;" disabled="true"/>
										</logic:notPresent>
									</center>
								</td>
								<td width="20%"align="center">
									<center>
										<logic:present name="ULDFlightMessageReconcileDetailsVOs" property="destination">
											<!--<bean:define id="destination" name="ULDFlightMessageReconcileDetailsVOs" property="destination"/>
												<ihtml:text property="destinations" componentID="TXT_ULD_DEFAULTS_MISSINGUCMLIST_DESTINATIONS" value ="<%=destination.toString()%>" readonly="true" />-->
												<bean:write name="ULDFlightMessageReconcileDetailsVOs" property="destination" />
										</logic:present>
										<logic:notPresent name="ULDFlightMessageReconcileDetailsVOs" property="destination">
											<!--<ihtml:text property="destinations" componentID="TXT_ULD_DEFAULTS_MISSINGUCMLIST_DESTINATIONS" value ="" />-->
										</logic:notPresent>
									</center>

								</td>
								<td width="10%"align="center">
									<center>
										<logic:present name="ULDFlightMessageReconcileDetailsVOs" property="ucmInMissed">
											<bean:define id="ucmInMissed" name="ULDFlightMessageReconcileDetailsVOs" property="ucmInMissed" />

																<logic:equal name="ucmInMissed" value="Y">
																	<input type="checkbox" name="ucmInMissed" checked style="width:50px;" disabled="true"/>

																</logic:equal>
																<logic:equal name="ucmInMissed" value="N">
																	<input type="checkbox" name="ucmInMissed"  style="width:50px;" disabled="true"/>

																</logic:equal>
																<!--<logic:notEqual name="ucmInMissed" value="Y">
																	<input type="checkbox" name="ucmInMissed"  style="width:50px;" disabled="true"/>

																</logic:notEqual>-->

										</logic:present>
										<logic:notPresent name="ULDFlightMessageReconcileDetailsVOs" property="ucmInMissed">
												<input type="checkbox" name="ucmInMissed" style="width:50px;" disabled="true"/>
										</logic:notPresent>
									</center>
								</td>


							</tr>
							</logic:iterate>
							</logic:present>
							</tbody>


							</table>
						</div>
					</div>
			</div>
		
		<div class="ic-foot-container">						
				    <div class="ic-row">
						<div class="ic-button-container paddR5">
				<ihtml:nbutton property="btClose" componentID="BTN_ULD_DEFAULTS_MISSINGUCMLIST_CLOSE" accesskey="O">
					<common:message key="uld.defaults.missingucmlist.close" />
				</ihtml:nbutton>
			</div>
       		</div>
					</div>
			</div>
</ihtml:form>
</div>

				
		
	</body>
</html:html>









