import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import FilterPanel from '../panels/FilterPanel.jsx';
import { listmailbagSecurityDetails, onChangeScreenMode, clearPanelFilter, onSelect, onOKClick, onClose, onSaveSecurityStatus } from '../../actions/filteraction';
import { asyncDispatch, dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';



const mapStateToProps = (state) => {
    return {
        screenMode: state.filterReducer.screenMode,
        origin: state.filterReducer.origin,
        destination: state.filterReducer.destination,
        mailbagId: state.filterReducer.mailbagId,
        editMode: state.filterReducer.editMode,
		showPopover: state.filterReducer.showPopover,
        oneTimeValues: state.listmailbagReducer.oneTimeValues,
        securityStatusCode: state.listmailbagReducer.securityStatusCode,
        warningFlag:state.filterReducer.warningFlag,
    }
}
const mapDispatchToProps = (dispatch, ownProps) => {
    return {

        onlistMailbagSecurityDetails: () => {
            dispatch(asyncDispatch(listmailbagSecurityDetails)({ action: 'LIST' }))
        },
        onChangeScreenMode: (mode) => {
            dispatch(dispatchAction(onChangeScreenMode)(mode));
        },
        onclearMailbagsecurityDetails: () => {
            dispatch(dispatchAction(clearPanelFilter)());
        },
		onSelect: () => {
            dispatch(dispatchAction(onSelect)());
        },
        onOKClick: () => {
            dispatch(dispatchAction(onOKClick)());
        },
        onClose: () => {
            dispatch(dispatchAction(onClose)());
        },
        onSaveSecurityStatus: () => {
            dispatch(asyncDispatch(onSaveSecurityStatus)({ action: 'LIST' })).then(() => {
                dispatch(asyncDispatch(listmailbagSecurityDetails)({ action: 'LIST' }))
            })
        }
    }
}

const FilterContainer = connectContainer(
    mapStateToProps,
    mapDispatchToProps

)(FilterPanel)


export default FilterContainer