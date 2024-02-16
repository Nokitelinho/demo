import React from 'react';
import App from './App.jsx';
import { initapp } from 'icoreact/lib/ico/framework/component/common/app/init'
import config from '../../../../app.config';
import store from '../store/store';
import 'icoreact/lib/ico/framework/assets/styles/scss/main.scss';
import '../styles/scss/offload_mail.scss'


class MainPage extends React.Component {


    render() {
        return (
           
                <App></App>
         
        );
    }
}
export default initapp({store,screenId:'mail.operations.ux.offload',screenAction:'mail/operations/offload',appConfig:config, showStatusBar : true })(MainPage)