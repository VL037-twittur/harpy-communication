package vincentlow.twittur.communication.web.model.response.exception;

public class ServiceUnavailableException extends RuntimeException {

  public ServiceUnavailableException(String message) {

    super(message);
  }
}
