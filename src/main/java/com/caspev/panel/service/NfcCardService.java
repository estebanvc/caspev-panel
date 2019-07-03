package com.caspev.panel.service;

import com.caspev.panel.domain.NfcCard;
import com.caspev.panel.repository.NfcCardRepository;
import com.caspev.panel.service.dto.NfcCardDTO;
import com.caspev.panel.service.mapper.NfcCardMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing NfcCard.
 */
@Service
@Transactional
public class NfcCardService {

    private final Logger log = LoggerFactory.getLogger(NfcCardService.class);
    private final NfcCardMapper nfcCardMapper;
    private final NfcCardRepository nfcCardRepository;

    public NfcCardService(NfcCardRepository nfcCardRepository, NfcCardMapper nfcCardMapper) {
        this.nfcCardRepository = nfcCardRepository;
        this.nfcCardMapper     = nfcCardMapper;
    }

    /**
     * Save a nfcCard.
     *
     * @param nfcCardDTO the entity to save
     * @return the persisted entity
     */
    public NfcCardDTO save(NfcCardDTO nfcCardDTO) {
        log.debug("Request to save NfcCard : {}", nfcCardDTO);
        NfcCard nfcCard = nfcCardMapper.toEntity(nfcCardDTO);
        nfcCard = nfcCardRepository.save(nfcCard);
        return nfcCardMapper.toDto(nfcCard);
    }

    /**
     * Get all the nfcCards.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<NfcCardDTO> findAll() {
        log.debug("Request to get all NfcCards");
        return nfcCardRepository.findAll(Sort.by("id").descending())
                .stream()
                .map(nfcCardMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one nfcCard by uuid.
     *
     * @param uuid the uuid of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<NfcCardDTO> findOneByUuid(String uuid) {
        log.debug("Request to get NfcCard : {}", uuid);
        return nfcCardRepository.findByUuid(uuid)
                .map(nfcCardMapper::toDto);
    }

    /**
     * Get one nfcCard by code.
     *
     * @param code the code of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<NfcCardDTO> findOneByCode(String code) {
        log.debug("Request to get NfcCard : {}", code);
        return nfcCardRepository.findByCode(code)
                .map(nfcCardMapper::toDto);
    }


    /**
     * Delete the nfcCard by uuid.
     *
     * @param uuid the uuid of the entity
     */
    public void delete(String uuid) {
        log.debug("Request to delete NfcCard : {}", uuid);
        nfcCardRepository.deleteByUuid(uuid);
    }
}
