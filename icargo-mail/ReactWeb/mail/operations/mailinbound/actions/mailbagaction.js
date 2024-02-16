import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax';
import { change } from 'icoreact/lib/ico/framework/component/common/form';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util'
import { reset } from 'redux-form';
import { dispatchAction , asyncDispatch } from 'icoreact/lib/ico/framework/component/common/actions';
import { openPopup } from 'icoreact/lib/ico/framework/action/openpopup';
import { createSelector } from 'icoreact/lib/ico/framework/component/common/app/util'
import {navigateToScreen} from 'icoreact/lib/ico/framework/action/navigateaction'
import { requestValidationError } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import {isSubGroupEnabled} from 'icoreact/lib/ico/framework/component/common/orchestration';
import * as constant from '../constants/constants';
import { onContainerRowSelection } from '../actions/containeraction'
import { requestWarning } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { clearHandsonTable } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import moment from 'moment';
import { addRow } from 'icoreact/lib/ico/framework/component/common/store/commonactions';



export function allMailbagIconAction(values){   
    const {args, dispatch, getState } = values;
    const state = getState();
    let mailBagDetailsCollection=[];
    const indexArray=args&&args.indexArray?args.indexArray:[];
    const filterValues=state.filterReducer.filterVlues
    let containerDetail=state.containerReducer.containerDetail?
                                        (state.containerReducer.containerDetail):{};
    let mailinboundDetails=state.listFlightreducer?(state.listFlightreducer.flightDetails):{};
    const mailbagDetailsCollectionFromStore=(state.mailbagReducer.filterFlag)?(state.mailbagReducer.mailDataAfterFilter?
        state.mailbagReducer.mailDataAfterFilter.results:{}):
            state.mailbagReducer?(state.mailbagReducer.mailbagData.results):{};
    indexArray.forEach(function(element) {
        mailBagDetailsCollection.push(mailbagDetailsCollectionFromStore[element]);
    
    }, this);
    if(isEmpty(containerDetail)){
        return;
    }
    const fromDate=filterValues.fromDate?filterValues.fromDate:'';
    const toDate=filterValues.toDate?filterValues.toDate:'';
    const airportCode= filterValues.port?filterValues.port:'';
    const containerNumber=containerDetail.containerno?containerDetail.containerno:''
    const carrierCode=mailinboundDetails.carrierCode?mailinboundDetails.carrierCode:'';
    const flightNumber=mailinboundDetails.flightNo?mailinboundDetails.flightNo:'';
    const flightDate = mailinboundDetails.flightDate?mailinboundDetails.flightDate.split(' ')[0]:'';
    let  valildationforimporthandling="N";
    const onlineAirportParam = mailinboundDetails.onlineAirportParam?mailinboundDetails.onlineAirportParam:'Y';
    const sysParamForRestrictingImportHandling= state.commonReducer.valildationforimporthandling;
    if(onlineAirportParam==='Y'&&sysParamForRestrictingImportHandling==='Y'){
        valildationforimporthandling='Y';
    }
    let mailbag='';
    mailbagDetailsCollectionFromStore.forEach(element => {
        mailbag=mailbag+element.mailBagId+'-';
    });
    //let mailbag=mailBagDetailsCollection&&mailBagDetailsCollection[0]?mailBagDetailsCollection[0].mailBagId:'';
    var url = "mail.operations.ux.mailbagenquiry.defaultscreenload.do?fromScreen=MailInbound&isPopup=true&fromDate="+fromDate+'&toDate='+toDate+'&airport='+airportCode+
        '&carrierCode=' + carrierCode + '&flightNumber=' + flightNumber + '&flightDate=' + flightDate + '&containerNumber=' + 
        containerNumber + '&mailBagId=' + mailbag
        +'&valildationforimporthandling='+valildationforimporthandling;
 
    var optionsArray = {
        url,
        dialogWidth: "1250",
        dialogHeight: "600",
      
        popupTitle: 'All Mailbags'
        }
    dispatch(dispatchAction(openPopup)(optionsArray));
}

export function onApplyDsnSort(values){

    const {args, dispatch, getState } = values;
    const state = getState();
    const getDsnSortDetails = () => args;
    const getDsnDetails = () =>  state.mailbagReducer && state.mailbagReducer.dsnData?
        (state.mailbagReducer.dsnData.results) : [];
    
    const getDsnSortedDetails = createSelector([getDsnSortDetails, getDsnDetails], (sortDetails, dsns) => {
        if (sortDetails) {
        const sortBy = sortDetails.sortBy;
        const sortorder = sortDetails.sortByItem;
            dsns.sort((record1, record2) => {
                let sortVal = 0;
                let data1;
                let data2;
                data1 = record1[sortBy] && typeof record1[sortBy] === "object"?record1[sortBy].systemValue:record1[sortBy];
                data2 =record2[sortBy] && typeof record2[sortBy] === "object"? record2[sortBy].systemValue:record2[sortBy];
                if(data1===null){
                    data1='';
                }    
                if(data2===null){
                    data2='';
                }
                if (data1 > data2) {
                    sortVal = 1;
                }
                if (data1 < data2) {
                    sortVal = -1;
                }
                if (sortorder === 'DSC') {
                    sortVal = sortVal * -1;
                }
                return sortVal;
            });
        }
        return dsns;
    });

    let dsnData=getDsnSortedDetails();
    let pagedDsnData=state.mailbagReducer && state.mailbagReducer.mailbagData ? 
          state.mailbagReducer.dsnData : []; 
    pagedDsnData={...pagedDsnData,results:dsnData}; 
    dispatch({type: constant.APPLY_DSN_SORT,dsnDataAfterSort:pagedDsnData});
}
export function onApplyMailSort(values){
    const { args, dispatch, getState } = values;
    const state = getState();
    const getMailSortDetails = () => args;
    const getMailDetails = () =>  state.mailbagReducer && state.mailbagReducer.mailbagData?
        (state.mailbagReducer.mailbagData.results) : [];
    const getMailSortedDetails = createSelector([getMailSortDetails, getMailDetails], (sortDetails, mails) => {
        if (sortDetails) {
        const sortBy = sortDetails.sortBy;
        const sortorder = sortDetails.sortByItem;
            mails.sort((record1, record2) => {
                let sortVal = 0;
                let data1;
                let data2;
                data1 = record1[sortBy] && typeof record1[sortBy] === "object"?record1[sortBy].systemValue:record1[sortBy];
                data2 =record2[sortBy] && typeof record2[sortBy] === "object"? record2[sortBy].systemValue:record2[sortBy];
                if(data1===null){
                    data1='';
                }    
                if(data2===null){
                    data2='';
                }
                if (data1 > data2) {
                    sortVal = 1;
                }
                if (data1 < data2) {
                    sortVal = -1;
                }
                if (sortorder === 'DSC') {
                    sortVal = sortVal * -1;
                }
                return sortVal;
            });
        }
        return mails;
    });

    let mailbagData=getMailSortedDetails();
    let pagedMailData=state.mailbagReducer && state.mailbagReducer.mailbagData ? 
          state.mailbagReducer.mailbagData : []; 
    pagedMailData={...pagedMailData,results:mailbagData}; 
    dispatch({type: constant.APPLY_MAIL_SORT,mailDataAfterSort:pagedMailData});
}
export function onApplyDsnFilter(values) {
    const { dispatch, getState } = values;
    const state = getState();
    /*
    const formValues = getState().form.dsnFilter.values;
    if(isEmpty(formValues)){
        return;
    }
    const state=getState();
    const getTableResults = () =>
        state.mailbagReducer && state.mailbagReducer.dsnData?
        (state.mailbagReducer.dsnData.results) : [];
    let dsnData=[];      
    const getTableFilter = () => 
        state.form.dsnFilter.values ? 
          state.form.dsnFilter.values : {};
    const getDetails = createSelector([getTableResults, getTableFilter], (results, filterValues) => {
         if (!isEmpty(filterValues)) {
             return results.filter((obj) => {
                 const currentObj = {
                     ooe:obj.ooe,doe:obj.doe,
                        category:obj.category,subClass:obj.mailSubclass,year:obj.year,
                            dsn:obj.dsn,class:obj.mailClass,
                            awb:obj.masterDocNumber,
                           consignmentNumber:obj.consignmentNumber,
                            paCode:obj.paCode,
                            routingAvlFlag:obj.routingAvl==true?true:null,pltEnableFlag:obj.pltEnable==true?true:null,
                 };
                 const anotherObj = { ...currentObj, ...filterValues };
                 if (JSON.stringify(currentObj) === JSON.stringify(anotherObj))
                     return true;
                 else
                     return false
                    })

         } else {
             return results;
         }
                });
    dsnData=getDetails();
    let pagedDsnData=state.mailbagReducer && state.mailbagReducer.dsnData ? 
          state.mailbagReducer.dsnData : []; 
    pagedDsnData={...pagedDsnData,results:dsnData}; 
    dispatch({ type: constant.APPLY_DSN_FILTER, dsnFilterValues:formValues,dsnDataAfterFilter:pagedDsnData });
   */
    let dsnFilterValues  = state.form.dsnFilter ? state.form.dsnFilter.values :{}
    dispatch({ type:constant.APPLY_DSN_FILTER, dsnFilterValues: dsnFilterValues});
    let selectedIndexes = state.containerReducer.selectedContainerIndex;
    let rowData = state.containerReducer.containerDetail;
    dispatch(asyncDispatch(onContainerRowSelection)({selectedIndexes,rowData}))
  
}

export function onApplyMailbagFilter(values) {
    const { dispatch, getState } = values;
    const state=getState();
    /*
    const formValues = getState().form.mailbagFilter.values;
    if(isEmpty(formValues)){
        return;
    }
    const state=getState();
    const getTableResults = () =>
        state.mailbagReducer && state.mailbagReducer.mailbagData?
        (state.mailbagReducer.mailbagData.results) : [];

    let mailbagData=[];      
    const getTableFilter = () => 
        state.form.mailbagFilter.values ? 
          state.form.mailbagFilter.values : {};

    const getDetails = createSelector([getTableResults, getTableFilter], (results, filterValues) => {
         if (!isEmpty(filterValues)) {
            
             return results.filter((obj) => {

                 const currentObj = {
                     mailBagId:obj.mailBagId,origin:obj.originDestPair.split('-')[0],destination:obj.originDestPair.split('-')[1],
                        category:obj.category,subClass:obj.subClass,year:obj.year,
                            dsn:obj.dsn,rsn:obj.rsn,hni:obj.hni,ri:obj.ri,weight:obj.weight,
                            volume:obj.volume,scanDate:obj.scanDate,awb:obj.awb,transfferCarrier:obj.transfferCarrier,
                            arriveSealNo:obj.sealNo,sealNo:obj.sealNo,consignmentNumber:obj.consignmentNumber,
                            paCode:obj.paCode,transitFlag:obj.transitFlag==='Y'?true:false,unarrived:obj.arriveFlag==='N'?true:false,
                            arriveFlag:obj.arriveFlag==='Y'?true:false,deliverFlag:obj.deliverFlag==='Y'?true:false,
                            routingAvlFlag:obj.routingAvlFlag==='Y'?true:false,pltEnableFlag:obj.pltEnableFlag==='Y'?true:false,
                 };
                 let filterTemp = {}
                 if(filterValues.mailBagId && filterValues.mailBagId.trim().length>0) {
                    filterTemp.mailBagId = filterValues.mailBagId;
                 }
                 if(filterValues.ooe && filterValues.ooe.trim().length>0) {
                    filterTemp.origin = filterValues.ooe;
                 }
                 if(filterValues.doe && filterValues.doe.trim().length>0) {
                    filterTemp.destination = filterValues.doe;
                 }
                 if(filterValues.category && filterValues.category.trim().length>0) {
                    filterTemp.category = filterValues.category;
                 }
                 if(filterValues.subClass && filterValues.subClass.trim().length>0) {
                    filterTemp.subClass = filterValues.subClass;
                 }
                 if(filterValues.year && filterValues.year.trim().length>0) {
                    filterTemp.year = filterValues.year;
                 }
                 if(filterValues.dsn && filterValues.dsn.trim().length>0) {
                    filterTemp.dsn = filterValues.dsn;
                 }
                 if(filterValues.rsn && filterValues.rsn.trim().length>0) {
                    filterTemp.rsn = filterValues.rsn;
                 }
                 if(filterValues.hni && filterValues.hni.trim().length>0) {
                    filterTemp.hni = filterValues.hni;
                 }
                 if(filterValues.ri && filterValues.ri.trim().length>0) {
                    filterTemp.ri = filterValues.ri;
                 }
                 if(filterValues.weight && filterValues.weight.trim().length>0) {
                    filterTemp.weight = filterValues.weight;
                 }
                 if(filterValues.volume && filterValues.volume.trim().length>0) {
                    filterTemp.volume = filterValues.volume;
                 }
                 if(filterValues.scanDate && filterValues.scanDate.trim().length>0) {
                    filterTemp.scanDate = filterValues.scanDate;
                 }
                 if(filterValues.awb && filterValues.awb.trim().length>0) {
                    filterTemp.awb = filterValues.awb;
                 }
                 if(filterValues.transfferCarrier && filterValues.transfferCarrier.trim().length>0) {
                    filterTemp.transfferCarrier = filterValues.transfferCarrier;
                 }
                 if(filterValues.arriveSealNo && filterValues.arriveSealNo.trim().length>0) {
                    filterTemp.arriveSealNo = filterValues.arriveSealNo;
                 }
                 if(filterValues.sealNo && filterValues.sealNo.trim().length>0) {
                    filterTemp.sealNo = filterValues.sealNo;
                 }
                 if(filterValues.consignmentNumber && filterValues.consignmentNumber.trim().length>0) {
                    filterTemp.consignmentNumber = filterValues.consignmentNumber;
                 }
                 if(filterValues.paCode && filterValues.paCode.trim().length>0) {
                    filterTemp.paCode = filterValues.paCode;
                 }
                 if(filterValues.transitFlag) {
                    filterTemp.transitFlag = filterValues.transitFlag;
                 }
                 if(filterValues.deliverFlag) {
                    filterTemp.deliverFlag = filterValues.deliverFlag;
                 }
                 if(filterValues.arriveFlag) {
                    filterTemp.arriveFlag = filterValues.arriveFlag;
                 }
                 if(filterValues.routingAvlFlag) {
                    filterTemp.routingAvlFlag = filterValues.routingAvlFlag;
                 }
                 if(filterValues.pltEnableFlag) {
                    filterTemp.pltEnableFlag = filterValues.pltEnableFlag;
                 }


                 const anotherObj = { ...currentObj, ...filterTemp };
                 if (JSON.stringify(currentObj) === JSON.stringify(anotherObj))
                     return true;
                 else
                     return false
             })

         } else {
             return results;
         }

     });
    
    mailbagData=getDetails();
    let pagedMailData=state.mailbagReducer && state.mailbagReducer.mailbagData ? 
          state.mailbagReducer.mailbagData : []; 
    pagedMailData={...pagedMailData,results:mailbagData}; 
    dispatch({ type:constant.APPLY_MAILBAG_FILTER, filterValues:formValues,mailDataAfterFilter:pagedMailData });
     */
    let formValues  = state.form.mailbagFilter ? state.form.mailbagFilter.values :{}
    dispatch({ type:constant.APPLY_MAILBAG_FILTER, filterValues: formValues});
    let selectedIndexes = state.containerReducer.selectedContainerIndex;
    let rowData = state.containerReducer.containerDetail;
    dispatch(asyncDispatch(onContainerRowSelection)({selectedIndexes,rowData}))
}
export function onClearMailbagFilter(values) {
    const { dispatch } = values;
    // const formValues= getState().form.mailbagFilter;
    /*
    dispatch(reset(constant.MAILBAG_FILTER));  
    dispatch({type: constant.CLEAR_MAIL_FILTER}); 
    */
   dispatch({
    type: "@@redux-form/INITIALIZE", meta: { form: 'mailbagFilter', keepDirty: true },
    payload: {
        mailbagId:"",
        mailOrigin: "",
        mailDestination: "",
        mailCategoryCode: "",
        mailSubclass:"",
        despatchSerialNumber: "",
        receptacleSerialNumber: "",
        consigmentNumber: "",
        shipmentPrefix: "",
        masterDocumentNumber: "",
        mailStatus: "",
        paCode: "",
        carditAvailable: false
    }
    });
    dispatch({ type: "@@redux-form/RESET", meta: { form: 'mailbagFilter' } })
}

