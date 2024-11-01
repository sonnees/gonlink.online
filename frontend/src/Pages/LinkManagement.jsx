import React, { useEffect, useState } from 'react'
import Cookies from 'js-cookie';
import { useUser } from '../context/UserContext';
import { useNavigate } from 'react-router-dom';
import PieChart from '../Components/PieChart';
import GeoChart from '../Components/GeoChart';
import LineChart from '../Components/LineChart';

export default function LinkManagement() {

  const {userObject, setUserObject, authenticated, setAuthenticated, email, setEmail, name, setName, avatar, setAvatar, token, setToken} = useUser();
  const navigate = useNavigate();
  const [totalClick, setTotalClick] = useState(0)
  const [totalLink, setTotalLink] = useState(0)
  const [objData, setObjData] = useState()
  const [traficRealTime, setTraficRealTime] = useState([])


  useEffect(() => {
    if (Cookies.get('token')) {
      getInfo(Cookies.get('token'));
    } else {
      alert("Bạn chưa đăng nhập!");
      navigate("/");
      return ;
    }

    setTotalClick(JSON.parse(localStorage.getItem("userObj")).totalClick)
    setTotalLink(JSON.parse(localStorage.getItem("userObj")).totalShortURL)
    setObjData(JSON.parse(localStorage.getItem("userObj")))
    
  }, [])
  // console.log(objData);

  useEffect(() => {
    const intervalId = setInterval(() => {
      if (Cookies.get('token')) {
        getRealTime(Cookies.get('token'));
      }
    }, 5000);

    // Hủy interval khi component unmount
    return () => clearInterval(intervalId);
  }, []);

  const getRealTime = async (token) => {
    const timeZone = Intl.DateTimeFormat().resolvedOptions().timeZone;
    try {
      const response = await fetch(
        `${process.env.REAL_TIME_ACCOUNT}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`,
          },
          body: JSON.stringify({
            "zoneId": timeZone,
          }),
        },
      );

      if (response.ok) {
        // Xử lý khi API trả về thành công
        const data = await response.json();
        console.log(data.data);
        
        setTraficRealTime(data.data)




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
    // h-[calc(100vh-70px)] 
    <div className='bg-gray-100'>
      <div className='font-bold text-xl pt-5 px-3'>
        Thống kê
      </div>

      <div className='flex justify-between items-center bg-white m-2 rounded-lg'>
        <div className='flex flex-col p-2 w-[20] m-5 text-center rounded-lg bg-white'>
          <div className='font-bold'>Tổng số link được rút gọn</div>
          <div>{totalLink}</div>
        </div>
        <div className='flex flex-col p-2 w-[20] m-5 text-center rounded-lg bg-white'>
          <div className='font-bold'>Tổng lượt truy cập</div>
          <div>{parseInt(totalClick)}</div>
        </div>
        <div className='flex flex-col p-2 w-[20] m-5 text-center rounded-lg bg-white'>
          <div className='font-bold'>Trung bình truy cập / link</div>
          <div>{totalLink !== 0 ? parseInt(totalClick / totalLink) : 'N/A'}</div>
        </div>
      </div>

      <div className='flex m-2'>
        {objData && (
            <div className='w-full bg-white m-2 rounded-lg'>
              <LineChart label="Biểu đồ truy cập theo thời gian thực" labels={Array.from({ length: 60 }, (_, i) => i + 1)} data={traficRealTime.data} width={68} />
            </div>
          ) 
        }
      </div>

      <div className='flex m-2'>
        {objData && (
            <div className='w-72 h-80 bg-white m-2 rounded-lg'>
              <PieChart label={"Biểu đồ khu vực truy cập"} labels={objData.zoneIds.map(item => item.name || "Không xác định")} data={objData.zoneIds.map(item => item.data)}/>
            </div>
          ) 
        }

        {objData && (
            <div className='w-72 h-80 bg-white m-2 rounded-lg'>
              <PieChart label={"Biểu đồ trình duyệt truy cập"} labels={objData.browsers.map(item => item.name || "Không xác định")} data={objData.browsers.map(item => item.data)}/>
            </div>
          ) 
        }

        {objData && (
            <div className='w-72 h-80 bg-white m-2 rounded-lg'>
              <PieChart label={"Biểu đồ hệ điều hành truy cập"} labels={objData.operatingSystems.map(item => item.name || "Không xác định")} data={objData.operatingSystems.map(item => item.data)}/>
            </div>
          ) 
        }

        {objData && (
            <div className='w-72 h-80 bg-white m-2 rounded-lg'>
              <PieChart label={"Biểu đồ loại thiết bị truy cập"} labels={objData.deviceTypes.map(item => item.name || "Không xác định")} data={objData.deviceTypes.map(item => item.data)}/>
            </div>
          ) 
        }
      </div>

      <div className='flex m-2'>
        {objData && (
            <div className='w-full bg-white m-2 rounded-lg'>
              <GeoChart label="Biểu đồ quốc gia truy cập" data={objData.zoneIds} width={68}/>
            </div>
          ) 
        }
      </div>

    </div>
    
  )
}
