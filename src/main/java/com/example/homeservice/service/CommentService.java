package com.example.homeservice.service;

import com.example.homeservice.exception.InformationDuplicateException;
import com.example.homeservice.exception.NotFoundException;
import com.example.homeservice.model.Customer;
import com.example.homeservice.model.Comment;
import com.example.homeservice.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public Comment save(Comment comment) {
        if (commentRepository.findByOrder(comment.getOrder()).isPresent())
            throw new InformationDuplicateException(
                    comment.getId() + " is duplicate"
            );
        return commentRepository.save(comment);
    }

    public void removeById(Long id) {
        Comment review = commentRepository.findById(id).orElseThrow(
                () -> new NotFoundException("comment with id " + id + " not found")
        );
        commentRepository.delete(review);
    }

    public Comment update(Comment comment, Long id) {
        Comment foundedComment = commentRepository.findById(id).orElseThrow(
                () -> new NotFoundException("comment with id " + id + " not found")
        );
        Optional.ofNullable(comment.getCustomer()).ifPresent(foundedComment::setCustomer);
        Optional.ofNullable(comment.getCommentDate()).ifPresent(foundedComment::setCommentDate);
        Optional.ofNullable(comment.getOrder()).ifPresent(foundedComment::setOrder);
        Optional.ofNullable(comment.getComment()).ifPresent(foundedComment::setComment);
        Optional.ofNullable(comment.getSpecialist()).ifPresent(foundedComment::setSpecialist);
        Optional.ofNullable(comment.getRating()).ifPresent(foundedComment::setRating);
        return commentRepository.save(foundedComment);
    }

    @Transactional
    public void updateReviewByComment(Long id, String newComment) {
        if (commentRepository.findById(id).isPresent()) {
            commentRepository.updateReviewByComment(newComment, id);
        } else {
            throw new NotFoundException("comment with id " + id + " not found");
        }
    }

    public List<Comment> findByCustomer(Customer customer) {
        return commentRepository.findByCustomer(customer);
    }

    public List<Comment> findAllByCustomer(Customer customer){
        return commentRepository.findAllByCustomer(customer);
    }
    public void deleteComment(Comment comment){
         commentRepository.delete(comment);
    }
}


