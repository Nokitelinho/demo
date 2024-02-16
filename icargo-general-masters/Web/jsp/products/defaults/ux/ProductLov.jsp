<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  INV - Inventory Control
* File Name				:  ProductLov.jsp
* Date					:  27-July-2005
* Author(s)				:  Akhila S

*************************************************************************/
 --%>

<%@ page language="java" %>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ include file="/jsp/includes/ux/tlds.jsp" %>
<%@ page import="java.util.Collection" %>
<%@ page import = "com.ibsplc.icargo.framework.util.time.LocalDate" %>
<%@ page import = "com.ibsplc.icargo.business.products.defaults.vo.ProductLovVO" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.ux.ProductLovForm" %>
<%@ page info="lite" %>	

		
		
	
<html>
<head> 
		
			
	
<bean:define id="form"
	name="productLovUXForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.ux.ProductLovForm"
	toScope="page" />
<title><common:message bundle="<%=form.getBundle() %>" key="products.defaults.title" scope="request"/></title>
<meta name="decorator" content="popuppanelux">
<common:include type="script" src="/js/products/defaults/ux/ProductLov_Script.jsp" />
</head>

<body >
	
	
	
	
<div id="divmain">		
	<ihtml:form action="products.defaults.ux.screenloadProductLov.do" styleClass="ic-main-form" style="height:100%;">
		<bean:define id="form" name="productLovUXForm" 
			type="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.ux.ProductLovForm" toScope="page" />
		<html:hidden property="lastPageNumber"/>
		<html:hidden property="displayPage"/>
		<html:hidden property="formNumber"/>
		<html:hidden property="productObject" />
		<html:hidden property="productCodeField" />
		<html:hidden property="sourceScreen" />
		<html:hidden property="activeProducts" />
		<html:hidden property="bookingDate" />
		<ihtml:hidden property="priorityObject" />
		<ihtml:hidden property="transportModeObject" />
		<ihtml:hidden property="rowIndex" />
		<ihtml:hidden property="invokeFunction"/> 
		<ihtml:hidden property="multiselect"/>
		<ihtml:hidden property="lovTxtFieldName"/>
		<ihtml:hidden property="lovDescriptionTxtFieldName"/>
		<ihtml:hidden property="selectedValues"  />
		<ihtml:hidden property="lastPageNum" />
		<bean:define name="form" property="productObject" id="productObject" />
		<bean:define name="form" property="priorityObject" id="priorityObject" />
		<bean:define name="form" property="transportModeObject" id="transportModeObject" />
		<bean:define name="form" property="multiselect" id="strMultiselect" />
		<bean:define name="form" property="lovTxtFieldName" id="strLovTxtFieldName" />
		<bean:define name="form" property="lovDescriptionTxtFieldName" id="strLovDescriptionTxtFieldName" />
		<bean:define name="form" property="rowIndex" id="arrayIndex" />
		<bean:define name="form" property="formNumber" id="formNumber" />
		<bean:define name="form" property="lastPageNum" id="lastPageNum"/>
		<logic:present name="productLovUXForm" property="pageProductLov">
			<bean:define id="pageProductLov" name="productLovUXForm"  property="pageProductLov" toScope="page" />
		</logic:present> 
     <div title="Product" class="lov-popup-content ui-dialog-content ui-widget-content" id="select_product" style="width: 100%">
 
 
