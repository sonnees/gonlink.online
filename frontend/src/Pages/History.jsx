import React from 'react'
import { useUser } from '../context/UserContext';
import LinkHistory from '../Components/LinkHistory';

export default function History() {
    const {userObject, setUserObject, authenticated, setAuthenticated, email, setEmail, name, setName, avatar, setAvatar, token, setToken} = useUser();
    console.log(userObject);

  return (
    <div className='justify-center'>
        
        <h1 className='flex justify-center pt-12 font-bold text-xl'>Lịch sử rút gọn link</h1>

        {userObject && userObject.urls.filter((_, index) => (index + 1) % 10 === 0).map((link, index) => (<LinkHistory key={index} link={link}/>))}

        
    </div>
  )
}
