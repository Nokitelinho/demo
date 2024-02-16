import commonReducer from './commonreducer';
import { AwbReducer}  from 'icoreact/lib/ico/framework/component/common/store/awbreducer';
const awbReducer = AwbReducer();
const brokermappingReducer = {
  commonReducer,awbReducer
}

export default brokermappingReducer;