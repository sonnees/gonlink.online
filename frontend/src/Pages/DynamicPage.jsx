import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom';
import Home from './Home';

export default function DynamicPage() {
    const { '*': dynamicPath } = useParams();
    const [password, setPassword] = useState("");

    // window.location.href = 'https://www.youtube.com/watch?v=Ehy3OftSHLs';

    useEffect(() => {
        checkPassword();

        // fetchLink(password);
    }, [])


    const checkPassword = async () => {
        try {
            const response = await fetch(
                `${process.env.CHECK_NEED_PASSWORD}`,
                {
                    method: "POST",
                    headers: {
                    "Content-Type": "application/json",
                    },
                    body: JSON.stringify({
                        shortCode: dynamicPath
                    }),
                },
            );

            if (response.ok) {
                const link = await response.json();
                // window.location.href = link.data.originalUrl;
                if (link.data.isNeedPassword) {
                    const input = prompt('Please enter password:');
                    fetchLinkWithPassword(input);

                } else {
                    fetchLink();
                }

            } else {
                // Xử lý khi API trả về lỗi
                alert("Link không tồn tại!")
                console.error("Failed API");
                return;
            }
        } catch (error) {
        // Xử lý lỗi khi gọi API
            console.error("Failed API");
        }
    
    }




    const fetchLinkWithPassword = async (password) => {

        const now = new Date();
        const timeZone = Intl.DateTimeFormat().resolvedOptions().timeZone;

        try {
            const response = await fetch(
                `${process.env.OG_URL}`,
                {
                    method: "POST",
                    headers: {
                    "Content-Type": "application/json",
                    },
                    body: JSON.stringify({
                        shortCode: dynamicPath,
                        clientTime: now.toISOString(),
                        zoneId: timeZone,
                        password: password
                    }),
                },
            );
      
            if (response.ok) {
                const link = await response.json();
                window.location.href = link.data.originalUrl;

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

    const fetchLink = async () => {

        const now = new Date();
        const timeZone = Intl.DateTimeFormat().resolvedOptions().timeZone;

        try {
            const response = await fetch(
                `${process.env.OG_URL}`,
                {
                    method: "POST",
                    headers: {
                    "Content-Type": "application/json",
                    },
                    body: JSON.stringify({
                        shortCode: dynamicPath,
                        clientTime: now.toISOString(),
                        zoneId: timeZone
                    }),
                },
            );
      
            if (response.status == 401) {
              console.error("Failed login");
              return;
            }
      
            if (response.ok) {
                const link = await response.json();
                window.location.href = link.data.originalUrl;

            } else {
                // Xử lý khi API trả về lỗi
                alert("Link không tồn tại!")
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