export function onClearDsnFilter(values) {
    const {  dispatch } = values;
    // const formValues= getState().form.dsnFilter;
    /*
    dispatch(reset(constant.DSN_FILTER));  
    dispatch({type: constant.CLEAR_DSN_FILTER}); 
    */
   dispatch({
    type: "@@redux-form/INITIALIZE", meta: { form: 'dsnFilter', keepDirty: true },
    payload: {
        despatchSerialNumber:"",
        ooe:"",
        doe:"",
        mailCategoryCode:"",
        mailSubclass:"",
        paCode:"",
        consignmentNumber:"",
        shipmentPrefix: "",
        masterDocumentNumber: ""
    }
    });
    dispatch({ type: "@@redux-form/RESET", meta: { form: 'dsnFilter' } })
}
export function saveUpdatedMailDetails(response){
    const data=response.results[0];
    const url = 'rest/mail/operations/mailinbound/savemailarrival';
                return makeRequest({
                    url,
                    data: {...data}
                }).then(function (response) {
                    // handleResponse(dispatch, response, actionType)
                    return response;
                })
                .catch(error => {
                    return error;
                });
}

export function listContainerDetails(values){  
    const {  dispatch,getState } = values;
    let state=getState();
    const containerVo=values.results[0].mailArrivalVO.containerDetails[0];
    let mailinboundDetails=state.listFlightreducer?(state.listFlightreducer.flightDetails):{}; 
    const data={mailinboundDetails}; 
    const url = 'rest/mail/operations/mailinbound/ListContainers';
    const mode=constant.CON_LIST_ON_CONT_OPRN; 
    return makeRequest({
                    url,
                    data: {...data}
                }).then(function (response) {
                    response={...response,containerVo:containerVo}
                     handleResponse(dispatch, response,mode);
                    return response;
                    })
                .catch(error => {
                    return error;
                });

}

export function listChangeFlightMailPanel(values){

    const { args, dispatch } = values;
    // const state=getState(); 
    
    if(args.action===constant.LIST_TRANSFER){
        const mode=constant.LIST_CONTAINERS_POPUP;
        let mailAcceptance ;
        const flightCarrierflag =  args.results[0] &&  args.results[0].flightCarrierFilter.assignTo;
        if(flightCarrierflag ==='F'){
            mailAcceptance = args.results[0]&&
        args.results[0].mailflightspage&&args.results[0].mailflightspage.results[0]?
            args.results[0].mailflightspage.results[0]:{};
        }
        else{
            mailAcceptance = args.results[0]&&
            args.results[0].mailcarrierspage&&args.results[0].mailcarrierspage.results[0]?
              args.results[0].mailcarrierspage.results[0]:{}; 
        }

       

        const data = { mailAcceptance:{...mailAcceptance,containerPageInfo:null}, flightCarrierflag, containerDisplayPage:1,actionType:'Mail Transfer'}
        const url = 'rest/mail/operations/mailinbound/listContainersOutbound';
        return makeRequest({
            url,
            data: { ...data }
        }).then(function (response) {
           // handleResponse(dispatch, response, flightsArray, flightIndex,containerDisplayPage);
            handleResponse(dispatch, response,mode);
            return response;
        })
            .catch(error => {
                return error;
            });
    }
    else{
        let mode = '';
        if(args.action===constant.ARRIVE_MAIL) {
            mode = constant.ARRIVE_MAIL;
        } else {
            mode=constant.LIST_CONTAINERS_POPUP_CHANGE_FLIGHT;
        }
        const mailinboundDetails=args.results[0].mailinboundDetailsCollectionPage.results[0];
        const data={mailinboundDetails, pageSize:100, pageNumber:args.pageNumber?args.pageNumber:1};
        const url = 'rest/mail/operations/mailinbound/ListContainers';
        return makeRequest({
            url,
            data: {...data}
        }).then(function (response) {
            response={...response,mailinboundDetails:mailinboundDetails}
             handleResponse(dispatch, response,mode);
            return response;
            })
        .catch(error => {
            return error;
        });
    }   
    //const mailinboundDetails=args.results[0].mailinboundDetailsCollectionPage.results[0];
    /*let mailAcceptance = args.results[0]&&
        args.results[0].mailflightspage&&args.results[0].mailflightspage.results[0]?
            args.results[0].mailflightspage.results[0]:{};
    //const data={mailinboundDetails,pageNumber:args.pageNumber?args.pageNumber:1}; 
    const mode='LIST_CONTAINERS_POPUP';
    //const url = 'rest/mail/operations/mailinbound/ListContainers';
    /*return makeRequest({
                    url,
                    data: {...data}
                }).then(function (response) {
                    response={...response,mailinboundDetails:mailinboundDetails}
                     handleResponse(dispatch, response,mode);
                    return response;
                    })
                .catch(error => {
                    return error;
                });*/
    /*const data = { mailAcceptance:{...mailAcceptance,containerPageInfo:null}, flightCarrierflag:'F', containerDisplayPage:1}
    const url = 'rest/mail/operations/mailinbound/listContainersOutbound';
        return makeRequest({
            url,
            data: { ...data }
        }).then(function (response) {
           // handleResponse(dispatch, response, flightsArray, flightIndex,containerDisplayPage);
            handleResponse(dispatch, response,mode);
            return response;
        })
            .catch(error => {
                return error;
            });*/


}

export function updateFlightDetails(values) {
    const { args,dispatch, getState } = values;
    let mailinboundDetails = {};
    if(args.response && args.response.results[0] && 
        args.response.results[0].mailinboundDetailsCollectionPage.results[0]) {
            mailinboundDetails=args.response.results[0].mailinboundDetailsCollectionPage.results[0];
    }
   
    let state  = getState();
    let flights = [...state.listFlightreducer.flightData.results]
    let index = flights.findIndex(flt=> flt.flightNo ===mailinboundDetails.flightNo && 
        flt.flightSeqNumber===mailinboundDetails.flightSeqNumber && flt.port ===mailinboundDetails.port 
        && flt.carrierId === mailinboundDetails.carrierId) 
        flights.splice(index, 1, mailinboundDetails)
    let updatedFlightsPage = {
        ...state.listFlightreducer.flightData,
        results: flights
    }
    dispatch( {type:constant.FLIGHT_LIST_ALONE, mailinboundDetails: updatedFlightsPage});
}

export function setDeliveryPopuFieldStatus(values) {
    const { args,dispatch, getState } = values;
    let enableDeliveryPopup = true;
    const indexArray=args?args.indexArray:null;
    const state=getState();
    const activeMailbagTab=state.containerReducer?state.containerReducer.activeMailbagTab:null;
    const dsnDetailsCollectionFromStore=(state.mailbagReducer.dsnFilterFlag)?(state.mailbagReducer.dsnDataAfterFilter?
            state.mailbagReducer.dsnDataAfterFilter.results:{}):
            state.mailbagReducer?(state.mailbagReducer.dsnData.results):{};
    const mailbagDetailsCollectionFromStore=(state.mailbagReducer.filterFlag)?(state.mailbagReducer.mailDataAfterFilter?
        state.mailbagReducer.mailDataAfterFilter.results:{}):
            state.mailbagReducer?(state.mailbagReducer.mailbagData.results):{};
    if(activeMailbagTab===constant.DSN_VIEW){
            indexArray.forEach((element)=> {
                if(dsnDetailsCollectionFromStore[element] && dsnDetailsCollectionFromStore[element].mailBagStatus==="Delivered") {
                    enableDeliveryPopup = false;
                }
            });
    }
    if(activeMailbagTab===constant.MAIL_VIEW){
        indexArray.forEach((element)=> {
            if(mailbagDetailsCollectionFromStore[element] && mailbagDetailsCollectionFromStore[element].mailBagStatus==="Delivered") {
                enableDeliveryPopup = false;
            }
        });
    }
    dispatch( {type:constant.SET_DELIVERY_POPUP_FIELDS_STATUS, data: enableDeliveryPopup});
}
export function getFlightDetailsForChangeFlight(values){
    const { args, getState } = values;
    const state=getState();
    let transferDetails={};
    let flightnumber={};
    let changeFlightMailDetails={};
    let flightCarrierFilter = {};
    if(args.action===constant.LIST_TRANSFER){
        if(state.form.transferMailForm&&state.form.transferMailForm.values&&state.form.transferMailForm.values.transferFilterType === 'F'){
        transferDetails=state.form.transferMailForm.values?
                                state.form.transferMailForm.values.flightnumber:{};
        /*flightnumber={carrierCode:transferDetails.carrierCode,
                        flightNumber:transferDetails.flightNumber,
                        flightDate:transferDetails.flightDate};*/
        
        flightCarrierFilter.flightNumber = transferDetails.flightNumber;
        flightCarrierFilter.flightDate = transferDetails.flightDate;
        flightCarrierFilter.carrierCode = transferDetails.carrierCode;
        flightCarrierFilter.assignTo = 'F';
        if((flightCarrierFilter.carrierCode==='' ||flightCarrierFilter.carrierCode=== undefined) ||(flightCarrierFilter.flightNumber==='' ||flightCarrierFilter.flightNumber=== undefined)){
            return Promise.reject(new Error("Please specify Flight Details")); 
        }
        flightCarrierFilter={
            ...flightCarrierFilter,
            airportCode:state.filterReducer.filterValues.port?state.filterReducer.filterValues.port:'',
            flightDisplayPage:1,
            flightStatus : ['ACT','TBA']
        };
        //added as part of IASCB-36551
        if(state.filterReducer.filterValues) {
            flightCarrierFilter.operatingReference = state.filterReducer.filterValues.operatingReference
        } 
        }
        else{
            let popupAction = state.commonReducer.popupAction;
            flightCarrierFilter.assignTo = 'C'; 
            flightCarrierFilter.carrierCode = state.form.transferMailForm.values.carrier;
            if(flightCarrierFilter.carrierCode==='' ||flightCarrierFilter.carrierCode=== undefined){
                return Promise.reject(new Error("Please specify Carrier")); 
            }
            flightCarrierFilter.destination  = state.form.transferMailForm.values.destination;
            if((flightCarrierFilter.destination ==="" ||flightCarrierFilter.destination=== undefined) && popupAction!=='embargo'){
                return Promise.reject(new Error("Please enter Destination")); 
            }
            flightCarrierFilter.airportCode = state.form.transferMailForm.values.uplift;
            flightCarrierFilter={...flightCarrierFilter,carrierDisplayPage:1};

        }
        const data = { flightCarrierFilter };
        const url = 'rest/mail/operations/mailinbound/list';
                    return makeRequest({
                        url,
                        data: { ...data }
                    }).then(function (response) {
                        response={...response,pageNumber:args.pageNumber?args.pageNumber:1}
                        return response;
                    })
                    .catch(error => {
                        return error;
                    });
    }
    else if(args.action===constant.LIST_CHANGEFLIGHT){
        changeFlightMailDetails=state.form.ChangeFlightMailDetails.values?state.form.ChangeFlightMailDetails.values.flightnumber:{};
        if((changeFlightMailDetails==='' ||changeFlightMailDetails=== undefined)){
          return Promise.reject(new Error("Please specify Flight Details")); 
      }
      else if(changeFlightMailDetails.carrierCode=='' || changeFlightMailDetails.carrierCode=== undefined){
        return Promise.reject(new Error("Please specify Carrier"));
      } else{
            flightnumber = {
                carrierCode: changeFlightMailDetails.carrierCode,
                        flightNumber:changeFlightMailDetails.flightNumber,
                flightDate: changeFlightMailDetails.flightDate
            };
      }
        
        const port=state.form.mailinboundFilter.values?state.form.mailinboundFilter.values.port:{};
        const mailinboundFilter={flightnumber:flightnumber,pageNumber:1,port:port,pageSize:30};
                const flightData={mailinboundFilter};
                const flightUrl = 'rest/mail/operations/mailinbound/listflightdetails';
                return makeRequest({
                    url:flightUrl,
                    data: {...flightData}
                }).then(function (response) {
                    response={...response,pageNumber:args.pageNumber?args.pageNumber:1}
                    return response;
                })
                .catch(error => {
                    return error;
                });
    }
    else if(args.action === constant.ARRIVE_MAIL) {
        let mailinboundDetails = {};
        if(args.response && args.response.results && args.response.results[0].mailinboundDetails) {
            mailinboundDetails = args.response.results[0].mailinboundDetails
        }
        let mailinboundFilter = {
            "port": mailinboundDetails.port,	
            "flightnumber":
                {
                "carrierCode": mailinboundDetails.carrierCode,
                "flightNumber":mailinboundDetails.flightNo,
                "flightDate": mailinboundDetails.flightDate.substring(0,11),
                },
            "pageNumber":"1",
            "pageSize":"10",
            "carrierCode":mailinboundDetails.carrierCode,
            "flightNumber":mailinboundDetails.flightNo,
            "flightDate":mailinboundDetails.flightDate.substring(0,11)
        }
        const flightUrl = 'rest/mail/operations/mailinbound/listflightdetails';
            return makeRequest({
                url:flightUrl,
                data: {mailinboundFilter}
            }).then(function (response) {
                response={...response,pageNumber:args.pageNumber?args.pageNumber:1}
                return response;
            })
            .catch(error => {
                return error;
         });
    }
    //const port=state.form.mailinboundFilter.values?state.form.mailinboundFilter.values.port:{};
    //flightCarrierFilter={...flightCarrierFilter,airportCode:state.filterReducer.filterValues.port?state.filterReducer.filterValues.port:'DFW',flightDisplayPage:1};
    //flightCarrierFilter.flightDisplayPage=1;
    /*let mailinboundDetailsCollection={};
    const mailinboundFilter={flightnumber:flightnumber,pageNumber:1,port:port};
                const flightData={mailinboundFilter};
                const flightUrl = 'rest/mail/operations/mailinbound/listflightdetails';
                return makeRequest({
                    url:flightUrl,
                    data: {...flightData}
                }).then(function (response) {
                    response={...response,pageNumber:args.pageNumber?args.pageNumber:1}
                    return response;
                })
                .catch(error => {
                    return error;
                });*/
    /*const data = { flightCarrierFilter };
    const url = 'rest/mail/operations/mailinbound/list';
                return makeRequest({
                    url,
                    data: { ...data }
                }).then(function (response) {
                    response={...response,pageNumber:args.pageNumber?args.pageNumber:1}
                    return response;
                })
                .catch(error => {
                    return error;
                });*/
}

