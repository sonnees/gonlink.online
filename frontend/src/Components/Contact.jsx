import React from 'react'

export default function Contact() {
  return (
    <div>
      <div className="hidden md:block">
        <div className='flex justify-around py-10 bg-blue-950 text-white'>
          <div className='w-[40%] p-5'>
            <h4 className='font-semibold'>Rút gọn link Gonlink.online</h4>
            <p>
            Gonlink.online là website hỗ trợ rút gọn URL miễn phí cho toàn bộ người dùng và tuân thủ theo pháp luật Việt Nam hiện hành. Nghiêm cấm mọi hành vi sử dụng chức năng rút gọn link cho mục đích vi phạm pháp luật.
            </p>
            <div className='flex'>
              <img src="./../facebook.png" alt="Description of image" className='w-6 h-6 mr-1'/>
              <img src="./../linkedin.png" alt="Description of image" className='w-6 h-6 mr-1'/>
              <img src="./../pinterest.png" alt="Description of image" className='w-6 h-6 mr-1'/>
              <img src="./../twitter.png" alt="Description of image" className='w-6 h-6 mr-1'/>
              <img src="./../youtube.png" alt="Description of image" className='w-6 h-6 mr-1'/>
            </div>
          </div>

          <div className='w-[20%] p-5 flex flex-col'>
            <h4 className='font-semibold'>Link</h4>
            <p className='text-cyan-500'>Bảng giá dịch vụ</p>
            <p className='text-cyan-500'>Điều khoản sử dụng</p>
            <p className='text-cyan-500'>Chính sách bảo mật</p>
            <p className='text-cyan-500'>Tải Extension</p>
          </div>
        </div>
      </div>

      <div className="block md:hidden">
      <div className='flex-col justify-around py-10 bg-blue-950 text-white'>
          <div className='p-5'>
            <h4 className='font-semibold py-2 my-2'>Rút gọn link Gonlink.online</h4>
            <p className=''>
            Gonlink.online là website hỗ trợ rút gọn URL miễn phí cho toàn bộ người dùng và tuân thủ theo pháp luật Việt Nam hiện hành. Nghiêm cấm mọi hành vi sử dụng chức năng rút gọn link cho mục đích vi phạm pháp luật.
            </p>
            <div className='flex my-2 py-2'>
              <img src="./../facebook.png" alt="Description of image" className='w-6 h-6 mr-1'/>
              <img src="./../linkedin.png" alt="Description of image" className='w-6 h-6 mr-1'/>
              <img src="./../pinterest.png" alt="Description of image" className='w-6 h-6 mr-1'/>
              <img src="./../twitter.png" alt="Description of image" className='w-6 h-6 mr-1'/>
              <img src="./../youtube.png" alt="Description of image" className='w-6 h-6 mr-1'/>
            </div>
          </div>

          <div className='p-5 flex flex-col'>
            <h4 className='font-semibold'>Link</h4>
            <p className='text-cyan-500'>Bảng giá dịch vụ</p>
            <p className='text-cyan-500'>Điều khoản sử dụng</p>
            <p className='text-cyan-500'>Chính sách bảo mật</p>
            <p className='text-cyan-500'>Tải Extension</p>
          </div>
        </div>

      </div>
    </div>
  )
}