<div class="lov-filter lov-filter-oneline">            
<div class="row">
                <div class="col-6">
					<label class="form-control-label">
						<common:message key="products.defaults.ProductName" />
					</label>
                    <div class="form-group">
					<ihtml:text tabindex="1" property="productName" id="productName" styleClass="form-control" maxlength="12" componentID="TXT_PRODUCTS_DEFAULTS_PRODUCTLOV_PRDNAME"/>
                    </div>
                </div>
                <div class="col-6">
				<label class="form-control-label">
						<common:message key="products.defaults.StartDate" />
					</label>
                    <div class="form-group">
						<ibusiness:litecalendar  property="startDate" id="startDate" 
											componentID="TXT_PRODUCTS_DEFAULTS_PRODUCTLOV_STARTDATE" 
											maxlength="11" value="<%=form.getStartDate()%>"/>
					
                    </div>
			</div>
				
				
                <div class="col-6">
				<label class="form-control-label">
						<common:message key="products.defaults.EndDate" />
					</label>
                    <div class="form-group">
                        <ibusiness:litecalendar property="endDate" id="endDate" 
											componentID="TXT_PRODUCTS_DEFAULTS_PRODUCTLOV_ENDDATE" 
											maxlength="11" value="<%=form.getEndDate()%>"/>
					 </div>
                </div>
                <div class="col">
                    <div class="mar-t-md">
					<ihtml:nbutton  property="btnList" accesskey="L" styleClass="btn btn-primary" componentID="BTN_PRODUCTS_DEFAULTS_PRODUCTLOV_LIST">
							<common:message bundle="<%=form.getBundle() %>" key="products.defaults.list" scope="request"/>
					</ihtml:nbutton>
					<ihtml:nbutton property="btnclear" accesskey="C" styleClass="btn btn-default" componentID="BTN_PRODUCTS_DEFAULTS_PRODUCTLOV_CLEAR">
							<common:message bundle="<%=form.getBundle() %>" key="products.defaults.Clear" scope="request"/>
					</ihtml:nbutton>
                    </div>
                </div>
            </div>
        </div>
		<div class="lov-list">
            <div class="card">
                <div class="card-header card-header-action">
				<logic:present name="form" property="pageProductLov" >
				<bean:define id="lovList" name="form" property="pageProductLov" toScope="page"/>
				<logic:present name="lovList">
				<bean:define id="multiselect" name="form" property="multiselect" />
				<bean:define id="lastPageNum" name="form" property="lastPageNum" />
					<bean:define name="form" property="defaultPageSize" id="defaultPageSize"/>
					<bean:define name="form" property="totalRecordsCount" id="totalRecordsCount"/>	
				<div class="mega-pagination">
                                            <common:enhancedPaginationTag 
							pageURL="javascript:preserveNavigationValues('lastPageNum','displayPage')"
                                            styleClass="ic-pagination-lg" 
							name="lovList" id="lovList"
							lastPageNum="<%=(String)lastPageNum%>"
							renderLengthMenu="true" lengthMenuName="defaultPageSize" 
							defaultSelectOption="<%=(String)defaultPageSize%>"  
							lengthMenuHandler="showEntriesReloading" pageNumberFormAttribute="displayPage"
							/>
                    </div>

				</logic:present>
				</logic:present>
		</div>
				<bean:define name="form" property="multiselect" id="strMultiselect" />
		<bean:define name="form" property="lovTxtFieldName" id="strLovTxtFieldName" />
		<bean:define name="form" property="lovDescriptionTxtFieldName" id="strLovDescriptionTxtFieldName" />
		<bean:define name="form" property="rowIndex" id="arrayIndex" />
			<bean:define name="form" property="formNumber" id="formNumber" />
                <div class="card-body">
					<div class="dataTableContainer tablePanel" id="dataTableContainer">
					<table class="table table-x-md mb-0 w-100" id="productDataTable">
                                        <thead>
                            <tr>
                                <th width="35"></th>
                                <th><common:message bundle="<%=form.getBundle()%>" key="products.defaults.ProductName" scope="request"/></th>
                                <th><common:message bundle="<%=form.getBundle() %>" key="products.defaults.StartDate" scope="request"/></th>
                                <th><common:message bundle="<%=form.getBundle() %>" key="products.defaults.EndDate" scope="request"/></th>
                                            </tr>
                                        </thead>
                                        <tbody>
							 <% String priorVal="";
                                                String transportMode="";%>
                                                <logic:present name="pageProductLov">
                                                    <logic:iterate id="lovVo" name="pageProductLov" indexId="nIndex">
                                <bean:define id="productName" name="lovVo" property="productCode" />
                                                        <bean:define id="startDate" name="lovVo" property="startDate" />
                                                        <bean:define id="endDate" name="lovVo" property="endDate" />
                                                        <bean:define id="name" name="lovVo" property="productName" />
                                                        <logic:present name="lovVo" property="productPriority">
                                                            <bean:define id="priorityValues" name="lovVo" property="productPriority" />
                                                            <logic:iterate id="priorityVal" name="priorityValues">
                                                                <%priorVal = priorVal+(String)priorityVal+"-";%>
                                                            </logic:iterate>
                                                        </logic:present>
                                                        <logic:present  name="lovVo" property="productTransportMode">
                                                            <bean:define id="transportModeValues" name="lovVo" property="productTransportMode" />
                                                            <bean:define id="transModeValues" name="lovVo" property="productTransportMode" />
                                                            <logic:iterate id="tranVal" name="transModeValues">
                                                                <%transportMode = transportMode+(String)tranVal+"-";%>
                                                            </logic:iterate>
                                                        </logic:present>
                                    <%  String fromDateString = TimeConvertor.toStringFormat(((LocalDate)startDate).toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);
                                                            String endDateString = TimeConvertor.toStringFormat(((LocalDate)endDate).toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);
                                                        %>

                                                            <logic:notEqual name="productLovUXForm" property="multiselect" value="Y">
                                                                <tr
    ondblclick="setProductLovOnDblClick('<%=productObject%>','<%=((ProductLovVO)lovVo).getProductName()%>','<%=formNumber%>')">
                                                            </logic:notEqual>
                                                            <logic:equal name="productLovUXForm" property="multiselect" value="Y">
                                                            <tr>
                                                            </logic:equal> 
                                <td><logic:notEqual name="productLovUXForm" property="multiselect" value="Y">				  
                                                                    <input type="checkbox" name="productChecked" 
                                                                        value="<%=((ProductLovVO)lovVo).getProductName()%>" 
                                                                        onclick="lovUtils.singleSelect({checkBoxVal:'<%=((ProductLovVO)lovVo).getProductName()%>'});"/>
                                                                </logic:notEqual>
                                                                <logic:equal name="productLovUXForm" property="multiselect" value="Y">
                                                                    <input type="checkbox" name="productChecked" 
                                                                        value="<%=((ProductLovVO)lovVo).getProductName()%>"  />
                                    </logic:equal>   </td>
                                <td><bean:write name="lovVo" property="productName"/></td>
                                <td> <%=fromDateString%></td>
                                <td><%=endDateString%></td>
                                                        </tr>
                                                    </logic:iterate>
                                                </logic:present>
                                                <logic:notPresent name="pageProductLov">
                                                    &nbsp;
                                                </logic:notPresent>
                                            </tbody>
                                        </table>
                                    </div>
				 </div>
				  </div>
				   </div>
                <logic:equal name="form" property="multiselect" value="Y">
				<div class="lov-selected" >
						<div class="col-auto">
							<div id="selectiondiv" name="Products"></div>
                        </div>
                                    </div>
                                </logic:equal>
               
				
				<div class="lov-btn-row">
					
					
					<ihtml:nbutton property="btok" id="btok" styleClass="btn btn-primary"  accesskey="K" componentID="TXT_PRODUCTS_DEFAULTS_PRODUCTLOV_OK">
								<common:message  key="products.defaults.ok" /> 
							</ihtml:nbutton> 
					
						<ihtml:nbutton property="btnclose" styleClass="btn btn-default" accesskey="O" componentID="BTN_PRODUCTS_DEFAULTS_PRODUCTLOV_CLOSE">
							<common:message bundle="<%=form.getBundle() %>" key="products.defaults.close" scope="request"/>
                        </ihtml:nbutton>
                </div>
            
		</div>
	</ihtml:form>
</div>		
			
	
	</body>

</html>