export function screenLoadDamageCapture(values){

    const { args, dispatch, getState } = values;
    const state=getState();
    // const actionType=args?args.actionName:null;
    const indexArray=args?args.indexArray:null;

    const mailbagDetailsCollectionFromStore=(state.mailbagReducer.filterFlag)?(state.mailbagReducer.mailDataAfterFilter?
                        state.mailbagReducer.mailDataAfterFilter.results:{}):
                            state.mailbagReducer?(state.mailbagReducer.mailbagData.results):{};
    let containerDetail=state.containerReducer.containerDetail?
                                        (state.containerReducer.containerDetail):{};
    const mailBagDetailsCollectionSelected=[];

    let mailinboundDetails=state.listFlightreducer?(state.listFlightreducer.flightDetails):{};
    indexArray.forEach(function(element) {
                mailBagDetailsCollectionSelected.push(mailbagDetailsCollectionFromStore[element]);
                }, this);
    containerDetail={...containerDetail,mailBagDetailsCollection:mailBagDetailsCollectionSelected};
    const data={containerDetail,mailinboundDetails};
    const url = 'rest/mail/operations/mailinbound/screenloaddamagecapture';
                return makeRequest({
                    url,
                    data: {...data}
                }).then(function (response) {
                    if(!isEmpty(response.errors)){
                        dispatch( {type:constant.ERROR_SHOW});
                        return response;
                        } 
                    else  {  
                    handleResponse(dispatch, response, constant.DAMAGE_LIST)
                    return response;
                    }
                })
                .catch(error => {
                    return error;
                });


}

export function saveDamageCapture(values){
    const {  getState } = values;
    const state=getState();
    // const damagedMailBagCollection=state.mailbagReducer?(state.mailbagReducer.damageDetails):{};
    let containerDetailsVos=(state.containerReducer)?state.containerReducer.containerDetailsToBeReused:{};
    const mailArrivalVo={containerDetails:containerDetailsVos};
    let damagedMailbags = state.form.damageData.values?state.form.damageData.values.damageData:{};
    const data={damagedMailBagCollection:damagedMailbags,mailArrivalVO:mailArrivalVo};
    const url = 'rest/mail/operations/mailinbound/savedamagecapture';
                return makeRequest({
                    url,
                    data: {...data}
                }).then(function (response) {
                    return response;
                })
                .catch(error => {
                    return error;
                });


}

export function screenloadAttachRoutingAction(values){

    const { args, dispatch, getState } = values;
    const state = getState();
    const actionType=args?args.actionName:null;
    const indexArray=args?args.indexArray:null;
    const dsnDetailsCollection=[];
    const containerDetailsCollection=[];

    const dsnDetailsCollectionFromStore=(state.mailbagReducer.dsnFilterFlag)?(state.mailbagReducer.dsnDataAfterFilter?
                        state.mailbagReducer.dsnDataAfterFilter.results:{}):
                            state.mailbagReducer?(state.mailbagReducer.dsnData.results):{};
    let containerDetail=state.containerReducer.containerDetail?
                                        (state.containerReducer.containerDetail):{}; 
    const mailBagDetailsCollectionSelected=[];
    const mailbagDetailsCollectionFromStore=(state.mailbagReducer.filterFlag)?(state.mailbagReducer.mailDataAfterFilter?
                        state.mailbagReducer.mailDataAfterFilter.results:{}):
                            state.mailbagReducer?(state.mailbagReducer.mailbagData.results):{};
    let mailinboundDetails=state.listFlightreducer?(state.listFlightreducer.flightDetails):{};    
    const activeMailbagTab=state.containerReducer?state.containerReducer.activeMailbagTab:null;                                                                   


 
    
    if(activeMailbagTab===constant.DSN_VIEW){
        indexArray.forEach(function(element) {
                dsnDetailsCollection.push(dsnDetailsCollectionFromStore[element]);
                }, this);
     }
    if(activeMailbagTab===constant.MAIL_VIEW){
        indexArray.forEach(function(element) {
                mailBagDetailsCollectionSelected.push(mailbagDetailsCollectionFromStore[element]);
                }, this);
     }

    containerDetail = {
        ...containerDetail, dsnDetailsCollection: dsnDetailsCollection,
        mailBagDetailsCollection: mailBagDetailsCollectionSelected
    }
    containerDetailsCollection.push(containerDetail);
    const data = {
        containerDetail: containerDetail, mailinboundDetails,
        containerDetailsCollection: containerDetailsCollection
    };
    const url = 'rest/mail/operations/mailinbound/validateattachrouting';
                return makeRequest({
                    url,
                    data: {...data}
                }).then(function (response) {
                    if(!isEmpty(response.errors)){
                        dispatch( {type:constant.ERROR_SHOW});
                        return response;
                    } 
                    else
                    handleResponse(dispatch, response, actionType)
                    return response;
                })
                .catch(error => {
                    return error;
                });

    
    
    
}

export function screenloadTransferMailAction(values){

    const { args, dispatch, getState } = values;
    const state = getState();
    const actionType=args?args.actionName:null;
    const indexArray=args?args.indexArray:null;
    const dsnDetailsCollection=[];
    const activeMailbagTab=state.containerReducer?state.containerReducer.activeMailbagTab:null;
    let containerDetail=state.containerReducer.containerDetail?
                                        (state.containerReducer.containerDetail):{};

    const dsnDetailsCollectionFromStore=(state.mailbagReducer.dsnFilterFlag)?(state.mailbagReducer.dsnDataAfterFilter?
                        state.mailbagReducer.dsnDataAfterFilter.results:{}):
                            state.mailbagReducer?(state.mailbagReducer.dsnData.results):{};
    const mailBagDetailsCollectionSelected=[];
    const mailbagDetailsCollectionFromStore=(state.mailbagReducer.filterFlag)?(state.mailbagReducer.mailDataAfterFilter?
                        state.mailbagReducer.mailDataAfterFilter.results:{}):
                            state.mailbagReducer?(state.mailbagReducer.mailbagData.results):{};
    let mailinboundDetails=state.listFlightreducer?(state.listFlightreducer.flightDetails):{};

    const popupAction=state.commonReducer.popupAction;

     if(activeMailbagTab===constant.DSN_VIEW){
        indexArray.forEach(function(element) {
                dsnDetailsCollection.push(dsnDetailsCollectionFromStore[element]);
                }, this);
     }
    if(activeMailbagTab===constant.MAIL_VIEW){
        indexArray.forEach(function(element) {
                mailBagDetailsCollectionSelected.push(mailbagDetailsCollectionFromStore[element]);
                }, this);
     }
    containerDetail = {
        ...containerDetail, dsnDetailsCollection: dsnDetailsCollection,
        mailBagDetailsCollection: mailBagDetailsCollectionSelected
    }
  
    const data={containerDetail:containerDetail,mailinboundDetails,popupAction,embargoFlag:'TRA'};

    
    const url = 'rest/mail/operations/mailinbound/validatetransfermail';
                return makeRequest({
                    url,
                    data: {...data}
                }).then(function (response) {
                    if(!isEmpty(response.errors)){
                        dispatch( {type:constant.ERROR_SHOW});
                        dispatch({type:constant.EMBARGO_WARNING_POPUP_HANDLING})
                        return response;
                    }
                    else if(response.status==='embargo'){
                        invokeEmbargoPopup(dispatch); 
                        dispatch({type:constant.EMBARGO_CONTINUE_ACTION,actionType,indexArray});
                    }else{
                        if(!state.commonReducer.isContinued){
                            dispatch({type:constant.EMBARGO_WARNING_POPUP_HANDLING})
                        }
                    handleResponse(dispatch, response, actionType)
                    }
                    return response;
                })
                .catch(error => {
                    return error;
                });
}

export function screenLoadChangeFlightAction(values){

    const { args, dispatch, getState } = values;
    const state = getState();
    const actionType=args?args.actionName:null;
    const indexArray=args?args.indexArray:null;
    const dsnDetailsCollection=[];
    const activeMailbagTab=state.containerReducer?state.containerReducer.activeMailbagTab:null;

    let containerDetail=state.containerReducer.containerDetail?
                                        (state.containerReducer.containerDetail):{};
    const dsnDetailsCollectionFromStore=(state.mailbagReducer.dsnFilterFlag)?(state.mailbagReducer.dsnDataAfterFilter?
                        state.mailbagReducer.dsnDataAfterFilter.results:{}):
                            state.mailbagReducer?(state.mailbagReducer.dsnData.results):{};

    const mailBagDetailsCollectionSelected=[];
    const mailbagDetailsCollectionFromStore=(state.mailbagReducer.filterFlag)?(state.mailbagReducer.mailDataAfterFilter?
                        state.mailbagReducer.mailDataAfterFilter.results:{}):
                            state.mailbagReducer?(state.mailbagReducer.mailbagData.results):{};
    let mailinboundDetails=state.listFlightreducer?(state.listFlightreducer.flightDetails):{};



    if(activeMailbagTab===constant.DSN_VIEW){
        indexArray.forEach(function(element) {
                dsnDetailsCollection.push(dsnDetailsCollectionFromStore[element]);
                }, this);
     }
    if(activeMailbagTab===constant.MAIL_VIEW){
        indexArray.forEach(function(element) {
                mailBagDetailsCollectionSelected.push(mailbagDetailsCollectionFromStore[element]);
                }, this);
     }
   
    containerDetail = {
        ...containerDetail, dsnDetailsCollection: dsnDetailsCollection,
        mailBagDetailsCollection: mailBagDetailsCollectionSelected
    }
    const data={containerDetail:containerDetail,mailinboundDetails};
    const url = 'rest/mail/operations/mailinbound/validatemailchange';
                return makeRequest({
                    url,
                    data: {...data}
                }).then(function (response) {
                    if(!isEmpty(response.errors)){
                        dispatch( {type:constant.ERROR_SHOW});
                        return response;
                    }
                    else 
                    handleResponse(dispatch, response, actionType)
                    return response;
                })
                .catch(error => {
                    return error;
                });

}

export function screenLoadAttachAwb(values){

    const { args, dispatch, getState } = values;
    const state=getState();
    const actionType=args?args.actionName:null;
    const indexArray=args?args.indexArray:null;
    const containerDetailsCollection=[];
    const dsnDetailsCollection=[];
    // const dsnVos=[];
    const mailBagDetailsCollectionSelected=[];
    const mailbagDetailsCollectionFromStore=(state.mailbagReducer.filterFlag)?(state.mailbagReducer.mailDataAfterFilter?
                        state.mailbagReducer.mailDataAfterFilter.results:{}):
                            state.mailbagReducer?(state.mailbagReducer.mailbagData.results):{};
    let mailinboundDetails=state.listFlightreducer?(state.listFlightreducer.flightDetails):{};
    const activeMailbagTab=state.containerReducer?state.containerReducer.activeMailbagTab:null;
    let containerDetails=state.containerReducer.containerDetail?
                                        (state.containerReducer.containerDetail):{};
     const dsnDetailsCollectionFromStore=(state.mailbagReducer.dsnFilterFlag)?(state.mailbagReducer.dsnDataAfterFilter?
                        state.mailbagReducer.dsnDataAfterFilter.results:{}):
                            state.mailbagReducer?(state.mailbagReducer.dsnData.results):{};
    /*const dsnDetailsCollectionFromStore=state.mailbagReducer.dsnData?
                                        (state.mailbagReducer.dsnData.results):{};*/
    
    
     if(activeMailbagTab===constant.DSN_VIEW){
        indexArray.forEach(function(element) {
                dsnDetailsCollection.push(dsnDetailsCollectionFromStore[element]);
                }, this);
     }
    if(activeMailbagTab===constant.MAIL_VIEW){
        indexArray.forEach(function(element) {
                mailBagDetailsCollectionSelected.push(mailbagDetailsCollectionFromStore[element]);
                }, this);
     }



    containerDetails = {
        ...containerDetails, mailBagDetailsCollection: mailBagDetailsCollectionSelected,
        dsnDetailsCollection: dsnDetailsCollection
    };
    containerDetailsCollection.push(containerDetails);
    const data={containerDetailsCollection:containerDetailsCollection,mailinboundDetails:mailinboundDetails};
    const url = 'rest/mail/operations/mailinbound/screenloadattachawb';
                return makeRequest({
                    url,
                    data: {...data}
                }).then(function (response) {
                    if(!isEmpty(response.errors)){
                        dispatch( {type:constant.ERROR_SHOW});
                        return response;
                    }
                    else 
                    handleResponse(dispatch, response, actionType)
                    return response;
                })
                .catch(error => {
                    return error;
                });


}

export function detachAwb(values){
    const { args, dispatch, getState } = values;
    const state = getState();
    let indexes=[];
    let data={}
    let url = '';
    let selectedMailbags=[];
    indexes.push(args.data.indexArray);
        let containerDetailsCollection=[];
        let despatchDetailsList=[];
     for(var i=0; i<indexes[0].length;i++) {
         if(state.mailbagReducer.mailbagData.results[indexes[0][i]].awb!=null){
       selectedMailbags.push(state.mailbagReducer.mailbagData.results[indexes[0][i]]);
      
            }
            if(state.containerReducer.activeMailbagTab =="DSN_VIEW" && state.mailbagReducer.dsnData!=null){
            despatchDetailsList.push(state.mailbagReducer.dsnData.results[indexes[0][i]]);
           }
       }
      
    let selectedContainer=state.containerReducer.containerDetail
    selectedContainer={...selectedContainer,mailbagpagelist:null,totalWeight:null,
                       mailBagDetailsCollection:selectedMailbags!=null?selectedMailbags:null}
    containerDetailsCollection.push(selectedContainer);
    despatchDetailsList=despatchDetailsList.map((value)=>({...value,weight:null})); 
    data={containerDetailsCollection,despatchDetailsList}
    url = 'rest/mail/operations/mailinbound/detachAWBCommand';
    return makeRequest({
        url,
        data: {...data}
    }).then(function (response) {
        return response
    })
    .catch(error => {
        return error;
    });
}

export function changeMailTab(values){
        const { args, dispatch } = values;
        const mode=args?args:null;
        dispatch({type: constant.MAIL_MODE,mode:mode});
}

