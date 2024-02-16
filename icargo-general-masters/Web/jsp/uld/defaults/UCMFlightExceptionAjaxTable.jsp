<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>

<%@ page import = "com.ibsplc.icargo.business.uld.defaults.vo.UCMExceptionFlightVO" %>

<bean:define id="form"
			 name="UCMFlightExceptionListForm"  type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.UCMFlightExceptionListForm"
		 toScope="page" />
<business:sessionBean
	id="ucmExceptionFlightVOs"
	moduleName="uld.defaults"
	screenID="uld.defaults.ucmflightexceptionlist"
	method="get"
	attribute="ucmExceptionFlightVOs"/>



<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>



<ihtml:form action="/uld.defaults.messaging.screenloaducmflightexceptionlist.do" method="post">
	<ihtml:hidden property="duplicateFlightStatus" />
	<ihtml:hidden property="actionStatus" />
	<ihtml:hidden property="listStatus" />
	<div class="tableContainer"  id="div1"  style="height:550px; ">
						  <table width="100%" class="fixed-header-table"  id="ucmflightexceptionlist">

	<thead>
	  <tr class="iCargoTableHeadingLeft">
		<td width="5%">
		<div align="center">
		<input type="checkbox" name="masterCheckbox" value="checkbox" onclick="updateHeaderCheckBox(this.form,this.form.masterCheckbox,this.form.selectedRows)" />
		</div>
		</td>
		
		<td  class="iCargoTableDataTd"  >
			<common:message key="uld.defaults.ucmflightexceptionlist.lbl.flightnumber" /><span class="iCargoMandatoryFieldIcon">*</span>
		</td>
		<td  class="iCargoTableDataTd" >
			<common:message key="uld.defaults.ucmflightexceptionlist.lbl.flightdate" /><span class="iCargoMandatoryFieldIcon">*</span>
		</td>

	  </tr>
	</thead>
	<tbody>
		<logic:present name="ucmExceptionFlightVOs">
								<logic:iterate id="ucmExceptionFlightVO" name="ucmExceptionFlightVOs" indexId="index" type="UCMExceptionFlightVO">
								<logic:present name="ucmExceptionFlightVO" property="opeartionalFlag">
									<bean:define id="opFlag" name="ucmExceptionFlightVO" property="opeartionalFlag"/>
								</logic:present>
								<logic:notPresent name="ucmExceptionFlightVO" property="opeartionalFlag">
									<bean:define id="opFlag"  value="NA"/>
							   </logic:notPresent>
							     <%boolean toDisableScanFields = false;%>
								<logic:notEqual name="ucmExceptionFlightVO" property="opeartionalFlag" value="D">
								  <% toDisableScanFields = true;%>
									<common:rowColorTag index="index">
										<tr bgcolor="<%=color%>">
										 <logic:present name="ucmExceptionFlightVO" property="opeartionalFlag">
											<ihtml:hidden property="operationFlag" value="<%=ucmExceptionFlightVO.getOpeartionalFlag()%>"/>
											
										</logic:present>
										<logic:notPresent name="ucmExceptionFlightVO" property="opeartionalFlag">
												<ihtml:hidden property="opeartionalFlag" value="NA"/>
										</logic:notPresent>
											<td  class="iCargoTableDataTd ic-center" >
												<input type="checkbox" name="selectedRows" value="<%=index%>" onclick="toggleTableHeaderCheckbox('selectedRows',this.form.masterCheckbox)" />
											</td>

									
									
									<td  class="iCargoTableDataTd" >
							<logic:present name="ucmExceptionFlightVO" property="flightNumber">
										<logic:present name="ucmExceptionFlightVO" property="carrierCode">
										<logic:match name="opFlag" value="I"> 
										
																		
										
							
								<ibusiness:flightnumber id="flightNo" componentID="TXT_ULD_DEFAULTS_UCMFLIGHTEXCEPTIONLIST_FLIGHTNO" carrierCodeProperty="flightCarrier" flightCodeProperty="flightNumber" carriercodevalue="<%=(String)ucmExceptionFlightVO.getCarrierCode()%>" flightcodevalue="<%=(String)ucmExceptionFlightVO.getFlightNumber()%>" indexId="index" />
							
										</logic:match>
										
										<logic:notMatch name="opFlag" value="I"> 
										
								<ibusiness:flightnumber id="flightNo" componentID="TXT_ULD_DEFAULTS_UCMFLIGHTEXCEPTIONLIST_FLIGHTNO" carrierCodeProperty="flightCarrier" flightCodeProperty="flightNumber" carriercodevalue="<%=(String)ucmExceptionFlightVO.getCarrierCode()%>" flightcodevalue="<%=(String)ucmExceptionFlightVO.getFlightNumber()%>" indexId="index" />
										</logic:notMatch>
									
										</logic:present>
							</logic:present>
										<logic:notPresent name="ucmExceptionFlightVO" property="carrierCode">
							<logic:notPresent name="ucmExceptionFlightVO" property="flightNumber">
							<logic:match name="opFlag" value="I">
																			
								<ibusiness:flightnumber id="flightNo" componentID="TXT_ULD_DEFAULTS_UCMFLIGHTEXCEPTIONLIST_FLIGHTNO" carrierCodeProperty="flightCarrier" flightCodeProperty="flightNumber" carriercodevalue="" flightcodevalue=""  indexId="index"/>
							</logic:match>
							<logic:notMatch name="opFlag" value="I"> 
									
								<ibusiness:flightnumber id="flightNo" componentID="TXT_ULD_DEFAULTS_UCMFLIGHTEXCEPTIONLIST_FLIGHTNO" carrierCodeProperty="flightCarrier" flightCodeProperty="flightNumber" carriercodevalue="<%=(String)ucmExceptionFlightVO.getCarrierCode()%>" flightcodevalue="<%=(String)ucmExceptionFlightVO.getFlightNumber()%>" indexId="index"  />
							</logic:notMatch>
						
								</logic:notPresent>
							</logic:notPresent>
						</td>

						<td  class="iCargoTableDataTd" >
								<logic:present name="ucmExceptionFlightVO" property="flightDate" > 
															
							<logic:match name="opFlag" value="I"> 
							
								<ibusiness:calendar
									  id="flightDate"
									  type="image" indexId="index"
									  property="flightDate"
									  componentID="CAL_ULD_DEFAULTS_UCMFLIGHTEXCEPTIONLIST_FLIGHTDATE"
									  value="<%=ucmExceptionFlightVO.getFlightDate().toDisplayDateOnlyFormat()%>"  />
							</logic:match>
							<logic:notMatch name="opFlag" value="I"> 
							
								<ibusiness:calendar
									  id="flightDate"
									  type="image" indexId="index"
									  property="flightDate"
									  componentID="CAL_ULD_DEFAULTS_UCMFLIGHTEXCEPTIONLIST_FLIGHTDATE"
									  value="<%=ucmExceptionFlightVO.getFlightDate().toDisplayDateOnlyFormat()%>" readonly="<%=toDisableScanFields%>"/>
									  
							</logic:notMatch>
							</logic:present>
						<logic:notPresent name="ucmExceptionFlightVO" property="flightDate">
							<logic:match name="opFlag" value="I">
																				
										 
									<ibusiness:calendar
									  id="flightDate"
									  type="image" indexId="index"
									  property="flightDate" 
									  componentID="CAL_ULD_DEFAULTS_UCMFLIGHTEXCEPTIONLIST_FLIGHTDATE"  value=" " />
												
							</logic:match>
							<logic:notMatch name="opFlag" value="I"> 
									<ibusiness:calendar
									  id="flightDate"
									  type="image" indexId="index"
									  property="flightDate"
									  componentID="CAL_ULD_DEFAULTS_UCMFLIGHTEXCEPTIONLIST_FLIGHTDATE"
									  value="<%=ucmExceptionFlightVO.getFlightDate().toDisplayDateOnlyFormat()%>" readonly="<%=toDisableScanFields%>"/>
							</logic:notMatch>
						
							</logic:notPresent>
									

						</td>

					</tr>
				</common:rowColorTag>
					<logic:present name="ucmExceptionFlightVO" property="flightSequenceNumber">
						<input type="hidden" name="flightSequenceNumber"  value="<%=String.valueOf(ucmExceptionFlightVO.getFlightSequenceNumber())%>" maxlength="12" />
					</logic:present>
					<logic:notPresent name="ucmExceptionFlightVO" property="flightSequenceNumber">
						<input type="hidden" name="flightSequenceNumber" value="" maxlength="12" />
				    </logic:notPresent>

			</logic:notEqual>

		</logic:iterate>
	  </logic:present>
	</tbody>
  </table>
  </div>
  </ihtml:form>
<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>

