package vincentlow.twittur.communication.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import vincentlow.twittur.communication.model.constant.DirectMessageStatus;

@Entity
@Table(name = "direct_message")
@Data
public class DirectMessage extends BaseEntity {

  private static final long serialVersionUID = -1314144049085931158L;

  @Column(name = "sender_id")
  private String senderId;

  @Column(name = "recipient_id")
  private String recipientId;

  @Column(name = "message")
  private String message;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private DirectMessageStatus status;
}
