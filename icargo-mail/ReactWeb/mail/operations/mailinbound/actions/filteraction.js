import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util'
import * as constant from '../constants/constants';



export function changeScreenMode(values) {
    const { args, dispatch } = values;
    const mode = args;
    dispatch({ type: constant.CHANGE_SCREEN_MODE, mode });
}
export function clearFilterPanel(values) {
    const {dispatch} = values;
    dispatch({ type: constant.CLEAR_FILTER,response:{} });
}


export function listMailInbound(values) {

    const { args, dispatch, getState } = values;
    const state = getState();
    let mailinboundFilter=(state.form.mailinboundFilter.values)?state.form.mailinboundFilter.values:{};
    const pageNumber=args.action.displayPage?args.action.displayPage:state.listFlightreducer.flightData.pageNumber;
    const pageSize=args.action.pageSize?args.action.pageSize:state.listFlightreducer.pageSize;



                mailinboundFilter={...mailinboundFilter,pageNumber:pageNumber,pageSize:pageSize};

                 let flightNumber=(state.form.mailinboundFilter.values) ? state.form.mailinboundFilter.values.flightnumber:{};
    if(!isEmpty(flightNumber)) {
      mailinboundFilter.carrierCode = flightNumber.carrierCode;  
      mailinboundFilter.flightNumber=flightNumber.flightNumber;
      mailinboundFilter.flightDate=flightNumber.flightDate;
    }

    if(!(mailinboundFilter.port) || !(mailinboundFilter.fromDate && mailinboundFilter.toDate)|| !(mailinboundFilter.fromTime && mailinboundFilter.toTime)){
        return Promise.reject(new Error("Please enter Port , Date Range and Time Range")); 
    }
    if(args && args.fromMainList) {
        dispatch({type:constant.CLEAR_FLIGHT_FILTER});
        dispatch({type:constant.EMPTY_FLIGHTS});
    }
    if(args && args.fromFilter) {
        let filterDefaultValue= {
            pol:"",
            mailstatus:"",
            flightnumber: {
                carrierCode:"",
                flightNumber:"",
                flightDate:""
            }
        }
        let flightDetailsFilter= state.form.flightDetailsFilter&& !isEmpty(state.form.flightDetailsFilter.values) ? 
          state.form.flightDetailsFilter.values : filterDefaultValue;
        if(flightDetailsFilter.flightnumber) {
            mailinboundFilter.carrierCode = flightDetailsFilter.flightnumber.carrierCode;  
            mailinboundFilter.flightNumber=flightDetailsFilter.flightnumber.flightNumber;
            mailinboundFilter.flightDate=flightDetailsFilter.flightnumber.flightDate;   
            mailinboundFilter.flightnumber = flightDetailsFilter.flightnumber 
         }
         mailinboundFilter = {...mailinboundFilter,...flightDetailsFilter}
         dispatch({type:constant.APPLY_FLIGHT_FILTER, data:flightDetailsFilter})
         dispatch({type:constant.EMPTY_FLIGHTS});
    }
                const data={mailinboundFilter};
                const url = 'rest/mail/operations/mailinbound/listflightdetails';
                return makeRequest({
                    url,
                    data: {...data}
                }).then(function (response) {
                    if(args&& !args.fromFilter && args.action && args.action.type === 'LIST_MULTI'  && args.selectedFlight){
                        response.results[0].mailinboundDetails = args.selectedFlight;
            // response.results[0].mailinboundDetails.flightOperationStatus = 'Open';
                    }
                    if(response.results&&response.results[0] && response.results[0].mailinboundFilter) {
                        args.action.filterValues = response.results[0].mailinboundFilter;
                    } else {
                        args.action.filterValues=mailinboundFilter;
                    }
                   
                    if (args&&args.action && args.action.type === 'EXPORT') {
                        let flightDetails = response.results[0].mailinboundDetailsCollectionPage;
                        return flightDetails;
                    }
                
                    response={...response,pageSize:pageSize};
                    handleResponse(dispatch, response,args.action);
                    return response;
                })
                .catch(error => {
                    return error;
                });
        


}

export function listFlightDetailsAlone(values){

    const { dispatch, getState } = values;
    const state = getState();
    let response=state.listFlightreducer.flightData;
    let flightDetails=state.listFlightreducer.flightData.results;
    const selectedFlight=state.listFlightreducer.flightDetails;
    if(selectedFlight!=null){
        flightDetails.map((element)=>{
            if(element.flightNo===selectedFlight.flightNo
                &&element.flightDate===selectedFlight.flightDate){
                    if(element.flightOperationStatus==='New')
                        element.flightOperationStatus='Open'
                }
        })
    }
    response={results:flightDetails}
    handleResponse(dispatch, response,constant.FLIGHT_LIST_ALONE);
}


