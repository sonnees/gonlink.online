import { faCalendar } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import React from 'react'

export default function ContactPage() {
  return (
    <div>
      <div className="hidden md:block">
        <div className='w-full'>
          <div className="flex-col justify-center items-center">
            <div className='flex justify-center p-16 bg-blue-100'>
              <h1 className="text-5xl font-bold">Điều khoản & Điều kiện</h1>
            </div>

            <div className='bg-gray-100 relative shadow-2xl py-20'>
              <div className="absolute top-0 left-1/2 transform -translate-x-1/2 -translate-y-1/2 bg-white px-60 py-4 rounded-2xl shadow-2xl flex items-center space-x-2">
                <FontAwesomeIcon icon={faCalendar} />
                <span className="text-gray-600">Last Updated October 22, 2024</span>
              </div>

              <div className='max-w-5xl mx-auto p-6 bg-gray-100'>
                <div className="my-2 py-2">
                  <h2 className="text-xl font-semibold text-blue-600">1. Giới thiệu</h2>
                  <p className="text-base">
                    Chào mừng bạn đến với dịch vụ rút gọn link của chúng tôi. Khi sử dụng dịch vụ này, bạn đồng ý với các điều khoản và điều kiện dưới đây.
                    Nếu bạn không đồng ý với bất kỳ phần nào của các điều khoản này, xin vui lòng không sử dụng dịch vụ của chúng tôi.
                  </p>
                </div>

                <div className="my-2 py-2">
                  <h2 className="text-xl font-semibold text-blue-600">2. Dịch vụ</h2>
                  <p className="text-base">
                    Dịch vụ rút gọn link của chúng tôi cho phép bạn tạo ra các liên kết ngắn gọn hơn từ các URL gốc.
                    Chúng tôi không chịu trách nhiệm về nội dung của các URL gốc mà người dùng nhập vào hệ thống của chúng tôi.
                  </p>
                </div>

                <div className="my-2 py-2">
                  <h2 className="text-xl font-semibold text-blue-600">3. Trách nhiệm của người dùng</h2>
                  <p className="text-base">
                    Khi sử dụng dịch vụ của chúng tôi, bạn đồng ý rằng bạn sẽ không:
                    <br />
                    - Tái bản tài liệu, bán, cho thuê hoặc cấp phép lại tài liệu từ gonlink.online.
                    <br />
                    - Sao chép, phân phối lại nội dung tài liệu từ gonlink.online mà không được sự đồng ý của đội ngũ quản trị.
                    <br />
                    - Tạo link spam, gây ảnh hưởng đến một phần hoặc toàn bộ tài nguyên của gonlink.online.
                    <br />
                    - Rút gọn link tới các trang đích có nội dung giả mạo, cá độ, đánh bạc, lừa đảo, tín dụng đen, đồi trụy, vi phạm hiến pháp và pháp luật nước CHXHCN Việt Nam.
                    <br />
                    - Rút gọn link tới các dịch vụ rút gọn link khác.
                    <br />
                    - Rút gọn link tới các website đang sử dụng dịch vụ bọc link.
                    <br />
                    - Sử dụng website của chúng tôi vào mục đích thương mại mà không có sự cho phép bằng văn bản của chúng tôi trước đó.
                    <br />
                    - Xâm phạm, tiếp cận hay sử dụng bất kỳ phần nào trong máy chủ của chúng tôi. Nghiêm cấm mọi hành vi lợi dụng lỗi hệ thống để trục lợi cá nhân gây thiệt hại đến nhà cung cấp dịch vụ.
                    <br />
                    - Các hành động nhằm hạn chế hoặc cấm đoán bất kỳ người dùng nào khác sử dụng và đọc thông tin trên website của chúng tôi.
                    <br />
                    - Sử dụng dịch vụ cho bất kỳ mục đích bất hợp pháp nào.
                    <br />
                    - Tạo ra các liên kết rút gọn dẫn đến nội dung vi phạm quyền sở hữu trí tuệ, nội dung khiêu dâm, bạo lực, hoặc bất kỳ nội dung không phù hợp nào khác.
                    <br />
                    - Sử dụng dịch vụ để phát tán spam hoặc bất kỳ hình thức quảng cáo không được yêu cầu nào khác.
                    <br />
                    - Sử dụng các biện pháp kỹ thuật nhằm gian lận hoặc phá hoại hệ thống của chúng tôi.
                  </p>
                </div>

                <div className="my-2 py-2">
                  <h2 className="text-xl font-semibold text-blue-600">4. Đảm bảo và Tuyên bố</h2>
                  <p className="text-base">
                    Bạn đảm bảo và tuyên bố rằng:
                    <br />
                    - Bạn có quyền rút gọn link tới trang đích trên trang web của chúng tôi và có tất cả các giấy phép và sự đồng ý cần thiết để làm như vậy;
                    <br />
                    - Các nội dung trang đích không xâm phạm bất kỳ quyền sở hữu trí tuệ nào, bao gồm nhưng không giới hạn bản quyền, bằng sáng chế hoặc nhãn hiệu của bất kỳ bên thứ ba nào;
                    <br />
                    - Nội dung trang đích không chứa bất kỳ tài liệu phỉ báng, bôi nhọ, xúc phạm, khiếm nhã hoặc bất hợp pháp nào khác là xâm phạm quyền riêng tư.
                  </p>
                </div>

                <div className="my-2 py-2">
                  <h2 className="text-xl font-semibold text-blue-600">5. Quyền hạn của chúng tôi</h2>
                  <p className="text-base">
                    Chúng tôi có quyền:
                    <br />
                    - Tạm ngừng hoặc chấm dứt dịch vụ của bạn nếu phát hiện bất kỳ hành vi vi phạm nào.
                    <br />
                    - Xóa bỏ bất kỳ liên kết rút gọn nào mà chúng tôi cho rằng vi phạm các điều khoản sử dụng.
                    <br />
                    - Thay đổi hoặc cập nhật các điều khoản sử dụng này bất kỳ lúc nào mà không cần thông báo trước.
                  </p>
                </div>

                <div className="my-2 py-2">
                  <h2 className="text-xl font-semibold text-blue-600">6. Miễn trừ trách nhiệm</h2>
                  <p className="text-base">
                    Dịch vụ của chúng tôi được cung cấp "như hiện có" và "như sẵn có". 
                    <br />
                    Chúng tôi không cam kết rằng dịch vụ sẽ không có lỗi hoặc gián đoạn. 
                    <br />
                    Chúng tôi không chịu trách nhiệm về bất kỳ thiệt hại nào phát sinh từ việc sử dụng dịch vụ của chúng tôi, bao gồm nhưng không giới hạn ở thiệt hại trực tiếp, gián tiếp, ngẫu nhiên, hoặc hậu quả.
                  </p>
                </div>

                <div className="my-2 py-2">
                  <h2 className="text-xl font-semibold text-blue-600">7. Bồi thường</h2>
                  <p className="text-base">
                    Bạn đồng ý bồi thường và giữ cho chúng tôi không bị thiệt hại từ bất kỳ khiếu nại, tổn thất, chi phí, hoặc yêu cầu nào (bao gồm phí luật sư) phát sinh từ việc bạn vi phạm các điều khoản này.
                  </p>
                </div>

                <div className="my-2 py-2">
                  <h2 className="text-xl font-semibold text-blue-600">8. Điều khoản chung</h2>
                  <p className="text-base">
                    Những điều khoản này được điều chỉnh bởi luật pháp hiện hành của quốc gia hoặc khu vực nơi công ty chúng tôi đăng ký hoạt động.
                    <br />
                    Nếu bất kỳ điều khoản nào bị coi là không hợp lệ hoặc không thể thực thi, điều khoản đó sẽ được xem là tách rời khỏi các điều khoản còn lại và không ảnh hưởng đến hiệu lực và tính khả thi của các điều khoản còn lại.
                  </p>
                </div>

                <div className="my-2 py-2">
                  <h2 className="text-xl font-semibold text-blue-600">9. Liên hệ</h2>
                  <p className="text-base">
                    Nếu bạn có bất kỳ câu hỏi hoặc thắc mắc nào về các điều khoản này, xin vui lòng liên hệ với chúng tôi qua email: gonlink.online@gmail.com.
                  </p>
                </div>

                <div className="my-2 py-2">
                  <h2 className="text-xl font-semibold text-blue-600">10. Chấp nhận các điều khoản</h2>
                  <p className="text-base">
                    Bằng việc sử dụng dịch vụ của chúng tôi, bạn đồng ý rằng bạn đã đọc, hiểu và chấp nhận tất cả các điều khoản và điều kiện này.
                  </p>
                </div>
          
              </div>  
            </div>
            
          </div>              
        </div>
      </div>

      <div className="block md:hidden">
        <div className='w-full'>
          <div className="flex-col justify-center items-center">
            <div className='flex justify-center px-4 py-16 bg-blue-100'>
              <h1 className="text-3xl font-bold">Điều khoản & Điều kiện</h1>
            </div>

            <div className='bg-gray-100 relative shadow-2xl py-10'>
              <div className="absolute top-0 left-1/2 transform -translate-x-1/2 -translate-y-1/2 bg-white px-4 py-2 rounded-2xl shadow-2xl w-[80%]">
                <FontAwesomeIcon icon={faCalendar}/>
                <span className="text-gray-600 text-sm pl-2">Last Updated October 22, 2024</span>
              </div>

              <div className='max-w-5xl mx-auto p-6 bg-gray-100'>
                <div className="my-2 py-2">
                  <h2 className="text-xl font-semibold text-blue-600">1. Giới thiệu</h2>
                  <p className="text-base">
                    Chào mừng bạn đến với dịch vụ rút gọn link của chúng tôi. Khi sử dụng dịch vụ này, bạn đồng ý với các điều khoản và điều kiện dưới đây.
                    Nếu bạn không đồng ý với bất kỳ phần nào của các điều khoản này, xin vui lòng không sử dụng dịch vụ của chúng tôi.
                  </p>
                </div>

                <div className="my-2 py-2">
                  <h2 className="text-xl font-semibold text-blue-600">2. Dịch vụ</h2>
                  <p className="text-base">
                    Dịch vụ rút gọn link của chúng tôi cho phép bạn tạo ra các liên kết ngắn gọn hơn từ các URL gốc.
                    Chúng tôi không chịu trách nhiệm về nội dung của các URL gốc mà người dùng nhập vào hệ thống của chúng tôi.
                  </p>
                </div>

                <div className="my-2 py-2">
                  <h2 className="text-xl font-semibold text-blue-600">3. Trách nhiệm của người dùng</h2>
                  <p className="text-base">
                    Khi sử dụng dịch vụ của chúng tôi, bạn đồng ý rằng bạn sẽ không:
                    <br />
                    - Tái bản tài liệu, bán, cho thuê hoặc cấp phép lại tài liệu từ gonlink.online.
                    <br />
                    - Sao chép, phân phối lại nội dung tài liệu từ gonlink.online mà không được sự đồng ý của đội ngũ quản trị.
                    <br />
                    - Tạo link spam, gây ảnh hưởng đến một phần hoặc toàn bộ tài nguyên của gonlink.online.
                    <br />
                    - Rút gọn link tới các trang đích có nội dung giả mạo, cá độ, đánh bạc, lừa đảo, tín dụng đen, đồi trụy, vi phạm hiến pháp và pháp luật nước CHXHCN Việt Nam.
                    <br />
                    - Rút gọn link tới các dịch vụ rút gọn link khác.
                    <br />
                    - Rút gọn link tới các website đang sử dụng dịch vụ bọc link.
                    <br />
                    - Sử dụng website của chúng tôi vào mục đích thương mại mà không có sự cho phép bằng văn bản của chúng tôi trước đó.
                    <br />
                    - Xâm phạm, tiếp cận hay sử dụng bất kỳ phần nào trong máy chủ của chúng tôi. Nghiêm cấm mọi hành vi lợi dụng lỗi hệ thống để trục lợi cá nhân gây thiệt hại đến nhà cung cấp dịch vụ.
                    <br />
                    - Các hành động nhằm hạn chế hoặc cấm đoán bất kỳ người dùng nào khác sử dụng và đọc thông tin trên website của chúng tôi.
                    <br />
                    - Sử dụng dịch vụ cho bất kỳ mục đích bất hợp pháp nào.
                    <br />
                    - Tạo ra các liên kết rút gọn dẫn đến nội dung vi phạm quyền sở hữu trí tuệ, nội dung khiêu dâm, bạo lực, hoặc bất kỳ nội dung không phù hợp nào khác.
                    <br />
                    - Sử dụng dịch vụ để phát tán spam hoặc bất kỳ hình thức quảng cáo không được yêu cầu nào khác.
                    <br />
                    - Sử dụng các biện pháp kỹ thuật nhằm gian lận hoặc phá hoại hệ thống của chúng tôi.
                  </p>
                </div>

                <div className="my-2 py-2">
                  <h2 className="text-xl font-semibold text-blue-600">4. Đảm bảo và Tuyên bố</h2>
                  <p className="text-base">
                    Bạn đảm bảo và tuyên bố rằng:
                    <br />
                    - Bạn có quyền rút gọn link tới trang đích trên trang web của chúng tôi và có tất cả các giấy phép và sự đồng ý cần thiết để làm như vậy;
                    <br />
                    - Các nội dung trang đích không xâm phạm bất kỳ quyền sở hữu trí tuệ nào, bao gồm nhưng không giới hạn bản quyền, bằng sáng chế hoặc nhãn hiệu của bất kỳ bên thứ ba nào;
                    <br />
                    - Nội dung trang đích không chứa bất kỳ tài liệu phỉ báng, bôi nhọ, xúc phạm, khiếm nhã hoặc bất hợp pháp nào khác là xâm phạm quyền riêng tư.
                  </p>
                </div>

                <div className="my-2 py-2">
                  <h2 className="text-xl font-semibold text-blue-600">5. Quyền hạn của chúng tôi</h2>
                  <p className="text-base">
                    Chúng tôi có quyền:
                    <br />
                    - Tạm ngừng hoặc chấm dứt dịch vụ của bạn nếu phát hiện bất kỳ hành vi vi phạm nào.
                    <br />
                    - Xóa bỏ bất kỳ liên kết rút gọn nào mà chúng tôi cho rằng vi phạm các điều khoản sử dụng.
                    <br />
                    - Thay đổi hoặc cập nhật các điều khoản sử dụng này bất kỳ lúc nào mà không cần thông báo trước.
                  </p>
                </div>

                <div className="my-2 py-2">
                  <h2 className="text-xl font-semibold text-blue-600">6. Miễn trừ trách nhiệm</h2>
                  <p className="text-base">
                    Dịch vụ của chúng tôi được cung cấp "như hiện có" và "như sẵn có". 
                    <br />
                    Chúng tôi không cam kết rằng dịch vụ sẽ không có lỗi hoặc gián đoạn. 
                    <br />
                    Chúng tôi không chịu trách nhiệm về bất kỳ thiệt hại nào phát sinh từ việc sử dụng dịch vụ của chúng tôi, bao gồm nhưng không giới hạn ở thiệt hại trực tiếp, gián tiếp, ngẫu nhiên, hoặc hậu quả.
                  </p>
                </div>

                <div className="my-2 py-2">
                  <h2 className="text-xl font-semibold text-blue-600">7. Bồi thường</h2>
                  <p className="text-base">
                    Bạn đồng ý bồi thường và giữ cho chúng tôi không bị thiệt hại từ bất kỳ khiếu nại, tổn thất, chi phí, hoặc yêu cầu nào (bao gồm phí luật sư) phát sinh từ việc bạn vi phạm các điều khoản này.
                  </p>
                </div>

                <div className="my-2 py-2">
                  <h2 className="text-xl font-semibold text-blue-600">8. Điều khoản chung</h2>
                  <p className="text-base">
                    Những điều khoản này được điều chỉnh bởi luật pháp hiện hành của quốc gia hoặc khu vực nơi công ty chúng tôi đăng ký hoạt động.
                    <br />
                    Nếu bất kỳ điều khoản nào bị coi là không hợp lệ hoặc không thể thực thi, điều khoản đó sẽ được xem là tách rời khỏi các điều khoản còn lại và không ảnh hưởng đến hiệu lực và tính khả thi của các điều khoản còn lại.
                  </p>
                </div>

                <div className="my-2 py-2">
                  <h2 className="text-xl font-semibold text-blue-600">9. Liên hệ</h2>
                  <p className="text-base">
                    Nếu bạn có bất kỳ câu hỏi hoặc thắc mắc nào về các điều khoản này, xin vui lòng liên hệ với chúng tôi qua email: gonlink.online@gmail.com .
                  </p>
                </div>

                <div className="my-2 py-2">
                  <h2 className="text-xl font-semibold text-blue-600">10. Chấp nhận các điều khoản</h2>
                  <p className="text-base">
                    Bằng việc sử dụng dịch vụ của chúng tôi, bạn đồng ý rằng bạn đã đọc, hiểu và chấp nhận tất cả các điều khoản và điều kiện này.
                  </p>
                </div>
          
              </div>  
            </div>
            
          </div>              
        </div>
      </div>
    </div>
  )
}
