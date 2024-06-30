import React from 'react'
import { Navigate, useRoutes } from 'react-router-dom'
import Layout from '../Layouts/Layout'
import Home from '../Pages/Home'
import ContactPage from '../Pages/ContactPage'
import About from '../Pages/About'
import PageNotFound from '../Pages/PageNotFound'
import DynamicPage from '../Pages/DynamicPage'

export default function Router() {
    return useRoutes([
        {
            path: "/",
            element: <Navigate to="/page/home"/>,
        }, 
        {
            path: "/page",
            element: <Layout/>,
            children: [
                { path: "home", element: <Home /> },
                { path: "contact", element: <ContactPage /> },
                { path: "about", element: <About /> },
                { path: "notfound", element: <PageNotFound/> },
                { path: "*", element: <Navigate to="/page/notfound" /> }
            ],
        }, 
        // {
        //     path: "/fnf",
        //     element: <Layout/>,
        //     children: [
        //         { path: "", element: <PageNotFound/> },
        //     ],
        // },
        { path: "*", element: <DynamicPage /> }
    ])
}
