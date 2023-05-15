package vincentlow.twittur.communication.model.constant;

public enum ErrorCode {

  REQUEST_MUST_NOT_BE_NULL("request must not be null"),

  MESSAGE_MUST_NOT_BE_NULL("message must not be null"),

  MESSAGE_MUST_NOT_BE_BLANK("message must not be blank");

  private String message;

  ErrorCode(String message) {

    this.message = message;
  }

  public String getMessage() {

    return message;
  }
}
