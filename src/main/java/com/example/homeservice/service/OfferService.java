package com.example.homeservice.service;

import com.example.homeservice.exception.InformationDuplicateException;
import com.example.homeservice.exception.NotFoundException;
import com.example.homeservice.model.Offer;
import com.example.homeservice.model.Specialist;
import com.example.homeservice.repository.OfferRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;

    public Offer save(Offer offer) {
        if (offerRepository.findByOrder(offer.getOrder()).isPresent())
            throw new InformationDuplicateException(
                    offer.getId() + " is duplicate"
            );
        return offerRepository.save(offer);
    }

    public void removeById(Integer id) {
        Offer offer = offerRepository.findById(Long.valueOf(id)).orElseThrow(
                () -> new NotFoundException("offer with id " + id + " not found")
        );
        offerRepository.delete(offer);
    }

    public Offer update(Offer offer, Specialist specialist) {
        Optional<Offer> optionalOffer = offerRepository.findBySpecialist(specialist);
        if (optionalOffer.isPresent()) {
            Offer foundedOffer = optionalOffer.get();
            Optional.ofNullable(offer.getProposedPrice()).ifPresent(foundedOffer::setProposedPrice);
            Optional.ofNullable(offer.getDuration()).ifPresent(foundedOffer::setDuration);
            Optional.ofNullable(offer.getOrder()).ifPresent(foundedOffer::setOrder);
            Optional.ofNullable(offer.getProposalDate()).ifPresent(foundedOffer::setProposalDate);
            return offerRepository.save(foundedOffer);
        } else {
            throw new NotFoundException("Offer not found for specialist " + specialist);
        }
    }


    @Transactional
    public void updateProposalByProposedPrice(int id, double newProposedPrice) {
        if (offerRepository.findById((long) id).isPresent()) {
            offerRepository.updateProposalByProposedPrice(newProposedPrice, id);
        } else {
            throw new NotFoundException("offer with id " + id + " not found");
        }
    }

    public List<Offer> findByOrderId(Long orderId) {
        return offerRepository.findByOrderId(orderId);
    }

    public Optional<Offer> findBySpecialist(Specialist specialist){
        return offerRepository.findBySpecialist(specialist);
    }
    public void deleteOffer(Offer offer){
        offerRepository.delete(offer);
    }
}
