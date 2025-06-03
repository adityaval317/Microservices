package com.aditya.microservices.loans.service.impl;

import com.aditya.microservices.loans.dto.CardDto;
import com.aditya.microservices.loans.entities.Card;
import com.aditya.microservices.loans.exception.ResourceNotFoundException;
import com.aditya.microservices.loans.mapper.CardMapper;
import com.aditya.microservices.loans.repositories.CardRepository;
import com.aditya.microservices.loans.service.ICardService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements ICardService {

    private CardRepository cardRepository;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public void createCard(String mobileNumber) {
        Card card = addNewCard(mobileNumber);
        cardRepository.save(card);
    }

    @Override
    public CardDto getCardUsingMobileNumber(String mobileNumber) {
        Card card = cardRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "mobileNumber", String.valueOf(mobileNumber)));
        CardDto cardDto = CardMapper.toCardDto(card, new CardDto());
        return cardDto;
    }

    private Card addNewCard(String mobileNumber) {
        Card card = new Card();
        card.setMobileNumber(mobileNumber);
        card.setCardType("Visa");
        card.setTotalLimit(10000);
        card.setAmountUsed(0);
        card.setAvailableAmount(10000);
        long cardNumber = 4000000000000000L + new Random().nextInt(90000000);
        card.setCardNumber(String.valueOf(cardNumber));

        return card;
    }

    @Override
    public boolean updateCard(CardDto cardDto) {
        boolean updated = false;
        Card existingCard = cardRepository.findByMobileNumber(cardDto.getMobileNumber()).orElseThrow(()->
                new ResourceNotFoundException("Card", "mobileNumber", String.valueOf(cardDto.getMobileNumber())));

        if (existingCard != null) {
            cardDto.setAvailableAmount(cardDto.getTotalLimit()-cardDto.getAmountUsed());
            Card updatedCard = CardMapper.toCard(cardDto, existingCard);
            cardRepository.save(updatedCard);
            updated = true;
        }
        return updated;
    }


    @Override
    public boolean deleteCardUsingMobileNumber(String mobileNumber) {
        boolean deleted = false;
        Card card = cardRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Card", "mobileNumber", String.valueOf(mobileNumber)));
        if(card!=null){
            cardRepository.delete(card);
            deleted = true;
        }
        return deleted;
    }

}
