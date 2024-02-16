import React, { Component, Fragment } from 'react';
import AwbDetailsTable from './AwbDetailsTable.jsx';
import { Constants } from '../../constants/constants.js';

export default class DetailsPanel extends Component {

    render() {

        return (

                <Fragment>
                    <div class="card animated fadeInUp">
                        <AwbDetailsTable awbDetails={this.props.awbDetails} attachAwb={this.props.attachAwb}
                            getNewPage={this.props.listAwbDetails} oneTimeValues={this.props.oneTimeValues} />
                    </div>
                </Fragment>

        )
    }
}