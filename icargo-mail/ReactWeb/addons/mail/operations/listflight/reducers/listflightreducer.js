import filterReducer from './filterreducer';
import commonReducer from './commonreducer';
import {FlightReducer} from 'icoreact/lib/ico/framework/component/common/store/flightreducer';
import {AwbReducer} from 'icoreact/lib/ico/framework/component/common/store/awbreducer';
const awbReducer = AwbReducer();
const flightReducer = FlightReducer();
const listFlightReducer = {
  filterReducer,
  commonReducer,
  flightReducer,
  awbReducer
  
}

export default listFlightReducer;