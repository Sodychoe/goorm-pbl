import React, {useState} from 'react'
import {useNavigate} from 'react-router-dom'
import Form from '../../../components/form/Form'
import {useDispatch} from 'react-redux';

const SignUp = () => {
  const navigate = useNavigate();
  const [firebaseError, setFirebaseError] = useState("");

  const dispatch = useDispatch();

  const handleSignupAndLogin = (email: string, password: string) => {
   
  }

  return (
    <Form
      title={"가입하기"}
      getDataForm={handleSignupAndLogin}
      firebaseError={firebaseError}
    />
  )
}

export default SignUp