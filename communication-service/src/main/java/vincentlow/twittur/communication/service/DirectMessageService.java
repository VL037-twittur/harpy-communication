package vincentlow.twittur.communication.service;

import org.springframework.data.domain.Page;

import vincentlow.twittur.communication.model.entity.DirectMessage;
import vincentlow.twittur.communication.web.model.request.DirectMessageRequest;

public interface DirectMessageService {

  DirectMessage sendMessage(String senderId, String recipientId, DirectMessageRequest request);

  Page<DirectMessage> getDirectMessages(String senderId, String recipientId, int pageNumber, int pageSize);
}
