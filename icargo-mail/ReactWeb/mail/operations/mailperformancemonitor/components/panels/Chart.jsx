import React from 'react';
import { IBarChart, IBar } from 'icoreact/lib/ico/framework/component/common/charts';

export default class Chart extends React.Component {

    testFn = (id) => {
        this.props.testFn(id);
    }    

    render() {

        console.log('render>>')
        return (

            this.props.data.length > 0 ? <IBarChart data={this.props.data}
                dataKey="name"
                showGrid={false}
                showTooltip={true}
               
                showLegend={false}
                layout="vertical"
                xAxis={{ hide: true }}
                yAxis={{ tickProps: { textAnchor: 'end' } }}
                margin={this.props.margin}
            >
                <IBar dataKey="value" fill="rgba(39, 163, 223, 1)" barSize={25} 
                onClick={(activeShape) => this.testFn(activeShape)}
                label={{ position: "right" }} />
            </IBarChart> : null



        );
    }
}