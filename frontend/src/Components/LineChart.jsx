import React from 'react';
import { Line } from 'react-chartjs-2';
import 'chart.js/auto'; // Đảm bảo Chart.js được tự động đăng ký

const LineChart = ({ label, data, labels, width }) => {

  const chartData = {
    labels: labels, // Nhận nhãn từ props
    datasets: [
      {
        label: label, // Nhận label từ props
        data: data,   // Nhận data từ props
        borderColor: 'rgba(75, 192, 192, 1)', // Màu đường biểu đồ
        backgroundColor: 'rgba(75, 192, 192, 0.2)', // Màu nền cho vùng dưới đường
        tension: 0.4, // Độ cong của đường biểu đồ
        pointBackgroundColor: 'rgba(75, 192, 192, 1)', // Màu của các điểm
        pointBorderColor: '#fff', // Màu viền của các điểm
        fill: true, // Có lấp đầy dưới đường hay không
      },
    ],
  };

  const options = {
    responsive: true,
    maintainAspectRatio: false, // Giữ tỉ lệ mặc định của chart
    plugins: {
      title: {
        display: true,
        text: label, // Hiển thị tiêu đề từ props
        font: {
          size: 18,
          family: 'Arial',
          weight: 'bold',
        },
        color: '#333',
        padding: {
          top: 10,
          bottom: 20,
        },
        align: 'center',
      },
      legend: {
        display: true, // Hiển thị chú thích
        position: 'top',
        labels: {
          font: {
            size: 14,
          },
          color: '#333',
        },
      },
      tooltip: {
        enabled: true,
        callbacks: {
          label: function (tooltipItem) {
            return ` ${tooltipItem.label}: ${tooltipItem.raw}`; // Nội dung khi hover vào từng điểm
          },
        },
      },
    },
    scales: {
      x: {
        title: {
          display: true,
          text: 'Thời gian',
          font: {
            size: 14,
            weight: 'bold',
          },
        },
      },
      y: {
        title: {
          display: true,
          text: 'Giá trị',
          font: {
            size: 14,
            weight: 'bold',
          },
        },
        ticks: {
        stepSize: 5, // Khoảng cách giữa các giá trị trên trục Y
        font: {
            size: 12, // Kích thước chữ của các giá trị
        },
        color: '#333', // Màu của các giá trị trên trục Y
        },
        beginAtZero: true, // Bắt đầu trục Y từ giá trị 0
      },
    },
  };
  

  return (
    <div className="flex justify-center items-center">
      {/* <div className={`w-[${width}rem] h-96`}> */}
      <div style={{ width: `${width}rem`, height: "24rem" }}>
        <Line data={chartData} options={options} />
      </div>
    </div>
  );
};

export default LineChart;
