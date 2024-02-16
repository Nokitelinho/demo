import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import {reset} from 'redux-form';
import { requestValidationError } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import { openPopup } from 'icoreact/lib/ico/framework/action/openpopup';

export function toggleFilter(screenMode) {  
  return {type: 'TOGGLE_FILTER',screenMode };
}


export function listCarditEnquiry(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    const screenMode=state.filterReducer.screenMode
  //  const {displayPage,action} = args;
    const displayPage = args && args.displayPage ? args.displayPage : state.filterReducer.displayPage
    const pageSize = args && args.pageSize ? args.pageSize : state.filterReducer.pageSize;  //Added by A-8164 for ICRD-320122
    var carditFilter  = (state.form.carditFilter.values)?state.form.carditFilter.values:{}
    carditFilter.displayPage=displayPage;
    carditFilter.pageSize=pageSize;//Added by A-8164 for ICRD-320122
    if(args && args.detachAWB && args.detachAWB == 'TRUE'){
        state.form.carditFilter.values.masterDocumentNumber='';
         state.form.carditFilter.values.shipmentPrefix='';
    }
    const flightNumber=(state.form.carditFilter.values) ? state.form.carditFilter.values.flightnumber:{};
    if(!isEmpty(flightNumber)) {
      carditFilter.carrierCode = flightNumber.carrierCode;  
      carditFilter.flightNumber=flightNumber.flightNumber;
      carditFilter.flightDate=flightNumber.flightDate;
    }
    const uldNumber=(state.form.carditFilter.values) ? state.form.carditFilter.values.uldValue:{};
    if(!isEmpty(uldNumber)) {
    carditFilter.uldNumber=state.form.carditFilter.values.uldValue;
     }
    var data = {carditFilter};
    const url = 'rest/mail/operations/carditenquiry/list';
    return makeRequest({
        url,
        data: {...data}
    }).then(function (response) {
        if (args && args.mode === 'EXPORT') {
            let mailbags = response.results[0].mailbags
            let exportMailbags = { ...response.results[0].mailbags }
            let exportDataCount = mailbags.results.length;
            for (var i = 0; i < mailbags.results.length; i++) {
                if(mailbags.results[i].carrierCode || mailbags.results[i].flightNumber){
                    exportMailbags.results[i].flightNumber = mailbags.results[i].carrierCode != '' || mailbags.results[i].carrierCode!=null? mailbags.results[i].carrierCode+'-'+mailbags.results[i].flightNumber :''
                }
              
                 if(mailbags.results[i].shipmentPrefix || mailbags.results[i].masterDocumentNumber) {
                    exportMailbags.results[i].shipmentPrefix = (mailbags.results[i].shipmentPrefix || '') +'-'+ (mailbags.results[i].masterDocumentNumber || '')
                }
            }
            return exportMailbags;
        }
        const mailCount = response && response.results && response.results[0].carditFilter && response.results[0].carditFilter.mailCount;
        carditFilter ={...carditFilter,mailCount}
        data ={carditFilter}
        handleResponse(dispatch, response,args.mode,data,screenMode,displayPage,pageSize);
        return response
    })
    .catch(error => {
        return error;
    });


}


export function applyCarditFilter(values) {
    const {dispatch, getState } = values;
    const state = getState();
  //  const {displayPage,action} = args;
    const tableFilter  = (state.form.carditTableFilter.values)?state.form.carditTableFilter.values:{}
    delete tableFilter.undefined; 
    const { flightnumber, ...rest } = tableFilter;
    const carditTableFilter={...flightnumber,...rest};
    Object.keys(carditTableFilter).forEach((key) => (carditTableFilter[key] === ''||carditTableFilter[key] === null) && delete carditTableFilter[key]);
    dispatch( { type: 'LIST_FILTER',carditTableFilter});
    return Object.keys(carditTableFilter).length;

 
   
}

export const clearFilter=(values)=> {
    const {dispatch} = values;  
    dispatch({ type: 'CLEAR_FILTER'});
    dispatch(reset('carditFilter'));
      
    
}

export const onClearFlightFilter=(values)=> {
    const {dispatch} = values;  
    dispatch({ type: 'CLEAR_TABLE_FILTER'});
    dispatch(reset('carditDetailsFilter'));
      
    
}

