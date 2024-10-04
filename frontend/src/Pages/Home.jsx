import React, { useEffect, useState } from 'react'
import { useLocation, useNavigate } from 'react-router-dom';
import { useUser } from '../context/UserContext';
import Cookies from 'js-cookie';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faGear } from '@fortawesome/free-solid-svg-icons';

export default function Home() {
  const navigate = useNavigate();
  const [isLoading, setIsLoading] = useState(false);
  const [errorShortLink, setErrorShortLink] = useState(false);
  const [qr, setQr] = useState("");
  const [error, setError] = useState("");
  const [settingToggle, setSettingToggle] = useState(false);

  const [customLink, setCustomLink] = useState("");
  const [timeExpired, setTimeExpired] = useState("");
  const [password, setPassword] = useState("");
  const [maxUsage, setMaxUsage] = useState(0);

  const [erorExistShortCode, setErorExistShortCode] = useState("");
  





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
        // `http://localhost:8080/account-service/api/v1/get-info-account`,
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
        setUserObject(data.data)
        localStorage.setItem('userObj', JSON.stringify(data.data));
        setName(data.data.name)
        setAvatar(data.data.avatar)
        
        


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
    setCustomLink("");
    setTimeExpired("");
    setPassword("");
    setMaxUsage(0);
  }

  

  const handleGenerateShortLink = async () => {
    console.log(timeExpired);
    setIsLoading(true);

    const now = new Date();
    const timeZone = Intl.DateTimeFormat().resolvedOptions().timeZone;
    console.log(timeZone);
    

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
            originalUrl: link,
            trafficDate: now.toISOString(),
	          zoneId: timeZone,
            shortCode: customLink,
            timeExpired: timeExpired,
            password: password,
            maxUsage: maxUsage,
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

        const shortLink = `${process.env.HOST_PAGE}`+ "/" + data.data.shortCode;
        console.log(shortLink);
        setErrorShortLink(false)
        setLink(shortLink);
        setIsShortCode(true);
        setQr("data:image/png;base64," + data.data.base64Image);
        // console.log(qr);
        // console.log(data.data.isOwner)
        if (!data.data.isOwner) {
          alert("Bạn không phải là chủ sở hữu đường dẫn này!")
        }
        

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
    const now = new Date();
    const timeZone = Intl.DateTimeFormat().resolvedOptions().timeZone;
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
            originalUrl: link,
            trafficDate: now.toISOString(),
	          zoneId: timeZone,

            shortCode: customLink,
            timeExpired: timeExpired,
            password: password,
            maxUsage: maxUsage,
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

        const shortLink = `${process.env.HOST_PAGE}` + "/" + data.data.shortCode;
        console.log(shortLink);
        setErrorShortLink(false)
        setLink(shortLink);
        setIsShortCode(true);
        setQr("data:image/png;base64," + data.data.base64Image);
        console.log(data.qr);
        getInfo(Cookies.get('token'));

        if (!data.data.isOwner) { 
          alert("Bạn không phải là chủ sở hữu đường dẫn này!")
        }

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

  const handleSettingToggle = () => {
    setSettingToggle(!settingToggle);
  }

  const handleCheckShortCode = async () => {
    try {
      const response = await fetch(
        `${process.env.CHECK_EXIST_SHORT_CODE}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            shortCode: customLink,
          }),
        },
      );

      if (response.ok) {
        // Xử lý khi API trả về thành công
        const data = await response.json();
        if (data.data.isExistShortCode) {
          setErorExistShortCode("Short Link đã tồn tại.")
          setCustomLink("")
          // alert("Short Link đã tồn tại.")
        } else {
          setErorExistShortCode("")
        }

        

      } else {
        setCustomLink("")
        console.error("API call failed");
        return ;
      }
    } catch (error) {
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

        {errorShortLink && (
          <div className="flex justify-center items-center">
            {/* <div className="loader ease-linear rounded-full border-8 border-t-8 border-gray-200 h-10 w-10"></div> */}
            <div className='flex justify-center p-2 text-red-500'>{error}</div>
          </div>
        )}

        {isLoading && (
          <div className="my-2 flex justify-center items-center">
            <div className="h-10 w-10 rounded-full border-4 border-t-transparent border-b-transparent border-r-transparent animate-spin"
              style={{background: 'linear-gradient(90deg, #ff7e5f, #feb47b)', clipPath: 'circle(50%)'}}>
            </div>
          </div>
        )}

        {isShortCode ? 
        <div className='flex justify-between border rounded-md p-3'>
          <input onChange={(e) => setLink(e.target.value)} disabled  type="text" className='w-[70%] focus:outline-none mx-5 bg-white' placeholder='Link cần rút gọn của bạn' value={link}/>
          <button onClick={handleCopyShortLink} className='font-bold text-xl px-5 py-2 rounded-md bg-blue-600 hover:bg-blue-700' type="button">Copy</button>
          <button onClick={() => {navigate("/")}} className='font-bold text-xl px-5 py-2 rounded-md bg-blue-600 hover:bg-blue-700 ml-2' type="button">Mới</button>
        </div>
        : 
        <div className='w-[1000px]'>
          <div className='flex justify-between border rounded-t-md p-3'>
            <input onChange={(e) => setLink(e.target.value)} type="text" className='w-[70%] focus:outline-none mx-5' placeholder='Link cần rút gọn của bạn' value={link}/>
            <button onClick={handleShortLink} className='font-bold text-xl px-5 py-2 rounded-md bg-blue-600 hover:bg-blue-700' type="button">{isLoading ? 'Đang tạo...' : 'Rút gọn'}</button>
          </div>
          <div className='flex-col justify-between items-center border rounded-b-md p-3'>
            <div className='flex justify-between items-center'>
              <button onClick={handleSettingToggle} className='font-bold text-base px-3 py-1 rounded-md bg-blue-600 hover:bg-blue-700' type="button"><FontAwesomeIcon icon={faGear} /> Tùy chọn</button>
              <p className='pl-10'> Bằng việc bấm vào nút <span className='font-bold'>RÚT GỌN LINK</span>, đồng nghĩa với việc bạn đồng ý với <span className='text-red-500'>Điều khoản sử dụng</span></p>
            </div>
            {settingToggle && <div className='flex items-center justify-between p-2 mt-3'>
              <div className='flex-col'>
                <p className='font-semibold px-2'> Link tùy chỉnh </p>
                <p className='text-sm font-thin px-2'> Mặc định, hệ thống sẽ tạo link ngẫu nhiên. Bạn có thể đặt link theo tùy chọn. </p>
                <p className='text-sm font-thin px-2'> Vui lòng nhập vào giá trị chỉ bao gồm các chữ cái (hoa hoặc thường) và chữ số, không có ký tự đặc biệt. Ví dụ: 'abc123', 'A1B2C3'. </p>
                <div className='text-sm font-thin px-2 text-red-500'>{erorExistShortCode}</div>
                <input onBlur={handleCheckShortCode} onChange={(e) => setCustomLink(e.target.value)}   type="text" className='w-[80%] border rounded focus:outline-none p-1 m-2 bg-white' placeholder='Link tùy chọn'/>
                
              </div>
            </div>}

            {settingToggle && <div className='flex items-center justify-between p-2 mt-3'>
              <div className='flex-col'>
                <p className='font-semibold px-2'> Giới hạn truy cập </p>
                <p className='text-sm font-thin px-2'> Mặc định, hệ thống sẽ tạo link không có giới hạn truy cập. Bạn có thể đặt giới hạn đó. </p>
                <input onChange={(e) => setMaxUsage(e.target.value)}  type="number" className='w-[80%] border rounded focus:outline-none p-1 m-2 bg-white' placeholder='Giới hạn truy cập'/>
              </div>
              <div className='flex-col'>
                <p className='font-semibold px-2'> Thời gian hiệu lực </p>
                <p className='text-sm font-thin px-2'> Sau 00:00 phút của ngày được chọn, link sẽ không còn hiệu lực. Để trống nếu giữ vĩnh viễn link. </p>
                <input onChange={(e) => {setTimeExpired(e.target.value+"T00:00:00Z"); console.log(e.target.value+timeExpired);}}  type="date" className='w-[80%] border rounded focus:outline-none p-1 m-2 bg-white' placeholder='MM/DD/YYYY'/>
              </div>
              <div className='flex-col'>
                <p className='font-semibold px-2'> Mật khẩu bảo vệ </p>
                <p className='text-sm font-thin px-2'> Đặt mật khẩu để bảo vệ link rút gọn. Để trống nếu bạn không muốn đặt mật khẩu.</p>
                <input onChange={(e) => setPassword(e.target.value)}  type="text" className='w-[80%] border rounded focus:outline-none p-1 m-2 bg-white' placeholder='Mật khẩu bảo vệ'/>
              </div>
            </div>}
          </div>
        </div>
        }

        {isShortCode && (
          <div className='flex border rounded-sm p-2 mt-4'> 
            <div>
              <button onClick={handleSaveImage} className='font-bold text-xl px-3 py-1 rounded-lg bg-blue-600 hover:bg-blue-700' type="button">Lưu QR code</button>
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
