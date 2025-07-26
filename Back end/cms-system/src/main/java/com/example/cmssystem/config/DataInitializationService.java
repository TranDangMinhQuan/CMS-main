package com.example.cmssystem.config;

import com.example.cmssystem.entity.court.Court;
import com.example.cmssystem.enums.Status;
import com.example.cmssystem.repository.court.CourtRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataInitializationService implements ApplicationRunner {
    
    private final CourtRepository courtRepository;
    
    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (courtRepository.count() == 0) {
            log.info("Initializing sample court data...");
            initializeSampleCourts();
            log.info("Sample court data initialized successfully!");
        }
    }
    
    private void initializeSampleCourts() {
        List<Court> sampleCourts = List.of(
            createCourt(
                "Sân A - Quận 1",
                "Sân cầu lông chất lượng cao với hệ thống điều hòa hiện đại",
                "123 Nguyễn Huệ",
                "Quận 1",
                "TP.HCM",
                new BigDecimal("120000"),
                "https://cdn.tuoitre.vn/thumb_w/730/471584752817336320/2023/7/6/san-cau-long-16886087164821647664781-1688608954969820211575.jpg",
                new BigDecimal("4.8"),
                50,
                "0901234567",
                "Điều hòa,Đèn LED,Nước uống miễn phí,Thay đồ,Wifi miễn phí",
                "06:00",
                "23:00"
            ),
            createCourt(
                "Sân B - Quận 5",
                "Sân cầu lông với không gian rộng rãi, thoáng mát",
                "456 Trần Hưng Đạo",
                "Quận 5",
                "TP.HCM",
                new BigDecimal("100000"),
                "https://statics.vinpearl.com/san-cau-long-ha-noi-1_1682561731.jpg",
                new BigDecimal("4.6"),
                32,
                "0907654321",
                "Quạt trần,Đèn halogen,Nước uống,Giữ xe miễn phí",
                "07:00",
                "22:00"
            ),
            createCourt(
                "Sân C - Bình Thạnh",
                "Sân cầu lông cao cấp với thiết bị hiện đại nhất",
                "789 Xô Viết Nghệ Tĩnh",
                "Bình Thạnh",
                "TP.HCM",
                new BigDecimal("150000"),
                "https://top10tphcm.com/wp-content/uploads/2020/09/San-cau-long-Binh-Quoi.jpg",
                new BigDecimal("4.9"),
                75,
                "0908765432",
                "Điều hòa VIP,Đèn LED chuyên nghiệp,Nước uống cao cấp,Thay đồ VIP,Wifi tốc độ cao,Bãi đậu xe",
                "06:00",
                "24:00"
            ),
            createCourt(
                "Sân D - Quận 7",
                "Sân cầu lông hiện đại tại khu Nam Sài Gòn",
                "321 Nguyễn Thị Thập",
                "Quận 7",
                "TP.HCM",
                new BigDecimal("110000"),
                "https://sancaulong24h.com/wp-content/uploads/2021/05/san-cau-long-quan-7-1.jpg",
                new BigDecimal("4.7"),
                28,
                "0909876543",
                "Điều hòa,Đèn LED,Nước uống,Thay đồ,Căn tin",
                "06:30",
                "22:30"
            ),
            createCourt(
                "Sân E - Quận 10",
                "Sân cầu lông giá rẻ chất lượng tốt",
                "567 3 Tháng 2",
                "Quận 10",
                "TP.HCM",
                new BigDecimal("80000"),
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQyWX8Zo8kVxM8qrXGqE8QCYz9wOQUOGzE5Rw&s",
                new BigDecimal("4.3"),
                18,
                "0906543210",
                "Quạt trần,Đèn huỳnh quang,Nước lọc,Giữ xe",
                "07:00",
                "21:00"
            )
        );
        
        courtRepository.saveAll(sampleCourts);
    }
    
    private Court createCourt(String name, String description, String address, String district, 
                             String city, BigDecimal pricePerHour, String imageUrl, 
                             BigDecimal rating, Integer totalReviews, String phone, 
                             String facilities, String openingTime, String closingTime) {
        Court court = new Court();
        court.setName(name);
        court.setDescription(description);
        court.setAddress(address);
        court.setDistrict(district);
        court.setCity(city);
        court.setPricePerHour(pricePerHour);
        court.setImageUrl(imageUrl);
        court.setRating(rating);
        court.setTotalReviews(totalReviews);
        court.setPhone(phone);
        court.setFacilities(facilities);
        court.setOpeningTime(openingTime);
        court.setClosingTime(closingTime);
        court.setIsActive(true);
        court.setStatus(Status.ACTIVE);
        return court;
    }
}