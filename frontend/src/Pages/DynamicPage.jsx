import React, { useEffect, useState } from 'react'
import { Await, useParams } from 'react-router-dom';
import {
    isMobile,
    isTablet,
    isDesktop,
    browserName,
    browserVersion,
    osName,
    osVersion,
    engineName,
} from 'react-device-detect';
import Home from './Home';

import { toast } from 'react-toastify';

export default function DynamicPage() {
    const { '*': dynamicPath } = useParams();
    const [password, setPassword] = useState("");

    const [browserInfo, setBrowserInfo] = useState({
        browser: '',
        browserVersion: '',
        operatingSystem: '',
        deviceType: '',
    });

    useEffect(() => {
        // Lấy thông tin từ navigator
        const userAgentData = navigator.userAgentData;
        console.log(isMobile,
            isTablet,
            isDesktop,
            browserName,
            browserVersion,
            osName,
            osVersion,
            engineName);
        



        setBrowserInfo({
            browser: browserName || 'Unknown Browser',
            browserVersion: browserVersion || 'Unknown Version',
            operatingSystem: osName || 'Unknown OS',
            deviceType: isMobile ? 'Mobile' : 'Desktop',
        });
        
    }, []);

    useEffect(() => {
        if (browserInfo.browser!=="") {
            checkPassword();
        }

        // fetchLink(password);
    }, [browserInfo])


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
                        shortCode: dynamicPath,
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
                // alert("Link không còn tồn tại!")
                toast.error("Link không còn tồn tại!");
                console.error("Failed API");
                return;
            }
        } catch (error) {
        // Xử lý lỗi khi gọi API
            console.error("Failed API");
        }
    
    }




    const fetchLinkWithPassword = async (password) => {
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
                        zoneId: timeZone,
                        password: password,
                        browser: browserInfo.browser,
                        browserVersion: browserInfo.browserVersion,
                        operatingSystem: browserInfo.operatingSystem,
                        deviceType: browserInfo.deviceType,
                    }),
                },
            );

            if (response.status === 403) {
                let link;
                try {
                    link = await response.json();
                } catch (error) {
                    console.error("Failed to parse JSON from 403 response", error);
                    return;
                }
                
                // Kiểm tra cấu trúc phản hồi
                if (link && link.message) {
                    // TIME_EXPIRED
                    // PASSWORD_NOT_CORRECT
                    // MAX_ACCESS

                    switch (link.message) {
                        case 'TIME_EXPIRED':
                            // alert('Liên kết đã hết hạn. Vui lòng thử lại.');
                            toast.error("Liên kết đã hết hạn. Vui lòng thử lại.");
                            break;
                        case 'PASSWORD_NOT_CORRECT':
                            // alert('Mật khẩu không đúng. Vui lòng thử lại.');
                            toast.error("Mật khẩu không đúng. Vui lòng thử lại.");
                            break;
                        case 'MAX_ACCESS':
                            // alert('Đã đạt giới hạn truy cập tối đa.');
                            toast.error("Đã đạt giới hạn truy cập tối đa!");
                            break;
                        default:
                            // alert('Link không còn tồn tại');
                            toast.error("Link không còn tồn tại!");
                            // console.log(`Error code: ${link.code}, Message: ${link.message}`);
                    }

                } else {
                    console.error("Unexpected 403 response structure", link);
                }
                return;
            }
      
            if (response.ok) {
                const link = await response.json();
                window.location.href = link.data.originalUrl;

            } else {
                // alert('Liên kết không tồn tại.');
                toast("Link không còn tồn tại!");
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

        console.log(browserInfo.browser);
        
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
                        zoneId: timeZone,
                        browser: browserInfo.browser,
                        browserVersion: browserInfo.browserVersion,
                        operatingSystem: browserInfo.operatingSystem,
                        deviceType: browserInfo.deviceType,
                    }),
                },
            );
      
            if (response.status == 401) {
              console.error("Failed login");
              return;
            }

            if (response.status === 403) {
                let link;
                try {
                    link = await response.json();
                } catch (error) {
                    console.error("Failed to parse JSON from 403 response", error);
                    return;
                }
                
                // Kiểm tra cấu trúc phản hồi
                if (link && link.message) {
                    // TIME_EXPIRED
                    // PASSWORD_NOT_CORRECT
                    // MAX_ACCESS

                    switch (link.message) {
                        case 'TIME_EXPIRED':
                            // alert('Liên kết đã hết hạn. Vui lòng thử lại.');
                            toast.error("Liên kết đã hết hạn. Vui lòng thử lại.");
                            break;
                        case 'PASSWORD_NOT_CORRECT':
                            // alert('Mật khẩu không đúng. Vui lòng thử lại.');
                            toast.error("Mật khẩu không đúng. Vui lòng thử lại.");
                            break;
                        case 'MAX_ACCESS':
                            // alert('Đã đạt giới hạn truy cập tối đa.');
                            toast.error("Đã đạt giới hạn truy cập tối đa!");
                            break;
                        default:
                            // alert('Link không còn tồn tại');
                            toast.error("Link không còn tồn tại!");
                            // console.log(`Error code: ${link.code}, Message: ${link.message}`);
                    }

                } else {
                    console.error("Unexpected 403 response structure", link);
                }
                return;
            }
      
            if (response.ok) {
                const link = await response.json();
                window.location.href = link.data.originalUrl;

            } else {
                // Xử lý khi API trả về lỗi
                // alert('Liên kết không tồn tại.');
                toast.error("Liên kết không tồn tại.");
                const link = await response.json();
                console.log(link.data.message);

                // alert("Link không còn tồn tại!")
                // console.error("Failed API");
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
