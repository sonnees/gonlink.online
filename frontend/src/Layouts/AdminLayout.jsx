import React from 'react'
import { Link, Outlet, useNavigate } from 'react-router-dom'
import { UserProvider } from '../context/UserContext'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCalendarDays, faHouse, faRightFromBracket } from '@fortawesome/free-solid-svg-icons';
import Cookies from 'js-cookie';

export default function AdminLayout() {
    const navigate = useNavigate();

    const handleLogout = () => {
        Cookies.remove('token');
        Cookies.remove('sonnees-auth');
        localStorage.clear();
        // navigate("/page/home");
        window.location.href = '/';
    }

  return (
    <div className='flex flex-col h-screen'>
      <UserProvider>
        <div className='flex items-center bg-gradient-to-r from-blue-400 via-purple-500 to-pink-500 px-10'>
            <img src="/src/assets/Logo.png" alt="" className='w-[180px] h-[70px] cursor-pointer' onClick={()=>navigate("/")}/>
        </div>  

        <div className='flex'>
            <div className='w-[20%] bg-black text-white h-[calc(100vh-70px)]'>
                <ul className='flex flex-col items-center justify-center p-2 my-5'>
                    <li className='hover:font-bold cursor-pointer p-2 hover:bg-blue-500 w-[80%] rounded-md m-1'>
                        <Link to={"linkmanagement"}><FontAwesomeIcon icon={faHouse} /> Trang chủ</Link> 
                    </li>
                    <li className='hover:font-bold cursor-pointer p-2 hover:bg-blue-500 w-[80%] rounded-md m-1'>
                        <Link to={"history"}><FontAwesomeIcon icon={faCalendarDays} /> Lịch sử</Link>
                    </li>
                    {/* <li className='hover:font-bold cursor-pointer p-2 hover:bg-blue-500 w-[80%] rounded-md m-1'>
                        <Link to={"contact"}>Liên hệ</Link>
                    </li>
                    <li className='hover:font-bold cursor-pointer p-2 hover:bg-blue-500 w-[80%] rounded-md m-1'>
                        <Link to={"about"}>Thông tin</Link>
                    </li> */}
                    <li className='hover:font-bold cursor-pointer p-2 hover:bg-blue-500 w-[80%] rounded-md m-1'>
                        <div onClick={handleLogout}> <FontAwesomeIcon icon={faRightFromBracket} /> Đăng xuất</div>
                    </li>
                </ul>
            </div>
            <div className='w-[80%]'>
                <Outlet/>
            </div>
        </div>

      </UserProvider>
    </div>
  )
}
