import React, { useEffect, useState } from 'react'
import { useUser } from '../context/UserContext';
import LinkHistory from '../Components/LinkHistory';
import Cookies from 'js-cookie';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCaretLeft, faCaretRight } from '@fortawesome/free-solid-svg-icons';

export default function History() {

  const [historyShortLink, setHistoryShortLink] = useState(null)
  const [pageNumber, setPageNumber] = useState(1)
  const [totalPages, setTotalPages] = useState(1)
  const {userObject, setUserObject, authenticated, setAuthenticated, email, setEmail, name, setName, avatar, setAvatar, token, setToken} = useUser();
  // console.log(userObject);


  const getHistoryAccount = async (token, page) => {
    console.log(page);
    
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
            "page": page - 1,
            "size": 10,
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
        setTotalPages(data.data.pageInfo.totalPages)


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
      getHistoryAccount(Cookies.get('token'), pageNumber)
    }
  }, [])

  useEffect(() => {
    if (Cookies.get('token')) {
      getHistoryAccount(Cookies.get('token'), pageNumber)
    }
  }, [pageNumber])





  return (
    <div className='justify-center h-[calc(100vh-70px)] bg-gray-100'>

      <h1 className='flex justify-center pt-12 font-bold text-xl'>Lịch sử rút gọn link</h1>

      <div className='flex flex-col items-center bg-white m-2 p-2 rounded-lg overflow-auto'>
        {historyShortLink && <div className='flex justify-center'>
          <div className='w-[74rem] flex justify-between items-center border border-gray-300 p-4 rounded-t-md'> 
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

        <div className='h-[440px]'>
          {historyShortLink && historyShortLink.generalTraffic.map((link, index) => (<LinkHistory key={index} link={link}/>))}
        </div>

      </div>

      <div className='flex justify-end items-center m-2 p-2 rounded-lg'>
        <div className='bg-white m-2 p-2 rounded-lg flex'>
          <div className='p-2' onClick={() => setPageNumber((prevPageNumber) => prevPageNumber == 1 ? 1 : prevPageNumber - 1)}><FontAwesomeIcon icon={faCaretLeft} /></div>
          <div className='p-2'>{pageNumber}</div>
          <div className='p-2' onClick={() => setPageNumber((prevPageNumber) => prevPageNumber == totalPages ? totalPages : prevPageNumber + 1)}><FontAwesomeIcon icon={faCaretRight} /></div>
        </div>
      </div>
      

      
        
    </div>
  )
}