function handleResponse(dispatch, response, action) {  
    if(response.errors==null){
    if (!isEmpty(response)) {
        if(response.results[0].mailinboundDetailsCollectionPage.results.length>0){
        for (var i = 0; i < response.results[0].mailinboundDetailsCollectionPage.results.length; i++) {
            let  flightData={carrierCode:response.results[0].mailinboundDetailsCollectionPage.results[i].carrierCode,flightNumber:response.results[0].mailinboundDetailsCollectionPage.results[i].flightNo,
                flightCarrierId:Number(response.results[0].mailinboundDetailsCollectionPage.results[i].carrierId),flightSequenceNumber:Number(response.results[0].mailinboundDetailsCollectionPage.results[i].flightSeqNumber),
                legSerialNumber:Number(response.results[0].mailinboundDetailsCollectionPage.results[i].legSerialNumber),
                flightDate:response.results[0].mailinboundDetailsCollectionPage.results[i].sta}
            dispatch({ type: 'FLIGHT_DATA_SUCCESS', flightData: flightData});
         }
        }
        if (action.type === constant.LIST) { 


        const filterdata = action.filterValues;
        const summaryFilter = makeSummaryFilter(filterdata);


           // const mailinboundDetails = response.results[0];    
            dispatch({ type: constant.LIST_SUCCESS,mode:'display', filterValues:action.filterValues,mailinboundDetails:response.results[0].mailinboundDetailsCollectionPage,
                    flightDetails:response.results[0].mailinboundDetails,containerDetails:response.results[0].containerDetailsCollectionPage,
                     mailbagData:response.results[0].mailBagDetailsCollectionPage,dsnData:response.results[0].dsnDetailsCollectionPage,
                     date:response.results[0].currentDate,time:response.results[0].currentTime, pageSize:response.pageSize,
                    containerDetail:response.results[0].containerDetailsCollectionPage?response.results[0].containerDetailsCollectionPage.results[0]:{},summaryFilter});
        }
        if(action === constant.FLIGHT_LIST_ALONE) {
            dispatch({ type: constant.FLIGHT_LIST_ALONE, mailinboundDetails:response })
        }

        if (action.type === constant.LIST_MULTI) { 


            const filterdata = action.filterValues;
            const summaryFilter = makeSummaryFilter(filterdata);
    
    
               // const mailinboundDetails = response.results[0];    
                dispatch({ type: constant.LIST_SUCCESS,mode:'multi', filterValues:action.filterValues,mailinboundDetails:response.results[0].mailinboundDetailsCollectionPage,
                        flightDetails:response.results[0].mailinboundDetails,containerDetails:response.results[0].containerDetailsCollectionPage,
                         mailbagData:response.results[0].mailBagDetailsCollectionPage,dsnData:response.results[0].dsnDetailsCollectionPage,
                         date:response.results[0].currentDate,time:response.results[0].currentTime, pageSize:response.pageSize,
                        containerDetail:response.results[0].containerDetailsCollectionPage?response.results[0].containerDetailsCollectionPage.results[0]:{},summaryFilter});
        }
    }
    else {
        if(!isEmpty(response.errors)){
             dispatch( { type: constant.CLEAR_TABLE});
        }
    }
    }
}


function makeSummaryFilter(data){
    
    let filter={};
    let popOver={};
    let count = 0;
    let popoverCount =0;
    
    
    if (data.flightnumber) {

        let flightnumber = {};
        flightnumber.flightNumber = flightnumber.flightNumber;
        flightnumber.carrierCode = flightnumber.carrierCode;
        filter = { ...filter, flightnumber: flightnumber};
            count++;

       if(data.flightnumber.flightDate){
           filter = { ...filter, flightDate:flightnumber.flightDate};
            count++;
       }  
    }

    if(data.port && data.port.length > 0){
        filter={...filter, port:data.port};
        count++;
    }
    if(data.fromDate && data.fromDate.length > 0){
        filter={...filter, fromDate:data.fromDate};
        count++;
    }
    if(data.toDate && data.toDate.length > 0){
        filter={...filter, toDate:data.toDate};
        count++;
    }
    if(data.mailstatus && data.mailstatus.length > 0){
        filter={...filter, mailstatus:data.mailstatus};
        count++;
    }
    if(data.transfercarrier && data.transfercarrier.length > 0){
        if(count<6){
            filter={...filter, transfercarrier:data.transfercarrier};
            count++;
        }else{
            popOver={...popOver, transfercarrier:data.transfercarrier};
            popoverCount++;
        }
    }

  if(data.pa && data.pa.length > 0){
        if(count<6){
            filter={...filter, pa:data.pa};
            count++;
        }else{
            popOver={...popOver, pa:data.pa};
            popoverCount++;
        }  
    }

    if(data.mailFlightChecked){
        if(count<6){
            filter={...filter, mailFlightChecked:data.mailFlightChecked};
            count++;
        }else{
            popOver={...popOver, mailFlightChecked:data.mailFlightChecked};
            popoverCount++;
        }  
    }
    if(data.pol){
        if(count<6){
            filter={...filter, pol:data.pol};
            count++;
        }else{
            popOver={...popOver, pol:data.pol};
            popoverCount++;
        }  
    }

    if(data.operatingReference){
        if(count<6){
            filter={...filter, operatingReference:data.operatingReference};
            count++;
        }else{
            popOver={...popOver, operatingReference:data.operatingReference};
            popoverCount++;
        }  
    }

   

    const summaryFilter = {filter:filter, popOver:popOver, popoverCount:popoverCount};
    return summaryFilter;
}