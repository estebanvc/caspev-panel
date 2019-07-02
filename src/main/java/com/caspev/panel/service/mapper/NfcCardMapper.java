package com.caspev.panel.service.mapper;

import com.caspev.panel.domain.NfcCard;
import com.caspev.panel.service.dto.NfcCardDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity NfcCard and its DTO NfcCardDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface NfcCardMapper extends EntityMapper<NfcCardDTO, NfcCard> {

    @Mapping(source = "user.id", target = "userId")
    NfcCardDTO toDto(NfcCard nfcCard);

    @Mapping(source = "userId", target = "user")
    NfcCard toEntity(NfcCardDTO nfcCardDTO);

    default NfcCard fromId(Long id) {
        if (id == null) {
            return null;
        }
        NfcCard nfcCard = new NfcCard();
        nfcCard.setId(id);
        return nfcCard;
    }
}