export function listTransferAction(values){
    const { args, getState } = values;
    let state=getState();
    const transferDetails=state.form.transferMailForm.values?
                                state.form.transferMailForm.values.flightnumber:{};
    const port=state.form.mailinboundFilter.values?state.form.mailinboundFilter.values.port:{};
    // let mailinboundDetailsCollection={};
    const flightnumber = {
        carrierCode: transferDetails.carrierCode,
                        flightNumber:transferDetails.flightNumber,
        flightDate: transferDetails.flightDate
    };
    const mailinboundFilter={flightnumber:flightnumber,pageNumber:1,port:port};
                const flightData={mailinboundFilter};
                const flightUrl = 'rest/mail/operations/mailinbound/listflightdetails';
                return makeRequest({
                    url:flightUrl,
                    data: {...flightData}
                }).then(function (response) {
                    response={...response,pageNumber:args.pageNumber?args.pageNumber:1}
                    return response;
                })
                .catch(error => {
                    return error;
                });
}
export const onSavemailbagDetails = (values) => {

    const { dispatch, getState ,args} = values;
    const state = getState();
    const tab=state.mailbagReducer.activeMailbagAddTab;
    let containerDetail=state.containerReducer.containerDetail?
                                        (state.containerReducer.containerDetail):{};
    let mailinboundDetails=state.listFlightreducer?(state.listFlightreducer.flightDetails):{};

    let mailbags = [];
    let mailbagslist =[];
    // let mailbagsflag=[];
    let deletedMails=[];
    const popupAction=state.commonReducer.popupAction;

    if(tab===constant.EXCEL_VIEW) {
        mailbagslist = state.handsontableReducer.addmailbagExcel ?
                             state.handsontableReducer.addmailbagExcel.insertedRows : null;
        if(mailbagslist.length>0){
            mailbagslist=mailbagslist.map( (obj)  =>
            ({ ...obj,mailbagId:obj.mailbagId?obj.mailbagId:''}));
                            mailbagslist= populateMailbagDetailsInExcel(mailbagslist,values,dispatch);

           
            mailbags= mailbagslist.map( (obj)  =>
                            ({ ...obj,operationFlag:'I'}));
        }   
    }
    else {
        mailbagslist=state.form.newMailbagsTable?
                        state.form.newMailbagsTable.values.newMailbagsTable :null
        mailbagslist=mailbagslist.filter(value=>{
            return !isEmpty(value.mailbagId)
        })
        mailbagslist = mailbagslist.map((value) => 
                                ({ ...value,operationFlag: value.__opFlag,weight:value.weight.roundedDisplayValue,weightUnit:value.weight.displayUnit }));
        deletedMails= state.form.newMailbagsTable.values ? state.form.newMailbagsTable.values.deleted :'';
        deletedMails.map((value) => ({ ...value,operationFlag:'D',mailbagWeight:value.weight.roundedDisplayValue,weightUnit:value.weight.displayUnit}));
    
        mailbags.push(...mailbagslist);
    }
    mailbags.push(...deletedMails);

    if(mailbags.length === 0){
        return Promise.reject(new Error("Please enter mailbags")); 
        }
        for(let i=0;i<mailbags.length;i++){
        if( mailbags[i].mailbagId.indexOf(' ') >= 0){

        return Promise.reject(new Error("Invalid mail id")); 
            }
        }
        let screenWarning=false;
        if(args&&args.screenWarning){
            screenWarning= args.screenWarning;
        }
    const data={containerDetail,mailinboundDetails,addMailbags:mailbags,popupAction,screenWarning}
    
    if(deletedMails.length>0){
        const message = 'Mail Bag cannot be deleted , Already exist in the system';
        const target = '';
        dispatch(requestValidationError(message, target));
        return Promise.resolve("Error"); 
    }
    
    const url = 'rest/mail/operations/mailinbound/addMailbags';
    return makeRequest({
        url,
        data: { ...data }
    }).then(function (response) {
        //handleResponseonAdding(dispatch, response,'ADD_MAILBAG_SUCCESS');
        if(response.errors && !isEmpty(response.errors.INFO)){
            state.handsontableReducer && state.handsontableReducer.addmailbagExcel ?
                    state.handsontableReducer.addmailbagExcel.insertedRows=[] : '';
            state.handsontableReducer&& state.handsontableReducer.addmailbagExcel ?
                    state.handsontableReducer.addmailbagExcel.data=[] : '';
            state.form.newMailbagsTable&&state.form.newMailbagsTable.values
                    &&state.form.newMailbagsTable.values.newMailbagsTable?
                      state.form.newMailbagsTable.values.newMailbagsTable=[]:null;
        }
        else if(response.status==='embargo') {
            invokeEmbargoPopup(dispatch); 
            dispatch({type: constant.EMBARGO_CONTINUE_ACTION_FOR_MAKING_ACTION_NULL_AFTER_ARRIVING})
        }

    //For changing the popupAction to null everytime a mailbag is added after continuing the warning
        dispatch({type:constant.EMBARGO_WARNING_POPUP_HANDLING});
        return response
    })
        .catch(error => {
            return error;
        });
}


export const onPopulatemailbagDetails = (values) => {

    const { dispatch, getState ,args} = values;
    const state = getState();
    let mailbags = [];
    let mailbagslist =[];
    let addMailbags=[];
// let mailbagsflag=[];
    let deletedMails=[];
    let time=state.containerReducer.time?state.containerReducer.time:'';
    let date=state.containerReducer.date?state.containerReducer.date:'';
    mailbagslist = state.handsontableReducer.addmailbagExcel ?
    state.handsontableReducer.addmailbagExcel.insertedRows : null;
    let mailIdWeight="";
    let dsn="";
    let rsn ="";
    let weightUnit="";
if(mailbagslist.length>0){
    for(let i = 0; i < mailbagslist.length; i++){
        if(mailbagslist[i].mailbagId)
            {
        addMailbags.push({mailbagId:mailbagslist[i].mailbagId})
    } 
            else{
        if(mailbagslist[i].ooe !=undefined&& mailbagslist[i].doe!=undefined && mailbagslist[i].mailCategoryCode!=undefined 
            && mailbagslist[i].mailSubclass!=undefined && mailbagslist[i].year!=undefined && mailbagslist[i].despatchSerialNumber!=undefined
            && mailbagslist[i].receptacleSerialNumber!=undefined &&mailbagslist[i].highestNumberedReceptacle!=undefined && mailbagslist[i].registeredOrInsuredIndicator!=undefined
            && mailbagslist[i].weight!=undefined)
        {
                if(mailbagslist[i].weightUnit===undefined || mailbagslist[i].weightUnit===null)
                {
                    weightUnit=state.commonReducer.defaultWeightUnit;
                }
                else
                {
                    weightUnit=mailbagslist[i].weightUnit;
                }
                 mailIdWeight=populateMailBagIdWeight(mailbagslist[i].weight,weightUnit,dispatch);   
            if(mailbagslist[i].despatchSerialNumber!=undefined)
                 dsn= populateDsn(mailbagslist[i].despatchSerialNumber);
            if(mailbagslist[i].receptacleSerialNumber!=undefined)
                 rsn= populateRsn(mailbagslist[i].receptacleSerialNumber);
            let mailbagId =  mailbagslist[i].ooe + mailbagslist[i].doe + mailbagslist[i].mailCategoryCode + mailbagslist[i].mailSubclass + mailbagslist[i].year + dsn + rsn + mailbagslist[i].highestNumberedReceptacle + mailbagslist[i].registeredOrInsuredIndicator + mailIdWeight;
            mailbagslist[i].mailbagId=mailbagId;     
            addMailbags.push(mailbagslist[i]);
        }
    }
    } 
    let mailinboundDetails=state.listFlightreducer?(state.listFlightreducer.flightDetails):{};
    let showWarning='Y';
            if(args&&args.showWarning){
                showWarning= args.showWarning;
            }
       const data={addMailbags,addMailbagMode:'EXCEL_VIEW',mailinboundDetails,showWarning};
        const url = 'rest/mail/operations/mailinbound/populatemailbag';
           return makeRequest({
               url,
               data: {...data}
           }).then(function (response) {
               if(!isEmpty(response.errors)){
                   dispatch( {type:constant.ERROR_SHOW});
                   return response;
                }
                let mailbags= response.results[0].addMailbags
                for(let i = 0; i < mailbags.length; i++){
                  if(mailbags[i].mailbagId.length == 12) {
                   let mailWeight=0;
                   mailWeight=populateMailWeightDomestic(mailbags[i].mailbagId.substring(10,12),state.commonReducer.defaultWeightUnit,dispatch);
                   let mailVolume=0;
                   mailVolume=populateMailVolume(mailWeight,values,mailVolume,state.commonReducer.defaultWeightUnit);
                   mailbags[i].scannedDate=date;
                   mailbags[i].scannedTime=time;
                   mailbags[i].weight=mailWeight;
                   mailbags[i].volume=mailVolume;
                   mailbags[i].weightUnit="K";
                   mailbags[i].operationFlag='I';

                      
                  }
                  else if(mailbags[i].mailbagId.length == 29) {
                    mailbags[i].scannedDate=date;
                    mailbags[i].scannedTime=time;
                    let mailVolume=0;
                    let mailWeight1 = populateMailWeight(mailbags[i].mailbagId.substring(25, 29), state.commonReducer.defaultWeightUnit, dispatch);
                    mailVolume=populateMailVolume(mailWeight1,values,mailVolume,state.commonReducer.defaultWeightUnit);
                    mailbags[i].weight=mailWeight1;
                    mailbags[i].volume=mailVolume;
                    mailbags[i].weightUnit=state.commonReducer.defaultWeightUnit;
                    mailbags[i].operationFlag='I';
                  
                  }
                } 
              dispatch({type: 'UPDATE_EXCEL_MAILBAGS',mailbags});
              return response;
            })
            .catch(error => {
                return error;
            });
}
   
}
export const onSaveExcelmailbagDetails = (values) => {

    const { dispatch, getState } = values;
    const state = getState();
   
    let containerDetail=state.containerReducer.containerDetail?
                                        (state.containerReducer.containerDetail):{};
    let mailinboundDetails=state.listFlightreducer?(state.listFlightreducer.flightDetails):{};

    let mailbags = state.mailbagReducer.excelMailbags;
    const data={containerDetail,mailinboundDetails,addMailbags:mailbags};
    let mailbagslist =[];
    let mails=[];
        // let mailbagsflag=[];
    let deletedMails=[];
    const url = 'rest/mail/operations/mailinbound/addMailbags';
    return makeRequest({
        url,
        data: { ...data }
    }).then(function (response) {
        //handleResponseonAdding(dispatch, response,'ADD_MAILBAG_SUCCESS');
        if(response.errors && !isEmpty(response.errors.INFO)){
            state.handsontableReducer && state.handsontableReducer.addmailbagExcel ?
                    state.handsontableReducer.addmailbagExcel.insertedRows=[] : '';
            state.handsontableReducer&& state.handsontableReducer.addmailbagExcel ?
                    state.handsontableReducer.addmailbagExcel.data=[] : '';
        }
        return response
    })
        .catch(error => {
            return error;
        });
}


function populateMailVolume(wgt,values,mailVolume,dispalyUnit){
        const state = values.getState();
        let density = parseFloat(state.commonReducer.density?state.commonReducer.density:0,10);
        let volume=mailVolume ;
        let weight=wgt;
        let  stationVolUnt=state.commonReducer.stationVolUnt;
        let displayUnit=dispalyUnit;
        if (density == ''){
            volume =  0.01;
        }
        var weightInKg=0;
        var convertedVolume=0;
        if(displayUnit=='H'){
        weightInKg=weight/10;
       }
       else if(displayUnit=='L'){
         weightInKg=weight*0.45359;
       }
       else{
         weightInKg=weight;
       }
       convertedVolume=weightInKg/density;
       if (stationVolUnt=='F'){
          volume=convertedVolume*35.31466688252347;
       }
        else if (stationVolUnt=='I'){
            volume=convertedVolume*61023.743837;
        }
        else if (stationVolUnt=='Y'){
         volume=convertedVolume*1.307950613786;
     }
     else if (stationVolUnt=='C'){
         volume=convertedVolume*1000000;
     }
     else {
         volume=convertedVolume;
     }
     volume = Math.round(volume*100)/100;
       

        return volume;
	
}
/**
 * 
 * @param {*} weight 
 * @param {*} unit 
 * Added by A-8164 for converting the weight entered
 * in the iMeasure text field to the Hg and is appended
 * along with mailbagid
 */
function populateMailWeight(weight,unit){
    let weightResult="";
     if(unit=='H'){
        weightResult=weight;
        weightResult= Math.round(weightResult);
     }   
     else if(unit=='K'){
        weightResult=weight/10;
        weightResult= Math.round(weightResult * 10)/ 10;
     }
     else if(unit=='L'){
         weightResult=weight/4.5392;
         weightResult= Math.round(weightResult);
     }
     weightResult=populateWeight(String(weightResult));
     return weightResult;
}
function populateMailWeightDomestic(weight, unit){
    let weightResult ="";
    if (unit == 'H') {
    weightResult = weight * 4.5392;
    }
    else if (unit == 'K'){
    weightResult = weight * 0.45359;
    }
    else if (unit == 'L'){
    weightResult = weight;
    }
    weightResult = Math.round(weightResult);
    //weightResult = populateWeight(String(weightResult));
    return weightResult;
    } 
function populateMailBagIdWeight(weight,unit){
    let weightResult="";
     if(unit=='H'){
        weightResult=weight;
     }   
     else if(unit=='K'){
        weightResult=weight*10;
     }
     else if(unit=='L'){
         weightResult=weight*4.5392;
     }
     weightResult= Math.round(weightResult);
     weightResult=populateWeight(String(weightResult));
     return weightResult;
}
function populateWeight(weight){
    if(weight.length == 1){
        weight = "000"+weight;
    }
    if(weight.length == 2){
                weight = "00"+weight;
    }
    if(weight.length == 3){
                weight = "0"+weight;
    }
    return weight;
}
function populateDsn(dsn){
    if(dsn.length == 1){
        dsn = "000"+dsn;
    }
    if(dsn.length == 2){
        dsn = "00"+dsn;
    }
    if(dsn.length == 3){
        dsn = "0"+dsn;
    }
    return dsn;
}
function populateRsn(rsn){
    if(rsn.length == 1){
        rsn = "00"+rsn;
    }
    if(rsn.length == 2){
        rsn = "0"+rsn;
    }

    return rsn;
}

