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

  const handleLinkManagement = () => {
    navigate("/link/linkmanagement");
  }

  return (
    <div className='flex justify-around items-center bg-gradient-to-r from-blue-400 via-purple-500 to-pink-500 p-1'>
      <img src="/src/assets/Logo.png" alt="" className='w-[180px]' onClick={()=>navigate("/")}/>
      <ul className='flex gap-4 md:gap-14'>
        <li className='hover:font-bold cursor-pointer'> <Link to={"home"}>Trang chủ</Link> </li>
        {/* {name && <li className='hover:font-bold cursor-pointer'> <Link to={"history"}>Lịch sử</Link> </li>} */}
        <li className='hover:font-bold cursor-pointer'> <Link to={"contact"}>Liên hệ</Link> </li>
        <li className='hover:font-bold cursor-pointer'> <Link to={"about"}>Thông tin</Link> </li>
        <li className='cursor-pointer relative'> 
          {name?
            <div onClick={() => setDropdownOpen(!dropdownOpen)} className="flex items-center">
              {name}
            </div>
          :<Link to={"/login"}>Đăng nhập</Link>}


          {dropdownOpen && name && (
            <ul className="absolute right-0 mt-2 w-32 bg-white shadow-lg rounded-lg">
              <li className="block px-4 py-2 text-gray-800 hover:bg-slate-400 rounded-lg">
              <div onClick={handleLinkManagement}>Link của tôi</div>
              </li>
              <li className="block px-4 py-2 text-gray-800 hover:bg-slate-400 rounded-lg">
                <div onClick={handleLogout}>Đăng xuất</div>
              </li>
            </ul>
          )}

        </li>
      </ul>
    </div>
  )
}
