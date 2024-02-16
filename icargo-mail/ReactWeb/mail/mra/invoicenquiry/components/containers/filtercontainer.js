import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import {clearFilter,listInvoicDetails,toggleFilter} from '../../actions/filteraction';
import { asyncDispatch,dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import FilterPanel from '../panels/FilterPanel.jsx';
import { requestValidationError } from 'icoreact/lib/ico/framework/component/common/store/commonactions';




const mapStateToProps = (state) => {

  return {

    screenMode:state.filterReducer.screenMode,
    disableAcceptButton:state.filterReducer.disableAcceptButton,
    filterValues: state.filterReducer.filterValues,
    invoicFilterForm:state.form.invoicFilter,
    initialValues:state.filterReducer.filterValues,
     gpaCode:(state.form.invoicFilter&&state.form.invoicFilter.values)?state.form.invoicFilter.values.gpaCode:null,
     fromDate:(state.form.invoicFilter&&state.form.invoicFilter.values)?state.form.invoicFilter.values.fromDate:null,
     toDate:(state.form.invoicFilter&&state.form.invoicFilter.values)?state.form.invoicFilter.values.toDate:null,
     invoicId:(state.form.invoicFilter&&state.form.invoicFilter.values)?state.form.invoicFilter.values.invoicId:null,
     //initialValues: state.filterReducer.initialValues
    }
}
const mapDispatchToProps = (dispatch, ownProps) => {
  return {
    onToggleFilter: (screenMode) => {
      dispatch(toggleFilter(screenMode));
    },
    onclearInvoicDetails: () => {
      dispatch(dispatchAction(clearFilter)());
    }	,
    onlistInvoicDetails: () => {
     dispatch(asyncDispatch(listInvoicDetails)({'displayPage':'1',mode:'LIST',pageSize:'100'}))
    },
    displayError: (message, target) => {
        dispatch(requestValidationError(message, target))
    }


  }
}
const FilterContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(FilterPanel)


export default FilterContainer