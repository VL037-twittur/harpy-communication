package vincentlow.twittur.communication.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vincentlow.twittur.communication.model.entity.DirectMessage;

@Repository
public interface DirectMessageRepository extends JpaRepository<DirectMessage, String> {

  Page<DirectMessage> findAllBySenderIdAndRecipientIdOrderByCreatedDateDesc(String senderId, String recipientId,
      Pageable pageable);
}
