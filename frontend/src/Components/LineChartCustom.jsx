// src/components/LineChart.js
import React, { useEffect, useState } from 'react';
import LineChart from './LineChart';
import BarChart from './BarChart';


const LineChartCustom = (object) => {
    const [chartType, setChartType] = useState('day');
    const [arrDay, setArrDay] = useState({label:[], data:[]});
    const [arrMonth, setArrMonth] = useState({label:[], data:[]});

    useEffect(() => {
        console.log(object);
        
        getArrMonth(object.token, object.shortCode);
        getArrDay(object.token, object.shortCode);

    }, [])
    

    const getArrMonth = async (token, shortCode) => {
        const formatDate = (date) => {
            const year = date.getFullYear();
            const month = String(date.getMonth() + 1).padStart(2, '0'); // Tháng từ 0 đến 11
            const day = String(date.getDate()).padStart(2, '0'); // Ngày từ 1 đến 31
            return `${year}-${month}-${day}`;
        };
        const getSevenDaysBefore = (dataArray, targetIndex) => {
            // Tính chỉ số bắt đầu, đảm bảo không âm
            const startIndex = Math.max(targetIndex - 7, 0);
            return dataArray.slice(startIndex, targetIndex);
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
                // console.log(data.data.trafficData);
                
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
                // console.log(data.data.trafficData)
                const extract = extractDatesAndValues(data.data.trafficData)

                setArrDay({
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

    return (
        <div className='w-[70rem] h-96 flex flex-col justify-between items-center'>
            <div className="flex space-x-4 mb-4 justify-end">
                <button onClick={() => setChartType('day')} className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600">Biến động theo giờ</button>
                <button onClick={() => setChartType('hour')} className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600">7 Ngày gần nhất</button>
                {/* <button onClick={() => setChartType('year')} className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600">Theo Năm</button> */}
            </div>

            {chartType === 'hour' ? (
                <LineChart label="Biểu đồ theo ngày" labels={arrMonth.label} data={arrMonth.data} />
            ) : (
                <BarChart label="Biểu đồ theo giờ" labels={arrDay.label} data={arrDay.data} />
            )}
        </div>
    );
};

export default LineChartCustom;
