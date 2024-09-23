// src/components/LineChart.js
import React, { useEffect, useState } from 'react';
import { Line } from 'react-chartjs-2';
import { Chart as ChartJS, LineElement, CategoryScale, LinearScale, Title, Tooltip, Legend, PointElement } from 'chart.js';

// Register components with Chart.js
ChartJS.register(LineElement, CategoryScale, LinearScale, Title, Tooltip, Legend, PointElement);

const getData = (type, arrDay, arrMonth) => {
    switch (type) {
        case 'day':
            return {
                labels: arrDay.label,
                datasets: [{
                    label: 'Thống kê biến động số lượt truy cập trong 1 ngày (UTC +0)',
                    data: arrDay.data,
                    borderColor: 'rgba(75, 192, 192, 1)',
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    fill: true,
                }],
            };
        case 'month':
            return {
                // ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
                // labels: [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,11,12,13,14,15,16,17,18,19,20,11,12,13,14,15,16,17,18,19,20],
                labels: arrMonth.label,
                datasets: [{
                    label: 'Mức độ tăng trưởng lượng truy cập trong 1 tuần',
                    data: arrMonth.data,
                    borderColor: 'rgba(75, 192, 192, 1)',
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    fill: true,
                }],
            };
        // case 'year':
        //     return {
        //         labels: ['2021', '2022', '2023', '2024'],
        //         datasets: [{
        //             label: 'Doanh thu theo năm',
        //             data: [300, 500, 400, 600],
        //             borderColor: 'rgba(75, 192, 192, 1)',
        //             backgroundColor: 'rgba(75, 192, 192, 0.2)',
        //             fill: true,
        //         }],
        //     };
        default:
            return {};
    }
};

const LineChart = (object) => {
    const [chartType, setChartType] = useState('day');
    const [arrDay, setArrDay] = useState({label:[], data:[]});
    const [arrMonth, setArrMonth] = useState({label:[], data:[]});

    useEffect(() => {
        console.log(object);
        
        getArrMonth(object.token, object.shortCode);
        getArrDay(object.token, object.shortCode);

    }, [])
    
    const data = getData(chartType, arrDay, arrMonth);
    const options = {
        responsive: true,
        plugins: {
            legend: {
                position: 'bottom',
            },
            tooltip: {
                callbacks: {
                    label: function (context) {
                        let label = context.dataset.label || '';
                        if (label) {
                            label += ': ';
                        }
                        if (context.parsed.y !== null) {
                            label += `${context.parsed.y}`;
                        }
                        return label;
                    },
                },
            },
        },
    };

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
                console.log("ngày trong tuần");
                console.log(data.data.trafficData);
                const index = data.data.trafficData.findIndex(item => item.date === formatDate(today));
                console.log(index);
                const arr = getSevenDaysBefore(data.data.trafficData, index+1);
                console.log(arr);
                
                const extract = extractDatesAndValues(arr);
                console.log(extract);

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
                console.log(data.data.trafficData)

                const currentHour = parseInt(today.getHours().toString().padStart(2, '0'));
                console.log(currentHour);
                const elements = data.data.trafficData.slice(0, currentHour+1-7);
                console.log(elements);
                

                const extract = extractDatesAndValues(elements)

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
                <button onClick={() => setChartType('month')} className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600">7 Ngày gần nhất</button>
                {/* <button onClick={() => setChartType('year')} className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600">Theo Năm</button> */}
            </div>

            <Line data={data} options={options} />

        </div>
    );
};

export default LineChart;
