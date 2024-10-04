// src/components/LineChart.js
import React, { useEffect, useState } from 'react';
import LineChart from './LineChart';
import BarChart from './BarChart';
import PieChart from './PieChart';


const LineChartCustom = (object) => {
    const [chartType, setChartType] = useState('day');
    const [arrDay, setArrDay] = useState({label:[], data:[]});
    const [arrMonth, setArrMonth] = useState({label:[], data:[]});
    const [traffic, setTraffic] = useState();

    useEffect(() => {
        console.log(object);
        
        getArrMonth(object.token, object.shortCode);
        getArrDay(object.token, object.shortCode);

    }, [])
    

    const getArrMonth = async (token, shortCode) => {       
        const extractDatesAndValues = (dataArray) => {
            if (!Array.isArray(dataArray) || dataArray.length === 0) {
              return { dates: [], values: [] };
            }
            const dates = dataArray.map(item => item.date);
            const values = dataArray.map(item => item.data);
            return { dates, values };
        };
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
                    "shortCode":shortCode
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
            const dates = dataArray.map(item => item.date);
            const values = dataArray.map(item => item.data);
            return { dates, values };
        };
        const today = new Date();
        const daysAgo = new Date();
        daysAgo.setDate(today.getDate() - 1);
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
                    "fromDate": formatDate(today),
                    "toDate": formatDate(today)
                }),
                },
            );
        
            if (response.ok) {
                // Xử lý khi API trả về thành công
                const data = await response.json();
                console.log(data.data)
                const extract = extractDatesAndValues(data.data.click)

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

    return (
        <div className='w-[70rem] h-96 flex flex-col justify-between items-center'>
            <div className="flex space-x-4 mb-4 justify-end">
                <button onClick={() => setChartType('day')} className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600">Biến động theo giờ</button>
                <button onClick={() => setChartType('hour')} className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600">Biểu đồ phát triển theo ngày</button>
                <button onClick={() => setChartType('traffic')} className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600">Chi tiết các truy cập</button>
            </div>

            {chartType === 'hour' ? (
                <LineChart label="Biểu đồ theo ngày" labels={arrMonth.label} data={arrMonth.data} />
            ) : chartType === 'day' ? (
                <BarChart label="Biểu đồ theo giờ" labels={arrDay.label} data={arrDay.data} />
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
            ) : null}
        </div>
    );
};

export default LineChartCustom;
