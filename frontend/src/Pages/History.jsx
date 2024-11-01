import React, { useEffect, useState } from 'react'
import { useUser } from '../context/UserContext';
import LinkHistory from '../Components/LinkHistory';
import Cookies from 'js-cookie';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faAngleLeft, faAngleRight, faCaretLeft, faCaretRight, faGripVertical } from '@fortawesome/free-solid-svg-icons';

export default function History() {

  const [historyShortLink, setHistoryShortLink] = useState(null)
  const [pageNumber, setPageNumber] = useState(1)
  const [totalPages, setTotalPages] = useState(1)
  const {userObject, setUserObject, authenticated, setAuthenticated, email, setEmail, name, setName, avatar, setAvatar, token, setToken} = useUser();
  // console.log(userObject);
  const [loading, setLoading] = useState(false);


  const getHistoryAccount = async (token, page) => {
    console.log(page);
    setLoading(true);
    const timeZone = Intl.DateTimeFormat().resolvedOptions().timeZone;
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
            "size": 7,
            "sortDirection": "ASC",
            "zoneId": timeZone,
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
        console.log(data.data);
        console.log("ngungu");
        

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
    } finally {
      setLoading(false); // Kết thúc loading
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



  const handleRemoveLink = async (token, shortCode) => {
    try {
      const response = await fetch(
        `${process.env.REMOVE_URL}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`,
          },
          body: JSON.stringify({
            "shortCode": shortCode,
          }),
        },
      );
  
      if (response.ok) {
          // Xử lý khi API trả về thành công
          const data = await response.json();
          console.log(data.data);
          // navigate('link/history')

          getHistoryAccount(token, pageNumber)

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

  const handleUpdateStatus = async (token, shortCode, status) => {
    const timeZone = Intl.DateTimeFormat().resolvedOptions().timeZone;
    try {
      const response = await fetch(
        `${process.env.UPDATE_URL}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`,
          },
          body: JSON.stringify({
            "shortCode": shortCode,
            "zoneId": timeZone,
            "active": !status
          }),
        },
      );
  
      if (response.ok) {
          // Xử lý khi API trả về thành công
          const data = await response.json();
          console.log(data.data);
          // navigate('link/history')

          getHistoryAccount(token, pageNumber)

      } else {
          // Xử lý khi API trả về lỗi
          getHistoryAccount(token, pageNumber)
          console.error("API call failed");
          return ;
      }
    } catch (error) {
      // Xử lý lỗi khi gọi API
      console.error("Error calling API:", error);
    } 
  }





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
            <div className='w-[10rem] text-center'>
              <p>Trạng thái</p>
            </div>
            <div>
              <p>Số truy cập</p>
            </div>
            <div>
              <p>&nbsp;</p>
            </div>
          </div>
        </div>}

        <div className='h-[452px]'>
          {/* {historyShortLink && historyShortLink.generalTraffic.map((link, index) => (<LinkHistory key={index} link={link}/>))} */}
          {loading ? (
            <div className='w-20 h-20 border-4 border-t-4 border-t-blue-500 border-gray-300 rounded-full animate-spin mt-10'></div> // Hiển thị loader khi đang tải
          ) : (
            historyShortLink && historyShortLink.generalTraffic.map((link, index) => (
              <LinkHistory key={index} link={link} updateStatus={()=> {handleUpdateStatus(Cookies.get('token'), link.shortCode, link.active)}} onDelete={()=>{handleRemoveLink(Cookies.get('token'), link.shortCode)}} />
            ))
          )}
        </div>

      </div>

      <div className='flex justify-end items-center m-2 p-2 rounded-lg'>
        <div className='bg-white m-2 p-2 rounded-lg flex'>
          <div className='p-2'>{totalPages}</div>
          <div className='p-2'><FontAwesomeIcon icon={faGripVertical} /></div>
          <div className='p-2 cursor-pointer' onClick={() => setPageNumber((prevPageNumber) => prevPageNumber == 1 ? 1 : prevPageNumber - 1)}><FontAwesomeIcon icon={faAngleLeft} /></div>
          <div className='p-2'>{pageNumber}</div>
          <div className='p-2 cursor-pointer' onClick={() => setPageNumber((prevPageNumber) => prevPageNumber == totalPages ? totalPages : prevPageNumber + 1)}><FontAwesomeIcon icon={faAngleRight} /></div>
        </div>
      </div>
      

      
        
    </div>
  )
}
