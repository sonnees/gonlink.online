import React, { useEffect, useState } from 'react'
import Cookies from 'js-cookie';
import { useUser } from '../context/UserContext';
import { useNavigate } from 'react-router-dom';
import PieChart from '../Components/PieChart';
import GeoChart from '../Components/GeoChart';
import LineChart from '../Components/LineChart';
import { Bar } from 'react-chartjs-2';
import BarChart from '../Components/BarChart';
import ExcelJS from "exceljs";
import { saveAs } from "file-saver";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faDownload } from '@fortawesome/free-solid-svg-icons';

export default function LinkManagement() {
  const [selectedOption, setSelectedOption] = useState('all-days');
  const {userObject, setUserObject, authenticated, setAuthenticated, email, setEmail, name, setName, avatar, setAvatar, token, setToken} = useUser();
  const navigate = useNavigate();
  const [totalClick, setTotalClick] = useState(0)
  const [totalLink, setTotalLink] = useState(0)
  const [objData, setObjData] = useState()
  const [traficRealTime, setTraficRealTime] = useState([])


  useEffect(() => {
    if (Cookies.get('token')) {
      getInfo(Cookies.get('token'));
    } else {
      alert("Bạn chưa đăng nhập!");
      navigate("/");
      return ;
    }

    if (Cookies.get('token')) {
      getRealTime(Cookies.get('token'));
    }

    setTotalClick(JSON.parse(localStorage.getItem("userObj")).totalClick)
    setTotalLink(JSON.parse(localStorage.getItem("userObj")).totalShortURL)
    setObjData(JSON.parse(localStorage.getItem("userObj")))
    
  }, [])
  // console.log(objData);

  useEffect(() => {
    const intervalId = setInterval(() => {
      if (Cookies.get('token')) {
        getRealTime(Cookies.get('token'));
      }
    }, 5000);

    // Hủy interval khi component unmount
    return () => clearInterval(intervalId);
  }, []);


  const handleChange = (event) => {
    const value = event.target.value;
    setSelectedOption(value); // Cập nhật state
    console.log('Option selected:', value); // Gọi hàm xử lý khác tại đây
  };

  const getRealTime = async (token) => {
    const timeZone = Intl.DateTimeFormat().resolvedOptions().timeZone;
    try {
      const response = await fetch(
        `${process.env.REAL_TIME_ACCOUNT}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`,
          },
          body: JSON.stringify({
            "zoneId": timeZone,
          }),
        },
      );

      if (response.ok) {
        // Xử lý khi API trả về thành công
        const data = await response.json();
        console.log(data.data);
        
        setTraficRealTime(data.data)

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
        setName(data.data.name)
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
    // const data = [
    //   { Name: "John Doe", Age: 28, Email: "john@example.com" },
    //   { Name: "Jane Smith", Age: 34, Email: "jane@example.com" },
    //   { Name: "Mike Brown", Age: 45, Email: "mike@example.com" },
    // ];
  
    const reportTitle = "Báo cáo dữ liệu người dùng";
    const creator = objData.name;
    const email = objData.email;
    const creationDate = new Date().toLocaleString("vi-VN");
    const totalClick = objData.totalClick;
    const totalShortURL = objData.totalShortURL;
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
    worksheet.getCell("D3").value = `Tổng truy cập: ${totalClick}`;
    worksheet.getCell("D3").font = { size: 12, italic: true };
    worksheet.getCell("D4").value = `Tổng đường rút gọn: ${totalShortURL}`;
    worksheet.getCell("D4").font = { size: 12, italic: true };
    // Tạo khoảng trống trước bảng dữ liệu
    worksheet.addRow([]);
    // Định nghĩa cột bắt đầu từ dòng thứ 5
    worksheet.getRow(6).values = ["Tên trình duyệt", "Số lượng"]; // Đặt tên cột vào dòng 5
    worksheet.getColumn(1).width = 20;
    worksheet.getColumn(2).width = 10;
    worksheet.getColumn(3).width = 30;
    worksheet.getColumn(4).width = 30;
    // Định dạng cột header
    worksheet.getRow(6).font = { bold: true }; // Hàng tiêu đề
    worksheet.getRow(6).alignment = { horizontal: "center", vertical: "middle" }; // Căn giữa
    // Thêm dữ liệu vào worksheet bắt đầu từ dòng thứ 7
    objData.browsers.forEach((item) => worksheet.addRow([item.name, parseInt(item.data)]));

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
    worksheet2.getCell("D3").value = `Tổng truy cập: ${totalClick}`;
    worksheet2.getCell("D3").font = { size: 12, italic: true };
    worksheet2.getCell("D4").value = `Tổng đường rút gọn: ${totalShortURL}`;
    worksheet2.getCell("D4").font = { size: 12, italic: true };
    // Tạo khoảng trống trước bảng dữ liệu
    worksheet2.addRow([]);
    // Định nghĩa cột bắt đầu từ dòng thứ 5
    worksheet2.getRow(6).values = ["Loại thiết bị", "Số lượng"]; // Đặt tên cột vào dòng 5
    worksheet2.getColumn(1).width = 20;
    worksheet2.getColumn(2).width = 10;
    worksheet2.getColumn(3).width = 30;
    worksheet2.getColumn(4).width = 30;
    // Định dạng cột header
    worksheet2.getRow(6).font = { bold: true }; // Hàng tiêu đề
    worksheet2.getRow(6).alignment = { horizontal: "center", vertical: "middle" }; // Căn giữa
    // Thêm dữ liệu vào worksheet bắt đầu từ dòng thứ 7
    objData.deviceTypes.forEach((item) => worksheet2.addRow([item.name, parseInt(item.data)]));

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
    worksheet3.getCell("D3").value = `Tổng truy cập: ${totalClick}`;
    worksheet3.getCell("D3").font = { size: 12, italic: true };
    worksheet3.getCell("D4").value = `Tổng đường rút gọn: ${totalShortURL}`;
    worksheet3.getCell("D4").font = { size: 12, italic: true };
    // Tạo khoảng trống trước bảng dữ liệu
    worksheet3.addRow([]);
    // Định nghĩa cột bắt đầu từ dòng thứ 5
    worksheet3.getRow(6).values = ["Khu vực truy cập", "Số lượng"]; // Đặt tên cột vào dòng 5
    worksheet3.getColumn(1).width = 20;
    worksheet3.getColumn(2).width = 10;
    worksheet3.getColumn(3).width = 30;
    worksheet3.getColumn(4).width = 30;
    // Định dạng cột header
    worksheet3.getRow(6).font = { bold: true }; // Hàng tiêu đề
    worksheet3.getRow(6).alignment = { horizontal: "center", vertical: "middle" }; // Căn giữa
    // Thêm dữ liệu vào worksheet bắt đầu từ dòng thứ 7
    objData.zoneIds.forEach((item) => worksheet3.addRow([item.name, parseInt(item.data)]));

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
    worksheet4.getCell("D3").value = `Tổng truy cập: ${totalClick}`;
    worksheet4.getCell("D3").font = { size: 12, italic: true };
    worksheet4.getCell("D4").value = `Tổng đường rút gọn: ${totalShortURL}`;
    worksheet4.getCell("D4").font = { size: 12, italic: true };
    // Tạo khoảng trống trước bảng dữ liệu
    worksheet4.addRow([]);
    // Định nghĩa cột bắt đầu từ dòng thứ 5
    worksheet4.getRow(6).values = ["Hệ điều hành", "Số lượng"]; // Đặt tên cột vào dòng 5
    worksheet4.getColumn(1).width = 20;
    worksheet4.getColumn(2).width = 10;
    worksheet4.getColumn(3).width = 30;
    worksheet4.getColumn(4).width = 30;
    // Định dạng cột header
    worksheet4.getRow(6).font = { bold: true }; // Hàng tiêu đề
    worksheet4.getRow(6).alignment = { horizontal: "center", vertical: "middle" }; // Căn giữa
    // Thêm dữ liệu vào worksheet bắt đầu từ dòng thứ 7
    objData.operatingSystems.forEach((item) => worksheet4.addRow([item.name, parseInt(item.data)]));



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
    saveAs(blob, 'ExcelReportBy_'+ objData.name + '_' + formattedDate +'.xlsx');
  };
  
  
  


  return (
    <div>
      <div className="hidden md:block">
        <div className='bg-gray-100'>
          <div className='flex justify-between'>
            <div className='font-bold text-xl pt-5 px-3'>
              Thống kê
            </div>

            <div> 
              {/* <select className="mt-5 p-2 border rounded bg-white text-gray-700" value={selectedOption} onChange={handleChange}>
                <option value="all-days">Tất cả</option>
                <option value="week">Tuần gần nhất</option>
                <option value="month">Tháng gần nhất</option>
              </select> */}

              <button onClick={exportToExcel} className="mt-5 p-2 bg-blue-500 hover:bg-blue-700 cursor-pointer text-white font-bold mx-5 rounded"><FontAwesomeIcon icon={faDownload} /> Excel</button>
            </div>
          </div>

          <div className='flex justify-between items-center bg-white m-2 rounded-lg'>
            <div className='flex flex-col p-2 w-[20] m-5 text-center rounded-lg bg-white'>
              <div className='font-bold'>Tổng số link được rút gọn</div>
              <div>{totalLink}</div>
            </div>
            <div className='flex flex-col p-2 w-[20] m-5 text-center rounded-lg bg-white'>
              <div className='font-bold'>Tổng lượt truy cập</div>
              <div>{parseInt(totalClick)}</div>
            </div>
            <div className='flex flex-col p-2 w-[20] m-5 text-center rounded-lg bg-white'>
              <div className='font-bold'>Trung bình truy cập / link</div>
              <div>{totalLink !== 0 ? parseInt(totalClick / totalLink) : '0'}</div>
            </div>
          </div>

          <div className='flex m-2'>
            {objData && (
                <div className='w-full bg-white m-2 rounded-lg'>
                  <BarChart label="Biểu đồ truy cập theo thời gian thực" labels={Array.from({ length: 60 }, (_, i) => "")} data={traficRealTime.data} width={68} />
                </div>
              ) 
            }
          </div>

          <div className='flex m-2'>
            {objData && (
                <div className='w-72 h-80 bg-white m-2 rounded-lg'>
                  <PieChart label={"Biểu đồ khu vực truy cập"} labels={objData.zoneIds.map(item => item.name || "Không xác định")} data={objData.zoneIds.map(item => item.data)}/>
                </div>
              ) 
            }

            {objData && (
                <div className='w-72 h-80 bg-white m-2 rounded-lg'>
                  <PieChart label={"Biểu đồ trình duyệt truy cập"} labels={objData.browsers.map(item => item.name || "Không xác định")} data={objData.browsers.map(item => item.data)}/>
                </div>
              ) 
            }

            {objData && (
                <div className='w-72 h-80 bg-white m-2 rounded-lg'>
                  <PieChart label={"Biểu đồ hệ điều hành truy cập"} labels={objData.operatingSystems.map(item => item.name || "Không xác định")} data={objData.operatingSystems.map(item => item.data)}/>
                </div>
              ) 
            }

            {objData && (
                <div className='w-72 h-80 bg-white m-2 rounded-lg'>
                  <PieChart label={"Biểu đồ loại thiết bị truy cập"} labels={objData.deviceTypes.map(item => item.name || "Không xác định")} data={objData.deviceTypes.map(item => item.data)}/>
                </div>
              ) 
            }
          </div>

          <div className='flex m-2'>
            {objData && (
                <div className='w-full bg-white m-2 rounded-lg'>
                  <div className='font-bold text-center pt-2'>Biểu đồ quốc gia truy cập</div>
                  <GeoChart label="Biểu đồ quốc gia truy cập" data={objData.zoneIds} width={68}/>
                </div>
              ) 
            }
          </div>

        </div>

      </div>
      
      <div className="block md:hidden w-screen">
        <div className='bg-gray-100'>
          <div className='font-bold text-xl pt-5 px-3'>
            Thống kê
          </div>

          <div className='flex justify-between items-center bg-white m-2 rounded-lg'>
            <div className='flex flex-col p-2 my-5 text-center rounded-lg bg-white'>
              <div className='font-bold'>Tổng số link được rút gọn</div>
              <div>{totalLink}</div>
            </div>
            <div className='flex flex-col p-2 my-5 text-center rounded-lg bg-white'>
              <div className='font-bold'>Tổng lượt truy cập</div>
              <div>{parseInt(totalClick)}</div>
            </div>
            <div className='flex flex-col p-2 my-5 text-center rounded-lg bg-white'>
              <div className='font-bold'>Trung bình truy cập / link</div>
              <div>{totalLink !== 0 ? parseInt(totalClick / totalLink) : '0'}</div>
            </div>
          </div>

          <div className='flex m-2'>
            {objData && (
                <div className='w-full bg-white m-2 rounded-lg'>
                  <BarChart label="Biểu đồ truy cập theo thời gian thực" labels={Array.from({ length: 60 }, (_, i) => "")} data={traficRealTime.data} width={20} />
                </div>
              ) 
            }
          </div>

          <div className='flex-col items-center justify-items-center m-2'>
            {objData && (
                <div className='w-72 h-80 bg-white m-2 rounded-lg'>
                  <PieChart label={"Biểu đồ khu vực truy cập"} labels={objData.zoneIds.map(item => item.name || "Không xác định")} data={objData.zoneIds.map(item => item.data)}/>
                </div>
              ) 
            }

            {objData && (
                <div className='w-72 h-80 bg-white m-2 rounded-lg'>
                  <PieChart label={"Biểu đồ trình duyệt truy cập"} labels={objData.browsers.map(item => item.name || "Không xác định")} data={objData.browsers.map(item => item.data)}/>
                </div>
              ) 
            }

            {objData && (
                <div className='w-72 h-80 bg-white m-2 rounded-lg'>
                  <PieChart label={"Biểu đồ hệ điều hành truy cập"} labels={objData.operatingSystems.map(item => item.name || "Không xác định")} data={objData.operatingSystems.map(item => item.data)}/>
                </div>
              ) 
            }

            {objData && (
                <div className='w-72 h-80 bg-white m-2 rounded-lg'>
                  <PieChart label={"Biểu đồ loại thiết bị truy cập"} labels={objData.deviceTypes.map(item => item.name || "Không xác định")} data={objData.deviceTypes.map(item => item.data)}/>
                </div>
              ) 
            }
          </div>

          <div className='flex m-2'>
            {objData && (
                <div className='w-full bg-white m-2 rounded-lg'>
                  <div className='font-bold text-center pt-2'>Biểu đồ quốc gia truy cập</div>
                  <GeoChart label="Biểu đồ quốc gia truy cập" data={objData.zoneIds} width={20}/>
                </div>
              ) 
            }
          </div>

        </div>
      </div>
    </div>
    
  )
}
