package vincentlow.twittur.communication.service.helper;

import java.util.Objects;

import vincentlow.twittur.communication.client.model.response.AccountCredentialResponse;
import vincentlow.twittur.communication.web.model.response.exception.NotFoundException;

public class ValidatorHelper {

  public static AccountCredentialResponse validateAccount(AccountCredentialResponse account, String errorMessage) {

    if (Objects.isNull(account)) {
      throw new NotFoundException(errorMessage);
    }
    return account;
  }
}
