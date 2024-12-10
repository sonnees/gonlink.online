import React, { useEffect, useState } from 'react';
import { toast } from 'react-toastify';
import Cookies from 'js-cookie';
import { TbLetterX } from "react-icons/tb";

function Popup({ isOpen, onClose, title, link}) {

  const [timeExpired, setTimeExpired] = useState("");
  const [password, setPassword] = useState("");
  const [maxUsage, setMaxUsage] = useState(0);

  const [alias, setAlias] = useState("");
  const [desc, setDesc] = useState("");

  const today = new Date();
  const tomorrow = new Date(today);
  tomorrow.setDate(tomorrow.getDate());
  const minDate = tomorrow.toISOString().slice(0, 16);
  const passTemp="********";

  useEffect(() => {
    setMaxUsage(link.maxUsage)
    setAlias(link.alias)
    setDesc(link.desc)
    setTimeExpired(link.timeExpired.replace(' ', 'T').slice(0, 16));
  }, [])

  useEffect(() => {
    if (isOpen) {
      document.body.style.overflow = 'hidden'; // Ngừng cuộn
    } else {
      document.body.style.overflow = ''; // Cho phép cuộn lại
    }

    // Cleanup khi component bị unmount
    return () => {
      document.body.style.overflow = ''; // Đảm bảo cuộn lại nếu component bị hủy
    };
  }, [isOpen]);

  const handleUpdateShortLinkWithToken = async (token) => {
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
	          zoneId: timeZone,
            shortCode: link.shortCode,
            timeExpired: timeExpired != "" ? timeExpired + ":00Z" : "null",
            password: password,
            maxUsage: maxUsage,
            active: true,
            alias: alias,
            desc: desc
          }),
        },
      );

      if (response.ok) {
        const data = await response.json();
        toast.info('Cập nhật đường dẫn thành công.');
        onClose();
      } else {    
        toast.error('Cập nhật đường dẫn thất bại. Vui lòng thử lại sau.');
        console.error("API call failed");
        return ;
      }
    } catch (error) {
      console.error("Error calling API:", error);
    }

  }

  const handleKeyDown = (e) => {
    // Chặn tất cả các phím nhập liệu (trừ mũi tên lên và xuống)
    if (e.key !== "ArrowUp" && e.key !== "ArrowDown") {
      e.preventDefault();
    }
  };


  if (!isOpen) return null;
  
  return (
    <div>
      <div className="hidden md:block">
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center">
          <div className="bg-white p-6 rounded shadow-lg max-w-4xl w-full relative">
            <button onClick={onClose} className="absolute top-2 right-2 text-gray-500 hover:text-gray-800"><TbLetterX className='text-red-500'/></button>

            <h2 className="text-xl font-bold mb-4">{title} <span className='text-blue-500'>{`${process.env.HOST_PAGE}` + "/" + link.shortCode}</span></h2>
            {/* <div>{JSON.stringify(link)}</div> */}

            <div className='flex items-center justify-between p-2'>
              <div className='flex-col'>
                <p className='font-semibold px-2'> Tên đường dẫn </p>
                <p className='text-sm font-thin px-2'> Bạn có thể tùy chỉnh tên cho đường dẫn của mình để dễ nhớ và sử dụng hiệu quả hơn. </p>
                <input onChange={(e) => setAlias(e.target.value)} value={alias} className='w-[80%] border rounded focus:outline-none p-1 m-2 bg-white' placeholder='Tên đường dẫn'/>
              </div>
              <div className='flex-col'>
                <p className='font-semibold px-2'> Mô tả </p>
                <p className='text-sm font-thin px-2'> Bạn có thể tạo mô tả cho đường dẫn của mình sao cho dễ nhớ và sử dụng hiệu quả. </p>
                <input onChange={(e) => {setDesc(e.target.value)}} value={desc} className='w-[80%] border rounded focus:outline-none p-1 m-2 bg-white' placeholder='Mô tả cho đường dẫn'/>
              </div>
            </div>

            <div className='flex items-center justify-between p-2'>
              <div className='flex-col'>
                <p className='font-semibold px-2'> Giới hạn truy cập </p>
                <p className='text-sm font-thin px-2'> Mặc định, hệ thống sẽ tạo link không có giới hạn truy cập. Bạn có thể đặt giới hạn đó. </p>
                <input onChange={(e) => setMaxUsage(e.target.value)} min={link.traffic} onKeyDown={handleKeyDown} value={maxUsage} type="number" className='w-[80%] border rounded focus:outline-none p-1 m-2 bg-white' placeholder='Giới hạn truy cập'/>
              </div>
              <div className='flex-col'>
                <p className='font-semibold px-2'> Thời gian hiệu lực </p>
                <p className='text-sm font-thin px-2'> Sau 00:00 phút của ngày được chọn, link sẽ không còn hiệu lực. Để trống nếu giữ vĩnh viễn link. </p>
                <input onChange={(e) => {setTimeExpired(e.target.value); console.log(timeExpired);}} value={timeExpired}  type="datetime-local" min={minDate} className='w-[80%] border rounded focus:outline-none p-1 m-2 bg-white' placeholder='MM/DD/YYYY'/>
              </div>
              <div className='flex-col'>
                <p className='font-semibold px-2'> Mật khẩu bảo vệ </p>
                <p className='text-sm font-thin px-2'> Đặt mật khẩu để bảo vệ link rút gọn. Nhập "null" nếu bạn không muốn đặt mật khẩu.</p>
                <input 
                  onChange={(e) => {
                    const value = e.target.value;
                    const normalizedValue = value.normalize("NFD").replace(/[\u0300-\u036f]/g, "").replace(/\s+/g, "");
                    setPassword(normalizedValue);
                  }}  
                  type="text" 
                  className='w-[80%] border rounded focus:outline-none p-1 m-2 bg-white' 
                  placeholder={link.isUsingPassword?passTemp:'Mật khẩu bảo vệ'}
                />
              </div>
            </div>
            <div className='flex items-center justify-between p-2 mt-3'>
              <button onClick={onClose} className="mt-4 px-4 py-2 bg-red-500 text-white rounded hover:bg-red-700">Đóng</button>
              <button onClick={()=>handleUpdateShortLinkWithToken(Cookies.get("token"))} className="mt-4 px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-700">Cập nhật</button>
            </div>
          </div>
        </div>
      </div>

      <div className="block md:hidden">
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-40">
          <div className="bg-white p-6 rounded shadow-lg max-w-4xl w-full relative z-50">
            <button onClick={onClose} className="absolute top-2 right-2 text-gray-500 hover:text-gray-800">&times;</button>

            <h2 className="text-xl font-bold mb-4">{title} <span className='text-blue-500'>{`${process.env.HOST_PAGE}` + "/" + link.shortCode}</span></h2>
            {/* <div>{JSON.stringify(link)}</div> */}

            <div className='flex-col items-center justify-between p-2 mt-3'>
              <div className='flex-col'>
                <p className='font-semibold px-2'> Giới hạn truy cập </p>
                <p className='text-sm font-thin px-2'> Mặc định, hệ thống sẽ tạo link không có giới hạn truy cập. Bạn có thể đặt giới hạn đó. </p>
                <input onChange={(e) => setMaxUsage(e.target.value)} min={link.traffic} onKeyDown={handleKeyDown} value={maxUsage} type="number" className='w-[80%] border rounded focus:outline-none p-1 m-2 bg-white' placeholder='Giới hạn truy cập'/>
              </div>
              <div className='flex-col'>
                <p className='font-semibold px-2'> Thời gian hiệu lực </p>
                <p className='text-sm font-thin px-2'> Sau 00:00 phút của ngày được chọn, link sẽ không còn hiệu lực. Để trống nếu giữ vĩnh viễn link. </p>
                <input onChange={(e) => {setTimeExpired(e.target.value+"T00:00:00Z"); console.log(e.target.value+timeExpired);}}  type="date" min={minDate} className='w-[80%] border rounded focus:outline-none p-1 m-2 bg-white' placeholder='MM/DD/YYYY'/>
              </div>
              <div className='flex-col'>
                <p className='font-semibold px-2'> Mật khẩu bảo vệ </p>
                <p className='text-sm font-thin px-2'> Đặt mật khẩu để bảo vệ link rút gọn. Để trống nếu bạn không muốn đặt mật khẩu.</p>
                <input onChange={(e) => setPassword(e.target.value)}  type="text" className='w-[80%] border rounded focus:outline-none p-1 m-2 bg-white' placeholder='Mật khẩu bảo vệ'/>
              </div>
            </div>
            <div className='flex items-center justify-between p-2 mt-3'>
              <button onClick={onClose} className="mt-4 px-4 py-2 bg-red-500 text-white rounded hover:bg-red-700">Đóng</button>
              <button onClick={()=>handleUpdateShortLinkWithToken(Cookies.get("token"))} className="mt-4 px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-700">Cập nhật</button>
            </div>
          </div>
        </div>

      </div>
    </div>
  );
}

export default Popup;
