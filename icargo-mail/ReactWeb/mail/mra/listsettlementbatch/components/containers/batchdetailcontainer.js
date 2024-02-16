import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import BatchDetailTable from '../panels/BatchDetailTable.jsx';
import { dispatchAction, asyncDispatch } from 'icoreact/lib/ico/framework/component/common/actions';
import {  saveSelectedBatchIndexes  } from '../../actions/commonaction';
import { loadBatchDetail} from '../../actions/filteraction';

const mapStateToProps = (state) => {
  return {    
    batchDetailsList: state.filterReducer.batchDetailsList
  }
}
const mapDispatchToProps = (dispatch) => {
  return {
    onSelectExcelUpload: (value) => {
      dispatch(dispatchAction(selectExcelUpload)(value))
    },
    getNewPage: (pageNumber, pageSize) => {
      dispatch(asyncDispatch(loadBatchDetail)({ mode: "NEXT_PAGE", pageNumber, pageSize }))
    },
    exportToExcel: (pageNumber, pageSize) => {
      return dispatch(asyncDispatch(loadBatchDetail)({ mode: "EXPORT", pageNumber, pageSize }))
    },
    saveSelectedBatchIndexes: (index) => {           
      dispatch(dispatchAction(saveSelectedBatchIndexes)(index));
    },
  }
}

const BatchDetailContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(BatchDetailTable)

export default BatchDetailContainer