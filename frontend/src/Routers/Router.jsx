import React from 'react'
import { Navigate, useRoutes } from 'react-router-dom'
import Layout from '../Layouts/Layout'
import Home from '../Pages/Home'
import ContactPage from '../Pages/ContactPage'
import About from '../Pages/About'
import PageNotFound from '../Pages/PageNotFound'
import DynamicPage from '../Pages/DynamicPage'
import Login from '../Login/Login'
import History from '../Pages/History'
import DetailLink from '../Pages/DetailLink'
import LinkManagement from '../Pages/LinkManagement'
import AdminLayout from '../Layouts/AdminLayout'
import Statistic from '../Pages/Statistic'

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
                { path: "history", element: <History /> },
                { path: "contact", element: <ContactPage /> },
                { path: "about", element: <About /> },
                { path: "detailLink", element: <DetailLink/> },
                { path: "notfound", element: <PageNotFound/> },
                // { path: "*", element: <Navigate to="/page/notfound" /> }
            ],
        },
        {
            path: "/login",
            element: <Login/>,
        }, 
        {
            path: "/link",
            element: <AdminLayout/>,
            children: [
                { path: "linkmanagement", element: <LinkManagement/> },
                { path: "history", element: <History /> },
                { path: "detailLink", element: <DetailLink/> },
                { path: "notfound", element: <PageNotFound/> },

            ],
        },
        {
            path: "/statistic",
            element: <Statistic/>,
        },
        { path: "*", element: <DynamicPage /> }
    ])
}
