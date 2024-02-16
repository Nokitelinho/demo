import React, { Component, Fragment } from 'react';
import FilterContainer from '../components/containers/filtercontainer.js';
import ActionButtonContainer from '../components/containers/ActionButtonContainer.js';
import DetailsContainer from '../components/containers/detailscontainer.js';



class MailbagSecurityDetails extends Component {
    constructor(props) {
        super(props)
    }



    render() {
        return (
            <Fragment>
                <div className="header-panel animated fadeInDown">
                    <FilterContainer />

                </div>
                <div className="section-panel animated fadeInUp">
                    <DetailsContainer />
                </div>
                <div className="footer-panel">
                    <ActionButtonContainer />
                </div>
            </Fragment>
        );
    }




}





export default MailbagSecurityDetails;

