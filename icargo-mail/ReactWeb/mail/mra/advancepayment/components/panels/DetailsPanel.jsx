import React from 'react';
import PaymentBatchContainer from '../containers/paymentbatchcontainer.js';


export default class DetailsPanel extends  React.Component{

    render() {
      return (
            (this.props.displayMode==='list')?
            <div class="section-panel mb-3 animated fadeInUp">       
                    <PaymentBatchContainer/>              
            </div>
            :
            <div class="section-panel mb-3 animated fadeInUp">
  
          
            </div>
        )
    }
}