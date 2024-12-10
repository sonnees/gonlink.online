import React, { useEffect, useState } from 'react'
import { useUser } from '../context/UserContext';
import Cookies from 'js-cookie';
import { useLocation } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faLink, faQrcode } from '@fortawesome/free-solid-svg-icons';
import LineChartCustom from '../Components/LineChartCustom';


export default function DetailLink() {
    const location = useLocation();
    const { link } = location.state;

    console.log(link);

    const {userObject, setUserObject, authenticated, setAuthenticated, email, setEmail, name, setName, avatar, setAvatar, token, setToken} = useUser();
    // console.log(userObject);
    const [isPopupOpen, setIsPopupOpen] = useState(false);
    const [qr, setQr] = useState("");

    const handleClick = async () => {
        try {
            const response = await fetch(
              `${process.env.GET_QR_CODE}`,
              {
                method: "POST",
                headers: {
                  "Content-Type": "application/json",
                  "Authorization": `Bearer ${Cookies.get('token')}`,
                },
                body: JSON.stringify({
                    "content": process.env.HOST_PAGE + "/" + link.shortCode,
                    "width": 200,
                    "height": 200
                }),
              },
            );
      
            if (response.ok) {
              // Xử lý khi API trả về thành công
              const data = await response.json();
              setQr("data:image/png;base64," + data.data.base64Image);
      
            } else {
              
              return ;
            }
        } catch (error) {
            console.error("Error calling API:", error);
        } 

        setIsPopupOpen(true); // Mở popup khi nhấn vào đoạn văn bản
    };

    const handleClosePopup = () => {
        setIsPopupOpen(false); // Đóng popup
    };

    useEffect(() => {
        if (Cookies.get('token')) {
            setName(JSON.parse(localStorage.getItem('userObj')).name)
            setUserObject(JSON.parse(localStorage.getItem('userObj')));
        }
        console.log(link);
        
        localStorage.setItem("link", JSON.stringify(link))
    }, [])

    const formatDate = (dateString) => {
        const date = new Date(dateString);
        return new Intl.DateTimeFormat('vi-VN', {
            day: '2-digit',
            month: 'short',
            year: 'numeric',
        }).format(date);
    };

    // ================================
    const handleSaveImage = () => {
        saveBase64Image(qr, 'qr.png');
    };  

    const saveBase64Image = (base64String, filename = 'image.png') => {
        const link = document.createElement('a');
        link.href = base64String;
        link.download = filename;
    
        // Thêm liên kết vào DOM để kích hoạt việc tải xuống
        document.body.appendChild(link);
        link.click(); // Kích hoạt tải xuống
        document.body.removeChild(link); // Loại bỏ liên kết khỏi DOM
    };


    return (
        <div>
            <div className="hidden md:block">
                <div className='h-[calc(100vh-70px)] flex flex-col items-center bg-gray-100'>
                    <div className='w-[70rem] h-36 flex-col justify-between items-center bg-white border border-gray-300 p-4 rounded-md mt-8 shadow-lg'> 
                        <div className='flex'>
                            <div className='pl-20'>
                                <p className='font-semibold'>Link Gốc</p>
                                <a href={link.originalUrl}><p>{link.originalUrl}</p></a>
                            </div>
                        </div>
                        
                        <div className='flex justify-between items-center'>
                            <div className='text-center pl-10'>
                                <p>{link.traffic}</p>
                                <p className='font-semibold'>Click</p>
                            </div>
                            <div className='text-center'>
                                <p className='font-semibold'>Ngày tạo</p>
                                <p>{formatDate(link.trafficDate)}</p>
                            </div>
                            <div className='flex flex-col justify-center items-end text-center'>
                                {/* <a href={link.originalUrl}><p className='truncate w-80'>{link.originalUrl}</p></a> */}
                                <a href={`${process.env.HOST_PAGE}` + "/" + link.shortCode}><p className='truncate w-80 p-2 font-medium'><FontAwesomeIcon icon={faLink} /> {`${process.env.HOST_PAGE}` + "/" + link.shortCode}</p></a>     
                                <p onClick={handleClick} className='truncate w-80 p-2 font-medium cursor-pointer hover:text-blue-500 hover:rounded-md'><FontAwesomeIcon icon={faQrcode} /> {`${process.env.HOST_PAGE}` + "/" + link.shortCode}</p>                 
                            </div>


                            {isPopupOpen && (
                                <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center">
                                    <div className="bg-white p-4 rounded">
                                        <div className="flex justify-end">
                                            <button onClick={handleClosePopup} className="text-black font-bold">X</button>
                                        </div>
                                        <div className='flex border-2 border-gray-400 rounded-sm p-2 mt-4'> 
                                            <div>
                                                <button onClick={handleSaveImage} className='font-bold text-xl px-3 py-1 rounded-lg bg-blue-500 hover:bg-blue-700 text-white' type="button">Lưu QR code</button>
                                                <p className='flex p-2 text-red-500'>Lưu ý: Những link gốc có nội dung giả mạo, lừa đảo, tín dụng đen, đồi trụy, vi phạm pháp luật </p>
                                                <p className='flex p-2 text-red-500'>sẽ bị xóa mà không cần báo trước.</p>
                                            </div>

                                            <div className='p-1 border-4 border-gray-800 rounded-xl ml-2'>
                                                <img src={qr} alt="QR" style={{ width: 120, height: 120 }} />
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            )}
                        </div>
                    </div>

                    {/* <div className='w-[80rem] h-96 flex-col justify-between items-center border border-gray-300 p-4 rounded-md mt-8 shadow-lg'> */}
                    <div className='w-[70rem] h-[30rem] flex-col justify-between items-center border border-gray-300 p-4 rounded-md mt-8 shadow-lg bg-white'>
                        {Cookies.get('token') && <LineChartCustom token={Cookies.get('token')} link={link}/>}
                    </div>


                </div>
            </div>

            <div className="block md:hidden">
                <div className='flex flex-col items-center bg-gray-100 w-screen p-2'>
                    <div className='w-full flex flex-col justify-between bg-white border border-gray-300 p-4 rounded-md mt-8 shadow-lg'> 
                        <div className='flex'>
                            <div className=''>
                                <p className='font-semibold'>Đường dẫn:</p>
                                <a href={link.originalUrl}><p className='truncate w-80'>{link.originalUrl}</p></a>
                            </div>
                        </div>
                        
                        <div className='flex justify-around'>
                            <div className='text-center'>
                                <p>{link.traffic}</p>
                                <p className='font-semibold'>Click</p>
                            </div>
                            <div className='text-center'>
                                <p className='font-semibold'>Ngày tạo</p>
                                <p>{formatDate(link.trafficDate)}</p>
                            </div>
                        </div>
                        <div className='flex'>
                            <div className=''>
                                {/* <a href={link.originalUrl}><p className='truncate w-80'>{link.originalUrl}</p></a> */}
                                <a href={`${process.env.HOST_PAGE}` + "/" + link.shortCode}><p className='truncate w-72 p-2 font-medium'><FontAwesomeIcon icon={faLink} /> {`${process.env.HOST_PAGE}` + "/" + link.shortCode}</p></a>     
                                <a href={`${process.env.HOST_PAGE}` + "/" + link.shortCode}><p className='truncate w-72 p-2 font-medium'><FontAwesomeIcon icon={faQrcode} /> {`${process.env.HOST_PAGE}` + "/" + link.shortCode + "/qr"}</p></a>                 
                            </div>
                        </div>
                    </div>

                    {/* <div className='w-[80rem] h-96 flex-col justify-between items-center border border-gray-300 p-4 rounded-md mt-8 shadow-lg'> */}
                    <div className='flex-col justify-between items-center border border-gray-300 p-4 rounded-md mt-8 shadow-lg bg-white'>
                        {Cookies.get('token') && <LineChartCustom token={Cookies.get('token')} link={link}/>}
                    </div>


                </div>
            </div>
        </div>
    )
}
