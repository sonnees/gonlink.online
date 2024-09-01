import { faGithub } from '@fortawesome/free-brands-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import React from 'react'

export default function Login() {

  const handleLogin = () => {
    window.location.href = `${process.env.LOGIN}`;
  }

  return (
    <div className='h-screen overflow-hidden flex'>
        <div className='w-[30%] bg-slate-400'>

        </div>
        <div className='w-[70%] my-72'>
            <div className='flex justify-center'>
                <div>
                    <h3 className='font-bold text-xl p-2'>Login using a social network</h3>
                    
                    <button onClick={handleLogin} className='font-bold px-5 py-2 border hover:border-sky-600 w-full rounded-md m-2' type="button"><FontAwesomeIcon icon={faGithub} /> Sign in with Github</button>

                    <p className=''>Bằng việc bấm vào nút Đăng nhập ở trên, có nghĩa là bạn đã</p>
                    <p className=''>đồng ý với <span className='font-bold text-sky-600 mx-1'>Chính sách và điều khoản sử dụng</span> của chúng tôi.</p>
                </div>
                
            </div>

            
            
        </div>
    </div>
  )
}
