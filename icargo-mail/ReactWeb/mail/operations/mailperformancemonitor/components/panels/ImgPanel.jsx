import React from 'react';
import { Row } from "reactstrap";
import Chart from './Chart.jsx'

export default class ImgPanel extends React.Component {
    constructor(props) {
        super(props);
        this.state = { graphData:this.getChartBarArea() }        
    }

    componentDidUpdate(prevProps) {
        if(prevProps.mailMonitorSummary!=this.props.mailMonitorSummary){
            this.setState({
                graphData: this.getChartBarArea()
            })
        }
    }
    changeGraph = (currentGraph, currentTab,e) => {

        this.props.changeGraph(currentGraph);
        if(e._targetInst.type === 'svg')//if this call came as a result of event propagation, it would be path
        {
           
        this.props.changeTab(currentTab);
    }
    }

    labelFn = (labelProps) => {
        console.log('labelProps ',labelProps);
        return ( <text
                fill={labelProps.fill} 
                x={labelProps.x}
                y={labelProps.y}
                stroke='none'
                alignmentBaseline='middle'
                className='recharts-text recharts-pie-label-text'
                textAnchor='end'>
                <tspan x={labelProps.x} textAnchor={labelProps.textAnchor} dy='0em'>{`${(labelProps.percent * 100).toFixed(0)}%`}</tspan>
                </text> ) ;
    }

       testFn = (tabNameOrShape) =>{
            switch(tabNameOrShape.name){
                
                    case 'Missing Origin Scan':
                           {this.props.changeTab('MISSING_ORIGIN_SCAN'); break;}
                    case 'Missing Deliver Scan':
                           {this.props.changeTab('MISSING_DESTINATION_SCAN'); break;}
                     case 'Missing Both Scan':
                           {this.props.changeTab('MISSING_BOTH_SCAN'); break;}
                    case 'Mailbags Delivered Ontime':
                           {this.props.changeTab('ON_TIME_MAILBAGS'); break;}
                    case 'Mailbags with delayed delivery time':
                           {this.props.changeTab('DELAYED_MAILBAGS'); break;}
                    case 'Raised':
                           {this.props.changeTab('RAISED_MAILBAGS'); break;}
                    case 'Approved':
                           {this.props.changeTab('APPROVED_MAILBAGS'); break;}
                    case 'Rejected':
                           {this.props.changeTab('REJECTED_MAILBAGS'); break;}
                           default: break;
                    
                }
     }

     getChartBarArea=()=>{
        const mailMonitorSummary = this.props.mailMonitorSummary;

        const mailMonitorSummaryServiceFailures = mailMonitorSummary.filter((element) => { if (element.monitoringType === 'SERVICE_FAILURE') return element });
        let dataServiceFailures = [];
        let dataServiceFailure = {}
        if (mailMonitorSummaryServiceFailures.length === 4) {
            dataServiceFailure.fill = "#5b9bd5";
            dataServiceFailure.name = "Missing Origin Scan";
            if(mailMonitorSummaryServiceFailures[0].value>0){
                dataServiceFailure.value = Math.round(mailMonitorSummaryServiceFailures[1].value/mailMonitorSummaryServiceFailures[0].value*100);
            }else{
                dataServiceFailure.value = 0;
            }
            dataServiceFailures[0] = dataServiceFailure;
            dataServiceFailure = {}
            dataServiceFailure.fill = "#ed7d31";
            dataServiceFailure.name = "Missing Deliver Scan";
            if(mailMonitorSummaryServiceFailures[0].value>0){
                dataServiceFailure.value = Math.round(mailMonitorSummaryServiceFailures[2].value/mailMonitorSummaryServiceFailures[0].value*100);
            }else{
                dataServiceFailure.value = 0;
            }
            dataServiceFailures[1] = dataServiceFailure;
            dataServiceFailure = {}
            dataServiceFailure.fill = "#a5a5a5";
            dataServiceFailure.name = "Missing Both Scan";
            if(mailMonitorSummaryServiceFailures[0].value>0){
                dataServiceFailure.value = 100-dataServiceFailures[0].value-dataServiceFailures[1].value;
            }else{
                dataServiceFailure.value = 0;
            }
            dataServiceFailures[2] = dataServiceFailure;

        }

        const mailMonitorSummaryStp = mailMonitorSummary.filter((element) => { if (element.monitoringType === 'ON_TIME_MAILBAGS') return element });
        let dataStp = [];
        let onTimeDeliveryDetails = [];
        let onTimeDelivery = {}
        if (mailMonitorSummaryStp.length == 3) {
            onTimeDelivery.fill = "#5b9bd5";
            onTimeDelivery.name = "Mailbags Delivered Ontime";
            if(mailMonitorSummaryStp[0].value>0){
                onTimeDelivery.value = Math.round(mailMonitorSummaryStp[1].value/mailMonitorSummaryStp[0].value*100);
            }else{
                onTimeDelivery.value = 0;
            }
            onTimeDeliveryDetails[0] = onTimeDelivery;
            onTimeDelivery = {}
            onTimeDelivery.fill = "#ed7d31";
            onTimeDelivery.name = "Mailbags with delayed delivery time";
            if(mailMonitorSummaryStp[0].value>0){
                onTimeDelivery.value = 100-onTimeDeliveryDetails[0].value;
            }else{
                onTimeDelivery.value = 0;
            }
            onTimeDeliveryDetails[1] = onTimeDelivery;

        }

        const mailMonitorSummaryForceMajeure = mailMonitorSummary.filter((element) => { if (element.monitoringType === 'FORCE_MAJEURE') return element });
        let dataForceMajeure = [];
        let forceMajeureDetails = [];
        let forceMajeure = {}
        if (mailMonitorSummaryForceMajeure.length == 4) {
            forceMajeure.fill = "#5b9bd5";
            forceMajeure.name = "Raised";
            if(mailMonitorSummaryForceMajeure[0].value>0){
                forceMajeure.value = Math.round(mailMonitorSummaryForceMajeure[3].value/mailMonitorSummaryForceMajeure[0].value*100);
            }else{
                forceMajeure.value = 0;
            }
            forceMajeureDetails[0] = forceMajeure;
            forceMajeure = {}
            forceMajeure.fill = "#ed7d31";
            forceMajeure.name = "Approved";
            if(mailMonitorSummaryForceMajeure[0].value>0){
                forceMajeure.value = Math.round(mailMonitorSummaryForceMajeure[1].value/mailMonitorSummaryForceMajeure[0].value*100);
            }else{
                forceMajeure.value = 0;
            }
            forceMajeureDetails[1] = forceMajeure;
            forceMajeure = {}
            forceMajeure.fill = "#a5a5a5";
            forceMajeure.name = "Rejected";
            if(mailMonitorSummaryForceMajeure[0].value>0){
                forceMajeure.value = 100-forceMajeureDetails[0].value-forceMajeureDetails[1].value;
            }else{
                forceMajeure.value = 0;
            }
            forceMajeureDetails[2] = forceMajeure;

        }

        let chartData={};
        chartData= {
            'forceMajeureDetails':forceMajeureDetails,
            'onTimeDeliveryDetails':onTimeDeliveryDetails,
            'dataServiceFailures':dataServiceFailures
        }
        return chartData;
        
     }

