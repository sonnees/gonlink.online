import React from 'react'
import Header from '../Components/Header'
import Footer from '../Components/Footer'
import Contact from '../Components/Contact'
import DotLine from '../Components/DotLine'
import { Outlet } from 'react-router-dom'
import { UserProvider } from '../context/UserContext'

export default function Layout() {
  return (
    <div className='flex flex-col min-h-screen bg-gradient-to-b from-white to-blue-200'>
      <UserProvider>
        <Header/>
        

        <div className='flex-grow'>
          <Outlet/>
        </div>

        {/* <DotLine/> */}
        <Contact/>
        <Footer/>
      </UserProvider>
    </div>
  )
}
