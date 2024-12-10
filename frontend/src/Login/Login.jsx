import { faGithub, faGoogle } from '@fortawesome/free-brands-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { FcGoogle } from "react-icons/fc";
import React from 'react'
import banner from './../assets/banner-img.svg'


export default function Login() {

  const handleLogin = () => {
    window.location.href = `${process.env.LOGIN}`;
  }

  const handleLoginGG = () => {
    window.location.href = `${process.env.LOGIN_GG}`;
  }

  return (
    <div>
      <div className="hidden md:block">
        <div className='h-screen overflow-hidden flex'>
          <div className='w-[30%] h-screen flex justify-center items-center mx-auto bg-blue-100'>
            <img 
              src={banner} 
              alt="Descript" 
              className="w-[200px] object-cover" 
            />
          </div>
          <div className='w-[70%] my-72  h-full'>
            <div className='flex justify-center'>
              <div>
                <h3 className='font-bold text-xl p-2'>Đăng nhập bằng mạng xã hội</h3>
                
                <button onClick={handleLogin} className='flex items-center justify-center font-bold px-5 py-2 border hover:border-sky-600 w-full rounded-md m-2 space-x-2' type="button"><FontAwesomeIcon icon={faGithub} /> <span>Đăng nhập bằng Github</span></button>
                <button onClick={handleLoginGG} className='flex items-center justify-center font-bold px-5 py-2 border hover:border-sky-600 w-full rounded-md m-2 space-x-2' type="button"><FcGoogle className="w-5 h-5" /> <span>Đăng nhập bằng Google</span></button>

                <p className=''>Bằng việc bấm vào nút Đăng nhập ở trên, có nghĩa là bạn đã</p>
                <p className=''>đồng ý với <span className='font-bold text-sky-600 mx-1'>Chính sách và điều khoản sử dụng</span> của chúng tôi.</p>
              </div>
                
            </div> 
          </div>
        </div>
      </div>

      <div className="block md:hidden">
        <div className='h-screen overflow-hidden flex bg-gradient-to-b from-white to-blue-200'>
          <div className='my-60 mx-10'>
            <div className='flex justify-center'>
              <div>
                <h3 className='font-bold text-xl p-2'>Login using a social network</h3>
                
                <button onClick={handleLogin} className='font-bold px-5 py-2 border hover:border-sky-600 w-full rounded-md m-2' type="button"><FontAwesomeIcon icon={faGithub} /> Sign in with Github</button>
                <button onClick={handleLoginGG} className='font-bold px-5 py-2 border hover:border-sky-600 w-full rounded-md m-2' type="button"><FontAwesomeIcon icon={faGoogle} /> Sign in with Google</button>

                <p className=''>Bằng việc bấm vào nút Đăng nhập ở trên, có nghĩa là bạn đã</p>
                <p className=''>đồng ý với <span className='font-bold text-sky-600 mx-1'>Chính sách và điều khoản sử dụng</span> của chúng tôi.</p>
              </div>
                
            </div> 
          </div>
        </div>

      </div>
    </div>
  )
}
