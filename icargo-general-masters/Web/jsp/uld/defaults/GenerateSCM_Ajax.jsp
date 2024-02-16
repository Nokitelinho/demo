<%@ page language="java" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.GenerateSCMForm" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
 <%@ page import = "com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>	
<%  GenerateSCMForm frm = (GenerateSCMForm)request.getAttribute("GenerateSCMForm");%>
<bean:define id="form"
	 name="GenerateSCMForm"
	 type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.GenerateSCMForm"
	 toScope="page" />

	<business:sessionBean id="systemStock"
		   moduleName="uld.defaults"
			screenID="uld.defaults.generatescm" method="get"
		attribute="systemStock"/>
			
<ihtml:form action="/uld.defaults.messaging.screenloadgeneratescm.do">
 
<div class="ic-row" id="_paginationResultsLabel">
				
				<%String lstPgNo = "";%>
				<logic:present name="GenerateSCMForm" property="lastPageNum">
					<bean:define id="lastPg" name="GenerateSCMForm" property="lastPageNum" scope="request"  toScope="page" />
					<!--bean:write name="GenerateSCMForm" property="lastPageNum" scope="request"  toScope="page" /-->
					<%
						lstPgNo = (String) lastPg;
					%>

				  </logic:present>
			      <logic:present name="systemStock" >
			      <bean:define id="pageObj" name="systemStock"  />
				  <common:paginationTag pageURL="uld.defaults.messaging.listgeneratescm.do"
							name="pageObj"
							display="label"
							labelStyleClass="iCargoResultsLabel"
							lastPageNum
							="<%=lstPgNo%>" />
							</logic:present>
				
				<!-- removed closing div from here as part of ICRD-194554 BY A-7426 -->
		
				 <div class="ic-button-container" id="_paginationLink">
			           <logic:present name="systemStock" >
			             <bean:define id="pageObj1" name="systemStock"  />
			     		<common:paginationTag
			     		  linkStyleClass="iCargoLink"
			   		  pageURL="javascript:submitList('lastPageNum','displayPage')"
			   		  name="pageObj1"
			   		  display="pages"
					  
					  disabledLinkStyleClass="iCargoLink"
			   		  lastPageNum="<%=lstPgNo%>"/>
			          </logic:present>
			       	  </div>
				</div><!-- added closing div here as part of ICRD-194554 BY A-7426 -->	
				<div id="_listedDynamicQuery"><bean:write name="form" property="listedDynamicQuery"/></div>
				
