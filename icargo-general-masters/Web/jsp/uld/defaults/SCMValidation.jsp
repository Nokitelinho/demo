<%@ include file="/jsp/includes/tlds.jsp" %>
 <%@page import="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO"%>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.SCMValidationForm"%>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
 <html:html>

 <head>
		
	
		<title><common:message key="uld.defaults.scmvalidation" bundle="SCMValidationResources" scope="request" /></title>
		<common:include type="script" src="/js/uld/defaults/SCMValidation_Script.jsp" />
		<meta name="decorator" content="mainpanelrestyledui">


</head>

<body style="width:85%;"  class="ic-center">
	
	
<%@include file="/jsp/includes/reports/printFrame.jsp" %>
	<bean:define id="form"
		name="SCMValidationForm"
		type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.SCMValidationForm"
			toScope="page" />

			<business:sessionBean id="oneTimeFacilityType"
						moduleName="uld.defaults"
						screenID="uld.defaults.scmvalidation"
						method="get"
						attribute="facilityType" />
			<business:sessionBean id="oneTimeMode"
						moduleName="uld.defaults"
						screenID="uld.defaults.scmvalidation"
						method="get"
						attribute="scmStatus" />
			<business:sessionBean id="KEY_SCMVAL"
						moduleName="uld.defaults"
						screenID="uld.defaults.scmvalidation"
						method="get"
						attribute="SCMValidationVOs" />

	<div class="iCargoContent" style="width:99%;height:100%;overflow:auto;">
	<ihtml:form action="/uld.defaults.screenloaddscmvalidation.do">
		<ihtml:hidden property="lastPageNum" />
	    <ihtml:hidden property="displayPage" />
	    <ihtml:hidden property="statusFlag"/>


	    <input type="hidden" name="currentDialogId" />
		<input type="hidden" name="currentDialogOption" />


	<div class="ic-content-main">    
	   <span class="ic-page-title ic-display-none">
	   <common:message key="uld.defaults.scmvalidation.scmvalidation" scope="request" />
	   </span>	
	   <div class="ic-head-container">
        
		 <div class="ic-filter-panel">
		 <div class="ic-row">
		 <h4><common:message key="uld.defaults.scmvalidation.searchcriteria" /></h4>
         </div>		 
		  <div class="ic-input-container ">		
           <div class="ic-row">
		    <div class="ic-input ic-split-30 ic-label-30" >
		     <label>	
			 <common:message key="uld.defaults.scmvalidation.uldtypecode" />
			 </label>								
					<ihtml:text componentID="ULD_DEFAULTS_SCMVALIDATION_ULDTYPECODE" property="uldTypeCode"   style="text-transform : uppercase" maxlength="5" />
					<div class="lovImg">
					<img name="uldlov" id="uldlov" src="<%= request.getContextPath()%>/images/lov.png" width="22" height="22" border="0" alt="ULD Type LOV"/>
			</div>
			</div>
            <div class="ic-input ic-split-30 ic-label-30 ic-mandatory" >
		     <label>			
			 <common:message key="uld.defaults.scmvalidation.airport" />
	         </label>
					<ihtml:text componentID="ULD_DEFAULTS_SCMVALIDATION_AIRPORT" property="airport"  />
					<div class="lovImg">
					<img src="<%= request.getContextPath()%>/images/lov.png" tabindex="19"  width="22" height="22" id="airportLovImg" name="airportLovImg" onclick="displayLOV('showAirport.do','N','Y','showAirport.do',document.forms[1].airport.value,'airport','1','airport','',0)" alt="Airport LOV"/>
			</div>			
           </div>
		<div class="ic-input ic-split-30 ic-label-30" >
		     <label>		
			 <common:message key="uld.defaults.scmvalidation.facilitytype" />
			 </label>			
					        <ihtml:select componentID="ULD_DEFAULTS_SCMVALIDATION_FACILITYCOMBO" property="facilityType">
								<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
									<logic:present name="oneTimeFacilityType">
									<%System.out.println("one times present");%>
										<logic:iterate id="frmMode" name="oneTimeFacilityType">
											<bean:define id="parameterType" name="frmMode" property="fieldType"/>
												<logic:equal name="parameterType" value="uld.defaults.facilitytypes">
													<logic:present name="frmMode" property="fieldValue">
														<bean:define id="fieldVal" name="frmMode" property="fieldValue" />
														<bean:define id="fieldDesc" name="frmMode" property="fieldDescription" />
														<ihtml:option value="<%=(String)fieldVal%>"><bean:write name="frmMode" property="fieldDescription" /></ihtml:option>
													</logic:present>
												</logic:equal >
										</logic:iterate>
									</logic:present>
									<logic:notPresent name="oneTimeFacilityType">
										<%System.out.println("one times not present");%>
									</logic:notPresent>
							</ihtml:select>
			</div>
           </div>
		   <div class="ic-row">
		    
            <div class="ic-input ic-split-30 ic-label-30" >
		     <label>			
			 <common:message key="uld.defaults.scmvalidation.location" />
			 </label>			
							<ihtml:text componentID="ULD_DEFAULTS_SCMVALIDATION_LOCATION" property="location" maxlength="12"  />
							<button type="button" class="iCargoLovButton" name="facilitycodelov" id="facilitycodelov"></button>
		    </div>
			<div class="ic-input ic-split-30 ic-label-30" >
		     <label>		   
			 <common:message key="uld.defaults.scmvalidation.scmstatus" />
			 </label>			
							<ihtml:select componentID="ULD_DEFAULTS_SCMVALIDATION_SCMSTATUSCOMBO" property="scmStatus">
								<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
									<logic:present name="oneTimeMode">
										<logic:iterate id="frmMode" name="oneTimeMode">
											<bean:define id="parameterType" name="frmMode" property="fieldType"/>
												<logic:equal name="parameterType" value="uld.defaults.scmstatus">
													<logic:present name="frmMode" property="fieldValue">
														<bean:define id="fieldVal" name="frmMode" property="fieldValue" />
														<bean:define id="fieldDesc" name="frmMode" property="fieldDescription" />
														<ihtml:option value="<%=(String)fieldVal%>"><bean:write name="frmMode" property="fieldDescription" /></ihtml:option>
													</logic:present>
												</logic:equal >
										</logic:iterate>
									</logic:present>
								</ihtml:select>
			</div>
            <div class="ic-button-container paddR5">			

								<ihtml:nbutton property="btList" componentID="BTN_ULD_DEFAULTS_SCMVALIDATION_LIST" accesskey="L">
									<common:message key="uld.defaults.scmvalidation.list" />
								</ihtml:nbutton>
								<ihtml:nbutton property="btClear" componentID="BTN_ULD_DEFAULTS_SCMVALIDATION_CLEAR" accesskey="C">
									<common:message key="uld.defaults.scmvalidation.clear" />
								</ihtml:nbutton>
            </div>
		   </div>
           
		  </div>
         </div>
        </div>
        <div class="ic-main-container">		
		 <div class="ic-input-container ">
		   <div class="ic-row"> 		
            <h4 style="text-align:left"><common:message  key="ULD.DEFAULTS.SCMdetails" scope="request"/></h4>
           </div> 
           <div class="ic-row"> 
			                             <logic:present name="KEY_SCMVAL">
													<common:paginationTag pageURL="javascript:submitList('lastPageNum','displayPage')"
													name="KEY_SCMVAL"
													display="label"
													labelStyleClass="iCargoResultsLabel"
													lastPageNum="<%=((SCMValidationForm)form).getLastPageNum() %>" />
												    </logic:present>

												   <logic:notPresent name="KEY_SCMVAL">
												 		&nbsp;
												   </logic:notPresent>


									
									  		          <logic:present name="KEY_SCMVAL">
													  
													  <div class="ic-button-container paddR5">
									  				  <common:paginationTag
									  				  pageURL="javascript:submitList('lastPageNum','displayPage')"
									  				  name="KEY_SCMVAL"
									  				  display="pages"
													  linkStyleClass="iCargoLink"
													  disabledLinkStyleClass="iCargoLink"
									  				  lastPageNum="<%=((SCMValidationForm)form).getLastPageNum() %>" 
													  exportToExcel="true"
													  exportTableId="scmValidationTable"
													  exportAction="uld.defaults.scmvalidationlist.do"/>
													  </div>
									  		           </logic:present>

									  		 	  <logic:notPresent name="KEY_SCMVAL">
									  		 		 &nbsp;
									  			  </logic:notPresent>
		  </div>						
		  <div class="ic-row">
				<div class="tableContainer" id="div1" style="height:480px">
					<table width="100%" class="fixed-header-table" id="scmValidationTable">
						<thead>
							<tr class="ic-th-all">
							  <th style="width:10%"/>
							  <th style="width:10%"/>
							  <th style="width:10%" />
							  <th style="width:10%"/>
							  <th style="width:10%"/>  
								  <th style="width:15%"/>
								  <th style="width:10%" />
								  <th style="width:25%" />	 
							</tr>
							<tr>
								<td width="15%" class="iCargoTableHeader" rowspan="2" >
									<common:message key="uld.defaults.scmvalidation.uldno"/>
								</td>
								<td width="15%" class="iCargoTableHeader"rowspan="2">
									<common:message key="uld.defaults.scmvalidation.facilitytype"/>
								</td>
								<td width="15%" class="iCargoTableHeader" rowspan="2">
									<common:message key="uld.defaults.scmvalidation.location"/>
								</td>
								<td width="10%" class="iCargoTableHeader" rowspan="2" >
									<common:message key="uld.defaults.scmvalidation.sighted"/>
								</td>
								<td width="15%" class="iCargoTableHeader" rowspan="2" >
									<common:message key="uld.defaults.scmvalidation.prevmissing"/>
								</td>
								<td width="28%" class="iCargoTableHeader" colspan= "3" >
									<common:message key="uld.defaults.scmvalidation.lastmovementDetails"/>
								</td>
							</tr>
							<tr class="iCargoTableHeaderLabel">
								<td class="iCargoTableHeader" ><common:message key="uld.defaults.scmvalidation.flightDetails"/></td>
								<td class="iCargoTableHeader" ><common:message key="uld.defaults.scmvalidation.flightSegment"/></td>
								<td class="iCargoTableHeader" ><common:message key="uld.defaults.scmvalidation.remarks"/></td>
							</tr>
						</thead>
							<tbody id="scmValidationTableBody">
								<logic:present name="KEY_SCMVAL">
								<logic:iterate id = "SCMValidationVO" name="KEY_SCMVAL" indexId="rowCount" scope="page" type="com.ibsplc.icargo.business.uld.defaults.message.vo.SCMValidationVO">
									<tr>
										<td class="iCargoTableDataTd">
											
											  	<logic:present name="SCMValidationVO" property="uldNumber">
											  		<bean:write name="SCMValidationVO" property="uldNumber"/>
											  	</logic:present>
											  	<logic:notPresent name="SCMValidationVO" property="uldNumber">
											  	</logic:notPresent>
											
										</td>
										<td class="iCargoTableDataTd">
											
												<logic:present name="SCMValidationVO" property="facilityType">
													<bean:write name="SCMValidationVO" property="facilityType"/>
												</logic:present>
												<logic:notPresent name="SCMValidationVO" property="facilityType">
												</logic:notPresent>
											
										</td>
										<td class="iCargoTableDataTd">
											
												<logic:present name="SCMValidationVO" property="location">
													<bean:write name="SCMValidationVO" property="location"/>
												</logic:present>
												<logic:notPresent name="SCMValidationVO" property="location">
												</logic:notPresent>
											
										</td>
										<td >
											
												<logic:present name="SCMValidationVO" property="scmFlag">
													<bean:define id="scmFlag" name="SCMValidationVO" property="scmFlag" />
														<logic:equal name="scmFlag" value="Y">
															<img src="<%= request.getContextPath()%>/images/icon_on.gif" tabindex="18"  width="18" height="18" />
															<!-- <input type="checkbox" name="sighted" checked style="width:50px;" disabled="true"/> -->
														</logic:equal>
														<logic:equal name="scmFlag" value="N">
															<img src="<%= request.getContextPath()%>/images/icon_off.gif" tabindex="18"  width="18" height="18" />
															<!-- <input type="checkbox" name="sighted"  style="width:50px;" disabled="true"/> -->
														</logic:equal>
												</logic:present>
												<logic:notPresent name="SCMValidationVO" property="scmFlag">
														<input type="checkbox" name="sighted" style="width:50px;" disabled="true"/>
												</logic:notPresent>
											
										</td>
										<td>
											
												<logic:present name="SCMValidationVO" property="prevMissingFlag">
													<bean:define id="prevMissingFlag" name="SCMValidationVO" property="prevMissingFlag" />
														<logic:equal name="prevMissingFlag" value="Y">
															<img src="<%= request.getContextPath()%>/images/icon_on.gif" tabindex="18"  width="18" height="18" />
															<!-- <input type="checkbox" name="sighted" checked style="width:50px;" disabled="true"/> -->
														</logic:equal>
														<logic:equal name="prevMissingFlag" value="N">
															<img src="<%= request.getContextPath()%>/images/icon_off.gif" tabindex="18"  width="18" height="18" />
															<!-- <input type="checkbox" name="sighted"  style="width:50px;" disabled="true"/> -->
														</logic:equal>
												</logic:present>
												<logic:notPresent name="SCMValidationVO" property="prevMissingFlag">
														<input type="checkbox" name="sighted" style="width:50px;" disabled="true"/>
												</logic:notPresent>
											
										</td>
										<td class="iCargoTableDataTd">
											
												<logic:present name="SCMValidationVO" property="flightDetails">
												<%
													String[] flightdetails = SCMValidationVO.getFlightDetails().split("~");	
												%>
												<%if(flightdetails.length==2){%>
												<%=flightdetails[0]%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=flightdetails[1]%>
												<%}%>
												</logic:present>
												<logic:notPresent name="SCMValidationVO" property="flightDetails">
												</logic:notPresent>
											
										</td>
										<td class="iCargoTableDataTd">
											
												<logic:present name="SCMValidationVO" property="flightSegment">
													<bean:write name="SCMValidationVO" property="flightSegment"/>
												</logic:present>
												<logic:notPresent name="SCMValidationVO" property="flightSegment">
												</logic:notPresent>
											
										</td>
										<td class="iCargoTableDataTd">
											
												<logic:present name="SCMValidationVO" property="remarks">
													<bean:write name="SCMValidationVO" property="remarks"/>
												</logic:present>
												<logic:notPresent name="SCMValidationVO" property="remarks">
												</logic:notPresent>
											
										</td>
									</tr>
								</logic:iterate>
								</logic:present>
							</tbody>
							</table>
						</div>
			</div>
			<div class="ic-row ic-input-round-border paddR15 marginT5" style="height:60px ;width:98%">
			 <div class="ic-split-33 ic-input">
			            <label>
						<common:message key="uld.defaults.scmvalidation.total" />
					    </label>					
						<ihtml:text componentID="ULD_DEFAULTS_SCMVALIDATION_TOTAL"  property="total"   style="text-transform : uppercase;text-align:right" readonly="true"   />
			 </div>	
             <div class="ic-split-33 ic-input">
			            <label>			 
						<common:message key="uld.defaults.scmvalidation.notsighted" />
					    </label>
						<ihtml:text componentID="ULD_DEFAULTS_SCMVALIDATION_NOTSIGHTED"  property="notSighted"   style="text-transform : uppercase;text-align:right" readonly="true" />
			 </div>	
			 <div class="ic-split-33 ic-input">
			            <label>	
						<common:message key="uld.defaults.scmvalidation.percentagemissing" />
					    </label>
						<ihtml:text componentID="ULD_DEFAULTS_SCMVALIDATION_MISSING"  property="missing"   style="text-transform : uppercase;text-align:right" readonly="true"  />
			 </div>	
            </div>
		</div>
	   </div>
	   <div class="ic-foot-container">						
            <div class="ic-row">
	            <div class="ic-button-container paddR5">
				<ihtml:nbutton property="btPrint" componentID="BTN_ULD_DEFAULTS_SCMVALIDATION_PRINT" accesskey="P">
					<common:message key="uld.defaults.scmvalidation.print" />
				</ihtml:nbutton>
				<ihtml:nbutton property="btClose" componentID="BTN_ULD_DEFAULTS_SCMVALIDATION_CLOSE" accesskey="O">
					<common:message key="uld.defaults.scmvalidation.close" />
				</ihtml:nbutton>
				</div>
			</div>
	   </div>
</div>			
</ihtml:form>
</div>

				
		
	</body>
</html:html>









