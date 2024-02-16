import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import BatchListPanel from '../panels/BatchListPanel.jsx';
import { asyncDispatch } from 'icoreact/lib/ico/framework/component/common/actions';
import { loadBatchDetail } from '../../actions/filteraction';

const mapStateToProps = (state) => {
    return {
        selectedBatchId: state.filterReducer.selectedBatchId,
        batchLists: state.filterReducer.batchLists,
        oneTimeValues: state.commonReducer.oneTimeValues,
        batchListMode: state.filterReducer.batchListMode  
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        onSelect: (selectedBatchId) => {
            dispatch(asyncDispatch(loadBatchDetail)({ selectedBatchId: selectedBatchId, pageNumber: '1', batchListMode: 'onSelect' }))
        }       
    }
}

const BatchListContainer = connectContainer(
    mapStateToProps,
    mapDispatchToProps
)(BatchListPanel)

export default BatchListContainer