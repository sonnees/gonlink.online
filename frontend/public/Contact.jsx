import React from 'react'

export default function Contact() {
  return (
    <div className='flex justify-around py-10'>
      <div className='w-[40%] p-5'>
        <h4 className='font-semibold'>Rút gọn link Gonlink.online</h4>
        <p>
        Gonlink.online là website hỗ trợ rút gọn URL miễn phí cho toàn bộ người dùng và tuân thủ theo pháp luật Việt Nam hiện hành. Nghiêm cấm mọi hành vi sử dụng chức năng rút gọn link cho mục đích vi phạm pháp luật.
        </p>
        <div className='flex'>
          <img src="/src/assets/facebook.png" alt="Description of image" className='w-6 h-6 mr-1'/>
          <img src="/src/assets/facebook.png" alt="Description of image" className='w-6 h-6 mr-1'/>
          <img src="/src/assets/facebook.png" alt="Description of image" className='w-6 h-6 mr-1'/>
          <img src="/src/assets/facebook.png" alt="Description of image" className='w-6 h-6 mr-1'/>
          <img src="/src/assets/facebook.png" alt="Description of image" className='w-6 h-6 mr-1'/>
          <img src="/src/assets/facebook.png" alt="Description of image" className='w-6 h-6 mr-1'/>
        </div>
      </div>

      <div className='w-[20%] p-5 flex flex-col'>
        <h4 className='font-semibold'>Link</h4>
        <p className='text-cyan-500'>Bảng giá dịch vụ</p>
        <p className='text-cyan-500'>Điều khoản sử dụng</p>
        <p className='text-cyan-500'>Chính sách bảo mật</p>
        <p className='text-cyan-500'>Tải Extension</p>
      </div>

      <div className='w-[20%] p-5 flex flex-col'>
        <h4 className='font-semibold'>Link</h4>
        <p className='text-cyan-500'>Bảng giá dịch vụ</p>
        <p className='text-cyan-500'>Điều khoản sử dụng</p>
        <p className='text-cyan-500'>Chính sách bảo mật</p>
        <p className='text-cyan-500'>Tải Extension</p>
      </div>
    </div>
  )
}
