import { faCalendar } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import React from 'react'

export default function About() {
  return (
    <div>
      <div className="hidden md:block">
        <div className='w-full'>
          <div className="flex-col justify-center items-center">
            <div className='flex justify-center p-16 bg-blue-100'>
              <h1 className="text-5xl font-bold">Thông tin</h1>
            </div>

            <div className='bg-gray-100 relative shadow-2xl py-20'>
              <div className="absolute top-0 left-1/2 transform -translate-x-1/2 -translate-y-1/2 bg-white px-60 py-4 rounded-2xl shadow-2xl flex items-center space-x-2">
                <FontAwesomeIcon icon={faCalendar} />
                <span className="text-gray-600">Last Updated October 22, 2024</span>
              </div>

              <div className='max-w-5xl mx-auto p-6 bg-gray-100'>
                <p className="text-base">
                  Bạn đang tìm kiếm một công cụ rút gọn link uy tín và hiệu quả tại Việt Nam? Với sự phát triển mạnh mẽ của internet, việc chia sẻ các đường link dài đôi khi trở thành một thách thức lớn. Đó là lý do tại sao dịch vụ rút gọn link của chúng tôi ra đời, mang đến cho bạn giải pháp tối ưu với nhiều lợi ích vượt trội.
                </p>
                <div className="my-2 py-2">
                  <h2 className="text-xl font-semibold text-blue-600">Tốc Độ Nhanh Chóng</h2>
                  <p className="text-base">
                    Trong thế giới số hóa hiện nay, tốc độ là yếu tố quyết định. Dịch vụ rút gọn link của chúng tôi cam kết mang lại tốc độ chuyển hướng nhanh nhất, đảm bảo người dùng không bị gián đoạn khi truy cập. Với công nghệ tiên tiến, mỗi cú nhấp chuột sẽ dẫn dắt người dùng đến đúng đích chỉ trong tích tắc.
                  </p>
                </div>
                <div className="my-2 py-2">
                  <h2 className="text-xl font-semibold text-blue-600">Liên Kết Lưu Trữ Dài Hạn</h2>
                  <p className="text-base">
                    Một trong những ưu điểm nổi bật của dịch vụ rút gọn link của chúng tôi là khả năng lưu trữ liên kết dài hạn. Bạn sẽ không phải lo lắng về việc link bị hỏng hay hết hạn. Chúng tôi đảm bảo rằng các liên kết của bạn sẽ luôn hoạt động ổn định và sẵn sàng phục vụ nhu cầu chia sẻ thông tin lâu dài.
                  </p>
                </div>
                <div className="my-2 py-2">
                  <h2 className="text-xl font-semibold text-blue-600">Chuyển Hướng Không Có Quảng Cáo</h2>
                  <p className="text-base">
                    Không giống như nhiều dịch vụ rút gọn link khác, chúng tôi không chèn quảng cáo vào quá trình chuyển hướng. Điều này giúp người dùng có trải nghiệm mượt mà, không bị làm phiền bởi các nội dung quảng cáo không mong muốn. Bạn có thể yên tâm rằng, mỗi liên kết rút gọn đều đảm bảo sự liền mạch và chuyên nghiệp.
                  </p>
                </div>
                <p className="text-base">
                  Hãy trải nghiệm ngay dịch vụ rút gọn link của chúng tôi để cảm nhận sự khác biệt. Với tốc độ nhanh chóng, khả năng lưu trữ dài hạn và chuyển hướng không quảng cáo, chúng tôi tự tin sẽ là lựa chọn hoàn hảo cho mọi nhu cầu chia sẻ link của bạn.
                </p>          
              </div>  
            </div>
            
          </div>              
        </div>
      </div>


      <div className="block md:hidden">
        <div className='w-full'>
          <div className="flex-col justify-center items-center">
            <div className='flex justify-center px-4 py-16 bg-blue-100'>
              <h1 className="text-3xl font-bold">Thông tin</h1>
            </div>

            <div className='bg-gray-100 relative shadow-2xl py-10'>
              <div className="absolute top-0 left-1/2 transform -translate-x-1/2 -translate-y-1/2 bg-white px-4 py-2 rounded-2xl shadow-2xl w-[80%]">
                <FontAwesomeIcon icon={faCalendar}/>
                <span className="text-gray-600 text-sm pl-2">Last Updated October 22, 2024</span>
              </div>

              <div className='max-w-5xl mx-auto p-6 bg-gray-100'>
                <p className="text-sm">
                  Bạn đang tìm kiếm một công cụ rút gọn link uy tín và hiệu quả tại Việt Nam? Với sự phát triển mạnh mẽ của internet, việc chia sẻ các đường link dài đôi khi trở thành một thách thức lớn. Đó là lý do tại sao dịch vụ rút gọn link của chúng tôi ra đời, mang đến cho bạn giải pháp tối ưu với nhiều lợi ích vượt trội.
                </p>
                <div className="my-2 py-2">
                  <h2 className="text-xl font-semibold text-blue-600">Tốc Độ Nhanh Chóng</h2>
                  <p className="text-sm">
                    Trong thế giới số hóa hiện nay, tốc độ là yếu tố quyết định. Dịch vụ rút gọn link của chúng tôi cam kết mang lại tốc độ chuyển hướng nhanh nhất, đảm bảo người dùng không bị gián đoạn khi truy cập. Với công nghệ tiên tiến, mỗi cú nhấp chuột sẽ dẫn dắt người dùng đến đúng đích chỉ trong tích tắc.
                  </p>
                </div>
                <div className="my-2 py-2">
                  <h2 className="text-xl font-semibold text-blue-600">Liên Kết Lưu Trữ Dài Hạn</h2>
                  <p className="text-sm">
                    Một trong những ưu điểm nổi bật của dịch vụ rút gọn link của chúng tôi là khả năng lưu trữ liên kết dài hạn. Bạn sẽ không phải lo lắng về việc link bị hỏng hay hết hạn. Chúng tôi đảm bảo rằng các liên kết của bạn sẽ luôn hoạt động ổn định và sẵn sàng phục vụ nhu cầu chia sẻ thông tin lâu dài.
                  </p>
                </div>
                <div className="my-2 py-2">
                  <h2 className="text-xl font-semibold text-blue-600">Chuyển Hướng Không Có Quảng Cáo</h2>
                  <p className="text-sm">
                    Không giống như nhiều dịch vụ rút gọn link khác, chúng tôi không chèn quảng cáo vào quá trình chuyển hướng. Điều này giúp người dùng có trải nghiệm mượt mà, không bị làm phiền bởi các nội dung quảng cáo không mong muốn. Bạn có thể yên tâm rằng, mỗi liên kết rút gọn đều đảm bảo sự liền mạch và chuyên nghiệp.
                  </p>
                </div>
                <p className="text-sm">
                  Hãy trải nghiệm ngay dịch vụ rút gọn link của chúng tôi để cảm nhận sự khác biệt. Với tốc độ nhanh chóng, khả năng lưu trữ dài hạn và chuyển hướng không quảng cáo, chúng tôi tự tin sẽ là lựa chọn hoàn hảo cho mọi nhu cầu chia sẻ link của bạn.
                </p>          
              </div>  
            </div>
            
          </div>              
        </div>
      </div>
    </div>
  )
}
