import React, { Component, Fragment } from 'react';
import FilterContainer from '../components/containers/filtercontainer.js';
import DetailsContainer from '../components/containers/detailscontainer.js';
import FooterContainer from '../components/containers/footercontainer.js';
import { asyncDispatch } from 'icoreact/lib/ico/framework/component/common/actions';
import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import { screenLoad} from '../actions/commonaction.js';
import { LoadingHOC } from 'icoreact/lib/ico/framework/component/common/loading';


class CarditDSNEnquiry extends Component {
       constructor(props) {
        super(props)
    }
    render() {
        return(
            <Fragment>
                <div className="header-panel animated fadeInDown">
                    <FilterContainer />
                </div>
                <div className="section-panel animated fadeInUp"  style={{display:this.props.screenMode === 'initial'?'none':null}}>
                    <DetailsContainer />
                </div>
                <div className="footer-panel"  style={{display:this.props.screenMode === 'initial'?'none':null}}>
                    <FooterContainer />
                </div>
        </Fragment>
        )
    }
  
    
}

const decApp = LoadingHOC('relisted', true)(CarditDSNEnquiry);
const mapStateToProps = (state) => {
    return {
        relisted: state.commonReducer.relisted,
        screenMode:state.filterReducer.screenMode,
       
    };
}
const mapDispatchToProps = (dispatch) => {
    return {
        onMount:(props)=> {
          dispatch(asyncDispatch(screenLoad)());
      },
        
    }
}

const CarditEnquiryContainer = connectContainer(
    mapStateToProps,
    mapDispatchToProps
)(decApp)

export default CarditEnquiryContainer;
    
