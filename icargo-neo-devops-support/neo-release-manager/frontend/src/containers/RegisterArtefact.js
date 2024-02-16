
import React,{useState,useEffect} from 'react';
import { Container } from '@material-ui/core';
import { Formik } from 'formik';
import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';
import TextField from '@material-ui/core/TextField';
import MenuItem from '@material-ui/core/MenuItem';
import Checkbox from '@material-ui/core/Checkbox';
import FormControlLabel from '@material-ui/core/FormControlLabel';

import { Typography } from '@material-ui/core';
import Button from '@material-ui/core/Button';


import { useRecoilState } from 'recoil';
import tenantState from '../store/TenantAtom';
import { postToBEnd } from '../components/Common'; 
import { getFromBEnd } from '../components/Common'; 



import commonStyles from '../components/styles';
import clsx from 'clsx';


 const TYPES=  [
      {
            key:"NODE",
            value:"NODE"
        },{
            key:"JAVA",
            value:"JAVA"
        }
   ];

const SVC_TYPES=[
    'DOMAIN_SERVICE',
    /**
     * Integration Services
     */
    'EAI',
    /**
     * Custom internal services like DB connectors, monitoring etc
     */
    'INTERNAL_SERVICE',
    /**
     * Web BFF
     */
    'WEB_BFF',
    /**
     * Front end services
     */
    'WEB_FE',
    /**
     * Frontend gateway services
     */
    'WEB_GW',
      /**
     * EBL Services
     */
    'EBL',
];
 




 

