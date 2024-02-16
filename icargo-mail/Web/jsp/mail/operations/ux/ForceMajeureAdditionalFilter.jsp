<%@ page language="java" %>
<%@ page import = "com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.ForceMajeureRequestForm"%>
<%@ page import="java.util.Collection"%>
<%@ include file="/jsp/includes/ux/tlds.jsp" %>
<%@ page info="lite" %>

<bean:define id="form"
   name="ForceMajeureRequestForm"
   type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.ForceMajeureRequestForm"
   toScope="page" />
   
<a href="#" id="setting-show"  data-role="popover" class="open-filter" onclick="" >
<i class="icon ico-filter"></i>
</a>

<div id="reqpopFilter" class="show-filter" >
   <div class ="pad-sm container-up" style="">
     <div class="row">
         <div class="col-12">
            <div class="form-group">
               <label class="form-control-label">Airport Code</label>
               <div class="input-group">
                  <ihtml:text tabindex="1" inputGroup="true" buttonIconClass="icon ico-expand" labelStyleClass="form-control-label" property="airportFilter" id="airportFilter" styleClass="form-control" maxlength="4"   componentID="CMP_Mail_Operations_ForceMajeure_AirpotFilter"/>
               </div>
            </div>
         </div>
         <div class="col-12">
            <div class="form-group">
               <ibusiness:flightnumber id="flightNumber" tabindex="2" componentID="CMP_Mail_Operations_ForceMajeure_FlightNumberFilter"
                  carrierCodeProperty="carrierFilter" flightCodeProperty="flightNumberFilter" 
                  carrierCodeMaxlength="3" flightCodeMaxlength="5" onblur="formatFlt(this)"/>
            </div>
         </div>
      </div>
      <div class="row">
         <div class="col-12">
            <div class="form-group">
               <label class="form-control-label">
                  <common:message key="mail.operations.ux.ForceMajeure.lbl.flightdate" scope="request" />
               </label>
               <div class="input-group">
                  <ibusiness:litecalendar   labelStyleClass="form-control-label" tabindex="4"  id="flightDateFilter" property="flightDateFilter"
                     componentID="CMP_Mail_Operations_ForceMajeure_FlightDateFilter" />
               </div>
            </div>
         </div>
         <div class="col-12">
            <div class="form-group">
               <label class="form-control-label"> Consignment No.</label>
               <div class="input-group">
                  <ihtml:text  name="form" property="consignmentNo"  tabindex="8"   styleClass="form-control" componentID="CMP_Mail_Operations_ForceMajeure_ConsignmentFilter"/>
               </div>
            </div>
         </div>
      </div>
      <div class="row">
         <div class="col-12">
			<div class="form-group">
            <label class="form-control-label">Mailbag ID</label>
            <div class="input-group">
               <ihtml:text  name="form" property="mailbagId"  tabindex="8"   styleClass="form-control" componentID="CMP_Mail_Operations_ForceMajeure_MailbagIdFilter"/>
            </div>
			</div>
         </div>
     </div>
   </div>
   <div class="btn-row pad-2sm">
      <button class="btn btn-primary" onclick="onApplyFilter()">Apply</button>
      <button class="btn btn-default" onclick="onClearFilter()">Clear</button>
      <button class="btn btn-default" onclick="IC.util.widget.closeDataSelector()">Cancel</button>
   </div>
</div>
