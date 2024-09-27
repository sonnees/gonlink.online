import React from 'react'
import { useUser } from '../context/UserContext';
import { useNavigate } from 'react-router-dom';

export default function LinkHistory({link}) {

    const navigate = useNavigate();

    const formatDate = (dateString) => {
        const date = new Date(dateString);
        return new Intl.DateTimeFormat('vi-VN', {
          day: '2-digit',
          month: 'short',
          year: 'numeric',
        }).format(date);
    };

    const handleClick = () => {
        navigate('/link/detailLink', { state: { link } });
    };

  return (
    <div className='flex justify-center'>
        <div className='w-[64rem] flex justify-between items-center border border-gray-300 p-4 rounded-md m-2 hover:bg-slate-100'>
            
            <div>
                <a href={link.originalUrl}><p className='truncate w-80'>{link.originalUrl}</p></a>
                <a href={`${process.env.HOST_PAGE}` + "/" + link.shortCode}><p className='truncate w-80'>{`${process.env.HOST_PAGE}` + "/" + link.shortCode}</p></a>     
            </div>
            
            
            <div>
                <p>{formatDate(link.trafficDate)}</p>
            </div>
            
            <div className='cursor-pointer hover:text-blue-500' onClick={handleClick}>
                <p>{link.traffic} Click</p>
            </div>
        </div>
    </div>
  )
}
