
import commonReducer from './commonreducer';

import {FlightReducer} from 'icoreact/lib/ico/framework/component/common/store/flightreducer';
import filterReducer from './filterreducer';

const flightReducer = FlightReducer();

const mailTransitReducer= {
 
  commonReducer,
   flightReducer,
   filterReducer
  
}

export default mailTransitReducer;