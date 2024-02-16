<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  IN - Inventory Control
* File Name				:  MaintainProduct_MileStone_Tab.jsp
* Date					:  15-July-2001
* Author(s)				:  Amritha S

*************************************************************************/
 --%>




<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.business.products.defaults.vo.ProductEventVO" %>

<bean:define id="form"
	name="MaintainProductForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainProductForm"
	toScope="page" />
	
	<div class="ic-row">
		<h4>Time Specification</h4>
	</div>
	<div class="ic-row">
		<div class="ic-button-container paddR5">
			<a href="#" class="iCargoLink" onclick="addDataFromLOV1('products.defaults.beforeOpenMileStoneLov.do',
				'products.defaults.screenloadMileStoneLov.do',
				'products.defaults.selectMilestoneNew.do','MAINTAIN_PRODUCT_SESSION','div10')" >
				<common:message  key="products.defaults.add" scope="request"/>
			</a>
			 |
			<a href="#" class="iCargoLink" onclick="deleteTableRow1('products.defaults.deleteMilestoneNew.do','mileStoneRowId','div10')">
				<common:message  key="products.defaults.delete" scope="request"/>
			</a>
		</div>
	</div>
	<div class="ic-row">
		<div class="tableContainer" id="div10" style="height:400px">
			<table class="fixed-header-table" id="milestoneTable">
				<thead>
					<tr class="iCargoTableHeadingLeft">
						<td width="2%" class="iCargoTableHeadingCenter">
							<input type="checkbox" name="checkAllMilestone" 
								onclick="updateHeaderCheckBox(this.form,this,this.form.mileStoneRowId)" />
						</td>
						<td width="8%" class="iCargoTableHeadingCenter"><common:message  key="products.defaults.Milestone" scope="request"/></td>
						<td width="8%" class="iCargoTableHeadingCenter"><common:message  key="products.defaults.Type" scope="request"/></td>
						<td width="9%" class="iCargoTableHeadingCenter"><common:message  key="products.defaults.MinTime" scope="request"/></td>
						<td width="9%" class="iCargoTableHeadingCenter"><common:message  key="products.defaults.MaxTime" scope="request"/></td>
						<td width="9%" class="iCargoTableHeadingCenter"><common:message  key="products.defaults.External" scope="request"/></td>
						<td width="9%" class="iCargoTableHeadingCenter"><common:message  key="products.defaults.Internal" scope="request"/></td>
						<td width="9%" class="iCargoTableHeadingCenter"><common:message  key="products.defaults.Transit" scope="request"/></td>
						<td width="9%" class="iCargoTableHeadingCenter"><common:message  key="products.defaults.Alert" scope="request"/></td>
						<td width="9%" class="iCargoTableHeadingCenter"><common:message  key="products.defaults.Chaser" scope="request"/></td>
						<td width="9%" class="iCargoTableHeadingCenter"><common:message  key="products.defaults.ChaserFrequency" scope="request"/></td>
						<td width="10%" class="iCargoTableHeadingCenter"><common:message  key="products.defaults.maxNoChaser" scope="request"/></td>
					</tr>
				</thead>
				<tbody>
					<business:sessionBean id="milestoneVOsFromSession" moduleName="product.defaults"
						screenID="products.defaults.maintainproduct"
						method="get"
						attribute="productEventVOs" />
					<logic:present name="milestoneVOsFromSession" >
						<bean:define id="eventList" name="milestoneVOsFromSession" />
						<% String operFlag = null;
							 int index = 0;
						%>
						<logic:iterate name="eventList" id="eventVO" indexId="rowCount9">
							<logic:present name="eventVO" property="operationFlag">
								<bean:define name="eventVO" property="operationFlag" id="operatFlg" />
									<%operFlag=(String)operatFlg;%>
							</logic:present>
							<logic:present name="eventVO" property="operationFlag" >
							 	<logic:notEqual name="eventVO" property="operationFlag" value="D">
									<bean:define name="eventVO"  property="operationFlag"  id="evntopflag" />
									    <tr>
										    <html:hidden property="milestoneOpFlag" value="<%=(String)evntopflag%>" />
										    <html:hidden property="isRowModified" value="0" />
											<html:hidden property="isNewRowModified" value="0" />
											<td  class="iCargoTableTd">
												<html:checkbox property="mileStoneRowId"
													value="<%=new Integer(index).toString()%>" onclick="toggleTableHeaderCheckbox('mileStoneRowId',this.form.checkAllMilestone)" />
											</td>
											<td  class="iCargoTableTd"><bean:write name="eventVO" property="eventCode" /></td>
											<html:hidden property="milestone" value="<%=((ProductEventVO)eventVO).getEventCode()%>" />
											<td  class="iCargoTableTd"><bean:write name="eventVO" property="eventType" /></td>
											<td  class="iCargoTableTd">
												<ibusiness:releasetimer property="minTime"
													componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINPRD_MINTIME"
													id="TXT_PRODUCTS_DEFAULTS_MAINTAINPRD_MINTIME"
													styleId="minTime"
													type="asTimeComponent" indexId="rowCount9"
													value="<%=((ProductEventVO)eventVO).getMinimumTimeStr()%>" style="text-align:right"/>
											</td>
											<td  class="iCargoTableTd">
												<ibusiness:releasetimer property="maxTime"
													componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINPRD_MAXTIME"
													id="TXT_PRODUCTS_DEFAULTS_MAINTAINPRD_MAXTIME"
													styleId="maxTime"   style="text-align:right"
													type="asTimeComponent" indexId="rowCount9"
													value="<%=((ProductEventVO)eventVO).getMaximumTimeStr()%>"/>
											</td>
											<td  class="iCargoTableTd ic-center">
												<bean:define name="eventVO" property="external" id="isExternal" />
												<bean:define name="eventVO" property="internal" id="isInternal" />
												<bean:define name="eventVO" property="transit" id="isTransit" />
													<logic:equal name="eventVO" property="external" value="true">
														<input type="checkbox" name="isExternal"
															value="<%=isExternal.toString()%>"
															checked="checked"
															title='<common:message  bundle="MaintainProduct" key="products.defaults.milestone.external"
															scope="request"/>'/>
													</logic:equal>
													<logic:notEqual name="eventVO" property="external" value="true">
														<input type="checkbox" name="isExternal"
															value="<%=isExternal.toString()%>"
															title='<common:message  bundle="MaintainProduct" key="products.defaults.milestone.external"
															scope="request"/>'/>
													</logic:notEqual>
											</td>
											<td  class="iCargoTableTd ic-center">
												<logic:equal name="eventVO" property="internal" value="true">
													<input type="checkbox" name="isInternal" value="<%=isInternal.toString()%>"
														checked="checked"
														title='<common:message  bundle="MaintainProduct" key="products.defaults.milestone.internal"
														scope="request"/>' />
												</logic:equal>
												<logic:notEqual name="eventVO" property="internal" value="true">
													<input type="checkbox" name="isInternal" value="<%=isInternal.toString()%>"
														title='<common:message  bundle="MaintainProduct"
														key="products.defaults.milestone.internal"
														scope="request"/>'/>
												</logic:notEqual>
											</td>
											<td  class="iCargoTableTd ic-center">
												<logic:equal name="eventVO" property="transit" value="true">
													<input type="checkbox" name="isTransit" value="<%=isTransit.toString()%>"
														checked="checked"
														title='<common:message  bundle="MaintainProduct" key="products.defaults.milestone.transit"
														scope="request"/>' />
												</logic:equal>
												<logic:notEqual name="eventVO" property="transit" value="true">
													<input type="checkbox" name="isTransit" value="<%=isTransit.toString()%>"
														title='<common:message  bundle="MaintainProduct" key="products.defaults.milestone.transit"
														scope="request"/>'/>
												</logic:notEqual>
											</td>
											<td  class="iCargoTableTd">
											<ihtml:text property="alertTime"
											style="background :'<%=color%>';text-align:right"
											styleId="alertTime"
											indexId="rowCount9" 
											value="<%=new Integer(new Double(((ProductEventVO)eventVO).getAlertTime()).intValue()).toString()%>"
											maxlength="5" componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINPRD_ALERTTIME"
											onblur="validateFields(this,-1,'Alert option',2,true,true)"
											/></td>
											<td  class="iCargoTableTd">
											<ihtml:text property="chaserTime"
											styleId="chaserTime"  
											indexId="rowCount9"
											style="background :'<%=color%>';text-align:right"
											value="<%=new Integer(new Double(((ProductEventVO)eventVO).getChaserTime()).intValue()).toString()%>"
											componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINPRD_CHASER"
											maxlength="5" onblur="validateFields(this,-1,'First send time',2,true,true)"
											/></td>

											<%
												double freq = ((ProductEventVO)eventVO).getChaserFrequency();
												int frequency = (int)freq;
											%>
											<td  class="iCargoTableTd">
											<ihtml:text property="chaserFrequency"
											styleId="chaserFrequency"  style="text-align:right;background :'<%=color%>'"
											indexId="rowCount9"
											value="<%=(new Integer(frequency)).toString()%>" 
												maxlength="5" componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINPRD_FREQUENCY"
												onblur="validateFields(this,-1,'Chaser Frequency',3,true,true)"/></td>

											<td  class="iCargoTableTd">
											<ihtml:text property="maxNoOfChasers"  
											styleId="maxNoOfChasers"
											
											style="background :'<%=color%>';text-align:right"
											value="<%=new Integer(((ProductEventVO)eventVO).getMaxNoOfChasers()).toString()%>"
												maxlength="5"  indexId="rowCount9"
												componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINPRD_MAXNOOFCHASERS"
												onblur="validateFields(this,-1,'Maximun Number of Chasers',3,true,true)"/></td>
								
											  </tr>

										</logic:notEqual>
									</logic:present>



							<logic:notPresent name="eventVO" property="operationFlag" >

							  <tr>
								 <html:hidden property="milestoneOpFlag" value="<%=(String)operFlag%>" />
								  <html:hidden property="isRowModified" value="0" />
									<html:hidden property="isNewRowModified" value="0" />
									<td  class="iCargoTableTd" style="text-align:center">
										<html:checkbox property="mileStoneRowId"
											value="<%=new Integer(index).toString()%>" onclick="toggleTableHeaderCheckbox('mileStoneRowId',this.form.checkAllMilestone)"/>
									</td>

									<td  class="iCargoTableTd"><bean:write name="eventVO" property="eventCode" /></td>
									<html:hidden property="milestone" value="<%=((ProductEventVO)eventVO).getEventCode()%>" />
									<td  class="iCargoTableTd"><bean:write name="eventVO" property="eventType" /></td>
									<td  class="iCargoTableTd">

									<ibusiness:releasetimer property="minTime" style="text-align:right"
									styleId="minTime"
									indexId="rowCount9"
									 componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINPRD_MINTIME"
									 id="TXT_PRODUCTS_DEFAULTS_MAINTAINPRD_MINTIME"
									 type="asTimeComponent" 
									 value="<%=((ProductEventVO)eventVO).getMinimumTimeStr()%>"
									 />
									 </td>
									<td  class="iCargoTableTd">
									<ibusiness:releasetimer property="maxTime" style="text-align:right"
									styleId="maxTime"
									
									componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINPRD_MAXTIME"
									id="TXT_PRODUCTS_DEFAULTS_MAINTAINPRD_MAXTIME"
									type="asTimeComponent" indexId="rowCount9"
									value="<%=((ProductEventVO)eventVO).getMaximumTimeStr()%>"
									/>
									</td>
									<td  class="iCargoTableTd">
											<bean:define name="eventVO" property="external" id="isExt" />
											<bean:define name="eventVO" property="internal" id="isInt" />
											<bean:define name="eventVO" property="transit" id="isTrans" />

											<logic:equal name="eventVO" property="external" value="true">
												<input type="checkbox" name="isExternal" value="<%=isExt.toString()%>"
												checked="checked"
												title='<common:message  bundle="MaintainProduct" key="products.defaults.milestone.external"
												scope="request"/>'/>
											</logic:equal>
											<logic:notEqual name="eventVO" property="external" value="true">
												<input type="checkbox" name="isExternal"
												value="<%=isExt.toString()%>"
												title='<common:message  bundle="MaintainProduct" key="products.defaults.milestone.external"
													scope="request"/>'/>
											</logic:notEqual>

									</td>

									<td  class="iCargoTableTd ic-center">

											<logic:equal name="eventVO" property="internal" value="true">
												<input type="checkbox" name="isInternal" value="<%=isInt.toString()%>"
													checked="checked"
													title='<common:message  bundle="MaintainProduct" key="products.defaults.milestone.internal"
													scope="request"/>'/>
											</logic:equal>
											<logic:notEqual name="eventVO" property="internal" value="true">
												<input type="checkbox" name="isInternal" value="<%=isInt.toString()%>"
													title='<common:message  bundle="MaintainProduct" key="products.defaults.milestone.internal"
													scope="request"/>'/>
											</logic:notEqual>
									</td>
									<td  class="iCargoTableTd ic-center">

										<logic:equal name="eventVO" property="transit" value="true">
										 <input type="checkbox" name="isTransit" value="<%=isTrans.toString()%>"
												checked="checked"
												title='<common:message  bundle="MaintainProduct" key="products.defaults.milestone.transit"
														scope="request"/>' />
										</logic:equal>
										<logic:notEqual name="eventVO" property="transit" value="true">
											<input type="checkbox" name="isTransit" value="<%=isTrans.toString()%>"
												title='<common:message  bundle="MaintainProduct" key="products.defaults.milestone.transit"
														scope="request"/>'/>
										</logic:notEqual>
									</td>

									<td  class="iCargoTableTd"><ihtml:text property="alertTime"
									styleId="alertTime" 
									indexId="rowCount9"
									style="background :'<%=color%>';text-align:right"
									value="<%=new Integer(new Double(((ProductEventVO)eventVO).getAlertTime()).intValue()).toString()%>"
									maxlength="5" componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINPRD_ALERTTIME" onblur="validateFields(this,-1,'Alert option',2,true,true)"/></td>
									<td  class="iCargoTableTd">
									<ihtml:text property="chaserTime" 
									styleId="chaserTime"
									indexId="rowCount9"
									style="background :'<%=color%>';text-align:right"
									value="<%=new Integer(new Double(((ProductEventVO)eventVO).getChaserTime()).intValue()).toString()%>"
									componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINPRD_CHASER"
									maxlength="5" onblur="validateFields(this,-1,'First send time',2,true,true)"/></td>
											<%
												double freq = ((ProductEventVO)eventVO).getChaserFrequency();
												int frequency = (int)freq;
											%>
									<td  class="iCargoTableTd">
									<ihtml:text property="chaserFrequency" 
                                    
									style="background :'<%=color%>';text-align:right" styleId="chaserFrequency" indexId="rowCount9"
									value="<%=(new Integer(frequency)).toString()%>"
										maxlength="5"  componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINPRD_FREQUENCY"
										onblur="validateFields(this,-1,'Chaser Frequency',3,true,true)"
										/></td>
									<td  class="iCargoTableTd">
									<ihtml:text property="maxNoOfChasers" 
									
									style="background :'<%=color%>';text-align:right" styleId="maxNoOfChasers" indexId="rowCount9"
									value="<%=new Integer(((ProductEventVO)eventVO).getMaxNoOfChasers()).toString()%>"
										maxlength="5" componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINPRD_MAXNOOFCHASERS"
										onblur="validateFields(this,-1,'Maximun Number of Chasers',3,true,true)"
												/></td>
									  </tr>

									</logic:notPresent>


									<logic:equal name="eventVO" property="operationFlag" value="D">

											<bean:define name="eventVO" property="external" id="ext" />
											<bean:define name="eventVO" property="internal" id="inter" />
											<bean:define name="eventVO" property="transit" id="trans" />
											<html:hidden property="milestoneOpFlag" value="<%=(String)operFlag%>" />
											<html:hidden property="isRowModified" value="0" />
											<html:hidden property="isNewRowModified" value="0" />
											<html:hidden property="mileStoneRowId" value="<%=new Integer(index).toString()%>" />

											<html:hidden property="isExternal" value="<%=ext.toString()%>" />
											<html:hidden property="isInternal" value="<%=inter.toString()%>" />
											<html:hidden property="isTransit" value="<%=trans.toString()%>" />
											<html:hidden property="eventCode" value="<%=((ProductEventVO)eventVO).getEventCode()%>" />
											<html:hidden property="eventType" value="<%=((ProductEventVO)eventVO).getEventType()%>" />
											<html:hidden property="minTime" value="<%=new Double(((ProductEventVO)eventVO).getMinimumTime()).toString()%>" />
											<html:hidden property="maxTime" value="<%=new Double(((ProductEventVO)eventVO).getMaximumTime()).toString()%>" />
											<html:hidden property="alertTime" value="<%=new Integer(new Double(((ProductEventVO)eventVO).getAlertTime()).intValue()).toString()%>" />
											<html:hidden property="chaserTime" value="<%=new Integer(new Double(((ProductEventVO)eventVO).getChaserTime()).intValue()).toString()%>" />
											<html:hidden property="chaserFrequency" value="<%=new Double(((ProductEventVO)eventVO).getChaserFrequency()).toString()%>" />
											<html:hidden property="maxNoOfChasers" value="<%=new Integer(((ProductEventVO)eventVO).getMaxNoOfChasers()).toString()%>" />

										</logic:equal>
										<%
										index++;

										%>
						</logic:iterate>
					</logic:present>
				</tbody>
			</table>
		</div>
	</div>