export function updateSortVariables(data) {
    const { dispatch } = data;
    dispatch({ type: 'UPDATE_SORT_VARIABLE', data: data.args })

}
export function saveMaibagResdit(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    let mailbagData=values.args.maildata;
    let mailDetails=state.filterReducer.mailbagsdetails;
    let selectedMailbag=values.args.selectedMailbag;
   let selectedMailbagFromMailDetails= mailDetails.results.filter((value)=> {if(value.mailbagId==selectedMailbag)return value });
   let selectedResditOfSelectedMailbag=mailbagData.filter((value)=> {if(value.mailbag==selectedMailbag)return value });
  for(let i=0;i<selectedResditOfSelectedMailbag.length;i++){
      for(let j=0;j< selectedMailbagFromMailDetails[0].mailbagHistories.length;j++){
   if( selectedResditOfSelectedMailbag[i].resdit==selectedMailbagFromMailDetails[0].mailbagHistories[j].eventCode&& selectedResditOfSelectedMailbag[i].selected&&selectedResditOfSelectedMailbag[i].airportCode==selectedMailbagFromMailDetails[0].mailbagHistories[j].airportCode){
    selectedMailbagFromMailDetails[0].mailbagHistories[j].additionalInfo=true ;
   }
   if( selectedResditOfSelectedMailbag[i].resdit==selectedMailbagFromMailDetails[0].mailbagHistories[j].eventCode&& !selectedResditOfSelectedMailbag[i].selected&&selectedResditOfSelectedMailbag[i].airportCode==selectedMailbagFromMailDetails[0].mailbagHistories[j].airportCode){
    selectedMailbagFromMailDetails[0].mailbagHistories[j].additionalInfo=false ;
   }
   }
  }
  dispatch({ type: 'SAVE_SELECTED_RESDIT_MAILBAG_DATA',mailDetails})
}
export function performCarditAction(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    const screenMode=state.filterReducer.screenMode
    const displayPage=state.filterReducer.displayPage
    let indexes=[]
    let url = '';
    let data={};
    if(args.index) {
        indexes.push(args.index);
    }
    else {
        indexes=state.filterReducer.selectedMailbagIndex;
    }
    
    let selectedMailbags=[];
    let selectedMailbagHistories=[];
    for(var i=0; i<indexes.length;i++) {
        selectedMailbags.push(state.filterReducer.mailbagsdetails.results[indexes[i]]);
    }
    if(args.mode === 'SEND_RESDIT')  {
      data ={selectedMailbags}
      url = 'rest/mail/operations/carditenquiry/sendResdit';
    
    return makeRequest({
        url,
        data: { ...data }
    }).then(function (response) {
        handleResponse(dispatch, response,args.mode,data,screenMode,displayPage);
        return response
    })
    .catch(error => {
        return error;
    });
   
}
if(args.mode=="BULK_SEND_RESDIT"){
    let selectedMailbags= args.selectedMailbags;
    let selectedResdits=args.selectedResdits;
    let url = '';
    let data={};
    let selectedResditVersion=args.selectedResditVersion;
         data ={selectedMailbags,selectedResdits,selectedResditVersion}
         url = 'rest/mail/operations/carditenquiry/sendResdit';
       return makeRequest({
           url,
           data: { ...data }
         }).then(function (response) {
           handleResponse(dispatch, response,args.mode,data,screenMode,displayPage);
           return response
       })
       .catch(error => {
        return error;
    });
       }
}
export function performCarditResditSendAction(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
const messageType = "IFTSTA";
const targetAction = "mail.operations.ux.carditenquiry.sendResditOk.do";
invokeAddressPopup(dispatch, messageType, targetAction);
}
function invokeAddressPopup(dispatch, messageType, targetAction) {
   const targetActionValue = targetAction 
   var url = "msgbroker.message.ux.sendmessage.newmessage.do?openPopUpFlg=UPDATEDESPATCH" +
       "&hideMessageDetails=Y&hideMessageRemarks=Y&messageType=" + messageType +
       "&targetAction=" + targetActionValue;
   var closeButtonIds = [];
   var optionsArray = {
       url,
       dialogWidth: "1050",
       dialogHeight: "450",
       closeButtonIds: closeButtonIds,
       popupTitle: 'Message Details'
   }
   dispatch(dispatchAction(openPopup)(optionsArray));
}