export function populateMailbagId(values) {
    const { dispatch, getState,args } = values;
    const state = getState();
    const index= args.rowIndex;
   // let mailbagId="";
    const tab=state.mailbagReducer.activeMailbagAddTab;
    let mailbags = [];
    let mailbagId="";
    let mailbagslist =[]
    // let mailbagsflag=[]
    // let deletedMails=[]
    let updatedMailbags=[]
    let mailbag={}
    let time=state.containerReducer.time?state.containerReducer.time:'';
    let date=state.containerReducer.date?state.containerReducer.date:'';
    if(tab===constant.EXCEL_VIEW) {
    mailbagslist = state.handsontableReducer.addmailbagFilter ? state.handsontableReducer.addmailbagFilter.data : null;
    mailbags= mailbagslist.filter( (obj)  => { return obj.ooe !=null});    
   }
    else {
        const active=state.form.newMailbagsTable.active?state.form.newMailbagsTable.active.split('.')[2]:args.active?args.active.split('.')[2]:null;
        
        mailbagslist=state.form.newMailbagsTable?state.form.newMailbagsTable.values.newMailbagsTable :null
        let selectedMailbagRow=mailbagslist[index];

     
        if(active==="weight" || active===null ){
            // let mailWeight=populateMailBagIdWeight(selectedMailbagRow.weight.roundedDisplayValue,selectedMailbagRow.weight.displayUnit,dispatch);
            /* state.form.newMailbagsTable.values.newMailbagsTable[index].weight=mailWeight;
             state.form.newMailbagsTable.values.newMailbagsTable[index].volume=populateMailVolume(mailWeight,values);*/
             //dispatch(change(constant.NEW_MAILBAG_TABLE, `newMailbagsTable.${index}.weight`   , {displayValue :selectedMailbagRow.weight.roundedDisplayValue, roundedDisplayValue: selectedMailbagRow.weight.roundedDisplayValue,displayUnit:selectedMailbagRow.weight.displayUnit} ));
             let mailVolume=0;
              mailVolume=populateMailVolume(selectedMailbagRow.weight.roundedDisplayValue,values,mailVolume,selectedMailbagRow.weight.displayUnit);
             dispatch(change(constant.NEW_MAILBAG_TABLE, `newMailbagsTable.${index}.volume`   , mailVolume ));
        }
        if(selectedMailbagRow.mailbagId&&(selectedMailbagRow.__opFlag?true:false)) {
          if (selectedMailbagRow.mailbagId.length === 29&&active==="mailbagId") {
            /*state.form.newMailbagsTable.values.newMailbagsTable[index].ooe=selectedMailbagRow.mailbagId.substring(0, 6);
            state.form.newMailbagsTable.values.newMailbagsTable[index].doe=selectedMailbagRow.mailbagId.substring(6, 12);
            state.form.newMailbagsTable.values.newMailbagsTable[index].mailCategoryCode=selectedMailbagRow.mailbagId.substring(12, 13);
            state.form.newMailbagsTable.values.newMailbagsTable[index].mailSubclass=selectedMailbagRow.mailbagId.substring(13, 15);
            state.form.newMailbagsTable.values.newMailbagsTable[index].year=selectedMailbagRow.mailbagId.substring(15, 16);
            state.form.newMailbagsTable.values.newMailbagsTable[index].despatchSerialNumber=selectedMailbagRow.mailbagId.substring(16, 20);
            state.form.newMailbagsTable.values.newMailbagsTable[index].receptacleSerialNumber=selectedMailbagRow.mailbagId.substring(20, 23);
            state.form.newMailbagsTable.values.newMailbagsTable[index].highestNumberedReceptacle=selectedMailbagRow.mailbagId.substring(23, 24);
            state.form.newMailbagsTable.values.newMailbagsTable[index].registeredOrInsuredIndicator=selectedMailbagRow.mailbagId.substring(24, 25);*/
            const mailId=selectedMailbagRow.mailbagId;
             let originDest=[];
             const  data={mailId};
             const url='rest/mail/operations/consignment/fetcharpcodes';
                return makeRequest({
                    url, data: { ...data }
                       }).then(function(response)  {
            if(response&&response.errors&&!isEmpty(response.errors)){
                            dispatch( {type:constant.ERROR_SHOW});
                            return response;
            }
            if( response&&response.results&&response.results[0].orgDestAirCodes){
                originDest=response.results[0].orgDestAirCodes;
            dispatch(change(constant.NEW_MAILBAG_TABLE, `newMailbagsTable.${index}.ooe`   , selectedMailbagRow.mailbagId.substring(0, 6) ));
            dispatch(change(constant.NEW_MAILBAG_TABLE, `newMailbagsTable.${index}.doe`   , selectedMailbagRow.mailbagId.substring(6, 12)));
            dispatch(change(constant.NEW_MAILBAG_TABLE, `newMailbagsTable.${index}.mailCategoryCode`   ,selectedMailbagRow.mailbagId.substring(12, 13) ));
            dispatch(change(constant.NEW_MAILBAG_TABLE, `newMailbagsTable.${index}.mailSubclass`   , selectedMailbagRow.mailbagId.substring(13, 15) ));
            dispatch(change(constant.NEW_MAILBAG_TABLE, `newMailbagsTable.${index}.year`   , selectedMailbagRow.mailbagId.substring(15, 16) ));
            dispatch(change(constant.NEW_MAILBAG_TABLE, `newMailbagsTable.${index}.despatchSerialNumber`   ,selectedMailbagRow.mailbagId.substring(16, 20) ));
            dispatch(change(constant.NEW_MAILBAG_TABLE, `newMailbagsTable.${index}.receptacleSerialNumber`   , selectedMailbagRow.mailbagId.substring(20, 23) ));
            dispatch(change(constant.NEW_MAILBAG_TABLE, `newMailbagsTable.${index}.highestNumberedReceptacle`   , selectedMailbagRow.mailbagId.substring(23, 24) ));
            dispatch(change(constant.NEW_MAILBAG_TABLE, `newMailbagsTable.${index}.registeredOrInsuredIndicator`   , selectedMailbagRow.mailbagId.substring(24, 25) ));
            let mailWeight1=0;
            mailWeight1=populateMailWeight(selectedMailbagRow.mailbagId.substring(25, 29),state.form.newMailbagsTable.values.newMailbagsTable[index].weight.displayUnit,dispatch);
            dispatch(change(constant.NEW_MAILBAG_TABLE,`newMailbagsTable.${index}.weight`  ,  {displayValue :mailWeight1,roundedDisplayValue: mailWeight1,displayUnit:state.form.newMailbagsTable.values.newMailbagsTable[index].weight.displayUnit, disabled: false }));
            //state.form.newMailbagsTable.values.newMailbagsTable[index].weight=selectedMailbagRow.mailbagId.substring(25, 29);
            /*state.form.newMailbagsTable.values.newMailbagsTable[index].volume=populateMailVolume(selectedMailbagRow.mailbagId.substring(25, 29),values);
            state.form.newMailbagsTable.values.newMailbagsTable[index].scannedDate=date;
            state.form.newMailbagsTable.values.newMailbagsTable[index].scannedTime=time;*/
            let mailVolume=0;
            mailVolume=populateMailVolume(mailWeight1,values,selectedMailbagRow.volume,state.form.newMailbagsTable.values.newMailbagsTable[index].weight.displayUnit);
            dispatch(change(constant.NEW_MAILBAG_TABLE, `newMailbagsTable.${index}.volume`,mailVolume));
            dispatch(change(constant.NEW_MAILBAG_TABLE, `newMailbagsTable.${index}.scannedDate`   , date ));
            dispatch(change(constant.NEW_MAILBAG_TABLE, `newMailbagsTable.${index}.scannedTime`   , time ));
            if (isSubGroupEnabled('TURKISH_SPECIFIC') ) { dispatch(change(constant.NEW_MAILBAG_TABLE, `newMailbagsTable.${index}.mailCompanyCode`, selectedMailbagRow.mailCompanyCode));}
     
            dispatch(change(constant.NEW_MAILBAG_TABLE, `newMailbagsTable.${index}.mailOrigin`,originDest[0]));
            dispatch(change(constant.NEW_MAILBAG_TABLE, `newMailbagsTable.${index}.mailDestination`,originDest[1]));
            return response;
            }
            })
            .catch(error => {
            return error;

            });
            }
         else if(selectedMailbagRow.mailbagId.length === 12&&active==="mailbagId"){
            const mailbagId = selectedMailbagRow.mailbagId;
             mailbags = {mailbagId:mailbagId};
            const addMailbags =[];
            addMailbags.push(mailbags);
            let showWarning='Y';
            if(args.showWarning){
                showWarning= args.showWarning;
            }
            let mailinboundDetails=state.listFlightreducer?(state.listFlightreducer.flightDetails):{};
            const data={addMailbags,addMailbagMode:'NORMAL_VIEW',showWarning,mailinboundDetails};
             const url = 'rest/mail/operations/mailinbound/populatemailbag';
                return makeRequest({
                    url,
                    data: {...data}
                }).then(function (response) {
                    if(!isEmpty(response.errors)){
                        dispatch( {type:constant.ERROR_SHOW});
                        return response;
                    }
                    mailbag=response.results[0]&&response.results[0].addMailbags&&response.results[0].addMailbags[0]?
                               response.results[0].addMailbags[0]:{};
                    if(!isEmpty(mailbag)){
                        /*state.form.newMailbagsTable.values.newMailbagsTable[index].ooe=mailbag.ooe;
                        state.form.newMailbagsTable.values.newMailbagsTable[index].doe=mailbag.doe;
                        state.form.newMailbagsTable.values.newMailbagsTable[index].mailCategoryCode=mailbag.mailCategoryCode;
                        state.form.newMailbagsTable.values.newMailbagsTable[index].mailSubclass=mailbag.mailSubclass;
                        state.form.newMailbagsTable.values.newMailbagsTable[index].year=mailbag.year;
                        state.form.newMailbagsTable.values.newMailbagsTable[index].despatchSerialNumber=mailbag.despatchSerialNumber;
                        state.form.newMailbagsTable.values.newMailbagsTable[index].receptacleSerialNumber=mailbag.receptacleSerialNumber;
                        state.form.newMailbagsTable.values.newMailbagsTable[index].highestNumberedReceptacle=mailbag.highestNumberedReceptacle;
                        state.form.newMailbagsTable.values.newMailbagsTable[index].registeredOrInsuredIndicator=mailbag.registeredOrInsuredIndicator;
                        state.form.newMailbagsTable.values.newMailbagsTable[index].weight=mailbag.weight;
                        state.form.newMailbagsTable.values.newMailbagsTable[index].volume=populateMailVolume(mailbag.weight,values);
                        state.form.newMailbagsTable.values.newMailbagsTable[index].scannedDate=date;
                        state.form.newMailbagsTable.values.newMailbagsTable[index].scannedTime=time;*/
                        dispatch(change(constant.NEW_MAILBAG_TABLE, `newMailbagsTable.${index}.ooe`   , mailbag.ooe ));
                        dispatch(change(constant.NEW_MAILBAG_TABLE, `newMailbagsTable.${index}.doe`   , mailbag.doe ));
                        dispatch(change(constant.NEW_MAILBAG_TABLE, `newMailbagsTable.${index}.mailCategoryCode`   , mailbag.mailCategoryCode ));
                        dispatch(change(constant.NEW_MAILBAG_TABLE, `newMailbagsTable.${index}.mailSubclass`   , mailbag.mailSubclass ));
                        dispatch(change(constant.NEW_MAILBAG_TABLE, `newMailbagsTable.${index}.year`   , mailbag.year ));
                        dispatch(change(constant.NEW_MAILBAG_TABLE, `newMailbagsTable.${index}.despatchSerialNumber`   , mailbag.despatchSerialNumber ));
                        dispatch(change(constant.NEW_MAILBAG_TABLE, `newMailbagsTable.${index}.receptacleSerialNumber`   , mailbag.receptacleSerialNumber ));
                        dispatch(change(constant.NEW_MAILBAG_TABLE, `newMailbagsTable.${index}.highestNumberedReceptacle`   , mailbag.highestNumberedReceptacle ));
                        dispatch(change(constant.NEW_MAILBAG_TABLE, `newMailbagsTable.${index}.registeredOrInsuredIndicator`   , mailbag.registeredOrInsuredIndicator ));
                        let mailWeight=0;
                        mailWeight=populateMailWeightDomestic(selectedMailbagRow.mailbagId.substring(10,12),state.form.newMailbagsTable.values.newMailbagsTable[index].weight.displayUnit,dispatch);
                        dispatch(change(constant.NEW_MAILBAG_TABLE,`newMailbagsTable.${index}.weight`  ,  { disabled:true ,displayValue :mailWeight, roundedDisplayValue: mailWeight,displayUnit:'L'}));
                        //dispatch(change(constant.NEW_MAILBAG_TABLE, `newMailbagsTable.${index}.weight`   , mailbag.weight ));
                        //dispatch(change(constant.NEW_MAILBAG_TABLE, `newMailbagsTable.${index}.volume`   , populateMailVolume(mailbag.weight,values) ));
                           let mailVolume=0;
                           mailVolume=populateMailVolume(mailWeight,values,mailVolume,state.form.newMailbagsTable.values.newMailbagsTable[index].weight.displayUnit)
                        dispatch(change(constant.NEW_MAILBAG_TABLE, `newMailbagsTable.${index}.volume`,mailVolume ));
                        dispatch(change(constant.NEW_MAILBAG_TABLE, `newMailbagsTable.${index}.scannedDate`   , date ));
                        dispatch(change(constant.NEW_MAILBAG_TABLE, `newMailbagsTable.${index}.scannedTime`   , time ));
                        dispatch(change(constant.NEW_MAILBAG_TABLE, `newMailbagsTable.${index}.mailOrigin`   , mailbag.mailOrigin ));
                        dispatch(change(constant.NEW_MAILBAG_TABLE, `newMailbagsTable.${index}.mailDestination`   , mailbag.mailDestination ));
                    }
                    return response;
                })
                .catch(error => {
                    return error;
                });
         }
            
        }
   

    //    const length=selectedMailbagRow.mailbagId?selectedMailbagRow.mailbagId.length:'';
       if(active!="mailbagId"   ){
       if(selectedMailbagRow.ooe && selectedMailbagRow.doe  && selectedMailbagRow.mailCategoryCode && selectedMailbagRow.mailSubclass &&selectedMailbagRow.year && selectedMailbagRow.despatchSerialNumber && selectedMailbagRow.receptacleSerialNumber) {
        let mailWeight=populateMailBagIdWeight(selectedMailbagRow.weight.roundedDisplayValue,selectedMailbagRow.weight.displayUnit,dispatch);
        let dsn = populateDsn(selectedMailbagRow.despatchSerialNumber);
        let rsn = populateRsn(selectedMailbagRow.receptacleSerialNumber);   
        mailbagId= mailbagId+selectedMailbagRow.ooe +selectedMailbagRow.doe +selectedMailbagRow.mailCategoryCode +selectedMailbagRow.mailSubclass +selectedMailbagRow.year +dsn +rsn +selectedMailbagRow.highestNumberedReceptacle +selectedMailbagRow.registeredOrInsuredIndicator + mailWeight;
            //state.form.newMailbagsTable.values.newMailbagsTable[index].mailbagId=mailbagId;
            dispatch(change(constant.NEW_MAILBAG_TABLE, `newMailbagsTable.${index}.mailbagId`   , mailbagId ));
       }
    }
   /* if(length !=29 && length!=12 && length!=0){
                let error = "InValid MailbagID"
                dispatch(requestValidationError(error, ''));
                return Promise.resolve("Error");   

       }*/
       

            updatedMailbags = state.form.newMailbagsTable.values.newMailbagsTable;
            dispatch({ type: constant.UPDATE_MAILBAGS_IN_ADD_POPUP, updatedMailbags});
   
      }
        updatedMailbags = state.form.newMailbagsTable.values.newMailbagsTable;
            dispatch({ type: constant.UPDATE_MAILBAGS_IN_ADD_POPUP, updatedMailbags});
			

        // COMMON LOGIC to enable the weight drop down whenever required should be put inside some condition based on some active field //
        /*
        dispatch(
                change(
                constant.NEW_MAILBAG_TABLE,
                `newMailbagsTable.${index}.weight`  ,  { disabled:false }
                )
        );*/
			
			
 }

 export function mailAllSelect(values){
    const { args, dispatch, getState } = values;
    const state=getState();
    const checked=args.checked;
    let mailIndexArray=[];
    let dsnIndexArray=[];
    let i=0;
    const activeMailbagTab=state.containerReducer?state.containerReducer.activeMailbagTab:null;
     if(activeMailbagTab===constant.MAIL_VIEW){
        let mailbagDetailsCollectionPage=state.mailbagReducer?(state.mailbagReducer.mailbagData):{};
        let mailbagDetailsCollection=mailbagDetailsCollectionPage.results?mailbagDetailsCollectionPage.results:{};
        mailbagDetailsCollection.forEach(function(element) {
            element.checkBoxSelect=checked;
            mailIndexArray.push(i);
            i++;
    }, this);
    mailbagDetailsCollectionPage={...mailbagDetailsCollectionPage,results:mailbagDetailsCollection};
    const indexDetails={checked:checked,mailIndexArray:mailIndexArray,dsnIndexArray:dsnIndexArray};
        dispatch({
            type: constant.SELECT_MAIL_ALL, mailDetails: mailbagDetailsCollectionPage, indexDetails: indexDetails,
                });
    }
    else{
        let dsnDetailsCollectionPage=state.mailbagReducer?(state.mailbagReducer.dsnData):{};
        let  dsnDetailsCollection=dsnDetailsCollectionPage.results?dsnDetailsCollectionPage.results:{};
        dsnDetailsCollection.forEach(function(element) {
            element.checkBoxSelect=checked;
            dsnIndexArray.push(i);
            i++;
        }, this);
    dsnDetailsCollectionPage={...dsnDetailsCollectionPage,results:dsnDetailsCollection};
    const indexDetails={checked:checked,mailIndexArray:mailIndexArray,dsnIndexArray:dsnIndexArray};
        dispatch({
            type: constant.SELECT_DSN_ALL, indexDetails: indexDetails,
            dsnDetails: dsnDetailsCollectionPage
        });
    }
       
}

