import React, { Component, Fragment } from 'react';
import ActionButtonsContainer from '../components/containers/actionbuttonscontainer.js';
import FilterContainer from '../components/containers/filtercontainer.js';
import DetailsContainer from  '../components/containers/detailscontainer.js';
import {
  fetchDetailsOnScreenload
} from "../actions/commonaction.js";
import { asyncDispatch } from "icoreact/lib/ico/framework/component/common/actions";
import { connectContainer } from "icoreact/lib/ico/framework/component/common/app/container";
import CustomerCreationWarningPopUpConatiner from '../components/containers/popup/customercreationwarningpopuppanel.js';
import RelistPopUpContainer from '../components/containers/popup/relistpopupcontainer.js';
/**
 * Screen created on 07-2022 for IASCB-150804
 * Main container component for the whole screen
 */
class BrokerMapping extends Component {

  constructor(props) {
      super(props)
  }
render() {
      return (
          <div className="d-flex flex-column justify-content-between h-100">
            {this.props.showLoader&&<div className="loader-overlay">
                        <div className="ic-loader"> </div>
                </div>}
            <div className="header-panel animated fadeInDown">
              <FilterContainer />
            </div>
            {this.props.screenMode==="invokePopUp"?<CustomerCreationWarningPopUpConatiner show={this.props.showCreateCustomerPopUp}/>:""}
            {this.props.screenMode==="invokeSavePopUp"?<RelistPopUpContainer show={this.props.showRelist}/>:""}
            {(this.props.screenMode ==="list"||this.props.screenMode ==="newCustomer"||this.props.screenMode ==="edit")&&<DetailsContainer/>}
          
            <ActionButtonsContainer/>
           </div>
          
      );
  }
}
const mapStateToProps = (state) => {
  return {
    screenMode:state.commonReducer.screenMode,
    showCreateCustomerPopUp:state.commonReducer.showCreateCustomerPopUp,
    showRelist:state.commonReducer.showRelist,
    showLoader:state.commonReducer.showLoader
  };
};
const mapDispatchToProps = (dispatch) => {
  return {
    onMount: () => {
      dispatch(asyncDispatch(fetchDetailsOnScreenload)());
    }
  };
};
const BrokerMappingContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(BrokerMapping);

export default BrokerMappingContainer;


