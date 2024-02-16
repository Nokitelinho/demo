import React, { Fragment } from 'react';
import { Row, Col } from "reactstrap";
import { IButton } from 'icoreact/lib/ico/framework/html/elements'
import { IAwbNumber } from 'icoreact/lib/ico/framework/component/business/awb';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form';

class LoadPlanFilterPanel extends React.Component {
    constructor(props) {
        super(props);
        this.toggleFilter = this.toggleFilter.bind(this);
        this.showPopover = this.showPopover.bind(this);
        this.closePopover = this.closePopover.bind(this);
    }
    componentDidMount(){
      
    }

    toggleFilter() {
        this.props.onToggleFilter((this.props.screenMode === 'edit') ? 'display' : 'edit');
    }

    showPopover() {
        this.props.showPopover();
    }

    closePopover() {
        this.props.closePopover();
    }


    render() {

        return (<Fragment>
            <div className="header-filter-panel flippane">
                <div className="pad-md pad-b-3xs">
                    <Row>
                        <Col xs="6" md="4">
                            <div className="form-group">
                                <label className="form-control-label" >AWB No.</label>
                                <IAwbNumber reducerName="awbReducer" hideLabel={true} form="true" name="awbNumber"/>
                            </div>
                        </Col>


                    </Row>
                </div>
             
            </div>
        </Fragment>)
    }
}


export default wrapForm('LoadPlanFlightFilter')(LoadPlanFilterPanel);