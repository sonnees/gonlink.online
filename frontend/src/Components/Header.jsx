import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom';
import { useUser } from '../context/UserContext';
import Cookies from 'js-cookie';

export default function Header() {
  const { authenticated, setAuthenticated, email, setEmail, name, setName, avatar, setAvatar} = useUser();
  const [dropdownOpen, setDropdownOpen] = useState(false);
  const [menuOpen, setMenuOpen] = useState(false);
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
    <div>
      <div className="hidden md:block">
        <div className='flex justify-around items-center p-1'>
          <img src="./../Logo.png" alt="" className='w-[180px]' onClick={()=>navigate("/")}/>
          <ul className='flex gap-4 md:gap-14'>
            <li className='flex items-center hover:text-blue-500 cursor-pointer'> <Link to={"home"}>Trang chủ</Link> </li>
            <li className='flex items-center hover:text-blue-500 cursor-pointer'> <Link to={"contact"}>Liên hệ</Link> </li>
            <li className='flex items-center hover:text-blue-500 cursor-pointer'> <Link to={"about"}>Thông tin</Link> </li>
            <li className='cursor-pointer relative'> 
              {name?
                <div onClick={() => setDropdownOpen(!dropdownOpen)} className="flex items-center hover:text-blue-500 cursor-pointer">
                  <img src={avatar} alt="" className='w-[40px] h-[40px] rounded-full'/>
                  &nbsp;&nbsp;{name}
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

      </div>

      <div className="block md:hidden">
        <div className='flex justify-between items-center p-1'>
          <img src="./../Logo.png" alt="" className='w-[180px]' onClick={() => navigate("/")} />
          <button onClick={() => setMenuOpen(!menuOpen)} className="text-blue-500 mx-2">
            {/* Icon để mở/đóng menu */}
            {menuOpen ? '✖️' : name?<img src={avatar} alt="" className='w-[40px] h-[40px] rounded-full' />:'☰'}
          </button>
        </div>

        {menuOpen && (
          <ul className='absolute right-0 top-12 w-48 bg-white shadow-lg rounded-lg p-2 z-50' onMouseLeave={() => setMenuOpen(false)}>
            <li className='flex items-center hover:text-blue-500 cursor-pointer'>
              <Link to={"home"} onClick={() => setMenuOpen(false)}>Trang chủ</Link>
            </li>
            <li className='flex items-center hover:text-blue-500 cursor-pointer'>
              <Link to={"contact"} onClick={() => setMenuOpen(false)}>Liên hệ</Link>
            </li>
            <li className='flex items-center hover:text-blue-500 cursor-pointer'>
              <Link to={"about"} onClick={() => setMenuOpen(false)}>Thông tin</Link>
            </li>
            <li className='cursor-pointer relative'>
              {name ? (
                <div onClick={() => setDropdownOpen(!dropdownOpen)} className="flex items-center hover:text-blue-500 cursor-pointer">
                  {name}
                </div>
              ) : (
                <Link to={"/login"} onClick={() => setMenuOpen(false)}>Đăng nhập</Link>
              )}

              {dropdownOpen && name && (
                <ul className="absolute right-0 mt-2 w-32 bg-white shadow-lg rounded-lg" onMouseLeave={() => setDropdownOpen(!dropdownOpen)}>
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
        )}
      </div>

    </div>
  )
}
