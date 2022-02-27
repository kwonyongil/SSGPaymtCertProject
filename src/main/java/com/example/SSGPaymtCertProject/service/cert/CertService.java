package com.example.SSGPaymtCertProject.service.cert;

import com.example.SSGPaymtCertProject.domain.PaymtCertDemnd;
import com.example.SSGPaymtCertProject.domain.dto.CertDataResDto;
import com.example.SSGPaymtCertProject.domain.dto.CertReqDto;
import com.example.SSGPaymtCertProject.domain.dto.CertResDto;
import com.example.SSGPaymtCertProject.repository.CertRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @since 2021. 02. 25
 * @author kwon-yong-il
 */
@Service
@RequiredArgsConstructor
public class CertService {

    private final CertRepository certRepository;

    private final ModelMapper modelMapper;

    @Transactional
    public CertResDto insertPaymtCertDemnd(CertReqDto certReqDto) {

        certReqDto.setModpeId("관리자");
        certReqDto.setRegpeId("관리자");
        /**
         *  Entity 와 view layer 에서 사용될 dto 가 구분된다.
         *  따라서 Entity 의 값을 view layer 에 전달할 경우 새로 변환해주는 작업을 해야한다.
         *  일반적으로 getter/setter 또는 Builder 패턴을 통하여 해당 작업을 할 경우
         *  필드가 많을 수록 코드가 길어지고 반복적인 작업량이 늘어난다.
         * object 간 변환을 간단하게 줄이고 싶을 때 ModelMapper
         * TypeMap<S, D>
         * TypeMap 인터페이스를 구현함으로써 매핑 설정을 캡슐화(Encapsulating) 하여
         * ModelMapper 객체의 매핑 관계를 설정해 줄 수 있다.
         * PropertyMap 방법도 있다.
         */
        modelMapper.typeMap(CertReqDto.class, PaymtCertDemnd.class).addMappings(mapper -> mapper.map(CertReqDto::getCrdcoCd, PaymtCertDemnd::setFnccoCd));

//        PropertyMap<CertReqDto, PaymtCertDemnd> certMap = new PropertyMap<CertReqDto, PaymtCertDemnd>() {
//            protected void configure() {
//                map().setFnccoCd(source.getCrdcoCd());
//            }
//        };
//        modelMapper.addMappings(certMap);

        PaymtCertDemnd paymtCertDemnd = modelMapper.map(certReqDto, PaymtCertDemnd.class);

        PaymtCertDemnd newPaymtCertDemnd = certRepository.save(paymtCertDemnd);
        Long certKey = newPaymtCertDemnd.getPgPaymtCertId();

        CertResDto certResDto = new CertResDto();

        CertDataResDto certDataResDto = modelMapper.map(newPaymtCertDemnd, CertDataResDto.class);
        certDataResDto.setCertPageUrl("http://127.0.0.1/cert/gate/" + certKey);

        certResDto.setData(certDataResDto);
        certResDto.setCode("0000");
        certResDto.setMessage("Success");
        certResDto.setSuccess(true);

        return certResDto;
    }
}
