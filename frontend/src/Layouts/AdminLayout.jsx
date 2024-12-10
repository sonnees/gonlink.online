import React, { useEffect, useState } from 'react'
import { Link, Outlet, useNavigate } from 'react-router-dom'
import { UserProvider } from '../context/UserContext'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCalendarDays, faHouse, faRightFromBracket } from '@fortawesome/free-solid-svg-icons';
import { MdOutlineOtherHouses } from "react-icons/md";
import { RiCalendarScheduleLine } from "react-icons/ri";
import { RiLogoutBoxLine } from "react-icons/ri";
import Cookies from 'js-cookie';

export default function AdminLayout() {
    const navigate = useNavigate();
    const [menuOpen, setMenuOpen] = useState(false);

    const handleLogout = () => {
        Cookies.remove('token');
        Cookies.remove('sonnees-auth');
        localStorage.clear();
        // navigate("/page/home");
        window.location.href = '/';
    }

  return (
    <div>
        <div className="hidden md:block">
            <div className='flex flex-col h-screen'>
                <UserProvider>
                    <div className='flex items-center bg-gradient-to-r from-blue-200 to-blue-100'>
                        <img src="./../Logo.png" alt="" className='w-[180px] h-[70px] cursor-pointer' onClick={()=>navigate("/")}/>
                    </div>  

                    <div className='flex bg-gray-200'>
                        <div className='w-[20%] bg-gray-200'>
                            <ul className='flex flex-col items-center justify-center p-2 my-5'>
                                <li className='cursor-pointer p-2 hover:bg-blue-500 w-[80%] rounded-md m-1 hover:text-white'>
                                    {/* <Link to={"linkmanagement"}><FontAwesomeIcon icon={faHouse} /> Trang chủ</Link>  */}
                                    <Link to={"linkmanagement"} className='flex items-center space-x-2'><MdOutlineOtherHouses className="w-5 h-5" /> <span>Trang chủ</span></Link> 
                                </li>
                                <li className='cursor-pointer p-2 hover:bg-blue-500 w-[80%] rounded-md m-1 hover:text-white'>
                                    {/* <Link to={"history"}><FontAwesomeIcon icon={faCalendarDays} /> Lịch sử</Link> */}
                                    <Link to={"history"} className='flex items-center space-x-2'><RiCalendarScheduleLine className="w-5 h-5"/> <span>Lịch sử</span></Link>
                                </li>
                                {/* <li className='hover:font-bold cursor-pointer p-2 hover:bg-blue-500 w-[80%] rounded-md m-1'>
                                    <Link to={"contact"}>Liên hệ</Link>
                                </li>
                                <li className='hover:font-bold cursor-pointer p-2 hover:bg-blue-500 w-[80%] rounded-md m-1'>
                                    <Link to={"about"}>Thông tin</Link>
                                </li> */}
                                <li className='cursor-pointer p-2 hover:bg-blue-500 w-[80%] rounded-md m-1 hover:text-white'>
                                    {/* <div onClick={handleLogout}> <FontAwesomeIcon icon={faRightFromBracket} /> Đăng xuất</div> */}
                                    <div onClick={handleLogout} className='flex items-center space-x-2'> <RiLogoutBoxLine className="w-5 h-5"/> <span>Đăng xuất</span></div>
                                </li>
                            </ul>
                        </div>
                        <div className='w-[80%] bg-gray-100 '>
                            <Outlet/>
                        </div>
                    </div>

                </UserProvider>
            </div>
        </div>

        <div className="block md:hidden">
            <div className='flex flex-col h-screen'>
                <UserProvider>
                    <div className='flex justify-between items-center bg-gradient-to-r from-blue-200 to-blue-100'>
                        <img src="./../Logo.png" alt="" className='w-[180px] h-[70px] cursor-pointer' onClick={()=>navigate("/")}/>
                        <button onClick={() => setMenuOpen(!menuOpen)} className="text-blue-500 mx-2">
                            {menuOpen ? '✖️' :'☰'}
                        </button>
                    </div>  

                    {menuOpen && (
                        <ul className='absolute right-0 top-12 w-48 bg-white shadow-lg rounded-lg p-2 z-50' onMouseLeave={() => setMenuOpen(false)}>
                            <li className='cursor-pointer p-2 hover:bg-blue-500 rounded-md m-1'>
                                <Link to={"linkmanagement"} onClick={() => setMenuOpen(false)}><FontAwesomeIcon icon={faHouse} /> Trang chủ</Link> 
                            </li>
                            <li className='cursor-pointer p-2 hover:bg-blue-500 rounded-md m-1'>
                                <Link to={"history"} onClick={() => setMenuOpen(false)}><FontAwesomeIcon icon={faCalendarDays} /> Lịch sử</Link>
                            </li>
                            <li className='cursor-pointer p-2 hover:bg-blue-500 rounded-md m-1'>
                                <div onClick={handleLogout}> <FontAwesomeIcon icon={faRightFromBracket} /> Đăng xuất</div>
                            </li>
                            
                        </ul>
                    )}


                    <div className='flex bg-gray-200'>
                        
                        <div className=''>
                            <Outlet/>
                        </div>
                    </div>

                </UserProvider>
            </div>
        </div>
    </div>
  )
}