export function saveNewContainer(values) {

    const { args, dispatch, getState } = values;
    const state = getState();
    const {  action, fromScreen } = args;
    let mailacceptance=state.mailbagReducer?(state.mailbagReducer.flightDetailsToBeReused):{};
    if(fromScreen === 'TRANSFER'){
        mailacceptance = mailacceptance
    }else{
        mailacceptance.flightDate = mailacceptance.flightDate.split(" ")[0];
        mailacceptance.flightSequenceNumber = mailacceptance.flightSeqNumber;
        mailacceptance.flightNumber = mailacceptance.flightNo;
    }

    const flightValidation = {
        companyCode: mailacceptance.companyCode, carrierCode: mailacceptance.carrierCode,
                                flightCarrierId:mailacceptance.carrierId,flightNumber:mailacceptance.flightNumber,
                                flightSequenceNumber:mailacceptance.flightSequenceNumber,legSerialNumber:mailacceptance.legSerialNumber,flightRoute:mailacceptance.flightRoute,
                                flightDate:mailacceptance.flightDate,aircraftType:mailacceptance.aircraftType,flightType:mailacceptance.flightType,
                                operationalStatus:mailacceptance.flightOperationalStatus,flightStatus:mailacceptance.flightStatus,
        applicableDateAtRequestedAirport: mailacceptance.flightDate
    };
    let selectedContainer= {} //(state.listmailbagsreducer.selectedContainer) ? state.listmailbagsreducer.selectedContainer : {}
     let url = '';
    let flightCarrierCode = '';
    let flightNumber = '';
    let assignToFlight = '';
    let flightDate = '';
    let destination = '';
    let carrierCode = '';
    let scanDate = '';
    let scanTime = '';   
    let addContainer ={};
    let mailinboundDetails ={} 
    let data;
if(fromScreen === 'TRANSFER'){
    scanDate = state.form.transferMailForm.values.scanDate?state.form.transferMailForm.values.scanDate:'';
    scanTime = state.form.transferMailForm.values.mailScanTime?state.form.transferMailForm.values.mailScanTime:'';

        scanDate = state.form.transferMailForm.values.scanDate?state.form.transferMailForm.values.scanDate:'';
        scanTime = state.form.transferMailForm.values.mailScanTime?state.form.transferMailForm.values.mailScanTime:'';
        carrierCode = state.form.transferMailForm.values.flightnumber?state.form.transferMailForm.values.flightnumber.carrierCode:'';
        flightCarrierCode = state.form.transferMailForm.values.flightnumber?state.form.transferMailForm.values.flightnumber.carrierCode:'';
        flightNumber = state.form.transferMailForm.values.flightnumber?state.form.transferMailForm.values.flightnumber.flightNumber:'';
        assignToFlight =  'FLIGHT' ;
        flightDate = state.form.transferMailForm.values.flightnumber?state.form.transferMailForm.values.flightnumber.flightDate:'';
        destination = state.form.transferMailForm.values.uldDestination?state.form.transferMailForm.values.uldDestination:'';
    
    let transferFilterType =state.form.transferMailForm.values.transferFilterType?state.form.transferMailForm.values.transferFilterType:'C';
    if(transferFilterType==='F'){
    carrierCode = state.form.transferMailForm.values.flightnumber?state.form.transferMailForm.values.flightnumber.carrierCode:'';
    flightCarrierCode = state.form.transferMailForm.values.flightnumber?state.form.transferMailForm.values.flightnumber.carrierCode:'';
    flightNumber = state.form.transferMailForm.values.flightnumber?state.form.transferMailForm.values.flightnumber.flightNumber:'';
    assignToFlight =  'FLIGHT' ;
    flightDate = state.form.transferMailForm.values.flightnumber?state.form.transferMailForm.values.flightnumber.flightDate:'';
    destination = state.form.transferMailForm.values.uldDestination?state.form.transferMailForm.values.uldDestination:'';
    }
    if(transferFilterType==='C'){
        carrierCode = state.form.transferMailForm.values.carrier?state.form.transferMailForm.values.carrier:'';
        flightDate ='';
        flightNumber ='';
        assignToFlight =  'CARRIER' ;
        destination =state.form.transferMailForm.values.destination?state.form.transferMailForm.values.destination:'';
    }
    if(!destination || destination===''){
        return Promise.reject(new Error("Please enter destination")); 
    }

    const pou = state.form.transferMailForm.values.pou?state.form.transferMailForm.values.pou:destination;
   
        selectedContainer = {
            containerNumber: state.form.transferMailForm.values.uldNumber ? state.form.transferMailForm.values.uldNumber : '',
                        type : state.form.transferMailForm.values.barrowFlag===true?'B':'U' ,
                        remarks : state.form.transferMailForm.values.uldRemarks?state.form.transferMailForm.values.uldRemarks:'',
                        pou : pou,
                        uldDestination : state.form.transferMailForm.values.uldDestination?state.form.transferMailForm.values.uldDestination:'',
                        paBuiltFlag : state.form.transferMailForm.values.paBuilt===true?'Y':'N',
                        finalDestination : state.form.transferMailForm.values.uldDestination?state.form.transferMailForm.values.uldDestination:'',
                        operationFlag : 'I',flightNumber : flightNumber,carrierCode : carrierCode,
                        assignedPort:state.filterReducer.filterVlues.port?state.filterReducer.filterVlues.port:'',
            destination: state.form.transferMailForm.values.uldDestination ? state.form.transferMailForm.values.uldDestination : '',
        }
         
   if(selectedContainer.type === 'B'){
    if(selectedContainer.pou !==  selectedContainer.finalDestination){
        return Promise.reject(new Error("POU and Destination should be same for barrow"))
        }
    }

    if(!selectedContainer.finalDestination || selectedContainer.finalDestination===''){
        return Promise.reject(new Error("Please enter destination"))   
    }

    let oneTimeValues = state.commonReducer.oneTimeValues;
   
    let showWarning='Y';
    if(args.showWarning){
        showWarning= args.showWarning;
    }
   
    data = {showWarning, scanDate, scanTime, flightValidation, oneTimeValues, carrierCode,flightCarrierCode, flightNumber, assignToFlight, flightDate, destination, selectedContainer };
    url = 'rest/mail/operations/mailbagenquiry/saveContainerDetailsForEnquiry';

   }

   if(fromScreen === 'CHANGE_FLIGHT'){
    addContainer={
    containerNo:state.form.ChangeFlightMailDetails.values.uldNumber?state.form.ChangeFlightMailDetails.values.uldNumber:'',
    barrow : state.form.ChangeFlightMailDetails.values.barrowFlag===true?true:false ,
    remarks : state.form.ChangeFlightMailDetails.values.uldRemarks?state.form.ChangeFlightMailDetails.values.uldRemarks:'',
    desination : state.form.ChangeFlightMailDetails.values.uldDestination?state.form.ChangeFlightMailDetails.values.uldDestination:'',
    paBuilt: state.form.ChangeFlightMailDetails.values.paBuilt===true?true:false,
    pol:state.mailbagReducer&&state.mailbagReducer.flightDetailsToBeReused?state.mailbagReducer.flightDetailsToBeReused.legOrigin:'',
    }
    mailinboundDetails = state.mailbagReducer?state.mailbagReducer.flightDetailsToBeReused:{}

    let oneTimeValues = state.commonReducer.oneTimeValues;
   
    let showWarning='Y';
    if(args.showWarning){
        showWarning= args.showWarning;
    }
    data={mailinboundDetails,addContainer ,showWarning,oneTimeValues }   
    url = 'rest/mail/operations/mailinbound/addContainer';
   }
   
    return makeRequest({
        url,
        data: { ...data }
    }).then(function (response) {
        if(fromScreen === 'TRANSFER'){
            handleResponse(dispatch, response, 'SAVE_CONTAINER');
        }else{
            handleResponse(dispatch, response, 'SAVE_CONTAINER_CHANGE_FLIGHT');
        }
        return response
    })
        .catch(error => {
            return error;
        });


}



export function validateMailbagDelivery(values){

  const { args, dispatch, getState } = values;
    const state=getState();
 
    const selectedIndexes=args?args.selectedIndexes:null;

    const mailBagDetailsCollection=[];
    let masterDocumentFlag="Y";
  
    let validateDeliveryMailBagFlag=false;
   
    const mailbagDetailsCollectionFromStore=(state.mailbagReducer.filterFlag)?(state.mailbagReducer.mailDataAfterFilter?
                        state.mailbagReducer.mailDataAfterFilter.results:{}):
                            state.mailbagReducer?(state.mailbagReducer.mailbagData.results):{}; 
  
        selectedIndexes.forEach(function(element) {
            mailBagDetailsCollection.push(mailbagDetailsCollectionFromStore[element]);
      
        }, this);

        for(var i=0;i<selectedIndexes.length;i++){
            if( state.mailbagReducer.mailbagData.results[selectedIndexes[i]].awb == null){
                           masterDocumentFlag="N";
                           break;
                          }
        }
         for( var i=0;i<mailBagDetailsCollection.length;i++){
         if(mailBagDetailsCollection[i].mailBagStatus==="Delivered"){
validateDeliveryMailBagFlag=true;
          
         }else{
validateDeliveryMailBagFlag=false;
break;
         }

     };
 

    dispatch({type: constant.VALIDATE_MAILBAG,validateDeliveryMailBagFlag,masterDocumentFlag});
           
}


