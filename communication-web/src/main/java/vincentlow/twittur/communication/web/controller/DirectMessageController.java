package vincentlow.twittur.communication.web.controller;

import static vincentlow.twittur.communication.util.ObjectMappingHelper.toResponse;
import static vincentlow.twittur.communication.util.ValidatorUtil.validatePageableRequest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import vincentlow.twittur.base.web.controller.BaseController;
import vincentlow.twittur.base.web.model.response.PageMetaData;
import vincentlow.twittur.base.web.model.response.api.ApiListResponse;
import vincentlow.twittur.base.web.model.response.api.ApiSingleResponse;
import vincentlow.twittur.communication.model.constant.ApiPath;
import vincentlow.twittur.communication.model.entity.DirectMessage;
import vincentlow.twittur.communication.service.DirectMessageService;
import vincentlow.twittur.communication.web.model.request.DirectMessageRequest;
import vincentlow.twittur.communication.web.model.response.DirectMessageResponse;

@Slf4j
@RestController
@RequestMapping(value = ApiPath.DIRECT_MESSAGE, produces = MediaType.APPLICATION_JSON_VALUE)
public class DirectMessageController extends BaseController {

  private final int DEFAULT_PAGE_SIZE = 2; // will return DEFAULT_PAGE_SIZE*2 messages

  @Autowired
  private DirectMessageService directMessageService;

  @PostMapping("/{senderId}/{recipientId}")
  public ResponseEntity<ApiSingleResponse<DirectMessageResponse>> sendDirectMessage(@PathVariable String senderId,
      @PathVariable String recipientId, @RequestBody DirectMessageRequest request) {

    try {
      DirectMessage directMessage = directMessageService.sendMessage(senderId, recipientId, request);
      DirectMessageResponse response = toResponse(directMessage, DirectMessageResponse.class);

      return toSuccessResponseEntity(toApiSingleResponse(response));
    } catch (Exception e) {
      log.error(
          "#DirectMessageController#sendDirectMessage ERROR! with senderId: {}, recipientId: {}, request: {}, and error: {}",
          senderId, recipientId, request, e.getMessage(), e);
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  @GetMapping("/{senderId}/{recipientId}")
  public ResponseEntity<ApiListResponse<DirectMessageResponse>> getDirectMessages(@PathVariable String senderId,
      @PathVariable String recipientId,
      @RequestParam(defaultValue = "0") int pageNumber) {

    try {
      validatePageableRequest(pageNumber, DEFAULT_PAGE_SIZE);

      Page<DirectMessage> directMessages =
          directMessageService.getDirectMessages(senderId, recipientId, pageNumber, DEFAULT_PAGE_SIZE);
      List<DirectMessageResponse> response = directMessages.stream()
          .map(directMessage -> toResponse(directMessage, DirectMessageResponse.class))
          .collect(Collectors.toList());
      PageMetaData pageMetaData = getPageMetaData(directMessages, pageNumber, DEFAULT_PAGE_SIZE);

      return toSuccessResponseEntity(toApiListResponse(response, pageMetaData));
    } catch (Exception e) {
      log.error(
          "#DirectMessageController#getDirectMessages ERROR! with senderId: {}, recipientId: {}, pageNumber: {}, and error: {}",
          senderId, recipientId, pageNumber, e.getMessage(), e);
      throw new RuntimeException(e.getMessage(), e);
    }
  }
}
