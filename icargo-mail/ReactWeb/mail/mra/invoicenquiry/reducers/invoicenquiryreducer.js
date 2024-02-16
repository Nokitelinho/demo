import filterReducer from './filterreducer';
import commonReducer from './commonreducer';
import {FlightReducer} from 'icoreact/lib/ico/framework/component/common/store/flightreducer';
const flightReducer = FlightReducer();

const invoiceEnquiryreducer = { 
  commonReducer,
  filterReducer,
  flightReducer  
  
}

export default invoiceEnquiryreducer;