function handleResponse(dispatch,response,action,data,screenMode,displayPage,pageSize) {
    
    if(!isEmpty(response.results)){
        console.log(response.results[0]);
        const {mailbags,totalPieces,totalWeight} = response.results[0];



        
       
        if (action==='LIST') {
        	 const filterdata = response.results[0].carditFilter;
             const summaryFilter = makeSummaryFilter(filterdata);

        	
            if(mailbags !=null) {
                if(displayPage > 1) {
                    dispatch( { type: 'LIST_SUCCESS_PAGINATION', mailbags,data,screenMode,displayPage,pageSize,summaryFilter });
                } else {
                  dispatch( { type: 'LIST_SUCCESS', mailbags,totalPieces,totalWeight,data,screenMode,displayPage,pageSize,summaryFilter });
                }
           
            }else {
                dispatch( { type: 'NO_DATA',data}); 
            }
        }
        if (action === 'BULK_SEND_RESDIT') {
            dispatch( { type: 'SEND_RESDIT_SUCCESS',screenMode,displayPage,pageSize});
        }
        if (action === 'SEND_RESDIT') {
            dispatch( { type: 'SEND_RESDIT_SUCCESS',screenMode,displayPage,pageSize});
        }
       
    } else {
        if(!isEmpty(response.errors)){
             dispatch( { type: 'RETAIN_VALUES'});
        }
    }
}

