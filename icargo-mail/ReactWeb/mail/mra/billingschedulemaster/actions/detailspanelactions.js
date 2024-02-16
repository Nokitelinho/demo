import { SCREEN_MODE,LOAD_ADD_POPUP } from '../constants';

export function onAddParameter(values) {
  const { dispatch } = values;
  dispatch({ type: LOAD_ADD_POPUP, index:values.args  });
  
}
export function onParamterOK(values) {
  const { dispatch } =values;
  
 
}

