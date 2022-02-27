package com.example.SSGPaymtCertProject.repository;

import com.example.SSGPaymtCertProject.domain.PaymtCertDemnd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertRepository extends JpaRepository<PaymtCertDemnd, Long> {

}
