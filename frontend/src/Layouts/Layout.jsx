import React from 'react'
import Header from '../Components/Header'
import Footer from '../Components/Footer'
import Contact from '../Components/Contact'
import DotLine from '../Components/DotLine'
import { Outlet } from 'react-router-dom'

export default function Layout() {
  return (
    <div>
      <Header/>
      

      <div>
        <Outlet/>
      </div>

      <DotLine/>
      <Contact/>
      <Footer/>
    </div>
  )
}
