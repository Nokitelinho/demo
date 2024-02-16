import filterReducer from './filterreducer.js';
import commonReducer from './commonreducer.js';
import detailsReducer from './detailsreducer.js'; 
import {FlightReducer} from 'icoreact/lib/ico/framework/component/common/store/flightreducer';
import {AwbReducer} from 'icoreact/lib/ico/framework/component/common/store/awbreducer';
const awbReducer = AwbReducer();
const flightReducer = FlightReducer();
const mailAwbBookingReducer = {
  filterReducer,
  commonReducer,
  flightReducer,
  detailsReducer,
  awbReducer,
  
}

export default mailAwbBookingReducer;