function makeSummaryFilter(data){
    
    let filter={};
    let popOver={};
    let count = 0;
    let popoverCount =0;
    if(data.mailbagId && data.mailbagId.length > 0){
        filter={...filter, mailbagId:data.mailbagId};
        count++;
    }
    if(data.ooe && data.ooe.length > 0){
        filter={...filter, ooe:data.ooe};
        count++;
    }
    if(data.doe && data.doe.length > 0){
        filter={...filter, doe:data.doe};
        count++;
    }
    if(data.mailCategoryCode && data.mailCategoryCode.length > 0){
        filter={...filter, mailCategoryCode:data.mailCategoryCode};
        count++;
    }
    if(data.mailSubclass && data.mailSubclass.length > 0){
        filter={...filter, mailSubclass:data.mailSubclass};
        count++;
    }
    if(data.year && data.year.length > 0){
        filter={...filter, year:data.year};
        count++;
    }
    if(data.despatchSerialNumber && data.despatchSerialNumber.length > 0){
        if(count<6){
            filter={...filter, despatchSerialNumber:data.despatchSerialNumber};
            count++;
        }else{
            popOver={...popOver, despatchSerialNumber:data.despatchSerialNumber};
            popoverCount++;
        }  
    }

        if(data.receptacleSerialNumber && data.receptacleSerialNumber.length > 0){
        if(count<6){
            filter={...filter, receptacleSerialNumber:data.receptacleSerialNumber};
            count++;
        }else{
            popOver={...popOver, receptacleSerialNumber:data.receptacleSerialNumber};
            popoverCount++;
        }  
    }

        if(data.conDocNo && data.conDocNo.length > 0){
        if(count<6){
            filter={...filter, conDocNo:data.conDocNo};
            count++;
        }else{
            popOver={...popOver, conDocNo:data.conDocNo};
            popoverCount++;
        }  
    }

        if(data.consignmentDate && data.consignmentDate.length > 0){
        if(count<6){
            filter={...filter, consignmentDate:data.consignmentDate};
            count++;
        }else{
            popOver={...popOver, consignmentDate:data.consignmentDate};
            popoverCount++;
        }  
    }

           if(data.fromDate && data.fromDate.length > 0){
        if(count<6){
            filter={...filter, fromDate:data.fromDate};
            count++;
        }else{
            popOver={...popOver, fromDate:data.fromDate};
            popoverCount++;
        }  
    }

        if(data.toDate && data.toDate.length > 0){
        if(count<6){
            filter={...filter, toDate:data.toDate};
            count++;
        }else{
            popOver={...popOver, toDate:data.toDate};
            popoverCount++;
        }  
    }

        if(data.paCode && data.paCode.length > 0){
        if(count<6){
            filter={...filter, paCode:data.paCode};
            count++;
        }else{
            popOver={...popOver, paCode:data.paCode};
            popoverCount++;
        }  
    }

    if(data.pendingResditChecked && data.pendingResditChecked.length > 0 && data.pendingResditChecked === 'true'){
        if(count<6){
            filter={...filter, pendingResditChecked:data.pendingResditChecked};
            count++;
        }else{
            popOver={...popOver, pendingResditChecked:data.pendingResditChecked};
            popoverCount++;
        }  
    }

    if (data.flightNumber && data.flightNumber.length > 0 && data.carrierCode && data.carrierCode.length > 0
        && data.flightDate && data.flightDate.length > 0) {



        let flightnumber = {};
        flightnumber.flightNumber = data.flightNumber;
        flightnumber.carrierCode = data.carrierCode;
        flightnumber.flightDate = data.flightDate;



        if (count < 6) {
            filter = { ...filter, flightnumber: flightnumber };
            count++;
        } else {
            popOver = { ...popOver, flightnumber: flightnumber };
            popoverCount++;
        }
    }

            if(data.airportCode && data.airportCode.length > 0){
        if(count<6){
            filter={...filter, airportCode:data.airportCode};
            count++;
        }else{
            popOver={...popOver, airportCode:data.airportCode};
            popoverCount++;
        }  
    }

            if(data.uldNumber && data.uldNumber.length > 0){
        if(count<6){
            filter={...filter, uldNumber:data.uldNumber};
            count++;
        }else{
            popOver={...popOver, uldNumber:data.uldNumber};
            popoverCount++;
        }  
    }

            if(data.mailOrigin && data.mailOrigin.length > 0){
        if(count<6){
            filter={...filter, mailOrigin:data.mailOrigin};
            count++;
        }else{
            popOver={...popOver, mailOrigin:data.mailOrigin};
            popoverCount++;
        }  
    }

                if(data.mailDestination && data.mailDestination.length > 0){
        if(count<6){
            filter={...filter, mailDestination:data.mailDestination};
            count++;
        }else{
            popOver={...popOver, mailDestination:data.mailDestination};
            popoverCount++;
        }  
    }

                if(data.mailStatus && data.mailStatus.length > 0){
        if(count<6){
            filter={...filter, mailStatus:data.mailStatus};
            count++;
        }else{
            popOver={...popOver, mailStatus:data.mailStatus};
            popoverCount++;
        }  
    }

                if(data.flightType && data.flightType.length > 0){
        if(count<6){
            filter={...filter, flightType:data.flightType};
            count++;
        }else{
            popOver={...popOver, flightType:data.flightType};
            popoverCount++;
        }  
    }

                if(data.reqDeliveryDate && data.reqDeliveryDate.length > 0){
        if(count<6){
            filter={...filter, reqDeliveryDate:data.reqDeliveryDate};
            count++;
        }else{
            popOver={...popOver, reqDeliveryDate:data.reqDeliveryDate};
            popoverCount++;
        }  
    }

                    if(data.reqDeliveryTime && data.reqDeliveryTime.length > 0){
        if(count<6){
            filter={...filter, reqDeliveryTime:data.reqDeliveryTime};
            //count++;
        }else{
            popOver={...popOver, reqDeliveryTime:data.reqDeliveryTime};
           // popoverCount++;
        }  
    }

    if(data.shipmentPrefix && data.shipmentPrefix.length > 0){
        if(count<6){
            filter={...filter, shipmentPrefix:data.shipmentPrefix};
            count++;
        }else{
            popOver={...popOver, shipmentPrefix:data.shipmentPrefix};
            popoverCount++;
        }  
    }

                    if(data.masterDocumentNumber && data.masterDocumentNumber.length > 0){
        if(count<6){
            filter={...filter, masterDocumentNumber:data.masterDocumentNumber};
            //count++;
        }else{
            popOver={...popOver, masterDocumentNumber:data.masterDocumentNumber};
           // popoverCount++;
        }  
    }

    
    if(data.transportServWindowDate && data.transportServWindowDate.length > 0){
        if(count<6){
            filter={...filter, transportServWindowDate:data.transportServWindowDate};
            //count++;
        }else{
            popOver={...popOver, transportServWindowDate:data.transportServWindowDate};
           // popoverCount++;
        }  
    }

    if(data.transportServWindowTime && data.transportServWindowTime.length > 0){
        if(count<6){
            filter={...filter, transportServWindowTime:data.transportServWindowTime};
            //count++;
        }else{
            popOver={...popOver, transportServWindowTime:data.transportServWindowTime};
           // popoverCount++;
        }  
    }
	  if (data.awbAttached && data.awbAttached.length > 0) {
        if (count < 6) {
            filter = { ...filter, awbAttached: data.awbAttached };
            count++;
        } else {
            popOver = { ...popOver, awbAttached: data.awbAttached };
            popoverCount++;
        }
    }


    const summaryFilter = {filter:filter, popOver:popOver, popoverCount:popoverCount};
    return summaryFilter;
}

