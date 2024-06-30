import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom';

export default function Home() {

  const [link, setLink] = useState("");

  const navigate = useNavigate();

  const handleGenerateShortLink = async () => {
    console.log(link);

    try {
      const response = await fetch(
        `http://localhost:8080/shorten-service/api/v1/generate-shortcode`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            originalUrl: link
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
        console.log(data);
      } else {
        // Xử lý khi API trả về lỗi
        navigate("/");
        console.error("API call failed");
      }
    } catch (error) {
      // Xử lý lỗi khi gọi API
      navigate("/");
      console.error("Error calling API:", error);
    }


  }

  return (
    <div className='flex justify-center h-screen'>
      <div className='flex-col'>
        <h1 className='flex justify-center pt-28 font-bold text-xl'>Gọn Link - Rút gọn link miễn phí. Dữ liệu lưu giữ vĩnh viễn</h1>
        <div  className='flex-col py-10'>
          <p className='flex justify-center'>Website hỗ trợ rút gọn link, thu gọn link, làm ngắn link, short link hoàn toàn miễn phí, </p>
          <p className='flex justify-center'>với Gonlink.online bạn có thể chia sẻ trên mạng xã hội, google, youtube, facebook, cốc cốc</p>
        </div>

        <div className='flex justify-between border rounded-full p-3 w-full'>
          <input onChange={(e) => setLink(e.target.value)} type="text" className='w-[70%] focus:outline-none mx-5' placeholder='Link cần rút gọn của bạn'/>
          <button onClick={handleGenerateShortLink} className='font-bold text-xl px-5 py-2 rounded-full bg-sky-600 hover:bg-orange-500' type="button">Rút gọn</button>
        </div>

      </div>
    </div>
  )
}
