
import { CLOSE_ADD_POPUP, PARAMETER_EXISTS, PARAMETER_OK } from '../constants';
import { resetTable, requestValidationError } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax';
export function onCloseParameter(values) {
  const { dispatch } = values;
  dispatch({ type: CLOSE_ADD_POPUP, response: {} });
}



export function saveParameter(values) {

  const { args, dispatch, getState } = values;
  const state = getState();
  let parameter = {};
  let parameterMap = state.detailspanelreducer.parameterMap;
  
  let validateForm = state.form.addParameterForm && state.form.addParameterForm.values;
  if (state.form.addParameterForm && state.form.addParameterForm.values) {
    if (state.form.addParameterForm.values.country && !state.form.addParameterForm.values.includeEnabledCountry) {
      dispatch(requestValidationError('Please select Include or Exclude for country', ''));
    }
    else if (state.form.addParameterForm.values.paCode && !state.form.addParameterForm.values.includeEnabledPA) {
      dispatch(requestValidationError('Please select Include or Exclude for GPA Code', ''));
    }
   else if ((state.form.addParameterForm.values.country && state.form.addParameterForm.values.includeEnabledCountry)||
    (state.form.addParameterForm.values.paCode && state.form.addParameterForm.values.includeEnabledPA)) {
    let firstParameter = state.form.addParameterForm.values.country ? { parameterCode: 'CNTCOD',parameterName: 'Country Code', parameterValue: state.form.addParameterForm.values.country, includeExcludeFlag: state.form.addParameterForm.values.includeEnabledCountry } : '';
    let secondParameter = state.form.addParameterForm.values.paCode ? { parameterCode: 'GPACOD',parameterName: 'GPA CODE', parameterValue: state.form.addParameterForm.values.paCode, includeExcludeFlag: state.form.addParameterForm.values.includeEnabledPA } : '';
    let index = state.detailspanelreducer.parameterIndex
    let parameterList = [];
    if (firstParameter) {
      parameterList.push(firstParameter);
    }
    if (secondParameter) {
      parameterList.push(secondParameter);
    }
    parameterMap.set( index, parameterList);
  dispatch({ type: PARAMETER_OK, parameter, parameterMap });
    }
  }
  else{
    dispatch({ type: CLOSE_ADD_POPUP, response: {} });
  }
}