export function performListFlightsDetails(values) {
    const { dispatch, getState } = values;
    const state = getState();
    let indexes = state.filterReducer.selectedMailbagIndex;
    var airportCode = state.commonReducer.airportCode;
    let selectedMailbags = [];
    if (checkRowselectOrNot(values)) {
        let flag = true;
        let awbNo = state.filterReducer.mailbagsdetails.results[indexes[0]].shipmentPrefix + "-" +
            state.filterReducer.mailbagsdetails.results[indexes[0]].masterDocumentNumber;
        for (var i = 0; i < indexes.length; i++) {
            if (awbNo==="null-null"||(state.filterReducer.mailbagsdetails.results[indexes[i]].shipmentPrefix === null ||
                state.filterReducer.mailbagsdetails.results[indexes[i]].masterDocumentNumber === null)) {
                dispatch(requestValidationError('Please select mail bag(s) which are having  AWBs', ''))
                return;
            } else {
                let selectedMailBag = state.filterReducer.mailbagsdetails.results[indexes[i]];
                selectedMailbags.push(selectedMailBag);
                if (awbNo !== selectedMailBag.shipmentPrefix + "-" + selectedMailBag.masterDocumentNumber) {
                    flag = false;
                }
            }
        }
        state.commonReducer.selectedMailbags = selectedMailbags;
        if (flag) {
            var url = "addons.mail.operations.listflight.defaultscreenload.do?fromScreen=mail.operations.carditenquiry&isPopup=true&airportCode=" + airportCode + '&selectedMailbags=' + selectedMailbags;
            var optionsArray = {
                url,
                dialogWidth: "1250",
                dialogHeight: "600",
                popupTitle: 'Booked Flight Details'
            }

            dispatch(dispatchAction(openPopup)(optionsArray));
            return Promise.resolve({});
        }
        else {
            dispatch(requestValidationError('Please select mail bag(s) which are having same AWBs', ''));
            return;
        }
    }
    else {
        dispatch(requestValidationError('Please select at least one row', ''));
        return;
    }

}
export function performdetachAWB(values) {
    const { args, dispatch, getState } = values;

    if (checkRowselectOrNot(values)) {

        let indexes = []
        let url = '';
        let data = {};
        const { args, dispatch, getState } = values;
        const state = getState();
        indexes = state.filterReducer.selectedMailbagIndex;
        const screenMode = state.filterReducer.screenMode
        let mailbagsVos = state.filterReducer.mailbagsdetails.results
        const displayPage = args && args.displayPage ? args.displayPage : state.filterReducer.displayPage
        const pageSize = args && args.pageSize ? args.pageSize : state.filterReducer.pageSize;  //Added by A-8164 for ICRD-320122
        const carditFilter = (state.form.carditFilter.values) ? state.form.carditFilter.values : {}
        carditFilter.displayPage = displayPage;
        carditFilter.pageSize = pageSize;//Added by A-8164 for ICRD-320122
        const flightNumber = (state.form.carditFilter.values) ? state.form.carditFilter.values.flightnumber : {};
        if (!isEmpty(flightNumber)) {
            carditFilter.carrierCode = flightNumber.carrierCode;
            carditFilter.flightNumber = flightNumber.flightNumber;
            carditFilter.flightDate = flightNumber.flightDate;
        }
        const uldNumber = (state.form.carditFilter.values) ? state.form.carditFilter.values.uldValue : {};
        if (!isEmpty(uldNumber)) {
            carditFilter.uldNumber = state.form.carditFilter.values.uldValue;
        }

        let selectedMailbags = []
        for (var i = 0; i < indexes.length; i++) {
            selectedMailbags.push(state.filterReducer.mailbagsdetails.results[indexes[i]]);
        }

        
        data = { selectedMailbags, carditFilter, mailbagsVos }

        url = 'rest/addons/mail/operations/carditenquiry/detachAWB';

        return makeRequest({
            url,
            data: { ...data }
        }).then(function (response) {
       
            return response
        })
            .catch(error => {
                return error;
            });



    }
    else {
        dispatch(requestValidationError('Please select at least one row', ''));
        return Promise.resolve("Error");
    }


}
function checkRowselectOrNot(values) {
    const { dispatch, getState } = values;
    const state = getState();
    let indexes = state.filterReducer.selectedMailbagIndex;
    const carditFilter = (state.form.carditFilter.values) ? state.form.carditFilter.values : {}
    if (indexes.length > 0) {
        return true
    }
 else if (carditFilter.shipmentPrefix != null || carditFilter.masterDocumentNumber != null) {

        return true
    }
    else {
        return false;
    }
}