import React, { useEffect } from 'react'
import Cookies from 'js-cookie';
import { Link, useNavigate } from 'react-router-dom';
import { useUser } from '../context/UserContext';
import errorImage from './../../src/assets/imageError.png';

export default function PageNotFound() {
  const {userObject, setUserObject, authenticated, setAuthenticated, email, setEmail, name, setName, avatar, setAvatar, token, setToken} = useUser();
  
  const navigate = useNavigate();

  useEffect(() => {
    if (Cookies.get('token')) {
      getInfo(Cookies.get('token'));
    }
  },[])

  const getInfo = async (token) => {
    try {
      const response = await fetch(
        `${process.env.GET_INFO}`,
        // `http://localhost:8080/account-service/api/v1/get-info-account`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`,
          },
          body: JSON.stringify({
            
          }),
        },
      );

      if (response.status == 401) {
        console.error("URL Is Forbidden");
        return;
      }

      if (response.status == 500) {
        console.error("URL Error");
        return;
      }

      if (response.ok) {
        // Xử lý khi API trả về thành công
        const data = await response.json();
        setUserObject(data.data)
        localStorage.setItem('userObj', JSON.stringify(data.data));
        setName(data.data.name)
        setAvatar(data.data.avatar)
        
        


      } else {
        // Xử lý khi API trả về lỗi
        console.error("API call failed");
        return ;
      }
    } catch (error) {
      // Xử lý lỗi khi gọi API
      console.error("Error calling API:", error);
    }
  }

  return (
    <div className="flex flex-col justify-start items-center h-screen">
      <img src={errorImage} alt="Error Image" className="h-[15%] mt-44" />
      <p className="mt-4 text-3xl text-center text-gray-700">Đã xảy ra lỗi! Vui lòng thử lại sau.</p>
    </div>
  )
}
