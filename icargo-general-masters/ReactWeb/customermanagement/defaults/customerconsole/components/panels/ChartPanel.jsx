import React, { Component, Fragment } from 'react';
import { Row, Col } from "reactstrap";
import { IBarChart, IBar, IPieChart, IAreaChart, IArea } from 'icoreact/lib/ico/framework/component/common/charts';
import { getIndex } from '../../actions/invoiceaction.js'
import PropTypes from 'prop-types';
import {IMoney} from 'icoreact/lib/ico/framework/component/business/money';
import { IMessage } from 'icoreact/lib/ico/framework/html/elements';

class ChartPanel extends Component {
    constructor(props) {
        super(props);
        this.barType = "";

    }
    onMouseOverBarChart=(type)=> {
        this.barType = type
    }
    getBarChartToolTip=(e)=>{
        if (e.active && e.payload != null && e.payload[0] != null) {
            return (<div className="chartpanel-tooltip-wraper">
                        {this.barType === "awbAmount" ?
                <Fragment>
                <div className="chartpanel-header"><IMessage msgkey="customermanagement.defaults.customerconsole.awbvalue"/></div>
                <div className="chartpanel-body">
                    <span className="chartpanel-body-content"><IMoney value={e.payload[0].payload.awbAmount} mode="display" placement="left"/></span>
                </div>
                </Fragment>:
                <Fragment>
                <div className="chartpanel-header"><IMessage msgkey="customermanagement.defaults.customerconsole.awbcount"/></div>
                <div className="chartpanel-body">
                    <span className="chartpanel-body-content">{e.payload[0].payload.awbCount}</span>
                </div>
                </Fragment>
                }
            </div>)
        }
        else {
            return "";
        }
    }
    getPieChartToolTip=(payload)=> {
        return <div className="chartpanel-tooltip-wraper">
                    <div className="chartpanel-header"><IMessage msgkey="customermanagement.defaults.customerconsole.amt"/></div>
                    <div className="chartpanel-body justify-content-between">
                        <span className="chartpanel-body-content d-flex align-items-center pad-r-sm"><IMoney value={`${payload[0].value}`} mode="display" placement="left"/></span>
                        <span className="chartpanel-body-content d-flex align-self-center font-weight-bold">{`${payload[0].payload.amountPercentage}`}%</span>
                    </div>
        </div>

    }

    getAreaChartToolTip=(payload)=> {
        return <div className="chartpanel-tooltip-wraper">
                    <div className="chartpanel-header"><IMessage msgkey="customermanagement.defaults.customerconsole.awb"/></div>
                    <div className="chartpanel-body justify-content-between">
                        <span className="chartpanel-body-content d-flex align-items-center pad-r-sm">{`${payload[0].payload.awbCount}`}</span>
                        <span className="chartpanel-body-content d-flex align-self-center font-weight-bold">{`${payload[0].payload.countPercentage}`}%</span>
                    </div>
        </div>

    }
    getFillColour=(name)=> {
        switch (name) {
            case "0-15": return "#58af2e"
            case "16-30": return "#658356"
            case "31-45": return "#FFBF00"
            case "46-60": return "#FF8000"
            case "61-90": return "#FA5858"
            case "91-120": return "#FF0000"
            case ">120": return "#B40404"
        }

    }

    getChartBarArea=()=>{
         this.awbValueSum = this.props.ageingReceivables.reduce(function (cnt, o) { return cnt + o.value; }, 0);
         this.awbCountSum = this.props.ageingReceivables.reduce(function (cnt, o) { return cnt + o.awbCount; }, 0);
         this.ageingReceivables = this.props.ageingReceivables.map((ageingReceivable) => {
            return {
                ...ageingReceivable,
                fill: this.getFillColour(ageingReceivable.name),
                customLabel: ageingReceivable.name + " DAYS",
                amountPercentage: (ageingReceivable.value * 100 / this.awbValueSum).toFixed(2),
                countPercentage: (ageingReceivable.awbCount * 100 / this.awbCountSum).toFixed(2),
            }
        });
        
        this.outstandingReceivables = this.props.statusView.map(outstandingReceivable => {
            return outstandingReceivable
        })
        this.outstandingReceivables.splice(getIndex('Total', this.outstandingReceivables, 'awbType'), 1)
         
    }

