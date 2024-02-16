
import React,{useState,useEffect} from 'react';
import { Container } from '@material-ui/core';
import { Formik,Field } from 'formik';
import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';
import TextField from '@material-ui/core/TextField';
import MenuItem from '@material-ui/core/MenuItem';
import { KeyboardDatePicker } from '@material-ui/pickers';
import { MuiPickersUtilsProvider } from '@material-ui/pickers';
import MomentUtils from '@date-io/moment';

import { Typography } from '@material-ui/core';
import Button from '@material-ui/core/Button';
import moment from 'moment';


import { useRecoilState } from 'recoil';
import tenantState from '../store/TenantAtom';
import { postToBEnd } from '../components/Common'; 
import { getFromBEnd } from '../components/Common'; 



import commonStyles from '../components/styles';
import clsx from 'clsx';
import { red } from '@material-ui/core/colors/red';


 const STATUSES=  [
      {
            key:"Planned",
            value:"Planned"
        },{
            key:"Complete",
            value:"Complete"
        },{
            key:"Cancelled",
            value:"Cancelled"
        }
   ];

    const ENVIRONMENTS=  [
        {
            key:"Production",
            value:"Production"
        },{
            key:"Staging",
            value:"Staging"
        },{
            key:"Training",
            value:"Training"
        },{
            key:"Integration",
            value:"Integration"
        },{
            key:"ONE",
            value:"ONE"
        },{
            key:"TWO",
            value:"TWO"
        },{
            key:"THREE",
            value:"THREE"
        },{
            key:"FOUR",
            value:"FOUR"
        },{
            key:"FIVE",
            value:"FIVE"
        },{
            key:"SIX",
            value:"SIX"
        },{
            key:"SEVEN",
            value:"SEVEN"
        },{
            key:"EIGHT",
            value:"EIGHT"
        },{
            key:"NINE",
            value:"NINE"
        },{
            key:"TEN",
            value:"TEN"
        },{
            key:"ELEVEN",
            value:"ELEVEN"
        },{
            key:"TWELVE",
            value:"TWELVE"
        }
   ];


const validate = values =>{
  const errors = {};
  if(!values.applicationId){
    errors.applicationId = 'Required';
  }
  if(!values.plannedDate){
    errors.plannedDate = 'Required';
  }
  if(!values.buildNum){
    errors.buildNum = 'Required';
  }

  return errors;
}


const DatePickerField = ({ field, form, ...other }) => {
  const currentError = form.errors[field.name];
  return (
  <MuiPickersUtilsProvider utils={MomentUtils}>
 
    <KeyboardDatePicker
      name={field.name}
      value={field.value}
      format="DD/MM/YYYY"
      helperText={currentError}
      variant='inline'
      error={Boolean(currentError)}
      autoOk
      onError={error => {
        // handle as a side effect
        if (error !== currentError) {
          form.setFieldError(field.name, error);
        }
      }}
      // if you are using custom validation schema you probably want to pass `true` as third argument
      onChange={date => {
            form.setFieldValue(field.name, date, false)
          }
        }
      InputAdornmentProps={{ position: "start" }}  
      {...other}  
    />
  </MuiPickersUtilsProvider>  
  );
};
 

