import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom';
import { useUser } from '../context/UserContext';
import Cookies from 'js-cookie';

export default function Header() {
  const { authenticated, setAuthenticated, email, setEmail, name, setName, avatar, setAvatar} = useUser();
  const [dropdownOpen, setDropdownOpen] = useState(false);
  const navigate = useNavigate();

  const handleLogout = () => {
    Cookies.remove('token');
    Cookies.remove('sonnees-auth');
    localStorage.clear();
    // navigate("/page/home");
    window.location.href = '/';
  }

  return (
    <div className='flex justify-around items-center bg-gradient-to-r from-blue-400 via-purple-500 to-pink-500 p-1'>
      <img src="/src/assets/Logo.png" alt="" className='w-[180px]' />
      <ul className='flex gap-4 md:gap-14'>
        <li className='hover:font-bold cursor-pointer'> <Link to={"home"}>Trang chủ</Link> </li>
        {name && <li className='hover:font-bold cursor-pointer'> <Link to={"history"}>Lịch sử</Link> </li>}
        <li className='hover:font-bold cursor-pointer'> <Link to={"contact"}>Liên hệ</Link> </li>
        <li className='hover:font-bold cursor-pointer'> <Link to={"about"}>Thông tin</Link> </li>
        <li className='hover:font-bold cursor-pointer relative'> 
          {name?
            <div onClick={() => setDropdownOpen(!dropdownOpen)} className="flex items-center">
              {name}
              {/* <svg className="ml-2 w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 9l-7 7-7-7" />
              </svg> */}
            </div>
          :<Link to={"/login"}>Đăng nhập</Link>}
          {/* <Link to={"/login"}>{name?name:"Đăng nhập"}</Link>  */}

          {dropdownOpen && name && (
            <ul className="absolute right-0 mt-2 w-28 bg-white shadow-lg rounded-lg">
              <li className="block px-4 py-2 text-gray-800">
                <div onClick={handleLogout}>Đăng xuất</div>
              </li>
            </ul>
          )}

        </li>
      </ul>
    </div>
  )
}
