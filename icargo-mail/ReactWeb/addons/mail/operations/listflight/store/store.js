import listFlightReducer from '../reducers/listflightreducer';
import {iCStore} from 'icoreact/lib/ico/framework/component/common/store';

export default iCStore(listFlightReducer,{
    peristanceConfig: {
        enablePersist: true, whitelist: [ 'listFlightReducer', 'commonReducer', 'filterReducer','awbReducer']
    }
})