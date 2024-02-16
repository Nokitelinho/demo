import React from 'react';
import { Box } from '@material-ui/core';
import TableRow from '@material-ui/core/TableRow';
import TableCell from '@material-ui/core/TableCell';

const ContentPane = (props)=>{
    return(
        <Box m={2}>
        {props.children}
        </Box>
    );
}

const API_ROOT_DEV='http://localhost:5000/release-manager-service'
const WEB_ROOT_DEV='/release-manager-service'
const API_ROOT='/release-manager-service'
const WEB_ROOT='/release-manager-service'

//const API_URL=`${API_ROOT}!=undefined?${API_ROOT}/rest/api:/rest/api`;

const asApiURL = (url)=>{
  if(process.env.NODE_ENV==='development'){
    return `${API_ROOT_DEV}/rest/api${url}`
  }
  return `${API_ROOT}/rest/api${url}`
}

const asWebPath = (url)=> {
  if(process.env.NODE_ENV==='development'){
    return `${WEB_ROOT_DEV}/web${url}`
  }
  return `${WEB_ROOT}/web${url}`
}


 async function  getFromBEnd(relurl = '') {
       const apiUrl = asApiURL(relurl);
       return fetch(apiUrl)
          .then((res) => {
            if(res.ok){
              return res.json()
            }else{
              res.text().then(resTxt=> console.log(`For GET ${apiUrl} got ${resTxt}`));
            }
            return null;
          })
}

 async function  deleteToBEnd(relurl = '') {
       const apiUrl = asApiURL(relurl);
       return fetch(apiUrl,{method: 'DELETE',})
          .then((res) => {
            if(res.ok){
              return 'ok'
            }else{
              res.text().then(resTxt=> console.log(`For DELETE ${apiUrl} got ${resTxt}`));
            }
            return null;
          })
}


const NoListRow = (props)=>{
  const{message} = props;
  return(
    <TableRow>
          <TableCell component="th" scope="row">
            {message}
          </TableCell>
    </TableRow> 
  )                     

}



async function postToBEnd(relurl='',data={}){
  const apiUrl = asApiURL(relurl);  
  const body = JSON.stringify(data);
  console.log(`Posting ${body}`);
  return fetch(apiUrl,
        {
            method: 'POST', // or 'PUT'
            headers: {
                'Content-Type': 'application/json',
            },
            body,
        }
    ).then(response => {
              return {
                ok: response.ok,
                text:response.text()
               };
     }).then(res=>{
              const {ok,text} = res;
                if(ok){
                  alert('Success!!');
                }else{
                  text.then(data=>alert(`Error: ${data}`));
                }
    }); 
  
}



export { NoListRow,ContentPane, getFromBEnd, postToBEnd,deleteToBEnd,asApiURL,asWebPath };