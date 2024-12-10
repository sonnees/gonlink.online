import React from 'react'

export default function Footer() {
  return (
    <div>
      <div className="hidden md:block">
        <div className='p-5 bg-black text-white flex justify-around'>
          <div className=''>2024-2025 © Rút gọn link Gonlink.online</div>
          <div className=''>Tạo QR Code</div>
        </div>
      </div>
      <div className="block md:hidden">
        <div className='p-5 bg-black text-white flex justify-around'>
          <div className=''>2024-2025 © Rút gọn link Gonlink.online</div>
        </div>
      </div>
    </div>
  )
}
