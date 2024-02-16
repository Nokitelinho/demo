import React, { Component, Fragment } from 'react';
import { asyncDispatch, dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import {screenLoad} from '../actions/commonaction.js';
import {listInvoicDetails,listInvoicEnquiryOnNavigation} from '../actions/filteraction';
import { LoadingHOC } from 'icoreact/lib/ico/framework/component/common/loading';
import { IButton } from 'icoreact/lib/ico/framework/html/elements'
import FilterContainer from '../components/containers/filtercontainer.js';
import DetailsContainer from '../components/containers/detailscontainer.js';
import ActionButtonContainer from '../components/containers/actionbuttoncontainer.js';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';

 const Aux =(props) =>props.children;
class InvoicEnquiry extends Component {
    constructor(props) {
        super(props)
    }
    render() {
        return (

            <Fragment>
               <Aux>
                    <FilterContainer />
                </Aux>
                <Aux>
                    <DetailsContainer />
                </Aux>
                


                 <div className="footer-panel">
                    <ActionButtonContainer />
                </div> 

            </Fragment>
        );
    }
}
const decApp = LoadingHOC('relisted', true)(InvoicEnquiry);

const mapStateToProps = (state) => {
    return {
        relisted: state.commonReducer.relisted

    };
}
const mapDispatchToProps = (dispatch, ownProps) => {
    return {
        onMount: (props) => {


            const { dynaProps: { __fromScreen } } = props
            if (__fromScreen && __fromScreen === 'List_INVOIC_SCREEN') {
                dispatch(asyncDispatch(screenLoad)({fromScreen:'List_INVOIC_SCREEN'}))
                    .then((response) => {
                        if (isEmpty(response.errors)) {
                            dispatch(asyncDispatch(listInvoicEnquiryOnNavigation)({ autoList: true }))
                        } else {
                            return response
                        }
                    });
            }
            else {
            dispatch(asyncDispatch(screenLoad)());
            }
           
      },
        
        
    }
}

const invoicEnquiryContainer = connectContainer(
    mapStateToProps,
    mapDispatchToProps
)(decApp)

export default invoicEnquiryContainer;

