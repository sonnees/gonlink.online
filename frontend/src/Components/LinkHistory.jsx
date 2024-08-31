import React from 'react'
import { useUser } from '../context/UserContext';

export default function LinkHistory({link}) {
    const count = 0;

  return (
    <div className='flex justify-center'>
        
        <div className='w-[52rem] flex justify-between items-center border border-gray-300 p-4 rounded m-2'>
            <div>
                <a href={link.originalUrl}><p className='truncate w-80'>{link.originalUrl}</p></a>
                
                <p className='truncate w-80'>{`${process.env.HOST_PAGE}` + link.shortCode}</p>
            </div>

            <div>
                {count} Click 
            </div>
        </div>


        
    </div>
  )
}
