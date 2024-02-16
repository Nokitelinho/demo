import React from 'react';
import store from '../store/store';
import App from './App.jsx';
import { initapp } from 'icoreact/lib/ico/framework/component/common/app/init';
import config from '../../../../app.config.js';

class MainPage extends React.Component {

    render() {

        return (
            <App></App>
        );
    }

}
export default initapp({
    store, screenId:'mail.mra.gpabilling.ux.generatepassbillingfile' ,
    screenAction: 'mail/mra/gpabilling/generatepassbillingfile', appConfig:config,showStatusBar:false
})(MainPage);