export default function RegisterArtefact(props){
    const classes = commonStyles();
    const [tenant, setTenant] = useRecoilState(tenantState);
    const artefactId = props.match.params.artefactId;

    let initials = {
           artifactId:artefactId?artefactId:'',
           domain:'',
           port:0,
           healthEndpoint:'',
           serviceType:'DOMAIN_SERVICE',
           serviceTechnology:'JAVA',
           contextPath:'',
           description:'',
           hostsPrivateApi:false,
           hostsPublicApi:false,
           hostsEnterpriseApi:false,
         };



   const [dsblSavBtn,setDsblSavBtn] = useState(false);
   const [formData,setFormData] = useState(initials);

   useEffect(()=>{
       if(artefactId){
            getFromBEnd(`/artifact/${artefactId}`)
                .then(data=>{
                    setFormData(data)
                })
       }
   },[])

    const validate = values =>{
        const errors = {};
        if(!values.artifactId){
            errors.artifactId = 'Required';
        }
        if(!values.domain){
            errors.domain = 'Required';
        }
        if(values.port<1){
            errors.port = 'Required';
        }
        if(!values.contextPath){
            errors.contextPath = 'Required';
        } 

        const badArtifactIdPattern=/[^A-Za-z0-9_-]/i
        if (badArtifactIdPattern.test(values.artifactId)) {
            errors.artifactId  = 'Invalid Artefact Id .Should only contain (A-Za-z0-9-+_)';
        }
        if (badArtifactIdPattern.test(values.domain)) {
            errors.domain  = 'Invalid Domain .Should only contain (A-Za-z0-9-+_)';
        }

        const inError =  errors.artifactId ||  errors.domain || errors.port || errors.contextPath
        setDsblSavBtn(inError?true:false)
        return errors;
    }

    const onSubmit = values => {
        setDsblSavBtn(true);
        postToBEnd(`/artifact/${values.artifactId}`,values).then(data=>setDsblSavBtn(false));
        setDsblSavBtn(false);
    } 
 

    return(
        <div className={clsx(classes.margin)}>
            <Container disableGutters={true} maxWidth="xl">
            <Formik initialValues={formData} validate={validate} onSubmit={onSubmit} enableReinitialize>
            {formik =>
               <form onSubmit={formik.handleSubmit}>
                <Paper className={classes.paper}>
                 <Grid container direction="column"  >
                  <Grid container   direction="row"  justify="flex-start"> 
                  <Typography variant="h6">Maintain Artefact</Typography>
                  </Grid>
                  <Grid container  direction="row" className={clsx(classes.border,classes.margin)} 
                  justify="space-around">   
                        <Grid item md={3}>
                         <TextField margin="normal" fullWidth disabled={artefactId!==undefined} 
                         error={formik.errors.artifactId!=null} id="artefactId" label="Artefact Id"
                         InputLabelProps={{ shrink: true, }}
                         {...formik.getFieldProps('artifactId')}
                         />
                        </Grid>    
                        <Grid item md={1}/>
                    <Grid item md={2}>
                         <TextField margin="normal"  error={formik.errors.domain!=null} id="domain" label="Domain"
                         InputLabelProps={{ shrink: true, }}
                         {...formik.getFieldProps('domain')}
                         />
                    </Grid>   
                            
                   <Grid item md={2}>
                        <TextField id="serviceType" error={formik.errors.serviceType!=null} select label="Type" value={formik.values.serviceType}
                              onChange={formik.handleChange("serviceType")} margin="normal" fullWidth
                               >
                              {SVC_TYPES?SVC_TYPES.map(item => (
                                  <MenuItem key={item} value={item}>
                                      {item}
                                  </MenuItem>
                                )):''}
                       </TextField>
                    </Grid>  

                    <Grid item md={2}>
                        <TextField error={formik.errors.serviceTechnology} id="serviceTechnology" select label="Technology" value={formik.values.serviceTechnology}
                              onChange={formik.handleChange("serviceTechnology")} margin="normal"
                              style={{width:'15ch'}} >
                              {TYPES?TYPES.map(status => (
                                  <MenuItem key={status.key} value={status.value}>
                                      {status.value}
                                  </MenuItem>
                                )):''}
                       </TextField>
                    </Grid>                                      

                  </Grid>
     
                
                    <Grid container  direction="row" className={clsx(classes.border,classes.margin)} 
                        justify="space-around">
                    <Grid item md={1}>
                         <TextField margin="normal"  error={formik.errors.port!=null} id="port" label="Port"
                         InputLabelProps={{ shrink: true, }}
                         {...formik.getFieldProps('port')}
                         />
                    </Grid>   
                    <Grid item md={1}/>
                   <Grid item md={3}>
                         <TextField margin="normal" multiline maxrows={2} id="healthEndpoint" label="Health URL"
                         InputLabelProps={{ shrink: true, }} fullWidth
                         {...formik.getFieldProps('healthEndpoint')}
                         />
                   </Grid>        
                <Grid item md={2}>
                         <TextField margin="normal"  error={formik.errors.contextPath!=null} id="contextPath" label="Context"
                         InputLabelProps={{ shrink: true, }}
                         {...formik.getFieldProps('contextPath')}
                         />
                 </Grid>                      
                <Grid item md={3}>
                         <TextField margin="normal"  multiline maxrows={2}  id="description" label="Description"
                         InputLabelProps={{ shrink: true, }} fullWidth
                         {...formik.getFieldProps('description')}
                         />
                 </Grid>                                 

                  </Grid>
              
                  <Grid container  direction="row" className={clsx(classes.border,classes.margin)} 
                  justify="flex-start"> 
                        <Grid item md={2}>
                              <FormControlLabel
                                control={
                                <Checkbox
                                    checked={formik.values.hostsPrivateApi}
                                    onChange={formik.handleChange}
                                    id="hostsPrivateApi"
                                    color="primary"
                                />
                                }
                                label="Private API"
                            />

                        </Grid>  
                        <Grid item md={2}>
                              <FormControlLabel
                                control={
                                <Checkbox
                                    checked={formik.values.hostsPublicApi}
                                    onChange={formik.handleChange}
                                    id="hostsPublicApi"
                                    color="primary"
                                />
                                }
                                label="Public API"
                            />
                        </Grid>  
                        <Grid item md={2}>
                              <FormControlLabel
                                control={
                                <Checkbox
                                    checked={formik.values.hostsEnterpriseApi}
                                    onChange={formik.handleChange}
                                    id="hostsEnterpriseApi"
                                    color="primary"
                                />
                                }
                                label="Enterprise API"
                            />
                        </Grid>                                               
                
                                  
                  </Grid>
                   <Grid container  direction="row" justify="flex-end">
                     <Grid item xs={1} >
                        <Button type="submit" variant="contained" color="primary" disabled={dsblSavBtn}>
                            Save
                        </Button>
                    </Grid>
                    </Grid>
                 </Grid>
              
                </Paper>
                </form>
            }
                </Formik>
            </Container>
        </div>    
    );
}
