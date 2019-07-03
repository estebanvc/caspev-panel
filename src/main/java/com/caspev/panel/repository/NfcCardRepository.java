package com.caspev.panel.repository;

import com.caspev.panel.domain.NfcCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the NfcCard entity.
 */
@Repository
public interface NfcCardRepository extends JpaRepository<NfcCard, Long> {

    List<NfcCard> findAllByUser_Id(Long id);

    Optional<NfcCard> findByUuid(String uuid);

    Optional<NfcCard> findByCode(String code);

    void deleteByUuid(String uuid);
}
