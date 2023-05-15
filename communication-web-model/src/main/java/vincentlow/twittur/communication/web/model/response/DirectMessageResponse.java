package vincentlow.twittur.communication.web.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vincentlow.twittur.base.web.model.response.BaseResponse;
import vincentlow.twittur.communication.model.constant.DirectMessageStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DirectMessageResponse extends BaseResponse {

  private static final long serialVersionUID = 658975927232614242L;

  private String senderId;

  private String recipientId;

  private String message;

  private DirectMessageStatus status;
}
