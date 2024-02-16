import { deleteRow } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { LOAD_ADD_POPUP, CLOSE_ADD_POPUP, SAVE_SELECTED_INDEX ,  LOAD_MODIFY_POPUP} from '../constants';
import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax';
import { requestValidationError } from 'icoreact/lib/ico/framework/component/common/store/commonactions';

export function onAddMailRule(values) {
    const { dispatch } = values;
    dispatch({ type: LOAD_ADD_POPUP, response: {} });
}

export function onmodiftMailRule(values) {
    const { dispatch, getState } = values;
    const state = getState();
   let selectedMailRleConfigIndex= state.commonReducer.selectedMailRleConfigIndex;
   if(selectedMailRleConfigIndex.length===0){
    const message = ' Please select one row ';
    const target = '';
   return dispatch(requestValidationError(message, target));   
   }
  if(selectedMailRleConfigIndex.length>1){
    const message = ' Multiple row cannot select ';
    const target = '';
  return  dispatch(requestValidationError(message, target));
   }
   let listValues = state.filterpanelreducer.mailRuleConfigList ? state.filterpanelreducer.mailRuleConfigList : [];
    let selectedMailRule=listValues[selectedMailRleConfigIndex[0]];
    dispatch({ type: 'LOAD_MODIFY_POPUP', selectedMailRule });
}


export function saveSelectedMailRleConfigIndex(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    const index = args;
    let indexes = state.commonReducer.selectedMailRleConfigIndex ? state.commonReducer.selectedMailRleConfigIndex : [];

    indexes.push(index);
    dispatch({ type: SAVE_SELECTED_INDEX, indexes });
}

export function saveUnselectedMailRleConfigIndex(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    const index = args;
    let indexes = state.commonReducer.selectedMailRleConfigIndex ? state.commonReducer.selectedMailRleConfigIndex : [];

    if (index > -1) {
        let ind = -1;
        for (let i = 0; i < indexes.length; i++) {
            var element = indexes[i];
            if (element === index) {
                ind = i;
                break;
            }
        }
        if (ind > -1) {
            indexes.splice(ind, 1);
        }
    } else {
        indexes = [];
    }


    dispatch({ type: SAVE_SELECTED_INDEX, indexes });
}

export function onDeleteMailRule(values) {
    const { dispatch, getState } = values;
    const state = getState();
    const rowIndex = state.commonReducer.selectedMailRleConfigIndex ? state.commonReducer.selectedMailRleConfigIndex : [];
    if (rowIndex.length > 0) {

        dispatch(deleteRow("mailrulelist", rowIndex))

    } else {
        dispatch(requestValidationError('Please select atleast one row to delete', ''));
    }


    
}
export function saveMultipleMailRleConfigIndex(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    const indexes = args;
    dispatch({ type: SAVE_SELECTED_INDEX, indexes });
}

