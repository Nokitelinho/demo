import React from 'react';
import App from './App.jsx';
import { initapp } from 'icoreact/lib/ico/framework/component/common/app/init'
import config from '../../../../app.config.js';
import store from '../store/store.js';

class MainPage extends React.Component {


    render() {
        return (
           
                <App></App>
         
        );
    }
}
export default initapp({store,screenId:'mail.mra.receivablemanagement.ux.advancepayment',screenAction:'mail/mra/receivablemanagement/advancepayment',appConfig:config})(MainPage)