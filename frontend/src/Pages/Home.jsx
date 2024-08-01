import React, { useEffect, useState } from 'react'
import { useLocation, useNavigate } from 'react-router-dom';
import { useUser } from '../context/UserContext';
import Cookies from 'js-cookie';

export default function Home() {
  const navigate = useNavigate();




  //===========================================================================
  const location = useLocation();
  const { authenticated, setAuthenticated, email, setEmail, name, setName, avatar, setAvatar, token, setToken} = useUser();
  // const cookies = new Cookies();

  useEffect(() => {
    const searchParams = new URLSearchParams(location.search);
    // const authenticatedParams = searchParams.get('authenticated');
    // const nameParams = searchParams.get('name');
    // const emailParams = searchParams.get('email');
    // const avatarParams = searchParams.get('avatar');
    const tokenParams = searchParams.get('token');

    if (tokenParams!=null) {
      // Lưu các giá trị vào localStorage hoặc state
      localStorage.setItem('authenticated', tokenParams);
      // setName(tokenParams)
      setToken(tokenParams)
      Cookies.set('token', tokenParams, { expires: 1 });
    }

    // if (authenticatedParams!=null&&nameParams!=null&&emailParams!=null&&avatarParams!=null) {
    //   // Lưu các giá trị vào localStorage hoặc state
    //   localStorage.setItem('authenticated', authenticatedParams);
    //   localStorage.setItem('name', nameParams);
    //   localStorage.setItem('email', emailParams);
    //   localStorage.setItem('avatar', avatarParams);

    //   setName(nameParams)
    // }


    // Chuyển hướng đến URL mới mà không chứa các tham số
    navigate("/page/home");
  }, [location.search]);
  //===========================================================================
    
  // Kiểm tra token có tồn tại hay không
  useEffect(() => {
    if (Cookies.get('token')) {
      getInfo(Cookies.get('token'));
    }
  }, [])
  //===========================================================================
  


  const [link, setLink] = useState("");
  const [isShortCode, setIsShortCode] = useState(false);


  const getInfo = async (token) => {
    try {
      const response = await fetch(
        `http://localhost:8080/account-service/api/v1/get-info-account`,
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
        // console.log(data);
        setName(data.name)


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

  const handleShortLink = async () => {
    if (Cookies.get("token")) {
      await handleGenerateShortLinkWithToken(Cookies.get("token"));
    } else {
      await handleGenerateShortLink();
    }
  }

  

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

        const shortLink = "http://localhost:5173/" + data.shortCode;
        console.log(shortLink);

        setLink(shortLink);
        setIsShortCode(true)

      } else {
        // Xử lý khi API trả về lỗi
        navigate("/");
        console.error("API call failed");
        return ;
      }
    } catch (error) {
      // Xử lý lỗi khi gọi API
      navigate("/");
      console.error("Error calling API:", error);
    }

  }

  const handleGenerateShortLinkWithToken = async (token) => {
    console.log(link);

    try {
      
      const response = await fetch(
        `http://localhost:8080/shorten-service/api/v1/account-generate-shortcode`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`,
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

        const shortLink = "http://localhost:5173/" + data.shortCode;
        console.log(shortLink);

        setLink(shortLink);
        setIsShortCode(true)

      } else {
        // Xử lý khi API trả về lỗi
        navigate("/");
        console.error("API call failed");
        return ;
      }
    } catch (error) {
      // Xử lý lỗi khi gọi API
      navigate("/");
      console.error("Error calling API:", error);
    }

  }

  const handleCopyShortLink = () => {
    navigator.clipboard.writeText(link)
      .then(() => {
        alert('Đường liên kết đã được sao chép!');
      })
      .catch(err => {
        console.error('Lỗi khi sao chép đường liên kết: ', err);
      });
  };

  return (
    <div className='flex justify-center h-screen'>
      <div className='flex-col'>
        <h1 className='flex justify-center pt-28 font-bold text-xl'>Gọn Link - Rút gọn link miễn phí. Dữ liệu lưu giữ vĩnh viễn</h1>
        <div  className='flex-col py-10'>
          <p className='flex justify-center'>Website hỗ trợ rút gọn link, thu gọn link, làm ngắn link, short link hoàn toàn miễn phí, </p>
          <p className='flex justify-center'>với Gonlink.online bạn có thể chia sẻ trên mạng xã hội, google, youtube, facebook, cốc cốc</p>
        </div>

        {isShortCode ? 
        <div className='flex justify-between border rounded-full p-3 w-full'>
          <input onChange={(e) => setLink(e.target.value)} disabled  type="text" className='w-[70%] focus:outline-none mx-5' placeholder='Link cần rút gọn của bạn' value={link}/>
          <button onClick={handleCopyShortLink} className='font-bold text-xl px-5 py-2 rounded-full bg-sky-600 hover:bg-orange-500' type="button">Copy</button>
        </div>
        : 
        <div className='flex justify-between border rounded-full p-3 w-full'>
          <input onChange={(e) => setLink(e.target.value)} type="text" className='w-[70%] focus:outline-none mx-5' placeholder='Link cần rút gọn của bạn' value={link}/>
          <button onClick={handleShortLink} className='font-bold text-xl px-5 py-2 rounded-full bg-sky-600 hover:bg-orange-500' type="button">Rút gọn</button>
        </div>
        }

      </div>
    </div>
  )
}
