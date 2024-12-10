import React, { useEffect, useRef, useState } from 'react'
import Cookies from 'js-cookie';
import jsPDF from 'jspdf';
import html2canvas from 'html2canvas';
import timesNewRomanFont from '../font/TimesNewRoman';
import LineChartCustom from '../Components/LineChartCustom';
import LineChart from '../Components/LineChart';
import BarChart from '../Components/BarChart';
import GeoChart from '../Components/GeoChart';
import PieChart from '../Components/PieChart';

export default function Statistic() {

  const [arrDay, setArrDay] = useState({label:[], data:[]});
  const [arrMonth, setArrMonth] = useState({label:[], data:[]});
  const [traffic, setTraffic] = useState();

  const [statisticData, setStatisticData] = useState({});
  const [userData, setUserData] = useState({});
  const [dayRangeRaw, setDayRangeRaw] = useState({});

  const [checkedItems, setCheckedItems] = useState([false, false, false, false]); // Mảng để lưu trạng thái checkbox

  const lineChartRef = useRef();
  const barChartRef = useRef();
  const pieChartRef = useRef();
  const geoChartRef = useRef();

  const lineChartRefMobile = useRef();
  const barChartRefMobile = useRef();
  const pieChartRefMobile = useRef();
  const geoChartRefMobile = useRef();




  const handleToggle = (index) => {
    // Tạo một bản sao mới của mảng checkedItems và thay đổi trạng thái checkbox ở vị trí index
    const updatedCheckedItems = checkedItems.map((item, i) =>
      i === index ? !item : item // Đảo ngược giá trị chỉ của checkbox tại index
    );
    setCheckedItems(updatedCheckedItems); // Cập nhật trạng thái
  };

  // console.log(shortCodeData);

  useEffect(() => {
    if (JSON.parse(localStorage.getItem("link"))) {
      setStatisticData(JSON.parse(localStorage.getItem("link")))
    }

    if (JSON.parse(localStorage.getItem("userObj"))) {
      setUserData(JSON.parse(localStorage.getItem("userObj")))
    }

    // setStatisticData({clicks: 16, shortCode:"HHzrM9", originalUrl: "https://cloud.mongodb.com/v2/63f46a2b2ed57d7fd1474d80#/metrics/replicaSet/65ef10c71033053ac4768cac/explorer/gonlink/account/find"})
  }, [])

  useEffect(() => {
    console.log(statisticData);

    if (Cookies.get('token') && statisticData) {
      getArrMonth(Cookies.get('token'), statisticData.shortCode);
      getArrDay(Cookies.get('token'), statisticData.shortCode);
      getArrDayAll(Cookies.get('token'), statisticData.shortCode);
    }
    
  }, [statisticData])

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
            setDayRangeRaw(data.data)
            const extract = extractDatesAndValues(data.data.click)
            console.log(extract);
            
            setArrDay({
                label: extract.dates,
                data: extract.values
            })

            // setTraffic(data.data)
            
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


const handleExportPDF = async () => {
  const pdf = new jsPDF();
  const pageWidth = pdf.internal.pageSize.getWidth();
  const pageHeight = pdf.internal.pageSize.getHeight();

   // Thêm chữ vào vị trí (40, 40)
  pdf.addFileToVFS('TimesNewRoman.ttf', timesNewRomanFont);
  pdf.addFont('TimesNewRoman.ttf', 'TimesNewRoman', 'normal');
  pdf.setFont('TimesNewRoman');

  pdf.setFontSize(20);
  const title = "BÁO CÁO THỐNG KÊ RÚT GỌN LINK";
  const textWidth = pdf.getTextWidth(title);
  const x = (pageWidth - textWidth) / 2;

  pdf.text(title, x, 20);
  pdf.setFontSize(16);
  pdf.text("Chủ sở hữu đường dẫn: " + userData.name, 20, 40);
  pdf.text("Đường dẫn rút gọn: " + `${process.env.HOST_PAGE}` + "/" + statisticData.shortCode, 20, 50);

  // Các thông tin bổ sung
  pdf.text("Thông tin chi tiết:", 20, 70);
  pdf.text("- Ngày tạo: " + new Date().toLocaleDateString("vi-VN"), 30, 80);
  pdf.text("- Lượt click: " + statisticData.traffic, 30, 90);
  pdf.text("- Thời gian hết hạn: Không giới hạn", 30, 100);
  
  // Ghi chú
  pdf.text("Ghi chú:", 20, 120);
  pdf.text("Báo cáo này cung cấp thông tin về lượt truy cập và trạng thái của đường dẫn rút gọn.", 30, 130, { maxWidth: pageWidth - 40 });


  // Hàm phụ để kiểm tra và thêm hình ảnh vào PDF
  const addChartToPDF = async (chartRef) => {
    
    if (chartRef?.current) {
      pdf.addPage();
      const chartCanvas = await html2canvas(chartRef.current);
      const chartImgData = chartCanvas.toDataURL('image/png');

      // Kích thước thu nhỏ 80%
      const scaledWidth = pageWidth * 0.8;
      const scaledHeight = (chartCanvas.height * scaledWidth) / chartCanvas.width;

      console.log(chartImgData);
      pdf.addImage(
        chartImgData, 
        'PNG', 
        (pageWidth - scaledWidth) / 2, 
        20, 
        scaledWidth, 
        scaledHeight
      );
    }
  };

  // Thêm biểu đồ vào PDF nếu tồn tại
  await addChartToPDF(barChartRef);
  if (barChartRef?.current) {
    pdf.text("Ghi chú:", 20, 140);

    const peakHour = dayRangeRaw.click.reduce((max, current) => {
      return Number(current.data) > Number(max.data) ? current : max;
    });

    // Nhận xét tổng quát
    pdf.text("Nhận xét tổng quát:", 20, 150);
    pdf.text("Giờ cao điểm là " + peakHour.name + " với " + peakHour.data + " lượt truy cập.", 30, 160, { maxWidth: pageWidth - 40 });
    // pdf.text("Sau giờ cao điểm, lượt truy cập giảm mạnh, cho thấy nhu cầu có thể giảm sau khoảng thời gian đó.", 30, 180, { maxWidth: pageWidth - 40 });


    // Tạo phân tích
    // dayRangeRaw.click.forEach((hourData, index) => {
    //   pdf.text(`${hourData.name}: ${hourData.data} lượt truy cập`, 30, 200 + index * 10, { maxWidth: pageWidth - 40 });
    // });

    

  }

  await addChartToPDF(lineChartRef);
  // if (lineChartRef?.current) {
  //   pdf.text("Ghi chú:", 20, 150);
  // }
  await addChartToPDF(pieChartRef);
  await addChartToPDF(geoChartRef);
  if (geoChartRef?.current) {
    if (traffic.zoneIds!=[]) {
      const maxZone = traffic.zoneIds.reduce((max, current) => {
        return Number(current.data) > Number(max.data) ? current : max;
      }, { data: -Infinity });

      if (maxZone.name != undefined) {
        pdf.text("Ghi chú:", 20, 150);
        pdf.text("Khu vực có truy cập cao nhất: " + maxZone.name + ", có " + maxZone.data + " lượt truy cập.", 30, 160, { maxWidth: pageWidth - 40 });
    
        // Lọc các khu vực còn lại và thêm vào PDF
        let yPosition = 170; // Vị trí y bắt đầu ghi chú các khu vực còn lại
        traffic.zoneIds
          .filter(zone => zone.name !== maxZone.name) // Bỏ khu vực có lượng truy cập cao nhất
          .forEach(zone => {
            pdf.text("- " + zone.name + ": " + zone.data + " lượt truy cập.", 30, yPosition, { maxWidth: pageWidth - 40 });
            yPosition += 10; // Tăng vị trí y cho dòng tiếp theo
          });
      } else {
        pdf.text("Ghi chú:", 20, 150);
        pdf.text("Không có dữ liệu", 30, 160);
      }
    }
  
    
  }
  // Mở URL trong tab mới
  // const pdfBlob = pdf.output("blob");
  // const pdfURL = URL.createObjectURL(pdfBlob);
  // window.open(pdfURL, "_blank");

  // Lưu PDF
  const currentDate = new Date();
  const year = currentDate.getFullYear();
  const month = String(currentDate.getMonth() + 1).padStart(2, "0"); // Tháng bắt đầu từ 0
  const day = String(currentDate.getDate()).padStart(2, "0");
  const hours = String(currentDate.getHours()).padStart(2, "0");
  const minutes = String(currentDate.getMinutes()).padStart(2, "0");
  const seconds = String(currentDate.getSeconds()).padStart(2, "0");
  const formattedDate = `${year}-${month}-${day}_${hours}-${minutes}-${seconds}`;
  pdf.save('StatisticsReportBy_'+ userData.name + '_' + formattedDate +'.pdf');
};


const handleExportPDFMobile = async () => {
  const pdf = new jsPDF();
  const pageWidth = pdf.internal.pageSize.getWidth();
  const pageHeight = pdf.internal.pageSize.getHeight();

   // Thêm chữ vào vị trí (40, 40)
  pdf.addFileToVFS('TimesNewRoman.ttf', timesNewRomanFont);
  pdf.addFont('TimesNewRoman.ttf', 'TimesNewRoman', 'normal');
  pdf.setFont('TimesNewRoman');

  pdf.setFontSize(20);
  const title = "BÁO CÁO THỐNG KÊ RÚT GỌN LINK";
  const textWidth = pdf.getTextWidth(title);
  const x = (pageWidth - textWidth) / 2;

  pdf.text(title, x, 20);
  pdf.setFontSize(16);
  pdf.text("Chủ sở hữu đường dẫn: " + userData.name, 20, 40);
  pdf.text("Đường dẫn rút gọn: " + `${process.env.HOST_PAGE}` + "/" + statisticData.shortCode, 20, 50);

  // Các thông tin bổ sung
  pdf.text("Thông tin chi tiết:", 20, 70);
  pdf.text("- Ngày tạo: " + new Date().toLocaleDateString("vi-VN"), 30, 80);
  pdf.text("- Lượt click: " + statisticData.traffic, 30, 90);
  pdf.text("- Thời gian hết hạn: Không giới hạn", 30, 100);
  
  // Ghi chú
  pdf.text("Ghi chú:", 20, 120);
  pdf.text("Báo cáo này cung cấp thông tin về lượt truy cập và trạng thái của đường dẫn rút gọn.", 30, 130, { maxWidth: pageWidth - 40 });


  // Hàm phụ để kiểm tra và thêm hình ảnh vào PDF
  const addChartToPDF = async (chartRef) => {
    
    if (chartRef?.current) {
      pdf.addPage();
      const chartCanvas = await html2canvas(chartRef.current);
      const chartImgData = chartCanvas.toDataURL('image/png');

      // Kích thước thu nhỏ 80%
      const scaledWidth = pageWidth * 0.8;
      const scaledHeight = (chartCanvas.height * scaledWidth) / chartCanvas.width;

      console.log(chartImgData);
      pdf.addImage(
        chartImgData, 
        'PNG', 
        (pageWidth - scaledWidth) / 2, 
        20, 
        scaledWidth, 
        scaledHeight
      );
    }
  };

  // Thêm biểu đồ vào PDF nếu tồn tại
  await addChartToPDF(barChartRefMobile);
  if (barChartRefMobile?.current) {
    pdf.text("Ghi chú:", 20, 140);

    const peakHour = dayRangeRaw.click.reduce((max, current) => {
      return Number(current.data) > Number(max.data) ? current : max;
    });

    // Nhận xét tổng quát
    pdf.text("Nhận xét tổng quát:", 20, 150);
    pdf.text("Giờ cao điểm là " + peakHour.name + " với " + peakHour.data + " lượt truy cập, cho thấy sự quan tâm cao trong khoảng thời gian này.", 30, 160, { maxWidth: pageWidth - 40 });
    pdf.text("Sau giờ cao điểm, lượt truy cập giảm mạnh, cho thấy nhu cầu có thể giảm sau khoảng thời gian đó.", 30, 180, { maxWidth: pageWidth - 40 });


    // Tạo phân tích
    // dayRangeRaw.click.forEach((hourData, index) => {
    //   pdf.text(`${hourData.name}: ${hourData.data} lượt truy cập`, 30, 200 + index * 10, { maxWidth: pageWidth - 40 });
    // });

    

  }

  await addChartToPDF(lineChartRefMobile);
  // if (lineChartRef?.current) {
  //   pdf.text("Ghi chú:", 20, 150);
  // }
  await addChartToPDF(pieChartRefMobile);
  await addChartToPDF(geoChartRefMobile);
  if (geoChartRefMobile?.current) {
    if (traffic.zoneIds!=[]) {
      const maxZone = traffic.zoneIds.reduce((max, current) => {
        return Number(current.data) > Number(max.data) ? current : max;
      }, { data: -Infinity });

      pdf.text("Ghi chú:", 20, 150);
      pdf.text("Khu vực có truy cập cao nhất: " + maxZone.name + ", có " + maxZone.data + " lượt truy cập.", 30, 160, { maxWidth: pageWidth - 40 });
  
      // Lọc các khu vực còn lại và thêm vào PDF
      let yPosition = 170; // Vị trí y bắt đầu ghi chú các khu vực còn lại
      traffic.zoneIds
        .filter(zone => zone.name !== maxZone.name) // Bỏ khu vực có lượng truy cập cao nhất
        .forEach(zone => {
          pdf.text("- " + zone.name + ": " + zone.data + " lượt truy cập.", 30, yPosition, { maxWidth: pageWidth - 40 });
          yPosition += 10; // Tăng vị trí y cho dòng tiếp theo
        });
    }
  }
  // Mở URL trong tab mới
  // const pdfBlob = pdf.output("blob");
  // const pdfURL = URL.createObjectURL(pdfBlob);
  // window.open(pdfURL, "_blank");

  // Lưu PDF
  const currentDate = new Date();
  const year = currentDate.getFullYear();
  const month = String(currentDate.getMonth() + 1).padStart(2, "0"); // Tháng bắt đầu từ 0
  const day = String(currentDate.getDate()).padStart(2, "0");
  const hours = String(currentDate.getHours()).padStart(2, "0");
  const minutes = String(currentDate.getMinutes()).padStart(2, "0");
  const seconds = String(currentDate.getSeconds()).padStart(2, "0");
  const formattedDate = `${year}-${month}-${day}_${hours}-${minutes}-${seconds}`;
  pdf.save('StatisticsReportBy_'+ userData.name + '_' + formattedDate +'.pdf');
};

  return (
    <div>
      <div className="hidden md:block">
        <div className='flex bg-gray-100 h-screen'>
          <div className='w-[25%]'>
            <ul className='flex flex-col items-center justify-center p-2 my-5 space-y-2 bg-white rounded-lg py-5'>
              <li className='flex items-center p-2 w-[80%] rounded-md m-1 font-semibold text-lg'>Lựa chọn nội dung báo cáo</li>
              {['Nội dung thống kê theo giờ', 'Nội dung phát triển theo ngày', 'Nội dung chi tiết truy cập', 'Nội dung về khu vực truy cập'].map((item, index) => (
                <li
                  key={index}
                  className='flex items-center hover:bg-gray-200 hover:font-bold cursor-pointer p-2 w-[80%] rounded-md m-1'
                  onClick={() => handleToggle(index)} // Cập nhật trạng thái checkbox khi nhấp vào
                >
                  <input
                    type="checkbox"
                    checked={checkedItems[index]} // Gán giá trị checked từ trạng thái
                    readOnly // Ngăn người dùng tương tác trực tiếp
                    className="mr-2"
                  />
                  {item}
                </li>
              ))}
            </ul>

            <div>
              <button onClick={handleExportPDF} className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600">Xuất các thống kê</button>
            </div>
          </div>
          <div className='w-[75%] overflow-auto'>
            <div className='flex flex-col items-center justify-center border rounded-lg p-5 m-5'>
              <div className='w-[794px] flex flex-col bg-white rounded-lg p-20 py-20'>
                <div className='hover:bg-gray-100 text-center font-bold text-xl my-1'>
                  <h1>Báo cáo thống kê cho đường dẫn</h1>
                </div>

                <div className='hover:bg-gray-100 my-1'>
                  <h4 className='text-lg'>Báo cáo thống kê cho đường dẫn</h4>
                  {/* <p className=''>Đường dẫn Gốc: {statisticData.originalUrl}</p> */}
                  <p className=''>Đường dẫn rút gọn: {`${process.env.HOST_PAGE}` + "/" + statisticData.shortCode}</p>
                </div>

                {checkedItems[0] && <div className='hover:bg-gray-100 my-2' ref={barChartRef}>
                  {/* <h4 className='text-lg'>Nội dung thống kê theo giờ</h4> */}
                  <BarChart label="Biểu đồ theo giờ" labels={arrDay.label} data={arrDay.data} width={68}/>
                  
                </div>}

                {checkedItems[1] && <div className='hover:bg-gray-100 my-2' ref={lineChartRef} >
                  {/* <h4 className='text-lg'>Nội dung phát triển theo ngày</h4> */}
                  <LineChart label="Biểu đồ theo ngày" labels={arrMonth.label} data={arrMonth.data} width={68} />
                  
                </div>}

                {checkedItems[2] && <div className='hover:bg-gray-100 my-2' ref={pieChartRef}>
                  {/* <h4 className='text-lg'>Nội dung chi tiết truy cập</h4> */}

                  <div className='flex'>
                    {traffic && (<div className='w-72 h-80 bg-white m-2 rounded-lg border'>
                      <PieChart 
                        label="Biểu đồ trình duyệt truy cập" 
                        labels={traffic.browsers.map(item => item.name || "Không xác định")} 
                        data={traffic.browsers.map(item => item.data)} 
                      />
                    </div>)}

                    {traffic && (<div className='w-72 h-80 bg-white m-2 rounded-lg border'>
                      <PieChart 
                        label="Biểu đồ phiên bản trình duyệt" 
                        labels={traffic.browserVersions.map(item => item.name || "Không xác định")} 
                        data={traffic.browserVersions.map(item => item.data)} 
                      />
                    </div>)}
                  </div>

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
                  </div>

                  
                </div>}

                {checkedItems[3] && <div className='hover:bg-gray-100 my-2' ref={geoChartRef}>
                  {/* <h4 className='text-lg my-2'>Nội dung về khu vực truy cập</h4> */}
                  <GeoChart label="Biểu đồ theo giờ" labels={arrDay.label} data={traffic.zoneIds} width={68}/>
                </div>}

              </div>
            </div>
          </div>
            
        </div>
      </div>

      <div className="block md:hidden">
        <div className='flex flex-col bg-gray-100 h-screen'>
          <div className=''>
            <ul className='flex flex-col items-center justify-center p-1 space-y-2 bg-white rounded-lg'>
              <li className='flex items-center w-[80%] rounded-md font-semibold text-lg'>Lựa chọn nội dung báo cáo</li>
              {['Nội dung thống kê theo giờ', 'Nội dung phát triển theo ngày', 'Nội dung chi tiết truy cập', 'Nội dung về khu vực truy cập'].map((item, index) => (
                <li
                  key={index}
                  className='flex items-center hover:bg-gray-200 cursor-pointer p-2 w-[80%] rounded-md m-1'
                  onClick={() => handleToggle(index)} // Cập nhật trạng thái checkbox khi nhấp vào
                >
                  <input
                    type="checkbox"
                    checked={checkedItems[index]} // Gán giá trị checked từ trạng thái
                    readOnly // Ngăn người dùng tương tác trực tiếp
                    className="mr-2"
                  />
                  {item}
                </li>
              ))}
            </ul>

            <div className='flex justify-center w-full'>
              <button onClick={handleExportPDFMobile} className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600">Xuất các thống kê</button>
            </div>
          </div>

          <div className='overflow-auto'>
            <div className='flex flex-col items-center justify-center'>
              <div className='flex flex-col bg-white rounded-lg p-10 py-10'>
                <div className='hover:bg-gray-100 text-center font-bold text-xl my-1'>
                  <h1>Báo cáo thống kê cho đường dẫn</h1>
                </div>

                <div className='hover:bg-gray-100 my-1'>
                  <h4 className='text-lg'>Báo cáo thống kê cho đường dẫn</h4>
                  {/* <p className=''>Đường dẫn Gốc: {statisticData.originalUrl}</p> */}
                  <p className=''>Đường dẫn rút gọn: {`${process.env.HOST_PAGE}` + "/" + statisticData.shortCode}</p>
                </div>

                {checkedItems[0] && <div className='hover:bg-gray-100 my-2' ref={barChartRefMobile}>
                  <h4 className='text-lg'>Nội dung thống kê theo giờ</h4>
                  <BarChart label="Biểu đồ theo giờ" labels={arrDay.label} data={arrDay.data} width={20}/>
                  
                </div>}

                {checkedItems[1] && <div className='hover:bg-gray-100 my-2' ref={lineChartRefMobile} >
                  <h4 className='text-lg'>Nội dung phát triển theo ngày</h4>
                  <LineChart label="Biểu đồ theo ngày" labels={arrMonth.label} data={arrMonth.data} width={20} />
                  
                </div>}

                {checkedItems[2] && <div className='hover:bg-gray-100 my-2' ref={pieChartRefMobile}>
                  <h4 className='text-lg'>Nội dung chi tiết truy cập</h4>

                  <div className='flex flex-col'>
                    {traffic && (<div className='w-72 h-80 bg-white m-2 rounded-lg border'>
                      <PieChart 
                        label="Biểu đồ trình duyệt truy cập" 
                        labels={traffic.browsers.map(item => item.name || "Không xác định")} 
                        data={traffic.browsers.map(item => item.data)} 
                      />
                    </div>)}

                    {traffic && (<div className='w-72 h-80 bg-white m-2 rounded-lg border'>
                      <PieChart 
                        label="Biểu đồ phiên bản trình duyệt" 
                        labels={traffic.browserVersions.map(item => item.name || "Không xác định")} 
                        data={traffic.browserVersions.map(item => item.data)} 
                      />
                    </div>)}
                  </div>

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
                  </div>

                  
                </div>}

                {checkedItems[3] && <div className='hover:bg-gray-100 my-2' ref={geoChartRefMobile}>
                  <h4 className='text-lg my-2'>Nội dung về khu vực truy cập</h4>
                  <GeoChart label="Biểu đồ theo giờ" labels={arrDay.label} data={traffic.zoneIds} width={20}/>
                </div>}

              </div>
            </div>
          </div>
            
        </div>

      </div>
      
    </div>
  )
}