    render() {


        return (
           
            <Row>
                <div className="col-8 pad-y-md">
                    <div className={this.props.activeGraph === 'ServiceFailuresGraph' ? 'card p-0 active-card-pane mar-l-md' : 'card p-0 mar-l-md'}>
                        <div className="card-body chart-wrapper active chart-grid" 
                            onClick={(e) => this.changeGraph('ServiceFailuresGraph', 'MISSING_ORIGIN_SCAN',e)}>
                            <h4>Service Failures</h4>(In Percentage)
                           
                            { this.state.graphData.dataServiceFailures.length > 0  ?
                                <Chart  margin={{top: 10, right: 100, left: 100, bottom: 10}} data={this.state.graphData.dataServiceFailures} testFn={this.testFn}></Chart> :null}  
                        </div>

                    </div>
                </div>
                <div className="col-8 pad-y-md">
                    <div className={this.props.activeGraph === 'StpGraph' ? 'card p-0 active-card-pane' : 'card p-0'}>
                        <div className="card-body chart-wrapper active chart-grid"
                            onClick={(e) => this.changeGraph('StpGraph', 'ON_TIME_MAILBAGS',e)}>
                           <h4>Station On Time Performance</h4>(In Percentage)
                           { this.state.graphData.onTimeDeliveryDetails.length > 0  ?
                                <Chart margin={{top: 20, right: 100, left: 100, bottom: 10}}  data={this.state.graphData.onTimeDeliveryDetails} accordionClicked={this.props.accordionClicked} testFn={this.testFn}></Chart> :null}
                        </div>
                    </div>
                </div>
                <div className="col-8 pad-y-md">
                    <div className={this.props.activeGraph === 'ForceMajeureGraph' ? 'card p-0 active-card-pane mar-r-md' : 'card p-0 mar-r-md'}>
                        <div className="card-body chart-wrapper active chart-grid"
                            onClick={(e) => this.changeGraph('ForceMajeureGraph', 'RAISED_MAILBAGS',e)}>
                            <h4>Force Majeure Mailbags</h4>(In Percentage)
                            { this.state.graphData.forceMajeureDetails.length > 0  ?
                                <Chart margin={{top: 20, right: 100, left: 100, bottom: 10}}  data={this.state.graphData.forceMajeureDetails} accordionClicked={this.props.accordionClicked} testFn={this.testFn}></Chart> :null}
                        </div>
                    </div>
                </div>

            </Row>

          
        );
    }
}
