// src/components/LineChart.js
import React, { useEffect, useRef, useState } from 'react';
import jsPDF from 'jspdf';
import html2canvas from 'html2canvas';
import LineChart from './LineChart';
import BarChart from './BarChart';
import PieChart from './PieChart';
import GeoChart from './GeoChart';
import Cookies from 'js-cookie';

import ExcelJS from "exceljs";
import { saveAs } from "file-saver";

import timesNewRomanFont from '../font/TimesNewRoman';
import { useNavigate } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faDownload } from '@fortawesome/free-solid-svg-icons';
import { useUser } from '../context/UserContext';


const LineChartCustom = (object) => {
    const [chartType, setChartType] = useState('day');
    const [arrDay, setArrDay] = useState({label:[], data:[]});
    const [arrMonth, setArrMonth] = useState({label:[], data:[]});
    const [traffic, setTraffic] = useState();
    const [menuOptionOpen, setMenuOptionOpen] = useState(false);
    const {userObject, setUserObject, authenticated, setAuthenticated, email, setEmail, name, setName, avatar, setAvatar, token, setToken} = useUser();
  

    const navigate = useNavigate();

    const chartRef = useRef();  // Dùng ref để chọn phần chứa biểu đồ

    const shortCodeData = object.link.shortCode;

    useEffect(() => {
        console.log(object);

        if (Cookies.get('token')) {
            getInfo(Cookies.get('token'));
        }
        
        getArrMonth(object.token, object.link.shortCode);
        getArrDay(object.token, object.link.shortCode);
        getArrDayAll(object.token, object.link.shortCode);

    }, [])

    const getInfo = async (token) => {
        try {
          const response = await fetch(
            `${process.env.GET_INFO}`,
            // `http://localhost:8080/account-service/api/v1/get-info-account`,
            {
              method: "POST",
              headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`,
              },
              body: JSON.stringify({
                
              }),
            },
          );
    
          if (response.status == 401) {
            console.error("URL Is Forbidden");
            return;
          }
    
          if (response.status == 500) {
            console.error("URL Error");
            return;
          }
    
          if (response.ok) {
            // Xử lý khi API trả về thành công
            const data = await response.json();
            setUserObject(data.data)
            localStorage.setItem('userObj', JSON.stringify(data.data));
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
        daysAgo.setDate(today.getDate() - 1);
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

    const getArrDayAll = async (token, shortCode) => {
        const formatDate = (date) => {
            const year = date.getFullYear();
            const month = String(date.getMonth() + 1).padStart(2, '0'); // Tháng từ 0 đến 11
            const day = String(date.getDate()).padStart(2, '0'); // Ngày từ 1 đến 31
            return `${year}-${month}-${day}`;
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
                console.log("ALL")

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

    const exportToExcel = async () => {
        const reportTitle = "Báo cáo dữ liệu người dùng";
        const creator = userObject.name;
        const email = userObject.email;
        const creationDate = new Date().toLocaleString("vi-VN");
        const totalClick = object.link.traffic;
        const linkOriginalUrl = object.link.originalUrl;
        const gonLink = process.env.HOST_PAGE + "/" + object.link.shortCode;
        // Tiêu đề và Worksheet1
        const workbook = new ExcelJS.Workbook();
        const worksheet = workbook.addWorksheet("Trình duyệt");
        // Tiêu đề báo cáo
        worksheet.mergeCells("A1:D1");
        worksheet.mergeCells("A2:C2");
        worksheet.mergeCells("A3:C3");
        worksheet.mergeCells("A4:C4");
        const titleCell = worksheet.getCell("A1");
        titleCell.value = reportTitle;
        titleCell.font = { size: 18, bold: true };
        titleCell.alignment = { horizontal: "center", vertical: "middle" };
        // Người tạo và ngày tạo
        worksheet.getCell("A2").value = `Người tạo: ${creator}`;
        worksheet.getCell("A2").font = { size: 12, italic: true };
        worksheet.getCell("A3").value = `Ngày tạo: ${creationDate}`;
        worksheet.getCell("A3").font = { size: 12, italic: true };
        worksheet.getCell("A4").value = `Email: ${email}`;
        worksheet.getCell("A4").font = { size: 12, italic: true };
        worksheet.getCell("A5").value = `Link gốc: ${linkOriginalUrl}`;
        worksheet.getCell("A5").font = { size: 12, italic: true };
        worksheet.getCell("A6").value = `Link rút gọn: ${gonLink}`;
        worksheet.getCell("A6").font = { size: 12, italic: true };
        worksheet.getCell("D6").value = `Tổng truy cập: ${totalClick}`;
        worksheet.getCell("D6").font = { size: 12, italic: true };

        // Tạo khoảng trống trước bảng dữ liệu
        worksheet.addRow([]);
        // Định nghĩa cột bắt đầu từ dòng thứ 5
        worksheet.getRow(8).values = ["STT", "Tên trình duyệt", "Số lượng"]; // Đặt tên cột vào dòng 5
        worksheet.getColumn(1).width = 20;
        worksheet.getColumn(2).width = 30;
        worksheet.getColumn(3).width = 30;
        worksheet.getColumn(4).width = 30;
        // Định dạng cột header
        worksheet.getRow(8).font = { bold: true }; // Hàng tiêu đề
        worksheet.getRow(8).alignment = { horizontal: "center", vertical: "middle" }; // Căn giữa
        // Thêm dữ liệu vào worksheet bắt đầu từ dòng thứ 7
        traffic.browsers.forEach((item, index) => worksheet.addRow([index + 1, item.name, parseInt(item.data)]));
    
        //Worksheet2
        const worksheet2 = workbook.addWorksheet("Loại thiết bị");
        // Tiêu đề báo cáo
        worksheet2.mergeCells("A1:D1");
        worksheet2.mergeCells("A2:C2");
        worksheet2.mergeCells("A3:C3");
        worksheet2.mergeCells("A4:C4");
        const titleCell2 = worksheet2.getCell("A1");
        titleCell2.value = reportTitle;
        titleCell2.font = { size: 18, bold: true };
        titleCell2.alignment = { horizontal: "center", vertical: "middle" };
        // Người tạo và ngày tạo
        worksheet2.getCell("A2").value = `Người tạo: ${creator}`;
        worksheet2.getCell("A2").font = { size: 12, italic: true };
        worksheet2.getCell("A3").value = `Ngày tạo: ${creationDate}`;
        worksheet2.getCell("A3").font = { size: 12, italic: true };
        worksheet2.getCell("A4").value = `Email: ${email}`;
        worksheet2.getCell("A4").font = { size: 12, italic: true };
        worksheet2.getCell("A5").value = `Link gốc: ${linkOriginalUrl}`;
        worksheet2.getCell("A5").font = { size: 12, italic: true };
        worksheet2.getCell("A6").value = `Link rút gọn: ${gonLink}`;
        worksheet2.getCell("A6").font = { size: 12, italic: true };
        worksheet2.getCell("D6").value = `Tổng truy cập: ${totalClick}`;
        worksheet2.getCell("D6").font = { size: 12, italic: true };
        // Tạo khoảng trống trước bảng dữ liệu
        worksheet2.addRow([]);
        // Định nghĩa cột bắt đầu từ dòng thứ 5
        worksheet2.getRow(8).values = ["STT", "Loại thiết bị", "Số lượng"]; // Đặt tên cột vào dòng 5
        worksheet2.getColumn(1).width = 20;
        worksheet2.getColumn(2).width = 30;
        worksheet2.getColumn(3).width = 30;
        worksheet2.getColumn(4).width = 30;
        // Định dạng cột header
        worksheet2.getRow(8).font = { bold: true }; // Hàng tiêu đề
        worksheet2.getRow(8).alignment = { horizontal: "center", vertical: "middle" }; // Căn giữa
        // Thêm dữ liệu vào worksheet bắt đầu từ dòng thứ 7
        traffic.deviceTypes.forEach((item, index) => worksheet2.addRow([index + 1, item.name, parseInt(item.data)]));
    
        //Worksheet3
        const worksheet3 = workbook.addWorksheet("Khu vực truy cập");
        // Tiêu đề báo cáo
        worksheet3.mergeCells("A1:D1");
        worksheet3.mergeCells("A2:C2");
        worksheet3.mergeCells("A3:C3");
        worksheet3.mergeCells("A4:C4");
        const titleCell3 = worksheet3.getCell("A1");
        titleCell3.value = reportTitle;
        titleCell3.font = { size: 18, bold: true };
        titleCell3.alignment = { horizontal: "center", vertical: "middle" };
        // Người tạo và ngày tạo
        worksheet3.getCell("A2").value = `Người tạo: ${creator}`;
        worksheet3.getCell("A2").font = { size: 12, italic: true };
        worksheet3.getCell("A3").value = `Ngày tạo: ${creationDate}`;
        worksheet3.getCell("A3").font = { size: 12, italic: true };
        worksheet3.getCell("A4").value = `Email: ${email}`;
        worksheet3.getCell("A4").font = { size: 12, italic: true };
        worksheet3.getCell("A5").value = `Link gốc: ${linkOriginalUrl}`;
        worksheet3.getCell("A5").font = { size: 12, italic: true };
        worksheet3.getCell("A6").value = `Link rút gọn: ${gonLink}`;
        worksheet3.getCell("A6").font = { size: 12, italic: true };
        worksheet3.getCell("D6").value = `Tổng truy cập: ${totalClick}`;
        worksheet3.getCell("D6").font = { size: 12, italic: true };
        // Tạo khoảng trống trước bảng dữ liệu
        worksheet3.addRow([]);
        // Định nghĩa cột bắt đầu từ dòng thứ 5
        worksheet3.getRow(8).values = ["STT", "Khu vực truy cập", "Số lượng"]; // Đặt tên cột vào dòng 5
        worksheet3.getColumn(1).width = 20;
        worksheet3.getColumn(2).width = 30;
        worksheet3.getColumn(3).width = 30;
        worksheet3.getColumn(4).width = 30;
        // Định dạng cột header
        worksheet3.getRow(8).font = { bold: true }; // Hàng tiêu đề
        worksheet3.getRow(8).alignment = { horizontal: "center", vertical: "middle" }; // Căn giữa
        // Thêm dữ liệu vào worksheet bắt đầu từ dòng thứ 7
        traffic.zoneIds.forEach((item, index) => worksheet3.addRow([index + 1, item.name, parseInt(item.data)]));
    
        //Worksheet3
        const worksheet4 = workbook.addWorksheet("Hệ điều hành");
        // Tiêu đề báo cáo
        worksheet4.mergeCells("A1:D1");
        worksheet4.mergeCells("A2:C2");
        worksheet4.mergeCells("A3:C3");
        worksheet4.mergeCells("A4:C4");
        const titleCell4 = worksheet4.getCell("A1");
        titleCell4.value = reportTitle;
        titleCell4.font = { size: 18, bold: true };
        titleCell4.alignment = { horizontal: "center", vertical: "middle" };
        // Người tạo và ngày tạo
        worksheet4.getCell("A2").value = `Người tạo: ${creator}`;
        worksheet4.getCell("A2").font = { size: 12, italic: true };
        worksheet4.getCell("A3").value = `Ngày tạo: ${creationDate}`;
        worksheet4.getCell("A3").font = { size: 12, italic: true };
        worksheet4.getCell("A4").value = `Email: ${email}`;
        worksheet4.getCell("A4").font = { size: 12, italic: true };
        worksheet4.getCell("A5").value = `Link gốc: ${linkOriginalUrl}`;
        worksheet4.getCell("A5").font = { size: 12, italic: true };
        worksheet4.getCell("A6").value = `Link rút gọn: ${gonLink}`;
        worksheet4.getCell("A6").font = { size: 12, italic: true };
        worksheet4.getCell("D6").value = `Tổng truy cập: ${totalClick}`;
        worksheet4.getCell("D6").font = { size: 12, italic: true };

        // Tạo khoảng trống trước bảng dữ liệu
        worksheet4.addRow([]);
        // Định nghĩa cột bắt đầu từ dòng thứ 5
        worksheet4.getRow(8).values = ["STT", "Hệ điều hành", "Số lượng"]; // Đặt tên cột vào dòng 5
        worksheet4.getColumn(1).width = 20;
        worksheet4.getColumn(2).width = 30;
        worksheet4.getColumn(3).width = 30;
        worksheet4.getColumn(4).width = 30;
        // Định dạng cột header
        worksheet4.getRow(8).font = { bold: true }; // Hàng tiêu đề
        worksheet4.getRow(8).alignment = { horizontal: "center", vertical: "middle" }; // Căn giữa
        // Thêm dữ liệu vào worksheet bắt đầu từ dòng thứ 7
        traffic.operatingSystems.forEach((item, index) => worksheet4.addRow([index + 1, item.name, parseInt(item.data)]));
    
    
    
        // Xuất file===================================
        const currentDate = new Date();
        const year = currentDate.getFullYear();
        const month = String(currentDate.getMonth() + 1).padStart(2, "0"); // Tháng bắt đầu từ 0
        const day = String(currentDate.getDate()).padStart(2, "0");
        const hours = String(currentDate.getHours()).padStart(2, "0");
        const minutes = String(currentDate.getMinutes()).padStart(2, "0");
        const seconds = String(currentDate.getSeconds()).padStart(2, "0");
        const formattedDate = `${year}-${month}-${day}_${hours}-${minutes}-${seconds}`;
    
        const buffer = await workbook.xlsx.writeBuffer();
        const blob = new Blob([buffer], { type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" });
        saveAs(blob, 'ExcelReportBy_'+ userObject.name + '_' + formattedDate +'.xlsx');
    };

    return (
        <div>
            <div className="hidden md:block">
                <div className='w-[70rem] h-96 flex flex-col justify-between items-center'>
                    <div className="flex space-x-4 mb-4 justify-end">
                        <button onClick={() => setChartType('day')} className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600">Biến động theo giờ</button>
                        <button onClick={() => setChartType('hour')} className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600">Biểu đồ phát triển</button>
                        <button onClick={() => setChartType('traffic')} className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600">Chi tiết các truy cập</button>
                        <button onClick={() => setChartType('geo')} className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600">Biểu đồ địa lý</button>
                        {/* <button onClick={handleExportPDF} className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600">Xuất các thống kê</button> */}
                        <button onClick={()=> {navigate('/statistic')}} className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600">Xuất báo cáo</button>
                        <button onClick={exportToExcel} className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600"><FontAwesomeIcon icon={faDownload} /> Excel</button>
                    </div>

                    <div ref={chartRef}>
                        {chartType === 'hour' ? (
                            <LineChart label="Biểu đồ theo ngày" labels={arrMonth.label} data={arrMonth.data} width={64} />
                        ) : chartType === 'day' ? (
                            <BarChart label="Biểu đồ theo giờ" labels={arrDay.label} data={arrDay.data} width={64}/>
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
                            <div>
                                <h4 className='font-semibold text-center'>Biểu đồ truy cập địa lý</h4>
                                <GeoChart label="Biểu đồ truy cập địa lý" labels={arrDay.label} data={traffic.zoneIds} width={60}/>
                            </div>
                        ) : null}   
                    </div>
                </div>
            </div>

            <div className="block md:hidden">
                <div className='flex flex-col justify-between items-center relative'>

                    <div className='flex justify-end items-end w-full'>
                        <button onClick={() => setMenuOptionOpen(!menuOptionOpen)} className="text-blue-500 mx-2">
                            {menuOptionOpen ? '✖️' :'☰'}
                        </button>
                    </div>

                    {menuOptionOpen && (
                        <ul className='absolute right-0 top-12 w-48 bg-white shadow-lg rounded-lg p-2 z-50' onMouseLeave={() => setMenuOpen(false)}>
                            <li className='flex items-center hover:bg-blue-500 hover:text-white rounded-md cursor-pointer'>
                                <div onClick={() => {setChartType('day'); setMenuOptionOpen(false)}} className="px-2 py-1">Biến động theo giờ</div>
                            </li>
                            <li className='flex items-center hover:bg-blue-500 hover:text-white rounded-md cursor-pointer'>
                                <div onClick={() => {setChartType('hour'); setMenuOptionOpen(false)}} className="px-2 py-1">Biểu đồ theo ngày</div>
                            </li>
                            <li className='flex items-center hover:bg-blue-500 hover:text-white rounded-md cursor-pointer'>
                                <div onClick={() => {setChartType('traffic'); setMenuOptionOpen(false)}} className="px-2 py-1">Chi tiết truy cập</div>
                            </li>
                            <li className='flex items-center hover:bg-blue-500 hover:text-white rounded-md cursor-pointer'>
                                <div onClick={() => {setChartType('geo'); setMenuOptionOpen(false)}} className="px-2 py-1">Biểu đồ địa lý</div>
                            </li>
                            <li className='flex items-center hover:bg-blue-500 hover:text-white rounded-md cursor-pointer'>
                                <div onClick={()=> {navigate('/statistic')}} className="px-2 py-1">Xuất các thống kê</div>
                            </li>
                            
                        </ul>
                    )}

                    {/* <div className="flex space-x-4 mb-4 justify-end">
                        <button onClick={() => setChartType('day')} className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600">Biến động theo giờ</button>
                        <button onClick={() => setChartType('hour')} className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600">Biểu đồ phát triển theo ngày</button>
                        <button onClick={() => setChartType('traffic')} className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600">Chi tiết các truy cập</button>
                        <button onClick={() => setChartType('geo')} className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600">Biểu đồ truy cập địa lý</button>
                        <button onClick={()=> {navigate('/statistic')}} className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600">Xuất các thống kê</button>
                    </div> */}

                    <div ref={chartRef}>
                        {chartType === 'hour' ? (
                            <LineChart label="Biểu đồ theo ngày" labels={arrMonth.label} data={arrMonth.data} width={20} />
                        ) : chartType === 'day' ? (
                            <BarChart label="Biểu đồ theo giờ" labels={arrDay.label} data={arrDay.data} width={20}/>
                        ) : chartType === 'traffic' ? (
                            <div className='flex flex-col'>
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
                            <div>
                                <h4 className='font-semibold text-center'>Biểu đồ truy cập địa lý</h4>
                                <GeoChart label="Biểu đồ truy cập địa lý" labels={arrDay.label} data={traffic.zoneIds} width={20}/>
                            </div>
                        ) : null}   
                    </div>
                </div>

            </div>
        </div>
    );
};

export default LineChartCustom;
