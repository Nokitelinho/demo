import React, { Component, Fragment } from 'react';
import { SCREEN_MODE } from '../../constants';
import PropTypes from 'prop-types';
export default class DetailsPanel extends Component {
    render() {
        return (
            (this.props.screenMode === SCREEN_MODE.INITIAL) ?
                <Fragment>
                    {/* Details panel initial state elements */}
                </Fragment>
                :
                <Fragment>
                    {  /*Details panel elements */}
                </Fragment>
        )
    }
}

DetailsPanel.propTypes = {
    screenMode: PropTypes.string,
}