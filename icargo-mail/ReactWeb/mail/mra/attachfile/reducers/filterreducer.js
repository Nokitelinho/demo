import { ActionType} from '../constants/constants';


const intialState ={
  relisted : false,  
  displayMode:'initial',
  screenMode:'initial',
  screenFilterMode:'edit'
 
}



const filterReducer = (state = intialState, action) => {
  switch (action.type) {  
    default:
      return state;
  }
}


export default filterReducer;