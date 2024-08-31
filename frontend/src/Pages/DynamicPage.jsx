import React, { useEffect } from 'react'
import { useParams } from 'react-router-dom';
import Home from './Home';

export default function DynamicPage() {
    const { '*': dynamicPath } = useParams();

    // window.location.href = 'https://www.youtube.com/watch?v=Ehy3OftSHLs';

    useEffect(() => {
        fetchLink();
    }, [])


    const fetchLink = async () => {

        const now = new Date();
        const timeZone = Intl.DateTimeFormat().resolvedOptions().timeZone;

        try {
            const response = await fetch(
                `${process.env.HOST}/shorten-service/api/v1/get-original-url`,
                {
                    method: "POST",
                    headers: {
                    "Content-Type": "application/json",
                    },
                    body: JSON.stringify({
                        shortCode: dynamicPath,
                        clientTime: now.toISOString(),
                        zoneId: timeZone,
                    }),
                },
            );
      
            if (response.status == 401) {
              console.error("Failed login");
              return;
            }
      
            if (response.ok) {
                const link = await response.json();
                window.location.href = link.originalUrl;

            } else {
                // Xử lý khi API trả về lỗi
                console.error("Failed API");
                return;
            }
        } catch (error) {
        // Xử lý lỗi khi gọi API
            console.error("Failed API");
        }
    
    }

    return (
        <div></div>
    )
}
