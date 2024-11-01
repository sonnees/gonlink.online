import React, { useEffect, useState } from 'react'
import { useUser } from '../context/UserContext';
import Cookies from 'js-cookie';
import { useLocation } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faLink, faQrcode } from '@fortawesome/free-solid-svg-icons';
import LineChartCustom from '../Components/LineChartCustom';


export default function DetailLink() {
    const location = useLocation();
    const { link } = location.state;

    console.log(link);

    const {userObject, setUserObject, authenticated, setAuthenticated, email, setEmail, name, setName, avatar, setAvatar, token, setToken} = useUser();
    // console.log(userObject);

    useEffect(() => {
        if (Cookies.get('token')) {
            setName(JSON.parse(localStorage.getItem('userObj')).name)
        }
        console.log(link);
        
        localStorage.setItem("link", JSON.stringify(link))
    }, [])

    const formatDate = (dateString) => {
        const date = new Date(dateString);
        return new Intl.DateTimeFormat('vi-VN', {
            day: '2-digit',
            month: 'short',
            year: 'numeric',
        }).format(date);
    };

    // ================================



    return (
        <div className='h-[calc(100vh-70px)] flex flex-col items-center'>
            <div className='w-[70rem] h-36 flex-col justify-between items-center border border-gray-300 p-4 rounded-md mt-8 shadow-lg'> 
                <div className='flex'>
                    <div className='pl-20'>
                        <p className='font-semibold'>Link Gốc</p>
                        <a href={link.originalUrl}><p>{link.originalUrl}</p></a>
                    </div>
                </div>
                
                <div className='flex justify-between items-center'>
                    <div className='text-center pl-10'>
                        <p>{link.traffic}</p>
                        <p className='font-semibold'>Click</p>
                    </div>
                    <div className='text-center'>
                        <p className='font-semibold'>Ngày tạo</p>
                        <p>{formatDate(link.trafficDate)}</p>
                    </div>
                    <div className='text-right pr-10'>
                        {/* <a href={link.originalUrl}><p className='truncate w-80'>{link.originalUrl}</p></a> */}
                        <a href={`${process.env.HOST_PAGE}` + "/" + link.shortCode}><p className='truncate w-80 p-2 font-medium'><FontAwesomeIcon icon={faLink} /> {`${process.env.HOST_PAGE}` + "/" + link.shortCode}</p></a>     
                        <a href={`${process.env.HOST_PAGE}` + "/" + link.shortCode}><p className='truncate w-80 p-2 font-medium'><FontAwesomeIcon icon={faQrcode} /> {`${process.env.HOST_PAGE}` + "/" + link.shortCode + "/qr"}</p></a>                 
                    </div>
                </div>
            </div>

            {/* <div className='w-[80rem] h-96 flex-col justify-between items-center border border-gray-300 p-4 rounded-md mt-8 shadow-lg'> */}
            <div className='w-[70rem] h-[30rem] flex-col justify-between items-center border border-gray-300 p-4 rounded-md mt-8 shadow-lg'>
                {Cookies.get('token') && <LineChartCustom token={Cookies.get('token')} link={link}/>}
            </div>


        </div>
    )
}
