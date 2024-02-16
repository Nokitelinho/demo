import React, { Component, Fragment } from 'react';
import { SCREEN_MODE } from '../../constants';
import PropTypes from 'prop-types';
import TabledetailsPanel from './TabledetailsPanel.jsx';

import BillingTableContainer from '../containers/billingTableContainer.js'

export default class DetailsPanel extends Component {
  render() {
    return (
      (this.props.screenMode === SCREEN_MODE.INITIAL) ?
      <div className="card inner-panel">
          <TabledetailsPanel />
              <BillingTableContainer />
      </div> 
      :
      <div className="card inner-panel">
        <TabledetailsPanel />
        <BillingTableContainer />
      </div>
    )
  }
}

DetailsPanel.propTypes = {
  screenMode: PropTypes.string,
}