    render() {

        {this.getChartBarArea()};

        return (
            <Row>
                <div className="col-8">
                    <div className="card">
                        <div className="card-header card-header-action">
                            <Col>
                                <h4><IMessage msgkey="customermanagement.defaults.customerconsole.receivagingamts"/></h4>
                            </Col>
                        </div>
                        <div className="card-body svg-area">
                            {this.ageingReceivables.length > 0 ?

                                <IPieChart showTooltip={true} tooltip={{ cursor: true, formatter: this.getPieChartToolTip }}
                                    data={this.ageingReceivables} label="dynamic" showLegend={true} legendTitle="Days"
                                     innerRadius={40} outerRadius={60}/>

                                : ""}

                        </div>
                    </div>

                </div>
                <div className="col-8">
                    <div className="card">
                        <div className="card-header card-header-action">
                            <Col>
                                <h4><IMessage msgkey="customermanagement.defaults.customerconsole.receivawbcount"/></h4>
                            </Col>
                        </div>
                        <div className="card-body svg-area">
                            {this.ageingReceivables.length > 0 ?
                                <IAreaChart
                                    showTooltip={true}
                                    tooltip={{ cursor: true, formatter: this.getAreaChartToolTip }}
                                    showGrid={true}
                                    grid={{ strokeDasharray: "3 3", horizontal: false, vertical: true }}
                                    yAxis={{ hide: true }}
									xAxis={{ hide: false , interval : 'preserveStartEnd' }}
                                    dataKey="name"
                                    data={this.ageingReceivables} >
                                    <IArea dataKey="awbCount" fill="#CC99FF" />
                                </IAreaChart> : ""}



                        </div>
                    </div>

                </div>
                <div className="col-8">
                    <div className="card">
                        <div className="card-header card-header-action">
                            <Col>
                                <h4><IMessage msgkey="customermanagement.defaults.customerconsole.receivbystatus"/></h4>
                            </Col>
                        </div>
                        <Row className="card-body svg-area">
                            {this.outstandingReceivables.length > 0 ?
                            <Fragment>
                                 <Col xs="16"><IMessage msgkey="customermanagement.defaults.customerconsole.awbamount"/></Col>
                                 <Col xs="8" ><IMessage msgkey="customermanagement.defaults.customerconsole.chartpanel.awbcount"/></Col>           
                                 <Col xs="12">
                                <IBarChart data={this.outstandingReceivables} dataKey="awbType"
                                    showGrid={false}
                                    showTooltip={false}
                                    showLegend={false}
                                    layout="vertical"
                                    xAxis={{hide: true}}     
                                    yAxis={{tickProps : { textAnchor : 'end'}}}
                                     >
                                         <IBar dataKey="awbAmount" fill="rgba(39, 163, 223, 1)" radius={[0, 0, 0, 0]} barSize={15} name="AWB Value" label={{position:"insideLeft"}} />
                                    </IBarChart>
                                 </Col>
                                 <Col xs="12">
                                    <IBarChart data={this.outstandingReceivables} dataKey="awbType"
                                    showGrid={false}
                                    showLegend={false}
                                    showTooltip={false}
                                    layout="vertical"
                                    xAxis={{hide: true}} 
                                    yAxis={{tick: false}}
                                    >
                                        <IBar dataKey="awbCount" fill="#58af2e" radius={[0, 0, 0, 0]} barSize={15} name="AWB Count" label={{position:"insideLeft"}} />
                                    </IBarChart>
                                 </Col>
                            </Fragment> : ""}
                        </Row>
                    </div>

                </div>
            </Row>

        )
    }
}

ChartPanel.propTypes = {
    ageingReceivables: PropTypes.array,
    statusView: PropTypes.array
};

export default ChartPanel;