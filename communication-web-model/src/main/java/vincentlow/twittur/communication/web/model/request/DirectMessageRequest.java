package vincentlow.twittur.communication.web.model.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor // needed when req field = 1
@AllArgsConstructor
public class DirectMessageRequest implements Serializable {

  private static final long serialVersionUID = 4509229736826795970L;

  private String message;
}
