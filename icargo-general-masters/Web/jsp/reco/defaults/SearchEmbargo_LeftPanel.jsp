<%@ page language="java" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Collection"%>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import="org.apache.struts.action.ActionMessages"%>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.SearchEmbargoForm"%>
<business:sessionBean id="geographicLevelTypes" moduleName="reco.defaults" screenID="reco.defaults.searchembargo" method="get" attribute="geographicLevelTypes" />
<business:sessionBean id="levelCodes" moduleName="reco.defaults" screenID="reco.defaults.searchembargo" method="get" attribute="levelCodes" />
<business:sessionBean id="parameterCodes" moduleName="reco.defaults" screenID="reco.defaults.searchembargo" method="get" attribute="parameterCodes" />
<business:sessionBean id="applicableTransactions" moduleName="reco.defaults" screenID="reco.defaults.searchembargo" method="get" attribute="applicableTransactions" />
<business:sessionBean id="categories" moduleName="reco.defaults" screenID="reco.defaults.searchembargo" method="get" attribute="categories" />
<business:sessionBean id="originTypes" moduleName="reco.defaults" screenID="reco.defaults.searchembargo" method="get" attribute="geographicLevelTypes" />
<business:sessionBean id="destinationTypes" moduleName="reco.defaults" screenID="reco.defaults.searchembargo" method="get" attribute="geographicLevelTypes" />
<business:sessionBean id="viaPointTypes" moduleName="reco.defaults" screenID="reco.defaults.searchembargo" method="get" attribute="geographicLevelTypes" />
<business:sessionBean id="embargoDetails" moduleName="reco.defaults" screenID="reco.defaults.searchembargo" method="get" attribute="EmabrgoDetailVOs" />
<business:sessionBean id="ruleTypes" moduleName="reco.defaults" screenID="reco.defaults.searchembargo" method="get" attribute="RuleTypes" />
<business:sessionBean id="dayOfOperationApplicableOn" moduleName="reco.defaults" screenID="reco.defaults.searchembargo" method="get" attribute="dayOfOperationApplicableOn" />
<business:sessionBean id="embargoSearchVO" moduleName="reco.defaults" screenID="reco.defaults.searchembargo" method="get" attribute="EmbargoSearchVO" />
<business:sessionBean id="regulatoryComposeMessages" moduleName="reco.defaults" screenID="reco.defaults.searchembargo" method="get" attribute="regulatoryComposeMessages" />
<business:sessionBean id="regulatoryComplianceRules" moduleName="reco.defaults" screenID="reco.defaults.searchembargo" method="get" attribute="regulatoryComplianceRules" />
<business:sessionBean id="flightTypes" moduleName="reco.defaults" screenID="reco.defaults.searchembargo" method="get" attribute="flightTypes" />
<business:sessionBean id="serviceCargoClass" moduleName="reco.defaults" screenID="reco.defaults.searchembargo" method="get" attribute="serviceCargoClass" />
<business:sessionBean id="shipmentType" moduleName="reco.defaults" screenID="reco.defaults.searchembargo" method="get" attribute="shipmentType" />