export function performMailbagScreenAction(values) {
    const { args, dispatch, getState } = values;
    const state=getState();
    const activeMailbagTab=state.containerReducer?state.containerReducer.activeMailbagTab:null;
    const actionType=args?args.actionName:null;
    const indexArray=args?args.indexArray:null;
    const containerDetailsCollection=[];
    const mailBagDetailsCollection=[];
    const dsnDetailsCollection=[];
    /*const dsnVos=[];
    let containerDetailsVo=[];
    const containerDetailsVos=(state.containerReducer)?state.containerReducer.containerDetailsInVo:{};*/
    let mailinboundDetails=state.listFlightreducer?(state.listFlightreducer.flightDetails):{};
    let containerDetail=state.containerReducer.containerDetail?
                                        (state.containerReducer.containerDetail):{};
    const mailbagDetailsCollectionFromStore=(state.mailbagReducer.filterFlag)?(state.mailbagReducer.mailDataAfterFilter?
                        state.mailbagReducer.mailDataAfterFilter.results:{}):
                            state.mailbagReducer?(state.mailbagReducer.mailbagData.results):{};
    const dsnDetailsCollectionFromStore=(state.mailbagReducer.dsnFilterFlag)?(state.mailbagReducer.dsnDataAfterFilter?
                        state.mailbagReducer.dsnDataAfterFilter.results:{}):
                            state.mailbagReducer?(state.mailbagReducer.dsnData.results):{};
    let screenWarning=false;
    if(args&&args.screenWarning){
    screenWarning= args.screenWarning;
    }
    if(activeMailbagTab===constant.DSN_VIEW){
        indexArray.forEach(function(element) {
            dsnDetailsCollection.push(dsnDetailsCollectionFromStore[element]);
            }, this);
    
    }
    if(activeMailbagTab===constant.MAIL_VIEW){
        indexArray.forEach(function(element) {
            mailBagDetailsCollection.push(mailbagDetailsCollectionFromStore[element]);
      
        }, this);

    
    }

    containerDetail = {
        ...containerDetail, dsnDetailsCollection: dsnDetailsCollection,
        mailBagDetailsCollection: mailBagDetailsCollection
    };
    containerDetailsCollection.push(containerDetail);



    if (actionType === constant.CHANGE_FLIGHT) {

		 const containerDetailsInPopUp = state.containerReducer ? (state.containerReducer.containerVosToBeReused ? state.containerReducer.containerVosToBeReused.results : {}) : {};
  
        
        const index=args.popUpIndex;
        const containerDetailFromPopUp=containerDetailsInPopUp[index];
		if (containerDetailFromPopUp == null || containerDetailFromPopUp.containerno == null) {
      return Promise.reject(new Error("Please Select container"));
    }
        const flightDetail=(state.mailbagReducer)?state.mailbagReducer.flightDetailsToBeReused:{};
        let changeFlightDetails=state.form.ChangeFlightMailDetails.values?state.form.ChangeFlightMailDetails.values:{};
        changeFlightDetails = {
            ...changeFlightDetails, flightNumber: changeFlightDetails.flightNumber,
                                flightCarrierCode:changeFlightDetails.flightCarrierCode,
            date: changeFlightDetails.date, containerDetail: containerDetailFromPopUp, flightDetail: flightDetail
        }
        const data={changeContainerDetails:changeFlightDetails,containerDetail:containerDetail,mailinboundDetails:mailinboundDetails};
        const url = 'rest/mail/operations/mailinbound/savechangemail';
                return makeRequest({
                    url,
                    data: {...data}
                }).then(function (response) {
                    handleResponse(dispatch, response, 'CHANGE_FLIGHT_SAVE')
                    return response;
                })
                .catch(error => {
                    return error;
                });

    } else if (actionType === constant.UNDO_ARRIVAL) {
         const data={containerDetailsCollection,operationLevel:'dsn',mailinboundDetails:mailinboundDetails};
          const url = 'rest/mail/operations/mailinbound/undoarrival';
                return makeRequest({
                    url,
                    data: {...data}
                }).then(function (response) {
                    handleResponse(dispatch, response, actionType)
                    return response;
                })
                .catch(error => {
                    return error;
                });

    } else if (actionType === constant.ATTACH_ROUTING) {
        // close flights service call+ state obj 
    } else if (actionType=== constant.ARRIVE_MAIL) {

        const popupAction=state.commonReducer.popupAction;
        const data={containerDetailsCollection,mailinboundDetails:mailinboundDetails,popupAction,containerDetail,transactionLevel:'M',screenWarning};
        const url = 'rest/mail/operations/mailinbound/arriveMail';
                return makeRequest({
                    url,
                    data: {...data}
                }).then(function (response) {
                    if(response.status==='embargo') {
                        invokeEmbargoPopup(dispatch); 
                        dispatch({type:constant.EMBARGO_CONTINUE_ACTION,actionType,indexArray});
                    }else if(response.status ==='success'){
                            dispatch({type:constant.EMBARGO_WARNING_POPUP_HANDLING})
                    }else{
                    handleResponse(dispatch, response, actionType)
                    }
                    return response;
                })
                .catch(error => {
                    return error;
                });

    } else if (actionType=== constant.DELIVER_MAIL) {

        const deliverMailDetails=(state.form.deliverMailDetails.values)?state.form.deliverMailDetails.values:{};
        const data = {
            containerDetailsCollection, deliverMailDetails, operationLevel: 'dsn',
            mailinboundDetails: mailinboundDetails,screenWarning
        }
        const url = 'rest/mail/operations/mailinbound/delivermail';
                return makeRequest({
                    url,
                    data: {...data}
                }).then(function (response) {
                    handleResponse(dispatch, response, actionType)
                    return response;
                })
                .catch(error => {
                    return error;
                }); 
    } else if (actionType === constant.TRANSFER) {
        let transferMailForm=(state.form.transferMailForm.values)?state.form.transferMailForm.values:{};
        if(transferMailForm.transferFilterType&&transferMailForm.transferFilterType=='F'){
            const containerDetailsInPopUp=(state.containerReducer && state.containerReducer.containerVosToBeReused)?state.containerReducer.containerVosToBeReused.results:{};
            transferMailForm=(state.form.transferMailForm.values)?state.form.transferMailForm.values:{};
            const flightDetailSaved=(state.mailbagReducer)?state.mailbagReducer.flightDetailsToBeReused:{};
            const flightDetail = {
                port: flightDetailSaved.upliftAirport,
                                flightNo:flightDetailSaved.flightNumber,
                                carrierCode:flightDetailSaved.carrierCode,
                                carrierId:flightDetailSaved.carrierId,
                                flightDate:flightDetailSaved.flightDate,
                                legSerialNumber:flightDetailSaved.legSerialNumber,
                                flightSeqNumber:flightDetailSaved.flightSequenceNumber,
                                flightRoute:flightDetailSaved.flightRoute,
                                flightType:flightDetailSaved.flightType,
                legOrigin: flightDetailSaved.pol
            }
            const index=args.popUpIndex;
            let containerDetailFromPopUp=null;
            if(index !== -1  && index !== undefined  ){
                containerDetailFromPopUp=containerDetailsInPopUp[index];
                }
            if(containerDetailFromPopUp!== null&&containerDetailFromPopUp!== undefined){
            containerDetailFromPopUp.actualWeight=null;
                }
                if(containerDetailFromPopUp!== null&&containerDetailFromPopUp!== undefined && containerDetailFromPopUp.provosionalCharge=='0'){
                    containerDetailFromPopUp.provosionalCharge=null;
                }
            transferMailForm = {
                ...transferMailForm, reassignedto: 'FLIGHT', scanTime: transferMailForm.mailScanTime,
                                flightNumber:transferMailForm.flightNumber,
                                flightCarrierCode:transferMailForm.flightCarrierCode,
                flightDate: transferMailForm.date, containerDetailsVO: {...containerDetailFromPopUp,provosionalCharge : null}, mailinboundDetails: flightDetail
            };
        }
        else{
            const containerDetailsInPopUp=(state.containerReducer && state.containerReducer.containerVosToBeReused)?state.containerReducer.containerVosToBeReused.results:{};
            const index=args.popUpIndex;  
            let containerDetailFromPopUp=null;
            if(index !== -1  && index !== undefined  ){
            containerDetailFromPopUp=containerDetailsInPopUp[index];
            }
            if(containerDetailFromPopUp!== null&&containerDetailFromPopUp!== undefined){
            containerDetailFromPopUp.actualWeight=null;
            }
            if(containerDetailFromPopUp!== null&&containerDetailFromPopUp!== undefined && containerDetailFromPopUp.provosionalCharge=='0'){
                containerDetailFromPopUp.provosionalCharge=null;
            }
          
            transferMailForm = {
                ...transferMailForm, reassignedto: 'CARRIER', scanTime: transferMailForm.mailScanTime, carrier: transferMailForm.carrier,
                destination: transferMailForm.destination, uplift: transferMailForm.uplift, containerDetailsVO: { ...containerDetailFromPopUp, containerType: containerDetailFromPopUp && containerDetailFromPopUp.type ? containerDetailFromPopUp.type : null,provosionalCharge : null }
            }
        }

        if(transferMailForm.reassignedto==='FLIGHT'&&(transferMailForm.containerDetailsVO === undefined||transferMailForm.containerDetailsVO===null)){
            return Promise.reject(new Error("Please select a container")); 
        }else if(transferMailForm.reassignedto==='CARRIER'&&(transferMailForm.carrier === undefined || transferMailForm.carrier  === '')){
                return Promise.reject(new Error("Please enter carrier code")); 
        }else if(transferMailForm.reassignedto==='CARRIER'&& state.mailbagReducer
                 &&state.mailbagReducer.ownAirlineCode&&state.mailbagReducer.ownAirlineCode===transferMailForm.carrier
                &&(transferMailForm.containerDetailsVO.containerType === undefined||transferMailForm.containerDetailsVO.containerType===null)){
                    return Promise.reject(new Error("Please select a container"));   
            }
        
        const popupAction = state.commonReducer.popupAction;
        const data={transferDetails:transferMailForm,containerDetail:containerDetail, mailinboundDetails:mailinboundDetails,popupAction,embargoFlag:'TRA',screenWarning};
        const url = 'rest/mail/operations/mailinbound/transfermail';
                return makeRequest({
                    url,
                    data: {...data}
                }).then(function (response) {
                    if(response.status ==='success'){
                        dispatch({type:constant.CONTINUE_ACTION_AFTER_SAVE_FOR_EMBARGO,
                             embargoInfo:response.results&& response.results[0].embargoInfo?response.results[0].embargoInfo:false})
                        dispatch({type:constant.EMBARGO_WARNING_POPUP_HANDLING})

                    }
                    handleResponse(dispatch, response, 'TRANSFER_SAVE',values)
                    return response;
                })
                .catch(error => {
                    return error;
                }); 



        
    }else if (actionType === constant.READY_FOR_DELIVERY) {
        const readyForDeliveryForm=(state.form.ReadyForDeliveryForm.values)?state.form.ReadyForDeliveryForm.values:{};
        const selectedFlight = state.listFlightreducer.flightDetails
        let data={};
        var toDate=(moment()).format('DD-MMM-YYYY hh:mm:ss');
        var flightDate=selectedFlight.flightDate;
        if(flightDate > toDate) {
            return Promise.reject(new Error( "Cannot mark ready for delivery as flight not arrived" ));
        }
        else {
           
        if(activeMailbagTab===constant.MAIL_VIEW){
            mailBagDetailsCollection.forEach(function(element) {
                element.readyForDeliveryDate=readyForDeliveryForm.date;
                element.readyForDeliveryTime=readyForDeliveryForm.time;
            }, this);
                containerDetail = {
                    ...containerDetail,
                    mailBagDetailsCollection: mailBagDetailsCollection
                };
            data={mailinboundDetails,containerDetail}
        }
        else{
            dsnDetailsCollection.forEach(function(element) {
                element.readyForDeliveryDate=readyForDeliveryForm.date;
                element.readyForDeliveryTime=readyForDeliveryForm.time;
            }, this);
                containerDetail = {
                    ...containerDetail,
                    dsnDetailsCollection: dsnDetailsCollection
                };
            data={containerDetail,mailinboundDetails}
        }
        
        const url = 'rest/mail/operations/mailinbound/readyfordelivery';
                return makeRequest({
                    url,
                    data: {...data}
                }).then(function (response) {
                    handleResponse(dispatch, response, actionType)
                    return response;
                })
                .catch(error => {
                    return error;
                });   
      }
    }  else if (actionType === constant.CHANGE_SCAN_TIME) {
        
        const deliverMailDetails=(state.form.changeScanTimeForm.values)?state.form.changeScanTimeForm.values:{};
        const data={containerDetail,deliverMailDetails,mailinboundDetails}
         const url = 'rest/mail/operations/mailinbound/changescantime';
                return makeRequest({
                    url,
                    data: {...data}
                }).then(function (response) {
                    handleResponse(dispatch, response, actionType)
                     if(response.status==='success'){
                        dispatch({type: constant.CHANGE_SCAN_TIME_CLOSE,showScanTimePanel:false});
                    }
                   /* if(response.errors.INFO!=null)
                    dispatch({type: 'CHANGE_SCAN_TIME_CLOSE',showScanTimePanel:false});*/

                    return response;
                })
                .catch(error => {
                    return error;
                }); 
    } else if (actionType === constant.VIEW_MAIL_HISTORY) {
        let mailBagids='';
        mailBagDetailsCollection.forEach(function(element) {
            mailBagids=mailBagids+'-'+element.mailBagId;
            
        }, this);

           navigateToScreen("mail.operations.ux.mbHistory.list.do",
                                 {  totalViewRecords:mailBagids,mailbagId:mailBagDetailsCollection[0].mailBagId,fromScreenId:'MTK064' });
                                 
    }
    else if (actionType === constant.REMOVE_MAILBAG) {
        const removeMailDetails=(state.form.removeDetails.values)?state.form.removeDetails.values:{};
        let data={};
         data={mailinboundDetails,containerDetail, ...removeMailDetails,removeType:'M' }
        
        const url = 'rest/mail/operations/mailinbound/removemailbag';
                return makeRequest({
                    url,
                    data: {...data}
                }).then(function (response) {
                    handleResponse(dispatch, response, actionType)
                    return response;
                })
                .catch(error => {
                    return error;
                });   
    }  
     else if (actionType === constant.DAMAGE_CAPTURE) {
        const damageDetails=(state.form.damageData.values)?state.form.damageData.values.damageData:{};
        let damagedMailBagCollection=[];
        damageDetails.forEach(function(element) {
            const damagedMailbag = {
                damageCode: element.damageCode, remarks: element.remarks,
                fileName :  element.fileData&&element.fileData.length>0?element.fileData[0].name:null,
            };
            damagedMailBagCollection.push(damagedMailbag);
        }, this);
        const oneTimeValues=(state.mailbagReducer.oneTimeValues)?state.mailbagReducer.oneTimeValues:{};
        const data={containerDetail,damagedMailBagCollection,mailinboundDetails,oneTimeValues};
        const url = 'rest/mail/operations/mailinbound/savedamagecapture';
                return makeRequest({
                    url,
                    data: {...data}
                }).then(function (response) {
            dispatch({ type: constant.DAMAGE_CAPTURE_CLOSE, showDamageClose: true });
                    return response;
                })
                .catch(error => {
                    return error;
                }); 

    }

    dispatch({ type: args.type });
}

