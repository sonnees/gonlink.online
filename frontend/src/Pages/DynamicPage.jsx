import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom';
import Home from './Home';

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

        if (userAgentData) {
            const { brands, platform, mobile } = userAgentData;

            setBrowserInfo({
                browser: brands[0]?.brand || 'Unknown Browser',
                browserVersion: brands[0]?.version || 'Unknown Version',
                operatingSystem: platform || 'Unknown OS',
                deviceType: mobile ? 'Mobile' : 'Desktop',
            });
        } else {
            // Fallback nếu `navigator.userAgentData` không hỗ trợ
            setBrowserInfo({
                browser: 'Unknown',
                browserVersion: 'Unknown',
                operatingSystem: 'Unknown',
                deviceType: 'Unknown',
            });
        }
    }, []);

    console.log(browserInfo);

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
                        shortCode: dynamicPath,
                        browser: browserInfo.browser,
                        browserVersion: browserInfo.browserVersion,
                        operatingSystem: browserInfo.operatingSystem,
                        deviceType: browserInfo.deviceType,
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
                alert("Link không còn tồn tại!")
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
                        password: password
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
                            alert('Liên kết đã hết hạn. Vui lòng thử lại.');
                            break;
                        case 'PASSWORD_NOT_CORRECT':
                            alert('Mật khẩu không đúng. Vui lòng thử lại.');
                            break;
                        case 'MAX_ACCESS':
                            alert('Đã đạt giới hạn truy cập tối đa.');
                            break;
                        default:
                            alert('Link không còn tồn tại');
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
                        zoneId: timeZone
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
                            alert('Liên kết đã hết hạn. Vui lòng thử lại.');
                            break;
                        case 'PASSWORD_NOT_CORRECT':
                            alert('Mật khẩu không đúng. Vui lòng thử lại.');
                            break;
                        case 'MAX_ACCESS':
                            alert('Đã đạt giới hạn truy cập tối đa.');
                            break;
                        default:
                            alert('Link không còn tồn tại');
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
