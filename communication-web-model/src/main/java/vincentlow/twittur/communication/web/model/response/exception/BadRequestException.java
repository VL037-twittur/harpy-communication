package vincentlow.twittur.communication.web.model.response.exception;

public class BadRequestException extends RuntimeException {

  public BadRequestException(String message) {

    super(message);
  }
}
