
import commonReducer from './commonreducer';
import filterReducer from './filterreducer';
import invoiceReducer from './invoicereducer';
import {ScribblepadReducer} from 'icoreact/lib/ico/framework/component/common/store/scribblepadreducer';

const customerscribblereducer = ScribblepadReducer('customerscribblereducer',
(state,action) => {
			
				//if( action.data && action.data.status === 'success' && action.data.customerCode ) {
				if( action.data && action.data.results && action.data.results.length > 0 ) {
					const screenModel = action.data.results[0];					
					const customerCode = screenModel.filterValues.customerCode;
					const notes = [];
					return {...state , [customerCode] : notes };
				} else {
					return {...state};
				}
},
(state,action) => {
			
				if( action.data && action.data.results && action.data.results.length > 0  ) {
					const screenModel = action.data.results[0];					
					const customerCode = screenModel.filterValues.customerCode;
					const notes = screenModel.details ? screenModel.details : [] ;
					return {...state , [customerCode] : notes };
				} 
				// Commented on 19-FEB as no results is also handled above 
				/* else {
					const customerCode = action.data.customerCode;
					const notes = [];
					return {...state , [customerCode] : notes };				
				} */
}
);
const customerConsoleReducer = {
  commonReducer,
  filterReducer,
  invoiceReducer,
  customerscribblereducer
}
export default customerConsoleReducer;