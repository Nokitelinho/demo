import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import screenLoad from './commonaction';
import {reset} from 'redux-form';
import {asyncDispatch} from 'icoreact/lib/ico/framework/component/common/actions';



export function toggleFilter(screenMode) {  
    return {type: 'TOGGLE_FILTER',screenMode };
  }


//For Clear 
export const clearFilter=(values)=> {
    const {dispatch} = values;  
    dispatch({ type: 'CLEAR_FILTER'});
    dispatch(reset('invoicFilter'));
    dispatch(reset('extendedFilter'));
}

//For List
export function listInvoicDetails(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    const screenMode=state.filterReducer.screenMode
    const displayPage = args && args.displayPage ? args.displayPage : state.filterReducer.displayPage;
    let pageSize=args.pageSize?args.pageSize:state.filterReducer.pageSize;
    if(pageSize===undefined){
        pageSize=100;
    }
    const invoicFilter  = (state.form.invoicFilter.values)?state.form.invoicFilter.values:{}
    invoicFilter.displayPage=displayPage;
    invoicFilter.defaultPageSize=pageSize;
    const data = {invoicFilter};
    const url = 'rest/mail/mra/gpareporting/invoicenquiry/list';
    return makeRequest({
        url,
        data: {...data}
    }).then(function (response) {
        handleResponse(dispatch, response,args.mode,data,screenMode,displayPage);
        return response
    })
    .catch(error => {
        return error;
    });
}

function handleResponse(dispatch,response,action,data,screenMode,displayPage) {
    
    if(!isEmpty(response.results)){
        console.log(response.results[0]);
        const {invoicDetails} = response.results[0];
        const currencycode = response.results[0].currencyCode;
        const pageSize=data.invoicFilter&&data.invoicFilter.defaultPageSize?data.invoicFilter.defaultPageSize:100;
        if (action==='LIST') {
            if(invoicDetails !=null ) {
                if(displayPage > 1) {
                    dispatch( { type: 'LIST_SUCCESS_PAGINATION',invoicDetails,data,screenMode,displayPage,currencycode,pageSize:pageSize });
                }
                else{
           dispatch( { type: 'LIST_SUCCESS',invoicDetails,data,currencycode,pageSize:pageSize});
                }
            }
            else {
                dispatch( { type: 'NO_DATA',data}); 
            }
        }
        
    } else {
        if(!isEmpty(response.errors)){
             dispatch( { type: 'RETAIN_VALUES'});
        }
    }
}


//For navigation list
export function listInvoicEnquiryOnNavigation(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    const screenMode=state.filterReducer.screenMode
   // const displayPage = args && args.displayPage ? args.displayPage : state.filterReducer.displayPage
    const invoicFilter  = (state.filterReducer.filterValues)?state.filterReducer.filterValues:'';
    const displayPage ='1';
    const pageSize=state.filterReducer.pageSize?state.filterReducer.pageSize:10;
    const action= 'LIST';
    invoicFilter.displayPage=displayPage;
    invoicFilter.defaultPageSize=pageSize;
    const data = {invoicFilter};
    const url = 'rest/mail/mra/gpareporting/invoicenquiry/list';
    return makeRequest({
        url,
        data: {...data}
    }).then(function (response) {
        handleResponse(dispatch, response,action,data,screenMode,displayPage);
        return response
    })
    .catch(error => {
        return error;
    });
 
}

/*function handleResponse(dispatch,response,action,data,screenMode,displayPage) {
    
    if(!isEmpty(response.results)){
        console.log(response.results[0]);
        const {invoicDetails} = response.results[0];
        if (action==='LIST') {
            if(invoicDetails !=null ) {
                if(displayPage > 1) {
                    dispatch( { type: 'LIST_SUCCESS_PAGINATION',data,screenMode,displayPage });
                }
                else{
           dispatch( { type: 'LIST_SUCCESS',invoicDetails,data });
                }
            }
            else {
                dispatch( { type: 'NO_DATA',data}); 
            }
        }
        
    } else {
        if(!isEmpty(response.errors)){
             dispatch( { type: 'RETAIN_VALUES'});
        }
    }
}*/