<div class="ic-row">
                  
					
                 <div class="tableContainer" id="div1" style="height:550px;">
					 <table class="fixed-header-table">
						<thead >
						<tr class="iCargoTableHeadingLeft">
						<td width="5%"><input type="checkbox" name="checkSysStockAll" value="checkbox" onclick="updateHeaderCheckBox(this.form,this.form.checkSysStockAll,this.form.selectedSysStock)" tabindex="35" /></td>
						<td><common:message key="uld.defaults.messaging.uldno" scope="request"/></td>						
						<td><common:message key="uld.defaults.messaging.status" scope="request"/></td>
						<td><common:message key="uld.defaults.messaging.latestscmsent" scope="request"/></td>
						</tr>
					</thead>
					<% int rowID = 0;%>
					<tbody>
					<%int count=0;%>
						<logic:present name="systemStock" >
							<bean:define id="ULDVO" name="systemStock"  />
								<logic:iterate id="uldVO" name="ULDVO" indexId="sysstockindex" type="com.ibsplc.icargo.business.uld.defaults.vo.ULDVO">
									
										<tr>

												<ihtml:hidden property="scmStatusFlags"  value="<%=uldVO.getScmStatusFlag()%>"/>
												<ihtml:hidden property="facilityType"  value=""/>	
												<ihtml:hidden property="locations"  value=""/>	
												<td  class="iCargoTableDataTd ic-center"><input type="checkbox" name="selectedSysStock" value="<%=String.valueOf(sysstockindex)%>" onclick="toggleTableHeaderCheckbox('selectedSysStock',this.form.elements.checkSysStockAll)"/>
												</td>
												<td class="iCargoTableDataTd" >
													<logic:present name="uldVO" property="scmStatusFlag">
														<bean:define id="statusValue" name="uldVO" property="scmStatusFlag" />
														<logic:equal name="statusValue" value="S">
														<ihtml:text property="extrauld" indexId="sysstockindex" styleId="extrauld" readonly="true" styleClass="iCargoEditableTextFieldRowColor1" maxlength="12" value="<%=uldVO.getUldNumber()%>"/>
														</logic:equal>
													<logic:equal name="statusValue" value="F">
											<ihtml:text property="extrauld" indexId="sysstockindex" styleId="extrauld" styleClass="iCargoEditableTextFieldRowColor1" maxlength="12" value="<%=uldVO.getUldNumber()%>"/>
										</logic:equal>
								<logic:equal name="statusValue" value="M">
									<ihtml:text property="extrauld" readonly="true" indexId="sysstockindex" styleId="extrauld"  styleClass="iCargoEditableTextFieldRowColor1" maxlength="12" value="<%=uldVO.getUldNumber()%>"/>
								</logic:equal>
						</logic:present>
						</td>
						<td class="iCargoTableTd">
							<logic:present name="uldVO" property="scmStatusFlag">
								<bean:define id="statusValue" name="uldVO" property="scmStatusFlag" />
								<logic:equal name="statusValue" value="S">
									<common:message key="uld.defaults.generatescm.sysstock" />
									<ihtml:hidden property="operationFlag"  value=""/>
								</logic:equal>
								<!--<logic:equal name="statusValue" value="F">
									<common:message key="uld.defaults.generatescm.found" />
									<ihtml:hidden property="operationFlag"  value="I"/>
								</logic:equal>-->

									<logic:equal name="statusValue" value="M">
										<common:message key="uld.defaults.generatescm.missing" />
										<ihtml:hidden property="operationFlag"  value="U"/>
									</logic:equal>
									<logic:equal name="statusValue" value="F">
										<common:message key="uld.defaults.generatescm.sighted" />
										<ihtml:hidden property="operationFlag"  value="F"/>
									</logic:equal>
							</logic:present>
						</td>
								<td class="iCargoTableDataTd" >
													<logic:present name="uldVO" property="stockCheckDate">
														<bean:define id="stockCheckDate" name="uldVO" property="stockCheckDate" />
														<logic:equal name="statusValue" value="S">
														<ihtml:hidden property="stockCheckDate" styleClass="iCargoEditableTextFieldRowColor1" maxlength="12"/>
														<%
			  								String sendscm ="";
			  								if(uldVO.getStockCheckDate() != null) {
			  								sendscm = TimeConvertor.toStringFormat(
			  											uldVO.getStockCheckDate().toCalendar(),"dd-MMM-yyyy HH:mm:ss");
			  								}
														%>
			  							<%=sendscm%>							
														</logic:equal>
													<logic:equal name="statusValue" value="F">
											<ihtml:hidden property="stockCheckDate"  styleClass="iCargoEditableTextFieldRowColor1" maxlength="12"/>
										<%
											String sendscm ="";
			  								if(uldVO.getStockCheckDate() != null) {
			  								sendscm = TimeConvertor.toStringFormat(
			  											uldVO.getStockCheckDate().toCalendar(),"dd-MMM-yyyy HH:mm:ss");
			  								}
			  							%>
			  							<%=sendscm%>	
										</logic:equal>
								<logic:equal name="statusValue" value="M">
									<ihtml:hidden property="stockCheckDate" styleClass="iCargoEditableTextFieldRowColor1" maxlength="12"/>
									<%
											String sendscm ="";
			  								if(uldVO.getStockCheckDate() != null) {
			  								sendscm = TimeConvertor.toStringFormat(
			  											uldVO.getStockCheckDate().toCalendar(),"dd-MMM-yyyy HH:mm:ss");
			  								}
			  							%>
			  							<%=sendscm%>	
								</logic:equal>
						</logic:present>
						</td>
						</tr>
						<%count++;%>
						
						</logic:iterate>

						</logic:present>						
					</tbody>
					</table>
                    </div>	

					</div>
									
				</ihtml:form>	
	<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>	