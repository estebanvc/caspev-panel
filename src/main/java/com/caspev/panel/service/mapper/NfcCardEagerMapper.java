package com.caspev.panel.service.mapper;

import com.caspev.panel.domain.NfcCard;
import com.caspev.panel.service.dto.NfcCardEagerDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity NfcCard and its DTO NfcCardEagerDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface NfcCardEagerMapper extends EntityMapper<NfcCardEagerDTO, NfcCard> {

    default NfcCard fromId(Long id) {
        if (id == null) {
            return null;
        }
        NfcCard nfcCard = new NfcCard();
        nfcCard.setId(id);
        return nfcCard;
    }
}
