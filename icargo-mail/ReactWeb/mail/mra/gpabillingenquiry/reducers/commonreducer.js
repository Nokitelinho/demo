import { SCREENLOAD_SUCCESS} from '../constants/constants';

const intialState = {
  relisted : false,  
  oneTimeValues:{},
  displayMode:'initial',
  screenMode:'initial',
  screenFilterMode:'edit',
  mcaPrivilege:'Y',
  containerRatingPAList:'',
  mcaPrivilege:'Y',
  maxFetchCount:null
}

 const commonReducer = (state = intialState, action) => {
  switch (action.type) {    
    case SCREENLOAD_SUCCESS:
      return {...state,relisted:true,oneTimeValues:action.data.oneTimeValues,containerRatingPAList:action.data.containerRatingPAList,mcaPrivilege:action.data.mcaPrivilege,screenMode:'initial',displayMode:'initial',screenFilterMode:'edit'};
      return {...state,relisted:true,oneTimeValues:action.data.oneTimeValues,mcaPrivilege:action.data.mcaPrivilege,screenMode:'initial',displayMode:'initial',screenFilterMode:'edit',maxFetchCount:action.data.maxPageCount};
    default:
      return state;
  }
}
 
export default commonReducer;


