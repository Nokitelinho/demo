import filterReducer from './filterreducer';
import commonReducer from './commonreducer';
import awbReducer from './awbreducer'
import {FlightReducer} from 'icoreact/lib/ico/framework/component/common/store/flightreducer';
const flightReducer = FlightReducer();
const carditEnquiryReducer = {
  filterReducer,
  commonReducer,
  flightReducer,
  awbReducer
  
}

export default carditEnquiryReducer;