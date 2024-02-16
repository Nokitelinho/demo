import {handleResponse} from './handleresponse.js'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { ActionType, Urls } from '../constants/constants.js'


export const updateSortVariables= (data) => {
    const { dispatch,args } = data;
    const payLoad={type:ActionType.UPDATE_SORT_VARIABLE,response:args,dispatch}
    handleResponse(payLoad);
}

export const onApplyDsnFilter = (values) =>{
    const { args, dispatch, getState } = values;
    const state = getState();
    let flightDetails={};
    const tableFilter  = (state.form.carditDsnTableFilter.values)?state.form.carditDsnTableFilter.values:{}
    const { flightnumber, ...rest } = tableFilter;
    if(!isEmpty(flightnumber)){
        Object.keys(flightnumber).forEach(element => {
            if(!isEmpty(flightnumber[element])){
                flightDetails={...flightDetails,[element]:flightnumber[element]}
            }
        });
    }
    const carditDsnTableFilter={...flightDetails,...rest};
    const payLoad={type:ActionType.SAVE_FILTER,response:carditDsnTableFilter,dispatch}
    handleResponse(payLoad);
}

export const onClearDsnFilter=(values)=> {
    const {dispatch} = values;  
    const payLoad={type: ActionType.CLEAR_TABLE_FILTER,dispatch}
    handleResponse(payLoad);   
}

export const saveSelectedIndexes=(values)=> {
    const {dispatch,args} = values;
    const payLoad={type: ActionType.SAVE_SELECTED_INDEXES,response:args,dispatch}
    handleResponse(payLoad);
}