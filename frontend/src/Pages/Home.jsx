import React, { useEffect, useState } from 'react'
import { useLocation, useNavigate } from 'react-router-dom';
import { useUser } from '../context/UserContext';
import Cookies from 'js-cookie';

export default function Home() {
  const navigate = useNavigate();
  const [isLoading, setIsLoading] = useState(false);
  const [errorShortLink, setErrorShortLink] = useState(false);
  const [qr, setQr] = useState("");
  const [error, setError] = useState("");



  //===========================================================================
  const location = useLocation();
  const {userObject, setUserObject, authenticated, setAuthenticated, email, setEmail, name, setName, avatar, setAvatar, token, setToken} = useUser();
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
      // localStorage.setItem('authenticated', tokenParams);
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
        `${process.env.GET_INFO}`,
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
        console.log(data);
        setUserObject(data)
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
    setIsLoading(true);
    try {
      
      const response = await fetch(
        `${process.env.GEN_SHORTCODE}`,
        // `http://localhost:8080/shorten-service/api/v1/generate-shortcode`,
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

      // if (response.status == 401) {
      //   console.error("URL Is Forbidden");
      //   return;
      // }

      // if (response.status == 500) {
      //   console.error("URL Error");
      //   return;
      // }

      if (response.ok) {
        // Xử lý khi API trả về thành công
        const data = await response.json();

        const shortLink = `${process.env.HOST_PAGE}` + data.shortCode;
        console.log(shortLink);
        setErrorShortLink(false)
        setLink(shortLink);
        setIsShortCode(true);
        setQr("data:image/png;base64," + data.base64Image);
        console.log(qr);
        console.log(data);
        

      } else {
        // Xử lý khi API trả về lỗi
        // navigate("/");

        if (response.status === 404) {
          setError("Liên kết không được tìm thấy.");
        } else if (response.status === 500) {
          setError("Những link không hợp lệ và vi phạm chính sách của chúng tôi. Vui lòng thử lại sau.");
        } else if (response.status === 429) {
          setError("Quá nhiều yêu cầu. Vui lòng thực hiện chậm lại.");
        } else {
          setError("Link bạn vừa nhập không hợp lệ hoặc vi phạm chính sách của chúng tôi.");
        }

        setErrorShortLink(true)
        console.error("API call failed");
        return ;
      }
    } catch (error) {
      // Xử lý lỗi khi gọi API
      navigate("/");
      console.error("Error calling API:", error);
    } finally {
      setIsLoading(false);
    }

  }

  const handleGenerateShortLinkWithToken = async (token) => {
    console.log(link);
    setIsLoading(true);
    try {
      
      const response = await fetch(
        `${process.env.ACCOUNT_GEN_SHORTCODE}`,
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

      // if (response.status == 401) {
      //   console.error("URL Is Forbidden");
      //   return;
      // }

      // if (response.status == 500) {
      //   console.error("URL Error");
      //   return;
      // }

      if (response.ok) {
        // Xử lý khi API trả về thành công
        const data = await response.json();

        const shortLink = `${process.env.HOST_PAGE}` + data.shortCode;
        console.log(shortLink);
        setErrorShortLink(false)
        setLink(shortLink);
        setIsShortCode(true);
        setQr("data:image/png;base64," + data.base64Image);
        console.log(qr);

      } else {
        // Xử lý khi API trả về lỗi
        // navigate("/");
        if (response.status === 404) {
          setError("Liên kết không được tìm thấy.");
        } else if (response.status === 500) {
          setError("Những link không hợp lệ và vi phạm chính sách của chúng tôi. Vui lòng thử lại sau.");
        } else if (response.status === 429) {
          setError("Quá nhiều yêu cầu. Vui lòng thực hiện chậm lại.");
        } else {
          setError("Link bạn vừa nhập không hợp lệ hoặc vi phạm chính sách của chúng tôi.");
        }
        setErrorShortLink(true)
        
        console.error("API call failed");
        return ;
      }
    } catch (error) {
      // Xử lý lỗi khi gọi API
      navigate("/");
      console.error("Error calling API:", error);
    } finally {
      setIsLoading(false);
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

  const saveBase64Image = (base64String, filename = 'image.png') => {
    const link = document.createElement('a');
    link.href = base64String;
    link.download = filename;

    // Thêm liên kết vào DOM để kích hoạt việc tải xuống
    document.body.appendChild(link);
    link.click(); // Kích hoạt tải xuống
    document.body.removeChild(link); // Loại bỏ liên kết khỏi DOM
  };

  const handleSaveImage = () => {
    saveBase64Image(qr, 'qr.png');
};


  return (
    <div className='flex justify-center h-screen'>
      <div className='flex-col'>
        <h1 className='flex justify-center pt-28 font-bold text-xl'>Gọn Link - Rút gọn link miễn phí. Dữ liệu lưu giữ vĩnh viễn</h1>
        <div  className='flex-col py-10'>
          <p className='flex justify-center'>Website hỗ trợ rút gọn link, thu gọn link, làm ngắn link, short link hoàn toàn miễn phí, </p>
          <p className='flex justify-center'>với Gonlink.online bạn có thể chia sẻ trên mạng xã hội, google, youtube, facebook, cốc cốc</p>
        </div>

        {errorShortLink && (
          <div className="flex justify-center items-center">
            {/* <div className="loader ease-linear rounded-full border-8 border-t-8 border-gray-200 h-10 w-10"></div> */}
            <div className='flex justify-center p-2 text-red-500'>{error}</div>
          </div>
        )}

        {isShortCode ? 
        <div className='flex justify-between border rounded-full p-3'>
          <input onChange={(e) => setLink(e.target.value)} disabled  type="text" className='w-[70%] focus:outline-none mx-5 bg-white' placeholder='Link cần rút gọn của bạn' value={link}/>
          <button onClick={handleCopyShortLink} className='font-bold text-xl px-5 py-2 rounded-md bg-sky-600 hover:bg-sky-700' type="button">Copy</button>
          <button onClick={() => {navigate("/")}} className='font-bold text-xl px-5 py-2 rounded-full bg-sky-600 hover:bg-sky-700 ml-2' type="button">Mới</button>
        </div>
        : 
        <div className='flex justify-between border rounded-full p-3'>
          <input onChange={(e) => setLink(e.target.value)} type="text" className='w-[70%] focus:outline-none mx-5' placeholder='Link cần rút gọn của bạn' value={link}/>
          <button onClick={handleShortLink} className='font-bold text-xl px-5 py-2 rounded-full bg-sky-600 hover:bg-sky-700' type="button">{isLoading ? 'Đang tạo...' : 'Rút gọn'}</button>
        </div>
        }

        {isLoading && (
          <div className="mt-4 flex justify-center items-center">
            <div className="h-10 w-10 rounded-full border-4 border-t-transparent border-b-transparent border-r-transparent animate-spin"
              style={{background: 'linear-gradient(90deg, #ff7e5f, #feb47b)', clipPath: 'circle(50%)'}}>
            </div>
          </div>
        )}

        {isShortCode && (
          <div className='flex border rounded-sm p-2 mt-4'> 
            <div>
              <button onClick={handleSaveImage} className='font-bold text-xl px-3 py-1 rounded-lg bg-sky-600 hover:bg-sky-700' type="button">Lưu QR code</button>
              <p className='flex p-2 text-red-500'>Lưu ý: Những link gốc có nội dung giả mạo, lừa đảo, tín dụng đen, đồi trụy, vi phạm pháp luật </p>
              <p className='flex p-2 text-red-500'>sẽ bị xóa mà không cần báo trước.</p>

            </div>
            <div className='p-1 border-4 border-gray-800 rounded-md ml-2'>
              <img src={qr} alt="QR" style={{ width: 120, height: 120 }} />
            </div>
          </div>
        )}

      </div>
    </div>
  )
}
