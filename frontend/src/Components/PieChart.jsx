import React from 'react';
import { Doughnut } from 'react-chartjs-2';
import 'chart.js/auto'; // Đảm bảo Chart.js được tự động đăng ký

// Hàm để tạo danh sách màu ngẫu nhiên dựa trên số lượng phần tử
const generateColors = (length) => {
  const backgroundColors = [];
  const borderColors = [];
  
  for (let i = 0; i < length; i++) {
    const r = Math.floor(Math.random() * 255);
    const g = Math.floor(Math.random() * 255);
    const b = Math.floor(Math.random() * 255);
    
    backgroundColors.push(`rgba(${r}, ${g}, ${b}, 0.2)`);
    borderColors.push(`rgba(${r}, ${g}, ${b}, 1)`);
  }

  return { backgroundColors, borderColors };
};

const PieChart = ({ label, data, labels }) => {
    const colors = generateColors(data.length); // Tạo màu dựa trên số lượng phần tử trong data

    // Bước 1: Tính tổng của mảng data
    const total = data.reduce((acc, value) => acc + parseInt(value), 0);

    // Bước 2: Tính phần trăm cho từng giá trị
    const dataPercentages = data.map(value => ((value / total) * 100).toFixed(2));
    console.log(dataPercentages   );
    
    const chartData = {
        labels: labels, // Nhận danh sách nhãn từ props
        datasets: [
        {
            label: label, // Nhận label từ props
            data: dataPercentages,   // Nhận data từ props
            // backgroundColor: colors.backgroundColors, // Sử dụng màu nền ngẫu nhiên
            // borderColor: colors.borderColors,         // Sử dụng màu viền ngẫu nhiên
            backgroundColor: [
                'rgba(255, 99, 132, 0.2)',
                'rgba(54, 162, 235, 0.2)',
                'rgba(255, 206, 86, 0.2)',
                'rgba(75, 192, 192, 0.2)',
                'rgba(153, 102, 255, 0.2)',
                'rgba(255, 159, 64, 0.2)',
            ],
            borderColor: [
                'rgba(255, 99, 132, 1)',
                'rgba(54, 162, 235, 1)',
                'rgba(255, 206, 86, 1)',
                'rgba(75, 192, 192, 1)',
                'rgba(153, 102, 255, 1)',
                'rgba(255, 159, 64, 1)',
            ],
            borderWidth: 1,
        },
        ],
    };

    const options = {
        responsive: true,
        maintainAspectRatio: false,
        cutout: '50%',  // Tùy chỉnh kích thước phần trống ở giữa (chỉ Doughnut)
        plugins: {
            title: {
                display: true, // Hiển thị tiêu đề
                text: label, // Nội dung tiêu đề từ props
                font: {
                    size: 18, // Kích thước chữ tiêu đề
                    family: 'Arial', // Font chữ
                    weight: 'bold', // Độ đậm chữ
                },
                color: '#333', // Màu chữ
                padding: {
                    top: 10, // Khoảng cách trên của tiêu đề
                    bottom: 30, // Khoảng cách dưới của tiêu đề
                },
                align: 'center', // Căn giữa tiêu đề
                },
            legend: {
                display: true, // Hiển thị chú thích
                position: 'top', // Vị trí của chú thích (trên cùng)
                labels: {
                    font: {
                        size: 14, // Kích thước chữ của chú thích
                    },
                    color: '#333', // Màu chữ của chú thích
                },
            },
            tooltip: {
                enabled: true, // Hiển thị tooltip khi hover
                callbacks: {
                    label: function (tooltipItem) {
                    return ` ${tooltipItem.label}: ${tooltipItem.raw}%`; // Tùy chỉnh nội dung tooltip
                    },
                },
            },
        },
    };

  return (
    <div className="flex justify-center items-center">
      <div className="w-64 h-64">
        <Doughnut data={chartData} options={options} />
      </div>
    </div>
  );
};

export default PieChart;
