import React from 'react';
import { Bar } from 'react-chartjs-2';
import 'chart.js/auto'; // Đảm bảo Chart.js được tự động đăng ký

const BarChart = ({ label, data, labels }) => {

  const chartData = {
    labels: labels, // Nhận nhãn từ props
    datasets: [
      {
        label: label, // Nhận label từ props
        data: data,   // Nhận data từ props
        backgroundColor: 'rgba(75, 192, 192, 0.5)', // Màu nền cho các cột
        borderColor: 'rgba(75, 192, 192, 1)',       // Màu viền cho các cột
        borderWidth: 1,
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
        display: false, // Ẩn chú thích
      },
      tooltip: {
        enabled: true,
        callbacks: {
          label: function (tooltipItem) {
            return ` ${tooltipItem.label}: ${tooltipItem.raw}`; // Nội dung khi hover vào cột
          },
        },
      },
    },
    scales: {
      x: {
        title: {
          display: true,
          text: 'Thời gian', // Tiêu đề cho trục X
          font: {
            size: 14,
            weight: 'bold',
          },
        },
      },
      y: {
        title: {
          display: true,
          text: 'Giá trị', // Tiêu đề cho trục Y
          font: {
            size: 14,
            weight: 'bold',
          },
        },
        ticks: {
          stepSize: 5, // Khoảng cách giữa các giá trị trên trục Y
          font: {
            size: 12,
          },
          color: '#333',
        },
        beginAtZero: true, // Bắt đầu trục Y từ giá trị 0
      },
    },
  };

  return (
    <div className="flex justify-center items-center">
      <div className="w-[68rem] h-96">
        <Bar data={chartData} options={options} />
      </div>
    </div>
  );
};

export default BarChart;
