package vincentlow.twittur.communication.model.constant;

public enum DirectMessageStatus {
  SENT, // message sent & is awaiting delivery to the recipient. (single check)
  DELIVERED, // successfully delivered to the recipient's device. (double check)
  READ,
  FAILED,
  PENDING
}
