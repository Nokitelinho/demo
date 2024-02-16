import {change} from 'icoreact/lib/ico/framework/component/common/form';
import { openPopup } from 'icoreact/lib/ico/framework/action/openpopup';
import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax';
import { dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import { requestValidationError } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { createSelector } from 'icoreact/lib/ico/framework/component/common/app/util'
import {isEmpty} from 'icoreact/lib/ico/framework/component/util'
import {reset} from 'redux-form';
import * as constant from '../constants/constants';
import { requestWarning } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { asyncDispatch} from 'icoreact/lib/ico/framework/component/common/actions';
import {onRowSelection} from './flightaction';
import {listMailInbound, listFlightDetailsAlone} from './filteraction';
import moment from 'moment';


export function clearFilter(values) {
    const { getState,dispatch } = values;
    const state = getState();
    state.containerReducer.isDisabled = false;
   dispatch(reset(constant.ADD_CONTAINER_FORM));
}

export function clearAttachRoutingAction(values){
    const {dispatch}=values;
       dispatch({type:constant.CLEAR_ATTACHROUTING_DATA});
       dispatch(reset(constant.ONWARD_ROUTING));
   
}
export function allContainerIconAction(values){   
    const {args, dispatch, getState } = values;
    const state = getState();
    const indexArray=args&&args.indexArray?args.indexArray:[];
    let containerDetailsCollection=[];
    let containerNumber='';
    const containerDetailsCollectionFromStore=(state.containerReducer.filterFlag)?(state.containerReducer.containerDataAfterFilter?
        state.containerReducer.containerDataAfterFilter.results:{}):
            state.containerReducer?(state.containerReducer.containerData.results):{};
    indexArray.forEach(function(element) {
        containerDetailsCollection.push(containerDetailsCollectionFromStore[element]);
    }, this);
    containerDetailsCollectionFromStore.forEach(element => {
        containerNumber=containerNumber+element.containerno+'/';
    });
       
    
    let mailinboundDetails=state.listFlightreducer?(state.listFlightreducer.flightDetails):{};
    if(isEmpty(mailinboundDetails)){
        return;
    }
    const filterValues=state.filterReducer.filterVlues;
    const fromDate=filterValues.fromDate?filterValues.fromDate:''
    const toDate=filterValues.toDate?filterValues.toDate:''
    const airportCode= filterValues.port?filterValues.port:'';
    const carrierCode=mailinboundDetails.carrierCode?mailinboundDetails.carrierCode:'';
    const flightNumber=mailinboundDetails.flightNo?mailinboundDetails.flightNo:'';
    const flightSeqNumber= mailinboundDetails.flightSeqNumber?mailinboundDetails.flightSeqNumber:null;
    const legSerialNumber= mailinboundDetails.legSerialNumber?mailinboundDetails.legSerialNumber:null;
    const flightDate = mailinboundDetails.flightDate?mailinboundDetails.flightDate.split(' ')[0]:'';
    const onlineAirportParam = mailinboundDetails.onlineAirportParam?mailinboundDetails.onlineAirportParam:'Y';
    const sysParamForRestrictingImportHandling= state.commonReducer.valildationforimporthandling;
    let  valildationforimporthandling='N';
    if(onlineAirportParam==='Y'&&sysParamForRestrictingImportHandling==='Y'){
        valildationforimporthandling='Y';
     }
     var url = "mail.operations.ux.containerenquiry.defaultscreenload.do?fromScreen=MailInbound&isPopup=true&fromDate="+fromDate+
                            '&todate='+toDate+'&airport='+airportCode+'&carrierCode='+carrierCode+'&flightNumber='+flightNumber +'&flightDate='+flightDate
                            +'&containerNumber='+containerNumber+'&flightSeqNumber='+flightSeqNumber+'&legSerialNumber='+legSerialNumber+'&type=I'
                            +'&valildationforimporthandling='+valildationforimporthandling;
  
    var optionsArray = {
        url,
        dialogWidth: "1250",
        dialogHeight: "600",
    
        popupTitle: 'All Containers'
        }
    dispatch(dispatchAction(openPopup)(optionsArray));
}

export function onContainerRowSelection(values){   
    const {args, dispatch, getState } = values;
    const state = getState();
    let containerDetail=(args)?args.rowData:state.containerReducer.containerDetail;
    let selectedIndex = (args)?args.selectedIndexes:[];
    const pageSize=state.listFlightreducer.pageSize?state.listFlightreducer.pageSize:'10';
    containerDetail={...containerDetail,pageNumber:1,pageSize:pageSize};
    let mailbagFilter = {}
    let dsnFilter = {}
    if(args && args.fromTableRowClick) {
        dispatch({ type:constant.CLEAR_MAIL_FILTER });
        dispatch({ type:constant.CLEAR_DSN_FILTER });
    } else {
        mailbagFilter = state.mailbagReducer.filterValues;
        dsnFilter = state.mailbagReducer.dsnFilterValues;
    }
    const embargoInfo = state.commonReducer.embargoInfo
    const data={containerDetail, mailbagFilter, dsnFilter,embargoInfo };
    const url = 'rest/mail/operations/mailinbound/ListMailDsn';
    const mode=constant.MAL_DSN_LIST_ON_CON_CLK;
    return makeRequest({
                    url,
                    data: {...data}
                }).then(function (response) {
                    response={...response,containerDetail:containerDetail}
                    handleResponseOnRowSelection(dispatch, response,mode, selectedIndex, state);
                    return response;
                    })
                .catch(error => {
                    return error;
                });


}

export function listMailbagAndDsns(values){
    const {args, dispatch, getState } = values;
    const state = getState();
    let containerDetail=state.containerReducer?(state.containerReducer.containerDetail):{};
    const pageNumber=args.action.displayPage?args.action.displayPage:1;
    const pageSize=state.listFlightreducer.pageSize?state.listFlightreducer.pageSize:'100';
    let mailbagFilter = state.mailbagReducer.filterValues;
    let  dsnFilter= state.mailbagReducer.dsnFilterValues
    containerDetail={...containerDetail,pageNumber:pageNumber,pageSize:pageSize};
    const data={containerDetail, mailbagFilter, dsnFilter };
    const url = 'rest/mail/operations/mailinbound/ListMailDsn';
    const mode=constant.MAL_LIST_ON_PAG_NXT;
    return makeRequest({
                    url,
                    data: {...data}
                }).then(function (response) {
                    response={...response,containerDetail:containerDetail}
                     handleResponse(dispatch, response,mode);
                    return response;
                    })
                .catch(error => {
                    return error;
                });

}

export function listMailbagAndDsnsOnContainerList(values){
    const {args,getState, dispatch} = values;
    const state = getState();
    let containerDetail=args.results[0].containerDetailsCollectionPage?
                            args.results[0].containerDetailsCollectionPage.results[0]:{};
     const pageSize=state.listFlightreducer.pageSize?state.listFlightreducer.pageSize:'100';
    const embargoInfo = state.commonReducer.embargoInfo;                        
    containerDetail={...containerDetail,pageNumber:1,pageSize:pageSize};
    const data={containerDetail,embargoInfo};
    const url = 'rest/mail/operations/mailinbound/ListMailDsn';
    const mode=constant.MAL_DSN_LIST_ON_CON_CLK;
    return makeRequest({
                    url,
                    data: {...data}
                }).then(function (response) {
                    response={...response,containerDetail:containerDetail}
                     handleResponse(dispatch, response,mode);
                    return response;
                    })
                .catch(error => {
                    return error;
                });

}

export function onSaveContainer(values){
    const { args,dispatch, getState } = values;
    const state=getState();
    const addContainer = state.form.addcontainerForm.values?(state.form.addcontainerForm.values):{}; 
    if(addContainer!=null){
        if(addContainer.containerNo==null || addContainer.containerNo==''){
            dispatch(requestValidationError('Please enter ULD/Barrow', ''));
            return Promise.resolve({errors:""});
        }
        else if(addContainer.pol==null){
            dispatch(requestValidationError('Please enter POL', ''));
            return Promise.resolve({errors:""});
        }
        else if(addContainer.desination==null){
            dispatch(requestValidationError('Please enter Destination', ''));
            return Promise.resolve({errors:""});
        }
    }
    let showWarning='Y';
    if(args.showWarning){
        showWarning= args.showWarning;
    }
    const  mailinboundDetails=state.listFlightreducer?(state.listFlightreducer.flightDetails):{};
    const data={mailinboundDetails,addContainer ,showWarning }   
    const action=constant.ADD_SUCCESS;
     const url = 'rest/mail/operations/mailinbound/addContainer';
                return makeRequest({
                    url,
                    data: {...data}
                }).then(function (response) {
                    handleResponse(dispatch, response, action)
                    return response;
                })
                .catch(error => {
                    return error;
                });
    
}
export function onApplyContainerSort(values){
    const { args, dispatch, getState } = values;
    const state = getState();
    const getContainerSortDetails = () => args;
    const getContainerDetails = () =>  state.containerReducer && state.containerReducer.containerData ? 
          state.containerReducer.containerData.results : [];
    const getContainerSortedDetails = createSelector([getContainerSortDetails, getContainerDetails], (sortDetails, containers) => {
        if (sortDetails) {
        const sortBy = sortDetails.sortBy;
        const sortorder = sortDetails.sortByItem;
            containers.sort((record1, record2) => {
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
        return containers;
    });

    let containerData=getContainerSortedDetails();
    let pagedContainerData=state.containerReducer && state.containerReducer.containerData ? 
          state.containerReducer.containerData : []; 
    pagedContainerData={...pagedContainerData,results:containerData};
    dispatch({type:constant.APPLY_CONTAINER_SORT,containerDataAfterSort:pagedContainerData});
    values={...values,args:{rowData:containerData[0]}}
    onContainerRowSelection(values);
}
export function onApplyContainerFilter(values){
     const { dispatch, getState } = values;
     const state=getState();
     /*
     const formValues= getState().form.containerFilter.values;
     if(isEmpty(formValues)){
        return;
    }
     const state=getState();
     const getTableResults = () =>
        state.containerReducer && state.containerReducer.containerData ? 
          state.containerReducer.containerData.results : [];

    let containerData=[];      
    const getTableFilter = () => 
        state.form.containerFilter.values ? 
          state.form.containerFilter.values : {};

    const getDetails = createSelector([getTableResults, getTableFilter], (results, filterValues) => {
         if (!isEmpty(filterValues)) {

             return results.filter((obj) => {

                 const currentObj = {
                     containerno: obj.containerno, remarks: obj.remarks
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
    
    containerData=getDetails();
    let pagedContainerData=state.containerReducer && state.containerReducer.containerData ? 
          state.containerReducer.containerData : []; 
    pagedContainerData={...pagedContainerData,results:containerData};
    dispatch({type: constant.APPLY_CONTAINER_FILTER,filterValues:formValues,containerDataAfterFilter:pagedContainerData});
    values={...values,args:{rowData:containerData[0]}}
    onContainerRowSelection(values);
    */
   const formValues= state.form.containerFilter? state.form.containerFilter.values:{};
   dispatch({type: constant.APPLY_CONTAINER_FILTER,filterValues:formValues});
   let rowData = state.listFlightreducer.flightDetails;
   dispatch(asyncDispatch(onRowSelection)({rowData,fromTableFilter:true}))
   

}
    export function onClearContainerFilter(values){
     const { dispatch, getState } = values;
     const state = getState();
     /*
    //  const formValues= getState().form.containerFilter.values;  
     let containerData= state.containerReducer && state.containerReducer.containerData ? 
                             state.containerReducer.containerData.results : []; 
     dispatch(change(constant.CONTAINER_FILTER,'containerno',''))
     dispatch(change(constant.CONTAINER_FILTER,'remarks',''))
    dispatch({type: constant.CLEAR_CONTAINER_FILTER});
    values={...values,args:{rowData:containerData[0]}}
    onContainerRowSelection(values);
    */
   dispatch({
    type: "@@redux-form/INITIALIZE", meta: { form: 'containerFilter', keepDirty: true },
    payload: {
        containerNumber:"",
        pol:"",
        destination:""
    }
    });
    dispatch({ type: "@@redux-form/RESET", meta: { form: 'containerFilter' } })
}

export function transferContainerAction(values){
    const {dispatch,getState,args} =values;
    const state =getState();
     //const selectedContainerData =(state.commonReducer)?state.commonReducer.selectedContainerData:{};
     let selectedContainerData=state.containerReducer?state.containerReducer.containerDetails:[];
    const transferForm  = (state.form.transferForm.values)?state.form.transferForm.values:{}
    const flightNumber=(state.form.transferForm.values) ? state.form.transferForm.values.flightnumber:{};
    const onwardRouting=(state.form.onwardRouting) ? state.form.onwardRouting.values.onwardRouting:[];

    for(let i=0; i<onwardRouting.length;i++){
        onwardRouting[i].operationFlag= 'I';
    }


    transferForm.assignedto = state.form.transferForm.values.transferFilterType;
    transferForm.reassignedto = 'DESTINATION';

    if(!isEmpty(flightNumber)) {
      transferForm.reassignedto = 'FLIGHT';
      transferForm.assignedto = 'FLIGHT';
      transferForm.flightNumber=flightNumber.flightNumber;
      transferForm.flightDate=flightNumber.flightDate;
      transferForm.flightCarrierCode=flightNumber.carrierCode;
    }

    if(selectedContainerData.length===0){
        selectedContainerData = (state.commonReducer)?state.commonReducer.selectedContainerDataByIndex:{};
    }
    let screenWarning=false;
    if(args&&args.screenWarning){
        screenWarning= args.screenWarning;
    }
    transferForm.flightPou = transferForm.destination;
    transferForm.fromScreen='MTK064';
    const data = {selectedContainerData,transferForm, onwardRouting,screenWarning};
       const url='rest/mail/operations/containerenquiry/transferContainerSave';
      return makeRequest({
         url,data: {...data}
    }).then(function(response) {
        console.log(response);
        handlevalidateContainerTransferSaveResponse(dispatch, response);
        return response;
    })
      .catch(error => {
        return { type:'TRANSFERSAVE_ERROR',error};
    });   

}

export function screenloadAttachRoutingAction(values){
     const { args, dispatch, getState } = values;
     const state = getState();
     const actionType=args?args.actionName:null;
     const indexArray=args?args.indexArray:null;
     let mailinboundDetails=state.listFlightreducer?(state.listFlightreducer.flightDetails):{};
     const containerDetailsCollectionFromStore=(state.containerReducer.filterFlag)?(state.containerReducer.containerDataAfterFilter?
                        state.containerReducer.containerDataAfterFilter.results:{}):
                            state.containerReducer?(state.containerReducer.containerData.results):{};
     const containerDetailsCollection=[];

    
    indexArray.forEach(function(element) {
        containerDetailsCollection.push(containerDetailsCollectionFromStore[element]);
    }, this);

    const data={containerDetailsCollection,mailinboundDetails}
    const url = 'rest/mail/operations/mailinbound/validateattachrouting';
    return makeRequest({
                    url,
                    data: {...data}
                }).then(function (response) {
                    handleResponse(dispatch, response,actionType)
                    if(null!=response.results[0].attachRoutingDetails&&
                        response.results[0].attachRoutingDetails.consignemntNumber!=null){
                            listAttachRoutingAction(values);
                        }
                    return response;
                })
                .catch(error => {
                    return error;
                }); 

}

export function listAttachRoutingAction(values){
    
    const { dispatch, getState } = values;
    const state = getState();
     
    const attachRoutingDetails=(state.form.attachRoutingForm)?
                                    state.form.attachRoutingForm.values:null;
    let mailinboundDetails=state.listFlightreducer?(state.listFlightreducer.flightDetails):{};
    const containerDetailsCollection=state.containerReducer?state.containerReducer.containerDetailsToBeReused:{};

    //const createMailInConsignmentVOs=state.containerReducer?(state.containerReducer.createMailInConsignmentVOs):{};
    const data={attachRoutingDetails:attachRoutingDetails,mailinboundDetails,containerDetailsCollection}
    const url = 'rest/mail/operations/mailinbound/listattachrouting';
    const actionType=constant.ATTACH_ROUTING_LIST;
    return makeRequest({
                    url,
                    data: {...data}
                }).then(function (response) {
                    handleResponse(dispatch, response,actionType)
                    return response;
                })
                .catch(error => {
                    return error;
                }); 

}

export function saveAttachRoutingAction(values){

    const { dispatch, getState } = values;
    const state = getState();

    let attachRoutingDetails=(state.form.attachRoutingForm)?
                                    state.form.attachRoutingForm.values:null;
    let onwardRouting=(state.form.onwardRouting)?
                                    state.form.onwardRouting.values.onwardRouting:{};
    let deletedRoutingDetails=(state.form.onwardRouting &&state.form.onwardRouting.values.deleted)?
                                    state.form.onwardRouting.values.deleted:{};
    let mailinboundDetails=state.listFlightreducer?(state.listFlightreducer.flightDetails):{};
    let onwardRoutingSelected=[];
    onwardRouting.forEach(function(element) {
        if((element.carrierCode+element.flightNumber+element.flightDate+element.origin+
                element.destination)!=null){
            onwardRoutingSelected.push({...element});
        }
    }, this);
    const containerDetailsCollection=state.containerReducer?state.containerReducer.containerDetailsToBeReused:{};
    let containerDetail=state.containerReducer?(state.containerReducer.containerDetail):{};
    if(deletedRoutingDetails!=null &&deletedRoutingDetails.length>0){ deletedRoutingDetails.forEach(function(element) {
        onwardRoutingSelected.push({...element,operationalStatus:'D'});       
    }, this);}
    attachRoutingDetails={...attachRoutingDetails,onwardRouting:onwardRoutingSelected};
    //const consignmentDocumentVO=(state.containerReducer)?state.containerReducer.consignmentDocumentVO:[];
    const data={attachRoutingDetails:attachRoutingDetails,
                        mailinboundDetails,containerDetailsCollection,containerDetail}
    const url = 'rest/mail/operations/mailinbound/saveattachrouting';
    const actionType=constant.ATTACH_ROUTING_SAVE;
    return makeRequest({
                    url,
                    data: {...data}
                }).then(function (response) {
                    handleResponse(dispatch, response,actionType)
                    return response;
                })
                .catch(error => {
                    return error;
                }); 

}

export function listAwbDetailsAction(values){
    const { args, dispatch, getState } = values;
    const state = getState();
    const containerDetailsCollection=state.containerReducer?state.containerReducer.containerDetailsToBeReused:{};
    let attachAwbDetails=state.containerReducer?state.containerReducer.attachAwbDetails:null;
    const documentNumber=(state.form.attachAwbDetails.values.masterDocumentNumber)?
                                state.form.attachAwbDetails.values.masterDocumentNumber:"";
    const shipmentPrefix=(state.form.attachAwbDetails.values.shipmentPrefix)?
                                state.form.attachAwbDetails.values.shipmentPrefix:null;
    attachAwbDetails={...attachAwbDetails,documentNumber:documentNumber,masterDocumentNumber:documentNumber,shipmentPrefix:shipmentPrefix}
    const data={containerDetailsCollection,attachAwbDetails};
    let action=null;
    if(args.from==='CONTAINER')
        action=constant.ATTACH_AWB_LIST_CON;
    else{
        action=constant.ATTACH_AWB_LIST_MAL;
    }
    const url = 'rest/mail/operations/mailinbound/listattachawb';
    return makeRequest({
                    url,
                    data: {...data}
                }).then(function (response) {
                    handleResponse(dispatch, response, action)
                    return response;
                })
                .catch(error => {
                    return error;
                });


}

export function saveAwbDetailsAction(values){
    const { dispatch, getState } = values;
    const state = getState();

    const containerDetailsCollection=state.containerReducer?state.containerReducer.containerDetailsToBeReused:{};
    const attachAwbDetails=state.containerReducer?state.containerReducer.attachAwbDetails:null;
    //let containerDetailsVosIterate=(state.containerReducer)?state.containerReducer.arrivalDetailsInVo.containerDetails:{};
    //const containerNumber=containerDetailsCollection?containerDetailsCollection[0].containerno:'';
   // const containerDetailsVos=[];
    //let containerDetailsVo=null;
    let mailinboundDetails=state.listFlightreducer?(state.listFlightreducer.flightDetails):{};
    const activeTab = state.containerReducer&&state.containerReducer.activeMailbagTab;
    const data={containerDetailsCollection,attachAwbDetails,mailinboundDetails,activeTab};

    const action=constant.ATTACH_AWB_SAVED;
    const url = 'rest/mail/operations/mailinbound/saveattachawb';
     return makeRequest({
                    url,
                    data: {...data}
                }).then(function (response) {
                    handleResponse(dispatch, response, action)
                    return response;
                })
                .catch(error => {
                    return error;
                });

}

export function saveChangeFlight(values){
    const { dispatch, getState } = values;
    const state = getState();

    const containerDetailsCollection=state.containerReducer?state.containerReducer.containerDetailsToBeReused:{};
    let changeContainerDetails=(state.form.ChangeFlightContainerDetails&&state.form.ChangeFlightContainerDetails.values)?
                            state.form.ChangeFlightContainerDetails.values:{};
    changeContainerDetails={...changeContainerDetails,flightNumber:changeContainerDetails.flightnumber?changeContainerDetails.flightnumber.flightNumber:null,
                                flightCarrierCode:changeContainerDetails.flightnumber?changeContainerDetails.flightnumber.carrierCode:null,
                                date:changeContainerDetails.flightnumber?changeContainerDetails.flightnumber.flightDate:null}
    let mailinboundDetails=state.listFlightreducer?(state.listFlightreducer.flightDetails):{};
    const data={containerDetailsCollection,mailinboundDetails,changeContainerDetails};

    const action=constant.CHANGE_FLIGHT_SAVE;
    const url = 'rest/mail/operations/mailinbound/savechangecontainer'; 
     return makeRequest({
                    url,
                    data: {...data}
                }).then(function (response) {
                    handleResponse(dispatch, response, action)
                    return response;
                })
                .catch(error => {
                    return error;
                });
    
}

export function containerAllSelect(values){
    const { args, dispatch, getState } = values;
    const state=getState();
    const checked=args.checked;
    let indexArray=[];
    let i=0;
    let containerDetailsCollectionPage=state.containerReducer?(state.containerReducer.containerData):{};
    let containerDetailsCollection=containerDetailsCollectionPage.results?containerDetailsCollectionPage.results:{};
    containerDetailsCollection.forEach(function(element) {
        element.checkBoxSelect=checked;
        indexArray.push(i);
        i++;
    }, this);
    const indexDetails={checked:checked,indexArray:indexArray};
    containerDetailsCollectionPage={...containerDetailsCollectionPage,results:containerDetailsCollection};
    dispatch({type: constant.SELECT_ALL, containerDetails:containerDetailsCollectionPage,indexDetails:indexDetails})

}
 
export function populateULD(values) {
    const { args, dispatch } = values;
    dispatch({ type: constant.POPULATE_ULD, args });
}



export function performContainerScreenAction(values){

    const {args, dispatch, getState } = values;
    const state=getState();
    const actionType=args?args.actionName:null;
    const indexArray=args?args.indexArray:null;
    const containerDetailsCollection=[];
    const containerDetailsCollectionFromStore=(state.containerReducer.filterFlag)?(state.containerReducer.containerDataAfterFilter?
                        state.containerReducer.containerDataAfterFilter.results:{}):
                            state.containerReducer?(state.containerReducer.containerData.results):{};

    let mailinboundDetails=state.listFlightreducer?(state.listFlightreducer.flightDetails):{};
    indexArray.forEach(function(element) {
        containerDetailsCollection.push(containerDetailsCollectionFromStore[element]);
    }, this);
    let screenWarning=false;
    if(args&&args.screenWarning){
        screenWarning= args.screenWarning;
    }
    const data={containerDetailsCollection,mailinboundDetails,transactionLevel:'U',screenWarning}
        
    if(actionType===constant.CHANGE_FLIGHT){
        const url = 'rest/mail/operations/mailinbound/validatechangecontainer';
                return makeRequest({
                    url,
                    data: {...data}
                }).then(function (response) {
                    handleResponse(dispatch, response, actionType)
                    return {...response,stopPropgn:true};
                })
                .catch(error => {
                    return error;
                });
    }else if(actionType===constant.UNDO_ARRIVAL){
        const data={containerDetailsCollection,operationLevel:'container',mailinboundDetails}
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
    }else if(actionType===constant.ACQUIT_ULD){
         const url = 'rest/mail/operations/mailinbound/acquiltuld';
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

    }else if(actionType===constant.ARRIVE_MAIL){
        let showWarning=false;
        let arrivemail=args && args.arriveMail;
        if(!isEmpty(containerDetailsCollection) ){
            for (let i=0;i<containerDetailsCollection.length;i++){
                if(containerDetailsCollection[0].transitFlag==="N" &&
                            containerDetailsCollection[0].containerType!=="B"){
                   showWarning =true;
                }
            }
        }
        
        if(showWarning && (arrivemail==undefined||arrivemail==false)){
            dispatch(requestWarning([{code:"mail.operations.container.releaseWarning", description:"The selected container is already released. Do you want to continue? "}],{functionRecord:performContainerScreenAction, args}));
           
        }else{
        const url = 'rest/mail/operations/mailinbound/arriveMail';
                return makeRequest({
                    url,
                    data: {...data}
                }).then(function (response) {
                    //handleResponse(dispatch, response, actionType)
                    return response;
                })
                .catch(error => {
                    return error;
                });
            }
    }else if(actionType===constant.DETACH_AWB){
        let detachAwb=args && args.detachAwb;
       
        if( detachAwb==undefined||detachAwb==false){
            dispatch(requestWarning([{code:"mail.operations.container.detachAWBwarningContainer", description:"Are you sure you want Detach AWB?"}],{functionRecord:performContainerScreenAction, args}));
           }else{
            let mailbagsWithAWB=[];
            let indexes=[];
            let url = '';
            let data={};
            indexes.push(args.indexArray);
                let containerDetailsCollection=[];
                let length=state.mailbagReducer.mailbagData.results.length;
             for(var i=0; i<length;i++) {
                 if(state.mailbagReducer.mailbagData.results[i].awb!=null){
                    mailbagsWithAWB.push(state.mailbagReducer.mailbagData.results[i]);
               }
                }
            let selectedContainer=state.containerReducer.containerDetail
            selectedContainer={...selectedContainer,mailbagpagelist:null,totalWeight:null,
                               mailBagDetailsCollection:mailbagsWithAWB!=null?mailbagsWithAWB:null}
            containerDetailsCollection.push(selectedContainer); 
            data={containerDetailsCollection}
            url = 'rest/mail/operations/mailinbound/detachAWBCommand';
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
        }
    else if(actionType===constant.DELIVER_MAIL){
        const deliverMailDetails=(state.form.deliverMailDetails.values)?state.form.deliverMailDetails.values:{};
        if(deliverMailDetails.time==""||deliverMailDetails.date==""){
            dispatch(requestValidationError('Please enter the date and time', ''));
            return {};
        }
        let showWarning=false;
        let arrivemail=args && args.arriveMail;
        if(!isEmpty(containerDetailsCollection) ){
            for (let i=0;i<containerDetailsCollection.length;i++){
                if(containerDetailsCollection[0].transitFlag==="N"){
                   showWarning =true;
                }
            }
        }
        
        if(showWarning && (arrivemail==undefined||arrivemail==false)){
            dispatch(requestWarning([{code:"mail.operations.container.releaseWarning", description:"The selected container is already released. Do you want to continue? "}],{functionRecord:performContainerScreenAction, args}));
            return Promise.reject({});
        }
        else{
        const data={containerDetailsCollection,deliverMailDetails,operationLevel:'container',mailinboundDetails,screenWarning,transactionLevel:'U'}
        const url = 'rest/mail/operations/mailinbound/delivermail';
                return makeRequest({
                    url,
                    data: {...data}
                }).then(function (response) {
                    handleResponse(dispatch, response, actionType);
                    return response;
                })
                .catch(error => {
                    return error;
                });
            }

    }else if(actionType===constant.ATTACH_ROUTING){
        // close flights service call+ state obj 
    }else if(actionType===constant.ATTACH_AWB){
        const url = 'rest/mail/operations/mailinbound/screenloadattachawb';
                return makeRequest({
                    url,
                    data: {...data}
                }).then(function (response) {
                    handleResponse(dispatch, response, actionType)
                    return {...response,stopPropgn:true};
                })
                .catch(error => {
                    return error;
                });
    }else if(actionType===constant.TRANSFER){
        let isBulkExist=false;
        if(containerDetailsCollection.length>0){
            for(let i=0;i<containerDetailsCollection.length;i++){
                if(containerDetailsCollection[i].containerType==='B'){
                    isBulkExist=true;
                }
            }
        }
        if(isBulkExist){
            dispatch(requestValidationError('Bulk Containers cannot be transferred.', ''));
            return Promise.resolve({errors:""});
        }
        else{
        const url = 'rest/mail/operations/mailinbound/validatetransfercontainer';
                return makeRequest({
                    url,
                    data: {...data}
                }).then(function (response) {
                    handleResponse(dispatch, response, actionType)
                    return {...response,stopPropgn:true};
                })
                .catch(error => {
                    return error;
                });
            }
    }
    else if(actionType===constant.REMOVE_CONTAINER){
        const removeDetails=(state.form.removeDetails.values)?state.form.removeDetails.values:{};
        const data={containerDetail:containerDetailsCollection[0],mailinboundDetails,removeType:'U'}
        const url = 'rest/mail/operations/mailinbound/removemailbag';
            return makeRequest({
                    url,
                    data: {...data, ...removeDetails}
                }).then(function (response) {
                    handleResponse(dispatch, response, actionType)
                    return response;
                })
                .catch(error => {
                    return error;
                });
    }
    else if(actionType===constant.READY_FOR_DELIVERY){
        const readyForDeliveryForm=(state.form.ReadyForDeliveryForm.values)?state.form.ReadyForDeliveryForm.values:{};
        if(readyForDeliveryForm.time==""||readyForDeliveryForm.date==""){
            dispatch(requestValidationError('Please enter the date and time', ''));
            return {};
        }
        const selectedFlight = state.listFlightreducer.flightDetails
        let data={};
        var toDate=(moment()).format('DD-MMM-YYYY hh:mm:ss');
        var flightDate=selectedFlight.flightDate;
        if(flightDate > toDate) {
            return Promise.reject(new Error( "Cannot mark ready for delivery as flight not arrived" ));
        }
        else {
        containerDetailsCollection.forEach(function(element) {
            element.readyForDeliveryDate=readyForDeliveryForm.date;
            element.readyForDeliveryTime=readyForDeliveryForm.time;
        }, this);
        const data={containerDetailsCollection,mailinboundDetails}
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
    }    
    //dispatch( { type: args.type});
}

function handleResponse(dispatch, response, action) {  
    let mismatchInPieceOrWeightStatus=false;
    if (response.errors!=null&& action=="ATTACH_AWB_LIST_MAL") {
    let errors=response.errors.ERROR;
    for(var i=0; i<errors.length;i++) {
        if( errors[i].code== 'mailtracking.defaults.attachawb.msg.err.invalidshipmentpieceorweight'){
            mismatchInPieceOrWeightStatus=true;
        }
    }   
    }
    if(response.errors==null||response.errors.INFO!=null||mismatchInPieceOrWeightStatus==true ){
        if (action === constant.ARRIVE_MAIL) { 
            dispatch({ });
        }
        if (action === constant.ADD_SUCCESS) { 
            dispatch({type: constant.ADD_CONTAINER_SHOW,data:false})
           dispatch({type: constant.LIST_CONTAINERS_ONADD_SUCCESS,flightDetails:{...response.results[0].mailinboundDetails, flightOperationStatus:'Open'}})
                dispatch(asyncDispatch(onRowSelection)({fromAction:'Container'}));
            // // dispatch(asyncDispatch(listMailInbound)({'action':{type:constant.LIST_MULTI} , selectedFlight: response.results[0].mailinboundDetails})).then(() =>{
            // //     dispatch(asyncDispatch(onRowSelection)({fromAction:'Container'}));
            // })
        }
        if (action === constant.ACQUIT_ULD) { 
            dispatch({type: constant.ACQUIT_ULD});
        }
        if (action === constant.ATTACH_AWB) { 
            dispatch({type: constant.ATTACH_AWB,attachConClose:true,attachAwbDetails:response.results[0].attachAwbDetails,
                        containerDetailsToBeReused:response.results[0].containerDetailsCollection});
        }
        if (action === constant.ATTACH_AWB_LIST_CON) { 
            dispatch({type: constant.ATTACH_AWB_LIST_CON,attachAwbDetails:response.results[0].attachAwbDetails,attachConClose:true,
                        containerDetailsToBeReused:response.results[0].containerDetailsCollection});
        }
        if (action === constant.ATTACH_AWB_LIST_MAL) {
            dispatch({type: constant.ATTACH_AWB_LIST_MAL,attachAwbDetails:response.results[0].attachAwbDetails,attachMalClose:false,
                        containerDetailsToBeReused:response.results[0].containerDetailsCollection});
        }  
		if (action === constant.ATTACH_AWB_SAVED) {
            dispatch({type: constant.ATTACH_AWB_SAVED});
        }
        if (action === constant.CHANGE_FLIGHT) {
            dispatch({type: constant.CHANGE_FLIGHT, changeContainerClose: true, 
                         containerDetailsToBeReused: response.results[0].containerDetailsCollection});
        }
        if (action === constant.TRANSFER) {
            dispatch({type: constant.TRANSFER, transferContainerClose: true,
                         containerDetailsToBeReused: response.results[0].containerDetailsCollection});
        }                        
        if (action === constant.ATTACH_ROUTING) {
            dispatch({type: constant.ATTACH_ROUTING, attachRoutingConClose: true, attachRoutingDetails:response.results[0].attachRoutingDetails,
                         createMailInConsignmentVOs: response.results[0].createMailInConsignmentVOs,oneTimeValues:response.results[0].oneTimeValues,
                          attachRoutingMalClose: false,containerDetailsToBeReused: response.results[0].containerDetailsCollection  });
        }
        if (action === constant.ATTACH_ROUTING_LIST) {
            dispatch({type: constant.ATTACH_ROUTING_LIST,  attachRoutingDetails:response.results[0].attachRoutingDetails,
                         consignmentDocumentVO: response.results[0].consignmentDocumentVO,oneTimeValues:response.results[0].oneTimeValues});
        }
        if (action === constant.ATTACH_ROUTING_SAVE) {
            dispatch({type: constant.ATTACH_ROUTING_SAVE, attachRoutingConClose: false,attachRoutingMalClose: false});
        }  
        if (action === constant.CHANGE_FLIGHT_SAVE) {
            dispatch({type: constant.CHANGE_FLIGHT_SAVE, changeContainerClose: false});
        }
        if (action === constant.REMOVE_CONTAINER) {
            dispatch({type: constant.REMOVE_CONTAINER, showRemoveContainerPanel: false});
        }
        if (action === constant.MAL_DSN_LIST_ON_CON_CLK||action === constant.MAL_LIST_ON_PAG_NXT) {
            dispatch({type: constant.MAIL_DSN_LIST,mode:'multi', containerDetail:response.containerDetail,mailDetails:response.results[0].mailBagDetailsCollectionPage?
                        response.results[0].mailBagDetailsCollectionPage:{},dsnDetails:response.results[0].dsnDetailsCollectionPage?
                        response.results[0].dsnDetailsCollectionPage:{}, mailbagData:response.results[0].mailBagDetailsCollectionPage?
                        response.results[0].mailBagDetailsCollectionPage.results:{} });
            dispatch({type: constant.TRANSFER_SAVE,showTransferClose:false});  
            dispatch({type: constant.ADD_MAIL_POPUP_SHOW,addMailPopUpShow:false});          
            dispatch({type: constant.TRANSFER_CLOSE,transferContainerClose:false}); 
            dispatch({type: constant.DELIVER_CLOSE,showDeliverMail:false});        
        } 

     if (action === constant.READY_FOR_DELIVERY) {
            dispatch({type: constant.READY_FOR_CONT_DEL_CLOSE, showContReadyForDel: false});
        }
    if(action===constant.DELIVER_MAIL){
        dispatch({type: constant.DELIVER_CONTAINER, showDeliverMail: false});
    }
    

              
    }             
                   
  
}

export function setDeliveryPopuFieldStatus(values) {   
    const { dispatch } = values; 
    dispatch( {type:constant.SET_DELIVERY_POPUP_CONT_FIELDS_STATUS, data: false});
}
export function validateContainerForTransfer(values){
    const {args, dispatch ,getState } = values;
    const state = getState();
     //const selectedContainerData =(state.commonReducer)?state.commonReducer.selectedContainerData:{};
     const indexes = state.containerReducer.selectedContainerIndex;
     const {index, mode} = args;
     let selectedContainerData=[];
     if(!index){
        for(var i=0; i<indexes.length;i++) {
            selectedContainerData.push(state.containerReducer.containerList[indexes[i]]);
        }
     }
     
    if(selectedContainerData.length===0){
        selectedContainerData = [state.containerReducer.containerList[index]];
    }
    const data = {selectedContainerData};
    const url='rest/mail/operations/containerenquiry/validateContainerForTransfer';
      return makeRequest({
        url,data: {...data}
    }).then(function(response) {
        handlevalidateContainerTransferResponse(dispatch, response, selectedContainerData, mode);
        return response;
    })
      .catch(error => {
        return { type:'TRANSFER_ERROR',error};
    });   
}

function handleResponseOnRowSelection(dispatch, response, action, selectedIndex, state) {
    if (response.errors == null || response.errors.INFO != null) {
        if (action === constant.MAL_DSN_LIST_ON_CON_CLK || action === constant.MAL_LIST_ON_PAG_NXT) {
            dispatch({
                type: constant.MAIL_DSN_LIST, mode: 'multi', containerDetail: response.containerDetail, mailDetails: response.results[0].mailBagDetailsCollectionPage ?
                    response.results[0].mailBagDetailsCollectionPage : {}, dsnDetails: response.results[0].dsnDetailsCollectionPage ?
                        response.results[0].dsnDetailsCollectionPage : {}, mailbagData: response.results[0].mailBagDetailsCollectionPage ?
                            response.results[0].mailBagDetailsCollectionPage.results : {}
            });
            dispatch({ type: constant.TRANSFER_SAVE, showTransferClose: false });
            dispatch({ type: constant.ADD_MAIL_POPUP_SHOW, addMailPopUpShow: false });
            dispatch({ type: constant.TRANSFER_CLOSE, transferContainerClose: false });
            dispatch({ type: constant.DELIVER_CLOSE, showDeliverMail: false });
            let retainValidation = false;
            const loggedAirport = state.commonReducer.loggedAirport;
            for(let i=0; i<selectedIndex.length; i++){
                const containerDetail = state.containerReducer.containerData.results[selectedIndex[i]];
                if(containerDetail.destination !== loggedAirport){
                    retainValidation =true;
                    break;
                }
            }
            dispatch({ type: constant.SAVE_SELECTED_CONTAINER_INDEX, selectedIndex: selectedIndex, retainValidation:retainValidation });
        }
    }
}

export function handlevalidateContainerTransferResponse(dispatch, response, selectedContainerData, mode){
    if(response.status === 'success'){
        let currentTime= response.results[0].transferForm.mailScanTime;
        let uldTobarrow=response.results[0].transferForm.uldToBarrow;
        if(mode === 'CARRIER'){
            dispatch(validateContainerSuccessForTransfer(selectedContainerData,currentTime, response));
        }else if(mode === 'FLIGHT'){
            dispatch(validateContainerSuccessForTransferToFlight(selectedContainerData,currentTime, response,uldTobarrow));
        }
       
            
    }
}

export function validateContainerSuccessForTransfer(selectedContainerData,currentTime, data){
    return { type: constant.VALIDATEFORTRANSFER_SUCCESS, selectedContainerData , currentTime, data};
}
export function validateContainerSuccessForTransferToFlight(selectedContainerData,currentTime, data,uldTobarrow){
    if(selectedContainerData.length>0){
        for(let i=0;i< selectedContainerData.length;i++){
        if(selectedContainerData[i].paBuiltFlag==='Y') {
            selectedContainerData[i].isPaBuilt=true;
        }
        else {
            selectedContainerData[i].isPaBuilt=false;
        } 
    }
    }
   
    return { type: constant.VALIDATEFORTRANSFER_FLIGHT_SUCCESS, selectedContainerData , currentTime, data,uldTobarrow};
}

export const listFlightDetailsForReassignAndTransfer = (values) => {
    const { args, dispatch, getState } = values;
    const state = getState();
    let displayPage='';
   // const mode = state.commonReducer.displayMode;
    let mode=state.commonReducer.displayMode;
    let loggedAirport= state.commonReducer.loggedAirport
    let flightActionsEnabled= 'false';
    const assignTo = state.commonReducer.flightCarrierflag;
    let flightCarrierFilter = {};
    let panel = '';

    if(args && args.panel){
        panel = args.panel;
    }
    

    flightCarrierFilter = (state.form.transferContainerToFlight && state.form.transferContainerToFlight.values) ? state.form.transferContainerToFlight.values : {}

    
    if(!flightCarrierFilter){
    if (!flightCarrierFilter.flightnumber.flightDate || !flightCarrierFilter.flightnumber.flightNumber) {
        if(!flightCarrierFilter.flightnumber.flightDate && !flightCarrierFilter.flightnumber.flightNumber) {
           if (!(flightCarrierFilter.fromDate && flightCarrierFilter.toDate)) {
            return Promise.reject(new Error("Please enter Flight date and Flight number or Flight date range"));
           }
        }
        else if(!flightCarrierFilter.flightnumber.flightDate){
            return Promise.reject(new Error("Please enter Flight Date"));
        }
        else if (!flightCarrierFilter.flightnumber.flightNumber){
            return Promise.reject(new Error("Please enter Flight Number"));
        }
    }
}

    if((flightCarrierFilter.fromDate && flightCarrierFilter.toDate)){
        const fromDate = new Date(flightCarrierFilter.fromDate);
        const toDate = new Date(flightCarrierFilter.toDate);
        const diffTime = Math.abs(toDate - fromDate);
        const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
        if(diffDays>2){
            return Promise.reject(new Error("Date difference cannot be greater than 2 days"));
        } 
    }
    
    flightCarrierFilter.assignTo = 'F';
    flightCarrierFilter.airportCode=loggedAirport;
    flightCarrierFilter.filterType = 'F';
    //Added by A-8893 as part of IASCB-33763 starts
    flightCarrierFilter.fromTime='00:00',
    flightCarrierFilter.toTime='23:59',
  //Added by A-8893 as part of IASCB-33763 ends
    flightCarrierFilter.destination=flightCarrierFilter.dest?flightCarrierFilter.dest:'';
    flightCarrierFilter.carrierCode?flightCarrierFilter.carrierCode=flightCarrierFilter.carrierCode:flightCarrierFilter.carrierCode='AA';
    const flightNumber = (state.form.transferContainerToFlight && state.form.transferContainerToFlight.values) ? state.form.transferContainerToFlight.values.flightnumber : {};
    if(flightCarrierFilter.airportCode ===loggedAirport ){
        flightActionsEnabled='true';
    }
        if (!isEmpty(flightNumber) && (flightNumber.carrierCode)) {
            flightCarrierFilter.flightNumber = flightNumber.flightNumber;
            flightCarrierFilter.flightDate = flightNumber.flightDate;
            flightCarrierFilter.carrierCode = flightNumber.carrierCode
        }
        if(args) {
            displayPage = args.displayPage;
            //for 1st page listing
           if(args.mode==='display') {
              mode='display';
          }
           flightCarrierFilter.flightDisplayPage = displayPage;
       }
      else
    {
       flightCarrierFilter.flightDisplayPage = state.filterReducer.flightDisplayPage;
       mode = state.commonReducer.displayMode;
       displayPage=state.filterReducer.flightDisplayPage
    } 
    flightCarrierFilter.flightStatus = ['ACT','TBA']
    flightCarrierFilter.flightOperationalStatus = 'O,N'
     //added as part of IASCB-36551
    if(state.filterReducer.filterValues) {
        flightCarrierFilter.operatingReference = state.filterReducer.filterValues.operatingReference
    }  
    const data = { flightCarrierFilter };
    const url = 'rest/mail/operations/outbound/list';
    return makeRequest({
        url,
        data: { ...data }
    }).then(function (response) {
        if(!response.results){
            return Promise.reject(new Error("No results found"));
        }
        const { mailflightspage, mailcarrierspage, wareHouses } = response.results[0];
            if(!mailflightspage){
                return Promise.reject(new Error("No results found"));
            }
            handleResponseForReassignToFlight(dispatch, response, data,mode,flightActionsEnabled);
        return response
    })
        .catch(error => {
            return error;
        });
    //  dispatch( { type: 'LIST_DETAILS', data:data});

}

export const fetchFlightCapacityDetails = (values) =>{
    const {  dispatch, getState } = values;
    const state = getState();
   // const mode = state.commonReducer.displayMode;
    const flightCarrierFilter = (state.form.reassignContainerToFlight && state.form.reassignContainerToFlight.values) ? state.form.reassignContainerToFlight.values : {}
    let loggedAirport= state.commonReducer.loggedAirport
    let flightActionsEnabled= 'false';
    if(flightCarrierFilter.airportCode === loggedAirport ){
        flightActionsEnabled='true';
    }
    flightCarrierFilter.assignTo = 'F';
    flightCarrierFilter.airportCode=loggedAirport;
    flightCarrierFilter.filterType = 'F';
    flightCarrierFilter.destination=flightCarrierFilter.dest?flightCarrierFilter.dest:'';
    flightCarrierFilter.carrierCode?flightCarrierFilter.carrierCode=flightCarrierFilter.carrierCode:flightCarrierFilter.carrierCode='AA';
   
    let List=state.containerReducer.flightDetails?state.containerReducer.flightDetails.results:[];
    let mailAcceptanceList=[];
    for (var i = 0; i < List.length; i++) {
        let acceptance={};
        acceptance.companyCode=List[i].companyCode;
        acceptance.carrierId=List[i].carrierId;
        acceptance.flightRoute=List[i].flightRoute;
        acceptance.flightNumber=List[i].flightNumber;
        acceptance.flightSequenceNumber=List[i].flightSequenceNumber;
        acceptance.flightDate=List[i].flightDate;
        mailAcceptanceList.push(acceptance);
    }
 // const data = { mailAcceptanceList:{...mailAcceptanceList,pouList: null,preadvice: null,preassignFlag: null,totalContainerWeight: null, totalCapacity:null,totalWeight: null,numericalScreenId: null,containerPageInfo:null, mailCapacity:null,mailbags:null,flightpk:null,containerDetails:null},flightCarrierFilter };
 const data = { mailAcceptanceList,flightCarrierFilter}
 const url = 'rest/mail/operations/outbound/fetchFlightCapacityDetails';
    return makeRequest({
        url,
        data: { ...data }
    }).then(function (response) {
        
        handleResponseForReassignToFlight(dispatch, response, data, 'FETCH_CAPACITY',flightActionsEnabled);
        return response
    })
        .catch(error => {
            return error;
        });
    //  dispatch( { type: 'LIST_DETAILS', data:data});

}

function handleResponseForReassignToFlight(dispatch, response, data, mode,flightActionsEnabled) {
    
    if (!isEmpty(response.results)) {
        if(mode === 'FETCH_CAPACITY') {
            const { flightCapacityDetails } = response.results[0];
             dispatch({ type: constant.LIST_CAPACITY_TRANSFER_FLIGHT,flightCapacityDetails,mode});
       }else{
            const { mailflightspage, mailcarrierspage, wareHouses } = response.results[0];
            dispatch({ type: constant.LIST_DETAILS_TRANSFER_FLIGHT, mailflightspage, wareHouses, data, mode,flightActionsEnabled });
       }
    }
}


export const clearTransferFlightPanel = (values) => {
    const { dispatch, getState } = values;
    const state = getState();
    dispatch({ type: constant.CLEAR_TRANSFER_FLIGHT_PANEL });
    dispatch(reset('transferContainerToFlight'));

}

export function transferContainerToFlightAction(values){
    const {dispatch, getState ,args} = values;
    const state = getState(); 
    let  containerearray = state.form.containerDetails.values&&state.form.containerDetails.values.containerDetails?(state.form.containerDetails.values.containerDetails):[];
   let uldCheck=false;
    if (containerearray.length > 0) {
        for (let item in containerearray) {
            if (containerearray[item].barrowFlag) {
                uldCheck=true;
                 }
        }
    }
    if(args.uldToBarrow||!uldCheck){
    let selectedContainerData=[];
    selectedContainerData=state.containerReducer?state.containerReducer.containerDetails:[];

    const selectedFlightIndex=state.containerReducer?state.containerReducer.selectedFlightIndexReassign:[];

    if(selectedFlightIndex.length!==1){
        return Promise.reject(new Error("Please select one of the flight details"));
    }

    let selectedFlight={};
    selectedFlight=state.containerReducer?state.containerReducer.flightDetails.results[selectedFlightIndex[0]]:{};

   
    // const selectedContainerData =(state.commonReducer)?state.commonReducer.selectedContainerData:{};
    //const data = {selectedContainerData};
    const transferContainerToFlight  = (state.form.transferContainerToFlight.values)?state.form.transferContainerToFlight.values:{}
    const containerDetails = state.form.containerDetails?(state.form.containerDetails.values.containerDetails):[];
    let onwardRouting=(state.form.onwardRouting && state.form.onwardRouting.values) ? state.form.onwardRouting.values.onwardRouting:[];

    for(let i=0; i<onwardRouting.length;i++){
        onwardRouting[i].operationFlag= 'I';
    }

    /*transferForm.reassignedto = 'FLIGHT';
    transferForm.assignedto = 'FLIGHT';
    transferForm.flightNumber=flightNumber.flightNumber;
    transferForm.flightDate=flightNumber.flightDate;
    transferForm.flightCarrierCode=flightNumber.carrierCode;*/

    let loggedAirport= state.commonReducer.loggedAirport;
    const flightRoute = selectedFlight.flightRoute;
    const pous = flightRoute.split("-");
    let pou = '';
    for(let k=0; k<pous.length; k++){
        if(pous[k] === loggedAirport){
            pou = pous[k+1];
            break;
        }
    }

    let transferForm = {}
    transferForm.reassignedto = 'FLIGHT';
    transferForm.assignedto = 'FLIGHT';
    transferForm.flightNumber=selectedFlight.flightNumber;
    transferForm.flightDate=selectedFlight.flightDate;
    transferForm.flightCarrierCode=selectedFlight.carrierCode;
    transferForm.flightSeqNumber=selectedFlight.flightSequenceNumber;
    transferForm.scanDate = transferContainerToFlight.scanDate;
    transferForm.mailScanTime = transferContainerToFlight.mailScanTime;
    if(!containerDetails[0].pou || containerDetails[0].pou === loggedAirport){
        transferForm.flightPou = pou;
    }else{
        transferForm.flightPou = containerDetails[0].pou;
    } 
    transferForm.destination = containerDetails[0].destination;

    if(!selectedContainerData || selectedContainerData.length===0){
        selectedContainerData = (state.commonReducer)?state.commonReducer.selectedContainerDataByIndex:{};
    }
    

    for(let i=0; i<selectedContainerData.length; i++){
        for(let j=0; j<containerDetails.length; j++){
            if(selectedContainerData[i].containerNumber === containerDetails[j].containerNumber){
                if(!containerDetails[j].pou || containerDetails[j].pou === loggedAirport){
                    selectedContainerData[i].pou = pou;
                }else{
                    selectedContainerData[i].pou = containerDetails[j].pou;
                }
                selectedContainerData[i].destination = containerDetails[j].destination;
                selectedContainerData[i].actualWeight = containerDetails[j].actualWeight;
                selectedContainerData[i].contentId = containerDetails[j].contentId;
                selectedContainerData[i].remarks = containerDetails[j].remarks;
                if(args.uldToBarrow&&containerDetails[j].barrowFlag){
                    selectedContainerData[i].type='B';
                    selectedContainerData[i].uldTobarrow=true;
                    transferForm.destination = pou;
                }
            }
        }
    }

    transferForm.fromScreen='MTK064';
    const data = {selectedContainerData,transferForm, onwardRouting};
    //const data = {selectedContainerData,reassignContainer,onwardRouting};
    const url='rest/mail/operations/containerenquiry/transferContainerSave';
     return makeRequest({
        url,data: {...data}
    }).then(function(response) {
        handlevalidateContainerTransferSaveResponse(dispatch, response);
        return response;
    })
}  else{
    dispatch(requestWarning([{ code: "mail.operations.container.uldwarning", description: "The selected ULD container will be converted as Bulk. Do you want to continue?" }], { functionRecord: transferContainerToFlightAction, args: { warningFlag: true } }));                
          
}
  }

export function handlevalidateContainerTransferSaveResponse(dispatch,response){
    if (response.status==='transfersave_success') {
         dispatch(transferContainerSuccess(response.status));       
     } 
 }

export function transferContainerSuccess(data){
    return{ type : constant.TRANSFERSAVE_SUCCESS, data }
}

export function retainContainer(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    const { index, warningFlag } = args;
    const containerDetailsCollection = [];
    let containerNumber = '';
    if(index){
        state.containerReducer.containerData &&  state.containerReducer.containerData.results?
        containerDetailsCollection.push(state.containerReducer.containerData.results[index]):'';
    }else{
        const indexes = state.containerReducer.selectedContainerIndex;
        for(let i=0; i<indexes.length; i++){
            containerDetailsCollection.push(state.containerReducer.containerData.results[indexes[i]]);
        }
    }
    //const containerDetail = state.containerReducer.containerData &&  state.containerReducer.containerData.results? state.containerReducer.containerData.results[index] : {};
    for(let i=0; i< containerDetailsCollection.length; i++){
            if(containerDetailsCollection[i].containerType === 'B'){
            dispatch(requestValidationError('BULK cannot be retained', ''));
            return Promise.resolve({errors:""});
        }else{
            if(i === 0){
                containerNumber = containerNumber + containerDetailsCollection[i].containerNumber;
            }else{
                containerNumber = containerNumber + ', ' +containerDetailsCollection[i].containerNumber;
            }
            
        }
    }
    
    if(!warningFlag){
        
        dispatch(requestWarning([{ code: "mail.operations.container.retainwarning", description: "The Container "+containerNumber+" will be retained in the flight. Do you want to continue" }], { functionRecord: retainContainer, args: { warningFlag: true, index:index } }));                
    }else{
        const containerDetailsCollection = [];
        let containerDetail = {};
        if(index){
            containerDetail = state.containerReducer.containerData &&  state.containerReducer.containerData.results?
            state.containerReducer.containerData.results[index]:'';
            containerDetail.legSerialNumber = state.listFlightreducer.flightDetails.legSerialNumber;
            containerDetailsCollection.push(containerDetail);
        }else{
            const indexes = state.containerReducer.selectedContainerIndex;
            for(let i=0; i<indexes.length; i++){
                containerDetail = state.containerReducer.containerData.results[indexes[i]];
                containerDetail.legSerialNumber = state.listFlightreducer.flightDetails.legSerialNumber;
                containerDetailsCollection.push(containerDetail);
            }
        }
       // const containerDetail = state.containerReducer.containerData &&  state.containerReducer.containerData.results? state.containerReducer.containerData.results[index] : {};
        
        
        const data = { containerDetailsCollection };
        const url = 'rest/mail/operations/mailinbound/updateRetainFlagForContainer';
        return makeRequest({
            url, data: { ...data }
        }).then(function (response) {
            return response;
        })
            .catch(error => {
            });
    }    
}