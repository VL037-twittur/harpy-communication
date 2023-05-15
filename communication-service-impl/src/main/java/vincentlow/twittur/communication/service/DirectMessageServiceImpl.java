package vincentlow.twittur.communication.service;

import static vincentlow.twittur.communication.service.helper.ValidatorHelper.validateAccount;
import static vincentlow.twittur.communication.util.ValidatorUtil.validateArgument;
import static vincentlow.twittur.communication.util.ValidatorUtil.validateState;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import vincentlow.twittur.base.web.model.response.api.ApiSingleResponse;
import vincentlow.twittur.communication.client.AccountCredentialFeignClient;
import vincentlow.twittur.communication.client.model.response.AccountCredentialResponse;
import vincentlow.twittur.communication.model.constant.DirectMessageStatus;
import vincentlow.twittur.communication.model.constant.ErrorCode;
import vincentlow.twittur.communication.model.constant.ExceptionMessage;
import vincentlow.twittur.communication.model.entity.BaseEntity;
import vincentlow.twittur.communication.model.entity.DirectMessage;
import vincentlow.twittur.communication.repository.DirectMessageRepository;
import vincentlow.twittur.communication.web.model.request.DirectMessageRequest;

@Service
public class DirectMessageServiceImpl implements DirectMessageService {

  @Autowired
  private AccountCredentialFeignClient accountCredentialFeignClient;

  @Autowired
  private DirectMessageRepository directMessageRepository;

  @Override
  public DirectMessage sendMessage(String senderId, String recipientId, DirectMessageRequest request) {

    validateState(Objects.nonNull(request), ErrorCode.REQUEST_MUST_NOT_BE_NULL.getMessage());
    validateState(Objects.nonNull(request.getMessage()), ErrorCode.MESSAGE_MUST_NOT_BE_NULL.getMessage());
    validateArgument(StringUtils.isNotBlank(request.getMessage()), ErrorCode.MESSAGE_MUST_NOT_BE_BLANK.getMessage());

    ResponseEntity<ApiSingleResponse<AccountCredentialResponse>> senderResponse =
        accountCredentialFeignClient.getAccountCredentialById(senderId);

    AccountCredentialResponse accountResponse = senderResponse.getBody()
        .getData();
    validateAccount(accountResponse, ExceptionMessage.SENDER_ACCOUNT_NOT_FOUND);

    ResponseEntity<ApiSingleResponse<AccountCredentialResponse>> recipientResponse =
        accountCredentialFeignClient.getAccountCredentialById(recipientId);

    accountResponse = recipientResponse.getBody()
        .getData();
    validateAccount(accountResponse, ExceptionMessage.RECIPIENT_ACCOUNT_NOT_FOUND);

    DirectMessage directMessage = new DirectMessage();
    directMessage.setSenderId(senderId);
    directMessage.setRecipientId(recipientId);
    directMessage.setMessage(request.getMessage());
    directMessage.setStatus(DirectMessageStatus.DELIVERED);

    LocalDateTime now = LocalDateTime.now();
    directMessage.setCreatedBy(senderId);
    directMessage.setCreatedDate(now);
    directMessage.setUpdatedBy(senderId);
    directMessage.setUpdatedDate(now);

    return directMessageRepository.save(directMessage);
  }

  @Override
  public Page<DirectMessage> getDirectMessages(String senderId, String recipientId, int pageNumber, int pageSize) {

    ResponseEntity<ApiSingleResponse<AccountCredentialResponse>> senderResponse =
        accountCredentialFeignClient.getAccountCredentialById(senderId);

    AccountCredentialResponse accountResponse = senderResponse.getBody()
        .getData();
    validateAccount(accountResponse, ExceptionMessage.SENDER_ACCOUNT_NOT_FOUND);

    ResponseEntity<ApiSingleResponse<AccountCredentialResponse>> recipientResponse =
        accountCredentialFeignClient.getAccountCredentialById(recipientId);

    accountResponse = recipientResponse.getBody()
        .getData();
    validateAccount(accountResponse, ExceptionMessage.RECIPIENT_ACCOUNT_NOT_FOUND);

    Pageable pageable = PageRequest.of(pageNumber, pageSize);

    Page<DirectMessage> senderToRecipientMessages =
        directMessageRepository.findAllBySenderIdAndRecipientIdOrderByCreatedDateDesc(senderId, recipientId, pageable);
    Page<DirectMessage> recipientToSenderMessages =
        directMessageRepository.findAllBySenderIdAndRecipientIdOrderByCreatedDateDesc(recipientId, senderId, pageable);

    List<DirectMessage> allMessages = new ArrayList<>();
    allMessages.addAll(senderToRecipientMessages.getContent());
    allMessages.addAll(recipientToSenderMessages.getContent());

    /**
     * oldest to newest. UI can consume [for 0 to N] and attach to the beginning of the array when doing Infinite
     * Scrolling (scroll UP)
     */
    allMessages.sort(Comparator.comparing(BaseEntity::getCreatedDate));
    return new PageImpl<>(allMessages, pageable, allMessages.size());
  }
}
