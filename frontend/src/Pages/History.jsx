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
            "sortDirection": "DESC",
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

  const loadLinkHistory = () => {
    if (Cookies.get('token')) {
      getHistoryAccount(Cookies.get('token'), pageNumber)
    }
  };



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
    <div>
      <div className="hidden md:block">
        <div className='justify-center bg-gray-100'>

          <h1 className='flex justify-center pt-12 font-bold text-xl'>Lịch sử rút gọn link</h1>

          <div className='w-full flex flex-col items-center bg-white p-2 rounded-lg overflow-auto'>
            {historyShortLink && <div className='w-full flex justify-center'>
              <div className='w-full flex justify-between items-center border border-gray-300 p-4 rounded-t-md'> 
                <div className='w-[5%] flex justify-center'>
                  <p>STT</p>
                </div>
                <div className='w-[12%] flex justify-around'>
                  <p>Thao tác</p>
                </div>
                <div className='w-[25%] flex justify-center'>
                  <p className='truncate w-80'>Link / Short Link</p>
                </div>
                <div className='w-[8%] flex justify-center'>
                  <p>Ngày tạo</p>
                </div>
                <div className='w-[12%] flex justify-center'>
                  <p>Ngày hết hạn</p>
                </div>
                <div className='w-[7%] flex justify-center'>
                  <p>Mật khẩu</p>
                </div>

                <div className='w-[7%] flex justify-center'>
                  <p>Trạng thái</p>
                </div>
                <div className='w-[15%] flex justify-center'>
                  <p>Giới hạn truy cập</p>
                </div>
                <div className='w-[9%] flex justify-center'>
                  <p>Số truy cập</p>
                </div>
              </div>
            </div>}

            <div className='h-[452px] w-full'>
              {/* {historyShortLink && historyShortLink.generalTraffic.map((link, index) => (<LinkHistory key={index} link={link}/>))} */}
              {loading ? (
                <div className='flex justify-center'>
                  <div className='w-20 h-20 border-4 border-t-4 border-t-blue-500 border-gray-300 rounded-full animate-spin mt-10'></div> 
                </div>
              ) : (
                historyShortLink && historyShortLink.generalTraffic.map((link, index) => (
                  <LinkHistory key={index} index={index} link={link} loadLinkHistory={loadLinkHistory} updateStatus={()=> {handleUpdateStatus(Cookies.get('token'), link.shortCode, link.active)}} onDelete={()=>{handleRemoveLink(Cookies.get('token'), link.shortCode)}} />
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

      </div>
      
      <div className="block md:hidden">
        <div className='justify-center bg-gray-100 w-screen h-screen'>

          <h1 className='flex justify-center pt-4 font-bold text-xl'>Lịch sử rút gọn link</h1>

          <div className='flex flex-col items-center rounded-lg overflow-auto'>
            {/* {historyShortLink && <div className='flex justify-center'>
              <div className='w-[74rem] flex justify-between items-center border border-gray-300 p-4 rounded-t-md'> 
                <div className='w-[30%] flex justify-center'>
                  <p className='truncate w-80'>Link / Short Link</p>
                </div>
                <div className='w-[10%] flex justify-center'>
                  <p>Ngày tạo</p>
                </div>
                <div className='w-[10%] flex justify-center'>
                  <p>Mật khẩu</p>
                </div>
                <div className='w-[10%] flex justify-center'>
                  <p>Ngày hết hạn</p>
                </div>
                <div className='w-[10%] flex justify-center'>
                  <p>Trạng thái</p>
                </div>
                <div className='w-[15%] flex justify-center'>
                  <p>Giới hạn truy cập</p>
                </div>
                <div className='w-[10%] flex justify-center'>
                  <p>Số truy cập</p>
                </div>
                <div className='w-[5%] flex justify-center'>
                  <p>&nbsp;</p>
                </div>
              </div>
            </div>} */}

            <div className=''>
              {/* {historyShortLink && historyShortLink.generalTraffic.map((link, index) => (<LinkHistory key={index} link={link}/>))} */}
              {loading ? (
                <div className='w-20 h-20 border-4 border-t-4 border-t-blue-500 border-gray-300 rounded-full animate-spin mt-10'></div> // Hiển thị loader khi đang tải
              ) : (
                historyShortLink && historyShortLink.generalTraffic.map((link, index) => (
                  <LinkHistory key={index} link={link} loadLinkHistory={loadLinkHistory} updateStatus={()=> {handleUpdateStatus(Cookies.get('token'), link.shortCode, link.active)}} onDelete={()=>{handleRemoveLink(Cookies.get('token'), link.shortCode)}} />
                ))
              )}
            </div>

          </div>

          <div className='flex justify-end items-center bg-gray-100'>
            <div className='bg-white m-2 p-2 rounded-lg flex'>
              <div className='p-2'>{totalPages}</div>
              <div className='p-2'><FontAwesomeIcon icon={faGripVertical} /></div>
              <div className='p-2 cursor-pointer' onClick={() => setPageNumber((prevPageNumber) => prevPageNumber == 1 ? 1 : prevPageNumber - 1)}><FontAwesomeIcon icon={faAngleLeft} /></div>
              <div className='p-2'>{pageNumber}</div>
              <div className='p-2 cursor-pointer' onClick={() => setPageNumber((prevPageNumber) => prevPageNumber == totalPages ? totalPages : prevPageNumber + 1)}><FontAwesomeIcon icon={faAngleRight} /></div>
            </div>
          </div>

        </div>

      </div>
    </div>
  )
}
