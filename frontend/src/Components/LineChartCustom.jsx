// src/components/LineChart.js
import React, { useEffect, useRef, useState } from 'react';
import jsPDF from 'jspdf';
import html2canvas from 'html2canvas';
import LineChart from './LineChart';
import BarChart from './BarChart';
import PieChart from './PieChart';
import GeoChart from './GeoChart';

import timesNewRomanFont from '../font/TimesNewRoman';
import { useNavigate } from 'react-router-dom';


const LineChartCustom = (object) => {
    const [chartType, setChartType] = useState('day');
    const [arrDay, setArrDay] = useState({label:[], data:[]});
    const [arrMonth, setArrMonth] = useState({label:[], data:[]});
    const [traffic, setTraffic] = useState();

    const navigate = useNavigate();

    const chartRef = useRef();  // Dùng ref để chọn phần chứa biểu đồ

    const shortCodeData = object.link.shortCode;

    useEffect(() => {
        console.log(object);
        
        getArrMonth(object.token, object.link.shortCode);
        getArrDay(object.token, object.link.shortCode);

    }, [])

    
    

    const getArrMonth = async (token, shortCode) => {       
        const extractDatesAndValues = (dataArray) => {
            if (!Array.isArray(dataArray) || dataArray.length === 0) {
              return { dates: [], values: [] };
            }
            const dates = dataArray.map(item => item.name);
            const values = dataArray.map(item => item.data);
            return { dates, values };
        };
        const timeZone = Intl.DateTimeFormat().resolvedOptions().timeZone;
        try {
            const response = await fetch(
                `${process.env.GET_DATA_MONTH}`,
                {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`,
                },
                body: JSON.stringify({
                    "shortCode":shortCode,
                    "zoneId": timeZone,
                    // "shortCode":"FjppMm",
                }),
                },
            );
        
            if (response.ok) {
                // Xử lý khi API trả về thành công
                const data = await response.json();
                // console.log("ngày trong tuần");
                // console.log(data.data);
                
                const extract = extractDatesAndValues(data.data.trafficData);
                // console.log(extract);

                setArrMonth({
                    label: extract.dates,
                    data: extract.values
                })
                
            } else {
                    // Xử lý khi API trả về lỗi
                    console.error("API call failed");
                    return ;
            }
        } catch (error) {
            // Xử lý lỗi khi gọi API
            console.error("Error calling API:", error);
        }
    }

    const getArrDay = async (token, shortCode) => {
        const formatDate = (date) => {
            const year = date.getFullYear();
            const month = String(date.getMonth() + 1).padStart(2, '0'); // Tháng từ 0 đến 11
            const day = String(date.getDate()).padStart(2, '0'); // Ngày từ 1 đến 31
            return `${year}-${month}-${day}`;
        };
        const extractDatesAndValues = (dataArray) => {
            if (!Array.isArray(dataArray) || dataArray.length === 0) {
              return { dates: [], values: [] };
            }
            const dates = dataArray.map(item => item.name);
            const values = dataArray.map(item => item.data);
            return { dates, values };
        };
        const today = new Date();
        const daysAgo = new Date();
        daysAgo.setDate(today.getDate() - 365);
        const timeZone = Intl.DateTimeFormat().resolvedOptions().timeZone;
        try {
            const response = await fetch(
                `${process.env.GET_DATA_DAY}`,
                {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`,
                },
                body: JSON.stringify({
                    // "shortCode":"FjppMm",
                    "shortCode":shortCode,
                    "fromDate": formatDate(daysAgo),
                    "toDate": formatDate(today),
                    "zoneId": timeZone,

                }),
                },
            );
        
            if (response.ok) {
                // Xử lý khi API trả về thành công
                const data = await response.json();
                console.log(data.data)
                const extract = extractDatesAndValues(data.data.click)
                console.log(extract);
                
                setArrDay({
                    label: extract.dates,
                    data: extract.values
                })

                setTraffic(data.data)
                
            } else {
                // Xử lý khi API trả về lỗi
                console.error("API call failed");
                return ;
            }
        } catch (error) {
            // Xử lý lỗi khi gọi API
            console.error("Error calling API:", error);
        }
    }

    // const handleExportPDF = async () => {
    //     const pdf = new jsPDF("p", "pt", "a4"); // Tạo đối tượng PDF
    //     const input = chartRef.current; // Chọn phần tử chứa biểu đồ

    //     pdf.setFontSize(16); // Cài đặt kích thước font chữ
    //     pdf.text("Statistical Report", 40, 40); // Thêm chữ vào vị trí (40, 40)

    //     pdf.addFileToVFS('TimesNewRoman.ttf', timesNewRomanFont);
    //     pdf.addFont('TimesNewRoman.ttf', 'TimesNewRoman', 'normal');
    //     pdf.setFont('TimesNewRoman');

    //     pdf.setFontSize(12);
    //     pdf.text("Biểu đồ này thể hiện thông tin chi tiết về lưu lượng truy cập.", 40, 60);

    //     // Sử dụng html2canvas để chuyển đổi DOM thành ảnh
    //     await html2canvas(input, { scale: 2 }).then(canvas => {
    //         const imgData = canvas.toDataURL("image/png");
    //         const imgWidth = 595.28;
    //         const pageHeight = 841.89;
    //         const imgHeight = (canvas.height * imgWidth) / canvas.width;
    //         const heightLeft = imgHeight;

    //         pdf.addImage(imgData, 'PNG', 0, 100, imgWidth, imgHeight);
    //         pdf.save("chart_report.pdf");  // Xuất file PDF
    //     });
    // }

    return (
        <div className='w-[70rem] h-96 flex flex-col justify-between items-center'>
            <div className="flex space-x-4 mb-4 justify-end">
                <button onClick={() => setChartType('day')} className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600">Biến động theo giờ</button>
                <button onClick={() => setChartType('hour')} className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600">Biểu đồ phát triển theo ngày</button>
                <button onClick={() => setChartType('traffic')} className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600">Chi tiết các truy cập</button>
                <button onClick={() => setChartType('geo')} className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600">Biểu đồ truy cập địa lý</button>
                {/* <button onClick={handleExportPDF} className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600">Xuất các thống kê</button> */}
                <button onClick={()=> {navigate('/statistic')}} className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600">Xuất các thống kê</button>
            </div>

            <div ref={chartRef}>
                {chartType === 'hour' ? (
                    <LineChart label="Biểu đồ theo ngày" labels={arrMonth.label} data={arrMonth.data} width={68} />
                ) : chartType === 'day' ? (
                    <BarChart label="Biểu đồ theo giờ" labels={arrDay.label} data={arrDay.data} width={68}/>
                ) : chartType === 'traffic' ? (
                    <div className='flex'>
                        {traffic && (<div className='w-72 h-80 bg-white m-2 rounded-lg border'>
                            <PieChart 
                                label="Biểu đồ các thiết bị truy cập" 
                                labels={traffic.deviceTypes.map(item => item.name || "Không xác định")} 
                                data={traffic.deviceTypes.map(item => item.data)} 
                            />
                        </div>)}

                        {traffic && (<div className='w-72 h-80 bg-white m-2 rounded-lg border'>
                            <PieChart 
                                label="Biểu đồ khu vực truy cập" 
                                labels={traffic.zoneIds.map(item => item.name || "Không xác định")} 
                                data={traffic.zoneIds.map(item => item.data)} 
                            />
                        </div>)}

                        {traffic && (<div className='w-72 h-80 bg-white m-2 rounded-lg border'>
                            <PieChart 
                                label="Biểu đồ trình duyệt truy cập" 
                                labels={traffic.browsers.map(item => item.name || "Không xác định")} 
                                data={traffic.browsers.map(item => item.data)} 
                            />
                        </div>)}
                    </div>
                ) : chartType === 'geo' ? (
                    <GeoChart label="Biểu đồ quốc gia truy cập" labels={arrDay.label} data={traffic.zoneIds} width={68}/>
                ) : null}   
            </div>
        </div>
    );
};

export default LineChartCustom;