export default function ReleasePackage(props){
    const classes = commonStyles();
    const [tenant, setTenant] = useRecoilState(tenantState);
    const parBuildNum = props.match.params.buildNum;
    const pkgId = props.match.params.packageId;
    const applicationId = props.match.params.applicationId;
    const initials={
           pkgId:-1,
           buildNum:parBuildNum,
           applicationId:applicationId,
           plannedDate:null,
           createdDate:new Intl.DateTimeFormat('en-GB').format(new Date()),
           approvedDate:'',
           customerReference:'',
           remarks:'',
           status:'Planned', 
           environment:'Production',           
         };
  const [formData,setFormData] = useState(initials);

  useEffect(()=>{
    if(pkgId){
        getFromBEnd(`/packages/${tenant.value}/${pkgId}`)
        .then(data=>setFormData({
          pkgId:data.pkgId,
          buildNum:data.bldnum,
          applicationId:data.applicationId,
          plannedDate:data.plannedDate,
          createdDate:moment(data.createDate,"YYYY-MM-DD").format("DD/MM/YYYY"),
          //approvedDate:data,
          customerReference:data.customerChangeRef,
          remarks:data.remarks,
          status:data.status, 
          environment:data.envRef,

          
        }));
    }

  },[])


   const [dsblSavBtn,setDsblSavBtn] = useState(false);


    const onSubmit = async values => {
           setDsblSavBtn(true);
            let rlsPkg={
                pkgId:values.pkgId,
                bldnum:values.buildNum,
                applicationId:values.applicationId,
                tenantId:tenant.value,
                plannedDate:values.plannedDate,
                createDate:moment(values.createdDate,"DD/MM/YYYY").format("YYYY-MM-DD"),
                customerChangeRef:values.customerReference,
                remarks:values.remarks,
                status:values.status,
                envRef:values.environment,
            };
            postToBEnd("/packages",rlsPkg).then(data=>setDsblSavBtn(false));

      };



    return(
        <div className={clsx(classes.margin)} key="mainDepForm">
            <Container disableGutters={true} maxWidth="xl">
             <Formik initialValues={formData} validate={validate} onSubmit={onSubmit} enableReinitialize>
             {formik=>
               <form onSubmit={formik.handleSubmit}>
                <Paper className={classes.paper} key="mainDepPaper">
                 <Grid container direction="column"  >
                  <Grid container   direction="row"  justify="flex-start"> 
                  <Typography variant="h6">Maintain Release Package</Typography>
                  </Grid>
                  <Grid container  direction="row" className={clsx(classes.border,classes.padding)} 
                  justify="flex-start">   
                    {/* <Grid item md={3}>
                        <TextField margin="normal" id="dep-id" label="id" 
                         value={formik.values.id} disabled/>
                    </Grid> */}
                    <Grid item md={2}>
                         <TextField margin="normal" disabled={parBuildNum} error={formik.errors.buildNum!=null} id="build-num" label="Build#"
                         InputLabelProps={{ shrink: true, }}
                         {...formik.getFieldProps('buildNum')}
                         />
                    </Grid>                    
                    <Grid item md={2}>
                        <TextField id="applicationId" disabled label="Application Id" 
                              value={formik.values.applicationId}
                              margin="normal" 
                              fullWidth />
        
                    </Grid>  
                     <Grid item md={1}/>
                    <Grid item md={2}>
                        <TextField error={formik.errors.status} id="status" select label="Status" value={formik.values.status}
                              onChange={formik.handleChange("status")} margin="normal"
                              style={{width:'15ch'}} >
                              {STATUSES?STATUSES.map(status => (
                                  <MenuItem key={status.key} value={status.value}>
                                      {status.value}
                                  </MenuItem>
                                )):''}
                       </TextField>
                    </Grid>    
                  <Grid item md={2}>
                        <TextField id="environment" select label="Environment" value={formik.values.environment}
                              onChange={formik.handleChange("environment")} margin="normal"
                              style={{width:'15ch'}} >
                              {ENVIRONMENTS?ENVIRONMENTS.map(env => (
                                  <MenuItem key={env.key} value={env.value}>
                                      {env.value}
                                  </MenuItem>
                                )):''}
                       </TextField>
                    </Grid>                                                           
                  </Grid>
                  <br></br>
                  <Grid container  direction="column" className={clsx(classes.border)} alignItems="center">   
                  <Grid container  direction="row" className={classes.margin} justify="flex-start">
                    <Grid item md={4}>

                        {/* <DatePicker
                            autoOk
                            label="Planned For"
                            clearable
                            format="DD/MM/YYYY" 
                            value={formik.values.plannedDate}
                            onChange={value => formik.setFieldValue("plannedDate", value)}
                          /> */}
  
                        <Field key="plannedDate" name="plannedDate" component={DatePickerField} 
                         label="Planned For" /> 
                    </Grid>     
                    <Grid item xs={2}>
                     <TextField margin="normal" id="createdDate" 
                         InputLabelProps={{ shrink: true, }}
                        label="Created"  value={formik.values.createdDate} disabled/>
                    </Grid>     
                     <Grid item md={3}>
                   <TextField
                        label="Customer Change Reference#"
                        margin="normal"
                        InputLabelProps={{ shrink: true, }}
                        style={{width:'30ch'}}
                      {...formik.getFieldProps('customerReference')}
                        />
                    </Grid>                    
                  </Grid> 
                    <Grid container  direction="row" className={classes.margin} justify="flex-start">
                                 
                    <Grid item md={6}>
                   <TextField label="Remarks" fullWidth name="remarks" margin="normal"
                        InputLabelProps={{ shrink: true, }} multiline maxrows={2}
                     {...formik.getFieldProps('remarks')}
                        />
                    </Grid>
             
                   </Grid>     
                    <Grid container  direction="row" className={classes.margin} justify="flex-end">
                    <Grid item xs={6}>
                       <Button type="submit" variant="contained" color="primary" disabled={dsblSavBtn}>
                        Save
                        </Button>
                   </Grid>
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