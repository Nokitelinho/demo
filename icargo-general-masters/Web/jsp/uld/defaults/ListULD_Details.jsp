<%--
********************************************************************
* Project	 		: iCargo
* Module Code & Name  : Uld
* File Name			: ListULD_Details.jsp
* Date				: 31-Jul-2008
* Author(s)			: A-3353
********************************************************************
--%>

<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ListULDForm" %>
<%@ page import="com.ibsplc.icargo.business.uld.defaults.vo.ULDListVO"%>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ include file="/jsp/includes/tlds.jsp" %>

<bean:define id="form"
		 name="listULDForm"  type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ListULDForm"
		 toScope="page" />

<business:sessionBean
		id="oneTimeValues"
		moduleName="uld.defaults"
		screenID="uld.defaults.listuld"
		method="get"
		attribute="oneTimeValues" />

<business:sessionBean
		id="LIST_DISPLAYVOS"
		moduleName="uld.defaults"
		screenID="uld.defaults.listuld"
		method="get"
		attribute="listDisplayPage" />

<business:sessionBean
		id="LIST_FILTERVO"
		moduleName="uld.defaults"
		screenID="uld.defaults.listuld"
		method="get"
		attribute="listFilterVO" />

	<ihtml:hidden property="statusFlag"/>



                       <div class="tableContainer uldlisting-tbl"  id="div1" style=" height:460px"><!-- Modified by A-7794 for ICRD-229126-->
				  <table  class="fixed-header-table" id="listuldtable" style="width:112%;"><!-- Modified by A-7359 for ICRD-226774/Modified width by A-7359 for ICRD-259293-->
					<thead>
					<tr class="iCargoTableHeadingLeft">
								<logic:notEqual name="form" property="actiontype" value="exportexcel">
					          <td width="2%"  class="iCargoTableHeader"><input id="allCheck" type="checkbox" name="allCheck" value="checkbox" onclick="updateHeaderCheckBox(this.form,this.form.allCheck,this.form.selectedRows)"/></td>
							  </logic:notEqual>
                              <td data-ic-csid="td0" width="6%" ><common:message key="uld.defaults.listuld.uldno" /><span></span></td>
					          <td data-ic-csid="td1" width="5%" ><common:message key="uld.defaults.listuld.uldgroup" /><span></span></td>
					          <td data-ic-csid="td2" width="7%" ><common:message key="uld.defaults.listuld.manufacturer" /><span></span></td>
							  <td data-ic-csid="td3" width="6%" ><common:message key="uld.defaults.listUld.lbl.loanedto" /><span></span></td>
					          <td data-ic-csid="td4" width="5%" ><common:message key="uld.defaults.listUld.lbl.borrowedfrom" /><span></span></td>
							  <td data-ic-csid="td5" width="5%" ><common:message key="uld.defaults.listUld.lbl.remdaystoreturn" /><span></span></td>
					          <td data-ic-csid="td6" width="7%" ><common:message key="uld.defaults.listuld.damagestatus" /><span></span></td>
					          <td data-ic-csid="td7" width="6%" ><common:message key="uld.defaults.listuld.overallstatus" /><span></span></td> 
					          <td data-ic-csid="td8" width="5%" ><common:message key="uld.defaults.listuld.currentairport" /><span></span></td>
					          <td data-ic-csid="td9" width="6%" ><common:message key="uld.defaults.listuld.facilityType" /><span></span></td>
					          <td data-ic-csid="td10" width="3%" ><common:message key="uld.defaults.listuld.loc" /><span></span></td>
					          <td data-ic-csid="td12" width="7%" ><common:message key="uld.defaults.listuld.lastmovt" /><span></span></td>
							  <td data-ic-csid="td13" width="4%" ><common:message key="uld.defaults.listuld.dayselapsed" /><span></span></td>
							  <td data-ic-csid="td14" width="5%" ><common:message key="uld.defaults.listUld.lbl.uldnature" /><span></span></td>
							  <td data-ic-csid="td15" width="5%" ><common:message key="uld.defaults.listUld.lbl.occupancy" /><span></span></td>
							  <td data-ic-csid="td16" width="5%" ><common:message key="uld.defaults.listUld.lbl.intransitStatus" /><span></span></td>
							  <td data-ic-csid="td17" width="8%" ><common:message key="uld.defaults.listUld.lbl.flightinfo" /><span></span></td>
							  <td data-ic-csid="td18" width="5%" ><common:message key="uld.defaults.listUld.lbl.contents" /><span></span></td>
							  <td data-ic-csid="td19" width="4%" ><common:message key="uld.defaults.listUld.lbl.on/Off" /><span></span></td>
					</tr>

                    </thead>
				  <tbody>
				  	    <logic:present name="LIST_DISPLAYVOS">
				  	    <logic:iterate id="uldListVO" name="LIST_DISPLAYVOS"  type="ULDListVO" indexId="index">
				  	   


                          <tr>
						  <logic:notEqual name="form" property="actiontype" value="exportexcel">
                            <td class="iCargoTableDataTd" style="text-align:center;">
                              <input type="checkbox" name="selectedRows" value="<%=index%>" onclick="toggleTableHeaderCheckbox('selectedRows',this.form.masterCheckbox)"/>
                            </td>
							</logic:notEqual>

                            <logic:present name="uldListVO" property="transitStatus">
								<input type="hidden" name="transitStatus" value="<%=uldListVO.getTransitStatus()%>"/>
                            </logic:present>

                           <td id="td0" class="iCargoTableDataTd">
                           <logic:present name="uldListVO" property="uldNumber">
                           		<bean:write name="uldListVO" property="uldNumber"/>
                           		<input type="hidden" name="uldNumbers" value="<%=uldListVO.getUldNumber()%>"/>
                           </logic:present>
                           </td>
                           <td id="td1" class="iCargoTableDataTd">
                            <logic:present name="uldListVO" property="uldGroupCode">
						        <bean:write name="uldListVO" property="uldGroupCode"/>
                           </logic:present>
                           </td>
                           <%--<td class="iCargoTableDataTd">
							<logic:present name="uldListVO" property="uldTypeCode">
							<bean:write name="uldListVO" property="uldTypeCode"/>
							</logic:present>
                           </td>--%>
                           <td id="td2" class="iCargoTableDataTd">
						   <logic:present name="uldListVO" property="manufacturer">
								<bean:write name="uldListVO" property="manufacturer"/>
						   </logic:present>
						   </td>
						   <td id="td3" class="iCargoTableDataTd">
						   <logic:present name="uldListVO" property="partyLoaned">
								<bean:write name="uldListVO" property="partyLoaned"/>
								<input type="hidden" name="loanedToParty" value="<%=uldListVO.getPartyLoaned()%>"/>
						   </logic:present>
						   <logic:notPresent name="uldListVO" property="partyLoaned">
								<input type="hidden" name="loanedToParty" value=""/>
						   </logic:notPresent>
                           </td>
                           <td id="td4" class="iCargoTableDataTd">
						   <logic:present name="uldListVO" property="partyBorrowed">
								<bean:write name="uldListVO" property="partyBorrowed"/>
						   </logic:present>
						   </td>

                           <td id="td5"  class="iCargoTableDataTd">
			  			 <% int days = 0;%>
			  			  <logic:present name="uldListVO" property="remainingDayToReturn">
			  			    <bean:define id="remainingDays" name="uldListVO" property="remainingDayToReturn" toScope="page"/>
							<%days = Integer.parseInt((String)remainingDays);%>
							 <%if(days>0){%>
			  			   <div style="color:green" align="centre"> <bean:write  name="uldListVO" property="remainingDayToReturn"/></div>
							<%}else if(days<0){%>
							 <div style="color:red" align="centre"> <bean:write  name="uldListVO" property="remainingDayToReturn"/></div>
							 <%}else{%>
							   <div style="color:red" align="centre">
							  <bean:write  name="uldListVO" property="remainingDayToReturn"/>
							  </div>
							  <%}%>
			  			  </logic:present>
							</td>
                           <td id="td6"  class="iCargoTableDataTd">
						   <logic:present name="uldListVO" property="damageStatus">
							<logic:present name="oneTimeValues">
								<logic:iterate id="oneTimeValue" name="oneTimeValues">
								<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
									<logic:equal name="parameterCode" value="uld.defaults.damageStatus">
									<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
										<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
											<logic:present name="parameterValue">
											<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
												<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
												<logic:equal name="uldListVO" property="damageStatus" value="<%=(String)fieldValue%>">
												<%=(String)fieldDescription%>
												</logic:equal>
											</logic:present>
										</logic:iterate>
									</logic:equal>
								</logic:iterate>
						  	 </logic:present>
						   </logic:present>
						   </td>
						   <td id="td7" class="iCargoTableDataTd">
						   <logic:present name="uldListVO" property="overallStatus">
						   <input type="hidden" name="hiddenOverAllStatus" value="<%=uldListVO.getOverallStatus()%>"/>
								 <logic:present name="oneTimeValues">
								<logic:iterate id="oneTimeValue" name="oneTimeValues">
								<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
									<logic:equal name="parameterCode" value="uld.defaults.operationalStatus">
									<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
										<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
											<logic:present name="parameterValue">
											<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
												<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
												<logic:equal name="uldListVO" property="overallStatus" value="<%=(String)fieldValue%>">
													<%=(String)fieldDescription%>
												</logic:equal>
											</logic:present>
										</logic:iterate>
									</logic:equal>
								</logic:iterate>
						  	</logic:present>
						   </logic:present>
                           </td>
                         <td id="td8" class="iCargoTableDataTd">
						   <logic:present name="uldListVO" property="currentStation">
								<bean:write name="uldListVO" property="currentStation"/>
						   </logic:present>
						   <input type="hidden" name="currentStationHidden" value="<%=uldListVO.getCurrentStation()%>"/>
							<input type="hidden" name="operatingAirlineHidden" value="<%=uldListVO.getOperatingAirline()%>"/>

						   </td>
                            <%--<td class="iCargoTableDataTd">
							   <logic:present name="uldListVO" property="location">
									<bean:write name="uldListVO" property="location"/>
							   </logic:present>
								<%if(uldListVO.getLocation()!=null){%>
							   <input type="hidden" name="locationHidden" value="<%=uldListVO.getLocation()%>"/>
							   <%}else{%>
								<input type="hidden" name="locationHidden" value=""/>
							  <%}%>
                           </td>--%>
                           <td id="td9" width="7%" class="iCargoTableDataTd">
			   	   <logic:present name="uldListVO" property="facilityType">
			   		<!-- <bean:write name="uldListVO" property="facilityType"/>  -->
			   		 <input type="hidden" name="hiddenFacility" value="<%=uldListVO.getFacilityType()%>"/>
			   		 <logic:equal name="uldListVO" property="facilityType" value="NIL">
			   		 <bean:write name="uldListVO" property="facilityType"/>
			   		 </logic:equal>
			   		 <logic:notEqual name="uldListVO" property="facilityType" value="NIL">
			   		<logic:present name="oneTimeValues">
													<logic:iterate id="oneTimeValue" name="oneTimeValues">
													<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
													<%
													System.out.println("the ParameterCode"+parameterCode);
													%>
														<logic:equal name="parameterCode" value="uld.defaults.facilitytypes">
														<%
														System.out.println("from equal in pressent");
														%>
														<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
															<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																<logic:present name="parameterValue">
																<%
																System.out.println("from equal in pressent1");
																%>
																
																<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
																	<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
																	<logic:equal name="uldListVO" property="facilityType" value="<%=(String)fieldValue%>">
																	<%
																	System.out.println("from equal in pressent from equal");
																	%>
																	<%=(String)fieldDescription%>
																	</logic:equal>
																</logic:present>
															</logic:iterate>
														</logic:equal>
													</logic:iterate>
						  	 	</logic:present>
						 <logic:notPresent name="oneTimeValues">
						 </logic:notPresent>
					</logic:notEqual>
			   	 </logic:present>
			   	 <logic:notPresent name="uldListVO" property="facilityType" >
 					<input type="hidden" name="hiddenFacility" value=""/>
						 </logic:notPresent>
			  </td>
                           <%--<td class="iCargoTableDataTd">
						   <logic:present name="uldListVO" property="currentStation">
								<bean:write name="uldListVO" property="currentStation"/>
						   </logic:present>
						   <input type="hidden" name="currentStationHidden" value="<%=uldListVO.getCurrentStation()%>"/>
						    <input type="hidden" name="operatingAirlineHidden" value="<%=uldListVO.getOperatingAirline()%>"/>

						   </td>--%>
			  <td id="td10" class="iCargoTableDataTd">
							   <logic:present name="uldListVO" property="location">
									<bean:write name="uldListVO" property="location"/>
							   </logic:present>
								<%if(uldListVO.getLocation()!=null){%>
							   <input type="hidden" name="locationHidden" value="<%=uldListVO.getLocation()%>"/>
							   <%}else{%>
								<input type="hidden" name="locationHidden" value=""/>
							  <%}%>
                           </td>
						   <%--<td class="iCargoTableDataTd">
						   <logic:present name="uldListVO" property="ownerStation">
								<bean:write name="uldListVO" property="ownerStation"/>
						   </logic:present>
                           </td>--%>
                           <td id="td11" width="10%" class="iCargoTableDataTd">

							<center>
							<logic:present name="uldListVO" property="lastMovementDate">

							<%
								String movtDate ="";
								if(uldListVO.getLastMovementDate() != null) {
								movtDate = TimeConvertor.toStringFormat(
											uldListVO.getLastMovementDate().toCalendar(),"dd-MMM-yyyy HH:mm:ss");
								}
							%>
							<%=movtDate%>

							</logic:present>
					      </center>
                           </td>

				   <td id="td12" class="iCargoTableDataTd">
						   <logic:present name="uldListVO" property="daysElapsed">
								<bean:write name="uldListVO" property="daysElapsed"/>
						   </logic:present>
                           </td>


                           <td id="td13" class="iCargoTableDataTd">
						   <logic:present name="uldListVO" property="uldNature">
						   		<logic:present name="oneTimeValues">
								<logic:iterate id="oneTimeValue" name="oneTimeValues">
								<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
									<logic:equal name="parameterCode" value="uld.defaults.uldnature">
									<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
										<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
											<logic:present name="parameterValue">
											<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
												<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
												<logic:equal name="uldListVO" property="uldNature" value="<%=(String)fieldValue%>">
												<%=(String)fieldDescription%>
												</logic:equal>
											</logic:present>
										</logic:iterate>
									</logic:equal>
								</logic:iterate>
						  	 	</logic:present>

						   </logic:present>
                           </td>
                           <td id="td14" class="iCargoTableDataTd">
                           <center>
			          <logic:present name="uldListVO" property="occupied">
			   		      <!--  <bean:write name="uldListVO" property="occupied"/> -->
			   		      <bean:define id="occupied" name="uldListVO" property="occupied" />
			   		     <input type="hidden" name="hiddenOccupancy" value="<%=uldListVO.getOccupied()%>"/>
					      		<logic:equal name="occupied" value="Y">
					      			    <img src="<%= request.getContextPath()%>/images/icon_on.gif"   width="18" height="18" />	 
					      			<!--  -->						      
					      		</logic:equal>
					      		<logic:notEqual name="occupied" value="Y">
					      			 <img src="<%= request.getContextPath()%>/images/icon_off.gif"   width="18" height="18" />	 
						    	</logic:notEqual>
			          </logic:present>
			       </center> 
                           </td>
                            <td id="td15" class="iCargoTableDataTd">
								<center>
							<logic:present name="uldListVO" property="transitStatus">
			   		      <!--  <bean:write name="uldListVO" property="transitStatus"/> -->
			   		      <bean:define id="transitStatus" name="uldListVO" property="transitStatus" />
			   		     <input type="hidden" name="hiddenTransitStatus" value="<%=uldListVO.getTransitStatus()%>"/>
					      		<logic:equal name="transitStatus" value="Y">
					      			    <img src="<%= request.getContextPath()%>/images/icon_on.gif"   width="18" height="18" />	 
					      			<!--  -->						      
					      		</logic:equal>
					      		<logic:notEqual name="transitStatus" value="Y">
					      			 <img src="<%= request.getContextPath()%>/images/icon_off.gif"   width="18" height="18" />	 
						    	</logic:notEqual>
						</logic:present>
						</center> 
						    </td>
                            <td id="td16" class="iCargoTableDataTd">
						 	  <logic:present name="uldListVO" property="flightInfo">
								<bean:write name="uldListVO" property="flightInfo"/>
						 	  </logic:present>
                           </td>
                           <td id="td17" class="iCargoTableDataTd">
			   	<logic:present name="uldListVO" property="content">
			   	      <!--  <bean:write name="uldListVO" property="content"/> -->
			   	      
			   	     <logic:present name="oneTimeValues">
				      	<logic:iterate id="oneTimeValue" name="oneTimeValues">
				      		<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
				      			<logic:equal name="parameterCode" value="uld.defaults.contentcodes">
				      				<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
				      					<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
				      						<logic:present name="parameterValue">
				      							<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
				      							<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
				      								<logic:equal name="uldListVO" property="content" value="<%=(String)fieldValue%>">
				      									<%=(String)fieldDescription%>
				      								</logic:equal>
				      						</logic:present>
				      					</logic:iterate>
				      			</logic:equal>
				      		</logic:iterate>
					</logic:present>
			   	</logic:present>
                           </td>
                           <td id="td18" class="iCargoTableDataTd">
                           
				  <logic:present name="uldListVO" property="offAirport">
					  <bean:define id="offAirport" name="uldListVO" property="offAirport" />
					  <ihtml:text property="checkOffAirport"  value="<%=(String)offAirport%>" style="width:0px;visibility:hidden"/>
						<logic:equal name="offAirport" value="Y">
						     <img src="<%= request.getContextPath()%>/images/icon_on.gif"   width="18" height="18" />	 
						    	 
						   	
						    </logic:equal>
						    <logic:notEqual name="offAirport" value="Y">
						    	 <img src="<%= request.getContextPath()%>/images/icon_off.gif"   width="18" height="18" />	 
						    </logic:notEqual>
				  </logic:present>
				  
			 
			 </td>

                  			</tr>

                        </logic:iterate>
						</logic:present>
	            </tbody>
              </table>
            </div>
         
<jsp:include page="/jsp/includes/columnchooser/columnchooser.jsp"/>

