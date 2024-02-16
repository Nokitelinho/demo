import React, { Component, Fragment } from 'react';
import { LoadingHOC } from 'icoreact/lib/ico/framework/component/common/loading';
import FilterContainer from '../components/containers/filtercontainer';
import DetailsContainer from '../components/containers/detailscontainer';
import Actionbuttonscontainer from '../components/containers/actionbuttonscontainer';
import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import { asyncDispatch } from 'icoreact/lib/ico/framework/component/common/actions';
import { screenLoad } from '../actions/commonactions';

class BillingScheduleMaster extends Component {
  constructor(props) {
    super(props)

  }
  render() {
    return (
      <Fragment>
        <div className="header-panel animated fadeInDown relative">
          <FilterContainer />
        </div>
        <div className="section-panel animated fadeInUp">
          <DetailsContainer />
        </div>
        <div className="footer-panel">
          <Actionbuttonscontainer />
        </div>
      </Fragment>
    );
  }
}

const decApp = LoadingHOC('screenLoadComplete', true)(BillingScheduleMaster);

const mapStateToProps = (state) => {
  return {
    screenLoadComplete: state.commonReducer.screenLoadComplete
  };
}

const mapDispatchToProps = (dispatch) => {
  return {
    onMount: () => {
      dispatch(asyncDispatch(screenLoad)());
    }
  }
}

const Billingschedulecontainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(decApp)

export default Billingschedulecontainer;