function handleResponse(dispatch, response, action,values) {


    if(isEmpty(response.errors) ){
        //const transferManifestVO = response.results[0].transferManifestVO;
        if (action === constant.CON_LIST_ON_CONT_OPRN) {
            const containerVo=response.containerVo;
            const containerDetails=response.results[0].containerDetailsCollection;
            const containersSelected=[];
            containerDetails.forEach(function(element) {
                if(containerVo.containerNumber===element.containerno){
                    containersSelected.push(element);
                }
            }, this);
            dispatch({
                type: constant.UPDATE_MAIL_DETLS, containerDetails: response.results[0].containerDetailsCollection,
                    mailbagData:response.results[0].containerDetailsCollection[0].mailBagDetailsCollection,
                    flightOperationDetails:response.results[0].operationalFlightVO,containerDetailsInVo:response.results[0].containerDetailsVos,
                arrivalDetailsInVo: response.results[0].mailArrivalVO, containerSelected: containersSelected
            });
        }
        if (action === constant.ADD_SUCCESS) { 
            dispatch({type: constant.ADD_SUCCESS,saveClose:true});
        }
        if (action === constant.ATTACH_AWB) { 
            dispatch({
                type: constant.ATTACH_AWB, attachAwbDetails: response.results[0].attachAwbDetails,
                containerDetailsToBeReused: response.results[0].containerDetailsCollection
            });
             dispatch({type:constant.ATTACH_AWB_MAL,attachMalClose:true});
        }
        if (action === constant.ATTACH_ROUTING) {
            dispatch({
                type: constant.ATTACH_ROUTING, attachRoutingMalClose: true, attachRoutingDetails: response.results[0].attachRoutingDetails,
                         createMailInConsignmentVOs: response.results[0].createMailInConsignmentVOs,attachRoutingConClose: false,
                containerDetailsToBeReused: response.results[0].containerDetailsCollection, oneTimeValues: response.results[0].oneTimeValues
            });
        }
        if (action === constant.LIST_CONTAINERS_POPUP) {
            dispatch({
                type: constant.LIST_CONTAINERS_POPUP,
                         //containerVosToBeReused:response.results[0].containerDetailsCollectionPage,flightDetailsToBeReused:response.mailinboundDetails});
                         containerVosToBeReused:response.results[0].mailAcceptance.containerPageInfo,flightDetailsToBeReused:response.results[0].mailAcceptance,
                            addContainerButtonShow:false,destination:response.results[0].mailAcceptance.destination,
                carrierCode: response.results[0].mailAcceptance && response.results[0].mailAcceptance.carrierCode ? response.results[0].mailAcceptance.carrierCode : null
            });
                         
        } 
        if (action === constant.LIST_CONTAINERS_POPUP_CHANGE_FLIGHT) {
            dispatch({
                type: constant.LIST_CONTAINERS_POPUP,
                containerVosToBeReused: response.results[0].containerDetailsCollectionPage, flightDetailsToBeReused: response.mailinboundDetails
            });
                         //containerVosToBeReused:response.results[0].mailAcceptance.containerPageInfo,flightDetailsToBeReused:response.results[0].mailAcceptance,
                           // addContainerButtonShow:false});
                         
        } 
                        
        if (action === constant.CHANGE_FLIGHT) { 
            dispatch({type: constant.CHANGE_FLIGHT,changeFlightClose:true});
        }
        if (action === constant.CHANGE_FLIGHT_SAVE) { 
            dispatch({type: constant.CHANGE_FLIGHT_SAVE,changeFlightClose:false});
        }
        if (action === constant.TRANSFER) { 
            const ownAirlineCode =response.results && response.results[0]&& response.results[0].ownAirlineCode?response.results[0]&& response.results[0].ownAirlineCode:'';
            let partnerCarriers = response.results && response.results[0]&& response.results[0].partnerCarriers?response.results[0]&& response.results[0].partnerCarriers:{};
            dispatch({type: constant.MAILBAG_TRANSFER,showTransferClose:true,ownAirlineCode:ownAirlineCode,partnerCarriers:partnerCarriers});
        }
        if (action === constant.TRANSFER_SAVE) {
            if (response.results && response.results[0] != null && response.results[0].transferManifestVO != null) {                
                const transferManifestVO = response.results[0].transferManifestVO;
                dispatch({type: constant.TRANSFER_MANIFEST,transferManifestVO});                
                dispatch(asyncDispatch(onGenManifestPrint)(values));
            } else {                  
            if(response.results && response.results[0]!=null&&response.results[0].transferManifestVO!=null){
                const transferManifestVO = response.results[0].transferManifestVO;
                dispatch({type: constant.TRANSFER_MANIFEST,transferManifestVO});
                //dispatch(asyncDispatch(onGenManifestPrint)(values));
            }
            else{
                dispatch({type: constant.TRANSFER_SAVE,showTransferClose:false});
            }
            
        }
        }
        if (action === constant.DAMAGE_LIST) { 
            dispatch({type: constant.DAMAGE_LIST,oneTimeValues:response.results[0].oneTimeValues,showDamageClose:true});
        }
        if (action === constant.MAL_LIST_ON_PAG_NXT) { 
            dispatch({type: constant.DAMAGE_LIST,oneTimeValues:response.results[0].oneTimeValues,showDamageClose:true});
        }
        if(action===constant.READY_FOR_DELIVERY) {
          dispatch({type:constant.READY_FOR_DEL_CLOSE,showReadyForDel:false });
        }
        if(action===constant.REMOVE_MAILBAG) {
            dispatch({type:constant.REMOVE_MAILBAG,showRemoveMailPanel:false });
            dispatch({type:constant.CON_LIST_ALONE,containerDetails: response.results[0].containerDetailsCollectionPage });
          }
        if(action===constant.ARRIVE_MAIL) {
            dispatch({type:constant.CON_LIST_ALONE,containerDetails: response.results[0].containerDetailsCollectionPage });
          }
          if(action==='SAVE_CONTAINER'){
            dispatch(asyncDispatch(getFlightDetailsForChangeFlight)({'action':constant.LIST_TRANSFER})).then((response)=>{
                dispatch(asyncDispatch(listChangeFlightMailPanel)({ ...response, 'action': constant.LIST_TRANSFER }))
            })
             
          }
          if(action==='SAVE_CONTAINER_CHANGE_FLIGHT'){
            dispatch(asyncDispatch(getFlightDetailsForChangeFlight)({'action':constant.LIST_CHANGEFLIGHT})).then((response)=>{
                dispatch(asyncDispatch(listChangeFlightMailPanel)({ ...response, 'action': constant.LIST_CHANGEFLIGHT }))
            })
             
          }
          
    }
    else{
        if (action === constant.TRANSFER) { 
            const ownAirlineCode =response.results && response.results[0]&& response.results[0].ownAirlineCode?response.results[0]&& response.results[0].ownAirlineCode:'';
            let partnerCarriers = response.results && response.results[0]&& response.results[0].partnerCarriers?response.results[0]&& response.results[0].partnerCarriers:{};
            dispatch({type: constant.MAILBAG_TRANSFER,showTransferClose:true,ownAirlineCode:ownAirlineCode,partnerCarriers:partnerCarriers});
        }
    }
}
function populateMailbagDetailsInExcel(excelmailbags,values,dispatch) {
    let newMailbagList=[];
    for(let i = 0; i < excelmailbags.length; i++){
        if(excelmailbags[i].mailbagId===null||excelmailbags[i].mailbagId.length === 0 ){
    if(excelmailbags[i].ooe !=null&&excelmailbags[i].doe!=null  &&excelmailbags[i].mailCategoryCode!=null &&excelmailbags[i].mailSubclass!=null
        &&excelmailbags[i].year!=null &&excelmailbags[i].despatchSerialNumber!=null &&excelmailbags[i].receptacleSerialNumber!=null &&excelmailbags[i].highestNumberedReceptacle!=null
        &&excelmailbags[i].registeredOrInsuredIndicator!=null &&excelmailbags[i].weight!=null&&excelmailbags[i].weight!=null){
            newMailbagList.push(excelmailbags[i]);
           
        }
    }
        else{
            newMailbagList.push(excelmailbags[i]);  
        }
    }
    const state = values.getState();
    let mailbagId=null;
    for(let i=0;i<newMailbagList.length;i++){
     let selectedMailbagRow = newMailbagList[i];
     selectedMailbagRow.scannedDate=state.containerReducer.date;
     selectedMailbagRow.scannedTime=state.containerReducer.time;
        if (selectedMailbagRow.mailbagId.length === 29) {
             selectedMailbagRow.ooe=selectedMailbagRow.mailbagId.substring(0, 6);
             selectedMailbagRow.doe=selectedMailbagRow.mailbagId.substring(6, 12);
             selectedMailbagRow.mailCategoryCode=selectedMailbagRow.mailbagId.substring(12, 13);
             selectedMailbagRow.mailSubclass=selectedMailbagRow.mailbagId.substring(13, 15);
             selectedMailbagRow.year=selectedMailbagRow.mailbagId.substring(15, 16);
             selectedMailbagRow.despatchSerialNumber=selectedMailbagRow.mailbagId.substring(16, 20);
             selectedMailbagRow.receptacleSerialNumber=selectedMailbagRow.mailbagId.substring(20, 23);
             selectedMailbagRow.highestNumberedReceptacle=selectedMailbagRow.mailbagId.substring(23, 24);
             selectedMailbagRow.registeredOrInsuredIndicator=selectedMailbagRow.mailbagId.substring(24, 25);
             let mailWeight = populateMailWeight(selectedMailbagRow.mailbagId.substring(25, 29), state.commonReducer.defaultWeightUnit, dispatch);
             selectedMailbagRow.weight=mailWeight;
            let mailVolume = populateVolumeInExcel(selectedMailbagRow.mailbagVolume, mailWeight, state.commonReducer.density,state.commonReducer.defaultWeightUnit,state.commonReducer.stationVolUnt)
            selectedMailbagRow.volume=mailVolume;
            
 
        }
    
        else if(selectedMailbagRow.mailbagId.length === 0){
            
                let mailWeight=populateMailBagIdWeight(selectedMailbagRow.weight,selectedMailbagRow.weightUnit,dispatch);
              let dsn = populateDsn(selectedMailbagRow.despatchSerialNumber);
                let rsn = populateRsn(selectedMailbagRow.receptacleSerialNumber);   
                mailbagId= selectedMailbagRow.ooe +selectedMailbagRow.doe +selectedMailbagRow.mailCategoryCode +selectedMailbagRow.mailSubclass +selectedMailbagRow.year +dsn +rsn +selectedMailbagRow.highestNumberedReceptacle +selectedMailbagRow.registeredOrInsuredIndicator + mailWeight;
                    selectedMailbagRow.mailbagId=mailbagId;
              
           
       
 
     }
    }
     return newMailbagList;
 
}
 function populateVolumeInExcel(volume,weight,density,displayUnit,stationVolUnt) {
    /* var volume = volume;
    var w = weight
    var wt = weight/(10*density);
    var strWt=wt.toString();
    var s = strWt.indexOf(".");
    var prefix = strWt.substring(0,s);
    var suffix = strWt.substring(s,s+5);
    if(wt != 0 && prefix == 0 && suffix < 0.01){
           volume =  0.01;
    }else{				
       volume = prefix+suffix;
     } */
     var volume =volume;
     var weightInKg=0;
     var convertedVolume=0;
     if(displayUnit=='H'){
     weightInKg=weight/10;
    }
    else if(displayUnit=='L'){
      weightInKg=weight*0.45359;
    }
    else{
      weightInKg=weight;
    }
    convertedVolume=weightInKg/density;
    if (stationVolUnt=='F'){
       volume=convertedVolume*35.31466688252347;
    }
     else if (stationVolUnt=='I'){
         volume=convertedVolume*61023.743837;
     }
     else if (stationVolUnt=='Y'){
      volume=convertedVolume*1.307950613786;
  }
  else if (stationVolUnt=='C'){
      volume=convertedVolume*1000000;
  }
  else {
      volume=convertedVolume;
  }
  volume = Math.round(volume*100)/100;
     return volume;
  }
 export function onLoadAddMailbagPopup(values){
    const {dispatch,args,getState } = values;
    const data = args;
    const state = getState();
    const tableId = 'addmailbagExcel';
    if(!data) {
        dispatch(clearHandsonTable(tableId));
    }
    (state.form.newMailbagsTable && state.form.newMailbagsTable.values) ? state.form.newMailbagsTable.values.newMailbagsTable=[] : '';
    dispatch({type:constant.ADD_MAIL_POPUP_SHOW,addMailPopUpShow:data});
  }

  export function isdomestic(values){
    const {dispatch,args,getState } = values;
    const state = getState();
    const index= args.rowIndex;
    const isdomesticflag = (state.form.newMailbagsTable&&state.form.newMailbagsTable.values&&state.form.newMailbagsTable.values.newMailbagsTable&&state.form.newMailbagsTable.values.newMailbagsTable[index].mailbagId && state.form.newMailbagsTable.values.newMailbagsTable[index].mailbagId.length === 12) ? 'Y': 'N'
    return Promise.resolve({isDomesticFlag:isdomesticflag, active:state.form.newMailbagsTable.active});
  }

  export function onGenManifestPrint (values) {
    const { args,dispatch, getState } = values;
     const state = getState();
    let transferManifestVO = state.mailbagReducer.transferManifestVO;
    const url = 'rest/mail/operations/mailinbound/transferManifestPrint';
        if(args&&args.transferManifest){
    const data={transferManifestVO};
            return makeRequest({
                url,
                data: { ...data }
            }).then(function (response) {    
                dispatch({type: constant.TRANSFER_SAVE,showTransferClose:false});
                return response
            }).catch(error => {
                return error;
            });
        }
        else{   
                dispatch(requestWarning([{ code: "mail.operations.mailbag.transfermanifestwarning", description: "Do you want to generate Transfer Manifest Report?" }], { functionRecord: onGenManifestPrint, args: { warningFlag: true } }));
      }

}  

 export function pabuiltUpdate(values) {
    const { args, dispatch,getState } = values;
    const state = getState();
    if(args.barrowCheck){
    dispatch(change('transferMailForm','paBuilt', false)); 
    }
}

export const addRowInMailbagTable=(values)=>{
    const { args, getState, dispatch}=values;
    const state=getState();
    const {currentDate,currentTime,previousWeightUnit}=args;
    const size=state.form.newMailbagsTable&&state.form.newMailbagsTable.values&&
                state.form.newMailbagsTable.values.newMailbagsTable?
                    state.form.newMailbagsTable.values.newMailbagsTable.length:0
    let previouslyEnteredMail;
    if(size>0){
        previouslyEnteredMail=state.form.newMailbagsTable&&state.form.newMailbagsTable.values&&
            state.form.newMailbagsTable.values.newMailbagsTable&&state.form.newMailbagsTable.values.newMailbagsTable[size-1];
        dispatch(addRow(constant.NEW_MAILBAG_TABLE, {
            mailCategoryCode: previouslyEnteredMail.mailCategoryCode,
                            scannedDate:currentDate,
                            ooe:previouslyEnteredMail.ooe,
                            doe:previouslyEnteredMail.doe,
                            mailSubclass:previouslyEnteredMail.mailSubclass,
                            year:previouslyEnteredMail.year,
                            despatchSerialNumber:previouslyEnteredMail.despatchSerialNumber,
                            receptacleSerialNumber:previouslyEnteredMail.receptacleSerialNumber,
                            scannedTime:currentTime,
                            registeredOrInsuredIndicator:previouslyEnteredMail.registeredOrInsuredIndicator,
                            highestNumberedReceptacle:previouslyEnteredMail.highestNumberedReceptacle,
                            mailbagVolume:0,
            weight: {
                displayValue: 0, roundedDisplayValue: '0',
                displayUnit: previousWeightUnit, unitSelect: true, disabled: false
            }
        }))
    }
    else{
        dispatch(addRow(constant.NEW_MAILBAG_TABLE, {
            mailCategoryCode: 'A',
                        scannedDate:currentDate,
                        scannedTime:currentTime,
                        registeredOrInsuredIndicator:0,
                        highestNumberedReceptacle:0,mailbagVolume:0,
            weight: {
                displayValue: 0, roundedDisplayValue: '0',
                displayUnit: previousWeightUnit, unitSelect: true, disabled: false
            }
        }))
    }
}

  export function validateNewMailbagAdded(data) {
    let error = ""
    let isValid = true;
    let tabledata=[];
    tabledata = data;
    if(tabledata===undefined){
        tabledata=[];
    }
    for(var i=0;i<tabledata.length;i++) {
        let mailbag=tabledata[i];
        if(!mailbag.ooe) {
            isValid=false;
            error="Please enter Origin of exchange";
        }
        else if(!mailbag.doe) {
            isValid=false;
            error="Please enter Dest of exchange";
        }
        else if(!mailbag.mailCategoryCode) {
            isValid=false;
            error="Please enter Category Code";
        }
        else if(!mailbag.mailSubclass) {
            isValid=false;
            error="Please enter Mail subclass";
        }
        else if(!mailbag.year) {
            isValid=false;
            error="Please enter Year";
        }
        else if(!mailbag.despatchSerialNumber) {
            isValid=false;
            error="Please enter DSN";
        }
        else if(!mailbag.receptacleSerialNumber) {
            isValid=false;
            error="Please enter RSN";
        }
        else if (!mailbag.weight.displayValue) {
                isValid=false;
                error="Please enter Weight";
        }
        else if(mailbag.weight.displayValue==='0000' ||mailbag.weight.displayValue==='0' ) {
            isValid=false;
            error="Weight cannot be zero";
    }
    }
   
    let validObject = {
        valid: isValid,
        msg: error
    }
    return validObject;
  }

  export function clearAddContainerPopover(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    if(args==='TRANSFER'){
    const flightnumber = state.form.transferMailForm?state.form.transferMailForm.values.flightnumber:{};
    dispatch({type: constant.CLEAR_TRANSFER_FORM, flightnumber: flightnumber}); 
    dispatch(reset(constant.TRANSFER_MAIL_FORM));  
    }else{
        const flightno = state.form.ChangeFlightMailDetails?state.form.ChangeFlightMailDetails.values.flightnumber:{};
        dispatch({type: constant.CLEAR_CHANGE_FLIGHT, flightno: flightno}); 
        dispatch(reset(constant.CHANGE_FLIGHT_MAIL_DETAILS)); 
        }
}

export function invokeEmbargoPopup(dispatch){
    var url = "reco.defaults.ux.showEmbargo.do";
    var optionsArray = {
        url,
        dialogWidth: "650",
        dialogHeight: "210",
        popupTitle: 'Check Embargo'
        }
    dispatch(dispatchAction(openPopup)(optionsArray));
}
  
