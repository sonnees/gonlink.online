import React, { useEffect, useState } from 'react'
import { useUser } from '../context/UserContext';
import LinkHistory from '../Components/LinkHistory';
import Cookies from 'js-cookie';

export default function History() {

  const [historyShortLink, setHistoryShortLink] = useState(null)
  const {userObject, setUserObject, authenticated, setAuthenticated, email, setEmail, name, setName, avatar, setAvatar, token, setToken} = useUser();
  // console.log(userObject);


  const getHistoryAccount = async (token) => {
    try {
      const response = await fetch(
        `${process.env.ACCOUNT_GET_HISTORY}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`,
          },
          body: JSON.stringify({
            "page": 0,
            "size":10,
            "sortDirection": "ASC"
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
        console.log(data);
        setHistoryShortLink(data.data)


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


  useEffect(() => {
    setName(JSON.parse(localStorage.getItem('userObj')).name)
    if (Cookies.get('token')) {
      getHistoryAccount(Cookies.get('token'))
    }
  }, [])





  return (
    <div className='justify-center h-[calc(100vh-70px)]'>

        
        <h1 className='flex justify-center pt-12 font-bold text-xl'>Lịch sử rút gọn link</h1>
        {historyShortLink && <div className='flex justify-center'>
          <div className='w-[64rem] flex justify-between items-center border border-gray-300 p-4 rounded-md mt-2'> 
            <div>
              <p className='truncate w-80'>Link / Short Link</p>
            </div>
            <div>
              <p>Ngày tạo</p>
            </div>
            <div>
              <p>Số truy cập</p>
            </div>
          </div>
        </div>}
        {historyShortLink && historyShortLink.generalTraffic.map((link, index) => (<LinkHistory key={index} link={link}/>))}

        
    </div>
  )
}
