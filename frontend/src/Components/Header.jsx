import React from 'react'
import { Link, useNavigate } from 'react-router-dom';

export default function Header() {
  return (
    <div className='flex justify-around items-center bg-gradient-to-r from-blue-400 via-purple-500 to-pink-500 p-1'>
      <img src="/src/assets/Logo.png" alt="" className='w-[180px]' />
      <ul className='flex gap-4 md:gap-14'>
        <li className='hover:font-bold cursor-pointer'> <Link to={"home"}>Trang chủ</Link> </li>
        <li className='hover:font-bold cursor-pointer'> <Link to={"contact"}>Liên hệ</Link> </li>
        <li className='hover:font-bold cursor-pointer'> <Link to={"about"}>Thông tin</Link> </li>
      </ul>
    </div>
  )
}
