import React, { Component, Fragment } from 'react';
import { LoadingHOC } from 'icoreact/lib/ico/framework/component/common/loading';
import FilterContainer from '../components/containers/filtercontainer';
import DetailsContainer from '../components/containers/detailscontainer';
import Actionbuttonscontainer from '../components/containers/actionbuttonscontainer';
import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import { ScreenLoad } from '../actions/commonactions';
import { asyncDispatch } from 'icoreact/lib/ico/framework/component/common/actions';
import {onlistConsignment} from '../actions/filterpanelactions';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';

class ConsignmentSecurity extends Component {
    constructor(props) {
        super(props)
    }

    render() {

        return (
            <Fragment>
                <div className="header-panel animated fadeInDown">
                    <FilterContainer />
                </div>
                {this.props.filterList === true?
                <div className="section-panel animated fadeInUp">
                    <DetailsContainer />
                </div>:
             <div className="card-body p-0 inner-panel">
             </div>}
                <div className="footer-panel">
                    {<Actionbuttonscontainer />}
                </div>
            </Fragment>
        );
    }

}

const decApp = LoadingHOC('relisted', true)(ConsignmentSecurity);

const mapStateToProps = (state) => {
    return {
        relisted:true,
        filterList: state.filterpanelreducer.filterList
    };
}

const mapDispatchToProps = (dispatch) => {
    return {
        onMount: (props) => {
            const { dynaProps: { __fromScreen } } = props
            if (__fromScreen && ((__fromScreen === 'mail.operations.ux.consignment'))) {
              dispatch(asyncDispatch(ScreenLoad)())
                  .then((response) => {
                      if (isEmpty(response.errors)) {
                          dispatch(asyncDispatch(onlistConsignment)())
                      } else {
                          return response
                      }
                  });
          }
          else {
            dispatch(asyncDispatch(ScreenLoad)());
          }
        }
    }
}

const detailscontainer= connectContainer
(mapStateToProps,
mapDispatchToProps)
(decApp);

export default detailscontainer