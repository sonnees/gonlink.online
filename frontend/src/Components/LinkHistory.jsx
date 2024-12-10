import React, { useState } from 'react'
import { useUser } from '../context/UserContext';
import { useNavigate } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEllipsisVertical } from '@fortawesome/free-solid-svg-icons';
import Cookies from 'js-cookie';
import Popup from './Popup';

import { RiDeleteBinLine } from "react-icons/ri";
import { FaCheck } from "react-icons/fa6";
import { CiSettings } from "react-icons/ci";
import { TbLetterX } from "react-icons/tb";

export default function LinkHistory({link, onDelete, updateStatus, loadLinkHistory, index}) {
    console.log(index);
    const [dropdownOpen, setDropdownOpen] = useState(false);
    const navigate = useNavigate();
    const [isPopupOpen, setIsPopupOpen] = useState(false);

    const openPopup = () => {
        setIsPopupOpen(true);
    };

    const closePopup = () => {
        setIsPopupOpen(false);
        loadLinkHistory();
    };

    const formatDate = (dateString) => {
        // Kiểm tra nếu dateString không hợp lệ hoặc không có giá trị
        if (!dateString) return ''; // Trả về chuỗi rỗng nếu dateString không hợp lệ
        
        const date = new Date(dateString);
        
        // Kiểm tra nếu date là 'Invalid Date'
        if (isNaN(date)) return ''; // Trả về chuỗi rỗng nếu date không hợp lệ
        
        const day = String(date.getDate()).padStart(2, '0'); // Đảm bảo ngày có 2 chữ số
        const month = String(date.getMonth() + 1).padStart(2, '0'); // Đảm bảo tháng có 2 chữ số
        const year = date.getFullYear();
        const hours = String(date.getHours()).padStart(2, '0'); // Đảm bảo giờ có 2 chữ số
        const minutes = String(date.getMinutes()).padStart(2, '0'); // Đảm bảo phút có 2 chữ số
    
        // Trả về định dạng theo dd/mm/yyyy hh:mm
        return `${day}/${month}/${year}`;
    };

    const formatDateTime = (dateString) => {
        // Kiểm tra nếu dateString không hợp lệ hoặc không có giá trị
        if (!dateString) return ''; // Trả về chuỗi rỗng nếu dateString không hợp lệ
    
        // Thay thế dấu cách giữa ngày và giờ thành dấu "T" để đảm bảo đúng định dạng ISO 8601
        const formattedDate = dateString.replace(' ', 'T');
        
        const date = new Date(formattedDate);
    
        // Kiểm tra nếu date là 'Invalid Date'
        if (isNaN(date)) return ''; // Trả về chuỗi rỗng nếu date không hợp lệ
    
        // Lấy các thành phần ngày tháng và giờ
        const day = String(date.getDate()).padStart(2, '0'); // Đảm bảo ngày có 2 chữ số
        const month = String(date.getMonth() + 1).padStart(2, '0'); // Đảm bảo tháng có 2 chữ số
        const year = date.getFullYear();
        const hours = String(date.getHours()).padStart(2, '0'); // Đảm bảo giờ có 2 chữ số
        const minutes = String(date.getMinutes()).padStart(2, '0'); // Đảm bảo phút có 2 chữ số
    
        // Trả về định dạng theo dd/mm/yyyy hh:mm
        return `${day}/${month}/${year} ${hours}:${minutes}`;
    };

    const handleClick = () => {
        navigate('/link/detailLink', { state: { link } });
    };

    

  return (
    <div>
        <div className="hidden md:block">
            <div className='w-full flex justify-center'>
                <div className='w-full flex justify-between items-center p-2 hover:bg-slate-100 border' > {/*onClick={handleClick}*/}
                    
                    <div className='w-[5%] flex justify-center'>
                        <p>{index!=null?index+1:""}</p> 
                    </div>

                    <div className='w-[12%] flex justify-center'>
                        <div className='px-2' onClick={openPopup}>
                            <CiSettings />
                        </div>
                        {/* <div className='px-2' onClick={updateStatus}>
                            {link.active?<TbLetterX className='text-red-500'/>:<FaCheck className='text-green-500'/>}
                        </div> */}
                        <div className='px-2' onClick={onDelete}>
                            <RiDeleteBinLine className='text-red-500'/>
                        </div>  
                    </div>

                    <div className='w-[25%] flex flex-col justify-center' onClick={handleClick}>
                        <p className='truncate w-64'>{link.originalUrl}</p>
                        <p className='truncate w-60'>{`${process.env.HOST_PAGE}` + "/" + link.shortCode}</p>   
                    </div>
                    
                    
                    <div className='w-[8%] flex justify-center'>
                        <p>{formatDate(link.trafficDate)}</p>
                    </div>

                    <div className='w-[12%] flex justify-center'>
                        <p>{formatDateTime(link.timeExpired)}</p>
                    </div>

                    <div className='w-[7%] flex justify-center'>
                        <input type="checkbox" checked={link.isUsingPassword} disabled className="form-checkbox h-5 w-5 text-blue-600" />
                    </div>

                    <div className='w-[7%] flex justify-center'>
                        <input type="checkbox" checked={link.active} onChange={(e) => updateStatus(e.target.checked)} className="form-checkbox h-5 w-5 text-blue-600" />
                    </div>

                    <div className='w-[15%] flex justify-center'>
                        <p>{link.maxUsage==="0"?"Vô hạn":link.maxUsage}</p>
                    </div>
                    
                    <div className='cursor-pointer hover:text-blue-500 w-[9%] flex justify-center' onClick={handleClick}>
                        <p>{link.traffic} Click</p>
                    </div>

                    {/* <div onClick={() => setDropdownOpen(!dropdownOpen)} className="w-[5%] flex justify-center">
                        <div className="cursor-pointer relative">
                            <FontAwesomeIcon icon={faEllipsisVertical} className="flex items-center hover:font-bold cursor-pointer "/>
                        </div>

                        <div onMouseLeave={() => setDropdownOpen(!dropdownOpen)}>
                            {dropdownOpen && (
                                <ul className="absolute right-0 mt-2 w-42 bg-white shadow-lg rounded-lg z-10">
                                    <li className="block px-4 py-2 text-gray-800 hover:bg-slate-400 rounded-lg">
                                        <div onClick={updateStatus}>Thay đổi trạng thái</div>
                                    </li>

                                    <li className="block px-4 py-2 text-gray-800 hover:bg-slate-400 rounded-lg">
                                        <div onClick={openPopup}>Cập nhật đường dẫn</div>
                                    </li>

                                    <li className="block px-4 py-2 text-gray-800 hover:bg-slate-400 rounded-lg">
                                        <div onClick={onDelete}>Xóa link</div>
                                    </li>
                                </ul>
                            )}
                        </div>
                    </div> */}

                    <Popup isOpen={isPopupOpen} onClose={closePopup} title="Cập nhật cho đường dẫn" link={link}/>

                    
                </div>
            </div>
        </div>

        <div className="block md:hidden">
            <div className='flex justify-center w-screen p-1 bg-gray-100'>
                <div className='w-full flex justify-between m-1 p-2 rounded-md border bg-white'>
                    
                    <div className='flex-col flex justify-center'>
                        <a href={link.originalUrl}><p className='truncate w-60'>{link.originalUrl}</p></a>
                        <br />
                        <a href={`${process.env.HOST_PAGE}` + "/" + link.shortCode}><p className='truncate w-80 text-blue-500 text-sm'>{`${process.env.HOST_PAGE}` + "/" + link.shortCode}</p></a>     
                         
                        <div className='flex text-sm'>
                            <p >{formatDate(link.trafficDate)}&nbsp;&nbsp;-&nbsp;&nbsp;</p>
                            <p className='cursor-pointer hover:text-blue-500 flex justify-center' onClick={handleClick}>{link.traffic} Click</p>
                        </div>

                        <div className='flex text-sm'>
                            <p>Ngày hết hạn: {formatDate(link.timeExpired)}</p>
                        </div>

                        <div className='flex text-sm'>
                            {link.active && (
                                <p className='text-green-500'><span className='text-black'>Trạng thái: </span>Hoạt động</p>
                            )}
                            {!link.active && (
                                <p className='text-red-500'><span className='text-black'>Trạng thái: </span>Ngừng</p>
                            )}
                        </div>

                        <div className='flex text-sm'>
                            <p>Mật khẩu: {link.isUsingPassword?"Có":"Không"}</p>
                            {/* <p>Mật khẩu: <input type="checkbox" disabled checked={!!link.isUsingPassword}/></p> */}
                        </div>

                        <div className='flex text-sm'>
                            <p>Giới hạn truy cập: {link.maxUsage==="0"?"Vô hạn":link.maxUsage}</p>
                        </div>
                    </div>


                    {/* <div className='flex-col w-[30%] flex justify-center'>
                        <a href={link.originalUrl}><p className='truncate w-80'>{link.originalUrl}</p></a>
                        <a href={`${process.env.HOST_PAGE}` + "/" + link.shortCode}><p className='truncate w-80'>{`${process.env.HOST_PAGE}` + "/" + link.shortCode}</p></a>     
                    </div>
                    
                    
                    <div className='w-[10%] flex justify-center'>
                        <p>{formatDate(link.trafficDate)}</p>
                    </div>

                    <div className='w-[10%] flex justify-center'>
                        <p>{link.isUsingPassword?"Có":"Không"}</p>
                    </div>

                    <div className='w-[10%] flex justify-center'>
                        <p>{formatDate(link.timeExpired)}</p>
                    </div>

                    <div className='w-[10%] flex justify-center'>
                        {link.active && (
                            <p className='text-green-500 rounded-full p-2'>Hoạt động</p>
                        )}
                        {!link.active && (
                            <p className='text-red-500 rounded-full p-2'>Ngừng</p>
                        )}
                    </div>

                    <div className='w-[15%] flex justify-center'>
                        <p>{link.maxUsage==="0"?"Vô hạn":link.maxUsage}</p>
                    </div> */}
                    
                    {/* <div className='cursor-pointer hover:text-blue-500 w-[10%] flex justify-center' onClick={handleClick}>
                        <p>{link.traffic} Click</p>
                    </div> */}

                    <div onClick={() => setDropdownOpen(!dropdownOpen)} className="w-[5%] flex justify-center">
                        <div className="cursor-pointer relative">
                            <FontAwesomeIcon icon={faEllipsisVertical} className="flex items-center hover:font-bold cursor-pointer "/>
                        </div>

                        <div onMouseLeave={() => setDropdownOpen(!dropdownOpen)}>
                            {dropdownOpen && (
                                <ul className="absolute right-0 mt-2 w-42 bg-white shadow-lg rounded-lg z-10">
                                    <li className="block px-4 py-2 text-gray-800 hover:bg-slate-400 rounded-lg">
                                        <div onClick={updateStatus}>Thay đổi trạng thái</div>
                                    </li>

                                    <li className="block px-4 py-2 text-gray-800 hover:bg-slate-400 rounded-lg">
                                        <div onClick={openPopup}>Cập nhật đường dẫn</div>
                                    </li>

                                    <li className="block px-4 py-2 text-gray-800 hover:bg-slate-400 rounded-lg">
                                        <div onClick={onDelete}>Xóa link</div>
                                    </li>
                                </ul>
                            )}
                        </div>
                    </div>

                    <Popup isOpen={isPopupOpen} onClose={closePopup} title="Cập nhật cho đường dẫn" link={link}/>

                    
                </div>
            </div>

        </div>
    </div>
  )
}
