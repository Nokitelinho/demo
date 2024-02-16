<%@ page language="java" %>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.ArrayList"%>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainSubProductForm" %>
<%@ page import="com.ibsplc.icargo.business.products.defaults.vo.ProductEventVO" %>

<bean:define id="form"
name="MaintainSubProductForm"
type="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainSubProductForm"
toScope="page" />


<div class="iCargoContent" style="overflow:auto;">
<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>
<ihtml:form action="products.defaults.maintainsubproductscreenload.do">


		<table class="fixed-header-table" width="100%">
	<thead>
		<tr class="iCargoTableHeadingLeft">
			  <td width="4%" class="iCargoTableHeadingCenter ic-center">
			  <input type="checkbox" name="checkAllMilestone"
			  onclick="updateHeaderCheckBox(this.form,this,this.form.mileStoneRowId)"
			  title='<common:message  bundle="MaintainSubProduct" key="milestone"
					scope="request"/>'/></td>
			  <td width="10%"> <common:message  key="milestone" scope="request"/></td>
			  <td width="7%"> <common:message  key="type" scope="request"/></td>
			  <td width="10%"> <common:message  key="minTime" scope="request"/></td>
			  <td width="10%"> <common:message  key="maxTime" scope="request"/></td>
			  <td width="6%"> <common:message  key="external" scope="request"/></td>
			  <td width="6%"> <common:message  key="internal" scope="request"/></td>
			  <td width="6%"> <common:message  key="Transit" scope="request"/></td>
			  <td width="10%"> <common:message  key="alert" scope="request"/></td>
			  <td width="9%"> <common:message  key="chaser" scope="request"/></td>
			  <td width="10%"> <common:message  key="chaserFrequency" scope="request"/></td>
			  <td width="12%"> <common:message  key="maxNo" scope="request"/></td>
			</tr>

	  </thead>
	<tbody>
    	<business:sessionBean id="milestoneVOsFromSession" moduleName="product.defaults"
			screenID="products.defaults.maintainsubproducts"
			method="get"
			attribute="productEventVOs" />


		<logic:present name="milestoneVOsFromSession" >
		  <bean:define id="eventList" name="milestoneVOsFromSession" />
			<% String operFlag = null;
			 int index = 0;
			 String external = "false";
			 String internal = "false";
			 ArrayList<ProductEventVO> list = (ArrayList<ProductEventVO>)eventList; %>

		<logic:iterate id="eventVO" name="eventList" indexId="nIndex">
			<% operFlag = ((ProductEventVO)eventVO).getOperationFlag();
				if(!"D".equals(operFlag)){
			%>

	  <tr>
		 <html:hidden property="milestoneOpFlag" value="<%=(String)operFlag%>" />
		  <html:hidden property="isRowModified" value="0" />
			<html:hidden property="isNewRowModified" value="0" />
			<td class="iCargoTableDataTd ic-center">
				<ihtml:checkbox property="mileStoneRowId"
					value="<%=new Integer(index).toString()%>"
					componentID="CHK_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_MILESTONE"
					onclick="toggleTableHeaderCheckbox('mileStoneRowId',this.form.checkAllMilestone)"/>
			</td>

			<td class="iCargoTableDataTd"><%=((ProductEventVO)eventVO).getEventCode()%></td>
			<html:hidden property="milestone" value="<%=((ProductEventVO)eventVO).getEventCode()%>" />
			<td class="iCargoTableDataTd"><%=((ProductEventVO)eventVO).getEventType()%></td>
			<td class="iCargoTableDataTd">

			<ibusiness:releasetimer property="minTime"
			componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_MINTIME"
			id="TXT_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_MINTIME"
			type="asTimeComponent" indexId="nIndex"
			value="<%=((ProductEventVO)eventVO).getMinimumTimeStr()%>"
			/>
			</td>
			<td class="iCargoTableDataTd">
			<ibusiness:releasetimer property="maxTime"
			componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_MAXTIME"
			id="TXT_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_MAXTIME"
			type="asTimeComponent" indexId="nIndex"
			value="<%=((ProductEventVO)eventVO).getMaximumTimeStr()%>"
			/>

			</td>
			<td  class="iCargoTableTd ic-center">

			<bean:define name="eventVO" property="internal" id="isInternal" toScope="page"/>
			<bean:define name="eventVO" property="transit" id="isTransit" toScope="page"/>

			<logic:equal name="eventVO" property="external" value="true">
			<bean:define name="eventVO" property="external" id="isExternal"
				toScope="page"/>
				<input type="checkbox" name="isExternal"
				value="<%=isExternal.toString()%>"
				checked="checked" title='<common:message  bundle="MaintainSubProduct" key="products.defaults.milestone.external"
				scope="request"/>'/>
			</logic:equal>
			<logic:notEqual name="eventVO" property="external" value="true">
			<bean:define name="eventVO" property="external" id="isExternal" toScope="page"/>
				<input type="checkbox" name="isExternal"
				value="<%=isExternal.toString()%>"
				title='<common:message  bundle="MaintainSubProduct" key="products.defaults.milestone.external"
				scope="request"/>'/>
			</logic:notEqual>

			</td>

			<td  class="iCargoTableTd ic-center">

			<logic:equal name="eventVO" property="internal" value="true">
				<input type="checkbox" name="isInternal" value="<%=isInternal.toString()%>"
					checked="checked"
					title='<common:message  bundle="MaintainSubProduct"
						key="products.defaults.milestone.internal"
						scope="request"/>'/>
			</logic:equal>
			<logic:notEqual name="eventVO" property="internal" value="true">
				<input type="checkbox" name="isInternal"
				value="<%=isInternal.toString()%>"
					title='<common:message  bundle="MaintainSubProduct"
						key="products.defaults.milestone.internal"
						scope="request"/>'/>
			</logic:notEqual>
			</td>
			<td  class="iCargoTableTd ic-center">
				<logic:equal name="eventVO" property="transit" value="true">
					<input type="checkbox" name="isTransit" value="<%=isTransit.toString()%>"
						checked="checked"
						title='<common:message  bundle="MaintainSubProduct" key="products.defaults.milestone.transit"
						scope="request"/>'  />
				</logic:equal>
				<logic:notEqual name="eventVO" property="transit" value="true">
					<input type="checkbox" name="isTransit"
					value="<%=isTransit.toString()%>"
					title='<common:message  bundle="MaintainSubProduct" key="products.defaults.milestone.transit"
					scope="request"/>' />
				</logic:notEqual>
	                </td>

			<td class="iCargoTableDataTd">

			<ihtml:text property="alertTime"
			value="<%=new Integer(new Double(((ProductEventVO)eventVO).getAlertTime()).intValue()).toString()%>"
				componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_ALERTTIME"/></td>
			<td class="iCargoTableDataTd">

			<ihtml:text property="chaserTime"
			value="<%=new Integer(new Double(((ProductEventVO)eventVO).getChaserTime()).intValue()).toString()%>"
				componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_CHASER"/></td>

				<%
					double freq = ((ProductEventVO)eventVO).getChaserFrequency();
					int frequency = (int)freq;
				%>


			<td class="iCargoTableDataTd">

			<ihtml:text property="chaserFrequency"
			value="<%=(new Integer(frequency)).toString()%>"
						componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_FREQUENCY" /></td>
			<td class="iCargoTableDataTd">
			<ihtml:text property="maxNoOfChasers"
			value="<%=new Integer(((ProductEventVO)eventVO).getMaxNoOfChasers()).toString()%>"
						componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_MAXNOOFCHASERS" /></td>
			  </tr>

		<%
		}else{

		%>


				<bean:define name="eventVO" property="external" id="ext" />
				<bean:define name="eventVO" property="internal" id="inter" />
				<bean:define name="eventVO" property="transit" id="trans" />
				<html:hidden property="milestoneOpFlag" value="<%=(String)operFlag%>" />
				<html:hidden property="isRowModified" value="0" />
				<html:hidden property="isNewRowModified" value="0" />
				<html:hidden property="mileStoneRowId" value="<%=new Integer(index).toString()%>" />
				<html:hidden property="eventCode" value="<%=((ProductEventVO)eventVO).getEventCode()%>" />
				<html:hidden property="eventType" value="<%=((ProductEventVO)eventVO).getEventType()%>" />
				<html:hidden property="minTime" value="<%=new Double(((ProductEventVO)eventVO).getMinimumTime()).toString()%>" />
				<html:hidden property="maxTime" value="<%=new Double(((ProductEventVO)eventVO).getMaximumTime()).toString()%>" />
				<html:hidden property="isExternal" value="<%=ext.toString()%>" />
				<html:hidden property="isInternal" value="<%=inter.toString()%>" />
				<html:hidden property="isTransit" value="<%=trans.toString()%>" />
				<html:hidden property="alertTime" value="<%=new Integer(new Double(((ProductEventVO)eventVO).getAlertTime()).intValue()).toString()%>" />
				<html:hidden property="chaserTime" value="<%=new Integer(new Double(((ProductEventVO)eventVO).getChaserTime()).intValue()).toString()%>" />
				<html:hidden property="chaserFrequency" value="<%=new Double(((ProductEventVO)eventVO).getChaserFrequency()).toString()%>" />
				<html:hidden property="maxNoOfChasers" value="<%=new Integer(((ProductEventVO)eventVO).getMaxNoOfChasers()).toString()%>" />

			<%}
			index++;

			%>
			</logic:iterate>
				</logic:present>

			</tbody>
			</table>
			</ihtml:form>
			<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>

