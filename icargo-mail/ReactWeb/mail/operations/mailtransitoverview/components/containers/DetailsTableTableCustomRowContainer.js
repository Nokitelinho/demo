import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import  DetailsTableCustomRowPanel  from '../panels/DetailsTableCustomRowPanel.jsx';
import { fetchFlightCapacityDetailPerRow } from "../../actions/filteraction.js";
import { asyncDispatch, dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
const mapStateToProps = (state) => {
    return {
        capacityDetailsPerRowMap:state.filterReducer.capacityDetailsPerRowMap,
        capacityDetailsKey:state.filterReducer.capacityDetailsKey,
        onRowDoubleClick:state.filterReducer.onRowDoubleClick,
        pageCount:state.filterReducer.pageCount
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        findAllotment:(props)=>{
            dispatch(asyncDispatch(fetchFlightCapacityDetailPerRow)(props));
          }
          
    }
}

const DetailsTableTableCustomRowContainer = connectContainer(
    mapStateToProps,
    mapDispatchToProps
)(DetailsTableCustomRowPanel)

export default DetailsTableTableCustomRowContainer