package com.aditya.microservices.cards.service;

import com.aditya.microservices.cards.dto.CardDto;

public interface ICardService {
    public void createCard(String mobileNumber);

    public CardDto getCardUsingMobileNumber(String mobileNumber);

    public boolean updateCard(CardDto cardDto);

    public boolean deleteCardUsingMobileNumber(String mobileNumber);
}
