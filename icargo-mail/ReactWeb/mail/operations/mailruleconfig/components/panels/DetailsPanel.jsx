import React, { Component, Fragment } from 'react';
import { SCREEN_MODE } from '../../constants';
import PropTypes from 'prop-types';
import TabledetailsPanel from './TabledetailsPanel.jsx';

import MailRuleTableContainer from '../containers/mailruletablecontainer.js'

export default class DetailsPanel extends Component {
    render() {
        return (
              
            <div className="card inner-panel">
                    
                        <TabledetailsPanel />
                        
                <div className="card-body p-0 d-flex">
                        <MailRuleTableContainer />
                        </div>
                </div>

        )
    }
}

DetailsPanel.propTypes = {
    screenMode: PropTypes.string,
}