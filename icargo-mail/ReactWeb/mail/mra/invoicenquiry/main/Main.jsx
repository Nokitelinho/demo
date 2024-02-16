import React from 'react';
import App from './App.jsx';
import { initapp } from 'icoreact/lib/ico/framework/component/common/app/init'
import config from '../../../../app.config.js';
import store from '../store/store.js';
import 'icoreact/lib/ico/framework/assets/styles/scss/main.scss';
import '../styles/scss/invoice_enquiry.scss'


class MainPage extends React.Component {


    render() {
        return (
           
                <App></App>
         
        );
    }
}
export default initapp({store,screenId:'mail.mra.gpareporting.ux.invoicenquiry',screenAction:'mail/mra/gpareporting/invoicenquiry',appConfig:config})(MainPage)