<bean:define id="form" name="SearchEmbargoForm" toScope="page" type="com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.SearchEmbargoForm"/>
<div class="ic-row">
	<div class="ic-col-20 ic-border">
	<div class="ic-row">
		<div class="tableContainer" id="div1" style="height:794px;">
			<table class="fixed-header-table" id="tableContainer1">
				<tbody>
					<logic:present name="embargoSearchVO" property="embargoLeftPanelParameterVOs">
						<bean:define id="leftPanelParameters" name="embargoSearchVO" property="embargoLeftPanelParameterVOs" />
						<%int i=1;%>
						<logic:iterate id="leftPanelParameter" name="leftPanelParameters" type="com.ibsplc.icargo.business.reco.defaults.vo.EmbargoLeftPanelParameterVO">
							<bean:define id="fieldDescription" name="leftPanelParameter" property="fieldDescription" type="String"/>
							<bean:define id="fieldValue" name="leftPanelParameter" property="fieldValue" type="String"/>
							<%List<String> modules =(List<String>)leftPanelParameter.getSubModules(); %>
							<%String moduleName = (String)fieldDescription; 
							String moduleValue = (String)fieldValue;
							int parameterTotalCount=0;
							%>
							<!-- Added for getting parameters total count  starts -->
							<logic:present name="leftPanelParameter" property="parameters">            
								<logic:iterate id="parameter" name="leftPanelParameter" property="parameters" type="com.ibsplc.icargo.business.reco.defaults.vo.EmbargoParameterVO">         
									 <%List<String> parameterModules =(List<String>)parameter.getSubModules(); %>
									<%
									if(null !=parameterModules && parameterModules.size()>0){
										 parameterTotalCount=parameterTotalCount+parameterModules.size();
									}%>
									</logic:iterate>
							</logic:present>
							<!-- Added for getting parameters total count ends -->
							<tr class="ic-table-row-main"  id="<%=moduleName%><%=i%>">
								<td class="iCargoTableDataTd ic-border-none" width="10%">
									<a href="#" onclick="toggleRows(this,'folder-collapse','folder-expand')" class="ic-tree-table-expand folder-collapse tier1"></a>
								</td>
								<td width="90%" class="iCargoTableDataTd ic-border-none"><%=moduleName%>
									<%if(null !=modules && modules.size()>0){
										%>(<%=modules.size()%>)<%
										}else if(parameterTotalCount>0){
										%>(<%=parameterTotalCount%>)<%}%>
								 </td>
							</tr>
							<tr class="ic-table-row-sub"  id="<%=moduleName%><%=i%>-<%=i%>">
								<td class="iCargoTableDataTd ic-border-none" width="10%"></td>
								<td class="iCargoTableDataTd ic-border-none" width="90%"><a href="#" class="tier5"></a>
									<logic:present name="leftPanelParameter" property="subModules">												
										<logic:iterate id="submodule" name="leftPanelParameter" property="subModules" type="String">
											<%String submodulename =(String)submodule; %>													
											<div  class="ic-row">
												<a href="#" onclick="javascript:getEmbargoDetails('<%=moduleValue%>','<%=submodulename%>')"><%=submodule%></a>
												<%if ("CNG".equals(moduleValue)){%>
												<img id="parameterValues_link_CNTGRP-<%=(String)submodulename%>" src="<%=request.getContextPath()%>/images/info.gif"
																		width="17" height="17"  onclick="viewGroupingDetails(this)"/>
												<%}
												else if("APG".equals(moduleValue)){%>
													<img id="parameterValues_link_ARPGRP_<%=(String)submodulename%>" src="<%=request.getContextPath()%>/images/info.gif"
																		width="17" height="17"  onclick="viewGroupingDetails(this)"/>
												<%}%>
											</div>
										</logic:iterate>									
									</logic:present>												 
									<!-- Parameter section starts-->													
									<logic:present name="leftPanelParameter" property="parameters">
										<bean:define id="parameters" name="leftPanelParameter" property="parameters"/>
										
										<div class="tableContainer" id="div2">
										<table class="fixed-header-table" id="tableContainer2">
											<tbody>														   
												<logic:iterate id="parameter" name="leftPanelParameter" property="parameters" type="com.ibsplc.icargo.business.reco.defaults.vo.EmbargoParameterVO">									
													<%String parameterCode =(String)parameter.getParameterCode(); %>
													<%String parameterDescription =(String)parameter.getParameterDescription(); %>
													<%List<String> parameterModules =(List<String>)parameter.getSubModules(); %>
													<tr class="ic-table-row-main"  id="<%=parameterCode%><%=i%>">
														<td class="iCargoTableDataTd ic-border-none" width="12%">
															<a href="#" onclick="toggleRows(this,'folder-collapse','folder-expand')" class="ic-tree-table-expand folder-collapse tier1"></a>
														</td>
														<td  class="iCargoTableDataTd ic-border-none" width="88%"><%=parameterDescription%>
															<%if(null !=parameterModules && parameterModules.size()>0){
																%>(<%=parameterModules.size()%>)<%}%>
														</td>
													</tr>
													<tr class="ic-table-row-sub"  id="<%=parameterCode%><%=i%>-<%=i%>">
														<td  class="iCargoTableDataTd ic-border-none" width="10%"></td>
														<td  class="iCargoTableDataTd ic-border-none" width="90%"><a href="#" class="tier5"></a>															
															<logic:present name="parameter" property="subModules">														
																<logic:iterate id="parameterValue" name="parameter" property="subModules" type="String">
																	<div  class="ic-row">																			
																	<%
																	String code = "";
																	String desc = "";
																	if(parameterValue !=null & parameterValue.indexOf("~") > -1 ){
																	 code = parameterValue.split("~")[0];
																	 desc = parameterValue.split("~")[1];
																	}else{
																		code=parameterValue;
																	}
																	%>	
																	<a href="#" onclick="javascript:getEmbargoDetails('<%=parameterCode%>','<%=code%>')" title='<%=desc%>'
																		 >
																		<%=code%></a>
																		<%if ("ARLGRP".equals(parameterCode)|| "SCCGRP".equals(parameterCode) || "AGTGRP".equals(parameterCode) || "ACRTYPGRP".equals(parameterCode) || "SHPGRP".equals(parameterCode)|| "CNSGRP".equals(parameterCode)){%>
																		<img id="parameterValues_link_<%=parameterCode%>_<%=(String)parameterValue%>" src="<%=request.getContextPath()%>/images/info.gif" 
																			width="17" height="17"  onclick="viewGroupingDetails(this)"/>
																		<%}%>
																	</div>																			
																</logic:iterate>																	  																																					
															</logic:present>															 
														</td>
													</tr>
												</logic:iterate>
											</tbody>				  
										</table>
										</div>
									
									</logic:present>
									 <!-- Parameter section ends-->  												  								
								</td>
							</tr>
							<%i++;%>
						</logic:iterate>
						<div id="parameterValuesPopUpForSearchCompliance" name="parameterValuesDivForSearchCompliance">	
							<div style="overflow:auto;" class="groupingDetailsBodyForSearchCompliance">
							</div>
						</div>
					</logic:present>
				</tbody>
			</table>
		</div>
	</div>
	</div>

	<div class="ic-border" id="data-div">
		<logic:present name="embargoSearchVO">
			<div class="ic-row">
				<div class="ic-col-50">
					<logic:present name="regulatoryComplianceRules">
						<common:paginationTag pageURL="reco.defaults.simplesearch.do" name="regulatoryComplianceRules" display="label"
							labelStyleClass="iCargoResultsLabel" lastPageNum="<%=form.getLastPageNum() %>" />										 
					</logic:present>
				</div>
				<div class="ic-col-50">
					<div class="ic-button-container">
						<logic:present name="regulatoryComplianceRules">
							<common:paginationTag linkStyleClass="iCargoLink" disabledLinkStyleClass="iCargoLink"
							pageURL="javascript:submitPage('lastPageNum','displayPage')" name="regulatoryComplianceRules" display="pages"
								lastPageNum="<%=form.getLastPageNum() %>" exportToExcel="false" exportTableId="embargoDetails"
								exportAction="reco.defaults.simplesearch.do"/>
						</logic:present>
					</div>					
				</div>
			</div>	
			<div class="ic-row">		
				<div class="tableContainer" id="div1" style="height:770px">
					<table class="fixed-header-table"  id="embargoDetails">
						<thead>
							<tr>        								
							<td style="width:13%;"><common:message  key="reco.defaults.searchembargo.tbl.geographiclevel"/></td>
							<td style="width:8%"><common:message  key="reco.defaults.searchembargo.tbl.complianceissuetype"/></td>
							<td style="width:40%;"><common:message  key="reco.defaults.searchembargo.tbl.description"/></td>
							<td style="width:8%"><common:message  key="reco.defaults.searchembargo.tbl.startdate"/></td>
							<td style="width:8%"><common:message  key="reco.defaults.searchembargo.tbl.enddate"/></td>										
							<td style="width:15%"><common:message  key="reco.defaults.searchembargo.tbl.complancesource"/></td>
							<td style="width:8%"><common:message  key="reco.defaults.searchembargo.tbl.refnumber"/></td>
							</tr>
						</thead>
						<tbody>
							<% String description=null;
							String referenceNumber=null;%>						
							<logic:present name="regulatoryComplianceRules" >								 
								<bean:define id="regulatoryComplianceRules" name="regulatoryComplianceRules" />
								<logic:iterate id="embargoDetailsVO" name="regulatoryComplianceRules" type="com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO" indexId="nIndex">											
									<tr>
										<td class="iCargoTableDataTd">											
										<logic:present name="embargoDetailsVO" property="processType">
											<bean:define id="processType"  name="embargoDetailsVO" property="processType" />
											<%
											String process= processType.toString();
											String processAry[] =process.split("~");											
											%>													
											<%=processAry[0]%><br/>
											<%=processAry[1]%><br/>
											<%=processAry[2]%>
											</logic:present>												
										</td>
										<td class="iCargoTableDataTd">
											<logic:present name="embargoDetailsVO" property="complianceTypeDescription">
												<bean:define id="complianceTypeDescription" name="embargoDetailsVO" property="complianceTypeDescription" />
												<bean:write name="complianceTypeDescription" />
											</logic:present>						
										</td>
										<td class="iCargoTableDataTd">
											<logic:present name="embargoDetailsVO" property="embargoDescription">
											<bean:define id="embargoDescription" name="embargoDetailsVO" property="embargoDescription" />
											<bean:define id="embargoReferenceNumber" name="embargoDetailsVO" property="embargoReferenceNumber" />
											<% description=(String)embargoDescription;%>
											<% referenceNumber=(String)embargoReferenceNumber;%>
											<% if(null != description && description.length()>450){%>
											<%=description.substring(0,450)+"..."%>
											<div class="ic-input-round-border" name="descriptionDiv"  id="descriptionDivId_<%=referenceNumber%>" style="display:none;">
												<div class="ic-row">
													<label><%=description%></label>
												</div>
											</div>
											<img id="descriptionImageId_<%=referenceNumber%>" src="<%=request.getContextPath()%>/images/info.gif" style="cursor:pointer;height:13px;width:13px;" class="info_image" onclick="javascript:showDescription(this,'descriptionDivId_<%=referenceNumber%>','descriptionDiv');"/>										
												<%}else{%>
												<%=description%>
												<%}%>
											</logic:present>
										</td>
										<td class="iCargoTableDataTd">
											<logic:notEmpty name="embargoDetailsVO" property="startDate">
												<bean:define id="startDate" name="embargoDetailsVO" property="startDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>													
												<%=startDate.toDisplayDateOnlyFormat()%>
											</logic:notEmpty>
										</td>
										<td class="iCargoTableDataTd">
											<logic:notEmpty name="embargoDetailsVO" property="endDate">
												<bean:define id="endDate" name="embargoDetailsVO" property="endDate"  type="com.ibsplc.icargo.framework.util.time.LocalDate"/>													
												<%=endDate.toDisplayDateOnlyFormat()%>
											</logic:notEmpty>
										</td>																				
										<td class="iCargoTableDataTd">										
											<logic:present name="embargoDetailsVO" property="categoryDescription">
												<bean:define id="categoryDescription" name="embargoDetailsVO" property="categoryDescription" />
												<bean:write name="categoryDescription" />
											</logic:present>
										</td>
										<td class="iCargoTableDataTd">										
											<logic:present name="embargoDetailsVO" property="embargoReferenceNumber">
												<bean:define id="embargoReferenceNumber" name="embargoDetailsVO" property="embargoReferenceNumber" />
												<bean:write name="embargoReferenceNumber" />
											</logic:present>
										</td>
									</tr>
								</logic:iterate>
							</logic:present>
						</tbody>
					</table>
				</div>
			</div>	   
			</logic:present>
			<logic:notPresent name="embargoSearchVO">
				<ihtml:hidden  property="closeBtnFlag" value="1"/>
				<div class="ic-row">
					<div class="ic-input-round-border">
						<h3 class="ic-col-100 ic-center">
							<common:message  key="reco.defaults.searchembargo.welcome.title"/>
						</h3>
					</div>
				</div>
				<div class="ic-row">
							<div class="tableContainer" id="msgdiv" style="height:745px">
								<table class="fixed-header-table" id="tableContaine3">
									<thead>										
										<tr class="iCargoTableHeadingLeft">
											<td  class="iCargoTableHeaderLabel"  style="width:80%"><common:message  key="reco.defaults.searchembargo.messagetitle"/></td>
											<td class="iCargoTableHeaderLabel" style="width:20%"><common:message  key="reco.defaults.searchembargo.lastupdatedon"/></td>										
										</tr>
									</thead>
									<tbody>
									<logic:present name="regulatoryComposeMessages">
										<logic:iterate id="regulatoryComposeMessage" name="regulatoryComposeMessages" indexId="rowCount">
											<bean:define id="message" name="regulatoryComposeMessage" property="message" />
											<tr>							
												<td class="iCargoTableDataTd ic-center" style="word-wrap:break-word;">														
													<li class="ic-bold-value" style="margin-left:100px;"><%=message%></li>
												</td>
												<td class="iCargoTableDataTd ic-center">
													<logic:present name="regulatoryComposeMessage" property="updatedTransactionTimeView">
															<bean:define id ="updatedTransactionTimeView" name = "regulatoryComposeMessage" property="updatedTransactionTimeView" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
															<%String utcDate=TimeConvertor.toStringFormat(updatedTransactionTimeView.toCalendar(),"dd-MMM-yyyy HH:mm");%>
															<%=utcDate%>
													</logic:present>
												</td>	
											</tr>
										</logic:iterate>													
									</logic:present>
									</tbody>
								</table>
							</div>													
				</div>
			</logic:notPresent>
	</div>
	
</div>