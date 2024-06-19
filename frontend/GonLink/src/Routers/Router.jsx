import React from 'react'
import { useRoutes } from 'react-router-dom'
import Layout from '../Layouts/Layout'
import Home from '../Pages/Home'
import ContactPage from '../Pages/ContactPage'
import About from '../Pages/About'

export default function Router() {
    return useRoutes([
        {
            path: "/",
            element: <Layout/>,
            children: [
            { path: "home", element: <Home /> },
            { path: "contact", element: <ContactPage /> },
            { path: "about", element: <About /> },
            ],
        }
    ])
}
