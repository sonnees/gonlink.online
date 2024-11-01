import React, { useEffect, useState } from "react";
import { Chart } from "react-google-charts";



const timezoneToCountryCode = {
  "Asia/Saigon": "VN",          // Việt Nam
  "Pacific/Honolulu": "US",      // Hoa Kỳ (Hawaii)
  "Australia/Sydney": "AU",      // Úc
  "Asia/Seoul": "KR",            // Hàn Quốc
  "Europe/Paris": "FR",          // Pháp
  "America/Sao_Paulo": "BR",     // Brazil
  "Asia/Shanghai": "CN",         // Trung Quốc
  "America/New_York": "US",      // Hoa Kỳ (New York)
  "America/Los_Angeles": "US",   // Hoa Kỳ (Los Angeles)
  "America/Chicago": "US",       // Hoa Kỳ (Chicago)
  "America/Denver": "US",        // Hoa Kỳ (Denver)
  "Europe/London": "GB",         // Vương quốc Anh
  "Europe/Berlin": "DE",         // Đức
  "Europe/Madrid": "ES",         // Tây Ban Nha
  "Europe/Rome": "IT",           // Ý
  "Asia/Tokyo": "JP",            // Nhật Bản
  "Asia/Bangkok": "TH",          // Thái Lan
  "Asia/Kuala_Lumpur": "MY",     // Malaysia
  "Asia/Jakarta": "ID",          // Indonesia
  "Asia/Dubai": "AE",            // Các Tiểu vương quốc Ả Rập Thống nhất
  "Africa/Cairo": "EG",          // Ai Cập
  "Africa/Johannesburg": "ZA",   // Nam Phi
  "Europe/Moscow": "RU",         // Nga
  "America/Toronto": "CA",       // Canada
  "America/Mexico_City": "MX",   // Mexico
  "America/Argentina/Buenos_Aires": "AR", // Argentina
  "Pacific/Auckland": "NZ",      // New Zealand
  "Pacific/Fiji": "FJ",          // Fiji
  "Asia/Kolkata": "IN",          // Ấn Độ
  "Asia/Karachi": "PK",          // Pakistan
  "Asia/Dhaka": "BD",            // Bangladesh
};


const GeoChart = ({ label, data, labels, width }) => {

    const [timezoneData, setTimezoneData] = useState([
      ["Country", "Popularity"],
    ]);

    useEffect(() => {
      if (data) {
        const chartData = data.map(zone => [
          timezoneToCountryCode[zone.name] || "Unknown",
          parseInt(zone.data, 10),
        ]);
        setTimezoneData(prevData => [...prevData, ...chartData]);
      }
    }, [data]);



    // const data1 = [
    //     ["Country", "Popularity"],
    //     ["Germany", 2],
    //     ["United States", 16],
    //     ["Brazil", 12],
    //     ["Canada", 6],
    //     ["France", 4],
    //     ...data.map(item => [item.name, parseInt(item.data)])
    // ];

    console.log(timezoneData);
    console.log(data);
    

  return (
    <div className="flex justify-center items-center">
        {/* <div className={`w-[${width}rem] h-96`}> */}
        <div style={{ width: `${width}rem`, height: "24rem" }}>
            <Chart chartType="GeoChart" width="100%" height="100%" data={timezoneData}/>
        </div>
    </div>
  );
};

export default GeoChart;
