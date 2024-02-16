import React, { Component, Fragment } from 'react';
import FilterContainer from '../components/containers/filtercontainer.js';
import DetailsContainer from '../components/containers/detailcontainer.js';
import {screenLoad,handlePopupActions} from '../actions/commonaction.js';
import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import {asyncDispatch,dispatchAction} from 'icoreact/lib/ico/framework/component/common/actions';
import { LoadingHOC } from 'icoreact/lib/ico/framework/component/common/loading';
import PropTypes from 'prop-types'; 
import { IButton } from 'icoreact/lib/ico/framework/html/elements';
import {navigateToScreen} from 'icoreact/lib/ico/framework/action/navigateaction';
import {IPrintMultiButton} from 'icoreact/lib/ico/framework/component/common/printmultibutton';
import { onClose } from '../actions/commonaction.js';
class MailInbound extends Component {

    constructor(props) {
        super(props)
        this.filterArray = this.filterArray.bind(this);
    }
    filterArray (reportId) {
         let filterArray = [];
         if (reportId == 'MALSTNDLVRPT') {
             filterArray = [this.props.loggedAirport,"","",""];
         }      
         return filterArray;
    }
    render() {      
        return (
            <Fragment>
                <div className="header-panel animated fadeInDown">
                <FilterContainer/>   
                </div>
                <div className="section-panel animated fadeInUp">
                <DetailsContainer/>  
                </div>
                <div className="footer-panel">
                <IPrintMultiButton  category='default' screenId='MTK064'  filterArray={this.filterArray} >Print Misc Reports</IPrintMultiButton>
                    <IButton category='default' bType="CLOSE" accesskey="O" onClick={this.props.onClose} componentId="BUT_MAIL_OPERATIONS_MAILINBOUND_CLOSE">Close</IButton>
            </div>            
            </Fragment>
        );
    }
}
//const decApp = LoadingHOC('relisted', true)(MailInbound);
const mapStateToProps = (state) => {
    return {
        loggedAirport: state.commonReducer.loggedAirport ,
        fromPopup: state.commonReducer.fromPopup
    };
}
const mapDispatchToProps = (dispatch, ownProps) => {
    return {
        onMount: (props) => {
           dispatch(asyncDispatch(screenLoad)(props));       
        },
        // onClose: ()=> {
        //     navigateToScreen('home.jsp',{});
        // },
        onClose: () => {
            dispatch(asyncDispatch(onClose)());
          },
        onUpdate: (props) => {
            dispatch(dispatchAction(handlePopupActions)(props)); 
        }
    }
}
const AppContainer = connectContainer(
    mapStateToProps,
    mapDispatchToProps
)(MailInbound)

AppContainer.propTypes = {   
    onMount:PropTypes.func
}
export default AppContainer