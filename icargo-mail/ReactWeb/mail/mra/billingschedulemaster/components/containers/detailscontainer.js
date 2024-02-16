import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import DetailsPanel from '../panels/DetailsPanel.jsx';
import { dispatchAction, asyncDispatch } from 'icoreact/lib/ico/framework/component/common/actions';

const mapStateToProps = (state) => {
    return {
        screenMode: state.filterpanelreducer.screenMode,
        noData:state.filterpanelreducer.noData,
        parameterMap: state.detailspanelreducer.parameterMap
    }
}
const mapDispatchToProps = (dispatch) => {
    return {

      addRow: (newRowData, curRowData) => {
        let newRow = newRowCheck(curRowData);
        if (newRow) {
          dispatch(addRow("mailBagsTable", newRowData))
        }
        else {
          let validObject = validateForm(curRowData);
          if (!validObject.valid) {
            dispatch(requestValidationError(validObject.msg, ''));
          }
          else {
            dispatch(addRow("mailBagsTable", newRowData))
          }
        }
      },
    }
}

export default connectContainer(mapStateToProps, mapDispatchToProps)(DetailsPanel);
